package io.taraxacum.finaltech.core.item.machine.operation;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.limit.DustFactoryDirtMenu;
import io.taraxacum.finaltech.core.operation.DustFactoryOperation;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
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
import java.util.List;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class DustFactoryDirt extends AbstractOperationMachine implements RecipeItem, MenuUpdater {
    public final int baseAmountDifficulty = ConfigUtil.getOrDefaultItemSetting(1024, this, "difficulty", "base", "amount");
    public final int baseTypeDifficulty = ConfigUtil.getOrDefaultItemSetting(16, this, "difficulty", "base", "type");
    public final int multiAmountDifficulty = ConfigUtil.getOrDefaultItemSetting(64, this, "difficulty", "multi", "amount");
    public final int multiTypeDifficulty = ConfigUtil.getOrDefaultItemSetting(1, this, "difficulty", "multi", "type");
    public final int deviationDifficulty = ConfigUtil.getOrDefaultItemSetting(-4, this, "difficulty", "deviation");

    public DustFactoryDirt(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
                blockMenu.dropItems(location, DustFactoryDirt.this.getInputSlot());
                blockMenu.dropItems(location, DustFactoryDirt.this.getOutputSlot());

                DustFactoryDirt.this.getMachineProcessor().endOperation(location);
            }
        };
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new DustFactoryDirtMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        DustFactoryOperation operation = (DustFactoryOperation) this.getMachineProcessor().getOperation(block);

        for (int slot : this.getInputSlot()) {
            ItemStack inputItem = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }

            if (operation == null) {

                operation = new DustFactoryOperation(this.baseAmountDifficulty, this.baseTypeDifficulty);
                this.getMachineProcessor().startOperation(block, operation);
            }
            operation.addItem(inputItem);

            blockMenu.consumeItem(slot, inputItem.getAmount());

            ItemStack operationResult = operation.getResult();
            if (operationResult != null && MachineUtil.calMaxMatch(blockMenu.toInventory(), this.getOutputSlot(), operationResult) > 0) {
                blockMenu.pushItem(operationResult, this.getOutputSlot());
                this.getMachineProcessor().endOperation(block);
                operation = null;
            }
        }

        if (operation == null) {

            operation = new DustFactoryOperation(this.baseAmountDifficulty, this.baseTypeDifficulty);
            this.getMachineProcessor().startOperation(block, operation);
        }

        if (blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, DustFactoryDirtMenu.STATUS_SLOT, this,
                    String.valueOf(operation.getAmountCount()),
                    String.valueOf(operation.getTypeCount()),
                    String.valueOf(operation.getAmountDifficulty()),
                    String.valueOf(operation.getTypeDifficulty()));
        }
    }

    @Override
    public void updateMenu(@Nonnull BlockMenu blockMenu, int slot, @Nonnull SlimefunItem slimefunItem, @Nonnull String... text) {
        MenuUpdater.super.updateMenu(blockMenu, slot, slimefunItem, text);
        if (text.length == 4) {
            int amountCount = Integer.parseInt(text[0]);
            int typeCount = Integer.parseInt(text[1]);
            int amountDifficulty = Integer.parseInt(text[2]);
            int typeDifficulty = Integer.parseInt(text[3]);

            ItemStack itemStack = blockMenu.getItemInSlot(slot);

            if (amountCount == 0 && typeCount == 0) {
                itemStack.setType(Material.RED_STAINED_GLASS_PANE);
            } else if (amountCount > amountDifficulty || typeCount > typeDifficulty) {
                itemStack.setType(Material.YELLOW_STAINED_GLASS_PANE);
            } else {
                itemStack.setType(Material.GREEN_STAINED_GLASS_PANE);
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.baseAmountDifficulty),
                String.valueOf(this.baseTypeDifficulty),
                String.valueOf(this.baseAmountDifficulty + this.deviationDifficulty * this.multiAmountDifficulty),
                String.valueOf(this.baseTypeDifficulty + this.deviationDifficulty * this.multiTypeDifficulty));
    }
}
