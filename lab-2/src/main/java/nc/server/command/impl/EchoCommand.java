package nc.server.command.impl;

import nc.server.command.ServerCommand;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;

public class EchoCommand implements ServerCommand {
    public void execute(DatagramSocket server, SocketAddress address, List<byte[]> datagrams) throws IOException {
        byte[] datagram = datagrams.get(0);
        byte[] data = Arrays.copyOfRange(datagram, 5, datagram.length);
        DatagramPacket packet = new DatagramPacket(data, data.length, address);
        server.send(packet);
        System.out.println(address + " >" + new String(data).split("\n")[0]);
    }
}
