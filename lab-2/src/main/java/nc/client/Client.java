package nc.client;

import nc.client.command.CommandProvider;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class Client {
    private CommandProvider provider;
    private DatagramSocket client;
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
        client = new DatagramSocket();
        provider = CommandProvider.instance;

        Scanner input = new Scanner(System.in);

        while (!client.isClosed() && input.hasNextLine()) {
            String command = input.nextLine();
            provider.command(command.split(" ")[0]).execute(client, command);
        }
    }
}
