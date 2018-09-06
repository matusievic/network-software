package by.bsuir.nc.client.runner;

import by.bsuir.nc.client.Client;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }
}
