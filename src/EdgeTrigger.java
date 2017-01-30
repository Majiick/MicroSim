import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ecoste on 1/30/2017.
 */
public class EdgeTrigger extends Trigger {
    float coolDownTime;
    float coolDown;

    EdgeTrigger(IComponent parent, Micro_Sim processing) {
        super(parent, processing);
    }

    public void Start() {
        coolDownTime = ThreadLocalRandom.current().nextInt(60, 300);
        coolDown = coolDownTime;
    }

    public void Update() {
        coolDown--;

        if (parent instanceof GameObject && outOfMap((GameObject)parent) && coolDown < 0) {
            parent.Trigger();
            coolDown = coolDownTime;
        }
    }

    private boolean outOfMap(GameObject g) {
        if (g.GetGlobalPosition().x < 0 || g.GetGlobalPosition().x > processing.width ||
                g.GetGlobalPosition().y < 0 || g.GetGlobalPosition().y > processing.height) {
            return true;
        }

        return false;
    }
}
