/**
 * Created by Ecoste on 1/23/2017.
 */
import java.awt.Point;
import java.awt.geom.Point2D;

public class Grid {
    Point size;
    public GameObject[][] contents = new GameObject[7][7]; //Change this to an array
    GameObject parent;

    Grid(Point size, GameObject parent) {
        this.size = size;
        this.parent = parent;

        for(int x = 0; x < size.x; x++) {
            for(int y = 0; y < size.y; y++) {
                GameObject cytoplasm = new Cytoplasm(parent.processing, parent);
                cytoplasm.SetPosition(new Point2D.Float(x * 5, y * 5));
                contents[x][y] = cytoplasm;
                parent.processing.AddGameObject(cytoplasm);
            }
        }
    }

    public int countCytoplasm() {
        int count = 0;

        for(int x = 0; x < size.x; x++) {
            for(int y = 0; y < size.y; y++) {
                if(contents[y][y].tag.equals("Cytoplasm")) {
                    count++;
                }

            }
        }

        return count;
    }

    public void disableContents(Micro_Sim processing) {
        for(int x = 0; x < size.x; x++) {
            for(int y = 0; y < size.y; y++) {
                contents[x][y].enabled = false;
                processing.RemoveGameObject(contents[x][y]);
            }
        }
    }

    public void enableContents(GameObject parent, Micro_Sim processing) {
        for(int x = 0; x < size.x; x++) {
            for(int y = 0; y < size.y; y++) {
                contents[x][y].enabled = true;
                contents[x][y].SetParent(parent);
                processing.AddGameObject(contents[x][y]);
            }
        }
    }


    GameObject[][] getContents() {
        return contents;
    }

    public void PrintGrid() {
        for(int x = 0; x < size.x; x++) {
            for(int y = 0; y < size.y; y++) {
                System.out.println("x: " + x + " y: " + y + " " + contents[x][y]);
            }
        }
    }
}
