package DevRoomApplication.utils.string;

import org.bukkit.ChatColor;

public class StringUtils {
    public static String convertString(String old){
        return ChatColor.translateAlternateColorCodes('&', old);
    }
}
