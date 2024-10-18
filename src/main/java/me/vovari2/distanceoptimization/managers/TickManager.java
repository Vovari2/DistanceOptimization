package me.vovari2.distanceoptimization.managers;

import me.vovari2.distanceoptimization.Config;
import me.vovari2.distanceoptimization.DistanceOptimization;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;
import java.util.Deque;

public class TickManager {
    public static void initialize(DistanceOptimization instance){
        new BukkitRunnable(){
            @Override
            public void run() {
                if (!instance.IS_BAD_MSPT && Config.OPTIMIZATION_ENABLE_LIMIT < instance.getServer().getAverageTickTime()){
                    instance.IS_BAD_MSPT = true; return; }

                if (instance.IS_BAD_MSPT && Config.OPTIMIZATION_DISABLE_LIMIT > instance.getServer().getAverageTickTime()){
                    instance.IS_BAD_MSPT = false; return; }
            }
        }.runTaskTimer(instance, 0, 20);
    }
}
