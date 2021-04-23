package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import packet.Packet;
import packet.Packet03Chat;
import packet.Packet07User;
import packet.Packet9Info;
import packet.Packet.PacketTypes;
import packet.Packet00Register;
import frame.ChatWindow;
import frame.Dashboard;
import frame.LoginWindow;
import frame.MainWindow;


public class Client implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int PORTNUMBER = 8000;
	private Socket connectionSocket; 
	private  ObjectOutputStream objOs;
	private ObjectInputStream objIs;
	private static MainWindow mainWindow;  
	
	

	
	public Client(MainWindow main) {
		mainWindow = main;
		this.createConnection();
		this.configureStreams();
		
	}
	

	private void createConnection() {
		try {
			connectionSocket = new Socket(InetAddress.getLocalHost(), PORTNUMBER);
		}
		catch(IOException e) {
			parsePacket(new Packet9Info("Not Connected to server:  " + e.getMessage()));
		}
	}
	
	private void configureStreams() {
		try {
			objIs = new ObjectInputStream(connectionSocket.getInputStream());
			objOs = new ObjectOutputStream(connectionSocket.getOutputStream());
		}
		catch(IOException e) {
			parsePacket(new Packet9Info("Not Connected to server:  " + e.getMessage()));
		}
	}	

	
	public void closeConnection() {
		try {
			objOs.close();
			objIs.close();
			connectionSocket.close();
		}
		catch(IOException e) {
			System.err.println("error " + e.getMessage());
		}
	}
	
	
	public void sendData(Packet data) { 
		try {
			objOs.writeObject(data);
		} catch (IOException e) {
			System.out.println(" error sending data to server " + e.getMessage());
		}catch(NullPointerException e) {
			System.out.println(" error sending data to server " + e.getMessage());
		}
	} 
	
	public Packet readData() { 
		Packet data = null;  
		try {
			data = (Packet) objIs.readObject();
		} catch (IOException e) {
			System.out.println("error recieving data from server " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("error recieving data from server  " + e.getMessage()); 
		}catch(NullPointerException e) {
			System.err.println("error recieving data from server " + e.getMessage());
		}
		return data;
		 
	}	

	public void run() {
		while(true) {
			Packet data = readData(); 
			parsePacket(data);
		}
	}
	
	
	private void parsePacket(Packet data) {  
		
		PacketTypes type = Packet.lookupPacket(data.getPacketId()); 
		
		switch(type) {
			default:
			case INVALID: 	System.err.println("Invalid request!!");
				break;
			case INFO:
							InfoHandler((Packet9Info) data); 	  
				break;
			case REGISTER:
							RegisterHandler((Packet00Register) data);	
				break;
			case LOGOUT:
							System.out.println("logout");								 
				break;
			case CHAT: 
							ChatHandler((Packet03Chat) data);			
				break;
			case USERS: 
							UserDataHandler((Packet07User) data);			
				break;
			
		}
	}
	

	private void InfoHandler(Packet9Info data) { 
		mainWindow.setMessageFromServer(data.getData());
		JOptionPane.showMessageDialog(null, data.getData(), "INFO",JOptionPane.INFORMATION_MESSAGE);// display the message sent from server
	}

	private void RegisterHandler(Packet00Register data) {
		LoginWindow LoginWindow = new LoginWindow();
		LoginWindow.getLoginIdField().setText(data.getData().getPassword());// extract the user Id that the server placed in the password field 
		mainWindow.getDesktopPane().add(LoginWindow);
		mainWindow.getDesktopPane().moveToFront(LoginWindow); //move the login window to the front of all component, without doing this, it would ended up behind the background image the was added b4 it  
	}
	
	private void UserDataHandler(Packet07User data) {
		if(data.getData().getFirstName() != null) {
			mainWindow.getDesktopPane().add(new Dashboard(data.getData()));
			((JInternalFrame) mainWindow.getDesktopPane().getComponent(0)).dispose();// remove  login window
		}else 
			JOptionPane.showMessageDialog(null, "Invalid Id or Password", "",JOptionPane.ERROR_MESSAGE);
	}
	
	private void ChatHandler(Packet03Chat data) {  
		mainWindow.getDesktopPane().add(new ChatWindow());
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


	public static MainWindow getMainWindow() {
		return mainWindow;
	}
	
	
}
