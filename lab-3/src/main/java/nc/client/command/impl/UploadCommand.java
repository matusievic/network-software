package nc.client.command.impl;

import nc.client.command.ClientCommand;
import nc.client.command.CommandProvider;
import nc.util.AckListener;
import nc.util.DataBuilder;
import nc.util.Operations;
import nc.util.PacketConf;
import nc.util.Types;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UploadCommand implements ClientCommand {
    private String fileName;
    private FileInputStream input;
    private Map<Short, DatagramPacket> output;
    private AtomicInteger window;
    private AckListener ackListener;
    private short length;
    private long startTime;

    @Override
    public void execute(DatagramSocket client, String command) throws Exception {
        this.prepareResources(client, command);

        DatagramPacket initPacket = this.createInitPacket();
        client.send(initPacket);

        byte[] buffer = new byte[PacketConf.payloadSize];
        short current = 1;
        short total = (short) Math.ceil((double) length / PacketConf.payloadSize);
        int count;

        while ((count = input.read(buffer)) > 0) {
            this.waitForWindow(client);
            DatagramPacket packet = createPacket(buffer, current, total, count);
            client.send(packet);
            output.put(current++, packet);
            window.getAndIncrement();
            System.out.println("\tsending " + current + " / " + total);
        }

        while (window.get() != 0) {
            Thread.sleep(1000);
        }
        cleanResources(input);
    }

    private DatagramPacket createInitPacket() {
        byte[] initData = new byte[200];
        initData[0] = 4;
        System.arraycopy((fileName + "\n").getBytes(), 0, initData, 6, fileName.length() + 1);
        return new DatagramPacket(initData, 200, CommandProvider.address, CommandProvider.port);
    }

    private DatagramPacket createPacket(byte[] buffer, short current, short total, int count) {
        byte[] datagram = DataBuilder.build(Operations.UPLOAD, Types.PACKET, current, total, buffer);
        return new DatagramPacket(datagram, 200, CommandProvider.address, CommandProvider.port);
    }

    private void waitForWindow(DatagramSocket client) throws InterruptedException, IOException {
        int sleepCount = 0;
        int maxSleepCount = 10;
        while (window.get() >= 10) {
            Thread.sleep(1000);
            sleepCount++;
            if (sleepCount >= maxSleepCount) {
                this.resendPackets(client);
                sleepCount = 0;
            }
        }
    }

    private void resendPackets(DatagramSocket client) throws IOException {
        for (DatagramPacket packet : output.values()) {
            byte[] datagram = packet.getData();
            short current = (short) (datagram[1] << 8 | datagram[2]);
            short total = (short) (datagram[3] << 8 | datagram[4]);
            client.send(packet);
            System.out.println("\tresending " + current + " / " + total);
        }
    }

    private void prepareResources(DatagramSocket client, String command) throws Exception {
        fileName = command.split(" ")[1];
        File file = new File("lab-2/client" + File.separator + fileName);
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("RESPONSE > File not found");
            throw new Exception(e);
        }
        length = (short) input.available();
        startTime = System.nanoTime();

        output = Collections.synchronizedMap(new HashMap<>(10));
        window = new AtomicInteger(0);
        ackListener = new AckListener(client, output, window);
        ackListener.start();
    }

    private void cleanResources(FileInputStream input) throws IOException {
        long finishTime = System.nanoTime();
        input.close();
        ackListener.interrupt();
        System.out.println("RESPONSE > SUCCESSFUL " + (length / ((finishTime - startTime) / 1000000000.0)) + " bps");
    }
}
