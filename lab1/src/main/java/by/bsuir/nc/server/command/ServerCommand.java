package by.bsuir.nc.server.command;

import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;

public interface ServerCommand {
    void execute(Socket client, Scanner input, Writer output, String command) throws IOException;
}
