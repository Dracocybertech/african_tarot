package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import deck.Card;
import deck.Deck;
import deck.RemovingTooManyCards;
import people.Player;
import people.PlayerGroup;
import people.PlayerNameTooLongException;
import people.TooManyCardsException;

public class Game {

    // Keep a trace of the players at the start of the game in case we need
    // to use them again for another game
    private PlayerGroup players;
    private PlayerGroup playersAlive;
    private Scanner scanner;
    private Deck deck;
    // Store the player who played the fool card
    private HashMap<Player, Card> foolPlayer;

    public static final int NUMBER_PLAYERS = 4;
    public static final int ROUND_MAX = 5;
    
    public static final String GO_NEXT_PLAYER = " to go to next player.";

    public enum FoolValues {
        MIN_VALUE,
        MAX_VALUE,
    }

    private static final Map<FoolValues, Integer> FoolValuesMap = new EnumMap<>(FoolValues.class);

    // Create a Logger
    Logger logger = Logger.getLogger(Game.class.getName());

    /**
     * \brief Constructor Game
     * Game(): create the players and the deck of cards.
     */
    public Game() {
        players = new PlayerGroup(NUMBER_PLAYERS);
        playersAlive = new PlayerGroup(NUMBER_PLAYERS);
        scanner = new Scanner(System.in);
        deck = new Deck();
        deck.buildDeck();
        deck.shuffle();
        foolPlayer = new HashMap<>();
        FoolValuesMap.put(FoolValues.MIN_VALUE, 0);
        FoolValuesMap.put(FoolValues.MAX_VALUE, 22);
    }

    /**
     * \brief Getter players
     *
     * getPlayers() : Return the list of the players in the game.
     * \return ArrayList<Player>
     */
    public List<Player> getPlayers() {
        return this.players.getPlayers();
    }

    /**
     * \brief Setter players
     *
     * setPlayers(ArrayList<Player> players) : Set a new list of players. Update the
     * current list of
     * players alive so the list of players alive is only made with players in the
     * game.
     * \param ArrayList<Player> players
     * 
     * @throws BadNumberOfPlayersException
     */
    public void setPlayers(List<Player> players) throws BadNumberOfPlayersException {
        if (players.size() > NUMBER_PLAYERS) {
            throw new BadNumberOfPlayersException("The number of player must be less or equal than :" + NUMBER_PLAYERS);
        }
        this.players.setPlayers(players);
        this.playersAlive.setPlayers(new ArrayList<>(players));
        this.playersAlive.removeDeadPlayers();
    }

    /**
     * \brief Getter playersAlive
     *
     * getPlayersAlive() : Return the list of the players alive in the game.
     * \return ArrayList<Player>
     */
    public List<Player> getPlayersAlive() {
        return this.playersAlive.getPlayers();
    }

    /**
     * \brief Getter player
     *
     * getPlayer(int index) : Return the a specific player
     * \return Player
     */
    public Player getPlayer(int index) {
        return this.players.getPlayer(index);
    }

    /**
     * \brief Getter player alive
     *
     * getPlayerAlive(int index) : Return the a specific player
     * \return Player
     */
    public Player getPlayerAlive(int index) {
        return this.playersAlive.getPlayer(index);
    }

    /**
     * \brief Number players
     *
     * getNumberPlayers() : Return the number of total players in the game.
     * \return int
     */
    public int getNumberPlayers() {
        return this.players.getNumberPlayers();
    }

    /**
     * \brief Number players alive
     *
     * getNumberPlayersAlive() : Return the number of total players in the game.
     * \return int
     */
    public int getNumberPlayersAlive() {
        return this.playersAlive.getNumberPlayers();
    }

    /**
     * \brief Getter Deck
     *
     * getDeck() : Return the current deck in the game.
     * \return Deck
     */
    public Deck getDeck() {
        return this.deck;
    }

    /**
     * \brief Getter Deck size
     *
     * getDeckSize() : Return the size of current deck in the game.
     * \return int
     */
    public int getDeckSize() {
        return this.deck.getSize();
    }

