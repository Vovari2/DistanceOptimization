package me.vovari2.distanceoptimization.managers;

import me.vovari2.distanceoptimization.Config;
import me.vovari2.distanceoptimization.DistanceOptimization;
import me.vovari2.distanceoptimization.listeners.ChunkListener;
import me.vovari2.distanceoptimization.listeners.PlayerListener;
import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ListenerManager {
    private static HashMap<String, List<Listener>> worldListeners;
    public static void initialize(DistanceOptimization instance){
        worldListeners = new HashMap<>();
        for (World world : Config.WORLDS){
            List<Listener> listeners = new LinkedList<>();
            listeners.add(new ChunkListener(instance, world));
            listeners.add(new PlayerListener(instance, world));
            worldListeners.put(world.getName(), listeners);
        }
    }
    public static void launch(DistanceOptimization instance){
        for (List<Listener> listeners : worldListeners.values()){
            for (Listener listener : listeners)
                instance.getServer().getPluginManager().registerEvents(listener, instance);
        }
    }
    public static void stop(DistanceOptimization instance){
        HandlerList.unregisterAll(instance);
    }
}
