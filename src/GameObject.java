/**
 * Created by Ecoste on 1/23/2017.
 */
import java.awt.Point;
import java.awt.geom.Point2D;

abstract public class GameObject {
    abstract public void Update();
    abstract public void Start();
    float rotation = 0;

    Point2D.Float position = new Point2D.Float(0, 0);
    GameObject parent = null;
    Micro_Sim processing;
    String tag = "untagged";

    GameObject(Micro_Sim processing) {
        this.processing = processing;
    }

    public void SetParent(GameObject parent) {
        this.parent = parent;
    }

    public void SetPosition(Point2D.Float position) {
        this.position = position;
    }

    public Point2D.Float GetGlobalPosition() {
        if (parent != null) {
            return new Point2D.Float(position.x + parent.position.x, position.y + parent.position.y);
        }

        return position;
    }

    void OnCollisionEnter() {

    }
}
