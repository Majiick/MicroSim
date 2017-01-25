/**
 * Created by Ecoste on 1/24/2017.
 */
abstract public class Trigger {
    IComponent parent;
    Micro_Sim processing;

    Trigger(IComponent parent, Micro_Sim processing) {
        this.parent = parent;
        this.processing = processing;
    }

    abstract public void Start();
    abstract public void Update();
}
