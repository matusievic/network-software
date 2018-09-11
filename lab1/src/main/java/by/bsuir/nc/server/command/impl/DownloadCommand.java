package by.bsuir.nc.server.command.impl;

import by.bsuir.nc.server.command.ServerCommand;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class DownloadCommand implements ServerCommand {
    @Override
    public void execute(Socket client, String command) throws IOException {
        FileInputStream input = new FileInputStream("files" + File.separator + command.split(" ")[1]);
        OutputStream output = new BufferedOutputStream(client.getOutputStream());

        byte[] buffer = new byte[16 * 1024];
        int count;
        while ((count = input.read(buffer)) > 0) {
            output.write(buffer, 0, count);
            output.flush();
        }

        input.close();
        output.close();
    }
}
