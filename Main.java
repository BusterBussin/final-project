import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public void main(String args[]) {
        String password = "greenbeaneatingmachine";
        String inputPassword = "a";
        char option = 'A';
        int numAstro = 0;
        String name;
        String dob;
        int serial;
        String address;
        String email;
        int number;
        double rate;
        double weight;
        String kin;
        boolean status = false;
        String phoneNum;
        String numCheck;
        Scanner scan = new Scanner(System.in);
        ArrayList<Astronaut> astros = new ArrayList<>();
        while (!inputPassword.equals(password)) {
            System.out.println("Please enter the password.");
            inputPassword = scan.nextLine();
            if (!inputPassword.equals(password)) {
                System.out.println("That's the wrong password.");
            }
        }
        while (option != 'Z') {
            System.out.println("Please select an option");
            System.out.println("A) Edit Astronauts");
            System.out.println("Z) Exit");
            option = scan.nextLine().toUpperCase().charAt(0);
            while (option == 'A') {
                option = ' ';
                while (option != 'Z') {
                    System.out.println("Current number of astronauts: " + astros.size());
                    System.out.println("Select what to do");
                    System.out.println("A) Add Astroanuts");
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
                            System.out.println("Enter phone number. (Enter without dashes or parantheses, will autoformat)");
                            number = scan.nextInt();
                            scan.nextLine();
                            numCheck = String.valueOf(number);
                            while(numCheck.length() != 10) {
                                System.out.println("Invalid number. Please enter a valid phone number");
                                number = scan.nextInt();
                                scan.nextLine();
                                numCheck = String.valueOf(number);
                            }
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
                                System.out.println("Invalid input. Is the astronaut currently in space? (T for yes, F for no)");
                                option = scan.nextLine().toUpperCase().charAt(0);
                            }
                            if (option == 'T') {
                                status = true;
                            }
                            if (option == 'F') {
                                status = false;
                            }
                            Astronaut newAstronaut = new Astronaut(name, dob, serial, address, email, number, rate, weight, kin, status);
                            astros.add(newAstronaut);
                            System.out.println("Astronaut added successfully!");
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