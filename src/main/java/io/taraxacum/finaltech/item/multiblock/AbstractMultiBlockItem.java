package io.taraxacum.finaltech.core.item.multiblock;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.libs.slimefun.dto.MachineRecipeFactory;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public abstract class AbstractMultiBlockItem extends MultiBlockMachine {
    public AbstractMultiBlockItem(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull ItemStack[] recipe, @Nonnull BlockFace blockFace) {
        super(itemGroup, item, recipe, blockFace);
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);
        if (this instanceof RecipeItem recipeItem) {
            int delay = recipeItem.getRegisterRecipeDelay();
            if (delay > 0) {
                this.getAddon().getJavaPlugin().getServer().getScheduler().runTaskLater((Plugin) addon, () -> {
                    recipeItem.registerDefaultRecipes();
                    MachineRecipeFactory.getInstance().initAdvancedRecipeMap(this.getId());
                }, delay);
            } else {
                recipeItem.registerDefaultRecipes();
                MachineRecipeFactory.getInstance().initAdvancedRecipeMap(this.getId());
            }
        }
    }

    public SlimefunItem register() {
        this.register(JavaPlugin.getPlugin(FinalTechChanged.class));
        return this;
    }
}
