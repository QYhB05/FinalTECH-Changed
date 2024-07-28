package io.taraxacum.finaltech.core.menu.manual;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.item.machine.manual.EquivalentExchangeTable;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.ItemValueTable;
import io.taraxacum.libs.slimefun.util.SfItemUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class EquivalentExchangeTableMenu extends AbstractManualMachineMenu {
    public static final int PARSE_ITEM_SLOT = 13;
    public static final int STATUS_SLOT = 40;
    private static final int[] BORDER = new int[]{30, 31, 32, 39, 41, 48, 49, 50};
    private static final int[] INPUT_BORDER = new int[]{2, 11, 20, 29, 38, 47};
    private static final int[] OUTPUT_BORDER = new int[]{6, 15, 24, 33, 42, 51};
    private static final int[] INPUT_SLOT = new int[]{0, 1, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46};
    private static final int[] OUTPUT_SLOT = new int[]{7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53};
    private static final int[] PARSE_BORDER = new int[]{3, 4, 5, 12, 14, 21, 23};
    private static final int PARSE_STATUS_SLOT = 22;
    private static final ItemStack PARSE_BORDER_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("items", SfItemUtil.getIdFormatName(EquivalentExchangeTable.class), "parse-border-icon", "name"), FinalTechChanged.getLanguageStringArray("items", SfItemUtil.getIdFormatName(EquivalentExchangeTable.class), "parse-border-icon", "lore"));
    private static final ItemStack PARSE_STATUS_ICON = new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("items", SfItemUtil.getIdFormatName(EquivalentExchangeTable.class), "parse-result-icon", "name"), FinalTechChanged.getLanguageStringArray("items", SfItemUtil.getIdFormatName(EquivalentExchangeTable.class), "parse-result-icon", "lore"));
    private static final ItemStack CRAFT_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("items", SfItemUtil.getIdFormatName(EquivalentExchangeTable.class), "status-icon", "name"), FinalTechChanged.getLanguageStringArray("items", SfItemUtil.getIdFormatName(EquivalentExchangeTable.class), "status-icon", "lore"));

    public EquivalentExchangeTableMenu(@Nonnull AbstractMachine machine) {
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
    public void init() {
        super.init();
        for (int slot : PARSE_BORDER) {
            this.addItem(slot, PARSE_BORDER_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
        this.addItem(PARSE_STATUS_SLOT, PARSE_STATUS_ICON);
        this.addMenuClickHandler(PARSE_STATUS_SLOT, ChestMenuUtils.getEmptyClickHandler());

        this.addItem(STATUS_SLOT, CRAFT_ICON);
        this.addMenuClickHandler(STATUS_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        ItemStack item = inventory.getItem(PARSE_ITEM_SLOT);
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        List<String> lore = new ArrayList<>();
        if (!ItemStackUtil.isItemNull(item)) {
            lore.add("§f" + ItemStackUtil.getItemName(item));
        }
        if (slimefunItem == null) {
            lore.addAll(FinalTechChanged.getLanguageStringList("items", this.getID(), "no-value", "lore"));
        } else {
            lore.addAll(FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("items", this.getID(), "input-value", "lore"), ItemValueTable.getInstance().getOrCalItemInputValue(item)));
            lore.addAll(FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("items", this.getID(), "output-value", "lore"), ItemValueTable.getInstance().getOrCalItemOutputValue(item)));
        }
        ItemStack iconItem = inventory.getItem(PARSE_STATUS_SLOT);
        ItemStackUtil.setLore(iconItem, lore);

        iconItem = inventory.getItem(STATUS_SLOT);
        ItemStackUtil.setLore(iconItem, FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("items", this.getID(), "stored-value", "lore"), BlockStorage.getLocationInfo(location, "value")));
    }
}
