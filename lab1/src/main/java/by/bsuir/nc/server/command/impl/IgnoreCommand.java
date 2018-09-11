package by.bsuir.nc.server.command.impl;

import by.bsuir.nc.server.command.ServerCommand;

import java.io.IOException;
import java.net.Socket;

public class IgnoreCommand implements ServerCommand {
    @Override
    public void execute(Socket client, String command) throws IOException {
        System.out.println("CLIENT: " + command);
    }
}
