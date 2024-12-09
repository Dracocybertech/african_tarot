package srctest.people;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import people.NegativeLifeValueException;
import people.Player;
import people.PlayerNameTooLongException;
import people.PlayerGroup;

public class TestPlayerGroup {
    
    PlayerGroup players;
    static int NUMBER_PLAYERS = 4;
    
    @Before
    public void beforeTest() throws PlayerNameTooLongException{
        players = new PlayerGroup(NUMBER_PLAYERS);
        Player Player1 = new Player("Player1");
        Player Player2 = new Player("Player2");
        Player Player3 = new Player("Player3");
        Player Player4 = new Player("Player4");
        players.addPlayer(Player1);
        players.addPlayer(Player2);
        players.addPlayer(Player3);
        players.addPlayer(Player4);
    }

    @After
    public void afterTest(){
        System.out.println("Test PlayerGroup over");
    }

    @Test 
    public void testConstructor() throws PlayerNameTooLongException{
        int numberPlayers = 4;
        PlayerGroup playersTestEmpty = new PlayerGroup(numberPlayers);
        Assert.assertEquals(playersTestEmpty.getNumberPlayers(), 0);

        String namePlayer = "Player1";
        numberPlayers = 1;
        String[] namePlayers = {namePlayer};
        //Create the players with a array of string
        PlayerGroup playersWithString = new PlayerGroup(namePlayers);
        
        //Create the arrayList we expect to have in PlayerGroup
        ArrayList<Player> playersExpected = new ArrayList<Player>(numberPlayers);
        Player Player1 = new Player(namePlayer);
        playersExpected.add(Player1);

        Assert.assertTrue(playersExpected.equals(playersWithString.getPlayers()));

        //Create the players with a list of players
        ArrayList<Player> playersToCreate = new ArrayList<Player>(numberPlayers);
        playersToCreate.add(Player1);
        PlayerGroup playersWithArray = new PlayerGroup(playersToCreate);

        Assert.assertTrue(playersExpected.equals(playersWithArray.getPlayers()));
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
    public void testGetNumberPlayers(){
        int size = players.getNumberPlayers();
        Assert.assertEquals(size, NUMBER_PLAYERS);
    }

    @Test
    public void testAddPlayer() throws PlayerNameTooLongException{
        Player player1 = new Player("Player1");
        players.addPlayer(player1);
        Player playerExpected = players.getPlayer(players.getNumberPlayers() -1);

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
    public void testRemoveDeadPlayers() throws PlayerNameTooLongException, NegativeLifeValueException{
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
        PlayerGroup players1 = new PlayerGroup(namePlayers);
        PlayerGroup players2 = new PlayerGroup(namePlayers);
        Assert.assertEquals(players1, players2);
    }
}
