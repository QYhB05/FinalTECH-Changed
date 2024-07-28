package io.taraxacum.finaltech;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.setup.SetupUtil;
import io.taraxacum.finaltech.setup.Updater;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.plugin.dto.ConfigFileManager;
import io.taraxacum.libs.plugin.dto.CustomLogger;
import io.taraxacum.libs.plugin.dto.LanguageManager;
import io.taraxacum.libs.plugin.dto.ServerRunnableLockFactory;
import io.taraxacum.libs.slimefun.dto.ItemValueTable;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.AdvancedPie;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * @author Final_ROOT
 */
public class FinalTechChanged extends JavaPlugin implements SlimefunAddon {
    private static FinalTechChanged instance;
    private final String version = Updater.LATEST_VERSION;
    public ConfigFileManager CON;
    /**
     * Force other slimefun machine to run async.
     */
    private boolean forceSlimefunMultiThread = false;
    /**
     * 0: nothing change, all task will run at slimefun #{@link io.github.thebusybiscuit.slimefun4.implementation.tasks.TickerTask}
     * 1: async task will be put in #{@link ServerRunnableLockFactory}, so they will be really async
     * 2: sync task will be run as async, so all (FinalTechChanged's machines') task will be put in #{@link ServerRunnableLockFactory}
     */
    private int multiThreadLevel = 0;
    /**
     * Add by 1 every slimefun tick.
     */
    private int slimefunTickCount = 0;
    private boolean dataLossFix = false;
    private boolean dataLossFixCustom = false;
    private boolean dataLossFixCustomAll = false;
    private Map<String, Map<String, String>> dataLossFixCustomMap = new HashMap<>();
    private double tps = 20;
    public static boolean y = false;
    private boolean debugMode = false;
    private CustomLogger logger;
    private ServerRunnableLockFactory<Location> locationRunnableFactory;
    private ServerRunnableLockFactory<Entity> entityRunnableFactory;
    private ConfigFileManager config;
    private ConfigFileManager value;
    private ConfigFileManager item;
    private ConfigFileManager template;
    private LanguageManager languageManager;
    private Set<String> asyncSlimefunIdSet = new HashSet<>();
    private Set<String> antiAccelerateSlimefunIdSet = new HashSet<>();
    private Set<String> performanceLimitSlimefunIdSet = new HashSet<>();
    private Set<String> noBlockTickerSlimefunIdSet = new HashSet<>();
    private Set<String> asyncSlimefunPluginSet = new HashSet<>();
    private Set<String> antiAccelerateSlimefunPluginSet = new HashSet<>();
    private Set<String> performanceLimitSlimefunPluginSet = new HashSet<>();
    private Set<String> noBlockTickerSlimefunPluginSet = new HashSet<>();
    private Random random;
    private long seed;
    private BukkitTask bukkitTask;

    public static FinalTechChanged getInstance() {
        return instance;
    }

    public static Logger logger() {
        return instance.logger;
    }

    public static boolean debugMode() {
        return instance.debugMode;
    }

    public static int getMultiThreadLevel() {
        return instance.multiThreadLevel;
    }

    public static boolean getForceSlimefunMultiThread() {
        return instance.forceSlimefunMultiThread;
    }

    public static boolean getDataLossFix() {
        return instance.dataLossFix;
    }

    public static boolean getDataLossFixCustom() {
        return instance.dataLossFixCustom;
    }

    @Nullable
    public static Map<String, String> getDataLossFixCustomMap(@Nonnull String id) {
        Map<String, String> map = instance.dataLossFixCustomMap.get(id);
        if (map == null && instance.dataLossFixCustomAll) {
            map = new HashMap<>();
            map.put(ConstantTableUtil.CONFIG_ID, id);
            instance.dataLossFixCustomMap.put(id, map);
        }
        return map;
    }

    public static int getSlimefunTickCount() {
        return instance.slimefunTickCount;
    }

    public static double getTps() {
        return instance.tps;
    }

    public static long getSeed() {
        return instance.seed;
    }

    public static Random getRandom() {
        return instance.random;
    }

    public static ServerRunnableLockFactory<Location> getLocationRunnableFactory() {
        return instance.locationRunnableFactory;
    }

    public static ServerRunnableLockFactory<Entity> getEntityRunnableFactory() {
        return instance.entityRunnableFactory;
    }

    public static ConfigFileManager getConfigManager() {
        return instance.config;
    }

    public static ConfigFileManager getValueManager() {
        return instance.value;
    }

    public static ConfigFileManager getItemManager() {
        return instance.item;
    }

    public static LanguageManager getLanguageManager() {
        return instance.languageManager;
    }

    public static String getLanguageString(@Nonnull String... paths) {
        return instance.languageManager.getString(paths);
    }

