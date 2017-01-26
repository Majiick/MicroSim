/**
 * Created by Ecoste on 1/24/2017.
 */
import java.awt.geom.Point2D;
import java.util.*;

public class Driver extends GameObject implements IComponent {

    Vector<Trigger> triggers = new Vector<>();

    Driver(Micro_Sim processing, GameObject parent) {
        super(processing);
        SetParent(parent);
    }

    public void Start() {
        triggers.add(new ChronoTrigger(this, processing));
        size = new Point2D.Float(5, 5);
        tag = "Driver";
    }

    public void Update() {
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
        ((Organism)parent).Move(0, 20);
    }

    public void OnCollisionEnter(GameObject other) {

    }
}
