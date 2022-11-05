package DevRoomApplication.utils.database;

import DevRoomApplication.Core;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseUtils {
    private final Core plugin;

    public DatabaseUtils(Core plugin) {
        this.plugin = plugin;
    }

    public void createTable(){
        PreparedStatement ps;

        try {
            ps = plugin.database.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playerdata (name varchar(100) primary key," +
                    "uuid varchar(36), kills int, deaths int, kd float)"
            );
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlayer(Player player){
        try{
            UUID uuid = player.getUniqueId();
            if(!exists(uuid)){
                PreparedStatement ps1 = plugin.database.getConnection().prepareStatement("INSERT IGNORE INTO playerdata (name, uuid) VALUES (?,?)");
                ps1.setString(1, player.getName());
                ps1.setString(2, uuid.toString());

                ps1.executeUpdate();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean exists(UUID uuid){
        try{
            PreparedStatement ps = plugin.database.getConnection().prepareStatement("SELECT * FROM playerdata WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();
            if(results.next()){
                return true;
            }
            return false;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void addDeaths(UUID uuid){
        try {
            PreparedStatement ps = plugin.database.getConnection().prepareStatement("UPDATE playerdata SET deaths=? WHERE uuid=?");
            ps.setInt(1, (getDeaths(uuid) + 1));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getDeaths(UUID uuid){
        int deaths;

        try{
            PreparedStatement ps = plugin.database.getConnection().prepareStatement("SELECT deaths FROM playerdata WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();

            if(results.next()){
                deaths = results.getInt("deaths");
                return deaths;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void addKills(UUID uuid){
        try{
            PreparedStatement ps = plugin.database.getConnection().prepareStatement("UPDATE playerdata SET kills=? WHERE uuid=?");
            ps.setInt(1, (getKills(uuid) + 1));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }catch(SQLException exception){
            exception.printStackTrace();
        }
    }

    public int getKills(UUID uuid){
        int kills;

        try{
            PreparedStatement ps = plugin.database.getConnection().prepareStatement("SELECT kills FROM playerdata WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();

            if(results.next()){
                kills = results.getInt("kills");
                return kills;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void setKillDeaths(UUID uuid){
        try{
            PreparedStatement ps = plugin.database.getConnection().prepareStatement("UPDATE playerdata SET kd=? WHERE uuid=?");
            ps.setFloat(1, (float) getKills(uuid) / getDeaths(uuid));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        }catch(SQLException exception){
            exception.printStackTrace();
        }
    }

    public float getKilLDeaths(UUID uuid){
        float kilLDeaths;

        try{
            PreparedStatement ps = plugin.database.getConnection().prepareStatement("SELECT kd FROM playerdata WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();

            if(results.next()){
                kilLDeaths = results.getFloat("kd");
                return kilLDeaths;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}