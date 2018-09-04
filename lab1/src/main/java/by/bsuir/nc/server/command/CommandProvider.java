package by.bsuir.nc.server.command;

import by.bsuir.nc.server.command.impl.CloseCommand;
import by.bsuir.nc.server.command.impl.EchoCommand;
import by.bsuir.nc.server.command.impl.IgnoreCommand;
import by.bsuir.nc.server.command.impl.TimeCommand;

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
    }

    public ServerCommand command(String command) {
        return commands.getOrDefault(command.toUpperCase(), ignoreCommand);
    }
}
