package by.bsuir.nc.server.command.impl;

import by.bsuir.nc.server.command.ServerCommand;

import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

public class TimeCommand implements ServerCommand {
    public void execute(Socket client, Scanner input, Writer output, String command) throws IOException {
        System.out.println("CLIENT: TIME");
        output.write("SERVER: " + LocalDateTime.now().toString() + '\n');
        output.flush();
    }
}
