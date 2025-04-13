import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class MatchCards {
//Represents a playing card with a name and an associated image
/* We are creating a Card Class which is an inner class and its purpose will be 
to represent a single card with a name and image. */
class Card{
    String cardName;
    ImageIcon cardImageIcon;

    Card(String cardName, ImageIcon cardImageIcon){
        this.cardName = cardName;
        this.cardImageIcon = cardImageIcon;
    }
    public String toString(){
        return cardName;
    }
}

/* Here as you can see, we are storing card name like fire, water etc. We are also 
storing card image icon to display the image. For making it useful for debugging, it 
overrides toString() to return cardName. */

//List of card names (each card type appears twice in the game)
/* Now we will declare the variables. */
String[] cardList = { //track cardNames
    "darkness",
    "double",
    "fairy",
    "fighting",
    "fire",
    "grass",
    "lightning",
    "metal",
    "psychic",
    "water"
};

//Board configuration(defines number of rows and columns)
int rows = 4;
int columns = 5;
int cardWidth = 90;
int cardHeight = 128;

//List to store all cards, including duplicates 
ArrayList<Card> cardSet; //create a deck of cards with cardNames and cardImageIcons
ImageIcon cardBackImageIcon;
/* Basically, cardList[] holds names of 10 unique cards. We have set the game grid 
as 4x5(20 slots), so that each card can get a duplicate version of itself. In here, 
the cardSet is storing the shuffled deck and cardBackImageIcon is holding the back-side image. */

//Defines game board size based on card dimensions
int boardWidth = columns * cardWidth; //5*128 = 640px
int boardHeight = rows * cardHeight; //4*90 = 360px

//UI Components
/* Now, we will create the Game Window. */
JFrame frame = new JFrame("Pokemon Match Cards");//Main game window
JLabel textLabel = new JLabel();//Displays error count
JPanel textPanel = new JPanel();//Panel to hold the label
JPanel boardPanel = new JPanel();//Panel for the game grid
JPanel restartGamPanel = new JPanel();//Panel for restart button
JButton restartButton = new JButton();//Restart button
/* JFrame frame is the main wondow
 * JLabel textLabel is displaying the error count
 * JPanel boardPanel is holding the 4x5 grid of cards
 * JButton restartButton is letting the user restart the game
 */

//Game logic variables
int errorCount = 0;//Tracks incorrect matches
ArrayList<JButton> board;//Stores the game buttons
Timer hideCardTimer;//Timer to control card hiding delay
boolean gameReady = false;//Indicates whether game is active
JButton card1Selected;//Stores first selected card
JButton card2Selected;//Stores second selected card

/* We will initialize the game by using a constructor MatchCards() */
    MatchCards(){
    setupCards();//Initialize cards
    shuffleCards();//Shuffle the deck

    //Configure the main frame  
    //frame.setVisible(true);
    frame.setLayout(new BorderLayout());
    frame.setSize(boardWidth, boardHeight);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
/* We are calling setupCards() to create cards and shuffleCards() to randomize them. */    


    //Setup error label
    textLabel.setFont(new Font("Arial", Font.PLAIN, 20));
    textLabel.setHorizontalAlignment(JLabel.CENTER);
    textLabel.setText("Errors: " + Integer.toString(errorCount));

    textPanel.setPreferredSize(new Dimension(boardWidth, 30));
    textPanel.add(textLabel);
    frame.add(textPanel, BorderLayout.NORTH);

    //card game board
    //Create card grid
/* WE will create Buttons for cards. */    
    board = new ArrayList<JButton>();
    boardPanel.setLayout(new GridLayout(rows, columns));
    for (int i = 0; i< cardSet.size(); i++){
        JButton tile = new JButton();
        tile.setPreferredSize(new Dimension(cardWidth, cardHeight));
        tile.setOpaque(true);
        tile.setIcon(cardSet.get(i).cardImageIcon);
        tile.setFocusable(false);

        //Handle card selection
        tile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameReady) {
                    return;
                }
                JButton tile = (JButton) e.getSource();
                if (tile.getIcon() == cardBackImageIcon) {
                    if (card1Selected == null) {
                        card1Selected = tile;
                        int index = board.indexOf(card1Selected);
                        card1Selected.setIcon(cardSet.get(index).cardImageIcon);
                    }
                    else if (card2Selected == null) {
                        card2Selected = tile;
                        int index = board.indexOf(card2Selected);
                        card2Selected.setIcon(cardSet.get(index).cardImageIcon);

                        //Check for a match
                        if (card1Selected.getIcon() != card2Selected.getIcon()) {
                            errorCount += 1;
                            textLabel.setText("Errors: " + Integer.toString(errorCount));
                            hideCardTimer.start();
                        }
                        else{
                            card1Selected = null;
                            card2Selected = null;
                        }
                    }
                }
            }
        });
        board.add(tile);
        boardPanel.add(tile);
    }
