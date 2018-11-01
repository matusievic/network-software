package nc.server;

import nc.server.command.CommandProvider;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Collections;

public final class Server {
    private CommandProvider provider;
    private DatagramSocket server;
    private DatagramPacket packet;
    private boolean interrupted;
    private int port;

    public Server() {
        this.port = 3345;
    }

    public Server(int port) {
        this.port = port;
    }

    public void run() throws IOException {
        server = new DatagramSocket(port);
        provider = CommandProvider.instance;

        byte[] buf = new byte[200];
        packet = new DatagramPacket(buf, 200);
        while (!interrupted) {
            server.receive(packet);
            try {
                processPacket(packet);
            } catch (Exception e) {}
        }
    }

    private void processPacket(DatagramPacket packet) throws Exception {
        byte[] data = packet.getData();
        byte type = data[0];
        InetSocketAddress address = (InetSocketAddress) packet.getSocketAddress();
        provider.command(type).execute(server, address, Collections.singletonList(data));
    }

    public void interrupt() {
        this.interrupted = true;
    }
}
