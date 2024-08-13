package io.taraxacum.finaltech.core.item.machine.manual;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.finaltech.core.menu.manual.EquivalentExchangeTableMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.ItemValueTable;
import io.taraxacum.libs.slimefun.interfaces.SimpleValidItem;
import io.taraxacum.libs.slimefun.interfaces.ValidItem;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
 
public class EquivalentExchangeTable extends AbstractManualMachine implements RecipeItem {
    private final String key = "value";

    public EquivalentExchangeTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this, EquivalentExchangeTableMenu.PARSE_ITEM_SLOT);
    }

    @Nonnull
    @Override
    protected AbstractManualMachineMenu newMachineMenu() {
        return new EquivalentExchangeTableMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        String value = JavaUtil.getFirstNotNull(BlockStorage.getLocationInfo(block.getLocation(), this.key), StringNumberUtil.ZERO);
        for (int slot : this.getInputSlot()) {
            ItemStack itemStack = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(itemStack)) {
                continue;
            }

            if (FinalTechItems.UNORDERED_DUST.verifyItem(itemStack)) {
                if (MachineUtil.slotCount(blockMenu.toInventory(), this.getOutputSlot()) == this.getOutputSlot().length) {
                    continue;
                }
                value = this.doCraft(value, blockMenu, itemStack.getAmount());
                itemStack.setAmount(0);
                break;
            }

            SlimefunItem sfItem = SlimefunItem.getByItem(itemStack);
            if (sfItem != null) {
                if (sfItem instanceof ValidItem validItem && !validItem.verifyItem(itemStack)) {
                    value = StringNumberUtil.add(value);
                } else {
                    value = StringNumberUtil.add(value, StringNumberUtil.mul(ItemValueTable.getInstance().getOrCalItemInputValue(sfItem), String.valueOf(itemStack.getAmount())));
                }
                itemStack.setAmount(0);
            }
        }

        BlockStorage.addBlockInfo(block.getLocation(), this.key, value);

        if (blockMenu.hasViewer()) {
            this.getMachineMenu().updateInventory(blockMenu.toInventory(), block.getLocation());
        }
    }

    private String doCraft(@Nonnull String value, @Nonnull BlockMenu blockMenu, int amount) {
        if (StringNumberUtil.compare(value, StringNumberUtil.ZERO) <= 0) {
            return StringNumberUtil.ZERO;
        }

        List<SlimefunItem> slimefunItemList = Slimefun.getRegistry().getAllSlimefunItems();
        int searchedTime = 0;
        SlimefunItem searchedSlimefunItem = null;
        String searchedValue = null;

        for (int i = 0, retryTimes = value.length(); i < retryTimes; i++) {
            SlimefunItem slimefunItem = slimefunItemList.get(FinalTechChanged.getRandom().nextInt(slimefunItemList.size()));
            String targetValue = ItemValueTable.getInstance().getOrCalItemOutputValue(slimefunItem);

            if (targetValue.equals(StringNumberUtil.VALUE_INFINITY) && ++searchedTime >= amount) {
                i--;
                continue;
            }

            if (StringNumberUtil.compare(value, targetValue) >= 0) {
                List<String> idList = ItemValueTable.getInstance().getValueItemListOutputMap().get(targetValue);
                if (idList == null || idList.isEmpty()) {
                    continue;
                }
                String id = idList.get(FinalTechChanged.getRandom().nextInt(idList.size()));
                slimefunItem = SlimefunItem.getById(id);
                if (slimefunItem == null || slimefunItem instanceof MultiBlockMachine) {
                    continue;
                }

                if (searchedValue == null || StringNumberUtil.ZERO.equals(targetValue) || StringNumberUtil.compare(targetValue, searchedValue) > 0) {
                    searchedSlimefunItem = slimefunItem;
                    searchedValue = targetValue;
                }
            }
        }

        if (searchedSlimefunItem != null) {
            ItemStack itemStack = searchedSlimefunItem instanceof SimpleValidItem simpleValidItem ? simpleValidItem.getValidItem() : ItemStackUtil.cloneItem(searchedSlimefunItem.getItem(), 1);
            if (MachineUtil.calMaxMatch(blockMenu.toInventory(), this.getOutputSlot(), List.of(new ItemAmountWrapper(itemStack))) >= 1) {
                blockMenu.pushItem(itemStack, this.getOutputSlot());
                if (StringNumberUtil.ZERO.equals(searchedValue)) {
                    value = StringNumberUtil.ZERO;
                } else {
                    value = StringNumberUtil.sub(value, searchedValue);
                }
            }
        }

        return value;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this);
    }
}
