package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.helper.RouteShow;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public class RouteViewer extends UsableSlimefunItem implements RecipeItem {
    private final Set<String> allowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "allowed-id"));

    private final int interval = ConfigUtil.getOrDefaultItemSetting(500, this, "interval");
    private final int threshold = ConfigUtil.getOrDefaultItemSetting(2, this, "threshold");

    public RouteViewer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    /**
     * The function the item will do
     * while a player hold the item and right click.
     *
     * @param playerRightClickEvent
     */
    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        playerRightClickEvent.cancel();

        Optional<Block> clickedBlock = playerRightClickEvent.getClickedBlock();
        if (clickedBlock.isPresent()) {
            Block block = clickedBlock.get();
            Location location = block.getLocation();
            LocationInfo locationInfo = LocationInfo.get(location);
            if (locationInfo != null && this.allowedId.contains(locationInfo.getId())) {
                String value = RouteShow.HELPER.getOrDefaultValue(locationInfo.getConfig());
                value = RouteShow.HELPER.nextOrDefaultValue(value);
                RouteShow.HELPER.setOrClearValue(locationInfo.getConfig(), value);
            }
        }
    }

    @Override
    int getThreshold() {
        return this.threshold;
    }

    @Override
    int getInterval() {
        return this.interval;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipeWithBorder(FinalTechChanged.getLanguageManager(), this);

        for (String id : this.allowedId) {
            SlimefunItem slimefunItem = SlimefunItem.getById(id);
            if (slimefunItem != null) {
                this.registerDescriptiveRecipe(slimefunItem.getItem());
            }
        }
    }
}
