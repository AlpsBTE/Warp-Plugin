package alpsbte.warp.main.core.system;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.database.DatabaseConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static net.kyori.adventure.text.Component.text;

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
            //country = new Country(rsWarp.getInt(7));

            // get correct case name
            correctName = rsWarp.getString(8);
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while getting data from the database!"), e);
        }

        this.name = correctName;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public World getWorld() {
        return world;
    }

    public Location getLocation() {
        return new Location(world, x, y, z, yaw, pitch);
    }

    public Country getCountry() {
        return country;
    }

    public void addWarpPlate(Location location) {
        try {
            DatabaseConnection.createStatement("UPDATE warps SET plate_x = ?, plate_y = ?, plate_z = ? WHERE name = ?")
                    .setValue(location.getX())
                    .setValue(location.getY())
                    .setValue(location.getZ())
                    .setValue(name)
                    .executeUpdate();
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while updating data from the database!"), e);
        }
    }

    public void removeWarpPlate() {
        try {
            DatabaseConnection.createStatement("UPDATE warps SET plate_x = ?, plate_y = ?, plate_z = ? WHERE name = ?")
                    .setValue(0)
                    .setValue(0)
                    .setValue(0)
                    .setValue(name)
                    .executeUpdate();
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while updating data from the database!"), e);
        }
    }

    public Location getPlateLocation() {
        Location location = null;
        try (ResultSet rsWarp = DatabaseConnection.createStatement("SELECT name, world, plate_x, plate_y, plate_z FROM warps WHERE name = ?").setValue(name).executeQuery()) {
            rsWarp.next();

            location = new Location(
                    Bukkit.getWorld(rsWarp.getString(2)),
                    rsWarp.getDouble(3),
                    rsWarp.getDouble(4),
                    rsWarp.getDouble(5)
            );
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while getting data from the database!"), e);
        }
        return location;
    }

    // Static Methods
    public static boolean exists(String name) {
        try (ResultSet rsWarp = DatabaseConnection.createStatement("SELECT 1 FROM warps WHERE name = ?").setValue(name).executeQuery()) {
            return rsWarp.next();
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while getting data from the database!"), e);
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
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while adding data to the database!"), e);
        }
    }

    public static void removeWarp(String name) {
        try {
            DatabaseConnection.createStatement("DELETE FROM warps WHERE name = ?").setValue(name).executeUpdate();
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while deleting data from the database!"), e);
        }
    }

    public static HashMap<Location, String> getWarpPlates() {
        HashMap<Location, String> plateList = new HashMap<>();

        // Get Values from Database
        //"WHERE NOT plate_x=0 AND NOT plate_y=0 AND NOT plate_z=0"
        try (ResultSet rsWarp = DatabaseConnection.createStatement("SELECT name, world, plate_x, plate_y, plate_z FROM warps").executeQuery()) {
            while (rsWarp.next()) {
                Location plateLocation = new Location(
                        Bukkit.getWorld(rsWarp.getString(2)),
                        rsWarp.getDouble(3),
                        rsWarp.getDouble(4),
                        rsWarp.getDouble(5));

                if (!plateLocation.equals(new Location(plateLocation.getWorld(), 0, 0, 0))) {
                    plateList.put(plateLocation, rsWarp.getString(1));
                }
            }
            Main.getPlugin().getComponentLogger().info("Successfully cached WarpPlate list!");
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while getting data from the database!"), e);
        }

        return plateList;
    }

    public static List<String> getTabCompletionList() {
        List<String> list = new ArrayList<>();
        try (ResultSet rsWarp = DatabaseConnection.createStatement("SELECT name FROM warps").executeQuery()) {
            while (rsWarp.next()) {
                list.add(rsWarp.getString(1));
            }
            Main.getPlugin().getComponentLogger().info("Successfully cached warp tab completion list!");
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while getting data from the database!"), e);
        }

        return list;
    }
}