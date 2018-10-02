package by.bsuir.nc.client.command;

import by.bsuir.nc.client.command.impl.CloseCommand;
import by.bsuir.nc.client.command.impl.DownloadCommand;
import by.bsuir.nc.client.command.impl.EchoCommand;
import by.bsuir.nc.client.command.impl.IgnoreCommand;
import by.bsuir.nc.client.command.impl.TimeCommand;
import by.bsuir.nc.client.command.impl.UploadCommand;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    public static final CommandProvider instance = new CommandProvider();
    private final Map<String, ClientCommand> commands;
    private final ClientCommand ignoreCommand = new IgnoreCommand();

    private CommandProvider() {
        commands = new HashMap<>();

        commands.put("ECHO", new EchoCommand());
        commands.put("TIME", new TimeCommand());
        commands.put("CLOSE", new CloseCommand());

        commands.put("DOWNLOAD", new DownloadCommand());
        commands.put("UPLOAD", new UploadCommand());
    }

    public ClientCommand command(String command) {
        return commands.getOrDefault(command.toUpperCase(), ignoreCommand);
    }
}
