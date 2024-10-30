package me.vovari2.distanceoptimization;

import me.vovari2.distanceoptimization.exceptions.ComponentException;
import me.vovari2.distanceoptimization.managers.ChunkManager;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Profiler {
    public static Profiler object = null;
    private static boolean waitConfirmForDeletion;
    public static void start(Player profilingPlayer, Player listeningPlayer){
        object = new Profiler(profilingPlayer, listeningPlayer);
        listeningPlayer.sendMessage(Text.get("command.profiler_start")
                .replaceText(TextReplacementConfig.
                        builder().
                        match("<%player%>").
                        replacement(profilingPlayer.getName()).
                        build()));
    }
    public static void stop(CommandSender sender){
        waitConfirmForDeletion = true;
        new BukkitRunnable(){
            @Override
            public void run() {
                waitConfirmForDeletion = false;
            }
        }.runTaskLater(DistanceOptimization.getInstance(), 200L);
        sender.sendMessage(Text.get("command.profiler_stop"));
    }
    public static void confirm(CommandSender sender) throws ComponentException{
        if (waitConfirmForDeletion)
            throw new ComponentException(Text.get("warning.nothing_to_confirm"));

        sender.sendMessage(Text.get("command.confirm"));
        object.listeningPlayer.sendMessage(Text.get("command.confirm"));

        object.task.cancel();
        object = null;
    }

    private final Player profilingPlayer;
    private final Player listeningPlayer;
    private final BukkitTask task;
    private Profiler(Player profilingPlayer, Player listeningPlayer){
        this.profilingPlayer = profilingPlayer;
        this.listeningPlayer = listeningPlayer;
        task = new BukkitRunnable(){
            @Override
            public void run() {
                listeningPlayer.sendMessage(MiniMessage.miniMessage().deserialize(ChunkManager.getStringScore(profilingPlayer)));
            }
        }.runTaskTimer(DistanceOptimization.getInstance(), 0, 20L);
    }
}
