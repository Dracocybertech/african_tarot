package game;

import java.util.ArrayList;
import java.util.Scanner;

import people.Player;
import people.PlayerNameTooLongException;

public class Game {

    public ArrayList<Player> players;
    private Scanner scanner;
    private static int NUMBER_PLAYERS = 4;
    
    /** \brief Constructor of Game
     	*
	* Game() : Initialize the game.
         * @throws CardException 
    */
    public Game(){
        players = new ArrayList<Player>(NUMBER_PLAYERS);
        scanner = new Scanner(System.in);
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
