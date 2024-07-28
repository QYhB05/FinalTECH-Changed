package io.taraxacum.finaltech.core.menu.manual;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.slimefun.dto.AdvancedCraft;
import io.taraxacum.libs.plugin.dto.AdvancedMachineRecipe;
import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.libs.slimefun.dto.MachineRecipeFactory;
import io.taraxacum.finaltech.core.item.machine.manual.craft.AbstractManualCraftMachine;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class ManualCraftMachineMenu extends AbstractManualMachineMenu {
    private static final int[] BORDER = new int[] {48, 50};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
    private static final int[] OUTPUT_SLOT = new int[] {27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};

    private static final int STATUS_SLOT = 40;
    private static final int[] STATUS_L_SLOT = new int[] {38, 37, 36};
    private static final int[] STATUS_R_SLOT = new int[] {42, 43, 44};
    private static final int PREVIOUS_SLOT = 39;
    private static final int NEXT_SLOT = 41;
    private static final int CRAFT_SLOT = 49;
    private static final int[] CRAFT_L_SLOT = new int[] {47, 46, 45};
    private static final int[] CRAFT_R_SLOT = new int[] {51, 52, 53};

    private static final ItemStack CRAFT_ICON = new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("items", "ManualCraftMachine", "craft-icon", "name"), FinalTechChanged.getLanguageStringArray("items", "ManualCraftMachine", "craft-icon", "lore"));
    private static final ItemStack STATUS_ICON = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("items", "ManualCraftMachine", "status-icon", "name"), FinalTechChanged.getLanguageStringArray("items", "ManualCraftMachine", "status-icon", "lore"));

    public static final String KEY = "offset";
    public static final String[] KEY_L = new String[] {"offset-l1", "offset-l2", "offset-l3"};
    public static final String[] KEY_R = new String[] {"offset-r1", "offset-r2", "offset-r3"};
    private static final String KEY_ORDER = "order";
    private static final String ORDER_VALUE_DESC = "desc";
    private static final String ORDER_VALUE_ASC = "asc";

    private final AbstractManualCraftMachine manualCraftMachine;

    public ManualCraftMachineMenu(@Nonnull AbstractManualCraftMachine abstractMachine) {
        super(abstractMachine);
        this.manualCraftMachine = abstractMachine;
    }

    @Override
    public void init() {
        super.init();

        this.addItem(STATUS_SLOT, STATUS_ICON);
        for (int slot : STATUS_L_SLOT) {
            this.addItem(slot, STATUS_ICON);
        }
        for (int slot : STATUS_R_SLOT) {
            this.addItem(slot, STATUS_ICON);
        }

        this.addItem(PREVIOUS_SLOT, new CustomItemStack(new SlimefunItemStack("_UI_PREVIOUS_INACTIVE", Material.BLACK_STAINED_GLASS_PANE, "&8\u21E6 Previous Page"), FinalTechChanged.getLanguageString("items", "ManualCraftMachine", "previous-icon", "name"), FinalTechChanged.getLanguageStringArray("items", "ManualCraftMachine", "previous-icon", "lore")));
        this.addItem(NEXT_SLOT, new CustomItemStack(new SlimefunItemStack("_UI_NEXT_INACTIVE", Material.BLACK_STAINED_GLASS_PANE, "&8Next Page \u21E8"), FinalTechChanged.getLanguageString("items", "ManualCraftMachine", "next-icon", "name"), FinalTechChanged.getLanguageStringArray("items", "ManualCraftMachine", "next-icon", "lore")));

        this.addItem(CRAFT_SLOT, CRAFT_ICON);
        for (int slot : CRAFT_L_SLOT) {
            this.addItem(slot, CRAFT_ICON);
        }
        for (int slot : CRAFT_R_SLOT) {
            this.addItem(slot, CRAFT_ICON);
        }
    }
    public static void add(Location l, String key, String value) {
        BlockStorage.addBlockInfo(l, key, value);
    }
    
    public static String get(Location l, String key) {
        return BlockStorage.getLocationInfo(l, key);
    }
    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Inventory inventory = blockMenu.toInventory();
        Location l = blockMenu.getLocation();
        Config config = BlockStorage.getLocationInfo(l);
        JavaPlugin javaPlugin = this.getSlimefunItem().getAddon().getJavaPlugin();

        blockMenu.addMenuOpeningHandler((player -> {
            if(!ManualCraftMachineMenu.this.verifyCount(l)) {
                return;
            }

            ManualCraftMachineMenu.this.updateInventory(inventory, l);
        }));

        blockMenu.addMenuClickHandler(STATUS_SLOT, (((player, i, itemStack, clickAction) -> {
            if(!ManualCraftMachineMenu.this.verifyCount(l)) {
                return false;
            }

            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));
            MachineUtil.stockSlots(inventory, INPUT_SLOT);
            return false;
        })));

        for (int slotP = 0; slotP < STATUS_L_SLOT.length; slotP++) {
            final int finalSlotP = slotP;
            blockMenu.addMenuClickHandler(STATUS_L_SLOT[slotP], (player, i, itemStack, clickAction) -> {
                if(!ManualCraftMachineMenu.this.verifyCount(l)) {
                    return false;
                }

                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));

                BlockStorage.addBlockInfo(l, KEY, BlockStorage.getLocationInfo(l, KEY_L[finalSlotP]));
                ManualCraftMachineMenu.this.updateInventory(inventory, l);
                return false;
            });
        }

        for (int slotP = 0; slotP < STATUS_R_SLOT.length; slotP++) {
            final int finalSlotP = slotP;
            blockMenu.addMenuClickHandler(STATUS_R_SLOT[slotP], (player, i, itemStack, clickAction) -> {
                if(!ManualCraftMachineMenu.this.verifyCount(l)) {
                    return false;
                }

                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));

                add(l, KEY, get(l, KEY_R[finalSlotP]));
                ManualCraftMachineMenu.this.updateInventory(inventory, l);
                return false;
            });
        }

        blockMenu.addMenuClickHandler(PREVIOUS_SLOT, ((player, i, itemStack, clickAction) -> {
            if(!ManualCraftMachineMenu.this.verifyCount(l)) {
                return false;
            }

            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));

            int offset = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), KEY));
            int length = MachineRecipeFactory.getInstance().getRecipe(this.getID()).size();
            offset = (offset + length - 1) % length;
            add(l, KEY, String.valueOf(offset));
            add(l, KEY_ORDER, ORDER_VALUE_DESC);
            ManualCraftMachineMenu.this.updateInventory(inventory, l);
            return false;
        }));

        blockMenu.addMenuClickHandler(NEXT_SLOT, ((player, i, itemStack, clickAction) -> {
            if(!ManualCraftMachineMenu.this.verifyCount(l)) {
                return false;
            }

            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));

            int offset = Integer.parseInt(BlockStorage.getLocationInfo(block.getLocation(), KEY));
            int length = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getID()).size();
            offset = (offset + 1) % length;
            add(l, KEY, String.valueOf(offset));
            add(l, KEY_ORDER, ORDER_VALUE_ASC);
            ManualCraftMachineMenu.this.updateInventory(inventory, l);
            return false;
        }));
        blockMenu.addMenuClickHandler(CRAFT_SLOT, ((player, i, itemStack, clickAction) -> {
            if(!ManualCraftMachineMenu.this.verifyCount(l)) {
                return false;
            }

            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));

            int offset = get(l, KEY) != null ? Integer.parseInt(get(l, KEY)) : 0;
            ManualCraftMachineMenu.this.doFunction(blockMenu, clickAction, player, offset);
            return false;
        }));
        for (int slotP = 0; slotP < CRAFT_L_SLOT.length; slotP++) {
            final int fSlotP = slotP;
            blockMenu.addMenuClickHandler(CRAFT_L_SLOT[slotP], (player, i, itemStack, clickAction) -> {
                if(!ManualCraftMachineMenu.this.verifyCount(l)) {
                    return false;
                }

                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));
                int offset = get(l, KEY_L[fSlotP]) != null ? Integer.parseInt(get(l, KEY_L[fSlotP])) : 0;
                ManualCraftMachineMenu.this.doFunction(blockMenu, clickAction, player, offset);
                return false;
            });
        }
        for (int slotP = 0; slotP < CRAFT_R_SLOT.length; slotP++) {
            final int fSlotP = slotP;
            blockMenu.addMenuClickHandler(CRAFT_R_SLOT[slotP], (player, i, itemStack, clickAction) -> {
                if(!ManualCraftMachineMenu.this.verifyCount(l)) {
                    return false;
                }

                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));

                int offset = get(l, KEY_R[fSlotP]) != null ? Integer.parseInt(get(l, KEY_R[fSlotP])) : 0;
                ManualCraftMachineMenu.this.doFunction(blockMenu, clickAction, player, offset);
                return false;
            });
        }
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
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        Config config = BlockStorage.getLocationInfo(location);
        String charge = EnergyUtil.getCharge(location);
        int intCharge = Integer.parseInt(charge);
        AdvancedCraft craft = null;
        String order = get(location, KEY_ORDER);
        int offset = get(location, KEY) != null ? Integer.parseInt(get(location, KEY)) : 0;
        if (order == null || ORDER_VALUE_ASC.equals(order)) {
            craft = AdvancedCraft.craftAsc(inventory, INPUT_SLOT, MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getID()), 3456, offset);
        } else if (ORDER_VALUE_DESC.equals(order)) {
            craft = AdvancedCraft.craftDesc(inventory, INPUT_SLOT, MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getID()), 3456, offset);
        }

        if (craft != null) {
            add(location, KEY, String.valueOf(craft.getOffset()));
            ItemStack item = ItemStackUtil.cloneItem(craft.getOutputItemList()[0].getItemStack());
            ItemStackUtil.addLoreToLast(item, FinalTechChanged.getLanguageManager().replaceString(FinalTechChanged.getLanguageString("items", "ManualCraftMachine", "match-item", "lore"), String.valueOf(Math.min(intCharge, craft.getMatchCount()))));
            inventory.setItem(STATUS_SLOT, item);
            int offsetR = offset + 1;
            for (int i = 0; i < STATUS_R_SLOT.length; i++) {
                craft = AdvancedCraft.craftAsc(inventory, INPUT_SLOT, MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getID()), 3456, offsetR);
                if (craft != null) {
                    add(location, KEY_R[i], String.valueOf(craft.getOffset()));
                    offsetR = craft.getOffset() + 1;
                    item = ItemStackUtil.cloneItem(craft.getOutputItemList()[0].getItemStack());
                    ItemStackUtil.addLoreToLast(item, FinalTechChanged.getLanguageManager().replaceString(FinalTechChanged.getLanguageString("items", "ManualCraftMachine", "match-item", "lore"), String.valueOf(Math.min(intCharge, craft.getMatchCount()))));
                    inventory.setItem(STATUS_R_SLOT[i], item);
                } else {
                    add(location, KEY_R[i], null);
                    inventory.setItem(STATUS_R_SLOT[i], STATUS_ICON);
                }
            }
            int offsetL = offset - 1;
            for (int i = 0; i < STATUS_L_SLOT.length; i++) {
                craft = AdvancedCraft.craftDesc(inventory, INPUT_SLOT, MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getID()), 3456, offsetL);
                if (craft != null) {
                    add(location, KEY_L[i], String.valueOf(craft.getOffset()));
                    offsetL = craft.getOffset() - 1;
                    item = ItemStackUtil.cloneItem(craft.getOutputItemList()[0].getItemStack());
                    ItemStackUtil.addLoreToLast(item, FinalTechChanged.getLanguageManager().replaceString(FinalTechChanged.getLanguageString("items", "ManualCraftMachine", "match-item", "lore"), String.valueOf(Math.min(intCharge, craft.getMatchCount()))));
                    inventory.setItem(STATUS_L_SLOT[i], item);
                } else {
                    add(location, KEY_L[i], null);
                    inventory.setItem(STATUS_L_SLOT[i], STATUS_ICON);
                }
            }
        } else {
            add(location, KEY, "0");
            inventory.setItem(STATUS_SLOT, STATUS_ICON);
            for (int slot : STATUS_R_SLOT) {
                inventory.setItem(slot, STATUS_ICON);
            }
            for (int slot : STATUS_L_SLOT) {
                inventory.setItem(slot, STATUS_ICON);
            }
        }

        add(location, KEY_ORDER, null);

        for(int slot : CRAFT_L_SLOT) {
            ItemStack item = inventory.getItem(slot);
            if(!ItemStackUtil.isItemNull(item)) {
                ItemStackUtil.setLore(item, FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("items", "ManualCraftMachine", "craft-icon", "lore"), charge));
            }
        }

        for(int slot : CRAFT_R_SLOT) {
            ItemStack item = inventory.getItem(slot);
            if(!ItemStackUtil.isItemNull(item)) {
                ItemStackUtil.setLore(item, FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("items", "ManualCraftMachine", "craft-icon", "lore"), charge));
            }
        }

        ItemStack item = inventory.getItem(CRAFT_SLOT);
        if(!ItemStackUtil.isItemNull(item)) {
            ItemStackUtil.setLore(item, FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("items", "ManualCraftMachine", "craft-icon", "lore"), charge));
        }
    }

    public void doFunction(@Nonnull BlockMenu blockMenu, @Nonnull ClickAction clickAction, @Nonnull Player player, int offset) {
        Inventory inventory = blockMenu.toInventory();
        Location location = blockMenu.getLocation();
        int charge = Integer.parseInt(EnergyUtil.getCharge(location));
        if(charge == 0) {
            return;
        }

        if (MachineUtil.slotCount(inventory, OUTPUT_SLOT) == OUTPUT_SLOT.length) {
            return;
        }

        int quantity;
        if(!clickAction.isRightClicked()) {
            if(!clickAction.isShiftClicked()) {
                // left-click and default 1
                quantity = this.manualCraftMachine.getLeftClickAmount();
            } else {
                // left-shift-click and default 576
                quantity = this.manualCraftMachine.getLeftShiftClickAmount();
            }
        } else {
            if(!clickAction.isShiftClicked()) {
                // right-click and default 16
                quantity = this.manualCraftMachine.getRightClickAmount();
            } else {
                // right-shift-click and default 2304
                quantity = this.manualCraftMachine.getRightShiftClickAmount();
            }
        }

        quantity = Math.min(quantity, charge / this.manualCraftMachine.getConsume());

        List<AdvancedMachineRecipe> advancedRecipe = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getID());
        List<AdvancedMachineRecipe> targetAdvancedRecipe = new ArrayList<>(List.of(advancedRecipe.get(offset % advancedRecipe.size())));

        AdvancedCraft craft;
        craft = AdvancedCraft.craftAsc(blockMenu.toInventory(), INPUT_SLOT, targetAdvancedRecipe, quantity, 0);

        if (craft == null) {
            return;
        }

        ItemAmountWrapper[] outputItems = craft.getOutputItemList();
        for (ItemAmountWrapper itemAmountWrapper : outputItems) {
            SlimefunItem slimefunItem = SlimefunItem.getByItem(itemAmountWrapper.getItemStack());
            if (slimefunItem != null && !slimefunItem.canUse(player, true)) {
                return;
            }
        }

        craft.setMatchCount(Math.min(craft.getMatchCount(), MachineUtil.calMaxMatch(inventory, OUTPUT_SLOT, craft.getOutputItemList())));
        if (craft.getMatchCount() == 0) {
            return;
        }
        if (advancedRecipe.get(craft.getOffset()).isRandomOutput()) {
            craft.setMatchCount(Math.min(craft.getMatchCount(), (OUTPUT_SLOT.length - MachineUtil.slotCount(inventory, OUTPUT_SLOT)) * ConstantTableUtil.ITEM_MAX_STACK));
        }

        AdvancedMachineRecipe advancedMachineRecipe = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getID()).get(craft.getOffset());
        if (advancedMachineRecipe.getOutputs().length > 1) {
            craft.setMatchCount(Math.min(64, craft.getMatchCount()));
        }

        craft.consumeItem(blockMenu.toInventory());
        for (ItemStack item : craft.calMachineRecipe(0).getOutput()) {
            blockMenu.pushItem(item, OUTPUT_SLOT);
        }

        EnergyUtil.setCharge(location, String.valueOf(charge - craft.getMatchCount() * this.manualCraftMachine.getConsume()));

        this.updateInventory(inventory, blockMenu.getLocation());
    }

    public boolean verifyCount(@Nonnull Location location) {
        Integer count = this.manualCraftMachine.getLocationCountMap().getOrDefault(location, 0);
        if(count < this.manualCraftMachine.getCountThreshold()) {
            this.manualCraftMachine.getLocationCountMap().put(location, ++count);
            return true;
        } else {
            return false;
        }
    }
}