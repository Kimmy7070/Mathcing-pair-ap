package matchingpairsgame;

import java.util.EventListener;

public interface ShuffleListener extends EventListener {
    void shuffled(ShuffleEvent e);
}