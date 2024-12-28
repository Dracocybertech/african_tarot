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
    Game game;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player player5;
    ArrayList<Player> players;
    PlayerGroup playerGroup;

    Card card1;
    Card card2;
    Card card3;
    Card card4;
    Card fool;
    ArrayList<Card> cardsList;

    @Before
    public void beforeTest() throws PlayerNameTooLongException, BadNumberOfPlayersException{
        game = new Game();
        players = new ArrayList<Player>(Game.NUMBER_PLAYERS);
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        player3 = new Player("Player3");
        player4 = new Player("Player4");
        player5 = new Player("Player5");
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        game.setPlayers(players);
        playerGroup = new PlayerGroup(players);
        try{
            card1 = new Card("1",1);
            card2 = new Card("2",2);
            card3 = new Card("3",3);
            card4 = new Card("4",4);
            fool = new Card("Fool",22);
        }
        catch(Exception e){
            System.out.println("Error while beforeTest testGame");
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

    /** \brief Init buffer with input
    * initGameWithInput(String simulatedInput): Initialize the game with a buffer already set with the input.
    * \param String simulatedInput
    */
    public void initGameWithInput(String simulatedInput){
        testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);
        game = new Game();

        try{
            ArrayList<Player> listPlayer = new ArrayList<Player>();
            listPlayer.add(player1);
            listPlayer.add(player2);
            listPlayer.add(player3);
            listPlayer.add(player4);
            game.setPlayers(listPlayer);
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
        Assert.assertEquals(game.getPlayers(), players);
    }

    @Test
    public void testSetPlayers() throws PlayerNameTooLongException, BadNumberOfPlayersException, NegativeLifeValueException{
        ArrayList<Player> playersTest = new ArrayList<Player>(2);
        playersTest.add(player1);
        player2.setLife(0);
        playersTest.add(player2);
        ArrayList<Player> playersAliveTest = new ArrayList<Player>(1);
        playersAliveTest.add(player1);
        game.setPlayers(playersTest);

        Assert.assertEquals(game.getPlayers(), playersTest);
        Assert.assertEquals(game.getPlayersAlive(), playersAliveTest);
        Assert.assertNotEquals(game.getPlayers(), game.getPlayersAlive());
    }
    
    @Test(expected=BadNumberOfPlayersException.class)
    public void testBadNumberOfPlayersException() throws BadNumberOfPlayersException{
        ArrayList<Player> playersTest = new ArrayList<Player>(Game.NUMBER_PLAYERS);
        //Add an extra player to go over the limit of max players
        for(int i = 0 ; i < Game.NUMBER_PLAYERS + 1 ; i++){
            playersTest.add(player1);
        }
        game.setPlayers(playersTest);
    }

    @Test
    public void testGetPlayersAlive() throws NegativeLifeValueException, BadNumberOfPlayersException{
        //Case where every player of the game is alive
        Assert.assertEquals(game.getPlayersAlive(), players);
        
        //Set the life of one player to 0 to see if the number of player alive is the right one 
        //even with dead players
        player1.setLife(0);
        ArrayList<Player> playersTest = new ArrayList<Player>(2);
        playersTest.add(player1);
        playersTest.add(player2);
        game.setPlayers(playersTest);
        ArrayList<Player> playersAliveExpected = new ArrayList<Player>(1);
        playersAliveExpected.add(player2);
        Assert.assertEquals(playersAliveExpected, game.getPlayersAlive());
    }

    @Test
    public void testGetNumberPlayers(){
        Assert.assertEquals(game.getNumberPlayers(), Game.NUMBER_PLAYERS);
    }

    @Test
    public void testGetNumberPlayersAlive(){
        Assert.assertEquals(game.getNumberPlayersAlive(), Game.NUMBER_PLAYERS);
    }

    @Test
    public void testGetDeck(){
        Deck deckExpected = new Deck();
        deckExpected.buildDeck();
        Assert.assertEquals(game.getDeck().getSize(), deckExpected.getSize());
    }

    @Test
    public void testGetDeckSize(){
        Deck deckExpected = new Deck();
        deckExpected.buildDeck();
        Assert.assertEquals(game.getDeckSize(), deckExpected.getSize());
    }

    @Test
    public void testGetFoolPlayer(){
        Assert.assertEquals(game.getFoolPlayer(), new HashMap<Player, Card>());
    }

    @Test
    public void testSetFoolPlayer(){
        game.setFoolPlayer(player1, fool);
        HashMap<Player, Card> expectedFoolPlayer = new HashMap<Player, Card>();
        expectedFoolPlayer.put(player1, fool);
        Assert.assertEquals(game.getFoolPlayer(), expectedFoolPlayer);
    }

    @Test
    public void testCreatePlayer(){
        //Invalid and valid input
        //PlayerWithANameTooLong is invalid because it raised PlayerNameTooLongException
        //Player1 is invalid because a player already has this name
        //Player5 is a valid name because no one has the name and itas not raising any exception
        String simulatedInput = "PlayerWithANameTooLong Player1 Player5 \n";
        initGameWithInput(simulatedInput);

        Player newPlayer = game.createPlayer();
        
        System.out.println(game.getPlayers());

        //Check if player5 was created
        Assert.assertEquals(player5, newPlayer);
        Assert.assertNotEquals(player1, newPlayer);
    }

    @Test
    public void testCreatePlayers(){
        //Invalid and valid input
        String simulatedInput = "Player1 Player2 PlayerWithANameTooLong Player3 Player4";
        testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);
        game = new Game();
    
        //Create allplayers
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
        int deckSize = game.getDeckSize();
        //Compute the number of cards that can be distributed
        int maxCardsDistributable = deckSize / game.getNumberPlayersAlive();
        //Distribute cards
        game.distributeCards(maxCardsDistributable);
        int deckSizeAfterDistribution = game.getDeckSize();
        int expectedDeckSize = deckSize - maxCardsDistributable * game.getNumberPlayersAlive();
        
        //Check that the size of the deck has been reduced of the correct amount
        Assert.assertEquals(deckSizeAfterDistribution, expectedDeckSize);

        //Check if each player received the correct number of cards
        for (Player player: game.getPlayersAlive()){
            Assert.assertEquals(player.getNumberCards(), maxCardsDistributable);
        }
    }

    @Test
    public void testPlayOnePlayer() throws NotEnoughCardsInDeckException, TooManyCardsException, RemovingTooManyCards, BadNumberOfPlayersException{
        //Invalid and valid input
        //String & are the invalid inputs
        //The first 1 is a random card played but the first card the player has to make sure they can play at least a card
        //The 2nd 1 is for the fool test part
        //\n is for the enter input everytime it's needed
        String simulatedInput = "\n \n String & 1 \n \n 1 \n \r";
        initGameWithInput(simulatedInput);

        //Distribute cards to every player
        int deckSize = game.getDeckSize();
        int maxCardsDistributable = deckSize / game.getNumberPlayersAlive();
        game.distributeCards(maxCardsDistributable);

        int indexCard = 0;
        int indexPlayer = 0;

        //Only created to allow playOnePlayer to run as needed as parameter
        //Does not matter which value is inside and is not used for testing purpose
        HashMap<Player, Card> cardsPlayed = new HashMap<Player, Card>();
        cardsPlayed.put(player2, card2);

        //It works because every player1 is a shallow copy inside the list in the game
        Card cardExpected = player1.getCard(indexCard);
        Card cardPlayed = game.playOnePlayer(player1, cardsPlayed);

        //Check if the card returned is the card played 
        Assert.assertEquals(cardExpected, cardPlayed);
        //Check if the card is no longer in the hand of the player
        Assert.assertFalse(game.getPlayer(indexPlayer).containsCard(cardPlayed));

        //Test if the fool card is stored by the game
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(fool);
        player1.setCards(cards);

        //It works because every player1 is a shallow copy inside the list in the game
        cardExpected = player1.getCard(indexCard);
        cardPlayed = game.playOnePlayer(player1, cardsPlayed);

        //Check if the card returned is the card played 
        Assert.assertEquals(cardExpected, cardPlayed);
        //Check if the card is no longer in the hand of the player
        Assert.assertFalse(game.getPlayer(indexPlayer).containsCard(cardPlayed));
        //Check if the fool card and the player is stored by the game
        HashMap<Player, Card> expectedFoolPlayer = new HashMap<Player, Card>();
        expectedFoolPlayer.put(player1, fool);
        Assert.assertEquals(game.getFoolPlayer(), expectedFoolPlayer);
    }

    @Test
    public void testPlayAllPlayers() throws NotEnoughCardsInDeckException, TooManyCardsException, RemovingTooManyCards, BadNumberOfPlayersException{
        //Every player played the first card in their hand with the input 1
        //\n is for the enter input everytime it's needed
        String simulatedInput = "\n \n 1 \n \n 1 \n \n 1 \n \n 1 \n";
        initGameWithInput(simulatedInput);

        //Give one specific card to every player
        initPlayersCards();
        
        int indexCard = 0;
        HashMap<Player, Card> cardExpected = new HashMap<Player, Card>();
        //Each player should play the only card they have in their hand
        for(Player player: players){
            cardExpected.put(player,player.getCard(indexCard));
        }
        
        //Every player play one card
        HashMap<Player, Card> cardPlayed = game.playAllPlayers();

        //Check if the card returned is the card played 
        Assert.assertEquals(cardExpected, cardPlayed);
        for(var entry : cardPlayed.entrySet()){
            Player currentPlayer = entry.getKey();
            Card currentCard = entry.getValue();
            //Check if the card is no longer in the hand of the player
            Assert.assertFalse(currentPlayer.containsCard(currentCard));
        }
    }

    @Test
    public void testPlayOnePlayerLastRound(){
        //Valid input
        initGameWithInput("\n \n 1 \n");
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
        Boolean decisionTaken = game.playOnePlayerLastRound(player2, opponentsCards,opponentsDecisions);
        Boolean decisionExpected = false;

        //Check if the decision the player took is the one returned
        Assert.assertEquals(decisionTaken, decisionExpected);

        //Invalid and valid inputs
        initGameWithInput("\n \n 3 string & 1 \n");
        decisionTaken = game.playOnePlayerLastRound(player2, opponentsCards,opponentsDecisions);

        //Check if the decision the player took is the one returned
        Assert.assertEquals(decisionTaken, decisionExpected);
    }

    @Test
    public void testBuildOpponentsCards(){
        initPlayersCards();
        //Cards opponents have in their hands
        HashMap<Player, ArrayList<Card>> opponentsCards = game.buildOpponentsCards(player1, playerGroup);
        
        //Check if among the cards, there is not the card of the player displayed
        Assert.assertFalse(opponentsCards.containsKey(player1));
        Assert.assertFalse(opponentsCards.containsValue(player1.getCards()));
        //Check if among the cards, there is those of one of the opponents
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
        // 3 string & are invalid input
        // 0 is a valid input
        // 2 string & are invalid input
        //1 is a valid input
        //\n is for simulating enter input
        initGameWithInput("\n \n 3 string & 0 \n \n 2 string & 1 \n \n 1 \n \n 1 \n");
        HashMap<Player, Boolean> results = game.playAllPlayersLastRound();

        for(Map.Entry<Player,Boolean> entry :resultsExpected.entrySet()){
            Boolean resultValue = results.get(entry.getKey());
            //Check if every player have made a decision
            Assert.assertNotNull(resultValue);
            //Check if the decision returned is the one in the input
            Assert.assertEquals(resultValue, entry.getValue());
        }
    }

    @Test
    public void testBetTricks(){
        initPlayersCards();
        int expectedBetPlayer1 = 1;
        int expectedBetPlayer2 = 1;
        int expectedBetPlayer3 = 2;
        int expectedBetPlayer4 = 2;
        int numberRound = 5;

        //Invalid and valid inputs
        // string & are invalid input
        //any integers is a valid input
        String input = "\n \n " +
        expectedBetPlayer1 + " \n \n string & " 
        + expectedBetPlayer2 + " \n \n "
        + expectedBetPlayer3 + "\n \n"
        + expectedBetPlayer4 + "\n";
        initGameWithInput(input);
        
        game.betTricks(numberRound);

        //Check if all players have made a bet
        Assert.assertEquals(player1.getBetTricks(), expectedBetPlayer1);
        Assert.assertEquals(player2.getBetTricks(), expectedBetPlayer2);
        Assert.assertEquals(player3.getBetTricks(), expectedBetPlayer3);
        Assert.assertEquals(player4.getBetTricks(), expectedBetPlayer4);

        //Valid inputs but total is equal to the number of cards distributed
        int wrongValue = 1;
        input = "\n \n" + expectedBetPlayer1 + " \n \n string & " 
        + expectedBetPlayer2 + "\n \n "
        + expectedBetPlayer3 + "\n \n "
        + wrongValue +  "\n \n "
        + expectedBetPlayer4 + "\n";
        initGameWithInput(input);
        game.betTricks(numberRound);

        //Check if all players have made a bet
        Assert.assertEquals(player1.getBetTricks(), expectedBetPlayer1);
        Assert.assertEquals(player2.getBetTricks(), expectedBetPlayer2);
        Assert.assertEquals(player3.getBetTricks(), expectedBetPlayer3);
        Assert.assertEquals(player4.getBetTricks(), expectedBetPlayer4);
    }

    @Test
    public void testEvaluateRound(){
        int rightBet = 2;
        int wrongBet = 3;
        int currentTricks = 2;

        int player1Life = player1.getLife();
        int player2Life = player2.getLife();
        int player3Life = player3.getLife();
        int player4Life = player4.getLife();

        try{
            //players who predicted right
            player1.setBetTricks(rightBet);
            player1.setCurrentTricks(currentTricks);
            player2.setBetTricks(rightBet);
            player2.setCurrentTricks(currentTricks);
            player3.setBetTricks(rightBet);
            player3.setCurrentTricks(currentTricks);

            //Players who predicted wrong
            player4.setBetTricks(wrongBet);
            player4.setCurrentTricks(currentTricks);
        }
        catch(Exception e){
            System.out.println("Error for testEvaluate: "+ e.getLocalizedMessage());
        }
        
        //This input is needed for the enter input as the end of evaluateRound
        String input = "\n";
        initGameWithInput(input);

        game.evaluateRound();
        
        //Check if players loose life if prediction was wrong
        Assert.assertEquals(player1.getLife(), player1Life);
        Assert.assertEquals(player2.getLife(), player2Life);
        Assert.assertEquals(player3.getLife(), player3Life);
        Assert.assertEquals(player4.getLife(), player4Life - (Math.abs(wrongBet - currentTricks)));
        wrongBet = Player.MAX_LIFE + currentTricks;
        //Case where a player has 0 life points
        wrongBet = Player.MAX_LIFE + currentTricks;
        try{
            //Players who predicted wrong
            player4.setBetTricks(wrongBet);
        }
        catch(Exception e){
            System.out.println("Error for testEvaluate when player has 0 life points: "+ e.getLocalizedMessage());
        }
        
        game.evaluateRound();

        //Check if players loose life if prediction was wrong
        Assert.assertEquals(player1.getLife(), player1Life);
        Assert.assertEquals(player2.getLife(), player2Life);
        Assert.assertEquals(player3.getLife(), player3Life);
        Assert.assertEquals(player4.getLife(),0);
    }

    @Test
    public void testEvaluateCards(){
        HashMap<Player, Card> cardsPlayed = new HashMap<Player, Card>();
        cardsPlayed.put(player1,card1);
        cardsPlayed.put(player2,card2);
        cardsPlayed.put(player3,card3);
        cardsPlayed.put(player4,card4);

        int expectedCurrentTricksPlayer1 = player1.getCurrentTricks();
        int expectedCurrentTricksPlayer2 = player2.getCurrentTricks();
        int expectedCurrentTricksPlayer3 = player3.getCurrentTricks();
        int expectedCurrentTricksPlayer4 = player4.getCurrentTricks()+1;

        //\n is for the enter input at the end of the function
        initGameWithInput("\n");
        game.evaluateCards(cardsPlayed);

        //Check if the player who won this turn got +1 in they tricks
        Assert.assertEquals(expectedCurrentTricksPlayer1, player1.getCurrentTricks());
        Assert.assertEquals(expectedCurrentTricksPlayer2, player2.getCurrentTricks());
        Assert.assertEquals(expectedCurrentTricksPlayer3, player3.getCurrentTricks());
        Assert.assertEquals(expectedCurrentTricksPlayer4, player4.getCurrentTricks());
    
        //Test in case the fool got played this turn
        //Invalid and valid inputs
        //3 string & are invalid input
        //22 is a valid input
        //\n is for the enter input at the end of the function
        initGameWithInput("3 string & 22 \n");

        //Player1 plays the fool
        cardsPlayed.remove(player1);
        cardsPlayed.put(player1, fool);

        game.setFoolPlayer(player1, fool);
        
        expectedCurrentTricksPlayer1 = player1.getCurrentTricks() + 1;
        expectedCurrentTricksPlayer2 = player2.getCurrentTricks();
        expectedCurrentTricksPlayer3 = player3.getCurrentTricks();
        expectedCurrentTricksPlayer4 = player4.getCurrentTricks();

        game.evaluateCards(cardsPlayed);
        
        //Check if the player who won this turn got +1 in they tricks
        //Player1 should be the one who won wince they choose 22, the highest value
        Assert.assertEquals(expectedCurrentTricksPlayer1, player1.getCurrentTricks());
        Assert.assertEquals(expectedCurrentTricksPlayer2, player2.getCurrentTricks());
        Assert.assertEquals(expectedCurrentTricksPlayer3, player3.getCurrentTricks());
        Assert.assertEquals(expectedCurrentTricksPlayer4, player4.getCurrentTricks());
    }

    @Test
    public void testEvaluateCardsLastRound(){
        initPlayersCards();
        HashMap<Player, Boolean> decisions = new HashMap<Player, Boolean>();
        decisions.put(player1, false);
        decisions.put(player2, false);
        //Wrong prediction
        decisions.put(player3, true);
        //Winner
        decisions.put(player4, true);

        int expectedLifePointsPlayer1 = player1.getLife();
        int expectedLifePointsPlayer2 = player2.getLife();
        //Should lose a life point for predicting wrongfully
        int expectedLifePointsPlayer3 = player3.getLife() -1;
        int expectedLifePointsPlayer4 = player4.getLife();

        //\n is for the enter input at the end of the function
        initGameWithInput("\n");

        game.evaluateCardsLastRound(decisions);

        //Check if the player loose life points if theyr predicted wrong
        Assert.assertEquals(expectedLifePointsPlayer1, player1.getLife());
        Assert.assertEquals(expectedLifePointsPlayer2, player2.getLife());
        Assert.assertEquals(expectedLifePointsPlayer3, player3.getLife());
        Assert.assertEquals(expectedLifePointsPlayer4, player4.getLife());
    }

    @Test
    public void testEvaluateDeadPlayers() throws NegativeLifeValueException{
        //List of players alive before the evaluation
        ArrayList<Player> playersAlive = game.getPlayersAlive();
        ArrayList<Player> players = game.getPlayers();

        //Let player4 the player who got 0 life points at the end of the round
        player4.setLife(0);
        game.evaluateDeadPlayers();

        //Check if player4 is removed from the list of players alive
        Assert.assertNotEquals(playersAlive, players);
        Assert.assertFalse(playersAlive.contains(player4));
    }

    @Test
    public void testIsVictory(){
        //Check that the victory condition is not achieved yet
        Assert.assertFalse(game.isVictory());

        //Set all life of players to 0 except to one
        try{
            player1.setLife(0);
            player2.setLife(0);
            player3.setLife(0);
            ArrayList<Player> playersTest = new ArrayList<Player>();
            playersTest.add(player1);
            playersTest.add(player2);
            playersTest.add(player3);
            playersTest.add(player4);
            game.setPlayers(playersTest);
        }
        catch(Exception e){
            System.err.println("Error while processing testIsVictory "+e.getMessage());
        }
        
        System.out.println(game.getPlayersAlive());
        System.out.println(game.getNumberPlayersAlive());

        //Check that the victory condition is achieved
        Assert.assertTrue(game.isVictory());

        //Set all life of players to 0
        try{
            player4.setLife(0);
        }
        catch(Exception e){
            System.err.println("Error while processing testIsVictory "+e.getMessage());
        }
        
        //Check that the victory condition is achieved
        Assert.assertTrue(game.isVictory());
    }
}
