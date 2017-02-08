/**
 * Created by Ecoste on 1/23/2017.
 */
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

public class Organism extends GameObject {
    static int idCounter = 0;
    int id = 0;

    Point2D.Float moveTo = new Point2D.Float(0, 0);
    float rotateTo = 0;
    Grid grid = new Grid(new Point(7, 7), this);
    int points = 0;
    MutationRates mRates;

    PointRound pr = new PointRound();
    class PointRound {
        int points;
        int round;
    }

    Organism(Micro_Sim processing) {
        super(processing);
        moveTo = position;
    }

    Organism(Micro_Sim processing, Point2D.Float startingPosition) {
        super(processing);
        position = startingPosition;
        moveTo = position;
        rotation = 20;
        mRates = new MutationRates();
    }


    public void Update() {
        Point2D.Float newLoc = new Point2D.Float(0, 0);
        newLoc.x = Helper.Lerp(position.x, moveTo.x, 0.1f);
        newLoc.y = Helper.Lerp(position.y, moveTo.y, 0.1f);
        position = newLoc;

        rotation = Helper.Lerp(rotation, rotateTo, 0.1f);

        processing.fill(255, 255, 255);
        processing.textSize(18);
        processing.text(Integer.toString(points), position.x + 17, position.y - 7);
    }

    public void Start() {
        tag = "Organism";
        idCounter++;
        id = idCounter;
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
        int x = ThreadLocalRandom.current().nextInt(0, grid.size.x);
        int y = ThreadLocalRandom.current().nextInt(0, grid.size.y);

        processing.RemoveGameObject(grid.getContents()[x][y]);
        grid.getContents()[x][y] = (GameObject)component;
        processing.AddGameObject((GameObject)component);
        ((GameObject)component).SetPosition(new Point2D.Float(5 * x, 5 * y));
    }

    void Mutate(int rate) {
        int mutations = 0;
        while (mutations <= rate) {
            if(Helper.PercentageChance(mRates.driverChance)) {
                AddDriver();
                mutations++;
            }

            if(Helper.PercentageChance(mRates.rotatorChance)) {
                AddRotator();
                mutations++;
            }
        }
    }

    public void newRound(int mutationRate, XML xml) {
        xml.SaveOrganism(this, processing.round);

        if (pr.points < points) {
            System.out.println(id + " went from " + pr.points + " to " + points);
            pr.points = points;
            pr.round = processing.round;
        } else {
            System.out.println(id + " reverted back to round " + pr.round + " because it got " + points + " instead of " + pr.points);
            grid.disableContents(processing);
            grid = xml.ReadInGrid(id, pr.round, this);
            grid.enableContents(this, processing);
        }

        points = 0;
        position = new Point2D.Float(ThreadLocalRandom.current().nextInt(200, 800), ThreadLocalRandom.current().nextInt(200, 800));
        Mutate(mutationRate);
    }

    public void addPoint() {
        points++;
    }
}
