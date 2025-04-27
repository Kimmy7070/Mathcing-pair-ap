package matchingpairsgame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.beans.PropertyVetoException;

public class Board extends JFrame implements ShuffleListener {
    private static final int N = 4;
    private final Card[] cards = new Card[2 * N];
    private final Controller controller = new Controller(cards);
    private final Counter counter = new Counter();
    private final JButton shuffleButton = new JButton("Shuffle");
    private final JButton exitButton = new JButton("Exit");

    public Board() {
        setTitle("Matching Pairs Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Card panel with 2 rows and 4 columns
        JPanel cardPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card();
            cardPanel.add(cards[i].getButton()); // Use getButton() to add to UI
        }

        // Button panel (Shuffle + Exit)
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(shuffleButton);
        buttonPanel.add(exitButton);

        // Label panel (Controller + Counter)
        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        labelPanel.add(controller);
        labelPanel.add(counter);

        // Add panels to the frame
        add(cardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(labelPanel, BorderLayout.NORTH);

        // Shuffle button action
        shuffleButton.addActionListener(e -> shuffleCards());

        // Exit button action
        exitButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this, 
                "Exit game?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) System.exit(0);
        });

        // Add listeners to cards
        for (Card card : cards) {
            // Listen for state changes
            card.addPropertyChangeListener("state", new java.beans.PropertyChangeListener() {
                @Override
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    if (card.getState() == Card.State.FACE_UP) {
                        controller.cardFlipped(card);
                    }
                }
            });
            card.addPropertyChangeListener("state", counter);
            card.addVetoableChangeListener(controller); // For vetoable state changes
        }

        
        // Initial shuffle
        shuffleCards();

        // Finalize window setup
        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void shuffleCards() {
    
    for (Card c : cards) {
        c.removeVetoableChangeListener(controller);//removed while shuffle
    }
    
    List<Integer> values = new ArrayList<>();
    for (int i = 1; i <= N; i++) {
        values.add(i);
        values.add(i);
    }
    Collections.shuffle(values);
    fireShuffleEvent(values.stream().mapToInt(i -> i).toArray());
    controller.reset();
    counter.reset();
    
    for (Card c : cards) {
        c.addVetoableChangeListener(controller);//added after shuffle
    }
}

    @Override
    public void shuffled(ShuffleEvent e) {
    int[] values = e.getValues();
    for (int i = 0; i < cards.length; i++) {
        cards[i].setValue(values[i]);
        try {
            cards[i].setState(Card.State.FACE_DOWN); // Force reset
            cards[i].getButton().setEnabled(true);
        } catch (PropertyVetoException ex) {
            // Should not happen
        }
    }
    }

    private void fireShuffleEvent(int[] values) {
        ShuffleEvent event = new ShuffleEvent(this, values);
        this.shuffled(event); // Directly handle the event
    }

    public static void main(String[] args) {
        // Start the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new Board());
    }
}