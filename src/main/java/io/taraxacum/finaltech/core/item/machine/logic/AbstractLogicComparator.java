package io.taraxacum.finaltech.core.item.machine.logic;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.LogicComparatorMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.1
 */
public abstract class AbstractLogicComparator extends AbstractMachine {
    public AbstractLogicComparator(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
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
        return new LogicComparatorMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);

        if (MachineUtil.slotCount(blockMenu.toInventory(), this.getOutputSlot()) == this.getOutputSlot().length) {
            return;
        }

        ItemStack item1 = blockMenu.getItemInSlot(this.getInputSlot()[0]);
        ItemStack item2 = blockMenu.getItemInSlot(this.getInputSlot()[1]);

        if (this.preCompare(item1, item2)) {
            if (this.compare(item1, item2)) {
                blockMenu.pushItem(this.resultTrue(), this.getOutputSlot());
            } else {
                blockMenu.pushItem(this.resultFalse(), this.getOutputSlot());
            }
            for (int slot : this.getInputSlot()) {
                blockMenu.replaceExistingItem(slot, ItemStackUtil.AIR);
            }
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    protected abstract boolean preCompare(@Nullable ItemStack item1, @Nullable ItemStack item2);

    protected abstract boolean compare(@Nullable ItemStack item1, @Nullable ItemStack item2);

    @Nonnull
    protected abstract ItemStack resultTrue();

    @Nonnull
    protected abstract ItemStack resultFalse();
}
