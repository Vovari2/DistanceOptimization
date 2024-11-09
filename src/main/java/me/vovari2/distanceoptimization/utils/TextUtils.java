package me.vovari2.distanceoptimization.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class TextUtils {
    public static Component toComponent(String text){
        return MiniMessage.miniMessage().deserialize(text);
    }
    public static String toStringWithColors(boolean bool){
        return bool ? "<green>true</green>" : "<red>false</red>";
    }
}
