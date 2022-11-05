package DevRoomApplication.commands.user;

import DevRoomApplication.Core;
import DevRoomApplication.utils.game.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StatsCommand implements CommandExecutor {
    private final Core plugin;

    public StatsCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        Inventory stats = Bukkit.createInventory(player, 27, "Your stats");

        ItemStack kills = ItemUtils.createItem(Material.DIAMOND_SWORD, "&cKills", String.valueOf(plugin.databaseUtils.getKills(player.getUniqueId())));
        ItemStack deaths = ItemUtils.createItem(Material.SKELETON_SKULL, "&cDeaths", String.valueOf(plugin.databaseUtils.getDeaths(player.getUniqueId())));
        ItemStack kd = ItemUtils.createItem(Material.SKELETON_SKULL, "&cK/D", String.valueOf(plugin.databaseUtils.getKilLDeaths(player.getUniqueId())));

        stats.setItem(10, kills);
        stats.setItem(13, deaths);
        stats.setItem(16, kd);

        player.openInventory(stats);

        return true;
    }
}
