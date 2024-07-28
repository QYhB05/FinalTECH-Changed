package io.taraxacum.finaltech.core.item.machine.cargo.storage;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.item.machine.cargo.AbstractCargo;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.MatrixReactorMenu;
import io.taraxacum.finaltech.core.menu.machine.StorageInteractPortMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.StringItemUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class StorageInteractPort extends AbstractCargo implements RecipeItem {
    private final int searchLimit = ConfigUtil.getOrDefaultItemSetting(3, this, "search-limit");

    public StorageInteractPort(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StorageInteractPortMenu(this);
    }

    @Override
    protected void tick(Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Block targetBlock = block.getRelative(BlockFace.UP);
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (!BlockStorage.hasInventory(targetBlock)) {
            if (Bukkit.isPrimaryThread()) {
                BlockState blockState = targetBlock.getState();
                if (blockState instanceof InventoryHolder) {
                    Inventory targetInventory = ((InventoryHolder) blockState).getInventory();
                    this.doFunction(targetInventory, blockMenu, block.getLocation());
                }
            } else {
                JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
                javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                    BlockState blockState = targetBlock.getState();
                    if (blockState instanceof InventoryHolder) {
                        Inventory targetInventory = ((InventoryHolder) blockState).getInventory();
                        FinalTechChanged.getLocationRunnableFactory().waitThenRun(() -> StorageInteractPort.this.doFunction(targetInventory, blockMenu, block.getLocation()), targetBlock.getLocation(), block.getLocation());
                    }
                });
            }
        }
    }

    private void doFunction(@Nonnull Inventory targetInventory, @Nonnull BlockMenu blockMenu, @Nonnull Location location) {
        boolean canInput = !MachineUtil.isEmpty(blockMenu.toInventory(), this.getInputSlot()) && MachineUtil.slotCount(blockMenu.toInventory(), this.getInputSlot()) >= this.getInputSlot().length / 2;
        boolean canOutput = !MachineUtil.isFull(blockMenu.toInventory(), this.getOutputSlot()) && MachineUtil.slotCount(blockMenu.toInventory(), this.getOutputSlot()) < this.getOutputSlot().length / 2;

        if (!canInput && !canOutput) {
            return;
        }

        if (canInput) {
            MachineUtil.stockSlots(blockMenu.toInventory(), this.getInputSlot());
        }

        canInput = !MachineUtil.isEmpty(blockMenu.toInventory(), this.getInputSlot()) && MachineUtil.slotCount(blockMenu.toInventory(), this.getInputSlot()) >= this.getInputSlot().length / 2;

        if (!canInput && !canOutput) {
            return;
        }

        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (!ItemStackUtil.isItemNull(item) && !FinalTechItems.STORAGE_CARD.isTargetItem(item)) {
                JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
                javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> blockMenu.dropItems(location, MatrixReactorMenu.ITEM_INPUT_SLOT));
                return;
            }
        }

        int pushItemAmount = 0;
        List<ItemWrapper> storageCardItemList = new ArrayList<>(Math.min(targetInventory.getSize(), this.searchLimit));
        for (int i = 0, size = Math.min(targetInventory.getSize(), this.searchLimit); i < size; i++) {
            ItemStack itemStack = targetInventory.getItem(i);
            if (ItemStackUtil.isItemNull(itemStack) || !itemStack.hasItemMeta()) {
                continue;
            }
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null && FinalTechItems.STORAGE_CARD.verifyItem(itemMeta)) {
                storageCardItemList.add(new ItemWrapper(itemStack, itemMeta));
                if (itemStack.getAmount() == 1) {
                    pushItemAmount++;
                }
            }
        }

        List<ItemWrapper> unOutputItem = new LinkedList<>();
        List<ItemWrapper> unInputItem = new LinkedList<>();

        for (ItemWrapper storageCardItem : storageCardItemList) {
            if (!canInput && !canOutput) {
                continue;
            }
            ItemMeta itemMeta = storageCardItem.getItemMeta();
            ItemStack stringItemStack = StringItemUtil.parseItemInCard(itemMeta);
            ItemWrapper stringItem = stringItemStack == null ? null : new ItemWrapper(stringItemStack);

            boolean work;

            int pushCount = 0;
            if (canOutput && storageCardItem.getItemStack().getAmount() == 1 && stringItem != null) {
                work = true;
                for (ItemWrapper unWorkItem : unOutputItem) {
                    if (ItemStackUtil.isItemSimilar(stringItem, unWorkItem)) {
                        work = false;
                        break;
                    }
                }
                if (work) {
                    pushItemAmount--;
                    pushCount = StringItemUtil.pullItemFromCard(itemMeta, stringItem, blockMenu.toInventory(), this.getOutputSlot());
                    if (pushCount == 0) {
                        unOutputItem.add(new ItemWrapper(stringItem.getItemStack()));
                    } else {
                        MachineUtil.stockSlots(blockMenu.toInventory(), this.getOutputSlot());
                        canOutput = !MachineUtil.isFull(blockMenu.toInventory(), this.getOutputSlot());
                    }
                    if (pushItemAmount == 0) {
                        canOutput = false;
                    }
                }
            }

            int stackCount = 0;
            if (canInput) {
                work = true;
                if (stringItem != null) {
                    for (ItemWrapper unWorkItem : unInputItem) {
                        if (ItemStackUtil.isItemSimilar(stringItem, unWorkItem)) {
                            work = false;
                            break;
                        }
                    }
                }
                if (work) {
                    stackCount = StringItemUtil.storageItemToCard(itemMeta, stringItem, storageCardItem.getItemStack().getAmount(), blockMenu.toInventory(), JavaUtil.shuffle(this.getInputSlot()));
                    if (stackCount == 0) {
                        if (stringItem != null) {
                            unInputItem.add(new ItemWrapper(stringItem.getItemStack()));
                        }
                    } else {
                        canInput = !MachineUtil.isEmpty(blockMenu.toInventory(), this.getInputSlot());
                        if (stringItem == null) {
                            stringItem = new ItemWrapper(StringItemUtil.parseItemInCard(itemMeta));
                        }
                    }
                }
            }
            if (pushCount != 0 || stackCount != 0) {
                FinalTechItems.STORAGE_CARD.updateLore(itemMeta, stringItem.getItemStack());
                storageCardItem.getItemStack().setItemMeta(itemMeta);
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.searchLimit));
    }
}
