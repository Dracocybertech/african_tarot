package srctest.game;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import deck.Card;
import deck.Deck;
import deck.RemovingTooManyCards;
import game.BadNumberOfPlayersException;
import game.Game;
import game.NotEnoughCardsInDeckException;
import people.NegativeLifeValueException;
import people.Player;
import people.PlayerNameTooLongException;
import people.TooManyCardsException;

public class TestGame {

    private final InputStream originalSystemIn = System.in;
    private ByteArrayInputStream testIn;
    Game gameWithPlayer;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    ArrayList<Player> players;

    @Before
    public void beforeTest() throws PlayerNameTooLongException, BadNumberOfPlayersException{
        gameWithPlayer = new Game();
        players = new ArrayList<Player>(Game.NUMBER_PLAYERS);
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        player3 = new Player("Player3");
        player4 = new Player("Player4");
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        gameWithPlayer.setPlayers(players);
    }
    
    @After
    public void afterTest(){ 
        System.setIn(originalSystemIn);
        System.out.println("Test Game over");
    }

    @Test
    public void testConstructor(){
        Game gameInit = new Game();
        Assert.assertEquals(gameInit.getNumberPlayers(), 0);
        Assert.assertEquals(gameInit.getNumberPlayersAlive(), 0);
        Assert.assertNotEquals(gameInit.getDeckSize(), 0);
    }

    @Test
    public void testGetPlayers(){
        Assert.assertEquals(gameWithPlayer.getPlayers(), players);
    }

    @Test
    public void testSetPlayers() throws PlayerNameTooLongException, BadNumberOfPlayersException, NegativeLifeValueException{
        ArrayList<Player> playersTest = new ArrayList<Player>(2);
        playersTest.add(player1);
        player2.setLife(0);
        playersTest.add(player2);
        ArrayList<Player> playersAliveTest = new ArrayList<Player>(1);
        playersAliveTest.add(player1);
        gameWithPlayer.setPlayers(playersTest);

        Assert.assertEquals(gameWithPlayer.getPlayers(), playersTest);
        Assert.assertEquals(gameWithPlayer.getPlayersAlive(), playersAliveTest);
        System.out.println("getPlayers() : " + gameWithPlayer.getPlayers());
        System.out.println("getPlayersAlive() : " + gameWithPlayer.getPlayersAlive());
        Assert.assertNotEquals(gameWithPlayer.getPlayers(), gameWithPlayer.getPlayersAlive());
    }
    
    @Test(expected=BadNumberOfPlayersException.class)
    public void testBadNumberOfPlayersException() throws BadNumberOfPlayersException{
        ArrayList<Player> playersTest = new ArrayList<Player>(Game.NUMBER_PLAYERS);
        //Add an extra player to go over the limit of max players
        for(int i = 0 ; i < Game.NUMBER_PLAYERS + 1 ; i++){
            playersTest.add(player1);
        }
        gameWithPlayer.setPlayers(playersTest);
    }

    @Test
    public void testGetPlayersAlive() throws NegativeLifeValueException, BadNumberOfPlayersException{
        //Case where every player of the game is alive
        Assert.assertEquals(gameWithPlayer.getPlayersAlive(), players);
        
        //Set the life of one player to 0 to see if the number of player alive is the right one 
        //even with dead players
        player1.setLife(0);
        ArrayList<Player> playersTest = new ArrayList<Player>(2);
        playersTest.add(player1);
        playersTest.add(player2);
        gameWithPlayer.setPlayers(playersTest);
        ArrayList<Player> playersAliveExpected = new ArrayList<Player>(1);
        playersAliveExpected.add(player2);
        Assert.assertEquals(playersAliveExpected, gameWithPlayer.getPlayersAlive());
    }

    @Test
    public void testGetNumberPlayers(){
        Assert.assertEquals(gameWithPlayer.getNumberPlayers(), Game.NUMBER_PLAYERS);
    }

    @Test
    public void testGetNumberPlayersAlive(){
        Assert.assertEquals(gameWithPlayer.getNumberPlayersAlive(), Game.NUMBER_PLAYERS);
    }

    @Test
    public void testGetDeck(){
        Deck deckExpected = new Deck();
        deckExpected.buildDeck();
        Assert.assertEquals(gameWithPlayer.getDeck().getSize(), deckExpected.getSize());
    }

    @Test
    public void testGetDeckSize(){
        Deck deckExpected = new Deck();
        deckExpected.buildDeck();
        Assert.assertEquals(gameWithPlayer.getDeckSize(), deckExpected.getSize());
    }

    @Test
    public void testCreatePlayer(){
        String simulatedInput = "Player1";
        testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);
        Game game = new Game();
        Player player = game.createPlayer();
        Assert.assertNotEquals(player.getName(), null);
    }

    @Test
    public void testCreatePlayers(){
        String simulatedInput = "Player1 Player2 PlayerWithANameTooLong Player3 Player4";
        testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);
        Game game = new Game();
        game.createPlayers();
        System.out.println(game.toString());
        //Check that both lists have the same players
        Assert.assertEquals(game.getNumberPlayers(), Game.NUMBER_PLAYERS);
        Assert.assertEquals(game.getNumberPlayersAlive(), Game.NUMBER_PLAYERS);

        //Check that players and playersAlive are two distincts lists
        ArrayList<Player> players = game.getPlayers();
        ArrayList<Player> playersAlive = game.getPlayersAlive();
        //Modify one list and not the other
        playersAlive.add(player1);

        //Check if both lists are different now that there is a changment on one of them and not the other
        Assert.assertNotEquals(players, playersAlive);

    }

    @Test
    public void testDistributeCards() throws NotEnoughCardsInDeckException, TooManyCardsException, RemovingTooManyCards{
        int deckSize = gameWithPlayer.getDeckSize();
        int maxCardsDistributable = deckSize / gameWithPlayer.getNumberPlayersAlive();
        gameWithPlayer.distributeCards(maxCardsDistributable);
        int deckSizeAfterDistribution = gameWithPlayer.getDeckSize();
        int expectedDeckSize = deckSize - maxCardsDistributable * gameWithPlayer.getNumberPlayersAlive();
        Assert.assertEquals(deckSizeAfterDistribution, expectedDeckSize);

        for (Player player: gameWithPlayer.getPlayersAlive()){
            Assert.assertEquals(player.getNumberCards(), maxCardsDistributable);
        }
    }

    @Test
    public void testPlayOnePlayer() throws NotEnoughCardsInDeckException, TooManyCardsException, RemovingTooManyCards, BadNumberOfPlayersException{
        String simulatedInput = "1";
        testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);
        Game game = new Game();

        ArrayList<Player> listPlayer = new ArrayList<Player>();
        listPlayer.add(player1);
        listPlayer.add(player2);
        listPlayer.add(player3);
        listPlayer.add(player4);
        game.setPlayers(listPlayer);

        int deckSize = game.getDeckSize();
        int maxCardsDistributable = deckSize / game.getNumberPlayersAlive();
        game.distributeCards(maxCardsDistributable);

        int indexCard = 0;
        int indexPlayer = 0;
        //It works because every player1 is a shallow copy inside the list in the game
        Card cardExpected = player1.getCard(indexCard);
        Card cardPlayed = game.playOnePlayer(player1);
        Assert.assertEquals(cardExpected, cardPlayed);
        Assert.assertFalse(game.getPlayer(indexPlayer).containsCard(cardPlayed));
    }
}