    @Nonnull
    public static List<String> getLanguageStringList(@Nonnull String... paths) {
        return instance.languageManager.getStringList(paths);
    }

    @Nonnull
    public static String[] getLanguageStringArray(@Nonnull String... paths) {
        return instance.languageManager.getStringList(paths).toArray(new String[0]);
    }

    public static boolean isAsyncSlimefunItem(@Nonnull String id) {
        return instance.asyncSlimefunIdSet.contains(id);
    }

    public static boolean addAsyncSlimefunItem(@Nonnull String id) {
        return instance.asyncSlimefunIdSet.add(id);
    }

    public static boolean isAntiAccelerateSlimefunItem(@Nonnull String id) {
        // TODO config GUI
        return instance.antiAccelerateSlimefunIdSet.contains(id);
    }

    public static boolean addAntiAccelerateSlimefunItem(@Nonnull String id) {
        return instance.antiAccelerateSlimefunIdSet.add(id);
    }

    public static boolean isPerformanceLimitSlimefunItem(@Nonnull String id) {
        // TODO config GUI
        return instance.performanceLimitSlimefunIdSet.contains(id);
    }

    public static boolean addPerformanceLimitSlimefunItem(@Nonnull String id) {
        return instance.performanceLimitSlimefunIdSet.add(id);
    }

    public static boolean isNoBlockTickerSlimefunItem(@Nonnull String id) {
        // TODO config GUI
        return instance.noBlockTickerSlimefunIdSet.contains(id);
    }

    public static boolean addNoBlockTickerSlimefunItem(@Nonnull String id) {
        return instance.noBlockTickerSlimefunIdSet.add(id);
    }

    public static Set<String> getAsyncSlimefunPluginSet() {
        return instance.asyncSlimefunPluginSet;
    }

    public static Set<String> getAntiAccelerateSlimefunPluginSet() {
        return instance.antiAccelerateSlimefunPluginSet;
    }

    public static Set<String> getPerformanceLimitSlimefunPluginSet() {
        return instance.performanceLimitSlimefunPluginSet;
    }

