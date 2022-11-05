package DevRoomApplication.commands.admin;

import DevRoomApplication.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetStartCommand implements CommandExecutor {
    private final Core plugin;

    public SetStartCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if(player.hasPermission("MobRunner.cmd.admin.setstart")){
            plugin.getConfig().set("startLocation.X", player.getLocation().getX());
            plugin.getConfig().set("startLocation.Y", player.getLocation().getY());
            plugin.getConfig().set("startLocation.Z", player.getLocation().getZ());
            plugin.getConfig().set("startLocation.Pitch", player.getLocation().getPitch());
            plugin.getConfig().set("startLocation.Yaw", player.getLocation().getYaw());
            plugin.getConfig().set("startLocation.World", player.getLocation().getWorld().getName());
        }

        player.sendMessage("Start point set!");

        return true;
    }
}
