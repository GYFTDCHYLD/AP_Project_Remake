package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;


import domain.*;
import factories.DBConectorFactory;
import frame.ServerWindow;
import packet.*;
import packet.Packet.PacketTypes;

public class Server{

	private java.sql.Connection databaseConnection; 
	private Statement statement; 
	private ResultSet result;
	
	private ServerSocket serverSocket;
	private Socket connectionSocket;
	private Calendar date; 
	private static List<ClientHandler> onlineClient; // used for live chat 
	private List<Long> clientHandlerId;
	private List<Long> threadHandlerId;
	private List<Thread> onlineThreads;// used to logout/ disconnect user/ kill client thread

	
	
	public Server(int Port) {
		
		try {
			onlineClient = new ArrayList<>();
			onlineThreads = new ArrayList<>();
			clientHandlerId = new ArrayList<>(); 
			threadHandlerId = new ArrayList<>(); 
			
			this.serverSocket = new ServerSocket(Port);
			this.databaseConnection = DBConectorFactory.getDatabaseConnection();// connect to the data base
		}
		catch(Exception e) {
			System.err.println("error " + e.getMessage());
			return;
		}
		this.date = Calendar.getInstance();
		ServerWindow.getIpAddress().setText("Host IP: " + ServerWindow.Ip()); 
		JOptionPane.showInternalMessageDialog(ServerWindow.getServerDash(),"Server has started at "+ date.getTime(), "Server Online",JOptionPane.INFORMATION_MESSAGE);// display the message sent from server
		ServerWindow.getStartDate().setText("Server has started at "+ date.getTime()); 
		ServerWindow.getStatus().setText("Status: ONLINE"); 
		ServerWindow.getConnectedClient().setText("Connected Client(s): " + onlineThreads.size());
		ServerWindow.getMovingLabel().setVisible(true);

		while(true) {
			try {
				connectionSocket = serverSocket.accept();
				date = Calendar.getInstance();
				
				ClientHandler clientHandler = new ClientHandler(connectionSocket);// creating the client handler
				onlineClient.add(clientHandler);// add the client handlers to a list in-order to send chat to all
				clientHandlerId.add(clientHandler.getId());// The thread ID is a positive long number generated when this thread was created. The thread ID is unique and remains unchanged during its lifetime. When a thread is terminated, this thread ID may be reused.
				
				Thread thread = new Thread(clientHandler);// make client handler run on separate thread
				onlineThreads.add(thread);// add the thread to the online list, stop thread in the list when logging out
				threadHandlerId.add(thread.getId());
				thread.start();// start the client handler thread
				
				
				ServerWindow.getConnectedClient().setText("Connected Client(s): " + onlineThreads.size()); 
				System.out.println("Starting a thread for a client at "+ date.getTime());
				
				 List<Long> IdList = new ArrayList<Long>();// uses to store the clientHandler and thread Id that will be sent to connected client
				 IdList.add(clientHandler.getId());// add clientHandler id to the list
				 IdList.add(thread.getId());// add thread id to the list
				 
				 Packet11List identifiers = new Packet11List(IdList);// return list of client and add it to the packet/object
				 identifiers.setType("ID's"); 
				 clientHandler.sendData((Packet11List) identifiers);// send the id list
				
			} catch (IOException e) {
				System.err.println("error " + e.getMessage());
			}catch(Exception e) {
				System.err.println("error " + e.getMessage());
			}
		}
	}


	public class ClientHandler extends Thread{ 

		private Socket clientHandlerSocket;
		private ObjectOutputStream outputStream; 
		private ObjectInputStream inputStream; 
		private String UserInfo[][] = {{"",""}}; // user id and firstname 
		private String userType = "";// used to help filter complain list
		
		private ClientHandler (Socket socket) {
			this.clientHandlerSocket = socket;
			try {
				this.outputStream = new ObjectOutputStream(clientHandlerSocket.getOutputStream());
				this.inputStream = new ObjectInputStream(clientHandlerSocket.getInputStream());
			}
			catch(Exception e) {
				System.err.println("error " + e.getMessage());
			}
			Packet infoPacket = new Packet9Info("Sussessfully Connected to server");
			sendData(infoPacket);// send the info object/packet to the user
		}
		
