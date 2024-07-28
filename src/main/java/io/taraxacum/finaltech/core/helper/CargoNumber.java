package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.slimefun.dto.BlockStorageHelper;
import io.taraxacum.libs.slimefun.dto.BlockStorageLoreHelper;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;

/**
 * @author Final_ROOT
 */
public final class CargoNumber {
    public static final String KEY = "cb";
    public static final String KEY_INPUT = "cbi";
    public static final String KEY_OUTPUT = "cbo";

    public static final ItemStack CARGO_NUMBER_ICON = new CustomItemStack(Material.TARGET, FinalTechChanged.getLanguageString("helper", "CARGO_NUMBER", "icon", "name"), FinalTechChanged.getLanguageStringArray("helper", "CARGO_NUMBER", "icon", "lore"));
    public static final ItemStack CARGO_NUMBER_ADD_ICON = new CustomItemStack(Material.GREEN_CONCRETE, FinalTechChanged.getLanguageString("helper", "CARGO_NUMBER", "add-icon", "name"), FinalTechChanged.getLanguageStringArray("helper", "CARGO_NUMBER", "add-icon", "lore"));
    public static final ItemStack CARGO_NUMBER_SUB_ICON = new CustomItemStack(Material.RED_CONCRETE, FinalTechChanged.getLanguageString("helper", "CARGO_NUMBER", "sub-icon", "name"), FinalTechChanged.getLanguageStringArray("helper", "CARGO_NUMBER", "sub-icon", "lore"));

    public static final BlockStorageLoreHelper HELPER = new BlockStorageLoreHelper(BlockStorageHelper.ID_CARGO, 0, new LinkedHashMap<>() {{
//        this.put("0", FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("helper", "CARGO_NUMBER", "icon", "lore0")));
        for (int i = 1; i <= ConstantTableUtil.ITEM_MAX_STACK * 9; i++) {
            this.put(String.valueOf(i), FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("helper", "CARGO_NUMBER", "icon", "lore"), String.valueOf(i)));
        }
    }}) {
        @Nonnull
        @Override
        public String getKey() {
            return KEY;
        }

        @Nonnull
        @Override
        public String defaultValue() {
            return String.valueOf(ConstantTableUtil.ITEM_MAX_STACK);
        }

        @Nonnull
        @Override
        public String clickNextValue(@Nullable String value, @Nonnull ClickAction clickAction) {
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return HELPER.nextOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return HELPER.offsetOrDefaultValue(value, ConstantTableUtil.ITEM_MAX_STACK);
            } else {
                return HELPER.offsetOrDefaultValue(value, 8);
            }
        }

        @Nonnull
        @Override
        public String clickPreviousValue(@Nullable String value, @Nonnull ClickAction clickAction) {
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return HELPER.previousOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return HELPER.offsetOrDefaultValue(value, -ConstantTableUtil.ITEM_MAX_STACK);
            } else {
                return HELPER.offsetOrDefaultValue(value, -8);
            }
        }
    };
    public static final BlockStorageLoreHelper INPUT_HELPER = new BlockStorageLoreHelper(BlockStorageHelper.ID_CARGO, 0, new LinkedHashMap<>() {{
//        this.put("0", FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("helper", "CARGO_NUMBER", "icon", "lore0")));
        for (int i = 1; i <= ConstantTableUtil.ITEM_MAX_STACK * 9; i++) {
            this.put(String.valueOf(i), FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("helper", "CARGO_NUMBER", "icon", "lore"), String.valueOf(i)));
        }
    }}) {
        @Nonnull
        @Override
        public String getKey() {
            return KEY_INPUT;
        }

        @Nonnull
        @Override
        public String defaultValue() {
            return String.valueOf(ConstantTableUtil.ITEM_MAX_STACK);
        }

        @Nonnull
        @Override
        public String clickNextValue(@Nullable String value, @Nonnull ClickAction clickAction) {
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return INPUT_HELPER.nextOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return INPUT_HELPER.offsetOrDefaultValue(value, ConstantTableUtil.ITEM_MAX_STACK);
            } else {
                return INPUT_HELPER.offsetOrDefaultValue(value, 8);
            }
        }

        @Nonnull
        @Override
        public String clickPreviousValue(@Nullable String value, @Nonnull ClickAction clickAction) {
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return INPUT_HELPER.previousOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return INPUT_HELPER.offsetOrDefaultValue(value, -ConstantTableUtil.ITEM_MAX_STACK);
            } else {
                return INPUT_HELPER.offsetOrDefaultValue(value, -8);
            }
        }
    };
    public static final BlockStorageLoreHelper OUTPUT_HELPER = new BlockStorageLoreHelper(BlockStorageHelper.ID_CARGO, 0, new LinkedHashMap<>() {{
//        this.put("0", FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("helper", "CARGO_NUMBER", "icon", "lore0")));
        for (int i = 1; i <= ConstantTableUtil.ITEM_MAX_STACK * 9; i++) {
            this.put(String.valueOf(i), FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("helper", "CARGO_NUMBER", "icon", "lore"), String.valueOf(i)));
        }
    }}) {
        @Nonnull
        @Override
        public String getKey() {
            return KEY_OUTPUT;
        }

        @Nonnull
        @Override
        public String defaultValue() {
            return String.valueOf(ConstantTableUtil.ITEM_MAX_STACK);
        }

        @Nonnull
        @Override
        public String clickNextValue(@Nullable String value, @Nonnull ClickAction clickAction) {
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return OUTPUT_HELPER.nextOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return OUTPUT_HELPER.offsetOrDefaultValue(value, ConstantTableUtil.ITEM_MAX_STACK);
            } else {
                return OUTPUT_HELPER.offsetOrDefaultValue(value, 8);
            }
        }

        @Nonnull
        @Override
        public String clickPreviousValue(@Nullable String value, @Nonnull ClickAction clickAction) {
            if (!clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return OUTPUT_HELPER.previousOrDefaultValue(value);
            } else if (clickAction.isShiftClicked() && !clickAction.isRightClicked()) {
                return OUTPUT_HELPER.offsetOrDefaultValue(value, -ConstantTableUtil.ITEM_MAX_STACK);
            } else {
                return OUTPUT_HELPER.offsetOrDefaultValue(value, -8);
            }
        }
    };
}
