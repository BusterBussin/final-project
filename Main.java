import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static void main(String args[]) {
        //conects to database
        connect();
        //url for database
        String url = "jdbc:sqlite:spaceprogram.db";
        //Astronaut table.
        var createAstronautTable = "CREATE TABLE IF NOT EXISTS Astronauts (" +
                "   id INTEGER PRIMARY KEY," +
                "	AstroName text NOT NULL," +
                "   dob text NOT NULL," +
                "   serial INTEGER NOT NULL," +
                "   phone REAL NOT NULL," +
                "   address text NOT NULL," +
                "   email text NOT NULL," +
                "   rate REAL NOT NULL," +
                "   weight REAL NOT NULL," +
                "   kin text NOT NULL," +
                "   status BOOLEAN," +
                "   spacecraftID INTEGER" +
                ");";
        // Spaceship table
        var createShipTable = "CREATE TABLE IF NOT EXISTS Ships (" +
                "   id INTEGER PRIMARY KEY," +
                "	name text NOT NULL," +
                "   maxFuel REAL NOT NULL," +
                "   currentFuel REAL NOT NULL" +
                ");";
        // misc table
        var createGeneralTable = "CREATE TABLE IF NOT EXISTS General (" +
                "   id INTEGER PRIMARY KEY," +
                "   password INTEGER NOT NULL," +
                "   logins INTEGER NOT NULL" +
                ");";
        // sets attempts to 0
        var addZeroAttempts = "INSERT INTO ...";
        // command to get all from astronaut table and combine with spaceship table
        var getAllAstronauts = "SELECT * FROM Astronauts LEFT JOIN Ships ON Astronauts.spacecraftID=Ships.id;";
        // command to combine with spaceship table and combine with astronaut table
        var getAllSpace = "SELECT * FROM Ships LEFT JOIN Astronauts ON Ships.id=Astronauts.spacecraftID;";
        // command to get general table
        var getAllGeneral = "SELECT * FROM General;";
        // var getAstronautByEmail = "SELECT * Astronauts WHERE email =
        // 'kruskiej@baisd.net'";

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
            // stmt.execute(createGeneralTable);
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
        Scanner scan = new Scanner(System.in);
        // SELECT * FROM Astronauts LEFT JOIN spacecrafts.id on astronauts.spacecraftId;
        // two array lists, one for astronauts and one for spaceships
        ArrayList<Astronaut> astros = new ArrayList<>();
        ArrayList<Spaceship> space = new ArrayList<>();
        // try (var conn = DriverManager.getConnection(url);
        // var stmt = conn.createStatement();
        // var rs = stmt.executeQuery(getAllAstronauts)) {

        // while (rs.next()) {

        // for (int i = 0; i < rs.getRow(); i++) {
        // Astronaut loadAstro = new Astronaut(rs);
        // astros.add(loadAstro);
        // }
        // }
        // } catch (SQLException e) {
        // System.err.println(e.getMessage());
        // }

        // gets all astronaut and ship information off of SQL.
        try (var conn = DriverManager.getConnection(url);
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(getAllAstronauts)) {

            while (rs.next()) {
                for (int i = 0; i < rs.getRow(); i++) {
                    Spaceship loadSpace1 = new Spaceship(rs);
                    space.add(loadSpace1);

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
                if (shipLoad.getID() == astroLoad.getID()) {
                    shipLoad.addAstro(astroLoad.getName());
                }
            }
        }
        // If this is first time using the table, display.
        if (attempts == 0) {
            System.out.println("The password is 573219. Write this down.");
            // add an attempt so it can no longer be seen
            attempts += 1;
            // save to SQL
            sqlInput = "INSERT INTO General(logins) VALUES(?)";
            try (var conn = DriverManager.getConnection(url);
                    var pstmt = conn.prepareStatement(sqlInput)) {

                for (int i = 0; i < 3; i++) {
                    pstmt.setInt(1, attempts);
                    pstmt.executeUpdate();
                }

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
                            // System.out.println("Are they currently in space? (T for yes, F for no)");
                            // option = scan.nextLine().toUpperCase().charAt(0);
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
                                    if (astro.getName().equalsIgnoreCase(input)) {
                                        // Found.
                                        found = true;
                                        while (option != 'Z') {
                                            System.out.println("Editing astronaut: " + astro.getName());
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
                            System.out.println("Enter the name of the astronaut you would like to delete. Say cancel to cancel.");
                            input = scan.nextLine();
                            // Cancel if told to
                            if (input.equalsIgnoreCase("cancel")) {
                                System.out.println("Cancelling.");
                                // Found = true, since there was nothing to find anyway.
                                found = true;
                            } else {
                                // Else, take the input and find the astronaut.
                                for (Astronaut astro : astros) {
                                    if (input.equals(astro.getName())) {
                                        // Delete from SQL
                                        astro.astroDeleter();
                                        // Delete from arraylist.
                                        astros.remove(astro.getID() - 1);
                                        found = true;
                                    }
                                }
                            }
                            if (!found) {
                                // If not found, error out.
                                System.out.println("Astronaut name was invalid. Please check for spelling and capitalization.");
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
                    System.out.println("Current number of spaceships: " + space.size());
                    System.out.println("Enter what you'd like to do.");
                    System.out.println("A) Add Spaceships");
                    System.out.println("B) Refuel Ship");
                    System.out.println("C) Add Astronaut to Ship");
                    System.out.println("D) Remove Astronaut");
                    System.out.println("E) Show Info");
                    System.out.println("Z) Quit");
                    option = scan.nextLine().toUpperCase().charAt(0);
                    switch (option) {
                        case 'A':
                            System.out.println("Enter the spaceship name.");
                            shipName = scan.nextLine();
                            System.out.println("Enter the fuel cap.");
                            fuelCap = scan.nextInt();
                            scan.nextLine();
                            System.out.println("New spaceship " + shipName + " created.");
                            Spaceship newShip = new Spaceship(shipName, fuelCap);
                            space.add(newShip);
                            break;
                        case 'B':
                            input = " ";
                            System.out.println("Enter the name of the spacecraft. Say cancel to cancel.");
                            input = scan.nextLine();
                            for (Spaceship ship : space) {
                                if (ship.getName().equalsIgnoreCase(input)) {
                                    found = true;
                                    shipName = ship.getName();
                                    currentFuel = ship.getCurrent();
                                    fuelCap = ship.getCap();
                                    remainingSpace = fuelCap - currentFuel;
                                    System.out.println("Now viewing refuel settings for " + shipName);
                                    System.out.println("Max Capacity: " + fuelCap);
                                    System.out.println("Current Fuel: " + currentFuel);
                                    System.out.println("Available space: " + remainingSpace);
                                    if (remainingSpace == 0) {
                                        System.out.println("No room left. Cancelling refuel.");
                                        break;
                                    } else {
                                        System.out.println("Enter the amount to refuel.");
                                        refuel = scan.nextDouble();
                                        ship.refuel(refuel);
                                        break;
                                    }
                                } else if (input.equalsIgnoreCase("cancel")) {
                                    System.out.println("Cancelling.");
                                    break;
                                } else {
                                    System.out.println("No name found. Try again.");
                                    break;
                                }
                            }
                        case 'C':
                            System.out.println("Enter the name of the spaceship. Say cancel to cancel.");
                            input = scan.nextLine();
                            if (input.equalsIgnoreCase("cancel")) {
                                System.out.println("Cancelling.");
                                break;
                            } else {
                                for (Spaceship ship : space) {
                                    if (ship.getName().equalsIgnoreCase(input)) {
                                        System.out.println("Ship found. Enter the astronaut name.");
                                        input = scan.nextLine();
                                        for (Astronaut astro : astros) {
                                            if (astro.getName().equalsIgnoreCase(input)) {
                                                System.out.println("Astronaut found. Adding to ship.");
                                                ship.addAstro(astro.getName());
                                                found = true;
                                                astro.setID(ship.getID());
                                            }
                                        }
                                    }
                                }
                            }
                            if (!found) {
                                System.out.println(
                                        "Either ship name or astronaut name was not found. Please check for typos and try again.");
                            }
                            break;
                        case 'D':
                            System.out.println("Enter the name of the spaceship. Say cancel to cancel.");
                            input = scan.nextLine();
                            if (input.equalsIgnoreCase("cancel")) {
                                System.out.println("Cancelling.");
                                break;
                            } else {
                                for (Spaceship ship : space) {
                                    if (ship.getName().equalsIgnoreCase(input)) {
                                        System.out.println("Ship found. Enter the astronaut name.");
                                        input = scan.nextLine();
                                        for (Astronaut astro : astros) {
                                            if (astro.getName().equalsIgnoreCase(input)) {
                                                System.out.println("Astronaut found. Deleting from ship.");
                                                ship.removeAstro(astro.getName());
                                                found = true;
                                                astro.setID(0);
                                            }
                                        }
                                    }
                                }
                            }
                            if (!found) {
                                System.out.println(
                                        "Either ship name or astronaut name was not found. Please check for typos and try again.");
                            }
                            break;
                        case 'E':
                            System.out.println("Enter name of ship, all for all ship info, or cancel to cancel.");
                            input = scan.nextLine();
                            if (input.equalsIgnoreCase("cancel")) {
                                System.out.println("Cancelling.");
                                found = true;
                                break;
                            } else if (input.equalsIgnoreCase("all")) {
                                for (Spaceship ship : space) {
                                    ship.fullShow();
                                }
                                found = true;
                                break;
                            } else {
                                for (Spaceship ship : space) {
                                    if (ship.getName().equalsIgnoreCase(input)) {
                                        ship.fullShow();
                                        found = true;
                                        break;

                                    }
                                }
                            }
                            if (!found) {
                                System.out.println("Input does not match with any options. Check for misspells.");
                            }
                            break;
                        case 'Z':
                            System.out.println("Returning to the main menu.");
                            break;

                        default:
                            System.out.println("Invalid option.");
                            break;
                    }
                }
                found = false;
                option = ' ';
            }
        }
        scan.close();
    }
}