/* We have added JButton for each card in the grid and when we will click, it will reveal 
the card. if two cards don't match, it will hide them after a short delay. */

    frame.add(boardPanel);

    //restart game button
    //restart button setup
    restartButton.setFont(new Font("Arial", Font.PLAIN, 16));
    restartButton.setText("Restart Game");
    restartButton.setPreferredSize(new Dimension(boardWidth, 30));
    restartButton.setFocusable(false);
    restartButton.setEnabled(false);
    restartButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameReady) {
                return;
            }
            gameReady = false;
            restartButton.setEnabled(false);
            card1Selected = null;
            card2Selected = null;
            shuffleCards();

            //reassign buttons with new cards
            //reassign shuffled images
            for (int i = 0; i < board.size(); i++){
                board.get(i).setIcon(cardSet.get(i).cardImageIcon);
            }
            errorCount = 0;
            textLabel.setText("Errors: " + Integer.toString(errorCount));
            hideCardTimer.start();
        }
    });
/* In here, we are reshuffling the cards and resetting the game. */

    restartGamPanel.add(restartButton);
    frame.add(restartGamPanel, BorderLayout.SOUTH);

    frame.pack();
    frame.setVisible(true);

    //start game
    //Timer to hide unmatched cards after delay
    hideCardTimer = new Timer(1500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            hideCards();
        }
    });
    hideCardTimer.setRepeats(false);
/* If two selected cards don't match, the timer waits 1.5 seconds before flipping them back */  
  
    hideCardTimer.start();
    }

    //Load and duplicate card images
    void setupCards(){
        cardSet = new ArrayList<Card>();
        for (String cardName : cardList){
            //load each card image
            Image cardImg = new ImageIcon(getClass().getResource("./img/" + cardName + ".jpg")).getImage();
            ImageIcon cardImageIcon = new ImageIcon(cardImg.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH));

            //create card object and add to cardSet
            Card card = new Card(cardName, cardImageIcon);
            cardSet.add(card);
        }
        cardSet.addAll(cardSet);//Duplicate cards

        //load the back card image
        Image cardBackImg = new ImageIcon(getClass().getResource("./img/back.jpg")).getImage();
        cardBackImageIcon = new ImageIcon(cardBackImg.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH));
    }
/* WE are loading the images from /img/ directory and creating a Card object for each name. 
We have also duplicated each card and for that reason now we have total 20 cards (10 pairs). */    

    void shuffleCards(){
        System.out.println(cardSet);
        //shuffle
        for(int i = 0; i < cardSet.size(); i++){
            int j = (int) (Math.random() * cardSet.size()); //get random index
            //swap
            Card temp = cardSet.get(i);
            cardSet.set(i, cardSet.get(j));
            cardSet.set(j, temp);
        }
        System.out.println(cardSet);
    }
/* We are using a random index swapping method to shuffle. */    

    void hideCards() {
        if (gameReady && card1Selected != null && card2Selected != null) { //only flip 2 cards
            card1Selected.setIcon(cardBackImageIcon);
            card1Selected = null;
            card2Selected.setIcon(cardBackImageIcon);
            card2Selected = null;
        }
        else { //flip all cards face down
        for (int i = 0; i < board.size(); i++) {
            board.get(i).setIcon(cardBackImageIcon);
        }
        gameReady = true;
        restartButton.setEnabled(true);
    }
    }
/* If two cards don't match, then they will flip back. Also, if game starts, it will flip all cards face down. */    
}
