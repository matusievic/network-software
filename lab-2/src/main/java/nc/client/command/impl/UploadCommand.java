package nc.client.command.impl;

import nc.client.command.ClientCommand;

import java.io.IOException;
import java.net.DatagramSocket;

public class UploadCommand implements ClientCommand {
    @Override
    public void execute(DatagramSocket client, String command) throws IOException {
        /*
        System.out.println("CLIENT: " + command);
        System.out.println("INFO: Uploading started");

        DataInputStream input = new DataInputStream(client.getInputStream());
        DataOutputStream output = new DataOutputStream(client.getOutputStream());
        output.writeBytes(command + '\n');
        output.flush();

        String fileName = command.split(" ")[1];
        File file = new File("downloads" + File.separator + fileName);
        FileInputStream uploadedFile;


        byte[] initPositionBuffer = new byte[Long.BYTES];
        while (input.read(initPositionBuffer) < 0) ;

        long initPosition = ByteBuffer.wrap(initPositionBuffer).getLong();

        try {
            uploadedFile = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            output.writeLong(-1L);
            output.flush();
            System.out.println("INFO: File not found");
            return;
        }


        long startTime = System.nanoTime();

        long length = file.length() - uploadedFile.skip(initPosition);
        output.writeLong(length);
        output.flush();

        byte[] buffer = new byte[1];
        int count;
        while ((count = uploadedFile.read(buffer)) > 0) {
            output.write(buffer, 0, count);
            output.flush();
        }

        long finishTime = System.nanoTime();
        uploadedFile.close();

        System.out.println("INFO: Uploading finished");
        System.out.println("Bitrate: " + (length / ((finishTime - startTime) / 1000000000.0)) + " bps");
        */
    }
}
