import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
public class Launch {
    double blastOff = .9;
    double blastOffFail = 1 - blastOff;

    Timer timer = new Timer();
    int countdownStart = 10;
    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            int seconds = countdownStart;
            @Override
            public void run() {
                if(seconds >= 0) {
                    System.out.println(seconds);
                    seconds--;
                } else {
                    timer.cancel();
                    double engineFail = Math.random();
                    if(engineFail < blastOff){
                    System.out.println("Blast off!");
                    }
                    else {
                        System.out.println("Engine failed, try again");
                        // TODO: make it possile to try again
                    }
                }
            }
        }, 0, 1000);
    }

    
    double altitude = 0;
    double FuelBurnRate = 1;
    double speed = FuelBurnRate * 30;
    double swAlt = 70000;
    Timer Timer = new Timer();
    TimerTask missionTask = new TimerTask() {
        @Override
        public void run() {
            System.out.println("Spacewalk complete, spaceship losing altitude.");
        }
    };
    while (altitude <= swAlt) {
        try{
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
    double land = .95;
    double crash = 1 - land;
    public String ReturnToEarth() {
        double Land = Math.random();
        if(speed >= 3000) {
            System.out.println("The ship burned up.");
        }
        if(altitude <= 0) {
            if(Land < land) {
            System.out.println("The ship has landed safely. Astronauts may exit.");
            }
            else {
                System.out.println("Crash landing, everyone is dead.");
            }
        }
    }
}

