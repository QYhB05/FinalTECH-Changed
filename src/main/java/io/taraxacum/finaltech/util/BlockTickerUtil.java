package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.common.api.RunnableLockFactory;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.plugin.dto.ServerRunnableLockFactory;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
 
public class BlockTickerUtil {
    @SafeVarargs
    public static <T> void runTask(@Nonnull ServerRunnableLockFactory<T> serverRunnableLockFactory, boolean async, @Nonnull Runnable runnable, T... locks) {
        if (async) {
            serverRunnableLockFactory.waitThenRun(runnable, locks);
        } else {
            runnable.run();
        }
    }


    public static <T> void runTask(@Nonnull ServerRunnableLockFactory<T> serverRunnableLockFactory, boolean async, @Nonnull Runnable runnable, Supplier<T[]> supplier) {
        if (async) {
            serverRunnableLockFactory.waitThenRun(runnable, supplier.get());
        } else {
            runnable.run();
        }
    }

    public static void setSleep(@Nonnull Config config, @Nullable String sleep) {
        config.setValue(ConstantTableUtil.CONFIG_SLEEP, sleep);
    }

    public static boolean hasSleep(@Nonnull Config config) {
        return config.contains(ConstantTableUtil.CONFIG_SLEEP);
    }

    public static void subSleep(@Nonnull Config config) {
        String sleepStr = config.getString(ConstantTableUtil.CONFIG_SLEEP);
        if (sleepStr != null) {
            double sleep = Double.parseDouble(sleepStr) - 1;
            if (sleep > 0) {
                config.setValue(ConstantTableUtil.CONFIG_SLEEP, String.valueOf(sleep));
            } else {
                config.setValue(ConstantTableUtil.CONFIG_SLEEP, "0");
            }
        }
    }

    @Nonnull
    public static BlockTicker getDebugModeBlockTicker(@Nonnull BlockTicker blockTicker, @Nonnull SlimefunItem slimefunItem) {
        return new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return blockTicker.isSynchronized();
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                FinalTechChanged.logger().info("DEBUG MODE: " + slimefunItem.getId() + " | Location: " + b.getLocation());
                blockTicker.tick(b, item, data);
            }

