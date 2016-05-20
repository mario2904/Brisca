package model;
import java.util.ArrayList;
import java.util.Random;
/**
 * This class create the deck to be used in the game
 * @author Mario Orbegoso
 *
 */
public class Deck {

	public ArrayList <Card> deck;
	
	public Deck(){
		
		deck = new ArrayList<>();
		//Initialize deck of 40 cards (in order)
		for(int i = 0; i<= 39; i++)
			deck.add(new Card(i));
		
		//shuffle();
	}	
	/**
	 * Method for shuffling the deck cards
	 */
	public void shuffle(){
		
		Random randomizer = new Random();
		// iterate over the array
		for(int i = 0; i <= deck.size() - 1; i++ )
		{
			// choose a random int to swap with
			int d = randomizer.nextInt(deck.size());
			// swap the entries
			Card t = deck.get(d);
			deck.set(d, deck.get(i));
			deck.set(i, t);
		}
	}
	/**
	 * Deals cards to players
	 * Removes card from the deck
	 * @return deck with the card removed
	 */
	public Card deal(){
		return deck.remove(deck.size()-1);
	}
	/**
	 * Gets size of the deck
	 * @return size of deck in type integer
	 */
	public int getSize(){
		return deck.size();
	}
	/**
	 * Gets "life" of the game (card that determines which suit is the winner)
	 * @return life of the game of type card
	 */
	public Card getLife(){
		return deck.get(0);
	}
}
