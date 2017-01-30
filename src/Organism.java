/**
 * Created by Ecoste on 1/23/2017.
 */
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

public class Organism extends GameObject {

    Point2D.Float moveTo = new Point2D.Float(0, 0);
    float rotateTo = 0;
    Grid grid = new Grid(new Point(7, 7), this);
    int points = 0;

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
        tag = "Organism";
    }

    public void AddDriver() {
        AddComponent(new Driver(processing, this));
    }

    public void AddRotator() {
        AddComponent(new Rotator(processing, this));
    }

    public void Destroy() {
        processing.RemoveGameObject(this);
    }

    public void MoveTo(Point2D.Float p) {
        moveTo = p;
    }

    public void Move(float degree, float magnitude) {
        moveTo = new Point2D.Float(position.x + magnitude * (float)Math.cos(processing.radians(degree + rotation)), position.y + magnitude * (float)Math.sin(processing.radians(degree + rotation)));
    }

    public void Rotate(float x) {
        rotateTo += x;
    }

    public void OnCollisionEnter(GameObject other) {

    }

    void AddComponent(IComponent component) {
        int x = ThreadLocalRandom.current().nextInt(0, grid.contents.size());
        int y = ThreadLocalRandom.current().nextInt(0, grid.contents.size());

        processing.RemoveGameObject(grid.getContents().get(x).get(y));
        grid.getContents().get(1).set(2, (GameObject)component);
        processing.AddGameObject((GameObject)component);
        ((GameObject)component).SetPosition(new Point2D.Float(5 * x, 5 * y));
    }
}
