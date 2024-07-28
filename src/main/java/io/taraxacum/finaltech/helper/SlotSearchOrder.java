package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.slimefun.dto.BlockStorageHelper;
import io.taraxacum.libs.slimefun.dto.BlockStorageIconHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

public final class SlotSearchOrder {
    public static final String KEY = "sso";
    public static final String KEY_INPUT = "ssoi";
    public static final String KEY_OUTPUT = "ssoo";

    public static final String VALUE_ASCENT = "a";
    public static final String VALUE_DESCEND = "d";
    public static final String VALUE_FIRST_ONLY = "f";
    public static final String VALUE_LAST_ONLY = "l";
    public static final String VALUE_RANDOM = "r";

    public static final ItemStack ASCENT_ICON = new CustomItemStack(Material.BLUE_WOOL, FinalTechChanged.getLanguageString("helper", "SLOT_SEARCH_ORDER", "ascent", "name"), FinalTechChanged.getLanguageStringArray("helper", "SLOT_SEARCH_ORDER", "ascent", "lore"));
    public static final ItemStack DESCEND_ICON = new CustomItemStack(Material.ORANGE_WOOL, FinalTechChanged.getLanguageString("helper", "SLOT_SEARCH_ORDER", "descend", "name"), FinalTechChanged.getLanguageStringArray("helper", "SLOT_SEARCH_ORDER", "descend", "lore"));
    public static final ItemStack FIRST_ONLY_ICON = new CustomItemStack(Material.BLUE_CARPET, FinalTechChanged.getLanguageString("helper", "SLOT_SEARCH_ORDER", "first-only", "name"), FinalTechChanged.getLanguageStringArray("helper", "SLOT_SEARCH_ORDER", "first-only", "lore"));
    public static final ItemStack LAST_ONLY_ICON = new CustomItemStack(Material.ORANGE_CARPET, FinalTechChanged.getLanguageString("helper", "SLOT_SEARCH_ORDER", "last-only", "name"), FinalTechChanged.getLanguageStringArray("helper", "SLOT_SEARCH_ORDER", "last-only", "lore"));
    public static final ItemStack RANDOM_ICON = new CustomItemStack(Material.PAPER, FinalTechChanged.getLanguageString("helper", "SLOT_SEARCH_ORDER", "random", "name"), FinalTechChanged.getLanguageStringArray("helper", "SLOT_SEARCH_ORDER", "random", "lore"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_ASCENT, ASCENT_ICON);
        this.put(VALUE_DESCEND, DESCEND_ICON);
        this.put(VALUE_FIRST_ONLY, FIRST_ONLY_ICON);
        this.put(VALUE_LAST_ONLY, LAST_ONLY_ICON);
        this.put(VALUE_RANDOM, RANDOM_ICON);
    }});
    public static final BlockStorageIconHelper INPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_INPUT, new LinkedHashMap<>() {{
        this.put(VALUE_ASCENT, ASCENT_ICON);
        this.put(VALUE_DESCEND, DESCEND_ICON);
        this.put(VALUE_FIRST_ONLY, FIRST_ONLY_ICON);
        this.put(VALUE_LAST_ONLY, LAST_ONLY_ICON);
        this.put(VALUE_RANDOM, RANDOM_ICON);
    }});
    public static final BlockStorageIconHelper OUTPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_OUTPUT, new LinkedHashMap<>() {{
        this.put(VALUE_ASCENT, ASCENT_ICON);
        this.put(VALUE_DESCEND, DESCEND_ICON);
        this.put(VALUE_FIRST_ONLY, FIRST_ONLY_ICON);
        this.put(VALUE_LAST_ONLY, LAST_ONLY_ICON);
        this.put(VALUE_RANDOM, RANDOM_ICON);
    }});
}
