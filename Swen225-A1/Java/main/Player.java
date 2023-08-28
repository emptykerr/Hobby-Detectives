package main;

import controller.ChecklistPanel;
import view.Ongoing;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A player instance represents a player playing the game.
 * Handles the user input for that player which controls a character
 * A player does a turn, makes guesses, makes solve attempt
 */
public class Player {
    private final Character character;
    private final List<Card> hand;
    private HobbyDetectives game;
    private Set<Square> visited = new HashSet<>();
    private int moves = 0;

    private static Ongoing ongoingView;

    private boolean eliminated = false;
    private boolean won = false;
    private boolean dieRolled = false;
    private boolean guessMade = false;
    private boolean solveAttempted = false;

    private String name;
    private Player playerChosen = null;

    private String actionPerformed = "";

    private ChecklistPanel checklist = new ChecklistPanel();

    public Player(String name, Character c, HobbyDetectives g) {
        this.name = name;
        character = c;
        game = g;
        hand = new ArrayList<>();
    }

    /**
     * Get the player's name.
     *
     * @return The enum value representing the player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Reveal a card of the player's choice.
     *
     * @param guess The current player's guess.
     * @return The card to be revealed (can be null).
     */
    public Card reveal(Guess guess) {
        List<Card> guessOverlap = new ArrayList<>();
        playerChosen = null;

        ongoingView.updateGrid();

        for (Player p : HobbyDetectives.getOrderedPlayers(HobbyDetectives.getPlayers(), this)) {
            if (!p.character.getName().equals(this.character.getName())) {
                for (Card card : p.hand) {
                    if (guess.getCards().contains(card)) {
                        playerChosen = p;
                        guessOverlap.add(card);
                    }
                }
                if (!guessOverlap.isEmpty()) {
                    break;
                }
                ongoingView.printToUI(p.character.getName() + " cannot show");
            }
        }



        if (guessOverlap.isEmpty()) {
            return null;
        } else {
            ongoingView.printToUI("Pass the tablet to " + playerChosen.character.getName());
            ongoingView.printToUI("Please enter the card you would like to reveal");
            String revealCardName = ongoingView.askCardDropdown(guessOverlap.stream().map(c -> c.getCardName()).toArray());
            Card result = null;
            for (Card c : game.getAllCards()){
                if (c.getCardName().equals(revealCardName)) {
                    result = c;
                }
            }
            return result;
        }
    }


    /**
     * returns the current state of the game for the main class
     *
     * @return
     */
    public boolean checkGamestate() {
        return won;
    }

    /**
     * return whether the player has been eliminated to check if turn should be skipped.
     *
     * @return
     */
    public boolean checkEliminated() {
        return eliminated;
    }

    /**
     * Moves the player out of the estate, checks every door
     * the estate has, and offers the player to leave
     * through one of the doors. Teleports the player to the chosen
     * door exit.
     */
    public void moveOutOfEstate(char pressed) {
        if (moves > 0) {
            Estate e = character.getSquare().getEstate();
            String moveDirection = switch (pressed) {
                case 'W' -> "Top";
                case 'A' -> "Left";
                case 'S' -> "Bottom";
                case 'D' -> "Right";
                default -> throw new Error("Invalid move direction");
            };

            // teleport the player to the door
            for (Map.Entry door : e.doors.entrySet()) {
                if (door.getValue().equals(moveDirection)) {
                    character.setSquare((Square) door.getKey());
                }
            }

            if (character.step(pressed) != null) {
                moves--;
                actionPerformed = "You exited through the " + moveDirection + " door";

                ongoingView.printToUI("You exited through the " + moveDirection + " door");
            }
            ongoingView.updateGrid();
        }
    }

    /**
     * moves the characters on the board
     * @param pressed the direction the user has chosen to go
     */
    public void doBoardMove(char pressed) {
        if (moves > 0) {
            // pressed is either W A S or D
            Square res = character.step(pressed);

            if (res != null) {
                Estate e = character.getSquare().getEstate();
                if (e != null) {
                    // entered an estate, moving is over
                    ongoingView.updateGrid();
                    character.moveCharacterIntoEstate(e);
                    moves = 0;
                    doGuess();
                } else {
                    moves--;
                }
            }
            ongoingView.updateGrid();
        }
    }

    /**
     * Gives the player a move value from two dice rolls.
     */
    public void doDieRoll() {
        if (!dieRolled) {
            int die1 = Die.roll();
            int die2 = Die.roll();
            moves = die1 + die2;
            actionPerformed = "You rolled a " + die1 + " and " + die2 + " for a total of " + moves;
            ongoingView.printToUI("You rolled a " + die1 + " and " + die2 + " for a total of " + moves);
            dieRolled = true;
        } else {
            ongoingView.printToUI("You have already rolled!");
        }
    }

    /**
     * Resets all bool player values for next turn
     */
    public void reset(){
        moves = 0;
        dieRolled = false;
        guessMade = false;
        solveAttempted = false;
    }

