package io.taraxacum.finaltech.core.item.machine.range.cube.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.plugin.util.StringItemUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
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
public class BasicGenerator extends AbstractCubeElectricGenerator {
    private final int energy = ConfigUtil.getOrDefaultItemSetting(1, this, "energy");
    private final int range = ConfigUtil.getOrDefaultItemSetting(2, this, "range");

    public BasicGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        boolean drawParticle = blockMenu.hasViewer();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        int energy = 0;
        for (int slot : this.getInputSlot()) {
            ItemStack itemStack = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemSimilar(itemStack, this.getItem())) {
                energy = Integer.MAX_VALUE / this.getEnergy() < itemStack.getAmount() ? Integer.MAX_VALUE : this.getEnergy() * itemStack.getAmount();
                break;
            } else if (!ItemStackUtil.isItemNull(itemStack) && FinalTechItems.STORAGE_CARD.verifyItem(itemStack) && ItemStackUtil.isItemSimilar(StringItemUtil.parseItemInCard(itemStack), this.getItem())) {
                int amount = Integer.parseInt(StringNumberUtil.min(StringItemUtil.parseAmountInCard(itemStack), StringNumberUtil.INTEGER_MAX_VALUE));
                energy = Integer.MAX_VALUE / this.getEnergy() < amount ? Integer.MAX_VALUE : this.getEnergy() * amount;
                break;
            } else {
                energy = this.getEnergy();
            }
        }

        int finalEnergy = Math.min(4, energy);
        int count = this.cubeFunction(block, this.getRange(), location -> {
            LocationInfo locationInfo = LocationInfo.get(location);
            if (locationInfo != null && !this.notAllowedId.contains(locationInfo.getId()) && locationInfo.getSlimefunItem() instanceof EnergyNetComponent energyNetComponent && energyNetComponent.getEnergyComponentType() != EnergyNetComponentType.GENERATOR) {
                BlockTickerUtil.runTask(FinalTechChanged.getLocationRunnableFactory(), FinalTechChanged.isAsyncSlimefunItem(locationInfo.getId()), () -> BasicGenerator.this.chargeMachine(energyNetComponent, finalEnergy, locationInfo), location);
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
    protected int getEnergy() {
        return this.energy;
    }

    @Override
    protected int getRange() {
        return this.range;
    }
}
