package io.taraxacum.finaltech.core.item.machine.manual;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.machine.ItemDismantleTableMenu;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.RecipeTypeRegistry;
import io.taraxacum.libs.slimefun.interfaces.ValidItem;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
 
public class ItemDismantleTable extends AbstractManualMachine implements RecipeItem {
    private final Set<String> allowedRecipeType = new HashSet<>(ConfigUtil.getItemStringList(this, "allowed-recipe-type"));
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final Set<String> allowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "allowed-id"));

    private final String key = "c";
    private final String count = ConfigUtil.getOrDefaultItemSetting("600", this, "count");
    private final String limit = ConfigUtil.getOrDefaultItemSetting("900", this, "limit");

    public ItemDismantleTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractManualMachineMenu newMachineMenu() {
        return new ItemDismantleTableMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        String count = JavaUtil.getFirstNotNull(BlockStorage.getLocationInfo(block.getLocation(), key), StringNumberUtil.ZERO);
        if (StringNumberUtil.compare(count, limit) < 0) {
            BlockStorage.addBlockInfo(block.getLocation(), key, StringNumberUtil.add(count));
        }

        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (blockMenu != null && blockMenu.hasViewer()) {
            this.getMachineMenu().updateInventory(blockMenu.toInventory(), block.getLocation());
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeTypeRegistry.getInstance().reload();

        RecipeUtil.registerDescriptiveRecipeWithBorder(FinalTechChanged.getLanguageManager(), this);

        for (String id : this.allowedRecipeType) {
            RecipeType recipeType = RecipeTypeRegistry.getInstance().getRecipeTypeById(id);
            if (recipeType != null && !ItemStackUtil.isItemNull(recipeType.toItem())) {
                this.registerDescriptiveRecipe(recipeType.toItem());
            }
        }
    }

    public boolean calAllowed(@Nonnull SlimefunItem slimefunItem) {
        if (this.allowedId.contains(slimefunItem.getId())) {
            return true;
        } else if (this.notAllowedId.contains(slimefunItem.getId())) {
            return false;
        } else {
            String slimefunItemId = slimefunItem.getId();
            synchronized (this) {
                if (this.allowedId.contains(slimefunItemId)) {
                    return true;
                } else if (this.notAllowedId.contains(slimefunItemId)) {
                    return false;
                }

                if (!this.allowedRecipeType.contains(slimefunItem.getRecipeType().getKey().getKey())) {
                    this.notAllowedId.add(slimefunItemId);
                    return false;
                }

                if (slimefunItem.getRecipe().length > this.getOutputSlot().length) {
                    this.notAllowedId.add(slimefunItemId);
                    return false;
                }

                boolean hasRecipe = false;
                for (ItemStack itemStack : slimefunItem.getRecipe()) {
                    if (ItemStackUtil.isItemNull(itemStack)) {
                        continue;
                    }
                    hasRecipe = true;
                    SlimefunItem sfItem = SlimefunItem.getByItem(itemStack);
                    if (sfItem == null && !ItemStackUtil.isItemSimilar(itemStack, new ItemStack(itemStack.getType()))) {
                        this.notAllowedId.add(slimefunItemId);
                        return false;
                    } else if (sfItem instanceof ValidItem) {
                        this.notAllowedId.add(slimefunItemId);
                        return false;
                    }
                }
                if (!hasRecipe) {
                    this.notAllowedId.add(slimefunItemId);
                    return false;
                }

                this.allowedId.add(slimefunItemId);
                return true;
            }
        }
    }

    public String getKey() {
        return key;
    }

    public String getCount() {
        return count;
    }
}
