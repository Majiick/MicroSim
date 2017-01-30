import java.awt.geom.Point2D;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Created by Ecoste on 1/30/2017.
 */
public class EyeTrigger extends Trigger {
    float radius;
    float dotProductRange;
    float coolDownTime;
    float coolDown;

    EyeTrigger(IComponent parent, Micro_Sim processing) {
        super(parent, processing);
    }

    public void Start() {
        radius = ThreadLocalRandom.current().nextInt(200, 800);
        coolDownTime = ThreadLocalRandom.current().nextInt(60, 200);
        dotProductRange = Helper.randomRange(0.6f, 0.95f);
        coolDown = coolDownTime;
    }

    public void Update() {
        coolDown--;
        if (coolDown > 0) {
            return;
        }

        GameObject organism = ((GameObject)parent).parent;
        float orgRotRadians = processing.radians(organism.rotation % 360);
        Point2D.Float orgDirection = Helper.radiansToVector(orgRotRadians);

        List<GameObject> food = processing.gameObjects.stream().filter(g -> g instanceof Food).collect(Collectors.toList());
        for (GameObject g : food) {
            Point2D.Float difference = new Point2D.Float(organism.GetGlobalPosition().x - g.GetGlobalPosition().x, organism.GetGlobalPosition().y - g.GetGlobalPosition().y);
            difference = Helper.normalized(difference);

            if (Helper.vectorDot(orgDirection, difference) * -1 > dotProductRange && g.GetGlobalPosition().distance(organism.GetGlobalPosition()) < radius) {
                parent.Trigger();
                break;
            }
        }
    }

}