    public static Set<String> getNoBlockTickerSlimefunPluginSet() {
        return instance.noBlockTickerSlimefunPluginSet;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        instance = this;

        /* read config file */
        try {
            this.config = ConfigFileManager.getOrNewInstance(this, "config");
            this.value = ConfigFileManager.getOrNewInstance(this, "value");
            this.item = ConfigFileManager.getOrNewInstance(this, "item");
            this.template = ConfigFileManager.getOrNewInstance(this, "template");

            String language = this.config.getOrDefault("zh-CN", "language");
            this.languageManager = LanguageManager.getOrNewInstance(this, language);
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        /* set language manager */
        SetupUtil.setupLanguageManager(this.languageManager);

        /* set logger */
        this.logger = CustomLogger.newInstance(this.getJavaPlugin().getServer().getLogger());
        this.logger.setBanner("[" + this.languageManager.getOrDefault("FinalTECHChanged", "FinalTechChanged") + "] ");

        /* set version */
        if (!this.config.containPath("version")) {
            this.config.setValue(version, "version");
            if (!this.config.containPath("enable", "item")) {
                this.config.setValue(true, "enable", "item");
            }
        }

        /* update the config file */
        if (this.config.getOrDefault(true, "update", "enable")) {
            this.logger.info("You have enabled the config updater.");
            Updater updater = Updater.getInstance();
            try {
                updater.update(this);
            } catch (Exception e) {
                e.printStackTrace();
                this.logger.warning("Some error occurred while doing update..");
            }
        } else {
            this.logger.info("You have disabled the config updater.");
        }

        /* set random seed */
        this.seed = this.config.getOrDefault(new Random().nextLong(Long.MAX_VALUE), "seed");
        this.random = new Random(this.seed);

        /* set debug mode */
        this.debugMode = this.config.getOrDefault(false, "debug-mode");
        if (this.debugMode) {
            this.logger.warning("You have debug mode on!");
        }

        /* set runnable factory */
        this.locationRunnableFactory = ServerRunnableLockFactory.getInstance(this, Location.class);
        this.entityRunnableFactory = ServerRunnableLockFactory.getInstance(this, Entity.class);

        /* configure multi thread level */
        this.multiThreadLevel = this.config.getOrDefault(0, "multi-thread", "level");
        if (this.multiThreadLevel > 2 || this.multiThreadLevel < 0) {
            this.multiThreadLevel = 0;
        }
        if (this.multiThreadLevel >= 1 && !this.config.getOrDefault(false, "multi-thread", "warn-I_know_what_I'm_doing")) {
            this.logger.warning("It seems you don't know what you are doing. So multi thread level is set to 0");
            this.multiThreadLevel = 0;
        }
        if (this.multiThreadLevel >= 2 && !this.config.getOrDefault(false, "multi-thread", "warn-I_really_know_what_I'm_doing")) {
            this.logger.warning("It seems you don't know what you are doing. So multi thread level is set to 0");
            this.multiThreadLevel = 0;
        }

        /* configure whether to force slimefun items to run async */
        this.forceSlimefunMultiThread = this.config.getOrDefault(false, "force-slimefun-multi-thread", "enable");
        if (this.forceSlimefunMultiThread && !this.config.getOrDefault(false, "force-slimefun-multi-thread", "warn-I_know_what_I'm_doing_and_I_will_be_responsible_for_it")) {
            this.logger.warning("It seems you don't know what you are doing. So force-slimefun-multi-thread.enable is set to false!");
            this.forceSlimefunMultiThread = false;
        }

        /* setup data loss bug fix */
        this.dataLossFix = this.config.getOrDefault(false, "data-loss-fix", "enable");
        this.dataLossFixCustom = this.config.getOrDefault(false, "data-loss-fix-custom", "enable");
        if (this.config.containPath("data-loss-fix-custom", "config")) {
            this.dataLossFixCustomAll = this.config.getOrDefault(false, "data-loss-fix-custom", "all");
            if (this.dataLossFixCustomAll) {
                this.logger.warning("You have enabled all items to be fixed for data loss bug!");
            }
            for (String id : this.config.getStringList("data-loss-fix-custom", "config")) {
                List<String> keyList = this.config.getStringList("data-loss-fix-custom", "config", id);
                Map<String, String> configMap = new HashMap<>(keyList.size());
                for (String key : keyList) {
                    configMap.put(key, this.config.getString("data-loss-fix-custom", "config", id, key));
                }
                this.dataLossFixCustomMap.put(id, configMap);
            }
        }

        /* read tweak for machine */
        this.antiAccelerateSlimefunIdSet = new HashSet<>(this.config.getStringList("tweak", "anti-accelerate", "item"));
        this.performanceLimitSlimefunIdSet = new HashSet<>(this.config.getStringList("tweak", "performance-limit", "item"));
        this.noBlockTickerSlimefunIdSet = new HashSet<>(this.config.getStringList("tweak", "no-blockTicker", "item"));
        this.asyncSlimefunIdSet = new HashSet<>(this.config.getStringList("tweak", "force-async", "item"));
        this.antiAccelerateSlimefunPluginSet = new HashSet<>(this.config.getStringList("tweak", "anti-accelerate", "addon"));
        this.performanceLimitSlimefunPluginSet = new HashSet<>(this.config.getStringList("tweak", "performance-limit", "addon"));
        this.noBlockTickerSlimefunPluginSet = new HashSet<>(this.config.getStringList("tweak", "no-blockTicker", "addon"));
        this.asyncSlimefunPluginSet = new HashSet<>(this.config.getStringList("tweak", "force-async", "addon"));

        if (this.asyncSlimefunPluginSet.size() > 0 || this.asyncSlimefunIdSet.size() > 0) {
            this.logger.warning("You set force-async for some SlimefunItems! It's ok but you should be aware that this may cause some strange error.");
        }

        /* run task timer to do some function */
        int tickRate = Slimefun.getTickerTask().getTickRate();
        this.bukkitTask = this.getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            private final BigDecimal FULL_SLIMEFUN_TICK = new BigDecimal(StringNumberUtil.mul("1000000000", String.valueOf(tickRate)));
            private long currentNanoTime = System.nanoTime();
            private long lastNanoTime = System.nanoTime();

            @Override
            public void run() {
                currentNanoTime = System.nanoTime();
                FinalTechChanged.instance.tps = Math.min(FULL_SLIMEFUN_TICK.divide(BigDecimal.valueOf(Math.max(1, currentNanoTime - lastNanoTime)), 10, RoundingMode.FLOOR).doubleValue(), 20);
                lastNanoTime = currentNanoTime;

                FinalTechChanged.instance.slimefunTickCount++;
            }
        }, tickRate - 1, tickRate);


        /* set up my items and menus and... */
        SetupUtil.init();

        this.performanceLimitSlimefunIdSet.add(FinalTechItemStacks.ORDERED_DUST_FACTORY_DIRT.getItemId());
        this.performanceLimitSlimefunIdSet.add(FinalTechItemStacks.ORDERED_DUST_FACTORY_STONE.getItemId());

        /* mark for some machines */
        this.antiAccelerateSlimefunIdSet.add(FinalTechItemStacks.ENERGIZED_ACCELERATOR.getItemId());
        this.antiAccelerateSlimefunIdSet.add(FinalTechItemStacks.OVERLOADED_ACCELERATOR.getItemId());
        this.antiAccelerateSlimefunIdSet.add(FinalTechItemStacks.ITEM_DESERIALIZE_PARSER.getItemId());
        this.antiAccelerateSlimefunIdSet.add(FinalTechItemStacks.ENTROPY_SEED.getItemId());
        this.antiAccelerateSlimefunIdSet.add(FinalTechItemStacks.EQUIVALENT_CONCEPT.getItemId());
        this.antiAccelerateSlimefunIdSet.add(FinalTechItemStacks.MATRIX_ACCELERATOR.getItemId());

