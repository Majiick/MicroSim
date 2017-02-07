import java.awt.geom.Point2D;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ecoste on 1/25/2017.
 */
public class Rotator extends GameObject implements IComponent {
    Vector<Trigger> triggers = new Vector<>();
    float angle;

    Rotator(Micro_Sim processing, GameObject parent) {
        super(processing);
        SetParent(parent);
        angle = ThreadLocalRandom.current().nextInt(-180, 180);
    }

    Rotator(Micro_Sim processing, float angle) {
        super(processing);
        SetParent(parent);
        this.angle = angle;
    }

    public void Start() {
        if (Helper.PercentageChance(20)) {
            triggers.add(new EdgeTrigger(this, processing));
        } else {
            triggers.add(new ChronoTrigger(this, processing));
        }

        size = new Point2D.Float(5, 5);
        tag = "rotator";
    }

    public void Update() {
        if (!enabled) return;

        processing.pushMatrix();
        processing.translate(parent.position.x + 17, parent.position.y + 17);
        processing.rotate(processing.radians(parent.rotation + rotation));
        processing.translate(-parent.position.x - 17,-parent.position.y - 17);

        processing.fill(processing.color(0, 0, 255));
        processing.rect(GetGlobalPosition().x, GetGlobalPosition().y, 5, 5);

        processing.popMatrix();

        triggers.forEach(t -> t.Update());
    }

    public void Trigger() {
        ((Organism)parent).Rotate(angle);
    }

    public void OnCollisionEnter(GameObject other) {

    }

    public Vector<Trigger> getTriggers() {
        return triggers;
    }

    public void AddTrigger(Trigger trigger) {
        triggers.add(trigger);
    }
}
