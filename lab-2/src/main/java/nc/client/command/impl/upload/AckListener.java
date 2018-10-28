package nc.client.command.impl.upload;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AckListener extends Thread {
    private DatagramSocket client;
    private Map<Short, DatagramPacket> output;
    private AtomicInteger window;
    private boolean isInterrupted;

    public AckListener(DatagramSocket client, Map<Short, DatagramPacket> output, AtomicInteger window) {
        this.output = output;
        this.client = client;
        this.window = window;
    }

    @Override
    public void run() {
        byte[] buf = new byte[200];
        DatagramPacket packet = new DatagramPacket(buf, 200);
        try {
            while (!isInterrupted) {
                client.receive(packet);
                byte[] packContent = packet.getData();
                if (packContent[0] == 5) {
                    short index = (short) ((packContent[1] << 8) | packContent[2]);
                    output.remove(index);
                    window.getAndDecrement();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void interrupt() {
        this.isInterrupted = true;
    }
}
