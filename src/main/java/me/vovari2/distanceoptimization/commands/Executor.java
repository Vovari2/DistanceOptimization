package me.vovari2.distanceoptimization.commands;

import me.vovari2.distanceoptimization.DistanceOptimization;
import me.vovari2.distanceoptimization.Text;
import me.vovari2.distanceoptimization.commands.commands.HelpCommand;
import me.vovari2.distanceoptimization.commands.commands.ReloadCommand;
import me.vovari2.distanceoptimization.exceptions.ComponentException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Executor implements CommandExecutor {
    private static HashMap<String, Class<? extends Command>> commands;
    public static List<String> getCommandList(){
        return new ArrayList<>(commands.keySet());
    }
    public Executor(){
        commands = new HashMap<>();
        commands.put("help", HelpCommand.class);
        commands.put("reload", ReloadCommand.class);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command commandObject, @NotNull String label, @NotNull String[] args) {
        try{
            if (args.length == 0){
                executeCommand("help", sender, args);
                return true;
            }

            String str = args[0].toLowerCase();
            if (!commands.containsKey(str))
                throw new ComponentException(Text.get("warning.command_incorrectly"));

            if (!DistanceOptimization.isEnable() && !(args.length == 1 && args[0].equals("reload")))
                throw new ComponentException(Text.get("warning.command_incorrectly"));

            executeCommand(args[0].toLowerCase(), sender, args);
        }
        catch (ComponentException error){
            sender.sendMessage(error.getComponentMessage());
        }
        catch (Exception error){
            error.printStackTrace();
            sender.sendMessage(error.getMessage());
        }
        return true;
    }

    private void executeCommand(String subcommand, CommandSender sender, String[] args) throws Exception{
        Command command = commands.get(subcommand).getDeclaredConstructor(CommandSender.class, String[].class, DistanceOptimization.class).newInstance(sender, args, DistanceOptimization.getInstance());
        command.execute();
    }
}
