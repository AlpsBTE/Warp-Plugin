package alpsbte.warp.main.core.system;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.database.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.kyori.adventure.text.Component.text;

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
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while getting data from the database!"), e);
        }
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getHeadID() {
        return headID;
    }
}
