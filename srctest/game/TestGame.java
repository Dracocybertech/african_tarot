package srctest.game;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import deck.RemovingTooManyCards;
import game.BadNumberOfPlayersException;
import game.Game;
import game.NotEnoughCardsInDeckException;
import people.Player;
import people.PlayerNameTooLongException;
import people.TooManyCardsException;

public class TestGame {

    private final InputStream originalSystemIn = System.in;
    private ByteArrayInputStream testIn;
    Game gameWithPlayer;
    ArrayList<Player> players;

    @Before
    public void beforeTest() throws PlayerNameTooLongException, BadNumberOfPlayersException{
        gameWithPlayer = new Game();
        players = new ArrayList<Player>(Game.NUMBER_PLAYERS);
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        players.add(new Player("Player3"));
        players.add(new Player("Player4"));
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
        Assert.assertEquals(game.getNumberPlayers(), Game.NUMBER_PLAYERS);
        Assert.assertEquals(game.getNumberPlayersAlive(), Game.NUMBER_PLAYERS);
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
}
