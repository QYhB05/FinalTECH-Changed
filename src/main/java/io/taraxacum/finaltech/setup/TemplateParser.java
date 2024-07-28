package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.exception.TemplateParserErrorException;
import io.taraxacum.finaltech.core.item.machine.template.advanced.AbstractAdvanceMachine;
import io.taraxacum.finaltech.core.item.machine.template.basic.AbstractBasicMachine;
import io.taraxacum.libs.plugin.dto.ConfigFileManager;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.RandomMachineRecipe;
import io.taraxacum.libs.slimefun.dto.RecipeTypeRegistry;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Final_ROOT
 * @see 2.3
 */
public class TemplateParser {
    private final ConfigFileManager configFile;
    /**
     * if ture, we will do some game balance check.
     * includes:
     * all items should be crafted by at least one _FINALTECH_ANNULAR
     * only Slimefun item could be recipe output
     * recipe input must contain Slimefun item
     */
    private final boolean safeBalanceDesign;
    /**
     * if true, we will do some potential error check.
     * includes:
     * if recipe type is from Slimefun, the item amount must be 1
     * if recipe type is from Slimefun,
     */
    private final boolean noErrorDesign;

    private final Set<String> slimefunRecipeTypeIdSet = new HashSet<>();
    private final Set<String> notAllowedRecipeTypeIdSet = new HashSet<>();

    public TemplateParser(@Nonnull ConfigFileManager configFile, boolean safeBalanceDesign, boolean noErrorDesign) {
        this.configFile = configFile;
        this.safeBalanceDesign = safeBalanceDesign;
        this.noErrorDesign = noErrorDesign;

        slimefunRecipeTypeIdSet.add("enhanced_crafting_table");
        slimefunRecipeTypeIdSet.add("armor_forge");
        slimefunRecipeTypeIdSet.add("smeltery");
        slimefunRecipeTypeIdSet.add("magic_workbench");
        slimefunRecipeTypeIdSet.add("ancient_altar");

        notAllowedRecipeTypeIdSet.add("grind_stone");
        notAllowedRecipeTypeIdSet.add("ore_crusher");
        notAllowedRecipeTypeIdSet.add("compressor");
        notAllowedRecipeTypeIdSet.add("pressure_chamber");
        notAllowedRecipeTypeIdSet.add("gold_pan");
        notAllowedRecipeTypeIdSet.add("juicer");
        notAllowedRecipeTypeIdSet.add("heated_pressure_chamber");
    }

    public void registerMachine() {
        List<String> idList = this.configFile.getStringList();
        FinalTechChanged.logger().info("Find " + idList.size() + " machines to be registered");
        List<AbstractBasicMachine> basicMachineList = new ArrayList<>();
        List<AbstractAdvanceMachine> advanceMachineList = new ArrayList<>();
        for (String id : idList) {
            FinalTechChanged.logger().info("Registering " + id);
            try {
                // get material
                if (!this.configFile.containPath(id, "material")) {
                    throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".material ?");
                }
                String materialStr = this.configFile.getString(id, "material");
                Material material = Material.getMaterial(materialStr);
                if (material == null) {
                    throw new TemplateParserErrorException("Template file parse field. Can not find material: " + materialStr);
                }

                ItemStack itemStack = new ItemStack(material);

                // get item name
                if (!this.configFile.containPath(id, "name")) {
                    throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".name ?");
                }
                String name = this.configFile.getString(id, "name");
                name = FinalTechChanged.getLanguageManager().getResult(name);
                ItemStackUtil.setItemName(itemStack, name);

                if (this.configFile.containPath(id, "lore")) {
                    ItemStackUtil.setLore(itemStack, FinalTechChanged.getLanguageManager().getResult(this.configFile.getStringList(id, "lore")));
                }

                // get craft recipe type
                if (!this.configFile.containPath(id, "craft-recipe-type")) {
                    throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".craft-recipe-type ?");
                }
                String recipeTypeStr = this.configFile.getString(id, "craft-recipe-type");
                RecipeType recipeType = null;
                for (RecipeType recipeTypeTemp : RecipeTypeRegistry.getInstance().getRecipeTypeSet()) {
                    if (recipeTypeTemp.getKey().getKey().equals(recipeTypeStr)) {
                        recipeType = recipeTypeTemp;
                        break;
                    }
                }
                if (recipeType == null) {
                    throw new TemplateParserErrorException("Template file parse field. Can not find recipe type: " + recipeTypeStr);
                }

                // TODO check no error

