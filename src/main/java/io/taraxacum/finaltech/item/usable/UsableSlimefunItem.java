package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.taraxacum.finaltech.core.item.AbstractMySlimefunItem;
import io.taraxacum.finaltech.util.MachineUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * An {@link SlimefunItem} that can be used by player to do some #function.
 *
 * @author Final_ROOT
 * @since 2.0
 */
// TODO: Optimization
public abstract class UsableSlimefunItem extends AbstractMySlimefunItem {
    private final Map<Player, List<Long>> intervalThresholdMap = new HashMap<>();

    public UsableSlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.addItemHandler(MachineUtil.BLOCK_PLACE_HANDLER_DENY);
        this.addItemHandler((ItemUseHandler) UsableSlimefunItem.this::doFunction);
    }

    private void doFunction(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        Player player = playerRightClickEvent.getPlayer();
        if (this.getInterval() > 0 && this.getThreshold() > 0) {
            List<Long> timeList = this.intervalThresholdMap.getOrDefault(player, new ArrayList<>(this.getThreshold()));
            Iterator<Long> iterator = timeList.iterator();
            long nowTime = System.currentTimeMillis();
            while (iterator.hasNext()) {
                Long time = iterator.next();
                if (nowTime - time > this.getInterval()) {
                    iterator.remove();
                }
            }
            if (timeList.size() < this.getThreshold()) {
                timeList.add(nowTime);
                this.intervalThresholdMap.put(player, timeList);
                this.function(playerRightClickEvent);
            } else {
                this.errorFunction(playerRightClickEvent);
            }
        } else {
            this.function(playerRightClickEvent);
        }

    }

    /**
     * The function the item will do
     * while a player hold the item and right click.
     *
     * @param playerRightClickEvent
     */
    protected abstract void function(@Nonnull PlayerRightClickEvent playerRightClickEvent);

    void errorFunction(@Nonnull PlayerRightClickEvent playerRightClickEvent) {

    }

    int getThreshold() {
        return 1;
    }

    int getInterval() {
        return 0;
    }
}
