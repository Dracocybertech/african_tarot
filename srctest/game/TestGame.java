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
    Game game;
    ArrayList<Player> players;
    @Before
    public void beforeTest() throws PlayerNameTooLongException, BadNumberOfPlayersException{
        game = new Game();
        players = new ArrayList<>(Game.NUMBER_PLAYERS);
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        players.add(new Player("Player3"));
        players.add(new Player("Player4"));
        game.setPlayers(players);
    }
    
    @After
    public void afterTest(){ 
        System.setIn(originalSystemIn);
        System.out.println("Test Game over");
    }

    @Test
    public void testCreatePlayer(){
        // Simulate user input "Hello World"
        String simulatedInput = "Player1";
        testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);
        game = new Game();
        Player player = game.createPlayer();
        Assert.assertNotEquals(player.getName(), null);
    }

    @Test
    public void testCreatePlayers(){
        String simulatedInput = "Player1 Player2 Player3 Player4";
        testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);
        game = new Game();
        game.createPlayers();
        Assert.assertEquals(game.getNumberPlayers(), Game.NUMBER_PLAYERS);
    }

    @Test
    public void testDistributeCards() throws NotEnoughCardsInDeckException, TooManyCardsException, RemovingTooManyCards{
        String simulatedInput = "Player1 Player2 Player3 Player4";
        testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);
        game = new Game();
        game.createPlayers();
        int deckSize = game.getDeckSize();
        int maxCardsDistributable = deckSize / game.getNumberPlayersAlive();
        game.distributeCards(maxCardsDistributable);
        int deckSizeAfterDistribution = game.getDeckSize();
        int expectedDeckSize = deckSize - maxCardsDistributable * game.getNumberPlayersAlive();
        Assert.assertEquals(deckSizeAfterDistribution, expectedDeckSize);

        for (Player player: game.getPlayersAlive()){
            Assert.assertEquals(player.getCards(), simulatedInput);
        }
    }
}
