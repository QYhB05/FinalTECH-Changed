package io.taraxacum.finaltech.core.item.machine.range.cube.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.LocationMachine;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.item.machine.range.cube.AbstractCubeMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.plugin.util.StringItemUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public abstract class AbstractCubeElectricGenerator extends AbstractCubeMachine implements RecipeItem, MenuUpdater, LocationMachine {
    protected final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final ItemWrapper itemWrapper = new ItemWrapper(this.getItem());

    public AbstractCubeElectricGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_ALLOW;
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusL2Menu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        boolean drawParticle = blockMenu.hasViewer();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        int energy = 0;
        ItemStack itemStack = blockMenu.getItemInSlot(13);
        if (ItemStackUtil.isItemSimilar(itemStack, this.itemWrapper)) {
            energy = Integer.MAX_VALUE / this.getEnergy() < itemStack.getAmount() ? Integer.MAX_VALUE : this.getEnergy() * itemStack.getAmount();

        } else if (!ItemStackUtil.isItemNull(itemStack) && FinalTechItems.STORAGE_CARD.verifyItem(itemStack) && ItemStackUtil.isItemSimilar(StringItemUtil.parseItemInCard(itemStack), this.itemWrapper)) {
            int amount = Integer.parseInt(StringNumberUtil.min(StringItemUtil.parseAmountInCard(itemStack), StringNumberUtil.INTEGER_MAX_VALUE));
            energy = Integer.MAX_VALUE / this.getEnergy() < amount ? Integer.MAX_VALUE : this.getEnergy() * amount;

        } else {
            energy = this.getEnergy();
        }

        int finalEnergy = energy;
        int count = this.cubeFunction(block, this.getRange(), location -> {
            LocationInfo locationInfo = LocationInfo.get(location);
            if (locationInfo != null && !this.notAllowedId.contains(locationInfo.getId()) && locationInfo.getSlimefunItem() instanceof EnergyNetComponent energyNetComponent && energyNetComponent.getEnergyComponentType() != EnergyNetComponentType.GENERATOR && energyNetComponent.getEnergyComponentType() != EnergyNetComponentType.CAPACITOR) {
                BlockTickerUtil.runTask(FinalTechChanged.getLocationRunnableFactory(), FinalTechChanged.isAsyncSlimefunItem(locationInfo.getId()), () -> AbstractCubeElectricGenerator.this.chargeMachine(energyNetComponent, finalEnergy, locationInfo), location);
                if (drawParticle) {
                    Location cloneLocation = location.clone();
                    javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, cloneLocation.getBlock()));
                }
                return 1;
            }
            return 0;
        });

        blockMenu = BlockStorage.getInventory(block);
        if (blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, StatusL2Menu.STATUS_SLOT, this,
                    String.valueOf(count),
                    String.valueOf(finalEnergy));
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    void chargeMachine(@Nonnull EnergyNetComponent energyNetComponent, int chargeEnergy, LocationInfo locationInfo) {
        int capacity = energyNetComponent.getCapacity();
        String energyStr = EnergyUtil.getCharge(locationInfo.getLocation());
        int energy = energyStr == null ? 0 : Integer.parseInt(energyStr);
        if (energy < capacity) {
            int transferEnergy = Math.min(capacity - energy, chargeEnergy);
            if (transferEnergy > 0) {
                energyNetComponent.setCharge(locationInfo.getLocation(), energy + transferEnergy);
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.getEnergy()),
                String.valueOf(this.getRange()),
                String.valueOf(String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0)));
    }

    @Override
    public Location[] getLocations(@Nonnull Location sourceLocation) {
        int i = 0;
        Location location = sourceLocation.clone();
        World world = location.getWorld();
        int minX = location.getBlockX() - this.getRange();
        int minY = Math.max(location.getBlockY() - this.getRange(), world.getMinHeight());
        int minZ = location.getBlockZ() - this.getRange();
        int maxX = location.getBlockX() + this.getRange();
        int maxY = Math.min(location.getBlockY() + this.getRange(), world.getMaxHeight());
        int maxZ = location.getBlockZ() + this.getRange();
        Location[] locations = new Location[(maxX - minX + 1) * (maxY - minY + 1) + (maxZ - minZ + 1)];
        for (int x = minX; x <= maxX; x++) {
            location.setX(x);
            for (int y = minY; y <= maxY; y++) {
                location.setY(y);
                for (int z = minZ; z <= maxZ; z++) {
                    location.setZ(z);
                    locations[i++] = location.clone();
                }
            }
        }

        return locations;
    }

    @Override
    public void updateMenu(@Nonnull BlockMenu blockMenu, int slot, @Nonnull SlimefunItem slimefunItem, @Nonnull String... text) {
        MenuUpdater.super.updateMenu(blockMenu, slot, slimefunItem, text);
        ItemStack itemStack = blockMenu.getItemInSlot(slot);
        if (text.length > 0) {
            itemStack.setType(StringNumberUtil.ZERO.equals(text[0]) ? Material.RED_STAINED_GLASS_PANE : Material.GREEN_STAINED_GLASS_PANE);
        }
    }

    protected abstract int getEnergy();

    protected abstract int getRange();
}
