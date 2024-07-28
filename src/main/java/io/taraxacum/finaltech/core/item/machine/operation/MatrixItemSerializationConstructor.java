package io.taraxacum.finaltech.core.item.machine.operation;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.ItemSerializationConstructorMenu;
import io.taraxacum.finaltech.core.operation.ItemCopyCardOperation;
import io.taraxacum.finaltech.core.operation.ItemSerializationConstructorOperation;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
 
public class MatrixItemSerializationConstructor extends AbstractOperationMachine {
    private final CustomItemStack nullInfoIcon = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, FinalTechChanged.getLanguageString("items", this.getId(), "null-icon", "name"), FinalTechChanged.getLanguageStringArray("items", this.getId(), "null-icon", "lore"));
    private final String blockStorageItemKey = "item";
    private final String blockStorageAmountKey = "amount";

    private List<Location> locationList = new ArrayList<>();
    private List<Location> lastLocationList = new ArrayList<>();

    public MatrixItemSerializationConstructor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack item, @Nonnull List<ItemStack> drops) {
                Location location = blockBreakEvent.getBlock().getLocation();
                BlockMenu blockMenu = BlockStorage.getInventory(location);
                blockMenu.dropItems(location, MatrixItemSerializationConstructor.this.getInputSlot());
                blockMenu.dropItems(location, MatrixItemSerializationConstructor.this.getOutputSlot());

                MatrixItemSerializationConstructor.this.getMachineProcessor().endOperation(location);
            }
        };
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new ItemSerializationConstructorMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        this.locationList.add(location);
        BlockMenu blockMenu = BlockStorage.getInventory(block);

        if (FinalTechChanged.getTps() < 19.5 && this.lastLocationList.size() > 1) {
            if (BlockTickerUtil.hasSleep(config)) {
                BlockTickerUtil.subSleep(config);
                return;
            }

            Location randomLocation = this.lastLocationList.get(FinalTechChanged.getRandom().nextInt(this.lastLocationList.size()));
            double manhattanDistance = LocationUtil.getManhattanDistance(randomLocation, location);
            if (manhattanDistance < this.lastLocationList.size()) {
                BlockTickerUtil.setSleep(config, String.valueOf(this.lastLocationList.size() * (int) (20 - FinalTechChanged.getTps() + 1) * (1 + MachineUtil.slotCount(blockMenu.toInventory(), this.getInputSlot()))));
                return;
            }
        }

        ItemSerializationConstructorOperation operation = (ItemSerializationConstructorOperation) this.getMachineProcessor().getOperation(block);

        if (operation == null && config.contains(this.blockStorageItemKey)) {
            String itemString = config.getString(this.blockStorageItemKey);
            ItemStack stringItem = ItemStackUtil.stringToItemStack(itemString);
            if (!ItemStackUtil.isItemNull(stringItem) && ItemSerializationConstructorOperation.getType(stringItem) == ItemSerializationConstructorOperation.COPY_CARD) {
                operation = ItemSerializationConstructorOperation.newInstance(stringItem);
                if (operation != null) {
                    this.getMachineProcessor().startOperation(block, operation);
                    int amount = (int) Double.parseDouble(config.getString(this.blockStorageAmountKey));
                    ((ItemCopyCardOperation) operation).setCount(amount);
                }
            }
        }

        for (int slot : this.getInputSlot()) {
            ItemStack inputItem = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            if (operation == null) {
                operation = ItemSerializationConstructorOperation.newInstance(inputItem);
                if (operation == null) {
                    break;
                }
                this.getMachineProcessor().startOperation(block, operation);
            } else {
                operation.addItem(inputItem);
            }
        }

        if (operation != null && operation.isFinished() && InvUtils.fits(blockMenu.toInventory(), operation.getResult(), this.getOutputSlot())) {
            blockMenu.pushItem(operation.getResult(), this.getOutputSlot());
            this.getMachineProcessor().endOperation(block);
            operation = null;
            BlockStorage.addBlockInfo(location, this.blockStorageItemKey, null);
            BlockStorage.addBlockInfo(location, this.blockStorageAmountKey, null);
        }

        if (operation != null && operation.getType() == ItemSerializationConstructorOperation.COPY_CARD) {
            if (!config.contains(this.blockStorageItemKey)) {
                BlockStorage.addBlockInfo(location, this.blockStorageItemKey, ItemStackUtil.itemStackToString(((ItemCopyCardOperation) operation).getMatchItem()));
            }
            BlockStorage.addBlockInfo(location, this.blockStorageAmountKey, String.valueOf((int) ((ItemCopyCardOperation) operation).getCount()));
        }

        if (blockMenu.hasViewer()) {
            ItemStack showItem;
            if (operation != null) {
                operation.updateShowItem();
                showItem = operation.getShowItem();
            } else {
                showItem = this.nullInfoIcon;
            }
            blockMenu.replaceExistingItem(ItemSerializationConstructorMenu.STATUS_SLOT, showItem);
        }
    }

    @Override
    protected void uniqueTick() {
        super.uniqueTick();
        List<Location> locationList = this.lastLocationList;
        this.lastLocationList = this.locationList;
        this.locationList = locationList;
        this.locationList.clear();

        if (FinalTechChanged.getTps() < ConstantTableUtil.WARNING_TPS) {
            FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.setEfficiency(Math.pow(FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getRate() / (1 + FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getLastLocationList().size() + this.lastLocationList.size()), 20.0 - FinalTechChanged.getTps()));
            FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.setEfficiency(FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getEfficiency() / (1 + FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getLastLocationList().size() + this.lastLocationList.size()));
        } else {
            FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.setEfficiency(1);
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT),
                String.valueOf(ConstantTableUtil.ITEM_SINGULARITY_AMOUNT),
                String.valueOf(ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT));
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public List<Location> getLastLocationList() {
        return lastLocationList;
    }
}
