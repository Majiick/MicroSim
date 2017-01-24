/**
 * Created by Ecoste on 1/24/2017.
 */
import java.util.*;

public class ChronoTrigger extends Trigger{
    Timer timer = new Timer();

    ChronoTrigger(IComponent parent) {
        super(parent);
    }

    public void Start() {
        timer.scheduleAtFixedRate(new ChronoTask(), 0, 100);
    }

    private class ChronoTask extends TimerTask {
        public void run() {
            parent.Trigger();
        }
    }
}