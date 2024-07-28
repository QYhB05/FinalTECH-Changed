package io.taraxacum.finaltech.core.task.effect;

import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.libs.plugin.task.StartTask;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.annotation.Nonnull;

public class VoidCurse extends AbstractEffect implements StartTask<LivingEntity> {
    public static final String ID = "VOID_CURSE";
    private double health;

    public VoidCurse(int time, int level) {
        super(time, level);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public int maxLevel() {
        return 0;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public void tick(@Nonnull LivingEntity livingEntity) {
        double nowHealth = livingEntity.getHealth();
        if (this.health <= 0) {
            this.health = livingEntity.getMaxHealth() / 3 * 2;
        } else if (this.health < livingEntity.getHealth()) {
            Location location = livingEntity.getLocation();
            location.getWorld().spawnParticle(Particle.FALLING_LAVA, LocationUtil.fromRandom(location, FinalTechChanged.getRandom(), 0.4), 1);
            this.health = Math.max(this.health - livingEntity.getHealth() + this.health, 0);
            if (this.health > 0) {
                livingEntity.setHealth(this.health);
            } else {
                this.getPlugin().getServer().getScheduler().runTask(this.getPlugin(), () -> {
                    livingEntity.setLastDamageCause(new EntityDamageEvent(livingEntity, EntityDamageEvent.DamageCause.VOID, livingEntity.getHealth()));
                    livingEntity.setHealth(0);
                });
            }
        }
        this.health = Math.min(this.health, Math.min(livingEntity.getHealth(), nowHealth));
    }

    @Override
    public void startTick(@Nonnull LivingEntity livingEntity) {
        this.health = livingEntity.getHealth();
    }

    @Override
    public boolean isSync() {
        return false;
    }
}
