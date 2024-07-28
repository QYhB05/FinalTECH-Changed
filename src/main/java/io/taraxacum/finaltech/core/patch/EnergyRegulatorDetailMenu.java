package io.taraxacum.finaltech.core.patch;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNet;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.common.util.ReflectionUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.enchantment.NullEnchantment;
import io.taraxacum.finaltech.core.helper.Icon;
import io.taraxacum.finaltech.core.networks.AlteredEnergyNet;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.libs.plugin.dto.LanguageManager;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.slimefun.util.SfItemUtil;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This menu should only be opened by one player at same time.
 *
 * @author Final_ROOT
 * @since 2.4
 */
public class EnergyRegulatorDetailMenu extends ChestMenu {
    private static final int[] CONTENT = new int[]{0, 1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 18, 19, 20, 21, 22, 23, 24, 27, 28, 29, 30, 31, 32, 33, 36, 37, 38, 39, 40, 41, 42, 45, 46, 47, 48, 49, 50, 51};
    private static final int[] PREVIOUS_PAGE = new int[]{7, 16, 25};
    private static final int[] NEXT_PAGE = new int[]{34, 43, 52};
    private static final int[] CONSUMER_SLOT = new int[]{8, 17};
    private static final int[] GENERATOR_SLOT = new int[]{26, 35};
    private static final int[] CAPACITOR_SLOT = new int[]{44, 53};

    private static final String TYPE_CONSUMER = "consumers";
    private static final String TYPE_GENERATOR = "generators";
    private static final String TYPE_CAPACITOR = "capacitors";

    private Set<String> types = new HashSet<>();
    private Location location;
    private int page;
    private BukkitTask refreshTask = null;

