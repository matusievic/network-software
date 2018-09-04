package by.bsuir.nc.server;

import by.bsuir.nc.server.command.CommandProvider;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public final class Server {
    private CommandProvider provider;
    private ServerSocket server;
    private boolean interrupted;
    private int port;

    public Server() {
        this.port = 3345;
    }

    public Server(int port) {
        this.port = port;
    }

    public void run() throws IOException {
        server = new ServerSocket(port);
        provider = CommandProvider.instance;
        System.out.println("INFO: Server started");

        while (!interrupted) {
            // wait for the client
            Socket client = server.accept();
            System.out.println("INFO: Client joined");

            Writer output = new PrintWriter(client.getOutputStream());
            System.out.println("INFO: Output stream initialized");

            Scanner input = new Scanner(client.getInputStream());
            System.out.println("INFO: Input stream initialized");

            // event handling cycle
            while (!client.isClosed() && input.hasNextLine()) {
                String[] command = input.nextLine().split(" ");
                provider.command(command[0]).execute(client, input, output, command);
            }
        }
    }

    public void interrupt() {
        this.interrupted = true;
    }
}
