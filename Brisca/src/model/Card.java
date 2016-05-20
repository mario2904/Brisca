package model;
/**
 * This class will create the cards to be used in the game
 * Will assign the images accordingly, rank and suit
 * @author Mario Orbegoso
 *
 */
public class Card {
	
	private String rank;
	private String suit;
	private int value;
	private String card;
	private int hierarchy;
	private static final int hierarchyValue[] = {0,10,1,9,2,3,4,5,0,0,6,7,8};
	private static final String cardName[]= {"1o", "2o", "3o", "4o","5o", "6o", "7o", "10o", "11o","12o",
								"1b", "2b", "3b", "4b","5b", "6b", "7b", "10b", "11b","12b",
								"1e", "2e", "3e", "4e","5e", "6e", "7e", "10e", "11e","12e",
								"1c", "2c", "3c", "4c","5c", "6c", "7c", "10c", "11c","12c"};
	
	
	/**
	 * Initialize each card with its image, suit and rank
	 * @param i - receive the number of card as an integer 
	 */
	public Card(int i){
		
		card = cardName[i];
		rank = card.substring(0,card.length() - 1);
		suit = card.substring(card.length()-1, card.length());
		value = calculateValue(rank);
		hierarchy = hierarchyValue[Integer.parseInt(rank)];	
		
	}
	/**
	 * Method that will calculate the value of the card according to its rank
	 * @param rank - receives the rank of the card as a string
	 * @return value of the card
	 */
	private int calculateValue(String rank){	
		if(rank.equals("1"))
			return 11;
		else if(rank.equals("3"))
			return 10;
		else if(rank.equals("10"))
			return 2;
		else if(rank.equals("11"))
			return 3;
		else if(rank.equals("12"))
			return 4;
		else{
			return 0;
		}
	}	
	/**
	 * Gets the rank of the card
	 * @return rank of card as a string
	 */
	public String getRank(){
		return rank;
	}	
	/**
	 * Gets the suit of the card
	 * @return suit of card as a string
	 */
	public String getSuit(){
		return suit;
	}
	/**
	 * Gets the value of the card
	 * @return value of the card as an integer value
	 */
	public int getValue(){
		return value;
	}
	/**
	 * Gets the suit, rank and value of the card
	 * @return card as a string
	 */
	public String getCard(){
		return card;
	}
	/**
	 * Gets the position of the card according to its value
	 * @return hierarchy as an integer
	 */
	public int getHierarchyValue(){
		return hierarchy;
	}
}
