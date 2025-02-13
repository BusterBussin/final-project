import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Spaceship {
    String url = "jdbc:sqlite:spaceprogram.db";
    String shipName;
    double fuelCap;
    ArrayList<String> astroNames = new ArrayList<>();
    double currentFuel;
    String sqlInput;

    public Spaceship(ResultSet rs) throws SQLException {
        shipName = rs.getString("name");
        fuelCap = rs.getDouble("maxFuel");
        currentFuel = rs.getDouble("currentFuel");
    }

    public Spaceship(String shipName, double fuelCap) {
        this.shipName = shipName;
        this.fuelCap = fuelCap;
        currentFuel = 0;
        sqlInput = "INSERT INTO Ships(name, maxFuel, currentFuel) VALUES (?, ?, ?)";
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {
            pstmt.setString(1, shipName);
            pstmt.setDouble(2, fuelCap);
            pstmt.setDouble(3, currentFuel);
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

    public void addAstro(String name) {
        if (astroNames.size() != 10) {
            astroNames.add(name);
        } else {
            System.out.println("The ship is already at max capacity.");
        }

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