        /* setup template machine */
        // we need more test. and it's not all finished.
//        int templateMachineDelay = this.config.getOrDefault(-1, "setups", "template-machine", "delay");
//        if(templateMachineDelay >= 0) {
//            this.getServer().getScheduler().runTaskLater(this, () -> new TemplateParser(FinalTechChanged.this.template, false, false).registerMachine(), templateMachineDelay);
//        } else {
//            new TemplateParser(this.template, false, false).registerMachine();
//        }

        /* fix data loss for others */
        if (this.dataLossFixCustom) {
            int dataLossFixCustomDelay = this.config.getOrDefault(1, "setups", "data-loss-fix-custom", "delay");
            if (dataLossFixCustomDelay >= 0) {
                this.getServer().getScheduler().runTaskLater(this, SetupUtil::dataLossFix, dataLossFixCustomDelay);
            } else {
                SetupUtil.dataLossFix();
            }
        }

        /* setup item value table */
        int itemValueTableDelay = this.config.getOrDefault(10, "setups", "item-value-table", "delay");
        if (itemValueTableDelay >= 0) {
            this.getServer().getScheduler().runTaskLater(this, () -> ItemValueTable.getInstance().init(), itemValueTableDelay);
        } else {
            ItemValueTable.getInstance().init();
        }

        /* setup slimefun machine block ticker */
        int blockTickerRegisterDelay = this.config.getOrDefault(20, "setups", "slimefun-machine", "delay");
        if (blockTickerRegisterDelay >= 0) {
            this.getServer().getScheduler().runTaskLater(this, SetupUtil::registerBlockTicker, blockTickerRegisterDelay);
        } else {
            SetupUtil.registerBlockTicker();
        }

        /* setup bstats */
        Metrics metrics = new Metrics(this, 16920);
        metrics.addCustomChart(new AdvancedPie("how_you_translate_gearwheel", () -> {
            Map<String, Integer> result = new HashMap<>();
            result.put(FinalTechItemStacks.GEARWHEEL.getDisplayName(), 1);
            return result;
        }));
        metrics.addCustomChart(new AdvancedPie("how_you_called_this_plugin", () -> {
            Map<String, Integer> result = new HashMap<>();
            result.put(FinalTechChanged.getLanguageManager().getOrDefault("unknown", "FinalTechChanged"), 1);
            return result;
        }));
        metrics.addCustomChart(new AdvancedPie("data_loss_fix", () -> {
            Map<String, Integer> result = new HashMap<>();
            result.put(String.valueOf(FinalTechChanged.getDataLossFix()), 1);
            return result;
        }));
        metrics.addCustomChart(new AdvancedPie("data_loss_fix_for_others", () -> {
            Map<String, Integer> result = new HashMap<>();
            result.put(String.valueOf(FinalTechChanged.getDataLossFixCustom()), 1);
            return result;
        }));
        metrics.addCustomChart(new AdvancedPie("multi_thread_level", () -> {
            Map<String, Integer> result = new LinkedHashMap<>();
            result.put(String.valueOf(FinalTechChanged.getMultiThreadLevel()), 1);
            return result;
        }));
        metrics.addCustomChart(new AdvancedPie("config_version", () -> {
            Map<String, Integer> result = new HashMap<>();
            result.put(FinalTechChanged.getConfigManager().getOrDefault("unknown", "version"), 1);
            return result;
        }));
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        // TODO reload more...
    }

    @Override
    public void onDisable() {
        ServerRunnableLockFactory.stop();
        if (this.bukkitTask != null) {
            this.bukkitTask.cancel();
        }
        BlockStorage.saveChunks();
        try {
            FinalTechChanged.logger().info("Waiting all task to end.(" + FinalTechChanged.getLocationRunnableFactory().taskSize() + ")");
            FinalTechChanged.getLocationRunnableFactory().waitAllTask();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            BlockStorage.saveChunks();
            try {
                for (World world : Bukkit.getWorlds()) {
                    BlockStorage storage = BlockStorage.getStorage(world);
                    if (storage != null) {
                        storage.save();
                    }
                }
                BlockStorage.saveChunks();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            FinalTechChanged.getEntityRunnableFactory().waitAllTask();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            BlockStorage.saveChunks();
            try {
                for (World world : Bukkit.getWorlds()) {
                    BlockStorage storage = BlockStorage.getStorage(world);
                    if (storage != null) {
                        storage.save();
                    }
                }
                BlockStorage.saveChunks();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBugTrackerURL() {
        return "???";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }
}
