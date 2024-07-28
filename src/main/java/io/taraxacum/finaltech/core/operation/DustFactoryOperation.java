package io.taraxacum.finaltech.core.operation;

import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class DustFactoryOperation implements MachineOperation {
    private final int amountDifficulty;
    private final int typeDifficulty;
    private final ItemWrapper[] matchItemList;
    private int amountCount = 0;
    private int typeCount = 0;

    public DustFactoryOperation(int amountDifficulty, int typeDifficulty) {
        this.amountDifficulty = amountDifficulty;
        this.typeDifficulty = typeDifficulty;
        this.matchItemList = new ItemWrapper[typeDifficulty + 1];
    }

    public void addItem(@Nullable ItemStack item) {
        if (ItemStackUtil.isItemNull(item)) {
            return;
        }

        this.amountCount += item.getAmount();
        if (this.amountCount > this.amountDifficulty + 1) {
            this.amountCount = this.amountDifficulty + 1;
        }

        if (this.typeCount <= this.typeDifficulty) {
            boolean newItem = true;
            for (int i = 0; i < this.typeCount; i++) {
                ItemWrapper existedItem = this.matchItemList[i];
                if (ItemStackUtil.isItemSimilar(item, existedItem)) {
                    newItem = false;
                    break;
                }
            }
            if (newItem) {
                this.matchItemList[this.typeCount++] = new ItemWrapper(ItemStackUtil.cloneItem(item));
            }
        }
    }

    public int getAmountCount() {
        return this.amountCount;
    }

    public int getTypeCount() {
        return this.typeCount;
    }

    public int getAmountDifficulty() {
        return this.amountDifficulty;
    }

    public int getTypeDifficulty() {
        return this.typeDifficulty;
    }

    @Override
    public boolean isFinished() {
        return this.amountCount >= this.amountDifficulty && this.typeCount >= this.typeDifficulty;
    }

    @Nullable
    public ItemStack getResult() {
        if (this.amountCount == this.amountDifficulty && this.typeCount == this.typeDifficulty) {
            return FinalTechItems.ORDERED_DUST.getValidItem();
        } else if (this.isFinished()) {
            return FinalTechItems.UNORDERED_DUST.getValidItem();
        } else {
            return null;
        }
    }

    @Deprecated
    @Override
    public void addProgress(int i) {

    }

    @Deprecated
    @Override
    public int getProgress() {
        return 0;
    }

    @Deprecated
    @Override
    public int getTotalTicks() {
        return 0;
    }
}
