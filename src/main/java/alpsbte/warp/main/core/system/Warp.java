package alpsbte.warp.main.core.system;

import alpsbte.warp.main.core.database.DatabaseConnection;
import alpsbte.warp.main.core.system.Country;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;

public class Warp {
    private final String name;

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private World world;

    private Country country;

    public Warp(String name) {
        String correctName = name;

        // Get Values from Database
        try (ResultSet rsWarp = DatabaseConnection.createStatement("SELECT x, y, z, yaw, pitch, world, country_id, name FROM warps WHERE name = ?").setValue(name).executeQuery()) {
            rsWarp.next();

            x = rsWarp.getDouble(1);
            y = rsWarp.getDouble(2);
            z = rsWarp.getDouble(3);
            yaw = rsWarp.getFloat(4);
            pitch = rsWarp.getFloat(5);
            world = Bukkit.getWorld(rsWarp.getString(6));
            country = new Country(rsWarp.getInt(7));

            // get correct case name
            correctName = rsWarp.getString(8);
        } catch (SQLException throwables) {
            Bukkit.getLogger().log(Level.SEVERE,"An error occurred while getting warp data from database!", throwables);
        }

        this.name = correctName;
    }

    public String getName() { return name; }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public float getYaw() { return yaw; }
    public float getPitch() { return pitch; }
    public World getWorld() { return world; }

    public Country getCountry() { return country; }

    // Static Methods
    public static boolean exists(String name) {
        try (ResultSet rsWarp = DatabaseConnection.createStatement("SELECT 1 FROM warps WHERE name = ?").setValue(name).executeQuery()) {
            return rsWarp.next();
        } catch (SQLException throwables) {
            Bukkit.getLogger().log(Level.SEVERE,"An error occurred while getting warp data from database!", throwables);
        }
        return false;
    }

    public static void addWarp(String name, Location location) {
        try {
            DatabaseConnection.createStatement("INSERT INTO warps (name, x, y, z, yaw, pitch, world) VALUES (?, ?, ?, ?, ?, ?, ?)")
                    .setValue(name)
                    .setValue(location.getX())
                    .setValue(location.getY())
                    .setValue(location.getZ())
                    .setValue(location.getYaw())
                    .setValue(location.getPitch())
                    .setValue(location.getWorld().getName())
                    .executeUpdate();
        } catch (SQLException throwables) {
            Bukkit.getLogger().log(Level.SEVERE,"An error occurred while adding a warp to the database!", throwables);
        }
    }

    public static void removeWarp(String name) {
        try {
            DatabaseConnection.createStatement("DELETE FROM warps WHERE name = ?").setValue(name).executeUpdate();
        }catch (SQLException throwables) {
            Bukkit.getLogger().log(Level.SEVERE,"An error occurred while removing a warp from the database!", throwables);
        }
    }

    public static HashMap<Location, String> getWarpPlates() {
        HashMap<Location, String> plateList = new HashMap<>();

        // Get Values from Database
        try (ResultSet rsWarp = DatabaseConnection.createStatement("SELECT world, plate_x, plate_y, plate_z, name FROM warps " +
                "WHERE NOT plate_x=0 AND NOT plate_y=0 AND NOT plate_z=0").executeQuery()) {
            while (rsWarp.next()) {
                Location plateLocation = new Location(
                        Bukkit.getWorld(rsWarp.getString(1)),
                        rsWarp.getDouble("2"),
                        rsWarp.getDouble("3"),
                        rsWarp.getDouble("4"));

                plateList.put(plateLocation, rsWarp.getString("5"));
            }
            Bukkit.getLogger().log(Level.INFO, "Successfully cached WarpPlate List!");
        } catch (SQLException throwables) {
            Bukkit.getLogger().log(Level.SEVERE,"An error occurred while getting warp plate data from database!", throwables);
        }

        return plateList;
    }
}