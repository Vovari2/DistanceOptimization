package me.vovari2.distanceoptimization.managers;

import java.util.ArrayDeque;
import java.util.Deque;

public class TickManager {
    private static Deque<Long> mspts;
    private static Deque<Long> tpss;
    private static long lastNanosecond;

    public static void initialize(){
        mspts = new ArrayDeque<>();
        if ()

    }

    public static void startTick(){
        lastNanosecond = System.nanoTime();
    }
    public static void endTick(){
        mspts.addLast(System.nanoTime() - lastNanosecond);
        if (mspts.size() >= 11)
            mspts.removeFirst();
    }
    public static double getTPS(){
        long sum = 0;
        for (long ns : mspts)
            sum += ns;
        return sum / 20000000D;
    }
    public static double getMSPT(){
        long sum = 0;
        for (long ns : tpss)
            sum += ns;
        return sum / 10000000D;
    }


}