    /**
     * \brief Getter Player who played the Fool card
     *
     * getFoolPlayer() : Return the player who played the Fool for the current turn
     * and the card.
     * \return HashMap<Player, Card>
     */
    public Map<Player, Card> getFoolPlayer() {
        return this.foolPlayer;
    }

    /**
     * \brief Setter Player who played the Fool card
     *
     * setFoolPlayer(Player player, Card cardFool) : Set the player who played the
     * Fool for the current turn and the card.
     * \param Player player
     * \param Card cardFool
     */
    public void setFoolPlayer(Player player, Card cardFool) {
        this.foolPlayer.put(player, cardFool);
    }

    /**
     * \brief Create a Player
     *
     * createPlayer() : Allow the user to create a new Player with a specific name.
     * \return Player
     */
    public Player createPlayer() {
        Player player = null;
        while (player == null) {
            try {
                System.out.println("Enter a name (max 8 chars).");
                String userName = scanner.next(); // Read user input
                System.out.println("userName : " + userName);
                player = new Player(userName);
                if (this.players.hasPlayer(player)) {
                    System.err.println("This name is already used. Please choose another one.");
                    player = null;
                }
            } catch (PlayerNameTooLongException e) {
                System.err.println("The name of the player is too long.");
            }
        }
        clearTerminal();
        return player;
    }

    /**
     * \brief Create all player
     *
     * createPlayers() : Fill the game with all the players for launching a game.
     */
    public void createPlayers() {
        for (int i = 0; i < NUMBER_PLAYERS; i++) {
            Player player = createPlayer();
            players.addPlayer(player);
            // To have a distinct list of alive players, we need to create a new instance of
            // player
            playersAlive.addPlayer(player.copy(player));
        }
    }

    /**
     * \brief Distribute cards to players
     *
     * distributeCards(int numberCards) : Distribute exactly numberCards to each
     * player alive.
     * 
     * @throws NotEnoughCardsInDeckException
     * @throws RemovingTooManyCards
     * @throws TooManyCardsException
     */
    public void distributeCards(int numberCards)
            throws NotEnoughCardsInDeckException, TooManyCardsException, RemovingTooManyCards {
        if (numberCards * getNumberPlayersAlive() > this.deck.getSize()) {
            throw new NotEnoughCardsInDeckException("There isn't enough cards left in the deck to distribute " +
                    numberCards + " to each player.");
        }

        for (Player player : playersAlive.getPlayers()) {
            // Card removed from the deck are added to the player's hand
            player.setCards(deck.removeCards(numberCards));
        }
    }

    /**
     * \brief One player play
     * play(Player player) : The player can play any card from their hands.
     * \param Player player
     * \return Card
     */
    public Card playOnePlayer(Player player, Map<Player, Card> cardsPlayed) {
        // Should be a shallow copy, so any changed made to current player
        // will be reflected in the list of players
        playerTransition(player);
        Card cardPlayed = null;

        System.out.println("Life points: " + player.getLife());
        separatorPrint();

        System.out.println("Cards currently played: ");
        printCardsPlayed(cardsPlayed);
        separatorPrint();

        while (cardPlayed == null) {
            try {
                System.out.println("Which card would you like to play ?");
                printCardsPlayer(player.getCards());
                int indexCard = scanner.nextInt();
                cardPlayed = player.removeCard(indexCard);
                // If the player played the Fool
                if (cardPlayed.getValue() == Card.MAX_VALUE) {
                    setFoolPlayer(player, cardPlayed);
                }
            } catch (Exception e) {
                System.err.println("The card can't be played.");
                // Skip the next input to allow the function to wait for the next one
                scanner.next();
            }
        }
        enterWait(GO_NEXT_PLAYER);
        return cardPlayed;
    }

    private void printCardsPlayer(List<Card> cards) {
        // Number of the card
        int numberCard = 1;
        for (Card card : cards) {
            System.out.print(card + " [" + numberCard + "]");
            if (numberCard != cards.size()) {
                System.out.print(" | ");
            }
            numberCard += 1;
        }
        // The input return to the beginning
        System.out.println("");
    }

