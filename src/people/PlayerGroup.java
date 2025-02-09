package people;

import java.util.ArrayList;
import java.util.List;

public class PlayerGroup {

    List<Player> players;

    /**
     * \brief Constructor of Player
     *
     * PlayerGroup() : Create a empty list of players.
     * \param int numberPlayers
     */
    public PlayerGroup(int numberPlayers) {
        this.players = new ArrayList<>(numberPlayers);
    }

    /**
     * \brief Constructor of PlayerGroup
     *
     * PlayerGroup() : Create a list of players with specific name, default life and
     * empty hand.
     * If one player can't be created, the process stops and the list of players
     * stay empty.
     * \param String[] namePlayers
     */
    public PlayerGroup(String[] namePlayers) throws PlayerNameTooLongException {
        try {
            this.players = new ArrayList<>(namePlayers.length);
            for (int i = 0; i < namePlayers.length; i++) {
                this.players.add(new Player(namePlayers[i]));
            }
        } catch (PlayerNameTooLongException e) {
            System.out.println(e.getMessage());
            if (this.players != null && this.players.isEmpty()) {
                this.players.clear();
            }
        }
    }

    /**
     * \brief Constructor of PlayerGroup
     *
     * PlayerGroup() : Fill the list with a pre existing list if players.
     * \param ArrayList<Player> players
     */
    public PlayerGroup(List<Player> players) {
        this.players = new ArrayList<>(players.size());
        for (Player player : players) {
            this.players.add(player);
        }
    }

    /**
     * \brief Getter Players
     *
     * getPlayers() : Return the players.
     * \return ArrayList<Player>
     */
    public List<Player> getPlayers() {
        return this.players;
    }

    /**
     * \brief Setter Players
     *
     * setPlayers(ArrayList<Player> players) : Set the new list of players.
     * \param ArrayList<Player> players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * \brief Getter Player
     *
     * getPlayer() : Return a specific player.
     * \return Player
     */
    public Player getPlayer(int index) {
        return this.players.get(index);
    }

    /**
     * \brief Number of player
     *
     * getSize() : return the number of players.
     * \return int
     */
    public int getNumberPlayers() {
        return this.players.size();
    }

    /**
     * \brief Add player
     *
     * addPlayer(Player player) : Add a player to the list.
     * \param Player player
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * \brief Remove player
     *
     * removePlayer(Player player) : Remove a player from the list.
     * \param Player player
     */
    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    /**
     * \brief Has player
     *
     * hasPlayer(Player player) : Return true if the list contains a specific
     * player.
     * \return Player
     */
    public boolean hasPlayer(Player player) {
        return this.players.contains(player);
    }

    /**
     * \brief If a list of players is alive
     *
     * isAlive() : Return true is all the players have more than 0 life point.
     * \return boolean
     */
    public boolean isAlive() {
        for (Player player : this.players) {
            if (!player.isAlive()) {
                return false;
            }
        }
        return true;
    }

    /**
     * \brief Remove player with 0 life points
     *
     * removeDeadPlayers() : Remove any player with 0 life points from the list and
     * return them
     * \return Players
     */
    public List<Player> removeDeadPlayers() {
        ArrayList<Player> deadPlayers = new ArrayList<>();
        for (Player player : this.players) {
            if (!player.isAlive()) {
                deadPlayers.add(player);
            }
        }
        this.players.removeIf(playerDead -> playerDead.getLife() <= 0);
        return deadPlayers;
    }

    /**
     * \brief Rotating players
     * rotatingPlayers() : Rotate players between round so the next player becomes
     * the one who begin the round.
     */
    public void rotatingPlayers() {
        this.players.addLast(players.removeFirst());
    }

    @Override
    public boolean equals(Object o) {
        return o == this 
        || ( o instanceof PlayerGroup playerGroup && playerGroup.hashCode() == this.hashCode());
    }

    /**
     * \brief Copy
     *
     * copy(PlayerGroup playerGroup) : Return a copy of the player group.
     * \return PlayerGroup
     */
    public PlayerGroup copy(PlayerGroup playerGroup) {
        return new PlayerGroup(playerGroup.getPlayers());
    }

    /**
     * \brief hashCode
     *
     * hashCode() : Return the hashcode of Players.
     * \return int
     */
    public int hashCode() {
        return 13 * this.players.hashCode();
    }

    /**
     * \brief toString
     *
     * toString() : Return the string representation of Players.
     * \return String
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Player player : this.players) {
            result.append(player.toString());
            result.append("\n");
        }
        return result.toString();
    }
}
