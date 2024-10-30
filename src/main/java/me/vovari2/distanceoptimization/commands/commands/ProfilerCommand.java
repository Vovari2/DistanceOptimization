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

        if (args.length < 2)
            throw new ComponentException(Text.get("warning.command_incorrectly"));

        switch(args[1].toLowerCase()){
            case "start":{
                if (args.length != 3)
                    throw new ComponentException(Text.get("warning.command_incorrectly"));

                Player targetPlayer = DistanceOptimization.getPlayer(args[2]);
                if (targetPlayer == null)
                    throw new ComponentException(Text.get("warning.player_name_incorrectly"));

                if (Profiler.isStarted())
                    throw new ComponentException(Text.get("warning.profiler_already_started"));

                Profiler.start(targetPlayer, player);
            } break;
            case "stop": {
                if (args.length != 2)
                    throw new ComponentException(Text.get("warning.command_incorrectly"));

                if (!Profiler.isStarted())
                    throw new ComponentException(Text.get("warning.profiler_not_started_yet"));

                Profiler.stop(sender);
            } break;
            case "confirm": {
                if (args.length != 2)
                    throw new ComponentException(Text.get("warning.command_incorrectly"));

                Profiler.confirm(sender);
            } break;
            default: throw new ComponentException(Text.get("warning.command_incorrectly"));
        }
    }
}
