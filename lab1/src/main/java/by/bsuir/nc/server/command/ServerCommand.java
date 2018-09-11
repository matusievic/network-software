package by.bsuir.nc.server.command;

import java.io.IOException;
import java.net.Socket;

public interface ServerCommand {
    void execute(Socket client, String command) throws IOException;
}
