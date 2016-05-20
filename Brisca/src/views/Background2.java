package views;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.ClientEngine;
import model.CardImage;

/**
 * This class receives the image for the backgound
 * Handles the animations of the cards
 * @author Mario Orbegoso
 *
 */
public class Background2 extends JPanel {

	private static Image bg = new ImageIcon("Images/back2.jpg").getImage();
	private static Image back1 = new ImageIcon("Images/backofcard.jpg").getImage();

	private int height1 = back1.getHeight(this);// 139
	private int width1 = back1.getWidth(this);// 83

	private int panelWidth, panelHeight;
	private static final int TOTAL_ROUNDS = 10;
	private int round = 1;
	private static final int DISTANCE = 2;
	private static final int TIME = 4;
	
	private String[] playerName = new String[4];
	
	private CardImage deck = new CardImage("backofcard", 0, 0);
	private CardImage life = new CardImage("backofcard", 0, 0);
	 	
	//Player 1 cards
	private static CardImage p1_1 = new CardImage("backofcard", 0, 0);
	private static CardImage p1_2 = new CardImage("backofcard", 0, 0);
	private static CardImage p1_3 = new CardImage("backofcard", 0, 0);
	//Player 2 cards
	private static CardImage p2_1 = new CardImage("backofcard", 0, 0);
	private static CardImage p2_2 = new CardImage("backofcard", 0, 0);
	private static CardImage p2_3 = new CardImage("backofcard", 0, 0);
	//Player 3 cards
	private static CardImage p3_1 = new CardImage("backofcard", 0, 0);
	private static CardImage p3_2 = new CardImage("backofcard", 0, 0);
	private static CardImage p3_3 = new CardImage("backofcard", 0, 0);
	//Player 4 cards
	private static CardImage p4_1 = new CardImage("backofcard", 0, 0);
	private static CardImage p4_2 = new CardImage("backofcard", 0, 0);
	private static CardImage p4_3 = new CardImage("backofcard", 0, 0);
	
	private ArrayList <CardImage> cards = new ArrayList<>();
	private CardImage[] cardsPlayed = new CardImage[4];

	Timer t, tP1, tP2, tP3, tP4;
	private boolean initialization = true;
	private boolean p1CardPlayed, p2CardPlayed, p3CardPlayed, p4CardPlayed;
	private boolean p1ReadyToRestart, p2ReadyToRestart, p3ReadyToRestart, p4ReadyToRestart;
	private boolean played1, played2, played3, played4;
	private boolean showWinnerOfRound, playerNameInitialization, gameEnded;
	private boolean set1, set2, set3;
	
	private static final String waiting = "Waiting For Players To Join Game...";
	private static final String myTurn = "It's Your Turn!";
	private static final String notMyTurn = "Wait For Your Turn";
	private static final String played = "You've Played This Round";
	private static final String roundEnded = "Round Just Ended";
	private String winner, winnerOfGame;
	private String roundPoints, totalPoints;
	private static String status = waiting;
	
	private ClientEngine engine;
	private JFrame frame;
	
	/**
	 * Adds the cards to the window and handles them when selected
	 * @param engine connection to server (client)
	 * @param frame It's own frame
	 */
	public Background2(ClientEngine engine, JFrame frame){
		
		this.engine = engine;
		this.frame = frame;
		
		cards.add(p1_1);
		cards.add(p1_2);
		cards.add(p1_3);
		cards.add(p2_1);
		cards.add(p2_2);
		cards.add(p2_3);
		cards.add(p3_1);
		cards.add(p3_2);
		cards.add(p3_3);
		cards.add(p4_1);
		cards.add(p4_2);
		cards.add(p4_3);
		
		addMouseListener(new ClickListener());
		
		p2_1.rotateImage(90, this);
		p2_2.rotateImage(90, this);
		p2_3.rotateImage(90, this);
		
		p4_1.rotateImage(-90, this);
		p4_2.rotateImage(-90, this);
		p4_3.rotateImage(-90, this);
	}
	/**
	 * Paints the window with all the images used
	 * Positions the cards
	 * Shows the round winner and the players names
	 */
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		panelWidth = this.getWidth();
		panelHeight = this.getHeight();

