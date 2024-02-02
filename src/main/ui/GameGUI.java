package ui;

import model.Event;
import model.EventLog;
import model.PianoGame;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represents the GUI for the game.

public class GameGUI extends JFrame implements ActionListener {
    public final int WIDTH = 1000;
    public final int HEIGHT = 600;
    public final Font FONT = new Font("Times New Roman", Font.PLAIN, 20);
    public final BorderLayout LAYOUT = new BorderLayout();
    public static final String JSON_STORE = "./data/game.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private PianoGame pianoGame;
    private List<JTextField> playerNames = new ArrayList<>();

    // EFFECTS: Constructs a GameGUI.

    public GameGUI() {
        super("PiaNote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        ImageIcon icon = new ImageIcon("./data/images/PiaNoteIcon.png");
        setIconImage(icon.getImage());
//        centerOnScreen();
        pianoGame = new PianoGame();
    }

    // MODIFIES: this
    // EFFECTS: Displays the welcome screen.


    public void welcome() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);

        JPanel welcomePanel = new JPanel(new BorderLayout());

        ImageIcon mainImage = new ImageIcon("./data/images/PiaNoteIcon.png");
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(mainImage);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomePanel.add(imageLabel, BorderLayout.NORTH);

        JLabel welcomeLabel = new JLabel("Welcome to PiaNote!");
        welcomeLabel.setFont(FONT);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JLabel descriptionLabel = new JLabel("Improve your piano note reading skills with this fun game!");
        descriptionLabel.setFont(FONT);
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        bottomPanel.add(descriptionLabel, BorderLayout.NORTH);

        JButton startButton = new JButton("Let's Dive In!");
        startButton.setFont(FONT);
        startButton.addActionListener(this);
        startButton.setActionCommand("start");
        startButton.setHorizontalAlignment(JButton.CENTER);
        bottomPanel.add(startButton, BorderLayout.SOUTH);

        welcomePanel.add(bottomPanel, BorderLayout.SOUTH);

        add(welcomePanel);

