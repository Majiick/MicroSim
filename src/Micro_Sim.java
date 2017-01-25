/**
 * Created by Ecoste on 1/23/2017.
 */
import processing.core.*;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;

public class Micro_Sim extends PApplet{
    Vector<GameObject> gameObjects = new Vector<>();

    public static void main(String... args){
        PApplet.main("Micro_Sim");
    }

    public void settings() {
        size(1000, 1000);
    }

    public void start() {
        AddGameObject(new Organism(this, new Point2D.Float(200, 200)));
    }

    public void draw() {
        background(0);
        gameObjects.forEach((gameObject) -> gameObject.Update());
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

//        for (GameObject g : gameObjects) {
//            if (g instanceof Organism) {
//                ((Organism) g).AddDriver();
//            }
//        }
    }
}
