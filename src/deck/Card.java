package deck;

public class Card {
    private String name;
    private int value;

    public static final int MAX_NAME = 8;

    /// Min value of a trump card
    public static final int MIN_VALUE = 1;
    // Max value of a trump card aka the 22 with the fool
    // Max value is set at the fool as this is the default value for this card
    // for the last round
    public static final int MAX_VALUE = 22;

    /**
     * \brief Constructor of Card
     *
     * Card() : Create a card with a 0 value.
     */
    public Card() {
        this.name = "";
        this.value = 0;
    }

    /**
     * \brief Constructor of Card
     *
     * Card(String name, int value) : Create a specific card.
     * \param int value
     * 
     * @throws CardException
     * @throws CardNameTooLongException
     */
    public Card(String name, int value) throws CardException, CardNameTooLongException {
        if (name.length() > MAX_NAME) {
            throw new CardNameTooLongException("The name of the card is over 8 characters.");
        }
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new CardException("The value of the card is > 22 or < 1. Use one between 1 and 22.");
        }
        this.name = name;
        this.value = value;
    }

    /**
     * \brief Getter name
     *
     * getName() : Return the card name.
     * \return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * \brief Setter name
     *
     * setName() : Return the card name.
     * \param String name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * \brief Getter value
     *
     * getValue() : Return the card value.
     * \return int value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * \brief Setter value
     *
     * setValue() : Return the card value.
     * \param int value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * \brief equals
     *
     * equals(Object o) : Return true if the cards have the same name and value.
     * \return boolean
     */
    @Override
    public boolean equals(Object o) {
        return (o == this 
        || o instanceof Card card 
        && card.hashCode() == this.hashCode());
    }

    /**
     * \brief hashCode
     *
     * hashCode() : Return the hashcode of a Card.
     * \return int
     */
    public int hashCode() {
        return 13 * this.name.hashCode() + 17 * this.value;
    }

    /**
     * \brief toString
     *
     * toString() : Return the string representation of a Card.
     * \return String
     */
    public String toString() {
        return "Card " + this.name;
    }
}
