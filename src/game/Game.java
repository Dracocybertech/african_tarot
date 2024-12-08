package game;

import java.util.ArrayList;

import people.Player;

public class Game {

    public ArrayList<Player> players;
    
    /** \brief Constructor of Game
     	*
	* Game() : Initialize the game.
         * @throws CardException 
    */
    public Game(){
        players = new ArrayList<Player>(4);
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
