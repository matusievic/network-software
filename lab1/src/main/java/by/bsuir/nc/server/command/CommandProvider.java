package by.bsuir.nc.server.command;

import by.bsuir.nc.server.command.impl.CloseCommand;
import by.bsuir.nc.server.command.impl.DownloadCommand;
import by.bsuir.nc.server.command.impl.EchoCommand;
import by.bsuir.nc.server.command.impl.IgnoreCommand;
import by.bsuir.nc.server.command.impl.TimeCommand;
import by.bsuir.nc.server.command.impl.UploadCommand;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    public static final CommandProvider instance = new CommandProvider();
    private final Map<String, ServerCommand> commands;
    private final ServerCommand ignoreCommand = new IgnoreCommand();

    private CommandProvider() {
        commands = new HashMap<>();

        commands.put("ECHO", new EchoCommand());
        commands.put("TIME", new TimeCommand());
        commands.put("CLOSE", new CloseCommand());

        commands.put("DOWNLOAD", new DownloadCommand());
        commands.put("UPLOAD", new UploadCommand());
    }

    public ServerCommand command(String command) {
        return commands.getOrDefault(command.toUpperCase(), ignoreCommand);
    }
}
