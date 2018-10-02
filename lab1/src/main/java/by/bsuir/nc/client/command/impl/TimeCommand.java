package by.bsuir.nc.client.command.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;

public class TimeCommand implements by.bsuir.nc.client.command.ClientCommand {
    @Override
    public void execute(Socket server, String command) throws IOException {
        Writer output = new PrintWriter(server.getOutputStream());
        output.write(command + '\n');
        output.flush();

        Scanner input = new Scanner(server.getInputStream());
        if (input.hasNext()) {
            System.out.println(input.nextLine());
        }
    }
}
