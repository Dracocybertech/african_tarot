package game;

import java.util.ArrayList;
import java.util.Scanner;

import deck.Card;
import deck.Deck;
import deck.RemovingTooManyCards;
import people.Player;
import people.PlayerGroup;
import people.PlayerNameTooLongException;
import people.TooManyCardsException;

public class Game {

    private PlayerGroup players;
    private PlayerGroup playersAlive;
    private Scanner scanner;
    private Deck deck;
    
    public static int NUMBER_PLAYERS = 4;
    public static int ROUND_MAX = 5;
    
    /** \brief Constructor Game
     * Game(): create the players and the deck of cards.
     */
    public Game(){
        players = new PlayerGroup(NUMBER_PLAYERS);
        playersAlive = new PlayerGroup(NUMBER_PLAYERS);
        scanner = new Scanner(System.in);
        deck = new Deck();
        deck.buildDeck();
    }

    /** \brief Getter players
        *
    * getPlayers() : Return the list of the players in the game.
    * \return ArrayList<Player>
    */
    public ArrayList<Player> getPlayers(){
        return this.players.getPlayers();
    }

    /** \brief Setter players
        *
    * setPlayers(ArrayList<Player> players) : Set a new list of players. Update the current list of 
    * players alive so the list of players alive is only made with players in the game.
    * \param ArrayList<Player> players
         * @throws BadNumberOfPlayersException 
        */
    public void setPlayers(ArrayList<Player> players) throws BadNumberOfPlayersException{
        if (players.size() > NUMBER_PLAYERS){
            throw new BadNumberOfPlayersException("The number of player must be less or equal than :"+NUMBER_PLAYERS);
        }
        this.players.setPlayers(players);
        this.playersAlive.setPlayers(players);
        this.playersAlive.removeDeadPlayers();
    }

    /** \brief Getter playersAlive
        *
    * getPlayersAlive() : Return the list of the players  alive in the game.
    * \return ArrayList<Player>
    */
    public ArrayList<Player> getPlayersAlive(){
        return this.playersAlive.getPlayers();
    }

    /** \brief Number players
        *
    * getNumberPlayers() : Return the number of total players in the game.
    * \return int
    */
    public int getNumberPlayers(){
        return this.players.getNumberPlayers();
    }
    
    /** \brief Number players alive
        *
    * getNumberPlayersAlive() : Return the number of total players in the game.
    * \return int
    */
    public int getNumberPlayersAlive(){
        return this.players.getNumberPlayers();
    }

    /** \brief Getter Deck
        *
    * getDeck() : Return the current deck in the game.
    * \return Deck
    */
    public Deck getDeck(){
        return this.deck;
    }

    /** \brief Getter Deck size
        *
    * getDeckSize() : Return the size of current deck in the game.
    * \return int
    */
    public int getDeckSize(){
        return this.deck.getSize();
    }

    /** \brief Create a Player
        *
    * createPlayer() : Allow the user to create a new Player with a specific name.
    * \return Player
    */
    public Player createPlayer(){
        Player player = null;
        while(player == null){
            try{
                System.out.println("Entrez un nom.");
                String userName = scanner.next();  // Read user input
                System.out.println("userName : " + userName);
                player = new Player(userName);
            }
            catch(PlayerNameTooLongException e){
                System.out.println(e.getMessage());
            }
        }
        return player;
    }

    /** \brief Create all player
        *
    * createPlayers() : Fill the game with all the players for launching a game.
    */
    public void createPlayers(){
        for (int i  = 0 ; i < NUMBER_PLAYERS; i++){
            Player player = createPlayer();
            players.addPlayer(player);
            playersAlive.addPlayer(player);
        }
    }

    /** \brief Distribute cards to players
    *
    * distributeCards(int numberCards) : Distribute exactly numberCards to each player alive.
    * @throws NotEnoughCardsInDeckException 
    * @throws RemovingTooManyCards 
    * @throws TooManyCardsException 
    */
    public void distributeCards(int numberCards) throws NotEnoughCardsInDeckException, TooManyCardsException, RemovingTooManyCards{
        if (numberCards * getNumberPlayersAlive() > this.deck.getSize()){
            throw new NotEnoughCardsInDeckException("There isn't enough cards left in the deck to distribute "+ 
            numberCards +" to each player.");
        }

        for(Player player : playersAlive.getPlayers()){
            //Card removed from the deck are added to the player's hand
            player.setCards(deck.removeCards(numberCards));
        }
    }
    
    /** \brief Player play
    * play(int playerNumber) : The player can play any card from their hands.
    * \param int playerNumber
    */
    public void play(int playerNumber){
        try{
            //Should be a shallow copy, so any changed made to current player
            //will be reflected in the list of players
            Player currentPlayer = players.getPlayer(playerNumber);
            System.out.println("Which card would you like to play ?");
            System.out.println(currentPlayer.getCards().toString());
            Card cardPlayed = null;
            while (cardPlayed == null){
                int indexCard = scanner.nextInt();
                try{
                    cardPlayed = currentPlayer.removeCard(indexCard);
                }
                catch(Exception e){
                    System.out.println("The card can't be played.");
                    System.out.println(e.getMessage());
                }
            }
        }
        catch(Exception e){

        }
    }

    /** \brief Round process
    * round(int numberRound) : Start a round by distributing cards according to the current number of round.
    * Allow player to play until there is no card left in hands. Compute which player lose or not a life points.
    * Remove players with no life points.
    * \param int numberRound
         * @throws BadNumberOfRoundException 
        */
        public void round(int numberRound) throws BadNumberOfRoundException{
        if (numberRound > ROUND_MAX || numberRound < 0){
            throw new BadNumberOfRoundException("The current number of round must be between "+ROUND_MAX+" and 0.");
        }
        try {
            //distribute cards to every player
            distributeCards(numberRound);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }
        play(numberRound);
    }

    /** \brief Start the game
    * start() : Start the game by creating every player and distributing the cards. Process to 
    * play every round until there is only one player alive.
    */
    public void start(){
        createPlayers();
        boolean endOfGame = false;
        while (!endOfGame){
            endOfGame = true;
        }
    }

    /** \brief toString
     	*
	* toString() : Return the string representation of a Deck.
	* \return String
    */
    public String toString(){
        String result = "\n Players: ";
        result += this.players.toString();
        result += "\n Players alive: ";
        result += this.playersAlive.toString();

        return result;
    }
}
