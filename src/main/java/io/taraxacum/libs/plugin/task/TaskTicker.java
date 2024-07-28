package io.taraxacum.libs.plugin.task;

import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.libs.plugin.dto.ServerRunnableLockFactory;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * @param <T> Target object type
 * @author Final_ROOT
 * @version 2
 * @since 2.2
 */
public class TaskTicker<T> {
    private static boolean serverStop;
    private final T object;
    private List<TickerTask<T>> tickerTaskList = new ArrayList<>();
    private Class<T> clazz;
    private BukkitTask bukkitTask;

    protected TaskTicker(@Nonnull T t) {
        this.object = t;
        this.clazz = (Class<T>) t.getClass();
    }

    @Nonnull
    public static <T> TaskTicker<T> applyOrAddTo(@Nonnull TickerTask<T> tickerTask, @Nonnull T object, @Nonnull Class<T> clazz) {
        TaskMap<T> taskMap = TaskMap.getInstance(clazz);
        TaskTicker<T> taskTicker = taskMap.getRunner(object);
        if (taskTicker == null) {
            synchronized (taskMap) {
                taskTicker = taskMap.getRunner(object);
                if (taskTicker == null) {
                    taskTicker = new TaskTicker<>(object);
                    taskMap.put(object, taskTicker);
                }
            }
        }

        List<TickerTask<T>> tickerTaskList = taskTicker.tickerTaskList;
        for (TickerTask<T> existedTickerTask : tickerTaskList) {
            if (existedTickerTask.getId().equals(tickerTask.getId())) {
                existedTickerTask.addTime(tickerTask.getTime());
                if (tickerTask instanceof AddTask addTask && existedTickerTask instanceof AddTask existedAddTask) {
                    taskTicker.addTick(existedAddTask, addTask);
                }
                return taskTicker;
            }
        }

        taskTicker.tickerTaskList.add(tickerTask);
        if (tickerTask instanceof StartTask startTask) {
            taskTicker.startTick(startTask);
        }

        taskTicker.run();
        return taskTicker;
    }

    @Nonnull
    public static <T> TaskTicker<T> applyOrSetTo(@Nonnull TickerTask<T> tickerTask, @Nonnull T object, @Nonnull Class<T> clazz) {
        TaskMap<T> taskMap = TaskMap.getInstance(clazz);
        TaskTicker<T> taskTicker = taskMap.getRunner(object);
        if (taskTicker == null) {
            synchronized (taskMap) {
                taskTicker = taskMap.getRunner(object);
                if (taskTicker == null) {
                    taskTicker = new TaskTicker<>(object);
                    taskMap.put(object, taskTicker);
                }
            }
        }

        List<TickerTask<T>> tickerTaskList = taskTicker.tickerTaskList;
        for (TickerTask<T> existedTickerTask : tickerTaskList) {
            if (existedTickerTask.getId().equals(tickerTask.getId())) {
                existedTickerTask.setTime(Math.max(existedTickerTask.getTime(), tickerTask.getTime()));
                return taskTicker;
            }
        }

        taskTicker.tickerTaskList.add(tickerTask);
        if (tickerTask instanceof StartTask startTask) {
            taskTicker.startTick(startTask);
        }

        taskTicker.run();
        return taskTicker;
    }

    public static <T> void clear(@Nonnull T object, @Nonnull Class<T> clazz, @Nonnull String... ids) {
        Set<String> stringSet = JavaUtil.toSet(ids);
        TaskMap<T> taskMap = TaskMap.getInstance(clazz);
        TaskTicker<T> taskTicker = taskMap.getRunner(object);
        if (taskTicker != null) {
            for (TickerTask<T> tickerTask : taskTicker.getTickerTaskList()) {
                if (stringSet.contains(tickerTask.getId())) {
                    tickerTask.setTime(0);
                }
            }
        }
    }