		public void sendData(Packet data) { 
			try {
				outputStream.writeObject(data); 
				outputStream.flush();
			} catch (IOException e) {
				System.out.println("error sending data to client " + e.getMessage());
			}catch(NullPointerException e) {
				System.out.println("error sending data to client " + e.getMessage());
			}
		} 
		 
		private Packet readData() { 
			Object data = new Object(); 
			try {
				data = inputStream.readObject(); 
			} catch (IOException e) {
				System.out.println("error recieving data from client " + e.getMessage());
			}catch (ClassNotFoundException e) {
				System.out.println(" error recieving data from client " + e.getMessage()); 
			}catch(NullPointerException e) {
				System.err.println("error recieving data from client" + e.getMessage());
			}
			return (Packet) data;
			
		}
		
		@Override
		public void run() {
			
			while(true) {
				Packet data =  readData(); 
				parsePacket(data);	
			}
			
		}
		
		private void parsePacket(Packet data) { 
			try {
				PacketTypes type = Packet.lookupPacket(data.getPacketId()); 
				
				switch(type) {
					case INVALID: 	ErrorHandler(new Packet10Error("Invalid request")); 
						break;
					case REGISTER:
									RegistrationHandler((Packet00Register) data);	
						break;
					case LOGIN:
									LoginHandler((Packet01Login) data) ;
						break;
					case LOGOUT:
									LogoutHandler((Packet02Logout) data);								 
						break;
					case CHAT: 
									ChatHandler((Packet03Chat) data);	 	
						break;
					case COMPLAIN: 
									ComplainHandler((Packet04Complain) data); 	 	
						break;
					case INFO:
									InfoHandler((Packet9Info) data); 	  
						break;
						
					default:
					
				}
			}catch(Exception e) {
				
			}
		}

		private void ChatHandler(Packet03Chat data) { // handle chat
			sendChatToSendingAndReceivingClients(data);
		}
		
		private void InfoHandler(Packet9Info assign) {  
			switch (assign.getAssignment()) { 
				case "killThread":
											killThread(assign.getThreadID());
					break;
				case "Assign a complain":
											assignComplain(Integer.valueOf(assign.getInfo()), assign.getLoginId(), assign.getInfo2());					
					break;
				case "Set Date":
											setVisitDaTe(Integer.valueOf(assign.getInfo()), assign.getInfo2());
					break;
	
				default:
					break;
			}	
		}
		
		
		private void ErrorHandler(Packet10Error error) {// handle invalid request
			sendData(error);// send error message to client
		}
		
		private void RegistrationHandler(Packet00Register data) { // handle registration
			String sql = "SELECT * FROM microstar. `User`";
			int databaseSize = 0;
			try {
				statement = databaseConnection.createStatement();
				ResultSet result = statement.executeQuery(sql);
				while(result.next()) {//check the database for the user
					databaseSize++;
				} 
				statement.close();
			}catch (SQLException e) {
				System.err.println("sql exception caught");
			}
			
			String id = (data.getData().getFirstName()).substring(0,1) + "34" + databaseSize;//create user Id Using Fist letter of first name plus 34 plus the amount of user;
			User newUser = data.getData();  
			newUser.setUserId(id); // set the user id to the new id
			Register(newUser);//add user to database
			BillCustomer(id,"Due", 15000, "30/05/2021");// add a bill to the account for the customer
			Packet infoPacket = new Packet9Info("Sussessfully Registered");
			Packet00Register registered = new  Packet00Register(newUser);// prepare the object with the New User ID to be sent to the user for login
			sendData(registered);// send the the object to the user to extract the User ID
			sendData(infoPacket);// send the info object/packet to the user
			
		} 
		
