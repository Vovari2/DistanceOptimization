package me.vovari2.distanceoptimization.commands.commands;

import me.vovari2.distanceoptimization.DistanceOptimization;
import me.vovari2.distanceoptimization.Text;
import me.vovari2.distanceoptimization.commands.Command;
import me.vovari2.distanceoptimization.exceptions.ComponentException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand extends Command {
    public HelpCommand(CommandSender sender, String[] args, DistanceOptimization plugin) {
        super(sender, args, plugin);
    }

    @Override
    public void execute() throws ComponentException {
        if (!sender.hasPermission("distance_optimization.help"))
            throw new ComponentException(Text.get("warning.dont_have_permission"));

        if (args.length > 1)
            throw new ComponentException(Text.get("warning.command_incorrectly"));

        if (!(sender instanceof Player player))
            throw new ComponentException(Text.get("warning.you_not_player"));

        player.sendMessage(Text.get("command.help"));
    }
}