        pack();
    }


    // MODIFIES: this
    // EFFECTS: Displays the start screen.

    private void startGame() {
        ImageIcon pianoImage = new ImageIcon("./data/images/whitePiano.png");
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(pianoImage);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(imageLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new BorderLayout());

        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(FONT);
        newGameButton.addActionListener(this);
        newGameButton.setActionCommand("newGame");
        newGameButton.setHorizontalAlignment(JButton.CENTER);
        buttonPanel.add(newGameButton, BorderLayout.SOUTH);

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.setFont(FONT);
        loadGameButton.addActionListener(this);
        loadGameButton.setActionCommand("loadGame");
        loadGameButton.setHorizontalAlignment(JButton.CENTER);
        buttonPanel.add(loadGameButton, BorderLayout.NORTH);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    // MODIFIES: this
    // EFFECTS: Displays the new game screen.
    private void newGame() {
        setLayout(new GridLayout(2, 1));
        JLabel numberOfPlayers = new JLabel("How many players?");
        numberOfPlayers.setFont(FONT);
        numberOfPlayers.setHorizontalAlignment(JLabel.CENTER);
        add(numberOfPlayers);
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3));
        JButton onePlayerButton = makeNumberOfPlayersButtons(1);
        buttonPanel.add(onePlayerButton);
        JButton twoPlayerButton = makeNumberOfPlayersButtons(2);
        buttonPanel.add(twoPlayerButton);
        JButton threePlayerButton = makeNumberOfPlayersButtons(3);
        buttonPanel.add(threePlayerButton);
        JButton fourPlayerButton = makeNumberOfPlayersButtons(4);
        buttonPanel.add(fourPlayerButton);
        JButton fivePlayerButton = makeNumberOfPlayersButtons(5);
        buttonPanel.add(fivePlayerButton);
        JButton sixPlayerButton = makeNumberOfPlayersButtons(6);
        buttonPanel.add(sixPlayerButton);
        add(buttonPanel);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: Displays the new players screen.
    private void newPlayers(int numberOfPlayers) {
        int rows  = 1;
        if (numberOfPlayers > 2 && numberOfPlayers <= 4) {
            rows = 2;
        } else if (numberOfPlayers > 4) {
            rows = 3;
        }
        rows++;

        setLayout(new GridLayout(rows, 2));

        for (int i = 1; i <= numberOfPlayers; i++) {
            JPanel fieldPanel = new JPanel(new GridLayout(1, 2));

            JLabel playerLabel = new JLabel("Player " + i + ":");
            playerLabel.setFont(FONT);
            playerLabel.setHorizontalAlignment(JLabel.CENTER);
            fieldPanel.add(playerLabel);

            JTextField playerTextField = new JTextField();
            playerTextField.setFont(FONT);
            playerTextField.setHorizontalAlignment(JTextField.CENTER);
            fieldPanel.add(playerTextField);
            playerNames.add(playerTextField);

            add(fieldPanel);
        }

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(FONT);
        submitButton.addActionListener(this);
        submitButton.setActionCommand("submit");
        submitButton.setHorizontalAlignment(JButton.CENTER);
        add(submitButton);

        pack();
    }


    // MODIFIES: this
    // EFFECTS: Displays the load game screen.
    private void loadGame() {
        jsonReader = new JsonReader(JSON_STORE);
        try {
            pianoGame = jsonReader.read();
            pianoGame.nextRound();
            playRound();
        } catch (Exception e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays the play round screen.
    private void playRound() {
        setLayout(LAYOUT);

        JLabel playerNameLabel = new JLabel(pianoGame.getCurrentPlayerName());
        playerNameLabel.setFont(FONT);
        playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
        add(playerNameLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(FONT);
        playAgainButton.setHorizontalAlignment(JButton.CENTER);
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File audioFile = new File("./data/audio/" + pianoGame.getCurrentNote() + ".wav");
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();

                } catch (LineUnavailableException | UnsupportedAudioFileException | NullPointerException |
                         IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        playAgainButton.setEnabled(false);

        JButton playNoteButton = new JButton("Play Note");
        playNoteButton.setFont(FONT);
        playNoteButton.setHorizontalAlignment(JButton.CENTER);
        playNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File audioFile = new File("./data/audio/" + pianoGame.getRandomNote() + ".wav");
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();

                } catch (LineUnavailableException | UnsupportedAudioFileException | NullPointerException |
                         IOException ex) {
                    throw new RuntimeException(ex);
                }
                playNoteButton.setEnabled(false);
                playAgainButton.setEnabled(true);
            }
        });
        buttonPanel.add(playNoteButton);
        buttonPanel.add(playAgainButton);

        add(buttonPanel, BorderLayout.CENTER);

        JPanel answerPanel = new JPanel(new GridLayout(1, 7));
        answerPanel.add(makeAnswerButton("C"));
        answerPanel.add(makeAnswerButton("D"));
        answerPanel.add(makeAnswerButton("E"));
        answerPanel.add(makeAnswerButton("F"));
        answerPanel.add(makeAnswerButton("G"));
        answerPanel.add(makeAnswerButton("A"));
        answerPanel.add(makeAnswerButton("B"));

        add(answerPanel, BorderLayout.SOUTH);

        pack();
    }

    // MODIFIES: this
    // EFFECTS: Displays the score board screen.
    private void scoreBoard() {
        setLayout(LAYOUT);

        JLabel scoreBoardLabel = new JLabel("Score Board");
        scoreBoardLabel.setFont(FONT);
        scoreBoardLabel.setHorizontalAlignment(JLabel.CENTER);
        add(scoreBoardLabel, BorderLayout.NORTH);

        JPanel scorePanel = new JPanel(new GridLayout(6, 1));
        for (int i = 0; i < pianoGame.getNumberOfPlayers(); i++) {
            JLabel playerScoreLabel = new JLabel(pianoGame.getPlayer(i).getName() + ": " +
                    pianoGame.getPlayer(i).getScore());
            playerScoreLabel.setFont(FONT);
            playerScoreLabel.setHorizontalAlignment(JLabel.CENTER);
            scorePanel.add(playerScoreLabel);
        }
        add(scorePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        JButton nextRoundButton = new JButton("Next Round");
        nextRoundButton.setFont(FONT);
        nextRoundButton.setHorizontalAlignment(JButton.CENTER);
        nextRoundButton.addActionListener(this);
        nextRoundButton.setActionCommand("nextRound");
        buttonPanel.add(nextRoundButton);

        JButton leaveGameButton = new JButton("Leave Game");
        leaveGameButton.setFont(FONT);
        leaveGameButton.setHorizontalAlignment(JButton.CENTER);
        leaveGameButton.addActionListener(this);
        leaveGameButton.setActionCommand("leaveGame");
        buttonPanel.add(leaveGameButton);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    // MODIFIES: this
    // EFFECTS: Displays the leave game screen.
    private void leaveGame() {
        JLabel leaveGameLabel = new JLabel("Thanks for playing!\nDo you want to save your game?");
        leaveGameLabel.setFont(FONT);
        leaveGameLabel.setHorizontalAlignment(JLabel.CENTER);
        add(leaveGameLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        JButton yesButton = new JButton("Yes");
        yesButton.setFont(FONT);
        yesButton.setHorizontalAlignment(JButton.CENTER);
        yesButton.addActionListener(this);
        yesButton.setActionCommand("saveGame");
        buttonPanel.add(yesButton);

        JButton noButton = new JButton("No");
        noButton.setFont(FONT);
        noButton.setHorizontalAlignment(JButton.CENTER);
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(noButton);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    // EFFECTS: Returns a JButton with the given number of players.
    private JButton makeNumberOfPlayersButtons(int numberOfPlayers) {
        JButton playerNumberButton = new JButton(Integer.toString(numberOfPlayers));
        playerNumberButton.setFont(FONT);
        playerNumberButton.addActionListener(this);
        playerNumberButton.setActionCommand(Integer.toString(numberOfPlayers) + "Player");
        playerNumberButton.setHorizontalAlignment(JButton.CENTER);
        return playerNumberButton;
    }


    // MODIFIES: this
    // EFFECTS: Adds the players to the game.
    private void addPlayers(List<JTextField> playerNames) {
        for (JTextField playerName : playerNames) {
            pianoGame.addPlayer(playerName.getText());
        }
    }

    // EFFECTS: Returns a JButton with the given note.
    private JButton makeAnswerButton(String note) {
        JButton answerButton = new JButton(note);
        answerButton.setFont(FONT);
        answerButton.addActionListener(this);
        answerButton.setActionCommand(note);
        answerButton.setHorizontalAlignment(JButton.CENTER);
        return answerButton;
    }

    // MODIFIES: this
    // EFFECTS: Processes the given action command.

    @Override
    public void actionPerformed(ActionEvent e) {
        getContentPane().removeAll();
        repaint();
        switch (e.getActionCommand()) {
            case "start":
                startGame();
                break;
            case "newGame":
                newGame();
                break;
            case "loadGame":
                loadGame();
                break;
            case "1Player":
            case "2Player":
            case "3Player":
            case "4Player":
            case "5Player":
            case "6Player":
                newPlayers(Integer.parseInt(e.getActionCommand().substring(0, 1)));
                break;
            case "submit":
                submitAction();
                break;
            case "C":
            case "D":
            case "E":
            case "F":
            case "G":
            case "A":
            case "B":
                noteAction(e.getActionCommand());
                break;
            case "nextRound":
                nextRoundAction();
                break;
            case "leaveGame":
                leaveGame();
                printLog();
                break;
            case "saveGame":
                saveGameAction();
                printLog();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds the players to the game and starts the next round.
    private void submitAction() {
        addPlayers(playerNames);
        pianoGame.nextRound();
        playRound();
    }

    // MODIFIES: this
    // EFFECTS: Processes the given note.
    private void noteAction(String note) {
        if (pianoGame.verifyAnswer(note)) {
            pianoGame.currentPlayerScores();
            JOptionPane.showMessageDialog(this, "Correct!");
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect!" + "\n" + "The correct answer was " +
                    pianoGame.getCurrentNote() + "");
        }
        if (pianoGame.getCurrentPlayerName().equals(pianoGame.getPlayerName(pianoGame.getNumberOfPlayers() - 1))) {
            JOptionPane.showMessageDialog(this, "Round over!");
            getContentPane().removeAll();
            repaint();
            scoreBoard();
        } else {
            pianoGame.nextPlayer();
            playRound();
        }
    }

    // MODIFIES: this
    // EFFECTS: Starts the next round.
    private void nextRoundAction() {
        pianoGame.nextRound();
        playRound();
    }

    // MODIFIES: this
    // EFFECTS: Saves the game and exits the program.
    private void saveGameAction() {
        jsonWriter = new JsonWriter(JSON_STORE);
        try {
            jsonWriter.open();
            jsonWriter.write(pianoGame);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Your game has been saved!");
            System.exit(0);
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
    // EFFECTS: Prints the event log.
    private void printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n\n");
        }
    }
}