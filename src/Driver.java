/**
 * Created by Ecoste on 1/24/2017.
 */
public class Driver extends GameObject implements IComponent {

    Driver(Micro_Sim processing, GameObject parent) {
        super(processing);
        SetParent(parent);
    }

    public void Start() {

    }

    public void Update() {
        processing.fill(processing.color(255, 0, 0));
        processing.rect(position.x, position.y, 5, 5);
    }

    public void Trigger() {

    }
}
