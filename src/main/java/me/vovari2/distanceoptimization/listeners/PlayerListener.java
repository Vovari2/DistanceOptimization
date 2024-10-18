package me.vovari2.distanceoptimization.listeners;

import me.vovari2.distanceoptimization.Config;
import me.vovari2.distanceoptimization.DistanceOptimization;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class PlayerListener implements Listener {

    private final DistanceOptimization instance;
    private final World world;
    private final HashMap<Player, Long> lastPlayersMovementOnFlying;
    public PlayerListener(DistanceOptimization instance, World world){
        this.instance = instance;
        this.world = world;
        lastPlayersMovementOnFlying = new HashMap<>();
        new BukkitRunnable(){ @Override public void run() { lastPlayersMovementOnFlying.clear(); }
        }.runTaskTimer(DistanceOptimization.getInstance(), 0, 1728000);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!equalsWorld(event.getTo().getWorld())
                || !event.hasChangedBlock())
            return;

        Player player = event.getPlayer();
        long statisticValue = getStatisticOfPlayer(player);
        if (!lastPlayersMovementOnFlying.containsKey(player)){
            lastPlayersMovementOnFlying.put(player, statisticValue);
            return;
        }

        long playerValue = lastPlayersMovementOnFlying.get(player);
        if (!instance.IS_BAD_MSPT || playerValue == statisticValue){
            setNormalDistance(player);
            return;
        }

        ItemStack itemStack = player.getInventory().getItem(EquipmentSlot.CHEST);
        if (itemStack.isEmpty() || itemStack.getType() != Material.ELYTRA)
            return;

        lastPlayersMovementOnFlying.put(player, statisticValue);
        setOptimizeDistance(player);
    }

    private void setOptimizeDistance(Player player){
        if (player.getViewDistance() == Config.OPTIMIZE_VIEW_DISTANCE)
            return;

        player.setViewDistance(Config.OPTIMIZE_VIEW_DISTANCE);
        player.setSendViewDistance(Config.WORLD_SEND_VIEW_DISTANCE);
        player.setSimulationDistance(Config.OPTIMIZE_SIMULATION_DISTANCE);
    }
    private void setNormalDistance(Player player){
        if (player.getViewDistance() == world.getViewDistance())
            return;

        player.setViewDistance(world.getViewDistance());
        player.setSendViewDistance(world.getSendViewDistance());
        player.setSimulationDistance(world.getSimulationDistance());
    }

    private boolean equalsWorld(World world){
        return world.equals(this.world);
    }
    private long getStatisticOfPlayer(Player player){
        return player.getStatistic(Statistic.AVIATE_ONE_CM);
    }
}
