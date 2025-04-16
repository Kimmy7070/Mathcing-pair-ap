package matchingpairsgame;

import java.util.EventObject;

public class MatchedEvent extends EventObject {
    private final boolean matched;

    public MatchedEvent(Object source, boolean matched) {
        super(source);
        this.matched = matched;
    }

    public boolean isMatched() { return matched; }
}