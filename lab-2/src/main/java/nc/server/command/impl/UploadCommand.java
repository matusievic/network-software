package nc.server.command.impl;

import nc.server.command.ServerCommand;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class UploadCommand implements ServerCommand {
    private String outputFileName;

    @Override
    public void execute(DatagramSocket server, SocketAddress address, List<byte[]> datagrams) throws IOException {
        byte[] header = datagrams.get(0);
        short current = (short) (header[1] << 8 | header[2]);
        short total = (short) (header[3] << 8 | header[4]);
        if (current == 0) {
            outputFileName = new String(Arrays.copyOfRange(header, 6, header.length)).split("\n")[0];
            Files.createFile(Paths.get("lab-2/server" + File.separator + outputFileName));
            return;
        }

        FileOutputStream output = new FileOutputStream("lab-2/server" + File.separator + outputFileName, true);
        output.write(Arrays.copyOfRange(header, 6, 6 + header[5]));

        header[0] = 5;
        DatagramPacket ack = new DatagramPacket(header, 0, header.length, address);
        server.send(ack);

        if (current == total) {
            System.out.println(address + " > downloaded");
        }
    }
}
