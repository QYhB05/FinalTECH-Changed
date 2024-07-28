package io.taraxacum.finaltech.core.item.machine.range;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.*;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
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
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public class NormalConsumableElectricityShootPile extends AbstractRangeMachine implements RecipeItem, MenuUpdater, PointMachine, LineMachine, LocationMachine {
    private final int range = ConfigUtil.getOrDefaultItemSetting(16, this, "range");
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));

    public NormalConsumableElectricityShootPile(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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

    @Nullable
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusL2Menu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        int digital;
        ItemStack itemStack = blockMenu.getItemInSlot(this.getInputSlot()[0]);
        SlimefunItem digitalSlimefunItem = SlimefunItem.getByItem(itemStack);
        if (digitalSlimefunItem instanceof DigitalItem digitalItem) {
            digital = digitalItem.getDigit();
        } else {
            digital = -1;
        }

        BlockData blockData = block.getBlockData();
        if (blockData instanceof Directional directional && digital != -1) {
            Runnable runnable = () -> {
                AtomicInteger capacitorEnergy = new AtomicInteger(0);
                AtomicInteger transferEnergy = new AtomicInteger(0);

                LocationInfo capacitorLocationInfo = LocationInfo.get(block.getRelative(directional.getFacing().getOppositeFace()).getLocation());
                if (capacitorLocationInfo != null && capacitorLocationInfo.getSlimefunItem() instanceof EnergyNetComponent energyNetComponent && JavaUtil.matchOnce(energyNetComponent.getEnergyComponentType(), EnergyNetComponentType.CAPACITOR, EnergyNetComponentType.GENERATOR)) {
                    capacitorEnergy.set(Integer.parseInt(EnergyUtil.getCharge(capacitorLocationInfo.getLocation())));
                }

                RangeMachine.RangeFunction rangeFunction = location -> {
                    if (capacitorLocationInfo == null || capacitorEnergy.get() <= 0) {
                        return -1;
                    }

                    LocationInfo energyComponentLocationInfo = LocationInfo.get(location);
                    if (energyComponentLocationInfo != null && !notAllowedId.contains(energyComponentLocationInfo.getId())) {
                        if (energyComponentLocationInfo.getSlimefunItem() instanceof EnergyNetComponent energyNetComponent && !EnergyNetComponentType.NONE.equals(energyNetComponent.getEnergyComponentType())) {
                            int componentCapacity = energyNetComponent.getCapacity();
                            if (componentCapacity > 0) {
                                int componentEnergy = Integer.parseInt(EnergyUtil.getCharge(energyComponentLocationInfo.getLocation()));
                                if (componentEnergy < componentCapacity) {
                                    transferEnergy.set(Math.min(capacitorEnergy.get(), componentCapacity - componentEnergy));
                                    capacitorEnergy.set(capacitorEnergy.get() - transferEnergy.get());
                                    EnergyUtil.setCharge(energyComponentLocationInfo.getLocation(), String.valueOf(componentEnergy + transferEnergy.get()));
                                    EnergyUtil.setCharge(capacitorLocationInfo.getLocation(), String.valueOf(capacitorEnergy.get()));
                                }

                                itemStack.setAmount(itemStack.getAmount() - 1);
                                return -1;
                            }
                        }
                    }

                    return 0;
                };

                if (capacitorEnergy.get() > 0) {
                    if (digital != 0) {
                        this.pointFunction(block, digital, rangeFunction);
                    } else {
                        this.lineFunction(block, this.range, rangeFunction);
                    }
                }

                this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, this,
                        String.valueOf(capacitorEnergy.get()),
                        String.valueOf(transferEnergy));
            };

            BlockTickerUtil.runTask(FinalTechChanged.getLocationRunnableFactory(), FinalTechChanged.isAsyncSlimefunItem(this.getId()), runnable, () -> {
                if (digital > 0) {
                    Location[] locations = new Location[3];
                    locations[0] = block.getRelative(directional.getFacing().getOppositeFace()).getLocation();
                    locations[1] = block.getLocation();
                    locations[2] = block.getRelative(directional.getFacing(), digital).getLocation();
                    return locations;
                } else if (digital == 0) {
                    return this.getLocations(block.getLocation());
                } else {
                    return new Location[0];
                }
            });
        } else if (blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, this,
                    "0",
                    "0");
        }
    }

    @Nonnull
    @Override
    public Location getTargetLocation(@Nonnull Location location, int range) {
        Block block = location.getBlock();
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Directional directional) {
            return block.getRelative(directional.getFacing().getOppositeFace(), range).getLocation();
        }
        return location;
    }

    @Override
    public Location[] getLocations(@Nonnull Location sourceLocation) {
        Block block = sourceLocation.getBlock();
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Directional directional) {
            BlockFace blockFace = directional.getFacing();
            Location[] locations = new Location[this.range + 2];
            int i = 0;
            locations[i++] = block.getRelative(blockFace.getOppositeFace()).getLocation();
            locations[i++] = sourceLocation;
            while (i < locations.length) {
                locations[i++] = block.getRelative(blockFace, i - 2).getLocation();
            }
            return locations;
        }
        return new Location[0];
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this);
    }
}