                // get craft recipe
                if (!this.configFile.containPath(id, "craft-recipe")) {
                    throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".craft-recipe ?");
                }
                List<String> craftRecipe = this.configFile.getStringList(id, "craft-recipe");
                ItemStack[] craftRecipeItems = new ItemStack[craftRecipe.size()];
                for (int i = 0; i < craftRecipe.size(); i++) {
                    String index = craftRecipe.get(i);

                    ItemStack recipeItem;

                    if (this.configFile.containPath(id, "craft-recipe", index, "id")) {
                        String slimefunItemId = this.configFile.getString(id, "craft-recipe", index, "id");
                        SlimefunItem slimefunItem = SlimefunItem.getById(slimefunItemId);
                        if (slimefunItem == null) {
                            throw new TemplateParserErrorException("Template file parse field. Can not find Slimefun item with id '" + slimefunItemId + "'");
                        }
                        recipeItem = ItemStackUtil.cloneItem(slimefunItem.getItem());
                    } else if (this.configFile.containPath(id, "craft-recipe", index, "material")) {
                        String materialName = this.configFile.getString(id, "craft-recipe", index, "material");
                        Material m = Material.getMaterial(materialName);
                        if (m == null) {
                            throw new TemplateParserErrorException("Template file parse field. Can not find material with name '" + materialName + "'");
                        }
                        recipeItem = m.isAir() ? null : new ItemStack(m);
                    } else {
                        throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".craft-recipe ?");
                    }

                    if (!this.configFile.containPath(id, "craft-recipe", index, "amount")) {
                        throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".craft-recipe." + index + ".amount ?");
                    }
                    int amount = Integer.parseInt(this.configFile.getString(id, "craft-recipe", index, "amount"));
                    recipeItem.setAmount(amount);

                    craftRecipeItems[i] = recipeItem;
                }

                // get machine recipe
                if (!this.configFile.containPath(id, "machine-recipe")) {
                    throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".machine-recipe ?");
                }
                List<String> machineRecipeNameList = this.configFile.getStringList(id, "machine-recipe");
                List<MachineRecipe> machineRecipeList = new ArrayList<>(machineRecipeNameList.size());
                for (String recipeName : machineRecipeNameList) {
                    MachineRecipe machineRecipe = null;
                    if (!this.configFile.containPath(id, "machine-recipe", recipeName, "input")) {
                        throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".machine-recipe." + recipeName + ".input ?");
                    }

                    // get input items
                    List<String> inputList = this.configFile.getStringList(id, "machine-recipe", recipeName, "input");
                    ItemStack[] inputItems = new ItemStack[inputList.size()];
                    for (int i = 0; i < inputList.size(); i++) {
                        String index = craftRecipe.get(i);

                        ItemStack recipeItem = null;

                        if (this.configFile.containPath(id, "machine-recipe", recipeName, "input", index, "id")) {
                            String slimefunItemId = this.configFile.getString(id, "machine-recipe", recipeName, "input", index, "id");
                            SlimefunItem slimefunItem = SlimefunItem.getById(slimefunItemId);
                            if (slimefunItem == null) {
                                throw new TemplateParserErrorException("Template file parse field. Can not find Slimefun item with id '" + slimefunItemId + "'");
                            }
                            recipeItem = ItemStackUtil.cloneItem(slimefunItem.getItem());
                        } else if (this.configFile.containPath(id, "machine-recipe", recipeName, "input", index, "material")) {
                            String materialName = this.configFile.getString(id, "machine-recipe", recipeName, "input", index, "material");
                            Material m = Material.getMaterial(materialName);
                            if (m == null) {
                                throw new TemplateParserErrorException("Template file parse field. Can not find material with name '" + materialName + "'");
                            }
                            recipeItem = new ItemStack(m);
                        } else {
                            throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".machine-recipe." + recipeName + ".input." + index + " ?");
                        }

                        if (!this.configFile.containPath(id, "machine-recipe", recipeName, "input", index, "amount")) {
                            throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".machine-recipe." + recipeName + ".input." + index + ".amount ?");
                        }
                        int amount = Integer.parseInt(this.configFile.getString(id, "machine-recipe", recipeName, "input", index, "amount"));
                        recipeItem.setAmount(amount);

                        inputItems[i] = recipeItem;
                    }

                    // get output items
                    if (this.configFile.containPath(id, "machine-recipe", recipeName, "outputs")) {
                        // get random output items
                        List<String> outputGroupNameList = this.configFile.getStringList(id, "machine-recipe", recipeName, "outputs");
                        RandomMachineRecipe.RandomOutput[] randomOutputs = new RandomMachineRecipe.RandomOutput[outputGroupNameList.size()];
                        for (int j = 0; j < outputGroupNameList.size(); j++) {
                            String outputGroupName = outputGroupNameList.get(j);

                            // get weight
                            if (!this.configFile.containPath(id, "machine-recipe", recipeName, "outputs", outputGroupName, "weight")) {
                                throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".machine-recipe." + recipeName + ".outputs." + outputGroupName + ".weight ?");
                            }
                            int weight = Integer.parseInt(this.configFile.getString(id, "machine-recipe", recipeName, "outputs", outputGroupName, "weight"));
                            if (weight <= 0) {
                                throw new TemplateParserErrorException("Template file parse field. The value for " + id + ".machine-recipe." + recipeName + ".outputs." + outputGroupName + ".weight must be positive number !");
                            }

                            // get output items
                            if (!this.configFile.containPath(id, "machine-recipe", recipeName, "outputs", outputGroupName, "output")) {
                                throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".machine-recipe." + recipeName + ".outputs." + outputGroupName + ".output ?");
                            }
                            List<String> outputList = this.configFile.getStringList(id, "machine-recipe", recipeName, "outputs", outputGroupName, "output");
                            ItemStack[] outputItems = new ItemStack[inputList.size()];
                            for (int k = 0; k < inputList.size(); k++) {
                                String index = craftRecipe.get(k);

                                ItemStack recipeItem = null;

                                if (this.configFile.containPath(id, "machine-recipe", recipeName, "outputs", outputGroupName, "output", index, "id")) {
                                    String slimefunItemId = this.configFile.getString(id, "machine-recipe", recipeName, "outputs", outputGroupName, "output", index, "id");
                                    SlimefunItem slimefunItem = SlimefunItem.getById(slimefunItemId);
                                    if (slimefunItem == null) {
                                        throw new TemplateParserErrorException("Template file parse field. Can not find Slimefun item with id '" + slimefunItemId + "'");
                                    }
                                    recipeItem = ItemStackUtil.cloneItem(slimefunItem.getItem());
                                } else if (this.configFile.containPath(id, "machine-recipe", recipeName, "outputs", outputGroupName, "output", index, "material")) {
                                    String materialName = this.configFile.getString(id, "machine-recipe", recipeName, "outputs", outputGroupName, "output", index, "material");
                                    Material m = Material.getMaterial(materialName);
                                    if (m == null) {
                                        throw new TemplateParserErrorException("Template file parse field. Can not find material with name '" + materialName + "'");
                                    }
                                    recipeItem = new ItemStack(m);
                                } else {
                                    throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".machine-recipe." + recipeName + ".outputs." + outputGroupName + ".outputs." + index + " ?");
                                }

                                if (!this.configFile.containPath(id, "machine-recipe", recipeName, "outputs", outputGroupName, "output", index, "amount")) {
                                    throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".machine-recipe." + recipeName + ".outputs." + outputGroupName + ".outputs." + index + ".amount ?");
                                }
                                int amount = Integer.parseInt(this.configFile.getString(id, "machine-recipe", recipeName, "outputs", outputGroupName, "output", index, "amount"));
                                recipeItem.setAmount(amount);

                                outputItems[k] = recipeItem;
                            }
                            randomOutputs[j] = new RandomMachineRecipe.RandomOutput(outputItems, weight);
                        }
                        machineRecipe = new RandomMachineRecipe(inputItems, randomOutputs);
                    } else if (!this.configFile.containPath(id, "machine-recipe", recipeName, "output")) {
                        List<String> outputList = this.configFile.getStringList(id, "machine-recipe", recipeName, "output");
                        ItemStack[] outputItems = new ItemStack[inputList.size()];
                        for (int k = 0; k < inputList.size(); k++) {
                            String index = craftRecipe.get(k);

                            ItemStack recipeItem = null;

                            if (this.configFile.containPath(id, "machine-recipe", recipeName, "outputs", index, "id")) {
                                String slimefunItemId = this.configFile.getString(id, "machine-recipe", recipeName, "outputs", index, "id");
                                SlimefunItem slimefunItem = SlimefunItem.getById(slimefunItemId);
                                if (slimefunItem == null) {
                                    throw new TemplateParserErrorException("Template file parse field. Can not find Slimefun item with id '" + slimefunItemId + "'");
                                }
                                recipeItem = ItemStackUtil.cloneItem(slimefunItem.getItem());
                            } else if (this.configFile.containPath(id, "machine-recipe", recipeName, "output", index, "material")) {
                                String materialName = this.configFile.getString(id, "machine-recipe", recipeName, "output", index, "material");
                                Material m = Material.getMaterial(materialName);
                                if (m == null) {
                                    throw new TemplateParserErrorException("Template file parse field. Can not find material with name '" + materialName + "'");
                                }
                                recipeItem = new ItemStack(m);
                            } else {
                                throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".machine-recipe." + recipeName + ".output." + index + " ?");
                            }

                            if (!this.configFile.containPath(id, "machine-recipe", recipeName, "output", index, "amount")) {
                                throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".machine-recipe." + recipeName + ".output." + index + ".amount ?");
                            }
                            int amount = Integer.parseInt(this.configFile.getString(id, "machine-recipe", recipeName, "output", index, "amount"));
                            recipeItem.setAmount(amount);

                            outputItems[k] = recipeItem;
                        }
                        machineRecipe = new MachineRecipe(0, inputItems, outputItems);
                    } else {
                        throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".machine-recipe." + recipeName + ".outputs ?");
                    }

                    machineRecipeList.add(machineRecipe);
                }

                SlimefunItemStack slimefunItemStack = new SlimefunItemStack(id, itemStack, name);

                if (!this.configFile.containPath(id, "type")) {
                    throw new TemplateParserErrorException("Template file parse field. Dou you set the value for " + id + ".type ?");
                }
                String type = this.configFile.getString(id, "type");

                switch (type) {
                    case "BASIC" ->
                            basicMachineList.add(this.registerBasicMachine(slimefunItemStack, recipeType, craftRecipeItems, machineRecipeList));
                    case "ADVANCED" ->
                            advanceMachineList.add(this.registerAdvancedMachine(slimefunItemStack, recipeType, craftRecipeItems, machineRecipeList));
                    default ->
                            throw new TemplateParserErrorException("Template file parse field. Can not register machine with type '" + type + "' !!!");
                }
                FinalTechChanged.logger().info("Slimefun item " + id + " is registered from template");

                if (this.configFile.containPath(id, "research")) {

                }
                new Research(new NamespacedKey(FinalTechChanged.getInstance(), "key"), 0, "name", 0);
            } catch (Exception e) {
                FinalTechChanged.logger().severe("An error occurred while registering Slimefun item [" + id + "] from template file");
                if (e instanceof TemplateParserErrorException templateParserErrorException) {
                    FinalTechChanged.logger().severe(templateParserErrorException.getMessage());
                }
                e.printStackTrace();
            }
        }

        FinalTechMenus.SUB_MENU_BASIC_MACHINE.addTo(basicMachineList.toArray(new AbstractBasicMachine[0]));
        for (AbstractBasicMachine abstractBasicMachine : basicMachineList) {
            FinalTechMenus.MENU_PRODUCTIVE_MACHINE.add(abstractBasicMachine);
        }

        FinalTechMenus.SUB_MENU_ADVANCED_MACHINE.addTo(advanceMachineList.toArray(new AbstractAdvanceMachine[0]));
        for (AbstractAdvanceMachine abstractBasicMachine : advanceMachineList) {
            FinalTechMenus.MENU_PRODUCTIVE_MACHINE.add(abstractBasicMachine);
        }
    }

    private AbstractBasicMachine registerBasicMachine(SlimefunItemStack slimefunItemStack, RecipeType recipeType, ItemStack[] craftRecipeItems, List<MachineRecipe> machineRecipeList) {
        AbstractBasicMachine abstractBasicMachine = new AbstractBasicMachine(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, slimefunItemStack, recipeType, craftRecipeItems) {
            @Override
            public void registerDefaultRecipes() {
                for (MachineRecipe machineRecipe : machineRecipeList) {
                    this.registerRecipe(machineRecipe);
                }
            }
        };
        abstractBasicMachine.registerThis();

        return abstractBasicMachine;
    }

    private AbstractAdvanceMachine registerAdvancedMachine(SlimefunItemStack slimefunItemStack, RecipeType recipeType, ItemStack[] craftRecipeItems, List<MachineRecipe> machineRecipeList) {
        AbstractAdvanceMachine abstractAdvanceMachine = new AbstractAdvanceMachine(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, slimefunItemStack, recipeType, craftRecipeItems) {
            @Override
            public void registerDefaultRecipes() {
                for (MachineRecipe machineRecipe : machineRecipeList) {
                    this.registerRecipe(machineRecipe);
                }
            }
        };
        abstractAdvanceMachine.registerThis();

        return abstractAdvanceMachine;
    }
}
