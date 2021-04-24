package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import domain.Complain;
import packet.Packet;
import packet.Packet03Chat;
import packet.Packet07User;
import packet.Packet10List;
import packet.Packet9Info;
import packet.Packet.PacketTypes;
import packet.Packet00Register;
import packet.Packet02Logout;
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
	
	

	
	public Client() {
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
			objOs.flush();
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
			case INVALID: 	System.err.println("Invalid request!!");
				break;
			case INFO:
							InfoHandler((Packet9Info) data); 	  
				break;
			case REGISTER:
							RegisterHandler((Packet00Register) data);	
				break;
			case LOGOUT:
							LogoutHandler((Packet02Logout) data);	 							 
				break;
			case CHAT: 
							ChatHandler((Packet03Chat) data);			
				break;
			case USERS: 
							UserDataHandler((Packet07User) data);			
				break;
			case LIST: 
							ListHandler((Packet10List) data); 	  	
				break;
			default:
			
		}
	}
	

	


	private void InfoHandler(Packet9Info info) {  
		JOptionPane.showMessageDialog(null,info.getData(), "From Server",JOptionPane.INFORMATION_MESSAGE);// display the message sent from server
		MainWindow.setMessageFromServer(info.getData());
	}

	private void RegisterHandler(Packet00Register data) {
		LoginWindow LoginWindow = new LoginWindow();
		LoginWindow.getLoginIdField().setText(data.getData().getPassword());// extract the user Id that the server placed in the password field 
		MainWindow.getDesktopPane().add(LoginWindow);
		MainWindow.getDesktopPane().moveToFront(LoginWindow); //move the login window to the front of all component, without doing this, it would ended up behind the background image the was added b4 it  
	}
	
	private void UserDataHandler(Packet07User data) {
		if(data.getData().getFirstName() != null) {
			MainWindow.setLoginID(data.getData().getUserId());//logout user
			MainWindow.getDesktopPane().removeAll();// remove  login window
			MainWindow.getDesktopPane().add(new Dashboard(data.getData()));
			MainWindow.getDesktopPane().add(MainWindow.background);
		}else 
			JOptionPane.showMessageDialog(null, "Invalid Id or Password", "",JOptionPane.ERROR_MESSAGE);
	}
	
	private void LogoutHandler(Packet02Logout data) {
		//((JInternalFrame) MainWindow.getDesktopPane().getComponent(0)).dispose();// remove  dashboard window
		MainWindow.getDesktopPane().removeAll();//remove  dashboard window
		MainWindow.getDesktopPane().add(new LoginWindow());// add a new login window
		MainWindow.getDesktopPane().add(MainWindow.background);
		
	}
	
	private void ChatHandler(Packet03Chat data) { 
		MainWindow.getChat().add(data.getData());
		MainWindow.getDesktopPane().add(new ChatWindow());
	}
	
	
	private void ListHandler(Packet10List data) {
		MainWindow.setComplain((List<Complain>)data.getData());
		System.out.println("complain recieved from server");
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
	
}
