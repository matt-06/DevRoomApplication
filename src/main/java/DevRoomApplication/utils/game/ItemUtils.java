package DevRoomApplication.utils.game;

import DevRoomApplication.utils.string.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    public static ItemStack createItem(Material material, String name, String lore){
        ItemStack item = new ItemStack(material);
        List<String> itemLore = new ArrayList<>();
        itemLore.add(StringUtils.convertString(lore));

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(StringUtils.convertString(name));
        if(lore != null) itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack createItem(Material material, String name, String lore, int quantity){
        ItemStack item = new ItemStack(material, quantity);

        List<String> itemLore = new ArrayList<>();
        itemLore.add(StringUtils.convertString(lore));

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(StringUtils.convertString(name));
        if(lore != null) itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack createItem(Material material, String name, String lore, int quantity, Enchantment enchantment, int level){
        ItemStack item = new ItemStack(material, quantity);

        List<String> itemLore = new ArrayList<>();
        itemLore.add(StringUtils.convertString(lore));

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(StringUtils.convertString(name));
        if(lore != null) itemMeta.setLore(itemLore);
        itemMeta.addEnchant(enchantment, level, true);
        item.setItemMeta(itemMeta);

        return item;
    }

    public ItemStack[] createStartInventory(){
        ItemStack[] inventory = new ItemStack[]{
                new ItemStack(createItem(Material.DIAMOND_SWORD, "&aVery good sword", "&cKill 'em all", 1, Enchantment.DAMAGE_ALL, 10)),
                new ItemStack(createItem(Material.BOW, "&cVery good bow", "&cMake them disappear like their daddy", 1 , Enchantment.ARROW_KNOCKBACK, 2)),
                new ItemStack(createItem(Material.ENCHANTED_GOLDEN_APPLE, "", "", 64)),
                new ItemStack(createItem(Material.NETHER_STAR, "&aGapple!", "", 3)),
                new ItemStack(createItem(Material.ARROW, "", "", 256)),
        };
        return inventory;
    }
}
