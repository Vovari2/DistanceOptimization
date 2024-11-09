package me.vovari2.distanceoptimization.managers;

import me.vovari2.distanceoptimization.Config;
import me.vovari2.distanceoptimization.DistanceOptimization;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TickManager {
    private static BukkitTask task;
    public static void initialize(DistanceOptimization instance){
        task = new BukkitRunnable(){
            @Override
            public void run() {
                double mspt = instance.getServer().getAverageTickTime();
                if (!instance.IS_BAD_MSPT && Config.MSPT_LIMIT_ENABLE < mspt){
                    instance.IS_BAD_MSPT = true; return; }

                if (instance.IS_BAD_MSPT && Config.MSPT_LIMIT_DISABLE > mspt){
                    instance.IS_BAD_MSPT = false; return; }

                ChunksScoreManager.update();
            }
        }.runTaskTimer(instance, 0, 20);
    }
    public static void shutdown(){
        task.cancel();
    }
}
