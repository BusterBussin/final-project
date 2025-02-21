import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class Launch {
    // fix all the errors IDK how
    double blastOff = .9;
    double blastOffFail = 1 - blastOff;
    // final double gravity = 9.81;
    // final double terminalVelocity = 300;
    // final double parachuteDeployAltitude = 10000;

    Timer timer = new Timer();
    int countdownStart = 10;

    // public void run() {
    //     timer.scheduleAtFixedRate(new TimerTask() {
    //         int seconds = countdownStart;

    //         @Override
            
    //     }, 0, 1000);
    //             if (seconds >= 0) {
    //                 System.out.println(seconds);
    //                 seconds--;
    //             } else {
    //                 timer.cancel();
    //                 double engineFail = Math.random();
    //                 if (engineFail < blastOff) {
    //                     System.out.println("Blast off!");
    //                 } else {
    //                     System.out.println("Engine failed, try again"); // Extra credit
    //                     // TODO: make it possile to try again
    //                 }
    //             }
    //         }

    // public void start() {

    // }

    double altitude = 0;
    double FuelBurnRate = 1;
    double speed = FuelBurnRate * 30;
    double swAlt = 70000;
    Timer Timer = new Timer();
    TimerTask missionTask = new TimerTask() {
        @Override
        public static void run() {
            System.out.println("Spacewalk complete, spaceship losing altitude.");
        while (altitude <= swAlt) {
        try {
            Thread.sleep(1000);
            altitude += speed;
            System.out.println("Spaceship altitude: " + altitude + " meters");
            if(altitude > swAlt) {
                System.out.println("Spaceship has reached 70000 meters, beginning spacewalk");
                Timer.schedule(missionTask, 30000);
            }
        } catch (InterruptedException e) {

        }
    }
}
         
    double land = .95;
    double crash = 1 - land;

    // public String ReturnToEarth() {
    // //TODO: make the gravity effect the return with the 9.81 m/s^2 formula p.s.
    // IDK what I'm doing
    // double Land = Math.random();
    // if(speed >= 3000) {
    // System.out.println("The ship burned up.");
    // }
    // if(altitude < 10000) {
    // if(Land < land) {
    // speed = 7;
    // System.out.println("Parachutes have been deployed.");
    // }
    // else {
    // System.out.println("Parachutes failed to deploy, say your prayers"); //Extra
    // credit
    // }
    // }
    // if(altitude <= 0) {
    // System.out.println("The ship has landed safely. Astronauts may exit.");
    // }

    // }
    public void ReturnToEarth() {
    final double GRAVITY = 9.81; // Earth's gravity in m/s^2
    final double TERMINAL_VELOCITY = 300; // Approximate safe descent speed limit
    final double PARACHUTE_DEPLOY_ALTITUDE = 10000; // Altitude threshold for parachute deployment

    while (altitude > 0) {
        // Increase speed due to gravity
        speed += GRAVITY;

        // Decrease altitude based on speed
        altitude -= speed;

        System.out.println("Altitude: " + altitude + " meters, Speed: " + speed + " m/s");

        // If altitude is below parachute deployment range
        if (altitude <= PARACHUTE_DEPLOY_ALTITUDE && speed > TERMINAL_VELOCITY) {
            if (Math.random() < land) { // Chance-based parachute deployment
                speed = 7; // Parachute slows descent
                System.out.println("Parachutes have been deployed.");
            } else {
                System.out.println("Parachutes failed to deploy, say your prayers.");
            }
        }

        // Simulate real-time descent
        try {
            Thread.sleep(500); // Slows down the loop to simulate time passing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Final landing check
    if (speed >= 3000) {
        System.out.println("The ship burned up.");
    } else if (altitude <= 0) {
        System.out.println("The ship has landed safely. Astronauts may exit.");
    }
}
    }                        
}