		private boolean Register(User newUser) { 
			String query = ("INSERT INTO microstar. `User` (userId, nameTitle, firstName, lastName, password, jobTitle)"
					+ "VALUES ('" + newUser.getUserId() + "', '" + newUser.getNameTitle() + "', '" + newUser.getFirstName() + "', '" + newUser.getLastName() + "', '" + newUser.getPassword() + "', '" + "" + "');");
			String query2 = ("INSERT INTO microstar. `Phone` (userId, phoneNumber)"
					+ "VALUES ('" + newUser.getUserId() + "', '" + newUser.getPhoneNumber() + "');");
			String query3 = ("INSERT INTO microstar. `Email` (userId, email)"
					+ "VALUES ('" + newUser.getUserId() + "', '" + newUser.getEmail() + "');");
			try {
				statement = databaseConnection.createStatement();
				statement.addBatch(query);
				statement.addBatch(query2);
				statement.addBatch(query3);
				statement.executeBatch();
				statement.clearBatch();
				statement.closeOnCompletion(); 
			} catch (SQLException e) {
				Packet10Error error = new Packet10Error("Cannot register at the moment, please try again Later");// create a message/packer/object
				sendData(error);// send the info object/packet/message to the user
				return false;
			}
			return true; 
		}
		
		private boolean BillCustomer(String userId, String status, float amountDue, String dueDate) {  
			String query = ("INSERT INTO microstar. `Billing` (userId, status, amountDue, dueDate)"
					+ "VALUES ('" + userId + "', '" + status + "', '" + amountDue + "', '" + dueDate + "');");
			try {
				statement = databaseConnection.createStatement();
				statement.executeUpdate(query);
				statement.close();
			} catch (SQLException e) {
				Packet10Error error = new Packet10Error("Error updating billing account");// create a message/packer/object
				sendData(error);// send the info object/packet/message to the user
				return false;
			}
			return true;
		}
		

		private void LoginHandler(Packet01Login data) { // handle login
			User loginData;
			boolean found = false;
			try {
				String loginQuery = "SELECT * FROM `User` WHERE userId = '" + data.getUserId() + "' AND password = '" + data.getPassword() + "'";
				statement = databaseConnection.createStatement();
				result = statement.executeQuery(loginQuery);
				if(result.next()) {// if id and password match
					if(!result.getString("jobTitle").equals("")) {
						loginData = new Employee();
						((Employee)loginData).setJobTitle(result.getString("jobTitle"));
					}else {
						loginData = new Customer();
					}
					loginData.setUserId(result.getString("userId"));
					loginData.setNameTitle(result.getString("nameTitle"));
					loginData.setFirstName(result.getString("firstName"));
					loginData.setLastName(result.getString("lastName"));
					
					for(ClientHandler client : onlineClient) {// loop through the client connected to the server
						if(client.UserInfo[0][0].matches(loginData.getUserId())) { // check first row first column where user id is stored
							Packet infoPacket = new Packet9Info("User Already Logedin");
							sendData(infoPacket);// send the info object/packet to the user if user is already lodged in
							return;// stop the code here if user already logged in 
						}
					}
					
					String UserType;// userd to Prepared complain list for specific user
					if(loginData instanceof Customer) {// check if its a customer is loging in
						List<Billing>  billingList = new ArrayList<>();
						String billingQuery = "SELECT * FROM microstar. `Billing` WHERE userId = '" + loginData.getUserId() + "'";  // search for their account in database
						try {
							result = statement.executeQuery(billingQuery);
							while(result.next()) {//check the database for the billing info
								billingList.add(new Billing(result.getString("userId"), result.getString("status"), result.getFloat("amountDue"), result.getFloat("interest"), result.getString("dueDate"), result.getString("paidDate")));
							}  
							((Customer) loginData).setBillingAccount(billingList);// attach the account info to the Customer object
						} catch (SQLException e) {
							System.err.println("sql exception caught");
						}
						UserType = "Customer";
					}else {
						UserType = ((Employee) loginData).getJobTitle();//Representative  Technician
					}
					
					UserInfo[0][0] = loginData.getUserId();// set the UserId of this client handler to the Id of the loggedin user
					UserInfo[0][1] = loginData.getFirstName();// add the user firstname to the handler for chat purpose
					userType = UserType;// usertype to help filter complain list when sending to all clients
					
					sendData(new Packet01Login(loginData));// send the login data for the user dashboard
					
					Packet9Info infoPacket = new Packet9Info("Login Sussessfully");// prepare message 
					sendData(infoPacket); // send info to client
					
					Packet11List online = new Packet11List(onlineClient());// return list of client and add it to the packet/object
					online.setType("Online Clients");
					sendOnlineClientListToAllClients(online);// send list of clients id to all connected user
					
					Packet11List tech = new Packet11List(technitionList());// return list of Technician and add it to the packet/object
					tech.setType("Technicians");
					sendTechClientListToAllRep(tech);// send list of Technician id to all rep
					
					sendComplainListToAllClients(Complains());// send the packet to the user
					
					found = true;
					statement.close();
				}
			} catch (SQLException e) {
				Packet10Error error = new Packet10Error("Cannot login at the moment, please try again Later");// create a message/packer/object
				sendData(error);// send the info object/packet/message to the user
			}
			
			if(!found) {
				Packet10Error error = new Packet10Error("Invalid Id or Password");// create a message/packer/object
				sendData(error);// send the info object/packet/message to the user
			}	
		}
		
