import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
public class Launch {
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
                    // for extra credit make an engine failure outcome 10% chance
                    System.out.println("Blast off!");
                }
            }
        }, 0, 1000);
    }

    double speed;
    double altitude;
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
}

