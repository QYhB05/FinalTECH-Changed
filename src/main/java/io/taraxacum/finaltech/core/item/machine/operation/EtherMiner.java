package io.taraxacum.finaltech.core.item.machine.operation;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.MathUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.EtherMinerMenu;
import io.taraxacum.finaltech.core.operation.EtherMinerOperation;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.OptionalInt;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public class EtherMiner extends AbstractOperationMachine implements RecipeItem, MenuUpdater {
    // time = baseTime / logN(supplies * mul + 1)
    private final double baseTime = ConfigUtil.getOrDefaultItemSetting(60, this, "time");
    private final double logN = ConfigUtil.getOrDefaultItemSetting(100, this, "logN");
    private final double mul = ConfigUtil.getOrDefaultItemSetting(0.01, this, "mul");
    private final double add = ConfigUtil.getOrDefaultItemSetting(0.08, this, "add");
    private final double random = ConfigUtil.getOrDefaultItemSetting(0.1, this, "random");

    public EtherMiner(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> drops) {
                Location location = blockBreakEvent.getBlock().getLocation();
                BlockMenu blockMenu = BlockStorage.getInventory(location);
                blockMenu.dropItems(location, EtherMiner.this.getInputSlot());
                blockMenu.dropItems(location, EtherMiner.this.getOutputSlot());

                EtherMiner.this.getMachineProcessor().endOperation(location);
            }
        };
    }

    @Nullable
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new EtherMinerMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(location);

        OptionalInt supplies = Slimefun.getGPSNetwork().getResourceManager().getSupplies(FinalTechItems.ETHER, block.getWorld(), block.getX() >> 4, block.getZ() >> 4);
        int etherAmount = supplies.isPresent() ? supplies.getAsInt() : -1;

        EtherMinerOperation etherMinerOperation = (EtherMinerOperation) this.getMachineProcessor().getOperation(location);
        if (etherMinerOperation != null) {
            etherMinerOperation.addProgress(1);
            if (etherMinerOperation.isFinished()) {
                ItemStack outputItemStack = FinalTechItems.ETHER.getValidItem();
                if (MachineUtil.calMaxMatch(blockMenu.toInventory(), this.getOutputSlot(), outputItemStack) > 0) {
                    this.getMachineProcessor().endOperation(location);
                    if (etherMinerOperation.getEtherAmount() == etherAmount--) {
                        blockMenu.pushItem(outputItemStack, this.getOutputSlot());
                        Slimefun.getGPSNetwork().getResourceManager().setSupplies(FinalTechItems.ETHER, block.getWorld(), block.getX() >> 4, block.getZ() >> 4, Math.max(0, etherAmount));
                    }
                    etherMinerOperation = null;
                }
            }
        } else if (etherAmount > 0) {
            ItemStack unorderedDustItemStack = null;
            for (int slot : this.getInputSlot()) {
                ItemStack itemStack = blockMenu.getItemInSlot(slot);
                if (FinalTechItems.UNORDERED_DUST.verifyItem(itemStack)) {
                    unorderedDustItemStack = itemStack;
                    break;
                }
            }
            if (unorderedDustItemStack != null) {
                etherMinerOperation = new EtherMinerOperation((int) (this.baseTime / MathUtil.getLog(this.logN, etherAmount * this.mul + 1 + this.add) * (1 + FinalTechChanged.getRandom().nextDouble(this.random))), etherAmount);
                boolean startOperation = this.getMachineProcessor().startOperation(location, etherMinerOperation);
                if (startOperation) {
                    unorderedDustItemStack.setAmount(unorderedDustItemStack.getAmount() - 1);
                } else {
                    etherMinerOperation = null;
                }
            }
        } else if (etherAmount == 0) {
            etherAmount = FinalTechItems.ETHER.getDefaultSupply(block.getWorld().getEnvironment(), block.getBiome()) + FinalTechChanged.getRandom().nextInt(1 + FinalTechItems.ETHER.getMaxDeviation());
            Slimefun.getGPSNetwork().getResourceManager().setSupplies(FinalTechItems.ETHER, block.getWorld(), block.getX() >> 4, block.getZ() >> 4, Math.max(0, etherAmount));
        }

        if (blockMenu.hasViewer()) {
            int progress = 0;
            int totalTicks = 0;
            if (etherMinerOperation != null) {
                progress = etherMinerOperation.getProgress();
                totalTicks = etherMinerOperation.getTotalTicks();
            }
            this.updateMenu(blockMenu, EtherMinerMenu.STATUS_SLOT, this,
                    String.valueOf(progress),
                    String.valueOf(totalTicks),
                    String.valueOf(etherAmount));
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipeWithBorder(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.baseTime));

        this.registerRecipe(FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.ETHER);
    }
}
