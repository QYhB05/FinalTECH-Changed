package io.taraxacum.finaltech.core.listener;

import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.setup.FinalTechItems;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Final_ROOT
 * @see io.taraxacum.finaltech.core.item.unusable.Box
 */
public class BoxListener implements Listener {
    private final double height;

    public BoxListener(double height) {
        this.height = height;
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent playerDeathEvent) {
        Player player = playerDeathEvent.getEntity();
        Location location = player.getLocation();
        World world = location.getWorld();
        if (world != null) {
            int maxHeight = world.getMaxHeight();
            if (location.getY() >= maxHeight + this.height) {
                EntityDamageEvent lastDamageEvent = player.getLastDamageCause();
                if (lastDamageEvent != null && EntityDamageEvent.DamageCause.SUICIDE.equals(lastDamageEvent.getCause())) {
                    Optional<PlayerProfile> playerProfile = PlayerProfile.find(player);
                    if (playerProfile.isPresent()) {
                        List<Research> researchList = new ArrayList<>(playerProfile.get().getResearches());
                        if (!researchList.isEmpty()) {
                            playerProfile.get().setResearched(researchList.get(FinalTechChanged.getRandom().nextInt(researchList.size())), false);
                        }
                        player.sendMessage(FinalTechChanged.getLanguageString("items", "_FINALTECH_BOX", "message"));
                    }
                } else {
                    world.dropItem(location, FinalTechItems.BOX.getValidItem());
                }
            }
        }
    }
}
