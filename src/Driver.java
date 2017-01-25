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
    }

    public void Update() {
        processing.fill(processing.color(255, 0, 0));
        processing.rect(GetGlobalPosition().x, GetGlobalPosition().y, 5, 5);
        triggers.forEach(t -> t.Update());
    }

    public void Trigger() {
        ((Organism)parent).Move(new Point2D.Float(0, 20));
    }
}
