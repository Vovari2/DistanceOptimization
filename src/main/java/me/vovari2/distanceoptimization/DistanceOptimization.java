package me.vovari2.distanceoptimization;

import me.vovari2.distanceoptimization.exceptions.ComponentException;
import me.vovari2.distanceoptimization.managers.*;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class DistanceOptimization extends JavaPlugin {
    private static DistanceOptimization instance;
    private boolean isEnable;

    public boolean IS_BAD_MSPT;


    @Override
    public void onEnable() {
        instance = this;
        isEnable = true;

        IS_BAD_MSPT = false;

        try{
            Text.initialize(getServer().getConsoleSender());
            Config.initialize();
            isEnable = Config.ENABLED;
        }
        catch (ComponentException e) {
            Text.sendMessageToConsole(e.getComponentMessage().color(TextColor.fromHexString("FF5555")));
            isEnable = false;
        }

        if (isEnable){
            WorldManager.initialize();

            ListenerManager.initialize(this);
            ListenerManager.launch(this);

            CommandManager.initialize(this);

            ChunkManager.initialize();
            TickManager.initialize(this);
        }

        Text.sendMessageToConsole(isEnable ? "<green>Plugin enabled!" : "<yellow>Plugin not enabled!");
    }

    @Override
    public void onDisable() {
        Text.clear();
        ListenerManager.shutdown(this);
        ChunkManager.shutdown();
        TickManager.shutdown();

        Text.sendMessageToConsole("<red>Plugin disabled!");
    }

    public static DistanceOptimization getInstance() {
        return instance;
    }
    public static boolean isEnable(){
        return instance.isEnable;
    }

    public static World getWorld(String world){
        return instance.getServer().getWorld(world);
    }
    public static Player getPlayer(String playerName){
        for (Player targetPlayer : instance.getServer().getOnlinePlayers())
            if (targetPlayer.getName().equals(playerName))
                return targetPlayer;
        return null;
    }
}
