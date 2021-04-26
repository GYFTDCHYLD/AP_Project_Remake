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
import packet.*;
import packet.Packet.PacketTypes;

public class Server{

	private ServerSocket serverSocket;
	private Socket connectionSocket;
	private Calendar date;
	private int clientCount;
	private List<User> userDatabase;  
	private List<Complain> complainDatabase; 
	private List<BillingAccount> billigAccountDatabase;   
	private List<ClientHandler> onlineClient; // used for live chat
	private List<Thread> onlineThreads;// used to logout/ disconnect user/ kill client thread

	
	
	public Server() {
		
		try {
			userDatabase = new ArrayList<User>();  
			complainDatabase = new ArrayList<Complain>();
			billigAccountDatabase = new ArrayList<BillingAccount>();
			onlineClient = new ArrayList<ClientHandler>();
			onlineThreads = new ArrayList<Thread>();
			
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
			
			this.serverSocket = new ServerSocket(8000);
		}
		catch(Exception e) {
			System.err.println("error " + e.getMessage());
			return;
		}
		this.date = Calendar.getInstance();
		this.clientCount = 0;
		
		JOptionPane.showMessageDialog(null,"Server has started at "+ date.getTime(), "Server Online",JOptionPane.INFORMATION_MESSAGE);// display the message sent from server
		

		while(true) {
			try {
				connectionSocket = serverSocket.accept();
				date = Calendar.getInstance();
				clientCount+= 1;
				System.out.println("Starting a thread for a client at "+ date.getTime()+"\nClient Count: "+clientCount);
				
				ClientHandler clientHandler = new ClientHandler(connectionSocket);// creating the client handler
				onlineClient.add(clientHandler);// add the client handlers to a list in-order to send chat to all
				
				Thread thread = new Thread(clientHandler);// make client handler run on separate thread
				thread.start();// start the client handler thread
				onlineThreads.add(thread);// add the thread to the online list, stop thread in the list when logging out
				
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
		 
		private Object readData() { 
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
			return data;
			
		}
		
		@Override
		public void run() {
			
			while(true) {
				Packet data = (Packet) readData(); 
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

		
		private void InfoHandler(Packet9Info info) { 
			switch (info.getInfo()) { 
				case "Send Online List":
											
					break;
				case "killThread":
											killThread(info.getThreadIndex());
					break;
	
				default:
					break;
			}	
		}
		
		private void killThread(int index) { 
			onlineClient.remove(index);
			onlineThreads.get(index).stop();// stop the users thread;
			onlineThreads.remove(index);// remove the users thread from the list of threads
			clientCount--;
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
				if(user.getUserId().equals(data.getUserId()) && user.getPassword().equals(data.getPassword())) {
					loginData = new Packet07User(user); 
					for(ClientHandler client : onlineClient) {// loop through the client connected to the server
						if(client.UserInfo[0][0].equals(loginData.getData().getUserId())) { // check first row first column where user id is stored
							Packet infoPacket = new Packet9Info("User Already Logedin");
							sendData(infoPacket);// send the info object/packet to the user if user is already lodged in
							return;// stop the code here if user already logged in 
						}
					}
					
					
					if(loginData.getData() instanceof Customer) {// check if its a customer is loging in
						for(BillingAccount account : billigAccountDatabase) {// search for their account in database
							if(account.getId().equals(loginData.getData().getUserId())) {
								((Customer)loginData.getData()).setBillingAccount(account);/// ass the account to the user object to be sent to customer
							}
						}
					}
					
					
					Packet Complains = new Packet11List(myComplains(loginData.getData().getUserId()));/// prepare the packet with the list that will be sent to the user
					sendData(Complains);// send the packet to the user
					sendData(loginData);// send the data for the user dashboard
					
					Packet9Info infoPacket = new Packet9Info("Login Sussessfully");// prepare message 
					infoPacket.setThreadIndex(onlineThreads.size()-1); // set the index of the user's thread
					sendData(infoPacket); // send info to client
					this.UserInfo[0][0] = loginData.getData().getUserId();// set the UserId of this client handler to the Id of the loggedin user
					this.UserInfo[0][1] = loginData.getData().getFirstName();// add the user firstname to the handler for chat purposes
					
					Packet onlineClients = new Packet11List(onlineClient());// return list of client and add it to the packet/object
					sendOnlineClientListToAllClients((Packet11List) onlineClients);// send list of clients id to all connected user
					found = true;
					break;
				}
			} 
			
			if(!found) {
				Packet error = new Packet10Error("Invalid Id or Password");// create a message/packer/object
				sendData(error);// send the info object/packet/message to the user
			}
				
		}
		
		private void LogoutHandler(Packet02Logout data) {
			putUserOffline(data); // remove user from the server 
		} 
		
		private void ComplainHandler(Packet04Complain data) {
			complainDatabase.add(data.getData());//add the complain to the database
			Packet infoPacket = new Packet9Info("Complain Recieved");// create a message/packer/object
			sendData(infoPacket);// send the info object/packet/message to the user

			Packet Complains = new Packet11List(myComplains(data.getData().getUserId()));/// prepare the packet with the list that will be sent to the user
			sendData(Complains);// send the packet to the user
		}
		
		private List<Complain> myComplains(String userId){// get complain from database
			List<Complain> list = new ArrayList<Complain>();//create an arraylist to store the complains to be sent back to the user
			for (Complain com : complainDatabase) {// loop through the list of complain from the database
				if (com.getUserId().equals(userId)) {// check if the user id match the user id in the database for the complain
					list.add(com);// if the id match, store the complain in the arraylist that will be sent to the user
				}
			}
			return list;
		}
		
		private List<String[][]> onlineClient(){// a list of active client to send to server for chat
			List<String[][]>  list = new ArrayList<String[][]>();
			for (ClientHandler client : onlineClient) {  
				if(!client.UserInfo[0][0].equals(""))
					list.add(client.UserInfo);
			}
			
			return list;
		}
		
		private void ChatHandler(Packet03Chat data) { // handle chat
			sendChatToSendingAndReceivingClients(data);
			
		}

		private void putUserOffline(Packet02Logout data) { // remove the user id from the client handler
			try {
				
				int index = getUserIndex(data.getData());
				onlineClient.get(index).UserInfo[0][0] = ""; // set the userID of the client handler to an empty string so that user can re-login with the same handler
				onlineClient.get(index).UserInfo[0][1] = ""; // set the firstname of the client handler to an empty string so that user can re-login with the same handler
				
				sendData(data);// send the packet to the user for them to use it to take necesasary action
				sendData(new Packet9Info("Logout Successfully"));// send the packet to the user 
				Packet onlineClients = new Packet11List(onlineClient());// return list of client and add it to the packet/object
				sendOnlineClientListToAllClients((Packet11List) onlineClients);// send list of clients id to all connected user
				
			} catch (IndexOutOfBoundsException e) {
				sendData(new Packet10Error("Logout Error " + e.getMessage()));// send the packet to the user 
			}
		}

		private int getUserIndex(String userId) throws IndexOutOfBoundsException {// get the index for the user in the online list
			int index = 0;
			for (ClientHandler client : onlineClient) {
				if (client.UserInfo[0][0].equals(userId)) {
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
			data.writeData(client);// only send the message to the sender or the reciever
		}
	}
	
}
