package io.taraxacum.finaltech.core.menu.clicker;

import io.taraxacum.finaltech.core.helper.ForceClose;
import io.taraxacum.finaltech.core.item.machine.clicker.AbstractClickerMachine;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public class ClickWorkMachineMenu extends AbstractClickerMenu {
    private static final int[] BORDER = new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 13, 16, 17};
    private static final int[] INPUT_BORDER = new int[]{11};
    private static final int[] OUTPUT_BORDER = new int[]{15};
    private static final int[] INPUT_SLOT = new int[]{12};
    private static final int[] OUTPUT_SLOT = new int[]{14};
    private static final int FORCE_CLOSE_SLOT = 4;

    public ClickWorkMachineMenu(@Nonnull AbstractClickerMachine slimefunItem) {
        super(slimefunItem);
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

        this.addItem(FORCE_CLOSE_SLOT, ForceClose.HELPER.defaultIcon());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);

        Inventory inventory = blockMenu.toInventory();
        Location location = block.getLocation();

        blockMenu.addMenuClickHandler(FORCE_CLOSE_SLOT, ForceClose.HELPER.getHandler(inventory, location, this.getSlimefunItem(), FORCE_CLOSE_SLOT));
    }

    @Override
    protected void doFunction(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull Player player) {
        ItemStack inputItem = blockMenu.getItemInSlot(INPUT_SLOT[0]);
        ItemStack outputItem = blockMenu.getItemInSlot(OUTPUT_SLOT[0]);
        if (ItemStackUtil.isItemNull(inputItem) || !ItemStackUtil.isItemNull(outputItem)) {
            ItemStack item = blockMenu.getItemInSlot(FORCE_CLOSE_SLOT);
            if (ForceClose.TRUE_ICON.getType().equals(item.getType())) {
                blockMenu.close();
            }
        } else {
            outputItem = ItemStackUtil.cloneItem(inputItem);
            int amount = player.isSneaking() ? Math.min(inputItem.getAmount(), 64) : Math.min(inputItem.getAmount(), 1);

            outputItem.setAmount(amount);
            inputItem.setAmount(inputItem.getAmount() - amount);

            blockMenu.pushItem(outputItem, OUTPUT_SLOT);
            blockMenu.close();

            JavaPlugin javaPlugin = this.getSlimefunItem().getAddon().getJavaPlugin();
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));
        }
    }

    @Override
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        ForceClose.HELPER.checkAndUpdateIcon(inventory, location, FORCE_CLOSE_SLOT);
    }
}
