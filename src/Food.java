/**
 * Created by Ecoste on 1/26/2017.
 */
import java.util.concurrent.ThreadLocalRandom;
import java.awt.geom.Point2D;

public class Food extends GameObject{
    Food(Micro_Sim processing) {
        super(processing);
        position = new Point2D.Float((float)ThreadLocalRandom.current().nextInt(200, processing.width - 200), ThreadLocalRandom.current().nextInt(200, processing.height - 200));
    }

    public void Update() {
        processing.fill(processing.color(255, 255, 0));
        processing.rect(GetGlobalPosition().x, GetGlobalPosition().y, 5, 5);
    }

    public void Start() {
        size = new Point2D.Float(5, 5);
        tag = "Food";
    }

    public void OnCollisionEnter(GameObject other) {

    }
}
