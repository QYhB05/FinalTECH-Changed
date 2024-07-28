package io.taraxacum.finaltech.core.task.effect;

import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.plugin.task.TickerTask;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

public abstract class AbstractEffect extends TickerTask<LivingEntity> {
    private int level;

    public AbstractEffect(int time, int level) {
        super(time);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (this.maxLevel() > 0) {
            this.level = Math.min(this.maxLevel(), level);
        } else if (this.maxLevel() < 0) {
            this.level = level;
        }
    }

    @Override
    public Plugin getPlugin() {
        return FinalTechChanged.getInstance();
    }

    abstract int maxLevel();
}
