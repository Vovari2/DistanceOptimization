package me.vovari2.distanceoptimization;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.ConsoleCommandSender;

import java.util.HashMap;

public class Text {
    public static final String PLUGIN_NAME = DistanceOptimization.getInstance().getName();
    public static final String VERSION = DistanceOptimization.getInstance().getPluginMeta().getVersion();
    private static final Component MESSAGE_BEGIN = MiniMessage.miniMessage().deserialize("<gray>[" + PLUGIN_NAME + "]<reset> ");


    private static ConsoleCommandSender sender;
    private static Text object;
    protected static void initialize(ConsoleCommandSender newSender){
        sender = newSender;
        declare();
    }
    private static void declare(){
        Text texts = new Text();
        texts.put("command.help", toComponent("""
                <gradient:dark_gray:gray:white><strikethrough>      </strikethrough> %s Helper v%s <strikethrough>      </strikethrough></gradient>
                
                <gradient:dark_gray:gray:white> - <hover:show_text:"<gray>View information about commands">/do help</hover></gradient>
                <gradient:dark_gray:gray:white> - <hover:show_text:"<gray>Reload plugin">/do reload</hover></gradient>
                <gradient:dark_gray:gray:white> - <hover:show_text:"<gray>Shows player information per second">/do profiler</hover></gradient>
                   <gradient:dark_gray:gray:white><hover:show_text:"<gray>Launch profiler for a specific player">/do profiler start [player_name]</hover></gradient>
                   <gradient:dark_gray:gray:white><hover:show_text:"<gray>Stopping the profiler">/do profiler stop</hover></gradient>
                   <gradient:dark_gray:gray:white><hover:show_text:"<gray>Confirmation of profiler cancellation">/do profiler confirm</hover></gradient>
                """.formatted(PLUGIN_NAME, VERSION)));
        texts.put("command.reload", toComponent("<gradient:#54B435:#82CD47>Plugin reloaded!</gradient>"));
        texts.put("command.profiler_start", toComponent("<green>Profiler for <yellow><%player%></yellow> started!"));
        texts.put("command.profiler_stop", toComponent("<yellow>Confirm <click:suggest_command:/do profiler confirm><gray>/do profiler confirm</gray></click> to stop profiler"));
        texts.put("command.confirm", toComponent("<green>Profiler stopped!"));
        texts.put("warning.command_incorrectly", toComponent("<red>Command is incorrectly! (/cho help)"));
        texts.put("warning.dont_have_permission", toComponent("<red>You don't have permissions!"));
        texts.put("warning.you_not_player", toComponent("<red>You are not a player!"));
        texts.put("warning.player_name_incorrectly", toComponent("<red>Player's name is incorrectly!"));
        texts.put("warning.profiler_already_started", toComponent("<red>Profiler has already started!"));
        texts.put("warning.profiler_not_started_yet", toComponent("<red>Profiler has not started yet!"));
        texts.put("warning.nothing_to_confirm", toComponent("<red>Nothing to confirm!"));
        Text.object = texts;
    }
    private static Component toComponent(String text){
        return MiniMessage.miniMessage().deserialize(text);
    }

    protected static void clear(){
        object = null;
    }
    public static Component get(String key){
        return object.texts.get(key);
    }


    private final HashMap<String, Component> texts;
    private Text(){
        texts = new HashMap<>();
    }
    private void put(String key, Component text){
        texts.put(key, text);
    }



    public static void sendMessageToConsole(String message){
        sendMessageToConsole(message, true);
    }
    public static void sendMessageToConsole(String message, boolean hasMessageBegin){
        Component targetMessage = MiniMessage.miniMessage().deserialize(message);
        sender.sendMessage(hasMessageBegin ? MESSAGE_BEGIN.append(targetMessage) : targetMessage);
    }
    public static void sendMessageToConsole(Component message){
        sendMessageToConsole(message, true);
    }
    public static void sendMessageToConsole(Component message, boolean hasMessageBegin){
        sender.sendMessage(hasMessageBegin ? MESSAGE_BEGIN.append(message) : message);
    }
}
