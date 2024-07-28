package io.taraxacum.libs.slimefun.dto;

import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.plugin.dto.AdvancedMachineRecipe;
import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
public class AdvancedCraft {
    @Nonnull
    private ItemAmountWrapper[] inputItemList;
    @Nonnull
    private ItemAmountWrapper[] outputItemList;
    @Nonnull
    private int[][] consumeSlotList;
    private int matchCount;
    private int offset;

    private AdvancedCraft(@Nonnull ItemAmountWrapper[] inputItemList, @Nonnull ItemAmountWrapper[] outputItemList, @Nonnull int[][] consumeSlotList, int matchCount, int offset) {
        this.inputItemList = inputItemList;
        this.outputItemList = outputItemList;
        this.consumeSlotList = consumeSlotList;
        this.matchCount = matchCount;
        this.offset = offset;
    }

    /**
     * Cal the craft work a machine will do.
     *
     * @param inventory                 the container that contains items and all operation will do here.
     * @param inputSlots                where the items will be consumed to match the {@link MachineRecipe}.
     * @param advancedMachineRecipeList list of {@link AdvancedMachineRecipe} that a machine can work to.
     * @param quantityModule            how many times a machine will work in max.
     * @param offset                    machine-recipe will begin in the given offset.
     * @return
     */
    @Nullable
    public static AdvancedCraft craftAsc(@Nonnull Inventory inventory, int[] inputSlots, @Nonnull List<AdvancedMachineRecipe> advancedMachineRecipeList, int quantityModule, int offset) {
        Map<Integer, ItemWrapper> inputItemSlotMap = MachineUtil.getSlotItemWrapperMap(inventory, inputSlots);
        int[][] consumeSlots;
        Set<Integer> skipSlotSet = new HashSet<>(inputItemSlotMap.size());
        int matchCount;
        int matchAmount;
        for (int i = 0, length = advancedMachineRecipeList.size(); i < length; i++) {
            AdvancedMachineRecipe advancedMachineRecipe = advancedMachineRecipeList.get((i + offset) % length);
            ItemAmountWrapper[] recipeInputItems = advancedMachineRecipe.getInput();
            consumeSlots = new int[recipeInputItems.length][];
            matchCount = quantityModule;

            for (int k = 0; k < recipeInputItems.length; k++) {
                ItemAmountWrapper recipeInputItem = recipeInputItems[k];
                matchAmount = 0;
                int j = 0;
                consumeSlots[k] = new int[inputItemSlotMap.size()];
                for (Map.Entry<Integer, ItemWrapper> inputItemEntry : inputItemSlotMap.entrySet()) {
                    if (skipSlotSet.contains(inputItemEntry.getKey())) {
                        continue;
                    }
                    if (ItemStackUtil.isItemSimilar(recipeInputItem, inputItemEntry.getValue())) {
                        matchAmount += inputItemEntry.getValue().getItemStack().getAmount();
                        skipSlotSet.add(inputItemEntry.getKey());
                        consumeSlots[k][j++] = inputItemEntry.getKey();
                    }
                    if (matchAmount / recipeInputItem.getAmount() >= matchCount) {
                        break;
                    }
                }
                matchCount = Math.min(matchCount, matchAmount / recipeInputItem.getAmount());
                if (matchCount == 0) {
                    break;
                }
                while (j < inputItemSlotMap.size()) {
                    consumeSlots[k][j++] = -1;
                }
            }

            if (matchCount > 0) {
                ItemAmountWrapper[] recipeOutputItemList = advancedMachineRecipe.getOutput();
                return new AdvancedCraft(advancedMachineRecipe.getInput(), recipeOutputItemList, consumeSlots, matchCount, (i + offset) % length);
            }
            skipSlotSet.clear();
        }
        return null;
    }

