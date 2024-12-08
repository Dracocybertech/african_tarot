package game;

import java.util.ArrayList;
import java.util.Scanner;

import deck.Deck;
import deck.RemovingTooManyCards;
import people.Player;
import people.PlayerNameTooLongException;
import people.TooManyCardsException;

public class Game {

    private ArrayList<Player> players;
    private ArrayList<Player> playersStillAlive;
    private Scanner scanner;
    private Deck deck;
    
    public static int NUMBER_PLAYERS = 4;
    
    /** \brief Constructor of Game
        *
    * Game() : Initialize the game.
         * @throws CardException 
    */
    public Game(){
        players = new ArrayList<Player>(NUMBER_PLAYERS);
        scanner = new Scanner(System.in);
        deck.buildDeck();
    }

    /** \brief Getter players
        *
    * getPlayers() : Return the list of the players in the game.
    * \return ArrayList<Player>
    */
    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    /** \brief Setter players
        *
    * setPlayers(ArrayList<Player> players) : Set a new list of players.
    * \param ArrayList<Player> players
         * @throws BadNumberOfPlayersException 
        */
    public void setPlayers(ArrayList<Player> players) throws BadNumberOfPlayersException{
        if (players.size() != this.players.size()){
            throw new BadNumberOfPlayersException("The number of player must be :"+NUMBER_PLAYERS);
        }
        this.players = players;
    }

    /** \brief Getter playersStillAlive
        *
    * getPlayersStillAlive() : Return the list of the players still alive in the game.
    * \return ArrayList<Player>
    */
    public ArrayList<Player> getPlayersStillAlive(){
        return this.playersStillAlive;
    }

    /** \brief Setter playersStillAlive
        *
    * setPlayersStillAlive(ArrayList<Player> players) : Set a new list of players still alive. 
    * \param ArrayList<Player> playersStillAlive
         * @throws BadNumberOfPlayersException 
        */
    public void setPlayersStillAlive(ArrayList<Player> playersStillAlive) throws BadNumberOfPlayersException{
        if (playersStillAlive.size() > this.playersStillAlive.size()){
            throw new BadNumberOfPlayersException("The number of player must be less or equal than :"+NUMBER_PLAYERS);
        }
        
        this.playersStillAlive = playersStillAlive;
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
    * createPlayer() : Create an instance of a new Player with a specific name.
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
            players.add(player);
            playersStillAlive.add(player);
        }
    }

    /** \brief Distribute cards to players
    *
    * distributeCards(int numberCards) : Distribute exactly numberCards to each player still alive.
    * @throws NotEnoughCardsInDeckException 
    * @throws RemovingTooManyCards 
    * @throws TooManyCardsException 
    */
    public void distributeCards(int numberCards) throws NotEnoughCardsInDeckException, TooManyCardsException, RemovingTooManyCards{
        if (numberCards * NUMBER_PLAYERS > this.deck.getSize()){
            throw new NotEnoughCardsInDeckException("There isn't enough cards left in the deck to distribute "+ 
            numberCards +" to each player.");
        }

        for(Player player : playersStillAlive){
            //Card removed from the deck are added to the player's hand
            player.setCards(deck.removeCards(numberCards));
        }
    }

    /** \brief toString
     	*
	* toString() : Return the string representation of a Deck.
	* \return String
    */
    public String toString(){
        String result = "Players : ";
        for(Player player: this.players){
            result += player.toString();
        }

        return result;
    }
}
