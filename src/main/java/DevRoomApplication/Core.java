package DevRoomApplication;

import DevRoomApplication.commands.admin.SetStartCommand;
import DevRoomApplication.commands.user.StartCommand;
import DevRoomApplication.commands.user.StatsCommand;
import DevRoomApplication.commands.user.StopCommand;
import DevRoomApplication.events.*;
import DevRoomApplication.utils.database.DatabaseConnection;
import DevRoomApplication.utils.database.DatabaseUtils;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Core extends JavaPlugin {
    public DatabaseConnection database;
    public DatabaseUtils databaseUtils;
    public static BukkitAudiences adventure;

    //TODO: stop, restore dell'inventario

    @Override
    public void onEnable() {
        registerCommands();
        registerEvent();
        registerDatabase();
        createConfig();

        adventure = BukkitAudiences.create(this);
        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled!");

        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }

        this.database.disconnect();
    }

    void registerCommands(){
        getCommand("setstart").setExecutor(new SetStartCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));
        getCommand("stop").setExecutor(new StopCommand(this));
        getCommand("stats").setExecutor(new StatsCommand(this));
    }

    void registerEvent(){
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new JoinEvent(this), this);
        pm.registerEvents(new EntityTargetsEntityEvent(), this);
        pm.registerEvents(new DeathEvent(this), this);
        pm.registerEvents(new InteractEvent(), this);
        pm.registerEvents(new ClickEvent(), this);
    }

    void createConfig(){
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    void registerDatabase(){
        this.database = new DatabaseConnection(this);
        this.databaseUtils = new DatabaseUtils(this);

        try {
            database.connect();
            databaseUtils.createTable();
        } catch (SQLException exception) {
            getLogger().warning("Database is not connected. Please make sure to insert the correct credentials in the config.yml file");
        }

        if(database.isConnected()){
            getLogger().info("Database connected");
        }
    }
}
