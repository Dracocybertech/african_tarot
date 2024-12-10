package game;

import java.util.ArrayList;
import java.util.Scanner;

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
    
    /** \brief Constructor of Game
        *
    * Game() : Initialize the game.
         * @throws CardException 
    */
    public Game(){
        players = new PlayerGroup(NUMBER_PLAYERS);
        scanner = new Scanner(System.in);
        deck.buildDeck();
    }

    /** \brief Getter players
        *
    * getPlayers() : Return the list of the players in the game.
    * \return ArrayList<Player>
    */
    public PlayerGroup getPlayers(){
        return players;
    }

    /** \brief Setter players
        *
    * setPlayers(ArrayList<Player> players) : Set a new list of players.
    * \param ArrayList<Player> players
         * @throws BadNumberOfPlayersException 
        */
    public void setPlayers(ArrayList<Player> players) throws BadNumberOfPlayersException{
        if (players.size() != this.players.getNumberPlayers()){
            throw new BadNumberOfPlayersException("The number of player must be :"+NUMBER_PLAYERS);
        }
        this.players.setPlayers(players);
    }

    /** \brief Getter playersAlive
        *
    * getPlayersAlive() : Return the list of the players  alive in the game.
    * \return PlayerGroup
    */
    public PlayerGroup getPlayersAlive(){
        return this.playersAlive;
    }

    /** \brief Setter playersAlive
        *
    * setPlayersAlive(ArrayList<Player> players) : Set a new list of players  alive. 
    * \param ArrayList<Player> playersAlive
         * @throws BadNumberOfPlayersException 
        */
    public void setPlayersAlive(ArrayList<Player> playersAlive) throws BadNumberOfPlayersException{
        if (playersAlive.size() > this.playersAlive.getNumberPlayers()){
            throw new BadNumberOfPlayersException("The number of player must be less or equal than :"+NUMBER_PLAYERS);
        }
        
        this.playersAlive.setPlayers(playersAlive);
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
        if (numberCards * NUMBER_PLAYERS > this.deck.getSize()){
            throw new NotEnoughCardsInDeckException("There isn't enough cards left in the deck to distribute "+ 
            numberCards +" to each player.");
        }

        for(Player player : playersAlive.getPlayers()){
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
        for(Player player: this.players.getPlayers()){
            result += player.toString();
        }

        return result;
    }
}
