package io.taraxacum.finaltech.core.item.unusable;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.listener.BoxListener;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.interfaces.SimpleValidItem;
import io.taraxacum.libs.slimefun.util.SfItemUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import javax.annotation.Nonnull;
 
public class Box extends UnusableSlimefunItem implements RecipeItem, SimpleValidItem {
    private final double height = ConfigUtil.getOrDefaultItemSetting(64, this, "height");

    private final ItemWrapper templateValidItem;

    public Box(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        ItemStack validItem = new ItemStack(this.getItem());
        SfItemUtil.setSpecialItemKey(validItem);
        this.templateValidItem = new ItemWrapper(validItem);
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);
        if (!this.isDisabled()) {
            PluginManager pluginManager = addon.getJavaPlugin().getServer().getPluginManager();
            pluginManager.registerEvents(new BoxListener(this.height), addon.getJavaPlugin());
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this);
    }

    @Nonnull
    @Override
    public ItemStack getValidItem() {
        return ItemStackUtil.cloneItem(this.templateValidItem.getItemStack());
    }

    @Override
    public boolean verifyItem(@Nonnull ItemStack itemStack) {
        return ItemStackUtil.isItemSimilar(itemStack, this.templateValidItem);
    }
}
