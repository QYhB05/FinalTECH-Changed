package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.slimefun.dto.BlockStorageHelper;
import io.taraxacum.libs.slimefun.dto.BlockStorageIconHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
 
public final class CargoLimit {
    public static final String KEY = "cl";
    public static final String KEY_INPUT = "cli";
    public static final String KEY_OUTPUT = "clo";

    public static final String VALUE_ALL = "a";
    public static final String VALUE_TYPE = "t";
    public static final String VALUE_STACK = "s";
    public static final String VALUE_NONNULL = "n";
    public static final String VALUE_FIRST = "f";

    public static final ItemStack ALL_ICON = new CustomItemStack(Material.PUMPKIN, FinalTechChanged.getLanguageString("helper", "CARGO_LIMIT", "all", "name"), FinalTechChanged.getLanguageStringArray("helper", "CARGO_LIMIT", "all", "lore"));
    public static final ItemStack TYPE_ICON = new CustomItemStack(Material.CARVED_PUMPKIN, FinalTechChanged.getLanguageString("helper", "CARGO_LIMIT", "type", "name"), FinalTechChanged.getLanguageStringArray("helper", "CARGO_LIMIT", "type", "lore"));
    public static final ItemStack STACK_ICON = new CustomItemStack(Material.JACK_O_LANTERN, FinalTechChanged.getLanguageString("helper", "CARGO_LIMIT", "stack", "name"), FinalTechChanged.getLanguageStringArray("helper", "CARGO_LIMIT", "stack", "lore"));
    public static final ItemStack NONNULL_ICON = new CustomItemStack(Material.PAPER, FinalTechChanged.getLanguageString("helper", "CARGO_LIMIT", "nonnull", "name"), FinalTechChanged.getLanguageStringArray("helper", "CARGO_LIMIT", "nonnull", "lore"));
    public static final ItemStack FIRST_ICON = new CustomItemStack(Material.PAPER, FinalTechChanged.getLanguageString("helper", "CARGO_LIMIT", "first", "name"), FinalTechChanged.getLanguageStringArray("helper", "CARGO_LIMIT", "first", "lore"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_FIRST, FIRST_ICON);
        this.put(VALUE_ALL, ALL_ICON);
        this.put(VALUE_TYPE, TYPE_ICON);
        this.put(VALUE_STACK, STACK_ICON);
        this.put(VALUE_NONNULL, NONNULL_ICON);
    }});
    public static final BlockStorageIconHelper INPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_INPUT, new LinkedHashMap<>() {{
        this.put(VALUE_FIRST, FIRST_ICON);
        this.put(VALUE_ALL, ALL_ICON);
        this.put(VALUE_TYPE, TYPE_ICON);
        this.put(VALUE_STACK, STACK_ICON);
        this.put(VALUE_NONNULL, NONNULL_ICON);
    }});
    public static final BlockStorageIconHelper OUTPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_OUTPUT, new LinkedHashMap<>() {{
        this.put(VALUE_FIRST, FIRST_ICON);
        this.put(VALUE_ALL, ALL_ICON);
        this.put(VALUE_TYPE, TYPE_ICON);
        this.put(VALUE_STACK, STACK_ICON);
        this.put(VALUE_NONNULL, NONNULL_ICON);
    }});

    public static boolean typeLimit(@Nonnull String value) {
        return VALUE_TYPE.equals(value) || VALUE_STACK.equals(value);
    }
}
