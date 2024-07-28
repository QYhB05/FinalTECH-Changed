package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.event.ConfigSaveActionEvent;
import io.taraxacum.finaltech.core.helper.Icon;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.listener.ConfigSaveListener;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.ItemConfigurationUtil;
import io.taraxacum.finaltech.util.PermissionUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class MachineConfigurator extends UsableSlimefunItem implements RecipeItem {
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));

    public MachineConfigurator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);
        if (!this.isDisabled()) {
            PluginManager pluginManager = addon.getJavaPlugin().getServer().getPluginManager();
            pluginManager.registerEvents(new ConfigSaveListener(), addon.getJavaPlugin());
        }
    }

    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        playerRightClickEvent.cancel();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        Optional<Block> clickedBlock = playerRightClickEvent.getClickedBlock();
        if (clickedBlock.isPresent()) {
            Block block = clickedBlock.get();
            Location location = block.getLocation();
            LocationInfo locationInfo = LocationInfo.get(location);
            if (locationInfo != null && !this.notAllowedId.contains(locationInfo.getId()) && PermissionUtil.checkPermission(playerRightClickEvent.getPlayer(), location, Interaction.BREAK_BLOCK, Interaction.INTERACT_BLOCK, Interaction.PLACE_BLOCK)) {
                ItemStack itemStack = playerRightClickEvent.getItem();
                if (playerRightClickEvent.getPlayer().isSneaking()) {
                    // save data

                    if (ItemConfigurationUtil.saveConfigToItem(itemStack, locationInfo.getLocation())) {
                        ItemStackUtil.setLore(itemStack, locationInfo.getSlimefunItem().getItemName());

                        javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));
                    }
                } else {
                    // load data

                    if (ItemConfigurationUtil.loadConfigFromItem(itemStack, location)) {
                        FinalTechChanged.getInstance().getServer().getPluginManager().callEvent(new ConfigSaveActionEvent(false, location, locationInfo.getId()));

                        javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));
                    }
                }
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipeWithBorder(FinalTechChanged.getLanguageManager(), this);

        for (Map.Entry<String, Set<String>> entry : ItemConfigurationUtil.getGroupItemMap().entrySet()) {
            for (String id : entry.getValue()) {
                SlimefunItem slimefunItem = SlimefunItem.getById(id);
                if (slimefunItem != null) {
                    this.registerDescriptiveRecipe(slimefunItem.getItem());
                }
            }
            this.registerDescriptiveRecipe(Icon.BORDER_ICON);
        }
    }
}
