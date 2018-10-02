package by.bsuir.nc.client.command.impl;

import by.bsuir.nc.client.command.ClientCommand;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CloseCommand implements ClientCommand {
    @Override
    public void execute(Socket server, String command) throws IOException {
        PrintWriter output = new PrintWriter(server.getOutputStream());
        output.write("CLOSE\n");
        output.flush();

        Scanner input = new Scanner(server.getInputStream());
        if (input.hasNext()) {
            System.out.println(input.nextLine());
        }

        server.close();
    }
}
