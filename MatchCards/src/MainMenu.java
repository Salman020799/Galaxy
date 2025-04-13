import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    JFrame frame = new JFrame("Main Menu");
    JLabel label = new JLabel("Main Menu", JLabel.CENTER);
    JLabel label2 = new JLabel("Select deck to play:", JLabel.CENTER);
    JButton left = new JButton("←");
    JButton right = new JButton("→");

    JButton play = new JButton();
    JPanel deckPanel = new JPanel();
    JLabel deckLabel = new JLabel("Pokemon"); // Default deck
    String[] decks = {"Pokemon", "Naruto"};
    static int deckIndex = 0;

    // Deck image preview
    JLabel deckImageLabel = new JLabel();

    public MainMenu() {
        frame.setSize(640, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background image
        JLabel background = new JLabel(new ImageIcon("src/img/black_lego.jpg"));
        background.setLayout(new BorderLayout());
        frame.setContentPane(background);

        // Title Label
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);

        // Subtitle Label
        label2.setFont(new Font("Arial", Font.PLAIN, 18));
        label2.setForeground(Color.WHITE);

        // Deck panel
        deckPanel.setLayout(new FlowLayout());
        deckPanel.setOpaque(false);
        deckPanel.add(left);
        deckLabel.setFont(new Font("Arial", Font.BOLD, 18));
        deckLabel.setForeground(Color.WHITE);
        deckPanel.add(deckLabel);
        deckPanel.add(right);

        // Deck image label setup
        deckImageLabel.setHorizontalAlignment(JLabel.CENTER);
        deckImageLabel.setIcon(new ImageIcon("src/img/decks/Pokemon.png")); // default image

        // Play button
        play.setFont(new Font("Arial", Font.BOLD, 20));
        play.setPreferredSize(new Dimension(140, 50));
        ImageIcon playIcon = new ImageIcon("src/img/play-btn.png");
        play.setIcon(playIcon);
        play.setContentAreaFilled(false);
        play.setBorderPainted(false);

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playSound("tap.wav");
                frame.dispose();

                if (decks[deckIndex].equals("Naruto")) {
                    new MatchCards2();
                } else {
                    new MatchCards();
                }
            }
        });

        // Left button
        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playSound("tap.wav");
                deckIndex = (deckIndex - 1 + decks.length) % decks.length;
                deckLabel.setText(decks[deckIndex]);
                updateDeckImage();
            }
        });

        // Right button
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playSound("tap.wav");
                deckIndex = (deckIndex + 1) % decks.length;
                deckLabel.setText(decks[deckIndex]);
                updateDeckImage();
            }
        });

        // Panels
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setOpaque(false);
        topPanel.add(label);
        topPanel.add(label2);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(deckPanel, BorderLayout.NORTH);
        centerPanel.add(deckImageLabel, BorderLayout.CENTER); // show image below deck name

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(play);

        background.add(topPanel, BorderLayout.NORTH);
        background.add(centerPanel, BorderLayout.CENTER);
        background.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void updateDeckImage() {
        String imagePath = "src/img/decks/" + decks[deckIndex] + ".png";
        deckImageLabel.setIcon(new ImageIcon(imagePath));
    }
}