            @Override
            public void uniqueTick() {
                blockTicker.uniqueTick();
            }
        };
    }

    public static BlockTicker getGeneralIntervalBlockTicker(@Nonnull BlockTicker blockTicker, int interval) {
        return new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return blockTicker.isSynchronized();
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                if (FinalTechChanged.getSlimefunTickCount() % interval == 0) {
                    blockTicker.tick(b, item, data);
                }
            }

            @Override
            public void uniqueTick() {
                blockTicker.uniqueTick();
            }
        };
    }

    public static BlockTicker getIndependentIntervalBlockTicker(@Nonnull BlockTicker blockTicker, int interval) {
        String i = String.valueOf(--interval);
        return new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return blockTicker.isSynchronized();
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                if (BlockTickerUtil.hasSleep(data)) {
                    BlockTickerUtil.subSleep(data);
                }
                blockTicker.tick(b, item, data);
                BlockTickerUtil.setSleep(data, i);
            }

            @Override
            public void uniqueTick() {
                blockTicker.uniqueTick();
            }
        };
    }

    public static BlockTicker getRangeLimitBlockTicker(@Nonnull BlockTicker blockTicker, int range, int mulRange, boolean dropSelf, @Nonnull String message) {
        return new BlockTicker() {
            private List<Location> lastLocationList = new ArrayList<>();
            private List<Location> locationList = new ArrayList<>();
            private Random random = new Random();

            @Override
            public boolean isSynchronized() {
                return blockTicker.isSynchronized();
            }

            @Override
            public void tick(Block block, SlimefunItem item, Config data) {
                if (this.lastLocationList.size() > 1) {
                    Location randomLocation = this.lastLocationList.get(this.random.nextInt(this.lastLocationList.size()));
                    Location location = block.getLocation();
                    double manhattanDistance = LocationUtil.getManhattanDistance(randomLocation, location);
                    if (manhattanDistance < range + mulRange * this.lastLocationList.size() && manhattanDistance > 0) {
                        JavaPlugin javaPlugin = item.getAddon().getJavaPlugin();
                        World world = block.getLocation().getWorld();
                        javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                            boolean canBreak = true;
                            if (world != null) {
                                List<Player> playerList = new ArrayList<>();
                                for (Entity entity : world.getNearbyEntities(block.getLocation(), range, range, range, entity -> entity instanceof Player)) {
                                    if (entity instanceof Player player) {
                                        if (canBreak && !PermissionUtil.checkPermission(player, location, Interaction.BREAK_BLOCK)) {
                                            canBreak = false;
                                        }
                                        playerList.add(player);
                                    }
                                }
                                if (canBreak) {
                                    BlockStorage.clearBlockInfo(block);
                                    block.setType(Material.AIR);
                                    if (item instanceof MachineProcessHolder machineProcessHolder) {
                                        machineProcessHolder.getMachineProcessor().endOperation(block);
                                    }
                                    if (dropSelf && item.getId().equals(BlockStorage.getLocationInfo(block.getLocation(), ConstantTableUtil.CONFIG_ID))) {
                                        block.getLocation().getWorld().dropItem(block.getLocation(), ItemStackUtil.cloneItem(item.getItem(), 1));
                                    }
                                    for (Player player : playerList) {
                                        player.sendMessage(message.replace("{1}", item.getItemName()));
                                    }
                                }
                            }
                        });
                        return;
                    }
                }
                blockTicker.tick(block, item, data);
                this.locationList.add(block.getLocation());
            }

            @Override
            public void uniqueTick() {
                blockTicker.uniqueTick();
                List<Location> tempLocationList = this.lastLocationList;
                this.lastLocationList = this.locationList;
                this.locationList = tempLocationList;
                this.locationList.clear();
            }
        };
    }

    @Nonnull
    public static BlockTicker generateBlockTicker(@Nonnull BlockTicker blockTicker, boolean forceAsync, boolean antiAcceleration, boolean performanceLimit) {
        if (forceAsync && antiAcceleration && performanceLimit) {
            return new BlockTicker() {
                private final RunnableLockFactory<Location> runnableLockFactory = FinalTechChanged.getLocationRunnableFactory();

                @Override
                public boolean isSynchronized() {
                    return false;
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if (!AntiAccelerationUtil.isAccelerated(data) && PerformanceLimitUtil.charge(data)) {
                        this.runnableLockFactory.waitThenRun(() -> blockTicker.tick(b, item, data), b.getLocation());
                    }
                }

                @Override
                public void uniqueTick() {
                    blockTicker.uniqueTick();
                }
            };
        } else if (forceAsync && antiAcceleration && !performanceLimit) {
            return new BlockTicker() {
                private final RunnableLockFactory<Location> runnableLockFactory = FinalTechChanged.getLocationRunnableFactory();

                @Override
                public boolean isSynchronized() {
                    return false;
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if (!AntiAccelerationUtil.isAccelerated(data)) {
                        this.runnableLockFactory.waitThenRun(() -> blockTicker.tick(b, item, data), b.getLocation());
                    }
                }

                @Override
                public void uniqueTick() {
                    blockTicker.uniqueTick();
                }
            };
        } else if (forceAsync && !antiAcceleration && performanceLimit) {
            return new BlockTicker() {
                private final RunnableLockFactory<Location> runnableLockFactory = FinalTechChanged.getLocationRunnableFactory();

                @Override
                public boolean isSynchronized() {
                    return false;
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if (PerformanceLimitUtil.charge(data)) {
                        this.runnableLockFactory.waitThenRun(() -> blockTicker.tick(b, item, data), b.getLocation());
                    }
                }

                @Override
                public void uniqueTick() {
                    blockTicker.uniqueTick();
                }
            };
        } else if (forceAsync && !antiAcceleration && !performanceLimit) {
            return new BlockTicker() {
                private final RunnableLockFactory<Location> runnableLockFactory = FinalTechChanged.getLocationRunnableFactory();

                @Override
                public boolean isSynchronized() {
                    return false;
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    this.runnableLockFactory.waitThenRun(() -> blockTicker.tick(b, item, data), b.getLocation());
                }

                @Override
                public void uniqueTick() {
                    blockTicker.uniqueTick();
                }
            };
        } else if (!forceAsync && antiAcceleration && performanceLimit) {
            return new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if (!AntiAccelerationUtil.isAccelerated(data) && PerformanceLimitUtil.charge(data)) {
                        blockTicker.tick(b, item, data);
                    }
                }

                @Override
                public void uniqueTick() {
                    blockTicker.uniqueTick();
                }
            };
        } else if (!forceAsync && antiAcceleration && !performanceLimit) {
            return new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if (!AntiAccelerationUtil.isAccelerated(data)) {
                        blockTicker.tick(b, item, data);
                    }
                }

                @Override
                public void uniqueTick() {
                    blockTicker.uniqueTick();
                }
            };
        } else if (!forceAsync && !antiAcceleration && performanceLimit) {
            return new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return blockTicker.isSynchronized();
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    if (PerformanceLimitUtil.charge(data)) {
                        blockTicker.tick(b, item, data);
                    }
                }

                @Override
                public void uniqueTick() {
                    blockTicker.uniqueTick();
                }
            };
        } else {
            return blockTicker;
        }
    }
}
