package io.taraxacum.finaltech.core.item.machine.manual;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.finaltech.core.menu.manual.CardOperationPortMenu;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class CardOperationTable extends AbstractManualMachine implements RecipeItem {
    public CardOperationTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (blockMenu != null && blockMenu.hasViewer()) {
            this.getMachineMenu().updateInventory(blockMenu.toInventory(), block.getLocation());
        }
    }

    @Nonnull
    @Override
    protected AbstractManualMachineMenu newMachineMenu() {
        return new CardOperationPortMenu(this);
    }

    @Override
    public void registerDefaultRecipes() {
        for (CardOperationPortMenu.Craft craft : CardOperationPortMenu.CRAFT_LIST) {
            if (craft.isEnabled()) {
                String outputItemId = craft.getInfoOutput();
                SlimefunItem slimefunItem = SlimefunItem.getById(outputItemId);
                if (slimefunItem != null) {
                    this.registerDescriptiveRecipe(slimefunItem.getItem(), craft.getInfoName(), craft.getInfoLore());
                } else {
                    this.registerDescriptiveRecipe(craft.getInfoName(), craft.getInfoLore());
                }
            }
        }
    }
}
