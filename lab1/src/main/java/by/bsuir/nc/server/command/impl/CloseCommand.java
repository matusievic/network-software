package by.bsuir.nc.server.command.impl;

import by.bsuir.nc.server.command.ServerCommand;

import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;

public class CloseCommand implements ServerCommand {
    public void execute(Socket client, Scanner input, Writer output, String command) throws IOException {
        System.out.println("CLIENT: CLOSE");
        output.write("SERVER: Connection is closed\n");
        output.flush();
        client.close();
        System.out.println("INFO: The connection is closed");
    }
}
