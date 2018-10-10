package nc.client.command.impl;

import nc.client.command.ClientCommand;

import java.io.IOException;
import java.net.DatagramSocket;

public class DownloadCommand implements ClientCommand {
    @Override
    public void execute(DatagramSocket client, String command) throws IOException {
        /*
        System.out.println("INFO: Downloading started");

        Writer output = new PrintWriter(client.getOutputStream());
        DataInputStream input = new DataInputStream(client.getInputStream());

        output.write(command + '\n');
        output.flush();

        byte[] lengthBuffer = new byte[Long.BYTES];
        input.read(lengthBuffer);

        long length = ByteBuffer.wrap(lengthBuffer).getLong();
        if (length == 0) {
            System.out.println("INFO: File not found");
            return;
        }

        String fileName = command.substring(command.indexOf(' ') + 1);
        OutputStream downloadedFile = new FileOutputStream("downloads" + File.separator + fileName, true);

        long position = 0;
        byte[] buffer = new byte[1];
        int count;
        long startTime = System.nanoTime();
        while ((count = input.read(buffer)) > 0) {
            downloadedFile.write(buffer, 0, count);
            downloadedFile.flush();
            position += count;

            if (position >= length) {
                break;
            }
        }

        long finishTime = System.nanoTime();
        downloadedFile.close();

        System.out.println("INFO: Downloading finished");
        System.out.println("Bitrate: " + (length / ((finishTime - startTime) / 1000000000.0)) + " bps");
        */
    }
}
