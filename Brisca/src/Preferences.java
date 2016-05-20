import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
/**
 * This class opens a new window for selecting the type of game the user wants to create
 * @author Michelle M Ortiz & Mario Orbegoso
 */
public class Preferences extends JFrame{

	private class GamePanelListener implements ActionListener, KeyListener {
		/**
		 * After preferences selected the game will be created and the game room will open
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			new GameRoom(engine);
			new CreatedGame(engine);
			engine.createGame(getPreferences());
			dispose();
			
		}
		/**
		 * After preferences selected the game will be created and the game room will open
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				new GameRoom(engine);
				new CreatedGame(engine);
				engine.createGame(getPreferences());
				dispose();
				
			}
		}
		/**
		 * Default KeyListener Method
		 */
		@Override
		public void keyReleased(KeyEvent e) {}
		/**
		 * Default KeyListener Method
		 */
		@Override
		public void keyTyped(KeyEvent e) {}
	}

	private JRadioButton blackHand;
	private	JRadioButton trump;
	private JRadioButton timer;
	private JRadioButton fold;
	private JRadioButton single;
	private JRadioButton tournament;
	private JRadioButton score;
	private JRadioButton defaultSettings;
	private JPanel panel;
	private JButton createGame;
	private ClientEngine engine;
	/**
	 * Creates the window for selecting preferences
	 * @param engine
	 */
	public Preferences(ClientEngine engine){

		super("Preferences");
		setSize(600,400);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.engine = engine;
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel=new JPanel();
		super.add(panel);
		panel.setLayout(new GridLayout(8,1));
		add(panel);
		blackHand =new JRadioButton("Enable Black Hand");
		trump = new JRadioButton("Enable Change of TrumpCard");
		timer = new JRadioButton("Enable Timer");
		fold =new JRadioButton("Enable Fold");
		single =new JRadioButton("Single Play");
		tournament =new JRadioButton("Tournament");
		score =new JRadioButton("Show Scores");
		defaultSettings =new JRadioButton("Set Default Settings");
		createGame = new JButton("Create Game");
		createGame.addActionListener(new GamePanelListener());
		panel.add(blackHand);
		panel.add(trump);
		panel.add(timer);
		panel.add(fold);
		panel.add(single);
		panel.add(tournament);
		panel.add(score);
		panel.add(defaultSettings);
		panel.add(createGame);
		setVisible(true);
	}
	/**
	 * Gets the preferences selected 
	 * @return preferences of type string
	 */
	public String getPreferences(){
		String preferences = "";

		preferences += engine.getUser();

		if(blackHand.isSelected())
			preferences += "|Black Hand Enabled";
		if(trump.isSelected())
			preferences += "|Change of TrumpCard Enabled";
		if(timer.isSelected())
			preferences += "|Timer Enabled";
		if(fold.isSelected())
			preferences += "|Fold Enabled";
		if(single.isSelected())
			preferences += "|Single Play";
		if(tournament.isSelected())
			preferences += "|Tournament";
		if(score.isSelected())
			preferences += "|View Score";
		if(defaultSettings.isSelected())
			preferences += "|Default Settings";

		return preferences;
	}

}

