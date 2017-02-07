/**
 * Created by Ecoste on 1/24/2017.
 */
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ChronoTrigger extends Trigger{
    //Timer timer = new Timer();
    int interval;

    ChronoTrigger(IComponent parent, Micro_Sim processing) {
        super(parent, processing);
        interval = ThreadLocalRandom.current().nextInt(10, 300 + 1);
    }

    ChronoTrigger(IComponent parent, Micro_Sim processing, int interval) {
        super(parent, processing);
        this.interval = interval;
    }

    public void Start() {

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