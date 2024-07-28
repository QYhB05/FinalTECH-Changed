package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.slimefun.dto.BlockStorageHelper;
import io.taraxacum.libs.slimefun.dto.BlockStorageIconHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

public final class BlockSearchSelf {
    public static final String KEY = "bss";

    public static final String VALUE_FALSE = "f";
    public static final String VALUE_START = "b";
    public static final String VALUE_END = "l";

    public static final ItemStack FALSE_ICON = new CustomItemStack(Material.MINECART, FinalTechChanged.getLanguageString("helper", "BLOCK_SEARCH_SELF", "false", "name"), FinalTechChanged.getLanguageStringArray("helper", "BLOCK_SEARCH_SELF", "false", "lore"));
    public static final ItemStack START_ICON = new CustomItemStack(Material.CHEST_MINECART, FinalTechChanged.getLanguageString("helper", "BLOCK_SEARCH_SELF", "start", "name"), FinalTechChanged.getLanguageStringArray("helper", "BLOCK_SEARCH_SELF", "start", "lore"));
    public static final ItemStack END_ICON = new CustomItemStack(Material.CHEST_MINECART, FinalTechChanged.getLanguageString("helper", "BLOCK_SEARCH_SELF", "end", "name"), FinalTechChanged.getLanguageStringArray("helper", "BLOCK_SEARCH_SELF", "end", "lore"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_FALSE, FALSE_ICON);
        this.put(VALUE_START, START_ICON);
        this.put(VALUE_END, END_ICON);
    }});
}
