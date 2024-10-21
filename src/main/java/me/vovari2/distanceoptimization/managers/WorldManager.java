package me.vovari2.distanceoptimization.managers;

import me.vovari2.distanceoptimization.Config;
import org.bukkit.World;

public class WorldManager {
    public static void initialize(){
        if (Config.USE_DEFAULT_DISTANCE)
            return;

        for (World world : Config.WORLDS){
            world.setViewDistance(Config.NORMAL_VIEW_DISTANCE);
            world.setSendViewDistance(Config.NORMAL_SEND_VIEW_DISTANCE);
            world.setSimulationDistance(Config.NORMAL_SIMULATION_DISTANCE);
        }
    }
}
