package io.taraxacum.finaltech.core.item.machine.tower;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
 
public class CureTower extends AbstractTower implements RecipeItem, MenuUpdater {
    private final double baseRange = ConfigUtil.getOrDefaultItemSetting(1.6, this, "range-base");
    private final double mulRange = ConfigUtil.getOrDefaultItemSetting(0.1, this, "range-mul");
    private final double health = ConfigUtil.getOrDefaultItemSetting(0.025, this, "health");

    public CureTower(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
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
        return new StatusL2Menu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(location);
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        double range = this.baseRange;
        ItemStack itemStack = blockMenu.getItemInSlot(this.getInputSlot()[0]);
        if (ItemStackUtil.isItemSimilar(itemStack, this.getItem())) {
            range += itemStack.getAmount() * this.mulRange;
        }
        final double finalRange = range;

        javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
            int count = 0;
            for (Entity entity : location.getWorld().getNearbyEntities(LocationUtil.getCenterLocation(block), finalRange, finalRange, finalRange, entity -> entity instanceof LivingEntity)) {
                LivingEntity livingEntity = (LivingEntity) entity;
                FinalTechChanged.getEntityRunnableFactory().waitThenRun(() -> {
                    if (livingEntity.getHealth() > 0) {
                        livingEntity.setHealth(Math.min(livingEntity.getHealth() + CureTower.this.health, livingEntity.getMaxHealth()));
                    }
                }, livingEntity);
                count++;
            }

            if (blockMenu.hasViewer()) {
                this.updateMenu(blockMenu, StatusL2Menu.STATUS_SLOT, this,
                        String.valueOf(count),
                        String.valueOf(finalRange));
            }
        });
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.baseRange),
                String.valueOf(this.mulRange),
                String.valueOf(this.health));
    }
}
