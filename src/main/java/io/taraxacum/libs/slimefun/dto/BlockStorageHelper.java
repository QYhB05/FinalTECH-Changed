package io.taraxacum.libs.slimefun.dto;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Just for easily change value of the given key in specific condition
 *
 * @author Final_ROOT
 * @see BlockStorage
 * @since 2.0
 */
public abstract class BlockStorageHelper {
    public static final String ID_CARGO = "cargo";
    protected static final Map<String, Map<String, BlockStorageHelper>> BLOCK_STORAGE_HELPER_FACTORY = new HashMap<>();
    private static final String ERROR = "0";
    /**
     * The id of a {@link SlimefunItem},
     * Or a public id
     */
    @Nonnull
    private final String id;
    @Nonnull
    private final List<String> valueList;

    protected BlockStorageHelper(@Nonnull String id, @Nonnull List<String> valueList) {
        this.id = id;
        this.valueList = valueList;
    }

    protected BlockStorageHelper(@Nonnull SlimefunItem slimefunItem, @Nonnull List<String> valueList) {
        this(slimefunItem.getId(), valueList);
    }

    @Nonnull
    public static BlockStorageHelper newInstanceOrGet(@Nonnull String id, @Nonnull String key, @Nonnull List<String> valueList) {
        if (BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if (stringBlockStorageHelperMap.containsKey(key)) {
                return stringBlockStorageHelperMap.get(key);
            } else {
                synchronized (stringBlockStorageHelperMap) {
                    if (stringBlockStorageHelperMap.containsKey(key)) {
                        return stringBlockStorageHelperMap.get(key);
                    }
                    BlockStorageHelper blockStorageHelper = new BlockStorageHelper(id, valueList) {
                        @Nonnull
                        @Override
                        public String getKey() {
                            return key;
                        }
                    };
                    stringBlockStorageHelperMap.put(key, blockStorageHelper);
                    return blockStorageHelper;
                }
            }
        } else {
            synchronized (BLOCK_STORAGE_HELPER_FACTORY) {
                if (BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
                    return BlockStorageHelper.newInstanceOrGet(id, key, valueList);
                }
                Map<String, BlockStorageHelper> stringBlockStorageHelperMap = new HashMap<>();
                BLOCK_STORAGE_HELPER_FACTORY.put(id, stringBlockStorageHelperMap);
                BlockStorageHelper blockStorageHelper = new BlockStorageHelper(id, valueList) {
                    @Nonnull
                    @Override
                    public String getKey() {
                        return key;
                    }
                };
                stringBlockStorageHelperMap.put(key, blockStorageHelper);
                return blockStorageHelper;
            }
        }
    }

    @Nonnull
    public static BlockStorageHelper newInstanceOrGet(@Nonnull String id, @Nonnull String key, @Nonnull String... values) {
        return BlockStorageHelper.newInstanceOrGet(id, key, List.of(values));
    }

    @Nonnull
    public static BlockStorageHelper newInstanceOrGet(@Nonnull SlimefunItem slimefunItem, @Nonnull String key, @Nonnull List<String> valueList) {
        return BlockStorageHelper.newInstanceOrGet(slimefunItem.getId(), key, valueList);
    }

    @Nonnull
    public static BlockStorageHelper newInstanceOrGet(@Nonnull SlimefunItem slimefunItem, @Nonnull String key, @Nonnull String... values) {
        return BlockStorageHelper.newInstanceOrGet(slimefunItem.getId(), key, values);
    }

    @Nonnull
    public String getOrDefaultValue(@Nonnull Location location) {
        String value = BlockStorage.getLocationInfo(location, this.getKey());
        return value == null ? this.defaultValue() : value;
    }

    @Nonnull
    public String getOrDefaultValue(@Nonnull Config config) {
        return config.contains(this.getKey()) ? config.getString(this.getKey()) : this.defaultValue();
    }

    public void setOrClearValue(@Nonnull Location location, @Nullable String value) {
        BlockStorage.addBlockInfo(location, this.getKey(), value);
    }

    public void setOrClearValue(@Nonnull Config config, @Nullable String value) {
        config.setValue(this.getKey(), value);
    }

    public int valueSize() {
        return this.valueList.size();
    }

    @Nonnull
    public String defaultValue() {
        return this.valueList.isEmpty() ? ERROR : this.valueList.get(0);
    }

    @Nonnull
    public String offsetOrDefaultValue(@Nullable String value, int offset) {
        return this.valueList.contains(value) ? this.valueList.get(((this.valueList.indexOf(value) + offset) % this.valueList.size() + this.valueList.size()) % this.valueList.size()) : this.defaultValue();
    }

    @Nonnull
    public String nextOrDefaultValue(@Nullable String value) {
        return this.offsetOrDefaultValue(value, 1);
    }

    @Nonnull
    public String previousOrDefaultValue(@Nullable String value) {
        return this.offsetOrDefaultValue(value, -1);
    }

    public boolean validValue(@Nullable String value) {
        return valueList.contains(value);
    }

    public boolean checkOrSetBlockStorage(@Nonnull Location location) {
        if (BlockStorage.getLocationInfo(location, this.getKey()) == null) {
            BlockStorage.addBlockInfo(location, this.getKey(), this.defaultValue());
            return false;
        }
        return true;
    }

    @Nonnull
    public abstract String getKey();
}
