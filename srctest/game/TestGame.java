package srctest.game;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import people.PlayerGroup;
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
    PlayerGroup playerGroup;

    Card card1;
    Card card2;
    Card card3;
    Card card4;
    ArrayList<Card> cardsList;

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
        playerGroup = new PlayerGroup(players);
        try{
            card1 = new Card("1",1);
            card2 = new Card("2",2);
            card3 = new Card("3",3);
            card4 = new Card("4",4);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        cardsList = new ArrayList<Card>();
        cardsList.add(card1);
        cardsList.add(card2);
        cardsList.add(card3);
        cardsList.add(card4);
    }
    
    @After
    public void afterTest(){ 
        System.setIn(originalSystemIn);
        System.out.println("Test Game over");
    }

    public void initGameWithInput(String simulatedInput){
        testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);
        gameWithPlayer = new Game();

        try{
            ArrayList<Player> listPlayer = new ArrayList<Player>();
            listPlayer.add(player1);
            listPlayer.add(player2);
            listPlayer.add(player3);
            listPlayer.add(player4);
            gameWithPlayer.setPlayers(listPlayer);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /** \brief Init players with cards in their hand
    * initPlayersCards(): Every player get add one unique card to their hand.
    */
    public void initPlayersCards(){
        player1.addCard(card1);
        player2.addCard(card2);
        player3.addCard(card3);
        player4.addCard(card4);
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

    @Test
    public void testPlayAllPlayers() throws NotEnoughCardsInDeckException, TooManyCardsException, RemovingTooManyCards, BadNumberOfPlayersException{
        String simulatedInput = "1 1 1 1";
        testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);
        Game game = new Game();

        ArrayList<Player> listPlayer = new ArrayList<Player>();
        listPlayer.add(player1);
        listPlayer.add(player2);
        listPlayer.add(player3);
        listPlayer.add(player4);
        game.setPlayers(listPlayer);

        //Creating cards of the players
        try{
            player1.addCard(card1);
            player2.addCard(card2);
            player3.addCard(card3);
            player4.addCard(card4);
        }
        catch(Exception e){
            System.out.println("Error in testPlayAllPlayers" + e.getMessage());
        }
        
        int indexCard = 0;
        HashMap<Player, Card> cardExpected = new HashMap<Player, Card>();
        for(Player player: listPlayer){
            cardExpected.put(player,player.getCard(indexCard));
        }
        
        HashMap<Player, Card> cardPlayed = game.playAllPlayers();
        Assert.assertEquals(cardExpected, cardPlayed);
        for(var entry : cardPlayed.entrySet()){
            Player currentPlayer = entry.getKey();
            Card currentCard = entry.getValue();
            Assert.assertFalse(currentPlayer.containsCard(currentCard));
        }
    }

    @Test
    public void testPlayOnePlayerLastRound(){
        //Valid input
        initGameWithInput("1");
        HashMap<Player, ArrayList<Card>> opponentsCards = new HashMap<Player, ArrayList<Card>>();

        ArrayList<Card> card1List = new ArrayList<Card>();
        card1List.add(card1);
        ArrayList<Card> card2List = new ArrayList<Card>();
        card1List.add(card2);
        ArrayList<Card> card3List = new ArrayList<Card>();
        card1List.add(card3);
        ArrayList<Card> card4List = new ArrayList<Card>();
        card1List.add(card4);

        opponentsCards.put(player1, card1List);
        opponentsCards.put(player2, card2List);
        opponentsCards.put(player3, card3List);
        opponentsCards.put(player4, card4List);

        HashMap<Player, Boolean> opponentsDecisions = new HashMap<Player, Boolean>();
        opponentsDecisions.put(player1, true);
        Boolean decisionTaken = gameWithPlayer.playOnePlayerLastRound(player2, opponentsCards,opponentsDecisions);
        Boolean decisionExpected = false;

        Assert.assertEquals(decisionTaken, decisionExpected);

        //Invalid and valid inputs
        initGameWithInput("3 string & 1");
        decisionTaken = gameWithPlayer.playOnePlayerLastRound(player2, opponentsCards,opponentsDecisions);

        Assert.assertEquals(decisionTaken, decisionExpected);
    }

    @Test
    public void testBuildOpponentsCards(){
        initPlayersCards();
        HashMap<Player, ArrayList<Card>> opponentsCards = gameWithPlayer.buildOpponentsCards(player1, playerGroup);
        Assert.assertFalse(opponentsCards.containsKey(player1));
        Assert.assertFalse(opponentsCards.containsValue(player1.getCards()));
        Assert.assertTrue(opponentsCards.containsKey(player2));
        Assert.assertTrue(opponentsCards.containsValue(player2.getCards()));
    }

    @Test
    public void testPlayAllPlayersLastRound(){
        HashMap<Player, Boolean> resultsExpected = new HashMap<Player, Boolean>();
        resultsExpected.put(player1, true);
        resultsExpected.put(player2, false);
        resultsExpected.put(player3, false);
        resultsExpected.put(player4, false);

        initPlayersCards();
        //Invalid and valid inputs
        initGameWithInput("3 string & 0 2 string & 1 1 1");
        HashMap<Player, Boolean> results = gameWithPlayer.playAllPlayersLastRound();

        for(Map.Entry<Player,Boolean> entry :resultsExpected.entrySet()){
            Boolean resultValue = results.get(entry.getKey());
            Assert.assertNotNull(resultValue);
            Assert.assertEquals(resultValue, entry.getValue());
        }
    }
}
