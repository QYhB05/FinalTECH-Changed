package io.taraxacum.finaltech.core.menu.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.helper.Icon;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.item.machine.AdvancedAutoCraftFrame;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.plugin.dto.AdvancedMachineRecipe;
import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.libs.plugin.dto.LocationRecipeRegistry;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.StringItemUtil;
import io.taraxacum.libs.slimefun.dto.MachineRecipeFactory;
import io.taraxacum.libs.slimefun.dto.RecipeTypeRegistry;
import io.taraxacum.libs.slimefun.util.SfItemUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

public class AdvancedAutoCraftFrameMenu extends AbstractMachineMenu {
    public static final int PARSE_ITEM_SLOT = 46;
    public static final int STATUS_SLOT = 27;
    public static final int MODULE_SLOT = 28;

    private static final int[] BORDER = new int[]{29, 36, 38, 47};
    private static final int[] INPUT_BORDER = new int[]{18, 19, 20, 21, 22, 23, 24, 25, 26};
    private static final int[] OUTPUT_BORDER = new int[]{33, 34, 35, 42, 44, 51, 52, 53};
    private static final int[] INPUT_SLOT = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
    private static final int[] OUTPUT_SLOT = new int[0];

    private static final int PARSE_SLOT = 45;
    private static final ItemStack PARSE_ICON = new CustomItemStack(Material.OBSERVER, FinalTechChanged.getLanguageString("items", SfItemUtil.getIdFormatName(AdvancedAutoCraftFrame.class), "parse-icon", "name"), FinalTechChanged.getLanguageStringArray("items", SfItemUtil.getIdFormatName(AdvancedAutoCraftFrame.class), "parse-icon", "lore"));

    private static final int[] ITEM_INPUT_SLOT = new int[]{30, 31, 32, 39, 40, 41, 48, 49, 50};
    private static final int ITEM_OUTPUT_SLOT = 43;

    private static final int WIKI_SLOT = 37;
    private static final ItemStack WIKI_ICON = new CustomItemStack(Material.KNOWLEDGE_BOOK, FinalTechChanged.getLanguageString("items", SfItemUtil.getIdFormatName(AdvancedAutoCraftFrame.class), "wiki-icon", "name"), FinalTechChanged.getLanguageStringArray("items", SfItemUtil.getIdFormatName(AdvancedAutoCraftFrame.class), "wiki-icon", "lore"));

