package io.taraxacum.finaltech.core.item.usable.machine;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.item.usable.UsableSlimefunItem;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.finaltech.util.PermissionUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public abstract class AbstractMachineActivateCard extends UsableSlimefunItem {
    public AbstractMachineActivateCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
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
        if (!BlockStorage.hasBlockInfo(location)) {
            return;
        }

        if (BlockStorage.getLocationInfo(location, ConstantTableUtil.CONFIG_ID) == null) {
            return;
        }

        if (!PermissionUtil.checkPermission(player, location, Interaction.INTERACT_BLOCK, Interaction.BREAK_BLOCK, Interaction.PLACE_BLOCK)) {
            player.sendRawMessage(FinalTechChanged.getLanguageString("message", "no-permission", "location"));
            return;
        }

        if (BlockStorage.hasInventory(block)) {
            BlockMenu blockMenu = BlockStorage.getInventory(location);
            if (!blockMenu.canOpen(block, player)) {
                player.sendRawMessage(FinalTechChanged.getLanguageString("message", "no-permission", "location"));
                return;
            }
        }

        if (!this.conditionMatch(player)) {
            player.sendRawMessage(FinalTechChanged.getLanguageString("message", "no-condition", "player"));
            return;
        }

        SlimefunItem slimefunItem = SlimefunItem.getById(BlockStorage.getLocationInfo(location, ConstantTableUtil.CONFIG_ID));
        if (slimefunItem == null) {
            return;
        }
        BlockTicker blockTicker = slimefunItem.getBlockTicker();

        if (blockTicker != null && FinalTechChanged.isAntiAccelerateSlimefunItem(slimefunItem.getId())) {
            return;
        }

        if (this.consume()) {
            if (playerRightClickEvent.getItem().getAmount() > 0) {
                ItemStack item = playerRightClickEvent.getItem();
                item.setAmount(item.getAmount() - 1);
            } else {
                return;
            }
        }

        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        if (blockTicker != null && slimefunItem instanceof EnergyNetComponent energyNetComponent) {
            int time;
            if (this.consume()) {
                time = this.times();
            } else {
                time = this.times() * playerRightClickEvent.getItem().getAmount();
            }

            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));

            Runnable runnable = () -> {
                int capacity = energyNetComponent.getCapacity();
                int chargeEnergy = (int) AbstractMachineActivateCard.this.energy();
                if (!EnergyNetComponentType.CAPACITOR.equals(energyNetComponent.getEnergyComponentType()) && !EnergyNetComponentType.GENERATOR.equals(energyNetComponent.getEnergyComponentType())) {
                    chargeEnergy += (int) ((this.energy() - (int) this.energy()) * capacity);
                }
                if (!AbstractMachineActivateCard.this.consume()) {
                    chargeEnergy *= playerRightClickEvent.getItem().getAmount();
                }
                for (int i = 0; i < time; i++) {
                    int storedEnergy = energyNetComponent.getCharge(location);
                    storedEnergy = chargeEnergy / 2 + storedEnergy / 2 > Integer.MAX_VALUE / 2 ? Integer.MAX_VALUE : chargeEnergy + storedEnergy;
                    energyNetComponent.setCharge(location, Math.min(capacity, storedEnergy));
                    blockTicker.tick(block, slimefunItem, BlockStorage.getLocationInfo(location));
                }
            };

            if (blockTicker.isSynchronized() || !FinalTechChanged.isAsyncSlimefunItem(slimefunItem.getId())) {
                javaPlugin.getServer().getScheduler().runTask(javaPlugin, runnable);
            } else {
                FinalTechChanged.getLocationRunnableFactory().waitThenRun(runnable, location);
            }
        } else if (blockTicker != null) {
            // this slimefun item have blockTicker
            int time;
            if (this.consume()) {
                time = this.times();
            } else {
                time = this.times() * playerRightClickEvent.getItem().getAmount();
            }

            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));

            Runnable runnable = () -> {
                for (int i = 0; i < time; i++) {
                    blockTicker.tick(block, slimefunItem, BlockStorage.getLocationInfo(location));
                }
            };

            if (blockTicker.isSynchronized() || !FinalTechChanged.isAsyncSlimefunItem(slimefunItem.getId())) {
                javaPlugin.getServer().getScheduler().runTask(javaPlugin, runnable);
            } else {
                FinalTechChanged.getLocationRunnableFactory().waitThenRun(runnable, location);
            }
        } else if (slimefunItem instanceof EnergyNetComponent energyNetComponent) {
            // this slimefun item is energy net component
            if (energyNetComponent.getCapacity() <= 0) {
                return;
            }

            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(this.getAddon().getJavaPlugin(), Particle.WAX_OFF, 0, block));

            int capacity = energyNetComponent.getCapacity();
            int chargeEnergy = (int) AbstractMachineActivateCard.this.energy();
            if (!EnergyNetComponentType.CAPACITOR.equals(energyNetComponent.getEnergyComponentType()) && !EnergyNetComponentType.GENERATOR.equals(energyNetComponent.getEnergyComponentType())) {
                chargeEnergy += (int) ((this.energy() - (int) this.energy()) * capacity);
            }
            if (!this.consume()) {
                chargeEnergy *= playerRightClickEvent.getItem().getAmount();
            }
            int storedEnergy = energyNetComponent.getCharge(location);
            chargeEnergy = chargeEnergy / 2 + storedEnergy / 2 > Integer.MAX_VALUE / 2 ? Integer.MAX_VALUE : chargeEnergy + storedEnergy;
            energyNetComponent.setCharge(location, Math.min(capacity, chargeEnergy));
        }
    }

    protected abstract double energy();

    protected abstract int times();

    /**
     * @return If using it will consume itself
     */
    protected abstract boolean consume();

    /**
     * If it can work.
     * May cost player's health or exp;
     *
     * @param player
     * @return
     */
    protected abstract boolean conditionMatch(@Nonnull Player player);
}
