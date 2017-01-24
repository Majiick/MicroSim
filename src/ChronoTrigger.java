/**
 * Created by Ecoste on 1/24/2017.
 */
import java.util.*;

public class ChronoTrigger extends Trigger{
    Timer timer = new Timer();

    ChronoTrigger(IComponent parent) {
        super(parent);
        Start();
    }

    public void Start() {
        timer.scheduleAtFixedRate(new ChronoTask(), 0, 1000);
    }

    private class ChronoTask extends TimerTask {
        public void run() {
            parent.Trigger();
        }
    }
}