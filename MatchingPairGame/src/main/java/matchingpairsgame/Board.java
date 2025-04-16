package matchingpairsgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board extends JFrame implements ShuffleListener {
    private static final int N = 4;
    private final Card[] cards = new Card[2 * N];
    private final Controller controller = new Controller();
    private final Counter counter = new Counter();
    private final JButton shuffleButton = new JButton("Shuffle");
    private final JButton exitButton = new JButton("Exit");

    public Board() {
        setTitle("Matching Pairs Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel cardPanel = new JPanel(new GridLayout(2 * N / 4, 4));
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card();
            cardPanel.add(cards[i]);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(shuffleButton);
        buttonPanel.add(exitButton);

        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        labelPanel.add(controller);
        labelPanel.add(counter);

        add(cardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(labelPanel, BorderLayout.NORTH);

        shuffleButton.addActionListener(e -> shuffleCards());
        exitButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this, "Exit game?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) System.exit(0);
        });

        for (Card card : cards) {
            card.addPropertyChangeListener("state", evt -> {
                if (card.getState() == Card.State.FACE_UP) {
                    controller.cardFlipped(card);
                }
            });
            card.addVetoableChangeListener(controller);
        }

        controller.addPropertyChangeListener(counter);
        shuffleCards();
    }

    private void shuffleCards() {
        List<Integer> values = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            values.add(i);
            values.add(i);
        }
        Collections.shuffle(values);
        fireShuffleEvent(values.stream().mapToInt(i -> i).toArray());
        controller.reset();
        counter.reset();
    }

    @Override
    public void shuffled(ShuffleEvent e) {
        int[] values = e.getValues();
        for (int i = 0; i < cards.length; i++) {
            cards[i].setValue(values[i]);
            try {
                cards[i].setState(Card.State.FACE_DOWN);
            } catch (PropertyVetoException ex) {
                // Should not happen as initial state is allowed
            }
        }
    }

    private void fireShuffleEvent(int[] values) {
        ShuffleEvent event = new ShuffleEvent(this, values);
        for (Card card : cards) {
            card.shuffled(event);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Board().setVisible(true));
    }
}