package io.taraxacum.finaltech.core.menu.clicker;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.helper.Icon;
import io.taraxacum.finaltech.core.item.machine.clicker.AbstractClickerMachine;
import io.taraxacum.finaltech.core.item.machine.clicker.AreaAccessor;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import io.taraxacum.libs.slimefun.util.SfItemUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public class AreaAccessorMenu extends AbstractClickerMenu {
    private static final int[] BORDER = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[0];
    private static final int[] OUTPUT_SLOT = new int[0];

    private static final int[] TEMP_CONTENT = new int[]{0, 1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 18, 19, 20, 21, 22, 23, 24, 27, 28, 29, 30, 31, 32, 33, 36, 37, 38, 39, 40, 41, 42, 45, 46, 47, 48, 49, 50, 51};
    private static final int[] TEMP_BORDER = new int[]{7, 16, 25, 34, 43, 52};
    private static final int[] TEMP_PREVIOUS_PAGE = new int[]{8, 17, 26};
    private static final int[] TEMP_NEXT_PAGE = new int[]{35, 44, 53};

    private final int range;

    public AreaAccessorMenu(@Nonnull AbstractClickerMachine slimefunItem, int range) {
        super(slimefunItem);
        this.range = range;
    }

    @Override
    public void init() {
        super.init();
        this.setSize(54);
    }

    @Override
    protected void doFunction(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull Player player) {
        this.generateMenu(player, block.getLocation(), this.range, 0);
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

    /**
     * @param page begin from 0
     */
    // TODO async
    public void generateMenu(@Nonnull Player player, @Nonnull Location location, int range, int page) {
        World world = location.getWorld();
        if (world == null) {
            return;
        }
        BlockStorage storage = BlockStorage.getStorage(world);
        if (storage == null) {
            return;
        }

        Map<Integer, List<Location>> distanceLocationMap = new HashMap<>(range * 3);
        Location tempLocation = location.clone();

        int minX = location.getBlockX() - range;
        int minY = Math.max(location.getBlockY() - range, world.getMinHeight());
        int minZ = location.getBlockZ() - range;
        int maxX = location.getBlockX() + range;
        int maxY = Math.min(location.getBlockY() + range, world.getMaxHeight());
        int maxZ = location.getBlockZ() + range;
        for (int x = minX; x <= maxX; x++) {
            tempLocation.setX(x);
            for (int y = minY; y <= maxY; y++) {
                tempLocation.setY(y);
                for (int z = minZ; z <= maxZ; z++) {
                    tempLocation.setZ(z);
                    if (BlockStorage.hasBlockInfo(tempLocation) && storage.hasInventory(tempLocation)) {
                        int distance = Math.abs(tempLocation.getBlockX() - location.getBlockX()) + Math.abs(tempLocation.getBlockY() - location.getBlockY()) + Math.abs(tempLocation.getBlockZ() - location.getBlockZ());
                        List<Location> locationList = distanceLocationMap.computeIfAbsent(distance, d -> new ArrayList<>(d * d * 4 + 2));
                        locationList.add(tempLocation.clone());
                    }
                }
            }
        }

        List<Location> locationList = new ArrayList<>();
        for (int i = 0; i < range * 3; i++) {
            if (distanceLocationMap.containsKey(i)) {
                locationList.addAll(distanceLocationMap.get(i));
            }
        }

        ChestMenu chestMenu = new ChestMenu(" ");
        for (int i = 0; i < TEMP_CONTENT.length; i++) {
            if (i + page * TEMP_CONTENT.length >= locationList.size()) {
                chestMenu.addItem(TEMP_CONTENT[i], Icon.BORDER_ICON);
                chestMenu.addMenuClickHandler(TEMP_CONTENT[i], ChestMenuUtils.getEmptyClickHandler());
                continue;
            }
            Location l = locationList.get((i + page * TEMP_CONTENT.length) % locationList.size());
            LocationInfo locationInfo = LocationInfo.get(l);
            if (locationInfo != null) {
                BlockMenu blockMenu = BlockStorage.getInventory(l);
                if (blockMenu != null) {
                    ItemStack icon = new CustomItemStack(locationInfo.getSlimefunItem().getItem(), locationInfo.getSlimefunItem().getItemName(), FinalTechChanged.getLanguageManager().replaceStringArray(FinalTechChanged.getLanguageStringArray("items", SfItemUtil.getIdFormatName(AreaAccessor.class), "temp-icon", "lore"),
                            String.valueOf(l.getBlockX() - location.getBlockX()),
                            String.valueOf(l.getBlockY() - location.getBlockY()),
                            String.valueOf(l.getBlockZ() - location.getBlockZ())));
                    chestMenu.addItem(TEMP_CONTENT[i], icon);
                    chestMenu.addMenuClickHandler(TEMP_CONTENT[i], (p, slot, item, action) -> {
                        // BlockMenu may be updated after the menu generated.
                        if (BlockStorage.hasBlockInfo(l) && BlockStorage.hasInventory(l.getBlock()) && blockMenu.canOpen(l.getBlock(), player)) {
                            JavaPlugin javaPlugin = AreaAccessorMenu.this.getSlimefunItem().getAddon().getJavaPlugin();
                            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, blockMenu.getBlock()));
                            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(javaPlugin, Particle.WAX_OFF, 0, 0.25, LocationUtil.getCenterLocation(location.getBlock()), LocationUtil.getCenterLocation(blockMenu.getBlock())));
                            if (action.isRightClicked()) {
                                player.closeInventory();
                            } else {
                                blockMenu.open(player);
                            }
                        } else {
                            player.sendMessage(FinalTechChanged.getLanguageString("items", SfItemUtil.getIdFormatName(AreaAccessor.class), "message", "no-permission", "location"));
                        }
                        return false;
                    });
                    continue;
                }
            }
            chestMenu.addItem(TEMP_CONTENT[i], Icon.ERROR_ICON);
            chestMenu.addMenuClickHandler(TEMP_CONTENT[i], ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : TEMP_BORDER) {
            chestMenu.addItem(slot, Icon.BORDER_ICON);
            chestMenu.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : TEMP_NEXT_PAGE) {
            chestMenu.addItem(slot, Icon.NEXT_PAGE_ICON);
            chestMenu.addMenuClickHandler(slot, (p, slot1, item, action) -> {
                AreaAccessorMenu.this.generateMenu(player, location, range, Math.min(page + 1, locationList.size() / TEMP_CONTENT.length));
                return false;
            });
        }
        for (int slot : TEMP_PREVIOUS_PAGE) {
            chestMenu.addItem(slot, Icon.PREVIOUS_PAGE_ICON);
            chestMenu.addMenuClickHandler(slot, (p, slot1, item, action) -> {
                AreaAccessorMenu.this.generateMenu(player, location, range, Math.max(page - 1, 0));
                return false;
            });
        }
        chestMenu.open(player);
    }
}
