package deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> completeDeck;

    /**
     * \brief Constructor of Deck
     *
     * Deck() : Create an empty deck.
     */
    public Deck() {
        completeDeck = new ArrayList<>(Card.MAX_VALUE);
    }

    /**
     * \brief Builder deck
     *
     * buildDeck() : Build the deck with all the trump cards.
     */
    public void buildDeck() {
        this.completeDeck.clear();
        try {
            for (int value = Card.MIN_VALUE; value < Card.MAX_VALUE; value++) {
                if (value == Card.MAX_VALUE) {
                    completeDeck.add(new Card("Fool", value));
                } else {
                    completeDeck.add(new Card(Integer.toString(value), value));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * \brief Getter completeDeck
     *
     * getCompleteDeck() : Return the complete deck.
     * \return ArrayList<Card>
     */
    public List<Card> getCompleteDeck() {
        return this.completeDeck;
    }

    /**
     * \brief Getter completeDeck.size
     *
     * getSize() : Return the size of the deck.
     * \return int
     */
    public int getSize() {
        return this.completeDeck.size();
    }

    /**
     * \brief Shuffling the deck
     *
     * shuffle() : Shuffle the deck.
     */
    public void shuffle() {
        Collections.shuffle(completeDeck);
    }

    /**
     * \brief Add a card
     *
     * addCard(Card card) : Add a card to the deck.
     * \param Card card
     */
    public void addCard(Card card) {
        this.completeDeck.add(card);
    }

    /**
     * \brief Get card
     *
     * getCard(int index) : Get a specific card from the deck.
     * \param int index
     * \return Card
     */
    public Card getCard(int index) {
        return this.completeDeck.get(index);
    }

    /**
     * \brief Remove a card
     *
     * removeCard(int index) : Remove a card of the deck.
     * \param int index
     * \return Card
     */
    public Card removeCard(int index) {
        return this.completeDeck.remove(index);
    }

    /**
     * \brief Remove multiples cards
     *
     * removeCards() : Remove multiple cards from the deck beginning with the last
     * card.
     * \param int numberCards
     * \return ArrayList<Card>
     * 
     * @throws RemovingTooManyCards
     */

    public List<Card> removeCards(int numberCards) throws RemovingTooManyCards {
        if (numberCards > this.completeDeck.size()) {
            throw new RemovingTooManyCards("You can't remove more cards than the deck currently has.");
        }
        // deckSize needs to be static during the removal, otherwise we will jump over a
        // card everytime we remove one
        int deckSize = this.completeDeck.size();
        ArrayList<Card> removedCards = new ArrayList<>(numberCards);
        for (int i = 0; i < numberCards; i++) {
            // Remove the last card
            removedCards.add(this.removeCard(deckSize - i - 1));
        }
        return removedCards;
    }

    /**
     * \brief equals
     *
     * equals(Object o) : Return true if the decks have the same cards.
     * \return boolean
     */
    @Override
    public boolean equals(Object o) {
        return o == this || 
        (this.getClass() == o.getClass() && ((Deck) o).hashCode() == this.hashCode());
    }

    /**
     * \brief hashCode
     *
     * hashCode() : Return the hashcode of a Deck.
     * \return int
     */
    public int hashCode() {
        return 13 * this.completeDeck.hashCode();
    }

    /**
     * \brief toString
     *
     * toString() : Return the string representation of a Deck.
     * \return String
     */
    public String toString() {
        StringBuilder result = new StringBuilder("Deck \n");

        for (Card card : completeDeck) {
            result.append("Name : " + card.getName() + "Value : " + card.getValue());
        }
        return result.toString();
    }
}
