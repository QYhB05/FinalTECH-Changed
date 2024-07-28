package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.item.machine.range.point.EquivalentConcept;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

public class EntropySeed extends AbstractMachine implements RecipeItem {
    private final double equivalentConceptLife = ConfigUtil.getOrDefaultItemSetting(8.0, this, "life");
    private final int equivalentConceptRange = ConfigUtil.getOrDefaultItemSetting(4, this, "range");
    private final String key = "key";
    private final String value = "value";

    public EntropySeed(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                Location location = e.getBlock().getLocation();
                BlockStorage.addBlockInfo(location, EntropySeed.this.key, EntropySeed.this.value);
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new BlockBreakHandler(false, true) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack item, @Nonnull List<ItemStack> drops) {
                blockBreakEvent.setDropItems(false);
                drops.clear();
            }
        };
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return null;
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        // TODO optimization

        Location location = block.getLocation();
        if (BlockStorage.getLocationInfo(block.getLocation(), this.key) != null && this.value.equals(BlockStorage.getLocationInfo(block.getLocation(), this.key))) {
            BlockStorage.addBlockInfo(location, this.key, null);
            SlimefunItem sfItem = SlimefunItem.getByItem(FinalTechItemStacks.EQUIVALENT_CONCEPT);
            if (sfItem != null) {
                BlockStorage.clearBlockInfo(location);
                JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
                javaPlugin.getServer().getScheduler().runTaskLaterAsynchronously(javaPlugin, () -> {
                    if (location.getBlock().getType().equals(EntropySeed.this.getItem().getType())) {
                        BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_ID, FinalTechItemStacks.EQUIVALENT_CONCEPT.getItemId(), true);
                        BlockStorage.addBlockInfo(location, EquivalentConcept.KEY_LIFE, String.valueOf(EntropySeed.this.equivalentConceptLife));
                        BlockStorage.addBlockInfo(location, EquivalentConcept.KEY_RANGE, String.valueOf(EntropySeed.this.equivalentConceptRange));
                    }
                }, Slimefun.getTickerTask().getTickRate() + 1);
            }
        } else {
            BlockStorage.clearBlockInfo(location);
            JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
            javaPlugin.getServer().getScheduler().runTaskLaterAsynchronously(javaPlugin, () -> {
                if (location.getBlock().getType().equals(EntropySeed.this.getItem().getType())) {
                    BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_ID, FinalTechItemStacks.JUSTIFIABILITY.getItemId(), true);
                }
            }, Slimefun.getTickerTask().getTickRate() + 1);
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
}
