package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.finaltech.util.PermissionUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.plugin.util.PlayerUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
 
public class LocationRecorder extends UsableSlimefunItem implements RecipeItem {
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));

    public LocationRecorder(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        playerRightClickEvent.cancel();

        PlayerInteractEvent interactEvent = playerRightClickEvent.getInteractEvent();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        if (playerRightClickEvent.getPlayer().isSneaking()) {
            Block block = interactEvent.getClickedBlock();
            if (block != null) {
                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));
                LocationInfo locationInfo = LocationInfo.get(block.getLocation());
                if (locationInfo != null && !this.notAllowedId.contains(locationInfo.getId()) && PermissionUtil.checkPermission(playerRightClickEvent.getPlayer(), block.getLocation(), Interaction.INTERACT_BLOCK, Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK)) {
                    ItemStack item = playerRightClickEvent.getItem();
                    LocationUtil.saveLocationToItem(item, block.getLocation());
                    LocationUtil.updateLocationItem(item);
                    PlayerUtil.updateIdInItem(item, playerRightClickEvent.getPlayer(), true);
                }
            }
        } else {
            Location location = LocationUtil.parseLocationInItem(playerRightClickEvent.getItem());
            if (location == null) {
                return;
            }

            Block block = location.getBlock();
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));

            Player player = playerRightClickEvent.getPlayer();
            if (!PermissionUtil.checkPermission(playerRightClickEvent.getPlayer(), location, Interaction.INTERACT_BLOCK, Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK)) {
                player.sendRawMessage(FinalTechChanged.getLanguageString("message", "no-permission", "location"));
                return;
            }

            if (BlockStorage.hasInventory(block)) {
                BlockMenu blockMenu = BlockStorage.getInventory(block);
                if (blockMenu.canOpen(block, player)) {
                    blockMenu.open(player);
                } else {
                    player.sendRawMessage(FinalTechChanged.getLanguageString("message", "no-permission", "location"));
                }
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this);
    }
}
