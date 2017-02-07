/**
 * Created by Ecoste on 1/23/2017.
 */
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class Organism extends GameObject {
    static int idCounter = 0;
    int id = 0;

    Point2D.Float moveTo = new Point2D.Float(0, 0);
    float rotateTo = 0;
    Grid grid = new Grid(new Point(7, 7), this);
    int points = 0;
    List<State> states = new ArrayList<>();
    //State lastState = null;
    MutationRates mRates;

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
        //Add cytoplasm back here.
        int x = ThreadLocalRandom.current().nextInt(0, grid.contents.size());
        int y = ThreadLocalRandom.current().nextInt(0, grid.contents.size());

        processing.RemoveGameObject(grid.getContents().get(x).get(y));
        grid.getContents().get(x).set(y, (GameObject)component);
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

    public void newRound(int mutationRate) {
//        if (lastState == null) {
//            lastState = new State();
//            lastState.grid = new Grid(grid);
//            lastState.points = points;
//        }
//
//
        boolean reverted = false;

        if(states.size() > 0) {
            State lastState = states.get(states.size() - 1);

            if(points < lastState.points) {
                System.out.println(id + " reverted back.");
                //grid.disableContents();

                grid = new Grid(lastState.grid); //this is broken
                reverted = true;
            }
        }

        State s = new State();
        s.points = points;
        s.grid = grid;
        if (!reverted) {
            states.add(s);
        }

        System.out.println("id " + id + " has " + grid.toString() + " cytoplasm.");
        System.out.println("Round " + processing.round + " id " + id + " has " + points + " pts.");

        points = 0;
        position = new Point2D.Float(ThreadLocalRandom.current().nextInt(200, 800), ThreadLocalRandom.current().nextInt(200, 800));
        Mutate(mutationRate);
    }

    public void addPoint() {
        points++;
    }
//
    private class State {
        public int points;
        public Grid grid;
    }
}
