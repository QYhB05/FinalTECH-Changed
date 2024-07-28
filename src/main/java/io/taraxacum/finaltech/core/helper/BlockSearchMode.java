package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.slimefun.dto.BlockStorageHelper;
import io.taraxacum.libs.slimefun.dto.BlockStorageIconHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

public final class BlockSearchMode {
    public static final String KEY = "bsm";
    public static final String KEY_INPUT = "bsmi";
    public static final String KEY_OUTPUT = "bsmo";

    public static final String SUB_KEY_PIPE = "-p";
    public static final String SUB_KEY_LINE = "-l";
    public static final String SUB_KEY_STATION = "-s";

    public static final String VALUE_ZERO = "z";
    public static final String VALUE_INHERIT = "ih";
    public static final String VALUE_PENETRATE = "p";
    public static final String VALUE_INTERRUPT = "it";

    public static final ItemStack ZERO_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, FinalTechChanged.getLanguageString("helper", "BLOCK_SEARCH_MODE", "zero", "name"), FinalTechChanged.getLanguageStringArray("helper", "BLOCK_SEARCH_MODE", "zero", "lore"));
    public static final ItemStack INHERIT_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, FinalTechChanged.getLanguageString("helper", "BLOCK_SEARCH_MODE", "inherit", "name"), FinalTechChanged.getLanguageStringArray("helper", "BLOCK_SEARCH_MODE", "inherit", "lore"));
    public static final ItemStack PENETRATE_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, FinalTechChanged.getLanguageString("helper", "BLOCK_SEARCH_MODE", "penetrate", "name"), FinalTechChanged.getLanguageStringArray("helper", "BLOCK_SEARCH_MODE", "penetrate", "lore"));
    public static final ItemStack INTERRUPT_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS, FinalTechChanged.getLanguageString("helper", "BLOCK_SEARCH_MODE", "interrupt", "name"), FinalTechChanged.getLanguageStringArray("helper", "BLOCK_SEARCH_MODE", "interrupt", "lore"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_ZERO, ZERO_ICON);
        this.put(VALUE_INHERIT, INHERIT_ICON);
        this.put(VALUE_PENETRATE, PENETRATE_ICON);
        this.put(VALUE_INTERRUPT, INTERRUPT_ICON);
    }});
    public static final BlockStorageIconHelper POINT_INPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_INPUT + SUB_KEY_PIPE, new LinkedHashMap<>() {{
        this.put(VALUE_ZERO, ZERO_ICON);
        this.put(VALUE_INHERIT, INHERIT_ICON);
        this.put(VALUE_PENETRATE, PENETRATE_ICON);
    }});
    public static final BlockStorageIconHelper POINT_OUTPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_OUTPUT + SUB_KEY_PIPE, new LinkedHashMap<>() {{
        this.put(VALUE_ZERO, ZERO_ICON);
        this.put(VALUE_INHERIT, INHERIT_ICON);
        this.put(VALUE_PENETRATE, PENETRATE_ICON);
    }});
    public static final BlockStorageIconHelper LINE_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY + SUB_KEY_LINE, new LinkedHashMap<>() {{
        this.put(VALUE_ZERO, ZERO_ICON);
        this.put(VALUE_PENETRATE, PENETRATE_ICON);
        this.put(VALUE_INTERRUPT, INTERRUPT_ICON);
    }});
    public static final BlockStorageIconHelper MESH_INPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_INPUT + SUB_KEY_STATION, new LinkedHashMap<>() {{
        this.put(VALUE_ZERO, ZERO_ICON);
        this.put(VALUE_PENETRATE, PENETRATE_ICON);
        this.put(VALUE_INTERRUPT, INTERRUPT_ICON);
    }});
    public static final BlockStorageIconHelper MESH_OUTPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_OUTPUT + SUB_KEY_STATION, new LinkedHashMap<>() {{
        this.put(VALUE_ZERO, ZERO_ICON);
        this.put(VALUE_PENETRATE, PENETRATE_ICON);
        this.put(VALUE_INTERRUPT, INTERRUPT_ICON);
    }});
}
