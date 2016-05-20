import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.ClientEngine;
import views.AvailableGames;
import views.ChatPanel;
import views.HowTo;
import views.JPanelWithBackground;
import views.Preferences;
import views.Stats;
/**
 * This is the main class
 * Once a player has registered he can create a game, view available games, view his playing status and get help info from here
 * @author Mario Orbegoso
 *
 */
public class BriscaMain extends JFrame{
	
	private JPanel centerPanel;
	private JPanel mainPanel;
	private JButton newGame, availableGames, howTo, myStats;
	private static ClientEngine engine = new ClientEngine();
	
	private static class ChatWindowListener implements WindowListener {

		private ClientEngine engine;
		/**
		 * Constructor of the chat window listener
		 * @param engine - will receive the engine for the connection of the client
		 */
		public ChatWindowListener(ClientEngine engine) {
			this.engine = engine;
		}
		
		@Override
		public void windowActivated(WindowEvent e) {}

		@Override
		public void windowClosed(WindowEvent e) {}

		/**
		 * Method for the action of closing window
		 * This method will remove the user from the game
		 */
		@Override
		public void windowClosing(WindowEvent e) {
			engine.removeUser();
		}
		
		@Override
		public void windowDeactivated(WindowEvent e) {}

		@Override
		public void windowDeiconified(WindowEvent e) {}

		@Override
		public void windowIconified(WindowEvent e) {}

		/**
		 * Method for the action taken when opening a window
		 * This method will activate the chat panel
		 */
		@Override
		public void windowOpened(WindowEvent e) {
			ChatPanel.setFocusOnMessageField();
		}		
	}

	/**
	 * This will create a frame to that will be the welcoming/lobby frame
	 * @param newUser - receives the new user 
	 * @param password - receives password from user
	 */
	public BriscaMain(String newUser, String password){
		
		super("Brisca: "+ newUser);
		setSize(600,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); 						// Set Location to Center of Screen
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		centerPanel = new JPanelWithBackground("Images/BACK.jpg");
		centerPanel.setLayout(new GridBagLayout());
		centerPanel.setOpaque(false);
		
		//Buttons
		newGame = new JButton("New Game");
		newGame.addActionListener(new newGameListener());
		availableGames = new JButton("Available Games");
		availableGames.addActionListener(new availableGamesListener());
		howTo = new JButton("How To");
		howTo.addActionListener(new HowToListener());
		myStats = new JButton("My Stats");
		myStats.addActionListener(new MyStatsListener());
		
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridy = 0;
		c.gridx = 0;
		c.insets = new Insets(10,0,10,0);
		
		c.gridy = 1;
		centerPanel.add(newGame, c);
		
		c.gridy = 2;
		centerPanel.add(availableGames, c);
		
		c.gridy = 3;
		centerPanel.add(myStats, c);
		
		c.gridy = 4;
		centerPanel.add(howTo, c);
		
		ChatPanel ch = new ChatPanel(engine);
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(ch, BorderLayout.EAST);
		engine.enterRoom(newUser, password);
		
		class Listener implements ActionListener {
			private ClientEngine engine;
			Listener(ClientEngine engine){
				this.engine = engine;
			}
			/**
			 * Method for listening and updating info 
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {

				this.engine.hasNewUserArrived(); 			// Ask if new user has arrived
				this.engine.hasNewMessageArrived();			// Ask if new message has arrived 
				this.engine.hasNewMember(); 				// Ask if new member has arrived to the game
				System.out.println("stupidity");
			}
		}
		Timer t1 = new Timer(1000, new Listener(engine));
		t1.start();
		
		add(mainPanel);
		addWindowListener(new ChatWindowListener(engine));
		setVisible(true);
		
	}

	public class newGameListener implements ActionListener{
		/**
		 * Method that will check if the user wants to create a new game and will open the preferences window
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new Preferences(engine);
		}
	}	
	public class availableGamesListener implements ActionListener{

		/**
		 * Method that will check if a player wants to see the available games
		 * Will open the available games window and will update the game list
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			new AvailableGames(engine);
			engine.newWindowGameList();
		}
	}
	public class HowToListener implements ActionListener{
		/**
		 * Method that will check if a user wants to know how to play
		 * Will open the How to window
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new HowTo();
		}
	}
	/**
	 * Method that will check if a user wants to know his gaming status
	 * Will open the Stats window
	 */
	public class MyStatsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new Stats(engine);
			engine.getStats();
		}
	}
	
	/**
	 * Main class
	 * Will open the connection between the user and the game (GUI)
	 * @param args
	 */
	public static void main(String[] args) {
		
		String newUser = "";
		String password = "";
		do {
			newUser = JOptionPane.showInputDialog("Enter username:");
		}
		while (newUser == null || newUser.equals(""));
		do {
			password = JOptionPane.showInputDialog("Enter password:");
		}
		while (password == null || password.equals(""));
		
		new BriscaMain(newUser, password);
		
	}
}