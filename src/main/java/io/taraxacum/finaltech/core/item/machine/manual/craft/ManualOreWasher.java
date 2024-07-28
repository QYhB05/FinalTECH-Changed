package io.taraxacum.finaltech.core.item.machine.manual.craft;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.libs.slimefun.dto.RandomMachineRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class ManualOreWasher extends AbstractManualCraftMachine {
    public ManualOreWasher(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        List<RandomMachineRecipe.RandomOutput> randomOutputList = new ArrayList<>();
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.IRON_DUST, SlimefunItems.STONE_CHUNK}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.GOLD_DUST, SlimefunItems.STONE_CHUNK}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.COPPER_DUST, SlimefunItems.STONE_CHUNK}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.TIN_DUST, SlimefunItems.STONE_CHUNK}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.ZINC_DUST, SlimefunItems.STONE_CHUNK}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.ALUMINUM_DUST, SlimefunItems.STONE_CHUNK}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.MAGNESIUM_DUST, SlimefunItems.STONE_CHUNK}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.LEAD_DUST, SlimefunItems.STONE_CHUNK}, 1));
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.SILVER_DUST, SlimefunItems.STONE_CHUNK}, 1));
        this.registerRecipe(new RandomMachineRecipe(new ItemStack[]{SlimefunItems.SIFTED_ORE}, randomOutputList));

        this.registerRecipe(new ItemStack(Material.SAND, 2), SlimefunItems.SALT);
        this.registerRecipe(SlimefunItems.PULVERIZED_ORE, SlimefunItems.PURE_ORE_CLUSTER);
    }
}
