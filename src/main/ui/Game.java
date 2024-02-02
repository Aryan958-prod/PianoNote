package ui;

import model.PianoGame;
import persistance.JsonWriter;
import persistance.JsonReader;

import java.io.FileNotFoundException;
import java.util.Scanner;

// Represents a game of piano note challenge.

public class Game {
    private static final String JSON_STORE = "./data/game.json";
    private PianoGame pianoGame = new PianoGame();
    private Scanner scanner = new Scanner(System.in);
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);



    // EFFECTS: Constructs a game with the given number of players.
    public Game() {
        if (!loadOldGame()) {
            int numberOfPlayers = startNewGame();
            if (numberOfPlayers == 1) {
                System.out.println("Enter player's name: ");
                String playerName = scanner.nextLine().trim();
                pianoGame.addPlayer(playerName);
            } else {
                for (int i = 0; i < numberOfPlayers; i++) {
                    System.out.println("Enter player " + (i + 1) + "'s name: ");
                    String playerName = scanner.nextLine().trim();
                    pianoGame.addPlayer(playerName);
                }
            }

            System.out.println("Press enter to start: ");
            scanner.nextLine();
        }
    }

    private boolean loadOldGame() {
        System.out.println("Welcome to the Piano Note Challenge!");
        System.out.println("Would you like to load a saved game? [Enter Y or N]");
        String loadGame = scanner.nextLine().toUpperCase().trim();
        if (loadGame.equals("Y")) {
            loadPianoGame();
            return true;
        }
        return false;
    }

    private int startNewGame() {
        System.out.println("How many players are there?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    // MODIFIES: this
    // EFFECTS: Runs the game.
    public void runGame() {
        while (true) {
            pianoGame.nextRound();
            playARound();
            System.out.println("Scores:");
            for (int i = 0; i < pianoGame.getNumberOfPlayers(); i++) {
                System.out.println(pianoGame.getPlayerName(i) + ": " + pianoGame.getCurrentPlayerScore(i));
            }
            System.out.println("Would you like to play another round? [Enter Y or N]");
            String playAgain = scanner.nextLine().toUpperCase().trim();
            if (playAgain.equals("N")) {
                break;
            } else if (playAgain.equals("Y")) {
                System.out.println("Press enter to start: ");
                scanner.nextLine();
            }
        }
        System.out.println("Would you like to save the game? [Enter Y or N]");
        String saveGame = scanner.nextLine().toUpperCase().trim();
        if (saveGame.equals("Y")) {
            savePianoGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: Plays a round of the game.
    private void playARound() {
        for (int i = 0; i < pianoGame.getNumberOfPlayers(); i++) {
            System.out.println(pianoGame.getCurrentPlayerName() + ":\nPress Enter to listen to the note.");
            scanner.nextLine();

            System.out.println("[Played " + pianoGame.getRandomNote() + "]");
            System.out.println("Which note was played just now? (C, D, E, F, G, A, B)");
            String playerAnswer = scanner.nextLine();
            if (pianoGame.verifyAnswer(playerAnswer)) {
                pianoGame.currentPlayerScores();
                System.out.println("Yes, it is correct.");
            } else {
                System.out.println("Wrong answer. " + pianoGame.getCurrentNote() + " was played.");
            }
            pianoGame.nextPlayer();
        }
    }

    // EFFECTS: Saves the game to file.
    // MODIFIES: this
    private void savePianoGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(pianoGame);
            jsonWriter.close();
            System.out.println("Saved the game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to write to file: " + JSON_STORE);
        }
    }
    // MODIFIES: this
    // EFFECTS: Loads the game from file.
    private void loadPianoGame() {
        try {
            pianoGame = jsonReader.read();
            System.out.println("Loaded the game from " + JSON_STORE);
        } catch (Exception e) {
            System.err.println("Unable to read from file: " + JSON_STORE);
        }
    }


}