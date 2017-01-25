/**
 * Created by Ecoste on 1/23/2017.
 */
import java.awt.Point;
import java.awt.geom.Point2D;

public class Organism extends GameObject {

    Point2D.Float moveTo = new Point2D.Float(0, 0);
    Grid grid = new Grid(new Point(7, 7), this);

    Organism(Micro_Sim processing) {
        super(processing);
        moveTo = position;
    }

    Organism(Micro_Sim processing, Point2D.Float startingPosition) {
        super(processing);
        position = startingPosition;
        moveTo = position;
    }

    public void Update() {
        //processing.rect(position.x, position.y, 100, 100);
        Point2D.Float newLoc = new Point2D.Float(0, 0);
        newLoc.x = Helper.Lerp(position.x, moveTo.x, 0.1f);
        newLoc.y = Helper.Lerp(position.y, moveTo.y, 0.1f);
        position = newLoc;
    }

    public void Start() {

    }

    public void AddDriver() {
        GameObject driver = new Driver(processing, this);
        processing.RemoveGameObject(grid.getContents().get(5).get(5));
        grid.getContents().get(5).set(5, driver);
        processing.AddGameObject(driver);
        driver.SetPosition(new Point2D.Float(5 * 5, 5 * 5));
    }

    public void Destroy() {
        processing.RemoveGameObject(this);
    }

    public void MoveTo(Point2D.Float p) {
        moveTo = p;
    }

    public void Move(Point2D.Float p) {
        moveTo = new Point2D.Float(position.x + p.x, position.y + p.y);
    }
}
