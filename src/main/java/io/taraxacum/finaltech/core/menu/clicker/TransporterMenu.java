package io.taraxacum.finaltech.core.menu.clicker;

import io.taraxacum.finaltech.core.item.machine.clicker.AbstractClickerMachine;
import io.taraxacum.finaltech.util.LocationUtil;
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
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public class TransporterMenu extends AbstractClickerMenu {
    private static final int[] BORDER = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[0];
    private static final int[] OUTPUT_SLOT = new int[0];

    private final int range;

    public TransporterMenu(@Nonnull AbstractClickerMachine slimefunItem, int range) {
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
        BlockData blockData = block.getState().getBlockData();
        List<Block> blockList = new ArrayList<>();
        if (blockData instanceof Directional) {
            BlockFace blockFace = ((Directional) blockData).getFacing();
            Block targetBlock = block;

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
                    return;
                }
            }
        }

        blockMenu.close();
    }
}
