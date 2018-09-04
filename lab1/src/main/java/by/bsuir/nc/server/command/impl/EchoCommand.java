package by.bsuir.nc.server.command.impl;

import by.bsuir.nc.server.command.ServerCommand;

import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;

public class EchoCommand implements ServerCommand {
    public void execute(Socket client, Scanner input, Writer output, Object[] params) throws IOException {
        String message = null;

        if (params.length > 1) {
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < params.length; i++) {
                builder.append(params[i].toString()).append(' ');
            }
            message = builder.toString();
        }

        System.out.println("CLIENT: " + params[0] + message);
        output.write("SERVER: " + message + '\n');
        output.flush();
    }
}
