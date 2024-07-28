package io.taraxacum.finaltech.core.menu.limit.lock;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.core.helper.MachineRecipeLock;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.limit.AbstractLimitMachineMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public abstract class AbstractLockMachineMenu extends AbstractLimitMachineMenu {
    public static final int RECIPE_LOCK_SLOT = 4;

    public AbstractLockMachineMenu(@Nonnull AbstractMachine abstractMachine) {
        super(abstractMachine);
    }

    @Override
    public void init() {
        super.init();
        this.addItem(this.getRecipeLockSlot(), MachineRecipeLock.ICON);
        this.addMenuClickHandler(this.getRecipeLockSlot(), ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Inventory inventory = blockMenu.toInventory();
        Location location = block.getLocation();

        blockMenu.addMenuClickHandler(this.getRecipeLockSlot(), MachineRecipeLock.HELPER.getHandler(inventory, location, this.getSlimefunItem(), this.getRecipeLockSlot()));
    }

    @Override
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        super.updateInventory(inventory, location);
        MachineRecipeLock.HELPER.checkOrSetBlockStorage(location);
        ItemStack item = inventory.getItem(this.getRecipeLockSlot());
        String recipeLock = BlockStorage.getLocationInfo(location, MachineRecipeLock.KEY);
        MachineRecipeLock.HELPER.setIcon(item, recipeLock, this.getSlimefunItem());
    }

    public int getRecipeLockSlot() {
        return RECIPE_LOCK_SLOT;
    }
}