		private void LogoutHandler(Packet02Logout data) {
			putUserOffline(data); // remove user from the server 
		} 
		
		private void ComplainHandler(Packet04Complain data) {
			try {
				data.getData().makeComplain();// Hibernate method
			}catch(Exception e) {
				Packet10Error error = new Packet10Error("Complain not sent ");// prepare message  
				sendData(error); // send info to client
				return;
			}
			Packet9Info infoPacket = new Packet9Info("Complain Recieved");// create a message/packer/object
			sendData(infoPacket);// send the info object/packet/message to the user
			sendComplainListToAllClients(Complains());// send the packet to the user
		}
		
		private void  assignComplain(int complainId, String repId, String techId) {// use for rep to assign complain to Technician
			Complain complain = new Complain(); 
			complain.setId(complainId);
			complain.setRepId(repId);
			complain.setTechId(techId);
			try {
				complain.assignComplain();// Hibernate method
			}catch(Exception e) {
				Packet10Error error = new Packet10Error("Complain not assigned ");// prepare message  
				sendData(error); // send info to client
				return;
			}
			
			sendComplainListToAllClients(Complains());// send the packet to the user
			Packet9Info infoPacket = new Packet9Info("Complain Assigned");// prepare message 
			sendData(infoPacket); // send info to client
		}
		
		private void  setVisitDaTe(int complainId, String Date) {// use for rep to assign complain to Technician
			Complain complain = new Complain(); 
			complain.setId(complainId);
			complain.setVisitDate(Date);
			try {
				complain.setDate();// Hibernate method
			}catch(Exception e) {
				Packet10Error error = new Packet10Error("Date not set ");// prepare message  
				sendData(error); // send info to client
				return;
			}
			
			sendComplainListToAllClients(Complains());// send the packet to the user
			Packet9Info infoPacket = new Packet9Info("Date Set");// prepare message 
			sendData(infoPacket); // send info to client
		}
		
		
		private Packet11List Complains(){// get complain from database and store it in a list
			Complain complain = new Complain();
			return new Packet11List(complain.readAll());// Hibernate method
		}
		
		
		private List<String[][]> onlineClient(){// a list of active client to send to server for chat
			List<String[][]>  list = new ArrayList<>();
			for (ClientHandler client : onlineClient) {  
				if(!client.UserInfo[0][0].matches(""))
					list.add(client.UserInfo);
			}
			
			return list;
		}
		
		private List<String[][]> technitionList(){// a list of Technician to be sent to rep in order to assign complain(s)
			List<String[][]>  list = new ArrayList<>();
			String techInfo[][] = new String[1][2];
			String query = "SELECT * FROM microstar. `User`"; 
			try {
				statement = databaseConnection.createStatement();
				result = statement.executeQuery(query);
				while(result.next()) {//check the database for the user
					if(result.getString("jobTitle").equals("Technician")) {
						techInfo[0][0] = result.getString("userId"); 
						techInfo[0][1] = result.getString("firstName");
						list.add(techInfo);
					}
				}
			} catch (SQLException e) {
				System.err.println("sql exception caught");
			}
			return list;
		}
		

