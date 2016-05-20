package views;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;;
/**
 * This class creates a window with the instructions of the game
 * @author Mario Orbegoso
 *
 */
public class HowTo extends JFrame {
	
	private JTextArea pane;
	/**
	 * Instructions for playing on a different window
	 */
	public HowTo(){
		
			super("How to");
			setSize(600, 700);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
			pane =new JTextArea("\n Rules of the Game\n"+"\r\n Each Player has 3 cards\n"+
			"\r\n On every turn each player will play one card\n"+
			"\r\n The player that plays the highest trump card will win the hand\n"+
			"If a trump has been played, the highest trump wins the trick;\n"+ 
			"otherwise, it's the highest card of the suit led.\n"+
			"Before leading the next hand, the trick winner adds the top card from the deck to their hand\n"+
			"and every other player then does the same. When the deck is gone\n"+
			"(the final card picked up being the one which defined trumps) and all the cards are played,\n"+
			"the winning player or partnership is the one whose tricks contain the most points.");
			add(pane);
			setVisible(true);
	}
	public class HowToListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {

			dispose();
		}
	}
}

