package nc.client.command.impl;

import nc.client.command.ClientCommand;
import nc.client.command.CommandProvider;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class TimeCommand implements ClientCommand {
    @Override
    public void execute(DatagramSocket client, String command) throws IOException {
        byte[] datagram = new byte[200];
        datagram[0] = 1;
        datagram[2] = 1;
        datagram[4] = 1;

        DatagramPacket packet = new DatagramPacket(datagram, 200, CommandProvider.address, CommandProvider.port);
        client.send(packet);

        client.receive(packet);
        System.out.println("RESPONSE > " + new String(packet.getData()).split("\n")[0]);
    }
}