    /**
     * Perform a guess by the player.
     * Checks if each player can refute
     */
    public void doGuess() {
        if (guessMade) {
            ongoingView.printToUI("You have already made a guess!");
            return;
        }
        if (character.getSquare().getEstate() == null) {
            ongoingView.printToUI("You must be in an estate to guess");
            return;
        }

        Card card = reveal(inputGuess(character.getSquare().getEstate()));


        if (card != null) {
            ongoingView.printToUI("Pass the tablet back to " + character.getName());
            ongoingView.printToUI("The card shown to you by " + playerChosen.character.getName() + " is: " + card.getCardName());
        } else {
            ongoingView.printToUI("No players could show you any cards");
        }
        guessMade = true;
    }

    /**
     * Collects a solve attempt from the player and checks whether it is correct
     *
     * @return true for the guess was correct or false for not
     */
    public void doSolveAttempt() {
        if (solveAttempted) {
            ongoingView.printToUI("You have already made a solve attempt this turn!");
            return;
        }
        Guess guess = inputGuess(null);
        if (guess.equals(HobbyDetectives.getSolution())) {
            won = true;
            ongoingView.printToUI("You solved correctly! You are the winner!");

            game.end();
        } else {
            eliminated = true;
            ongoingView.printToUI("Unfortunately, you guessed incorrectly. You are eliminated. However, you must still refute guesses");
        }
        solveAttempted = true;
    }

    /**
     * Helper method to create a Guess (collection of the three card types) for a guess or solve attempt
     *
     * @return Guess created, consisting of a Character, Weapon and Estate Card
     */
    private Guess inputGuess(Estate estate) {
        EstateCard estateCard;

        if (estate != null) {
            estateCard = HobbyDetectives.estateMap.get(estate.getName());
        } else {
            estateCard = selectEstate();
        }

        CharacterCard characterCard = selectCharacter();
        WeaponCard weaponCard = selectWeapon();


        return new Guess(characterCard, weaponCard, estateCard);
    }

    /**
     * helper method for selecting the estate for guessing
     *
     * @return the card selected
     */
    private EstateCard selectEstate() {
        String estateName = ongoingView.askCardDropdown(HobbyDetectives.estateMap.keySet().toArray());
        EstateCard estateCard = HobbyDetectives.estateMap.get(estateName);
        return estateCard;
    }

    /**
     * helper method for selecting the character guessing
     *
     * @return the card selected
     */
    private CharacterCard selectCharacter() {
        String characterName = ongoingView.askCardDropdown(HobbyDetectives.characterMap.keySet().toArray());
        CharacterCard characterCard = HobbyDetectives.characterMap.get(characterName);

        //for each player, find the character that matches the guessed character, and move them into the current estate
        for (Player p : HobbyDetectives.getPlayers()) {
            String selectedCharacter = characterCard.getCardName();
            String characterFromList = p.getCharacter().getName().name();
            if (selectedCharacter.equals(characterFromList) && p.getCharacter().getSquare().getEstate() != character.getSquare().getEstate()) {
                p.getCharacter().moveCharacterIntoEstate(character.getSquare().getEstate());
            }
        }

        return characterCard;
    }

    /**
     * helper method for selecting the weapon for guessing
     *
     * @return the card selected
     */
    private WeaponCard selectWeapon() {
        String weaponName = ongoingView.askCardDropdown(HobbyDetectives.weaponMap.keySet().toArray());
        WeaponCard weaponCard = HobbyDetectives.weaponMap.get(weaponName);
        Estate currentEstate = character.getSquare().getEstate();
        Weapon currentEstateWeapon = currentEstate.getWeapon();
        Estate toSwapEstate = null;
        Weapon toSwapWeapon = null;

        for (Weapon w : HobbyDetectives.getWeapons()) {
            if (w.getName().equals(weaponCard.getCardName())) {
                toSwapWeapon = w;
                toSwapEstate = w.getEstate();
            }
        }

        // make swap
        if (toSwapWeapon != null && toSwapEstate != null) {
            currentEstate.setWeapon(toSwapWeapon);
            toSwapEstate.setWeapon(currentEstateWeapon);
            currentEstate.getWeaponSquare().setWeapon(toSwapWeapon);
            toSwapEstate.getWeaponSquare().setWeapon(currentEstateWeapon);
        }

        return weaponCard;
    }

    /**
     * Adds card to players hand
     *
     * @param c
     */
    public void addCard(Card c) {
        hand.add(c);
    }

    /**
     * Get cards in players hand
     *
     * @return list of cards in hand
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Sets the ongoing view
     * @param ong ongoing view
     */
    public static void setOngoingView(Ongoing ong) {
        ongoingView = ong;
    }

    /**
     * Get the character that belongs to the player
     *
     * @return the character object
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Get moves for this player
     * @return moves
     */
    public int getMoves(){
        return moves;
    }

    /**
     * get action preformed by this player
     * @return action preformed
     */
    public String getActionPerformed(){
        return actionPerformed;
    }

    public ChecklistPanel getChecklist(){
        return this.checklist;
    }

}
