/**
 * Created by Ecoste on 1/24/2017.
 */
abstract public class Trigger {
    IComponent parent;

    Trigger(IComponent parent) {
        this.parent = parent;
    }

    abstract public void Start();
}
