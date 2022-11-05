package DevRoomApplication.utils.game.mobs;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobUtils {
    static Map<Player, ArrayList<LivingEntity>> targets = new HashMap<>();
    static List<Player> alivePlayers = new ArrayList<>();
    static Map<Player, Integer> counts= new HashMap<>();
    static List<Player> playersInGame = new ArrayList<>();

    public static boolean checkTarget(Player player, LivingEntity entity){
        if(targets.get(player).contains(entity)) return true;

        return false;
    }

    public static Map<Player, ArrayList<LivingEntity>> getTargets(){
        return targets;
    }

    public static List<Player> getAlivePlayers(){
        return alivePlayers;
    }

    public static Map<Player, Integer> getCounts(){
        return counts;
    }

    public static List<Player> getPlayersInGame(){
        return playersInGame;
    }
}
