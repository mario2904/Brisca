package model;
import java.util.ArrayList;

import controller.GameManager;

/**
 * This class receives the four players, the game manager 
 * and stores them until it's time to start the game
 * @author Mario Orbegoso
 *
 */
public class Game {

	private String game;
	private ArrayList<Player> members;
	private boolean newMember, newChosenCard, newCurrentRoundInfo;
	private GameManager gameManager;
	private boolean gameStarted;
	private int newChosenCardsFetched, newCurrentRoundInfoFetched;
	private int chosenCardsCounter;
	private int round = 0;

	/**
	 * Constructor that receives the specification of the game and the creator of the game
	 * @param game the specification of the game
	 * @param p1 creator of the game
	 */
	public Game(String game, Player p1){
		members = new ArrayList<>();
		this.game = game;
		members.add(p1);
		newMember = true;
		chosenCardsCounter = 0;

		class RoundListeningThread implements Runnable{

			/**
			 * Starts the loop for the game
			 */
			@Override
			public void run() {

				while(true){

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if(chosenCardsCounter > 3){
						round++;

						System.out.println("Entered first round to calculate roundpoints and winner  -  Game");

						chosenCardsCounter = 0;

						members.get(0).setPlayedTo(false);
						members.get(1).setPlayedTo(false);
						members.get(2).setPlayedTo(false);
						members.get(3).setPlayedTo(false);

						gameManager.calculateRoundPoints();
						gameManager.calculateRoundWinner();
						newCurrentRoundInfo = true;

						if(moreCards()) nextRound();
						if(round == 10){							
							gameManager.assignPointsToWinnerOfGame();
						}
					}
				}	
			}
		}
		(new Thread(new RoundListeningThread())).start();

	}
	/**
	 * Returns the life card of this current game
	 * @return the life card of this current game
	 */
	public String getLifeCard(){
		return gameManager.getDeck().getLife().getCard();
	}
	/**
	 * Returns the usernames of all the members of the game
	 * @return the usernames of all the members of the game
	 */
	public String getMembers(){
		String result = "";
		for(Player s : members)
			result += s.getUsername() + ",";
		return result;
	}
	/**
	 * Starts the game
	 */
	public void startGame(){

		gameManager = new GameManager(members.get(0),members.get(1),members.get(2),members.get(3));
		gameStarted = true;

		firstRound();
	}
	/**
	 * Returns the info of the winner of the game. It's username and total points.
	 * @return  username and total points of the winner of the game
	 */
	public String getWinnerInfo(){
		
		String winner = members.get(gameManager.getWinnerOfGame()).getUsername();
		int totalPoints = members.get(gameManager.getWinnerOfGame()).getTotalPoints();
		
		return winner + "-" + totalPoints;
	}
	/**
	 * Returns the boolean value that tells whether or not the deck has more cards left
	 * @return whether or not the deck has more cards left
	 */
	public boolean moreCards(){
		return (gameManager.getDeck().getSize() > 0);
	}
	/**
	 * Deals three cards to each player in order starting from the player that created the game
	 */
	public void firstRound(){
		int firstPlayerToDeal = gameManager.getIndexPlayerFirstTurn();

		for(int i = 0; i <= 2; i++)
			for(int j = firstPlayerToDeal; j <= firstPlayerToDeal + members.size() - 1; j++)
				addCard(j % 4, gameManager.getDeck().deal());
	}
	/**
	 * Deals one card to each player in order starting from the winner of last round
	 */
	public void nextRound(){
		int firstPlayerToDeal = gameManager.getIndexPlayerFirstTurn();

		for(int j = firstPlayerToDeal; j <= firstPlayerToDeal + members.size() -1; j++)
			addCard(j % 4, gameManager.getDeck().deal());
	}
	/**
	 * Adds a new player to the game
	 * @param member new player
	 */
	public void addMember(Player member){
		members.add(member);
	}
	/**
	 * Returns the specifications of the game
	 * @return specifications of the game
	 */
	public String getGame(){
		return game;
	}
	/**
	 * Returns the specific player
	 * @param i index
	 * @return a player of the game
	 */
	public Player getMember(int i){
		return members.get(i);
	}
	/**
	 * Changes the boolean value of the instance variable newMember to true
	 */
	public void newMember(){
		newMember = true;
	}
	/**
	 * Changes the boolean value of the instance variable newMember to false
	 */
	public void noNewMember(){
		newMember = false;
	}
	/**
	 * Returns a boolean value to whether a new member has joined the game or not
	 * @return a boolean value to whether a new member has joined the game or not
	 */
	public boolean hasNewMember(){
		return newMember;
	}	
	/**
	 * Returns a boolean value to whether the game has started or not
	 * @return a boolean value to whether the game has started or not
	 */
	public boolean hasGameStarted(){
		return gameStarted;
	}
	/**
	 * Returns the current round information. The winner of this round and the round points
	 * @return the current round information
	 */
	public String getCurrentRoundInfo(){

		if(newCurrentRoundInfo == true){

			if(newCurrentRoundInfoFetched < members.size()){
				newCurrentRoundInfoFetched++;

				String result = gameManager.getWinnerOfRound() + "-" + gameManager.getRoundPoints();

				return result;
			}
			else{
				newCurrentRoundInfo = false;
				newCurrentRoundInfoFetched = 0;
				return "noNewCurrentRoundInfo";
			}
		}
		else{
			return "noNewCurrentRoundInfo";
		}
	}
	/**
	 * Returns the size of the players in the game
	 * @return current size of the players in the game
	 */
	public int numOfMembers(){
		return members.size();
	}
	/**
	 * Adds a card to the specified player in the game
	 * @param i
	 * @param card
	 */
	public void addCard(int i, Card card){
		members.get(i).addCard(card);
	}
	/**
	 * Selects chosen card for a specified player
	 * @param name username of player
	 * @param card card to be played 
	 */
	public void chooseCard(String name, String card){

		for(int i = 0; i < members.size(); i++){
			if(members.get(i).getUsername().equals(name)){
				members.get(i).playCard(card);
				newChosenCard = true;
				break;
			}
		}
	}
	/**
	 * Returns the chosen cards and who played them in a a single string
	 * @return the chosen cards and who played them
	 */
	public String getChosenCards(){ 

		if(newChosenCard == true){

			if(newChosenCardsFetched < members.size()){
				newChosenCardsFetched++;

				String result = "";

				for(int i = 0; i < members.size(); i++){
					if(members.get(i).hasPlayed()){
						result += members.get(i).getUsername() + "-" + members.get(i).getChosenCard().getCard() + ",";
					}
				}
				return result;
			}
			else{
				chosenCardsCounter++;
				System.out.println( "Chosen Cards Counter: " + chosenCardsCounter);
				newChosenCard = false;
				newChosenCardsFetched = 0;
				return "noNewCardsChosen";
			}
		}
		else{
			return "noNewCardsChosen";
		}
	}
	/**
	 * Returns the cards of the player
	 * @param name - the username of the player
	 * @return cards of the player
	 */
	public String getMyCards(String name){

		for(int i = 0; i < members.size(); i++){
			if(members.get(i).getUsername().equals(name)){
				if(!members.get(i).cardsFetched()){
					String result = members.get(i).getHand();
					members.get(i).setCardsFetched(true);
					return result;
				}
				else{
					return "noNewCard";
				}
			}
		}
		return "noNewCard";
	}

}
