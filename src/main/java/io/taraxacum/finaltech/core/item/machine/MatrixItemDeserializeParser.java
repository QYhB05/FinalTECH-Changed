package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.ItemDeserializeParserMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.StringItemUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

/**
 * This is a slimefun machine
 * it will be used in gameplay
 * It's not a function class!
 *
 * @author Final_ROOT
 * @since 1.0
 */
public class MatrixItemDeserializeParser extends AbstractMachine implements RecipeItem {
    public MatrixItemDeserializeParser(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new ItemDeserializeParserMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Inventory inventory = blockMenu.toInventory();
        if (MachineUtil.slotCount(inventory, this.getOutputSlot()) == this.getOutputSlot().length) {
            return;
        }
        for (int slot : this.getInputSlot()) {
            ItemStack itemStack = blockMenu.getItemInSlot(slot);
            if (FinalTechItems.COPY_CARD.verifyItem(itemStack)) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                ItemStack stringItem = StringItemUtil.parseItemInCard(itemMeta);
                if (!ItemStackUtil.isItemNull(stringItem)) {
                    int amount = itemStack.getAmount();
                    String amountInCardStr = StringItemUtil.parseAmountInCard(itemMeta);
                    if (StringNumberUtil.compare(amountInCardStr, "3456") >= 0) {
                        amount = 3456;
                    } else {
                        amount *= Integer.parseInt(amountInCardStr);
                    }
                    if (amount <= 0) {
                        return;
                    }
                    int count;
                    for (int outputSlot : this.getOutputSlot()) {
                        if (!ItemStackUtil.isItemNull(blockMenu.getItemInSlot(outputSlot))) {
                            continue;
                        }
                        count = Math.min(amount, stringItem.getMaxStackSize());
                        stringItem.setAmount(count);
                        blockMenu.pushItem(stringItem, outputSlot);
                        amount -= count;
                        if (amount == 0) {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0));
    }
}
