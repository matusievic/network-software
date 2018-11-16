package nc.server;

import nc.server.command.CommandProvider;
import nc.util.BitOps;
import nc.util.PacketConf;
import nc.util.Types;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Server {
    private Map<InetSocketAddress, List<byte[]>> datagrams;
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
        datagrams = new HashMap<>();
        server = new DatagramSocket(port);
        provider = CommandProvider.instance;

        byte[] buf = new byte[200];
        packet = new DatagramPacket(buf, 200);
        while (!interrupted) {
            server.receive(packet);
            try {
                processPacket(packet);
            } catch (Exception e) {
            }
        }
    }

    private void processPacket(DatagramPacket packet) throws Exception {
        InetSocketAddress address = (InetSocketAddress) packet.getSocketAddress();
        byte[] data = packet.getData();
        byte operation = data[0];
        byte type = data[1];
        short current = BitOps.byteToShort(Arrays.copyOfRange(data, PacketConf.currentOffset, PacketConf.currentOffset + 2));
        short total = BitOps.byteToShort(Arrays.copyOfRange(data, PacketConf.totalOffset, PacketConf.totalOffset + 2));

        if (type == Types.PACKET) {
            sendAck(address, data);
        }

        if (current == 1) {
            ArrayList<byte[]> list = new ArrayList<>();
            list.add(data);
            datagrams.put(address, list);
        } else {
            datagrams.get(address).add(data);
        }

        if (current == total) {
            provider.command(operation).execute(server, address, datagrams.remove(address));
        }
    }

    private void sendAck(InetSocketAddress address, byte[] data) throws IOException {
        byte[] datagram = Arrays.copyOf(data, data.length);
        datagram[1] = 1;
        DatagramPacket packet = new DatagramPacket(datagram, 0, data.length, address);
        server.send(packet);
    }

    public void interrupt() {
        this.interrupted = true;
    }
}
