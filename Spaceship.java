import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Spaceship {
    // Variables
    int spacecraftID;
    String shipName;
    double fuelCap;
    double currentFuel;
    // ArrayList for the names of astronauts
    ArrayList<String> astroNames = new ArrayList<>();
    // SQL Lines
    String sqlInput;
    String url = "jdbc:sqlite:spaceprogram.db";
    // Gets all info from SQL
    public Spaceship(ResultSet rs) throws SQLException {
        shipName = rs.getString("name");
        fuelCap = rs.getDouble("maxFuel");
        currentFuel = rs.getDouble("currentFuel");
        spacecraftID = rs.getInt("id");
    }

    // Creates new spaceship
    public Spaceship(String shipName, double fuelCap) {
        this.shipName = shipName;
        this.fuelCap = fuelCap;
        currentFuel = 0;
        //SQL Line
        sqlInput = "INSERT INTO Spacecraft(name, maxFuel, currentFuel) VALUES(?, ?, ?)";
        // Inputs info to SQL and saves.
        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sqlInput)) {

            for (int i = 0; i < 3; i++) {
                pstmt.setString(2, shipName);
                pstmt.setDouble(3, fuelCap);
                pstmt.setDouble(4, currentFuel);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
        // SQL line to get ID
        sqlInput = "SELECT * FROM Ships;";
        // Get ID
        try (var conn = DriverManager.getConnection(url);
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(sqlInput)) {

            while (rs.next()) {
                spacecraftID = rs.getInt("id");
            }
        } catch (SQLException e) {
            // If an error occurs, display error.
            System.err.println(e.getMessage());
        }
    }

    public String getAstro(int i) {
        return astroNames.get(i);
    }
    // Refuel
    public String refuel(double amount) {
        // If possible, refuel the ship by requested amount.
        if (currentFuel + amount <= fuelCap) {
            currentFuel = currentFuel + amount;
            return shipName + " has been refueled. Current fuel level: " + currentFuel + " liters. ";
        } else {
            // If not, let the user know.
            return "Cannot refuel beyond the spacecraft's fuel capacity.";
        }
    }
    // Get spacecraft name
    public String getName() {
        return shipName;
    }
    // Get spacecraft fuel capacity
    public double getCap() {
        return fuelCap;
    }
    // Get current fuel
    public double getCurrent() {
        return currentFuel;
    }
    // Get ID
    public int getID() {
        return spacecraftID;
    }

    public void setCurrent(double currentFuel) {
        this.currentFuel = currentFuel;
    }
    // Adds astronauts
    public void addAstro(String name) {
        // If there are not 10 astronauts on the ship already, then add.
        if (astroNames.size() != 10) {
            astroNames.add(name);
        } else {
            // If at max capacity, let the user know.
            System.out.println("The ship is already at max capacity.");
        }

    }
    // Removes the astronaut from the astronaut list.
    public void removeAstro(String name) {
        astroNames.remove(name);
        System.out.println("Astronaut removed successfully");
    }
    // Shows all astronauts on ship
    public void showAstro() {
        System.out.println("Astronauts: " + astroNames.size());
        for (int i = 0; i < astroNames.size(); i++) {
            // Loop and display all astronaut names
            System.out.println(astroNames.get(i));
        }
    }

    public int getSize() {
        return astroNames.size() + 1;
    }
    // Show all info on the specified ship
    public void fullShow() {
        System.out.println("Name: " + getName());
        System.out.println("Fuel Capacity: " + getCap());
        System.out.println("Current Fuel: " + getCurrent());
        if (astroNames.size() == 0) {
            // If there are no astronauts, say so
            System.out.println("No astronauts assigned to ship.");
        } else {
            // Else, show astronauts
            showAstro();
        }
    }
}
