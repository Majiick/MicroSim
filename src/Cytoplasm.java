import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Vector;

/**
 * Created by Ecoste on 1/23/2017.
 */
public class Cytoplasm extends GameObject implements IComponent{
    Vector<Trigger> triggers = new Vector<>();

    Cytoplasm(Micro_Sim processing) {
        super(processing);
    }

    Cytoplasm(Micro_Sim processing, GameObject parent) {
        super(processing);
        SetParent(parent);
    }

    Cytoplasm(Micro_Sim processing, Point2D.Float startingPosition) {
        super(processing);
        position = startingPosition;
    }

    public void Update() {
        if (!enabled) return;

        //This is a bit wonky but I'm not too familiar with processing.
        processing.pushMatrix();
        processing.translate(parent.position.x + 17, parent.position.y + 17);
        processing.rotate(processing.radians(parent.rotation + rotation));
        processing.translate(-parent.position.x - 17,-parent.position.y - 17);

        processing.fill(processing.color(0, 255, 0));
        processing.rect(GetGlobalPosition().x, GetGlobalPosition().y, 5, 5);

        processing.popMatrix();

        for (GameObject g : processing.gameObjects) {
            if (g == this) continue;

            g.CheckCollision(this);
        }
    }

    public void Start() {
        size = new Point2D.Float(5, 5);
        tag = "Cytoplasm";
    }

    public void Trigger() {

    }

    public void OnCollisionEnter(GameObject other) {
        if (other.tag == "Food" && !((Food)other).consumed) {
            ((Food)other).consumed = true;
            ((Organism)parent).addPoint();
        }
    }

    public Vector<Trigger> getTriggers() {
        return triggers;
    }
}
