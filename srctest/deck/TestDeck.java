package srctest.deck;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.hamcrest.core.IsInstanceOf;

import java.util.ArrayList;

import org.hamcrest.MatcherAssert;

import deck.Card;
import deck.CardException;
import deck.CardNameTooLongException;
import deck.Deck;
import deck.RemovingTooManyCards;

public class TestDeck {

    Deck deck;
    Deck shuffledDeck;
    Deck builtDeck;

    @Before
    public void beforeTest() throws CardException, CardNameTooLongException{
        deck = new Deck();
        shuffledDeck = new Deck();
        builtDeck = new Deck();
        builtDeck.buildDeck();
    }
    @After
    public void afterTest(){ 
        System.out.println("Test Deck over");
    }

    @Test
    public void testBuildDeck(){
        deck.buildDeck();
        for(Card card: deck.completeDeck){
            MatcherAssert.assertThat(card, IsInstanceOf.instanceOf(Card.class));
            Assert.assertTrue(card.getValue() >= Card.MIN_VALUE && card.getValue() <= Card.MAX_VALUE);
        }
    }

    @Test
    public void testShuffleDeck(){
        shuffledDeck.buildDeck();
        shuffledDeck.shuffle();
        Assert.assertFalse(deck.equals(shuffledDeck));
    }

    @Test
    public void testGetCompleteDeck() throws CardException, CardNameTooLongException{
        Card card0 = new Card("Fool",0);
        deck.addCard(card0);

        ArrayList<Card> deckExpected = new ArrayList<Card>(1);
        deckExpected.add(card0);

        Assert.assertTrue(deckExpected.equals(deck.getCompleteDeck()));
    }

    @Test
    public void testAddCard() throws CardException, CardNameTooLongException{
        Card card0 = new Card("Fool",0);
        deck.addCard(card0);
        Assert.assertTrue(deck.getCompleteDeck().contains(card0));
    }

    @Test
    public void testGetCard() throws CardException, CardNameTooLongException {
        Card card0 = new Card("Fool",0);
        deck.addCard(card0);
        Assert.assertEquals(deck.getCard(0),card0);
    }

    @Test
    public void testRemoveCard(){
        int index = 0;
        Card card0 = builtDeck.getCard(index);
        Card cardRemoved = builtDeck.removeCard(index);
        Assert.assertEquals(card0, cardRemoved);
    }

    @Test
    public void testRemoveCards() throws RemovingTooManyCards{
        ArrayList<Card> cardsExpected = new ArrayList<Card>(2);
        cardsExpected.add(builtDeck.getCard(builtDeck.getCompleteDeck().size() - 1));
        cardsExpected.add(builtDeck.getCard(builtDeck.getCompleteDeck().size() - 2));
        ArrayList<Card> cardsRemoved = builtDeck.removeCards(2);

        Assert.assertTrue(cardsRemoved.equals(cardsExpected));
    }
}
