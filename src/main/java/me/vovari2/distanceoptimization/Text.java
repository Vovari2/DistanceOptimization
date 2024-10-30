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
                <gradient:#27374D:#526D82:#27374D><strikethrough>      </strikethrough> %s Helper v%s <strikethrough>      </strikethrough></gradient>
                
                <gradient:#526D82:#9DB2BF> - <hover:show_text:"<gray>View information about commands">/cho help</hover></gradient>
                <gradient:#526D82:#9DB2BF> - <hover:show_text:"<gray>Reload plugin">/cho reload</hover></gradient>
                """.formatted(PLUGIN_NAME, VERSION)));
        texts.put("command.reload", toComponent("<gradient:#54B435:#82CD47>Plugin reloaded!</gradient>"));
        texts.put("command.profiler_start", toComponent("<green>Profiler for <%player%> started!"));
        texts.put("command.profiler_stop", toComponent("<green>Confirm <click:suggest_command:/do confirm><gray>/do confirm</gray></click> to stop profiler"));
        texts.put("command.confirm", toComponent("<yellow>Profiler stopped!"));
        texts.put("warning.command_incorrectly", toComponent("<red>Command is incorrectly! (/cho help)"));
        texts.put("warning.dont_have_permission", toComponent("<red>You don't have permissions!"));
        texts.put("warning.you_not_player", toComponent("<red>You are not a player!"));
        texts.put("warning.player_name_incorrectly", toComponent("<red>Player's name is incorrectly!"));
        texts.put("warning.nothing_to_confirm", toComponent("<red>Nothing to confirm!"));
        Text.object = texts;
    }
    public static Component toComponent(String text){
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
