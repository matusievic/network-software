package by.bsuir.nc.server.command.impl;

import by.bsuir.nc.server.command.ServerCommand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class UploadCommand implements ServerCommand {
    private Map<String, Long> interruptedUploads = new HashMap<>();

    @Override
    public void execute(Socket client, String command) throws IOException {
        System.out.println("CLIENT: " + command);
        System.out.println("INFO: Uploading started");

        DataOutputStream output = new DataOutputStream(client.getOutputStream());
        DataInputStream input = new DataInputStream(client.getInputStream());

        String fileName = command.substring(command.indexOf(' ') + 1);
        OutputStream downloadedFile = new FileOutputStream("files" + File.separator + fileName, true);

        long initPosition = 0;
        if (interruptedUploads.containsKey(fileName)) {
            initPosition = interruptedUploads.get(fileName);
        }
        output.writeLong(initPosition);
        output.flush();

        byte[] lengthBuffer = new byte[Long.BYTES];
        input.read(lengthBuffer);

        long length = ByteBuffer.wrap(lengthBuffer).getLong();
        if (length == 0) {
            System.out.println("INFO: File not found");
            return;
        }

        byte[] buffer = new byte[1];
        int count;
        long receivedByteCount = 0;
        try {
            while ((count = input.read(buffer)) > 0) {
                downloadedFile.write(buffer, 0, count);
                downloadedFile.flush();
                receivedByteCount += count;

                if (receivedByteCount >= length) {
                    if (interruptedUploads.containsKey(fileName)) {
                        interruptedUploads.remove(fileName);
                    }
                    break;
                }

                if (!client.isConnected() || client.isClosed()) {
                    throw new SocketException("Client disconnected");
                }
            }
        } catch (SocketException e) {
            interruptedUploads.put(fileName, receivedByteCount - 1);
            System.out.println("INFO: Uploading interrupted");
            return;
        }

        downloadedFile.close();

        System.out.println("INFO: Uploading finished");
    }
}