    /**
     * \brief All players play
     * playAllPlayers() : All players must play one card from their hand.
     * \return Card
     */
    public Map<Player, Card> playAllPlayers() {

        HashMap<Player, Card> results = new HashMap<>();
        for (Player player : playersAlive.getPlayers()) {
            results.put(player, playOnePlayer(player, results));
        }
        return results;
    }

    /**
     * \brief One player play for the last round
     * playOnePlayerLastRound(Player player, HashMap<Player, Card> opponentsCards,
     * HashMap<Player, Boolean> opponentsDecisions) :
     * The player bet if they won or lose this round by typing 0 for win or 1 for
     * lose. Win return true, Lose return false.
     * They will know their opponents cards and each decision players have made so
     * far.
     * \param Player player, HashMap<Player, Card> opponentsCards, HashMap<Player,
     * Boolean> opponentsDecisions
     * \return Boolean
     */
    public Boolean playOnePlayerLastRound(Player player, Map<Player, List<Card>> opponentsCards,
            Map<Player, Boolean> opponentsDecisions) {
        playerTransition(player);
        printLastRound(opponentsCards, opponentsDecisions);
        System.out.println("Do you bet that you win or lose this round?");

        Boolean decision = null;
        while (decision == null) {
            try {
                System.out.println("Enter 0 for win, 1 for lose.");
                int input = scanner.nextInt();
                if (input == 0) {
                    decision = true;
                } else if (input == 1) {
                    decision = false;
                }
            } catch (Exception e) {
                System.err.println("Error while processing input: " + e.getMessage());
                scanner.next();
                logger.log(Level.WARNING, "The input is not an int ", new InputMismatchException());
            }
        }
        enterWait(" to go to the next player.");
        return decision;
    }

    /**
     * \brief Print last round info
     * printLastRound(HashMap<Player, ArrayList<Card>> opponentsCards,
     * HashMap<Player, Boolean> opponentsDecisions):
     * Print cards and decisions taken by the opponents of the current player.
     * \param HashMap<Player, ArrayList<Card>> opponentsCards
     * \param HashMap<Player, Boolean> opponentsDecisions
     */
    private void printLastRound(Map<Player, List<Card>> opponentsCards,
            Map<Player, Boolean> opponentsDecisions) {
        System.out.println("Your opponents have those cards:");
        for (Map.Entry<Player, List<Card>> entries : opponentsCards.entrySet()) {
            String playerName = entries.getKey().getName();
            List<Card> cards = entries.getValue();
            // Print the cards of the player
            System.out.println(playerName + ": " + cards);
        }

        separatorPrint();

        if (!opponentsDecisions.isEmpty()) {
            System.out.println("Those are the decision taken so far:");
            for (Map.Entry<Player, Boolean> entries : opponentsDecisions.entrySet()) {
                String playerName = entries.getKey().getName();
                System.out.println(playerName + ": " + conversionBoolWinLose(entries.getValue()));
            }
        }
    }

    /**
     * \brief Convert boolean to win or lose word
     * conversionBoolWinLose(boolean decision): Return 'win' if true, 'lose'
     * otherwise.
     * \param boolean decision
     * \return String
     */
    private String conversionBoolWinLose(boolean decision) {
        if (decision) {
            return "win";
        }
        return "lose";
    }

    /**
     * \brief All players play the last round
     * playAllPlayersLastRound() : All players must bet if they win or lose this
     * round.
     * \return HashMap<Player, Boolean>
     */
    public Map<Player, Boolean> playAllPlayersLastRound() {
        HashMap<Player, Boolean> results = new HashMap<>();
        HashMap<Player, Boolean> opponentsDecisions = new HashMap<>();
        // According to the rules, the turn where players have one card
        // is special: players must bet if they win or lose and not a number of trick
        // they would win by the end of the round
        for (Player player : this.playersAlive.getPlayers()) {
            Map<Player, List<Card>> opponentsCards = buildOpponentsCards(player, this.playersAlive);
            Boolean decision = playOnePlayerLastRound(player, opponentsCards, opponentsDecisions);
            opponentsDecisions.put(player, decision);
            results.put(player, decision);
        }
        return results;
    }

