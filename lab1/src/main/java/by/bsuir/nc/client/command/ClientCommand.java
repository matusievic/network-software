package by.bsuir.nc.client.command;

import java.io.IOException;
import java.net.Socket;

public interface ClientCommand {
    void execute(Socket client, String command) throws IOException;
}
