package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;

import domain.Complain;
import packet.*;
import packet.Packet.PacketTypes;
import frame.*;


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
		}catch(NullPointerException e) {
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
		Object data = new Object();  
		try {
			data = (Packet) objIs.readObject();
		} catch (IOException e) {
			System.out.println("error recieving data from server " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("error recieving data from server  " + e.getMessage()); 
		}catch(NullPointerException e) {
			System.err.println("error recieving data from server " + e.getMessage());
		}
		return (Packet) data;
		 
	}	

	public void run() {
		while(true) {
			Packet data = readData(); 
			parsePacket(data);
		}
	}
	
	
	private void parsePacket(Packet data) {  
		try {
			PacketTypes type = Packet.lookupPacket(data.getPacketId()); 
			
			switch(type) {
			
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
				case INFO:
								InfoHandler((Packet9Info) data); 	  
					break;
				case ERROR:
								ErrorHandler((Packet10Error) data);  	  
					break;
				case LIST: 
								ListHandler((Packet11List) data); 	  	
					break;
				default:
				
			}
		}catch(Exception e) {
			
		}
	}
	


	private void InfoHandler(Packet9Info info) { 
		
		switch (info.getInfo()) {
		case "Exit": 
									System.exit(0);// end the program if an exit message is recieved from the server				
			break;
		case "Complain Recieved": 
									((Dashboard)MainWindow.getDesktopPane().getComponent(0)).setComplainText("");//clear the text if server recieved the message	
									((Dashboard)MainWindow.getDesktopPane().getComponent(0)).setComplainCategoryIndex(0);	// reset the complain category if server recieve complain
			break;

		default:
			break;
		}
		JOptionPane.showInternalMessageDialog(MainWindow.getDesktopPane(),info.getData(), "From Server",JOptionPane.INFORMATION_MESSAGE);// display the message sent from server
	}

	private void ErrorHandler(Packet10Error error) {
		JOptionPane.showInternalMessageDialog(MainWindow.getDesktopPane(),error.getData(), "From Server",JOptionPane.ERROR_MESSAGE);// display the message sent from server
		switch (error.getData()) {
			case "Server Ended": 
								System.exit(0);// end the program if an exit message is recieved from the server
				break;
	
			default:
				break;
		}	
	}
	
	private void RegisterHandler(Packet00Register data) {
		LoginWindow LoginWindow = new LoginWindow();
		LoginWindow.getLoginIdField().setText(data.getData().getPassword());// extract the user Id that the server placed in the password field 
		MainWindow.getDesktopPane().add(LoginWindow);
		MainWindow.getDesktopPane().moveToFront(LoginWindow); //move the login window to the front of all component, without doing this, it would ended up behind the background image the was added b4 it  
	}
	
	private void UserDataHandler(Packet07User data) {
		MainWindow.setLoginID(data.getData().getUserId());//logout user
		MainWindow.getDesktopPane().removeAll();// remove  login window
		Dashboard myDashboard = new Dashboard(data.getData());
		MainWindow.getDesktopPane().add(myDashboard);
		MainWindow.getDesktopPane().add(MainWindow.background);
		MainWindow.getDesktopPane().moveToFront(myDashboard);
	}
	
	private void LogoutHandler(Packet02Logout data) {
		MainWindow.getDesktopPane().removeAll();//remove  dashboard window
		MainWindow.getDesktopPane().add(new LoginWindow());// add a new login window
		MainWindow.getDesktopPane().add(MainWindow.background);
		
	}
	
	private void ChatHandler(Packet03Chat data) { 
		((Dashboard)MainWindow.getDesktopPane().getComponent(0)).append(data);
	}
	
	
	private void ListHandler(Packet11List data) {
		if(data.getData().get(0) instanceof Complain) {// check if its a list of complains being sent over 
			MainWindow.setComplain((List<Complain>)data.getData());
			((Dashboard)MainWindow.getDesktopPane().getComponent(0)).createTable();// update table in realtime
			System.out.println("List of complain recieved from server");
		}else if(data.getType().matches("ID's")) {
			MainWindow.setClientHandlerId((long) data.getData().get(0)); 
			MainWindow.setThreadHandlerId((long) data.getData().get(1));
			System.out.println("List of Id's clients recieved from server");
		}else if(data.getType().matches("Online Clients")){
			MainWindow.setOnlineClient((List<String[][]>) data.getData());
			System.out.println("List of online clients recieved from server");
		}else if(data.getType().matches("Technitions")) {
			MainWindow.setTechnitions((List<String[][]>) data.getData()) ;
			System.out.println("List of Technitions recieved from server");
		}
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
