package DevRoomApplication.commands.user;

import DevRoomApplication.Core;
import DevRoomApplication.utils.game.mobs.MobUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StopCommand implements CommandExecutor {
    private final Core plugin;

    public StopCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if(!MobUtils.getPlayersInGame().contains(player)){
            player.sendMessage("You are not in game!");
            return true;
        }else{
            MobUtils.getPlayersInGame().remove(player);
            player.getInventory().setContents((ItemStack[]) plugin.getConfig().get("Inventory." + player.getName() + ".items",  player.getInventory().getContents()));

            for (LivingEntity livingEntity : MobUtils.getTargets().get(player)) {
                livingEntity.setHealth(0);
            }
        }

        return true;
    }
}
