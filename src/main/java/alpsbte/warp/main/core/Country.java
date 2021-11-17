package alpsbte.warp.main.core;

import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class Country {

    private final int ID;
    private String name;
    private String headID;

    public Country(int ID) {
        this.ID = ID;

        // Get Values from Database
        try (ResultSet rsCountry = DatabaseConnection.createStatement("SELECT name, head_id FROM countries WHERE id = ?").setValue(ID).executeQuery()) {
            rsCountry.next();
            name = rsCountry.getString(1);
            headID = rsCountry.getString(2);
        } catch (SQLException throwables) {
            Bukkit.getLogger().log(Level.SEVERE,"An error occurred while getting country data from database!", throwables);
        }
    }

    public int getID() { return ID; }

    public String getName() { return name; }

    public String getHeadID() { return  headID; }
}
