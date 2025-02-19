import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Spaceship {
    int spacecraftID;
    String shipName;
    double fuelCap;
    ArrayList<String> astroNames = new ArrayList<>();
    double currentFuel;
    String sqlInput;
    String url = "jdbc:sqlite:spaceprogram.db";

    public Spaceship(ResultSet rs) throws SQLException {
        shipName = rs.getString("name");
        fuelCap = rs.getDouble("maxFuel");
        currentFuel = rs.getDouble("currentFuel");
        spacecraftID = rs.getInt("id");
    }
    // SELECT * FROM Astronaut WHERE spacecraftID=1
    public Spaceship(String shipName, double fuelCap) {
        this.shipName = shipName;
        this.fuelCap = fuelCap;
        currentFuel = 0;
        sqlInput = "INSERT INTO Spacecraft(name, maxFuel, currentFuel) VALUES(?, ?, ?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(2, shipName);
                pstmt.setDouble(3, fuelCap);
                pstmt.setDouble(4, currentFuel);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        sqlInput = "SELECT * FROM Ships;";
        try (var conn = DriverManager.getConnection(url);
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(sqlInput)) {

            while (rs.next()) {
                spacecraftID = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public String refuel(double amount) {
        if (currentFuel + amount <= fuelCap) {
            currentFuel = currentFuel + amount;
            return shipName + " has been refueled. Current fuel level: " + currentFuel + " liters. ";
        } else {
            return "Cannot refuel beyond the spacecraft's fuel capacity.";
        }
    }

    public String getName() {
        return shipName;
    }

    public double getCap() {
        return fuelCap;
    }

    public double getCurrent() {
        return currentFuel;
    }

    public int getID() {
        return spacecraftID;
    }

    public void addAstro(String name) {
        astroNames.add(name);
    }

    public void showAstro() {
        System.out.println("Astronauts: " + astroNames.size());
        for (int i = 0; i < astroNames.size(); i++) {
            System.out.println(astroNames.get(i));
        }
    }

    public void fullShow() {
        System.out.println("Name: " + getName());
        System.out.println("Fuel Capacity: " + getCap());
        System.out.println("Current Fuel: " + getCurrent());
        if (astroNames.size() == 0) {
            System.out.println("No astronauts assigned to ship.");
        } else {
            showAstro();
        }
    }
}
