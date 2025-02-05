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
        showAstro();
    }
}
