package DevRoomApplication.utils.database;


import DevRoomApplication.Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;
    private final Core plugin;

    public DatabaseConnection(Core plugin) {
        this.plugin = plugin;
    }

    public boolean isConnected(){
        return (connection != null);
    }

    public void connect() throws SQLException {
        String host = plugin.getConfig().getString("database.host");
        String port = plugin.getConfig().getString("database.port");
        String database = plugin.getConfig().getString("database.databasename");
        String username = plugin.getConfig().getString("database.username");
        String password = plugin.getConfig().getString("database.password");

        if(!isConnected()){
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
        }
    }

    public void disconnect(){
        if(isConnected()){
            try{
                connection.close();
            }catch(SQLException exception){
                exception.printStackTrace();
            }
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
