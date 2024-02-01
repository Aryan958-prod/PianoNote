# PiaNote

## PREFACE


- The **PiaNote** is a Java desktop game that allows users to improve their music note recognition skills in a very fun and interactive way. The game will play a music note and the user has to correctly identify the notes by clicking on the correct note from the options given.

- The application is suitable for any music student, beginners or anyone interested in improving their musical accuracy.

- I am a passionate songwriter and coder. Creating a very simple music game that helps people to learn and practice music notes aligns with my interest in both programming and music writing. It is a very fun and nerdy way to combine these two passions of mine into a single project.

## User Stories

- As a user, I want to choose the number of players.
- As a user, I want to be able to enter my name and start the game.
- As a user, I want to see a musical note played and be able to choose my answer from the options given.
- As a user, I want to receive immediate feedback on whether my answer was correct or not.
- As a user, I want to earn points for correctly identifying notes.
- As a user, I want to see my score at the end of each game session.
- As a user, I want to be able to choose to save my progress in the game when I quit.
- As a user, I want to be able to reload my previous progress when I start the game.


In this Piano Note Challenge application, the two classes with a relationship where one class represents an individual item and the other class collects a list of these items are:

1. **Class Player (X class):** This class represents an individual player participating in the game. It contains information about a player, such as their name and score.

2. **Class PianoGame (Y class):** The PianoGame class collects a list of Player objects. It maintains a list of players, and their scores and their turns, making it a container for multiple Player instances.

The `PianoGame` class holds a collection (list) of `Player` objects, where each `Player` instance represents a player's data. This relationship allows the `PianoGame` class to store and manage multiple players' scores within the game.

## Instructions for Grader

- The application can be run by running the main method in the Main class.
- The piano audio files are located in the Audio Directory inside the data folder.
- You can save the state of the game by clicking on the save button on the dialogue box when you exit the application.
- You can load the state of the game by clicking on the load button on the main menu.
- You can quit the game by clicking on no to not to continue the game after a player's turn.
- You can see the icons and images used in the images directory of the data folder. 

## Phase 4: Task 2
Thu Nov 30 22:55:30 PST 2023
model.Player@3c2bef81's score set to 4


Thu Nov 30 22:55:30 PST 2023
model.Player@6d2a6b99's score set to 2


Thu Nov 30 22:55:31 PST 2023
New note: E


Thu Nov 30 22:55:35 PST 2023
New note: E


Thu Nov 30 22:55:37 PST 2023
model.Player@6d2a6b99 scored!


Thu Nov 30 22:55:37 PST 2023
model.PianoGame@490f3001 scored!



Process finished with exit code 0

## Phase 4: Task 3
The UML diagram you've shared represents the structure of a software system. It includes eight classes: `Event`, 
`Writable`, `Player`, `PianoGame`, `JsonReader`, `JsonWriter`, `Game`, and `Main`. These classes are interconnected 
through various relationships, which are represented by the lines between them.

Here's a brief interpretation of the classes and their relationships:

1. **Event**: This could represent an action or occurrence detected by the program.
2. **Writable**: This might be an interface that `Event` implements, suggesting that events can be written to a 
certain medium, like a file or a database.
3. **Player**: This class is linked to `Event`, indicating that a player can trigger events.
4. **PianoGame**: This class is connected to `Player`, suggesting that it uses player input to drive the game logic.
5. **JsonReader** and **JsonWriter**: These classes are linked to `PianoGame`, suggesting they might be used for saving 
and loading game data in JSON format.
6. **Game**: This class is linked to `JsonWriter`, which could mean that it uses `JsonWriter` to save its state.
7. **Main**: This class is likely the entry point of the application. It's linked to `Game`, suggesting that it creates 
and controls instances of the game.
8. **EventLog**: This class is linked to `Main` and `Event`, suggesting that it might be used to log events 
that occur during the execution of the program.


One of the things that I would love to improve is to have the dark theme in the game. I would also love to add more 
octaves and chords to the game. Due to the GUI limitations I was not able to allow more than 6 players at a time even 
though the game logic allows for more than 6 players. I would love to improve the GUI to allow more than 6 players at a
time. I would also love to add more features to the game such as allowing the user to have a timer for each turn and 
introduce playing of multiple chords at the same time in the quiz.

   

## Citation
Persistance package and classes were adapted from the following source:
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
The Event and EventLog classes were adapted from the following source:
https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
