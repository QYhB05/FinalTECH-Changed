package io.taraxacum.finaltech.core.menu.limit;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.taraxacum.finaltech.core.helper.MachineMaxStack;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
 
public abstract class AbstractLimitMachineMenu extends AbstractMachineMenu {
    private final int MACHINE_MAX_STACK_SLOT = 13;

    public AbstractLimitMachineMenu(@Nonnull AbstractMachine abstractMachine) {
        super(abstractMachine);
    }

    @Override
    public void init() {
        super.init();
        this.addItem(this.getMachineMaxStackSlot(), MachineMaxStack.ICON);
        this.addMenuClickHandler(this.getMachineMaxStackSlot(), ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Inventory inventory = blockMenu.toInventory();
        Location location = block.getLocation();

        blockMenu.addMenuClickHandler(this.getMachineMaxStackSlot(), MachineMaxStack.HELPER.getHandler(inventory, location, this.getSlimefunItem(), this.getMachineMaxStackSlot()));
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        if (ItemTransportFlow.WITHDRAW.equals(flow)) {
            return this.getOutputSlot();
        } else if (flow == null) {
            return new int[0];
        }

        int full = 0;
        if (menu.getItemInSlot(this.getMachineMaxStackSlot()).getType().equals(Material.CHEST)) {
            return this.getInputSlot();
        }

        ArrayList<Integer> itemList = new ArrayList<>();
        ArrayList<Integer> nullList = new ArrayList<>();
        ItemStackWrapper itemStackWrapper = ItemStackWrapper.wrap(item);
        int inputLimit = menu.getItemInSlot(this.getMachineMaxStackSlot()).getAmount();
        for (int slot : this.getInputSlot()) {
            ItemStack existedItem = menu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(existedItem)) {
                nullList.add(slot);
            } else if (ItemStackUtil.isItemSimilar(itemStackWrapper, existedItem)) {
                if (existedItem.getAmount() < existedItem.getMaxStackSize()) {
                    itemList.add(slot);
                } else {
                    full++;
                }
                if (itemList.size() + full >= inputLimit) {
                    break;
                }
            }
        }

        int[] slots = new int[Math.max(inputLimit - full, 0)];
        int i;
        for (i = 0; i < itemList.size() && i < slots.length; i++) {
            slots[i] = itemList.get(i);
        }
        for (int j = 0; j < nullList.size() && j < slots.length - i; j++) {
            slots[i + j] = nullList.get(j);
        }
        return slots;
    }

    @Override
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        MachineMaxStack.HELPER.checkOrSetBlockStorage(location);
        String quantity = MachineMaxStack.HELPER.getOrDefaultValue(location);
        ItemStack item = inventory.getItem(this.getMachineMaxStackSlot());
        MachineMaxStack.HELPER.setIcon(item, quantity);
    }

    public int getMachineMaxStackSlot() {
        return MACHINE_MAX_STACK_SLOT;
    }
}
