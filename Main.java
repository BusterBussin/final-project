import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    public static void connect() {
        // connection string, connects to database
        var url = "jdbc:sqlite:spaceprogram.db";
        // tries to connect to database
        try (var conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            // if unable to connect, display error.
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[]) throws InterruptedException {

        // conects to database
        connect();
        // url for database
        String url = "jdbc:sqlite:spaceprogram.db";
        // Astronaut table.
        var createAstronautTable = "CREATE TABLE IF NOT EXISTS Astronauts (" +
                "   id INTEGER PRIMARY KEY," +
                "	AstroName text," +
                "   dob text," +
                "   serial INTEGER," +
                "   phone REAL," +
                "   address text," +
                "   email text," +
                "   rate REAL," +
                "   weight REAL," +
                "   kin text," +
                "   status," +
                "   spacecraftID INTEGER" +
                ");";
        // Spaceship table
        var createShipTable = "CREATE TABLE IF NOT EXISTS Ships (" +
                "   id INTEGER PRIMARY KEY," +
                "	name text," +
                "   maxFuel REAL," +
                "   currentFuel REAL" +
                ");";
        // misc table
        var createGeneralTable = "CREATE TABLE IF NOT EXISTS General (" +
                "   id INTEGER PRIMARY KEY," +
                "   password INTEGER DEFAULT 573219 CHECK (password = 573219)," +
                "   logins INTEGER DEFAULT 0" +
                ");";
        // sets attempts to 0
        // command to get all from astronaut table and combine with spaceship table
        var getAllAstronauts = "SELECT Astronauts.*, Ships.id AS shipId, Ships.*\r\n" + //
                "FROM Astronauts  \r\n" + //
                "FULL JOIN Ships ON Astronauts.spacecraftID = Ships.id;\r\n" + //
                "";
        // command to get general table
        var getAllGeneral = "SELECT * FROM General;";

        try (var conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                var meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // creates new tables if they do not exist already
        try (var conn = DriverManager.getConnection(url);
                var stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createAstronautTable);
            stmt.execute(createShipTable);
            stmt.execute(createGeneralTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // variables used.
        var sqlInput = " ";
        int password = 573219;
        int inputPassword = 0;
        int attempts = 0;
        char option = 'A';
        String input;
        boolean found = false;
        String name;
        String dob;
        int serial;
        String address;
        String email;
        long number;
        double rate;
        double weight;
        String kin;
        String shipName;
        double fuelCap;
        double currentFuel;
        double remainingSpace;
        double refuel;
        boolean status = false;
        String numCheck;
        // Scanner
        Scanner scan = new Scanner(System.in);
        // Ascii, to call ascii programs
        Ascii ascii = new Ascii();
        // two array lists, one for astronauts and one for spaceships
        ArrayList<Astronaut> astros = new ArrayList<>();
        ArrayList<Spaceship> space = new ArrayList<>();

        // gets all astronaut and ship information off of SQL.
        try (var conn = DriverManager.getConnection(url);
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(getAllAstronauts)) {

            while (rs.next()) {

                if (rs.getInt("serial") != 0 && rs.getInt("shipId") != 0) {
                    // We have a spacecraft AND astronaut in one row
                    Spaceship loadSpace1 = new Spaceship(rs);
                    if (space.size() != 0) {
                    for (Spaceship check : space) {
                        if (!loadSpace1.getName().equals(check.getName())) {
                            space.add(loadSpace1);
                        }
                    }
                } else {
                    space.add(loadSpace1);
                }
                    Astronaut loadAstro = new Astronaut(rs);
                    astros.add(loadAstro);
                } else if (rs.getInt("serial") == 0) {
                    // We have just a spacecraft
                    Spaceship loadSpace1 = new Spaceship(rs);
                    space.add(loadSpace1);
                } else {
                    // We have just an astronaut
                    Astronaut loadAstro = new Astronaut(rs);
                    astros.add(loadAstro);

                }

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // gets all misc info off SQL
        try (var conn = DriverManager.getConnection(url);
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(getAllGeneral)) {

            while (rs.next()) {
                attempts = rs.getInt("logins");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // loads astronauts into designated ships, if they have one.
        for (Spaceship shipLoad : space) {
            for (Astronaut astroLoad : astros) {
                if (shipLoad.getID() == astroLoad.spaceshipID) {
                    shipLoad.addAstro(astroLoad.name);
                }
            }
        }
        // If this is first time using the table, display.
        if (attempts == 0) {
            System.out.println("The password is 573219. Write this down.");
            // add an attempt so it can no longer be seen
            attempts += 1;
            // save to SQL
            sqlInput = "UPDATE General SET logins WHERE id = 1;";
            try (var conn = DriverManager.getConnection(url);
                    var pstmt = conn.prepareStatement(sqlInput)) {

                pstmt.setInt(1, attempts);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        // prompt for password
        while (inputPassword != password) {
            System.out.println("Please enter the password.");
            inputPassword = scan.nextInt();
            scan.nextLine();
            // Check if password is correct.
            if (inputPassword != password) {
                System.out.println("That's the wrong password.");
            }
        }
        // Main menu
        while (option != 'Z') {
            System.out.println("Please select an option");
            System.out.println("A) Manage Astronauts");
            System.out.println("B) Manage Spaceships");
            System.out.println("C) Launch");
            System.out.println("Z) Exit");
            // autoupper, takes the first letter
            option = scan.nextLine().toUpperCase().charAt(0);
            while (option == 'A') {
                option = ' ';
                while (option != 'Z') {
                    found = false;
                    // Astronaut screen
                    // Displays the number of astronauts using the number in the arraylist
                    System.out.println("Current number of astronauts: " + astros.size());
                    System.out.println("Select what to do");
                    System.out.println("A) Add Astroanuts");
                    System.out.println("B) Display Astronauts");
                    System.out.println("C) Edit Astronaut");
                    System.out.println("D) Delete Astronaut");
                    System.out.println("Z) Stop");
                    option = scan.nextLine().toUpperCase().charAt(0);
                    switch (option) {
                        case 'A':
                            // Creates the astronaut
                            System.out.println("Enter the name of the astronaut");
                            name = scan.nextLine();
                            System.out.println("Enter date of birth (Any format, preferably YYYY-MM-DD)");
                            dob = scan.nextLine();
                            System.out.println("Enter astronaut serial number.");
                            serial = scan.nextInt();
                            scan.nextLine();
                            System.out.println("Enter the address.");
                            address = scan.nextLine();
                            System.out.println("Enter email.");
                            email = scan.nextLine();
                            // Check for @ symbol.
                            while (!email.contains("@")) {
                                System.out.println("Invalid email. Enter email.");
                                email = scan.nextLine();
                            }
                            System.out.println(
                                    "Enter phone number. (Enter without dashes or parantheses, will autoformat)");
                            numCheck = scan.nextLine();
                            // Phone number must be 10 digits.
                            while (!numCheck.matches("\\d{10}")) {
                                System.out.println("Invalid number. Please enter a valid phone number");
                                numCheck = scan.nextLine();
                            }
                            // Takes numCheck and parses it to long.
                            number = Long.parseLong(numCheck);
                            System.out.println("Enter payrate (In form 0.00)");
                            rate = scan.nextDouble();
                            scan.nextLine();
                            System.out.println("Enter weight (In form 0.00, in pounds)");
                            weight = scan.nextDouble();
                            scan.nextLine();
                            System.out.println("Enter next of kin's name");
                            kin = scan.nextLine();
                            status = false;
                            // Registers astronaut into system.
                            Astronaut newAstronaut = new Astronaut(name, dob, serial, address, email, number, rate,
                                    weight, kin, status);
                            astros.add(newAstronaut);
                            System.out.println("Astronaut added successfully!");
                            break;
                        case 'B':
                            // Displays astronaut name.
                            for (int i = 0; i < astros.size(); i++) {
                                System.out.println("Astronaut " + astros.size());
                                System.out.println(astros.get(i));
                            }
                            break;
                        case 'C':
                            // Asks for an astronaut name
                            System.out.println("Enter the name of the astronaut. Type cancel to cancel.");
                            input = scan.nextLine();
                            // Cancel if needed
                            if (input.equalsIgnoreCase("cancel")) {
                                System.out.println("Cancelling.");
                                found = true;
                            } else {
                                // else, do this.
                                for (Astronaut astro : astros) {
                                    // Go through astros, find name.
                                    // If the astronaut name is the same as the input, trigger this.
                                    if (astro.name.equalsIgnoreCase(input)) {
                                        // Found.
                                        found = true;
                                        while (option != 'Z') {
                                            System.out.println("Editing astronaut: " + astro.name);
                                            System.out.println("Select what to change.");
                                            System.out.println(
                                                    "A) Name\nB) Date of Birth\nC) Serial\nD) Address\nE) Email\nF) Phone Number\nG)Pay Rate\nH) Weight\nI) Kin\nF) Status\nZ) Quit");
                                            option = scan.nextLine().toUpperCase().charAt(0);
                                            switch (option) {
                                                case 'A':
                                                    // Changes name
                                                    System.out.println("Enter new name");
                                                    name = scan.nextLine();
                                                    astro.setName(name);
                                                    option = ' ';
                                                    break;
                                                case 'B':
                                                    // Changes date of birth
                                                    System.out.print("Enter new DOB (preferred in 2000-01-01)");
                                                    dob = scan.nextLine();
                                                    astro.setDOB(dob);
                                                    option = ' ';
                                                    break;
                                                case 'C':
                                                    // Changes serial number
                                                    System.out.println("Enter new serial");
                                                    serial = scan.nextInt();
                                                    scan.nextLine();
                                                    astro.setSerial(serial);
                                                    option = ' ';
                                                    break;
                                                case 'D':
                                                    // Changes address
                                                    System.out.println("Enter new address");
                                                    address = scan.nextLine();
                                                    astro.setAddress(address);
                                                    option = ' ';
                                                    break;
                                                case 'E':
                                                    // Changes email
                                                    System.out.println("Enter email.");
                                                    email = scan.nextLine();
                                                    // check for @ symbol
                                                    while (!email.contains("@")) {
                                                        System.out.println("Invalid email. Enter email.");
                                                        email = scan.nextLine();
                                                    }
                                                    astro.setEmail(email);
                                                    option = ' ';
                                                    break;
                                                case 'F':
                                                    // Change phone number
                                                    System.out.println(
                                                            "Enter phone number. (Enter without dashes or parantheses, will autoformat)");
                                                    numCheck = scan.nextLine();
                                                    // Checks for 10 digits
                                                    while (!numCheck.matches("\\d{10}")) {
                                                        System.out.println(
                                                                "Invalid number. Please enter a valid phone number");
                                                        numCheck = scan.nextLine();
                                                    }
                                                    // parse to long
                                                    number = Long.parseLong(numCheck);
                                                    astro.setPhone(number);
                                                    option = ' ';
                                                    break;
                                                case 'G':
                                                    // Change payrate
                                                    System.out.println("Enter payrate (In form 0.00)");
                                                    rate = scan.nextDouble();
                                                    scan.nextLine();
                                                    astro.setRate(rate);
                                                    option = ' ';
                                                    break;
                                                case 'H':
                                                    // Change weight
                                                    System.out.println("Enter weight (In form 0.00, in pounds)");
                                                    weight = scan.nextDouble();
                                                    scan.nextLine();
                                                    astro.setWeight(weight);
                                                    option = ' ';
                                                    break;
                                                case 'I':
                                                    // Change next of kin
                                                    System.out.println("Enter next of kin's name");
                                                    kin = scan.nextLine();
                                                    astro.setKin(kin);
                                                    option = ' ';
                                                case 'Z':
                                                    // Returns to main menu
                                                    System.out.println("Returning to main menu");
                                                    break;
                                                default:
                                                    // If all else fails, give error.
                                                    System.out.println("invalid input.");
                                                    option = ' ';
                                                    break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (!found) {
                                // Astronaut not found.
                                System.out.println("Astronaut not found, please check for typos.");
                            }
                            break;
                        case 'D':
                            // Warn user.
                            System.out.println("THIS IS THE DELETION SERVICE. PLEASE REALLY CONSIDER YOUR NEXT MOVE.");
                            System.out.println(
                                    "Enter the name of the astronaut you would like to delete. Say cancel to cancel.");
                            input = scan.nextLine();
                            // Cancel if told to
                            if (input.equalsIgnoreCase("cancel")) {
                                System.out.println("Cancelling.");
                                // Found = true, since there was nothing to find anyway.
                                found = true;
                            } else {
                                // Else, take the input and find the astronaut.
                                // Make i, autoset to 0
                                int i = 0;
                                for (Astronaut astro : astros) {
                                    if (input.equals(astro.name)) {
                                        // Delete from SQL
                                        astro.astroDeleter();
                                        // Delete from arraylist.
                                        astros.remove(i);
                                        found = true;
                                        break;
                                    }
                                    // increase i
                                    i++;
                                }
                                // If found, update ID lists
                                // Reason why only if found is because it doesn't matter if no astronauts were
                                // deleted.
                                if (found) {
                                    for (Astronaut update : astros) {
                                        update.idGrab();
                                    }
                                }
                            }
                            if (!found) {
                                // If not found, error out.
                                System.out.println(
                                        "Astronaut name was invalid. Please check for spelling and capitalization.");
                            }

                        case 'Z':
                            // Returns to main menu.
                            System.out.println("Returning to the main menu.");
                            break;
                        default:
                            // user entered invalid option.
                            System.out.println("Invalid option.");
                            break;
                    }
                }
                // reset option and found
                option = ' ';
                found = false;
            }
            while (option == 'B') {
                option = ' ';
                while (option != 'Z') {
                    found = false;
                    // spaceship menu
                    System.out.println("Current number of spaceships: " + space.size());
                    System.out.println("Enter what you'd like to do.");
                    System.out.println("A) Add Spaceships");
                    System.out.println("B) Refuel Ship");
                    System.out.println("C) Add Astronaut to Ship");
                    System.out.println("D) Remove Astronaut");
                    System.out.println("E) Show Info");
                    System.out.println("F) Delete Ship");
                    System.out.println("Z) Quit");
                    // take input, autoupper at first letter
                    option = scan.nextLine().toUpperCase().charAt(0);
                    switch (option) {
                        case 'A':
                            // Add spaceship process.
                            System.out.println("Enter the spaceship name.");
                            shipName = scan.nextLine();
                            System.out.println("Enter the fuel cap.");
                            fuelCap = scan.nextInt();
                            scan.nextLine();
                            System.out.println("New spaceship " + shipName + " created.");
                            // makes a new spaceship object
                            Spaceship newShip = new Spaceship(shipName, fuelCap);
                            // adds to space arraylist
                            space.add(newShip);
                            break;
                        case 'B':
                            input = " ";
                            // ask for spacecraft name
                            System.out.println("Enter the name of the spacecraft. Say cancel to cancel.");
                            input = scan.nextLine();
                            for (Spaceship ship : space) {
                                // go through each spaceship object
                                if (ship.shipName.equalsIgnoreCase(input)) {
                                    // if the name is equal to input, select this.
                                    // found is true.
                                    found = true;
                                    // display name
                                    shipName = ship.shipName;
                                    // display the current amount of fuel already in there.
                                    currentFuel = ship.getCurrent();
                                    // display the capacity.
                                    fuelCap = ship.getCap();
                                    // the remaining space is the capacity subtracted by the current amount.
                                    remainingSpace = fuelCap - currentFuel;
                                    // show details
                                    System.out.println("Now viewing refuel settings for " + shipName);
                                    System.out.println("Max Capacity: " + fuelCap);
                                    System.out.println("Current Fuel: " + currentFuel);
                                    System.out.println("Available space: " + remainingSpace);
                                    if (remainingSpace == 0) {
                                        // since there's no space left, cancel refuel.
                                        System.out.println("No room left. Cancelling refuel.");
                                        break;
                                    } else {
                                        // since there is space left, refuel.
                                        System.out.println("Enter the amount to refuel.");
                                        // take a number from the user.
                                        refuel = scan.nextDouble();
                                        // call the refuel method
                                        ship.refuel(refuel);
                                        break;
                                    }
                                } else if (input.equalsIgnoreCase("cancel")) {
                                    // cancel the refuel
                                    System.out.println("Cancelling.");
                                    break;
                                } else {
                                    // Default.
                                    System.out.println("No name found. Try again.");
                                    break;
                                }
                            }
                        case 'C':
                            // Adding astronauts
                            // Prompt for spaceship name
                            System.out.println("Enter the name of the spaceship. Say cancel to cancel.");
                            input = scan.nextLine();
                            if (input.equalsIgnoreCase("cancel")) {
                                // Cancels the operation, sets found to true in order to skip error message.
                                System.out.println("Cancelling.");
                                found = true;
                                break;
                            } else {
                                // scan space arraylist for a spaceship named as the input.
                                for (Spaceship ship : space) {
                                    // If found
                                    if (ship.shipName.equalsIgnoreCase(input)) {
                                        // Prompt for astronaut name.
                                        System.out.println("Ship found. Enter the astronaut name.");
                                        input = scan.nextLine();
                                        // Search astros arraylist for the astronaut name.
                                        for (Astronaut astro : astros) {
                                            if (astro.name.equalsIgnoreCase(input)) {
                                                // If found, add to ship
                                                System.out.println("Astronaut found. Adding to ship.");
                                                // adds to ship
                                                ship.addAstro(astro.name);
                                                // set found to true
                                                found = true;
                                                // sets spacecraftID to the ship's id for SQL saving.
                                                astro.setID(ship.getID());
                                            }
                                        }
                                    }
                                }
                            }
                            if (!found) {
                                // If not found, show error.
                                System.out.println(
                                        "Either ship name or astronaut name was not found. Please check for typos and try again.");
                            }
                            break;
                        case 'D':
                            // removing astronauts.
                            // Prompt for ship name
                            System.out.println("Enter the name of the spaceship. Say cancel to cancel.");
                            input = scan.nextLine();
                            if (input.equalsIgnoreCase("cancel")) {
                                // Cancel.
                                System.out.println("Cancelling.");
                                // Found set to true to override error message.
                                found = true;
                                break;
                            } else {
                                // Scan space arraylist to find spacecraft name matching the input
                                for (Spaceship ship : space) {
                                    if (ship.shipName.equalsIgnoreCase(input)) {
                                        // if found, prompt for astronaut name.
                                        System.out.println("Ship found. Enter the astronaut name.");
                                        input = scan.nextLine();
                                        // scan astros arraylist for astronaut name matching with input.
                                        for (Astronaut astro : astros) {
                                            if (astro.name.equalsIgnoreCase(input)) {
                                                // If found, remove astronaut from ship.
                                                System.out.println("Astronaut found. Deleting from ship.");
                                                // Remove astronaut from ship's name arraylist
                                                ship.removeAstro(astro.name);
                                                // set found to true
                                                found = true;
                                                // set astronaut's ID to 0, the default number for astronauts with no
                                                // spacecraft.
                                                astro.setID(0);
                                            }
                                        }
                                    }
                                }
                            }
                            if (!found) {
                                // If not found, display error message.
                                System.out.println(
                                        "Either ship name or astronaut name was not found. Please check for typos and try again.");
                            }
                            break;
                        case 'E':
                            // Ship info display.
                            System.out.println("Enter name of ship, all for all ship info, or cancel to cancel.");
                            input = scan.nextLine();
                            if (input.equalsIgnoreCase("cancel")) {
                                // If cancel, cancel and set found to true to override error.
                                System.out.println("Cancelling.");
                                found = true;
                                break;
                            } else if (input.equalsIgnoreCase("all")) {
                                // Show info for all ships in arraylist, if prompted to.
                                for (Spaceship ship : space) {
                                    // Fully show details of current ship.
                                    ship.fullShow();
                                }
                                // Set found to true to override error.
                                found = true;
                                break;
                            } else {
                                // Scan ships that match with the input
                                for (Spaceship ship : space) {
                                    // Find ship that matches
                                    if (ship.shipName.equalsIgnoreCase(input)) {
                                        // Show info for that ship, set found to true.
                                        ship.fullShow();
                                        found = true;
                                        break;

                                    }
                                }
                            }
                            if (!found) {
                                // If not found, display error.
                                System.out.println("Input does not match with any options. Check for misspells.");
                            }
                            break;

                        case 'F':
                            // Warn user
                            System.out.println(
                                    "THIS IS THE DELETION SERVICE. IF THIS IS NOT WHERE YOU WANT TO BE, BACK OUT NOW.");
                            System.out.println("Y TO CANCEL, N TO PROCEED");
                            option = scan.nextLine().toUpperCase().charAt(0);
                            // Run this while the input doesn't match.
                            while (option != 'Y' && option != 'N') {
                                System.out.println("Not a valid option, please try again.");
                                option = scan.nextLine().toUpperCase().charAt(0);
                            }
                            // If yes, cancel
                            if (option == 'Y') {
                                System.out.println("Cancelling...");
                                break;
                                // Else, follow through
                            } else {
                                System.out.println("Please enter the name of the ship to delete.");
                                input = scan.nextLine();
                                // Scan the arraylist for a matching ship
                                for (Spaceship delete : space) {
                                    if (delete.shipName.equals(input)) {
                                        // Found the ship
                                        found = true;
                                        System.out.println("Deleting, please be patient.");
                                        // Set all astronaut spacecraft IDs to zero if they were on the ship, since the
                                        // ship isn't existent anymore.
                                        for (Astronaut remove : astros) {
                                            if (remove.spaceshipID == delete.spacecraftID) {
                                                remove.setID(0);
                                            }
                                        }
                                        delete.shipDeleter();
                                        // Remove from arraylist
                                        for (int i = 0; i < space.size(); i++) {
                                            if (space.get(i).spacecraftID == delete.spacecraftID) {
                                                space.remove(i);
                                            }
                                        }
                                    }
                                }
                                // Error out if not found
                                if (!found) {
                                    System.out.println("Spaceship not found, try again later.");
                                }
                            }
                        case 'Z':
                            // Returns to main menu.
                            System.out.println("Returning to the main menu.");
                            break;

                        default:
                            // By default, show error.
                            System.out.println("Invalid option.");
                            break;
                    }
                }
                // reset found and option.
                found = false;
                option = ' ';
            }
            while (option == 'C') {
                // Set all needed inputs to default.
                found = false;
                input = " ";
                option = ' ';
                // Print a warning
                System.out.println(
                        "You are now in the launching process. If your designated ship is ready, launch. Cancel now to back out. (Y/N)");
                // Take input from user
                option = scan.nextLine().toUpperCase().charAt(0);
                // While theinput is invalid, keep requesting input.
                while (option != 'Y' && option != 'N') {
                    System.out.println("Not a valid option. Press Y to cancel or N to proceed.");
                    option = scan.nextLine().toUpperCase().charAt(0);
                }
                // If yes, cancel.
                if (option == 'Y') {
                    System.out.println("Cancelling.");
                    break;
                }
                // Otherwise, request for spaceship name.
                System.out.println("Type the name of the spaceship needed. We will undergo checks for the spaceship.");
                input = scan.nextLine();
                // Find spaceship
                for (Spaceship ship : space) {
                    // If found
                    if (ship.getName().equals(input)) {
                        System.out.println("Spaceship found.");
                        // Set found to true
                        found = true;
                        // Start checks
                        // Check 1: Fuel
                        System.out.println("Minimal fuel required: 500 lbs");
                        System.out.println("spaceship fuel: " + ship.currentFuel + " lbs");
                        // If it lines up, proceed
                        if (ship.getCurrent() >= 500) {
                            // Check 2: People
                            System.out.println("Minimal capacity required: 3 people.");
                            System.out.println("Spaceship Capacity: " + ship.getSize() + " people");
                            // If good, proceed
                            if (ship.getSize() >= 3) {
                                // Start launch
                                System.out.println(ship.shipName + " is ready for takeoff. Prepare the engine...");
                                System.out.println("Engine prepared. Preparing for launch...");
                                // Start countdown, show ascii
                                ascii.asciiLaunch();
                                // Every second, count down one second

                                // Preset launch
                                for (int i = 10; i >= 0; i--) {
                                    // Print seconds
                                    System.out.println("T-Minus " + i + " seconds");
                                    // Take one away
                                    Thread.sleep(1000);
                                }
                                System.out.println("Launching...");
                                boolean blown = false;
                                int distance = 0;
                                int blow;
                                double speed = 0;
                                for (int i = 6; i >= 0; i--) {

                                    // If the ship has not blown up
                                    if (!blown) {
                                        // Increase distance by 10000m
                                        distance = distance + 10000;
                                        // Increase speed by 9.81m/s (so 442.94 for 10000m)
                                        speed = speed + 442.94;
                                        // Display stats
                                        System.out.println("The ship is currently at: " + distance + " meters.");
                                        System.out.println("The current speed is " + speed + " m/s.");
                                        // Use rng for failure
                                        blow = (int) (Math.random() * 100 + 1);
                                        // If rng = 2, blow up
                                        if (blow == 2) {
                                            System.out.println("The ship blew up. No survivors.");
                                            // Blown is true, cut loop
                                            blown = true;
                                            // Ship is no longer active
                                            ship.setStat(false);
                                        }
                                        Thread.sleep(1000);
                                    }
                                }

                                // If blown up
                                if (!ship.getStat()) {
                                    // Show blown
                                    ascii.asciiBlow();
                                    // use for loop
                                    for (int j = 0; j < ship.getSize(); j++) {
                                        // Loops through astros
                                        for (Astronaut astro : astros) {
                                            // If the astronaut's name is in the astro list
                                            if (astro.name.equals(ship.getAstro(j))) {
                                                // Delete astronaut entirely
                                                astro.astroDeleter();
                                                astros.remove(astro.getID());
                                            }
                                        }
                                    }
                                    // Delete ship
                                    ship.shipDeleter();
                                    space.remove(ship.getID());
                                    break;
                                }
                                System.out.println("We have made it to the moon.");

                                // Show moon
                                ascii.asciiMoon();
                                // Start moonwalk.
                                System.out.println("Starting moonwalk. Be patient, this takes 30 seconds.");
                                for (int k = 30; k >= 0; k--) {

                                    // Take one away
                                    Thread.sleep(1000);
                                }
                                // Show ship
                                ascii.asciiShip();
                                distance = 70000;
                                speed = 0;
                                for (int i = 7; i >= 0; i--) {

                                    // Decrease height by 10000m
                                    if (distance != 10000) {
                                        distance = distance - 10000;
                                        // Add speed
                                        speed = speed + 442.94;
                                        // Display stats
                                        System.out.println("Current altitude: " + distance + "m");
                                        System.out.println("Speed: " + speed + " m/s");
                                    }
                                    // Stop at 10000m
                                    // Take one away
                                    Thread.sleep(1000);
                                }
                                distance = 10000;
                                speed = 7;
                                for (int k = 11; k >= 0; k--) {

                                    // remove 700m (7x100, 100 seconds.)
                                    distance = distance - 700;
                                    if (distance > 0) {
                                        System.out.println("Current altitude: " + distance + "m");
                                        System.out.println("Speed: " + speed + " m/s");
                                    }
                                    Thread.sleep(1000);
                                }
                                System.out.println("All astronauts have landed safely.");
                                // Remove 500 fuel.
                                ship.setCurrent(ship.getCurrent() - 500);

                            } else {
                                // Else, display error message
                                System.out.println("Not enough people on board.");
                            }
                        } else {
                            // Else, display error message.
                            System.out.println("Not enough fuel. Please get the correct amount of fuel to proceed");
                        }
                    }
                }

                // NOTE: CUT HERE.
                if (!found) {
                    // If not found, display error
                    System.out.println("Invalid input. Check for spelling errors.");
                }
                // reset all.
                found = false;
                input = " ";
                option = ' ';

            }
        }
        // Close scanner.
        scan.close();
        System.exit(0);
    }
}
