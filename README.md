## Memory Card Game

Our project is a fun and interactive memory card puzzle game developed using Java and Swing. The game presents a 4x5 face-down card grid through which players must flip two cards simultaneously to locate matching pairs. If the selected cards match, they remain face up, otherwise, they flip back after a short delay. An error counter keeps track of incorrect attempts and a restart button allows players to shuffle and reset the game.
For this project, we have set up everything in Visual Studio Code, with App.java handling the initialization and MatchCards.java focusing on the game logic. The game uses Java's Swing and AWT libraries for the graphical interface and event handling. We have saved all the images in an "images" folder which includes both the card faces and the back image.
A 4x5 grid of JButton components displays the cards through an ArrayList. After card selection, the Timer system triggers a 1.5-second wait until mismatched cards fade from view. Players have the option to restart the game using the button which resets both the board display and error count.

-- PROBLEM STATEMENT --

The current version of our memory card puzzle game is functional and enjoyable, however, it lacks several key features that would enhance user engagement and the overall gameplay experience. The game does not yet include a win condition detector to notify players when they have successfully matched all cards. Additionally, there is no timer to track the game's completion time, nor is there support for a two-player mode that allows competitive play with score tracking.
![Screenshot (132)](https://github.com/user-attachments/assets/35a9ae3d-0869-4539-8ab6-234526225e84)
![Screenshot (124)](https://github.com/user-attachments/assets/b3e7b7ab-1ed4-4e50-b917-032d0647be43)
