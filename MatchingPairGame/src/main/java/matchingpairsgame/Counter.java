package matchingpairsgame;

import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Counter extends JLabel implements PropertyChangeListener {
    private int count;

    public Counter() {
        super("Moves: 0", SwingConstants.CENTER);
        setFont(new Font("Arial", Font.BOLD, 18));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName()) && evt.getNewValue() == Card.State.FACE_UP) {
            count++;
            setText("Moves: " + (int)Math.ceil(count/2));// works after 2 cards are flipped and counter as a move
        }
    }
    
    public void shufflePerformed(ShuffleEvent e) {
    reset(); // Reset counter to 0
}

    public void reset() {
        count = 0;
        setText("Moves: 0");
    }
}
