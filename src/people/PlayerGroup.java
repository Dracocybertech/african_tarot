package people;

import java.util.ArrayList;

public class PlayerGroup {
    
    ArrayList<Player> players;

    /** \brief Constructor of Player
     	*
	* PlayerGroup() : Create a list of players with default life and empty handc.
    * \param int numberPlayers
    */
    public PlayerGroup(int numberPlayers){
        this.players = new ArrayList<Player>(numberPlayers);
        for (int i = 0 ; i < numberPlayers ; i++){
            players.add(new Player());
        }
    }

    /** \brief Constructor of PlayerGroup
     	*
	* PlayerGroup() : Create a list of players with specific name, default life and empty hand.
    * If one player can't be created, the process stops and the list of players stay empty. 
    * \param String[] namePlayers
       */
    public PlayerGroup(String[] namePlayers) throws PlayerNameTooLongException{
        try{
            this.players = new ArrayList<Player>(namePlayers.length);
            for (int i = 0 ; i < namePlayers.length ; i++){
                this.players.add(new Player(namePlayers[i]));
            }
        }
        catch(PlayerNameTooLongException e){
            System.out.println(e.getMessage());
            if (this.players  != null && this.players.size() != 0 ){
                this.players.clear();
            }
        }
    }

    /** \brief Constructor of PlayerGroup
     	*
	* PlayerGroup() : Fill the list with a pre existing list if players.
    * \param ArrayList<Player> players
       */
    public PlayerGroup(ArrayList<Player> players){
        for (Player player : players){
            this.players.add(player);
        }
    }

    /** \brief Getter Players
        *
    * getPlayers() : Return the players.
    * \return ArrayList<Player>
    */
    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    /** \brief Setter Players
        *
    * setPlayers(ArrayList<Player> players) : Set the new list of players.
    * \param ArrayList<Player> players
    */
    public void setPlayers(ArrayList<Player> players){
        this.players = players;
    }

    /** \brief Getter Player
        *
    * getPlayer() : Return a specific player.
    * \return Player
    */
    public Player getPlayer(int index){
        return this.players.get(index);
    }

    /** \brief Getter size
        *
    * getSize() : return the size of the list of players.
    * \return int
    */
    public int getSize(){
        return this.players.size();
    }

    /** \brief Add player
     	*
	* addPlayer(Player player) : Add a player to the list.
	* \param Player player
    */
    public void addPlayer(Player player){
        this.players.add(player);
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
            this.players.removeIf(playerDead -> playerDead.getLife() <= 0);
        }
        return deadPlayers;
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }
        if (o == null || o.getClass() != this.getClass()){
            return false;
        }
        if (o.hashCode() == this.hashCode()){
            return true;
        }
        return false;
    }

    /** \brief hashCode
     	*
	* hashCode() : Return the hashcode of Players.
	* \return int
    */
    public int hashCode(){
        return 13 * this.players.hashCode();
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
