package alpsbte.warp.main.core.system;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.database.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.kyori.adventure.text.Component.text;

public class State {
    private final int ID;
    private String name;
    private String mainWarpName;

    public State(int ID) {
        this.ID = ID;

        // Get Values from Database
        try (ResultSet rsState = DatabaseConnection.createStatement("SELECT name, mainWarp_name FROM states WHERE id = ?").setValue(ID).executeQuery()) {
            rsState.next();
            name = rsState.getString(1);
            mainWarpName = rsState.getString(2);
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while getting data from the database!"), e);
        }
    }

    public int getID() { return ID; }

    public String getName() { return name; }

    public String getMainWarpName() { return mainWarpName; }
}