    /**
     * \brief The cards of the opponents that are currently displayed
     * buildOpponentsCards(Player currentPlayer, PlayerGroup opponentsPlayers) :
     * Return a hashmap of every card currently in the opponents' hand of one
     * specific player.
     * \param Player currentPlayer, PlayerGroup opponentsPlayers
     * \return HashMap<Player, Card>
     */
    public Map<Player, List<Card>> buildOpponentsCards(Player currentPlayer, PlayerGroup playersAlive) {
        HashMap<Player, List<Card>> opponentsCards = new HashMap<>();
        PlayerGroup opponents = playersAlive.copy(playersAlive);
        opponents.removePlayer(currentPlayer);
        for (Player player : opponents.getPlayers()) {
            opponentsCards.put(player, player.getCards());
        }
        return opponentsCards;
    }

    /**
     * \brief Bet part of the game
     * betTricks(int numberRound): Every player can see their cards and bet a number
     * of tricks they think they will win.
     * The total of the bets can't be equal to the number of cards distributed per
     * player.
     * \param int numberRound
     */
    public void betTricks(int numberRound) {
        // The total of the bets can't be equal to the number of cards distributed per
        // player
        int totalBet = 0;
        // Count the number of player to know who is the last player
        int numberPlayer = 0;
        for (Player player : this.getPlayersAlive()) {
            numberPlayer += 1;
            playerTransition(player);
            System.out.println(player.getCards());
            System.out.println("How many tricks do you want to bet?");
            System.out.println("Current total bet: " + totalBet);
            boolean isDone = false;
            while (!isDone) {
                try {
                    int betTricks = scanner.nextInt();
                    if (totalBet + betTricks == numberRound && numberPlayer == this.getNumberPlayersAlive()) {
                        System.out.println("The total of the bets can't be equal" +
                                " to the number of cards distributed per player.");
                    } else {
                        player.setBetTricks(betTricks);
                        totalBet += betTricks;
                        isDone = true;
                    }
                } catch (Exception e) {
                    System.err.println("Error while processing bet tricks " + e.getMessage());

                    // Skip to the next input
                    scanner.next();
                }
            }
            enterWait(" to go to the next player.");
        }
    }

    /**
     * \brief Evaluate the cards
     * evaluateCards(HashMap<Player, Card> cardsPlayed) : Evaluate which player won
     * this turn. If any player played the Fool,
     * they choose its value. Cards played this round are displayed.
     * \param HashMap<Player, Card> cardsPlayed
     */
    public void evaluateCards(Map<Player, Card> cardsPlayed) {
        printCardsPlayed(cardsPlayed);

        // If any player played the fool this turn, they need to choose its value
        if (!getFoolPlayer().isEmpty()) {
            for (Map.Entry<Player, Card> entry : getFoolPlayer().entrySet()) {
                Player player = entry.getKey();
                Card card = entry.getValue();

                System.out.println(String.format("Player %s : you played the card %s", player.getName(), card.getName()));
                System.out.println("Enter its value: 0 or 22.");
                boolean isValid = false;
                while (!isValid) {
                    try {
                        int value = scanner.nextInt();
                        if (FoolValuesMap.containsValue(value)) {
                            card.setValue(value);
                            isValid = true;
                        }
                    } catch (Exception e) {
                        System.err.println("You can't choose any other value other than 0 or 22.");
                        // Skip to the next entry
                        scanner.next();
                    }
                }
            }
        }

        // Get the player who won the trick
        Player player = Collections.max(cardsPlayed.entrySet(), Map.Entry.comparingByValue(
                Comparator.comparing(Card::getValue))).getKey();

        printWinnerTurn(player);

        // Add a trick to the those won this round
        player.addCurrentTricks();

        // Reset the player who played the fool
        getFoolPlayer().clear();

        enterWait(" to launch the next turn.");
    }

    /**
     * \brief Print evaluateCards
     * printEvaluateCards(HashMap<Player, Card> cardsPlayed) : Print all cards
     * played this turn.
     * \param HashMap<Player, Card> cardsPlayed
     */
    private void printCardsPlayed(Map<Player, Card> cardsPlayed) {
        // Display all the cards played for this turn
        for (Map.Entry<Player, Card> entries : cardsPlayed.entrySet()) {
            System.out.println(String.format("Player %s Card: %s", entries.getKey().getName(), entries.getValue().getName()));
        }
    }

