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

    @AfterEach
    public void restoreSystemIn() {
        // Restore System.in after each test
        System.setIn(originalSystemIn);
    }

    @After
    public void afterTest(){ 
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
        // Simulate user input "Hello World"
        String simulatedInput = "Player1 Player2 Player3 Player4";
        testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);
        game = new Game();
        game.createPlayers();
        Assert.assertEquals(game.getPlayers().size(), Game.NUMBER_PLAYERS);
    }

    @Test
    public void testDistributeCards() throws NotEnoughCardsInDeckException, TooManyCardsException, RemovingTooManyCards{
        // Simulate user input "Hello World"
        String simulatedInput = "Player1 Player2 Player3 Player4";
        testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);
        game = new Game();
        game.createPlayers();
        int deckSize = game.getDeckSize();
        //int maxCardsDistributable = deckSize / game.getPl
        game.distributeCards(5);
    }
}
