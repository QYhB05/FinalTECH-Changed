package io.taraxacum.finaltech.core.item.unusable;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.interfaces.SimpleValidItem;
import io.taraxacum.libs.slimefun.util.SfItemUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public class Ether extends UnusableSlimefunItem implements GEOResource, SimpleValidItem, RecipeItem {
    private final ItemWrapper templateValidItem;
    private final NamespacedKey key = new NamespacedKey(FinalTechChanged.getInstance(), this.getId());
    private final int baseAmountNormal = ConfigUtil.getOrDefaultItemSetting(16, this, "base-amount-normal");
    private final int baseAmountNether = ConfigUtil.getOrDefaultItemSetting(8, this, "base-amount-nether");
    private final int baseAmountTheEnd = ConfigUtil.getOrDefaultItemSetting(48, this, "base-amount-end");
    private final int maxDeviation = ConfigUtil.getOrDefaultItemSetting(8, this, "max-deviation");

    public Ether(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        ItemStack validItem = new ItemStack(this.getItem());
        SfItemUtil.setSpecialItemKey(validItem);
        this.templateValidItem = new ItemWrapper(validItem);
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);
        this.register();
    }

    @Override
    public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
        return switch (environment) {
            case NORMAL -> this.baseAmountNormal;
            case NETHER -> this.baseAmountNether;
            case THE_END -> this.baseAmountTheEnd;
            default -> 0;
        };
    }

    @Override
    public int getMaxDeviation() {
        return this.maxDeviation;
    }

    @Nonnull
    @Override
    public String getName() {
        return this.getItemName();
    }

    @Override
    public boolean isObtainableFromGEOMiner() {
        return false;
    }

    @Nonnull
    @Override
    public NamespacedKey getKey() {
        return this.key;
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

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this);
    }
}
