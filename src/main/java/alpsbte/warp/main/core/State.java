package alpsbte.warp.main.core;

import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class State {
    private final int ID;
    private String name;
    private String mainWarpName;

    public State(int ID) {
        this.ID = ID;

        // Get Values from Database
        try (ResultSet rsCountry = DatabaseConnection.createStatement("SELECT name, mainWarp_name FROM states WHERE id = ?").setValue(ID).executeQuery()) {
            name = rsCountry.getString(1);
            mainWarpName = rsCountry.getString(2);
        } catch (SQLException throwables) {
            Bukkit.getLogger().log(Level.SEVERE,"An error occurred while getting state data from database!", throwables);
        }
    }

    public int getID() { return ID; }

    public String getName() { return name; }

    public String getMainWarpName() { return mainWarpName; }
}
