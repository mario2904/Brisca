package views;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import controller.ClientEngine;

/**
 * This class is a frame to show the players the available games with the specified preferences
 * @author Mario Orbegoso
 *
 */
public class AvailableGames extends JFrame{
	private Background3 MainPanel;
	private static JList<String> List;
	private JScrollPane scroll;
	private Image background;
	private JPanel UserPanel;
	private JPanel panel;
	private JButton button;
	private ClientEngine engine;
	private static DefaultListModel<String> listModel;
	

	/**
	 * Constructor that will draw the frame that will contain the available games
	 * @param engine - connection to server (client)
	 */
	public AvailableGames(ClientEngine engine){
		super("Available Games");

		
		setSize(400,280);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		this.engine = engine;
		MainPanel=new Background3();
		MainPanel.setLayout(new BorderLayout());
		super.add(MainPanel);
	
		UserPanel= new JPanel();
		MainPanel.add(UserPanel);
		
		UserPanel.setOpaque(false);
		List = new JList<String>();
		listModel = new DefaultListModel<String>();
	
		List.setModel(listModel);
		scroll = new JScrollPane(List);
		UserPanel.add(scroll);
	

		scroll.setPreferredSize(new Dimension(300, 200));
		scroll.setLocation(150, 100);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		button = new JButton("Join Game");
		button.addActionListener(new joinGameListener());
		UserPanel.add(button);

		class Listener1 implements ActionListener {
			private ClientEngine engine;
			Listener1(ClientEngine engine){
				this.engine = engine;
			}
			@Override
			public void actionPerformed(ActionEvent arg0) {

				try{
					this.engine.hasNewGameCreated();
				}catch(NullPointerException e){}
			}
		}
		Timer t1 = new Timer(1000, new Listener1(engine));
		t1.start();
		
		setVisible(true);

		
	}
	/**
	 * Method to add game to the list of games available
	 * @param game
	 */
	public static void addGame(String game) {
		listModel.addElement(game);
		List.setModel(listModel);
	}
	/**
	 * Method to clear the game list for it to be updated
	 */
	public static void clearGameList() {
		listModel = new DefaultListModel<String>();
	}
	/**
	 * Listener class for the button used to select the game and join it
	 * @author  Michelle M Ortiz & Mario Orbegoso
	 *
	 */
	public class joinGameListener implements ActionListener{

		/**
		 * Method that tells the action received what to do
		 * In this case the button will select the game and join
		 */
		public void actionPerformed(ActionEvent e) {
			String game = (String) List.getSelectedValue();

			if(game != null){
				dispose();
				engine.joinGame(game);
				new GameRoom(engine);
			}
		}	
	}
}