		private void putUserOffline(Packet02Logout data) { // remove the user id from the client handler
			try {
			
				for (ClientHandler client : onlineClient) {
					if (client.getId() == data.getHandlerID()) { 
						client.UserInfo[0][0] = ""; // set the userID of the client handler to an empty string so that user can re-login with the same handler
						client.UserInfo[0][1] = ""; // set the firstname of the client handler to an empty string so that user can re-login with the same handler
						client.userType = "";
						break;
					}
				}
				
				Packet11List online = new Packet11List(onlineClient());// return list of client and add it to the packet/object
				online.setType("Online Clients");
				sendOnlineClientListToAllClients(online);// send list of clients id to all connected user
			
				sendData(data);// send the packet to the user for them to use it to take necesasary action
				sendData(new Packet9Info("Logout Successfully"));// send the packet to the user 
			} catch (IndexOutOfBoundsException e) {
				sendData(new Packet10Error("Logout Error " + e.getMessage()));// send the packet to the user 
				 e.printStackTrace();
			}
		}

		private void killThread(long id) { 
			Packet9Info infoPacket = new Packet9Info("Exit");
			sendData(infoPacket);
			int index = getThreadIndex(id);
			
			onlineClient.get(index).UserInfo[0][0] = ""; // set the userID of the client handler to an empty string so that user can re-login with the same handler
			onlineClient.get(index).UserInfo[0][1] = ""; // set the firstname of the client handler to an empty string so that user can re-login with the same handler
			onlineClient.get(index).userType = "";
			onlineClient.remove(index);
			
			Thread treadToKill = onlineThreads.get(index);
			onlineThreads.remove(index);// remove the users thread from the list of threads
			ServerWindow.getConnectedClient().setText("Connected Client(s): " + onlineThreads.size()); 
			treadToKill.stop();// stop the users thread;
		}

		
		private int getThreadIndex(long threadId) throws IndexOutOfBoundsException {// get the index for the user in the online list
			int index = 0;
			for (Thread thread : onlineThreads) {
				if (thread.getId() == threadId) {
					break;
				}
				index++;
			}
			return index;
		}
	}



	public void sendChatToSendingAndReceivingClients(Packet03Chat data) { 
		for (ClientHandler client : onlineClient) {
			if(client.UserInfo[0][0].equals(data.getSenderId()) || client.UserInfo[0][0].equals(data.getRecieverId()))
				data.writeData(client);// only send the message to the sender or the reciever
		}
	}
	
	public void sendOnlineClientListToAllClients(Packet11List data) {
		for (ClientHandler client : onlineClient) {
			data.writeData(client);// send the list of persons online to everyone online
		}
	}
	
	public void sendTechClientListToAllRep(Packet11List data) {
		for (ClientHandler client : onlineClient) {
			if(client.userType.equals("Representative")){
				data.writeData(client);// send the list of Technician to all representative
			}
		}
	}
	
	public void sendComplainListToAllClients(Packet11List data) {
		for (ClientHandler client : onlineClient) {
			client.sendData(myComplains(data, client.UserInfo[0][0], client.userType));// send the list of complain to everyone to everyone online
		}
	}
	
	private Packet11List myComplains(Packet11List Packet11List,  String userId, String userType){// filter the complain list
		List<Complain> list = new ArrayList<>();//create an arraylist to store the complains to be sent back to the user
		for (Complain complain : (List<Complain>)Packet11List.getData()) {// loop through the list of complain that was pass through the parameter
			if (userType.equals("Customer")) {
				if (complain.getCustId().equals(userId)) {// check if the user id match the user id in the complain list
					list.add(complain);// if the id match, store the complain in the arraylist that will be sent to the user
				}
			}else if (userType.equals("Technician")) {
				if (complain.getTechId().equals(userId)) {// check if a complain has been assigned to the Technician
					list.add(complain);// if the id match, store the complain in the arraylist that will be sent to the Technician
				}
			}else { 
				list.add(complain);// all complain should be sent to rep
			}
		}
		return new Packet11List(list);
	}
	
	public static void serverExit() {
		Packet10Error error = new Packet10Error("Server Ended");
		for (ClientHandler client : onlineClient) {
			client.sendData(error);// seend command to all client, this message will make all client programs windows 
		}
		System.exit(0);// exit / close server window
	}
	
}
