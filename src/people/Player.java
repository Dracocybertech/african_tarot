package people;
import java.util.ArrayList;

import deck.Card;

public class Player {
    public final static int NAME_MAX = 8;
    public final static int CARDS_MAX = 5;
    public final static int MAX_LIFE = 10;

    public int life;
    public ArrayList<Card> cards;
    public String name;

    /** \brief Constructor of Player
     	*
	* Player() : Create a player with default life and empty handc.
    */
    public Player(){
        this.name = "";
        this.life = MAX_LIFE;
        this.cards = new ArrayList<Card>(CARDS_MAX);
    }

    /** \brief Constructor of Player
     	*
	* Player() : Create a player with a specific name, with default life and empty handc.
    * @throws PlayerNameTooLongException
    */
    public Player(String name) throws PlayerNameTooLongException{
        if (name.length() > NAME_MAX){
            throw new PlayerNameTooLongException("The name of the player is over 8 characters.");
        }
        this.name = name;
        this.life = MAX_LIFE;
        this.cards = new ArrayList<Card>(CARDS_MAX);
    }

    /** \brief Getter name
     	*
	* getName() : Return the name of the player.
	* \return String name
    */
    public String getName(){
        return this.name;
    }

    /** \brief Setter name
     	*
	* setName() : Set the name of the player.
	* \param String name
         * @throws PlayerNameTooLongException 
            */
    public void setName(String name) throws PlayerNameTooLongException {
        if (name.length() > NAME_MAX){
            throw new PlayerNameTooLongException("The name of the player is over 8 characters.");
        }
        this.name = name;
    }

    /** \brief Getter life
     	*
	* getLife() : Return the life of the player.
	* \return int life
    */
    public int getLife(){
        return this.life;
    }

    /** \brief Setter life
     	*
	* setLife() : Set the life of the player.
	* \param int life
         * @throws NegativeOrNullLifeValueException 
            */
    public void setLife(int life) throws NegativeOrNullLifeValueException {
        if (life <= 0){
            throw new NegativeOrNullLifeValueException("Life can't be equal or less than 0.");
        }
        this.life = life;
    }

    /** \brief Remove a life
     	*
	* removeLife() : Remove one life to the player.
    */
    public void removeLife(){
        this.life -= 1;
    }

    /** \brief If the player still have one life left
     	*
	* isAlive() : Return true is the player has more than 0 life point.
    * \return boolean
    */
    public boolean isAlive(){
        return this.life > 0;
    }

    /** \brief Getter cards
     	*
	* getCards() : Return the cards of the player.
	* \return ArrayList<Card> cards
    */
    public ArrayList<Card> getCards(){
        return this.cards;
    }

    /** \brief Setter cards
     	*
	* setCards() : Set the entire hand of the player.
	* \param ArrayList<Card> cards
         * @throws TooManyCardsException 
            */
    public void setCards(ArrayList<Card> cards) throws TooManyCardsException{
            if (cards.size() > CARDS_MAX){
            throw new TooManyCardsException("Players can only have "+CARDS_MAX+" cards at max in their hands.");
        }
        this.cards.clear();
        this.cards.addAll(cards);

        this.cards = cards;
    }

    /** \brief Get number cards
     	*
	* getNumberCards() : Return the number of cards the player has in his hand.
	* \return int
    */
    public int getNumberCards(){
        return this.cards.size();
    }

    /** \brief Add a card
     	*
	* addCard(Card card) : Add one card to the player's hand
	* \param Card card
            */
    public void addCard(Card card){
        this.cards.add(card);
    }

    /** \brief Remove a card
     	*
	* removeCard(int index) : Remove one card to the player's hand and return it
	* \param int index
    * \return Card card
    */
    
    public Card removeCard(int index){
        return this.cards.remove(index -1);
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
	* hashCode() : Return the hashcode of a Player.
	* \return int
    */
    public int hashCode(){
        return 13 * this.life + 17 * this.name.hashCode()+ 7 * this.cards.hashCode();
    }

    /** \brief toString
     	*
	* toString() : Return the string representation of a Deck.
	* \return String
    */
    public String toString(){
        return this.name + this.life + this.cards.toString();
    }
}