    /**
     * \brief Print winner
     * printWinnerRound(HashMap<Player, Card> cardsPlayed) : Print the player winner
     * of this turn
     * \param HashMap<Player, Card> cardsPlayed
     */
    private void printWinnerTurn(Player player) {
        separatorPrint();
        // Display the player who won the trick
        System.out.println(String.format("Player %s won the trick!", player.getName()));
        separatorPrint();
    }

    /**
     * \brief Evaluate the cards for the last round
     * evaluateCardsLastRound(HashMap<Player, Boolean> decisions) : Display all
     * cards the players have in their hands.
     * Display the winner of the trick. Remove life points according to the bet.
     * \param HashMap<Player, Boolean> decisions
     */
    public void evaluateCardsLastRound(Map<Player, Boolean> decisions) {
        HashMap<Player, Card> cardsPlayed = new HashMap<>();
        // Display all the cards the players have this turn
        for (Player player : getPlayersAlive()) {
            Card cardCurrent = player.getCard(0);
            System.out.println(String.format("Player %s  Card: %s", player.getName(), cardCurrent));
            cardsPlayed.put(player, cardCurrent);
        }

        // Get the player who won the trick
        Player winner = Collections.max(cardsPlayed.entrySet(), Map.Entry.comparingByValue(
                Comparator.comparing(Card::getValue))).getKey();

        // Display the player who won the trick
        printWinnerTurn(winner);

        // Remove life to those who didn't predict right
        for (Map.Entry<Player, Boolean> entries : decisions.entrySet()) {
            int lifePointsRemoved = 1;
            Player currentPlayer = entries.getKey();
            Boolean predictedBet = entries.getValue();
            // If the player is the winner but didn't predict that they would won
            // or if any played predicted they would won but didn't
            if ((currentPlayer == winner && !predictedBet)
                    || (currentPlayer != winner && predictedBet)) {
                System.out.println(String.format("Player %s  you lose 1 life point for betting the wrong outcome.", currentPlayer.getName()));
                currentPlayer.removeLife(lifePointsRemoved);
            }
        }
        enterWait(" to launch the next round.");
    }

