package io.taraxacum.finaltech.core.item.tool;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ToolUseHandler;
import io.taraxacum.finaltech.core.item.AbstractMySlimefunItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
 
public abstract class UltimateTool extends AbstractMySlimefunItem {
    private final Map<Player, Long> timeMap = new HashMap<>();
    private final long intervalThreshold = ConfigUtil.getOrDefaultItemSetting(20, this, "interval-threshold");

    public UltimateTool(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.addItemHandler((ToolUseHandler) (blockBreakEvent, itemStack, fortune, drops) -> {
            blockBreakEvent.setDropItems(false);
            Player player = blockBreakEvent.getPlayer();
            Long lastTime = this.timeMap.get(player);
            long nowTime = System.currentTimeMillis();
            if (lastTime != null && nowTime - lastTime < this.intervalThreshold) {
                return;
            }
            this.timeMap.put(player, nowTime);

            int count = 1;
            for (ItemStack drop : drops) {
                count += drop.getAmount();
            }
            count = (int) Math.pow(count, 0.5);
            blockBreakEvent.setExpToDrop(blockBreakEvent.getExpToDrop() + count);
        });
    }
}
