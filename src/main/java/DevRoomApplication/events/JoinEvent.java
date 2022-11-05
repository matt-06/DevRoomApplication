package DevRoomApplication.events;

import DevRoomApplication.Core;
import DevRoomApplication.utils.game.mobs.Zombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    private final Core plugin;

    public JoinEvent(Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();

        plugin.databaseUtils.createPlayer(player);

        Zombie zombie = new Zombie(plugin);
    }
}
