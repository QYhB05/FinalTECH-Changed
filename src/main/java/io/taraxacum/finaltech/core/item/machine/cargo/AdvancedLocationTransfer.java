package io.taraxacum.finaltech.core.item.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.dto.CargoDTO;
import io.taraxacum.finaltech.core.helper.*;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.cargo.AdvancedLocationTransferMenu;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AdvancedLocationTransfer extends AbstractCargo implements RecipeItem {
    private final double particleDistance = 0.25;
    private final int particleInterval = 2;

    public AdvancedLocationTransfer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                Block block = blockPlaceEvent.getBlock();
                Location location = block.getLocation();

                CargoNumber.HELPER.checkOrSetBlockStorage(location);
                SlotSearchSize.HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.HELPER.checkOrSetBlockStorage(location);
                CargoLimit.HELPER.checkOrSetBlockStorage(location);

                CargoMode.HELPER.checkOrSetBlockStorage(location);
                CargoOrder.HELPER.checkOrSetBlockStorage(location);
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this, AdvancedLocationTransferMenu.LOCATION_RECORDER_SLOT);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new AdvancedLocationTransferMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Location location = block.getLocation();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
        boolean drawParticle = blockMenu.hasViewer() || RouteShow.VALUE_TRUE.equals(RouteShow.HELPER.getOrDefaultValue(config));

        ItemStack locationRecorder = blockMenu.getItemInSlot(AdvancedLocationTransferMenu.LOCATION_RECORDER_SLOT);
        if (ItemStackUtil.isItemNull(locationRecorder)) {
            return;
        }
        Location targetLocation = LocationUtil.parseLocationInItem(locationRecorder);
        if (targetLocation == null || targetLocation.equals(location)) {
            return;
        }
        Block targetBlock = targetLocation.getBlock();

        if (!PermissionUtil.checkOfflinePermission(locationRecorder, targetLocation)) {
            return;
        }

        String slotSearchSize = SlotSearchSize.HELPER.getOrDefaultValue(config);
        String slotSearchOrder = SlotSearchOrder.HELPER.getOrDefaultValue(config);

        CargoDTO cargoDTO = new CargoDTO();
        cargoDTO.setJavaPlugin(this.addon.getJavaPlugin());

        boolean positive;
        if (CargoOrder.VALUE_POSITIVE.equals(CargoOrder.HELPER.getOrDefaultValue(config))) {
            cargoDTO.setInputBlock(block);
            cargoDTO.setInputSize(SlotSearchSize.VALUE_INPUTS_ONLY);
            cargoDTO.setInputOrder(SlotSearchOrder.VALUE_ASCENT);

            cargoDTO.setOutputBlock(targetBlock);
            cargoDTO.setOutputSize(slotSearchSize);
            cargoDTO.setOutputOrder(slotSearchOrder);
            positive = true;
        } else {
            cargoDTO.setOutputBlock(block);
            cargoDTO.setOutputSize(SlotSearchSize.VALUE_INPUTS_ONLY);
            cargoDTO.setOutputOrder(SlotSearchOrder.VALUE_ASCENT);

            cargoDTO.setInputBlock(targetBlock);
            cargoDTO.setInputSize(slotSearchSize);
            cargoDTO.setInputOrder(slotSearchOrder);
            positive = false;
        }

        if (drawParticle) {
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, targetBlock));
            if (FinalTechChanged.getSlimefunTickCount() % this.particleInterval == 0) {
                List<Location> locationList = new ArrayList<>();
                locationList.add(LocationUtil.getCenterLocation(block));
                locationList.add(LocationUtil.getCenterLocation(targetBlock));
                final List<Location> finalLocationList = positive ? locationList : JavaUtil.reserve(locationList);
                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(javaPlugin, Particle.CRIT_MAGIC, this.particleInterval * Slimefun.getTickerTask().getTickRate() * 50L, this.particleDistance, finalLocationList));
            }
        }

        cargoDTO.setCargoNumber(Integer.parseInt(CargoNumber.HELPER.getOrDefaultValue(config)));
        cargoDTO.setCargoLimit(CargoLimit.HELPER.getOrDefaultValue(config));
        cargoDTO.setCargoFilter(CargoFilter.VALUE_BLACK);
        cargoDTO.setFilterInv(blockMenu.toInventory());
        cargoDTO.setFilterSlots(new int[0]);

        CargoUtil.doCargo(cargoDTO, CargoMode.HELPER.getOrDefaultValue(config));
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this);
    }
}
