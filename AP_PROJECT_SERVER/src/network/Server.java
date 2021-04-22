package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import domain.Login;
import domain.User;
import domain.Logout;
import packet.Packet;
import packet.Packet00Register;
import packet.Packet01Login;
import packet.Packet02Logout;
import packet.Packet03Chat;
import packet.Packet07User;
import packet.Packet.PacketTypes;

public class Server {

	private ServerSocket serverSocket;
	private Socket connectionSocket;
	private Calendar date;
	private int clientCount;
	private List<User> connectedUsers; 
	
	public Server() {
		
		try {
			connectedUsers = new ArrayList<User>(); 
			User User = new User("C123","Craig", "Reid", "12345");
			addConnection(User); 
			User = new User("A123","Ashari", "Jones", "12345");
			addConnection(User); 
			this.serverSocket = new ServerSocket(8000);
		}
		catch(Exception ex) {
			ex.printStackTrace();
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
				e.printStackTrace();
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
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		public void sendData(Object packet) {
			try {
				objOs.writeObject(packet);
			} catch (IOException e) {
				System.out.println(" error sending data to client " + e.getMessage());
			}catch(NullPointerException e) {
				System.out.println(" error sending data to client " + e.getMessage());
			}
		} 
		
		public Object readData() {
			Object packet = null; 
			try {
				packet = objIs.readObject();
			} catch (IOException e) {
				System.out.println("error recieving data from client " + e.getMessage());
			} catch (ClassNotFoundException e) {
				System.out.println("error recieving data from client " + e.getMessage());
			}
			return packet;
			
		}
		
		@Override
		public void run() {
			
			while(true) {
				Object packet = readData();
				parsePacket(packet);	
			}
			
		}
		
		private void parsePacket(Object data) { 
			
			PacketTypes type = Packet.lookupPacket(((Packet) data).getPacketId()); 
			
			switch(type) {
				default:
				case INVALID: 	System.err.println("Invalid request!!");
					break;
				case REGISTER:
								RegisterHandler((Packet00Register) data);	
					break;
				case LOGIN:
								LoginHandler((Packet01Login) data) ;
					break;
				case LOGOUT:
								System.out.println("logout");								 
					break;
				case CHAT: 
								ChatHandler((Packet03Chat) data);	 	
					break;
				
			}
		}

		private void RegisterHandler(Packet00Register data) {
			sendData(data);
		}
		

		private void LoginHandler(Packet01Login packet) {
			Packet07User loginData = new Packet07User(new User());
			System.out.println(packet.getData().getUserId());
			for (User user : connectedUsers) {
				if(user.getUserId().equals(packet.getData().getUserId()) && user.getPassword().equals(packet.getData().getPassword())) {
					loginData = new Packet07User(user);
				}
			}
			sendData(loginData);
			//User User = new User();
			//User.setUserId(String.valueOf(clientCount+1));
			//addConnection(User); 
		}
		
		private void ChatHandler(Packet03Chat packet) { 
			sendData(packet);
		}
		
	}


	public void sendData(Packet03Chat packet03Chat) {
		// TODO Auto-generated method stub
		
	}


	public void sendData(Packet00Register packet00Register) { 
		// TODO Auto-generated method stub
		
	}


	public void sendData(Login data) {
		// TODO Auto-generated method stub
		
	}


	public void sendData(Logout data) {
		// TODO Auto-generated method stub
		
	}


	public void sendData(Packet07User packet07User) {
		// TODO Auto-generated method stub
		
	}

	public void addConnection(User user) { 
		boolean alreadyConnected = false;
		for (User uzr : this.connectedUsers) { 
			if (user.getUserId().equalsIgnoreCase(uzr.getUserId())) {
				alreadyConnected = true;
			}
		}
		if (!alreadyConnected) {
			this.connectedUsers.add(user);
		}
	}

	public void removeConnection(Packet02Logout packet) {
		try {
			this.connectedUsers.remove(getPlayerMPIndex(packet.getData().getUserId()));
			packet.writeData(this);
		} catch (IndexOutOfBoundsException e) {

		}
	}

	public User getPlayerMP(String userId) {
		for (User user : this.connectedUsers) {
			if (user.getUserId().equals(userId)) {
				return user;
			}
		}
		return null;
	}

	public int getPlayerMPIndex(String userId) throws IndexOutOfBoundsException {
		int index = 0;
		for (User user : this.connectedUsers) {
			if (user.getUserId().equals(userId)) {
				break;
			}
			index++;
		}
		return index;
	}

	public void sendDataToAllClients(Packet03Chat data) {
		for (User user : connectedUsers) {
			sendData(data);
		}
	}

}
