package matchingpairsgame;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class Controller extends JLabel implements VetoableChangeListener, MatchedListener {
    private int matchedPairs;
    private Card firstCard;
    private Card secondCard;
    private final Timer timer = new Timer(true);

    public Controller() {
        super("Pairs: 0", SwingConstants.CENTER);
        setFont(new Font("Arial", Font.BOLD, 18));
    }

    @Override
    public void vetoableChange(java.beans.PropertyChangeEvent evt) throws PropertyVetoException {
        if ("state".equals(evt.getPropertyName())) {
            Card card = (Card) evt.getSource();
            Card.State newState = (Card.State) evt.getNewValue();
            if (newState == Card.State.FACE_DOWN && (card.getState() == Card.State.EXCLUDED || card.getState() == Card.State.FACE_UP)) {
                throw new PropertyVetoException("Cannot flip excluded or face-up card", evt);
            }
        }
    }

    @Override
    public void matched(MatchedEvent e) {
        if (e.isMatched()) {
            firstCard.setState(Card.State.EXCLUDED);
            secondCard.setState(Card.State.EXCLUDED);
            matchedPairs++;
            setText("Pairs: " + matchedPairs);
        } else {
            try {
                firstCard.setState(Card.State.FACE_DOWN);
                secondCard.setState(Card.State.FACE_DOWN);
            } catch (PropertyVetoException ex) {
                // Should not happen as Controller allows this change
            }
        }
        firstCard = null;
        secondCard = null;
    }

    public void cardFlipped(Card card) {
        if (firstCard == null) {
            firstCard = card;
        } else if (secondCard == null && card != firstCard) {
            secondCard = card;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    boolean match = firstCard.getValue() == secondCard.getValue();
                    fireMatchedEvent(new MatchedEvent(this, match));
                }
            }, 500);
        }
    }

    private void fireMatchedEvent(MatchedEvent e) {
        for (Component c : getParent().getComponents()) {
            if (c instanceof MatchedListener) {
                ((MatchedListener) c).matched(e);
            }
        }
    }

    public void reset() {
        matchedPairs = 0;
        setText("Pairs: 0");
        firstCard = null;
        secondCard = null;
    }
}