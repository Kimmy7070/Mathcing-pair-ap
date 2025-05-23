package matchingpairsgame;

import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

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
    public void reset()//reset function
    {
        matchedPairs = 0;
        setText("Pairs: 0");
        if(firstCard != null && secondCard != null)
        {
            try 
            {
                firstCard.setState(Card.State.FACE_DOWN); // Flip back
                secondCard.setState(Card.State.FACE_DOWN);
            } catch (PropertyVetoException ex) {
            // Ignore
        }
        }
        else if(firstCard != null && secondCard == null)
        {
            try 
            {
                firstCard.setState(Card.State.FACE_DOWN);
            }
            catch (PropertyVetoException ex) {
            // Ignore
            }
        }
        firstCard = null;
        secondCard = null;
    }
    
    @Override
public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
    // only care about State changes on Card
    if (!(evt.getNewValue() instanceof Card.State) || !(evt.getSource() instanceof Card)) {
        return;
    }

    Card.State oldState = (Card.State) evt.getOldValue();
    Card.State newState = (Card.State) evt.getNewValue();

    if (oldState == Card.State.EXCLUDED && newState == Card.State.FACE_DOWN) {
        throw new PropertyVetoException("Cannot flip an excluded card",evt);
    }

    if (oldState == Card.State.FACE_UP && newState == Card.State.FACE_DOWN) {
        throw new PropertyVetoException(
            "Cannot flip a card from face-up back to face-down directly",
            evt
        );
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
    } else if(firstCard != null && secondCard != null) {
        firstCard.removeVetoableChangeListener(this);
        secondCard.removeVetoableChangeListener(this);
        try {
            firstCard.setState(Card.State.FACE_DOWN); // Flip back
            secondCard.setState(Card.State.FACE_DOWN);
        } catch (PropertyVetoException ex) {
            // Ignore
        }
        firstCard.addVetoableChangeListener(this);
        secondCard.addVetoableChangeListener(this);
    }
    if (matchedPairs == cards.length/2)
    {
        setText("VICTORY!");
        return;
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
        matched(e);
        for (Card card : cards) {
            if (card instanceof MatchedListener) {
                ((MatchedListener) card).matched(e);
            }
        }
    }
}