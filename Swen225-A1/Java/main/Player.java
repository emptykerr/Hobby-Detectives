package main;

import view.Ongoing;

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
    private final Board board;
    private HobbyDetectives game;

    
    private Ongoing ongoingView;

    private boolean eliminated = false;
    private boolean won = false;

    private String name;
    private Player playerChosen = null;

    public Player(String name, Character c, Board b, HobbyDetectives g) {
        this.name = name;
        character = c;
        board = b;
        game = g;
        //ongoingView = g.getOngoingView();
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
        int cardNum;

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
                System.out.println(p.character.getName() + " cannot show");
            }
        }

        if (guessOverlap.isEmpty()) {
            return null;
        } else {
            System.out.println("Pass the tablet to " + playerChosen.character.getName() + " you'll have 10 seconds");
            for (int i = 10; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                    System.out.println(i);
                } catch (InterruptedException ie) {
                    System.out.println("Error: " + ie);
                }
            }

            System.out.println("Please enter the number corresponding to the card you would like to reveal: ");
            for (int i = 0; i < guessOverlap.size(); i++) {
                System.out.println(i + ": " + guessOverlap.get(i).getCardName());
            }

            while (true) {
                Scanner in = new Scanner(System.in);
                if (in.hasNextInt()) {
                    cardNum = in.nextInt();
                    if (cardNum >= 0 && cardNum < guessOverlap.size()) {
                        break;
                    }
                }
                System.out.println("Please enter a valid number");
            }
            return guessOverlap.get(cardNum);
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
     * Performs the players turn:
     * Each turn the player will
     * roll 2 die
     * Prompt the player for a direction
     * While the player still has moves left(die value sum)
     * Move the player in the prompted direction
     * Check if the player has entered an estate
     * Offer them to guess or solve
     * Run the required methods
     */
    public void doTurn() {
        if (eliminated) {
            System.out.println(character.getName() + " is eliminated");
            ongoingView.printToUI(character.getName() + " is eliminated");
            System.out.println("Skipping turn...");
            return;
        }

        System.out.println("It is " + getName() + "'s turn");
        ongoingView.printToUI("It is " + getName() + "'s turn");
        doMove();

        while (true) {
            if (eliminated) {
                break;
            }
            System.out.println("Would you like to make a solve attempt? (Y/N)");
            //Scanner s = new Scanner(System.in);
            //String ans = s.nextLine().toUpperCase();
            boolean solveAttempt = ongoingView.askSolveAttempt(this);
            if (solveAttempt) {
                if (doSolveAttempt()) {
                    System.out.println("You solved correctly!");
                    ongoingView.printToUI("You solved correctly!");
                    won = true;
                } else {
                    System.out.println("\n----------------------------------------");
                    System.out.println("You solved incorrectly");
                    System.out.println("You can no longer guess or solve the murder, but you can still refute guesses.");
                    System.out.println("----------------------------------------\n");
                    eliminated = true;
                    System.out.println("Press enter to continue");
                }
                break;
            } else {
                break;
            }
        }
        // Turn over
    }

    /**
     * Performs the movement of the player.
     * Checks if the player is in an estate when their turn starts, and offers them
     * to leave the said estate, or continue to make a guess.
     */
    public void doMove() {
        if (character.getSquare().getEstate() != null) {
            while (true) {
                //System.out.println("Would you like to leave the estate? (Y/N)");
                //Scanner s = new Scanner(System.in);
                //String ans = s.nextLine().toUpperCase();
                boolean leaveEstate = ongoingView.askLeaveEstate(this);
                if (leaveEstate) {
                    character.getSquare().removeCharacter();
                    moveOutOfEstate();
                    return;
                } else {
                    doGuess();
                    return;
                }
            }
        } else {
            doBoardMove();
        }
    }

    /**
     * Moves the player out of the estate, checks every door
     * the estate has, and offers the player to leave
     * through one of the doors. Teleports the player to the chosen
     * door exit.
     */
    private void moveOutOfEstate() {
        Estate currentEstate = character.getSquare().getEstate();
        System.out.println("Which door would you like to leave through?");
        System.out.println("There are " + currentEstate.doors.size() + " doors:");

        for (Map.Entry door : currentEstate.doors.entrySet()) {
            System.out.println(door.getValue() + " door");
        }

        while (character.getSquare().getEstate() != null) {
            System.out.println("Enter 'U', 'D', 'L', or 'R' to move");
            Scanner in = new Scanner(System.in);
            String direction = ongoingView.askDoorDirection(this);

            String moveDirection = switch (direction) {
                case "U" -> "Top";
                case "R" -> "Right";
                case "D" -> "Bottom";
                case "L" -> "Left";
                default -> "";
            };

            for (Map.Entry door : currentEstate.doors.entrySet()) {
                if (door.getValue().equals(moveDirection)) {
                    character.setSquare((Square) door.getKey());
                }
            }

            if (character.step(direction) != null) {
                board.drawToScreen();
                System.out.println("You exited through the " + moveDirection + " door");
                ongoingView.printToUI("You exited through the " + moveDirection + " door");
                doBoardMove();
                break;
            } else {
                System.out.println("There is no door there or the door is blocked. Try again");
            }
        }
    }

    /**
     * Gives the player a move value from two dice rolls.
     * Perform the move on the game board.
     * Displays the information of the entered estate, including its name, and its weapon.
     */
    private void doBoardMove() {
        int die1 = Die.roll();
        int die2 = Die.roll();
        int moves = die1 + die2;
        System.out.println("You have " + this.getHand().size() + " cards");
        ongoingView.printToUI("You have " + this.getHand().size() + " cards");
        for (Card card : this.getHand()) {
            System.out.println(card.getClass().toString().substring(card.getClass().toString().lastIndexOf(" ") + 1) + ": " + card.getCardName());
        }
        System.out.println("\nYou rolled a " + die1 + " and " + die2 + " for a total of " + moves);
        System.out.println("Moves left: " + moves);
        Set<Square> visited = new HashSet<>();
        while (moves > 0) {
            String askPlayerDirection = "Where would you like to move to? (Enter U, R, D or L to move): ";
            // Change what direction options are available depending on walls

            System.out.println(askPlayerDirection);
            Scanner in = new Scanner(System.in);
            String direction = in.nextLine().toUpperCase();

            String moveDirection = switch (direction) {
                case "U" -> "Up";
                case "R" -> "Right";
                case "D" -> "Down";
                case "L" -> "Left";
                default -> "";
            };

            if (character.step(direction) != null) { // making sure they put a correct input and the square they're going to does not have a player in it
                board.drawToScreen();
                if (character.getSquare().getEstate() != null) {
                    moves = 0;
                    System.out.println("\n--------------------------------------------");
                    System.out.println("You have entered " + character.getSquare().getEstate().getName());
                    System.out.println("It contains the weapon: " + character.getSquare().getEstate().getWeapon().getName());
                    System.out.println("--------------------------------------------\n");

                    doGuess();
                } else {
                    moves--;
                    System.out.println("You moved " + moveDirection + ". Moves left: " + moves);
                }
            } else {
                System.out.println("Invalid direction, please try again.");
            }
        }
    }

    /**
     * Perform a guess by the player.
     * Checks if each player can refute
     */
    public void doGuess() {
        Card card = reveal(inputGuess(character.getSquare().getEstate()));

        if (card != null) {
            System.out.println("Pass the tablet back to " + character.getName() + ". You'll have 10 seconds");
            for (int i = 10; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                    System.out.println(i);
                } catch (InterruptedException ie) {
                    System.out.println("Error: " + ie);
                }
            }

            System.out.println("The card shown to you by " + playerChosen.character.getName() + " is: " + card.getCardName());
        } else {
            System.out.println("No players could show you any cards");
        }
    }

    /**
     * Collects a solve attempt from the player and checks whether it is correct
     *
     * @return true for the guess was correct or false for not
     */
    public boolean doSolveAttempt() {
        Guess guess = inputGuess(null);

        return guess.equals(HobbyDetectives.getSolution());
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
        System.out.println("Please input the estate name that you want to interrogate out of: ");
        printEnumValues(HobbyDetectives.EstateName.values());

        EstateCard estateCard;
        while (true) {
            String input = getUserInput();
            HobbyDetectives.EstateName estateName = HobbyDetectives.EstateName.estateNameMap.get(input.toLowerCase());
            if (estateName != null) {
                estateCard = HobbyDetectives.estateMap.get(estateName.toString());
                break;
            } else {
                System.out.println("That estate does not exist. Please try again");
            }
        }

        return estateCard;
    }

    /**
     * helper method for selecting the character guessing
     *
     * @return the card selected
     */
    private CharacterCard selectCharacter() {
        System.out.println("Please input the character name that you want to interrogate out of: ");
        printEnumValues(HobbyDetectives.PlayerName.values());

        CharacterCard characterCard;
        while (true) {
            String input = getUserInput();
            HobbyDetectives.PlayerName characterName = HobbyDetectives.PlayerName.playerNameMap.get(input.toLowerCase());

            if (characterName != null) {
                characterCard = HobbyDetectives.characterMap.get(characterName.toString());
                if (character.getSquare().getEstate() == null) break;
                System.out.println("\n---------------------------------------------------");
                System.out.println("Bringing " + characterName + " to the estate for interrogating");
                System.out.println("---------------------------------------------------\n");

                //for each player, find the character that matches the guessed character, and move them into the current estate
                for (Player p : HobbyDetectives.getPlayers()) {
                    String selectedCharacter = characterCard.getCardName();
                    String characterFromList = p.getCharacter().getName().name();
                    if (selectedCharacter.equals(characterFromList) && p.getCharacter().getSquare().getEstate() != character.getSquare().getEstate()) {
                        p.getCharacter().moveCharacterIntoEstate(character.getSquare().getEstate());
                    }
                }
                break;
            } else {
                System.out.println("That character does not exist. Please try again");
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
        System.out.println("Please input the weapon name that you want to interrogate out of: ");
        printEnumValues(HobbyDetectives.WeaponName.values());

        WeaponCard weaponCard;
        while (true) {
            String input = getUserInput();
            HobbyDetectives.WeaponName weaponName = HobbyDetectives.WeaponName.weaponNameMap.get(input.toLowerCase());
            if (weaponName != null) {
                weaponCard = HobbyDetectives.weaponMap.get(weaponName.toString());
                break;
            } else {
                System.out.println("That weapon does not exist. Please try again");
            }
        }

        return weaponCard;
    }

    /**
     * Helper method to print the cards to choose from, from the enum values.
     */
    private void printEnumValues(Enum[] values) {
        Arrays.stream(values).forEach(c -> System.out.println(c.toString()));
    }

    /**
     * Creates a scanner to be used in the guessing methods
     *
     * @return the inputted value from the player
     */
    private String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
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
     * Get the character that belongs to the player
     *
     * @return the character object
     */
    public Character getCharacter() {
        return character;
    }
}
