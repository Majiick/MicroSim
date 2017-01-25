/**
 * Created by Ecoste on 1/24/2017.
 */
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ChronoTrigger extends Trigger{
    Timer timer = new Timer();
    int interval;

    ChronoTrigger(IComponent parent, Micro_Sim processing) {
        super(parent, processing);
        interval = ThreadLocalRandom.current().nextInt(10, 300 + 1);
        Start();
    }

    public void Start() {
        //timer.scheduleAtFixedRate(new ChronoTask(), 0, 1000);
    }

    private class ChronoTask extends TimerTask {
        public void run() {
            //parent.Trigger();
        }
    }

    public void Update() {
        if (processing.frameCount % interval == 0) {
            parent.Trigger();
        }
    }
}