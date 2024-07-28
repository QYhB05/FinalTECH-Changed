package io.taraxacum.finaltech.core.item.machine.template.extraction;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.ExtractionMachineMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.plugin.dto.AdvancedMachineRecipe;
import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.MachineRecipeFactory;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
 
public abstract class AbstractExtractionMachine extends AbstractMachine implements RecipeItem {
    public AbstractExtractionMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return new ExtractionMachineMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        try {
            BlockMenu blockMenu = BlockStorage.getInventory(block);
            int itemSlot = this.getInputSlot()[FinalTechChanged.getRandom().nextInt(this.getInputSlot().length)];
            ItemStack item = blockMenu.getItemInSlot(itemSlot);

            if (ItemStackUtil.isItemNull(item)) {
                return;
            }
            ItemWrapper itemWrapper = new ItemWrapper(item);
            int matchAmount = 0;
            ItemAmountWrapper[] outputItems = null;
            List<AdvancedMachineRecipe> advancedRecipeList = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getId());
            for (AdvancedMachineRecipe advancedMachineRecipe : advancedRecipeList) {
                if (itemWrapper.getItemStack().getAmount() >= advancedMachineRecipe.getInput()[0].getAmount() && ItemStackUtil.isItemSimilar(advancedMachineRecipe.getInput()[0], itemWrapper)) {
                    outputItems = advancedMachineRecipe.getOutput();
                    matchAmount = itemWrapper.getItemStack().getAmount() / advancedMachineRecipe.getInput()[0].getAmount();
                    break;
                }
            }

            if (outputItems != null) {
                matchAmount = Math.min(matchAmount, MachineUtil.calMaxMatch(blockMenu.toInventory(), this.getOutputSlot(), outputItems));
                if (matchAmount > 0) {
                    for (ItemStack outputItem : ItemStackUtil.calEnlargeItemArray(outputItems, matchAmount)) {
                        blockMenu.pushItem(outputItem, this.getOutputSlot());

                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerRecipe(@Nonnull MachineRecipe recipe) {
        if (recipe.getInput().length != 1) {
            throw new IllegalArgumentException("Register recipe for " + this.getItemName() + " has occurred a error: " + " input item type should be only just one");
        }

        if (recipe.getInput()[0].getAmount() > 1) {
            this.getAddon().getJavaPlugin().getServer().getLogger().info("Register recipe for " + this.getItemName() + " has occurred a error: " + " input item amount should be one");
            recipe.getInput()[0] = new CustomItemStack(recipe.getInput()[0], 1);
        }

        RecipeItem.super.registerRecipe(recipe);
    }
}
