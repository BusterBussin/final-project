import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    public void main(String args[]) {
        String password = "greenbeaneatingmachine";
        String inputPassword = "a";
        char option = 'A';
        int numAstro = 0;
        Scanner scan = new Scanner(System.in);
        while(!inputPassword.equals(password)) {
            System.out.println("Please enter the password.");
            inputPassword = scan.nextLine();
            if (!inputPassword.equals(password)) {
                System.out.println("That's the wrong password.");
            }
        }
        while(option != 'Z') {
        System.out.println("Please select an option");
        System.out.println("A) Edit Astronauts");
        System.out.println("Z) Exit");
        option = scan.nextLine().toUpperCase().charAt(0);
        while(option == 'A') {
            if (numAstro == 0) {
                System.out.println("Please enter the number of astronauts");
                numAstro = scan.nextInt();
                while (numAstro <= 0) {
                    System.out.println("Number must be above 0. Please enter a valid number.");
                    numAstro = scan.nextInt();
                }
                ArrayList<Astronaut> astros = new ArrayList<>();
            }
        }
        }
    }
}