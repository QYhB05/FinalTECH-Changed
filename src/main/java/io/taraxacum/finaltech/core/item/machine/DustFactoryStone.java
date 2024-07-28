package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.OrderedDustFactoryStoneMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class DustFactoryStone extends AbstractMachine implements RecipeItem {
    private final double SLEEP = ConfigUtil.getOrDefaultItemSetting(1, this, "sleep");

    public DustFactoryStone(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                Block block = e.getBlock();
                Location location = block.getLocation();
                Config config = BlockStorage.getLocationInfo(location);
                BlockTickerUtil.setSleep(config, String.valueOf(DustFactoryStone.this.SLEEP * DustFactoryStone.this.SLEEP));
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new OrderedDustFactoryStoneMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {

        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (MachineUtil.slotCount(blockMenu.toInventory(), this.getInputSlot()) != this.getInputSlot().length) {
            return;
        }

        Set<Integer> amountList = new HashSet<>(this.getInputSlot().length);
        ItemWrapper firstItem = new ItemWrapper(blockMenu.getItemInSlot(this.getInputSlot()[0]));
        boolean allSameItem = true;

        for (int slot : this.getInputSlot()) {
            ItemStack itemStack = blockMenu.getItemInSlot(slot);
            amountList.add(itemStack.getAmount());
            if (allSameItem && !ItemStackUtil.isItemSimilar(firstItem, itemStack)) {
                allSameItem = false;
            }
        }

        for (int slot : this.getInputSlot()) {
            blockMenu.replaceExistingItem(slot, ItemStackUtil.AIR);
        }

        if (amountList.size() == this.getInputSlot().length && allSameItem) {
            blockMenu.pushItem(FinalTechItems.ORDERED_DUST.getValidItem(), JavaUtil.shuffle(this.getOutputSlot()));
        } else if (Math.random() < (double) (amountList.size()) / this.getInputSlot().length) {
            blockMenu.pushItem(FinalTechItems.UNORDERED_DUST.getValidItem(), JavaUtil.shuffle(this.getOutputSlot()));
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.format("%.2f", 100.0 / this.getInputSlot().length));
    }
}
