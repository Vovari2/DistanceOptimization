package me.vovari2.distanceoptimization.commands;

import me.vovari2.distanceoptimization.DistanceOptimization;
import org.bukkit.command.CommandSender;

public abstract class Command {
    protected CommandSender sender;
    protected String[] args;
    protected DistanceOptimization plugin;

    public Command(CommandSender sender, String[] args, DistanceOptimization plugin){
        this.sender = sender;
        this.args = args;
        this.plugin = plugin;
    }

    public abstract void execute() throws Exception;
}
