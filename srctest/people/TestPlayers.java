package srctest.people;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import people.NegativeOrNullLifeValueException;
import people.Player;
import people.PlayerNameTooLongException;
import people.Players;

public class TestPlayers {
    
    Players players;
    static int NUMBER_PLAYERS = 4;
    
    @Before
    public void beforeTest(){
        players = new Players(NUMBER_PLAYERS);
    }

    @After
    public void afterTest(){ 
        System.out.println("Test Players over");
    }

    @Test 
    public void testConstructor() throws PlayerNameTooLongException{
        String namePlayer = "Player1";
        int numberPlayers = 1;
        String[] namePlayers = {namePlayer};
        //Create the players
        Players playersTest = new Players(namePlayers);
        
        
        //Create the arrayList we expect to have in Players
        ArrayList<Player> playersExpected = new ArrayList<Player>(numberPlayers);
        Player Player1 = new Player(namePlayer);
        playersExpected.add(Player1);

        Assert.assertTrue(playersExpected.equals(playersTest.getPlayers()));
    }

    @Test
    public void testGetPlayers(){
        ArrayList<Player> playersList = players.getPlayers();

        Assert.assertEquals(playersList.size(),NUMBER_PLAYERS);
    }

    @Test
    public void testSetPlayers() throws PlayerNameTooLongException{
        Player player1 = new Player("Player1");
        ArrayList<Player> playersTest = new ArrayList<Player>(1);
        playersTest.add(player1);
        players.setPlayers(playersTest);
        ArrayList<Player> playersExpected = new ArrayList<Player>(1);
        playersExpected.add(player1);
        Assert.assertEquals(players.getPlayers(),playersExpected);
    }

    @Test
    public void testGetPlayer() throws PlayerNameTooLongException{
        Player player1 = new Player("Player1");
        ArrayList<Player> playersTest = new ArrayList<Player>(1);
        playersTest.add(player1);
        players.setPlayers(playersTest);
        Player playerExpected = players.getPlayer(0);

        Assert.assertEquals(playerExpected, player1);
    }
 
    @Test
    public void testGetSize(){
        int size = players.getSize();
        Assert.assertEquals(size, NUMBER_PLAYERS);
    }

    @Test
    public void testAddPlayer() throws PlayerNameTooLongException{
        Player player1 = new Player("Player1");
        players.addPlayer(player1);
        Player playerExpected = players.getPlayer(players.getSize() -1);

        Assert.assertEquals(playerExpected, player1);
    }

    @Test
    public void testIsAlive(){
        Assert.assertTrue(players.isAlive());

        for (int i = 0; i < Player.MAX_LIFE; i++){
            players.getPlayer(0).removeLife();
        }

        Assert.assertFalse(players.isAlive());
    }

    @Test
    public void testRemoveDeadPlayers() throws PlayerNameTooLongException, NegativeOrNullLifeValueException{
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        ArrayList<Player> playersTest = new ArrayList<Player>();
        playersTest.add(player1);
        playersTest.add(player2);
        players.setPlayers(playersTest);

        for (int i = 0; i < Player.MAX_LIFE; i++){
            players.getPlayer(0).removeLife();
            player1.removeLife();
        }

        ArrayList<Player> playersAliveExpected = new ArrayList<Player>();
        ArrayList<Player> playersDeadExpected = new ArrayList<Player>();

        playersAliveExpected.add(player2);
        playersDeadExpected.add(player1);

        ArrayList<Player> playersDead = players.removeDeadPlayers();
        ArrayList<Player> playersAlive = players.getPlayers();

        Assert.assertEquals(playersAliveExpected, playersAlive);
        Assert.assertEquals(playersDeadExpected, playersDead);
    }

    @Test
    public void testEquals() throws PlayerNameTooLongException{
        String[] namePlayers = {"Player1", "Player2"};
        Players players1 = new Players(namePlayers);
        Players players2 = new Players(namePlayers);
        Assert.assertEquals(players1, players2);
    }
}
