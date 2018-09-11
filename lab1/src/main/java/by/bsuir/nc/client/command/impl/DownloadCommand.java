package by.bsuir.nc.client.command.impl;

import by.bsuir.nc.client.command.ClientCommand;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;

public class DownloadCommand implements ClientCommand {
    @Override
    public void execute(Socket client, String command) throws IOException {
        Writer output = new PrintWriter(client.getOutputStream());
        InputStream input = new DataInputStream(client.getInputStream());

        output.write(command + '\n');
        output.flush();

        String fileName = command.substring(command.indexOf(' ') + 1);
        OutputStream downloadedFile = new FileOutputStream("downloads" + File.separator + fileName);

        byte[] buffer = new byte[16 * 1024];
        int count;
        while ((count = input.read(buffer)) > 0) {
            downloadedFile.write(buffer, 0, count);
            downloadedFile.flush();
        }

        downloadedFile.close();
        input.close();
    }
}
