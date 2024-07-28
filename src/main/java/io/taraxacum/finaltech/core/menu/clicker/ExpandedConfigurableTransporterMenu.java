package io.taraxacum.finaltech.core.menu.clicker;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.core.interfaces.DigitalItem;
import io.taraxacum.finaltech.core.item.machine.clicker.AbstractClickerMachine;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public class ExpandedConfigurableTransporterMenu extends AbstractClickerMenu {
    private static final int[] BORDER = new int[]{0, 1, 2, 4, 6, 7, 8};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[]{3, 5};
    private static final int[] OUTPUT_SLOT = new int[]{3, 5};

    private final int range;

    public ExpandedConfigurableTransporterMenu(@Nonnull AbstractClickerMachine slimefunItem, int range) {
        super(slimefunItem);
        this.range = range;
    }

    @Override
    protected int[] getBorder() {
        return BORDER;
    }

    @Override
    protected int[] getInputBorder() {
        return INPUT_BORDER;
    }

    @Override
    protected int[] getOutputBorder() {
        return OUTPUT_BORDER;
    }

    @Override
    public int[] getInputSlot() {
        return INPUT_SLOT;
    }

    @Override
    public int[] getOutputSlot() {
        return OUTPUT_SLOT;
    }

    @Override
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }

    @Override
    protected void doFunction(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull Player player) {
        int digit = -1;
        ItemStack[] digitItems = new ItemStack[INPUT_SLOT.length];
        for (int i = 0; i < INPUT_SLOT.length; i++) {
            if (i == 0) {
                digit = 0;
            } else {
                digit *= 16;
            }

            ItemStack item = blockMenu.getItemInSlot(INPUT_SLOT[i]);
            if (ItemStackUtil.isItemNull(item)) {
                digit = -1;
                break;
            }
            SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
            if (!(slimefunItem instanceof DigitalItem digitalItem)) {
                digit = -1;
                break;
            }
            digit += digitalItem.getDigit();
            digitItems[i] = item;
        }


        if (digit != -1) {
            blockMenu.close();

            BlockData blockData = block.getState().getBlockData();
            List<Block> blockList = new ArrayList<>();
            if (blockData instanceof Directional) {
                BlockFace blockFace = ((Directional) blockData).getFacing();
                Block targetBlock = block;

                if (digit > 0) {
                    for (int i = 0; i < digit; i++) {
                        targetBlock = targetBlock.getRelative(blockFace);
                    }
                    blockList.add(targetBlock);

                    if (targetBlock.getType().isAir()) {
                        JavaPlugin javaPlugin = this.getSlimefunItem().getAddon().getJavaPlugin();
                        javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, blockList));

                        Location sourceLocation = player.getLocation().clone();
                        Location targetLocation = LocationUtil.getCenterLocation(targetBlock);
                        targetLocation.setYaw(sourceLocation.getYaw());
                        targetLocation.setPitch(sourceLocation.getPitch());
                        player.teleport(targetLocation);
                    }
                } else if (digit == 0) {
                    for (int i = 0; i < this.range; i++) {
                        targetBlock = targetBlock.getRelative(blockFace);
                        blockList.add(targetBlock);
                        if (targetBlock.getType().isAir()) {
                            JavaPlugin javaPlugin = this.getSlimefunItem().getAddon().getJavaPlugin();
                            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, blockList));

                            Location sourceLocation = player.getLocation().clone();
                            Location targetLocation = LocationUtil.getCenterLocation(targetBlock);
                            targetLocation.setYaw(sourceLocation.getYaw());
                            targetLocation.setPitch(sourceLocation.getPitch());
                            player.teleport(targetLocation);
                            break;
                        }
                    }
                }
                return;
            }
        }

        JavaPlugin javaPlugin = this.getSlimefunItem().getAddon().getJavaPlugin();
        javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, block));
    }
}
