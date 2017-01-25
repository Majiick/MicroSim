import java.awt.geom.Point2D;
import java.util.Vector;

/**
 * Created by Ecoste on 1/25/2017.
 */
public class Rotator extends GameObject implements IComponent {
    Vector<Trigger> triggers = new Vector<>();

    Rotator(Micro_Sim processing, GameObject parent) {
        super(processing);
        SetParent(parent);
    }

    public void Start() {
        triggers.add(new ChronoTrigger(this, processing));
    }

    public void Update() {
        processing.pushMatrix();
        processing.translate(parent.position.x, parent.position.y);
        processing.rotate(processing.radians(parent.rotation + rotation));
        processing.translate(-parent.position.x,-parent.position.y);

        processing.fill(processing.color(0, 0, 255));
        processing.rect(GetGlobalPosition().x, GetGlobalPosition().y, 5, 5);

        processing.popMatrix();

        triggers.forEach(t -> t.Update());
    }

    public void Trigger() {
        ((Organism)parent).Rotate(45);
    }
}