    /**
     * \brief Evaluate the round
     * evaluateRound() : Remove life points to any player who bet the wrong amount
     * of tricks.
     * Display the name of the player(s) who reach 0 life points.
     */
    public void evaluateRound() {
        // Remove lifepoints
        for (Player player : getPlayersAlive()) {
            System.out.println(String.format("Player %s", player.getName()));
            int betTricks = player.getBetTricks();
            int currentTricks = player.getCurrentTricks();
            System.out.println("Bet tricks: " + betTricks);
            System.out.println("Current tricks: " + currentTricks);
            int lifePointsRemoved = Math.abs(player.getBetTricks() - player.getCurrentTricks());
            if (lifePointsRemoved != 0) {
                System.out.println("You lose " + lifePointsRemoved + " life points.");
                player.removeLife(lifePointsRemoved);
            }

            separatorPrint();

            // Reset the tricks for the turn
            try{
                player.setBetTricks(0);
                player.setCurrentTricks(0);
            }
            catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        enterWait(" to launch next round.");
    }

    /**
     * \brief Evaluate the players life points
     * evaluateDeadPlayers() : Remove and display the name of the players who
     * reached 0 life points.
     */
    public void evaluateDeadPlayers() {
        // Remove players who reach 0 life points
        List<Player> playersDead = playersAlive.removeDeadPlayers();

        // If any player reach 0 life points
        if (playersDead.isEmpty()) {
            for (Player player : playersDead) {
                System.out.println("Player " + player.getName() +
                        " has 0 life points! They can't play anymore.");
            }
            enterWait(" to launch next round");
        }

    }

    /**
     * \brief Round process
     * round(int numberRound) : Start a round by distributing cards according to the
     * current number of round.
     * Allow player to play until there is no card left in hands. Compute which
     * player lose or not a life points.
     * Remove players with no life points.
     * \param int numberRound
     * 
     * @throws BadNumberOfRoundException
     */
    public void round(int numberRound) throws BadNumberOfRoundException {
        if (numberRound > ROUND_MAX || numberRound < 0) {
            throw new BadNumberOfRoundException("The current number of round must be between " + ROUND_MAX + " and 0.");
        }
        try {
            // Distribute cards to every player
            distributeCards(numberRound);

            // Case of the last round where players have one card
            if (numberRound == 1) {
                Map<Player, Boolean> decisions = playAllPlayersLastRound();
                evaluateCardsLastRound(decisions);
            } else {
                // Every player can bet the number of tricks they think they will win
                betTricks(numberRound);

                // All players played until they don't have any cards left in their hands
                for (int turn = 0; turn < numberRound; turn++) {
                    // Players played one card
                    Map<Player, Card> cardsPlayed = playAllPlayers();
                    // Winner of the trick is displayed
                    evaluateCards(cardsPlayed);
                }
                evaluateRound();
            }
            // Remove players with 0 life points
            evaluateDeadPlayers();

            // Generate deck for the next round
            deck.buildDeck();
            // Shuffle deck
            deck.shuffle();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            logger.log(Level.WARNING, "Error while processing the round", new Exception(e.getMessage()));
        }
    }

    /**
     * \brief Rotating players
     * rotatingPlayers() : Rotate players between round so the next player becomes
     * the one who begin the round.
     */
    public void rotatingPlayers() {
        // Only players stil alive needed to be rotated
        this.playersAlive.rotatingPlayers();
    }

    /**
     * \brief Start the game
     * start() : Start the game by creating every player and distributing the cards.
     * Process to
     * play every round until there is only one player alive.
     */
    public void start() {
        createPlayers();
        boolean endOfGame = false;
        while (!endOfGame) {
            for (int numberRound = ROUND_MAX; numberRound > 0; numberRound--) {
                try {
                    round(numberRound);
                    if (isVictory()) {
                        endOfGame = true;
                        break;
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }

            // The next player become the new player who begin the round
            rotatingPlayers();
        }
        System.out.println("The game is over.");
        if (getNumberPlayersAlive() == 1) {
            // The last player alive is the winner
            Player winner = getPlayersAlive().getFirst();
            System.out.println("Player " + winner.getName() + " won the game!");
        } else {
            System.out.println("This is a tie. No player won this game.");
        }
    }

    /**
     * \brief Winner of the game
     * getWinner() : Return the winner of the current game.
     * \return Player
     */
    public Player getWinner() {
        return getPlayersAlive().getFirst();
    }

    /**
     * \brief Condition of victory
     * isVictory() : Return true if there is only one with life points, or no more
     * players with lifepoints in case all players
     * lose their life points at the same time.
     */
    public boolean isVictory() {
        return getNumberPlayersAlive() == 1 || getNumberPlayersAlive() == 0;
    }

    /**
     * \brief Clear terminal
     *
     * clearTerminal() : Pseudo clear the terminal
     */
    public void clearTerminal() {
        System.out.print("\033\143");
    }

    /**
     * \brief Wait for enter input
     *
     * enterWait(String message) : Wait for the user to use the input enter for any
     * action specified in the message.
     * Once it's done, clear the terminal.
     */
    public void enterWait(String message) {
        System.out.println("Press enter" + message);
        try {
            System.in.read();
            // Clear the buffer in case the input has anything
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        } catch (Exception e) {
            System.err.println("Error while reading input: " + e.getMessage());
        }

        clearTerminal();
    }

    /**
     * \brief Wait for player
     *
     * playerTransition(Player player) : Wait for the player to press enter before
     * printing any information regarding they cards
     * Once it's done, clear the terminal.
     */
    public void playerTransition(Player player) {
        System.out.println(player.getName() + " it's your turn");
        enterWait("");
    }

    /**
     * \brief Print separator
     *
     * separatorPrint(): Print separator.
     */
    public void separatorPrint() {
        System.out.println("--------------------");
    }

    /**
     * \brief toString
     *
     * toString() : Return the string representation of a Deck.
     * \return String
     */
    public String toString() {
        String result = "\n Players: ";
        result += this.players.toString();
        result += "\n Players alive: ";
        result += this.playersAlive.toString();

        return result;
    }
}
