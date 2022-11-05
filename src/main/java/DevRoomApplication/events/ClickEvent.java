package DevRoomApplication.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickEvent implements Listener {
    @EventHandler
    public void clickEvent(InventoryClickEvent event){
        if(event.getView().getTitle().equals("Your stats")){
            event.setCancelled(true);
        }
    }
}
