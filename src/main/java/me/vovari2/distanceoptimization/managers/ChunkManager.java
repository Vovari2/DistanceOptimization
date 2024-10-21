package me.vovari2.distanceoptimization.managers;

import me.vovari2.distanceoptimization.Config;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ChunkManager {
    public static void initialize(){
        playerScores = new HashMap<>();
    }
    public static void shutdown(){
        playerScores.clear();
    }

    private static HashMap<Player, PlayerScore> playerScores;
    public static void add(Player player, int value){
        if (playerScores.containsKey(player))
            playerScores.get(player).add(value);
        else playerScores.put(player, new PlayerScore(value));
    }
    public static void update(){
        for (PlayerScore playerScore : playerScores.values())
            playerScore.update();
    }
    public static boolean getScore(Player player){
        return playerScores.containsKey(player) && playerScores.get(player).isEnableOptimization;
    }

    private static final double AMOUNT_VALUES_FOR_AVERAGE = 10; // Number of last point values to calculate the average
    private static class PlayerScore{
        private boolean isEnableOptimization;
        private final List<ExtendedInteger> scores;
        public PlayerScore(int value){
            isEnableOptimization = false;
            scores = new LinkedList<>();
            scores.addLast(new ExtendedInteger(value));
        }
        public void add(int value){
            scores.getLast().add(value);
        }
        private void next(){
            scores.addLast(new ExtendedInteger(0));
            if (scores.size() > AMOUNT_VALUES_FOR_AVERAGE)
                scores.removeFirst();
        }
        private double get(){
            int sum = 0;
            for (ExtendedInteger value : scores)
                sum += value.value;
            return sum / AMOUNT_VALUES_FOR_AVERAGE;
        }
        public void update(){
            if (!isEnableOptimization && Config.CHUNKS_LIMIT_ENABLE < get()){
                isEnableOptimization = true; return; }

            if (isEnableOptimization && Config.CHUNKS_LIMIT_DISABLE > get()){
                isEnableOptimization = false; return; }

            next();
        }
    }
    private static class ExtendedInteger{
        private Integer value;
        public ExtendedInteger(int value){
            this.value = value;
        }
        public void add(int value){
            this.value += value;
        }
    }
}