    public static <T> boolean has(@Nonnull T object, @Nonnull Class<T> clazz, @Nonnull String id) {
        TaskMap<T> taskMap = TaskMap.getInstance(clazz);
        TaskTicker<T> taskTicker = taskMap.getRunner(object);
        if (taskTicker != null) {
            for (TickerTask<T> tickerTask : taskTicker.getTickerTaskList()) {
                if (id.equals(tickerTask.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Nullable
    public static <T> TaskTicker<T> getInstance(@Nonnull T object, @Nonnull Class<T> clazz) {
        return TaskMap.getInstance(clazz).getRunner(object);
    }

    public static void stop() {
        TaskTicker.serverStop = true;
    }

    public T getObject() {
        return object;
    }

    public List<TickerTask<T>> getTickerTaskList() {
        return tickerTaskList;
    }

    public void run() {
        synchronized (this) {
            if (this.bukkitTask == null && !this.tickerTaskList.isEmpty() && !TaskTicker.serverStop) {
                this.bukkitTask = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this.tickerTaskList.get(0).getPlugin(), () -> {
                    if (TaskTicker.serverStop) synchronized (this) {
                        this.bukkitTask.cancel();
                        TaskMap.getInstance(this.clazz).remove(this.object);
                        this.bukkitTask = null;
                        return;
                    }

                    this.tickerTaskList.sort(Comparator.comparingInt(TickerTask::getPriority));
                    Iterator<TickerTask<T>> iterator = this.tickerTaskList.iterator();

                    while (iterator.hasNext()) {
                        TickerTask<T> tickerTask = iterator.next();
                        if (tickerTask.getTime() <= 0) {
                            iterator.remove();
                            if (tickerTask instanceof EndTask endTask) {
                                TaskTicker.this.endTick(endTask);
                            }
                        } else {
                            this.tickTask(tickerTask);
                        }
                        tickerTask.setTime(tickerTask.getTime() - 1);
                    }

                    if (this.tickerTaskList.size() == 0) synchronized (this) {
                        this.bukkitTask.cancel();
                        TaskMap.getInstance(this.clazz).remove(this.object);
                        this.bukkitTask = null;
                    }
                }, 1, 1);
            }
        }
    }

    private void tickTask(@Nonnull TickerTask<T> tickerTask) {
        if (tickerTask.isSync()) {
            tickerTask.getPlugin().getServer().getScheduler().runTask(tickerTask.getPlugin(), () -> tickerTask.tick(object));
        } else {
            ServerRunnableLockFactory.getInstance(tickerTask.getPlugin(), this.clazz).waitThenRun(() -> tickerTask.tick(object), object);
        }
    }

    private void startTick(@Nonnull StartTask<T> startTask) {
        if (startTask.isSync()) {
            startTask.getPlugin().getServer().getScheduler().runTask(startTask.getPlugin(), () -> startTask.startTick(object));
        } else {
            ServerRunnableLockFactory.getInstance(startTask.getPlugin(), this.clazz).waitThenRun(() -> startTask.startTick(object), object);
        }
    }

    private void endTick(@Nonnull EndTask<T> endTask) {
        if (endTask.isSync()) {
            endTask.getPlugin().getServer().getScheduler().runTask(endTask.getPlugin(), () -> endTask.endTick(object));
        } else {
            ServerRunnableLockFactory.getInstance(endTask.getPlugin(), this.clazz).waitThenRun(() -> endTask.endTick(object), object);
        }
    }

    private void addTick(@Nonnull AddTask<T> addTask, @Nonnull AddTask<T> existedTask) {
        if (addTask.isSync()) {
            addTask.getPlugin().getServer().getScheduler().runTask(addTask.getPlugin(), () -> existedTask.addTick(object, addTask));
        } else {
            ServerRunnableLockFactory.getInstance(addTask.getPlugin(), this.clazz).waitThenRun(() -> existedTask.addTick(object, addTask));
        }

    }

    private static class TaskMap<T> {
        private static final Map<Class<?>, TaskMap<?>> INSTANCE_MAP = new HashMap<>();
        private final Map<T, TaskTicker<T>> runnerMap = new HashMap<>();

        private TaskMap() {

        }

        @Nonnull
        protected static <T> TaskMap<T> getInstance(@Nonnull Class<T> clazz) {
            if (INSTANCE_MAP.containsKey(clazz)) {
                return (TaskTicker.TaskMap<T>) INSTANCE_MAP.get(clazz);
            }
            synchronized (INSTANCE_MAP) {
                if (INSTANCE_MAP.containsKey(clazz)) {
                    return (TaskTicker.TaskMap<T>) INSTANCE_MAP.get(clazz);
                }
                TaskMap<T> objectMap = new TaskMap<>();
                INSTANCE_MAP.put(clazz, objectMap);
                return objectMap;
            }

        }

        @Nullable
        protected TaskTicker<T> getRunner(@Nonnull T object) {
            return this.runnerMap.get(object);
        }

        protected void remove(@Nonnull T object) {
            this.runnerMap.remove(object);
        }

        protected void put(@Nonnull T object, @Nonnull TaskTicker<T> taskTicker) {
            this.runnerMap.put(object, taskTicker);
        }
    }
}
