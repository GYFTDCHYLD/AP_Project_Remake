package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;


import domain.*;
import frame.ServerWindow;
import packet.*;
import packet.Packet.PacketTypes;

public class Server{

	private ServerSocket serverSocket;
	private Socket connectionSocket;
	private Calendar date; 
	private List<User> userDatabase;  
	private List<Complain> complainDatabase; 
	private List<BillingAccount> billigAccountDatabase;   
	private static List<ClientHandler> onlineClient; // used for live chat
	private static List<Long> clientHandlerId;
	private static List<Long> threadHandlerId;
	private List<Thread> onlineThreads;// used to logout/ disconnect user/ kill client thread

	
	
	public Server(int Port) {
		
		try {
			userDatabase = new ArrayList<User>();  
			complainDatabase = new ArrayList<Complain>();
			billigAccountDatabase = new ArrayList<BillingAccount>();
			onlineClient = new ArrayList<ClientHandler>();
			onlineThreads = new ArrayList<Thread>();
			clientHandlerId = new ArrayList<Long>(); 
			threadHandlerId = new ArrayList<Long>(); 
			
			User User = new Employee("S122", "Ms", "Shericka", "Jones", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Representative"); 
			userDatabase.add(User);
			
			User = new Employee("D111", "Ms", "Dahlia", "Holness", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Representative"); 
			userDatabase.add(User);
			
			User = new Customer("A121", "Ms", "Akielia", "Willbrugh", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"); 
			billigAccountDatabase.add(new BillingAccount("A121","Due", 15000));
			userDatabase.add(User);
			
			User = new Employee("C123", "Mr", "Craig", "Reid", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Technitian"); 
			userDatabase.add(User);
			
			User = new Customer("C124", "Mr", "Craig", "Reid", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"); 
			billigAccountDatabase.add(new BillingAccount("C124","Due", 15000));
			userDatabase.add(User);
			
			this.serverSocket = new ServerSocket(Port);
		}
		catch(Exception e) {
			System.err.println("error " + e.getMessage());
			return;
		}
		this.date = Calendar.getInstance();
		
		JOptionPane.showInternalMessageDialog(ServerWindow.getServerDash(),"Server has started at "+ date.getTime(), "Server Online",JOptionPane.INFORMATION_MESSAGE);// display the message sent from server
		
		ServerWindow.StartDate.setText("Server has started at "+ date.getTime()); 
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
		private ObjectOutputStream objOs;
		private ObjectInputStream objIs;
		private String UserInfo[][] = {{"",""}}; // user id and firstname 
		private String userType = "";// used to help filter complain list
		
		private ClientHandler (Socket socket) {
			this.clientHandlerSocket = socket;
			try {
				this.objOs = new ObjectOutputStream(clientHandlerSocket.getOutputStream());
				this.objIs = new ObjectInputStream(clientHandlerSocket.getInputStream());
			}
			catch(Exception e) {
				System.err.println("error " + e.getMessage());
			}
			Packet infoPacket = new Packet9Info("Sussessfully Connected to server");
			sendData(infoPacket);// send the info object/packet to the user
		}
		
		public void sendData(Packet data) { 
			try {
				objOs.writeObject(data);
				objOs.flush();
			} catch (IOException e) {
				System.out.println("error sending data to client " + e.getMessage());
			}catch(NullPointerException e) {
				System.out.println("error sending data to client " + e.getMessage());
			}
		} 
		 
		private Packet readData() { 
			Object data = new Object(); 
			try {
				data = objIs.readObject();
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

		
		private void InfoHandler(Packet9Info assign) {  
			switch (assign.getAssignment()) { 
				case "killThread":
											killThread(assign.getThreadID());
					break;
					
				case "Assign a complain":
											assignComplain(Integer.valueOf(assign.getInfo()), assign.getLoginId(), assign.getInfo2());
											
				break;
	
				default:
					break;
			}	
		}

		
		
		private void ErrorHandler(Packet10Error error) {// handle invalid request
			sendData(error);// send error message to client
		}
		
		private void RegistrationHandler(Packet00Register data) { // handle registration
			String id = (data.getData().getFirstName()).substring(0,1) + "34" + userDatabase.size();//create user Id Using Fist letter of first name plus 34 plus the amount of user;
			User User = new Customer(id,data.getData().getNameTitle(), data.getData().getFirstName(), data.getData().getLastName(), data.getData().getPassword());
			userDatabase.add(User);//add user to database
			billigAccountDatabase.add(new BillingAccount(id,"unpaid", 15000));// add account
			Packet infoPacket = new Packet9Info("Sussessfully Registered");
			((Packet00Register)data).getData().setPassword(id);// replace the pasword in the object with the New User ID 
			sendData(data);// send the the object to the user to extract the User ID
			sendData(infoPacket);// send the info object/packet to the user
		}
		

		private void LoginHandler(Packet01Login data) { // handle login
			Packet07User loginData = new Packet07User(new User());
			boolean found = false;
			
			for (User user : userDatabase) {//check the database for the user
				if(user.getUserId().matches(data.getUserId()) && user.getPassword().matches(data.getPassword())) {
					loginData = new Packet07User(user); 
					for(ClientHandler client : onlineClient) {// loop through the client connected to the server
						if(client.UserInfo[0][0].matches(loginData.getData().getUserId())) { // check first row first column where user id is stored
							Packet infoPacket = new Packet9Info("User Already Logedin");
							sendData(infoPacket);// send the info object/packet to the user if user is already lodged in
							return;// stop the code here if user already logged in 
						}
					}
					
					String UserType;// userd to Prepared complain list for specific user
					if(loginData.getData() instanceof Customer) {// check if its a customer is loging in
						for(BillingAccount account : billigAccountDatabase) {// search for their account in database
							if(account.getId().equals(loginData.getData().getUserId())) {
								((Customer)loginData.getData()).setBillingAccount(account);/// ass the account to the user object to be sent to customer
							}
						}
						UserType = "Customer";
					}else {
						UserType = ((Employee) loginData.getData()).getJobTitle();//Representative  Technitian
					}
					
					this.UserInfo[0][0] = loginData.getData().getUserId();// set the UserId of this client handler to the Id of the loggedin user
					this.UserInfo[0][1] = loginData.getData().getFirstName();// add the user firstname to the handler for chat purpose
					this.userType = UserType;// usertype to help filter complain list when sending to all clients
					
					sendData(loginData);// send the data for the user dashboard
					
					sendComplainListToAllClients(Complains());// send the packet to the user
					
					Packet11List list = new Packet11List(onlineClient());// return list of client and add it to the packet/object
					list.setType("Online Clients");
					sendOnlineClientListToAllClients(list);// send list of clients id to all connected user
					
					list = new Packet11List(technitionList());// return list of technition and add it to the packet/object
					list.setType("Technitions");
					sendTechClientListToAllRep(list);// send list of technitions id to all rep
					
					Packet9Info infoPacket = new Packet9Info("Login Sussessfully");// prepare message 
					sendData(infoPacket); // send info to client
					
					found = true;
					break;
				}
			} 
			
			if(!found) {
				Packet10Error error = new Packet10Error("Invalid Id or Password");// create a message/packer/object
				sendData(error);// send the info object/packet/message to the user
			}
				
		}
		
		private void ComplainHandler(Packet04Complain data) {
			data.getData().setId(complainDatabase.size()+1);
			complainDatabase.add(data.getData());//add the complain to the database
			Packet9Info infoPacket = new Packet9Info("Complain Recieved");// create a message/packer/object
			sendData(infoPacket);// send the info object/packet/message to the user
			sendComplainListToAllClients(Complains());// send the packet to the user
		}
		
		private void  assignComplain(int complainId, String repId, String techId) {// use for rep to assign complain to technition
			for(Complain complain: complainDatabase) {
				if(complain.getId() == complainId) {
					complain.setRepId(repId);
					complain.setTecId(techId); 
				}
			}
			sendComplainListToAllClients(Complains());// send the packet to the user
		}
		
		
		private Packet11List Complains(){// get complain from database and store it in a list
			List<Complain> list = new ArrayList<Complain>();
			for (Complain complain : complainDatabase) {// loop through the list of complain from the database
				list.add(  complain);
			}
			return new Packet11List(list);
		}
		
		private void LogoutHandler(Packet02Logout data) {
			putUserOffline(data); // remove user from the server 
		} 
		
		private void killThread(long id) { 
			Packet9Info infoPacket = new Packet9Info("Exit");
			sendData(infoPacket);
			int index = getThreadIndex(id);
			onlineClient.remove(index);
			Thread treadToKill = onlineThreads.get(index);
			onlineThreads.remove(index);// remove the users thread from the list of threads
			ServerWindow.getConnectedClient().setText("Connected Client(s): " + onlineThreads.size()); 
			treadToKill.stop();// stop the users thread;
		}
		
		private List<String[][]> onlineClient(){// a list of active client to send to server for chat
			List<String[][]>  list = new ArrayList<String[][]>();
			for (ClientHandler client : onlineClient) {  
				if(!client.UserInfo[0][0].matches(""))
					list.add(client.UserInfo);
			}
			
			return list;
		}
		
		private List<String[][]> technitionList(){// a list of technition to be sent to rep in order to assign complain(s)
			List<String[][]>  list = new ArrayList<String[][]>();
			String techInfo[][] = new String[1][2];
			for (User user : userDatabase) { 
				if(user instanceof Employee) {
					if(((Employee) user).getJobTitle().equals("Technitian")) {
						techInfo[0][0] = user.getUserId(); 
						techInfo[0][1] = user.getFirstName();
						list.add(techInfo);
					}
				}
			}
			return list;
		}
		
		
		
		private void ChatHandler(Packet03Chat data) { // handle chat
			sendChatToSendingAndReceivingClients(data);
			
		}

		private void putUserOffline(Packet02Logout data) { // remove the user id from the client handler
			try {
			
				int index = getHandlerIndex(data.getHandlerID());
				onlineClient.get(index).UserInfo[0][0] = ""; // set the userID of the client handler to an empty string so that user can re-login with the same handler
				onlineClient.get(index).UserInfo[0][1] = ""; // set the firstname of the client handler to an empty string so that user can re-login with the same handler
				onlineClient.get(index).userType = "";
				
				Packet11List onlineClients = new Packet11List(onlineClient());// return list of client and add it to the packet/object
				sendOnlineClientListToAllClients(onlineClients);// send list of clients id to all connected user

				sendData(data);// send the packet to the user for them to use it to take necesasary action
				sendData(new Packet9Info("Logout Successfully"));// send the packet to the user 
			} catch (IndexOutOfBoundsException e) {
				sendData(new Packet10Error("Logout Error " + e.getMessage()));// send the packet to the user 
				 e.printStackTrace();
			}
		}

		private int getHandlerIndex(long handlerId) throws IndexOutOfBoundsException {// get the index for the user in the online list
			int index = 0;
			for (ClientHandler client : onlineClient) {
				if (client.getId() == handlerId) { 
					break;
				}
				index++;
			}
			return index;
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
				data.writeData(client);// send the list of technitions to all representative
			}
		}
	}
	
	public void sendComplainListToAllClients(Packet11List data) {
		for (ClientHandler client : onlineClient) {
			client.sendData(myComplains(data, client.UserInfo[0][0], client.userType));// send the list of complain to everyone to everyone online
		}
	}
	
	private Packet11List myComplains(Packet11List Packet11List,  String userId, String userType){// filter the complain list
		List<Complain> list = new ArrayList<Complain>();//create an arraylist to store the complains to be sent back to the user
		for (Complain complain : (List<Complain>)Packet11List.getData()) {// loop through the list of complain that was pass through the perameter
			if (userType.equals("Customer")) {
				if (complain.getcustId().equals(userId)) {// check if the user id match the user id in the complain list
					list.add(complain);// if the id match, store the complain in the arraylist that will be sent to the user
				}
			}else if (userType.equals("Technitian")) {
				if (complain.getTecId().equals(userId)) {// check if a complain has been assigned to the technition
					list.add(complain);// if the id match, store the complain in the arraylist that will be sent to the technition
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
