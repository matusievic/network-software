package nc.server.command.impl;

import nc.server.command.ServerCommand;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.util.List;

public class TimeCommand implements ServerCommand {
    public void execute(DatagramSocket server, SocketAddress address, List<byte[]> datagrams) throws IOException {
        byte[] data = (LocalDateTime.now().toString() + '\n').getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, address);
        server.send(packet);
        System.out.println(address + " > TIME");
    }
}
