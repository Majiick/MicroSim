/**
 * Created by Ecoste on 1/23/2017.
 */
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.*;

public class Grid {
    Point size;
    Vector<Vector<GameObject>> contents = new Vector<Vector<GameObject>>(); //Change this to an array
    GameObject parent;

    Grid(Point size, GameObject parent) {
        this.size = size;
        this.parent = parent;

        for(int x = 0; x < size.x; x++) {
            contents.add(new Vector<GameObject>());

            for(int y = 0; y < size.y; y++) {
                GameObject cytoplasm = new Cytoplasm(parent.processing, parent);
                cytoplasm.SetPosition(new Point2D.Float(x * 5, y * 5));
                contents.get(x).add(cytoplasm);
                parent.processing.AddGameObject(cytoplasm);
            }
        }
    }

    Grid(Grid g) {
        this.size = g.size;
        this.parent = g.parent;

        for(int x = 0; x < g.size.x; x++) {
            contents.add(new Vector<GameObject>());

            for(int y = 0; y < g.size.y; y++) {
                contents.get(x).add(g.contents.get(x).get(y));
            }
        }
    }

    public int countCytoplasm() {
        int count = 0;

        for(int x = 0; x < size.x; x++) {
            for(int y = 0; y < size.y; y++) {
                if(contents.get(x).get(y).tag.equals("Cytoplasm")) {
                    count++;
                }

            }
        }

        return count;
    }

    public void disableContents() {
        for(int x = 0; x < size.x; x++) {
            for(int y = 0; y < size.y; y++) {
                contents.get(x).get(y).enabled = false;

            }
        }
    }

    Vector<Vector<GameObject>> getContents() {
        return contents;
    }
}
