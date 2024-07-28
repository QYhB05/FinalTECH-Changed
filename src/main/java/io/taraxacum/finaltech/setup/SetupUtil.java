package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.common.util.ReflectionUtil;
import io.taraxacum.common.util.StringUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.command.ShowItemInfo;
import io.taraxacum.finaltech.core.command.TransformToCopyCardItem;
import io.taraxacum.finaltech.core.command.TransformToStorageItem;
import io.taraxacum.finaltech.core.command.TransformToValidItem;
import io.taraxacum.finaltech.core.enchantment.NullEnchantment;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.plugin.dto.ConfigFileManager;
import io.taraxacum.libs.plugin.dto.LanguageManager;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.TextUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import io.taraxacum.libs.slimefun.interfaces.SimpleValidItem;
import io.taraxacum.libs.slimefun.util.ResearchUtil;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public final class SetupUtil {
    private static final List<String> BELIEVABLE_PLUGIN_ID_LIST = new ArrayList<>();

    public static void setupLanguageManager(@Nonnull LanguageManager languageManager) {
        // Color normal
        languageManager.addFunction(new Function<>() {
            @Override
            public String apply(String s) {
                String[] split = StringUtil.split(s, "{color:", "}");
                if (split.length == 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(split[0]);
                    switch (split[1]) {
                        case "normal" -> stringBuilder.append(TextUtil.COLOR_NORMAL);
                        case "stress" -> stringBuilder.append(TextUtil.COLOR_STRESS);
                        case "action" -> stringBuilder.append(TextUtil.COLOR_ACTION);
                        case "initiative" -> stringBuilder.append(TextUtil.COLOR_INITIATIVE);
                        case "passive" -> stringBuilder.append(TextUtil.COLOR_PASSIVE);
                        case "number" -> stringBuilder.append(TextUtil.COLOR_NUMBER);
                        case "positive" -> stringBuilder.append(TextUtil.COLOR_POSITIVE);
                        case "negative" -> stringBuilder.append(TextUtil.COLOR_NEGATIVE);
                        case "conceal" -> stringBuilder.append(TextUtil.COLOR_CONCEAL);
                        case "input" -> stringBuilder.append(TextUtil.COLOR_INPUT);
                        case "output" -> stringBuilder.append(TextUtil.COLOR_OUTPUT);
                        case "random" -> stringBuilder.append(TextUtil.getRandomColor());
                        case "prandom" -> stringBuilder.append(TextUtil.getPseudorandomColor(FinalTechChanged.getSeed()));
                        default -> stringBuilder.append(split[1]);
                    }
                    return stringBuilder.append(this.apply(split[2])).toString();
                } else {
                    return s;
                }
            }
        });
        // SlimefunItem name by id
        languageManager.addFunction(new Function<>() {
            @Override
            public String apply(String s) {
                String[] split = StringUtil.split(s, "{id:", "}");
                if (split.length == 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(split[0]);
                    SlimefunItem slimefunItem = SlimefunItem.getById(split[1]);
                    if (slimefunItem != null) {
                        stringBuilder.append(slimefunItem.getItemName());
                    } else {
                        stringBuilder.append(split[1]);
                    }
                    return stringBuilder.append(this.apply(split[2])).toString();
                } else {
                    return s;
                }
            }
        });
        // Color random
        languageManager.addFunction(new Function<>() {
            @Override
            public String apply(String s) {
                String[] split = StringUtil.split(s, "{random-color:start}", "{random-color:end}");
                if (split.length == 3) {
                    return split[0] + TextUtil.colorRandomString(split[1]) + this.apply(split[2]);
                } else {
                    return s;
                }
            }
        });
    }

    private static void setupEnchantment() {
        try {


            // material
            NullEnchantment.addAndHidden(FinalTechItemStacks.UNORDERED_DUST);
            NullEnchantment.addAndHidden(FinalTechItemStacks.ORDERED_DUST);
            NullEnchantment.addAndHidden(FinalTechItemStacks.BUG);
            NullEnchantment.addAndHidden(FinalTechItemStacks.ETHER);
            NullEnchantment.addAndHidden(FinalTechItemStacks.BOX);
            NullEnchantment.addAndHidden(FinalTechItemStacks.SHINE);
            NullEnchantment.addAndHidden(FinalTechItemStacks.ENTROPY);
            NullEnchantment.addAndHidden(FinalTechItemStacks.COPY_CARD);
            NullEnchantment.addAndHidden(FinalTechItemStacks.ANNULAR);
            NullEnchantment.addAndHidden(FinalTechItemStacks.SINGULARITY);
            NullEnchantment.addAndHidden(FinalTechItemStacks.SPIROCHETE);
            NullEnchantment.addAndHidden(FinalTechItemStacks.SHELL);
            NullEnchantment.addAndHidden(FinalTechItemStacks.PHONY);
            NullEnchantment.addAndHidden(FinalTechItemStacks.JUSTIFIABILITY);
            NullEnchantment.addAndHidden(FinalTechItemStacks.EQUIVALENT_CONCEPT);

            // machine card
            NullEnchantment.addAndHidden(FinalTechItemStacks.MACHINE_CHARGE_CARD_L2);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MACHINE_CHARGE_CARD_L3);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L3);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3);

            // advanced quantity module
            NullEnchantment.addAndHidden(FinalTechItemStacks.ENERGIZED_QUANTITY_MODULE);
            NullEnchantment.addAndHidden(FinalTechItemStacks.OVERLOADED_QUANTITY_MODULE);

            // advanced operation accelerator
            NullEnchantment.addAndHidden(FinalTechItemStacks.ENERGIZED_OPERATION_ACCELERATOR);
            NullEnchantment.addAndHidden(FinalTechItemStacks.OVERLOADED_OPERATION_ACCELERATOR);

            // advanced cargo
            NullEnchantment.addAndHidden(FinalTechItemStacks.ADVANCED_POINT_TRANSFER);
            NullEnchantment.addAndHidden(FinalTechItemStacks.ADVANCED_MESH_TRANSFER);
            NullEnchantment.addAndHidden(FinalTechItemStacks.ADVANCED_LINE_TRANSFER);
            NullEnchantment.addAndHidden(FinalTechItemStacks.ADVANCED_LOCATION_TRANSFER);

            // final stage items
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_QUANTITY_MODULE);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_OPERATION_ACCELERATOR);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_MACHINE_CHARGE_CARD);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_MACHINE_ACCELERATE_CARD);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_MACHINE_ACTIVATE_CARD);
            NullEnchantment.addAndHidden(FinalTechItemStacks.ADVANCED_AUTO_CRAFT);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_ITEM_DISMANTLE_TABLE);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_EXPANDED_CAPACITOR);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_ITEM_DESERIALIZE_PARSER);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_GENERATOR);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_ACCELERATOR);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR);
            NullEnchantment.addAndHidden(FinalTechItemStacks.MATRIX_REACTOR);
        } catch (Exception e) {
            e.printStackTrace();
            FinalTechChanged.logger().warning("Some error occurred while registering enchantment");
        }
    }

    public static void setupItem() {
        ItemStackUtil.setLore(FinalTechItemStacks.TROPHY_MEAWERFUL,
                "§fThanks for some good idea");
        ItemStackUtil.setLore(FinalTechItemStacks.TROPHY_SHIXINZIA,
                "§fThanks for some test work");

        ItemStackUtil.addItemFlag(FinalTechItemStacks.STORAGE_CARD, ItemFlag.HIDE_ATTRIBUTES);

        /* items */
        // material
        FinalTechMenus.SUB_MENU_MATERIAL.addTo(
                FinalTechItems.GEARWHEEL.registerThis(),
                FinalTechItems.UNORDERED_DUST.registerThis(),
                FinalTechItems.ORDERED_DUST.registerThis(),
                FinalTechItems.BUG.registerThis(),
                FinalTechItems.ETHER.registerThis(),
                FinalTechItems.BOX.registerThis(),
                FinalTechItems.SHINE.registerThis(),
                FinalTechItems.ENTROPY.registerThis());
        FinalTechMenus.SUB_MENU_MATERIAL.addTo(
                FinalTechItems.COPY_CARD.registerThis(),
                FinalTechItems.ANNULAR.registerThis(),
                FinalTechItems.SINGULARITY.registerThis(),
                FinalTechItems.SPIROCHETE.registerThis(),
                FinalTechItems.SHELL.registerThis(),
                FinalTechItems.ITEM_PHONY.registerThis(),
                FinalTechItems.JUSTIFIABILITY.registerThis(),
                FinalTechItems.EQUIVALENT_CONCEPT.registerThis());
        FinalTechChanged.getInstance().getLogger().warning("Maybe something caused an exception, but it actually couldn't make any difference in working;)");
        FinalTechChanged.getInstance().getLogger().warning("可能出现了一些异常但是事实上这并不影响插件运行与工作, 请忽略它!");
        FinalTechMenus.SUB_MENU_MATERIAL.addTo(
                FinalTechItems.WATER_CARD.registerThis(),
                FinalTechItems.LAVA_CARD.registerThis(),
                FinalTechItems.MILK_CARD.registerThis(),
                FinalTechItems.FLINT_AND_STEEL_CARD.registerThis(),
                FinalTechItems.QUANTITY_MODULE.registerThis(),
                FinalTechItems.ENERGIZED_QUANTITY_MODULE.registerThis(),
                FinalTechItems.OVERLOADED_QUANTITY_MODULE.registerThis());
        // logic item
        FinalTechMenus.SUB_MENU_LOGIC_ITEM.addTo(
                FinalTechItems.LOGIC_FALSE.registerThis(),
                FinalTechItems.LOGIC_TRUE.registerThis(),
                FinalTechItems.DIGITAL_ZERO.registerThis(),
                FinalTechItems.DIGITAL_ONE.registerThis(),
                FinalTechItems.DIGITAL_TWO.registerThis(),
                FinalTechItems.DIGITAL_THREE.registerThis(),
                FinalTechItems.DIGITAL_FOUR.registerThis(),
                FinalTechItems.DIGITAL_FIVE.registerThis(),
                FinalTechItems.DIGITAL_SIX.registerThis(),
                FinalTechItems.DIGITAL_SEVEN.registerThis(),
                FinalTechItems.DIGITAL_EIGHT.registerThis(),
                FinalTechItems.DIGITAL_NINE.registerThis(),
                FinalTechItems.DIGITAL_TEN.registerThis(),
                FinalTechItems.DIGITAL_ELEVEN.registerThis(),
                FinalTechItems.DIGITAL_TWELVE.registerThis(),
                FinalTechItems.DIGITAL_THIRTEEN.registerThis(),
                FinalTechItems.DIGITAL_FOURTEEN.registerThis(),
                FinalTechItems.DIGITAL_FIFTEEN.registerThis());
        // tool
        FinalTechMenus.SUB_MENU_TOOL.addTo(
                FinalTechItems.POTION_EFFECT_COMPRESSOR.registerThis(),
                FinalTechItems.POTION_EFFECT_DILATOR.registerThis(),
                FinalTechItems.POTION_EFFECT_PURIFIER.registerThis(),
                FinalTechItems.GRAVITY_REVERSAL_RUNE.registerThis(),
                FinalTechItems.STAFF_ELEMENTAL_LINE.registerThis());
        FinalTechMenus.SUB_MENU_TOOL.addTo(
                FinalTechItems.MENU_VIEWER.registerThis(),
                FinalTechItems.ROUTE_VIEWER.registerThis(),
                FinalTechItems.LOCATION_RECORDER.registerThis(),
                FinalTechItems.MACHINE_CONFIGURATOR.registerThis(),
                FinalTechItems.PORTABLE_ENERGY_STORAGE.registerThis(),
                FinalTechItems.ENTROPY_CLEANER.registerThis());
        // consumable
        FinalTechMenus.SUB_MENU_CONSUMABLE.addTo(
                FinalTechItems.MACHINE_CHARGE_CARD_L1.registerThis(),
                FinalTechItems.MACHINE_CHARGE_CARD_L2.registerThis(),
                FinalTechItems.MACHINE_CHARGE_CARD_L3.registerThis(),
                FinalTechItems.MACHINE_ACCELERATE_CARD_L1.registerThis(),
                FinalTechItems.MACHINE_ACCELERATE_CARD_L2.registerThis(),
                FinalTechItems.MACHINE_ACCELERATE_CARD_L3.registerThis(),
                FinalTechItems.MACHINE_ACTIVATE_CARD_L1.registerThis(),
                FinalTechItems.MACHINE_ACTIVATE_CARD_L2.registerThis(),
                FinalTechItems.MACHINE_ACTIVATE_CARD_L3.registerThis());
        FinalTechMenus.SUB_MENU_CONSUMABLE.addTo(
                FinalTechItems.ENERGY_CARD_K.registerThis(),
                FinalTechItems.ENERGY_CARD_M.registerThis(),
                FinalTechItems.ENERGY_CARD_B.registerThis(),
                FinalTechItems.ENERGY_CARD_T.registerThis(),
                FinalTechItems.ENERGY_CARD_Q.registerThis());
        FinalTechMenus.SUB_MENU_CONSUMABLE.addTo(
                FinalTechItems.MAGIC_HYPNOTIC.registerThis(),
                FinalTechItems.RESEARCH_UNLOCK_TICKET.registerThis());
        // weapon
        FinalTechMenus.SUB_MENU_WEAPON.addTo(
                FinalTechItems.SUPER_SHOVEL.registerThis(),
                FinalTechItems.ULTIMATE_SHOVEL.registerThis(),
                FinalTechItems.SUPER_PICKAXE.registerThis(),
                FinalTechItems.ULTIMATE_PICKAXE.registerThis(),
                FinalTechItems.SUPER_AXE.registerThis(),
                FinalTechItems.ULTIMATE_AXE.registerThis(),
                FinalTechItems.SUPER_HOE.registerThis(),
                FinalTechItems.ULTIMATE_HOE.registerThis()
        );

        /* electricity system */
        // electric generator
        FinalTechMenus.SUB_MENU_ELECTRIC_GENERATOR.addTo(
                FinalTechItems.BASIC_GENERATOR.registerThis(),
                FinalTechItems.ADVANCED_GENERATOR.registerThis(),
                FinalTechItems.CARBONADO_GENERATOR.registerThis(),
                FinalTechItems.ENERGIZED_GENERATOR.registerThis(),
                FinalTechItems.ENERGIZED_STACK_GENERATOR.registerThis(),
                FinalTechItems.OVERLOADED_GENERATOR.registerThis());
        FinalTechMenus.SUB_MENU_ELECTRIC_GENERATOR.addTo(
                FinalTechItems.DUST_GENERATOR.registerThis(),
                FinalTechItems.ENERGIZED_CHARGE_BASE.registerThis(),
                FinalTechItems.OVERLOADED_CHARGE_BASE.registerThis());
        // electric storage
        FinalTechMenus.SUB_MENU_ELECTRIC_STORAGE.addTo(
                FinalTechItems.SMALL_EXPANDED_CAPACITOR.registerThis(),
                FinalTechItems.MEDIUM_EXPANDED_CAPACITOR.registerThis(),
                FinalTechItems.BIG_EXPANDED_CAPACITOR.registerThis(),
                FinalTechItems.LARGE_EXPANDED_CAPACITOR.registerThis(),
                FinalTechItems.CARBONADO_EXPANDED_CAPACITOR.registerThis(),
                FinalTechItems.ENERGIZED_EXPANDED_CAPACITOR.registerThis());
        FinalTechMenus.SUB_MENU_ELECTRIC_STORAGE.addTo(
                FinalTechItems.ENERGIZED_STACK_EXPANDED_CAPACITOR.registerThis(),
                FinalTechItems.OVERLOADED_EXPANDED_CAPACITOR.registerThis());
        // electric transmission
        FinalTechMenus.SUB_MENU_ELECTRIC_TRANSMISSION.addTo(
                FinalTechItems.NORMAL_ELECTRICITY_SHOOT_PILE.registerThis(),
                FinalTechItems.ENERGIZED_ELECTRICITY_SHOOT_PILE.registerThis(),
                FinalTechItems.OVERLOADED_ELECTRICITY_SHOOT_PILE.registerThis());
        // electric accelerator
        FinalTechMenus.SUB_MENU_ELECTRIC_ACCELERATOR.addTo(
                FinalTechItems.ENERGIZED_ACCELERATOR.registerThis(),
                FinalTechItems.OVERLOADED_ACCELERATOR.registerThis());

        /* cargo system */
        // storage unit
        FinalTechMenus.SUB_MENU_STORAGE_UNIT.addTo(
                FinalTechItems.NORMAL_STORAGE_UNIT.registerThis(),
                FinalTechItems.DIVIDED_STORAGE_UNIT.registerThis(),
                FinalTechItems.LIMITED_STORAGE_UNIT.registerThis(),
                FinalTechItems.STACK_STORAGE_UNIT.registerThis(),
                FinalTechItems.DIVIDED_LIMITED_STORAGE_UNIT.registerThis(),
                FinalTechItems.DIVIDED_STACK_STORAGE_UNIT.registerThis(),
                FinalTechItems.LIMITED_STACK_STORAGE_UNIT.registerThis());
        FinalTechMenus.SUB_MENU_STORAGE_UNIT.addTo(
                FinalTechItems.RANDOM_INPUT_STORAGE_UNIT.registerThis(),
                FinalTechItems.RANDOM_OUTPUT_STORAGE_UNIT.registerThis(),
                FinalTechItems.RANDOM_ACCESS_STORAGE_UNIT.registerThis(),
                FinalTechItems.DISTRIBUTE_LEFT_STORAGE_UNIT.registerThis(),
                FinalTechItems.DISTRIBUTE_RIGHT_STORAGE_UNIT.registerThis());
        // advanced storage
        FinalTechMenus.SUB_MENU_ADVANCED_STORAGE.addTo(
                FinalTechItems.STORAGE_INTERACT_PORT.registerThis(),
                FinalTechItems.STORAGE_INSERT_PORT.registerThis(),
                FinalTechItems.STORAGE_WITHDRAW_PORT.registerThis(),
                FinalTechItems.STORAGE_CARD.registerThis());
        // accessor
        FinalTechMenus.SUB_MENU_ACCESSOR.addTo(
                FinalTechItems.REMOTE_ACCESSOR.registerThis(),
                FinalTechItems.CONSUMABLE_REMOTE_ACCESSOR.registerThis(),
                FinalTechItems.CONFIGURABLE_REMOTE_ACCESSOR.registerThis(),
                FinalTechItems.EXPANDED_CONSUMABLE_REMOTE_ACCESSOR.registerThis(),
                FinalTechItems.EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR.registerThis(),
                FinalTechItems.RANDOM_ACCESSOR.registerThis()
        );
        FinalTechMenus.SUB_MENU_ACCESSOR.addTo(
                FinalTechItems.TRANSPORTER.registerThis(),
                FinalTechItems.CONSUMABLE_TRANSPORTER.registerThis(),
                FinalTechItems.CONFIGURABLE_TRANSPORTER.registerThis(),
                FinalTechItems.EXPANDED_CONSUMABLE_TRANSPORTER.registerThis(),
                FinalTechItems.EXPANDED_CONFIGURABLE_TRANSPORTER.registerThis());
        // logic
        FinalTechMenus.SUB_MENU_LOGIC.addTo(
                FinalTechItems.LOGIC_COMPARATOR_NOTNULL.registerThis(),
                FinalTechItems.LOGIC_COMPARATOR_AMOUNT.registerThis(),
                FinalTechItems.LOGIC_COMPARATOR_SIMILAR.registerThis(),
                FinalTechItems.LOGIC_COMPARATOR_EQUAL.registerThis(),
                FinalTechItems.LOGIC_CRAFTER.registerThis(),
                FinalTechItems.DIGIT_ADDER.registerThis());
        // cargo
        FinalTechMenus.SUB_MENU_CARGO.addTo(
                FinalTechItems.BASIC_FRAME_MACHINE.registerThis(),
                FinalTechItems.POINT_TRANSFER.registerThis(),
                FinalTechItems.MESH_TRANSFER.registerThis(),
                FinalTechItems.LINE_TRANSFER.registerThis(),
                FinalTechItems.LOCATION_TRANSFER.registerThis(),
                FinalTechItems.ADVANCED_POINT_TRANSFER.registerThis(),
                FinalTechItems.ADVANCED_MESH_TRANSFER.registerThis(),
                FinalTechItems.ADVANCED_LINE_TRANSFER.registerThis(),
                FinalTechItems.ADVANCED_LOCATION_TRANSFER.registerThis());
        FinalTechMenus.SUB_MENU_CARGO.addTo(
                FinalTechItems.CONFIGURATION_COPIER.registerThis(),
                FinalTechItems.CONFIGURATION_PASTER.registerThis(),
                FinalTechItems.CLICK_WORK_MACHINE.registerThis());

        /* functional machines */
        // core machines
        FinalTechMenus.SUB_MENU_CORE_MACHINE.addTo(
                FinalTechItems.BEDROCK_CRAFT_TABLE.register(),
                FinalTechItems.MATRIX_CRAFTING_TABLE.registerThis(),
                FinalTechItems.CARD_OPERATION_TABLE.registerThis());
        FinalTechMenus.SUB_MENU_CORE_MACHINE.addTo(
                FinalTechItems.DUST_FACTORY_DIRT.registerThis(),
                FinalTechItems.DUST_FACTORY_STONE.registerThis(),
                FinalTechItems.EQUIVALENT_EXCHANGE_TABLE.registerThis(),
                FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.registerThis(),
                FinalTechItems.ITEM_DESERIALIZE_PARSER.registerThis(),
                FinalTechItems.ETHER_MINER.registerThis());
        FinalTechMenus.SUB_MENU_CORE_MACHINE.addTo(
                FinalTechItems.ENERGY_TABLE.registerThis(),
                FinalTechItems.ENERGY_INPUT_TABLE.registerThis(),
                FinalTechItems.ENERGY_OUTPUT_TABLE.registerThis(),
                FinalTechItems.ITEM_DISMANTLE_TABLE.registerThis(),
                FinalTechItems.AUTO_ITEM_DISMANTLE_TABLE.registerThis(),
                FinalTechItems.ADVANCED_AUTO_CRAFT_FRAME.registerThis(),
                FinalTechItems.MULTI_FRAME_MACHINE.registerThis());

        // special machines
        FinalTechMenus.SUB_MENU_SPECIAL_MACHINE.addTo(
                FinalTechItems.ITEM_FIXER.registerThis(),
                FinalTechItems.COBBLESTONE_FACTORY.registerThis(),
                FinalTechItems.FUEL_CHARGER.registerThis(),
                FinalTechItems.FUEL_ACCELERATOR.registerThis(),
                FinalTechItems.FUEL_OPERATOR.registerThis(),
                FinalTechItems.OPERATION_ACCELERATOR.registerThis(),
                FinalTechItems.ENERGIZED_OPERATION_ACCELERATOR.registerThis(),
                FinalTechItems.OVERLOADED_OPERATION_ACCELERATOR.registerThis());
        // tower
        FinalTechMenus.SUB_MENU_TOWER.addTo(
                FinalTechItems.CURE_TOWER.registerThis(),
                FinalTechItems.PURIFY_LEVEL_TOWER.registerThis(),
                FinalTechItems.PURIFY_TIME_TOWER.registerThis());

        /* productive machine */
        // manual machine
        FinalTechMenus.SUB_MENU_MANUAL_MACHINE.addTo(
                FinalTechItems.MANUAL_CRAFT_MACHINE,
                FinalTechItems.MANUAL_CRAFTING_TABLE.registerThis(),
                FinalTechItems.MANUAL_ENHANCED_CRAFTING_TABLE.registerThis(),
                FinalTechItems.MANUAL_GRIND_STONE.registerThis(),
                FinalTechItems.MANUAL_ARMOR_FORGE.registerThis(),
                FinalTechItems.MANUAL_ORE_CRUSHER.registerThis(),
                FinalTechItems.MANUAL_COMPRESSOR.registerThis(),
                FinalTechItems.MANUAL_SMELTERY.registerThis(),
                FinalTechItems.MANUAL_PRESSURE_CHAMBER.registerThis(),
                FinalTechItems.MANUAL_MAGIC_WORKBENCH.registerThis(),
                FinalTechItems.MANUAL_ORE_WASHER.registerThis(),
                FinalTechItems.MANUAL_COMPOSTER.registerThis(),
                FinalTechItems.MANUAL_GOLD_PAN.registerThis(),
                FinalTechItems.MANUAL_CRUCIBLE.registerThis(),
                FinalTechItems.MANUAL_JUICER.registerThis(),
                FinalTechItems.MANUAL_ANCIENT_ALTAR.registerThis(),
                FinalTechItems.MANUAL_HEATED_PRESSURE_CHAMBER.registerThis());
        // basic machines
        FinalTechMenus.SUB_MENU_BASIC_MACHINE.addTo(
                FinalTechItems.BASIC_LOGIC_FACTORY.registerThis());
        // advanced machine
        FinalTechMenus.SUB_MENU_ADVANCED_MACHINE.addTo(
                FinalTechItems.ADVANCED_COMPOSTER.registerThis(),
                FinalTechItems.ADVANCED_JUICER.registerThis(),
                FinalTechItems.ADVANCED_FURNACE.registerThis(),
                FinalTechItems.ADVANCED_GOLD_PAN.registerThis(),
                FinalTechItems.ADVANCED_DUST_WASHER.registerThis(),
                FinalTechItems.ADVANCED_INGOT_FACTORY.registerThis(),
                FinalTechItems.ADVANCED_CRUCIBLE.registerThis(),
                FinalTechItems.ADVANCED_ORE_GRINDER.registerThis(),
                FinalTechItems.ADVANCED_HEATED_PRESSURE_CHAMBER.registerThis(),
                FinalTechItems.ADVANCED_INGOT_PULVERIZER.registerThis(),
                FinalTechItems.ADVANCED_AUTO_DRIER.registerThis(),
                FinalTechItems.ADVANCED_PRESS.registerThis(),
                FinalTechItems.ADVANCED_FOOD_FACTORY.registerThis(),
                FinalTechItems.ADVANCED_FREEZER.registerThis(),
                FinalTechItems.ADVANCED_CARBON_PRESS.registerThis(),
                FinalTechItems.ADVANCED_SMELTERY.registerThis());
        // conversion
        FinalTechMenus.SUB_MENU_CONVERSION.addTo(
                FinalTechItems.GRAVEL_CONVERSION.registerThis(),
                FinalTechItems.SOUL_SAND_CONVERSION.registerThis(),
                FinalTechItems.LOGIC_TO_DIGITAL_CONVERSION.registerThis());
        // extraction
        FinalTechMenus.SUB_MENU_EXTRACTION.addTo(
                FinalTechItems.DIGITAL_EXTRACTION.registerThis());
        // generator
        FinalTechMenus.SUB_MENU_GENERATOR.addTo(
                FinalTechItems.LIQUID_CARD_GENERATOR.registerThis(),
                FinalTechItems.LOGIC_GENERATOR.registerThis(),
                FinalTechItems.DIGITAL_GENERATOR.registerThis());

        /* final stage item */
        FinalTechMenus.SUB_MENU_FINAL_ITEM.addTo(
                FinalTechItems.ENTROPY_SEED.registerThis(),
                FinalTechItems.MATRIX_MACHINE_CHARGE_CARD.registerThis(),
                FinalTechItems.MATRIX_MACHINE_ACCELERATE_CARD.registerThis(),
                FinalTechItems.MATRIX_MACHINE_ACTIVATE_CARD.registerThis(),
                FinalTechItems.MATRIX_QUANTITY_MODULE.registerThis(),
                FinalTechItems.MATRIX_OPERATION_ACCELERATOR.registerThis());
        FinalTechMenus.SUB_MENU_FINAL_ITEM.addTo(
                FinalTechItems.ENTROPY_CONSTRUCTOR.registerThis(),
                FinalTechItems.MATRIX_EXPANDED_CAPACITOR.registerThis(),
                FinalTechItems.MATRIX_GENERATOR.registerThis(),
                FinalTechItems.ADVANCED_AUTO_CRAFT.registerThis(),
                FinalTechItems.MATRIX_ITEM_DISMANTLE_TABLE.registerThis(),
                FinalTechItems.MATRIX_ACCELERATOR.registerThis(),
                FinalTechItems.MATRIX_ITEM_DESERIALIZE_PARSER.registerThis(),
                FinalTechItems.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR.registerThis(),
                FinalTechItems.MATRIX_REACTOR.registerThis());
        FinalTechMenus.SUB_MENU_TROPHY.addTo(
                FinalTechItems.TROPHY_MEAWERFUL,
                FinalTechItems.TROPHY_SHIXINZIA,
                FinalTechItems.TROPHY_QY);
    }

    private static void setupMenu() {
        FinalTechChanged finalTech = FinalTechChanged.getInstance();

        FinalTechMenus.MAIN_MENU.setTier(0);

        FinalTechMenus.MENU_ITEMS.setTier(0);
        FinalTechMenus.MENU_ELECTRICITY_SYSTEM.setTier(0);
        FinalTechMenus.MENU_CARGO_SYSTEM.setTier(0);
        FinalTechMenus.MENU_FUNCTIONAL_MACHINE.setTier(0);
        FinalTechMenus.MENU_PRODUCTIVE_MACHINE.setTier(0);
        FinalTechMenus.MENU_DISC.setTier(0);

        for (SlimefunItem slimefunItem : FinalTechMenus.SUB_MENU_DEPRECATED.getSlimefunItems()) {
            try {
                Class<SlimefunItem> clazz = SlimefunItem.class;
                Field declaredField = clazz.getDeclaredField("blockTicker");
                declaredField.setAccessible(true);
                declaredField.set(slimefunItem, null);
                declaredField.setAccessible(false);

                Field ticking = clazz.getDeclaredField("ticking");
                ticking.setAccessible(true);
                ticking.set(slimefunItem, false);
                ticking.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        /* Menus */
        // item
        FinalTechMenus.MAIN_ITEM_GROUP.addTo(FinalTechMenus.MAIN_MENU_ITEM,
                FinalTechMenus.SUB_MENU_MATERIAL,
                FinalTechMenus.SUB_MENU_LOGIC_ITEM,
                FinalTechMenus.SUB_MENU_TOOL,
                FinalTechMenus.SUB_MENU_CONSUMABLE,
                FinalTechMenus.SUB_MENU_WEAPON);
        FinalTechMenus.MAIN_MENU_ITEM.addFrom(
                FinalTechMenus.SUB_MENU_MATERIAL,
                FinalTechMenus.SUB_MENU_LOGIC_ITEM,
                FinalTechMenus.SUB_MENU_CONSUMABLE,
                FinalTechMenus.SUB_MENU_TOOL,
                FinalTechMenus.SUB_MENU_WEAPON);
        // electricity system
        FinalTechMenus.MAIN_ITEM_GROUP.addTo(FinalTechMenus.MAIN_MENU_ELECTRICITY_SYSTEM,
                FinalTechMenus.SUB_MENU_ELECTRIC_GENERATOR,
                FinalTechMenus.SUB_MENU_ELECTRIC_STORAGE,
                FinalTechMenus.SUB_MENU_ELECTRIC_TRANSMISSION,
                FinalTechMenus.SUB_MENU_ELECTRIC_ACCELERATOR);
        FinalTechMenus.MAIN_MENU_ELECTRICITY_SYSTEM.addFrom(
                FinalTechMenus.SUB_MENU_ELECTRIC_GENERATOR,
                FinalTechMenus.SUB_MENU_ELECTRIC_STORAGE,
                FinalTechMenus.SUB_MENU_ELECTRIC_TRANSMISSION,
                FinalTechMenus.SUB_MENU_ELECTRIC_ACCELERATOR);
        // cargo system
        FinalTechMenus.MAIN_ITEM_GROUP.addTo(FinalTechMenus.MAIN_MENU_CARGO_SYSTEM,
                FinalTechMenus.SUB_MENU_STORAGE_UNIT,
                FinalTechMenus.SUB_MENU_ADVANCED_STORAGE,
                FinalTechMenus.SUB_MENU_ACCESSOR,
                FinalTechMenus.SUB_MENU_LOGIC,
                FinalTechMenus.SUB_MENU_CARGO);
        FinalTechMenus.MAIN_MENU_CARGO_SYSTEM.addFrom(
                FinalTechMenus.SUB_MENU_STORAGE_UNIT,
                FinalTechMenus.SUB_MENU_ADVANCED_STORAGE,
                FinalTechMenus.SUB_MENU_ACCESSOR,
                FinalTechMenus.SUB_MENU_LOGIC,
                FinalTechMenus.SUB_MENU_CARGO);
        // functional machine
        FinalTechMenus.MAIN_ITEM_GROUP.addTo(FinalTechMenus.MAIN_MENU_FUNCTIONAL_MACHINE,
                FinalTechMenus.SUB_MENU_CORE_MACHINE,
                FinalTechMenus.SUB_MENU_SPECIAL_MACHINE,
                FinalTechMenus.SUB_MENU_TOWER);
        FinalTechMenus.MAIN_MENU_FUNCTIONAL_MACHINE.addFrom(
                FinalTechMenus.SUB_MENU_CORE_MACHINE,
                FinalTechMenus.SUB_MENU_SPECIAL_MACHINE,
                FinalTechMenus.SUB_MENU_TOWER);
        // productive machine
        FinalTechMenus.MAIN_ITEM_GROUP.addTo(FinalTechMenus.MAIN_MENU_PRODUCTIVE_MACHINE,
                FinalTechMenus.SUB_MENU_MANUAL_MACHINE,
                FinalTechMenus.SUB_MENU_BASIC_MACHINE,
                FinalTechMenus.SUB_MENU_ADVANCED_MACHINE,
                FinalTechMenus.SUB_MENU_CONVERSION,
                FinalTechMenus.SUB_MENU_EXTRACTION,
                FinalTechMenus.SUB_MENU_GENERATOR);
        FinalTechMenus.MAIN_MENU_PRODUCTIVE_MACHINE.addFrom(
                FinalTechMenus.SUB_MENU_MANUAL_MACHINE,
                FinalTechMenus.SUB_MENU_BASIC_MACHINE,
                FinalTechMenus.SUB_MENU_ADVANCED_MACHINE,
                FinalTechMenus.SUB_MENU_CONVERSION,
                FinalTechMenus.SUB_MENU_EXTRACTION,
                FinalTechMenus.SUB_MENU_GENERATOR);
        // disc
        FinalTechMenus.MAIN_ITEM_GROUP.addTo(FinalTechMenus.MAIN_MENU_DISC,
                FinalTechMenus.SUB_MENU_FINAL_ITEM,
                FinalTechMenus.SUB_MENU_TROPHY,
                FinalTechMenus.SUB_MENU_DEPRECATED);
        FinalTechMenus.MAIN_MENU_DISC.addFrom(
                FinalTechMenus.SUB_MENU_FINAL_ITEM,
                FinalTechMenus.SUB_MENU_TROPHY,
                FinalTechMenus.SUB_MENU_DEPRECATED);

        FinalTechMenus.MAIN_ITEM_GROUP.setTier(0);
        FinalTechMenus.MAIN_ITEM_GROUP.register(finalTech);
    }

    private static void setupResearch() {
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "REPLACEABLE_CARD", 1, false,
                FinalTechItemStacks.WATER_CARD,
                FinalTechItemStacks.LAVA_CARD,
                FinalTechItemStacks.MILK_CARD,
                FinalTechItemStacks.FLINT_AND_STEEL_CARD);
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "ORDER_DUST", Slimefun.getInstalledAddons().size(), true,
                FinalTechItemStacks.ORDERED_DUST,
                FinalTechItemStacks.UNORDERED_DUST,
                FinalTechItemStacks.ORDERED_DUST_FACTORY_DIRT,
                FinalTechItemStacks.ORDERED_DUST_FACTORY_STONE,
                FinalTechItemStacks.ORDERED_DUST_GENERATOR);
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "BUG", (int) Math.pow(Slimefun.getRegistry().getEnabledSlimefunItems().size(), 0.5), true,
                FinalTechItemStacks.BUG,
                FinalTechItemStacks.EQUIVALENT_EXCHANGE_TABLE,
                FinalTechItemStacks.BASIC_LOGIC_FACTORY);
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "ETHER", (int) Math.pow(Slimefun.getRegistry().getEnabledSlimefunItems().size(), 0.5), true,
                FinalTechItemStacks.ETHER,
                FinalTechItemStacks.ETHER_MINER);
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "ENTROPY", (int) Math.pow(Slimefun.getRegistry().getResearches().size(), 0.5), true,
                FinalTechItemStacks.ENTROPY,
                FinalTechItemStacks.ENTROPY_SEED,
                FinalTechItemStacks.ENTROPY_CONSTRUCTOR);
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "BOX", Math.abs((int) FinalTechChanged.getSeed()) % 20, true,
                FinalTechItemStacks.BOX,
                FinalTechItemStacks.SHINE);
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "COPY_CARD", (int) Math.pow(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT, 0.25), true,
                FinalTechItemStacks.COPY_CARD,
                FinalTechItemStacks.ANNULAR,
                FinalTechItemStacks.ITEM_SERIALIZATION_CONSTRUCTOR,
                FinalTechItemStacks.ITEM_DESERIALIZE_PARSER);
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "QUANTITY_MODULE", (int) Math.pow(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT, 0.25), true,
                FinalTechItemStacks.QUANTITY_MODULE,
                FinalTechItemStacks.ENERGIZED_QUANTITY_MODULE,
                FinalTechItemStacks.OVERLOADED_QUANTITY_MODULE);
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "PHONY", (int) Math.pow(ConstantTableUtil.ITEM_SINGULARITY_AMOUNT * ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT, 0.6), true,
                FinalTechItemStacks.SINGULARITY,
                FinalTechItemStacks.SPIROCHETE,
                FinalTechItemStacks.SHELL,
                FinalTechItemStacks.PHONY,
                FinalTechItemStacks.JUSTIFIABILITY,
                FinalTechItemStacks.EQUIVALENT_CONCEPT,
                FinalTechItemStacks.ENTROPY_SEED,
                FinalTechItemStacks.MATRIX_MACHINE_CHARGE_CARD,
                FinalTechItemStacks.MATRIX_MACHINE_ACCELERATE_CARD,
                FinalTechItemStacks.MATRIX_MACHINE_ACTIVATE_CARD,
                FinalTechItemStacks.MATRIX_QUANTITY_MODULE,
                FinalTechItemStacks.MATRIX_OPERATION_ACCELERATOR,
                FinalTechItemStacks.MATRIX_EXPANDED_CAPACITOR,
                FinalTechItemStacks.MATRIX_GENERATOR,
                FinalTechItemStacks.ADVANCED_AUTO_CRAFT,
                FinalTechItemStacks.MATRIX_ITEM_DISMANTLE_TABLE,
                FinalTechItemStacks.MATRIX_ACCELERATOR,
                FinalTechItemStacks.MATRIX_ITEM_DESERIALIZE_PARSER,
                FinalTechItemStacks.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR,
                FinalTechItemStacks.MATRIX_REACTOR);
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "LOGIC", 1, false,
                FinalTechItemStacks.LOGIC_FALSE,
                FinalTechItemStacks.LOGIC_TRUE,
                FinalTechItemStacks.DIGITAL_ZERO,
                FinalTechItemStacks.DIGITAL_ONE,
                FinalTechItemStacks.DIGITAL_TWO,
                FinalTechItemStacks.DIGITAL_THREE,
                FinalTechItemStacks.DIGITAL_FOUR,
                FinalTechItemStacks.DIGITAL_FIVE,
                FinalTechItemStacks.DIGITAL_SIX,
                FinalTechItemStacks.DIGITAL_SEVEN,
                FinalTechItemStacks.DIGITAL_EIGHT,
                FinalTechItemStacks.DIGITAL_NINE,
                FinalTechItemStacks.DIGITAL_TEN,
                FinalTechItemStacks.DIGITAL_ELEVEN,
                FinalTechItemStacks.DIGITAL_TWELVE,
                FinalTechItemStacks.DIGITAL_THIRTEEN,
                FinalTechItemStacks.DIGITAL_FOURTEEN,
                FinalTechItemStacks.DIGITAL_FIFTEEN,
                FinalTechItemStacks.LOGIC_COMPARATOR_NOTNULL,
                FinalTechItemStacks.LOGIC_COMPARATOR_AMOUNT,
                FinalTechItemStacks.LOGIC_COMPARATOR_SIMILAR,
                FinalTechItemStacks.LOGIC_COMPARATOR_EQUAL,
                FinalTechItemStacks.LOGIC_CRAFTER,
                FinalTechItemStacks.DIGIT_ADDER,
                FinalTechItemStacks.LOGIC_GENERATOR,
                FinalTechItemStacks.DIGITAL_GENERATOR,
                FinalTechItemStacks.LOGIC_TO_DIGITAL_CONVERSION,
                FinalTechItemStacks.DIGITAL_EXTRACTION);
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "MACHINE_CARD", 10, false,
                FinalTechItemStacks.MACHINE_CHARGE_CARD_L1,
                FinalTechItemStacks.MACHINE_CHARGE_CARD_L2,
                FinalTechItemStacks.MACHINE_CHARGE_CARD_L3,
                FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1,
                FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2,
                FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L3,
                FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1,
                FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2,
                FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3);
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "ENERGY_CARD", 10, false,
                FinalTechItemStacks.ENERGY_CARD_K,
                FinalTechItemStacks.ENERGY_CARD_M,
                FinalTechItemStacks.ENERGY_CARD_B,
                FinalTechItemStacks.ENERGY_CARD_T,
                FinalTechItemStacks.ENERGY_CARD_Q,
                FinalTechItemStacks.ENERGY_TABLE,
                FinalTechItemStacks.ENERGY_INPUT_TABLE,
                FinalTechItemStacks.ENERGY_OUTPUT_TABLE);
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "CONSUMABLE_ITEM", 10, false,
                FinalTechItemStacks.MAGIC_HYPNOTIC,
                FinalTechItemStacks.RESEARCH_UNLOCK_TICKET);
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "SIMPLE_TOOL", 10, false,
                FinalTechItemStacks.STAFF_ELEMENTAL_LINE,
                FinalTechItemStacks.POTION_EFFECT_COMPRESSOR,
                FinalTechItemStacks.POTION_EFFECT_DILATOR,
                FinalTechItemStacks.POTION_EFFECT_PURIFIER,
                FinalTechItemStacks.GRAVITY_REVERSAL_RUNE);
        ResearchUtil.setResearches(FinalTechChanged.getLanguageManager(), "MACHINE_TOOL", 10, false,
                FinalTechItemStacks.MENU_VIEWER,
                FinalTechItemStacks.ROUTE_VIEWER,
                FinalTechItemStacks.LOCATION_RECORDER,
                FinalTechItemStacks.MACHINE_CONFIGURATOR,
                FinalTechItemStacks.PORTABLE_ENERGY_STORAGE);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.SUPER_SHOVEL, SlimefunItems.SOULBOUND_SHOVEL);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ULTIMATE_SHOVEL, SlimefunItems.SOULBOUND_SHOVEL);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.SUPER_PICKAXE, SlimefunItems.SOULBOUND_PICKAXE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ULTIMATE_PICKAXE, SlimefunItems.SOULBOUND_PICKAXE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.SUPER_AXE, SlimefunItems.SOULBOUND_AXE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ULTIMATE_AXE, SlimefunItems.SOULBOUND_AXE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.SUPER_HOE, SlimefunItems.SOULBOUND_HOE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ULTIMATE_HOE, SlimefunItems.SOULBOUND_HOE);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.BASIC_GENERATOR, SlimefunItems.SOLAR_GENERATOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_GENERATOR, SlimefunItems.SOLAR_GENERATOR_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CARBONADO_GENERATOR, SlimefunItems.SOLAR_GENERATOR_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_GENERATOR, SlimefunItems.SOLAR_GENERATOR_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_STACK_GENERATOR, SlimefunItems.SOLAR_GENERATOR_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.OVERLOADED_GENERATOR, SlimefunItems.SOLAR_GENERATOR_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_CHARGE_BASE, SlimefunItems.SOLAR_GENERATOR_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.OVERLOADED_CHARGE_BASE, SlimefunItems.SOLAR_GENERATOR_4);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR, SlimefunItems.SMALL_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MEDIUM_EXPANDED_CAPACITOR, SlimefunItems.MEDIUM_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.BIG_EXPANDED_CAPACITOR, SlimefunItems.BIG_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.LARGE_EXPANDED_CAPACITOR, SlimefunItems.LARGE_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CARBONADO_EXPANDED_CAPACITOR, SlimefunItems.CARBONADO_EDGED_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR, SlimefunItems.ENERGIZED_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_STACK_EXPANDED_CAPACITOR, SlimefunItems.ENERGIZED_CAPACITOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.OVERLOADED_EXPANDED_CAPACITOR, SlimefunItems.ENERGIZED_CAPACITOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.NORMAL_ELECTRICITY_SHOOT_PILE, SlimefunItems.ENERGY_REGULATOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE, SlimefunItems.ENERGY_REGULATOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE, SlimefunItems.ENERGY_REGULATOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_ELECTRICITY_SHOOT_PILE, SlimefunItems.ENERGY_REGULATOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.OVERLOADED_ELECTRICITY_SHOOT_PILE, SlimefunItems.ENERGY_REGULATOR);


        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_ACCELERATOR, SlimefunItems.NETHER_STAR_REACTOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.OVERLOADED_ACCELERATOR, SlimefunItems.NETHER_STAR_REACTOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.NORMAL_STORAGE_UNIT, SlimefunItems.BACKPACK_SMALL);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.DIVIDED_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.LIMITED_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.STACK_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.DIVIDED_LIMITED_STORAGE_UNIT, SlimefunItems.GILDED_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.DIVIDED_STACK_STORAGE_UNIT, SlimefunItems.GILDED_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.LIMITED_STACK_STORAGE_UNIT, SlimefunItems.GILDED_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.RANDOM_INPUT_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.RANDOM_OUTPUT_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.RANDOM_ACCESS_STORAGE_UNIT, SlimefunItems.WOVEN_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.DISTRIBUTE_LEFT_STORAGE_UNIT, SlimefunItems.ANDROID_INTERFACE_FUEL);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.DISTRIBUTE_RIGHT_STORAGE_UNIT, SlimefunItems.ANDROID_INTERFACE_ITEMS);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.STORAGE_INTERACT_PORT, SlimefunItems.RADIANT_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.STORAGE_INSERT_PORT, SlimefunItems.RADIANT_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.STORAGE_WITHDRAW_PORT, SlimefunItems.RADIANT_BACKPACK);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.STORAGE_CARD, SlimefunItems.RADIANT_BACKPACK);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.REMOTE_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CONSUMABLE_REMOTE_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CONFIGURABLE_REMOTE_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.EXPANDED_CONSUMABLE_REMOTE_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.RANDOM_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.AREA_ACCESSOR, SlimefunItems.REACTOR_ACCESS_PORT);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.TRANSPORTER, SlimefunItems.GPS_TELEPORTATION_MATRIX);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CONSUMABLE_TRANSPORTER, SlimefunItems.GPS_TELEPORTATION_MATRIX);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CONFIGURABLE_TRANSPORTER, SlimefunItems.GPS_TELEPORTATION_MATRIX);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.EXPANDED_CONSUMABLE_TRANSPORTER, SlimefunItems.GPS_TELEPORTATION_MATRIX);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.EXPANDED_CONFIGURABLE_TRANSPORTER, SlimefunItems.GPS_TELEPORTATION_MATRIX);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.BASIC_FRAME_MACHINE, SlimefunItems.CARGO_CONNECTOR_NODE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.POINT_TRANSFER, SlimefunItems.CARGO_MANAGER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MESH_TRANSFER, SlimefunItems.CARGO_MANAGER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.LINE_TRANSFER, SlimefunItems.CARGO_MANAGER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.LOCATION_TRANSFER, SlimefunItems.CARGO_MANAGER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_POINT_TRANSFER, SlimefunItems.CARGO_MANAGER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_MESH_TRANSFER, SlimefunItems.CARGO_MANAGER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_LINE_TRANSFER, SlimefunItems.CARGO_MANAGER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_LOCATION_TRANSFER, SlimefunItems.CARGO_MANAGER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CONFIGURATION_COPIER, SlimefunItems.CRAFTING_MOTOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CONFIGURATION_PASTER, SlimefunItems.CRAFTING_MOTOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CLICK_WORK_MACHINE, SlimefunItems.GPS_ACTIVATION_DEVICE_PERSONAL);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.SIMULATE_CLICK_MACHINE, SlimefunItems.GPS_ACTIVATION_DEVICE_SHARED);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CONSUMABLE_SIMULATE_CLICK_MACHINE, SlimefunItems.GPS_ACTIVATION_DEVICE_SHARED);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MATRIX_CRAFTING_TABLE, SlimefunItems.PROGRAMMABLE_ANDROID_2);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ITEM_DISMANTLE_TABLE, SlimefunItems.NUCLEAR_REACTOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.AUTO_ITEM_DISMANTLE_TABLE, SlimefunItems.NUCLEAR_REACTOR);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CARD_OPERATION_TABLE, SlimefunItems.WITHER_ASSEMBLER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_AUTO_CRAFT_FRAME, SlimefunItems.ENHANCED_AUTO_CRAFTER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MULTI_FRAME_MACHINE, SlimefunItems.ENHANCED_AUTO_CRAFTER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ITEM_FIXER, SlimefunItems.IRON_GOLEM_ASSEMBLER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.COBBLESTONE_FACTORY, SlimefunItems.PROGRAMMABLE_ANDROID);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.FUEL_CHARGER, SlimefunItems.PROGRAMMABLE_ANDROID_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.FUEL_OPERATOR, SlimefunItems.PROGRAMMABLE_ANDROID_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.FUEL_ACCELERATOR, SlimefunItems.PROGRAMMABLE_ANDROID_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.OPERATION_ACCELERATOR, SlimefunItems.PROGRAMMABLE_ANDROID_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ENERGIZED_OPERATION_ACCELERATOR, SlimefunItems.PROGRAMMABLE_ANDROID_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.OVERLOADED_OPERATION_ACCELERATOR, SlimefunItems.PROGRAMMABLE_ANDROID_3);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.CURE_TOWER, SlimefunItems.GPS_TRANSMITTER_4);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.PURIFY_LEVEL_TOWER, SlimefunItems.GPS_TRANSMITTER_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.PURIFY_TIME_TOWER, SlimefunItems.GPS_TRANSMITTER_3);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_GRIND_STONE, SlimefunItems.GRIND_STONE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_ARMOR_FORGE, SlimefunItems.ARMOR_FORGE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_ORE_CRUSHER, SlimefunItems.ORE_CRUSHER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_COMPRESSOR, SlimefunItems.COMPRESSOR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_SMELTERY, SlimefunItems.SMELTERY);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_PRESSURE_CHAMBER, SlimefunItems.PRESSURE_CHAMBER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_MAGIC_WORKBENCH, SlimefunItems.MAGIC_WORKBENCH);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_ORE_WASHER, SlimefunItems.ORE_WASHER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_COMPOSTER, SlimefunItems.COMPOSTER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_GOLD_PAN, SlimefunItems.GOLD_PAN);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_CRUCIBLE, SlimefunItems.CRUCIBLE);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_JUICER, SlimefunItems.JUICER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_ANCIENT_ALTAR, SlimefunItems.ANCIENT_ALTAR);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.MANUAL_HEATED_PRESSURE_CHAMBER, SlimefunItems.HEATED_PRESSURE_CHAMBER);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_COMPOSTER, SlimefunItems.FOOD_COMPOSTER_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_JUICER, SlimefunItems.JUICER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_FURNACE, SlimefunItems.ELECTRIC_FURNACE_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_GOLD_PAN, SlimefunItems.ELECTRIC_GOLD_PAN_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_DUST_WASHER, SlimefunItems.ELECTRIC_DUST_WASHER_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_INGOT_FACTORY, SlimefunItems.ELECTRIC_INGOT_FACTORY_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_CRUCIBLE, SlimefunItems.ELECTRIFIED_CRUCIBLE_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_ORE_GRINDER, SlimefunItems.ELECTRIC_ORE_GRINDER_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_HEATED_PRESSURE_CHAMBER, SlimefunItems.HEATED_PRESSURE_CHAMBER_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_INGOT_PULVERIZER, SlimefunItems.ELECTRIC_INGOT_PULVERIZER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_AUTO_DRIER, SlimefunItems.AUTO_DRIER);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_PRESS, SlimefunItems.ELECTRIC_PRESS_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_FOOD_FACTORY, SlimefunItems.FOOD_FABRICATOR_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_FREEZER, SlimefunItems.FREEZER_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_CARBON_PRESS, SlimefunItems.CARBON_PRESS_3);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_SMELTERY, SlimefunItems.ELECTRIC_SMELTERY_2);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.ADVANCED_COMPOSTER, SlimefunItems.FOOD_COMPOSTER_2);

        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.GRAVEL_CONVERSION, SlimefunItems.GOLD_PAN);
        ResearchUtil.setResearchBySlimefunItems(FinalTechItemStacks.SOUL_SAND_CONVERSION, SlimefunItems.NETHER_GOLD_PAN);
    }

    private static void setupCommand() {
        FinalTechChanged finalTech = FinalTechChanged.getInstance();

        finalTech.getCommand("finaltech-copy-card").setExecutor(new TransformToCopyCardItem());
        finalTech.getCommand("finaltech-storage-card").setExecutor(new TransformToStorageItem());
        finalTech.getCommand("finaltech-info").setExecutor(new ShowItemInfo());
        finalTech.getCommand("finaltech-valid-item").setExecutor(new TransformToValidItem());
    }

    public static void init() {
        ConfigFileManager configManager = FinalTechChanged.getConfigManager();

        if (configManager.getOrDefault(true, "enable", "item")) {
            // Yeah, you may not want new items from this plugin.
            setupEnchantment();
            setupItem();
            setupMenu();
            setupResearch();
        }

        setupCommand();
    }

    public static void registerBlockTicker() {
        SetupUtil.registerBlockTicker(0);
    }

    private static void registerBlockTicker(int begin) {
        try {
            ConfigFileManager configManager = FinalTechChanged.getConfigManager();
            List<SlimefunItem> slimefunItemList = Slimefun.getRegistry().getAllSlimefunItems();
            for (int size = slimefunItemList.size(); begin < size; begin++) {
                SlimefunItem slimefunItem = slimefunItemList.get(begin);
                SlimefunAddon slimefunAddon = slimefunItem.getAddon();
                if (slimefunItem.getBlockTicker() != null) {
                    BlockTicker blockTicker = slimefunItem.getBlockTicker();

                    if (FinalTechChanged.debugMode()) {
                        blockTicker = BlockTickerUtil.getDebugModeBlockTicker(blockTicker, slimefunItem);
                    }

                    if (slimefunItem instanceof RecipeDisplayItem recipeDisplayItem) {
                        for (ItemStack itemStack : recipeDisplayItem.getDisplayRecipes()) {
                            SlimefunItem sfItem = SlimefunItem.getByItem(itemStack);
                            if (sfItem instanceof SimpleValidItem simpleValidItem && !BELIEVABLE_PLUGIN_ID_LIST.contains(sfItem.getAddon().getName()) && simpleValidItem.verifyItem(itemStack)) {
                                FinalTechChanged.getConfigManager().setValue(new Random().nextLong(Long.MAX_VALUE), "valid-item-seed");
                            }
                        }
                    }

                    if (configManager.containPath("tweak", "interval", "general", slimefunItem.getId())) {
                        int interval = configManager.getOrDefault(-1, "tweak", "interval", "general", slimefunItem.getId());
                        if (interval > 0) {
                            blockTicker = BlockTickerUtil.getGeneralIntervalBlockTicker(blockTicker, interval);
                            FinalTechChanged.logger().info(slimefunItem.getId() + " is tweaked for general interval limit");
                        } else {
                            FinalTechChanged.logger().warning("wrong value of tweak.interval.general." + slimefunItem.getId() + " in config file");
                        }
                    }
                    if (configManager.containPath("tweak", "interval", "independent", slimefunItem.getId())) {
                        int interval = configManager.getOrDefault(-1, "tweak", "interval", "independent", slimefunItem.getId());
                        if (interval > 1) {
                            blockTicker = BlockTickerUtil.getIndependentIntervalBlockTicker(blockTicker, interval);
                            FinalTechChanged.logger().info(slimefunItem.getId() + " is tweaked for independent interval limit");
                        } else {
                            FinalTechChanged.logger().warning("wrong value of tweak.interval.independent." + slimefunItem.getId() + " in config file");
                        }
                    }

                    if (configManager.containPath("tweak", "range-limit", slimefunItem.getId(), "range")) {
                        int range = configManager.getOrDefault(-1, "tweak", "range-limit", slimefunItem.getId(), "range");
                        if (range > 0) {
                            int mulRange = configManager.getOrDefault(0, "tweak", "range-limit", slimefunItem.getId(), "mul-range");
                            boolean dropSelf = configManager.getOrDefault(false, "tweak", "range-limit", slimefunItem.getId(), "drop-self");
                            String message = configManager.getOrDefault("{1} is not allowed to be placed too closely", "tweak", "range-limit", slimefunItem.getId(), "message");
                            blockTicker = BlockTickerUtil.getRangeLimitBlockTicker(blockTicker, range, mulRange, dropSelf, message);
                            FinalTechChanged.logger().info(slimefunItem.getId() + " is tweaked for range limit");

                            if (dropSelf) {
                                FinalTechChanged.logger().warning("Please be carefully if you installed slimefun addon '基岩科技'(BedrockTechnology) and you set drop-self as true.");
                                FinalTechChanged.logger().warning("There is a duplication bug, and we may fix it in next version");
                            }
                        } else {
                            FinalTechChanged.logger().warning("wrong value of tweak.range." + slimefunItem.getId() + " in config file");
                        }
                    }

                    boolean forceAsync = !blockTicker.isSynchronized() && (FinalTechChanged.getForceSlimefunMultiThread() || FinalTechChanged.isAsyncSlimefunItem(slimefunItem.getId()) || FinalTechChanged.getAsyncSlimefunPluginSet().contains(slimefunAddon.getName()));
                    boolean antiAccelerate = FinalTechChanged.isAntiAccelerateSlimefunItem(slimefunItem.getId()) || FinalTechChanged.getAntiAccelerateSlimefunPluginSet().contains(slimefunAddon.getName());
                    boolean performanceLimit = FinalTechChanged.isPerformanceLimitSlimefunItem(slimefunItem.getId()) || FinalTechChanged.getPerformanceLimitSlimefunPluginSet().contains(slimefunAddon.getName());
                    if (forceAsync || antiAccelerate || performanceLimit) {
                        blockTicker = BlockTickerUtil.generateBlockTicker(blockTicker, forceAsync, antiAccelerate, performanceLimit);
                        if (antiAccelerate) {
                            FinalTechChanged.addAntiAccelerateSlimefunItem(slimefunItem.getId());
                            FinalTechChanged.logger().info(slimefunItem.getId() + " is tweaked for anti accelerate");
                        }
                        if (performanceLimit) {
                            FinalTechChanged.addPerformanceLimitSlimefunItem(slimefunItem.getId());
                            FinalTechChanged.logger().info(slimefunItem.getId() + " is tweaked for performance limit");
                        }
                    }

                    if (configManager.getOrDefault(false, "super-ban") && slimefunItem.isDisabled()) {
                        blockTicker = null;
                        FinalTechChanged.logger().info(slimefunItem.getId() + " is tweaked to remove block ticker");
                    } else if (FinalTechChanged.isNoBlockTickerSlimefunItem(slimefunItem.getId())) {
                        blockTicker = null;
                        FinalTechChanged.logger().info(slimefunItem.getId() + " is tweaked to remove block ticker");
                    } else if (FinalTechChanged.getNoBlockTickerSlimefunPluginSet().contains(slimefunAddon.getJavaPlugin().getName())) {
                        blockTicker = null;
                        FinalTechChanged.logger().info(slimefunItem.getId() + " is tweaked to remove block ticker");
                    }

                    if (slimefunItem.getBlockTicker() != blockTicker) {
                        Class<SlimefunItem> clazz = SlimefunItem.class;
                        Field declaredField = clazz.getDeclaredField("blockTicker");
                        declaredField.setAccessible(true);
                        declaredField.set(slimefunItem, blockTicker);
                        declaredField.setAccessible(false);
                        if (blockTicker == null) {
                            Field ticking = clazz.getDeclaredField("ticking");
                            ticking.setAccessible(true);
                            ticking.set(slimefunItem, false);
                            ticking.setAccessible(false);
                        }
                        if (forceAsync) {
                            FinalTechChanged.logger().info(slimefunItem.getId() + "(" + slimefunItem.getItemName() + ")" + " is optimized for multi-thread！！！");
                            FinalTechChanged.addAsyncSlimefunItem(slimefunItem.getId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            SetupUtil.registerBlockTicker(++begin);
        }
    }

    public static void dataLossFix() {
        for (World world : FinalTechChanged.getInstance().getServer().getWorlds()) {
            BlockStorage storage = BlockStorage.getStorage(world);
            if (storage != null) {
                try {
                    Map<Location, BlockMenu> inventories = ReflectionUtil.getProperty(storage, BlockStorage.class, "inventories");
                    if (inventories != null) {
                        int count = 0;
                        FinalTechChanged.logger().info("Data Loss Fix: start work for world: " + world.getName());
                        for (Map.Entry<Location, BlockMenu> entry : inventories.entrySet()) {
                            Location location = entry.getKey();
                            if (location.getBlock().getType().isAir()) {
                                continue;
                            }
                            LocationInfo locationInfo = LocationInfo.get(location);
                            if (locationInfo == null) {
                                String id = entry.getValue().getPreset().getID();
                                SlimefunItem slimefunItem = SlimefunItem.getById(id);
                                if (slimefunItem != null && !(slimefunItem instanceof AbstractMachine) && slimefunItem.getItem().getType().equals(location.getBlock().getType())) {
                                    FinalTechChanged.logger().warning("Data Loss Fix: location " + location + " seems loss its data. There should be " + id + " (" + slimefunItem.getItemName() + ")");
                                    Map<String, String> configMap = FinalTechChanged.getDataLossFixCustomMap(id);
                                    if (configMap == null) {
                                        FinalTechChanged.logger().warning("Data Loss Fix: I don't know how to fix it. Config me in config.yml with path: " + "data-loss-fix-custom" + "." + "config" + "." + id);
                                        continue;
                                    }

                                    BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_ID, id);
                                    for (Map.Entry<String, String> configEntry : configMap.entrySet()) {
                                        BlockStorage.addBlockInfo(location, configEntry.getKey(), configEntry.getValue());
                                    }
                                    FinalTechChanged.logger().info("Data Loss Fix: added location info to location: " + location);
                                    count++;
                                }
                            }
                        }
                        if (count > 0) {
                            FinalTechChanged.logger().info("Data Loss Fix: totally " + count + " block" + (count == 1 ? " is" : "s are") + " fixed");
                        } else {
                            FinalTechChanged.logger().info("Data Loss Fix: nothing changed! This is the best situation!");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
