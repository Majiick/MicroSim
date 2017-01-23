/**
 * Created by Ecoste on 1/23/2017.
 */
import processing.core.*;
import java.util.*;

public class Micro_Sim extends PApplet{
    Vector<GameObject> gameObjects = new Vector<>();

    public static void main(String... args){
        PApplet.main("Micro_Sim");
    }

    public void settings() {
        size(1000, 1000);
    }

    public void start() {
        AddGameObject(new Organism(this));
    }

    public void draw() {
        gameObjects.forEach((gameObject) -> gameObject.Update());
    }

    public void AddGameObject(GameObject x) {
        x.Start();
        gameObjects.add(x);
    }

    public void RemoveGameObject(GameObject x) {
        gameObjects.remove(x);
    }
}
