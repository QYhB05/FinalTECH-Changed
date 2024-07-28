package io.taraxacum.libs.slimefun.dto;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BlockStorageLoreHelper extends BlockStorageHelper {
    private static final List<String> ERROR_LORE = List.of("§cERROR");
    private final Map<String, List<String>> valueLoreMap;
    private int loreOffset = -1;

    protected BlockStorageLoreHelper(@Nonnull String id, @Nonnull Map<String, List<String>> valueLoreMap) {
        super(id, BlockStorageLoreHelper.init(valueLoreMap));
        this.valueLoreMap = valueLoreMap;
    }

    protected BlockStorageLoreHelper(@Nonnull SlimefunItem slimefunItem, @Nonnull Map<String, List<String>> valueLoreMap) {
        this(slimefunItem.getId(), valueLoreMap);
    }

    protected BlockStorageLoreHelper(@Nonnull String id, int loreOffset, @Nonnull Map<String, List<String>> valueLoreMap) {
        this(id, valueLoreMap);
        this.loreOffset = loreOffset;
    }

    protected BlockStorageLoreHelper(@Nonnull SlimefunItem slimefunItem, int loreOffset, @Nonnull Map<String, List<String>> valueLoreMap) {
        this(slimefunItem, valueLoreMap);
        this.loreOffset = loreOffset;
    }

    @Nonnull
    public static BlockStorageLoreHelper newInstanceOrGet(@Nonnull String id, @Nonnull String key, int loreOffset, @Nonnull Map<String, List<String>> valueLoreMap) {
        if (BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if (stringBlockStorageHelperMap.containsKey(key)) {
                BlockStorageHelper blockStorageHelper = stringBlockStorageHelperMap.get(key);
                return (BlockStorageLoreHelper) blockStorageHelper;
            } else {
                synchronized (stringBlockStorageHelperMap) {
                    if (stringBlockStorageHelperMap.containsKey(key)) {
                        return (BlockStorageLoreHelper) stringBlockStorageHelperMap.get(key);
                    }
                    BlockStorageLoreHelper blockStorageLoreHelper = new BlockStorageLoreHelper(id, loreOffset, valueLoreMap) {
                        @Nonnull
                        @Override
                        public String getKey() {
                            return key;
                        }
                    };
                    stringBlockStorageHelperMap.put(key, blockStorageLoreHelper);
                    return blockStorageLoreHelper;
                }
            }
        } else {
            synchronized (BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY) {
                if (BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
                    return BlockStorageLoreHelper.newInstanceOrGet(id, key, loreOffset, valueLoreMap);
                }
                Map<String, BlockStorageHelper> stringBlockStorageHelperMap = new HashMap<>();
                BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.put(id, stringBlockStorageHelperMap);
                BlockStorageLoreHelper blockStorageLoreHelper = new BlockStorageLoreHelper(id, loreOffset, valueLoreMap) {
                    @Nonnull
                    @Override
                    public String getKey() {
                        return key;
                    }
                };
                stringBlockStorageHelperMap.put(key, blockStorageLoreHelper);
                return blockStorageLoreHelper;
            }
        }
    }

    @Nonnull
    public static BlockStorageLoreHelper newInstanceOrGet(@Nonnull SlimefunItem slimefunItem, @Nonnull String key, int loreOffset, @Nonnull Map<String, List<String>> valueLoreMap) {
        return BlockStorageLoreHelper.newInstanceOrGet(slimefunItem.getId(), key, loreOffset, valueLoreMap);
    }

    @Nonnull
    public static BlockStorageLoreHelper newInstanceOrGet(@Nonnull String id, @Nonnull String key, @Nonnull Map<String, List<String>> valueLoreMap) {
        return BlockStorageLoreHelper.newInstanceOrGet(id, key, -1, valueLoreMap);
    }

    @Nonnull
    public static BlockStorageLoreHelper newInstanceOrGet(@Nonnull SlimefunItem slimefunItem, @Nonnull String key, @Nonnull Map<String, List<String>> valueLoreMap) {
        return BlockStorageLoreHelper.newInstanceOrGet(slimefunItem.getId(), key, -1, valueLoreMap);
    }

    private static List<String> init(@Nonnull Map<String, List<String>> valueLoreMap) {
        List<String> valueList = new ArrayList<>(valueLoreMap.size());
        for (Map.Entry<String, List<String>> entry : valueLoreMap.entrySet()) {
            valueList.add(entry.getKey());
        }
        return valueList;
    }

    /**
     * update the lore of the icon{@link ItemStack} by the given value
     */
    public boolean setIcon(@Nonnull ItemStack iconItem, @Nullable String value) {
        if (valueLoreMap.containsKey(value)) {
            if (this.loreOffset < 0) {
                ItemStackUtil.setLore(iconItem, valueLoreMap.get(value));
            } else {
                ItemStackUtil.replaceLore(iconItem, this.loreOffset, valueLoreMap.get(value));
            }
            return true;
        } else {
            if (this.loreOffset < 0) {
                ItemStackUtil.setLore(iconItem, ERROR_LORE);
            } else {
                ItemStackUtil.replaceLore(iconItem, this.loreOffset, ERROR_LORE);
            }
            return false;
        }
    }

    /**
     * Override this method to do some function
     */
    public boolean setIcon(@Nonnull ItemStack iconItem, @Nullable String value, @Nonnull SlimefunItem slimefunItem) {
        return this.setIcon(iconItem, value);
    }

    /**
     * check {@link BlockStorage} data
     * update the icon{@link ItemStack} in the given slot place
     */
    public boolean checkAndUpdateIcon(@Nonnull Inventory inventory, @Nonnull Location location, int slot) {
        String value = BlockStorage.getLocationInfo(location, this.getKey());
        if (!this.validValue(value)) {
            value = this.defaultValue();
        }
        ItemStack item = inventory.getItem(slot);
        this.setIcon(item, value);
        return true;
    }

    @Nonnull
    public ChestMenu.MenuClickHandler getHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageLoreHelper.this.getOrDefaultValue(location);
            if (clickAction.isRightClicked()) {
                value = BlockStorageLoreHelper.this.previousOrDefaultValue(value);
            } else {
                value = BlockStorageLoreHelper.this.nextOrDefaultValue(value);
            }
            ItemStack item = inventory.getItem(slot);
            BlockStorageLoreHelper.this.setIcon(item, value);
            BlockStorageLoreHelper.this.setOrClearValue(location, value);
            return false;
        };
    }

    @Nonnull
    public ChestMenu.MenuClickHandler getUpdateHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageLoreHelper.this.getOrDefaultValue(location);
            ItemStack item = inventory.getItem(slot);
            if (!BlockStorageLoreHelper.this.validValue(value)) {
                value = BlockStorageLoreHelper.this.defaultValue();
            }
            BlockStorageLoreHelper.this.setIcon(item, value);
            BlockStorageLoreHelper.this.setOrClearValue(location, value);
            return false;
        };
    }

    @Nonnull
    public ChestMenu.MenuClickHandler getNextHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageLoreHelper.this.getOrDefaultValue(location);
            value = BlockStorageLoreHelper.this.clickNextValue(value, clickAction);
            ItemStack item = inventory.getItem(slot);
            BlockStorageLoreHelper.this.setIcon(item, value);
            BlockStorageLoreHelper.this.setOrClearValue(location, value);
            return false;
        };
    }

    @Nonnull
    public ChestMenu.MenuClickHandler getPreviousHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageLoreHelper.this.getOrDefaultValue(location);
            value = BlockStorageLoreHelper.this.clickPreviousValue(value, clickAction);
            ItemStack item = inventory.getItem(slot);
            BlockStorageLoreHelper.this.setIcon(item, value);
            BlockStorageLoreHelper.this.setOrClearValue(location, value);
            return false;
        };
    }

    @Nonnull
    public String clickNextValue(@Nullable String value, @Nonnull ClickAction clickAction) {
        return this.nextOrDefaultValue(value);
    }

    @Nonnull
    public String clickPreviousValue(@Nullable String value, @Nonnull ClickAction clickAction) {
        return this.previousOrDefaultValue(value);
    }
}
