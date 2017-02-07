import java.util.Vector;

/**
 * Created by Ecoste on 1/23/2017.
 */

//I think this is better off as an abstract class instead of an interface. Yeah, it probably is.
public interface IComponent {
    void Trigger();
    Vector<Trigger> getTriggers();
}
