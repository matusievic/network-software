package nc.client.command;

import java.io.IOException;
import java.net.DatagramSocket;

public interface ClientCommand {
    void execute(DatagramSocket client, String command) throws IOException;
}
