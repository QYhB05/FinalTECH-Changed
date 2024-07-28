package io.taraxacum.finaltech.core.item.machine.template.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.core.helper.Icon;
import io.taraxacum.finaltech.core.helper.MachineRecipeLock;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.limit.lock.AbstractLockMachineMenu;
import io.taraxacum.finaltech.core.menu.limit.lock.AdvancedMachineMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.plugin.dto.AdvancedMachineRecipe;
import io.taraxacum.libs.slimefun.dto.AdvancedCraft;
import io.taraxacum.libs.slimefun.dto.MachineRecipeFactory;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public abstract class AbstractAdvanceMachine extends AbstractMachine implements RecipeItem {
    private final String offsetKey = "offset";

    protected AbstractAdvanceMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return MachineUtil.simpleBlockBreakerHandler(this, AdvancedMachineMenu.MODULE_SLOT);
    }

    @Nonnull
    @Override
    protected final AbstractMachineMenu setMachineMenu() {
        return new AdvancedMachineMenu(this);
    }

    @Override
    protected final void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        int offset = config.contains(this.offsetKey) ? Integer.parseInt(config.getString(offsetKey)) : 0;
        int recipeLock = config.contains(MachineRecipeLock.KEY) ? Integer.parseInt(config.getString(MachineRecipeLock.KEY)) : -2;
        MachineUtil.stockSlots(blockMenu.toInventory(), this.getInputSlot());
        MachineRecipe machineRecipe = this.matchRecipe(blockMenu, offset, recipeLock);
        if (machineRecipe != null) {
            ItemStack[] outputItems = machineRecipe.getOutput();
            for (ItemStack output : outputItems) {
                blockMenu.pushItem(output, this.getOutputSlot());
            }
            MachineUtil.stockSlots(blockMenu.toInventory(), this.getOutputSlot());
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    protected MachineRecipe matchRecipe(@Nonnull BlockMenu blockMenu, int offset, int recipeLock) {
        int quantityModule = Icon.updateQuantityModule(blockMenu, AdvancedMachineMenu.MODULE_SLOT, AdvancedMachineMenu.STATUS_SLOT);

        List<AdvancedMachineRecipe> advancedMachineRecipeList = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getId());
        if (recipeLock >= 0) {
            List<AdvancedMachineRecipe> finalAdvancedMachineRecipeList = advancedMachineRecipeList;
            advancedMachineRecipeList = new ArrayList<>(1);
            recipeLock = recipeLock % finalAdvancedMachineRecipeList.size();
            advancedMachineRecipeList.add(finalAdvancedMachineRecipeList.get(recipeLock));
        }

        AdvancedCraft craft = AdvancedCraft.craftAsc(blockMenu.toInventory(), this.getInputSlot(), advancedMachineRecipeList, quantityModule, offset);
        if (craft != null) {
            craft.setMatchCount(Math.min(craft.getMatchCount(), MachineUtil.calMaxMatch(blockMenu.toInventory(), this.getOutputSlot(), craft.getOutputItemList())));
            if (craft.getMatchCount() > 0) {
                craft.consumeItem(blockMenu.toInventory());
                if (recipeLock == Integer.parseInt(MachineRecipeLock.VALUE_UNLOCK)) {
                    ItemStack item = blockMenu.getItemInSlot(AbstractLockMachineMenu.RECIPE_LOCK_SLOT);
                    if (blockMenu.hasViewer()) {
                        MachineRecipeLock.HELPER.setIcon(item, String.valueOf(craft.getOffset()), this);
                    }
                    BlockStorage.addBlockInfo(blockMenu.getLocation(), MachineRecipeLock.KEY, String.valueOf(craft.getOffset()));
                } else if (recipeLock == Integer.parseInt(MachineRecipeLock.VALUE_LOCK_OFF)) {
                    BlockStorage.addBlockInfo(blockMenu.getLocation(), this.offsetKey, String.valueOf(craft.getOffset()));
                }
                return craft.calMachineRecipe(this.getMachineRecipes().get(offset).getTicks());
            }
        }
        BlockStorage.addBlockInfo(blockMenu.getLocation(), this.offsetKey, null);
        return null;
    }
}
