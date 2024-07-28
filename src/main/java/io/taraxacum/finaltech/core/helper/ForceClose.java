package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.slimefun.dto.BlockStorageHelper;
import io.taraxacum.libs.slimefun.dto.BlockStorageIconHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public final class ForceClose {
    public static final String KEY = "fc";

    public static final String VALUE_FALSE = "f";
    public static final String VALUE_TRUE = "t";

    public static final ItemStack FALSE_ICON = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("helper", "FORCE_CLOSE", "false", "name"), FinalTechChanged.getLanguageStringArray("helper", "FORCE_CLOSE", "false", "lore"));
    public static final ItemStack TRUE_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("helper", "FORCE_CLOSE", "true", "name"), FinalTechChanged.getLanguageStringArray("helper", "FORCE_CLOSE", "true", "lore"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_FALSE, FALSE_ICON);
        this.put(VALUE_TRUE, TRUE_ICON);
    }});
}
