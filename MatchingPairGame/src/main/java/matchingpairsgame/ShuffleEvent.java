package matchingpairsgame;

import java.util.EventObject;

public class ShuffleEvent extends EventObject {
    private final int[] values;

    public ShuffleEvent(Object source, int[] values) {
        super(source);
        this.values = values.clone();
    }

    public int[] getValues() { return values.clone(); }
}