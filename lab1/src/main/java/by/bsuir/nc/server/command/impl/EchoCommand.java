package by.bsuir.nc.server.command.impl;

import by.bsuir.nc.server.command.ServerCommand;

import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;

public class EchoCommand implements ServerCommand {
    public void execute(Socket client, Scanner input, Writer output, String command) throws IOException {
        String message = command.substring(command.indexOf(' ') + 1);

        System.out.println("CLIENT: " + message);
        output.write("SERVER: " + message + '\n');
        output.flush();
    }
}
