package people;

import java.util.ArrayList;
import java.util.List;

import deck.Card;

public class Player {
    public static final int NAME_MAX = 8;
    public static final int CARDS_MAX = 5;
    public static final int MAX_LIFE = 10;

    private int life;
    private List<Card> cards;
    private String name;
    private int currentTricks;
    private int betTricks;

    /**
     * \brief Constructor of Player
     *
     * Player() : Create a player with default life and empty handc.
     */
    public Player() {
        this.name = "";
        this.life = MAX_LIFE;
        this.cards = new ArrayList<>(CARDS_MAX);
        this.betTricks = 0;
        this.currentTricks = 0;
    }

    /**
     * \brief Constructor of Player
     *
     * Player() : Create a player with a specific name, with default life and empty
     * handc.
     * 
     * @throws PlayerNameTooLongException
     */
    public Player(String name) throws PlayerNameTooLongException {
        if (name.length() > NAME_MAX) {
            throw new PlayerNameTooLongException("The name of the player is over 8 characters.");
        }
        this.name = name;
        this.life = MAX_LIFE;
        this.cards = new ArrayList<>(CARDS_MAX);
        this.betTricks = 0;
        this.currentTricks = 0;
    }

    /**
     * \brief Getter name
     *
     * getName() : Return the name of the player.
     * \return String name
     */
    public String getName() {
        return this.name;
    }

    /**
     * \brief Setter name
     *
     * setName() : Set the name of the player.
     * \param String name
     * 
     * @throws PlayerNameTooLongException
     */
    public void setName(String name) throws PlayerNameTooLongException {
        if (name.length() > NAME_MAX) {
            throw new PlayerNameTooLongException("The name of the player is over 8 characters.");
        }
        this.name = name;
    }

    /**
     * \brief Getter life
     *
     * getLife() : Return the life of the player.
     * \return int life
     */
    public int getLife() {
        return this.life;
    }

    /**
     * \brief Setter life
     *
     * setLife() : Set the life of the player.
     * \param int life
     * 
     * @throws NegativeLifeValueException
     */
    public void setLife(int life) throws NegativeLifeValueException {
        if (life < 0) {
            throw new NegativeLifeValueException("Life can't be less than 0.");
        }
        this.life = life;
    }

    /**
     * \brief Remove life points
     *
     * removeLife(int lifepoints) : Remove life points to the player.
     * \param int lifepoints
     */
    public void removeLife(int lifepoints) {
        this.life -= lifepoints;
        if (this.life < 0) {
            this.life = 0;
        }
    }

    /**
     * \brief If the player still have one life left
     *
     * isAlive() : Return true is the player has more than 0 life point.
     * \return boolean
     */
    public boolean isAlive() {
        return this.life > 0;
    }

    /**
     * \brief Player has a specific card
     *
     * containsCard(Card card): Return true if the cards is in the hand of the
     * player.
     * \return boolean
     */
    public boolean containsCard(Card card) {
        return this.cards.contains(card);
    }

    /**
     * \brief Getter card
     *
     * getCard(int index) : Return the card with a specific index of the player.
     * \return Card
     */
    public Card getCard(int index) {
        return this.cards.get(index);
    }

    /**
     * \brief Getter cards
     *
     * getCards() : Return the cards of the player.
     * \return ArrayList<Card> cards
     */
    public List<Card> getCards() {
        return this.cards;
    }

    /**
     * \brief Setter cards
     *
     * setCards() : Set the entire hand of the player.
     * \param ArrayList<Card> cards
     * 
     * @throws TooManyCardsException
     */
    public void setCards(List<Card> cards) throws TooManyCardsException {
        if (cards.size() > CARDS_MAX) {
            throw new TooManyCardsException("Players can only have " + CARDS_MAX + " cards at max in their hands.");
        }
        this.cards.clear();
        this.cards.addAll(cards);

        this.cards = cards;
    }

    /**
     * \brief Get number cards
     *
     * getNumberCards() : Return the number of cards the player has in his hand.
     * \return int
     */
    public int getNumberCards() {
        return this.cards.size();
    }

    /**
     * \brief Add a card
     *
     * addCard(Card card) : Add one card to the player's hand
     * \param Card card
     */
    public void addCard(Card card) {
        this.cards.add(card);
    }

    /**
     * \brief Remove a card
     *
     * removeCard(int index) : Remove one card to the player's hand and return it
     * \param int index
     * \return Card card
     */
    public Card removeCard(int index) {
        return this.cards.remove(index - 1);
    }

    /**
     * \brief Getter bet tricks
     *
     * getBetTricks() : Return the tricks the player bet.
     * \return int
     */
    public int getBetTricks() {
        return this.betTricks;
    }

    /**
     * \brief Setter bet life
     *
     * setBetTricks(int betTricks) : Set the number of tricks the player bet.
     * \param int betTricks
     * 
     * @throws NegativeTricksValueException
     */
    public void setBetTricks(int betTricks) throws NegativeTricksValueException {
        if (betTricks < 0) {
            throw new NegativeTricksValueException("Bet can only be done with positive or null value.");
        }
        this.betTricks = betTricks;
    }

    /**
     * \brief Getter current tricks
     *
     * getCurrentTricks() : Return the tricks the player currently has won.
     * \return int
     */
    public int getCurrentTricks() {
        return this.currentTricks;
    }

    /**
     * \brief Setter current life
     *
     * setCurrentTricks(int currentTricks) : Set the number of tricks the player
     * currently has.
     * \param int currentTricks
     * 
     * @throws NegativeTricksValueException
     */
    public void setCurrentTricks(int currentTricks) throws NegativeTricksValueException {
        if (currentTricks < 0) {
            throw new NegativeTricksValueException("Bet can't have negative value.");
        }
        this.currentTricks = currentTricks;
    }

    /**
     * \brief Add trick
     *
     * addCurrentTricks() : Add one trick to the current trick.
     */
    public void addCurrentTricks() {
        this.currentTricks += 1;
    }

    @Override
    public boolean equals(Object o) {
        return o == this 
        || o instanceof Player player
        && player.hashCode() == this.hashCode();
    }

    /**
     * \brief hashCode
     *
     * hashCode() : Return the hashcode of a Player.
     * \return int
     */
    public int hashCode() {
        return 13 * this.life + 17 * this.name.hashCode() + 7 * this.cards.hashCode();
    }

    /**
     * \brief Deep clone
     *
     * clone() : Return a deep clone of a Player
     * \return Player
     */
    public Player copy(Player player) {
        Player playerCloned =  new Player();
        try {
            playerCloned.setLife(player.getLife());
            playerCloned.setCards(player.getCards());
            playerCloned.setName(player.getName());
        }
        catch(Exception e){
            System.err.println(e.getStackTrace());
        }
        
        return playerCloned;
    }

    /**
     * \brief toString
     *
     * toString() : Return the string representation of a Deck.
     * \return String
     */
    public String toString() {
        return this.name + this.life + this.cards.toString();
    }
}