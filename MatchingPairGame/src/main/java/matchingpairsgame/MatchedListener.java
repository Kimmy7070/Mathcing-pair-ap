package matchingpairsgame;

import java.util.EventListener;

public interface MatchedListener extends EventListener {
    void matched(MatchedEvent e);
}