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

    public void AddDriver() {
        GameObject driver = new Driver(processing, this);
        processing.RemoveGameObject(grid.getContents().get(5).get(5));
        grid.getContents().get(5).set(5, driver);
        processing.AddGameObject(driver);
        driver.SetPosition(new Point(5 * 5, 5 * 5));
    }

    public void Destroy() {
        processing.RemoveGameObject(this);
    }
}
