import java.util.concurrent.ThreadLocalRandom;
import java.awt.geom.Point2D;

/**
 * Created by Ecoste on 1/24/2017.
 */

public class Helper {

    static float Lerp(float a, float b, float f)
    {
        return a + f * (b - a);
    }


    static boolean PercentageChance(float chance) {
        if (ThreadLocalRandom.current().nextInt(0, 100) < chance) {
            return true;
        }

        return false;
    }

    static Point2D.Float radiansToVector(float radians) {
        return new Point2D.Float ((float)Math.cos(radians), (float)Math.sin(radians));
    }

    static Point2D.Float normalized(Point2D.Float vector) {
        float len = (float)vector.distance(new Point2D.Float(0, 0));

        return new Point2D.Float(vector.x / len, vector.y / len);
    }

    static float vectorDot(Point2D.Float v, Point2D.Float v1) {
        float angleDifferece = (float)Math.atan2(v.x, v.y) - (float)Math.atan2(v1.x, v1.y);
        return (float)v.distance(new Point2D.Float(0, 0)) * (float)v1.distance(new Point2D.Float(0, 0)) * (float)Math.cos(angleDifferece);
    }

    static float randomRange(float min, float max) {
        return (max - min) * ThreadLocalRandom.current().nextFloat() + min;
    }
}
