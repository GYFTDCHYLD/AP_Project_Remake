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
	private List<User> connectedUsers = new ArrayList<User>(); 
	
	public Server() {
		
		try {
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
			} catch (IOException | NullPointerException e) {
				System.out.println(" error sending data to client " + e.getMessage());
			}
		} 
		
		public Object readData() {
			Object packet = null; 
			try {
				packet = objIs.readObject();
			} catch (IOException e) {
				System.out.println(" error recieving data from server");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
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
			System.out.println(packet.getData().getUserId());
			sendData(packet);
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
	
}
