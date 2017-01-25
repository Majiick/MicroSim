/**
 * Created by Ecoste on 1/23/2017.
 */
import java.awt.Point;
import java.awt.geom.Point2D;

public class Organism extends GameObject {

    Point2D.Float moveTo = new Point2D.Float(0, 0);
    float rotateTo = 0;
    Grid grid = new Grid(new Point(7, 7), this);

    Organism(Micro_Sim processing) {
        super(processing);
        moveTo = position;
    }

    Organism(Micro_Sim processing, Point2D.Float startingPosition) {
        super(processing);
        position = startingPosition;
        moveTo = position;
        rotation = 20;
    }

    public void Update() {
        Point2D.Float newLoc = new Point2D.Float(0, 0);
        newLoc.x = Helper.Lerp(position.x, moveTo.x, 0.1f);
        newLoc.y = Helper.Lerp(position.y, moveTo.y, 0.1f);
        position = newLoc;

        rotation = Helper.Lerp(rotation, rotateTo, 0.1f);
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

    public void AddRotator() {
        GameObject rotator = new Rotator(processing, this);
        processing.RemoveGameObject(grid.getContents().get(1).get(2));
        grid.getContents().get(1).set(2, rotator);
        processing.AddGameObject(rotator);
        rotator.SetPosition(new Point2D.Float(1 * 5, 2 * 5));
    }

    public void Destroy() {
        processing.RemoveGameObject(this);
    }

    public void MoveTo(Point2D.Float p) {
        moveTo = p;
    }

    public void Move(float degree, float magnitude) {
        moveTo = new Point2D.Float(position.x + magnitude * (float)Math.cos(processing.radians(degree + rotateTo)), position.y + magnitude * (float)Math.sin(processing.radians(degree + rotateTo)));
    }

    public void Rotate(float x) {
        rotateTo += x;
    }
}
