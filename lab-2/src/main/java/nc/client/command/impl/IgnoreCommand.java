package nc.client.command.impl;

import nc.client.command.ClientCommand;

import java.io.IOException;
import java.net.DatagramSocket;

public class IgnoreCommand implements ClientCommand {
    @Override
    public void execute(DatagramSocket client, String command) throws IOException {
        System.out.println("RESPONSE > Unrecognized command");
    }
}
