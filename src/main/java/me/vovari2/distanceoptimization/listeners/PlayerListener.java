package me.vovari2.distanceoptimization.listeners;

import me.vovari2.distanceoptimization.Config;
import me.vovari2.distanceoptimization.DistanceOptimization;
import me.vovari2.distanceoptimization.managers.ChunkManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

    private final DistanceOptimization instance;
    private final World world;
    public PlayerListener(DistanceOptimization instance, World world){
        this.instance = instance;
        this.world = world;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!world.equals(event.getTo().getWorld())
                || !event.hasChangedPosition())
            return;

        Player player = event.getPlayer();
        if (instance.IS_BAD_MSPT && ChunkManager.getScore(player))
            setOptimizeDistance(player);
        else setNormalDistance(player);
    }

    private void setOptimizeDistance(Player player){
        if (player.getViewDistance() == Config.OPTIMIZE_VIEW_DISTANCE)
            return;

        player.setViewDistance(Config.OPTIMIZE_VIEW_DISTANCE);
        player.setSendViewDistance(Config.NORMAL_SEND_VIEW_DISTANCE);
        player.setSimulationDistance(Config.OPTIMIZE_SIMULATION_DISTANCE);
    }
    private void setNormalDistance(Player player){
        if (player.getViewDistance() == world.getViewDistance())
            return;

        player.setViewDistance(world.getViewDistance());
        player.setSendViewDistance(world.getSendViewDistance());
        player.setSimulationDistance(world.getSimulationDistance());
    }
}
