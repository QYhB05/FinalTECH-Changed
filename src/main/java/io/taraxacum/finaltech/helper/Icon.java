package io.taraxacum.finaltech.core.helper;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.item.unusable.module.AbstractQuantityModule;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class Icon {
    public static final ItemStack QUANTITY_MODULE_ICON = new CustomItemStack(Material.REDSTONE, FinalTechChanged.getLanguageString("helper", "ICON", "quantity-module", "name"), FinalTechChanged.getLanguageStringArray("helper", "ICON", "quantity-module", "lore"));

    public static final ItemStack BORDER_ICON = ChestMenuUtils.getBackground();
    //    public static final ItemStack INPUT_BORDER_ICON = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("helper", "CARGO_ORDER", "input-border", "name"), FinalTechChanged.getLanguageStringArray("helper", "CARGO_ORDER", "input-border", "lore"));
    public static final ItemStack INPUT_BORDER_ICON = ChestMenuUtils.getInputSlotTexture();
    //    public static final ItemStack OUTPUT_BORDER_ICON = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("helper", "CARGO_ORDER", "output-border", "name"), FinalTechChanged.getLanguageStringArray("helper", "CARGO_ORDER", "output-border", "lore"));
    public static final ItemStack OUTPUT_BORDER_ICON = ChestMenuUtils.getOutputSlotTexture();
    public static final ItemStack SPECIAL_BORDER_ICON = new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE, " ");

    public static final ItemStack NEXT_PAGE_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("helper", "ICON", "next-page", "name"), FinalTechChanged.getLanguageStringArray("helper", "ICON", "next-page", "lore"));
    public static final ItemStack PREVIOUS_PAGE_ICON = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("helper", "ICON", "previous-page", "name"), FinalTechChanged.getLanguageStringArray("helper", "ICON", "previous-page", "lore"));

    public static final ItemStack STATUS_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("helper", "ICON", "status", "name"), FinalTechChanged.getLanguageStringArray("helper", "ICON", "status", "lore"));

    public static final ItemStack ERROR_ICON = new CustomItemStack(Material.BARRIER, FinalTechChanged.getLanguageString("helper", "ICON", "error", "name"), FinalTechChanged.getLanguageStringArray("helper", "ICON", "error", "lore"));

    public static final ItemStack WIKI_ICON = new CustomItemStack(Material.KNOWLEDGE_BOOK, FinalTechChanged.getLanguageString("helper", "ICON", "wiki-icon", "name"), FinalTechChanged.getLanguageStringArray("helper", "ICON", "wiki-icon", "lore"));

    public static final ItemStack RECIPE_ICON = new CustomItemStack(Material.PAPER, FinalTechChanged.getLanguageString("helper", "ICON", "recipe-icon", "name"), FinalTechChanged.getLanguageStringArray("helper", "ICON", "recipe-icon", "lore"));

    public static final ItemStack CONSUMER_ICON = new CustomItemStack(Material.FURNACE, FinalTechChanged.getLanguageString("helper", "ICON", "consumer", "name"), FinalTechChanged.getLanguageStringArray("helper", "ICON", "consumer", "lore"));

    public static final ItemStack GENERATOR_ICON = new CustomItemStack(Material.DAYLIGHT_DETECTOR, FinalTechChanged.getLanguageString("helper", "ICON", "generator", "name"), FinalTechChanged.getLanguageStringArray("helper", "ICON", "generator", "lore"));

    public static final ItemStack CAPACITOR_ICON = new CustomItemStack(Material.YELLOW_STAINED_GLASS, FinalTechChanged.getLanguageString("helper", "ICON", "capacitor", "name"), FinalTechChanged.getLanguageStringArray("helper", "ICON", "capacitor", "lore"));

    public static int updateQuantityModule(@Nonnull BlockMenu blockMenu, int quantityModuleSlot, int statusSlot) {
        boolean updateLore = blockMenu.hasViewer();

        int amount;

        ItemStack itemStack = blockMenu.getItemInSlot(quantityModuleSlot);
        SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
        if (slimefunItem instanceof AbstractQuantityModule quantityModule) {
            amount = quantityModule.getEffect(itemStack.getAmount());
            if (updateLore) {
                List<String> loreList = new ArrayList<>(FinalTechChanged.getLanguageStringList("helper", "ICON", "quantity-module", "lore"));
                if (amount >= 3456) {
                    loreList.addAll(FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("helper", "ICON", "quantity-module", "amount-lore"),
                            FinalTechChanged.getLanguageString("helper", "ICON", "quantity-module", "amount-infinity")));
                } else {
                    loreList.addAll(FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("helper", "ICON", "quantity-module", "amount-lore"),
                            String.valueOf(amount)));
                }
                ItemStackUtil.setLore(blockMenu.getItemInSlot(statusSlot), loreList);
            }
        } else {
            amount = 1;
            if (updateLore) {
                List<String> loreList = new ArrayList<>(FinalTechChanged.getLanguageStringList("helper", "ICON", "quantity-module", "lore"));
                loreList.addAll(FinalTechChanged.getLanguageManager().replaceStringList(FinalTechChanged.getLanguageStringList("helper", "ICON", "quantity-module", "amount-none-lore"),
                        "1"));
                ItemStackUtil.setLore(blockMenu.getItemInSlot(statusSlot), loreList);
            }
        }

        return amount;
    }
}
