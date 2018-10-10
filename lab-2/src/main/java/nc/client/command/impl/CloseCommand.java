package nc.client.command.impl;

import nc.client.command.ClientCommand;

import java.io.IOException;
import java.net.DatagramSocket;

public class CloseCommand implements ClientCommand {
    @Override
    public void execute(DatagramSocket client, String command) throws IOException {
        /*PrintWriter output = new PrintWriter(client.getOutputStream());
        output.write("CLOSE\n");
        output.flush();

        Scanner input = new Scanner(client.getInputStream());
        if (input.hasNext()) {
            System.out.println(input.nextLine());
        }

        client.close();*/
    }
}
