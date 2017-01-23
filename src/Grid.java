/**
 * Created by Ecoste on 1/23/2017.
 */
import java.awt.Point;
import java.util.*;

public class Grid {
    Point size;
    Vector<Vector<GameObject>> contents = new Vector<Vector<GameObject>>();
    GameObject parent;

    Grid(Point size, GameObject parent) {
        this.size = size;
        this.parent = parent;

        for(int x = 0; x < size.x; x++) {
            contents.add(new Vector<GameObject>());

            for(int y = 0; y < size.y; y++) {
                GameObject cytoplasm = new Cytoplasm(parent.processing, parent);
                cytoplasm.SetPosition(new Point(x * 5, y * 5));
                contents.get(x).add(cytoplasm);
                parent.processing.AddGameObject(cytoplasm);
            }
        }
    }
}
