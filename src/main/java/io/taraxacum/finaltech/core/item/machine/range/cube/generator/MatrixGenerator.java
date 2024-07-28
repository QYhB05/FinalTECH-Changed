package io.taraxacum.finaltech.core.item.machine.range.cube.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class MatrixGenerator extends AbstractCubeElectricGenerator {
    private final int energy = ConfigUtil.getOrDefaultItemSetting(1, this, "energy");
    private final int range = ConfigUtil.getOrDefaultItemSetting(10, this, "range");

    public MatrixGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        boolean drawParticle = blockMenu.hasViewer();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        int range = 0;
        for (int slot : this.getInputSlot()) {
            ItemStack itemStack = blockMenu.getItemInSlot(slot);
            if (!ItemStackUtil.isItemNull(itemStack) && FinalTechItems.ITEM_PHONY.verifyItem(itemStack)) {
                for (int i = itemStack.getAmount(); i > 0; i /= 2) {
                    range++;
                }
            }
        }

        int count = this.cubeFunction(block, range + this.getRange(), location -> {
            LocationInfo locationInfo = LocationInfo.get(location);

            if (locationInfo != null && !this.notAllowedId.contains(locationInfo.getId()) && locationInfo.getSlimefunItem() instanceof EnergyNetComponent energyNetComponent && !JavaUtil.matchOnce(energyNetComponent.getEnergyComponentType(), EnergyNetComponentType.CAPACITOR, EnergyNetComponentType.GENERATOR)) {
                BlockTickerUtil.runTask(FinalTechChanged.getLocationRunnableFactory(), FinalTechChanged.isAsyncSlimefunItem(locationInfo.getId()), () -> this.chargeMachine(energyNetComponent, locationInfo), location);
                if (drawParticle) {
                    Location cloneLocation = location.clone();
                    javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, cloneLocation.getBlock()));
                }
                return 1;
            }
            return 0;
        });

        if (blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, StatusL2Menu.STATUS_SLOT, this,
                    String.valueOf(count),
                    String.valueOf(range + this.getRange()));
        }
    }

    private void chargeMachine(@Nonnull EnergyNetComponent energyNetComponent, @Nonnull LocationInfo locationInfo) {
        EnergyUtil.setCharge(locationInfo.getLocation(), energyNetComponent.getCapacity());
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.range),
                String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0));
    }

    @Override
    protected int getEnergy() {
        return this.energy;
    }

    @Override
    protected int getRange() {
        return this.range;
    }
}
