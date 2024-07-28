package io.taraxacum.finaltech.core.item.machine.range;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.event.ConfigSaveActionEvent;
import io.taraxacum.finaltech.core.interfaces.*;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.ConfigurationWorkerMenu;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public class ConfigurationPaster extends AbstractMachine implements RecipeItem, PointMachine, LineMachine, LocationMachine {
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final int range = ConfigUtil.getOrDefaultItemSetting(16, this, "range");

    public ConfigurationPaster(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
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
        return new ConfigurationWorkerMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block.getLocation());

        if (!ItemStackUtil.isItemNull(blockMenu.getItemInSlot(this.getOutputSlot()[0]))) {
            return;
        }

        ItemStack itemConfigurator = blockMenu.getItemInSlot(this.getInputSlot()[0]);
        SlimefunItem machineConfigurator = SlimefunItem.getByItem(itemConfigurator);
        if (machineConfigurator == null || !FinalTechItemStacks.MACHINE_CONFIGURATOR.getItemId().equals(machineConfigurator.getId())) {
            return;
        }

        ItemStack digitalItemStack = blockMenu.getItemInSlot(ConfigurationWorkerMenu.DIGITAL_SLOT);
        int digital = SlimefunItem.getByItem(digitalItemStack) instanceof DigitalItem digitalItem ? digitalItem.getDigit() : 0;

        BlockData blockData = block.getBlockData();
        if (blockData instanceof Directional directional) {
            Runnable runnable = () -> {
                ItemStack outputItem = ItemStackUtil.cloneItem(itemConfigurator, 1);
                AtomicBoolean atomicBoolean = new AtomicBoolean(false);

                RangeFunction rangeFunction = location -> {
                    if (atomicBoolean.get()) {
                        return -1;
                    }

                    LocationInfo locationInfo = LocationInfo.get(location);
                    if (locationInfo != null) {
                        if (ConfigurationPaster.this.notAllowedId.contains(locationInfo.getId())) {
                            return -1;
                        }

                        if (ItemConfigurationUtil.loadConfigFromItem(outputItem, locationInfo)) {
                            BlockTickerUtil.runTask(FinalTechChanged.getLocationRunnableFactory(), FinalTechChanged.isAsyncSlimefunItem(locationInfo.getId()), () -> FinalTechChanged.getInstance().getServer().getPluginManager().callEvent(new ConfigSaveActionEvent(true, location, locationInfo.getId())), location);

                            if (blockMenu.hasViewer()) {
                                JavaPlugin javaPlugin = ConfigurationPaster.this.getAddon().getJavaPlugin();
                                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, locationInfo.getLocation().getBlock()));
                            }
                        }
                        atomicBoolean.set(true);
                        return 1;
                    }

                    return 0;
                };

                int result = digital == 0 ? this.lineFunction(block, this.range, rangeFunction) : this.pointFunction(block, digital, rangeFunction);

                blockMenu.pushItem(outputItem, this.getOutputSlot()[0]);
                itemConfigurator.setAmount(itemConfigurator.getAmount() - 1);
            };

            BlockTickerUtil.runTask(FinalTechChanged.getLocationRunnableFactory(), FinalTechChanged.isAsyncSlimefunItem(this.getId()), runnable, () -> {
                if (digital > 0) {
                    return new Location[]{block.getLocation(), block.getRelative(directional.getFacing(), digital).getLocation()};
                } else if (digital == 0) {
                    return this.getLocations(block.getLocation());
                } else {
                    return new Location[0];
                }
            });
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this);
    }

    @Nonnull
    @Override
    public Location getTargetLocation(@Nonnull Location location, int range) {
        Block block = location.getBlock();
        BlockData blockData = block.getBlockData();
        return blockData instanceof Directional directional ? block.getRelative(directional.getFacing().getOppositeFace(), range).getLocation() : location;
    }

    @Override
    public Location[] getLocations(@Nonnull Location sourceLocation) {
        Block block = sourceLocation.getBlock();
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Directional directional) {
            BlockFace blockFace = directional.getFacing();
            Location[] locations = new Location[this.range + 1];
            int i = 0;
            locations[i++] = sourceLocation;
            while (i < locations.length) {
                locations[i++] = block.getRelative(blockFace, i - 1).getLocation();
            }
            return locations;
        }
        return new Location[0];
    }
}
