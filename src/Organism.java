/**
 * Created by Ecoste on 1/23/2017.
 */
import java.awt.Point;

public class Organism extends GameObject {

    Grid grid = new Grid(new Point(7, 7), this);

    Organism(Micro_Sim processing) {
        super(processing);
    }

    Organism(Micro_Sim processing, Point startingPosition) {
        super(processing);
        position = startingPosition;
    }

    public void Update() {
        //processing.rect(position.x, position.y, 100, 100);
    }

    public void Start() {

    }

    public void Destroy() {
        processing.RemoveGameObject(this);
    }
}
