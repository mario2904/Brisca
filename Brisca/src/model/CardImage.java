package model;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

/**
 * This class handles the image of the cards
 * Will set the specific location for each one
 * Specific card image
 * And will be handle the animation of the game (draws, repaints and rotates cards)
 * @author Mario Orbegoso
 *
 */
public class CardImage extends Rectangle {

	private Image img, fixedImg;
	private int width,height;
	private int fixedX, fixedY, fixedWidth,	fixedHeight;
	
	private String nameOfCard;
	
	/**
	 * Constructor of each card image of the game
	 * @param img - receives the according image of each card
	 * @param x - position of the x coordinate for the placement of the card
	 * @param y - position of the y coordinate for the placement of the card
	 */
	public CardImage(String img, int x, int y){
		nameOfCard = img;
		this.img = new ImageIcon("Images/" + img + ".jpg").getImage();
		ImageIcon icon = new ImageIcon(this.img);
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		
		super.setBounds(x, y, width, height);
	}
	/**
	 * Will set a fixed location for each card
	 */
	public void saveLocation(){
		fixedX = this.x;
		fixedY = this.y;
		fixedWidth = width;
		fixedHeight = height;
		fixedImg = img;
		
	}
	/**
	 * Overrides the toString method to only receive the name of the card
	 */
	@Override
	public String toString(){
		return nameOfCard;
	}
	/**
	 * Method to reset the location of cards
	 * Sets new x and y coordinates
	 */
	public void resetLocation(){
		this.img = fixedImg;
		this.setSize(fixedWidth, fixedHeight);
		this.setLocation(fixedX, fixedY);
	}
	/**
	 * Method that gets the card image
	 * @return according image of type image
	 */
	public Image getImg(){
		return this.img;
	}
	/**
	 * Method that set the card image
	 * @param img - receives the name of the image
	 */
	public void setImg(String img){
		nameOfCard = img;
		
		ImageIcon temp = new ImageIcon("Images/" + img + ".jpg");
		height = temp.getIconHeight();
		width = temp.getIconWidth();
		this.img = temp.getImage();
		
		this.setSize(width,height);
		
	}
	/**
	 * Method for drawing the card image
	 * 
	 */
	public void draw(Graphics2D g2, ImageObserver o){			
		g2.drawImage(this.img, this.x, this.y, o);
		g2.draw(this);
	}	
	/**
	 * Method for drawing card in new location
	 * @param x - new x coordinate
	 * @param y - new y coordinate
	 */
	public void draw(int x, int y, Graphics2D g2, ImageObserver o){	
		
		this.setLocation(x, y);
		
		g2.drawImage(this.img, this.x, this.y, o);
		g2.draw(this);
	}
	/**
	 * Method for rotating card image
	 * @param degrees - receives the degrees to be rotated to (90,180,270 or 360 degrees)
	 * @param o
	 */
	public void rotateImage(double degrees, ImageObserver o){
		
		int newWidth;
		int newHeight;
		boolean changedOrientation = false;
		
		if(degrees == 90 || degrees == -90 || degrees == 270){// changes the rectangle 
			newWidth = height;
			newHeight = width;
			changedOrientation = true;		
		}
		else{ // doesn't change
			newWidth = width; 
			newHeight = height;
		}
		
		BufferedImage blankCanvas = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) blankCanvas.getGraphics();

		if(changedOrientation)
			g2.translate(height/2 - width/2, -(height/2 - width/2));
		
		g2.rotate(Math.toRadians(degrees), width/2,  height / 2);
		g2.drawImage(this.img, 0,0, o);

		width = newWidth;
		height = newHeight;
		
		this.setSize(width,height);
		
		//sets image to the rotated one
		this.img = blankCanvas;	
	}
}
