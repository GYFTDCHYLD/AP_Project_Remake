package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import domain.Complain;
import domain.Customer;
import domain.Employee;
import domain.User;
import packet.Packet;
import packet.Packet00Register;
import packet.Packet01Login;
import packet.Packet02Logout;
import packet.Packet03Chat;
import packet.Packet04Complain;
import packet.Packet07User;
import packet.Packet10List;
import packet.Packet9Info;
import packet.Packet.PacketTypes;

public class Server{

	private ServerSocket serverSocket;
	private Socket connectionSocket;
	private Calendar date;
	private int clientCount;
	private List<User> Users; 
	private List<User> connectedUsers; 
	private List<Complain> complain; 
	private List<Packet03Chat> chat; 
	
	
	public Server() {
		
		try {
			Users = new ArrayList<User>(); 
			connectedUsers = new ArrayList<User>(); 
			complain = new ArrayList<Complain>();
			chat = new ArrayList<Packet03Chat>();
			
			User User = new Employee("C123", "Mr", "Craig", "Reid", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Technitian");  
			Users.add(User);
			User = new Employee("C124", "Mr", "Craig", "Reid", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", "Customer"); 
			Users.add(User);
			this.serverSocket = new ServerSocket(8000);
		}
		catch(Exception e) {
			System.err.println("error " + e.getMessage());
		}
		this.date = Calendar.getInstance();
		this.clientCount = 0;
		
		System.out.println("Server has started at "+ date.getTime());	

		while(true) {
			try {
				connectionSocket = serverSocket.accept();
				date = Calendar.getInstance();
				clientCount+= 1;
				System.out.println("Starting a thread for a client at "+ date.getTime()+"\nClient Count: "+clientCount);
				ClientHandler clientHandler = new ClientHandler(connectionSocket);
				Thread thread = new Thread((Runnable) clientHandler);
				thread.start();
			} catch (IOException e) {
				System.err.println("error " + e.getMessage());
			}catch(Exception e) {
				System.err.println("error " + e.getMessage());
			}
		}
	}
	
	
	public class ClientHandler implements Runnable{

		Socket clientHandlerSocket;
		ObjectOutputStream objOs;
		ObjectInputStream objIs;

		public ClientHandler (Socket socket) {
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
		
		public Packet readData() { 
			Packet data = null; 
			try {
				data = (Packet) objIs.readObject();
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
				Packet data = readData(); 
				parsePacket(data);	
			}
			
		}
		
		private void parsePacket(Packet data) { 
			
			PacketTypes type = Packet.lookupPacket(data.getPacketId()); 
			
			switch(type) {
				case INVALID: 	System.err.println("Invalid request!!");
					break;
				case REGISTER:
								RegisterHandler((Packet00Register) data);	
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
				default:
				
			}
		}


		private void RegisterHandler(Packet00Register data) {
			String id = (data.getData().getFirstName()).substring(0,1) + "34" + Users.size();//create user Id Using Fist letter of first name plus 34 plus the amount of user;
			User User = new Customer(id,data.getData().getNameTitle(), data.getData().getFirstName(), data.getData().getLastName(), data.getData().getPassword());
			Users.add(User);
			Packet infoPacket = new Packet9Info("Sussessfully Registered");
			((Packet00Register)data).getData().setPassword(id);// replace the pasword in the object with the New User ID 
			sendData(data);// send the the object to the user to extract the User ID
			sendData(infoPacket);// send the info object/packet to the user
		}
		

		private void LoginHandler(Packet01Login data) { 
			Packet07User loginData = new Packet07User(new User());
			
			for (User user : Users) {//check the database for the user
				if(user.getUserId().equals(data.getUserId()) && user.getPassword().equals(data.getPassword())) {
					loginData = new Packet07User(user);
					
					for(User conected : connectedUsers) {// loop through the users connected to the server
						if(conected.getUserId().equals(loginData.getData().getUserId()) && conected.getPassword().equals(loginData.getData().getPassword())) {
							Packet infoPacket = new Packet9Info("User Already Logedin");
							sendData(infoPacket);// send the info object/packet to the user if user is already lodged in
							return;// stop the code here if user already logged in 
						}
					}
				}
			}
			
			onlineUser(loginData.getData());// put the user online if they are not already logedin
			sendData(loginData);// send the data for the user dashboard
			 
			Packet Complains = new Packet10List(myComplains(loginData.getData().getUserId()));/// prepare the packet with the list that will be sent to the user
			sendData(Complains);// send the packet to the user
		}
		
		private void LogoutHandler(Packet02Logout data) {
			putUserOffline(data); // remove user from the server
			sendData(data);// send the packet to the user  
		} 
		
		private void ComplainHandler(Packet04Complain data) {
			complain.add(data.getData());//add the complain to the database
			Packet infoPacket = new Packet9Info("Complain Recieved");// create a message/packer/object
			sendData(infoPacket);// send the info object/packet/message to the user

			Packet Complains = new Packet10List(myComplains(data.getData().getUserId()));/// prepare the packet with the list that will be sent to the user
			sendData(Complains);// send the packet to the user
			
		}
		
		private List<Complain> myComplains(String userId){
			List<Complain> list = new ArrayList<Complain>();//create an arraylist to store the complains to be sent back to the user
			for (Complain com : complain) {// loop through the list of complain from the database
				if (com.getUserId().equals(userId)) {// check if the user id match the user id in the database for the complain
					list.add(com);// if the id match, store the complain in the arraylist that will be sent to the user
				}
			}
			return list;
		}
		
		private void ChatHandler(Packet03Chat data) { 
			chat.add(data.getData());
			sendData(data);
		}
		
		
		public void onlineUser(User user) {  
			boolean alreadyConnected = false;
			for (User uzr : connectedUsers) { 
				if (user.getUserId().equalsIgnoreCase(uzr.getUserId())) {
					alreadyConnected = true;
				}
			}
			if (!alreadyConnected) {
				connectedUsers.add(user);
			}
		}

		public void putUserOffline(Packet02Logout data) { 
			try {
				connectedUsers.remove(getPlayerMPIndex(data.getData()));
				sendData(data);
			} catch (IndexOutOfBoundsException e) {
				System.err.println("error " + e.getMessage());
			}
		}

		public User getPlayerMP(String userId) {
			for (User user : connectedUsers) {
				if (user.getUserId().equals(userId)) {
					return user;
				}
			}
			return null;
		}

		public int getPlayerMPIndex(String userId) throws IndexOutOfBoundsException {
			int index = 0;
			for (User user : connectedUsers) {
				if (user.getUserId().equals(userId)) {
					break;
				}
				index++;
			}
			return index;
		}
	}

	
	
	public void sendData(Packet data) { 
		data.writeData(this); 
	}



	public void sendDataToAllClients(Packet03Chat data) {
		for (User user : connectedUsers) {
			this.sendData(data); 
		}
	}
}
