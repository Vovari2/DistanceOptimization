package me.vovari2.distanceoptimization.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1)
            return getPermissionedCommands(sender);

        if (args.length == 2 && args[0].equals("profiler"))
            return List.of("start", "stop", "confirm");

        if (args.length == 3 && args[0].equals("profiler") && args[1].equals("start"))
            return null;

        return List.of();
    }

    private List<String> getPermissionedCommands(CommandSender sender){
        List<String> commands = Executor.getCommandList();
        commands.removeIf(command -> !sender.hasPermission("distance_optimization." + command));
        return commands;
    }
}
