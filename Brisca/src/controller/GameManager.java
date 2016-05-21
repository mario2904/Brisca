package controller;
import java.util.ArrayList;

import model.Deck;
import model.Player;
/**
 * This class creates the game logic 
 * @author Mario Orbegoso
 *
 */
public class GameManager {

	private ArrayList<Player> players = new ArrayList<>();
	private Deck deck;
	private String life;
	private int roundPoints;
	private int indexPlayerFirstTurn;
	private int indexOfLife;
	private int winnerOfGame;
	
	/**
	 * Adds player, gets deck, shuffles deck, sets life of the game
	 * @param player1 receives the characteristics of player1
	 * @param player2 receives the characteristics of player2
	 * @param player3 receives the characteristics of player3
	 * @param player4 receives the characteristics of player4
	 */
	public GameManager(Player player1, Player player2, Player player3, Player player4){
		
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		
		//creates a deck and shuffles it
		deck = new Deck();
		deck.shuffle();
		
		life = deck.getLife().getSuit();
		indexPlayerFirstTurn = 0;
		System.out.println("The life is: " + life + "\n");
		
	}
	/**
	 * Returns the deck of this game
	 * @return deck of this game
	 */
	public Deck getDeck(){
		return deck;
	}
	/**
	 * Returns the index of the player who won the last round
	 * @return the index of the player who won the last round
	 */
	public int getIndexPlayerFirstTurn(){
		return indexPlayerFirstTurn;
	}
	/**
	 * Calculates the points of every round
	 */
	public void calculateRoundPoints(){
		roundPoints = 0;
		
		for(int i = 0; i <= players.size() -1; i++)
			roundPoints += players.get(i).getChosenCard().getValue();
		
		System.out.println("Total score: " + roundPoints);
	}
	/**
	 * Returns the points of the round played
	 * @return points of the round
	 */
	public int getRoundPoints(){
		return roundPoints;
	}
	/**
	 * Determines which player wins the round according to the points, the life and the dominating suit
	 */
	public void calculateRoundWinner(){	
		int indexOfWinner = indexPlayerFirstTurn;
		String dominatingSuit;
		
		if(lifePlayed()){
			dominatingSuit = life;
			indexOfWinner = indexOfLife;
		}
			
		else{
			dominatingSuit = players.get(indexPlayerFirstTurn).getChosenCard().getSuit();
		}
		for(int j = indexPlayerFirstTurn; j <= indexPlayerFirstTurn + players.size() -2; j++){			
			if(players.get(indexOfWinner).getChosenCard().getSuit().equals(dominatingSuit) && players.get((j + 1)  % 4).getChosenCard().getSuit().equals(dominatingSuit))
				if(players.get(indexOfWinner).getChosenCard().getHierarchyValue() < players.get((j + 1)  % 4).getChosenCard().getHierarchyValue())
					indexOfWinner = ((j + 1)  % 4);
		}
		
		indexPlayerFirstTurn = indexOfWinner;
		System.out.println(players.get(indexOfWinner).getUsername() + " won this round!");
		addRoundPointstoWinner(indexOfWinner);
				
	}
	public String getWinnerOfRound(){
		return players.get(indexPlayerFirstTurn).getUsername();
	}
	/**
	 * Determines if a "life card" is played 
	 * @return boolean value 
	 */
	private boolean lifePlayed(){
		for(int i = 0; i <= players.size() -1; i++)
			if(players.get(i  % 4).getChosenCard().getSuit().equals(life)){
				indexOfLife=i;
				return true;
			}
		return false;		
	}
	/**
	 * Adds the points of the round to the winner of said round
	 * @param index  index of winner
	 */
	private void addRoundPointstoWinner(int index){
		players.get(index).addPoints(roundPoints);
		
	}
	/**
	 * Calculates the winner of the game according to the points won in each round
	 * @return the name of the winner in type string
	 */
	private int calculateWinnerOfGame(){
		int winner = 0;
		
		for(int i = 0; i <= players.size()-2; i++)
			if(players.get(winner).getPoints() < players.get(i+1).getPoints())
				winner = i+1;
		
		winnerOfGame = winner;
		
		System.out.println("Winner of this game is: " + players.get(winner).getUsername());
		return winner;
		
	}
	/**
	 * Returns the index of the winner of the game
	 * @return index of the winner of the game
	 */
	public int getWinnerOfGame(){
		return winnerOfGame;
	}
	/**
	 * Reset the points of each player at the end of the game
	 */
	private void resetPoints(){
		for(int i = 0; i <= players.size()-1; i++)
			players.get(i).resetPoints();
	}
	/**
	 * Add 1 to the losses of each player
	 */
	private void addLossesToOtherPlayers(){
		for(int i = 0; i < players.size(); i++){
			if(i != winnerOfGame){
				players.get(i).addGamesLost();
			}
		}
	}
	/**
	 * Assign points to the winner of the game and add 1 to his wins
	 */
	public void assignPointsToWinnerOfGame(){
		
		int winner = calculateWinnerOfGame();
		players.get(winner).addTotalPoints();
		players.get(winner).addGamesWon();
		
		resetPoints();
		addLossesToOtherPlayers();
	}
	
}
