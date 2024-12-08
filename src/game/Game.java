package game;

import java.util.ArrayList;
import java.util.Scanner;

import people.Player;
import people.PlayerNameTooLongException;

public class Game {

    private ArrayList<Player> players;
    private Scanner scanner;
    public static int NUMBER_PLAYERS = 4;
    
    /** \brief Constructor of Game
     	*
	* Game() : Initialize the game.
         * @throws CardException 
    */
    public Game(){
        players = new ArrayList<Player>(NUMBER_PLAYERS);
        scanner = new Scanner(System.in);
    }

    /** \brief Getter players
     	*
	* getPlayers() : Return the list of the players in the game.
	* \return ArrayList<Player>
    */
    public ArrayList<Player> getPlayers(){
        return this.players;
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
            players.add(createPlayer());
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
