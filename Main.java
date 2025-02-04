import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) {
        String password = "greenbeaneatingmachine";
        String inputPassword = "a";
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
        boolean status = false;
        String numCheck;
        Scanner scan = new Scanner(System.in);
        ArrayList<Astronaut> astros = new ArrayList<>();
        ArrayList<Spaceship> space = new ArrayList<>();
        while (!inputPassword.equals(password)) {
            System.out.println("Please enter the password.");
            inputPassword = scan.nextLine();
            if (!inputPassword.equals(password)) {
                System.out.println("That's the wrong password.");
            }
        }
        while (option != 'Z') {
            System.out.println("Please select an option");
            System.out.println("A) Manage Astronauts");
            System.out.println("B) Manage Spaceships");
            System.out.println("Z) Exit");
            option = scan.nextLine().toUpperCase().charAt(0);
            while (option == 'A') {
                option = ' ';
                while (option != 'Z') {
                    System.out.println("Current number of astronauts: " + astros.size());
                    System.out.println("Select what to do");
                    System.out.println("A) Add Astroanuts");
                    System.out.println("B) Display Astronauts");
                    System.out.println("C) Edit Astronaut");
                    System.out.println("Z) Stop");
                    option = scan.nextLine().toUpperCase().charAt(0);
                    switch (option) {
                        case 'A':
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
                            while (!email.contains("@")) {
                                System.out.println("Invalid email. Enter email.");
                                email = scan.nextLine();
                            }
                            System.out.println(
                                    "Enter phone number. (Enter without dashes or parantheses, will autoformat)");
                            numCheck = scan.nextLine();
                            while (!numCheck.matches("\\d{10}")) {
                                System.out.println("Invalid number. Please enter a valid phone number");
                                numCheck = scan.nextLine();
                            }
                            number = Long.parseLong(numCheck);
                            System.out.println("Enter payrate (In form 0.00)");
                            rate = scan.nextDouble();
                            scan.nextLine();
                            System.out.println("Enter weight (In form 0.00, in pounds)");
                            weight = scan.nextDouble();
                            scan.nextLine();
                            System.out.println("Enter next of kin's name");
                            kin = scan.nextLine();
                            System.out.println("Are they currently in space? (T for yes, F for no)");
                            option = scan.nextLine().toUpperCase().charAt(0);
                            while (option != 'T' && option != 'F') {
                                System.out.println(
                                        "Invalid input. Is the astronaut currently in space? (T for yes, F for no)");
                                option = scan.nextLine().toUpperCase().charAt(0);
                            }
                            if (option == 'T') {
                                status = true;
                            }
                            if (option == 'F') {
                                status = false;
                            }
                            Astronaut newAstronaut = new Astronaut(name, dob, serial, address, email, number, rate,
                                    weight, kin, status);
                            astros.add(newAstronaut);
                            System.out.println("Astronaut added successfully!");
                            break;
                        case 'B':
                            for (int i = 0; i < astros.size(); i++) {
                                System.out.println("Astronaut " + astros.size());
                                System.out.println(astros.get(i));
                            }
                            break;
                        case 'C':
                            System.out.println("Enter the name of the astronaut. Type cancel to cancel.");
                            input = scan.nextLine();
                            if (input.equalsIgnoreCase("cancel")) {
                                break;
                            }
                            for (Astronaut astro : astros) {
                                if (astro.getName().equalsIgnoreCase(input)) {
                                    while (option != 'Z') {
                                        System.out.println("Editing astronaut: " + astro.getName());
                                        System.out.println("Select what to change.");
                                        System.out.println(
                                                "A) Name\nB) Date of Birth\nC) Serial\nD) Address\nE) Email\nF) Phone Number\nG)Pay Rate\nH) Weight\nI) Kin\nF) Status\nZ) Quit");
                                        option = scan.nextLine().toUpperCase().charAt(0);
                                        switch (option) {
                                            case 'A':
                                                System.out.println("Enter new name");
                                                name = scan.nextLine();
                                                astro.setName(name);
                                                option = ' ';
                                                break;
                                            case 'B':
                                                System.out.print("Enter new DOB (preferred in 2000-01-01)");
                                                dob = scan.nextLine();
                                                astro.setDOB(dob);
                                                option = ' ';
                                                break;
                                            case 'C':
                                                System.out.println("Enter new serial");
                                                serial = scan.nextInt();
                                                scan.nextLine();
                                                astro.setSerial(serial);
                                                option = ' ';
                                                break;
                                            case 'D':
                                                System.out.println("Enter new address");
                                                address = scan.nextLine();
                                                astro.setAddress(address);
                                                option = ' ';
                                                break;
                                            case 'E':
                                                System.out.println("Enter email.");
                                                email = scan.nextLine();
                                                while (!email.contains("@")) {
                                                    System.out.println("Invalid email. Enter email.");
                                                    email = scan.nextLine();
                                                }
                                                astro.setEmail(email);
                                                option = ' ';
                                                break;
                                            case 'F':
                                                System.out.println(
                                                        "Enter phone number. (Enter without dashes or parantheses, will autoformat)");
                                                numCheck = scan.nextLine();
                                                while (!numCheck.matches("\\d{10}")) {
                                                    System.out.println(
                                                            "Invalid number. Please enter a valid phone number");
                                                    numCheck = scan.nextLine();
                                                }
                                                number = Long.parseLong(numCheck);
                                                astro.setPhone(number);
                                                option = ' ';
                                                break;
                                            case 'G':
                                                System.out.println("Enter payrate (In form 0.00)");
                                                rate = scan.nextDouble();
                                                scan.nextLine();
                                                astro.setRate(rate);
                                                option = ' ';
                                                break;
                                            case 'H':
                                                System.out.println("Enter weight (In form 0.00, in pounds)");
                                                weight = scan.nextDouble();
                                                scan.nextLine();
                                                astro.setWeight(weight);
                                                option = ' ';
                                                break;
                                            case 'I':
                                                System.out.println("Enter next of kin's name");
                                                kin = scan.nextLine();
                                                astro.setKin(kin);
                                                option = ' ';
                                            case 'Z':
                                                System.out.println("Returning to main menu");
                                                break;
                                            default:
                                                System.out.println("invalid input.");
                                                option = ' ';
                                                break;
                                        }
                                    }
                                }
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
                option = ' ';
            }
            while (option == 'B') {
                option = ' ';
                while (option != 'Z') {
                    System.out.println("Current number of spaceships: " + space.size());
                    System.out.println("Enter what you'd like to do.");
                    System.out.println("A) Add Spaceships");
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
                        case 'Z':
                            System.out.println("Returning to the main menu.");
                            break;

                        default:
                            System.out.println("Invalid option.");
                            break;
                    }
                }
                option = ' ';
            }
        }
    }
}