package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.common.util.MathUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.DustGeneratorMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class DustGenerator extends AbstractMachine implements RecipeItem, MenuUpdater, EnergyNetProvider {
    private final String keyCount = "count";
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(Integer.MAX_VALUE / 4, this, "capacity");
    // default = 144115188344291328
    // long.max= 9223372036854775808
    private final long countLimit = ((long) this.capacity);

    public DustGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), keyCount, StringNumberUtil.ZERO);
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
        return new DustGeneratorMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Location location = block.getLocation();

        long count = Long.parseLong(config.getString(keyCount)) % Integer.MAX_VALUE;
        boolean work = false;
        for (int slot : this.getInputSlot()) {
            ItemStack itemStack = blockMenu.getItemInSlot(slot);
            if (FinalTechItems.UNORDERED_DUST.verifyItem(itemStack)) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                count = Math.min(++count, this.countLimit);
                work = true;
                break;
            } else if (FinalTechItems.ITEM_PHONY.verifyItem(itemStack)) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                count *= 2;
                count = Math.min(count, this.countLimit);
                work = true;
                break;
            }
        }
        if (!work) {
            count /= 2;
        }
        int charge = (int) count;

        BlockStorage.addBlockInfo(location, keyCount, String.valueOf(count));
        if (count > 0) {
            this.addCharge(location, charge);
        }

        if (blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, DustGeneratorMenu.STATUS_SLOT, this,
                    String.valueOf(count),
                    String.valueOf(charge),
                    String.valueOf(this.getCharge(location)));
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.GENERATOR;
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location location, @Nonnull Config config) {
        int charge = this.getCharge(location);
        this.setCharge(location, 0);
        return charge;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.capacity));
    }
}
