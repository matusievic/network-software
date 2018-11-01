package nc.client.command.impl;

import nc.client.command.ClientCommand;
import nc.client.command.CommandProvider;
import nc.client.command.impl.upload.AckListener;

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

        byte[] buffer = new byte[126];
        short current = 1;
        short total = (short) (Math.ceil((double) length / 126) + 1);
        int count;

        while ((count = input.read(buffer)) > 0) {
            this.waitForWindow(client);
            DatagramPacket packet = createPacket(buffer, current, total, count);
            client.send(packet);
            output.put(current++, packet);
            window.getAndIncrement();
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
        byte[] datagram = new byte[200];
        datagram[0] = 4;
        datagram[1] = (byte) (current >> 8);
        datagram[2] = (byte) current;
        datagram[3] = (byte) (total >> 8);
        datagram[4] = (byte) total;
        datagram[5] = (byte) count;
        System.arraycopy(buffer, 0, datagram, 6, count);
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
            client.send(packet);
        }
    }

    private void prepareResources(DatagramSocket client, String command) throws Exception {
        fileName = command.split(" ")[1];
        File file = new File("files" + File.separator + fileName);
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
        System.out.println("RESPONSE > SUCCESSFUL " + (length / ((finishTime - startTime) / 1000000000.0)) + " bps");
    }
}
