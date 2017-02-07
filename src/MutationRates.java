import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ecoste on 1/30/2017.
 */
public class MutationRates {
    public float rotatorChance;
    public float driverChance;

    MutationRates() {
        rotatorChance = ThreadLocalRandom.current().nextInt(10, 50);
        driverChance = ThreadLocalRandom.current().nextInt(10, 50);
    }
}
