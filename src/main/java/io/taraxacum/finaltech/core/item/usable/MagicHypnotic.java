package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.task.effect.VoidCurse;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.task.TaskTicker;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;

public class MagicHypnotic extends UsableSlimefunItem implements RecipeItem {
    private final int interval = ConfigUtil.getOrDefaultItemSetting(500, this, "interval");
    private final int threshold = ConfigUtil.getOrDefaultItemSetting(2, this, "threshold");

    public MagicHypnotic(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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

        Player player = playerRightClickEvent.getPlayer();
        PotionEffectType[] allPotionEffectType = PotionEffectType.values();
        PotionEffectType randomPotionEffectType = allPotionEffectType[FinalTechChanged.getRandom().nextInt(allPotionEffectType.length)];
        boolean hasPotionEffect = false;
        int time = 0;
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            if (potionEffect.getType().equals(randomPotionEffectType)) {
                hasPotionEffect = true;
                player.removePotionEffect(potionEffect.getType());
                player.addPotionEffect(new PotionEffect(potionEffect.getType(), potionEffect.getDuration() + player.getLevel() + 1, potionEffect.getAmplifier()));
                break;
            }
            time += potionEffect.getDuration();
        }
        if (!hasPotionEffect) {
            player.addPotionEffect(new PotionEffect(randomPotionEffectType, player.getLevel() + 1, 0));
        }

        if (FinalTechChanged.getRandom().nextDouble() > (double) player.getActivePotionEffects().size() / PotionEffectType.values().length) {
            TaskTicker.applyOrAddTo(new VoidCurse(time, 1), player, LivingEntity.class);
        }

        ItemStack item = playerRightClickEvent.getItem();
        item.setAmount(item.getAmount() - 1);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this);
    }

    @Override
    int getThreshold() {
        return this.threshold;
    }

    @Override
    int getInterval() {
        return this.interval;
    }
}
