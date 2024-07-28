package io.taraxacum.libs.slimefun.dto;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BlockStorageIconHelper extends BlockStorageHelper {
    private static final ItemStack ERROR_ICON = new CustomItemStack(Material.BARRIER, " ");
    private final Map<String, ItemStack> valueIconMap;

    protected BlockStorageIconHelper(@Nonnull String id, @Nonnull Map<String, ItemStack> valueIconMap) {
        super(id, BlockStorageIconHelper.init(valueIconMap));
        this.valueIconMap = valueIconMap;
    }

    protected BlockStorageIconHelper(@Nonnull SlimefunItem slimefunItem, @Nonnull Map<String, ItemStack> valueIconMap) {
        this(slimefunItem.getId(), valueIconMap);
    }

    @Nonnull
    public static BlockStorageIconHelper newInstanceOrGet(@Nonnull String id, @Nonnull String key, @Nonnull Map<String, ItemStack> valueIconMap) {
        if (BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
            Map<String, BlockStorageHelper> stringBlockStorageHelperMap = BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.get(id);
            if (stringBlockStorageHelperMap.containsKey(key)) {
                BlockStorageHelper blockStorageHelper = stringBlockStorageHelperMap.get(key);
                return (BlockStorageIconHelper) blockStorageHelper;
            } else {
                synchronized (stringBlockStorageHelperMap) {
                    if (stringBlockStorageHelperMap.containsKey(key)) {
                        return (BlockStorageIconHelper) stringBlockStorageHelperMap.get(key);
                    }
                    BlockStorageIconHelper blockStorageIconHelper = new BlockStorageIconHelper(id, valueIconMap) {
                        @Nonnull
                        @Override
                        public String getKey() {
                            return key;
                        }
                    };
                    stringBlockStorageHelperMap.put(key, blockStorageIconHelper);
                    return blockStorageIconHelper;
                }
            }
        } else {
            synchronized (BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY) {
                if (BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.containsKey(id)) {
                    return BlockStorageIconHelper.newInstanceOrGet(id, key, valueIconMap);
                }
                Map<String, BlockStorageHelper> stringBlockStorageHelperMap = new HashMap<>();
                BlockStorageHelper.BLOCK_STORAGE_HELPER_FACTORY.put(id, stringBlockStorageHelperMap);
                BlockStorageIconHelper blockStorageIconHelper = new BlockStorageIconHelper(id, valueIconMap) {
                    @Nonnull
                    @Override
                    public String getKey() {
                        return key;
                    }
                };
                stringBlockStorageHelperMap.put(key, blockStorageIconHelper);
                return blockStorageIconHelper;
            }
        }
    }

    @Nonnull
    public static BlockStorageHelper newInstanceOrGet(@Nonnull SlimefunItem slimefunItem, @Nonnull String key, @Nonnull Map<String, ItemStack> valueIconMap) {
        return BlockStorageIconHelper.newInstanceOrGet(slimefunItem.getId(), key, valueIconMap);
    }

    @Nonnull
    private static List<String> init(@Nonnull Map<String, ItemStack> valueIconMap) {
        List<String> valueList = new ArrayList<>(valueIconMap.size());
        for (Map.Entry<String, ItemStack> entry : valueIconMap.entrySet()) {
            if (!ItemStackUtil.isItemNull(entry.getValue())) {
                valueList.add(entry.getKey());
            }
        }
        return valueList;
    }

    @Nonnull
    public ItemStack getOrErrorIcon(@Nullable String value) {
        return valueIconMap.getOrDefault(value, ERROR_ICON);
    }

    @Nonnull
    public ItemStack defaultIcon() {
        return valueIconMap.get(this.defaultValue());
    }

    public boolean checkAndUpdateIcon(@Nonnull Inventory inventory, @Nonnull Location location, int slot) {
        String value = BlockStorage.getLocationInfo(location, this.getKey());
        if (!this.validValue(value)) {
            value = this.defaultValue();
        }
        inventory.setItem(slot, this.getOrErrorIcon(value));
        return true;
    }

    @Nonnull
    public ChestMenu.MenuClickHandler getHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageIconHelper.this.getOrDefaultValue(location);
            if (clickAction.isRightClicked()) {
                value = BlockStorageIconHelper.this.previousOrDefaultValue(value);
            } else {
                value = BlockStorageIconHelper.this.nextOrDefaultValue(value);
            }
            inventory.setItem(slot, BlockStorageIconHelper.this.getOrErrorIcon(value));
            BlockStorageIconHelper.this.setOrClearValue(location, value);
            return false;
        };
    }

    @Nonnull
    public ChestMenu.MenuClickHandler getUpdateHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageIconHelper.this.getOrDefaultValue(location);
            inventory.setItem(slot, BlockStorageIconHelper.this.getOrErrorIcon(value));
            BlockStorageIconHelper.this.setOrClearValue(location, value);
            return false;
        };
    }

    @Nonnull
    public ChestMenu.MenuClickHandler getNextHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageIconHelper.this.getOrDefaultValue(location);
            value = BlockStorageIconHelper.this.clickNextValue(value, clickAction);
            inventory.setItem(slot, BlockStorageIconHelper.this.getOrErrorIcon(value));
            BlockStorageIconHelper.this.setOrClearValue(location, value);
            return false;
        };
    }

    @Nonnull
    public ChestMenu.MenuClickHandler getPreviousHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
        return (player, i, itemStack, clickAction) -> {
            String value = BlockStorageIconHelper.this.getOrDefaultValue(location);
            value = BlockStorageIconHelper.this.clickPreviousValue(value, clickAction);
            inventory.setItem(slot, BlockStorageIconHelper.this.getOrErrorIcon(value));
            BlockStorageIconHelper.this.setOrClearValue(location, value);
            return false;
        };
    }

    @Nonnull
    public String clickNextValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
        return this.nextOrDefaultValue(value);
    }

    @Nonnull
    public String clickPreviousValue(@Nonnull String value, @Nonnull ClickAction clickAction) {
        return this.previousOrDefaultValue(value);
    }
}
