import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Player {
    private final Character character;
    private final List<Card> hand;
    private final Board board;

    boolean won = false;

    Player playerChosen = null;

    public Player(Character c, Board b) {
        character = c;
        board = b;
        hand = new ArrayList<Card>();
    }

    /**
     * Get the player name
     * @return enum value of player name
     */
    public HobbyDetectives.PlayerName getName() {
        return character.getName();
    }

    /**
     * Reveal a card of the players choice
     *
     * @param guess current player's guess
     * @return the card to be revealed (can be null)
     */
    public Card reveal(Guess guess) {
        List<Card> guessOverlap = new ArrayList<>();
        playerChosen = null;
        int cardNum;
        // have to loop through all characters to find one that has at least one card that is in the guess
        for (Player p : HobbyDetectives.getPlayers()) {
            if (!p.character.getName().equals(this.character.getName())) {
                for (Card card : p.hand) {
                    // Check if player card is part of the guess
                    if (guess.getCards().contains(card)) {
                        playerChosen = p;
                        guessOverlap.add(card);
                    }
                }
                if (!guessOverlap.isEmpty()){
                    break;
                }
                System.out.println(p.character.getName() + " cannot show");
            }
        }
        if (guessOverlap.isEmpty()) {
            // All players have no cards to reveal
            return null;
        } else {
            // gives time to swap to the next player so other players cannot see what options the player wants has to
            // show the player making the guess.
            System.out.println("Pass the tablet to " + playerChosen.character.getName() + " you'll have 10 seconds");
            for (int i = 10; i >= 0; i--){
                try {
                    Thread.sleep(1000);
                    System.out.println(i);
                } catch (InterruptedException ie){
                    System.out.println("Error: " + ie);
                }
            }

            System.out.println("Please enter the number corresponding to the card you would like to reveal: ");
            for (int i = 0; i < guessOverlap.size(); i++) {
                System.out.println(i + ": " + guessOverlap.get(i).getCardName());
            }
            while (true) {
                Scanner in = new Scanner(System.in);
                // Check user gave valid card number
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
     * @return
     */
    public boolean checkGamestate(){
        return won;
    }



    /**
     * Each turn the player will
     * roll 2 die
     * Prompt the player for a direction
     * While the player still has moves left(die value sum)
     * Move the player in the prompted direction
     * Check if the player has entered an estate
     * Offer them to guess or solve
     * Run the required methods
     * - Matt
     */
    public void doTurn() {

        doMove();

        while (true) {
            System.out.println("Would you like to make a solve attempt? (Y/N)");
            Scanner s = new Scanner(System.in);
            String ans = s.nextLine().toUpperCase();
            if (ans.equals("Y")) {
                if (doSolveAttempt()){
                    System.out.println("You solved correctly!");
                    won = true;
                    break;
                }
            } else if (ans.equals("N")) {
                break;
            } else {
                System.out.println("Put either 'Y' or 'N' (is not case sensitive)");
            }
        }
        // Turn over
    }

    public void doMove() {
        if (character.getSquare().getEstate() != null) {
            while (true) {
                System.out.println("Would you like to move? (Y/N)");
                Scanner s = new Scanner(System.in);
                String ans = s.nextLine().toUpperCase();
                if (ans.equals("Y")) {
                    character.getSquare().removeCharacter();
                    moveOutOfEstate();
//                    doBoardMove();
                    return;
                } else if (ans.equals("N")) {
                    doGuess();
                    return;
                } else {
                    System.out.println("Put either 'Y' or 'N' (is not case sensitive)");
                }
            }
        } else {
            doBoardMove();
        }
    }

    private void moveOutOfEstate(){
        Estate currentEstate = character.getSquare().getEstate();
        System.out.println("Which door would you like to leave through?");
        System.out.println("There are " + currentEstate.doors.size() + " doors:");

        for(Map.Entry door : currentEstate.doors.entrySet()){
            System.out.println(door.getValue() + " door");
        }

        while(character.getSquare().getEstate() !=null) {
            System.out.println("Enter 'U', 'D', 'L', or 'R' to move");
            Scanner in = new Scanner(System.in);
            String direction = in.nextLine().toUpperCase();

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
                doBoardMove();
            } else {
                System.out.println("There is no door there. Try again");
            }
        }
    }

    private void doBoardMove() {
        int die1 = Die.roll();
        int die2 = Die.roll();
        int moves = die1 + die2;
        System.out.println(character.getName() + "'s turn:");
        System.out.println("You have " + this.getHand().size() + " cards");
        for (Card card : this.getHand()){
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
                if (character.getSquare().getEstate() != null){
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
     * method to make a guess
     */
    public void doGuess() {
        Card card = reveal(inputGuess(character.getSquare().getEstate()));

        if (card != null) {
            System.out.println("Pass the tablet back to " + character.getName() + ". You'll have 10 seconds");
            for (int i = 10; i >= 0; i--){
                try {
                    Thread.sleep(1000);
                    System.out.println(i);
                } catch (InterruptedException ie){
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
     * @return the card selected
     */
    private EstateCard selectEstate() {
        System.out.println("Please input the estate name that you want to interrogate out of: ");
        printEnumValues(HobbyDetectives.EstateName.values());

        EstateCard estateCard;
        do {
            String input = getUserInput();
            HobbyDetectives.EstateName estateName = HobbyDetectives.EstateName.estateNameMap.get(input.toLowerCase());
            if (estateName != null) {
                estateCard = HobbyDetectives.estateMap.get(estateName.toString());
                break;
            } else {
                System.out.println("That estate does not exist. Please try again");
            }
        } while (true);

        return estateCard;
    }

    /**
     * helper method for selecting the character guessing
     * @return the card selected
     */
    private CharacterCard selectCharacter() {
        System.out.println("Please input the character name that you want to interrogate out of: ");
        printEnumValues(HobbyDetectives.PlayerName.values());

        CharacterCard characterCard;
        do {
            String input = getUserInput();
            HobbyDetectives.PlayerName characterName = HobbyDetectives.PlayerName.playerNameMap.get(input.toLowerCase());
            if (characterName != null) {
                characterCard = HobbyDetectives.characterMap.get(characterName.toString());
                break;
            } else {
                System.out.println("That character does not exist. Please try again");
            }
        } while (true);

        return characterCard;
    }
    /**
     * helper method for selecting the weapon for guessing
     * @return the card selected
     */
    private WeaponCard selectWeapon() {
        System.out.println("Please input the weapon name that you want to interrogate out of: ");
        printEnumValues(HobbyDetectives.WeaponName.values());

        WeaponCard weaponCard;
        do {
            String input = getUserInput();
            HobbyDetectives.WeaponName weaponName = HobbyDetectives.WeaponName.weaponNameMap.get(input.toLowerCase());
            if (weaponName != null) {
                weaponCard = HobbyDetectives.weaponMap.get(weaponName.toString());
                break;
            } else {
                System.out.println("That weapon does not exist. Please try again");
            }
        } while (true);

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
     * @return the inputted value from the player
     */
    private String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }



    /**
     * Adds card to players hand
     * @param c
     */
    public void addCard(Card c){
        hand.add(c);
    }

    public List<Card> getHand(){
        return hand;
    }

    public Character getCharacter(){
        return character;
    }
}
