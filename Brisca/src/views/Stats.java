package views;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.ClientEngine;
import views.JPanelWithBackground;

/**
 * This class is a frame to show the players their gaming status
 * @author Mario Orbegoso
 *
 */
public class Stats extends JFrame{
	private JPanel MainPanel;
	private static JList<String> List;
	private JScrollPane scroll;
	private Image background;
	private JPanel UserPanel;
	private JPanel panel;
	private JButton button;
	private ClientEngine engine;
	private static DefaultListModel<String> listModel;
	
	/**
	 * Constructor that will draw the frame that will contain the stats
	 * @param engine - connection to server (client)
	 */
	public Stats(ClientEngine engine){
		super("Stats: " + engine.getUser());
		
		setSize(400,280);
		setLocationRelativeTo(null); 						// Set Location to Center of Screen
		
		this.engine = engine;
		MainPanel = new JPanelWithBackground("Images/back2.jpg");
		MainPanel.setLayout(new BorderLayout());
		super.add(MainPanel);
	
		UserPanel = new JPanel();
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
		
		setVisible(true);

	}
	/**
	 * Method to add info to the list of gaming status
	 * @param info information to be added to the gaming status
	 */
	public static void addStats(String info){
		listModel.addElement(info);
		List.setModel(listModel);
		System.out.println(info);
	}

	/**
	 * Method to clear the gaming status list for it to be updated
	 */
	public static void clearStatsList(){
		listModel = new DefaultListModel<String>();
	}
}
