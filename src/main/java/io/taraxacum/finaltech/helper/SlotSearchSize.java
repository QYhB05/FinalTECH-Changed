package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.slimefun.dto.BlockStorageHelper;
import io.taraxacum.libs.slimefun.dto.BlockStorageIconHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
 
public final class SlotSearchSize {
    public static final String KEY = "sss";
    public static final String KEY_INPUT = "sssi";
    public static final String KEY_OUTPUT = "ssso";

    public static final String VALUE_INPUTS_ONLY = "io";
    public static final String VALUE_OUTPUTS_ONLY = "oo";
    public static final String VALUE_INPUTS_AND_OUTPUTS = "iao";
    public static final String VALUE_OUTPUTS_AND_INPUTS = "oai";

    public static final ItemStack INPUTS_ONLY_ICON = new CustomItemStack(Material.SOUL_TORCH, FinalTechChanged.getLanguageString("helper", "SLOT_SEARCH_SIZE", "inputs-only", "name"), FinalTechChanged.getLanguageStringArray("helper", "SLOT_SEARCH_SIZE", "inputs-only", "lore"));
    public static final ItemStack OUTPUTS_ONLY_ICON = new CustomItemStack(Material.TORCH, FinalTechChanged.getLanguageString("helper", "SLOT_SEARCH_SIZE", "outputs-only", "name"), FinalTechChanged.getLanguageStringArray("helper", "SLOT_SEARCH_SIZE", "outputs-only", "lore"));
    public static final ItemStack INPUTS_AND_OUTPUTS_ICON = new CustomItemStack(Material.REDSTONE_TORCH, FinalTechChanged.getLanguageString("helper", "SLOT_SEARCH_SIZE", "inputs-and-outputs", "name"), FinalTechChanged.getLanguageStringArray("helper", "SLOT_SEARCH_SIZE", "inputs-and-outputs", "lore"));
    public static final ItemStack OUTPUTS_AND_INPUTS_ICON = new CustomItemStack(Material.REDSTONE_TORCH, FinalTechChanged.getLanguageString("helper", "SLOT_SEARCH_SIZE", "outputs-and-inputs", "name"), FinalTechChanged.getLanguageStringArray("helper", "SLOT_SEARCH_SIZE", "outputs-and-inputs", "lore"));

    public static final BlockStorageIconHelper HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_INPUTS_ONLY, INPUTS_ONLY_ICON);
        this.put(VALUE_OUTPUTS_ONLY, OUTPUTS_ONLY_ICON);
        this.put(VALUE_INPUTS_AND_OUTPUTS, INPUTS_AND_OUTPUTS_ICON);
        this.put(VALUE_OUTPUTS_AND_INPUTS, OUTPUTS_AND_INPUTS_ICON);
    }});
    public static final BlockStorageIconHelper INPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_INPUT, new LinkedHashMap<>() {{
        this.put(VALUE_OUTPUTS_ONLY, OUTPUTS_ONLY_ICON);
        this.put(VALUE_INPUTS_AND_OUTPUTS, INPUTS_AND_OUTPUTS_ICON);
        this.put(VALUE_OUTPUTS_AND_INPUTS, OUTPUTS_AND_INPUTS_ICON);
        this.put(VALUE_INPUTS_ONLY, INPUTS_ONLY_ICON);
    }});
    public static final BlockStorageIconHelper OUTPUT_HELPER = BlockStorageIconHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY_OUTPUT, new LinkedHashMap<>() {{
        this.put(VALUE_INPUTS_ONLY, INPUTS_ONLY_ICON);
        this.put(VALUE_OUTPUTS_ONLY, OUTPUTS_ONLY_ICON);
        this.put(VALUE_INPUTS_AND_OUTPUTS, INPUTS_AND_OUTPUTS_ICON);
        this.put(VALUE_OUTPUTS_AND_INPUTS, OUTPUTS_AND_INPUTS_ICON);
    }});
}
