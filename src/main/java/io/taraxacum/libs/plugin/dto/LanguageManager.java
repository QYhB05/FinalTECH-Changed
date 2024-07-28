package io.taraxacum.libs.plugin.dto;

import io.taraxacum.common.util.StringUtil;
import io.taraxacum.libs.plugin.interfaces.StringReplacer;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
 
public class LanguageManager extends ConfigFileManager implements StringReplacer {
    private final List<Function<String, String>> functionSet = new ArrayList<>();

    private LanguageManager(@Nonnull Plugin plugin, @Nonnull String configFileName) {
        super(plugin, "language", configFileName);
    }

    @Nonnull
    public static LanguageManager getOrNewInstance(@Nonnull Plugin plugin, @Nonnull String fileName) {
        if (INSTANCE_MAP.containsKey(plugin)) {
            Map<String, ConfigFileManager> configRegistryMap = INSTANCE_MAP.get(plugin);
            if (configRegistryMap.containsKey(fileName)) {
                return (LanguageManager) configRegistryMap.get(fileName);
            } else {
                synchronized (configRegistryMap) {
                    if (configRegistryMap.containsKey(fileName)) {
                        return (LanguageManager) configRegistryMap.get(fileName);
                    }
                    final LanguageManager configManager = new LanguageManager(plugin, fileName);
                    configRegistryMap.put(fileName, configManager);
                    return configManager;
                }
            }
        } else {
            synchronized (ConfigFileManager.class) {
                if (INSTANCE_MAP.containsKey(plugin)) {
                    return LanguageManager.getOrNewInstance(plugin, fileName);
                }
                LanguageManager configManager = new LanguageManager(plugin, fileName);
                final Map<String, ConfigFileManager> fileMap = new HashMap<>();
                fileMap.put(fileName, configManager);
                INSTANCE_MAP.put(plugin, fileMap);
                return configManager;
            }
        }
    }

    @SafeVarargs
    @Override
    public final void addFunction(@Nonnull Function<String, String>... function) {
        this.functionSet.addAll(List.of(function));
    }

    @Nonnull
    @Override
    public List<Function<String, String>> getFunction() {
        return this.functionSet;
    }

    @Nonnull
    @Override
    public String getString(@Nonnull String... paths) {
        return this.getResult(super.getString(paths));
    }

    @Nonnull
    @Override
    public List<String> getStringList(@Nonnull String... paths) {
        return this.getResult(super.getStringList(paths));
    }

    @Nonnull
    public String[] getStringArray(@Nonnull String... paths) {
        return this.getResult(super.getStringList(paths)).toArray(new String[0]);
    }

    @Nonnull
    public String replaceString(@Nonnull String source, @Nonnull String... targets) {
        String[] split = StringUtil.split(source, "{", "}");
        if (split.length == 3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(split[0]);
            if (StringUtil.onlyContainsNumber(split[1])) {
                int index = Integer.parseInt(split[1]) - 1;
                if (targets.length > index && index >= 0) {
                    stringBuilder.append(targets[index]);
                } else {
                    stringBuilder.append("{").append(split[1]).append("}");
                }
            } else {
                stringBuilder.append("{").append(split[1]).append("}");
            }
            return stringBuilder.append(this.replaceString(split[2], targets)).toString();
        } else {
            return source;
        }
    }

    @Nonnull
    public List<String> replaceStringList(@Nonnull List<String> source, @Nonnull String... targets) {
        List<String> result = new ArrayList<>(source.size());
        for (String string : source) {
            result.add(this.replaceString(string, targets));
        }
        return result;
    }

    @Nonnull
    public String[] replaceStringArray(@Nonnull String[] source, @Nonnull String... targets) {
        String[] result = new String[source.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = this.replaceString(source[i], targets);
        }
        return result;
    }
}