		int startingpointVerticalCards = panelWidth / 2 - width1 - width1 / 2 - 10;
		int startingpointHorizontalCards = panelHeight / 2 - width1 - width1 / 2 - 10;

		// sets the background
		g2.drawImage(bg, 0, 0, panelWidth, panelHeight, this);

		if (initialization) {//initialize x and y coordinates of cards
			
			for(int i = 0; i < 3; i++){
				cards.get(i).setLocation(startingpointVerticalCards + i * (width1 + 10), panelHeight - height1);
				cards.get(i).saveLocation();
			}
			for(int i = 3; i < 6; i++){
				cards.get(i).setLocation(0, startingpointHorizontalCards + (i - 3) * (width1 + 10));
				cards.get(i).saveLocation();
			}
			for(int i = 6; i < 9; i++){
				cards.get(i).setLocation(startingpointVerticalCards + (8 - i) * (width1 + 10), 0);
				cards.get(i).saveLocation();
			}
			for(int i = 9; i < 12; i++){
				cards.get(i).setLocation(panelWidth - height1, startingpointHorizontalCards + (11 - i) * (width1 + 10));
				cards.get(i).saveLocation();
			}
			initialization = false;
		}
		
		//draw cards
		for(CardImage c : cards)
			c.draw(g2, this);
		if(playerNameInitialization){
		//Draw Player1 name
		g2.setFont(new Font("Arial", Font.BOLD, 30));
		g2.setColor(Color.YELLOW);
		g2.drawString(playerName[0], panelWidth/2 - width1/2, panelHeight - height1);
		
		//Draw Player2 name
		g2.setFont(new Font("Arial", Font.BOLD, 30));
		g2.setColor(Color.YELLOW);
		g2.drawString(playerName[1], height1, panelHeight/2);		

		//Draw Player3 name
		g2.setFont(new Font("Arial", Font.BOLD, 30));
		g2.setColor(Color.YELLOW);
		g2.drawString(playerName[2], panelWidth/2 - width1/2, height1 + 30);

		//Draw Player4 name
		g2.setFont(new Font("Arial", Font.BOLD, 30));
		g2.setColor(Color.YELLOW);
		g2.drawString(playerName[3], panelWidth - 3 * width1, panelHeight/2);
		}
				
		if(showWinnerOfRound){//Displays the winner of the round
			g2.setFont(new Font("Arial", Font.BOLD, 25));
			g2.setColor(Color.RED);
			g2.drawString("Winner this round is: " + winner + " Round points: " + roundPoints, startingpointVerticalCards- height1, panelHeight/2 + 30);
		}
		
		//Draw status string
		g2.setFont(new Font("Arial", Font.BOLD, 20));
		g2.setColor(Color.BLACK);
		g2.drawString(status, 0, panelHeight -10);

		//Draw the round string
		g2.drawString("Round: " + round, panelWidth - width1, panelHeight);		
		
		if(round <= 7){// Don't show in the last hand
			//Draw the card life
			life.draw(panelWidth - width1, 0, g2, this);
			// set the deck of cards
			setDeck(0, 0, g2,this);
		}
		
