package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.event.EnergyDepositEvent;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.PermissionUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Final_ROOT
 * @version 2.4
 */
public class EnergyCard extends UsableSlimefunItem implements RecipeItem {
    public static final Map<String, EnergyCard> ENERGY_CARD_MAP = new LinkedHashMap<>();
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final String energy;

    public EnergyCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, String energy) {
        super(itemGroup, item, recipeType, recipe);
        if (ENERGY_CARD_MAP.containsKey(energy)) {
            throw new IllegalArgumentException("duplicated energy while registering " + this.getId());
        }
        ENERGY_CARD_MAP.put(energy, this);

        this.energy = energy;
    }

    @Nullable
    public static EnergyCard getByEnergy(@Nonnull String energy) {
        String targetEnergy = null;
        for (Map.Entry<String, EnergyCard> entry : ENERGY_CARD_MAP.entrySet()) {
            if (StringNumberUtil.compare(energy, entry.getKey()) >= 0) {
                if (targetEnergy == null || StringNumberUtil.compare(targetEnergy, energy) <= 0) {
                    targetEnergy = entry.getKey();
                }
            }
        }

        return ENERGY_CARD_MAP.get(targetEnergy);
    }

    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        playerRightClickEvent.cancel();

        Block block = playerRightClickEvent.getInteractEvent().getClickedBlock();
        if (block == null) {
            return;
        }

        Player player = playerRightClickEvent.getPlayer();
        if (player.isDead()) {
            return;
        }

        Location location = block.getLocation();
        LocationInfo locationInfo = LocationInfo.get(location);

        if (locationInfo == null || this.notAllowedId.contains(locationInfo.getId())) {
            return;
        }

        if (!PermissionUtil.checkPermission(player, location, Interaction.INTERACT_BLOCK, Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK)) {
            player.sendRawMessage(FinalTechChanged.getLanguageString("message", "no-permission", "location"));
            return;
        }

        if (locationInfo.getSlimefunItem() instanceof EnergyNetComponent energyNetComponent && energyNetComponent.isChargeable()) {
            JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(this.getAddon().getJavaPlugin(), Particle.WAX_OFF, 0, block));

            EnergyDepositEvent energyDepositEvent = new EnergyDepositEvent(location, this.energy);
            this.getAddon().getJavaPlugin().getServer().getPluginManager().callEvent(energyDepositEvent);
            String transferEnergy = energyDepositEvent.getEnergy();

            int capacity = energyNetComponent.getCapacity();
            String energyStr = EnergyUtil.getCharge(locationInfo.getConfig());
            int energy = Integer.parseInt(energyStr);
            if (energy < capacity) {
                energyStr = StringNumberUtil.min(StringNumberUtil.add(transferEnergy, energyStr), String.valueOf(capacity));
                EnergyUtil.setCharge(locationInfo.getLocation(), energyStr);

                ItemStack itemStack = playerRightClickEvent.getItem();
                itemStack.setAmount(itemStack.getAmount() - 1);
            }
        }
    }

    public String getEnergy() {
        return energy;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                this.energy);
    }
}
