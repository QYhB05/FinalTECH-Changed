package io.taraxacum.finaltech.core.patch;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.core.helper.Icon;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public class EnergyRegulatorStaticsMenu extends AbstractMachineMenu {
    public static final int STATUS_SLOT = 4;
    public static final int BUG_REPORT_SLOT = 8;
    public static final ItemStack BUG_REPORT_ICON = new CustomItemStack(Material.BOOK, "&fBug report",
            "&fThe energy regulator is altered by plugin FinalTECH.",
            "&fIf any bug is found, report it to FinalTECH, not Slimefun!");
    private static final int[] BORDER = new int[]{0, 1, 2, 3, 5, 6, 7};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[0];
    private static final int[] OUTPUT_SLOT = new int[0];

    public EnergyRegulatorStaticsMenu(@Nonnull SlimefunItem slimefunItem) {
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
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }

    @Override
    public void init() {
        super.init();
        this.addItem(STATUS_SLOT, Icon.STATUS_ICON);
        this.addMenuClickHandler(STATUS_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(BUG_REPORT_SLOT, BUG_REPORT_ICON);
        this.addMenuClickHandler(BUG_REPORT_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        blockMenu.addMenuOpeningHandler(player -> {
            if (player.isSneaking()) {
                new EnergyRegulatorDetailMenu(blockMenu.getLocation()).open(player);
            }
        });

        blockMenu.addMenuClickHandler(STATUS_SLOT, (player, slot, item, action) -> {
            new EnergyRegulatorDetailMenu(blockMenu.getLocation()).open(player);
            return false;
        });
    }
}