    @Nullable
    public static AdvancedCraft craftDesc(@Nonnull Inventory inventory, int[] inputSlots, @Nonnull List<AdvancedMachineRecipe> advancedMachineRecipeList, int quantityModule, int offset) {
        Map<Integer, ItemWrapper> inputItemSlotMap = MachineUtil.getSlotItemWrapperMap(inventory, inputSlots);
        int[][] consumeSlots;
        Set<Integer> skipSlotSet = new HashSet<>(inputItemSlotMap.size());
        int matchCount;
        int matchAmount;
        for (int i = 0, length = advancedMachineRecipeList.size(); i < length; i++) {
            AdvancedMachineRecipe advancedMachineRecipe = advancedMachineRecipeList.get((offset - i + length + length) % length);
            ItemAmountWrapper[] recipeInputItems = advancedMachineRecipe.getInput();
            consumeSlots = new int[recipeInputItems.length][];
            matchCount = quantityModule;

            for (int k = 0; k < recipeInputItems.length; k++) {
                ItemAmountWrapper recipeInputItem = recipeInputItems[k];
                matchAmount = 0;
                int j = 0;
                consumeSlots[k] = new int[inputItemSlotMap.size()];
                for (Map.Entry<Integer, ItemWrapper> inputItemEntry : inputItemSlotMap.entrySet()) {
                    if (skipSlotSet.contains(inputItemEntry.getKey())) {
                        continue;
                    }
                    if (ItemStackUtil.isItemSimilar(recipeInputItem, inputItemEntry.getValue())) {
                        matchAmount += inputItemEntry.getValue().getItemStack().getAmount();
                        skipSlotSet.add(inputItemEntry.getKey());
                        consumeSlots[k][j++] = inputItemEntry.getKey();
                    }
                    if (matchAmount / recipeInputItem.getAmount() >= matchCount) {
                        break;
                    }
                }
                matchCount = Math.min(matchCount, matchAmount / recipeInputItem.getAmount());
                if (matchCount == 0) {
                    break;
                }
                while (j < inputItemSlotMap.size()) {
                    consumeSlots[k][j++] = -1;
                }
            }

            if (matchCount > 0) {
                ItemAmountWrapper[] recipeOutputItemList = advancedMachineRecipe.getOutput();
                return new AdvancedCraft(advancedMachineRecipe.getInput(), recipeOutputItemList, consumeSlots, matchCount, (offset - i + length + length) % length);
            }
            skipSlotSet.clear();
        }
        return null;
    }

    @Nonnull
    public ItemAmountWrapper[] getInputItemList() {
        return inputItemList;
    }

    public void setInputItemList(@Nonnull ItemAmountWrapper[] inputItemList) {
        this.inputItemList = inputItemList;
    }

    @Nonnull
    public ItemAmountWrapper[] getOutputItemList() {
        return outputItemList;
    }

    public void setOutputItemList(@Nonnull ItemAmountWrapper[] outputItemList) {
        this.outputItemList = outputItemList;
    }

    @Nonnull
    public int[][] getConsumeSlotList() {
        return consumeSlotList;
    }

    public void setConsumeSlotList(@Nonnull int[][] consumeSlotList) {
        this.consumeSlotList = consumeSlotList;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Consume item after we know how a machine should work.
     */
    public void consumeItem(@Nonnull Inventory inventory) {
        for (int i = 0; i < this.inputItemList.length; i++) {
            int consumeItemAmount = this.inputItemList[i].getAmount() * this.matchCount;
            for (int slot : this.consumeSlotList[i]) {
                if (slot != -1) {
                    ItemStack item = inventory.getItem(slot);
                    int itemConsumeAmount = Math.min(consumeItemAmount, item.getAmount());
                    item.setAmount(item.getAmount() - itemConsumeAmount);
                    consumeItemAmount -= itemConsumeAmount;
                    if (consumeItemAmount == 0) {
                        break;
                    }
                }
            }
        }
    }

    @Nonnull
    public MachineRecipe calMachineRecipe(int ticks) {
        if (this.matchCount > 0) {
            return new MachineRecipe(ticks, ItemStackUtil.calEnlargeItemArray(this.inputItemList, this.matchCount), ItemStackUtil.calEnlargeItemArray(this.outputItemList, this.matchCount));
        } else {
            return new MachineRecipe(ticks, new ItemStack[0], new ItemStack[0]);
        }
    }
}
