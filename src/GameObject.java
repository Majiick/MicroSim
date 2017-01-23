/**
 * Created by Ecoste on 1/23/2017.
 */
import java.awt.Point;

abstract public class GameObject {
    abstract public void Update();
    abstract public void Start();

    Point position = new Point(0, 0);
    GameObject parent = null;
    Micro_Sim processing;

    GameObject(Micro_Sim processing) {
        this.processing = processing;
    }

    public void SetParent(GameObject parent) {
        this.parent = parent;
    }

    public void SetPosition(Point position) {
        this.position = position;
    }

    public Point GetGlobalPosition() {
        if (parent != null) {
            return new Point(position.x + parent.position.x, position.y + parent.position.y);
        }

        return position;
    }

}
