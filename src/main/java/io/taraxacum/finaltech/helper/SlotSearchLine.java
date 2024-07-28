package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.plugin.dto.KeyValueStringHelper;
import io.taraxacum.libs.slimefun.dto.BlockStorageHelper;
import io.taraxacum.libs.slimefun.dto.BlockStorageLoreHelper;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
 
public class SlotSearchLine {
    public static final String KEY = "ssl";

    public static final String VALUE_KEY_L1 = "l1";
    public static final String VALUE_KEY_L2 = "l2";
    public static final String VALUE_KEY_L3 = "l3";

    public static final ItemStack L1_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("helper", "SLOT_SEARCH_LINE", "l1", "name"), FinalTechChanged.getLanguageStringArray("helper", "SLOT_SEARCH_LINE", "l1", "lore"));
    public static final ItemStack L2_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("helper", "SLOT_SEARCH_LINE", "l2", "name"), FinalTechChanged.getLanguageStringArray("helper", "SLOT_SEARCH_LINE", "l2", "lore"));
    public static final ItemStack L3_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("helper", "SLOT_SEARCH_LINE", "l3", "name"), FinalTechChanged.getLanguageStringArray("helper", "SLOT_SEARCH_LINE", "l3", "lore"));

    public static final String VALUE_NULL = null;
    public static final String VALUE_POSITIVE = "p";
    public static final String VALUE_RESERVE = "re";
    public static final String VALUE_RANDOM = "ra";

    public static final List<String> NULL_LORE = FinalTechChanged.getLanguageStringList("helper", "SLOT_SEARCH_LINE", "null", "lore");
    public static final List<String> POSITIVE_LORE = FinalTechChanged.getLanguageStringList("helper", "SLOT_SEARCH_LINE", "positive", "lore");
    public static final List<String> RESERVE_LORE = FinalTechChanged.getLanguageStringList("helper", "SLOT_SEARCH_LINE", "reserve", "lore");
    public static final List<String> RANDOM_LORE = FinalTechChanged.getLanguageStringList("helper", "SLOT_SEARCH_LINE", "random", "lore");

    public static final Material NULL_MATERIAL = Material.GRAY_STAINED_GLASS_PANE;
    public static final Material POSITIVE_MATERIAL = Material.BLUE_STAINED_GLASS_PANE;
    public static final Material RESERVE_MATERIAL = Material.ORANGE_STAINED_GLASS_PANE;
    public static final Material RANDOM_MATERIAL = Material.PURPLE_STAINED_GLASS_PANE;

    public static final KeyValueStringHelper MAP_EXAMPLE = new KeyValueStringHelper(Arrays.asList(VALUE_KEY_L1, VALUE_KEY_L2, VALUE_KEY_L3), Arrays.asList(VALUE_NULL, VALUE_POSITIVE, VALUE_RESERVE, VALUE_RANDOM));

    public static final Map<String, Material> valueMaterialMap = new HashMap<>() {{
        this.put(VALUE_NULL, NULL_MATERIAL);
        this.put(VALUE_POSITIVE, POSITIVE_MATERIAL);
        this.put(VALUE_RESERVE, RESERVE_MATERIAL);
        this.put(VALUE_RANDOM, RANDOM_MATERIAL);
    }};

    public static final BlockStorageLoreMaterialHelper L1_HELPER = new BlockStorageLoreMaterialHelper() {
        @Nonnull
        @Override
        public String getKey() {
            return VALUE_KEY_L1;
        }
    };

    public static final BlockStorageLoreMaterialHelper L2_HELPER = new BlockStorageLoreMaterialHelper() {
        @Nonnull
        @Override
        public String getKey() {
            return VALUE_KEY_L2;
        }
    };

    public static final BlockStorageLoreMaterialHelper L3_HELPER = new BlockStorageLoreMaterialHelper() {
        @Nonnull
        @Override
        public String getKey() {
            return VALUE_KEY_L3;
        }
    };

    public static abstract class BlockStorageLoreMaterialHelper extends BlockStorageLoreHelper {
        BlockStorageLoreMaterialHelper() {
            super(BlockStorageHelper.ID_CARGO, new LinkedHashMap<>() {{
                this.put(VALUE_NULL, NULL_LORE);
                this.put(VALUE_POSITIVE, POSITIVE_LORE);
                this.put(VALUE_RESERVE, RESERVE_LORE);
                this.put(VALUE_RANDOM, RANDOM_LORE);
            }});
        }

        @Nonnull
        @Override
        public String getOrDefaultValue(@Nonnull Location location) {
            String valueMap = BlockStorage.getLocationInfo(location, KEY);
            KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
            String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
            if (!BlockStorageLoreMaterialHelper.this.validValue(value)) {
                value = BlockStorageLoreMaterialHelper.this.defaultValue();
            }
            return value;
        }

        @Nonnull
        @Override
        public String getOrDefaultValue(@Nonnull Config config) {
            String valueMap = config.getString(KEY);
            KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
            String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
            if (!BlockStorageLoreMaterialHelper.this.validValue(value)) {
                value = BlockStorageLoreMaterialHelper.this.defaultValue();
            }
            return value;
        }

        @Override
        public boolean setIcon(@Nonnull ItemStack iconItem, @Nullable String value) {
            if (BlockStorageLoreMaterialHelper.this.validValue(value) && valueMaterialMap.containsKey(value)) {
                iconItem.setType(valueMaterialMap.get(value));
            }
            return BlockStorageLoreMaterialHelper.super.setIcon(iconItem, value);
        }

        @Override
        public boolean setIcon(@Nonnull ItemStack iconItem, @Nullable String value, @Nonnull SlimefunItem slimefunItem) {
            if (BlockStorageLoreMaterialHelper.this.validValue(value) && valueMaterialMap.containsKey(value)) {
                iconItem.setType(valueMaterialMap.get(value));
            }
            return BlockStorageLoreMaterialHelper.super.setIcon(iconItem, value, slimefunItem);
        }

        @Override
        public boolean checkAndUpdateIcon(@Nonnull Inventory inventory, @Nonnull Location location, int slot) {
            String valueMap = BlockStorage.getLocationInfo(location, this.getKey());
            if (valueMap == null) {
                valueMap = "";
            }
            KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
            String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
            if (!BlockStorageLoreMaterialHelper.this.validValue(value)) {
                value = BlockStorageLoreMaterialHelper.this.defaultValue();
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorage.addBlockInfo(location, KEY, keyValueStringHelper.toString());
            }
            ItemStack item = inventory.getItem(slot);
            BlockStorageLoreMaterialHelper.this.setIcon(item, value);
            return true;
        }

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
            return (p, slot1, item, action) -> {
                String valueMap = BlockStorage.getLocationInfo(location, KEY);
                KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
                String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
                if (!action.isRightClicked()) {
                    value = BlockStorageLoreMaterialHelper.this.clickNextValue(value, action);
                } else {
                    value = BlockStorageLoreMaterialHelper.this.clickPreviousValue(value, action);
                }
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorageLoreMaterialHelper.this.setIcon(item, value);
                BlockStorageLoreMaterialHelper.this.setOrClearValue(location, keyValueStringHelper.toString());
                return false;
            };
        }

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getUpdateHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
            return (p, slot1, item, action) -> {
                String valueMap = BlockStorage.getLocationInfo(location, KEY);
                KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
                String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
                if (!BlockStorageLoreMaterialHelper.this.validValue(value)) {
                    value = BlockStorageLoreMaterialHelper.this.defaultValue();
                }
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorageLoreMaterialHelper.this.setIcon(item, value);
                BlockStorageLoreMaterialHelper.this.setOrClearValue(location, keyValueStringHelper.toString());
                return false;
            };
        }

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getNextHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
            return (p, slot1, item, action) -> {
                String valueMap = BlockStorage.getLocationInfo(location, KEY);
                KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
                String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
                value = BlockStorageLoreMaterialHelper.this.clickNextValue(value, action);
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorageLoreMaterialHelper.this.setIcon(item, value);
                BlockStorageLoreMaterialHelper.this.setOrClearValue(location, keyValueStringHelper.toString());
                return false;
            };
        }

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getPreviousHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
            return (p, slot1, item, action) -> {
                String valueMap = BlockStorage.getLocationInfo(location, KEY);
                KeyValueStringHelper keyValueStringHelper = MAP_EXAMPLE.parseString(valueMap);
                String value = keyValueStringHelper.getValue(BlockStorageLoreMaterialHelper.this.getKey());
                value = BlockStorageLoreMaterialHelper.this.clickPreviousValue(value, action);
                keyValueStringHelper.putEntry(BlockStorageLoreMaterialHelper.this.getKey(), value);
                BlockStorageLoreMaterialHelper.this.setIcon(item, value);
                BlockStorageLoreMaterialHelper.this.setOrClearValue(location, keyValueStringHelper.toString());
                return false;
            };
        }
    }
}
