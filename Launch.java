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
    public void spacewalk() {
        if(altitude > 70000) {
            //start 30 second timer
            
        }
    }
}