    public EnergyRegulatorDetailMenu(@Nonnull Location location) {
        super(SlimefunItems.ENERGY_REGULATOR.getDisplayName());

        this.location = location;

        this.types.add(TYPE_CONSUMER);
        this.types.add(TYPE_GENERATOR);
        this.types.add(TYPE_CAPACITOR);

        this.page = 0;

        for (int slot : CONTENT) {
            this.addItem(slot, Icon.BORDER_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }

        for (int slot : NEXT_PAGE) {
            this.addItem(slot, Icon.NEXT_PAGE_ICON);
            this.addMenuClickHandler(slot, (p, slot1, item, action) -> {
                this.page++;
                this.updateMenu();
                return false;
            });
        }
        for (int slot : PREVIOUS_PAGE) {
            this.addItem(slot, Icon.PREVIOUS_PAGE_ICON);
            this.addMenuClickHandler(slot, (p, slot1, item, action) -> {
                this.page--;
                this.updateMenu();
                return false;
            });
        }

        for (int slot : CONSUMER_SLOT) {
            ItemStack icon = ItemStackUtil.cloneItem(Icon.CONSUMER_ICON);
            NullEnchantment.addAndHidden(icon);
            this.addItem(slot, icon);
            this.addMenuClickHandler(slot, (p, slot1, item, action) -> {
                if (this.types.contains(TYPE_CONSUMER)) {
                    this.types.remove(TYPE_CONSUMER);
                    for (int s : CONSUMER_SLOT) {
                        this.replaceExistingItem(s, ItemStackUtil.cloneItem(Icon.CONSUMER_ICON));
                    }
                } else {
                    this.types.add(TYPE_CONSUMER);
                    for (int s : CONSUMER_SLOT) {
                        ItemStack itemStack = ItemStackUtil.cloneItem(Icon.CONSUMER_ICON);
                        NullEnchantment.addAndHidden(itemStack);
                        this.replaceExistingItem(s, itemStack);
                    }
                }

                this.updateMenu();
                return false;
            });
        }

        for (int slot : GENERATOR_SLOT) {
            ItemStack icon = ItemStackUtil.cloneItem(Icon.GENERATOR_ICON);
            NullEnchantment.addAndHidden(icon);
            this.addItem(slot, icon);
            this.addMenuClickHandler(slot, (p, slot1, item, action) -> {
                if (this.types.contains(TYPE_GENERATOR)) {
                    this.types.remove(TYPE_GENERATOR);
                    for (int s : GENERATOR_SLOT) {
                        this.replaceExistingItem(s, ItemStackUtil.cloneItem(Icon.GENERATOR_ICON));
                    }
                } else {
                    this.types.add(TYPE_GENERATOR);
                    for (int s : GENERATOR_SLOT) {
                        ItemStack itemStack = ItemStackUtil.cloneItem(Icon.GENERATOR_ICON);
                        NullEnchantment.addAndHidden(itemStack);
                        this.replaceExistingItem(s, itemStack);
                    }
                }

                this.updateMenu();
                return false;
            });
        }

        for (int slot : CAPACITOR_SLOT) {
            ItemStack icon = ItemStackUtil.cloneItem(Icon.CAPACITOR_ICON);
            NullEnchantment.addAndHidden(icon);
            this.addItem(slot, icon);
            this.addMenuClickHandler(slot, (p, slot1, item, action) -> {
                if (this.types.contains(TYPE_CAPACITOR)) {
                    this.types.remove(TYPE_CAPACITOR);
                    for (int s : CAPACITOR_SLOT) {
                        this.replaceExistingItem(s, ItemStackUtil.cloneItem(Icon.CAPACITOR_ICON));
                    }
                } else {
                    this.types.add(TYPE_CAPACITOR);
                    for (int s : CAPACITOR_SLOT) {
                        ItemStack itemStack = ItemStackUtil.cloneItem(Icon.CAPACITOR_ICON);
                        NullEnchantment.addAndHidden(itemStack);
                        this.replaceExistingItem(s, itemStack);
                    }
                }

                this.updateMenu();
                return false;
            });
        }

        this.updateMenu();

        this.addMenuCloseHandler((player) -> {
            if (this.refreshTask != null && !this.refreshTask.isCancelled()) {
                this.refreshTask.cancel();
            }
        });

        this.refreshTask = this.startRefreshTask();
    }

    public void updateMenu() {
        Map<Location, EnergyNetComponent> energyNetComponentMap = new HashMap<>();

        Map<Location, Long> generatedEnergyMap = new HashMap<>();
        Map<Location, Long> consumedEnergyMap = new HashMap<>();

        EnergyNet energyNet = EnergyNet.getNetworkFromLocation(this.location);
        if (energyNet != null) {
            try {
                for (String energyNetComponentType : this.types) {
                    Map<Location, EnergyNetComponent> componentMap = ReflectionUtil.getProperty(energyNet, AlteredEnergyNet.class, energyNetComponentType);
                    if (componentMap != null) {
                        energyNetComponentMap.putAll(componentMap);
                    }
                }
                if (energyNet instanceof AlteredEnergyNet) {
                    generatedEnergyMap = AlteredEnergyNet.getGeneratedEnergyMap();
                    consumedEnergyMap = AlteredEnergyNet.getConsumedEnergyMap();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        List<Location> locationList = energyNetComponentMap.keySet().stream().sorted((location1, location2) -> (int) (LocationUtil.getManhattanDistance(location1, this.location) - LocationUtil.getManhattanDistance(location2, this.location))).collect(Collectors.toList());

        this.page = Math.max(0, this.page);
        this.page = Math.min((locationList.size() - 1) / CONTENT.length, this.page);

        LanguageManager languageManager = FinalTechChanged.getLanguageManager();

        int i;
        for (i = 0; i + page * CONTENT.length < locationList.size() && i < CONTENT.length; i++) {
            Location componentLocation = locationList.get(i + page * CONTENT.length);
            EnergyNetComponent energyNetComponent = energyNetComponentMap.get(componentLocation);
            if (energyNetComponent instanceof SlimefunItem slimefunItem) {
                ItemStack itemStack = ItemStackUtil.cloneItem(slimefunItem.getItem());
                List<String> loreList = languageManager.replaceStringList(languageManager.getStringList("items", "ENERGY_REGULATOR", "statistics", "lore-location"),
                        String.valueOf(componentLocation.getBlockX() - this.location.getBlockX()),
                        String.valueOf(componentLocation.getBlockY() - this.location.getBlockY()),
                        String.valueOf(componentLocation.getBlockZ() - this.location.getBlockZ()));

                String energyTypeName = energyNetComponent.getEnergyComponentType().name();
                energyTypeName = languageManager.getString("items", "ENERGY_REGULATOR", "statistics", energyTypeName);

                loreList.addAll(languageManager.replaceStringList(languageManager.getStringList("items", "ENERGY_REGULATOR", "statistics", "lore-type"), energyTypeName));

                if (energyNetComponent.isChargeable()) {
                    int charge = energyNetComponent.getCharge(componentLocation);
                    int capacity = energyNetComponent.getCapacity();
                    loreList.addAll(languageManager.replaceStringList(languageManager.getStringList("items", "ENERGY_REGULATOR", "statistics", "lore-energy"),
                            String.valueOf(charge),
                            String.valueOf(capacity)));
                }

                Long generatedEnergy = generatedEnergyMap.get(componentLocation);
                if (generatedEnergy != null) {
                    loreList.addAll(languageManager.replaceStringList(languageManager.getStringList("items", "ENERGY_REGULATOR", "statistics", "lore-generate"), String.valueOf(generatedEnergy)));
                }

                Long consumedEnergy = consumedEnergyMap.get(componentLocation);
                if (consumedEnergy != null) {
                    loreList.addAll(languageManager.replaceStringList(languageManager.getStringList("items", "ENERGY_REGULATOR", "statistics", "lore-consume"), String.valueOf(consumedEnergy)));
                }

                ItemStackUtil.setLore(itemStack, loreList);
                SfItemUtil.removeSlimefunId(itemStack);

                this.replaceExistingItem(CONTENT[i], itemStack);
                this.addMenuClickHandler(CONTENT[i], (p, slot, item, action) -> {
                    BlockMenu blockMenu = BlockStorage.getInventory(componentLocation);
                    if (blockMenu != null && blockMenu.canOpen(this.location.getBlock(), p)) {
                        blockMenu.open(p);
                    }
                    Bukkit.getScheduler().runTaskAsynchronously(FinalTechChanged.getInstance(), () -> ParticleUtil.drawCubeByBlock(FinalTechChanged.getInstance(), Particle.WAX_OFF, 0, componentLocation.getBlock()));
                    return false;
                });
            } else {
                this.replaceExistingItem(CONTENT[i], Icon.BORDER_ICON);
                this.addMenuClickHandler(CONTENT[i], ChestMenuUtils.getEmptyClickHandler());
            }
        }

        while (i < CONTENT.length) {
            this.replaceExistingItem(CONTENT[i], Icon.BORDER_ICON);
            this.addMenuClickHandler(CONTENT[i], ChestMenuUtils.getEmptyClickHandler());
            i++;
        }
    }

    public BukkitTask startRefreshTask() {
        if (this.refreshTask != null) {
            return this.refreshTask;
        }
        return Bukkit.getScheduler().runTaskTimer(FinalTechChanged.getInstance(), this::updateMenu, Slimefun.getTickerTask().getTickRate(), Slimefun.getTickerTask().getTickRate());
    }
}
