package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public final class FinalTechRecipes {
    /* items */
    // material
    public static final ItemStack[] GEARWHEEL = new ItemStack[]{
            new ItemStack(Material.POLISHED_GRANITE), new ItemStack(Material.POLISHED_GRANITE), new ItemStack(Material.POLISHED_GRANITE),
            new ItemStack(Material.POLISHED_DIORITE), new ItemStack(Material.POLISHED_DIORITE), new ItemStack(Material.POLISHED_DIORITE),
            new ItemStack(Material.POLISHED_ANDESITE), new ItemStack(Material.POLISHED_ANDESITE), new ItemStack(Material.POLISHED_ANDESITE)
    };
    public static final ItemStack[] UNORDERED_DUST = new ItemStack[]{};
    public static final ItemStack[] ORDERED_DUST = new ItemStack[]{};
    public static final ItemStack[] BUG = new ItemStack[]{};
    public static final ItemStack[] ETHER = new ItemStack[]{};
    public static final ItemStack[] BOX = new ItemStack[]{};
    public static final ItemStack[] SHINE = new ItemStack[]{};
    public static final ItemStack[] ENTROPY = new ItemStack[]{};

    public static final ItemStack[] COPY_CARD = new ItemStack[]{};
    public static final ItemStack[] ANNULAR = new ItemStack[]{};
    public static final ItemStack[] SINGULARITY = new ItemStack[]{};
    public static final ItemStack[] SPIROCHETE = new ItemStack[]{};
    public static final ItemStack[] SHELL = new ItemStack[]{};
    public static final ItemStack[] PHONY = new ItemStack[]{};
    public static final ItemStack[] JUSTIFIABILITY = new ItemStack[]{};
    public static final ItemStack[] EQUIVALENT_CONCEPT = new ItemStack[]{};

    public static final ItemStack[] WATER_CARD = new ItemStack[]{
            null, null, null,
            null, new ItemStack(Material.WATER_BUCKET), null,
            null, null, null
    };
    public static final ItemStack[] LAVA_CARD = new ItemStack[]{
            null, null, null,
            null, new ItemStack(Material.LAVA_BUCKET), null,
            null, null, null
    };
    public static final ItemStack[] MILK_CARD = new ItemStack[]{
            null, null, null,
            null, new ItemStack(Material.MILK_BUCKET), null,
            null, null, null
    };
    public static final ItemStack[] FLINT_AND_STEEL_CARD = new ItemStack[]{
            null, null, null,
            null, new ItemStack(Material.FLINT_AND_STEEL), null,
            null, null, null
    };
    public static final ItemStack[] QUANTITY_MODULE = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 12), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 12), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 12), null,
            null, null, null, null, null, FinalTechItemStacks.ANNULAR
    };
    public static final ItemStack[] ENERGIZED_QUANTITY_MODULE = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), FinalTechItemStacks.QUANTITY_MODULE, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), FinalTechItemStacks.QUANTITY_MODULE, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), FinalTechItemStacks.QUANTITY_MODULE, FinalTechItemStacks.QUANTITY_MODULE, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), FinalTechItemStacks.QUANTITY_MODULE, FinalTechItemStacks.QUANTITY_MODULE, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), FinalTechItemStacks.QUANTITY_MODULE, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), FinalTechItemStacks.QUANTITY_MODULE, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20)
    };
    public static final ItemStack[] OVERLOADED_QUANTITY_MODULE = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), FinalTechItemStacks.QUANTITY_MODULE, FinalTechItemStacks.QUANTITY_MODULE, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), FinalTechItemStacks.QUANTITY_MODULE, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), FinalTechItemStacks.QUANTITY_MODULE, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), FinalTechItemStacks.QUANTITY_MODULE, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), FinalTechItemStacks.QUANTITY_MODULE, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), FinalTechItemStacks.QUANTITY_MODULE, FinalTechItemStacks.QUANTITY_MODULE, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20)
    };

    // logic item
    public static final ItemStack[] LOGIC_FALSE = new ItemStack[]{};
    public static final ItemStack[] LOGIC_TRUE = new ItemStack[]{};
    public static final ItemStack[] DIGITAL_ZERO = new ItemStack[]{
            FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_ONE = new ItemStack[]{
            FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_TRUE
    };
    public static final ItemStack[] DIGITAL_TWO = new ItemStack[]{
            FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_THREE = new ItemStack[]{
            FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_TRUE
    };
    public static final ItemStack[] DIGITAL_FOUR = new ItemStack[]{
            FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_FIVE = new ItemStack[]{
            FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_TRUE
    };
    public static final ItemStack[] DIGITAL_SIX = new ItemStack[]{
            FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_SEVEN = new ItemStack[]{
            FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_TRUE
    };
    public static final ItemStack[] DIGITAL_EIGHT = new ItemStack[]{
            FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_NINE = new ItemStack[]{
            FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_TRUE
    };
    public static final ItemStack[] DIGITAL_TEN = new ItemStack[]{
            FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_ELEVEN = new ItemStack[]{
            FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_TRUE
    };
    public static final ItemStack[] DIGITAL_TWELVE = new ItemStack[]{
            FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_THIRTEEN = new ItemStack[]{
            FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_TRUE
    };
    public static final ItemStack[] DIGITAL_FOURTEEN = new ItemStack[]{
            FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_FALSE
    };
    public static final ItemStack[] DIGITAL_FIFTEEN = new ItemStack[]{
            FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.LOGIC_TRUE
    };

    // tool
    public static final ItemStack[] POTION_EFFECT_COMPRESSOR = new ItemStack[]{
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, new ItemStack(Material.MILK_BUCKET), SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, new ItemStack(Material.MILK_BUCKET), FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, new ItemStack(Material.MILK_BUCKET), new ItemStack(Material.MILK_BUCKET), SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, new ItemStack(Material.MILK_BUCKET), new ItemStack(Material.MILK_BUCKET), SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, new ItemStack(Material.MILK_BUCKET), SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, new ItemStack(Material.MILK_BUCKET), FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST
    };
    public static final ItemStack[] POTION_EFFECT_DILATOR = new ItemStack[]{
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, new ItemStack(Material.MILK_BUCKET), new ItemStack(Material.MILK_BUCKET), SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, new ItemStack(Material.MILK_BUCKET), SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, new ItemStack(Material.MILK_BUCKET), FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, new ItemStack(Material.MILK_BUCKET), SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, new ItemStack(Material.MILK_BUCKET), FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, new ItemStack(Material.MILK_BUCKET), new ItemStack(Material.MILK_BUCKET), SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST
    };
    public static final ItemStack[] POTION_EFFECT_PURIFIER = new ItemStack[]{
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST
    };
    public static final ItemStack[] GRAVITY_REVERSAL_RUNE = new ItemStack[]{
            SlimefunItems.AIR_RUNE, SlimefunItems.AIR_RUNE, SlimefunItems.AIR_RUNE, SlimefunItems.AIR_RUNE, SlimefunItems.AIR_RUNE, SlimefunItems.AIR_RUNE,
            SlimefunItems.AIR_RUNE, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, SlimefunItems.AIR_RUNE,
            SlimefunItems.AIR_RUNE, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.SHINE, SlimefunItems.STAFF_WIND, FinalTechItemStacks.UNORDERED_DUST, SlimefunItems.AIR_RUNE,
            SlimefunItems.AIR_RUNE, FinalTechItemStacks.UNORDERED_DUST, SlimefunItems.BOOTS_OF_THE_STOMPER, new ItemStack(Material.ELYTRA), FinalTechItemStacks.UNORDERED_DUST, SlimefunItems.AIR_RUNE,
            SlimefunItems.AIR_RUNE, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, SlimefunItems.AIR_RUNE,
            SlimefunItems.AIR_RUNE, SlimefunItems.AIR_RUNE, SlimefunItems.AIR_RUNE, SlimefunItems.AIR_RUNE, SlimefunItems.AIR_RUNE, SlimefunItems.AIR_RUNE
    };
    public static final ItemStack[] STAFF_ELEMENTAL_LINE = new ItemStack[]{
            SlimefunItems.ENDER_RUNE, SlimefunItems.ENDER_RUNE, SlimefunItems.ENDER_RUNE, SlimefunItems.ENDER_RUNE, SlimefunItems.ENDER_RUNE, SlimefunItems.ENDER_RUNE,
            SlimefunItems.ENDER_RUNE, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, SlimefunItems.ENDER_RUNE,
            SlimefunItems.ENDER_RUNE, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.SHINE, SlimefunItems.STAFF_ELEMENTAL, FinalTechItemStacks.UNORDERED_DUST, SlimefunItems.ENDER_RUNE,
            SlimefunItems.ENDER_RUNE, FinalTechItemStacks.UNORDERED_DUST, SlimefunItems.MAGIC_EYE_OF_ENDER, new ItemStack(Material.STRING), FinalTechItemStacks.UNORDERED_DUST, SlimefunItems.ENDER_RUNE,
            SlimefunItems.ENDER_RUNE, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, SlimefunItems.ENDER_RUNE,
            SlimefunItems.ENDER_RUNE, SlimefunItems.ENDER_RUNE, SlimefunItems.ENDER_RUNE, SlimefunItems.ENDER_RUNE, SlimefunItems.ENDER_RUNE, SlimefunItems.ENDER_RUNE
    };

    public static final ItemStack[] MENU_VIEWER = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST, null, null, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, new ItemStack(Material.SPYGLASS), new ItemStack(Material.SPYGLASS), null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, new ItemStack(Material.SPYGLASS), new ItemStack(Material.SPYGLASS), null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST, null, null, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] ROUTE_VIEWER = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST, null, null, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, new ItemStack(Material.ENDER_EYE), new ItemStack(Material.ENDER_EYE), null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, new ItemStack(Material.ENDER_EYE), new ItemStack(Material.ENDER_EYE), null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST, null, null, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] LOCATION_RECORDER = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST, null, null, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, new ItemStack(Material.COMPASS), new ItemStack(Material.COMPASS), null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, new ItemStack(Material.COMPASS), new ItemStack(Material.COMPASS), null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST, null, null, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] MACHINE_CONFIGURATOR = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST, null, null, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, new ItemStack(Material.CLOCK), new ItemStack(Material.CLOCK), null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, new ItemStack(Material.CLOCK), new ItemStack(Material.CLOCK), null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST, null, null, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] PORTABLE_ENERGY_STORAGE = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.BOX, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BOX, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL, SlimefunItems.BATTERY, SlimefunItems.BATTERY, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL, SlimefunItems.BATTERY, SlimefunItems.BATTERY, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.BOX, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BOX, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST
    };

    // consumable
    public static final ItemStack[] MACHINE_CHARGE_CARD_L1 = new ItemStack[]{
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST
    };
    public static final ItemStack[] MACHINE_CHARGE_CARD_L2 = new ItemStack[]{
           null, null, null, null, null, null,
           null, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1,null,
           null, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, null,
           null, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, null,
           null, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1,null,
            null, null, null, null, null, null
    };
    public static final ItemStack[] MACHINE_CHARGE_CARD_L3 = new ItemStack[]{
            null, null, null, null, null, null,
            null, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, null,
            null, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, null,
            null, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, null,
            null, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2,null,
            null, null, null, null, null, null
    };
    public static final ItemStack[] MACHINE_ACCELERATE_CARD_L1 = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST
    };
    public static final ItemStack[] MACHINE_ACCELERATE_CARD_L2 = new ItemStack[]{
            null, null, null, null, null, null,
            null, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, null,
            null, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, null,
            null, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, null,
            null, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, null,
            null, null, null, null, null, null
    };
    public static final ItemStack[] MACHINE_ACCELERATE_CARD_L3 = new ItemStack[]{
            null, null, null, null, null, null,
            null, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, null,
            null, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, null,
            null, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, null,
            null, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, null,
            null, null, null, null, null, null
    };
    public static final ItemStack[] MACHINE_ACTIVATE_CARD_L1 = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.ORDERED_DUST
    };
    public static final ItemStack[] MACHINE_ACTIVATE_CARD_L2 = new ItemStack[]{
            null, null, null, null, null, null,
            null, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, null,
            null, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, null,
            null, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, null,
            null, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, null,
            null, null, null, null, null, null
    };
    public static final ItemStack[] MACHINE_ACTIVATE_CARD_L3 = new ItemStack[]{
            null, null, null, null, null, null,
            null, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, null,
            null, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, null,
            null, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, null,
            null, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, null,
            null, null, null, null, null, null
    };

    public static final ItemStack[] ENERGY_K = new ItemStack[]{};
    public static final ItemStack[] ENERGY_M = new ItemStack[]{};
    public static final ItemStack[] ENERGY_B = new ItemStack[]{};
    public static final ItemStack[] ENERGY_T = new ItemStack[]{};
    public static final ItemStack[] ENERGY_Q = new ItemStack[]{};

    public static final ItemStack[] MAGIC_HYPNOTIC = new ItemStack[]{
            null, new ItemStack(Material.DRAGON_BREATH), null,
            null, SlimefunItems.FORTUNE_COOKIE, null,
            null, FinalTechItemStacks.GEARWHEEL, null
    };
    public static final ItemStack[] RESEARCH_UNLOCK_TICKET = new ItemStack[]{
            null, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 64), null,
            null, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 1), null,
            null, ItemStackUtil.cloneItem(FinalTechItemStacks.UNORDERED_DUST, 1), null
    };

    // weapon
    public static final ItemStack[] SUPER_SHOVEL = new ItemStack[]{
            null, new ItemStack(Material.IRON_INGOT), null,
            null, FinalTechItemStacks.GEARWHEEL, null,
            null, FinalTechItemStacks.GEARWHEEL, null
    };
    public static final ItemStack[] ULTIMATE_SHOVEL = new ItemStack[]{
            null, FinalTechItemStacks.SHINE, null,
            null, new ItemStack(Material.DIAMOND_BLOCK), null,
            null, new ItemStack(Material.DIAMOND_BLOCK), null
    };
    public static final ItemStack[] SUPER_PICKAXE = new ItemStack[]{
            new ItemStack(Material.IRON_INGOT), new ItemStack(Material.IRON_INGOT), new ItemStack(Material.IRON_INGOT),
            null, FinalTechItemStacks.GEARWHEEL, null,
            null, FinalTechItemStacks.GEARWHEEL, null
    };
    public static final ItemStack[] ULTIMATE_PICKAXE = new ItemStack[]{
            FinalTechItemStacks.SHINE, FinalTechItemStacks.SHINE, FinalTechItemStacks.SHINE,
            null, new ItemStack(Material.DIAMOND_BLOCK), null,
            null, new ItemStack(Material.DIAMOND_BLOCK), null
    };
    public static final ItemStack[] SUPER_AXE = new ItemStack[]{
            new ItemStack(Material.IRON_INGOT), new ItemStack(Material.IRON_INGOT), null,
            new ItemStack(Material.IRON_INGOT), FinalTechItemStacks.GEARWHEEL, null,
            null, FinalTechItemStacks.GEARWHEEL, null
    };
    public static final ItemStack[] ULTIMATE_AXE = new ItemStack[]{
            FinalTechItemStacks.SHINE, FinalTechItemStacks.SHINE, null,
            FinalTechItemStacks.SHINE, new ItemStack(Material.DIAMOND_BLOCK), null,
            null, new ItemStack(Material.DIAMOND_BLOCK), null
    };
    public static final ItemStack[] SUPER_HOE = new ItemStack[]{
            new ItemStack(Material.IRON_INGOT), new ItemStack(Material.IRON_INGOT), null,
            null, FinalTechItemStacks.GEARWHEEL, null,
            null, FinalTechItemStacks.GEARWHEEL, null
    };
    public static final ItemStack[] ULTIMATE_HOE = new ItemStack[]{
            FinalTechItemStacks.SHINE, FinalTechItemStacks.SHINE, null,
            null, new ItemStack(Material.DIAMOND_BLOCK), null,
            null, new ItemStack(Material.DIAMOND_BLOCK), null
    };

    /*electricity system*/
    // electric generator
    public static final ItemStack[] BASIC_GENERATOR = new ItemStack[]{
            SlimefunItems.SOLAR_PANEL, SlimefunItems.SOLAR_PANEL, SlimefunItems.SOLAR_PANEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.GEARWHEEL, null
    };
    public static final ItemStack[] ADVANCED_GENERATOR = new ItemStack[]{
            FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BASIC_GENERATOR,
            FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.REDSTONE), FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BASIC_GENERATOR
    };
    public static final ItemStack[] CARBONADO_GENERATOR = new ItemStack[]{
            FinalTechItemStacks.ADVANCED_GENERATOR, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ADVANCED_GENERATOR,
            FinalTechItemStacks.GEARWHEEL, SlimefunItems.CARBONADO, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.ADVANCED_GENERATOR, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ADVANCED_GENERATOR
    };
    public static final ItemStack[] ENERGIZED_GENERATOR = new ItemStack[]{
            FinalTechItemStacks.CARBONADO_GENERATOR, FinalTechItemStacks.ETHER, FinalTechItemStacks.CARBONADO_GENERATOR,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.ETHER,
            FinalTechItemStacks.CARBONADO_GENERATOR, FinalTechItemStacks.ETHER, FinalTechItemStacks.CARBONADO_GENERATOR
    };
    public static final ItemStack[] ENERGIZED_STACK_GENERATOR = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), FinalTechItemStacks.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), FinalTechItemStacks.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), FinalTechItemStacks.ENERGIZED_GENERATOR, FinalTechItemStacks.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), FinalTechItemStacks.ENERGIZED_GENERATOR, FinalTechItemStacks.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), FinalTechItemStacks.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), FinalTechItemStacks.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20)
    };
    public static final ItemStack[] OVERLOADED_GENERATOR = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), FinalTechItemStacks.ENERGIZED_GENERATOR, FinalTechItemStacks.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), FinalTechItemStacks.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), FinalTechItemStacks.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), FinalTechItemStacks.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), FinalTechItemStacks.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), FinalTechItemStacks.ENERGIZED_GENERATOR, FinalTechItemStacks.ENERGIZED_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 20)
    };

    public static final ItemStack[] ORDERED_DUST_GENERATOR = new ItemStack[]{
            FinalTechItemStacks.ENTROPY, FinalTechItemStacks.ENTROPY, FinalTechItemStacks.ENTROPY, FinalTechItemStacks.ENTROPY, FinalTechItemStacks.ENTROPY, null,
            FinalTechItemStacks.ENTROPY, null, null, null, null, null,
            FinalTechItemStacks.ENTROPY, FinalTechItemStacks.ENTROPY, FinalTechItemStacks.ENTROPY, null, FinalTechItemStacks.ETHER, null,
            FinalTechItemStacks.ENTROPY, null, null, null, null, null,
            FinalTechItemStacks.ENTROPY, FinalTechItemStacks.ENTROPY, FinalTechItemStacks.ENTROPY, FinalTechItemStacks.ENTROPY, FinalTechItemStacks.ENTROPY, null
    };
    public static final ItemStack[] TIME_GENERATOR = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR,
            FinalTechItemStacks.BASIC_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 2), FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR,
            FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 4), FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR,
            FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 8), FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR,
            FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), FinalTechItemStacks.BASIC_GENERATOR,
            FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.BASIC_GENERATOR, FinalTechItemStacks.SHINE
    };
    static final ItemStack[] ENERGIZED_CHARGE_BASE = new ItemStack[]{
            null, null, null, null, null, null,
            null, null, null, null, null, null,
            FinalTechItemStacks.GEARWHEEL, null, null, null, null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BUG, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.BUG, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.ENERGIZED_GENERATOR, FinalTechItemStacks.ENERGIZED_GENERATOR, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER
    };
    public static final ItemStack[] OVERLOADED_CHARGE_BASE = new ItemStack[]{
            null, null, null, null, null, null,
            null, null, null, null, null, null,
            FinalTechItemStacks.GEARWHEEL, null, null, null, null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.ENERGIZED_GENERATOR, FinalTechItemStacks.ENERGIZED_GENERATOR, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER
    };

    // electric storage
    public static final ItemStack[] SMALL_EXPANDED_CAPACITOR = new ItemStack[]{
            SlimefunItems.DURALUMIN_INGOT, FinalTechItemStacks.ETHER, SlimefunItems.DURALUMIN_INGOT,
            FinalTechItemStacks.ORDERED_DUST, SlimefunItems.ENERGIZED_CAPACITOR, FinalTechItemStacks.ORDERED_DUST,
            SlimefunItems.DURALUMIN_INGOT, FinalTechItemStacks.GEARWHEEL, SlimefunItems.DURALUMIN_INGOT
    };
    public static final ItemStack[] MEDIUM_EXPANDED_CAPACITOR = new ItemStack[]{
            SlimefunItems.BILLON_INGOT, FinalTechItemStacks.ORDERED_DUST, SlimefunItems.BILLON_INGOT,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.GEARWHEEL,
            SlimefunItems.BILLON_INGOT, FinalTechItemStacks.ORDERED_DUST, SlimefunItems.BILLON_INGOT
    };
    public static final ItemStack[] BIG_EXPANDED_CAPACITOR = new ItemStack[]{
            SlimefunItems.STEEL_INGOT, FinalTechItemStacks.ORDERED_DUST, SlimefunItems.STEEL_INGOT,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.MEDIUM_EXPANDED_CAPACITOR, FinalTechItemStacks.GEARWHEEL,
            SlimefunItems.STEEL_INGOT, FinalTechItemStacks.ORDERED_DUST, SlimefunItems.STEEL_INGOT
    };
    public static final ItemStack[] LARGE_EXPANDED_CAPACITOR = new ItemStack[]{
            SlimefunItems.REINFORCED_ALLOY_INGOT, FinalTechItemStacks.ORDERED_DUST, SlimefunItems.REINFORCED_ALLOY_INGOT,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BIG_EXPANDED_CAPACITOR, FinalTechItemStacks.GEARWHEEL,
            SlimefunItems.REINFORCED_ALLOY_INGOT, FinalTechItemStacks.ORDERED_DUST, SlimefunItems.REINFORCED_ALLOY_INGOT
    };
    public static final ItemStack[] CARBONADO_EXPANDED_CAPACITOR = new ItemStack[]{
            SlimefunItems.CARBONADO, FinalTechItemStacks.ORDERED_DUST, SlimefunItems.CARBONADO,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.LARGE_EXPANDED_CAPACITOR, FinalTechItemStacks.GEARWHEEL,
            SlimefunItems.CARBONADO, FinalTechItemStacks.ORDERED_DUST, SlimefunItems.CARBONADO
    };
    public static final ItemStack[] ENERGIZED_EXPANDED_CAPACITOR = new ItemStack[]{
            SlimefunItems.CARBONADO, FinalTechItemStacks.ORDERED_DUST, SlimefunItems.CARBONADO,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.CARBONADO_EXPANDED_CAPACITOR, FinalTechItemStacks.ETHER,
            SlimefunItems.CARBONADO, FinalTechItemStacks.ORDERED_DUST, SlimefunItems.CARBONADO
    };

    public static final ItemStack[] ENERGIZED_STACK_EXPANDED_CAPACITOR = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12)
    };
    public static final ItemStack[] OVERLOADED_EXPANDED_CAPACITOR = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 12)
    };
    public static final ItemStack[] TIME_CAPACITOR = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 2), FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR,
            FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 2), FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR,
            FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 4), FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR,
            FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 8), FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR,
            FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR,
            FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, FinalTechItemStacks.SHINE,
    };

    // electric transmission
    public static final ItemStack[] NORMAL_ELECTRICITY_SHOOT_PILE = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, SlimefunItems.ENERGY_CONNECTOR, FinalTechItemStacks.GEARWHEEL,
            SlimefunItems.ENERGY_CONNECTOR, FinalTechItemStacks.ORDERED_DUST, SlimefunItems.ENERGY_CONNECTOR,
            FinalTechItemStacks.GEARWHEEL, SlimefunItems.ENERGY_CONNECTOR, FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE = new ItemStack[]{
            null, null, null,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.NORMAL_ELECTRICITY_SHOOT_PILE, FinalTechItemStacks.ORDERED_DUST,
            null, null, null
    };
    public static final ItemStack[] NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, null, FinalTechItemStacks.ORDERED_DUST,
            null, FinalTechItemStacks.NORMAL_ELECTRICITY_SHOOT_PILE, null,
            FinalTechItemStacks.ORDERED_DUST, null, FinalTechItemStacks.ORDERED_DUST
    };
    public static final ItemStack[] ENERGIZED_ELECTRICITY_SHOOT_PILE = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.NORMAL_ELECTRICITY_SHOOT_PILE, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST
    };
    public static final ItemStack[] OVERLOADED_ELECTRICITY_SHOOT_PILE = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.NORMAL_ELECTRICITY_SHOOT_PILE, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] VARIABLE_WIRE_RESISTANCE = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, SlimefunItems.ENERGY_CONNECTOR, FinalTechItemStacks.GEARWHEEL,
            SlimefunItems.ENERGY_CONNECTOR, SlimefunItems.CARBONADO_EDGED_CAPACITOR, SlimefunItems.ENERGY_CONNECTOR,
            FinalTechItemStacks.GEARWHEEL, SlimefunItems.ENERGY_CONNECTOR, FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] VARIABLE_WIRE_CAPACITOR = new ItemStack[]{
            SlimefunItems.ENERGY_CONNECTOR, FinalTechItemStacks.GEARWHEEL, SlimefunItems.ENERGY_CONNECTOR,
            FinalTechItemStacks.GEARWHEEL, SlimefunItems.CARBONADO_EDGED_CAPACITOR, FinalTechItemStacks.GEARWHEEL,
            SlimefunItems.ENERGY_CONNECTOR, FinalTechItemStacks.GEARWHEEL, SlimefunItems.ENERGY_CONNECTOR
    };

    // electric accelerator
    public static final ItemStack[] ENERGIZED_ACCELERATOR = new ItemStack[]{
            FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR,
            FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), FinalTechItemStacks.ANNULAR,
            FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), FinalTechItemStacks.ANNULAR,
            FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), FinalTechItemStacks.ANNULAR,
            FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), FinalTechItemStacks.ANNULAR,
            FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR
    };
    public static final ItemStack[] OVERLOADED_ACCELERATOR = new ItemStack[]{
            FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR,
            FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), FinalTechItemStacks.ANNULAR,
            FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), FinalTechItemStacks.ANNULAR,
            FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), FinalTechItemStacks.ANNULAR,
            FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 20), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 60), FinalTechItemStacks.ANNULAR,
            FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR
    };

    /* cargo system */
    // storage unit
    public static final ItemStack[] NORMAL_STORAGE_UNIT = new ItemStack[]{
            new ItemStack(Material.GLASS), new ItemStack(Material.CHEST), new ItemStack(Material.GLASS),
            new ItemStack(Material.CHEST), null, new ItemStack(Material.CHEST),
            new ItemStack(Material.GLASS), new ItemStack(Material.CHEST), new ItemStack(Material.GLASS),
    };
    public static final ItemStack[] DIVIDED_STORAGE_UNIT = new ItemStack[]{
            null, FinalTechItemStacks.GEARWHEEL, null,
            null, FinalTechItemStacks.NORMAL_STORAGE_UNIT, null,
            null, FinalTechItemStacks.GEARWHEEL, null,
    };
    public static final ItemStack[] LIMITED_STORAGE_UNIT = new ItemStack[]{
            null, null, null,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.NORMAL_STORAGE_UNIT, FinalTechItemStacks.GEARWHEEL,
            null, null, null,
    };
    public static final ItemStack[] STACK_STORAGE_UNIT = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.NORMAL_STORAGE_UNIT, null,
            FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL,
    };
    public static final ItemStack[] DIVIDED_LIMITED_STORAGE_UNIT = new ItemStack[]{
            null, FinalTechItemStacks.GEARWHEEL, null,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.NORMAL_STORAGE_UNIT, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.GEARWHEEL, null,
    };
    public static final ItemStack[] DIVIDED_STACK_STORAGE_UNIT = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.NORMAL_STORAGE_UNIT, null,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
    };
    public static final ItemStack[] LIMITED_STACK_STORAGE_UNIT = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.NORMAL_STORAGE_UNIT, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL,
    };

    public static final ItemStack[] RANDOM_INPUT_STORAGE_UNIT = new ItemStack[]{
            null, FinalTechItemStacks.ORDERED_DUST, null,
            null, FinalTechItemStacks.NORMAL_STORAGE_UNIT, null,
            null, null, null,
    };
    public static final ItemStack[] RANDOM_OUTPUT_STORAGE_UNIT = new ItemStack[]{
            null, null, null,
            null, FinalTechItemStacks.NORMAL_STORAGE_UNIT, null,
            null, FinalTechItemStacks.ORDERED_DUST, null,
    };
    public static final ItemStack[] RANDOM_ACCESS_STORAGE_UNIT = new ItemStack[]{
            null, FinalTechItemStacks.ORDERED_DUST, null,
            null, FinalTechItemStacks.NORMAL_STORAGE_UNIT, null,
            null, FinalTechItemStacks.ORDERED_DUST, null,
    };
    public static final ItemStack[] DISTRIBUTE_LEFT_STORAGE_UNIT = new ItemStack[]{
            null, null, null,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.NORMAL_STORAGE_UNIT, null,
            null, null, null,
    };
    public static final ItemStack[] DISTRIBUTE_RIGHT_STORAGE_UNIT = new ItemStack[]{
            null, null, null,
            null, FinalTechItemStacks.NORMAL_STORAGE_UNIT, FinalTechItemStacks.ORDERED_DUST,
            null, null, null,
    };

    // advanced storage
    public static final ItemStack[] STORAGE_INTERACT_PORT = new ItemStack[]{
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE),
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE)
    };
    public static final ItemStack[] STORAGE_INSERT_PORT = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE),
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE)
    };
    public static final ItemStack[] STORAGE_WITHDRAW_PORT = new ItemStack[]{
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE),
            new ItemStack(Material.STONE), new ItemStack(Material.STONE), new ItemStack(Material.STONE),
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] STORAGE_CARD = new ItemStack[]{
            new ItemStack(Material.TRAPPED_CHEST), new ItemStack(Material.TRAPPED_CHEST), new ItemStack(Material.TRAPPED_CHEST),
            new ItemStack(Material.TRAPPED_CHEST), FinalTechItemStacks.ORDERED_DUST, new ItemStack(Material.TRAPPED_CHEST),
            new ItemStack(Material.TRAPPED_CHEST), new ItemStack(Material.TRAPPED_CHEST), new ItemStack(Material.TRAPPED_CHEST)
    };

    // accessor
    public static final ItemStack[] REMOTE_ACCESSOR = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.OBSERVER), FinalTechItemStacks.GEARWHEEL,
            new ItemStack(Material.OBSERVER), FinalTechItemStacks.BASIC_FRAME_MACHINE, new ItemStack(Material.OBSERVER),
            FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.OBSERVER), FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] CONSUMABLE_REMOTE_ACCESSOR = new ItemStack[]{
            null, null, null,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.REMOTE_ACCESSOR, FinalTechItemStacks.ORDERED_DUST,
            null, null, null
    };
    public static final ItemStack[] CONFIGURABLE_REMOTE_ACCESSOR = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, null, FinalTechItemStacks.ORDERED_DUST,
            null, FinalTechItemStacks.REMOTE_ACCESSOR, null,
            FinalTechItemStacks.ORDERED_DUST, null, FinalTechItemStacks.ORDERED_DUST
    };
    public static final ItemStack[] EXPANDED_CONSUMABLE_REMOTE_ACCESSOR = new ItemStack[]{
            null, FinalTechItemStacks.ORDERED_DUST, null,
            FinalTechItemStacks.CONSUMABLE_REMOTE_ACCESSOR, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.CONSUMABLE_REMOTE_ACCESSOR,
            null, FinalTechItemStacks.ORDERED_DUST, null
    };
    public static final ItemStack[] EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR = new ItemStack[]{
            null, FinalTechItemStacks.ORDERED_DUST, null,
            FinalTechItemStacks.CONFIGURABLE_REMOTE_ACCESSOR, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.CONFIGURABLE_REMOTE_ACCESSOR,
            null, FinalTechItemStacks.ORDERED_DUST, null
    };
    public static final ItemStack[] RANDOM_ACCESSOR = new ItemStack[]{
            null, FinalTechItemStacks.GEARWHEEL, null,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.REMOTE_ACCESSOR, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.GEARWHEEL, null
    };
    public static final ItemStack[] AREA_ACCESSOR = new ItemStack[]{
            FinalTechItemStacks.REMOTE_ACCESSOR, null, FinalTechItemStacks.REMOTE_ACCESSOR,
            null, FinalTechItemStacks.BASIC_FRAME_MACHINE, null,
            FinalTechItemStacks.REMOTE_ACCESSOR, null, FinalTechItemStacks.REMOTE_ACCESSOR
    };

    public static final ItemStack[] TRANSPORTER = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.PISTON), FinalTechItemStacks.GEARWHEEL,
            new ItemStack(Material.PISTON), FinalTechItemStacks.BASIC_FRAME_MACHINE, new ItemStack(Material.PISTON),
            FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.PISTON), FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] CONSUMABLE_TRANSPORTER = new ItemStack[]{
            null, null, null,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.TRANSPORTER, FinalTechItemStacks.ORDERED_DUST,
            null, null, null
    };
    public static final ItemStack[] CONFIGURABLE_TRANSPORTER = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, null, FinalTechItemStacks.ORDERED_DUST,
            null, FinalTechItemStacks.TRANSPORTER, null,
            FinalTechItemStacks.ORDERED_DUST, null, FinalTechItemStacks.ORDERED_DUST
    };
    public static final ItemStack[] EXPANDED_CONSUMABLE_TRANSPORTER = new ItemStack[]{
            null, FinalTechItemStacks.ORDERED_DUST, null,
            FinalTechItemStacks.CONSUMABLE_TRANSPORTER, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.CONSUMABLE_TRANSPORTER,
            null, FinalTechItemStacks.ORDERED_DUST, null
    };
    public static final ItemStack[] EXPANDED_CONFIGURABLE_TRANSPORTER = new ItemStack[]{
            null, FinalTechItemStacks.ORDERED_DUST, null,
            FinalTechItemStacks.CONFIGURABLE_TRANSPORTER, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.CONFIGURABLE_TRANSPORTER,
            null, FinalTechItemStacks.ORDERED_DUST, null
    };

    // logic
    public static final ItemStack[] LOGIC_COMPARATOR_NOTNULL = new ItemStack[]{
            FinalTechItemStacks.BASIC_FRAME_MACHINE, null, null, null, null, null,
            null, FinalTechItemStacks.GEARWHEEL, null, null, null, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, null, null, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.GEARWHEEL, null, null, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.GEARWHEEL, null, null, null, FinalTechItemStacks.GEARWHEEL,
    };
    public static final ItemStack[] LOGIC_COMPARATOR_AMOUNT = new ItemStack[]{
            FinalTechItemStacks.BASIC_FRAME_MACHINE, null, null, null, null, null,
            null, null, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, null,
            null, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.GEARWHEEL, null, null, null, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.GEARWHEEL, null, null, null, FinalTechItemStacks.GEARWHEEL,
    };
    public static final ItemStack[] LOGIC_COMPARATOR_SIMILAR = new ItemStack[]{
            FinalTechItemStacks.BASIC_FRAME_MACHINE, null, null, null, null, null,
            null, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.GEARWHEEL, null, null, null, null,
            null, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            null, null, null, null, null, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
    };
    public static final ItemStack[] LOGIC_COMPARATOR_EQUAL = new ItemStack[]{
            FinalTechItemStacks.BASIC_FRAME_MACHINE, null, null, null, null, null,
            null, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.GEARWHEEL, null, null, null, null,
            null, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, null,
            null, FinalTechItemStacks.GEARWHEEL, null, null, null, null,
            null, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
    };
    public static final ItemStack[] LOGIC_CRAFTER = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_TRUE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_FALSE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_TRUE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_FALSE, 4), FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_FALSE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_TRUE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_FALSE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_TRUE, 4), FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_TRUE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_FALSE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_TRUE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_FALSE, 4), FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_FALSE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_TRUE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_FALSE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_TRUE, 4), FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] DIGIT_ADDER = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_ZERO, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_ONE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_TWO, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_THREE, 4), FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_FOUR, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_FIVE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_SIX, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_SEVEN, 4), FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_EIGHT, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_NINE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_TEN, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_ELEVEN, 4), FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_TWELVE, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_THIRTEEN, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_FOURTEEN, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.DIGITAL_FIFTEEN, 4), FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL
    };

    // cargo
    public static final ItemStack[] BASIC_FRAME_MACHINE = new ItemStack[]{
            new ItemStack(Material.STONE), new ItemStack(Material.CHAIN), new ItemStack(Material.STONE),
            new ItemStack(Material.CHAIN), FinalTechItemStacks.ORDERED_DUST, new ItemStack(Material.CHAIN),
            new ItemStack(Material.STONE), new ItemStack(Material.CHAIN), new ItemStack(Material.STONE)
    };
    public static final ItemStack[] POINT_TRANSFER = new ItemStack[]{
            new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.CHAIN), new ItemStack(Material.TRIPWIRE_HOOK),
            new ItemStack(Material.STRING), FinalTechItemStacks.BASIC_FRAME_MACHINE, new ItemStack(Material.STRING),
            new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.CHAIN), new ItemStack(Material.TRIPWIRE_HOOK)
    };
    public static final ItemStack[] MESH_TRANSFER = new ItemStack[]{
            new ItemStack(Material.HOPPER), new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.CHAIN),
            new ItemStack(Material.HOPPER), FinalTechItemStacks.BASIC_FRAME_MACHINE, new ItemStack(Material.STRING),
            new ItemStack(Material.HOPPER), new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.CHAIN)
    };
    public static final ItemStack[] LINE_TRANSFER = new ItemStack[]{
            new ItemStack(Material.STRING), new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.STRING),
            new ItemStack(Material.TRIPWIRE_HOOK), FinalTechItemStacks.BASIC_FRAME_MACHINE, new ItemStack(Material.TRIPWIRE_HOOK),
            new ItemStack(Material.CHEST), new ItemStack(Material.CHEST), new ItemStack(Material.CHEST)
    };
    public static final ItemStack[] LOCATION_TRANSFER = new ItemStack[]{
            new ItemStack(Material.CHAIN), new ItemStack(Material.CHEST), new ItemStack(Material.CHAIN),
            new ItemStack(Material.TRIPWIRE_HOOK), FinalTechItemStacks.BASIC_FRAME_MACHINE, new ItemStack(Material.TRIPWIRE_HOOK),
            new ItemStack(Material.TRIPWIRE_HOOK), new ItemStack(Material.STRING), new ItemStack(Material.TRIPWIRE_HOOK)
    };
    public static final ItemStack[] ADVANCED_POINT_TRANSFER = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 2),ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),ItemStackUtil.cloneItem(FinalTechItemStacks.POINT_TRANSFER, 1),ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),ItemStackUtil.cloneItem(FinalTechItemStacks.UNORDERED_DUST, 2),ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16)
    };
    public static final ItemStack[] ADVANCED_MESH_TRANSFER = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 2),ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),ItemStackUtil.cloneItem(FinalTechItemStacks.MESH_TRANSFER, 1),ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),ItemStackUtil.cloneItem(FinalTechItemStacks.UNORDERED_DUST, 2),ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16)
    };
    public static final ItemStack[] ADVANCED_LINE_TRANSFER = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 2),ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),ItemStackUtil.cloneItem(FinalTechItemStacks.LINE_TRANSFER, 1),ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),ItemStackUtil.cloneItem(FinalTechItemStacks.UNORDERED_DUST, 2),ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16)
    };
    public static final ItemStack[] ADVANCED_LOCATION_TRANSFER = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST, 2),ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),ItemStackUtil.cloneItem(FinalTechItemStacks.LOCATION_TRANSFER, 1),ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),
            ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16),ItemStackUtil.cloneItem(FinalTechItemStacks.UNORDERED_DUST, 2),ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 16)
    };

    public static final ItemStack[] CONFIGURATION_COPIER = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.MACHINE_CONFIGURATOR, FinalTechItemStacks.ORDERED_DUST,
            null, FinalTechItemStacks.BASIC_FRAME_MACHINE, null,
            FinalTechItemStacks.ORDERED_DUST, null, FinalTechItemStacks.ORDERED_DUST
    };
    public static final ItemStack[] CONFIGURATION_PASTER = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, null, FinalTechItemStacks.ORDERED_DUST,
            null, FinalTechItemStacks.BASIC_FRAME_MACHINE, null,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.MACHINE_CONFIGURATOR, FinalTechItemStacks.ORDERED_DUST
    };
    public static final ItemStack[] CLICK_WORK_MACHINE = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL,
            null, FinalTechItemStacks.BASIC_FRAME_MACHINE, null,
            FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] SIMULATE_CLICK_MACHINE = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, null, FinalTechItemStacks.ORDERED_DUST,
            null, FinalTechItemStacks.BASIC_FRAME_MACHINE, null,
            FinalTechItemStacks.ORDERED_DUST, null, FinalTechItemStacks.ORDERED_DUST
    };
    public static final ItemStack[] CONSUMABLE_SIMULATE_CLICK_MACHINE = new ItemStack[]{
            null, null, null,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.SIMULATE_CLICK_MACHINE, FinalTechItemStacks.ORDERED_DUST,
            null, null, null
    };

    /* functional machines */
    // core machine
    public static final ItemStack[] BEDROCK_CRAFT_TABLE = new ItemStack[]{
            null, new ItemStack(Material.DISPENSER), null,
            null, new ItemStack(Material.CHISELED_STONE_BRICKS), null,
            null, new ItemStack(Material.POLISHED_DEEPSLATE), null
    };
    public static final ItemStack[] MATRIX_CRAFTING_TABLE = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.ORDERED_DUST, new ItemStack(Material.ENDER_CHEST), FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST
    };
    public static final ItemStack[] CARD_OPERATION_TABLE = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.GEARWHEEL, null, null, null, null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, new ItemStack(Material.CRAFTING_TABLE), new ItemStack(Material.CRAFTING_TABLE), null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, new ItemStack(Material.CRAFTING_TABLE), new ItemStack(Material.CRAFTING_TABLE), null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, null, null, null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST
    };

    public static final ItemStack[] ORDERED_DUST_FACTORY_DIRT = new ItemStack[]{
            SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN,
            SlimefunItems.PORTABLE_DUSTBIN, new ItemStack(Material.DIRT), SlimefunItems.PORTABLE_DUSTBIN,
            SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN
    };
    public static final ItemStack[] ORDERED_DUST_FACTORY_STONE = new ItemStack[]{
            SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN,
            SlimefunItems.PORTABLE_DUSTBIN, new ItemStack(Material.COBBLESTONE), SlimefunItems.PORTABLE_DUSTBIN,
            SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN, SlimefunItems.PORTABLE_DUSTBIN
    };
    public static final ItemStack[] EQUIVALENT_EXCHANGE_TABLE = new ItemStack[]{  
            FinalTechItemStacks.BOX, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BOX,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.SHINE, FinalTechItemStacks.SHINE, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.SHINE, FinalTechItemStacks.SHINE, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.BOX, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BOX
    };
    public static final ItemStack[] ITEM_SERIALIZATION_CONSTRUCTOR = new ItemStack[]{
            FinalTechItemStacks.BOX, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BOX,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ENERGY_CARD_B, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 10), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 10), FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 10), FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 10), FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 10), FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 10), FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ENERGY_CARD_B, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 10), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 10), FinalTechItemStacks.ENERGY_CARD_B , FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.BOX, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BOX
    };
    public static final ItemStack[] ITEM_DESERIALIZE_PARSER = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.GEARWHEEL, null, null, null, null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechItemStacks.SHINE, null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.SHINE, FinalTechItemStacks.ITEM_SERIALIZATION_CONSTRUCTOR, null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, null, null, null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST
    };
    public static final ItemStack[] ETHER_MINER = new ItemStack[]{
            FinalTechItemStacks.BUG, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BUG,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ENERGY_CARD_M, FinalTechItemStacks.ENERGY_CARD_M, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BOX, ItemStackUtil.cloneItem(FinalTechItemStacks.SHINE, 2), ItemStackUtil.cloneItem(FinalTechItemStacks.SHINE, 2), FinalTechItemStacks.BOX, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BOX, ItemStackUtil.cloneItem(FinalTechItemStacks.SHINE, 2), ItemStackUtil.cloneItem(FinalTechItemStacks.SHINE, 2), FinalTechItemStacks.BOX, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ENERGY_CARD_M, FinalTechItemStacks.ENERGY_CARD_M, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.BUG, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BUG
    };

    public static final ItemStack[] ENERGY_TABLE = new ItemStack[]{
            null, FinalTechItemStacks.PORTABLE_ENERGY_STORAGE, null,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.GEARWHEEL,
    };
    public static final ItemStack[] ENERGY_INPUT_TABLE = new ItemStack[]{
            null, FinalTechItemStacks.PORTABLE_ENERGY_STORAGE, null,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BASIC_FRAME_MACHINE, null,
    };
    public static final ItemStack[] ENERGY_OUTPUT_TABLE = new ItemStack[]{
            null, FinalTechItemStacks.PORTABLE_ENERGY_STORAGE, null,
            null, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.GEARWHEEL,
    };
    public static final ItemStack[] ITEM_DISMANTLE_TABLE = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BUG, FinalTechItemStacks.ENERGY_CARD_K, FinalTechItemStacks.ENERGY_CARD_K, FinalTechItemStacks.BUG, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ETHER, new CustomItemStack(Material.CRAFTING_TABLE), new CustomItemStack(Material.CRAFTING_TABLE), FinalTechItemStacks.ETHER, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ETHER, new CustomItemStack(Material.CRAFTING_TABLE), new CustomItemStack(Material.CRAFTING_TABLE), FinalTechItemStacks.ETHER, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BUG, FinalTechItemStacks.ENERGY_CARD_K, FinalTechItemStacks.ENERGY_CARD_K, FinalTechItemStacks.BUG, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST
    };
    public static final ItemStack[] AUTO_ITEM_DISMANTLE_TABLE = new ItemStack[]{
            FinalTechItemStacks.ENERGY_CARD_B,FinalTechItemStacks.ENERGY_CARD_K,FinalTechItemStacks.ENERGY_CARD_K,FinalTechItemStacks.ENERGY_CARD_K,FinalTechItemStacks.ENERGY_CARD_K,FinalTechItemStacks.ENERGY_CARD_B,
            FinalTechItemStacks.ENERGY_CARD_K,FinalTechItemStacks.BUG, FinalTechItemStacks.SHINE, FinalTechItemStacks.BOX, FinalTechItemStacks.BUG,FinalTechItemStacks.ENERGY_CARD_K,
            FinalTechItemStacks.ENERGY_CARD_K,FinalTechItemStacks.BOX, FinalTechItemStacks.ITEM_DISMANTLE_TABLE, FinalTechItemStacks.ITEM_DISMANTLE_TABLE, FinalTechItemStacks.SHINE, FinalTechItemStacks.ENERGY_CARD_K,
            FinalTechItemStacks.ENERGY_CARD_K,FinalTechItemStacks.SHINE, FinalTechItemStacks.ITEM_DISMANTLE_TABLE, FinalTechItemStacks.ITEM_DISMANTLE_TABLE, FinalTechItemStacks.BOX, FinalTechItemStacks.ENERGY_CARD_K,
            FinalTechItemStacks.ENERGY_CARD_K,FinalTechItemStacks.BUG, FinalTechItemStacks.BOX, FinalTechItemStacks.SHINE, FinalTechItemStacks.BUG,FinalTechItemStacks.ENERGY_CARD_K,
            FinalTechItemStacks.ENERGY_CARD_B,FinalTechItemStacks.ENERGY_CARD_K,FinalTechItemStacks.ENERGY_CARD_K,FinalTechItemStacks.ENERGY_CARD_K,FinalTechItemStacks.ENERGY_CARD_K,FinalTechItemStacks.ENERGY_CARD_B,

    };
    public static final ItemStack[] ADVANCED_AUTO_CRAFT_FRAME = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.SHINE, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.SHINE, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ANNULAR, new ItemStack(Material.BEACON), FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.BEACON), FinalTechItemStacks.ANNULAR, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.SHINE, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.SHINE, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST
    };
    public static final ItemStack[] MULTI_FRAME_MACHINE = new ItemStack[]{
            FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER,
    };

    // special machine
    public static final ItemStack[] ITEM_FIXER = new ItemStack[]{
            FinalTechItemStacks.BUG, SlimefunItems.AUTO_ANVIL_2, FinalTechItemStacks.BUG,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ORDERED_DUST,
            SlimefunItems.DUCT_TAPE, SlimefunItems.DUCT_TAPE, SlimefunItems.DUCT_TAPE
    };
    public static final ItemStack[] COBBLESTONE_FACTORY = new ItemStack[]{
            FinalTechItemStacks.ETHER,FinalTechItemStacks.ETHER,FinalTechItemStacks.ETHER,FinalTechItemStacks.ETHER,FinalTechItemStacks.ETHER,FinalTechItemStacks.ETHER,
            FinalTechItemStacks.ENERGY_CARD_M, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.ORDERED_DUST,FinalTechItemStacks.ORDERED_DUST,FinalTechItemStacks.UNORDERED_DUST,FinalTechItemStacks.ENERGY_CARD_M,
            FinalTechItemStacks.ENERGY_CARD_M, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST,FinalTechItemStacks.UNORDERED_DUST,FinalTechItemStacks.ORDERED_DUST,FinalTechItemStacks.ENERGY_CARD_M,
            FinalTechItemStacks.ENERGY_CARD_M, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST,FinalTechItemStacks.UNORDERED_DUST,FinalTechItemStacks.ORDERED_DUST,FinalTechItemStacks.ENERGY_CARD_M,
            FinalTechItemStacks.ENERGY_CARD_M, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.ORDERED_DUST,FinalTechItemStacks.ORDERED_DUST,FinalTechItemStacks.UNORDERED_DUST,FinalTechItemStacks.ENERGY_CARD_M,
            FinalTechItemStacks.ETHER,FinalTechItemStacks.ETHER,FinalTechItemStacks.ETHER,FinalTechItemStacks.ETHER,FinalTechItemStacks.ETHER,FinalTechItemStacks.ETHER,

    };
    public static final ItemStack[] FUEL_CHARGER = new ItemStack[]{
            null, null, null, null, null, null,
            null, null, null, null, null, null,
            FinalTechItemStacks.GEARWHEEL, null, null, null, null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, null, SlimefunItems.FIRE_RUNE, SlimefunItems.FIRE_RUNE, null, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, SlimefunItems.FIRE_RUNE, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, SlimefunItems.FIRE_RUNE, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER,
    };
    public static final ItemStack[] FUEL_ACCELERATOR = new ItemStack[]{
            null, null, null, null, null, null,
            null, null, null, null, null, null,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BUG, SlimefunItems.FIRE_RUNE, SlimefunItems.FIRE_RUNE, FinalTechItemStacks.BUG, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, SlimefunItems.FIRE_RUNE, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.BASIC_FRAME_MACHINE, SlimefunItems.FIRE_RUNE, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER,
    };
    public static final ItemStack[] FUEL_OPERATOR = new ItemStack[]{
            null, null, null, null, null, null,
            null, null, null, null, null, null,
            FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, SlimefunItems.FIRE_RUNE, FinalTechItemStacks.BUG, FinalTechItemStacks.BUG, SlimefunItems.FIRE_RUNE, FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 64), FinalTechItemStacks.GEARWHEEL,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER, FinalTechItemStacks.ETHER,
    };
    public static final ItemStack[] OPERATION_ACCELERATOR = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 12), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 16), FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 12), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 12), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 12), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), null,
    };
    public static final ItemStack[] ENERGIZED_OPERATION_ACCELERATOR = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 8),
            ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), FinalTechItemStacks.OPERATION_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), FinalTechItemStacks.OPERATION_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3),
            ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), FinalTechItemStacks.OPERATION_ACCELERATOR, FinalTechItemStacks.OPERATION_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3),
            ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), FinalTechItemStacks.OPERATION_ACCELERATOR, FinalTechItemStacks.OPERATION_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3),
            ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), FinalTechItemStacks.OPERATION_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), FinalTechItemStacks.OPERATION_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3),
            ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 8)
    };
    public static final ItemStack[] OVERLOADED_OPERATION_ACCELERATOR = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 8),
            ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), FinalTechItemStacks.OPERATION_ACCELERATOR, FinalTechItemStacks.OPERATION_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3),
            ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), FinalTechItemStacks.OPERATION_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), FinalTechItemStacks.OPERATION_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3),
            ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), FinalTechItemStacks.OPERATION_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), FinalTechItemStacks.OPERATION_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3),
            ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), FinalTechItemStacks.OPERATION_ACCELERATOR, FinalTechItemStacks.OPERATION_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3),
            ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 3), ItemStackUtil.cloneItem(FinalTechItemStacks.BUG, 8)
    };

    // tower
    public static final ItemStack[] CURE_TOWER = new ItemStack[]{
            FinalTechItemStacks.ETHER, null, null, null, null, FinalTechItemStacks.ETHER,
            null, FinalTechItemStacks.ETHER, null, null, FinalTechItemStacks.ETHER, null,
            null, null, FinalTechItemStacks.SHINE, FinalTechItemStacks.SHINE, null, null,
            null, null, FinalTechItemStacks.SHINE, FinalTechItemStacks.SHINE, null, null,
            null, FinalTechItemStacks.ETHER, null, null, FinalTechItemStacks.ETHER, null,
            FinalTechItemStacks.ETHER, null, null, null, null, FinalTechItemStacks.ETHER,
    };
    public static final ItemStack[] PURIFY_LEVEL_TOWER = new ItemStack[]{
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.SHINE, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.SHINE, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.SHINE, FinalTechItemStacks.SHINE, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.SHINE, FinalTechItemStacks.SHINE, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.SHINE, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.POTION_EFFECT_DILATOR, FinalTechItemStacks.SHINE, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST
    };
    public static final ItemStack[] PURIFY_TIME_TOWER = new ItemStack[]{
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.SHINE, FinalTechItemStacks.SHINE, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.SHINE, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.SHINE, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.SHINE, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.SHINE, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.SHINE, FinalTechItemStacks.SHINE, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR, FinalTechItemStacks.UNORDERED_DUST,
            FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST
    };

    /* productive machine */
    // manual machine
    public static final ItemStack[] MANUAL_CRAFTING_TABLE = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, SlimefunItems.PORTABLE_CRAFTER, null,
            null, null, null,
            null, null, null
    };
    public static final ItemStack[] MANUAL_ENHANCED_CRAFTING_TABLE = new ItemStack[]{
            null, new ItemStack(Material.CRAFTING_TABLE), null,
            FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.DISPENSER), FinalTechItemStacks.GEARWHEEL,
            null, null, null
    };
    public static final ItemStack[] MANUAL_GRIND_STONE = new ItemStack[]{
            null, new ItemStack(Material.OAK_FENCE), null,
            FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.DISPENSER), FinalTechItemStacks.GEARWHEEL,
            null, null, null
    };
    public static final ItemStack[] MANUAL_ARMOR_FORGE = new ItemStack[]{
            null, new ItemStack(Material.ANVIL), null,
            FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.DISPENSER), FinalTechItemStacks.GEARWHEEL,
            null, null, null
    };
    public static final ItemStack[] MANUAL_ORE_CRUSHER = new ItemStack[]{
            null, new ItemStack(Material.NETHER_BRICK_FENCE), null,
            new ItemStack(Material.IRON_BARS), new ItemStack(Material.DISPENSER), new ItemStack(Material.IRON_BARS),
            FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] MANUAL_COMPRESSOR = new ItemStack[]{
            null, new ItemStack(Material.NETHER_BRICK_FENCE), null,
            new ItemStack(Material.PISTON), new ItemStack(Material.DISPENSER), new ItemStack(Material.PISTON),
            FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] MANUAL_SMELTERY = new ItemStack[]{
            SlimefunItems.IGNITION_CHAMBER, new ItemStack(Material.NETHER_BRICK_FENCE), SlimefunItems.IGNITION_CHAMBER,
            new ItemStack(Material.NETHER_BRICK), new ItemStack(Material.DISPENSER), new ItemStack(Material.NETHER_BRICK),
            FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL
    };
    public static final ItemStack[] MANUAL_PRESSURE_CHAMBER = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.DISPENSER), FinalTechItemStacks.GEARWHEEL,
            new ItemStack(Material.PISTON), null, new ItemStack(Material.PISTON),
            new ItemStack(Material.PISTON), new ItemStack(Material.CAULDRON), new ItemStack(Material.PISTON)
    };
    public static final ItemStack[] MANUAL_MAGIC_WORKBENCH = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL,
            new ItemStack(Material.BOOKSHELF), new ItemStack(Material.CRAFTING_TABLE), new ItemStack(Material.DISPENSER),
            null, null, null
    };
    public static final ItemStack[] MANUAL_ORE_WASHER = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.DISPENSER), FinalTechItemStacks.GEARWHEEL,
            null, new ItemStack(Material.OAK_FENCE), null,
            null, new ItemStack(Material.CAULDRON), null
    };
    public static final ItemStack[] MANUAL_COMPOSTER = new ItemStack[]{
            new ItemStack(Material.OAK_SLAB), FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.OAK_SLAB),
            new ItemStack(Material.OAK_SLAB), null, new ItemStack(Material.OAK_SLAB),
            new ItemStack(Material.OAK_SLAB), new ItemStack(Material.CAULDRON), new ItemStack(Material.OAK_SLAB)
    };
    public static final ItemStack[] MANUAL_GOLD_PAN = new ItemStack[]{
            null, new ItemStack(Material.OAK_TRAPDOOR), null,
            FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.CAULDRON), FinalTechItemStacks.GEARWHEEL,
            null, null, null
    };
    public static final ItemStack[] MANUAL_CRUCIBLE = new ItemStack[]{
            new ItemStack(Material.TERRACOTTA), FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.TERRACOTTA),
            new ItemStack(Material.TERRACOTTA), null, new ItemStack(Material.TERRACOTTA),
            new ItemStack(Material.TERRACOTTA), new ItemStack(Material.FLINT_AND_STEEL), new ItemStack(Material.TERRACOTTA)
    };
    public static final ItemStack[] MANUAL_JUICER = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, new ItemStack(Material.GLASS), FinalTechItemStacks.GEARWHEEL,
            null, new ItemStack(Material.NETHER_BRICK_FENCE), null,
            null, new ItemStack(Material.DISPENSER), null
    };
    public static final ItemStack[] MANUAL_ANCIENT_ALTAR = new ItemStack[]{
            SlimefunItems.ANCIENT_PEDESTAL, null, SlimefunItems.ANCIENT_PEDESTAL,
            FinalTechItemStacks.GEARWHEEL, SlimefunItems.ANCIENT_ALTAR, FinalTechItemStacks.GEARWHEEL,
            SlimefunItems.ANCIENT_PEDESTAL, null, SlimefunItems.ANCIENT_PEDESTAL
    };
    public static final ItemStack[] MANUAL_HEATED_PRESSURE_CHAMBER = new ItemStack[]{
            FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL,
            null, SlimefunItems.HEATED_PRESSURE_CHAMBER, null,
            FinalTechItemStacks.GEARWHEEL, null, FinalTechItemStacks.GEARWHEEL
    };

    // basic machine
    public static final ItemStack[] BASIC_LOGIC_FACTORY = new ItemStack[]{
            FinalTechItemStacks.ORDERED_DUST_FACTORY_DIRT, FinalTechItemStacks.BUG, FinalTechItemStacks.ORDERED_DUST_FACTORY_DIRT,
            FinalTechItemStacks.BUG, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.BUG,
            FinalTechItemStacks.ORDERED_DUST_FACTORY_STONE, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ORDERED_DUST_FACTORY_STONE
    };

    // advanced machine
    public static final ItemStack[] ADVANCED_COMPOSTER = new ItemStack[]{
            FinalTechItemStacks.MANUAL_COMPOSTER, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.MANUAL_COMPOSTER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            FinalTechItemStacks.MANUAL_COMPOSTER, FinalTechItemStacks.ENERGY_CARD_K, FinalTechItemStacks.MANUAL_COMPOSTER
    };
    public static final ItemStack[] ADVANCED_JUICER = new ItemStack[]{
            FinalTechItemStacks.MANUAL_JUICER, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.MANUAL_JUICER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            FinalTechItemStacks.MANUAL_JUICER, FinalTechItemStacks.ENERGY_CARD_K, FinalTechItemStacks.MANUAL_JUICER
    };
    public static final ItemStack[] ADVANCED_FURNACE = new ItemStack[]{
            SlimefunItems.ELECTRIC_FURNACE, FinalTechItemStacks.ANNULAR, SlimefunItems.ELECTRIC_FURNACE,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            SlimefunItems.ELECTRIC_FURNACE, FinalTechItemStacks.ENERGY_CARD_K, SlimefunItems.ELECTRIC_FURNACE
    };
    public static final ItemStack[] ADVANCED_GOLD_PAN = new ItemStack[]{
            SlimefunItems.ELECTRIC_GOLD_PAN, FinalTechItemStacks.ANNULAR, SlimefunItems.ELECTRIC_GOLD_PAN,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            SlimefunItems.ELECTRIC_GOLD_PAN, FinalTechItemStacks.ENERGY_CARD_K, SlimefunItems.ELECTRIC_GOLD_PAN
    };
    public static final ItemStack[] ADVANCED_DUST_WASHER = new ItemStack[]{
            SlimefunItems.ELECTRIC_DUST_WASHER, FinalTechItemStacks.ANNULAR, SlimefunItems.ELECTRIC_DUST_WASHER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            SlimefunItems.ELECTRIC_DUST_WASHER, FinalTechItemStacks.ENERGY_CARD_K, SlimefunItems.ELECTRIC_DUST_WASHER
    };
    public static final ItemStack[] ADVANCED_INGOT_FACTORY = new ItemStack[]{
            SlimefunItems.ELECTRIC_INGOT_FACTORY, FinalTechItemStacks.ANNULAR, SlimefunItems.ELECTRIC_INGOT_FACTORY,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            SlimefunItems.ELECTRIC_INGOT_FACTORY, FinalTechItemStacks.ENERGY_CARD_K, SlimefunItems.ELECTRIC_INGOT_FACTORY
    };
    public static final ItemStack[] ADVANCED_CRUCIBLE = new ItemStack[]{
            SlimefunItems.ELECTRIFIED_CRUCIBLE, FinalTechItemStacks.ANNULAR, SlimefunItems.ELECTRIFIED_CRUCIBLE,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            SlimefunItems.ELECTRIFIED_CRUCIBLE, FinalTechItemStacks.ENERGY_CARD_K, SlimefunItems.ELECTRIFIED_CRUCIBLE
    };
    public static final ItemStack[] ADVANCED_ORE_GRINDER = new ItemStack[]{
            SlimefunItems.ELECTRIC_ORE_GRINDER, FinalTechItemStacks.ANNULAR, SlimefunItems.ELECTRIC_ORE_GRINDER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            SlimefunItems.ELECTRIC_ORE_GRINDER, FinalTechItemStacks.ENERGY_CARD_K, SlimefunItems.ELECTRIC_ORE_GRINDER
    };
    public static final ItemStack[] ADVANCED_HEATED_PRESSURE_CHAMBER = new ItemStack[]{
            SlimefunItems.HEATED_PRESSURE_CHAMBER, FinalTechItemStacks.ANNULAR, SlimefunItems.HEATED_PRESSURE_CHAMBER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            SlimefunItems.HEATED_PRESSURE_CHAMBER, FinalTechItemStacks.ENERGY_CARD_K, SlimefunItems.HEATED_PRESSURE_CHAMBER
    };
    public static final ItemStack[] ADVANCED_INGOT_PULVERIZER = new ItemStack[]{
            SlimefunItems.ELECTRIC_INGOT_PULVERIZER, FinalTechItemStacks.ANNULAR, SlimefunItems.ELECTRIC_INGOT_PULVERIZER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            SlimefunItems.ELECTRIC_INGOT_PULVERIZER, FinalTechItemStacks.ENERGY_CARD_K, SlimefunItems.ELECTRIC_INGOT_PULVERIZER
    };
    public static final ItemStack[] ADVANCED_AUTO_DRIER = new ItemStack[]{
            SlimefunItems.AUTO_DRIER, FinalTechItemStacks.ANNULAR, SlimefunItems.AUTO_DRIER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            SlimefunItems.AUTO_DRIER, FinalTechItemStacks.ENERGY_CARD_K, SlimefunItems.AUTO_DRIER
    };
    public static final ItemStack[] ADVANCED_PRESS = new ItemStack[]{
            SlimefunItems.ELECTRIC_PRESS, FinalTechItemStacks.ANNULAR, SlimefunItems.ELECTRIC_PRESS,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            SlimefunItems.ELECTRIC_PRESS, FinalTechItemStacks.ENERGY_CARD_K, SlimefunItems.ELECTRIC_PRESS
    };
    public static final ItemStack[] ADVANCED_FOOD_FACTORY = new ItemStack[]{
            SlimefunItems.FOOD_COMPOSTER, FinalTechItemStacks.ANNULAR, SlimefunItems.FOOD_COMPOSTER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            SlimefunItems.FOOD_FABRICATOR, FinalTechItemStacks.ENERGY_CARD_K, SlimefunItems.FOOD_FABRICATOR
    };
    public static final ItemStack[] ADVANCED_FREEZER = new ItemStack[]{
            SlimefunItems.FREEZER, FinalTechItemStacks.ANNULAR, SlimefunItems.FREEZER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            SlimefunItems.FREEZER, FinalTechItemStacks.ENERGY_CARD_K, SlimefunItems.FREEZER
    };
    public static final ItemStack[] ADVANCED_CARBON_PRESS = new ItemStack[]{
            SlimefunItems.CARBON_PRESS, FinalTechItemStacks.ANNULAR, SlimefunItems.CARBON_PRESS,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            SlimefunItems.CARBON_PRESS, FinalTechItemStacks.ENERGY_CARD_K, SlimefunItems.CARBON_PRESS
    };
    public static final ItemStack[] ADVANCED_SMELTERY = new ItemStack[]{
            SlimefunItems.ELECTRIC_SMELTERY, FinalTechItemStacks.ANNULAR, SlimefunItems.ELECTRIC_SMELTERY,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            SlimefunItems.ELECTRIC_SMELTERY, FinalTechItemStacks.ENERGY_CARD_K, SlimefunItems.ELECTRIC_SMELTERY
    };

    // conversion
    public static final ItemStack[] GRAVEL_CONVERSION = new ItemStack[]{
            FinalTechItemStacks.ADVANCED_GOLD_PAN, SlimefunItems.GOLD_PAN, FinalTechItemStacks.ADVANCED_GOLD_PAN,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER_MINER,
            FinalTechItemStacks.ADVANCED_GOLD_PAN, FinalTechItemStacks.BUG, FinalTechItemStacks.ADVANCED_GOLD_PAN
    };
    public static final ItemStack[] SOUL_SAND_CONVERSION = new ItemStack[]{
            FinalTechItemStacks.ADVANCED_GOLD_PAN, SlimefunItems.NETHER_GOLD_PAN, FinalTechItemStacks.ADVANCED_GOLD_PAN,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            FinalTechItemStacks.ADVANCED_GOLD_PAN, FinalTechItemStacks.BUG, FinalTechItemStacks.ADVANCED_GOLD_PAN
    };
    public static final ItemStack[] LOGIC_TO_DIGITAL_CONVERSION = new ItemStack[]{
            FinalTechItemStacks.BUG, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.BUG,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            FinalTechItemStacks.BUG, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.BUG
    };

    // extraction
    public static final ItemStack[] DIGITAL_EXTRACTION = new ItemStack[]{
            FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.BOX, FinalTechItemStacks.LOGIC_FALSE,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.LOGIC_TRUE
    };

    // generator
    public static final ItemStack[] LIQUID_CARD_GENERATOR = new ItemStack[]{
            new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.MILK_BUCKET),
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.GEARWHEEL, FinalTechItemStacks.ORDERED_DUST
    };
    public static final ItemStack[] LOGIC_GENERATOR = new ItemStack[]{
            FinalTechItemStacks.BUG, FinalTechItemStacks.SHINE, FinalTechItemStacks.BUG,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ETHER
    };
    public static final ItemStack[] DIGITAL_GENERATOR = new ItemStack[]{
            FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.BUG, FinalTechItemStacks.LOGIC_FALSE,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.BASIC_FRAME_MACHINE, FinalTechItemStacks.ETHER,
            FinalTechItemStacks.ETHER, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ETHER
    };

    /* final stage item */
    public static final ItemStack[] ENTROPY_SEED = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), FinalTechItemStacks.PHONY, ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), null
    };
    public static final ItemStack[] MATRIX_MACHINE_CHARGE_CARD = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, 64), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, 16), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItemStacks.PHONY, 2), null, ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L3, 4), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, 16), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_CHARGE_CARD_L1, 64), null
    };
    public static final ItemStack[] MATRIX_MACHINE_ACCELERATE_CARD = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, 64), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, 16), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItemStacks.PHONY, 2), null, ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L3, 4), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, 16), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1, 64), null
    };
    public static final ItemStack[] MATRIX_MACHINE_ACTIVATE_CARD = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, 64), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, 16), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItemStacks.PHONY, 2), null, ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 4), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 4), null, ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, 16), null,
            ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3, 4), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1, 64), null
    };
    public static final ItemStack[] MATRIX_QUANTITY_MODULE = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 16), FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.SHINE,
            FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ENERGIZED_QUANTITY_MODULE, FinalTechItemStacks.OVERLOADED_QUANTITY_MODULE, FinalTechItemStacks.OVERLOADED_QUANTITY_MODULE, FinalTechItemStacks.ENERGIZED_QUANTITY_MODULE, FinalTechItemStacks.ANNULAR,
            FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.OVERLOADED_QUANTITY_MODULE, FinalTechItemStacks.ENERGIZED_QUANTITY_MODULE, FinalTechItemStacks.ENERGIZED_QUANTITY_MODULE, FinalTechItemStacks.OVERLOADED_QUANTITY_MODULE, FinalTechItemStacks.ENERGY_CARD_B,
            FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.OVERLOADED_QUANTITY_MODULE, FinalTechItemStacks.ENERGIZED_QUANTITY_MODULE, FinalTechItemStacks.ENERGIZED_QUANTITY_MODULE, FinalTechItemStacks.OVERLOADED_QUANTITY_MODULE, FinalTechItemStacks.ENERGY_CARD_B,
            FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ENERGIZED_QUANTITY_MODULE, FinalTechItemStacks.OVERLOADED_QUANTITY_MODULE, FinalTechItemStacks.OVERLOADED_QUANTITY_MODULE, FinalTechItemStacks.ENERGIZED_QUANTITY_MODULE, FinalTechItemStacks.ANNULAR,
            FinalTechItemStacks.SHINE, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.SHINE
    };
    public static final ItemStack[] MATRIX_OPERATION_ACCELERATOR = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 16), ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 1), FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.ENERGY_CARD_B, ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 1), FinalTechItemStacks.BOX,
            ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGIZED_OPERATION_ACCELERATOR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.OVERLOADED_OPERATION_ACCELERATOR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.OVERLOADED_OPERATION_ACCELERATOR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGIZED_OPERATION_ACCELERATOR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 1),
            FinalTechItemStacks.ENERGY_CARD_B, ItemStackUtil.cloneItem(FinalTechItemStacks.OVERLOADED_OPERATION_ACCELERATOR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGIZED_OPERATION_ACCELERATOR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGIZED_OPERATION_ACCELERATOR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.OVERLOADED_OPERATION_ACCELERATOR, 1), FinalTechItemStacks.ENERGY_CARD_B,
            FinalTechItemStacks.ENERGY_CARD_B, ItemStackUtil.cloneItem(FinalTechItemStacks.OVERLOADED_OPERATION_ACCELERATOR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGIZED_OPERATION_ACCELERATOR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGIZED_OPERATION_ACCELERATOR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.OVERLOADED_OPERATION_ACCELERATOR, 1), FinalTechItemStacks.ENERGY_CARD_B,
            ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 1), FinalTechItemStacks.ENERGIZED_OPERATION_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.OVERLOADED_OPERATION_ACCELERATOR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.OVERLOADED_OPERATION_ACCELERATOR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGIZED_OPERATION_ACCELERATOR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 1),
            FinalTechItemStacks.BOX, ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 1), FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.ENERGY_CARD_B, ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 1), FinalTechItemStacks.BOX
    };

    public static final ItemStack[] ENTROPY_CONSTRUCTOR = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64),ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64),
            FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR,
            ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64),
            ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64),
            ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), FinalTechItemStacks.PHONY,
            FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64), FinalTechItemStacks.ANNULAR, FinalTechItemStacks.ANNULAR, ItemStackUtil.cloneItem(FinalTechItemStacks.ENTROPY, 64)
    };
    public static final ItemStack[] MATRIX_EXPANDED_CAPACITOR = new ItemStack[]{
            FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT,
            FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItemStacks.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItemStacks.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItemStacks.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItemStacks.EQUIVALENT_CONCEPT,
            FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItemStacks.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItemStacks.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItemStacks.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItemStacks.EQUIVALENT_CONCEPT,
            FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItemStacks.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItemStacks.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItemStacks.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItemStacks.EQUIVALENT_CONCEPT,
            FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItemStacks.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItemStacks.OVERLOADED_EXPANDED_CAPACITOR, FinalTechItemStacks.ENERGIZED_STACK_EXPANDED_CAPACITOR, FinalTechItemStacks.EQUIVALENT_CONCEPT,
            FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT
    };
    public static final ItemStack[] MATRIX_GENERATOR = new ItemStack[]{
            FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT,
            FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.ENERGIZED_STACK_GENERATOR, FinalTechItemStacks.OVERLOADED_GENERATOR, FinalTechItemStacks.OVERLOADED_GENERATOR, FinalTechItemStacks.ENERGIZED_STACK_GENERATOR, FinalTechItemStacks.EQUIVALENT_CONCEPT,
            FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.OVERLOADED_GENERATOR, FinalTechItemStacks.ENERGIZED_STACK_GENERATOR, FinalTechItemStacks.ENERGIZED_STACK_GENERATOR, FinalTechItemStacks.OVERLOADED_GENERATOR, FinalTechItemStacks.EQUIVALENT_CONCEPT,
            FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.OVERLOADED_GENERATOR, FinalTechItemStacks.ENERGIZED_STACK_GENERATOR, FinalTechItemStacks.ENERGIZED_STACK_GENERATOR, FinalTechItemStacks.OVERLOADED_GENERATOR, FinalTechItemStacks.EQUIVALENT_CONCEPT,
            FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.ENERGIZED_STACK_GENERATOR, FinalTechItemStacks.OVERLOADED_GENERATOR, FinalTechItemStacks.OVERLOADED_GENERATOR, FinalTechItemStacks.ENERGIZED_STACK_GENERATOR, FinalTechItemStacks.EQUIVALENT_CONCEPT,
            FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechItemStacks.EQUIVALENT_CONCEPT
    };
    public static final ItemStack[] ADVANCED_AUTO_CRAFT = new ItemStack[]{
            FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ENERGY_CARD_B,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.BOX, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.BOX, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.ADVANCED_AUTO_CRAFT_FRAME, FinalTechItemStacks.ADVANCED_AUTO_CRAFT_FRAME, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.ADVANCED_AUTO_CRAFT_FRAME, FinalTechItemStacks.ADVANCED_AUTO_CRAFT_FRAME, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.BOX, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.UNORDERED_DUST, FinalTechItemStacks.BOX, FinalTechItemStacks.ORDERED_DUST,
            FinalTechItemStacks.ENERGY_CARD_B, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ORDERED_DUST, FinalTechItemStacks.ENERGY_CARD_B
    };
    public static final ItemStack[] MATRIX_ITEM_DISMANTLE_TABLE = new ItemStack[]{
            FinalTechItemStacks.ENERGY_CARD_T, FinalTechItemStacks.AUTO_ITEM_DISMANTLE_TABLE, FinalTechItemStacks.AUTO_ITEM_DISMANTLE_TABLE, FinalTechItemStacks.AUTO_ITEM_DISMANTLE_TABLE, FinalTechItemStacks.AUTO_ITEM_DISMANTLE_TABLE, FinalTechItemStacks.ENERGY_CARD_T,
            FinalTechItemStacks.ITEM_DISMANTLE_TABLE, ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ITEM_DISMANTLE_TABLE, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ITEM_DISMANTLE_TABLE, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 64), FinalTechItemStacks.ITEM_DISMANTLE_TABLE,
            FinalTechItemStacks.ITEM_DISMANTLE_TABLE, ItemStackUtil.cloneItem(FinalTechItemStacks.ITEM_DISMANTLE_TABLE, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ITEM_DISMANTLE_TABLE, 1), FinalTechItemStacks.ITEM_DISMANTLE_TABLE,
            FinalTechItemStacks.ITEM_DISMANTLE_TABLE, ItemStackUtil.cloneItem(FinalTechItemStacks.ITEM_DISMANTLE_TABLE, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ITEM_DISMANTLE_TABLE, 1), FinalTechItemStacks.ITEM_DISMANTLE_TABLE,
            FinalTechItemStacks.ITEM_DISMANTLE_TABLE, ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 64), ItemStackUtil.cloneItem(FinalTechItemStacks.ITEM_DISMANTLE_TABLE, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ITEM_DISMANTLE_TABLE, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ANNULAR, 64), FinalTechItemStacks.ITEM_DISMANTLE_TABLE,
            FinalTechItemStacks.ENERGY_CARD_T, FinalTechItemStacks.AUTO_ITEM_DISMANTLE_TABLE, FinalTechItemStacks.AUTO_ITEM_DISMANTLE_TABLE, FinalTechItemStacks.AUTO_ITEM_DISMANTLE_TABLE, FinalTechItemStacks.AUTO_ITEM_DISMANTLE_TABLE, FinalTechItemStacks.ENERGY_CARD_T
    };
    public static final ItemStack[] MATRIX_ACCELERATOR = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8),
            ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8), FinalTechItemStacks.ENERGIZED_ACCELERATOR, FinalTechItemStacks.OVERLOADED_ACCELERATOR, FinalTechItemStacks.OVERLOADED_ACCELERATOR, FinalTechItemStacks.ENERGIZED_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8),
            ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8), FinalTechItemStacks.OVERLOADED_ACCELERATOR, FinalTechItemStacks.ENERGIZED_ACCELERATOR, FinalTechItemStacks.ENERGIZED_ACCELERATOR, FinalTechItemStacks.OVERLOADED_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8),
            ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8), FinalTechItemStacks.OVERLOADED_ACCELERATOR, FinalTechItemStacks.ENERGIZED_ACCELERATOR, FinalTechItemStacks.ENERGIZED_ACCELERATOR, FinalTechItemStacks.OVERLOADED_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8),
            ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8), FinalTechItemStacks.ENERGIZED_ACCELERATOR, FinalTechItemStacks.OVERLOADED_ACCELERATOR, FinalTechItemStacks.OVERLOADED_ACCELERATOR, FinalTechItemStacks.ENERGIZED_ACCELERATOR, ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8),
            ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 8),
    };
    public static final ItemStack[] MATRIX_ITEM_DESERIALIZE_PARSER = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGY_CARD_T, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGY_CARD_T, 1),
            ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), FinalTechItemStacks.BOX, null, null, FinalTechItemStacks.SHINE, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1),
            ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), null, FinalTechItemStacks.ITEM_DESERIALIZE_PARSER, FinalTechItemStacks.ITEM_DESERIALIZE_PARSER, null, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1),
            ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), null, FinalTechItemStacks.ITEM_DESERIALIZE_PARSER, FinalTechItemStacks.ITEM_DESERIALIZE_PARSER, null, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1),
            ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), FinalTechItemStacks.SHINE, null, null, FinalTechItemStacks.BOX, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1),
            ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGY_CARD_T, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGY_CARD_T, 1)
    };
    public static final ItemStack[] MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGY_CARD_T, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGY_CARD_T, 1),
            ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), FinalTechItemStacks.BOX, null, null, FinalTechItemStacks.SHINE, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1),
            ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), null, FinalTechItemStacks.ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechItemStacks.ITEM_SERIALIZATION_CONSTRUCTOR, null, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1),
            ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), null, FinalTechItemStacks.ITEM_SERIALIZATION_CONSTRUCTOR, FinalTechItemStacks.ITEM_SERIALIZATION_CONSTRUCTOR, null, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1),
            ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), FinalTechItemStacks.SHINE, null, null, FinalTechItemStacks.BOX, ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1),
            ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGY_CARD_T, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ETHER, 1), ItemStackUtil.cloneItem(FinalTechItemStacks.ENERGY_CARD_T, 1)
    };
    public static final ItemStack[] MATRIX_REACTOR = new ItemStack[]{
            ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32),
            ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32), FinalTechItems.COPY_CARD.getValidItem(FinalTechItemStacks.UNORDERED_DUST, "1"), FinalTechItems.COPY_CARD.getValidItem(FinalTechItemStacks.GEARWHEEL, "1"), FinalTechItems.COPY_CARD.getValidItem(FinalTechItemStacks.ORDERED_DUST, "1"), FinalTechItems.COPY_CARD.getValidItem(FinalTechItemStacks.UNORDERED_DUST, "1"), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32),
            ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32), FinalTechItems.COPY_CARD.getValidItem(FinalTechItemStacks.ORDERED_DUST, "1"), null, null, FinalTechItems.COPY_CARD.getValidItem(FinalTechItemStacks.GEARWHEEL, "1"), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32),
            ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32), FinalTechItems.COPY_CARD.getValidItem(FinalTechItemStacks.GEARWHEEL, "1"), null, null, FinalTechItems.COPY_CARD.getValidItem(FinalTechItemStacks.ORDERED_DUST, "1"), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32),
            ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32), FinalTechItems.COPY_CARD.getValidItem(FinalTechItemStacks.UNORDERED_DUST, "1"), FinalTechItems.COPY_CARD.getValidItem(FinalTechItemStacks.ORDERED_DUST, "1"), FinalTechItems.COPY_CARD.getValidItem(FinalTechItemStacks.GEARWHEEL, "1"), FinalTechItems.COPY_CARD.getValidItem(FinalTechItemStacks.UNORDERED_DUST, "1"), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32),
            ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32), ItemStackUtil.cloneItem(FinalTechItemStacks.EQUIVALENT_CONCEPT, 32)
    };
}
