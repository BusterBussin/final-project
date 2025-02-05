import java.util.ArrayList;

public class Spaceship {
    String shipName;
    double fuelCap;
    ArrayList<String> astroNames = new ArrayList<>();
    double currentFuel;

    public Spaceship(String shipName, double fuelCap) {
        this.shipName = shipName;
        this.fuelCap = fuelCap;
        currentFuel = 0;
    }
    
    public String refuel(double amount) {
        if(currentFuel + amount <= fuelCap) {
            currentFuel = currentFuel + amount;
            return shipName + " has been refueled. Current fuel level: " + currentFuel + " liters. ";
        }
        else {
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
}
