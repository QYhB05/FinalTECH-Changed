package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.item.unusable.ReplaceableCard;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.AutoItemDismantleTableMenu;
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

public class AutoItemDismantleTable extends AbstractMachine implements RecipeItem {
    private final Set<String> allowedRecipeType = new HashSet<>(ConfigUtil.getItemStringList(this, "allowed-recipe-type"));
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final Set<String> allowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "allowed-id"));

    public AutoItemDismantleTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
    protected AbstractMachineMenu setMachineMenu() {
        return new AutoItemDismantleTableMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (MachineUtil.isEmpty(blockMenu.toInventory(), this.getOutputSlot())) {
            ItemStack itemStack = blockMenu.getItemInSlot(this.getInputSlot()[0]);
            SlimefunItem sfItem = SlimefunItem.getByItem(itemStack);
            if (sfItem != null && this.calAllowed(sfItem) && itemStack.getAmount() >= sfItem.getRecipeOutput().getAmount()) {
                boolean verify;
                if (sfItem instanceof ValidItem validItem) {
                    verify = validItem.verifyItem(itemStack);
                } else {
                    verify = ItemStackUtil.isItemSimilar(itemStack, sfItem.getRecipeOutput()) && ItemStackUtil.isEnchantmentSame(itemStack, sfItem.getRecipeOutput());
                }
                if (verify) {
                    int amount = itemStack.getAmount() / sfItem.getRecipeOutput().getAmount();
                    for (ItemStack outputItem : sfItem.getRecipe()) {
                        if (!ItemStackUtil.isItemNull(outputItem)) {
                            amount = Math.min(amount, outputItem.getMaxStackSize() / outputItem.getAmount());
                        }
                    }
                    itemStack.setAmount(itemStack.getAmount() - sfItem.getRecipeOutput().getAmount() * amount);
                    for (int i = 0; i < this.getOutputSlot().length && i < sfItem.getRecipe().length; i++) {
                        if (!ItemStackUtil.isItemNull(sfItem.getRecipe()[i])) {
                            ItemStack outputItem;
                            ReplaceableCard replaceableCard = RecipeUtil.getReplaceableCard(sfItem.getRecipe()[i]);
                            if (replaceableCard != null && replaceableCard.getExtraSourceMaterial() != null) {
                                outputItem = ItemStackUtil.cloneItem(replaceableCard.getItem());
                            } else {
                                outputItem = ItemStackUtil.cloneItem(sfItem.getRecipe()[i]);
                            }
                            outputItem.setAmount(outputItem.getAmount() * amount);
                            blockMenu.replaceExistingItem(this.getOutputSlot()[i], outputItem);
                        }
                    }
                }
            }
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

    private boolean calAllowed(@Nonnull SlimefunItem slimefunItem) {
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
}
