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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.ClientEngine;
/**
 * This class creates the window for the created games
 * @author Mario Orbegoso
 */
public class CreatedGame extends JFrame{
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
	 * Creates the frame of the games created by the users
	 * @param engine
	 */
	public CreatedGame(ClientEngine engine){
		super("Created Game: " + engine.getUser());

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
		List =new JList<String>();
		listModel = new DefaultListModel<String>();

		List.setModel(listModel);
		scroll = new JScrollPane(List);
		UserPanel.add(scroll);
	

		scroll.setPreferredSize(new Dimension(300, 200));
		scroll.setLocation(150, 100);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		button = new JButton("Play");
		button.addActionListener(new joinGameListener());
		UserPanel.add(button);
		
		setVisible(true);

	}
	/**
	 * Adds new member to the list of the game members
	 * @param member
	 */
	public static void addMember(String member){
		listModel.addElement(member);
		List.setModel(listModel);
		System.out.println(member);
	}
	/**
	 * Clears the member list to be updated
	 */
	public static void clearMemberList(){
		listModel = new DefaultListModel<String>();
	}
	
	public class joinGameListener implements ActionListener{
		/**
		 * Checks if the user that created the game is ready to start playing
		 * Sends message to the server to start the game
		 */
		public void actionPerformed(ActionEvent e) {

			if(listModel.size() != 4){
				JOptionPane.showMessageDialog(null, "Need to have four players to strart your game");
			}
			else{			
			dispose();
			engine.startGame();
			}
		}
	}
}

