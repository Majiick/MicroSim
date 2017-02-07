/**
 * Created by Ecoste on 1/24/2017.
 */
import java.awt.geom.Point2D;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Driver extends GameObject implements IComponent {
    Vector<Trigger> triggers = new Vector<>();
    float distance = 0;

    Driver(Micro_Sim processing, GameObject parent) {
        super(processing);
        SetParent(parent);
    }

    Driver(Micro_Sim processing, float distance) {
        super(processing);
        SetParent(parent);
        this.distance = distance;
    }

    public void Start() {
        if (Helper.PercentageChance(80)) {
            triggers.add(new EyeTrigger(this, processing));
        } else {
            triggers.add(new ChronoTrigger(this, processing));
        }

        size = new Point2D.Float(5, 5);
        tag = "Driver";

        if (distance == 0) {
            distance = ThreadLocalRandom.current().nextInt(10, 120);
        }
    }

    public void Update() {
        if (!enabled) return;

        processing.pushMatrix();
        processing.translate(parent.position.x + 17, parent.position.y + 17);
        processing.rotate(processing.radians(parent.rotation + rotation));
        processing.translate(-parent.position.x - 17, -parent.position.y - 17);

        processing.fill(processing.color(255, 0, 0));
        processing.rect(GetGlobalPosition().x, GetGlobalPosition().y, 5, 5);

        processing.popMatrix();

        triggers.forEach(t -> t.Update());
    }

    public void Trigger() {
        ((Organism)parent).Move(0, distance);
    }

    public void OnCollisionEnter(GameObject other) {

    }

    public void AddTrigger(Trigger trigger) {
        triggers.add(trigger);
    }

    public Vector<Trigger> getTriggers() {
        return triggers;
    }
}
