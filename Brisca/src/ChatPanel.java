import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * This class creates the chat user interface for the user
 * @author Michelle M Ortiz & Mario Orbegoso
 *
 */
public class ChatPanel extends JPanel {

	private class ChatPanelListener implements ActionListener, KeyListener {
		/**
		 * Checks to see if there is a new message to be sent 
		 * Gets text from text field to send it to the server/client
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			engine.sendMessage(messageField.getText());
			messageField.setText("");
		}
		/**
		 * If Send key is pressed the message will be sent 
		 * Message field will be reseted 
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (!messageField.getText().equals("")) {
					engine.sendMessage(messageField.getText());
					messageField.setText("");
				}
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

	private static JTextArea chatbox;
	private static JList userList;
	private static JLabel statusBar;
	private static JTextField messageField;
	private static DefaultListModel listModel;
	private static final long serialVersionUID = 1L;

	private ClientEngine engine;
	/**
	 * Gets the contents for the GUI to be drawn
	 * @param engine
	 */
	public ChatPanel(ClientEngine engine) {
		this.engine = engine;
		statusBar = new JLabel("Ready");
		chatbox = new JTextArea();
		userList = new JList();
		listModel = new DefaultListModel();
		drawGUI();
	}
	/**
	 * Method for drawing GUI
	 */
	private void drawGUI() {
		JPanel messageBoxPanel = new JPanel();
		JPanel leftPanel = new JPanel();
		JPanel chatBoxPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel centerPanel = new JPanel();

		messageField = new JTextField();
		messageField.setPreferredSize(new Dimension(150,21));

		JButton sendMessageButton = new JButton("Send");

		chatbox.setEditable(false);
		chatbox.setPreferredSize(new Dimension(400,500));
		Image bg = new ImageIcon("Images/BACK.jpg").getImage();
		
		messageBoxPanel.setLayout(new BoxLayout(messageBoxPanel, BoxLayout.X_AXIS));
		messageBoxPanel.add(messageField);
		messageBoxPanel.add(sendMessageButton);
		
		chatBoxPanel.setLayout(new BoxLayout(chatBoxPanel, BoxLayout.X_AXIS));
		chatBoxPanel.add(chatbox);

		JScrollPane listScroller = new JScrollPane(userList);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listScroller.setPreferredSize(new Dimension(100, 200));

		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(listScroller);

		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.add(chatBoxPanel);
		leftPanel.add(Box.createVerticalStrut(10));
		leftPanel.add(messageBoxPanel);

		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setPreferredSize(new Dimension(200,300));
		centerPanel.add(listScroller);	
		centerPanel.add(chatBoxPanel);
		centerPanel.add(Box.createHorizontalStrut(10));
		centerPanel.add(messageBoxPanel);
		

		ChatPanelListener listener = new ChatPanelListener();
		chatbox.addKeyListener(listener);
		messageField.addKeyListener(listener);
		userList.addKeyListener(listener);
		sendMessageButton.addActionListener(listener);
		
		this.setLayout(new BorderLayout());
		this.add(Box.createVerticalStrut(5), BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(statusBar, BorderLayout.SOUTH);
	}
	/**
	 * Gets the text in the chat box
	 * @return	text in chat box as a string
	 */
	public static String getChatboxText() {
		return chatbox.getText();
	}
	/**
	 * Sets the text in the chatbox
	 * @param chatbox - receives text from chatbox as a string
	 */
	public static void setChatboxText(String chatbox) {
		ChatPanel.chatbox.setText(chatbox);
	}
	/**
	 * Method for adding a user to the userlist displayed in the chat panel
	 * @param username - receives the username to be added to the list as a string
	 */
	public static void addUser(String username) {
		listModel.addElement(username);
		userList.setModel(listModel);
	}
	/**
	 * Method for clearing the user list to be be updated
	 */
	public static void clearUserList() {
		listModel = new DefaultListModel();
	}
	/**
	 * Gets the text in the status bar 
	 * @return status in type string
	 */
	public static String getStatusBarText() {
		return statusBar.getText();
	}
	/**
	 * Sets the status bar text
	 * @param statusBar -receives status bar text
	 */
	public static void setStatusBarText(String statusBar) {
		ChatPanel.statusBar.setText(statusBar);
	}
	/**
	 * Sets focus on message field
	 */
	public static void setFocusOnMessageField() {
		messageField.requestFocus();
	}

}

