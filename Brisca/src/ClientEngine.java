import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class is the Client Engine
 * Connects the user to the server 
 * Makes remote playin possible
 * @author Michelle M Ortiz & Mario Orbegoso
 *
 */
public class ClientEngine {

	private String newMessage, newUser, newGame, newMember, newCard, newCardsChosen, newCurrentRoundInfo, winnerOfGame, gameStarted, localUser, currentGame, localPassword, localGame;
	private final String server = "localhost";
	private final int port = 8290;
	
	
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public ClientEngine() {
		this.localUser = "";
		this.localPassword = "";
		this.newMessage = "";
		this.newUser = "";
		this.newGame = "";
		this.localGame = "";
		this.newMember = "";
		this.newCard = "";
		this.gameStarted = "";
		this.currentGame = "";
		this.newCardsChosen = "";
		this.newCurrentRoundInfo = "";
		this.winnerOfGame = "";
	}
	/**
	 * Establishes the initial connection with the username and password
	 * @param username
	 * @param password
	 */
	public void enterRoom(String username, String password) {

		try {
			clientSocket = new Socket(server, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String response = "";
		do {
			response = this.sendRequest(server, port, "registerUser?" + username + ";" + password);
		}
		while (!response.contains("userRegistered"));

		this.localUser = username;
		this.localPassword = password;
		ChatPanel.setStatusBarText("Entered chat successfully");

	}
	/**
	 * Sends message to server when a user has arrived
	 * Sends username
	 */
	public void hasNewUserArrived() { // changed
		
		String response = "";
		
		do{
		response = this.sendRequest(server, port, "newUserRequest");
		}while(!response.contains("newUserResponse"));
		
		if (!response.contains("noNewUser")) {

			this.newUser = response.split("\\*")[1].trim();
			ChatPanel.clearUserList();
			System.out.println("Cleared User List");
			if (!this.newUser.isEmpty()) {
				for (String s: this.newUser.split(",")) {
					ChatPanel.addUser(s);
				}
			}
			this.newUser = "";
			System.out.println("Updated User List");
		}
	}
	/**
	 * Sends the chat messages to the server
	 * @param message
	 */
	public void sendMessage(String message) {
		String response = "";
		do {
			response = this.sendRequest(server, port, "sendMessage?" + this.localUser + ";" + (message.replace(" ", "%20%")));
		}
		while (!response.contains("messageReceived"));

	}
	/**
	 * Sends message to user that a new message is going to be sent
	 * Sends the text
	 */
	public void hasNewMessageArrived() {  // changed
		
		String response = "";
		
		do{
		response = this.sendRequest(server, port, "newMessageRequest");
		}while(!response.contains("newMessageResponse"));
		
		if (!response.contains("noNewMessage")) {
			this.newMessage = response.split("\\*")[1];
			System.out.println(newMessage + "    newMessageVariable");
			if (!this.newMessage.isEmpty()) {
				String prevText = ChatPanel.getChatboxText();
				for (String s : this.newMessage.trim().split("%MessageSeparator%")) {
					prevText = prevText + "\n" + s.split(";")[0] + ": " + s.split(";")[1].replace("%20%", " ");
				}
				ChatPanel.setChatboxText(prevText);
			}
			this.newMessage = "";
			System.out.println("Updated Chat Area");
		}
	}
	/**
	 * Sends notice to server when a new game is going to be created
	 * @param game
	 */
	public void createGame(String game){
		String response = "";
		do {
			response = this.sendRequest(server, port, "createGame?" + game +";" + this.localUser);
		}
		while (!response.contains("gameCreated"));
		
		this.localGame = game;
		this.currentGame = game;
		System.out.println(localGame);
	}
	/**
	 * Sends notice to server that a new game has been created 
	 * Sends game created
	 */
	public void hasNewGameCreated() {
		
		String response = "";
		
		do{
		response = this.sendRequest(server, port, "newGameRequest");
		}while(!response.contains("newGameResponse"));
		
		if (!response.contains("noNewGame")) {
			this.newGame = response.split("\\*")[1].trim();
			System.out.println(newGame + "    newGame Variable");
			AvailableGames.clearGameList();
			if (!this.newGame.isEmpty()) {
				for (String s: this.newGame.split(",")) {
					AvailableGames.addGame(s);
				}
			}
			this.newGame = "";
			System.out.println("Updated Game List");
		}
	}
	/**
	 * Sends notice to the user that a new game has started
	 */
	public void hasGameStarted(){
		String response = "";
		
		do{
		response = this.sendRequest(server, port, "gameStartedRequest?" + this.currentGame);
		}while(!response.contains("gameStartedResponse"));
		
		if (!response.contains("gameNotStarted")) {
			this.gameStarted = response.split("\\*")[1].trim();
			System.out.println(gameStarted + "  At client");
			if (!this.gameStarted.isEmpty()) {
				GameRoom.initializeGame(gameStarted);
			}
			this.gameStarted = "";
			System.out.println("Updated GameStarted List");
		}
	}
	/**
	 * Sends request to get round winner and points
	 */
	public void hasNewCurrentRoundInfo(){
		String response = "";
		
		do{
		response = this.sendRequest(server, port, "newCurrentRoundInfoRequest?" + this.currentGame);
		}while(!response.contains("newCurrentRoundInfoResponse"));

		if (!response.contains("noNewCurrentRoundInfo")) {
			this.newCurrentRoundInfo = response.split("\\*")[1];
			CreatedGame.clearMemberList();
			if (!this.newCurrentRoundInfo.isEmpty()) {
				GameRoom.addCurrentRoundInfo(this.newCurrentRoundInfo);
			}
			this.newCurrentRoundInfo = "";
			System.out.println("Updated CurrentRoundInfo List");
		}
	}
	/**
	 * Sends notice to server when a new member has entered a game
	 * Sends the member username
	 */
	public void hasNewMember() {
		String response = "";
		
		do{
		response = this.sendRequest(server, port, "newMemberRequest?" + this.localGame);
		}while(!response.contains("newMemberResponse"));

		if (!response.contains("noNewMember")) {
			this.newMember = response.split("\\*")[1];
			CreatedGame.clearMemberList();
			if (!this.newMember.isEmpty()) {
				for (String s: this.newMember.split(",")) {
					CreatedGame.addMember(s);
				}
			}
			this.newMember = "";
			System.out.println("Updated Member List");
		}
	}
	/**
	 * Sends notice to server that a user has a new card
	 * Sends card
	 */
	public void hasNewCard(){
		String response = "";
		
		do{
		response = this.sendRequest(server, port,  "newCardRequest?" + this.currentGame + ";" + this.localUser);
		}while(!response.contains("newCardResponse"));
		if (!response.contains("noNewCard")) {
			try{
			System.out.println(response);		
			this.newCard = response.split("\\*")[1].trim();
			if (!this.newCard.isEmpty()) {
				GameRoom.addCard(newCard);
			}
			this.newCard = "";
			System.out.println("Updated Card List");
			
			}catch(ArrayIndexOutOfBoundsException e){
				this.newCard = "";
			}
		}	
			
	}
	/**
	 * Sends message to server that a game is going to be started
	 */
	public void startGame(){
		String response = "";
		do {
			response = this.sendRequest(server, port, "startGame?" + this.localGame);
		}
		while (!response.contains("gameStarted"));
	}
	/**
	 * Sends request to get the winner of the game and total points
	 */

	public void getWinnerOfGame(){		
		String response = "";
		
		do{
			response = this.sendRequest(server, port,  "winnerOfGameRequest?" + this.currentGame);
		}while(!response.contains("winnerOfGameResponse")); 
		
		System.out.println(response);
		this.winnerOfGame = response.split("\\*")[1].trim();
		if (!this.winnerOfGame.isEmpty()) {
			GameRoom.winnerOfGame(winnerOfGame);
		}
		this.winnerOfGame = "";
		System.out.println("Updated winnerOfGame List");

	}
	/**
	 * Sends notice to server that a new AvailableGames window has been opened
	 */
	public void newWindowGameList(){
		String response = "";
		
		do{
			response = this.sendRequest(server, port, "newWindow");
		}while(!response.contains("newWindowResponse"));
		AvailableGames.clearGameList();
		try{
			for (String s: response.split("\\*")[1].split(",")) {
				AvailableGames.addGame(s);
			}
		}catch(ArrayIndexOutOfBoundsException e){System.out.println("no games created yet");}
		System.out.println("Updated newWindow");
	}
	/**
	 * Sends message to server that a user is going to join a game
	 * @param game
	 */
	public void joinGame(String game){
		String response = "";
		do {
			response = this.sendRequest(server, port, "joinGameRequest?" + game + ";" + this.localUser);
		}
		while (!response.contains("joinedGame"));
		this.currentGame = game;
	}
	/**
	 * Sends message to server that a card is going to be chosen
	 * @param card
	 */
	public void chooseCard(String card){
		String response = "";
		
		do {
			response = this.sendRequest(server, port, "chooseCardRequest?" + this.currentGame + ";" + this.localUser + ";" + card);
		}
		while (!response.contains("cardChosen"));
		
	}
	/**
	 * Sends notice that a new card has been chosen
	 * Sends card
	 */
	public void hasNewCardChosen(){
		
		String response = "";
		
		do{
		response = this.sendRequest(server, port,  "newCardsChosenRequest?" + this.currentGame);
		}while(!response.contains("newCardsChosenResponse"));
		if (!response.contains("noNewCardsChosen")) {
			System.out.println(response);
			this.newCardsChosen = response.split("\\*")[1].trim();
			if (!this.newCardsChosen.isEmpty()) {
				GameRoom.addChosenCards(newCardsChosen);
			}
			this.newCardsChosen = "";
			System.out.println("Updated newCardsChosen List");
		}
	}
	/**
	 * Sends request to server to receive the gaming status of this local user
	 */
	public void getStats(){
		
		String response = "";
		
		do{
			response = this.sendRequest(server, port, "getStatsRequest?" + this.localUser);
		}while(!response.contains("statsResponse"));
		Stats.clearStatsList();
		for (String s: response.split("\\*")[1].split(",")) {
			Stats.addStats(s);
		}
	}
	/**
	 * Sends request to server to remove user
	 * Removes user
	 */
	public void removeUser() {
		String response = "";
		do {
			response = this.sendRequest(server, port, "removeUserRequest?" + this.localUser);
		}
		while (!response.contains("removalSuccess"));
		
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Sends request to server to remove game
	 * Removes game
	 */
	public void removeGame() {
		
		if((this.localGame.equals(this.currentGame))){

			String response = "";
			do {
				response = this.sendRequest(server, port, "removeGameRequest?" + this.localGame);
			}
			while (!response.contains("removalGameSuccess"));
		}
	}
	/**
	 * Sends the requests to the server and wait for a response
	 * @param serverIP
	 * @param port
	 * @param parameters
	 * @return response from server
	 */
	private String sendRequest(String serverIP, int port, String parameters)
	{
		try {
			out.println(parameters);

			String fromServer = "";
			while ((fromServer = in.readLine()) == null);
			return fromServer;

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	public String getUser(){
		return this.localUser;
	}
}