    private static final ItemStack PARSE_FAILED_ICON = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("items", SfItemUtil.getIdFormatName(AdvancedAutoCraftFrame.class), "parse-failed-icon", "name"), FinalTechChanged.getLanguageStringArray("items", SfItemUtil.getIdFormatName(AdvancedAutoCraftFrame.class), "parse-failed-icon", "lore"));

    private final ItemStack parseSuccessIcon = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("items", SfItemUtil.getIdFormatName(AdvancedAutoCraftFrame.class), "parse-success-icon", "name"), FinalTechChanged.getLanguageStringArray("items", SfItemUtil.getIdFormatName(AdvancedAutoCraftFrame.class), "parse-success-icon", "lore"));
    private final ItemStack parseExtendIcon = new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("items", SfItemUtil.getIdFormatName(AdvancedAutoCraftFrame.class), "parse-extend-icon", "name"), FinalTechChanged.getLanguageStringArray("items", SfItemUtil.getIdFormatName(AdvancedAutoCraftFrame.class), "parse-extend-icon", "lore"));

    // key: slimefun item id, value: the machine recipe list it handles
    private final Map<String, List<AdvancedMachineRecipe>> recipeMap = new HashMap<>();
    // RecipeType.getKey().getKey()
    private final Set<String> recipeTypeIdList;

    public AdvancedAutoCraftFrameMenu(@Nonnull AbstractMachine abstractMachine) {
        super(abstractMachine);
        this.recipeTypeIdList = new HashSet<>(ConfigUtil.getItemStringList(abstractMachine.getId(), "recipe-type-id"));
    }

    @Nonnull
    private static List<AdvancedMachineRecipe> getAdvancedMachineRecipeList(@Nonnull SlimefunItemStack slimefunItemStack) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(slimefunItemStack);
        if (slimefunItem != null) {
            return MachineRecipeFactory.getInstance().getAdvancedRecipe(slimefunItem.getId());
        }
        return new ArrayList<>();
    }

    @Override
    protected int[] getBorder() {
        return BORDER;
    }

    @Override
    protected int[] getInputBorder() {
        return INPUT_BORDER;
    }

    @Override
    protected int[] getOutputBorder() {
        return OUTPUT_BORDER;
    }

    @Override
    public int[] getInputSlot() {
        return INPUT_SLOT;
    }

    @Override
    public int[] getOutputSlot() {
        return OUTPUT_SLOT;
    }

    @Override
    public void init() {
        super.init();
        for (int slot : ITEM_INPUT_SLOT) {
            this.addItem(slot, PARSE_FAILED_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
        this.addItem(PARSE_SLOT, PARSE_ICON);
        this.addMenuClickHandler(PARSE_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addMenuClickHandler(ITEM_OUTPUT_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(WIKI_SLOT, WIKI_ICON);
        this.addMenuClickHandler(WIKI_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(STATUS_SLOT, Icon.QUANTITY_MODULE_ICON);
        this.addMenuClickHandler(STATUS_SLOT, ChestMenuUtils.getEmptyClickHandler());

        for (int slot : ITEM_INPUT_SLOT) {
            this.addItem(slot, Icon.INPUT_BORDER_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
        this.addItem(ITEM_OUTPUT_SLOT, Icon.BORDER_ICON);
        this.addMenuClickHandler(ITEM_OUTPUT_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Inventory inventory = blockMenu.toInventory();
        Location location = block.getLocation();

        blockMenu.addMenuClickHandler(PARSE_SLOT, ((player, i, itemStack, clickAction) -> {
            this.updateInventory(inventory, location);
            return false;
        }));
        blockMenu.addMenuCloseHandler(player -> this.updateInventory(inventory, location));
        blockMenu.addMenuOpeningHandler(player -> this.updateInventory(inventory, location));
    }

    @Override
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        ItemStack parseItem = inventory.getItem(PARSE_ITEM_SLOT);
        if (ItemStackUtil.isItemNull(parseItem)) {
            parseItem = inventory.getItem(ITEM_OUTPUT_SLOT);
            if (ItemStackUtil.isItemNull(parseItem) || ItemStackUtil.isItemSimilar(parseItem, PARSE_FAILED_ICON)) {
                this.setParseFailedMenu(inventory, location);
                return;
            }
        }

        SlimefunItem slimefunItem = SlimefunItem.getByItem(parseItem);
        if (slimefunItem == null) {
            this.setParseFailedMenu(inventory, location);
            return;
        }

        List<ItemAmountWrapper> inputList = null;
        if (this.recipeTypeIdList.contains(slimefunItem.getRecipeType().getKey().getKey())) {
            inputList = ItemStackUtil.calItemListWithAmount(slimefunItem.getRecipe());
        }
        if (inputList == null || inputList.size() == 0) {
            this.setParseFailedMenu(inventory, location);
            return;
        }

        for (int slot : INPUT_SLOT) {
            ItemStack machineItem = inventory.getItem(slot);
            SlimefunItem sfMachineItem = SlimefunItem.getByItem(machineItem);
            if (ItemStackUtil.isItemNull(machineItem) || sfMachineItem == null) {
                continue;
            }

            List<AdvancedMachineRecipe> advancedMachineRecipeList = this.recipeMap.get(sfMachineItem.getId());
            if (advancedMachineRecipeList != null) {
                for (int i = 0; i < machineItem.getAmount(); i++) {
                    boolean work = false;
                    List<ItemAmountWrapper> inputListTemp = new ArrayList<>();
                    for (ItemAmountWrapper oldInputItem : inputList) {
                        if (oldInputItem.getAmount() < Integer.MAX_VALUE / ConstantTableUtil.ITEM_MAX_STACK) {
                            for (AdvancedMachineRecipe advancedMachineRecipe : advancedMachineRecipeList) {
                                for (AdvancedMachineRecipe.AdvancedRandomOutput advancedRandomOutput : advancedMachineRecipe.getOutputs()) {
                                    ItemAmountWrapper outputItem = advancedRandomOutput.getOutputItem()[0];
                                    if (advancedRandomOutput.getOutputItem().length == 1 && oldInputItem.getAmount() >= outputItem.getAmount() && ItemStackUtil.isItemSimilar(oldInputItem, outputItem)) {
                                        int count = oldInputItem.getAmount() / outputItem.getAmount();
                                        for (ItemAmountWrapper inputItem : advancedMachineRecipe.getInput()) {
                                            ItemAmountWrapper.addToList(inputListTemp, inputItem, count * advancedMachineRecipe.getWeightSum() / advancedRandomOutput.weight());
                                        }
                                        oldInputItem.setAmount(oldInputItem.getAmount() - count * outputItem.getAmount());
                                        work = true;
                                    }
                                }
                            }
                        }
                        if (oldInputItem.getAmount() > 0) {
                            ItemAmountWrapper.addToList(inputListTemp, oldInputItem);
                            oldInputItem.setAmount(0);
                        }
                    }
                    inputList = inputListTemp;
                    if (!work) {
                        break;
                    }
                }
            } else if (FinalTechItems.COPY_CARD.verifyItem(machineItem)) {
                ItemStack stringItem = StringItemUtil.parseItemInCard(machineItem);
                if (!ItemStackUtil.isItemNull(stringItem)) {
                    String amount = StringItemUtil.parseAmountInCard(machineItem);
                    amount = StringNumberUtil.mul(amount, String.valueOf(machineItem.getAmount()));
                    if (!StringNumberUtil.ZERO.equals(amount)) {
                        Iterator<ItemAmountWrapper> iterator = inputList.iterator();
                        while (iterator.hasNext()) {
                            ItemAmountWrapper inputItem = iterator.next();
                            if (ItemStackUtil.isItemSimilar(inputItem, stringItem)) {
                                if (StringNumberUtil.compare(amount, String.valueOf(inputItem.getAmount())) >= 0) {
                                    iterator.remove();
                                } else {
                                    inputItem.setAmount(inputItem.getAmount() - Integer.parseInt(amount));
                                }
                                break;
                            }
                        }
                    }
                }

            }
        }

        AdvancedMachineRecipe.AdvancedRandomOutput advancedRandomOutput = new AdvancedMachineRecipe.AdvancedRandomOutput(new ItemAmountWrapper[]{(new ItemAmountWrapper(slimefunItem.getRecipeOutput()))}, 1);
        AdvancedMachineRecipe advancedMachineRecipe = new AdvancedMachineRecipe(inputList.toArray(new ItemAmountWrapper[0]), new AdvancedMachineRecipe.AdvancedRandomOutput[]{(advancedRandomOutput)});
        this.setParseSuccessMenu(inventory, location, advancedMachineRecipe);
    }

    private void setParseFailedMenu(@Nonnull Inventory inventory, @Nonnull Location location) {
        BlockMenu blockMenu = BlockStorage.getInventory(location);
        LocationRecipeRegistry.getInstance().setRecipe(location, null);
        for (int slot : ITEM_INPUT_SLOT) {
            inventory.setItem(slot, PARSE_FAILED_ICON);
            blockMenu.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
        inventory.setItem(ITEM_OUTPUT_SLOT, Icon.BORDER_ICON);
        blockMenu.addMenuClickHandler(ITEM_OUTPUT_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    private void setParseSuccessMenu(@Nonnull Inventory inventory, @Nonnull Location location, @Nonnull AdvancedMachineRecipe advancedMachineRecipe) {
        BlockMenu blockMenu = BlockStorage.getInventory(location);
        LocationRecipeRegistry.getInstance().setRecipe(location, advancedMachineRecipe);
        int i;
        for (i = 0; i < ITEM_INPUT_SLOT.length - 1; i++) {
            if (i < advancedMachineRecipe.getInput().length) {
                int amount = advancedMachineRecipe.getInput()[i].getAmount();
                ItemStack icon = ItemStackUtil.cloneItem(advancedMachineRecipe.getInput()[i].getItemStack());
                SfItemUtil.removeSlimefunId(icon);
                icon.setAmount(Math.min(amount, 64));
                ItemStackUtil.addLoreToLast(icon, FinalTechChanged.getLanguageManager().replaceString(FinalTechChanged.getLanguageString("items", this.getSlimefunItem().getId(), "parse-amount"), String.valueOf(amount)));
                inventory.setItem(ITEM_INPUT_SLOT[i], icon);
            } else {
                inventory.setItem(ITEM_INPUT_SLOT[i], this.parseSuccessIcon);
            }
            blockMenu.addMenuClickHandler(ITEM_INPUT_SLOT[i], ChestMenuUtils.getEmptyClickHandler());
        }
        if (advancedMachineRecipe.getInput().length < ITEM_INPUT_SLOT.length) {
            blockMenu.replaceExistingItem(ITEM_INPUT_SLOT[i], this.parseSuccessIcon);
        } else if (advancedMachineRecipe.getInput().length == ITEM_INPUT_SLOT.length) {
            int amount = advancedMachineRecipe.getInput()[i].getAmount();
            ItemStack icon = ItemStackUtil.cloneItem(advancedMachineRecipe.getInput()[i].getItemStack());
            SfItemUtil.removeSlimefunId(icon);
            icon.setAmount(Math.min(amount, 64));
            ItemStackUtil.addLoreToLast(icon, FinalTechChanged.getLanguageManager().replaceString(FinalTechChanged.getLanguageString("items", this.getSlimefunItem().getId(), "parse-amount"), String.valueOf(amount)));
            blockMenu.replaceExistingItem(ITEM_INPUT_SLOT[i], icon);
            blockMenu.addMenuClickHandler(ITEM_INPUT_SLOT[i], ChestMenuUtils.getEmptyClickHandler());
        } else {
            blockMenu.replaceExistingItem(ITEM_INPUT_SLOT[i], this.parseExtendIcon);
            blockMenu.addMenuClickHandler(ITEM_INPUT_SLOT[i], (player, i1, itemStack, clickAction) -> {
                ChestMenu chestMenu = new ChestMenu(ItemStackUtil.getItemName(advancedMachineRecipe.getOutput()[0].getItemStack()));
                for (int slot = 0; slot < 54 && slot < advancedMachineRecipe.getInput().length; slot++) {
                    if (advancedMachineRecipe.getInput().length > slot) {
                        int amount = advancedMachineRecipe.getInput()[slot].getAmount();
                        ItemStack icon = ItemStackUtil.cloneItem(advancedMachineRecipe.getInput()[slot].getItemStack());
                        SfItemUtil.removeSlimefunId(icon);
                        icon.setAmount(Math.min(amount, 64));
                        ItemStackUtil.addLoreToLast(icon, FinalTechChanged.getLanguageManager().replaceString(FinalTechChanged.getLanguageString("items", this.getSlimefunItem().getId(), "parse-amount"), String.valueOf(amount)));
                        chestMenu.addItem(slot, icon);
                    } else {
                        chestMenu.addItem(slot, Icon.BORDER_ICON);
                    }
                    chestMenu.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
                }
                chestMenu.open(player);
                return false;
            });
        }
        ItemStack icon = ItemStackUtil.cloneItem(advancedMachineRecipe.getOutput()[0].getItemStack());
        ItemStackUtil.addLoreToLast(icon, FinalTechChanged.getLanguageManager().replaceString(FinalTechChanged.getLanguageString("items", this.getSlimefunItem().getId(), "parse-amount"), String.valueOf(advancedMachineRecipe.getOutput()[0].getAmount())));
        blockMenu.replaceExistingItem(ITEM_OUTPUT_SLOT, icon);
    }

    public void registerRecipe() {
        for (String string : this.recipeTypeIdList) {
            RecipeType recipeType = RecipeTypeRegistry.getInstance().getRecipeTypeById(string);
            SlimefunItem slimefunItem = null;
            if (recipeType != null) {
                slimefunItem = recipeType.getMachine();
                if (slimefunItem == null) {
                    slimefunItem = SlimefunItem.getByItem(recipeType.toItem());
                    if (slimefunItem == null) {
                        slimefunItem = SlimefunItem.getById(recipeType.getKey().getKey());
                    }
                }
            }
            if (slimefunItem != null) {
                List<SlimefunItem> slimefunItemList = RecipeTypeRegistry.getInstance().getByRecipeType(recipeType);
                List<AdvancedMachineRecipe> advancedMachineRecipeList = new ArrayList<>(slimefunItemList.size());
                for (SlimefunItem sfItem : slimefunItemList) {
                    advancedMachineRecipeList.add(new AdvancedMachineRecipe(ItemStackUtil.calItemArrayWithAmount(sfItem.getRecipe()), new AdvancedMachineRecipe.AdvancedRandomOutput[]{new AdvancedMachineRecipe.AdvancedRandomOutput(new ItemAmountWrapper[]{new ItemAmountWrapper(sfItem.getRecipeOutput())}, 1)}));
                }
                this.recipeMap.put(slimefunItem.getId(), advancedMachineRecipeList);
            }
        }

        this.recipeMap.put(FinalTechItemStacks.MANUAL_ENHANCED_CRAFTING_TABLE.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.MANUAL_ENHANCED_CRAFTING_TABLE));
        this.recipeMap.put(FinalTechItemStacks.MANUAL_GRIND_STONE.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.MANUAL_GRIND_STONE));
        this.recipeMap.put(FinalTechItemStacks.MANUAL_ARMOR_FORGE.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.MANUAL_ARMOR_FORGE));
        this.recipeMap.put(FinalTechItemStacks.MANUAL_ORE_CRUSHER.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.MANUAL_ORE_CRUSHER));
        this.recipeMap.put(FinalTechItemStacks.MANUAL_COMPRESSOR.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.MANUAL_COMPRESSOR));
        this.recipeMap.put(FinalTechItemStacks.MANUAL_SMELTERY.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.MANUAL_SMELTERY));
        this.recipeMap.put(FinalTechItemStacks.MANUAL_PRESSURE_CHAMBER.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.MANUAL_PRESSURE_CHAMBER));
        this.recipeMap.put(FinalTechItemStacks.MANUAL_MAGIC_WORKBENCH.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.MANUAL_MAGIC_WORKBENCH));
        this.recipeMap.put(FinalTechItemStacks.MANUAL_ORE_WASHER.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.ADVANCED_DUST_WASHER));
        this.recipeMap.put(FinalTechItemStacks.MANUAL_COMPOSTER.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.MANUAL_COMPOSTER));
        this.recipeMap.put(FinalTechItemStacks.MANUAL_GOLD_PAN.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.MANUAL_GOLD_PAN));
        this.recipeMap.put(FinalTechItemStacks.MANUAL_CRUCIBLE.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.MANUAL_CRUCIBLE));
        this.recipeMap.put(FinalTechItemStacks.MANUAL_JUICER.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.MANUAL_JUICER));
        this.recipeMap.put(FinalTechItemStacks.MANUAL_ANCIENT_ALTAR.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.MANUAL_ANCIENT_ALTAR));
        this.recipeMap.put(FinalTechItemStacks.MANUAL_HEATED_PRESSURE_CHAMBER.getItemId(), AdvancedAutoCraftFrameMenu.getAdvancedMachineRecipeList(FinalTechItemStacks.MANUAL_HEATED_PRESSURE_CHAMBER));

        this.recipeTypeIdList.add(RecipeType.ENHANCED_CRAFTING_TABLE.getKey().getKey());
        this.recipeTypeIdList.add(RecipeType.GRIND_STONE.getKey().getKey());
        this.recipeTypeIdList.add(RecipeType.ARMOR_FORGE.getKey().getKey());
        this.recipeTypeIdList.add(RecipeType.ORE_CRUSHER.getKey().getKey());
        this.recipeTypeIdList.add(RecipeType.COMPRESSOR.getKey().getKey());
        this.recipeTypeIdList.add(RecipeType.SMELTERY.getKey().getKey());
        this.recipeTypeIdList.add(RecipeType.PRESSURE_CHAMBER.getKey().getKey());
        this.recipeTypeIdList.add(RecipeType.MAGIC_WORKBENCH.getKey().getKey());
        this.recipeTypeIdList.add(RecipeType.ORE_WASHER.getKey().getKey());
        this.recipeTypeIdList.add(RecipeType.GOLD_PAN.getKey().getKey());
        this.recipeTypeIdList.add(RecipeType.JUICER.getKey().getKey());
        this.recipeTypeIdList.add(RecipeType.ANCIENT_ALTAR.getKey().getKey());
        this.recipeTypeIdList.add(RecipeType.HEATED_PRESSURE_CHAMBER.getKey().getKey());
    }

    public Map<String, List<AdvancedMachineRecipe>> getRecipeMap() {
        return recipeMap;
    }
}
