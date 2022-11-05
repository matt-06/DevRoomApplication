package DevRoomApplication.events;

import DevRoomApplication.utils.string.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InteractEvent implements Listener {
    @EventHandler
    public void interactEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if(event.getAction() != Action.RIGHT_CLICK_AIR
                || event.getAction() != Action.RIGHT_CLICK_BLOCK
                || event.getAction() != Action.RIGHT_CLICK_AIR
                || event.getAction() != Action.LEFT_CLICK_AIR
                || event.getAction() != Action.LEFT_CLICK_BLOCK){
            return;
        }

        if(event.getItem().equals(new ItemStack(Material.NETHER_STAR))){
            player.getInventory().remove(event.getItem());
            player.setHealth(20);
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 120, 5));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 2));
            player.setFoodLevel(20);
        }
    }
}
