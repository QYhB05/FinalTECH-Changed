package io.taraxacum.libs.plugin.dto;

import io.taraxacum.common.api.RunnableLockFactory;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * This is not the best way to work with Slimefun in async,
 * And may be deprecated in the future.
 *
 * @param <T>
 * @author Final_ROOT
 * @version 2
 * @since 2.2
 */
public class ServerRunnableLockFactory<T> implements RunnableLockFactory<T> {
    private static final FutureTask<Void> VOID_FUTURE_TASK = new FutureTask<>(() -> null);
    private static final Map<Plugin, Map<Class<?>, ServerRunnableLockFactory<?>>> INSTANCE_MAP = new HashMap<>();
    private static boolean serverStop = false;
    private final Plugin plugin;
    private final BukkitScheduler scheduler;
    private final ObjectMap<T> objectMap;

    private ServerRunnableLockFactory(@Nonnull Plugin plugin, @Nonnull Class<T> clazz) {
        this.plugin = plugin;
        this.scheduler = this.plugin.getServer().getScheduler();
        this.objectMap = ObjectMap.getInstance(clazz);
    }

    public static void stop() {
        ServerRunnableLockFactory.serverStop = true;
    }

    public static <T> ServerRunnableLockFactory<T> getInstance(@Nonnull Plugin plugin, @Nonnull Class<T> clazz) {
        Map<Class<?>, ServerRunnableLockFactory<?>> instanceClassMap = INSTANCE_MAP.get(plugin);
        if (instanceClassMap != null) {
            ServerRunnableLockFactory<?> serverRunnableLockFactory = instanceClassMap.get(clazz);
            if (serverRunnableLockFactory != null) {
                return (ServerRunnableLockFactory<T>) serverRunnableLockFactory;
            } else synchronized (instanceClassMap) {
                serverRunnableLockFactory = instanceClassMap.get(clazz);
                if (serverRunnableLockFactory != null) {
                    return (ServerRunnableLockFactory<T>) serverRunnableLockFactory;
                } else {
                    ServerRunnableLockFactory<T> instance = new ServerRunnableLockFactory<>(plugin, clazz);
                    instanceClassMap.put(clazz, instance);
                    return instance;
                }
            }
        } else {
            synchronized (ServerRunnableLockFactory.class) {
                instanceClassMap = INSTANCE_MAP.get(plugin);
                if (instanceClassMap == null) {
                    INSTANCE_MAP.put(plugin, new HashMap<>());
                }
            }
            return ServerRunnableLockFactory.getInstance(plugin, clazz);
        }
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    @SafeVarargs
    public final FutureTask<Void> waitThenRun(long delay, @Nonnull Runnable runnable, @Nonnull T... objects) {
        FutureTask<Void> futureTask = new FutureTask<>(() -> {
            try {
                if (!ServerRunnableLockFactory.serverStop) {
                    runnable.run();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                for (T object : objects) {
                    ServerRunnableLockFactory.this.objectMap.remove(object);
                }
            }
            return null;
        });
        if (ServerRunnableLockFactory.serverStop) {
            return VOID_FUTURE_TASK;
        }
        this.scheduler.runTaskLaterAsynchronously(this.plugin, () -> {
            boolean work = false;
            while (!work) {
                ServerRunnableLockFactory.this.waitFor(objects);
                synchronized (futureTask) {
                    synchronized (ServerRunnableLockFactory.this.objectMap) {
                        if (ServerRunnableLockFactory.this.test(objects) && !ServerRunnableLockFactory.serverStop) {
                            for (T object : objects) {
                                ServerRunnableLockFactory.this.objectMap.put(object, futureTask, ServerRunnableLockFactory.this);
                            }
                            work = true;
                        }
                    }
                    if (work) {
                        futureTask.run();
                    }
                }
            }
        }, delay);
        return futureTask;
    }

    @Override
    @SafeVarargs
    public final FutureTask<Void> waitThenRun(@Nonnull Runnable runnable, @Nonnull T... objects) {
        return this.waitThenRun(0, runnable, objects);
    }

    @SafeVarargs
    public final <C> FutureTask<C> waitThenRun(long delay, @Nonnull Callable<C> callable, @Nonnull T... objects) {
        FutureTask<C> futureTask = new FutureTask<>(() -> {
            try {
                if (!ServerRunnableLockFactory.serverStop) {
                    return callable.call();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                for (T object : objects) {
                    ServerRunnableLockFactory.this.objectMap.remove(object);
                }
            }
            return null;
        });
        if (ServerRunnableLockFactory.serverStop) {
            return new FutureTask<>(() -> null);
        }
        this.scheduler.runTaskLaterAsynchronously(this.plugin, () -> {
            boolean work = false;
            while (!work) {
                ServerRunnableLockFactory.this.waitFor(objects);
                synchronized (futureTask) {
                    synchronized (ServerRunnableLockFactory.this.objectMap) {
                        if (ServerRunnableLockFactory.this.test(objects) && !ServerRunnableLockFactory.serverStop) {
                            for (T object : objects) {
                                ServerRunnableLockFactory.this.objectMap.put(object, futureTask, ServerRunnableLockFactory.this);
                            }
                            work = true;
                        }
                    }
                    if (work) {
                        futureTask.run();
                    }
                }
            }
        }, delay);
        return futureTask;
    }

    @Override
    @SafeVarargs
    public final <C> FutureTask<C> waitThenRun(@Nonnull Callable<C> callable, @Nonnull T... objects) {
        return this.waitThenRun(0, callable, objects);
    }

    public int taskSize() {
        return this.objectMap.keySet().size();
    }

    @SafeVarargs
    public final boolean test(@Nonnull T... objects) {
        for (T object : objects) {
            FutureTask<?> task = this.objectMap.getTask(object);
            if (task != null && !task.isDone()) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    public final void waitFor(@Nonnull T... objects) {
        if (objects.length > 0) {
            this.waitFor(0, objects);
        } else {
            this.plugin.getLogger().severe("wrong use of ServerRunnableLockFactory");
        }
    }

    @SafeVarargs
    private void waitFor(int index, @Nonnull T... objects) {
        int i = index;
        try {
            do {
                FutureTask<?> task = this.objectMap.getTask(objects[i]);
                if (task != null && !task.isDone()) {
                    synchronized (task) {
                        task.get();
                    }
                }
            } while (++i < objects.length);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                T object = objects[i];
                if (object != null) {
                    if (objects[i] instanceof Location) {
                        this.plugin.getLogger().severe("An error occurred in location: " + object);
                    } else {
                        this.plugin.getLogger().severe("An error occurred in object: " + object);
                    }
                } else {
                    this.plugin.getLogger().severe("An error occurred.");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                this.waitFor(++i, objects);
            }
        }
    }

    public void waitAllTask() throws ExecutionException, InterruptedException {
        for (T t : this.objectMap.keySet()) {
            try {
                FutureTask<?> futureTask = this.objectMap.getTask(t);
                if (futureTask != null && !futureTask.isDone()) {
                    futureTask.get(5, TimeUnit.SECONDS);
                }
            } catch (TimeoutException e) {
                if (t instanceof Location) {
                    this.plugin.getLogger().warning("An error occurred in location: " + t);
                } else {
                    this.plugin.getLogger().warning("An error occurred in object: " + t);
                }
                e.printStackTrace();
            }
        }
    }

    protected static class ObjectMap<T> {
        private static final Map<Class<?>, ObjectMap<?>> INSTANCE_MAP = new HashMap<>();
        private final Map<T, FutureTask<?>> taskMap = new HashMap<>();
        private final Map<T, ServerRunnableLockFactory<T>> factoryMap = new HashMap<>();

        private ObjectMap() {
        }

        @Nonnull
        protected static <T> ObjectMap<T> getInstance(@Nonnull Class<T> clazz) {
            if (INSTANCE_MAP.containsKey(clazz)) {
                return (ObjectMap<T>) INSTANCE_MAP.get(clazz);
            }
            synchronized (INSTANCE_MAP) {
                if (INSTANCE_MAP.containsKey(clazz)) {
                    return (ObjectMap<T>) INSTANCE_MAP.get(clazz);
                }
                ObjectMap<T> objectMap = new ObjectMap<>();
                INSTANCE_MAP.put(clazz, objectMap);
                return objectMap;
            }
        }

        @Nullable
        protected FutureTask<?> getTask(@Nonnull T object) {
            return this.taskMap.get(object);
        }

        @Nullable
        protected ServerRunnableLockFactory<T> getFactory(@Nonnull T object) {
            return this.factoryMap.get(object);
        }

        protected void put(@Nonnull T object, @Nonnull FutureTask<?> futureTask, @Nonnull ServerRunnableLockFactory<T> serverRunnableLockFactory) {
            this.taskMap.put(object, futureTask);
            this.factoryMap.put(object, serverRunnableLockFactory);
        }

        protected void remove(@Nonnull T object) {
            this.taskMap.remove(object);
            this.factoryMap.remove(object);
        }

        protected void remove(@Nonnull T... objects) {
            for (T object : objects) {
                this.taskMap.remove(object);
                this.factoryMap.remove(object);
            }
        }

        protected Set<T> keySet() {
            return this.taskMap.keySet();
        }
    }
}


