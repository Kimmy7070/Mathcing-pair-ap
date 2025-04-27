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
    private boolean isShuffling = false;
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
        isShuffling = true;
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

        isShuffling = false;
    }
    
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
    {
        if (!(evt.getNewValue() instanceof Card.State) || !(evt.getSource() instanceof Card)) {
            return;
        }

        Card card = (Card) evt.getSource();
        Card.State newState = (Card.State) evt.getNewValue();

        // Only block attempts to flip an EXCLUDED card back down
        if ((newState == Card.State.FACE_DOWN && card.getState() == Card.State.EXCLUDED)) {
            throw new PropertyVetoException("Cannot flip an excluded card", evt);
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
        matched(e);
        for (Card card : cards) {
            if (card instanceof MatchedListener) {
                ((MatchedListener) card).matched(e);
            }
        }
    }
}