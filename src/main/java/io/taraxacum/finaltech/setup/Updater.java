package io.taraxacum.finaltech.setup;

import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.plugin.dto.ConfigFileManager;
import io.taraxacum.libs.plugin.dto.LanguageManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

public class Updater implements Consumer<FinalTechChanged> {
    public static final String LATEST_VERSION = "202406";
    private static volatile Updater instance;
    private final Map<String, UpdateFunction> versionMap = new HashMap<>();
    private boolean init = false;

    @Nonnull
    public static Updater getInstance() {
        if (instance == null) {
            synchronized (Updater.class) {
                if (instance == null) {
                    instance = new Updater();
                }
            }
        }
        return instance;
    }

    public void init() {
        LanguageManager languageManager = FinalTechChanged.getLanguageManager();
        ConfigFileManager configManager = FinalTechChanged.getConfigManager();
        ConfigFileManager itemManager = FinalTechChanged.getItemManager();
        Logger logger = FinalTechChanged.logger();
        Boolean updateLanguage = configManager.getOrDefault(true, "update", "language");
        if (updateLanguage) {
            logger.info("You have enabled the config updater for language file");
        }

        versionMap.put("20220811", new UpdateFunction() {
            @Override
            protected String targetVersion() {
                return "20221204";
            }

            @Override
            protected void update(FinalTechChanged finalTech) {
                if (updateLanguage) {
                    List<String> lore1 = new ArrayList<>();
                    lore1.add("{color:normal}Input items to get {id:_FINALTECH_UNORDERED_DUST} {color:normal}or {id:_FINALTECH_ORDERED_DUST}");
                    lore1.add("");
                    lore1.add("{color:normal}A pair of random number will be generated.");
                    lore1.add("{color:normal}They will be used to determine how to craft item.");

                    List<String> lore2 = new ArrayList<>();
                    lore2.add("{color:normal}Input at least the specified amount of items");
                    lore2.add("{color:normal}Input at least the specified quantity of different type of items");
                    lore2.add("{color:normal}Then it will generate one {id:_FINALTECH_UNORDERED_DUST}");

                    List<String> lore3 = new ArrayList<>();
                    lore3.add("{color:normal}Input just the specified amount of items");
                    lore3.add("{color:normal}Input just the specified quantity of different type of items");
                    lore3.add("{color:normal}Then it will generate one {id:_FINALTECH_ORDERED_DUST}");

                    languageManager.setValue(lore1, "items", "_FINALTECH_ORDERED_DUST_FACTORY_DIRT", "info", "1", "lore");
                    languageManager.setValue(lore2, "items", "_FINALTECH_ORDERED_DUST_FACTORY_DIRT", "info", "2", "lore");
                    languageManager.setValue(lore3, "items", "_FINALTECH_ORDERED_DUST_FACTORY_DIRT", "info", "3", "lore");
                }
            }
        });

        versionMap.put("20221204", new UpdateFunction() {
            @Override
            protected String targetVersion() {
                return "20221229";
            }

            @Override
            protected void update(FinalTechChanged finalTech) {
                List<String> allowedIdList = new ArrayList<>();
                allowedIdList.add("_FINALTECH_ADVANCED_COMPOSTER");
                allowedIdList.add("_FINALTECH_ADVANCED_JUICER");
                allowedIdList.add("_FINALTECH_ADVANCED_FURNACE");
                allowedIdList.add("_FINALTECH_ADVANCED_GOLD_PAN");
                allowedIdList.add("_FINALTECH_ADVANCED_DUST_WASHER");
                allowedIdList.add("_FINALTECH_ADVANCED_INGOT_FACTORY");
                allowedIdList.add("_FINALTECH_ADVANCED_CRUCIBLE");
                allowedIdList.add("_FINALTECH_ADVANCED_ORE_GRINDER");
                allowedIdList.add("_FINALTECH_ADVANCED_HEATED_PRESSURE_CHAMBER");
                allowedIdList.add("_FINALTECH_ADVANCED_INGOT_PULVERIZER");
                allowedIdList.add("_FINALTECH_ADVANCED_AUTO_DRIER");
                allowedIdList.add("_FINALTECH_ADVANCED_PRESS");
                allowedIdList.add("_FINALTECH_ADVANCED_FOOD_FACTORY");
                allowedIdList.add("_FINALTECH_ADVANCED_FREEZER");
                allowedIdList.add("_FINALTECH_ADVANCED_CARBON_PRESS");
                allowedIdList.add("_FINALTECH_ADVANCED_SMELTERY");
                allowedIdList.add("_FINALTECH_ADVANCED_DUST_FACTORY");
                configManager.setValue(allowedIdList, "_FINALTECH_MULTI_FRAME_MACHINE", "allowed-id");

                if (updateLanguage) {
                    languageManager.setValue("{color:prandom}Remote Accessor {color:conceal}- {color:positive}Consumable", "items", "_FINALTECH_CONSUMABLE_REMOTE_ACCESSOR", "name");
                    languageManager.setValue("{color:prandom}Remote Accessor {color:conceal}- {color:positive}Configurable", "items", "_FINALTECH_CONFIGURABLE_REMOTE_ACCESSOR", "name");
                    languageManager.setValue("{color:prandom}Expanded Remote Accessor {color:conceal}- {color:positive}Consumable", "items", "_FINALTECH_EXPANDED_CONSUMABLE_REMOTE_ACCESSOR", "name");
                    languageManager.setValue("{color:prandom}Expanded Remote Accessor {color:conceal}- {color:positive}Configurable", "items", "_FINALTECH_EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR", "name");
                    languageManager.setValue("{color:prandom}Transporter", "items", "_FINALTECH_TRANSPORTER", "name");
                    languageManager.setValue("{color:prandom}Transporter {color:conceal}- {color:positive}Consumable", "items", "_FINALTECH_CONSUMABLE_TRANSPORTER", "name");
                    languageManager.setValue("{color:prandom}Transporter {color:conceal}- {color:positive}Configurable", "items", "_FINALTECH_CONFIGURABLE_TRANSPORTER", "name");
                    languageManager.setValue("{color:prandom}Expanded Transporter {color:conceal}- {color:positive}Consumable", "items", "_FINALTECH_EXPANDED_CONSUMABLE_TRANSPORTER", "name");
                    languageManager.setValue("{color:prandom}Expanded Transporter {color:conceal}- {color:positive}Configurable", "items", "_FINALTECH_EXPANDED_CONFIGURABLE_TRANSPORTER", "name");

                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_EXPANDED_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_TRANSPORTER", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_CONSUMABLE_TRANSPORTER", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_CONFIGURABLE_TRANSPORTER", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_EXPANDED_CONSUMABLE_TRANSPORTER", "info", "1", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_EXPANDED_CONFIGURABLE_TRANSPORTER", "info", "1", "name");

                    languageManager.setValue("_FINALTECH_DIGITAL_ZERO", "items", "_FINALTECH_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "output");
                    languageManager.setValue("_FINALTECH_DIGITAL_ZERO", "items", "_FINALTECH_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "output");
                    languageManager.setValue("_FINALTECH_DIGITAL_ZERO", "items", "_FINALTECH_EXPANDED_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "output");
                    languageManager.setValue("_FINALTECH_DIGITAL_ZERO", "items", "_FINALTECH_EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "output");
                    languageManager.setValue("_FINALTECH_DIGITAL_ZERO", "items", "_FINALTECH_CONSUMABLE_TRANSPORTER", "info", "1", "output");
                    languageManager.setValue("_FINALTECH_DIGITAL_ZERO", "items", "_FINALTECH_CONFIGURABLE_TRANSPORTER", "info", "1", "output");
                    languageManager.setValue("_FINALTECH_DIGITAL_ZERO", "items", "_FINALTECH_EXPANDED_CONSUMABLE_TRANSPORTER", "info", "1", "output");
                    languageManager.setValue("_FINALTECH_DIGITAL_ZERO", "items", "_FINALTECH_EXPANDED_CONFIGURABLE_TRANSPORTER", "info", "1", "output");

                    List<String> lore1 = new ArrayList<>();
                    lore1.add("{color:normal}Right click it to config it");
                    lore1.add("{color:normal}Right click it to open slimefun machine it looking at");
                    languageManager.setValue(lore1, "items", "_FINALTECH_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "lore");
                    languageManager.setValue(lore1, "items", "_FINALTECH_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "lore");
                    languageManager.setValue(lore1, "items", "_FINALTECH_EXPANDED_CONSUMABLE_REMOTE_ACCESSOR", "info", "1", "lore");
                    languageManager.setValue(lore1, "items", "_FINALTECH_EXPANDED_CONFIGURABLE_REMOTE_ACCESSOR", "info", "1", "lore");

                    List<String> lore2 = new ArrayList<>();
                    lore2.add("{color:normal}Right click it to teleport to the target place");
                    lore2.add("{color:normal}Range: {color:number}{1}");
                    languageManager.setValue(lore2, "items", "_FINALTECH_TRANSPORTER", "info", "1", "lore");

                    List<String> lore3 = new ArrayList<>();
                    lore3.add("{color:normal}Right click it to config it");
                    lore3.add("{color:normal}Right click it to teleport to the target place");

                    languageManager.setValue(lore3, "items", "_FINALTECH_CONSUMABLE_TRANSPORTER", "info", "1", "lore");
                    languageManager.setValue(lore3, "items", "_FINALTECH_CONFIGURABLE_TRANSPORTER", "info", "1", "lore");
                    languageManager.setValue(lore3, "items", "_FINALTECH_EXPANDED_CONSUMABLE_TRANSPORTER", "info", "1", "lore");
                    languageManager.setValue(lore3, "items", "_FINALTECH_EXPANDED_CONFIGURABLE_TRANSPORTER", "info", "1", "lore");

                    languageManager.setValue("{color:prandom}Time Generator", "items", "_FINALTECH_TIME_GENERATOR", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_TIME_GENERATOR", "info", "1", "name");

                    languageManager.setValue("{color:prandom}Time Capacitor", "items", "_FINALTECH_TIME_CAPACITOR", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_TIME_CAPACITOR", "info", "1", "name");

                    List<String> lore4 = new ArrayList<>();
                    lore4.add("{color:normal}Generate energy 1J per {color:number}{1}s");
                    lore4.add("{color:normal}Can store {color:number}{2} J {color:normal}of energy");
                    lore4.add("{color:normal}Double stored energy with time going");
                    languageManager.setValue(lore4, "items", "_FINALTECH_TIME_GENERATOR", "info", "1", "lore");

                    List<String> lore5 = new ArrayList<>();
                    lore5.add("{color:normal}Can store {color:number}{1} J {color:normal}of energy");
                    lore5.add("{color:normal}Double stored energy with time going");
                    languageManager.setValue(lore5, "items", "_FINALTECH_TIME_CAPACITOR", "info", "1", "lore");

                    List<String> lore6 = new ArrayList<>();
                    lore6.add("{color:normal}Stored Energy: {color:number}{1} J");
                    languageManager.setValue(lore6, "items", "_FINALTECH_TIME_GENERATOR", "status", "lore");
                    languageManager.setValue(lore6, "items", "_FINALTECH_TIME_CAPACITOR", "status", "lore");

                    languageManager.setValue("{color:prandom}Multi Frame Machine", "items", "_FINALTECH_MULTI_FRAME_MACHINE", "name");

                    languageManager.setValue("{color:random}Disc", "categories", "_FINALTECH_MAIN_MENU_DISC", "name");
                    languageManager.setValue("{color:random}Final Item", "categories", "_FINALTECH_SUB_MENU_FINAL_ITEM", "name");
                    languageManager.setValue("{color:random}Trophy", "categories", "_FINALTECH_SUB_MENU_TROPHY", "name");
                    languageManager.setValue("{color:random}Deprecated", "categories", "_FINALTECH_SUB_MENU_DEPRECATED", "name");

                    languageManager.setValue("{color:prandom}Digital Extraction", "items", "_FINALTECH_DIGITAL_EXTRACTION", "name");

                    List<String> lore7 = new ArrayList<>();
                    lore7.add("{color:normal}Can store {color:number}{1} J {color:normal}of energy every stack");
                    lore7.add("{color:normal}Can store {color:number}{2} stack {color:normal}of energy");
                    lore7.add("{color:normal}Generate energy with the same amount of stored energy stack every {color:number}{3} s");

                    languageManager.setValue(lore7, "items", "_FINALTECH_SMALL_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "_FINALTECH_MEDIUM_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "_FINALTECH_BIG_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "_FINALTECH_LARGE_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "_FINALTECH_CARBONADO_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "_FINALTECH_ENERGIZED_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "_FINALTECH_ENERGIZED_STACK_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "_FINALTECH_OVERLOADED_EXPANDED_CAPACITOR", "info", "1", "lore");
                    languageManager.setValue(lore7, "items", "_FINALTECH_MATRIX_EXPANDED_CAPACITOR", "info", "1", "lore");

                    languageManager.setValue("", "items", "_FINALTECH_NORMAL_ELECTRICITY_SHOOT_PILE", "info", "2");
                    languageManager.setValue("", "items", "_FINALTECH_ENERGIZED_ELECTRICITY_SHOOT_PILE", "info", "2");
                    languageManager.setValue("", "items", "_FINALTECH_OVERLOADED_ELECTRICITY_SHOOT_PILE", "info", "2");

                    List<String> lore8 = new ArrayList<>();
                    lore8.add("{color:normal}Transfer energy stored in the capacitor or generator behind it");
                    lore8.add("{color:normal}To the machine it is pointing at");
                    lore8.add("");
                    lore8.add("{color:normal}It will charge only one machine at same time");

                    List<String> lore9 = new ArrayList<>();
                    lore9.add("{color:normal}Stored Energy: {color:number}{1} J");
                    lore9.add("{color:normal}Transfer Energy: {color:number}{2} J");

                    languageManager.setValue("{color:prandom}Normal Electricity Shoot Pile {color:conceal}- {color:positive}Consumable", "items", "_FINALTECH_NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE", "info", "1", "name");
                    languageManager.setValue("_FINALTECH_DIGITAL_ZERO", "items", "_FINALTECH_NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE", "info", "1", "output");
                    languageManager.setValue(lore8, "items", "_FINALTECH_NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE", "info", "1", "lore");
                    languageManager.setValue(lore9, "items", "_FINALTECH_NORMAL_CONSUMABLE_ELECTRICITY_SHOOT_PILE", "status", "lore");

                    languageManager.setValue("{color:prandom}Normal Electricity Shoot Pile {color:conceal}- {color:positive}Configurable", "items", "_FINALTECH_NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE", "info", "1", "name");
                    languageManager.setValue("_FINALTECH_DIGITAL_ZERO", "items", "_FINALTECH_NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE", "info", "1", "output");
                    languageManager.setValue(lore8, "items", "_FINALTECH_NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE", "info", "1", "lore");
                    languageManager.setValue(lore9, "items", "_FINALTECH_NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE", "status", "lore");

                    languageManager.setValue("{color:prandom}Gravity Reversal Rune", "items", "_FINALTECH_GRAVITY_REVERSAL_RUNE", "name");
                    languageManager.setValue("{color:passive}Usage", "items", "_FINALTECH_GRAVITY_REVERSAL_RUNE", "info", "1", "name");
                    List<String> lore10 = new ArrayList<>();
                    lore10.add("{color:action}[Right-Click] {color:normal}Reverse your vertical speed");
                    languageManager.setValue(lore10, "items", "_FINALTECH_GRAVITY_REVERSAL_RUNE", "info", "1", "lore");
                }
            }
        });

        versionMap.put("20221229", new UpdateFunction() {
            @Override
            protected String targetVersion() {
                return "20230109";
            }

            @Override
            protected void update(FinalTechChanged finalTech) {
                ConfigFileManager itemManager = FinalTechChanged.getItemManager();
                List<String> l = new ArrayList<>();
                l.add("CARGO_NODE_INPUT");
                l.add("CARGO_NODE_OUTPUT");
                l.add("CARGO_NODE_OUTPUT_ADVANCED");
                itemManager.setValue(l, "MACHINE_CONFIGURATOR", "item-config", "cargo-node");

                l = new ArrayList<>();
                l.add("_FINALTECH_ADVANCED_COMPOSTER");
                l.add("_FINALTECH_ADVANCED_JUICER");
                l.add("_FINALTECH_ADVANCED_FURNACE");
                l.add("_FINALTECH_ADVANCED_GOLD_PAN");
                l.add("_FINALTECH_ADVANCED_DUST_WASHER");
                l.add("_FINALTECH_ADVANCED_INGOT_FACTORY");
                l.add("_FINALTECH_ADVANCED_CRUCIBLE");
                l.add("_FINALTECH_ADVANCED_ORE_GRINDER");
                l.add("_FINALTECH_ADVANCED_HEATED_PRESSURE_CHAMBER");
                l.add("_FINALTECH_ADVANCED_INGOT_PULVERIZER");
                l.add("_FINALTECH_ADVANCED_AUTO_DRIER");
                l.add("_FINALTECH_ADVANCED_PRESS");
                l.add("_FINALTECH_ADVANCED_FOOD_FACTORY");
                l.add("_FINALTECH_ADVANCED_FREEZER");
                l.add("_FINALTECH_ADVANCED_CARBON_PRESS");
                l.add("_FINALTECH_ADVANCED_SMELTERY");
                l.add("_FINALTECH_ADVANCED_DUST_FACTORY");
                l.add("_FINALTECH_ADVANCED_COMPOSTER");
                l.add("_FINALTECH_ADVANCED_COMPOSTER");
                itemManager.setValue(l, "MACHINE_CONFIGURATOR", "item-config", "advanced-machine");

                l = new ArrayList<>();
                l.add("_FINALTECH_POINT_TRANSFER");
                l.add("_FINALTECH_ADVANCED_POINT_TRANSFER");
                itemManager.setValue(l, "MACHINE_CONFIGURATOR", "item-config", "ft-cargo-point");


                l = new ArrayList<>();
                l.add("_FINALTECH_MESH_TRANSFER");
                l.add("_FINALTECH_ADVANCED_MESH_TRANSFER");
                itemManager.setValue(l, "MACHINE_CONFIGURATOR", "item-config", "ft-cargo-mesh");


                l = new ArrayList<>();
                l.add("_FINALTECH_LINE_TRANSFER");
                l.add("_FINALTECH_ADVANCED_LINE_TRANSFER");
                itemManager.setValue(l, "MACHINE_CONFIGURATOR", "item-config", "ft-cargo-line");


                l = new ArrayList<>();
                l.add("_FINALTECH_LOCATION_TRANSFER");
                l.add("_FINALTECH_ADVANCED_LOCATION_TRANSFER");
                itemManager.setValue(l, "MACHINE_CONFIGURATOR", "item-config", "ft-cargo-location");

                // _FINALTECH_AUTO_ITEM_DISMANTLE_TABLE

                l = new ArrayList<>();
                l.add("enhanced_crafting_table");
                l.add("grind_stone");
                l.add("armor_forge");
                l.add("ore_crusher");
                l.add("compressor");
                l.add("smeltery");
                l.add("pressure_chamber");
                l.add("magic_workbench");
                l.add("gold_pan");
                l.add("juicer");
                l.add("ancient_altar");
                l.add("heated_pressure_chamber");
                l.add("food_fabricator");
                l.add("food_composter");
                l.add("freezer");
                itemManager.setValue(l, "_FINALTECH_AUTO_ITEM_DISMANTLE_TABLE", "allowed-recipe-type");

                l = new ArrayList<>();
                l.add("GOLD_DUST");
                l.add("IRON_DUST");
                l.add("COMPRESSED_COBBLESTONE_1");
                l.add("COMPRESSED_COBBLESTONE_2");
                l.add("COMPRESSED_COBBLESTONE_3");
                l.add("COMPRESSED_COBBLESTONE_4");
                l.add("COMPRESSED_COBBLESTONE_5");
                itemManager.setValue(l, "_FINALTECH_AUTO_ITEM_DISMANTLE_TABLE", "not-allowed-id");

                // config tweak

                configManager.setValue(128, "tweak", "range-limit", "_FINALTECH_ITEM_DISMANTLE_TABLE", "range");
                configManager.setValue(4, "tweak", "range-limit", "_FINALTECH_ITEM_DISMANTLE_TABLE", "mul-range");
                configManager.setValue(true, "tweak", "range-limit", "_FINALTECH_ITEM_DISMANTLE_TABLE", "drop-self");
                configManager.setValue("{1} §4is not allowed to be placed too closely", "tweak", "range-limit", "_FINALTECH_ITEM_DISMANTLE_TABLE", "message");

                configManager.setValue(128, "tweak", "range-limit", "_FINALTECH_ORDERED_DUST_FACTORY_DIRT", "range");
                configManager.setValue(4, "tweak", "range-limit", "_FINALTECH_ORDERED_DUST_FACTORY_DIRT", "mul-range");
                configManager.setValue(false, "tweak", "range-limit", "_FINALTECH_ORDERED_DUST_FACTORY_DIRT", "drop-self");
                configManager.setValue("{1} §4is not allowed to be placed too closely", "tweak", "range-limit", "_FINALTECH_ORDERED_DUST_FACTORY_DIRT", "message");

                configManager.setValue(128, "tweak", "range-limit", "_FINALTECH_ORDERED_DUST_FACTORY_STONE", "range");
                configManager.setValue(4, "tweak", "range-limit", "_FINALTECH_ORDERED_DUST_FACTORY_STONE", "mul-range");
                configManager.setValue(false, "tweak", "range-limit", "_FINALTECH_ORDERED_DUST_FACTORY_STONE", "drop-self");
                configManager.setValue("{1} §4is not allowed to be placed too closely", "tweak", "range-limit", "_FINALTECH_ORDERED_DUST_FACTORY_STONE", "message");

                l = new ArrayList<>();
                l.add("_FINALTECH_ORDERED_DUST_FACTORY_DIRT");
                l.add("_FINALTECH_ORDERED_DUST_FACTORY_STONE");
                configManager.setValue(l, "tweak", "performance-limit", "item");

                l = new ArrayList<>();
                l.add("VARIABLE_WIRE_RESISTANCE");
                l.add("VARIABLE_WIRE_CAPACITOR");
                l.add("ENTROPY_SEED");
                l.add("EQUIVALENT_CONCEPT");
                l.add("MATRIX_GENERATOR");
                l.add("MATRIX_REACTOR");
                configManager.setValue(l, "tweak", "anti-accelerate", "item");

                configManager.setValue(1, "tweak", "general", "_FINALTECH_FUEL_OPERATOR");
                configManager.setValue(2, "tweak", "independent", "_FINALTECH_BASIC_LOGIC_FACTORY");

                if (updateLanguage) {
                    // HELPER: FORCE_CLOSE
                    languageManager.setValue("{color:normal}Force Close {color:conceal}- {color:random}False", "helper", "FORCE_CLOSE", "false", "name");
                    List<String> lore = new ArrayList<>();
                    lore.add("{color:normal}Not close menu if not work");
                    languageManager.setValue(lore, "helper", "FORCE_CLOSE", "false", "lore");

                    languageManager.setValue("{color:normal}Force Close {color:conceal}- {color:random}True", "helper", "FORCE_CLOSE", "true", "name");
                    List<String> lore0 = new ArrayList<>();
                    lore0.add("{color:normal}Close menu if not work");
                    languageManager.setValue(lore0, "helper", "FORCE_CLOSE", "true", "lore");

                    // _FINALTECH_CONFIGURATION_COPIER

                    languageManager.setValue("{color:prandom}Configuration Copier", "items", "_FINALTECH_CONFIGURATION_COPIER", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_CONFIGURATION_COPIER", "info", "1", "name");
                    List<String> lore1 = new ArrayList<>();
                    lore1.add("{color:normal}Copy the configuration of machine it looks at");
                    languageManager.setValue(lore1, "items", "_FINALTECH_CONFIGURATION_COPIER", "info", "1", "lore");
                    languageManager.setValue("_FINALTECH_MACHINE_CONFIGURATOR", "_FINALTECH_CONFIGURATION_COPIER", "info", "1", "output");

                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_CONFIGURATION_COPIER", "info", "2", "name");
                    List<String> lore2 = new ArrayList<>();
                    lore2.add("{color:normal}Use digital item to specify the location");
                    lore2.add("{color:normal}Optional, default 0");
                    languageManager.setValue(lore2, "items", "_FINALTECH_CONFIGURATION_COPIER", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_DIGITAL_ZERO", "_FINALTECH_CONFIGURATION_COPIER", "info", "2", "output");

                    // _FINALTECH_CONFIGURATION_PASTER

                    languageManager.setValue("{color:prandom}Configuration Paster", "items", "_FINALTECH_CONFIGURATION_PASTER", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_CONFIGURATION_PASTER", "info", "1", "name");
                    List<String> lore3 = new ArrayList<>();
                    lore3.add("{color:normal}Copy the configuration of machine it looks at");
                    languageManager.setValue(lore3, "items", "_FINALTECH_CONFIGURATION_PASTER", "info", "1", "lore");
                    languageManager.setValue("_FINALTECH_MACHINE_CONFIGURATOR", "_FINALTECH_CONFIGURATION_PASTER", "info", "1", "output");

                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_CONFIGURATION_PASTER", "info", "2", "name");
                    List<String> lore4 = new ArrayList<>();
                    lore4.add("{color:normal}Use digital item to specify the location");
                    lore4.add("{color:normal}Optional, default 0");
                    languageManager.setValue(lore4, "items", "_FINALTECH_CONFIGURATION_PASTER", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_DIGITAL_ZERO", "_FINALTECH_CONFIGURATION_PASTER", "info", "2", "output");

                    // _FINALTECH_CLICK_WORK_MACHINE

                    languageManager.setValue("{color:prandom}Click Work Machine", "items", "_FINALTECH_CLICK_WORK_MACHINE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_CLICK_WORK_MACHINE", "info", "1", "name");
                    List<String> lore5 = new ArrayList<>();
                    lore5.add("{color:normal}Transfer item from input slot to output slot while being clicked");
                    languageManager.setValue(lore5, "items", "_FINALTECH_CLICK_WORK_MACHINE", "info", "1", "lore");
                    languageManager.setValue("_FINALTECH_MACHINE_CONFIGURATOR", "_FINALTECH_CLICK_WORK_MACHINE", "info", "1", "output");

                    // _FINALTECH_SIMULATE_CLICK_MACHINE

                    languageManager.setValue("{color:prandom}Simulate Click Machine", "items", "_FINALTECH_SIMULATE_CLICK_MACHINE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_SIMULATE_CLICK_MACHINE", "info", "1", "name");
                    List<String> lore6 = new ArrayList<>();
                    lore6.add("{color:normal}Transfer item from input slot to output slot");
                    lore6.add("{color:normal}While there is a digit item in input slot");
                    lore6.add("{color:normal}And the machine in it's bottom will be clicked by around player");
                    lore6.add("{color:normal}Digit to range rate: {color:number}{1}");
                    languageManager.setValue(lore6, "items", "_FINALTECH_SIMULATE_CLICK_MACHINE", "info", "1", "lore");
                    languageManager.setValue("_FINALTECH_DIGITAL_ONE", "_FINALTECH_SIMULATE_CLICK_MACHINE", "info", "1", "output");

                    // _FINALTECH_CONSUMABLE_SIMULATE_CLICK_MACHINE

                    languageManager.setValue("{color:prandom}Simulate Click Machine {color:conceal}- {color:positive}Consumable", "items", "_FINALTECH_CONSUMABLE_SIMULATE_CLICK_MACHINE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_CONSUMABLE_SIMULATE_CLICK_MACHINE", "info", "1", "name");
                    List<String> lore7 = new ArrayList<>();
                    lore7.add("{color:normal}Consume digit item");
                    lore7.add("{color:normal}Then the machine in it's bottom will be clicked by around player");
                    lore7.add("{color:normal}Digit to range rate: {color:number}{1}");
                    languageManager.setValue(lore7, "items", "_FINALTECH_CONSUMABLE_SIMULATE_CLICK_MACHINE", "info", "1", "lore");
                    languageManager.setValue("_FINALTECH_DIGITAL_ONE", "_FINALTECH_CONSUMABLE_SIMULATE_CLICK_MACHINE", "info", "1", "output");

                    // _FINALTECH_ITEM_DISMANTLE_TABLE

                    List<String> lore8 = new ArrayList<>();
                    lore8.add("{color:normal}Charge: {color:number}{1} {color:conceal}/ {color:number}{2}");
                    languageManager.setValue(lore8, "items", "_FINALTECH_ITEM_DISMANTLE_TABLE", "status-icon", "lore");

                    // _FINALTECH_AUTO_ITEM_DISMANTLE_TABLE

                    languageManager.setValue("{color:prandom}Auto Item Dismantle Table", "items", "_FINALTECH_AUTO_ITEM_DISMANTLE_TABLE", "name");

                    // _FINALTECH_ADVANCED_POINT_TRANSFER

                    languageManager.setValue("{color:prandom}Advanced Point Transfer", "items", "_FINALTECH_ADVANCED_POINT_TRANSFER", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ADVANCED_POINT_TRANSFER", "info", "1", "name");
                    languageManager.setValue(List.of("{color:normal}Transfer items from one place to another place"), "items", "_FINALTECH_ADVANCED_POINT_TRANSFER", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ADVANCED_POINT_TRANSFER", "info", "2", "name");
                    languageManager.setValue(List.of("{color:normal}Remote Range: {color:number}{1} Blocks"), "items", "_FINALTECH_ADVANCED_POINT_TRANSFER", "info", "2", "lore");

                    // _FINALTECH_ADVANCED_MESH_TRANSFER

                    languageManager.setValue("{color:prandom}Advanced Mesh Transfer", "items", "_FINALTECH_ADVANCED_MESH_TRANSFER", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ADVANCED_MESH_TRANSFER", "info", "1", "name");
                    languageManager.setValue(List.of("{color:normal}Input items to storage and output them"), "items", "_FINALTECH_ADVANCED_MESH_TRANSFER", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ADVANCED_MESH_TRANSFER", "info", "2", "name");
                    languageManager.setValue(List.of("{color:normal}Can use chain to extend range in remote search mode"), "items", "_FINALTECH_ADVANCED_MESH_TRANSFER", "info", "2", "lore");

                    // _FINALTECH_ADVANCED_LINE_TRANSFER

                    languageManager.setValue("{color:prandom}Advanced Line Transfer", "items", "_FINALTECH_ADVANCED_LINE_TRANSFER", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ADVANCED_LINE_TRANSFER", "info", "1", "name");
                    languageManager.setValue(List.of("{color:normal}Transfer items along the place it look at"), "items", "_FINALTECH_ADVANCED_LINE_TRANSFER", "info", "1", "lore");

                    // _FINALTECH_ADVANCED_LOCATION_TRANSFER

                    languageManager.setValue("{color:prandom}Advanced Location Transfer", "items", "_FINALTECH_ADVANCED_LOCATION_TRANSFER", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ADVANCED_LOCATION_TRANSFER", "info", "1", "name");
                    languageManager.setValue(List.of("{color:normal}Transfer items to the specified location", "{color:normal}Need {id:_FINALTECH_LOCATION_RECORDER} {color:normal}to work"), "items", "_FINALTECH_ADVANCED_LOCATION_TRANSFER", "info", "1", "lore");

                    // _FINALTECH_ADVANCED_AUTO_CRAFT_FRAME

                    languageManager.setValue("{color:prandom}Advanced Auto Craft Frame", "items", "_FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "name");

                    languageManager.setValue("{color:stress}Parse", "items", "_FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-icon", "name");
                    languageManager.setValue(List.of("{color:action}[click] {color:normal}to parse item"), "items", "_FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-icon", "lore");

                    languageManager.setValue("{color:stress}Wiki", "items", "_FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "wiki-icon", "name");
                    List<String> lore9 = new ArrayList<>();
                    lore9.add("{color:normal}↓ Put item here");
                    lore9.add("{color:normal}↙ Click and parse it");
                    lore9.add("{color:normal}↑↑ Put machine item in top two lines to parse item more steps");
                    lore9.add("    {color:normal}Available machine item can be found in slimefun guide.");
                    lore9.add("{color:normal}↑ Put quantity module to speed up");
                    lore9.add("{color:negative}The machine can not work at all");
                    languageManager.setValue(lore9, "items", "_FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "wiki-icon", "lore");

                    languageManager.setValue("{color:stress}Parse Failed", "items", "_FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-failed-icon", "name");
                    languageManager.setValue(List.of("{color:normal}Please try to parse different item"), "items", "_FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-failed-icon", "lore");

                    languageManager.setValue("{color:positive}Parse Success", "items", "_FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-extend-icon", "name");
                    languageManager.setValue(List.of("{color:action}[click] {color:normal}to get more information"), "items", "_FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-extend-icon", "lore");

                    languageManager.setValue("{color:positive}Parse Success", "items", "_FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-success-icon", "name");
                    languageManager.setValue("{color:conceal}Amount : {1}", "items", "_FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "parse-amount");

                    // _FINALTECH_BOX

                    languageManager.setValue("{color:normal}You forget something :D", "items", "_FINALTECH_BOX", "message");

                    // _FINALTECH_SHINE

                    languageManager.setValue("{color:normal}You forget something :D", "items", "_FINALTECH_SHINE", "message");
                    languageManager.setValue("{color:negative}Void Curse", "items", "_FINALTECH_SHINE", "info", "2", "name");
                    languageManager.setValue("{color:negative}Notice", "items", "_FINALTECH_SHINE", "info", "3", "name");
                    languageManager.setValue("{color:negative}Notice", "items", "_FINALTECH_SHINE", "info", "4", "name");
                    languageManager.setValue("{color:negative}Notice", "items", "_FINALTECH_SHINE", "info", "5", "name");

                    languageManager.setValue(
                            List.of("{color:normal}While obtaining {id:_FINALTECH_SHINE}",
                                    "{color:normal}Player will be cursed by void for a short time"),
                            "items", "_FINALTECH_SHINE", "info", "2", "lore");
                    languageManager.setValue(
                            List.of("{color:normal}While carrying {id:_FINALTECH_SHINE} {color:normal}or {id:_FINALTECH_BOX}",
                                    "{color:normal}The void damage player get will be enlarged greatly"),
                            "items", "_FINALTECH_SHINE", "info", "3", "lore");
                    languageManager.setValue(
                            List.of("{color:normal}While carrying {id:_FINALTECH_BOX}",
                                    "{color:normal}if player is in low place or cursed by void",
                                    "{color:negative}Player will lose all his items after dead!!!"),
                            "items", "_FINALTECH_SHINE", "info", "4", "lore");
                    languageManager.setValue(
                            List.of("{color:normal}While carrying {id:_FINALTECH_BOX}",
                                    "{color:normal}if player is in low place or cursed by void",
                                    "{color:negative}Player will lose all his items after dead!!!",
                                    "",
                                    "{color:negative}Soul Bound Rune may be useful"),
                            "items", "_FINALTECH_SHINE", "info", "5", "lore");

                    // VOID_CURSE

                    languageManager.setValue("{1} {color:normal}is swallowed by the void.", "effect", "VOID_CURSE", "message", "death");

                    // ManualCraftMachine

                    languageManager.setValue(List.of("{color:normal}Energy: {color:number}{1}J"), "items", "ManualCraftMachine", "craft-icon", "lore");

                    // _FINALTECH_ITEM_SERIALIZATION_CONSTRUCTOR

                    List<String> list10 = languageManager.getStringList("items", "_FINALTECH_ITEM_SERIALIZATION_CONSTRUCTOR", "copy-card", "lore");
                    list10.add("{color:normal}Rate: {color:number}{4}");
                    languageManager.setValue(list10, "items", "_FINALTECH_ITEM_SERIALIZATION_CONSTRUCTOR", "copy-card", "lore");

                    // _FINALTECH_MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR

                    List<String> list11 = languageManager.getStringList("items", "_FINALTECH_MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR", "copy-card", "lore");
                    list11.add("{color:normal}Rate: {color:number}{4}");
                    languageManager.setValue(list11, "items", "_FINALTECH_MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR", "copy-card", "lore");

                    // HELPER-RECIPE_ITEM

                    languageManager.setValue("{color:stress}Display Recipe", "helper", "ICON", "recipe-icon", "name");

                    // MESSAGE-INVALID_ITEM

                    languageManager.setValue("{color:negative}Not valid item!", "message", "invalid-item");

                }
            }
        });

        versionMap.put("20230109", new UpdateFunction() {
            @Override
            protected String targetVersion() {
                return "20230211";
            }

            @Override
            protected void update(FinalTechChanged finalTech) {
                if (configManager.containPath("tweak", "range-limit", "_FINALTECH_ITEM_DISMANTLE_TABLE", "drop-self")) {
                    Boolean dropSelf = configManager.getOrDefault(false, "tweak", "range-limit", "_FINALTECH_ITEM_DISMANTLE_TABLE", "drop-self");
                    if (dropSelf) {
                        configManager.setValue(false, "tweak", "range-limit", "_FINALTECH_ITEM_DISMANTLE_TABLE", "drop-self");
                    }
                }
            }
        });

        versionMap.put("20230211", new UpdateFunction() {
            @Override
            protected String targetVersion() {
                return "20230326";
            }

            @Override
            protected void update(FinalTechChanged finalTech) {
                List<String> list = new ArrayList<>();
                list.add("_FINALTECH_POINT_TRANSFER");
                list.add("_FINALTECH_ADVANCED_POINT_TRANSFER");
                list.add("_FINALTECH_MESH_TRANSFER");
                list.add("_FINALTECH_ADVANCED_MESH_TRANSFER");
                list.add("_FINALTECH_LINE_TRANSFER");
                list.add("_FINALTECH_ADVANCED_LINE_TRANSFER");
                list.add("_FINALTECH_LOCATION_TRANSFER");
                list.add("_FINALTECH_ADVANCED_LOCATION_TRANSFER");

                itemManager.setValue(list, "_FINALTECH_ROUTE_VIEWER", "allowed-id");

                list = new ArrayList<>();
                list.add("CARGO_NODE_INPUT");
                list.add("CARGO_NODE_OUTPUT");
                list.add("CARGO_NODE_OUTPUT_ADVANCED");
                itemManager.setValue(list, "_FINALTECH_MACHINE_CONFIGURATOR", "item-config", "cargo-node");

                list = new ArrayList<>();
                list.add("_FINALTECH_ADVANCED_COMPOSTER");
                list.add("_FINALTECH_ADVANCED_JUICER");
                list.add("_FINALTECH_ADVANCED_FURNACE");
                list.add("_FINALTECH_ADVANCED_GOLD_PAN");
                list.add("_FINALTECH_ADVANCED_DUST_WASHER");
                list.add("_FINALTECH_ADVANCED_INGOT_FACTORY");
                list.add("_FINALTECH_ADVANCED_CRUCIBLE");
                list.add("_FINALTECH_ADVANCED_ORE_GRINDER");
                list.add("_FINALTECH_ADVANCED_HEATED_PRESSURE_CHAMBER");
                list.add("_FINALTECH_ADVANCED_INGOT_PULVERIZER");
                list.add("_FINALTECH_ADVANCED_AUTO_DRIER");
                list.add("_FINALTECH_ADVANCED_PRESS");
                list.add("_FINALTECH_ADVANCED_FREEZER");
                list.add("_FINALTECH_ADVANCED_CARBON_PRESS");
                list.add("_FINALTECH_ADVANCED_SMELTERY");
                list.add("_FINALTECH_ADVANCED_DUST_FACTORY");
                list.add("_FINALTECH_ADVANCED_COMPOSTER");
                list.add("_FINALTECH_ADVANCED_COMPOSTER");
                itemManager.setValue(list, "_FINALTECH_MACHINE_CONFIGURATOR", "item-config", "advanced-machine");

                list = new ArrayList<>();
                list.add("_FINALTECH_POINT_TRANSFER");
                list.add("_FINALTECH_ADVANCED_POINT_TRANSFER");
                itemManager.setValue(list, "_FINALTECH_MACHINE_CONFIGURATOR", "item-config", "ft-cargo-point");

                list = new ArrayList<>();
                list.add("_FINALTECH_MESH_TRANSFER");
                list.add("_FINALTECH_ADVANCED_MESH_TRANSFER");
                itemManager.setValue(list, "_FINALTECH_MACHINE_CONFIGURATOR", "item-config", "ft-cargo-mesh");

                list = new ArrayList<>();
                list.add("_FINALTECH_LINE_TRANSFER");
                list.add("_FINALTECH_ADVANCED_LINE_TRANSFER");
                itemManager.setValue(list, "_FINALTECH_MACHINE_CONFIGURATOR", "item-config", "ft-cargo-line");

                list = new ArrayList<>();
                list.add("_FINALTECH_LOCATION_TRANSFER");
                list.add("_FINALTECH_ADVANCED_LOCATION_TRANSFER");
                itemManager.setValue(list, "_FINALTECH_MACHINE_CONFIGURATOR", "item-config", "ft-cargo-location");

                if (updateLanguage) {
                    // _FINALTECH_FLINT_AND_STEEL_CARD
                    languageManager.setValue("{color:prandom}Flint and steel Card", "items", "_FINALTECH_FLINT_AND_STEEL_CARD", "name");
                    languageManager.setValue("{color:positive}Usage", "items", "_FINALTECH_FLINT_AND_STEEL_CARD", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Can be used to replace fline and steel in some machine");
                    languageManager.setValue(list, "items", "_FINALTECH_FLINT_AND_STEEL_CARD", "info", "1", "lore");

                    // _FINALTECH_ENTROPY
                    languageManager.setValue("{color:positive}Production method", "items", "_FINALTECH_ENTROPY", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Can be obtained in {id:_FINALTECH_BASIC_LOGIC_FACTORY}");
                    languageManager.setValue(list, "items", "_FINALTECH_ENTROPY", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_BASIC_LOGIC_FACTORY", "items", "_FINALTECH_ENTROPY", "info", "2", "output");

                    // _FINALTECH_ETHER
                    languageManager.setValue("{color:prandom}Ether", "items", "_FINALTECH_ETHER", "name");
                    languageManager.setValue("{color:positive}Production method", "items", "_FINALTECH_ETHER", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}This is a GEO resource");
                    list.add("{color:normal}It should be mined by {id:_FINALTECH_ETHER_MINER}");
                    languageManager.setValue(list, "items", "_FINALTECH_ETHER", "info", "1", "lore");
                    languageManager.setValue("_FINALTECH_ETHER_MINER", "items", "_FINALTECH_ETHER", "info", "1", "output");

                    // _FINALTECH_QUANTITY_MODULE
                    languageManager.setValue("{color:positive}Mechanism", "items", "_FINALTECH_QUANTITY_MODULE", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Efficiency: {color:number}n * {1} + nd{2} {color:conceal}(n = item amount)");
                    languageManager.setValue(list, "items", "_FINALTECH_QUANTITY_MODULE", "info", "2", "lore");

                    // _FINALTECH_ENERGIZED_QUANTITY_MODULE
                    languageManager.setValue("{color:prandom}Quantity Module {color:conceal}- {color:positive}Energized", "items", "_FINALTECH_ENERGIZED_QUANTITY_MODULE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGIZED_QUANTITY_MODULE", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Can tweak the efficiency of some machines");
                    list.add("{color:normal}Efficiency tweak is downward compatible");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGIZED_QUANTITY_MODULE", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGIZED_QUANTITY_MODULE", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Efficiency: {color:number}n * {1} + nd{2} {color:conceal}(n = item amount)");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGIZED_QUANTITY_MODULE", "info", "2", "lore");

                    // _FINALTECH_OVERLOADED_QUANTITY_MODULE
                    languageManager.setValue("{color:prandom}Quantity Module {color:conceal}- {color:positive}Energized", "items", "_FINALTECH_OVERLOADED_QUANTITY_MODULE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_OVERLOADED_QUANTITY_MODULE", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Can tweak the efficiency of some machines");
                    list.add("{color:normal}Efficiency tweak is downward compatible");
                    languageManager.setValue(list, "items", "_FINALTECH_OVERLOADED_QUANTITY_MODULE", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_OVERLOADED_QUANTITY_MODULE", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Efficiency: {color:number}n * {1} + nd{2} {color:conceal}(n = item amount)");
                    languageManager.setValue(list, "items", "_FINALTECH_OVERLOADED_QUANTITY_MODULE", "info", "2", "lore");

                    // _FINALTECH_PHONY
                    languageManager.setValue("_FINALTECH_ITEM_SERIALIZATION_CONSTRUCTOR", "items", "_FINALTECH_PHONY", "info", "1", "output");
                    languageManager.setValue("{color:initiative}Production method", "items", "_FINALTECH_PHONY", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Can be crafted in {id:_FINALTECH_CARD_OPERATION_TABLE}");
                    languageManager.setValue(list, "items", "_FINALTECH_PHONY", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_CARD_OPERATION_TABLE", "items", "_FINALTECH_PHONY", "info", "2", "output");

                    // _FINALTECH_ENERGY_CARD_K
                    languageManager.setValue("{color:prandom}Energy Card {color:conceal}- {color:positive}1K", "items", "_FINALTECH_ENERGY_CARD_K", "name");
                    languageManager.setValue("{color:passive}Usage", "items", "_FINALTECH_ENERGY_CARD_K", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Charge machine for {color:number}{1} J");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_CARD_K", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGY_CARD_K", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Support expanded capacitor");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_CARD_K", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_SMALL_EXPANDED_CAPACITOR", "items", "_FINALTECH_ENERGY_CARD_K", "info", "2", "output");

                    // _FINALTECH_ENERGY_CARD_M
                    languageManager.setValue("{color:prandom}Energy Card {color:conceal}- {color:positive}1K", "items", "_FINALTECH_ENERGY_CARD_M", "name");
                    languageManager.setValue("{color:passive}Usage", "items", "_FINALTECH_ENERGY_CARD_M", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Charge machine for {color:number}{1} J");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_CARD_M", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGY_CARD_M", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Support expanded capacitor");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_CARD_M", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_SMALL_EXPANDED_CAPACITOR", "items", "_FINALTECH_ENERGY_CARD_M", "info", "2", "output");

                    // _FINALTECH_ENERGY_CARD_B
                    languageManager.setValue("{color:prandom}Energy Card {color:conceal}- {color:positive}1K", "items", "_FINALTECH_ENERGY_CARD_B", "name");
                    languageManager.setValue("{color:passive}Usage", "items", "_FINALTECH_ENERGY_CARD_B", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Charge machine for {color:number}{1} J");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_CARD_B", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGY_CARD_B", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Support expanded capacitor");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_CARD_B", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_SMALL_EXPANDED_CAPACITOR", "items", "_FINALTECH_ENERGY_CARD_B", "info", "2", "output");

                    // _FINALTECH_ENERGY_CARD_T
                    languageManager.setValue("{color:prandom}Energy Card {color:conceal}- {color:positive}1K", "items", "_FINALTECH_ENERGY_CARD_T", "name");
                    languageManager.setValue("{color:passive}Usage", "items", "_FINALTECH_ENERGY_CARD_T", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Charge machine for {color:number}{1} J");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_CARD_T", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGY_CARD_T", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Support expanded capacitor");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_CARD_T", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_SMALL_EXPANDED_CAPACITOR", "items", "_FINALTECH_ENERGY_CARD_T", "info", "2", "output");

                    // _FINALTECH_ENERGY_CARD_Q
                    languageManager.setValue("{color:prandom}Energy Card {color:conceal}- {color:positive}1K", "items", "_FINALTECH_ENERGY_CARD_Q", "name");
                    languageManager.setValue("{color:passive}Usage", "items", "_FINALTECH_ENERGY_CARD_Q", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Charge machine for {color:number}{1} J");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_CARD_Q", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGY_CARD_Q", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Support expanded capacitor");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_CARD_Q", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_SMALL_EXPANDED_CAPACITOR", "items", "_FINALTECH_ENERGY_CARD_Q", "info", "2", "output");

                    // _FINALTECH_ROUTE_VIEWER
                    languageManager.setValue("{color:prandom}Route Viewer", "items", "_FINALTECH_ROUTE_VIEWER", "name");
                    languageManager.setValue("{color:passive}Usage", "items", "_FINALTECH_ROUTE_VIEWER", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:action}[Right-click] {color:normal}Toggle route display for some machines");
                    languageManager.setValue(list, "items", "_FINALTECH_ROUTE_VIEWER", "info", "1", "lore");

                    // _FINALTECH_MACHINE_CONFIGURATOR
                    languageManager.setValue("{color:prandom}Machine Configurator", "items", "_FINALTECH_MACHINE_CONFIGURATOR", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_MACHINE_CONFIGURATOR", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:action}[Right-click] {color:normal}Paste config to th machine you are looking at");
                    list.add("{color:action}[Shift + Right-click] {color:normal}Copy the machine config you are looking at");
                    languageManager.setValue(list, "items", "_FINALTECH_MACHINE_CONFIGURATOR", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_MACHINE_CONFIGURATOR", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Machines in same group can share their config");
                    list.add("");
                    list.add("{color:conceal}For example, you can copy the config of {id:CARGO_NODE_OUTPUT_ADVANCED}");
                    list.add("{color:conceal}Then paste it to {id:CARGO_NODE_INPUT}");
                    languageManager.setValue(list, "items", "_FINALTECH_MACHINE_CONFIGURATOR", "info", "2", "lore");

                    // _FINALTECH_PORTABLE_ENERGY_STORAGE
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_PORTABLE_ENERGY_STORAGE", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Can be used in {id:_FINALTECH_ENERGY_TABLE}");
                    languageManager.setValue(list, "items", "_FINALTECH_PORTABLE_ENERGY_STORAGE", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_ENERGY_TABLE", "items", "_FINALTECH_PORTABLE_ENERGY_STORAGE", "info", "2", "output");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_PORTABLE_ENERGY_STORAGE", "info", "3", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Can be used to craft energy card");
                    languageManager.setValue(list, "items", "_FINALTECH_PORTABLE_ENERGY_STORAGE", "info", "3", "lore");
                    languageManager.setValue("_FINALTECH_ENERGY_CARD_K", "items", "_FINALTECH_PORTABLE_ENERGY_STORAGE", "info", "3", "output");

                    // _FINALTECH_BASIC_GENERATOR
                    list = new ArrayList<>();
                    list.add("{color:normal}Affected Machine: {color:number}{1}");
                    list.add("{color:normal}Total Energy: {color:number}{2} J");
                    languageManager.setValue(list, "items", "_FINALTECH_BASIC_GENERATOR", "status", "lore");

                    // _FINALTECH_ADVANCED_GENERATOR
                    list = new ArrayList<>();
                    list.add("{color:normal}Affected Machine: {color:number}{1}");
                    list.add("{color:normal}Total Energy: {color:number}{2} J");
                    languageManager.setValue(list, "items", "_FINALTECH_ADVANCED_GENERATOR", "status", "lore");

                    // _FINALTECH_CARBONADO_GENERATOR
                    list = new ArrayList<>();
                    list.add("{color:normal}Affected Machine: {color:number}{1}");
                    list.add("{color:normal}Total Energy: {color:number}{2} J");
                    languageManager.setValue(list, "items", "_FINALTECH_CARBONADO_GENERATOR", "status", "lore");

                    // _FINALTECH_ENERGIZED_GENERATOR
                    list = new ArrayList<>();
                    list.add("{color:normal}Affected Machine: {color:number}{1}");
                    list.add("{color:normal}Total Energy: {color:number}{2} J");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGIZED_GENERATOR", "status", "lore");

                    // _FINALTECH_ENERGIZED_STACK_GENERATOR
                    list = new ArrayList<>();
                    list.add("{color:normal}Affected Machine: {color:number}{1}");
                    list.add("{color:normal}Total Energy: {color:number}{2} J");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGIZED_STACK_GENERATOR", "status", "lore");

                    // _FINALTECH_OVERLOADED_GENERATOR
                    list = new ArrayList<>();
                    list.add("{color:normal}Affected Machine: {color:number}{1}");
                    list.add("{color:normal}Total Energy: {color:number}{2} J");
                    languageManager.setValue(list, "items", "_FINALTECH_OVERLOADED_GENERATOR", "status", "lore");

                    // _FINALTECH_ORDERED_DUST_GENERATOR
                    languageManager.setValue("_FINALTECH_PHONY", "items", "_FINALTECH_OVERLOADED_GENERATOR", "info", "2", "output");

                    // _FINALTECH_SMALL_EXPANDED_CAPACITOR
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_SMALL_EXPANDED_CAPACITOR", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Be compatible with {id:_FINALTECH_PORTABLE_ENERGY_STORAGE}");
                    languageManager.setValue(list, "items", "_FINALTECH_SMALL_EXPANDED_CAPACITOR", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_PORTABLE_ENERGY_STORAGE", "items", "_FINALTECH_SMALL_EXPANDED_CAPACITOR", "info", "2", "output");

                    // _FINALTECH_MEDIUM_EXPANDED_CAPACITOR
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_MEDIUM_EXPANDED_CAPACITOR", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Be compatible with {id:_FINALTECH_PORTABLE_ENERGY_STORAGE}");
                    languageManager.setValue(list, "items", "_FINALTECH_MEDIUM_EXPANDED_CAPACITOR", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_PORTABLE_ENERGY_STORAGE", "items", "_FINALTECH_MEDIUM_EXPANDED_CAPACITOR", "info", "2", "output");

                    // _FINALTECH_BIG_EXPANDED_CAPACITOR
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_BIG_EXPANDED_CAPACITOR", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Be compatible with {id:_FINALTECH_PORTABLE_ENERGY_STORAGE}");
                    languageManager.setValue(list, "items", "_FINALTECH_BIG_EXPANDED_CAPACITOR", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_PORTABLE_ENERGY_STORAGE", "items", "_FINALTECH_BIG_EXPANDED_CAPACITOR", "info", "2", "output");

                    // _FINALTECH_LARGE_EXPANDED_CAPACITOR
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_LARGE_EXPANDED_CAPACITOR", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Be compatible with {id:_FINALTECH_PORTABLE_ENERGY_STORAGE}");
                    languageManager.setValue(list, "items", "_FINALTECH_LARGE_EXPANDED_CAPACITOR", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_PORTABLE_ENERGY_STORAGE", "items", "_FINALTECH_LARGE_EXPANDED_CAPACITOR", "info", "2", "output");

                    // _FINALTECH_CARBONADO_EXPANDED_CAPACITOR
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_CARBONADO_EXPANDED_CAPACITOR", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Be compatible with {id:_FINALTECH_PORTABLE_ENERGY_STORAGE}");
                    languageManager.setValue(list, "items", "_FINALTECH_CARBONADO_EXPANDED_CAPACITOR", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_PORTABLE_ENERGY_STORAGE", "items", "_FINALTECH_CARBONADO_EXPANDED_CAPACITOR", "info", "2", "output");

                    // _FINALTECH_ENERGIZED_EXPANDED_CAPACITOR
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGIZED_EXPANDED_CAPACITOR", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Be compatible with {id:_FINALTECH_PORTABLE_ENERGY_STORAGE}");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGIZED_EXPANDED_CAPACITOR", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_PORTABLE_ENERGY_STORAGE", "items", "_FINALTECH_ENERGIZED_EXPANDED_CAPACITOR", "info", "2", "output");

                    // _FINALTECH_ENERGIZED_STACK_EXPANDED_CAPACITOR
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGIZED_STACK_EXPANDED_CAPACITOR", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Be compatible with {id:_FINALTECH_PORTABLE_ENERGY_STORAGE}");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGIZED_STACK_EXPANDED_CAPACITOR", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_PORTABLE_ENERGY_STORAGE", "items", "_FINALTECH_ENERGIZED_STACK_EXPANDED_CAPACITOR", "info", "2", "output");

                    // _FINALTECH_OVERLOADED_EXPANDED_CAPACITOR
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_OVERLOADED_EXPANDED_CAPACITOR", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Be compatible with {id:_FINALTECH_PORTABLE_ENERGY_STORAGE}");
                    languageManager.setValue(list, "items", "_FINALTECH_OVERLOADED_EXPANDED_CAPACITOR", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_PORTABLE_ENERGY_STORAGE", "items", "_FINALTECH_OVERLOADED_EXPANDED_CAPACITOR", "info", "2", "output");

                    // _FINALTECH_VARIABLE_WIRE_RESISTANCE
                    languageManager.setValue("_FINALTECH_VARIABLE_WIRE_CAPACITOR", "items", "_FINALTECH_VARIABLE_WIRE_RESISTANCE", "info", "1", "output");

                    // _FINALTECH_VARIABLE_WIRE_CAPACITOR
                    languageManager.setValue("_FINALTECH_VARIABLE_WIRE_RESISTANCE", "items", "_FINALTECH_VARIABLE_WIRE_CAPACITOR", "info", "1", "output");

                    // _FINALTECH_ENERGIZED_ACCELERATOR
                    languageManager.setValue("{color:prandom}Energized Accelerator", "items", "_FINALTECH_ENERGIZED_ACCELERATOR", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGIZED_ACCELERATOR", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Consume energy to make around machine work more times");
                    list.add("{color:normal}Range: {color:number}{1}");
                    list.add("{color:normal}Capacity: {color:number}{2} J");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGIZED_ACCELERATOR", "info", "1", "lore");
                    languageManager.setValue("{color:negative}Limit", "items", "_FINALTECH_ENERGIZED_ACCELERATOR", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Only support machine that can store energy");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGIZED_ACCELERATOR", "info", "2", "lore");
                    list = new ArrayList<>();
                    list.add("{color:normal}Stored Energy: {color:number}{1} J");
                    list.add("{color:normal}Accelerated Machines: {color:number}{2}");
                    list.add("{color:normal}Accelerated Round Times: {color:number}{3}");
                    list.add("{color:normal}Accelerated Total Times: {color:number}{4}");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGIZED_ACCELERATOR", "status", "lore");

                    // _FINALTECH_OVERLOADED_ACCELERATOR
                    list = new ArrayList<>();
                    list.add("{color:normal}Available Machines: {color:number}{1}");
                    list.add("{color:normal}Accelerated Machines: {color:number}{2}");
                    languageManager.setValue(list, "items", "_FINALTECH_OVERLOADED_ACCELERATOR", "status", "lore");

                    // _FINALTECH_RANDOM_ACCESSOR
                    languageManager.setValue("{color:prandom}Random Accessor", "items", "_FINALTECH_RANDOM_ACCESSOR", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_RANDOM_ACCESSOR", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Right click it to randomly open slimefun machine around it");
                    languageManager.setValue(list, "items", "_FINALTECH_RANDOM_ACCESSOR", "info", "1", "lore");

                    // _FINALTECH_LOGIC_CRAFTER
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_LOGIC_CRAFTER", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}User logic item to craft digit item");
                    languageManager.setValue(list, "items", "_FINALTECH_LOGIC_CRAFTER", "info", "1", "lore");
                    languageManager.setValue("_FINALTECH_DIGITAL_ZERO", "items", "_FINALTECH_LOGIC_CRAFTER", "info", "1", "output");

                    // _FINALTECH_DIGIT_ADDER
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_DIGIT_ADDER", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Add two digit item to get one new digit item");
                    languageManager.setValue(list, "items", "_FINALTECH_DIGIT_ADDER", "info", "1", "lore");

                    // _FINALTECH_BEDROCK_CRAFT_TABLE
                    languageManager.setValue("{color:prandom}Bedrock Craft table", "items", "_FINALTECH_BEDROCK_CRAFT_TABLE", "name");
                    languageManager.setValue("{color:passive}Introduction", "items", "_FINALTECH_BEDROCK_CRAFT_TABLE", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Put item orderly to craft");
                    languageManager.setValue(list, "items", "_FINALTECH_BEDROCK_CRAFT_TABLE", "info", "1", "lore");

                    // _FINALTECH_ITEM_DISMANTLE_TABLE
                    languageManager.setValue("{color:passive}Introduction", "items", "_FINALTECH_ITEM_DISMANTLE_TABLE", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Restore item to its raw materials");
                    languageManager.setValue(list, "items", "_FINALTECH_ITEM_DISMANTLE_TABLE", "info", "1", "lore");

                    // _FINALTECH_AUTO_ITEM_DISMANTLE_TABLE
                    languageManager.setValue("{color:passive}Introduction", "items", "_FINALTECH_AUTO_ITEM_DISMANTLE_TABLE", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Restore item to its raw materials");
                    languageManager.setValue(list, "items", "_FINALTECH_AUTO_ITEM_DISMANTLE_TABLE", "info", "1", "lore");

                    // _FINALTECH_EQUIVALENT_EXCHANGE_TABLE
                    languageManager.setValue("_FINALTECH_UNORDERED_DUST", "items", "_FINALTECH_EQUIVALENT_EXCHANGE_TABLE", "info", "1", "output");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_EQUIVALENT_EXCHANGE_TABLE", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}{id:_FINALTECH_BUG} {color:normal}may be randomly generated");
                    list.add("{color:normal}and all stored value will be cleared at the same time");
                    languageManager.setValue(list, "items", "_FINALTECH_EQUIVALENT_EXCHANGE_TABLE", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_BUG", "items", "_FINALTECH_EQUIVALENT_EXCHANGE_TABLE", "info", "2", "output");

                    // _FINALTECH_CARD_OPERATION_TABLE
                    languageManager.setValue("{color:passive}Entropy", "items", "_FINALTECH_CARD_OPERATION_TABLE", "entropy", "info-icon", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Use {id:_FINALTECH_ENTROPY} {color:normal}to replace item in {id:_FINALTECH_STORAGE_CARD} {color:normal}or {id:_FINALTECH_COPY_CARD}");
                    languageManager.setValue(list, "items", "_FINALTECH_CARD_OPERATION_TABLE", "entropy", "info-icon", "lore");
                    languageManager.setValue("_FINALTECH_ENTROPY", "items", "_FINALTECH_CARD_OPERATION_TABLE", "entropy", "info-icon", "output");
                    languageManager.setValue("{color:passive}Craft Infinity Storage Card", "items", "_FINALTECH_CARD_OPERATION_TABLE", "craft-infinity-storage-card", "info-icon", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Use {id:_FINALTECH_PHONY} {color:normal}and {id:_FINALTECH_COPY_CARD} {color:normal}to craft one {id:_FINALTECH_STORAGE_CARD} {color:normal}with infinity amount");
                    languageManager.setValue(list, "items", "_FINALTECH_CARD_OPERATION_TABLE", "craft-infinity-storage-card", "info-icon", "lore");
                    languageManager.setValue("_FINALTECH_STORAGE_CARD", "items", "_FINALTECH_CARD_OPERATION_TABLE", "craft-infinity-storage-card", "info-icon", "output");
                    languageManager.setValue("{color:passive}Distribute Storage Card", "items", "_FINALTECH_CARD_OPERATION_TABLE", "distribute-storage-card", "info-icon", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Distribute item in one {id:_FINALTECH_STORAGE_CARD} {color:normal}to another empty {id:_FINALTECH_STORAGE_CARD}");
                    languageManager.setValue(list, "items", "_FINALTECH_CARD_OPERATION_TABLE", "distribute-storage-card", "info-icon", "lore");
                    languageManager.setValue("_FINALTECH_STORAGE_CARD", "items", "_FINALTECH_CARD_OPERATION_TABLE", "distribute-storage-card", "info-icon", "output");

                    // _FINALTECH_ADVANCED_AUTO_CRAFT_FRAME
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Parse how slimefun item is crafted");
                    languageManager.setValue(list, "items", "_FINALTECH_ADVANCED_AUTO_CRAFT_FRAME", "info", "1", "lore");

                    // _FINALTECH_ETHER_MINER
                    languageManager.setValue("{color:prandom}Ether Miner", "items", "_FINALTECH_ETHER_MINER", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ETHER_MINER", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Cost {id:_FINALTECH_UNORDERED_DUST} {color:normal}to start mine work");
                    list.add("{color:normal}You should scan the GEO resource first");
                    languageManager.setValue(list, "items", "_FINALTECH_ETHER_MINER", "info", "1", "lore");
                    languageManager.setValue("_FINALTECH_UNORDERED_DUST", "items", "_FINALTECH_ETHER_MINER", "info", "1", "output");
                    languageManager.setValue("{color:normal}Status", "items", "_FINALTECH_ETHER_MINER", "status", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Progress: {color:number}{1} {color:conceal}/ {color:number}{2}");
                    list.add("{color:normal}Ether in this chunk: {color:number}{3}");
                    languageManager.setValue(list, "items", "_FINALTECH_ETHER_MINER", "status", "lore");

                    // _FINALTECH_ENERGY_TABLE
                    languageManager.setValue("{color:prandom}Energy Table", "items", "_FINALTECH_ENERGY_TABLE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGY_TABLE", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Can input energy to {id:_FINALTECH_PORTABLE_ENERGY_STORAGE}");
                    list.add("{color:normal}Can output energy from {id:_FINALTECH_PORTABLE_ENERGY_STORAGE}");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_TABLE", "info", "1", "lore");
                    languageManager.setValue("_FINALTECH_PORTABLE_ENERGY_STORAGE", "items", "_FINALTECH_ENERGY_TABLE", "info", "1", "output");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGY_TABLE", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Use energy card to input energy");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_TABLE", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_ENERGY_CARD_K", "items", "_FINALTECH_ENERGY_TABLE", "info", "2", "output");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGY_TABLE", "info", "3", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Output energy will be replaced by energy card");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_TABLE", "info", "3", "lore");
                    languageManager.setValue("_FINALTECH_ENERGY_CARD_K", "items", "_FINALTECH_ENERGY_TABLE", "info", "3", "output");

                    // _FINALTECH_ENERGY_INPUT_TABLE
                    languageManager.setValue("{color:prandom}Energy Input Table", "items", "_FINALTECH_ENERGY_INPUT_TABLE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGY_INPUT_TABLE", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Can input energy to {id:_FINALTECH_PORTABLE_ENERGY_STORAGE}");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_INPUT_TABLE", "info", "1", "lore");
                    languageManager.setValue("_FINALTECH_PORTABLE_ENERGY_STORAGE", "items", "_FINALTECH_ENERGY_INPUT_TABLE", "info", "1", "output");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGY_INPUT_TABLE", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Use energy card to input energy");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_INPUT_TABLE", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_ENERGY_CARD_K", "items", "_FINALTECH_ENERGY_INPUT_TABLE", "info", "2", "output");

                    // _FINALTECH_ENERGY_OUTPUT_TABLE
                    languageManager.setValue("{color:prandom}Energy Output Table", "items", "_FINALTECH_ENERGY_OUTPUT_TABLE", "name");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGY_OUTPUT_TABLE", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Can output energy from {id:_FINALTECH_PORTABLE_ENERGY_STORAGE}");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_OUTPUT_TABLE", "info", "1", "lore");
                    languageManager.setValue("_FINALTECH_PORTABLE_ENERGY_STORAGE", "items", "_FINALTECH_ENERGY_OUTPUT_TABLE", "info", "1", "output");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGY_OUTPUT_TABLE", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Output energy will be replaced by energy card");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGY_OUTPUT_TABLE", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_ENERGY_CARD_K", "items", "_FINALTECH_ENERGY_OUTPUT_TABLE", "info", "2", "output");

                    // _FINALTECH_FUEL_OPERATOR
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_FUEL_OPERATOR", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Place it below the machine to work");
                    languageManager.setValue(list, "items", "_FINALTECH_FUEL_OPERATOR", "info", "1", "lore");

                    // _FINALTECH_OPERATION_ACCELERATOR
                    languageManager.setValue("{color:passive}Introduction", "items", "_FINALTECH_OPERATION_ACCELERATOR", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Place it below the machine to work");
                    list.add("{color:normal}It will accelerate the work progress of the machine by consuming energy");
                    languageManager.setValue(list, "items", "_FINALTECH_OPERATION_ACCELERATOR", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_OPERATION_ACCELERATOR", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Efficiency : {color:number}nd{1} {color:conceal}(n = item amount)");
                    list.add("{color:normal}Put {id:_FINALTECH_OPERATION_ACCELERATOR} {color:normal}to increase efficiency");
                    languageManager.setValue(list, "items", "_FINALTECH_OPERATION_ACCELERATOR", "info", "2", "lore");
                    list = new ArrayList<>();
                    list.add("{color:normal}Stored Energy: {color:number}{1} J");
                    list.add("{color:normal}Times: {color:number}{2}");
                    languageManager.setValue(list, "items", "_FINALTECH_OPERATION_ACCELERATOR", "status", "lore");

                    // _FINALTECH_ENERGIZED_OPERATION_ACCELERATOR
                    languageManager.setValue("{color:prandom}Energized Operation Accelerator", "items", "_FINALTECH_ENERGIZED_OPERATION_ACCELERATOR", "name");
                    languageManager.setValue("{color:passive}Introduction", "items", "_FINALTECH_ENERGIZED_OPERATION_ACCELERATOR", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Place it below the machine to work");
                    list.add("{color:normal}It will accelerate the work progress of the machine by consuming energy");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGIZED_OPERATION_ACCELERATOR", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_ENERGIZED_OPERATION_ACCELERATOR", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Efficiency : {color:number}nd{1} {color:conceal}(n = item amount)");
                    list.add("{color:normal}Put {id:_FINALTECH_ENERGIZED_OPERATION_ACCELERATOR} {color:normal}to increase efficiency");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGIZED_OPERATION_ACCELERATOR", "info", "2", "lore");
                    list = new ArrayList<>();
                    list.add("{color:normal}Stored Energy: {color:number}{1} J");
                    list.add("{color:normal}Times: {color:number}{2}");
                    languageManager.setValue(list, "items", "_FINALTECH_ENERGIZED_OPERATION_ACCELERATOR", "status", "lore");

                    // _FINALTECH_OVERLOADED_OPERATION_ACCELERATOR
                    languageManager.setValue("{color:prandom}Overloaded Operation Accelerator", "items", "_FINALTECH_OVERLOADED_OPERATION_ACCELERATOR", "name");
                    languageManager.setValue("{color:passive}Introduction", "items", "_FINALTECH_OVERLOADED_OPERATION_ACCELERATOR", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Place it below the machine to work");
                    list.add("{color:normal}It will accelerate the work progress of the machine by consuming energy");
                    languageManager.setValue(list, "items", "_FINALTECH_OVERLOADED_OPERATION_ACCELERATOR", "info", "1", "lore");
                    languageManager.setValue("{color:passive}Mechanism", "items", "_FINALTECH_OVERLOADED_OPERATION_ACCELERATOR", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Efficiency : {color:number}nd{1} {color:conceal}(n = item amount)");
                    list.add("{color:normal}Put {id:_FINALTECH_OVERLOADED_OPERATION_ACCELERATOR} {color:normal}to increase efficiency");
                    languageManager.setValue(list, "items", "_FINALTECH_OVERLOADED_OPERATION_ACCELERATOR", "info", "2", "lore");
                    list = new ArrayList<>();
                    list.add("{color:normal}Stored Energy: {color:number}{1} J");
                    list.add("{color:normal}Times: {color:number}{2}");
                    languageManager.setValue(list, "items", "_FINALTECH_OVERLOADED_OPERATION_ACCELERATOR", "status", "lore");

                    // _FINALTECH_MATRIX_OPERATION_ACCELERATOR
                    languageManager.setValue("{color:prandom}Energized Operation Accelerator", "items", "_FINALTECH_MATRIX_OPERATION_ACCELERATOR", "name");
                    languageManager.setValue("{color:passive}Introduction", "items", "_FINALTECH_MATRIX_OPERATION_ACCELERATOR", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Place it below the machine to work");
                    languageManager.setValue(list, "items", "_FINALTECH_MATRIX_OPERATION_ACCELERATOR", "info", "1", "lore");

                    // _FINALTECH_MANUAL_CRAFT_MACHINE
                    languageManager.setValue("{color:prandom}Tips of all manual craft machine", "items", "_FINALTECH_MANUAL_CRAFT_MACHINE", "name");
                    list = new ArrayList<>();
                    list.add("{color:positive}Do craft without the need to place items in order");
                    list.add("");
                    list.add("{color:normal}Cost energy to craft");
                    list.add("{color:normal}Recovery energy over time");
                    list.add("");
                    list.add("{color:negative}While the stored energy is more than half of capacity");
                    list.add("{color:negative}Stored energy will be reduced by half");
                    languageManager.setValue(list, "items", "_FINALTECH_MANUAL_CRAFT_MACHINE", "lore");

                    // _FINALTECH_MATRIX_MACHINE_CHARGE_CARD
                    languageManager.setValue("{color:prandom}Matrix Machine Charge Card", "items", "_FINALTECH_MATRIX_MACHINE_CHARGE_CARD", "name");
                    languageManager.setValue("{color:positive}Usage", "items", "_FINALTECH_MATRIX_MACHINE_CHARGE_CARD", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Charge machine for {color:number}{1} J + {2}% of machine capacity");
                    list.add("{color:normal}Percentage energy charge is not applicable to to capacitor and generator");
                    languageManager.setValue(list, "items", "_FINALTECH_MATRIX_MACHINE_CHARGE_CARD", "info", "1", "lore");

                    // _FINALTECH_MATRIX_MACHINE_ACCELERATE_CARD
                    languageManager.setValue("{color:prandom}Matrix Machine Accelerate Card", "items", "_FINALTECH_MATRIX_MACHINE_ACCELERATE_CARD", "name");
                    languageManager.setValue("{color:positive}Usage", "items", "_FINALTECH_MATRIX_MACHINE_ACCELERATE_CARD", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Make machine work immediately for {color:number}{1} times");
                    languageManager.setValue(list, "items", "_FINALTECH_MATRIX_MACHINE_ACCELERATE_CARD", "info", "1", "lore");

                    // _FINALTECH_MATRIX_MACHINE_ACTIVATE_CARD
                    languageManager.setValue("{color:prandom}Machine Activate Card {color:conceal}- {color:number}L4", "items", "_FINALTECH_MATRIX_MACHINE_ACTIVATE_CARD", "name");
                    languageManager.setValue("{color:positive}Usage", "items", "_FINALTECH_MATRIX_MACHINE_ACTIVATE_CARD", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Make machine work immediately for {color:number}{1} times");
                    list.add("{color:normal}Before every work, it will be charged for {color:number}{2} J + {3}% of machine capacity");
                    list.add("{color:normal}Percentage energy charge is not applicable to to capacitor and generator");
                    languageManager.setValue(list, "items", "_FINALTECH_MATRIX_MACHINE_ACTIVATE_CARD", "info", "1", "lore");
                    languageManager.setValue("{color:negative}Limit", "items", "_FINALTECH_MATRIX_MACHINE_ACTIVATE_CARD", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Cost {color:number}1 life {color:normal}every use");
                    languageManager.setValue(list, "items", "_FINALTECH_MATRIX_MACHINE_ACTIVATE_CARD", "info", "2", "lore");

                    // _FINALTECH_MATRIX_QUANTITY_MODULE
                    languageManager.setValue("{color:prandom}Matrix Quantity Module", "items", "_FINALTECH_MATRIX_QUANTITY_MODULE", "name");
                    languageManager.setValue("{color:positive}Mechanism", "items", "_FINALTECH_MATRIX_QUANTITY_MODULE", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Can tweak some machines to an amazing efficiency");
                    list.add("{color:normal}Efficiency tweak is downward compatible");
                    languageManager.setValue(list, "items", "_FINALTECH_MATRIX_QUANTITY_MODULE", "info", "1", "lore");

                    // _FINALTECH_ADVANCED_AUTO_CRAFT
                    languageManager.setValue("{color:prandom}Advanced Auto Craft", "items", "_FINALTECH_ADVANCED_AUTO_CRAFT", "name");
                    languageManager.setValue("{color:positive}Mechanism", "items", "_FINALTECH_ADVANCED_AUTO_CRAFT", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Parse how slimefun item is crafted");
                    list.add("{color:normal}Then craft it from its raw materials directly");
                    languageManager.setValue(list, "items", "_FINALTECH_ADVANCED_AUTO_CRAFT", "info", "1", "lore");
                    languageManager.setValue("{color:positive}Mechanism", "items", "_FINALTECH_ADVANCED_AUTO_CRAFT", "info", "2", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Support using {id:_FINALTECH_COPY_CARD} {color:normal}to replace raw material");
                    languageManager.setValue(list, "items", "_FINALTECH_ADVANCED_AUTO_CRAFT", "info", "2", "lore");
                    languageManager.setValue("_FINALTECH_COPY_CARD", "items", "_FINALTECH_ADVANCED_AUTO_CRAFT", "info", "2", "output");

                    // _FINALTECH_MULTI_FRAME_MACHINE
                    languageManager.setValue("{color:positive}Mechanism", "items", "_FINALTECH_MULTI_FRAME_MACHINE", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Put machine item in the center slots to work");
                    languageManager.setValue(list, "items", "_FINALTECH_MULTI_FRAME_MACHINE", "info", "1", "lore");

                    // _FINALTECH_MATRIX_ITEM_DISMANTLE_TABLE
                    languageManager.setValue("{color:positive}Introduction", "items", "_FINALTECH_MATRIX_ITEM_DISMANTLE_TABLE", "info", "1", "name");
                    list = new ArrayList<>();
                    list.add("{color:normal}Restore item to its raw materials");
                    languageManager.setValue(list, "items", "_FINALTECH_MATRIX_ITEM_DISMANTLE_TABLE", "info", "1", "lore");

                    languageManager.setValue("replace item with card!", "research", "REPLACEABLE_CARD");
                    languageManager.setValue("ordered universe", "research", "ORDER_DUST");
                    languageManager.setValue("crash me", "research", "BUG");
                    languageManager.setValue("more and more", "research", "ENTROPY");
                    languageManager.setValue("infinity resource", "research", "ETHER");
                    languageManager.setValue("new start", "research", "ANNULAR");
                    languageManager.setValue("the reality of you", "research", "PHONY");
                    languageManager.setValue("logic and digital", "research", "LOGIC");
                    languageManager.setValue("machine card", "research", "MACHINE_CARD");
                    languageManager.setValue("light and dark", "research", "BOX");
                    languageManager.setValue("quantity module", "research", "QUANTITY_MODULE");
                    languageManager.setValue("copy every thing", "research", "COPY_CARD");
                    languageManager.setValue("electricity trading", "research", "ENERGY_CARD");

                    // ENERGY_REGULATOR
                    languageManager.setValue("&4Multiple Energy Regulators connected", "items", "ENERGY_REGULATOR", "hologram", "multi-regulator");
                    languageManager.setValue("&4No Energy Network found", "items", "ENERGY_REGULATOR", "hologram", "no-nodes");
                    list = new ArrayList<>();
                    list.add("{color:normal}Consumer:  {color:number}{1} {color:normal}| {color:number}{2} J {color:conceal}/ {color:number}{3} J");
                    list.add("{color:normal}Generator: {color:number}{4} {color:normal}| {color:number}{5} J {color:conceal}/ {color:number}{6} J");
                    list.add("{color:normal}Capacitor: {color:number}{7} {color:normal}| {color:number}{8} J {color:conceal}/ {color:number}{9} J");
                    list.add("");
                    list.add("{color:normal}Generated Energy: {color:number}{10} J");
                    list.add("{color:normal}Consumed Energy:  {color:number}{11} J");
                    languageManager.setValue(list, "items", "ENERGY_REGULATOR", "status", "lore");
                    list = new ArrayList<>();
                    list.add("{color:normal}x: {color:number}{1}");
                    list.add("{color:normal}y: {color:number}{2}");
                    list.add("{color:normal}z: {color:number}{3}");
                    languageManager.setValue(list, "items", "ENERGY_REGULATOR", "statistics", "lore-location");
                    languageManager.setValue("Generator", "items", "ENERGY_REGULATOR", "statistics", "GENERATOR");
                    languageManager.setValue("Capacitor", "items", "ENERGY_REGULATOR", "statistics", "CAPACITOR");
                    languageManager.setValue("Consumer", "items", "ENERGY_REGULATOR", "statistics", "CONSUMER");
                    languageManager.setValue("Connector", "items", "ENERGY_REGULATOR", "statistics", "CONNECTOR");
                    languageManager.setValue("None", "items", "ENERGY_REGULATOR", "statistics", "NONE");
                    list = new ArrayList<>();
                    list.add("{color:normal}Energy Type: {color:passive}{1}");
                    languageManager.setValue(list, "items", "ENERGY_REGULATOR", "statistics", "lore-type");
                    list = new ArrayList<>();
                    list.add("{color:normal}Energy: {color:number}{1} J {color:conceal}/ {color:normal}{2} J");
                    languageManager.setValue(list, "items", "ENERGY_REGULATOR", "statistics", "lore-energy");
                    list = new ArrayList<>();
                    list.add("{color:normal}Generated energy: {color:number}{1} J");
                    languageManager.setValue(list, "items", "ENERGY_REGULATOR", "statistics", "lore-generate");
                    list = new ArrayList<>();
                    list.add("{color:normal}Consumed energy: {color:number}{1} J");
                    languageManager.setValue(list, "items", "ENERGY_REGULATOR", "statistics", "lore-consume");
                }
            }
        });
    }

    public void update(@Nonnull FinalTechChanged finalTech) {
        if (!this.init) {
            this.init();
        }

        String currentVersion = "";

        try {
            currentVersion = FinalTechChanged.getConfigManager().getOrDefault(LATEST_VERSION, "version");
        } catch (Exception e) {
            e.printStackTrace();
            finalTech.getLogger().warning("It seems you have set the wrong value type of version");
            Integer version = FinalTechChanged.getConfigManager().getOrDefault(Integer.parseInt(LATEST_VERSION), "version");
            currentVersion = String.valueOf(version);
        }

        if (LATEST_VERSION.equals(currentVersion)) {
            FinalTechChanged.logger().info("You are using the latest version. Good luck!");
            return;
        }

        UpdateFunction updateFunction = versionMap.get(currentVersion);
        if (updateFunction == null) {
            FinalTechChanged.logger().info("You are using the unknown version. Version updater is disabled.");
        } else {
            String targetVersion = LATEST_VERSION;
            FinalTechChanged.logger().info("Version " + currentVersion + " is detected! Version updater start to work...");
            while (updateFunction != null && !LATEST_VERSION.equals(currentVersion)) {
                targetVersion = updateFunction.apply(finalTech);
                FinalTechChanged.logger().info("FinalTECH Updated: " + currentVersion + " -> " + targetVersion);
                currentVersion = targetVersion;
                updateFunction = versionMap.get(currentVersion);
            }
            FinalTechChanged.getConfigManager().setValue(targetVersion, "version");
            FinalTechChanged.logger().info("You are using the latest version now. Good luck!");
        }
    }

    public void destroy() {
        this.versionMap.clear();
    }

    @Override
    public void accept(@Nonnull FinalTechChanged finalTech) {
        this.update(finalTech);
    }

    protected static abstract class UpdateFunction implements Function<FinalTechChanged, String> {
        protected UpdateFunction() {

        }

        @Override
        public String apply(FinalTechChanged finalTech) {
            this.update(finalTech);
            return this.targetVersion();
        }

        protected abstract void update(FinalTechChanged finalTech);

        protected abstract String targetVersion();
    }
}
