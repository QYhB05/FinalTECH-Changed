package io.taraxacum.libs.plugin.dto;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * @author Final_ROOT
 * @see LanguageManager
 * @since 2.0
 */
public class ConfigFileManager {
    protected static final Map<Plugin, Map<String, ConfigFileManager>> INSTANCE_MAP = new HashMap<>();
    @Nonnull
    protected final FileConfiguration configFile;
    @Nullable
    protected final File file;
    private Plugin plugin;

    protected ConfigFileManager(@Nonnull Plugin plugin, @Nonnull String configFileName) {
        this.plugin = plugin;
        this.file = new File("plugins/" + plugin.getName().replace(" ", "_"), configFileName + ".yml");
        if (!file.exists()) {
            try {
                boolean mkdirs = file.getParentFile().mkdirs();
                Files.copy(Objects.requireNonNull(this.getClass().getResourceAsStream("/" + configFileName + ".yml")), this.file.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.configFile = YamlConfiguration.loadConfiguration(this.file);
    }

    protected ConfigFileManager(@Nonnull Plugin plugin, @Nonnull String path, @Nonnull String configFileName) {
        this.plugin = plugin;
        this.file = new File("plugins/" + plugin.getName().replace(" ", "_") + "/" + path, configFileName + ".yml");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                Files.copy(Objects.requireNonNull(this.getClass().getResourceAsStream("/" + path + "/" + configFileName + ".yml")), this.file.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

        }
        this.configFile = YamlConfiguration.loadConfiguration(this.file);
    }

    @Nonnull
    protected static String calPath(@Nonnull String... paths) {
        if (paths.length == 0) {
            // Please make sure that this will not happen.
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String path : paths) {
            stringBuilder.append(path);
            stringBuilder.append(".");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    @Nonnull
    public static ConfigFileManager getOrNewInstance(@Nonnull Plugin plugin, @Nonnull String fileName) {
        if (INSTANCE_MAP.containsKey(plugin)) {
            Map<String, ConfigFileManager> configRegistryMap = INSTANCE_MAP.get(plugin);
            if (configRegistryMap.containsKey(fileName)) {
                return configRegistryMap.get(fileName);
            } else {
                synchronized (configRegistryMap) {
                    if (configRegistryMap.containsKey(fileName)) {
                        return configRegistryMap.get(fileName);
                    }
                    final ConfigFileManager configManager = new ConfigFileManager(plugin, fileName);
                    configRegistryMap.put(fileName, configManager);
                    return configManager;
                }
            }
        } else {
            synchronized (ConfigFileManager.class) {
                if (INSTANCE_MAP.containsKey(plugin)) {
                    return ConfigFileManager.getOrNewInstance(plugin, fileName);
                }
                ConfigFileManager configManager = new ConfigFileManager(plugin, fileName);
                final Map<String, ConfigFileManager> fileMap = new HashMap<>();
                fileMap.put(fileName, configManager);
                INSTANCE_MAP.put(plugin, fileMap);
                return configManager;
            }
        }
    }

    @Nonnull
    public Plugin getPlugin() {
        return this.plugin;
    }

    @Nonnull
    public Boolean containPath(@Nonnull String... paths) {
        String path = ConfigFileManager.calPath(paths);
        return this.configFile.contains(path);
    }

    @Nonnull
    public <T> Boolean setValue(@Nonnull T value, @Nonnull String... paths) {
        if (this.file == null) {
            return false;
        }
        String path = ConfigFileManager.calPath(paths);
        this.configFile.set(path, value);
        try {
            this.configFile.save(this.file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Nonnull
    public String getString(@Nonnull String... paths) {
        String path = ConfigFileManager.calPath(paths);
        if (this.configFile.contains(path)) {
            String result = this.configFile.getString(path);
            return result == null ? path : result;
        } else {
            this.setValue(path, paths);
            return path;
        }
    }

    @Nonnull
    public List<String> getStringList(@Nonnull String... paths) {
        String path = ConfigFileManager.calPath(paths);
        if (this.configFile.contains(path)) {
            if (this.configFile.isList(path)) {
                return this.configFile.getStringList(path);
            } else {
                try {
                    List<String> result = this.configFile.getStringList(path);
                    if (result.isEmpty()) {
                        Object object = this.configFile.get(path);
                        if (object instanceof MemorySection memorySection) {
                            result = new ArrayList<>(memorySection.getKeys(false));
                        }
                    }
                    return result;
                } catch (Exception e) {
                    this.plugin.getLogger().info(e.getMessage());
                    return new ArrayList<>();
                }
            }
        } else {
            this.setValue(new ArrayList<>(), paths);
            return new ArrayList<>();
        }
    }

    @Nonnull
    public <T> T getOrDefault(@Nonnull T defaultValue, @Nonnull String... paths) {
        String path = ConfigFileManager.calPath(paths);
        if (this.configFile.contains(path)) {
            try {
                return (T) this.configFile.get(path, defaultValue);
            } catch (Exception e) {
                e.printStackTrace();
                return defaultValue;
            }
        } else {
            this.setValue(defaultValue, paths);
            return defaultValue;
        }
    }
}
