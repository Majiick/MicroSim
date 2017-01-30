/**
 * Created by Ecoste on 1/23/2017.
 */
import processing.core.*;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;

public class Micro_Sim extends PApplet{
    public final int mutationRate = 3;
    public List<GameObject> gameObjects = new ArrayList<>();

    public static void main(String... args){
        PApplet.main("Micro_Sim");
    }

    public void settings() {
        size(1000, 1000);
    }

    public void start() {
        frameRate(60);

        AddGameObject(new Organism(this, new Point2D.Float(200, 200)));
        AddGameObject(new Food(this));
        for(int i = 0; i < 20; i++) {
            AddGameObject(new Food(this));
        }
    }

    public void draw() {
        background(0);
        gameObjects.forEach((gameObject) -> gameObject.Update());
        gameObjects = gameObjects.stream().filter(g -> !g.toBeDeleted).collect(Collectors.toList());

        if(Helper.PercentageChance(5)) {
            AddGameObject(new Food(this));
        }
    }

    public void AddGameObject(GameObject x) {
        x.Start();
        gameObjects.add(x);
    }

    public void RemoveGameObject(GameObject x) {
        gameObjects.remove(x);
    }

    @Override
    public void keyPressed() {
        List<GameObject> organisms = gameObjects.stream().filter(g -> g instanceof Organism).collect(Collectors.toList());
        organisms.forEach(g -> ((Organism) g).AddDriver());
        organisms.forEach(g -> ((Organism) g).AddRotator());
        organisms.forEach(g -> ((Organism) g).AddDriver());
        organisms.forEach(g -> ((Organism) g).AddRotator());
        organisms.forEach(g -> ((Organism) g).AddDriver());
        organisms.forEach(g -> ((Organism) g).AddRotator());

//        for (GameObject g : gameObjects) {
//            if (g instanceof Organism) {
//                ((Organism) g).AddDriver();
//            }
//        }
    }
}
