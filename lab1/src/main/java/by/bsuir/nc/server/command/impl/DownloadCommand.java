package by.bsuir.nc.server.command.impl;

import by.bsuir.nc.server.command.ServerCommand;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

public class DownloadCommand implements ServerCommand {
    @Override
    public void execute(Socket client, String command) throws IOException {
        System.out.println("CLIENT: " + command);
        System.out.println("INFO: Downloading started");
        long startTime = System.nanoTime();

        DataOutputStream output = new DataOutputStream(client.getOutputStream());
        File file = new File("files" + File.separator + command.split(" ")[1]);
        FileInputStream input;

        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            output.writeLong(0);
            output.flush();
            System.out.println("INFO: File not found");
            return;
        }

        long length = file.length();
        output.writeLong(length);
        output.flush();


        byte[] buffer = new byte[1];
        int count;
        while ((count = input.read(buffer)) > 0) {
            output.write(buffer, 0, count);
            output.flush();
        }

        input.close();

        long finishTime = System.nanoTime();
        System.out.println("INFO: Downloading finished");
        System.out.println("Bitrate: " + (length / ((finishTime - startTime) / 1000000000.0)) + " bps");
    }
}
