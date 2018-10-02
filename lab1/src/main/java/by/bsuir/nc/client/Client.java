package by.bsuir.nc.client;

import by.bsuir.nc.client.command.CommandProvider;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public final class Client {
    private CommandProvider provider;
    private Socket client;
    private String hostname;
    private int port;

    public Client() {
        this.hostname = "localhost";
        this.port = 3345;
    }

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void run() throws IOException {
        client = new Socket("192.168.10.2", 3345);
        provider = CommandProvider.instance;
        System.out.println("INFO: Client initialized");

        if (!client.isConnected()) {
            client.connect(new InetSocketAddress(hostname, port));
        }
        System.out.println("INFO: Joined to the Server");

        Scanner input = new Scanner(System.in);
        System.out.println("INFO: Command input stream initialized");

        while (!client.isClosed() && input.hasNextLine()) {
            String command = input.nextLine();
            provider.command(command.split(" ")[0]).execute(client, command);
        }
    }
}
