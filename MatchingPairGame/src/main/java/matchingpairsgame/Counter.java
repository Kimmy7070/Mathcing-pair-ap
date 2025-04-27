package matchingpairsgame;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
            setText("Moves: " + count);
        }
        // In Counter.java's propertyChange():
//        if (evt.getNewValue() == Card.State.FACE_UP && evt.getOldValue() != Card.State.FACE_UP) 
//        {
//            count++;
//            setText("Moves: " + count);
//        }
    }
    
    public void shufflePerformed(ShuffleEvent e) {
    reset(); // Reset counter to 0
}

    public void reset() {
        count = 0;
        setText("Moves: 0");
    }
}
