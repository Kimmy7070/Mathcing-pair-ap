package matchingpairsgame;

import javax.swing.*;
import java.awt.Font;
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
    private final Card[] cards;

    public Controller(Card[] cards) {
        super("Pairs: 0", SwingConstants.CENTER);
        setFont(new Font("Arial", Font.BOLD, 18));
        this.cards = cards;
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        if ("state".equals(evt.getPropertyName())) {
            Card card = (Card) evt.getSource();
            Card.State newState = (Card.State) evt.getNewValue();
            if (newState == Card.State.FACE_DOWN &&
                (card.getState() == Card.State.EXCLUDED || card.getState() == Card.State.FACE_UP)) {
                throw new PropertyVetoException("Cannot flip excluded/face-up card", evt);
            }
        }
    }

    @Override
    public void matched(MatchedEvent e) {
    if (e.isMatched()) {
        try {
            firstCard.setState(Card.State.EXCLUDED);
            secondCard.setState(Card.State.EXCLUDED);
        } catch (PropertyVetoException ex) {
            // Ignore
        }
        matchedPairs++;
        setText("Pairs: " + matchedPairs);
    } else {
        try {
            firstCard.setState(Card.State.FACE_DOWN); // Flip back
            secondCard.setState(Card.State.FACE_DOWN);
        } catch (PropertyVetoException ex) {
            // Ignore
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

        // Disable all cards except the current pair
        for (Card c : cards) {
            if (c != firstCard && c != secondCard) {
                c.getButton().setEnabled(false);
            }
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                boolean match = firstCard.getValue() == secondCard.getValue();
                fireMatchedEvent(new MatchedEvent(this, match));

                // Re-enable all non-excluded cards
                SwingUtilities.invokeLater(() -> {
                    for (Card c : cards) {
                        if (c.getState() != Card.State.EXCLUDED) {
                            c.getButton().setEnabled(true);
                        }
                    }
                });
            }
        }, 500);
        }
    }

    private void fireMatchedEvent(MatchedEvent e) {
        for (Card card : cards) {
            if (card instanceof MatchedListener) {
                ((MatchedListener) card).matched(e);
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