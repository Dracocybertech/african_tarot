package srctest.people;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import deck.Card;
import deck.CardException;
import deck.CardNameTooLongException;
import people.NegativeOrNullLifeValueException;
import people.Player;
import people.PlayerNameTooLongException;
import people.TooManyCardsException;

public class TestPlayer {
    
    Player player1;
    Player player2;

    @Before 
    public void beforeTest() throws CardException, PlayerNameTooLongException{
        player1 = new Player();
        player2 = new Player("Player2");
    }

    @After
    public void afterTest(){ 
        System.out.println("Test Player over");
    }

    @Test
    public void testGetName(){
        Assert.assertEquals(player1.getName(),"");
        Assert.assertEquals(player2.getName(),"Player2");
    }

    @Test
    public void testSetName() throws PlayerNameTooLongException{
        player1.setName("test");
        Assert.assertEquals(player1.getName(),"test");
    }

    @Test(expected=PlayerNameTooLongException.class)
    public void testPlayerNameTooLongException() throws PlayerNameTooLongException{
        player1.setName("A name too long");
    }

    @Test
    public void testGetLife(){
        Assert.assertEquals(player1.getLife(),10);
        Assert.assertEquals(player2.getLife(),10);
    }

    @Test
    public void testSetLife() throws NegativeOrNullLifeValueException{
        player1.setLife(5);
        Assert.assertEquals(player1.getLife(), 5);
    }

    @Test(expected=NegativeOrNullLifeValueException.class)
    public void testNegativeOrNullLifeValueException() throws NegativeOrNullLifeValueException{
        player1.setLife(-5);
    }

    @Test
    public void testGetCards() throws NegativeOrNullLifeValueException{
        Assert.assertTrue(player1.getCards().equals(new ArrayList<Card>(0)));
    }

    @Test
    public void testSetCards() throws TooManyCardsException, CardException, CardNameTooLongException{
        Card card0 = new Card("Fool",0);
        Card card1 = new Card("1",1);
        ArrayList<Card> cardsPlayer1 = new ArrayList<Card>(2);
        cardsPlayer1.add(card0);
        cardsPlayer1.add(card1);

        player1.setCards(cardsPlayer1);
        Assert.assertTrue(player1.getCards().equals(cardsPlayer1));
    }

    @Test(expected=TooManyCardsException.class)
    public void testTooManyCardsException() throws TooManyCardsException, CardException, CardNameTooLongException{
        Card card0 = new Card("Fool",0);
        ArrayList<Card> cardsPlayer1 = new ArrayList<Card>(Player.CARDS_MAX);
        for(int i = 0; i < Player.CARDS_MAX + 1 ; i++){
            cardsPlayer1.add(card0);
        }
        player1.setCards(cardsPlayer1);
    }

    @Test
    public void testGetNumberCards(){
        Assert.assertEquals(player1.getNumberCards(), 0);
    }
    
    @Test
    public void addCard() throws CardException, CardNameTooLongException{
        Card card0 = new Card("Fool",0);
        player1.addCard(card0);
        Assert.assertTrue(player1.getCards().contains(card0));
    }

    @Test
    public void removeCard() throws CardException, CardNameTooLongException{
        Card card0 = new Card("Fool",0);
        player1.addCard(card0);
        Card cardRemoved = player1.removeCard(1);
        Assert.assertEquals(card0, cardRemoved);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsException(){
        player1.removeCard(1);
    }

    @Test
    public void testRemoveLife(){
        int previousLife = player1.getLife();
        player1.removeLife();
        Assert.assertEquals(player1.getLife(),previousLife - 1);
    }

    @Test
    public void testIsAlive(){
        Assert.assertTrue(player1.isAlive());
        int lifePlayer1 = player1.getLife();
        for(int i=0; i < lifePlayer1 ; i++ ){
            player1.removeLife();
        }
        Assert.assertFalse(player1.isAlive());
    }

    @Test
    public void testIsEquals() throws PlayerNameTooLongException{
        String name = "Player";
        Player player3 = new Player(name);
        Player player4 = new Player(name);
        Player player5 = new Player(name+"1");
        Assert.assertEquals(player3, player4);
        Assert.assertNotEquals(player3, player5);
    }
}
