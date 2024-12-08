package srctest.game;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import game.Game;
import people.Player;

public class TestGame {

    private final InputStream originalSystemIn = System.in;
    private ByteArrayInputStream testIn;
    Game game;

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
}
