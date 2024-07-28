package io.taraxacum.libs.slimefun.dto;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.plugin.dto.ConfigFileManager;
import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: remove FinalTechChanged
public class ItemValueTable {
    private static volatile ItemValueTable instance;
    public final String baseOutputValue = FinalTechChanged.getValueManager().getOrDefault("64", "itemValueTable", "baseOutputValue");
    public final String baseInputValue = FinalTechChanged.getValueManager().getOrDefault("1", "itemValueTable", "baseInputValue");
    // key: slimefun item id, value: input value
    private final Map<String, String> itemOutputValueMap = new HashMap<>(Slimefun.getRegistry().getAllSlimefunItems().size());
    // key: slimefun item id, value: output value
    private final Map<String, String> itemInputValueMap = new HashMap<>(Slimefun.getRegistry().getAllSlimefunItems().size());
    // key: output value, value: all slimefun item id with same output value
    private final Map<String, List<String>> valueItemListOutputMap = new HashMap<>();
    private boolean init = false;

    private ItemValueTable() {

    }

    @Nonnull
    public static ItemValueTable getInstance() {
        if (instance == null) {
            synchronized (ItemValueTable.class) {
                if (instance == null) {
                    instance = new ItemValueTable();
                }
            }
        }
        return instance;
    }

    public void init() {
        if (this.init) {
            return;
        }

        this.init = true;

        ConfigFileManager valueFile = FinalTechChanged.getValueManager();

        this.itemInputValueMap.put(SlimefunItems.IRON_DUST.getItemId(), StringNumberUtil.mul(this.baseInputValue, StringNumberUtil.ZERO));
        this.itemInputValueMap.put(SlimefunItems.GOLD_DUST.getItemId(), StringNumberUtil.mul(this.baseInputValue, StringNumberUtil.ZERO));
        this.itemInputValueMap.put(SlimefunItems.COPPER_DUST.getItemId(), StringNumberUtil.mul(this.baseInputValue, StringNumberUtil.ZERO));
        this.itemInputValueMap.put(SlimefunItems.TIN_DUST.getItemId(), StringNumberUtil.mul(this.baseInputValue, StringNumberUtil.ZERO));
        this.itemInputValueMap.put(SlimefunItems.LEAD_DUST.getItemId(), StringNumberUtil.mul(this.baseInputValue, StringNumberUtil.ZERO));
        this.itemInputValueMap.put(SlimefunItems.SILVER_DUST.getItemId(), StringNumberUtil.mul(this.baseInputValue, StringNumberUtil.ZERO));
        this.itemInputValueMap.put(SlimefunItems.ALUMINUM_DUST.getItemId(), StringNumberUtil.mul(this.baseInputValue, StringNumberUtil.ZERO));
        this.itemInputValueMap.put(SlimefunItems.ZINC_DUST.getItemId(), StringNumberUtil.mul(this.baseInputValue, StringNumberUtil.ZERO));
        this.itemInputValueMap.put(SlimefunItems.MAGNESIUM_DUST.getItemId(), StringNumberUtil.mul(this.baseInputValue, StringNumberUtil.ZERO));

        this.itemOutputValueMap.put(SlimefunItems.IRON_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "16"));
        this.itemOutputValueMap.put(SlimefunItems.GOLD_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "16"));
        this.itemOutputValueMap.put(SlimefunItems.COPPER_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "16"));
        this.itemOutputValueMap.put(SlimefunItems.TIN_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "16"));
        this.itemOutputValueMap.put(SlimefunItems.LEAD_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "16"));
        this.itemOutputValueMap.put(SlimefunItems.SILVER_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "16"));
        this.itemOutputValueMap.put(SlimefunItems.ALUMINUM_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "16"));
        this.itemOutputValueMap.put(SlimefunItems.ZINC_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "16"));
        this.itemOutputValueMap.put(SlimefunItems.MAGNESIUM_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "16"));

        if (valueFile.containPath("input", "base")) {
            for (String key : valueFile.getStringList("input", "base")) {
                this.itemInputValueMap.put(key, valueFile.getString("input", "base", key));
            }
        }
        if (valueFile.containPath("output", "base")) {
            for (String key : valueFile.getStringList("output", "base")) {
                String value = valueFile.getString("output", "base", key);
                this.itemOutputValueMap.put(key, value);
                if (!StringNumberUtil.VALUE_INFINITY.equals(value)) {
                    this.addToOutputMap(key, value);
                }
            }
        }

        this.manualInitId(FinalTechItemStacks.COPY_CARD.getItemId(), String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT), StringNumberUtil.mul(String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT), String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT)), false);
        this.manualInitId(FinalTechItemStacks.SINGULARITY.getItemId(), StringNumberUtil.mul(String.valueOf(ConstantTableUtil.ITEM_SINGULARITY_AMOUNT), this.getOrCalItemInputValue(FinalTechItemStacks.COPY_CARD)), StringNumberUtil.mul(String.valueOf(ConstantTableUtil.ITEM_SINGULARITY_AMOUNT), this.getOrCalItemOutputValue(FinalTechItemStacks.COPY_CARD)), false);
        this.manualInitId(FinalTechItemStacks.SPIROCHETE.getItemId(), StringNumberUtil.mul(String.valueOf(ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT * ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT), this.getOrCalItemInputValue(FinalTechItemStacks.COPY_CARD)), StringNumberUtil.mul(String.valueOf(ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT * ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT), this.getOrCalItemOutputValue(FinalTechItemStacks.COPY_CARD)), false);
        this.manualInitId(FinalTechItemStacks.PHONY.getItemId(), StringNumberUtil.add(this.getOrCalItemInputValue(FinalTechItemStacks.SINGULARITY), this.getOrCalItemInputValue(FinalTechItemStacks.SPIROCHETE)), StringNumberUtil.mul(this.getOrCalItemOutputValue(FinalTechItemStacks.SINGULARITY), this.getOrCalItemOutputValue(FinalTechItemStacks.SPIROCHETE)), false);
        this.manualInitId(FinalTechItemStacks.BUG.getItemId(), StringNumberUtil.ZERO, StringNumberUtil.ZERO, true);

        List<SlimefunItem> allSlimefunItems = Slimefun.getRegistry().getAllSlimefunItems();
        for (SlimefunItem slimefunItem : allSlimefunItems) {
            if (!slimefunItem.isDisabled()) {
                try {
                    this.getOrCalItemInputValue(slimefunItem);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.itemInputValueMap.put(slimefunItem.getId(), StringNumberUtil.ZERO);
                }
                try {
                    this.getOrCalItemOutputValue(slimefunItem);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.itemOutputValueMap.put(slimefunItem.getId(), StringNumberUtil.VALUE_INFINITY);
                }
            }
        }

        this.itemInputValueMap.put(SlimefunItems.IRON_DUST.getItemId(), this.baseInputValue);
        this.itemInputValueMap.put(SlimefunItems.GOLD_DUST.getItemId(), this.baseInputValue);
        this.itemInputValueMap.put(SlimefunItems.COPPER_DUST.getItemId(), this.baseInputValue);
        this.itemInputValueMap.put(SlimefunItems.TIN_DUST.getItemId(), this.baseInputValue);
        this.itemInputValueMap.put(SlimefunItems.LEAD_DUST.getItemId(), this.baseInputValue);
        this.itemInputValueMap.put(SlimefunItems.SILVER_DUST.getItemId(), this.baseInputValue);
        this.itemInputValueMap.put(SlimefunItems.ALUMINUM_DUST.getItemId(), this.baseInputValue);
        this.itemInputValueMap.put(SlimefunItems.ZINC_DUST.getItemId(), this.baseInputValue);
        this.itemInputValueMap.put(SlimefunItems.MAGNESIUM_DUST.getItemId(), this.baseInputValue);

        this.itemOutputValueMap.put(SlimefunItems.IRON_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "8"));
        this.itemOutputValueMap.put(SlimefunItems.GOLD_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "8"));
        this.itemOutputValueMap.put(SlimefunItems.COPPER_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "8"));
        this.itemOutputValueMap.put(SlimefunItems.TIN_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "8"));
        this.itemOutputValueMap.put(SlimefunItems.LEAD_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "8"));
        this.itemOutputValueMap.put(SlimefunItems.SILVER_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "8"));
        this.itemOutputValueMap.put(SlimefunItems.ALUMINUM_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "8"));
        this.itemOutputValueMap.put(SlimefunItems.ZINC_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "8"));
        this.itemOutputValueMap.put(SlimefunItems.MAGNESIUM_DUST.getItemId(), StringNumberUtil.mul(this.baseOutputValue, "8"));

        if (valueFile.containPath("input", "result")) {
            for (String key : valueFile.getStringList("input", "result")) {
                this.itemInputValueMap.put(key, valueFile.getString("input", "result", key));
            }
        }
        if (valueFile.containPath("output", "result")) {
            for (String key : valueFile.getStringList("output", "result")) {
                String value = valueFile.getString("output", "result", key);
                this.itemOutputValueMap.put(key, value);
                if (!StringNumberUtil.VALUE_INFINITY.equals(value)) {
                    this.addToOutputMap(key, value);
                } else {
                    this.removeFromOutputMap(key);
                }
            }
        }

        if (valueFile.containPath("output", "remove")) {
            for (String id : valueFile.getStringList("output", "remove")) {
                this.removeFromOutputMap(id);
            }
        }
    }

    public String getOrCalItemInputValue(@Nullable ItemStack item) {
        if (ItemStackUtil.isItemNull(item)) {
            return StringNumberUtil.ZERO;
        }
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if (slimefunItem == null) {
            return StringNumberUtil.mul(this.baseInputValue, String.valueOf(item.getAmount()));
        }
        return this.getOrCalItemInputValue(slimefunItem);
    }

    public String getOrCalItemInputValue(@Nonnull String id) {
        if (this.itemInputValueMap.containsKey(id)) {
            return this.itemInputValueMap.get(id);
        }
        SlimefunItem slimefunItem = SlimefunItem.getById(id);
        if (slimefunItem == null) {
            return this.baseInputValue;
        }
        return this.getOrCalItemInputValue(slimefunItem);
    }

    public String getOrCalItemInputValue(@Nonnull SlimefunItem slimefunItem) {
        String id = slimefunItem.getId();
        if (this.itemInputValueMap.containsKey(id)) {
            return this.itemInputValueMap.get(id);
        } else if (slimefunItem.isDisabled()) {
            this.itemInputValueMap.put(id, StringNumberUtil.ZERO);
            return StringNumberUtil.ZERO;
        }
        this.itemInputValueMap.put(id, StringNumberUtil.ZERO);
        String value = StringNumberUtil.ZERO;

        List<ItemAmountWrapper> recipeList = ItemStackUtil.calItemListWithAmount(slimefunItem.getRecipe());
        for (ItemAmountWrapper recipeItem : recipeList) {
            ItemStack item = recipeItem.getItemStack();
            item.setAmount(1);
            value = StringNumberUtil.add(value, this.getOrCalItemInputValue(item));
        }

        value = StringNumberUtil.add(value, String.valueOf(recipeList.size()));
        value = StringNumberUtil.add(value, this.baseInputValue);
        this.itemInputValueMap.put(id, value);
        return value;
    }

    public String getOrCalItemOutputValue(@Nullable ItemStack item) {
        if (ItemStackUtil.isItemNull(item)) {
            return StringNumberUtil.ZERO;
        }
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if (slimefunItem == null) {
            return StringNumberUtil.mul(this.baseOutputValue, String.valueOf(item.getAmount()));
        }
        return this.getOrCalItemOutputValue(slimefunItem);
    }

    public String getOrCalItemOutputValue(@Nonnull String id) {
        if (this.itemOutputValueMap.containsKey(id)) {
            return this.itemOutputValueMap.get(id);
        }
        SlimefunItem slimefunItem = SlimefunItem.getById(id);
        if (slimefunItem == null) {
            return this.baseOutputValue;
        }
        return this.getOrCalItemOutputValue(slimefunItem);
    }

    public String getOrCalItemOutputValue(@Nonnull SlimefunItem slimefunItem) {
        String id = slimefunItem.getId();
        if (this.itemOutputValueMap.containsKey(id)) {
            return this.itemOutputValueMap.get(id);
        } else if (slimefunItem.isDisabled()) {
            this.itemOutputValueMap.put(id, StringNumberUtil.VALUE_INFINITY);
            return StringNumberUtil.VALUE_INFINITY;
        }
        this.itemOutputValueMap.put(id, StringNumberUtil.VALUE_INFINITY);
        String value = StringNumberUtil.ZERO;

        List<ItemAmountWrapper> recipeList = ItemStackUtil.calItemListWithAmount(slimefunItem.getRecipe());
        for (ItemAmountWrapper recipeItem : recipeList) {
            int amount = recipeItem.getAmount();
            SlimefunItem recipeSlimefunItem = SlimefunItem.getByItem(recipeItem.getItemStack());
            if (recipeSlimefunItem != null) {
                amount /= recipeSlimefunItem.getRecipeOutput().getAmount();
                if (amount == 0) {
                    amount = 1;
                }
            }
            ItemStack item = recipeItem.getItemStack();
            item.setAmount(1);
            value = StringNumberUtil.add(value, StringNumberUtil.mul(this.getOrCalItemOutputValue(item), String.valueOf(amount)));
        }
        value = StringNumberUtil.mul(value, String.valueOf(recipeList.size()));

        RecipeType recipeType = slimefunItem.getRecipeType();
        if (RecipeType.NULL.equals(recipeType) || recipeList.isEmpty() || StringNumberUtil.ZERO.equals(value)) {
            value = StringNumberUtil.VALUE_INFINITY;
        } else if (slimefunItem instanceof MultiBlockMachine || RecipeType.MULTIBLOCK.equals(recipeType)) {
            value = StringNumberUtil.add(value, this.baseOutputValue);
        } else if (slimefunItem.equals(recipeType.getMachine())) {
            value = StringNumberUtil.add(value, value);
        } else if (recipeType.getMachine() != null) {
            value = StringNumberUtil.add(value, this.getOrCalItemOutputValue(recipeType.getMachine()));
        } else {
            value = StringNumberUtil.add(value, this.baseOutputValue);
        }

        this.itemOutputValueMap.put(id, value);
        if (!StringNumberUtil.VALUE_INFINITY.equals(value) && !(slimefunItem instanceof MultiBlockMachine) && !RecipeType.MULTIBLOCK.equals(recipeType)) {
            this.addToOutputMap(id, value);
        }
        return value;
    }

    private void manualInitId(@Nonnull String id, @Nonnull String inputValue, @Nonnull String outputValue, boolean canOutput) {
        this.itemInputValueMap.put(id, inputValue);
        this.itemOutputValueMap.put(id, outputValue);
        if (canOutput && !outputValue.contains(StringNumberUtil.VALUE_INFINITY)) {
            this.addToOutputMap(id, outputValue);
        }
    }

    private void addToOutputMap(@Nonnull String id, @Nonnull String value) {
        List<String> list = this.valueItemListOutputMap.get(value);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(id);
        this.valueItemListOutputMap.put(value, list);
    }

    private void removeFromOutputMap(@Nullable String id) {
        if (this.itemOutputValueMap.containsKey(id)) {
            String value = this.itemOutputValueMap.get(id);
            if (this.valueItemListOutputMap.containsKey(value)) {
                List<String> idList = this.valueItemListOutputMap.get(value);
                idList.remove(id);
            }
        }
    }

    @Nonnull
    public Map<String, List<String>> getValueItemListOutputMap() {
        return this.valueItemListOutputMap;
    }
}
