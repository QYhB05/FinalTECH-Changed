package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import org.bukkit.NamespacedKey;

import java.util.Locale;

public class FinalTechRecipeTypes {
    // RecipesType
    public static final RecipeType ORDERED_DUST_FACTORY = new RecipeType(new NamespacedKey(FinalTechChanged.getInstance(), FinalTechItemStacks.ORDERED_DUST_FACTORY_DIRT.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.ORDERED_DUST_FACTORY_DIRT);
    public static final RecipeType ETHER_MINER = new RecipeType(new NamespacedKey(FinalTechChanged.getInstance(), FinalTechItemStacks.ETHER_MINER.getItemId().toUpperCase(Locale.ROOT)), FinalTechItemStacks.ETHER_MINER);
    public static final RecipeType ITEM_SERIALIZATION_CONSTRUCTOR = new RecipeType(new NamespacedKey(FinalTechChanged.getInstance(), FinalTechItemStacks.ITEM_SERIALIZATION_CONSTRUCTOR.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.ITEM_SERIALIZATION_CONSTRUCTOR);
    public static final RecipeType MATRIX_CRAFTING_TABLE = new RecipeType(new NamespacedKey(FinalTechChanged.getInstance(), FinalTechItemStacks.MATRIX_CRAFTING_TABLE.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.MATRIX_CRAFTING_TABLE);
    public static final RecipeType ENERGY_TABLE = new RecipeType(new NamespacedKey(FinalTechChanged.getInstance(), FinalTechItemStacks.ENERGY_TABLE.getItemId().toUpperCase(Locale.ROOT)), FinalTechItemStacks.ENERGY_TABLE);
    public static final RecipeType BOX = new RecipeType(new NamespacedKey(FinalTechChanged.getInstance(), FinalTechItemStacks.BOX.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.BOX);
    public static final RecipeType EQUIVALENT_EXCHANGE_TABLE = new RecipeType(new NamespacedKey(FinalTechChanged.getInstance(), FinalTechItemStacks.EQUIVALENT_EXCHANGE_TABLE.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.EQUIVALENT_EXCHANGE_TABLE);
    public static final RecipeType ENTROPY_SEED = new RecipeType(new NamespacedKey(FinalTechChanged.getInstance(), FinalTechItemStacks.ENTROPY_SEED.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.ENTROPY_SEED);
    public static final RecipeType LOGIC_CRAFTER = new RecipeType(new NamespacedKey(FinalTechChanged.getInstance(), FinalTechItemStacks.LOGIC_CRAFTER.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.LOGIC_CRAFTER);
    public static final RecipeType LOGIC_GENERATOR = new RecipeType(new NamespacedKey(FinalTechChanged.getInstance(), FinalTechItemStacks.LOGIC_GENERATOR.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.LOGIC_GENERATOR);
    public static final RecipeType CARD_OPERATION_TABLE = new RecipeType(new NamespacedKey(FinalTechChanged.getInstance(), FinalTechItemStacks.CARD_OPERATION_TABLE.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.CARD_OPERATION_TABLE);
    public static final RecipeType ENTROPY_CONSTRUCTOR = new RecipeType(new NamespacedKey(FinalTechChanged.getInstance(), FinalTechItemStacks.ENTROPY_CONSTRUCTOR.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.ENTROPY_CONSTRUCTOR);
    public static final RecipeType BEDROCK_CRAFT_TABLE = new RecipeType(new NamespacedKey(FinalTechChanged.getInstance(), FinalTechItemStacks.BEDROCK_CRAFT_TABLE.getItemId().toUpperCase(Locale.ROOT)), FinalTechItemStacks.BEDROCK_CRAFT_TABLE);
}
