package me.vovari2.distanceoptimization.managers;

import me.vovari2.distanceoptimization.DistanceOptimization;
import me.vovari2.distanceoptimization.Text;
import me.vovari2.distanceoptimization.commands.Executor;
import me.vovari2.distanceoptimization.commands.TabCompleter;
import org.bukkit.command.PluginCommand;

public class CommandManager {
    public static void initialize(DistanceOptimization instance){
        PluginCommand command = instance.getCommand("distanceoptimization");
        if (command != null){
            command.setExecutor(new Executor());
            command.setTabCompleter(new TabCompleter());
        }
        else Text.sendMessageToConsole("<red>Command /distanceoptimization (/do) must not be null!");
    }
}
