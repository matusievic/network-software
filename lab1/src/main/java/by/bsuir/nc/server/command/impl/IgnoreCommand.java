package by.bsuir.nc.server.command.impl;

import by.bsuir.nc.server.command.ServerCommand;

import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;

public class IgnoreCommand implements ServerCommand {
    @Override
    public void execute(Socket client, Scanner input, Writer output, Object[] params) throws IOException {
        String message = null;
        if (params.length >= 1) {
            StringBuilder builder = new StringBuilder();
            for (Object p : params) {
                builder.append(p.toString() + ' ');
            }
            message = builder.toString();
        }

        System.out.println("CLIENT: " + message);
    }
}
