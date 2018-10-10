package nc.client.runner;

import nc.client.Client;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }
}
