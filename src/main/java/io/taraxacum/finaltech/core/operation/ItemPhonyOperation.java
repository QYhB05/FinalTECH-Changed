package io.taraxacum.finaltech.core.operation;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class ItemPhonyOperation implements ItemSerializationConstructorOperation {
    private final int itemTypeDifficulty;
    private final int itemAmountDifficulty;
    private final ItemStack showItem;
    private final List<ItemStackWrapper> itemTypeList = new ArrayList<>(ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT);
    private int itemTypeCount;
    private int itemAmountCount;

    protected ItemPhonyOperation(@Nonnull ItemStack item) {
        this.itemTypeCount = 1;
        this.itemAmountCount = item.getAmount();
        this.itemTypeDifficulty = ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT;
        this.itemAmountDifficulty = ConstantTableUtil.ITEM_SINGULARITY_AMOUNT;
        this.showItem = new CustomItemStack(FinalTechItems.ITEM_PHONY.getItem().getType(), FinalTechChanged.getLanguageString("items", FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getId(), "phony", "name"));
        this.itemTypeList.add(ItemStackWrapper.wrap(item));
    }

    @Override
    public int getType() {
        return ItemSerializationConstructorOperation.ITEM_PHONY;
    }

    @Nonnull
    @Override
    public ItemStack getShowItem() {
        return this.showItem;
    }

    @Override
    public void updateShowItem() {
        ItemStackUtil.setLore(this.showItem, FinalTechChanged.getLanguageManager().replaceStringArray(FinalTechChanged.getLanguageStringArray("items", FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getId(), "phony", "lore"),
                String.valueOf(this.itemAmountCount),
                String.valueOf(this.itemAmountDifficulty),
                String.valueOf(this.itemTypeCount),
                String.valueOf(this.itemTypeDifficulty)));
    }

    @Override
    public int addItem(@Nullable ItemStack itemStack) {
        if (!FinalTechItems.COPY_CARD.verifyItem(itemStack)) {
            return 0;
        }

        if (this.itemTypeCount <= this.itemTypeDifficulty) {
            boolean newType = true;
            ItemStackWrapper itemWrapper = ItemStackWrapper.wrap(itemStack);
            for (ItemStackWrapper itemTypeWrapper : this.itemTypeList) {
                if (ItemStackUtil.isItemSimilar(itemWrapper, itemTypeWrapper)) {
                    newType = false;
                    break;
                }
            }
            if (newType) {
                this.itemTypeCount++;
                this.itemTypeList.add(itemWrapper);
            }
        }
        int amount = Math.min(itemStack.getAmount(), this.itemAmountDifficulty - this.itemAmountCount);
        this.itemAmountCount += amount;

        itemStack.setAmount(itemStack.getAmount() - amount);

        return amount;
    }

    @Override
    public boolean isFinished() {
        return this.itemAmountCount >= this.itemAmountDifficulty || this.itemTypeCount >= this.itemTypeDifficulty;
    }

    @Nonnull
    @Override
    public ItemStack getResult() {
        if (this.itemAmountCount >= this.itemAmountDifficulty && this.itemTypeCount >= this.itemTypeDifficulty) {
            return FinalTechItems.ITEM_PHONY.getValidItem();
        }
        if (this.itemAmountCount >= this.itemAmountDifficulty) {
            return FinalTechItems.SINGULARITY.getValidItem();
        }
        if (this.itemTypeCount >= this.itemTypeDifficulty) {
            return FinalTechItems.SPIROCHETE.getValidItem();
        }
        return ItemStackUtil.AIR;
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
