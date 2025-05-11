package matchingpairsgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

import javax.swing.JButton;

public class Card implements java.io.Serializable {
    public enum State { EXCLUDED, FACE_DOWN, FACE_UP }

    private final JButton button = new JButton(); // Composition instead of inheritance
    private int value;
    private State state;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final VetoableChangeSupport vcs = new VetoableChangeSupport(this);

    public Card() {
        state = State.FACE_DOWN;
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(100, 100));
        button.addActionListener(e -> {
            try {
                if (state == State.FACE_DOWN) {
                    setState(State.FACE_UP);
                } else {
                    setState(State.FACE_DOWN);
                }
            } catch (PropertyVetoException ex) {
                // Vetoed, do nothing
            }
        });
        updateAppearance();
    }

    public JButton getButton() { return button; } // Expose the button

    public int getValue() { return value; }
    public void setValue(int newValue) {
        int old = value;
        value = newValue;
        pcs.firePropertyChange("value", old, newValue);
    }

    public State getState() { return state; }
    public void setState(State newState) throws PropertyVetoException {
        State old = state;
        vcs.fireVetoableChange("state", old, newState);
        state = newState;
        pcs.firePropertyChange("state", old, newState);
        updateAppearance();
    }

    private void updateAppearance() {
        switch (state) {
            case EXCLUDED: button.setBackground(Color.RED); button.setText(""); break;
            case FACE_DOWN: button.setBackground(Color.GREEN); button.setText(""); break;
            case FACE_UP: button.setText(String.valueOf(value)); break;
        }
    }
    
    //@Override
    public void matched(MatchedEvent e) {
      if (state == State.FACE_UP) {
           try {
               // Flip down or exclude based on match result
               setState(e.isMatched() ? State.EXCLUDED : State.FACE_DOWN);
           } catch (PropertyVetoException ex) {
               // Vetoed change, ignore
           }
       }
    }
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) 
    {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vcs.addVetoableChangeListener(listener);
    }

    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        vcs.removeVetoableChangeListener(listener);
    }
}