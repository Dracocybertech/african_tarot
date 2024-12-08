package deck;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    public ArrayList<Card> completeDeck;

    /** \brief Constructor of Deck
    *
	* Deck() : Create an empty deck.
         * @throws CardException 
              * @throws CardNameTooLongException 
    */
    public Deck() throws CardException, CardNameTooLongException{
        completeDeck = new ArrayList<Card>(Card.MAX_VALUE);
    }

    /** \brief Builder deck
    *
	* buildDeck() : Build the deck with all the trump cards.
    */
    public void buildDeck(){
        try{
            for(int value = Card.MIN_VALUE; value < Card.MAX_VALUE ; value++ ){ 
                if (value == Card.MIN_VALUE){
                    completeDeck.add(new Card("Fool",value));
                }
                else {
                    completeDeck.add(new Card(Integer.toString(value),value));
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }    

    /** \brief Getter completeDeck
    *
	* getCompleteDeck() : Return the complete deck.
    * \return ArrayList<Card>
    */
    public ArrayList<Card> getCompleteDeck(){
        return this.completeDeck;
    }
    /** \brief Shuffling the deck
    *
	* shuffle() : Shuffle the deck.
    */
    public void shuffle(){
        Collections.shuffle(completeDeck);
    }

    /** \brief Add a card
    *
	* addCard(Card card) : Add a card to the deck.
    * \param Card card
    */
    public void addCard(Card card){
        this.completeDeck.add(card);
    }

    /** \brief Get card
    *
	* getCard(int index) : Get a specific card from the deck.
    * \param int index
    * \return Card
    */
    public Card getCard(int index){
        return this.completeDeck.get(index);
    }

    /** \brief Remove a card
    *
	* removeCard(int index) : Remove a card of the deck.
    * \param int index
    * \return Card
    */
    public Card removeCard(int index){
        return this.completeDeck.remove(index);
    }

    /** \brief Remove multiples cards
    *
	* removeCards() : Remove multiple cards from the deck beginning with the last card.
    * \param int numberCards
    * \return ArrayList<Card>
         * @throws RemovingTooManyCards 
        */
    
    public ArrayList<Card> removeCards(int numberCards) throws RemovingTooManyCards{
        if (numberCards > this.completeDeck.size()){
            throw new RemovingTooManyCards("You can't remove more cards than the deck currently has.");
        }
        //deckSize needs to be static during the removal, otherwise we will jump over a card everytime we remove one 
        int deckSize = this.completeDeck.size();
        ArrayList<Card> removedCards = new ArrayList<Card>(numberCards);
        for(int i = 0; i < numberCards ; i++){
            //Remove the last card
            removedCards.add(this.removeCard(deckSize - i - 1));
        }
        return removedCards;
    }

    /** \brief equals
     	*
	* equals(Object o) : Return true if the decks have the same cards.
	* \return boolean
     	*/
    @Override
    public boolean equals(Object o){
        if (o==this)
            return true;
        if (o==null || this.getClass() != o.getClass())
            return false;
        if (((Deck) o).hashCode() == this.hashCode()){
            return true;
        }
        return false;
    }

    /** \brief hashCode
     	*
	* hashCode() : Return the hashcode of a Deck.
	* \return int
    */
    public int hashCode() {
        return 13 * this.completeDeck.hashCode();
    }

    /** \brief toString
     	*
	* toString() : Return the string representation of a Deck.
	* \return String
    */
    public String toString(){
        String result =  "Deck \n";

        for(Card card : completeDeck) {
            result += "Name : " + card.getName() + "Value : " + card.getValue();
        }
        return result;
    }
}
