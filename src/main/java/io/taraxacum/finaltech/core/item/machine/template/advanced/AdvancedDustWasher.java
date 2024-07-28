package io.taraxacum.finaltech.core.item.machine.template.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.libs.slimefun.dto.RandomMachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
 
public class AdvancedDustWasher extends AbstractAdvanceMachine {
    public AdvancedDustWasher(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        RandomMachineRecipe.RandomOutput[] randomOutputs = new RandomMachineRecipe.RandomOutput[9];
        randomOutputs[0] = new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.IRON_DUST}, 1);
        randomOutputs[1] = new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.GOLD_DUST}, 1);
        randomOutputs[2] = new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.COPPER_DUST}, 1);
        randomOutputs[3] = new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.TIN_DUST}, 1);
        randomOutputs[4] = new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.ZINC_DUST}, 1);
        randomOutputs[5] = new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.ALUMINUM_DUST}, 1);
        randomOutputs[6] = new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.MAGNESIUM_DUST}, 1);
        randomOutputs[7] = new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.LEAD_DUST}, 1);
        randomOutputs[8] = new RandomMachineRecipe.RandomOutput(new ItemStack[]{SlimefunItems.SILVER_DUST}, 1);
        this.registerRecipe(new RandomMachineRecipe(new MachineRecipe(0, new ItemStack[]{SlimefunItems.SIFTED_ORE}, new ItemStack[0]), randomOutputs));

        this.registerRecipe(new ItemStack(Material.SAND, 2), SlimefunItems.SALT);
        this.registerRecipe(SlimefunItems.PULVERIZED_ORE, SlimefunItems.PURE_ORE_CLUSTER);
    }
}
