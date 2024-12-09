package people;

import java.util.ArrayList;

public class Players {
    
    ArrayList<Player> players;

    /** \brief Constructor of Player
     	*
	* Players() : Create a list of players with default life and empty handc.
    * \param int numberPlayers
    */
    public Players(int numberPlayers){
        players = new ArrayList<Player>(numberPlayers);
        for (int i = 0 ; i < numberPlayers ; i++){
            players.add(new Player());
        }
    }

    /** \brief Constructor of Players
     	*
	* Players() : Create a list of players with specific name, default life and empty hand.
    * If one player can't be created, the process stops and the list of players stay empty. 
    * \param int numberPlayers
    * \param String[] namePlayers
       */
    public Players(int numberPlayers, String[] namePlayers) throws PlayerNameTooLongException{
        players = new ArrayList<Player>(numberPlayers);
        try{
            for (int i = 0 ; i < numberPlayers ; i++){
                players.add(new Player(namePlayers[0]));
            }
        }
        catch(PlayerNameTooLongException e){
            System.out.println(e.getMessage());
            players.clear();
        }
    }

    /** \brief Constructor of Players
     	*
	* Players() : Fill the list with a pre existing list if players.
    * \param ArrayList<Player> players
       */
    public Players(ArrayList<Player> players){
        for (Player player : players){
            this.players.add(player);
        }
    }

    /** \brief Getter Deck
        *
    * getPlayers() : Return the players.
    * \return ArrayList<Player>
    */
    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    /** \brief If a list of players is alive
     	*
	* isAlive() : Return true is all the players have more than 0 life point.
    * \return boolean
    */
    public boolean isAlive(){
        for(Player player : this.players){
            if(!player.isAlive()){
                return false;
            }
        }
        return true;
    }

    /** \brief Remove player with 0 life points
     	*
	* removeDeadPlayers() : Remove any player with 0 life points from the list and return them
    * \return Players
    */
    public ArrayList<Player> removeDeadPlayers(){
        ArrayList<Player> deadPlayers = new ArrayList<Player>();
        for(Player player : this.players){
            if(!player.isAlive()){
                deadPlayers.add(player);
            }
        }
        return deadPlayers;
    }

    /** \brief toString
     	*
	* toString() : Return the string representation of Players.
	* \return String
    */
    public String toString(){
        String result = "";
        for(Player player: this.players){
            result += player.toString();
            result +="\n";
        }
        return result;
    }
}
