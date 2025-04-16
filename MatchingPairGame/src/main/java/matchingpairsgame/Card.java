package matchingpairsgame;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

public class Card extends JButton implements java.io.Serializable {
    public enum State { EXCLUDED, FACE_DOWN, FACE_UP }

    private int value;
    private State state;
    private final transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final transient VetoableChangeSupport vcs = new VetoableChangeSupport(this);

    public Card() {
        state = State.FACE_DOWN;
        setFont(new Font("Arial", Font.BOLD, 24));
        addActionListener(e -> {
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
    }

    public synchronized int getValue() { return value; }
    public synchronized void setValue(int newValue) {
        int old = value;
        value = newValue;
        pcs.firePropertyChange("value", old, newValue);
    }

    public synchronized State getState() { return state; }
    public synchronized void setState(State newState) throws PropertyVetoException {
        State old = state;
        vcs.fireVetoableChange("state", old, newState);
        state = newState;
        pcs.firePropertyChange("state", old, newState);
        updateAppearance();
    }

    private void updateAppearance() {
        switch (state) {
            case EXCLUDED: setBackground(Color.RED); setText(""); break;
            case FACE_DOWN: setBackground(Color.GREEN); setText(""); break;
            case FACE_UP: setText(String.valueOf(value)); break;
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
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

    public void shuffled(ShuffleEvent e) {
        int[] values = e.getValues();
        for (int i = 0; i < values.length; i++) {
            if (this == cards[i]) {
                setValue(values[i]);
                break;
            }
        }
    }
}
