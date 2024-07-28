package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.plugin.util.VectorUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
 
public class StaffElementalLine extends UsableSlimefunItem implements RecipeItem {
    private final double shortRange = ConfigUtil.getOrDefaultItemSetting(4, this, "short-range");
    private final double longRange = ConfigUtil.getOrDefaultItemSetting(16, this, "long-range");

    private final int interval = ConfigUtil.getOrDefaultItemSetting(500, this, "interval");
    private final int threshold = ConfigUtil.getOrDefaultItemSetting(2, this, "threshold");

    public StaffElementalLine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        playerRightClickEvent.cancel();
        Player player = playerRightClickEvent.getPlayer();
        Location playerLocation = player.getEyeLocation();
        Location targetLocation = player.getLocation();
        boolean teleport = false;
        RayTraceResult rayTraceResult;
        if (player.isSneaking()) {
            rayTraceResult = player.rayTraceBlocks(this.longRange);
            if (rayTraceResult != null) {
                Block hitBlock = rayTraceResult.getHitBlock();
                BlockFace hitBlockFace = rayTraceResult.getHitBlockFace();
                if (hitBlock != null && hitBlockFace != null) {
                    Block targetBlock = hitBlock.getRelative(hitBlockFace);
                    targetLocation = LocationUtil.getCenterLocation(targetBlock);
                    targetLocation.setY(targetLocation.getY() - player.getEyeHeight());
                    teleport = true;
                } else if (rayTraceResult.getHitEntity() != null) {
                    Entity hitEntity = rayTraceResult.getHitEntity();
                    targetLocation = hitEntity.getLocation();
                    teleport = true;
                }
            } else {
                Vector vector = VectorUtil.toLength(VectorUtil.fromYawPitch(playerLocation.getYaw(), playerLocation.getPitch()), this.longRange);
                targetLocation.add(vector);

                playerLocation.add(VectorUtil.toLength(VectorUtil.getTrueRandom(), FinalTechChanged.getRandom().nextDouble() * this.longRange / 2 + this.longRange / 2));
            }
        } else {
            rayTraceResult = player.rayTraceBlocks(this.shortRange);
            if (rayTraceResult != null) {
                targetLocation = rayTraceResult.getHitPosition().toLocation(player.getWorld());
                targetLocation.setY(targetLocation.getY() - player.getEyeHeight());
                teleport = true;
            } else {
                Vector vector = VectorUtil.toLength(VectorUtil.fromYawPitch(playerLocation.getYaw(), playerLocation.getPitch()), this.shortRange);
                targetLocation.add(vector);
                teleport = true;
            }
        }
        if (teleport) {
            targetLocation.setPitch(playerLocation.getPitch());
            targetLocation.setYaw(playerLocation.getYaw());
            if (!targetLocation.getWorld().getBlockAt(targetLocation).getType().isAir()) {
                targetLocation.setY(Math.ceil(targetLocation.getY()));
            }
            if (!targetLocation.getWorld().getBlockAt(new Location(targetLocation.getWorld(), targetLocation.getBlockX() + 0.5, targetLocation.getBlockY() + 0.5, targetLocation.getBlockZ() + 0.5)).getType().isAir()) {
                targetLocation.setY(Math.ceil(targetLocation.getY() + 0.1));
            }
            boolean sprinting = player.isSprinting();
            Vector velocity = player.getVelocity().clone();
            player.teleport(targetLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
            player.setVelocity(velocity);
            player.setSprinting(sprinting);
        }
        targetLocation.setY(targetLocation.getY() + player.getEyeHeight());

        final Location finalTargetLocation = targetLocation;

        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
        javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(this.getAddon().getJavaPlugin(), Particle.WAX_OFF, 0, 0.1, playerLocation, finalTargetLocation));
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.shortRange),
                String.valueOf(this.longRange));
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
