package me.vovari2.distanceoptimization.utils;

import me.vovari2.distanceoptimization.DistanceOptimization;
import me.vovari2.distanceoptimization.Text;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileUtils {
    public static void createFileInDataFolder(String fileName){
        File dataFolder = createDataFolder();

        File file = new File(dataFolder, fileName);
        if (!file.exists()){
            DistanceOptimization.getInstance().saveResource(fileName, false);
            Text.sendMessageToConsole("<green>File \"" + fileName + "\" was created!");
        }
    }
    public static void createFileInFolder(String fileName, String folderName){
        createFileInFolder(fileName, folderName, false);
    }
    public static void createFileInFolder(String fileName, String folderName, boolean isReplace){
        File folder = createFolder(folderName);

        File file = new File(folder, fileName);
        if (!file.exists()){
            DistanceOptimization.getInstance().saveResource(fileName, isReplace);
            Text.sendMessageToConsole("<green>File \"" + fileName + "\" was created!");
        }
    }

    public static File createFolder(String folderName){
        File dataFolder = createDataFolder(),
                folder = new File(dataFolder, folderName);
        if (folder.mkdir())
            Text.sendMessageToConsole("<green>Folder \"" + folderName + "\" in \"" + Text.PLUGIN_NAME + "\" was created!");
        return folder;
    }
    private static File createDataFolder(){
        File dataFolder = DistanceOptimization.getInstance().getDataFolder();
        if (dataFolder.mkdir())
            Text.sendMessageToConsole("<green>Folder \"" + Text.PLUGIN_NAME + "\" in \"plugins\" was created!");

        return dataFolder;
    }

    public static FileConfiguration getYamlConfiguration(String fileName){
        return YamlConfiguration.loadConfiguration(new File(DistanceOptimization.getInstance().getDataFolder(), fileName));
    }
}
