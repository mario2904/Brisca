package views;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class creates the background image for the other frames
 * @author Mario Orbegoso
 *
 */
public class JPanelWithBackground extends JPanel{
	Image background;
	
	public JPanelWithBackground(String fileName) {
		this.background = new ImageIcon(fileName).getImage();	
	}
	public void paintComponent(Graphics g){
		g.drawImage(background, 0, 0, this);
	}
}
