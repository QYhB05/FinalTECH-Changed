package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.slimefun.dto.BlockStorageHelper;
import io.taraxacum.libs.slimefun.dto.BlockStorageIconHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
 
public final class BlockSearchOrder {
    public static final String KEY = "bso";

    public static final String VALUE_POSITIVE = "p";
    public static final String VALUE_REVERSE = "re";
    public static final String VALUE_RANDOM = "ra";

    public static final ItemStack POSITIVE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, FinalTechChanged.getLanguageString("helper", "BLOCK_SEARCH_ORDER", "positive", "name"), FinalTechChanged.getLanguageStringArray("helper", "BLOCK_SEARCH_ORDER", "positive", "lore"));
    public static final ItemStack REVERSE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, FinalTechChanged.getLanguageString("helper", "BLOCK_SEARCH_ORDER", "reverse", "name"), FinalTechChanged.getLanguageStringArray("helper", "BLOCK_SEARCH_ORDER", "reverse", "lore"));
    public static final ItemStack RANDOM_ICON = new CustomItemStack(Material.CRIMSON_DOOR, FinalTechChanged.getLanguageString("helper", "BLOCK_SEARCH_ORDER", "random", "name"), FinalTechChanged.getLanguageStringArray("helper", "BLOCK_SEARCH_ORDER", "random", "lore"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_POSITIVE, POSITIVE_ICON);
        this.put(VALUE_REVERSE, REVERSE_ICON);
        this.put(VALUE_RANDOM, RANDOM_ICON);
    }});
}
