package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;
 
public class PotionEffectPurifier extends UsableSlimefunItem implements RecipeItem {
    private final double horizontalRange = ConfigUtil.getOrDefaultItemSetting(16, this, "horizontal-range");
    private final double verticalRange = ConfigUtil.getOrDefaultItemSetting(8, this, "vertical-range");

    public PotionEffectPurifier(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        if (player.isSneaking()) {
            Location location = player.getLocation();
            World world = location.getWorld();
            // TODO particle effect
            if (world != null) {
                for (Entity entity : world.getNearbyEntities(location, this.horizontalRange, this.verticalRange, this.horizontalRange, entity -> entity instanceof LivingEntity)) {
                    for (PotionEffect potionEffect : ((LivingEntity) entity).getActivePotionEffects()) {
                        ((LivingEntity) entity).removePotionEffect(potionEffect.getType());
                    }
                }
            }
            ItemStack item = playerRightClickEvent.getItem();
            item.setAmount(item.getAmount() - 1);
        } else {
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.horizontalRange),
                String.valueOf(this.verticalRange));
    }
}
