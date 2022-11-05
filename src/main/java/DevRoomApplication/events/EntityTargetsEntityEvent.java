package DevRoomApplication.events;

import DevRoomApplication.utils.game.mobs.MobUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class EntityTargetsEntityEvent implements Listener {

    @EventHandler
    public void entityTargetEvent(EntityTargetLivingEntityEvent event){
        try {
            if (event.getTarget() instanceof Player && MobUtils.checkTarget((Player) event.getTarget(), (LivingEntity) event.getEntity())) return;
            else event.setCancelled(true);
        }catch(NullPointerException | ClassCastException exception){
            //rip
        }
    }
}
