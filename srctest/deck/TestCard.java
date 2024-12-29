package srctest.deck;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import deck.Card;
import deck.CardException;
import deck.CardNameTooLongException;

public class TestCard {

	Card card1;
	Card card2;
	Card card3;
	Card card4;
	Card card5;

	@Before
	public void beforeTest() throws CardException, CardNameTooLongException {
		card1 = new Card();
		card2 = new Card("Fool", 22);
		card3 = new Card(String.valueOf(1), 1);
	}

	@After
	public void afterTest() {
		System.out.println("Test Card over");
	}

	@Test
	public void testGetName() {
		assertEquals(card1.getName(), "");
		assertEquals(card2.getName(), "Fool");
		assertEquals(card3.getName(), "1");
	}

	@Test
	public void testSetName() {
		card1.setName("NewName1");
		assertEquals(card1.getName(), "NewName1");
	}

	@Test(expected = CardNameTooLongException.class)
	public void testBadNameCard() throws CardException, CardNameTooLongException {
		card4 = new Card("A name that is way too long", 0);
	}

	@Test
	public void testSetValue() {
		card1.setValue(7);
		assertEquals(card1.getValue(), 7);
	}

	@Test
	public void testGetValue() {
		assertEquals(card1.getValue(), 0);
		assertEquals(card2.getValue(), 22);
		assertEquals(card3.getValue(), 1);
	}

	@Test(expected = CardException.class)
	public void testWrongValueAtCreation() throws CardException, CardNameTooLongException {
		card4 = new Card("WrongV", Card.MIN_VALUE - 1);
	}

	@Test(expected = CardException.class)
	public void testWrongValueAtCreation2() throws CardException, CardNameTooLongException {
		card5 = new Card("WrongV", Card.MAX_VALUE + 1);
	}

	@Test
	public void testEquals() throws CardException, CardNameTooLongException {
		card4 = new Card();
		Assert.assertTrue(card4.equals(card1));
		Assert.assertTrue(card1.equals(card4));

		card5 = new Card("Fool", 22);
		Assert.assertTrue(card5.equals(card2));
		Assert.assertTrue(card2.equals(card5));

		Assert.assertFalse(card5.equals(card1));
		Assert.assertFalse(card1.equals(card2));
	}
}
