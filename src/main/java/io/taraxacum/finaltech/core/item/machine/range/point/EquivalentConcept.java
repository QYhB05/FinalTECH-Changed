package io.taraxacum.finaltech.core.item.machine.range.point;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.*;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.interfaces.SimpleValidItem;
import io.taraxacum.libs.slimefun.util.SfItemUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
 
public class EquivalentConcept extends AbstractPointMachine implements RecipeItem, SimpleValidItem {
    public static final String KEY_LIFE = "l";
    public static final String KEY_RANGE = "r";
    private final double attenuationRate = ConfigUtil.getOrDefaultItemSetting(0.95, this, "attenuation-rate");
    private final double life = ConfigUtil.getOrDefaultItemSetting(4.0, this, "life");
    private final int range = ConfigUtil.getOrDefaultItemSetting(2, this, "range");

    private final ItemWrapper templateValidItem;

    public EquivalentConcept(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        ItemStack validItem = new ItemStack(this.getItem());
        SfItemUtil.setSpecialItemKey(validItem);
        this.templateValidItem = new ItemWrapper(validItem);

        this.addItemHandler(new ItemUseHandler() {
            @Override
            @EventHandler(priority = EventPriority.LOWEST)
            public void onRightClick(PlayerRightClickEvent e) {
                e.cancel();
            }
        });
        this.addItemHandler(new WeaponUseHandler() {
            @Override
            @EventHandler(priority = EventPriority.LOWEST)
            public void onHit(@Nonnull EntityDamageByEntityEvent e, @Nonnull Player player, @Nonnull ItemStack item) {
                e.setCancelled(true);
            }
        });
        this.addItemHandler(new EntityInteractHandler() {
            @Override
            @EventHandler(priority = EventPriority.LOWEST)
            public void onInteract(PlayerInteractEntityEvent e, ItemStack item, boolean offHand) {
                e.setCancelled(true);
            }
        });
        this.addItemHandler(new ToolUseHandler() {
            @Override
            @EventHandler(priority = EventPriority.LOWEST)
            public void onToolUse(BlockBreakEvent e, ItemStack tool, int fortune, List<ItemStack> drops) {
                e.setCancelled(true);
            }
        });
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler();
    }

    @Nonnull
    @Override
    public Collection<ItemStack> getDrops() {
        ArrayList<ItemStack> drops = new ArrayList<>();
        drops.add(this.getValidItem());
        return drops;
    }

    @Nullable
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        // this is the only
        return null;
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        try  {
            if (FinalTechChanged.y) {
                FinalTechChanged.getInstance().getServer().getScheduler().runTask(FinalTechChanged.getInstance(), () -> block.setType(Material.AIR));
                BlockStorage.clearBlockInfo(block.getLocation());
                return ;
            }
            if (BlockStorage.getLocationInfo(block.getLocation(), ConstantTableUtil.CONFIG_SLEEP) != null) {
                String sleepStr = BlockStorage.getLocationInfo(block.getLocation(), ConstantTableUtil.CONFIG_SLEEP);
                if (sleepStr != null) {
                    double sleep = Double.parseDouble(sleepStr) - 1;
                    if (sleep > 0) {
                        BlockStorage.addBlockInfo(block.getLocation(), ConstantTableUtil.CONFIG_SLEEP, String.valueOf(sleep));
                        return;
                    } else {
                        BlockStorage.addBlockInfo(block.getLocation(), ConstantTableUtil.CONFIG_SLEEP, String.valueOf(0));

                    }
                }
            }
            Location l = block.getLocation();
            double life = (BlockStorage.getLocationInfo(block.getLocation(), KEY_LIFE) != null) ? Double.parseDouble(BlockStorage.getLocationInfo(l, KEY_LIFE)) : 0;
            if (life < 1) {
                Location location = block.getLocation();
                BlockStorage.addBlockInfo(location, KEY_LIFE, "0");
                BlockStorage.addBlockInfo(location, KEY_RANGE, "0");
                BlockStorage.addBlockInfo(l, ConstantTableUtil.CONFIG_SLEEP, "0");
                BlockStorage.clearBlockInfo(location);
                JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
                javaPlugin.getServer().getScheduler().runTaskLaterAsynchronously(javaPlugin, () -> {
                    if (!location.getBlock().getType().isAir() && BlockStorage.getLocationInfo(location, ConstantTableUtil.CONFIG_ID) == null) {
                        BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_ID, FinalTechItemStacks.JUSTIFIABILITY.getItemId(), true);
                    }
                }, Slimefun.getTickerTask().getTickRate() + 1);
                return;
            }

            final int range = (BlockStorage.getLocationInfo(block.getLocation(), KEY_RANGE) != null) ? Integer.parseInt(BlockStorage.getLocationInfo(l, KEY_RANGE)) : this.range;

            while (life > 1) {
                final double finalLife = life--;
                this.pointFunction(block, range, location -> {
                    FinalTechChanged.getLocationRunnableFactory().waitThenRun(() -> {
                        Block targetBlock = location.getBlock();
                        if (!BlockStorage.hasBlockInfo(location)) {
                            if (targetBlock.getType() == Material.AIR) {
                                BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_ID, EquivalentConcept.this.getId(), true);
                                BlockStorage.addBlockInfo(location, KEY_LIFE, String.valueOf(finalLife * attenuationRate));
                                BlockStorage.addBlockInfo(location, KEY_RANGE, String.valueOf(range + 1));
                                BlockTickerUtil.setSleep(BlockStorage.getLocationInfo(location), String.valueOf(EquivalentConcept.this.life - finalLife));
                                JavaPlugin javaPlugin = EquivalentConcept.this.getAddon().getJavaPlugin();
                                javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> targetBlock.setType(EquivalentConcept.this.getItem().getType()));
                            }
                        }
                    }, location);
                    return 0;
                });
            }

            BlockStorage.addBlockInfo(block, KEY_LIFE, String.valueOf(0));
        } catch (Exception e) {
            FinalTechChanged.getInstance().getLogger().warning("[FINALTECH] 物品 等概念体 出现了异常, 但不要担心这是正常情况");
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    @Override
    public Location getTargetLocation(@Nonnull Location location, int range) {
        int y = location.getBlockY() - range + FinalTechChanged.getRandom().nextInt(range + range);
        y = Math.min(location.getWorld().getMaxHeight(), y);
        y = Math.max(location.getWorld().getMinHeight(), y);
        return new Location(location.getWorld(), location.getX() - range + FinalTechChanged.getRandom().nextInt(range + range), y, location.getZ() - range + new Random().nextInt(range + range));
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this);
    }

    @Nonnull
    @Override
    public ItemStack getValidItem() {
        return ItemStackUtil.cloneItem(this.templateValidItem.getItemStack());
    }

    @Override
    public boolean verifyItem(@Nonnull ItemStack itemStack) {
        return ItemStackUtil.isItemSimilar(itemStack, this.templateValidItem);
    }
}
