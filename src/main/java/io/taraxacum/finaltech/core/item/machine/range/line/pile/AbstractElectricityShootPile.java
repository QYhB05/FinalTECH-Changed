package io.taraxacum.finaltech.core.item.machine.range.line.pile;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.LocationMachine;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.item.machine.range.AbstractRangeMachine;
import io.taraxacum.finaltech.core.item.machine.range.line.AbstractLineMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public abstract class AbstractElectricityShootPile extends AbstractLineMachine implements RecipeItem, MenuUpdater, LocationMachine {
    protected final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));

    public AbstractElectricityShootPile(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return new StatusMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Directional directional) {
            Runnable runnable = () -> {
                int count = 0;
                Summary summary = new Summary();
                LocationInfo locationInfo = LocationInfo.get(block.getRelative(directional.getFacing().getOppositeFace()).getLocation());
                if (locationInfo != null && !this.notAllowedId.contains(locationInfo.getId()) && locationInfo.getSlimefunItem() instanceof EnergyNetComponent energyNetComponent && JavaUtil.matchOnce(energyNetComponent.getEnergyComponentType(), EnergyNetComponentType.CAPACITOR, EnergyNetComponentType.GENERATOR)) {
                    int capacitorEnergy = Integer.parseInt(EnergyUtil.getCharge(locationInfo.getLocation()));
                    summary.capacitorEnergy = capacitorEnergy;

                    count = this.lineFunction(block, this.getRange(), this.doFunction(summary));

                    if (capacitorEnergy != summary.capacitorEnergy) {
                        EnergyUtil.setCharge(locationInfo.getLocation(), summary.capacitorEnergy);
                    }
                }

                BlockMenu blockMenu = BlockStorage.getInventory(block);
                if (blockMenu.hasViewer()) {
                    this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, this,
                            String.valueOf(count),
                            summary.getEnergyCharge(),
                            String.valueOf(summary.capacitorEnergy));
                }
            };

            BlockTickerUtil.runTask(FinalTechChanged.getLocationRunnableFactory(), FinalTechChanged.isAsyncSlimefunItem(this.getId()), runnable, () -> this.getLocations(block.getLocation()));
        }
    }

    @Override
    protected final boolean isSynchronized() {
        return false;
    }

    protected void updateMenu(@Nonnull BlockMenu blockMenu, int count, @Nonnull Summary summary) {
        ItemStack item = blockMenu.getItemInSlot(StatusMenu.STATUS_SLOT);
        ItemStackUtil.setLore(item, ConfigUtil.getStatusMenuLore(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(count),
                summary.getEnergyCharge()));
    }

    public abstract int getRange();

    @Override
    public Location[] getLocations(@Nonnull Location sourceLocation) {
        Block block = sourceLocation.getBlock();
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Directional directional) {
            BlockFace blockFace = directional.getFacing();
            Location[] locations = new Location[this.getRange() + 1];
            int i = 0;
            locations[i++] = sourceLocation;
            while (i < locations.length) {
                locations[i++] = block.getRelative(blockFace, i - 1).getLocation();
            }
            return locations;
        }
        return new Location[0];
    }

    @Nonnull
    protected abstract AbstractRangeMachine.RangeFunction doFunction(@Nonnull Summary summary);

    protected static class Summary {
        private String energyCharge;
        private int capacitorEnergy;

        Summary() {
            this.energyCharge = StringNumberUtil.ZERO;
        }

        public String getEnergyCharge() {
            return energyCharge;
        }

        public void setEnergyCharge(String energyCharge) {
            this.energyCharge = energyCharge;
        }

        public int getCapacitorEnergy() {
            return capacitorEnergy;
        }

        public void setCapacitorEnergy(int capacitorEnergy) {
            this.capacitorEnergy = capacitorEnergy;
        }
    }
}
