package alpsbte.warp.main.core.system;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.database.DatabaseConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static net.kyori.adventure.text.Component.text;

public class Home {
    private final String name;

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private World world;

    private Country country;

    public Home(String name, String uuid) {
        String correctName = name;

        // Get Values from Database
        try (ResultSet rsWarp = DatabaseConnection.createStatement("SELECT x, y, z, yaw, pitch, world, name, uuid FROM homes WHERE name = ? AND uuid = ?").setValue(name).setValue(uuid).executeQuery()) {
            rsWarp.next();

            x = rsWarp.getDouble(1);
            y = rsWarp.getDouble(2);
            z = rsWarp.getDouble(3);
            yaw = rsWarp.getFloat(4);
            pitch = rsWarp.getFloat(5);
            world = Bukkit.getWorld(rsWarp.getString(6));

            // get correct case name
            correctName = rsWarp.getString(7);
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while getting data from the database!"), e);
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

    public Location getLocation() {
        return new Location(world, x, y, z, yaw, pitch);
    }

    // Static Methods
    public static boolean exists(String name, String uuid) {
        try (ResultSet rsHome = DatabaseConnection.createStatement("SELECT 1 FROM homes WHERE name = ? AND uuid = ?").setValue(name).setValue(uuid).executeQuery()) {
            return rsHome.next();
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while getting data from the database!"), e);
        }
        return false;
    }

    public static void addHome(String uuid, String name, Location location) {
        try {
            DatabaseConnection.createStatement("INSERT INTO homes (uuid, name, x, y, z, yaw, pitch, world) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
                    .setValue(uuid)
                    .setValue(name)
                    .setValue(location.getX())
                    .setValue(location.getY())
                    .setValue(location.getZ())
                    .setValue(location.getYaw())
                    .setValue(location.getPitch())
                    .setValue(location.getWorld().getName())
                    .executeUpdate();
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while getting data from the database!"), e);
        }
    }

    public static void removeHome(String uuid, String name) {
        try {
            DatabaseConnection.createStatement("DELETE FROM homes WHERE uuid = ? AND name = ?").setValue(uuid).setValue(name).executeUpdate();
        }catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while getting data from the database!"), e);
        }
    }

    public static int homeCount(String uuid) {
        try (ResultSet rsHome = DatabaseConnection.createStatement("SELECT COUNT(uuid) FROM homes WHERE uuid = ?").setValue(uuid).executeQuery()) {
            rsHome.next();
            return rsHome.getInt(1);
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while getting data from the database!"), e);
        }
        return 0;
    }

    public static List<Home> getHomeList(String uuid) {
        List<Home> homeList = new ArrayList<>();
        try (ResultSet rsHome = DatabaseConnection.createStatement("SELECT name, uuid FROM homes WHERE uuid = ?").setValue(uuid).executeQuery()) {
            while (rsHome.next()) {
                homeList.add(new Home(rsHome.getString(1),rsHome.getString(2)));
            }
            Main.getPlugin().getComponentLogger().info(text("Successfully cached WarpPlate List!"));
        } catch (SQLException e) {
            Main.getPlugin().getComponentLogger().error(text("An error occurred while getting data from the database!"), e);
        }
        return homeList;
    }
}
