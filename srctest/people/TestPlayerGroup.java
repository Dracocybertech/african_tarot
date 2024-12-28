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
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    ArrayList<Player> playersList;

    @Before
    public void beforeTest() throws PlayerNameTooLongException{
        players = new PlayerGroup(NUMBER_PLAYERS);
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        player3 = new Player("Player3");
        player4 = new Player("Player4");
        playersList = new ArrayList<Player>(NUMBER_PLAYERS);
        playersList.add(player1);
        playersList.add(player2);
        playersList.add(player3);
        playersList.add(player4);
        players.addPlayer(player1);
        players.addPlayer(player2);
        players.addPlayer(player3);
        players.addPlayer(player4);
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
        Player player1 = new Player(namePlayer);
        playersExpected.add(player1);

        Assert.assertTrue(playersExpected.equals(playersWithString.getPlayers()));

        //Create the players with a list of players
        ArrayList<Player> playersToCreate = new ArrayList<Player>(numberPlayers);
        playersToCreate.add(player1);
        PlayerGroup playersWithArray = new PlayerGroup(playersToCreate);

        Assert.assertTrue(playersExpected.equals(playersWithArray.getPlayers()));
    }

    @Test
    public void testGetPlayers(){
        ArrayList<Player> playersList = players.getPlayers();

        Assert.assertEquals(playersList.size(),NUMBER_PLAYERS);
    }

    @Test
    public void testSetPlayers(){
        ArrayList<Player> playersTest = new ArrayList<Player>(1);
        playersTest.add(player1);
        players.setPlayers(playersTest);
        ArrayList<Player> playersExpected = new ArrayList<Player>(1);
        playersExpected.add(player1);
        Assert.assertEquals(players.getPlayers(),playersExpected);
    }

    @Test
    public void testGetPlayer(){
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
    public void testAddPlayer(){
        players.addPlayer(player1);
        Player playerExpected = players.getPlayer(players.getNumberPlayers() -1);

        Assert.assertEquals(playerExpected, player1);
    }

    @Test
    public void testRemovePlayer(){
        players.removePlayer(player1);
        ArrayList<Player> playerExpectedList = playersList;
        playerExpectedList.remove(player1);
        PlayerGroup playerExpected = new PlayerGroup(playerExpectedList);

        Assert.assertEquals(playerExpected, players);
    }

    @Test
    public void testHasPlayer() throws PlayerNameTooLongException{
        Assert.assertTrue(players.hasPlayer(player1));
        Player player5 = new Player("Player5");
        Assert.assertFalse(players.hasPlayer(player5));
    }

    @Test
    public void testIsAlive(){
        Assert.assertTrue(players.isAlive());
        players.getPlayer(0).removeLife(Player.MAX_LIFE);

        Assert.assertFalse(players.isAlive());
    }

    @Test
    public void testRemoveDeadPlayers() throws PlayerNameTooLongException, NegativeLifeValueException{
        ArrayList<Player> playersTest = new ArrayList<Player>();
        playersTest.add(player1);
        playersTest.add(player2);
        players.setPlayers(playersTest);

        int lifePoints = Player.MAX_LIFE;
        players.getPlayer(0).removeLife(lifePoints);
        player1.removeLife(lifePoints);

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
    public void testRotatingPlayer(){
        int lastIndex = players.getNumberPlayers() - 1;

        Player firstPlayer = players.getPlayer(0);
        Player secondPlayer = players.getPlayer(1);
        Player lastPlayer = players.getPlayer(lastIndex);

        players.rotatingPlayers();

        Assert.assertEquals(secondPlayer, players.getPlayer(0));
        Assert.assertNotEquals(firstPlayer, players.getPlayer(0));
        Assert.assertEquals(firstPlayer, players.getPlayer(lastIndex));
        Assert.assertNotEquals(lastPlayer, players.getPlayer(lastIndex));
    }

    @Test
    public void testEquals() throws PlayerNameTooLongException{
        String[] namePlayers = {"Player1", "Player2"};
        PlayerGroup players1 = new PlayerGroup(namePlayers);
        PlayerGroup players2 = new PlayerGroup(namePlayers);
        Assert.assertEquals(players1, players2);
    }

    @Test
    public void testClone(){
        PlayerGroup playersClone = players.clone();
        Assert.assertEquals(players, playersClone);
        playersClone.addPlayer(player1);
        Assert.assertNotEquals(players, playersClone);
    }
}
