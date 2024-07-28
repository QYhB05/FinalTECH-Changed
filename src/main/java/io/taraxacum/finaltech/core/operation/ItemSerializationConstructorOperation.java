package io.taraxacum.finaltech.core.operation;

import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import io.taraxacum.finaltech.setup.FinalTechItems;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
 
public interface ItemSerializationConstructorOperation extends MachineOperation {
    int COPY_CARD = 1;
    int ITEM_PHONY = 2;
    int ERROR_ITEM = -1;

    static int getType(@Nonnull ItemStack item) {
        if (FinalTechItems.COPY_CARD.verifyItem(item)) {
            return ITEM_PHONY;
        }
        if (FinalTechItems.COPY_CARD.isTargetItem(item)) {
            return COPY_CARD;
        }
        return ERROR_ITEM;
    }

    @Nullable
    static ItemSerializationConstructorOperation newInstance(@Nonnull ItemStack item) {
        int type = ItemSerializationConstructorOperation.getType(item);
        if (type == COPY_CARD) {
            ItemStack itemStack = item.clone();
            item.setAmount(0);
            return new ItemCopyCardOperation(itemStack);
        } else if (type == ITEM_PHONY) {
            ItemStack itemStack = item.clone();
            item.setAmount(0);
            return new ItemPhonyOperation(itemStack);
        }
        return null;
    }

    int getType();

    @Nonnull
    ItemStack getShowItem();

    void updateShowItem();

    int addItem(@Nullable ItemStack item);

    @Nonnull
    ItemStack getResult();
}