		if(gameEnded){
			g2.setFont(new Font("Arial", Font.BOLD, 40));
			g2.drawString("Winner of game is: " + winnerOfGame,  startingpointVerticalCards- height1, panelHeight/2 + 30);
		}
	}
	/**
	 * Displays the deck
	 * @param x - coordinate
	 * @param y - coordinate
	 * @param g2 - graphics
	 * @param o - Image Observer
	 */
	public void setDeck(int x, int y, Graphics2D g2, ImageObserver o) {
		deck.draw(x + 6, y + 6, g2, o);
		deck.draw(x + 4, y + 4, g2, o);
		deck.draw(x + 2, y + 2, g2, o);
		deck.draw(x, y, g2, o);
	}
	/**
	 * Initializes the players with their respective names
	 * Displays the life card of this game
	 * Updates the status label
	 * 
	 * Assumes the following:
	 * player1 south position
	 * player2 west position
	 * player3 north position
	 * player4 east position
	 * @param playerName - The names of the four players
	 * @param life - the life card
	 */
	public void initializePlayersNamesAndLifeCard(String[] playerName, String life){
		
		//the creator of the game starts out as the winner
		winner = playerName[0];
		
		this.life.setImg(life);
		int k;

		// initialize players names
		for(int i = 0; i < playerName.length; i++){
			if(engine.getUser().equals(playerName[i])){	
				k = i;
				for(int j = 0; j < this.playerName.length; j++){
					this.playerName[j] = playerName[k % 4];
					System.out.println("Player: " + j + " assign: " + playerName[k % 4]);
					k++;
				}
				//break;	
			}
		}
		playerNameInitialization = true;
			
		//change status string
		if(engine.getUser().equals(winner)){
			status = myTurn;
		}
		else{
			status = notMyTurn;
		}		
		engine.hasNewCard();		
		repaint();
	}
	/**
	 * Receives the winner information and displays it at the end of each round
	 * Starts the winning animation and reset method
	 * @param winner
	 * @param roundPoints
	 */
	public void updateCurrentRoundInfo(String winner, String roundPoints){
		
		this.winner = winner;
		this.roundPoints = roundPoints;
		
		winningAnimation();
		reset();
		repaint();
	}
	/**
	 * Checks who of the four players is the winner and play their winning animation. 
	 * That is, to move the cards to their respective side.
	 * Updates the status label
	 */
	public void winningAnimation(){
		
		status = roundEnded;
		
		showWinnerOfRound = true;
		
		if(winner.equals(playerName[0])){
			p1Wins();
		}
		else if(winner.equals(playerName[1])){
			p2Wins();
		}
		else if(winner.equals(playerName[2])){
			p3Wins();
		}
		else if(winner.equals(playerName[3])){
			p4Wins();
		}
		
		repaint();		
	}
	/**
	 * Receives the winner of the game and total points gained in the game and displays it at the end
	 * Waits for 10 seconds and then closes the frame
	 * @param winnerOfGame - player that won the game
	 * @param totalPoints - total points gained in the game
	 */
	public void endOfGame(String winnerOfGame, String totalPoints){
		
		this.winnerOfGame = winnerOfGame;
		this.totalPoints = totalPoints;
		
		gameEnded = true;
		
		class Listener implements ActionListener {
			private ClientEngine engine;
			private JFrame frame;
			Listener(ClientEngine engine, JFrame frame){
				this.engine = engine;
				this.frame = frame;
			}
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				engine.removeGame();
				frame.dispose();
			}
		}
		Timer tEnd = new Timer(10000, new Listener(engine, frame));
		tEnd.start();
		
		repaint();
	}
	/**
	 * Animation that moves a card from player1 position to his side
	 * As part of the winning animation of player1
	 * @param card - card of player1 to be moved
	 */
	public void p1_toP1(final CardImage card){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.x > 0) {
					card.translate(-DISTANCE, 0);
					repaint();
				}
				else if(card.y < panelHeight){
					card.translate(0,DISTANCE);
					repaint();
				}
				else{
					tP1.stop();
					System.out.println("1toP1");
					p1ReadyToRestart = true;
				}
			}
		}
		tP1 = new Timer(TIME, new Listener());
		tP1.start();
	}
	/**
	 * Animation that moves a card from player2 position to player1 side
	 * As part of the winning animation of player1
	 * @param card - card of player2 to be moved
	 */
	public void p2_toP1(final CardImage card){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.x > 0) {
					card.translate(-DISTANCE, 0);
					repaint();
				}
				else if(card.y < panelHeight){
					card.translate(0,DISTANCE);
					repaint();
				}
				else{
					tP2.stop();
					System.out.println("2toP1");
					p2ReadyToRestart = true;
				}
			}
		}
		tP2 = new Timer(TIME, new Listener());
		tP2.start();
	}
	/**
	 * Animation that moves a card from player3 position to player1 side
	 * As part of the winning animation of player1
	 * @param card - card of player3 to be moved
	 */
	public void p3_toP1(final CardImage card){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.x > 0) {
					card.translate(-DISTANCE, 0);
					repaint();
				}
				else if(card.y < panelHeight){
					card.translate(0,DISTANCE);
					repaint();
				}
				else{
					tP3.stop();
					System.out.println("3toP1");
					p3ReadyToRestart = true;
				}
			}
		}
		tP3 = new Timer(TIME, new Listener());
		tP3.start();
	}
	/**
	 * Animation that moves a card from player4 position to player1 side
	 * As part of the winning animation of player1
	 * @param card - card of player4 to be moved
	 */
	public void p4_toP1(final CardImage card){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.x > 0) {
					card.translate(-DISTANCE, 0);
					repaint();
				}
				else if(card.y < panelHeight){
					card.translate(0,DISTANCE);
					repaint();
				}
				else{
					tP4.stop();
					System.out.println("4toP1");
					p4ReadyToRestart = true;
				}
			}
		}
		tP4 = new Timer(TIME, new Listener());
		tP4.start();
	}
	/**
	 * Animation that moves a card from player1 position to player2 side
	 * As part of the winning animation of player2
	 * @param card - card of player1 to be moved
	 */
	public void p1_toP2(final CardImage card){
		System.out.println("Entered p1_toP2 method");

		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.y > 0) {
					card.translate(0, -DISTANCE);
					repaint();
				}
				else if(card.x > - height1){
					card.translate(-DISTANCE,0);
					repaint();
				}
				else{
					tP1.stop();
					System.out.println("stopped");
					p1ReadyToRestart = true;
				}
			}
		}
		tP1 = new Timer(TIME, new Listener());
		tP1.start();
	}
	/**
	 * Animation that moves a card from player2 position to his side
	 * As part of the winning animation of player2
	 * @param card - card of player2 to be moved
	 */
	public void p2_toP2(final CardImage card){
		System.out.println("Entered p2_toP2 method");
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.y > 0) {
					card.translate(0, -DISTANCE);
					repaint();
				}
				else if(card.x > - height1){
					card.translate(-DISTANCE,0);
					repaint();
				}
				else{
					tP2.stop();
					System.out.println("stopped");
					p2ReadyToRestart = true;
				}
			}
		}
		tP2 = new Timer(TIME, new Listener());
		tP2.start();
	}
	/**
	 * Animation that moves a card from player3 position to player2 side
	 * As part of the winning animation of player2
	 * @param card - card of player3 to be moved
	 */
	public void p3_toP2(final CardImage card){
		System.out.println("Entered p3_toP2 method");

		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.y > 0) {
					card.translate(0, -DISTANCE);
					repaint();
				}
				else if(card.x > - height1){
					card.translate(-DISTANCE,0);
					repaint();
				}
				else{
					tP3.stop();
					System.out.println("stopped");
					p3ReadyToRestart = true;
				}
			}
		}
		tP3 = new Timer(TIME, new Listener());
		tP3.start();
	}
	/**
	 * Animation that moves a card from player4 position to player2 side
	 * As part of the winning animation of player2
	 * @param card - card of player4 to be moved
	 */
	public void p4_toP2(final CardImage card){
		System.out.println("Entered p4_toP2 method");

		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.y > 0) {
					card.translate(0, -DISTANCE);
					repaint();
				}
				else if(card.x > - height1){
					card.translate(-DISTANCE,0);
					repaint();
				}
				else{
					tP4.stop();
					System.out.println("stopped");
					p4ReadyToRestart = true;
				}
			}
		}
		tP4 = new Timer(TIME, new Listener());
		tP4.start();
	}
	/**
	 * Animation that moves a card from player1 position to player3 side
	 * As part of the winning animation of player3
	 * @param card - card of player 1 to be moved
	 */
	public void p1_toP3(final CardImage card){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.x < panelWidth - width1) {
					card.translate(DISTANCE, 0);
					repaint();
				}
				else if(card.y > - height1){
					card.translate(0,-DISTANCE);
					repaint();
				}
				else{
					tP1.stop();
					System.out.println("stopped");
					p1ReadyToRestart = true;
				}
			}
		}
		tP1 = new Timer(TIME, new Listener());
		tP1.start();
	}
	/**
	 * Animation that moves a card from player2 position to player3 side
	 * As part of the winning animation of player3
	 * @param card - card of player2 to be moved
	 */
	public void p2_toP3(final CardImage card){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.x < panelWidth - width1) {
					card.translate(DISTANCE, 0);
					repaint();
				}
				else if(card.y > - height1){
					card.translate(0,-DISTANCE);
					repaint();
				}
				else{
					tP2.stop();
					System.out.println("stopped");
					p2ReadyToRestart = true;
				}
			}
		}
		tP2 = new Timer(TIME, new Listener());
		tP2.start();
	}
	/**
	 * Animation that moves a card from player3 position to his side
	 * As part of the winning animation of player3
	 * @param card - card of player3 to be moved
	 */
	public void p3_toP3(final CardImage card){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.x < panelWidth - width1) {
					card.translate(DISTANCE, 0);
					repaint();
				}
				else if(card.y > - height1){
					card.translate(0,-DISTANCE);
					repaint();
				}
				else{
					tP3.stop();
					System.out.println("stopped");
					p3ReadyToRestart = true;
				}
			}
		}
		tP3 = new Timer(TIME, new Listener());
		tP3.start();
	}
	/**
	 * Animation that moves a card from player4 position to player3 side
	 * As part of the winning animation of player3
	 * @param card - card of player4 to be moved
	 */
	public void p4_toP3(final CardImage card){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.x < panelWidth - width1) {
					card.translate(DISTANCE, 0);
					repaint();
				}
				else if(card.y > - height1){
					card.translate(0,-DISTANCE);
					repaint();
				}
				else{
					tP4.stop();
					System.out.println("stopped");
					p4ReadyToRestart = true;
				}
			}
		}
		tP4 = new Timer(TIME, new Listener());
		tP4.start();
	}
	/**
	 * Animation that moves a card from player1 position to player4 side
	 * As part of the winning animation of player4
	 * @param card - card of player1 to be moved
	 */
	public void p1_toP4(final CardImage card){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.y < panelHeight - width1) {
					card.translate(0, DISTANCE);
					repaint();
				}
				else if(card.x < panelWidth){
					card.translate(DISTANCE,0);
					repaint();
				}
				else{
					tP1.stop();
					System.out.println("stopped");
					p1ReadyToRestart = true;
				}
			}
		}
		tP1 = new Timer(TIME, new Listener());
		tP1.start();
	}
	/**
	 * Animation that moves a card from player2 position to player4 side
	 * As part of the winning animation of player4
	 * @param card - card of player2 to be moved
	 */
	public void p2_toP4(final CardImage card){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.y < panelHeight - width1) {
					card.translate(0, DISTANCE);
					repaint();
				}
				else if(card.x < panelWidth){
					card.translate(DISTANCE,0);
					repaint();
				}
				else{
					tP2.stop();
					System.out.println("stopped");
					p2ReadyToRestart = true;
				}
			}
		}
		tP2 = new Timer(TIME, new Listener());
		tP2.start();
	}
	/**
	 * Animation that moves a card from player3 position to player4 side
	 * As part of the winning animation of player4
	 * @param card - card of player3 to be moved
	 */
	public void p3_toP4(final CardImage card){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.y < panelHeight - width1) {
					card.translate(0, DISTANCE);
					repaint();
				}
				else if(card.x < panelWidth){
					card.translate(DISTANCE,0);
					repaint();
				}
				else{
					tP3.stop();
					System.out.println("stopped");
					p3ReadyToRestart = true;
				}
			}
		}
		tP3 = new Timer(TIME, new Listener());
		tP3.start();
	}
	/**
	 * Animation that moves a card from player4 position to his side
	 * As part of the winning animation of player4
	 * @param card - card of player4 to be moved
	 */
	public void p4_toP4(final CardImage card){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.y < panelHeight - width1) {
					card.translate(0, DISTANCE);
					repaint();
				}
				else if(card.x < panelWidth){
					card.translate(DISTANCE,0);
					repaint();
				}
				else{
					tP4.stop();
					p4ReadyToRestart = true;
				}
			}
		}
		tP4 = new Timer(TIME, new Listener());
		tP4.start();
	}
	/**
	 * Animation that moves one of player1 cards to the center (up)
	 * @param card - card of player1 to be moved
	 */
	public void moveUp(final CardImage card){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.getY() > panelHeight - (2 * height1)) {
					card.translate(0,-DISTANCE);
					repaint();
				} else{
					tP1.stop();
					p1CardPlayed = true;
				}
			}
		}

		tP1 = new Timer(TIME, new Listener());
		tP1.start();
	}
	/**
	 * Animation that moves one of player2 cards to the center (right)
	 * @param card - card of player2 to be moved
	 * @param faceCard - the face of the card to show when it's flipped
	 */
	public void moveRight(final CardImage card, final String faceCard){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.x < height1) {
					card.translate(DISTANCE, 0);
					repaint();
				} else{
					tP2.stop();
					
					card.setImg(faceCard);
					card.rotateImage(90, null);
					repaint();
					p2CardPlayed = true;
				}
			}
		}

		tP2 = new Timer(TIME, new Listener());
		tP2.start();
		
	}
	/**
	 * Animation that moves one of player3 cards to the center (down)
	 * @param card - card of player3 to be moved
	 * @param faceCard - the face of the card to show when it's flipped
	 */
	public void moveDown(final CardImage card, final String faceCard){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.y < height1) {
					card.translate(0, DISTANCE);
					repaint();
				} else{
					tP3.stop();
					card.setImg(faceCard);
					card.rotateImage(180, null);
					repaint();
					p3CardPlayed = true;
				}
			}
		}
		tP3 = new Timer(TIME, new Listener());
		tP3.start();
	}
	/**
	 * Animation that moves one of player4 cards to the center (left)
	 * Updated the status label
	 * @param card - card of player4 to be moved
	 * @param faceCard - the face of the card to show when it's flipped
	 */
	public void moveLeft(final CardImage card, final String faceCard){
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (card.x > panelWidth - (2 * height1)) {
					card.translate(-DISTANCE, 0);
					repaint();
				} else{
					tP4.stop();
					card.setImg(faceCard);
					card.rotateImage(-90, null);
					repaint();
					status = myTurn;
					p4CardPlayed = true;
				}
			}
		}

		tP4 = new Timer(TIME, new Listener());
		tP4.start();
	}
	/**
	 * Animations that moves all played cards to player1 side
	 */
	public void p1Wins(){

		p1CardPlayed = p2CardPlayed = p3CardPlayed = p4CardPlayed = false;
		System.out.println("p1won");
		p1_toP1(cardsPlayed[0]);
		cardsPlayed[1].rotateImage(90, this);
		p2_toP1(cardsPlayed[1]);
		p3_toP1(cardsPlayed[2]);
		cardsPlayed[3].rotateImage(90, this);
		p4_toP1(cardsPlayed[3]);
	}
	/**
	 * Animations that moves all played cards to player2 side
	 */
	public void p2Wins(){
		
		p1CardPlayed = p2CardPlayed = p3CardPlayed = p4CardPlayed = false;
		System.out.println("p2won");
		cardsPlayed[0].rotateImage(90, this);
		p1_toP2(cardsPlayed[0]);		
		p2_toP2(cardsPlayed[1]);
		cardsPlayed[2].rotateImage(90, this);
		p3_toP2(cardsPlayed[2]);
		p4_toP2(cardsPlayed[3]);
	}
	/**
	 * Animations that moves all played cards to player3 side
	 */
	public void p3Wins(){
		
		p1CardPlayed = p2CardPlayed = p3CardPlayed = p4CardPlayed = false;
		System.out.println("p3won");
		p1_toP3(cardsPlayed[0]);
		cardsPlayed[1].rotateImage(90, this);
		p2_toP3(cardsPlayed[1]);
		p3_toP3(cardsPlayed[2]);
		cardsPlayed[3].rotateImage(90, this);
		p4_toP3(cardsPlayed[3]);
	}
	/**
	 * Animations that moves all played cards to player4 side
	 */
	public void p4Wins(){
		
		p1CardPlayed = p2CardPlayed = p3CardPlayed = p4CardPlayed = false;
		System.out.println("p4won");
		cardsPlayed[0].rotateImage(90, this);
		p1_toP4(cardsPlayed[0]);		
		p2_toP4(cardsPlayed[1]);
		cardsPlayed[2].rotateImage(90, this);
		p3_toP4(cardsPlayed[2]);
		p4_toP4(cardsPlayed[3]);
	}
	/**
	 * Resets the cards to their original position (deal the cards)
	 * Asks the server for the new cards for the player1 if not in the last hand (round 8)
	 * Asks the server for the winner's information if it's the last round
	 * Updates the status label
	 */
	public void reset(){
		
		class Listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (p1ReadyToRestart && p2ReadyToRestart && p3ReadyToRestart && p4ReadyToRestart) {
					if(round < TOTAL_ROUNDS - 2){//last hand
						round++;

						System.out.println("nextRound");
						cardsPlayed[0].resetLocation();
						cardsPlayed[1].resetLocation();
						cardsPlayed[2].resetLocation();
						cardsPlayed[3].resetLocation();
					}
					else if(round < TOTAL_ROUNDS - 1){
						round++;
						p2_1 = p2_2;
						p3_1 = p3_2;
						p4_1 = p4_2;
					}
					else if(round < TOTAL_ROUNDS){
						round++;
						p2_1 = p2_3;
						p3_1 = p3_3;
						p4_1 = p4_3;
					}
					else if(round == TOTAL_ROUNDS){
						engine.getWinnerOfGame();
					}
					showWinnerOfRound = false;
					p1ReadyToRestart = p2ReadyToRestart = p3ReadyToRestart = p4ReadyToRestart = false;
					p1CardPlayed = p2CardPlayed = p3CardPlayed = p4CardPlayed = false;
					played1 = played2 = played3 = played4 = false;
					set1 = set2 = set3 = false;
					
					//Change status string
					if(engine.getUser().equals(winner)){
						status = myTurn;
					}
					else{
						status = notMyTurn;
					}
					repaint();
					t.stop();
					engine.hasNewCard();
				}
			}
		}
		t = new Timer(5, new Listener());
		t.start();
	}
	/**
	 * Sets player1 cards
	 * @param card - player1 hand
	 */
	public void setLocalPlayerCards(String[] card){	
		
		p1_1.setImg(card[0]);
		p1_2.setImg(card[1]);
		p1_3.setImg(card[2]);	
		
		repaint();
	}
	/**
	 * Calls the moveRight method with the chosen card of player2
	 * @param card - player2 chosen card
	 */
	public void setPlayer2Card(String card){
		played2 = true;
		moveRight(p2_1, card);
		Array.set(cardsPlayed, 1,p2_1);
		System.out.println("p2 card moved");
	}
	/**
	 * Calls the moveRight method with the chosen card of player3
	 * @param card - player3 chosen card
	 */
	public void setPlayer3Card(String card){
		played3 = true;
		moveDown(p3_1, card);
		Array.set(cardsPlayed, 2,p3_1);
		System.out.println("p3 card moved");
	}
	/**
	 * Calls the moveRight method with the chosen card of player4
	 * @param card - player4 chosen card
	 */
	public void setPlayer4Card(String card){
		played4 = true;
		moveLeft(p4_1, card);
		Array.set(cardsPlayed, 3,p4_1);
		System.out.println("p4 card moved");
	}
	/**
	 * Receives the chosen cards of all players and calls the setPlayer n Card 
	 * Depending on the player that has chosen a card already
	 * @param chosenCards
	 */
	public void playOponentChosenCards(String chosenCards){
		System.out.println(" Entered method playOponentChosenCards of background2");
		String[] info = chosenCards.split(",");
		
		for(int i = 0; i < info.length; i++){
			String player = info[i].split("-")[0];
			System.out.println("player: " + player);
			String card = info[i].split("-")[1];
			System.out.println("card: " + card);
			for(int j = 0; j < playerName.length; j++){
				if(player.equals(playerName[j])){
					if(!set1){
						if(j == 1){
							setPlayer2Card(card);
							set1 = true;
						}
					}
					if(!set2){
						if(j == 2){
							setPlayer3Card(card);
							set2 = true;
						}
					}
					if(!set3){
						if(j == 3){
							setPlayer4Card(card);
							set3 = true;
						}
					}
				}		
			}
		}	
	}
	/**
	 * Listener that will react to the mouse clicks on the panel 
	 * @author Michelle M Ortiz & Mario Orbegoso
	 *
	 */
	public class ClickListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		/**
		 * Selects card the player1 has clicked and calls the moveUp method with that card
		 */
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

			int x = arg0.getX();
			int y = arg0.getY();
			
			if(!played1 && status.equals(myTurn)){
				for(int i = 0; i < 3; i++){
					if(cards.get(i).contains(x, y)){
						status = played;
						Array.set(cardsPlayed, 0,cards.get(i));
						moveUp(cards.get(i));
						System.out.println("p1 card moved");
						engine.chooseCard(cards.get(i).toString());
						played1 = true;
						break;
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
}
