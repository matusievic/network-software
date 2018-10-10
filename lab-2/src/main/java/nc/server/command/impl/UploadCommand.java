package nc.server.command.impl;

import nc.server.command.ServerCommand;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadCommand implements ServerCommand {
    private Map<String, Long> interruptedUploads = new HashMap<>();

    @Override
    public void execute(DatagramSocket server, SocketAddress address, List<byte[]> datagrams) throws IOException {
        /*System.out.println("CLIENT: " + command);
        System.out.println("INFO: Uploading started");

        DataOutputStream output = new DataOutputStream(server.getOutputStream());
        DataInputStream input = new DataInputStream(server.getInputStream());

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

                if (!server.isConnected() || server.isClosed()) {
                    throw new SocketException("Client disconnected");
                }
            }
        } catch (SocketException e) {
            interruptedUploads.put(fileName, receivedByteCount - 1);
            System.out.println("INFO: Uploading interrupted");
            return;
        }

        downloadedFile.close();

        System.out.println("INFO: Uploading finished");*/
    }
}
