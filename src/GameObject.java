/**
 * Created by Ecoste on 1/23/2017.
 */
import java.awt.Point;

abstract public class GameObject {
    abstract public void Update();
    abstract public void Start();

    Point position;
    GameObject parent;
}
