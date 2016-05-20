package views;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class creates the background image for the main Brisca frame
 * @author Mario Orbegoso
 *
 */
public class Background extends JPanel{

	Image bg = new ImageIcon("Images/BACK.jpg").getImage();

	/**
	 * Paints the background image in specified dimensions (the whole panel)
	 */
	public void paintComponent(Graphics g){
		g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
	}

}