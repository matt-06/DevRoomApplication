package DevRoomApplication.events;

import DevRoomApplication.Core;
import DevRoomApplication.utils.game.mobs.MobUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {
    private final Core plugin;

    public DeathEvent(Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void deathEvent(PlayerDeathEvent event){
        if(MobUtils.getAlivePlayers().contains(event.getEntity())){
            MobUtils.getAlivePlayers().remove(event.getEntity());
            Player player = event.getEntity();

            player.spigot().respawn();
            plugin.databaseUtils.addDeaths(player.getUniqueId());
            plugin.databaseUtils.setKillDeaths(player.getUniqueId());

            for (LivingEntity livingEntity : MobUtils.getTargets().get(player)) {
                livingEntity.setHealth(0);
            }

            MobUtils.getTargets().get(player).clear();
        }
    }

    @EventHandler
    public void entityDeathEvent(EntityDeathEvent event){
        Player player = event.getEntity().getKiller();

        if(MobUtils.getPlayersInGame().contains(player)){
            plugin.databaseUtils.addKills(player.getUniqueId());
            plugin.databaseUtils.setKillDeaths(player.getUniqueId());
        }
    }
}
