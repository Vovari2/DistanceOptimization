package me.vovari2.distanceoptimization.commands.commands;

import me.vovari2.distanceoptimization.DistanceOptimization;
import me.vovari2.distanceoptimization.Text;
import me.vovari2.distanceoptimization.commands.Command;
import me.vovari2.distanceoptimization.exceptions.ComponentException;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends Command {
    public ReloadCommand(CommandSender sender, String[] args, DistanceOptimization plugin) {
        super(sender, args, plugin);
    }

    @Override
    public void execute() throws ComponentException {
        if (!sender.hasPermission("distance_optimization.reload"))
            throw new ComponentException(Text.get("warning.dont_have_permission"));

        if (args.length > 1)
            throw new ComponentException(Text.get("warning.command_incorrectly"));

        plugin.onDisable();
        plugin.onEnable();

        sender.sendMessage(Text.get("command.reload"));
    }
}
