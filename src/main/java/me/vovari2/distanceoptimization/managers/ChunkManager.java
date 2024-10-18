package me.vovari2.distanceoptimization.managers;

import me.vovari2.distanceoptimization.Config;
import me.vovari2.distanceoptimization.DistanceOptimization;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ChunkManager {
    public static void initialize(DistanceOptimization instance){
        playerScores = new HashMap<>();
        new BukkitRunnable() {
            @Override
            public void run() {
                for (PlayerScore playerScore : playerScores.values())
                    playerScore.update();
                playerScores.clear();
            }
        }.runTaskTimer(instance, 0, 20);
    }

    private static HashMap<Player, PlayerScore> playerScores;
    public static void add(Player player, int value){
        if (playerScores.containsKey(player))
            playerScores.get(player).add(value);
        else playerScores.put(player, new PlayerScore(value));
    }
    public static boolean get(Player player){
        return playerScores.containsKey(player) && playerScores.get(player).isEnableOptimization;
    }

    public static class PlayerScore{
        private boolean isEnableOptimization;
        private int score;
        public PlayerScore(int value){
            isEnableOptimization = false;
            this.score = value;
        }
        public void add(int value){
            score += value;
        }
        public void update(){
            if (!isEnableOptimization && Config.SCORE_CHUNKS_ENABLE_LIMIT < score){
                isEnableOptimization = true; return; }

            if (isEnableOptimization && Config.SCORE_CHUNKS_DISABLE_LIMIT > score){
                isEnableOptimization = false; return; }
        }
    }
}
