package DevRoomApplication.utils.game.mobs;

import DevRoomApplication.Core;
import DevRoomApplication.utils.game.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Zombie {
    private final Core plugin;

    public Zombie(Core plugin) {
        this.plugin = plugin;
    }
    public void spawnSuperZombie(Player player, long ticks, int entities) {
        MobUtils.counts.put(player, 0);

        new BukkitRunnable() {
            int count = 0;
            ArrayList<LivingEntity> entityList = new ArrayList<>();

            @Override
            public void run() {
                if(!MobUtils.getPlayersInGame().contains(player)) cancel();

                if(!MobUtils.alivePlayers.contains(player)){
                    player.sendMessage("You died! Your kills: " + String.valueOf(plugin.databaseUtils.getKills(player.getUniqueId())));
                    MobUtils.playersInGame.remove(player);
                    cancel();
                }

                double X = plugin.getConfig().getDouble("startLocation.X");
                double Z = plugin.getConfig().getDouble("startLocation.Z");
                double Y = plugin.getConfig().getDouble("startLocation.Y");
                float Pitch = (float) plugin.getConfig().getDouble("startLocation.Pitch");
                float Yaw = (float) plugin.getConfig().getDouble("startLocation.Yaw");
                String World = plugin.getConfig().getString("startLocation.World");

                Location startLocation = new Location(Bukkit.getWorld(World), X, Y, Z, Yaw, Pitch);

                LivingEntity livingEntity = (LivingEntity) player.getWorld().spawnEntity(startLocation, EntityType.ZOMBIE);

                ItemStack weapon = ItemUtils.createItem(Material.valueOf(plugin.getConfig().getString("monsters.super_zombie.weapon")), "", "", 1, Enchantment.DAMAGE_ALL, 10);
                livingEntity.getEquipment().setItemInHand(weapon);
                livingEntity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                livingEntity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                livingEntity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                livingEntity.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                livingEntity.setMaxHealth(25);
                livingEntity.setHealth(25);

                for (Player user : Bukkit.getOnlinePlayers()) {
                    user.hideEntity(plugin, livingEntity);
                    player.showEntity(plugin, livingEntity);
                }

                count++;

                entityList.add(livingEntity);
                MobUtils.targets.put(player, entityList);

                if(count == entities) cancel();
            }
        }.runTaskTimer(plugin, ticks, entities);

    }
}
