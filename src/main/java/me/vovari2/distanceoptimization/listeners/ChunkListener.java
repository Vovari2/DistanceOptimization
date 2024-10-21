package me.vovari2.distanceoptimization.listeners;

import me.vovari2.distanceoptimization.managers.ChunkManager;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkListener implements Listener {
    private final World world;
    public ChunkListener(World world){
        this.world = world;
    }


    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event){
        Chunk chunk = event.getChunk();
        if (!world.equals(chunk.getWorld()))
            return;

        Player player = getNearPlayer(chunk.getX(), chunk.getZ(), world.getViewDistance() + 5);
        if (player == null)
            return;

        ChunkManager.add(player, event.isNewChunk() ? 2 : 1);
    }

    private Player getNearPlayer(int x, int z, double maxDistance){
        Player player = null;
        double nearDistance = Math.hypot(maxDistance, maxDistance);
        for (Player targetPlayer : world.getPlayers()){
            int playerX = targetPlayer.getChunk().getX(), playerZ = targetPlayer.getChunk().getZ();
            double playerDistance = Math.hypot(x - playerX, z - playerZ);
            if (nearDistance < playerDistance)
                continue;

            nearDistance = playerDistance;
            player = targetPlayer;
        }
        return player;
    }
}
