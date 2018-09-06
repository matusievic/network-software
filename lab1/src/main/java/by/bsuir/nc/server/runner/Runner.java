package by.bsuir.nc.server.runner;

import by.bsuir.nc.server.Server;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.run();
    }
}
