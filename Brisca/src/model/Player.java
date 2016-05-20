package model;
import java.util.ArrayList;

/**
 * This class manages the player and its game status
 * @author Mario Orbegoso
 *
 */
public class Player {
	
	private String username;
	private String password;
	private int won,lost,draw;
	private int points, totalPoints;
	private ArrayList<Card> hand;
	private Card chosenCard;
	private boolean played, cardsFetched;
	
	/**
	 * Creates player with username, password and hand 
	 * @param username - receives local username, type string
	 * @param password - receives password, type string
	 */
	public Player(String username, String password){
		
		this.username = username;
		this.password = password;
		hand = new ArrayList<>();
	}
	/**
	 * Return important information of the player in a single string 
	 * @return information of the player
	 */
	public String getStats(){
		
		String stats = "Username: " + this.username + "," + "Wins: " + this.won + "," + "Lost: " + this.lost + "," + "Draws: " + this.draw + "," + "Total Points: " + this.totalPoints;

		return stats;
	}
	/**
	 * Gets username for the player
	 * @return username type string
	 */
	public String getUsername(){
		return username;
	}	
	/**
	 * Keeps count of the games won and adds them to user
	 */
	public void addGamesWon(){
		won++;
	}
	/**
	 * Keeps count of games lost and adds them to user
	 */
	public void addGamesLost(){
		lost++;
	}
	/**
	 * Keeps count of games drawn and adds them to user
	 */
	public void addGamesDrawn(){
		draw++;
	}
	/**
	 * Counts points
	 * @param a
	 */
	public void addPoints(int a){
		points+= a;
	}
	/**
	 * Resets points
	 */
	public void resetPoints(){
		points = 0;
	}
	/**
	 * Keeps count of points and adds them
	 */
	public void addTotalPoints(){
		totalPoints+= points;
	}

	/**
	 * Adds card to hand
	 * @param a
	 */
	public void addCard(Card a){
		hand.add(a);
		cardsFetched = false;
	}
	/**
	 * Gets card from user and removes it from the hand
	 * @param card
	 */
	public void playCard(String card){
		
		for(int i = 0; i < hand.size(); i++){
			if(hand.get(i).getCard().equals(card)){
				chosenCard = hand.remove(i);
				played = true;
				break;
			}
		}
	}
	/**
	 * Checks if card is played
	 * @return boolean value
	 */
	public boolean hasPlayed(){
		return played;
	}
	/**
	 * Sets the state of the played instance to true or false
	 * @param a played or not
	 */
	public void setPlayedTo(boolean a){
		played = a;
	}
	/**
	 * Checks if the player already has fetched this information 
	 * @return true or false. If the player already has fetched this information 
	 */
	public boolean cardsFetched(){
		return cardsFetched;
	}
	/**
	 * Sets the state of the cardsFetched instance to true or false
	 * @param a 
	 */
	public void setCardsFetched(boolean a){
		cardsFetched = a;
	}
	/**
	 * Gets chosen card
	 * @return chosen card of type string
	 */
	public Card getChosenCard(){
		return chosenCard;	
	}
	/**
	 * Gets points 
	 * @return points of type integer
	 */
	public int getPoints(){
		return points;
	}
	/**
	 * Gets total points
	 * @return total points of type integer
	 */
	public int getTotalPoints(){
		return totalPoints;
	}
	/**
	 * Gets wins
	 * @return games won of type integer
	 */
	public int getWins(){
		return won;
	}
	/**
	 * Gets losses of user
	 * @return losses of type integer
	 */
	public int getLosses(){
		return lost;
	}
	/**
	 * Gets hand
	 * @return result of hand
	 */
	public String getHand(){
		String result = "";
		for(Card s : hand){
			result = result + s.getCard() + ",";
		}
		return result;
	}
}

