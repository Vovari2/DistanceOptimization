package me.vovari2.distanceoptimization;

import me.vovari2.distanceoptimization.exceptions.ComponentException;
import me.vovari2.distanceoptimization.utils.FileUtils;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.LinkedList;
import java.util.List;

public class Config {
    public static boolean ENABLED;
    public static int MSPT_LIMIT_ENABLE;
    public static int MSPT_LIMIT_DISABLE;

    public static int CHUNKS_LIMIT_ENABLE;
    public static int CHUNKS_LIMIT_DISABLE;

    public static List<World> WORLDS;

    public static boolean USE_DEFAULT_DISTANCE;
    public static int NORMAL_VIEW_DISTANCE;
    public static int NORMAL_SEND_VIEW_DISTANCE;
    public static int NORMAL_SIMULATION_DISTANCE;

    public static int OPTIMIZE_VIEW_DISTANCE;
    public static int OPTIMIZE_SEND_VIEW_DISTANCE;
    public static int OPTIMIZE_SIMULATION_DISTANCE;

    public static void initialize() throws ComponentException {
        new Config().initializeInside();
    }

    private FileConfiguration config;
    private void initializeInside() throws ComponentException {
        FileUtils.createFileInDataFolder("config.yml");
        config = FileUtils.getYamlConfiguration("config.yml");

        ENABLED = getBoolean("enabled");
        MSPT_LIMIT_ENABLE = getPositiveInt("mspt_limit_enable");
        MSPT_LIMIT_DISABLE = getPositiveInt("mspt_limit_disable");

        CHUNKS_LIMIT_ENABLE = getPositiveInt("chunks_limit_enable");
        CHUNKS_LIMIT_DISABLE = getPositiveInt("chunks_limit_disable");

        WORLDS =  getWorldList("worlds");

        USE_DEFAULT_DISTANCE = getBoolean("use_default_distance");
        NORMAL_VIEW_DISTANCE = getPositiveInt("normal_view_distance");
        NORMAL_SEND_VIEW_DISTANCE = getPositiveInt("normal_send_view_distance");
        NORMAL_SIMULATION_DISTANCE = getPositiveInt("normal_simulation_distance");

        OPTIMIZE_VIEW_DISTANCE = getPositiveInt("optimize_view_distance");
        OPTIMIZE_SEND_VIEW_DISTANCE = getPositiveInt("optimize_send_view_distance");
        OPTIMIZE_SIMULATION_DISTANCE = getPositiveInt("optimize_simulation_distance");
    }

    private List<World> getWorldList(String path) throws ComponentException{
        List<String> list = config.getStringList(path);
        int i = 1;
        List<World> worlds = new LinkedList<>();
        for (String worldStr : list){
            World world = DistanceOptimization.getWorld(worldStr);
            if (world == null)
                throw new ComponentException("Value " + i + " in list " + path + " is invalid!");
            worlds.add(world);
            i++;
        }
        return worlds;
    }
    private int getPositiveInt(String path) throws ComponentException{
        int value = getInt(path);
        if (value <= 0)
            throw new ComponentException("Value " + path + " must be greater than 0!");
        return value;
    }
    private int getInt(String path) throws ComponentException{
        Object object = config.get(path);
        if (!(object instanceof Integer))
            throw new ComponentException("Value " + path + " is not an integer!");
        return (int) object;
    }
    private boolean getBoolean(String path) throws ComponentException{
        Object object = config.get(path);
        if (!(object instanceof Boolean))
            throw new ComponentException("Value " + path + " is not a boolean!");
        return (boolean) object;
    }

}
