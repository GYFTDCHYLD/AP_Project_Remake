package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import packet.Packet;
import packet.Packet01Login;
import packet.Packet03Chat;
import packet.Packet.PacketTypes;
import packet.Packet00Register;
import frame.ChatWindow;


public class Client extends Thread implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int PORTNUMBER = 8000;
	private Socket connectionSocket;
	private  ObjectOutputStream objOs;
	private ObjectInputStream objIs;
	private ChatWindow ChatWindow;
	
	
	
	public Client() {
		ChatWindow = new ChatWindow();
		this.createConnection();
		this.configureStreams();
	}
	
	private void createConnection() {
		try {
			connectionSocket = new Socket("127.0.0.1",PORTNUMBER);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void configureStreams() {
		try {
			objIs = new ObjectInputStream(connectionSocket.getInputStream());
			objOs = new ObjectOutputStream(connectionSocket.getOutputStream());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}	

	
	public void closeConnection() {
		try {
			objOs.close();
			objIs.close();
			connectionSocket.close();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void sendData(Object packet) {
		try {
			objOs.writeObject(packet);
		} catch (IOException | NullPointerException e) {
			System.out.println(" error sending data to server");
			//e.printStackTrace();
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
							RegisterHandler((Packet00Register) data) ;	  
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
		System.out.println("registered successfully");
		
	}

	private void LoginHandler(Packet01Login packet) {
		System.out.println("login successfully");
	}
	
	private void ChatHandler(Packet03Chat packet) { 
		ChatWindow.getChatArea().append(packet.getData().getSender() + " " + packet.getData().getResponse() );
	}


	public int getPORTNUMBER() {
		return PORTNUMBER;
	}

	public void setPORTNUMBER(int pORTNUMBER) {
		PORTNUMBER = pORTNUMBER;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ChatWindow getChat() { 
		return ChatWindow;
	}
	
}
