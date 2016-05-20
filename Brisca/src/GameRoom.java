import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

/**
 * This class creates the frame containing the game graphics (animations)
 * @author Michelle M Ortiz & Mario Orbegoso
 *
 */
public class GameRoom extends JFrame{
	private static Background2 mainPanel;
	private JMenuBar menuBar;
	private JMenu menu, menu1, menu2;
	private JMenuItem item, item1, item2, item3;
	private ClientEngine engine;
	private static String chosenCards;
	private static String[] hand = new String[3];
	private static String[] membersNames = new String[4];
	private static String winner, points, life, winnerOfGame, totalPoints;
	private static boolean initialization;
	
	/**
	 * Method that updates the cards of the local player
	 * Calls the setLocalPlayerCards method of Background2 with this info
	 * @param cards the hand of the local player
	 */
	public static void addCard(String cards){
		System.out.println(" addCard method  -  GameRoom");
		System.out.println(cards);
		
		for (int i = 0; i < cards.split(",").length; i++)
			hand[i] = cards.split(",")[i];
		
		mainPanel.setLocalPlayerCards(hand);
	}
	/**
	 * Receives the winner of the game and total points
	 * Calls the endOfGame method of Background2 with this info
	 * @param winnerAndTotalPoints
	 */
	public static void winnerOfGame(String winnerAndTotalPoints){
		
		winnerOfGame = winnerAndTotalPoints.split("-")[0];
		totalPoints = winnerAndTotalPoints.split("-")[1];

		mainPanel.endOfGame(winnerOfGame, totalPoints);
	}
	/**
	 * Receives the winner of the round and round points
	 * Calls the updateCurrentRoundInfo method of Background2 with this info
	 * @param currentRoundInfo  receives the winner and the points in type string
	 */
	public static void addCurrentRoundInfo(String currentRoundInfo){
		
		winner = currentRoundInfo.split("-")[0];
		points = currentRoundInfo.split("-")[1];			
		mainPanel.updateCurrentRoundInfo(winner, points);
	}
	/**
	 * Method that initializes game with the names of the members and the card life
	 * calls the method initializePlayersNamesAndLifeCard of background2 with this info
	 * @param namesAndLifeCard names of the players and the life card of type string
	 */
	public static void initializeGame(String namesAndLifeCard){
		initialization = true;
		
		life = namesAndLifeCard.split("-")[1];
		String names = namesAndLifeCard.split("-")[0];
		
		membersNames = names.split(",");		
		mainPanel.initializePlayersNamesAndLifeCard(membersNames, life);
		
	}
	/**
	 * Adds chosen cards to the list (ARRAY)
	 * call the method playOponentChosenCards of Background2 with this array
	 * @param cards - receives the names of the cards chosen of type string
	 */
	public static void addChosenCards(String cards){
		
		chosenCards = cards;
		mainPanel.playOponentChosenCards( chosenCards);
	}
	/**
	 * Creates the frame that will display the cards (hold an instance of Background2)
	 * @param engine
	 */
	public GameRoom(ClientEngine engine){
				
		super("Brisca: " + engine.getUser());
		
		this.engine = engine;
		setSize(1000,700);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		menu1 = new JMenu("Options");
		menu2 = new JMenu("Help");
		super.setJMenuBar(menuBar);

		menuBar.add(menu);
		menuBar.add(menu1);
		menuBar.add(menu2);

		item = new JMenuItem("Exit");
		item1 = new JMenuItem("Mute");
		item2 = new JMenuItem("Enable Chat");
		item3 = new JMenuItem("How to Play");
		item3.addActionListener(new HowToListener());
		
		menu.add(item);
		menu1.add(item1);
		menu1.add(item2);
		menu2.add(item3);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		mainPanel = new Background2(this.engine, this);
		
		class Listener implements ActionListener {
			private ClientEngine engine;
			Listener(ClientEngine engine){
				this.engine = engine;

			}
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if(!initialization){
					engine.hasGameStarted();
				}
				engine.hasNewCardChosen();
				engine.hasNewCurrentRoundInfo();
			}
		}
		Timer t = new Timer(1000, new Listener(engine));
		t.start();
		
		mainPanel.setLayout(new BorderLayout());
		add(mainPanel);
		setVisible(true);

	}
	
	public class HowToListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			new HowTo();
		}
	}
}
