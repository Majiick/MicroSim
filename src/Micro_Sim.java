/**
 * Created by Ecoste on 1/23/2017.
 */
import processing.core.*;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Micro_Sim extends PApplet{
    public final int mutationRate = 1;
    int round = 0;
    final int roundDuration = 1800;
    int roundCooldown = 0;
    public List<GameObject> gameObjects = new ArrayList<>();
    XML xml = new XML();

    public static void main(String... args){
        PApplet.main("Micro_Sim");
    }

    public void settings() {
        size(1000, 1000, P2D);
    }

    public void start() {
        //XML xml = new XML();
        frameRate(1000);

        for(int i = 0; i < 5; i++) {
            AddGameObject(new Organism(this, new Point2D.Float(ThreadLocalRandom.current().nextInt(200, 800), ThreadLocalRandom.current().nextInt(200, 800))));
        }
        NextRound();
    }

    public void draw() {
        background(0);

        roundCooldown--;
        if (roundCooldown <= 0) {
            NextRound();
        }

        textAlign(CENTER);
        textSize(32);
        text("Round: " + Integer.toString(round), width/2 , 50);

        gameObjects.forEach((gameObject) -> gameObject.Update());
        gameObjects = gameObjects.stream().filter(g -> !g.toBeDeleted).collect(Collectors.toList());

        if(frameCount % 20 == 0) {
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
//        organisms.forEach(g -> ((Organism) g).AddDriver());
//        organisms.forEach(g -> ((Organism) g).AddRotator());
//        organisms.forEach(g -> ((Organism) g).AddDriver());
//        organisms.forEach(g -> ((Organism) g).AddRotator());
//        organisms.forEach(g -> ((Organism) g).AddDriver());
//        organisms.forEach(g -> ((Organism) g).AddRotator());
        xml.SaveOrganism((Organism)organisms.get(2));
    }

    void NextRound() {
        round++;
        roundCooldown = roundDuration;

        DeleteFood();
        for(int i = 0; i < 20; i++) {
            AddGameObject(new Food(this));
        }

        List<GameObject> organisms = gameObjects.stream().filter(g -> g instanceof Organism).collect(Collectors.toList());
        for(GameObject g : organisms) {
            ((Organism)g).newRound(mutationRate);
        }
    }

    void DeleteFood() {
        List<GameObject> food = gameObjects.stream().filter(g -> g instanceof Food).collect(Collectors.toList());
        food.forEach(f -> f.Delete());
    }
}
