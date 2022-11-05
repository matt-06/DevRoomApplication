package DevRoomApplication.commands.user;

import DevRoomApplication.Core;
import DevRoomApplication.utils.game.ItemUtils;
import DevRoomApplication.utils.game.adventurelib.TitleUtils;
import DevRoomApplication.utils.game.mobs.MobUtils;
import DevRoomApplication.utils.game.mobs.Zombie;
import DevRoomApplication.utils.string.StringUtils;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class StartCommand implements CommandExecutor {
    private final Core plugin;
    private TitleUtils titleUtils;

    public StartCommand(Core plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        titleUtils = new TitleUtils();

        double X = plugin.getConfig().getDouble("startLocation.X");
        double Z = plugin.getConfig().getDouble("startLocation.Z");
        double Y = plugin.getConfig().getDouble("startLocation.Y");
        float Pitch = (float) plugin.getConfig().getDouble("startLocation.Pitch");
        float Yaw = (float) plugin.getConfig().getDouble("startLocation.Yaw");
        String World = plugin.getConfig().getString("startLocation.World");

        Location startLocation = new Location(Bukkit.getWorld(World), X, Y, Z, Yaw, Pitch);

        try{
            player.teleport(startLocation);
        }catch (NullPointerException exception) {
            player.sendMessage(StringUtils.convertString("&cPlease set a start location!"));
            return true;
        }

        Zombie zombie = new Zombie(plugin);
        ItemUtils itemUtils = new ItemUtils();
        TitleUtils titleUtils = new TitleUtils();

        plugin.getConfig().set("Inventory." + player.getName() + ".items",  player.getInventory().getContents());

        if(!MobUtils.getPlayersInGame().contains(player)) MobUtils.getPlayersInGame().add(player);
        if(!MobUtils.getAlivePlayers().contains(player) ) MobUtils.getAlivePlayers().add(player);
        else if(!MobUtils.getPlayersInGame().contains(player)){
            player.sendMessage("You have already started the game!");
            return true;
        }

        player.getInventory().setContents(itemUtils.createStartInventory());
        player.getInventory().setHelmet(new ItemStack(ItemUtils.createItem(Material.DIAMOND_HELMET, "", "", 1, Enchantment.PROTECTION_ENVIRONMENTAL, 5)));
        player.getInventory().setChestplate(new ItemStack(ItemUtils.createItem(Material.DIAMOND_CHESTPLATE, "", "", 1, Enchantment.PROTECTION_ENVIRONMENTAL, 5)));
        player.getInventory().setLeggings(new ItemStack(ItemUtils.createItem(Material.DIAMOND_LEGGINGS, "", "", 1, Enchantment.PROTECTION_ENVIRONMENTAL, 5)));
        player.getInventory().setBoots(new ItemStack(ItemUtils.createItem(Material.DIAMOND_BOOTS, "", "", 1, Enchantment.PROTECTION_ENVIRONMENTAL, 5)));

        new BukkitRunnable(){
            int seconds = 6;

            @Override
            public void run(){
                seconds--;

                titleUtils.showMyTitle(plugin.adventure.player(player), String.valueOf(seconds), seconds>3 ? NamedTextColor.GREEN : NamedTextColor.RED);
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 50, seconds >3 ? 50 : -20);

                if(seconds == 1) cancel();
            }
        }.runTaskTimer(plugin, 0, 20);

        zombie.spawnSuperZombie(player, plugin.getConfig().getLong("monsters.super_zombie.spawn_time"), plugin.getConfig().getInt("monsters.super_zombie.entities"));

        return true;
    }
}
