package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.plugin.dto.AdvancedMachineRecipe;
import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.BlockStorageHelper;
import io.taraxacum.libs.slimefun.dto.BlockStorageLoreHelper;
import io.taraxacum.libs.slimefun.dto.MachineRecipeFactory;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Final_ROOT
 */
public final class MachineRecipeLock {
    public static final String KEY = "rl";

    public static final String VALUE_UNLOCK = "-1";
    public static final String VALUE_LOCK_OFF = "-2";

    public static final ItemStack ICON = new CustomItemStack(Material.TRIPWIRE_HOOK, FinalTechChanged.getLanguageString("helper", "MACHINE_RECIPE_LOCK", "icon", "name"), FinalTechChanged.getLanguageStringArray("helper", "MACHINE_RECIPE_LOCK", "icon", "lore"));

    public static final BlockStorageLoreHelper HELPER = new BlockStorageLoreHelper(BlockStorageHelper.ID_CARGO, new LinkedHashMap<>(2) {{
        this.put("-2", FinalTechChanged.getLanguageStringList("helper", "MACHINE_RECIPE_LOCK", "unlock", "lore"));
        this.put("-1", FinalTechChanged.getLanguageStringList("helper", "MACHINE_RECIPE_LOCK", "lock-off", "lore"));
    }}) {
        @Nonnull
        @Override
        public String getKey() {
            return KEY;
        }

        @Nonnull
        @Override
        public String nextOrDefaultValue(@Nullable String value) {
            return this.defaultValue();
        }

        @Nonnull
        @Override
        public String previousOrDefaultValue(@Nullable String value) {
            return this.defaultValue();
        }

        @Nonnull
        @Override
        public ChestMenu.MenuClickHandler getHandler(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull SlimefunItem slimefunItem, int slot) {
            return (player, i, itemStack, clickAction) -> {
                HELPER.checkOrSetBlockStorage(location);
                String value = clickAction.isRightClicked() ? VALUE_LOCK_OFF : VALUE_UNLOCK;
                HELPER.setIcon(inventory.getItem(slot), value);
                BlockStorage.addBlockInfo(location, KEY, value);
                return false;
            };
        }

        @Override
        public boolean setIcon(@Nonnull ItemStack item, @Nullable String value, @Nonnull SlimefunItem slimefunItem) {
            if (this.validValue(value)) {
                super.setIcon(item, value);
                return true;
            } else {
                int recipeLock = value == null ? Integer.parseInt(this.defaultValue()) : Integer.parseInt(value);
                List<AdvancedMachineRecipe> advancedMachineRecipeList = MachineRecipeFactory.getInstance().getAdvancedRecipe(slimefunItem.getId());
                if (recipeLock >= 0) {
                    recipeLock = recipeLock % advancedMachineRecipeList.size();
                    AdvancedMachineRecipe advancedMachineRecipe = advancedMachineRecipeList.get(recipeLock);
                    List<String> loreList;
                    if (advancedMachineRecipe.getOutputs().length == 1) {
                        loreList = new ArrayList<>(advancedMachineRecipe.getInput().length + advancedMachineRecipe.getOutputs()[0].getOutputItem().length + 3);
                        loreList.addAll(FinalTechChanged.getLanguageStringList("helper", "MACHINE_RECIPE_LOCK", "input", "title"));
                        for (ItemAmountWrapper inputItem : advancedMachineRecipe.getInput()) {
                            loreList.addAll(FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("helper", "MACHINE_RECIPE_LOCK", "input", "items"),
                                    ItemStackUtil.getItemName(inputItem.getItemStack()),
                                    String.valueOf(inputItem.getAmount())));
                        }
                        loreList.addAll(FinalTechChanged.getLanguageStringList("helper", "MACHINE_RECIPE_LOCK", "output", "title"));
                        for (ItemAmountWrapper outputItem : advancedMachineRecipe.getOutputs()[0].getOutputItem()) {
                            loreList.addAll(FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("helper", "MACHINE_RECIPE_LOCK", "output", "items"),
                                    ItemStackUtil.getItemName(outputItem.getItemStack()),
                                    String.valueOf(outputItem.getAmount())));
                        }
                    } else {
                        int outputLength = 0;
                        for (AdvancedMachineRecipe.AdvancedRandomOutput advancedRandomOutput : advancedMachineRecipe.getOutputs()) {
                            outputLength += advancedRandomOutput.getOutputItem().length + 1;
                        }
                        loreList = new ArrayList<>(advancedMachineRecipe.getInput().length + outputLength + 3);
                        loreList.addAll(FinalTechChanged.getLanguageStringList("helper", "MACHINE_RECIPE_LOCK", "input", "title"));
                        for (ItemAmountWrapper inputItem : advancedMachineRecipe.getInput()) {
                            loreList.addAll(FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("helper", "MACHINE_RECIPE_LOCK", "input", "items"),
                                    ItemStackUtil.getItemName(inputItem.getItemStack()),
                                    String.valueOf(inputItem.getAmount())));
                        }
                        loreList.addAll(FinalTechChanged.getLanguageStringList("helper", "MACHINE_RECIPE_LOCK", "output", "title"));
                        for (AdvancedMachineRecipe.AdvancedRandomOutput advancedRandomOutput : advancedMachineRecipe.getOutputs()) {
                            String random = String.valueOf(((double) advancedRandomOutput.getWeight()) / advancedMachineRecipe.getWeightSum() * 100.0);
                            if (random.contains(".")) {
                                random = random.substring(0, Math.min(random.indexOf(".") + 3, random.length()));
                            }
                            loreList.addAll(FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("helper", "MACHINE_RECIPE_LOCK", "output-random", "title"),
                                    random));
                            for (ItemAmountWrapper outputItem : advancedRandomOutput.getOutputItem()) {
                                loreList.addAll(FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("helper", "MACHINE_RECIPE_LOCK", "input", "items"),
                                        ItemStackUtil.getItemName(outputItem.getItemStack()),
                                        String.valueOf(outputItem.getAmount())));
                            }
                        }
                    }
                    ItemStackUtil.setLore(item, loreList);
                }
                return false;
            }
        }
    };
}
