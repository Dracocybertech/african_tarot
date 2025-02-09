package srctest.deck;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.hamcrest.core.IsInstanceOf;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.MatcherAssert;

import deck.Card;
import deck.CardException;
import deck.CardNameTooLongException;
import deck.Deck;
import deck.RemovingTooManyCards;

public class TestDeck {

    Card card0;
    Deck deck;
    Deck shuffledDeck;
    Deck builtDeck;

    @Before
    public void beforeTest() throws CardException, CardNameTooLongException {
        deck = new Deck();
        shuffledDeck = new Deck();
        builtDeck = new Deck();
        builtDeck.buildDeck();
        card0 = new Card("Fool", 22);
    }

    @After
    public void afterTest() {
        System.out.println("Test Deck over");
    }

    @Test
    public void testBuildDeck() {
        deck.buildDeck();
        for (Card card : deck.getCompleteDeck()) {
            MatcherAssert.assertThat(card, IsInstanceOf.instanceOf(Card.class));
            Assert.assertTrue(card.getValue() >= Card.MIN_VALUE && card.getValue() <= Card.MAX_VALUE);
        }
    }

    @Test
    public void testShuffleDeck() {
        shuffledDeck.buildDeck();
        shuffledDeck.shuffle();
        Assert.assertFalse(deck.equals(shuffledDeck));
    }

    @Test
    public void testGetCompleteDeck() {
        deck.addCard(card0);

        ArrayList<Card> deckExpected = new ArrayList<>(1);
        deckExpected.add(card0);

        Assert.assertTrue(deckExpected.equals(deck.getCompleteDeck()));
    }

    @Test
    public void testAddCard() {
        deck.addCard(card0);
        Assert.assertTrue(deck.getCompleteDeck().contains(card0));
    }

    @Test
    public void testGetCard() {
        deck.addCard(card0);
        Assert.assertEquals(deck.getCard(0), card0);
    }

    @Test
    public void testRemoveCard() {
        int index = 0;
        Card cardExpected = builtDeck.getCard(index);
        Card cardRemoved = builtDeck.removeCard(index);
        Assert.assertEquals(cardExpected, cardRemoved);
    }

    @Test
    public void testRemoveCards() throws RemovingTooManyCards {
        ArrayList<Card> cardsExpected = new ArrayList<>(2);
        cardsExpected.add(builtDeck.getCard(builtDeck.getCompleteDeck().size() - 1));
        cardsExpected.add(builtDeck.getCard(builtDeck.getCompleteDeck().size() - 2));
        List<Card> cardsRemoved = builtDeck.removeCards(2);

        Assert.assertTrue(cardsRemoved.equals(cardsExpected));
    }
}
