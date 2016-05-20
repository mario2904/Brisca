import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/**
 * This class is the server
 * Makes remote connection possible
 * Runs connection between the client
 * @author Michelle M Ortiz & Mario Orbegoso
 *
 */
public class SocketServer {

	private static ArrayList<String> sentMessages;
	private static ArrayList<Player> registeredUsers;
	private static ArrayList<Game> registeredGames;
	private static boolean newUser, newMessage, newGame;
	private static int newUsersFetched, newMembersFetched, newMessagesFetched, newGamesFetched, sentMessagesPos;
	private static int counter, port;
	
	private static class ServerThread implements Runnable {

		private Socket socket;

		public ServerThread(Socket socket) {
			this.socket = socket;
			System.out.println("Socket built");
		}
		/**
		 * This class runs the communication between the server and client
		 * Listens to inputs from client
		 * Waits to serve the client
		 */

		@Override
		public void run() {
			try {
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				String input = "", output = "";
				do {
					out.flush();

					if (input.contains("registerUser")) {
						output = "userRegistered";
						out.println(output);
						
						System.out.println("Received input: " + input);
						String queryValue = input.split("\\?")[1];
						String user = queryValue.split(";")[0];
						String password = queryValue.split(";")[1];
						registeredUsers.add(new Player(user,password));
						newUser = true;
						System.out.println("User " + user + " has logged in.");
						
					}
					else if (input.contains("newUserRequest")) {
						if (newUser == true) {
							if (newUsersFetched < registeredUsers.size()) {
								newUsersFetched++;
								String result = "";
								for (int i = 0; i < registeredUsers.size(); i++) {
									result = result + registeredUsers.get(i).getUsername() + ",";
								}
								output = "newUserResponse*" + result;
								out.println(output);
							}
							else {
								output = "newUserResponse*noNewUser";
								out.println(output);
								newUser = false;
								newUsersFetched = 0;
							}
						}
						else {
							output = "newUserResponse*noNewUser";
							out.println(output);
						}
					}
					else if (input.contains("sendMessage")) {
						String queryValue = input.split("\\?")[1];
						sentMessages.add(queryValue);
						newMessage = true;
						output = "messageReceived";
						out.println(output);
					}
					else if (input.contains("newMessageRequest")) {
						if (newMessage == true) {
							if (newMessagesFetched < registeredUsers.size()) {
								newMessagesFetched++;
								String result = "";
								for (int i = sentMessagesPos; i < sentMessages.size(); i++) {
									result = result + sentMessages.get(i) + "%MessageSeparator%";
								}
								output = "newMessageResponse*" + result;
								out.println(output);
							}
							else {
								newMessage = false;
								newMessagesFetched = 0;
								sentMessagesPos = sentMessages.size();
								output = "newMessageResponse*noNewMessage";
								out.println(output);
							}
						}
						else {
							//console.log('No new messages');
							output = "newMessageResponse*noNewMessage";
							out.println(output);
						}
					}
					else if(input.contains("createGame")){

						String queryValue = input.split("\\?")[1];
						String game = queryValue.split(";")[0];
						String user = queryValue.split(";")[1];

						for(int i = 0; i < registeredUsers.size(); i++){
							if(registeredUsers.get(i).getUsername().equals(user)){								
								registeredGames.add(new Game (game, registeredUsers.get(i)));//Initialized with newMember to true
								newGame = true;
								output = "gameCreated";
								out.println(output);
								break;								
							}
						}
					}
					else if (input.contains("newGameRequest")) {//ghjjjtujtyjytdfdhgf
						if (newGame == true) {
							if (newGamesFetched < registeredGames.size()) {
								newGamesFetched++;
								String result = "";
								for (int i = 0; i < registeredGames.size(); i++) {
									if(!registeredGames.get(i).hasGameStarted()){
										result = result + registeredGames.get(i).getGame() + ",";
									}
								}
								output = "newGameResponse*" + result;
								out.println(output);
							}
							else {
								output = "newGameResponse*noNewGame";
								out.println(output);
								newGame = false;
								newGamesFetched = 0;
							}
						}
						else {
							output = "newGameResponse*noNewGame";
							out.println(output);
						}
					}	
					else if (input.contains("gameStartedRequest")) {
						String game = input.split("\\?")[1];

						for(int i = 0; i < registeredGames.size(); i++){
							if(registeredGames.get(i).getGame().equals(game)){
								if(registeredGames.get(i).hasGameStarted()){
									String result = "gameStartedResponse*" + registeredGames.get(i).getMembers() + "-" + registeredGames.get(i).getLifeCard();
									output = result;
									out.println(output);
									System.out.println(output);
									//break;
								}
								else{
									output = "gameStartedResponse*gameNotStarted";
									out.println(output);
									System.out.println(output);
									//break;
								}
							}
						}
					}
					else if(input.contains("newCurrentRoundInfoRequest")){//jdhjkdfhgsdfg
						String game = input.split("\\?")[1];
						
						for(int i = 0; i < registeredGames.size(); i++){
							if(registeredGames.get(i).getGame().equals(game)){
								String result = registeredGames.get(i).getCurrentRoundInfo();
								output = "newCurrentRoundInfoResponse*" + result;
								out.println(output);
								break;
							}
						}
						
					}
					else if (input.contains("newMemberRequest")) {//ghjjjtujtyjytdfdhgf  Will change later on!
						try{
							String game = input.split("\\?")[1];
							String result = "";

							for(int i = 0; i < registeredGames.size(); i++){//Finds my game
								String Game = registeredGames.get(i).getGame();
								if(Game.equals(game)){
									if(registeredGames.get(i).hasNewMember() == true){		
										if (newMembersFetched < registeredGames.get(i).numOfMembers()) {// sketchyy############supposed to be size of current members of that game
											newMembersFetched++;
											result = "newMemberResponse*" + registeredGames.get(i).getMembers();
											output = result;
											out.println(output);
											break;
										}
										else {											
											output = "newMemberResponse*noNewMember";
											out.println(output);
											registeredGames.get(i).noNewMember();
											newMembersFetched = 0;
										}
									}
									else {
										output = "newMemberResponse*noNewMember";
										out.println(output);
									}
								}
							}
						}catch(ArrayIndexOutOfBoundsException e){
							output = "newMemberResponse*noNewMember";
							out.println(output);
						}
					}
					else if (input.contains("newCardRequest")){//bkjychewzewtxryctibuhonjm

						String queryValue = input.split("\\?")[1];
						String game = queryValue.split(";")[0];
						String user = queryValue.split(";")[1];
						
						for(int i = 0; i < registeredGames.size(); i++){
							if(registeredGames.get(i).getGame().equals(game)){
								String result = registeredGames.get(i).getMyCards(user);
								output = "newCardResponse*" + result;
								out.println(output);
								break;
							}
						}
					}
					else if(input.contains("startGame")){//jniuntrvienvjgtnmblrhtmnbgnbjenhyb
						String game = input.split("\\?")[1];
						for(int i = 0; i < registeredGames.size(); i++){
							String Game = registeredGames.get(i).getGame();
							if(Game.equals(game)){
								output = "gameStarted";
								out.println(output);
								registeredGames.get(i).startGame();
								break;
							}
						}
					}		
					else if(input.contains("winnerOfGameRequest")){//gfinbogindbgnbhdgkilngbjgi
						String game = input.split("\\?")[1];
						
						for(int i = 0; i < registeredGames.size(); i++){
							if(registeredGames.get(i).getGame().equals(game)){
								String result = "winnerOfGameResponse*" + registeredGames.get(i).getWinnerInfo();
								output = result;
								out.println(output);
								break;
							}
						}						
					}		
					else if(input.contains("newWindow")){//dkfjhglkjhgkldghlkdsf
						String result = "";
						for (int i = 0; i < registeredGames.size(); i++) {
							if(!registeredGames.get(i).hasGameStarted()){
								result = result + registeredGames.get(i).getGame() + ",";
							}
						}
						output = "newWindowResponse*" + result;
						out.println(output);
					}	
					else if(input.contains("joinGameRequest")){
						String queryValue = input.split("\\?")[1];
						String game = queryValue.split(";")[0];
						String user = queryValue.split(";")[1];

						for(int i = 0; i < registeredUsers.size(); i++){
							if(registeredUsers.get(i).getUsername().equals(user)){
								for(int j = 0; j < registeredGames.size(); j++){//finds game that want to join
									String Game = registeredGames.get(j).getGame();
									if(Game.equals(game)){
										output = "joinedGame";
										out.println(output);
										registeredGames.get(j).addMember(registeredUsers.get(i));
										registeredGames.get(j).newMember();
										break;
									}
								}
								break;
							}
						}
					}		
					else if(input.contains("chooseCardRequest")){
						String queryValue = input.split("\\?")[1];
						String game = queryValue.split(";")[0];
						String user = queryValue.split(";")[1];
						String card = queryValue.split(";")[2];
						
						for(int i = 0; i < registeredGames.size(); i++){
							if(registeredGames.get(i).getGame().equals(game)){
								registeredGames.get(i).chooseCard(user, card);
								output = "cardChosen";
								out.println(output);
								break;
							}
						}
					}
					else if(input.contains("newCardsChosenRequest")){
						String game = input.split("\\?")[1];
						
						for(int i = 0; i < registeredGames.size(); i++){
							if(registeredGames.get(i).getGame().equals(game)){
								output = "newCardsChosenResponse*" + registeredGames.get(i).getChosenCards();
								out.println(output);
								break;
							}
						}
					}
					else if(input.contains("getStatsRequest")){
						String user = input.split("\\?")[1];
						for(int i = 0; i< registeredUsers.size(); i++){
							if(registeredUsers.get(i).getUsername().equals(user)){
								String result = "statsResponse*" + registeredUsers.get(i).getStats();
								output = result;
								out.println(output);
								break;
							}
						}
					}
					else if (input.contains("removeUserRequest")) {
						String user = input.split("\\?")[1];
						for(int i = 0; i< registeredUsers.size(); i++){
							if(registeredUsers.get(i).getUsername().equals(user)){
								output = "removalSuccess";
								out.println(output);
								registeredUsers.remove(i);
								newUser = true;
								break;
							}
						}
					}
					else if (input.contains("removeGameRequest")) {
						output = "removalGameSuccess";
						out.println(output);	
						String queryValue = input.split("\\?")[1];
						registeredGames.remove(queryValue);
						newGame = true;
					}
				}
				while ((input = in.readLine()) != null);
				socket.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Main method that turns the socket to be listening to the commands of the client
	 * @param args
	 * @throws IOException 
	 * Initializes the array lists of the registered games, users, messages
	 */

	public static void main(String[] args) throws IOException {

		boolean listening = true;
		newMessage = newUser = newGame = false;
		registeredGames = new ArrayList<Game>();
		registeredUsers = new ArrayList<Player>();
		sentMessages = new ArrayList<String>();
		newMessagesFetched = newUsersFetched = sentMessagesPos = newGamesFetched = newMembersFetched = 0;

		counter = 0;
		port = 8290;

		ServerSocket serverSocket = new ServerSocket(port);
		while (listening) {
			System.out.println("Listening on: " + port + " ...");
			(new Thread(new ServerThread(serverSocket.accept()))).start();
		}
	}
}
