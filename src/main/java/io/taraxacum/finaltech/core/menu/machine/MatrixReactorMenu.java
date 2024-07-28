package io.taraxacum.finaltech.core.menu.machine;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.core.helper.Icon;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class MatrixReactorMenu extends AbstractMachineMenu {
    public static final int[] ORDERED_DUST_INPUT_SLOT = new int[]{25, 34, 43};
    public static final int[] UNORDERED_DUST_INPUT_SLOT = new int[]{19, 28, 37};
    public static final int[] ITEM_PHONY_INPUT_SLOT = new int[]{4};
    public static final int[] ITEM_INPUT_SLOT = new int[]{22};
    public static final int STATUS_SLOT = 49;
    private static final int[] BORDER = new int[]{0, 2, 3, 5, 6, 8, 12, 13, 14, 21, 23};
    private static final int[] INPUT_BORDER = new int[]{9, 10, 11, 18, 20, 27, 29, 36, 38, 45, 46, 47, 15, 16, 17, 24, 26, 33, 35, 42, 44, 51, 52, 53};
    private static final int[] OUTPUT_BORDER = new int[]{30, 31, 32, 39, 41, 48, 50};
    private static final int[] INPUT_SLOT = new int[]{19, 28, 37, 25, 34, 43, 22};
    private static final int[] OUTPUT_SLOT = new int[]{40};
    private static final int ORDERED_DUST_SLOT = 1;
    private static final ItemStack ORDERED_DUST_ICON = ItemStackUtil.cloneItem(FinalTechItemStacks.UNORDERED_DUST);
    private static final int UNORDERED_DUST_SLOT = 7;
    private static final ItemStack UNORDERED_DUST_ICON = ItemStackUtil.cloneItem(FinalTechItemStacks.ORDERED_DUST);

    static {
        ItemStackUtil.addLoreToLast(ORDERED_DUST_ICON, " ");
        ItemStackUtil.addLoreToLast(UNORDERED_DUST_ICON, " ");
    }

    public MatrixReactorMenu(@Nonnull AbstractMachine machine) {
        super(machine);
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
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }

    @Override
    public void init() {
        super.init();

        this.addItem(ORDERED_DUST_SLOT, ORDERED_DUST_ICON);
        this.addMenuClickHandler(ORDERED_DUST_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(UNORDERED_DUST_SLOT, UNORDERED_DUST_ICON);
        this.addMenuClickHandler(UNORDERED_DUST_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(STATUS_SLOT, Icon.STATUS_ICON);
        this.addMenuClickHandler(STATUS_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
        return super.getSlotsAccessedByItemTransport(itemTransportFlow);
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack itemStack) {
        if (ItemTransportFlow.WITHDRAW.equals(flow)) {
            return OUTPUT_SLOT;
        } else if (flow == null) {
            return new int[0];
        }
        if (FinalTechItems.ORDERED_DUST.verifyItem(itemStack)) {
            return ORDERED_DUST_INPUT_SLOT;
        } else if (FinalTechItems.UNORDERED_DUST.verifyItem(itemStack)) {
            return UNORDERED_DUST_INPUT_SLOT;
        } else if (FinalTechItems.ITEM_PHONY.verifyItem(itemStack)) {
            return ITEM_PHONY_INPUT_SLOT;
        } else {
            return ITEM_INPUT_SLOT;
        }
    }
}
