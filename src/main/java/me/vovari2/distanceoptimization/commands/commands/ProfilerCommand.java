package me.vovari2.distanceoptimization.commands.commands;

import me.vovari2.distanceoptimization.DistanceOptimization;
import me.vovari2.distanceoptimization.Profiler;
import me.vovari2.distanceoptimization.Text;
import me.vovari2.distanceoptimization.commands.Command;
import me.vovari2.distanceoptimization.exceptions.ComponentException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProfilerCommand extends Command {
    public ProfilerCommand(CommandSender sender, String[] args, DistanceOptimization plugin) {
        super(sender, args, plugin);
    }

    @Override
    public void execute() throws ComponentException {
        if (!sender.hasPermission("distance_optimization.profiler"))
            throw new ComponentException(Text.get("warning.dont_have_permission"));

        if (!(sender instanceof Player player))
            throw new ComponentException(Text.get("warning.you_not_player"));

        switch(args[1].toLowerCase()){
            case "start":{
                if (args.length != 3)
                    throw new ComponentException(Text.get("warning.command_incorrectly"));

                Player targetPlayer = DistanceOptimization.getPlayer(args[2]);
                if (targetPlayer == null)
                    throw new ComponentException(Text.get("warning.player_name_incorrectly"));

                Profiler.start(targetPlayer, player);
            }
            case "stop": {
                if (args.length != 2)
                    throw new ComponentException(Text.get("warning.command_incorrectly"));

                Profiler.stop(sender);
            }
            case "confirm": {
                if (args.length != 2)
                    throw new ComponentException(Text.get("warning.command_incorrectly"));

                Profiler.confirm(sender);
            }
            default: throw new ComponentException(Text.get("warning.command_incorrectly"));
        }
    }
}
