/**
 * Created by Ecoste on 1/24/2017.
 */
import java.util.*;

public class Driver extends GameObject implements IComponent {

    Vector<Trigger> triggers = new Vector<>();

    Driver(Micro_Sim processing, GameObject parent) {
        super(processing);
        SetParent(parent);
    }

    public void Start() {
        triggers.add(new ChronoTrigger(this));
    }

    public void Update() {
        processing.fill(processing.color(255, 0, 0));
        processing.rect(position.x, position.y, 5, 5);
    }

    public void Trigger() {
        System.out.println("Triggered");
    }
}
