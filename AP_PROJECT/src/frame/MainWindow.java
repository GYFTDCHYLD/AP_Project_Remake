package frame;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import domain.Complain;
import image.loadImages;
import network.Client;
import packet.Packet9Info;

public class MainWindow extends JFrame{
	private static JDesktopPane desktopPane;

	private JMenuBar menuBar;
	private JMenu Menu; 
	private MenuItem menuAbout;
	private MenuItem menuOpen;
	private MenuItem menuClose;
	private TrayIcon trayIcon;
	private PopupMenu popup;
	public static JLabel background;
	private loadImages loadImages; 
	private static LoginWindow LoginWindow; 
	public static Client ClientSocket;
	private static int threadIndex; 
	private static String loginID;

	private static List<Complain> complain;  
	private static List<String[][]>  onlineClient; 
	
	
	
	
	
	public MainWindow(){
		initializeComponent();
		addMenuItemsToPopup();
		addMenuItemsToMenu();
		addMenusToMenuBar();
		addComponentsToWindow();
		registerListeners();
		setWindowProperties();
		ClientSocket = new Client(); 
		ClientSocket.run(); 
	}

	public void initializeComponent() {
		setComplain(new ArrayList<Complain>());
		setOnlineClient(new ArrayList<String[][]>()); 
		loadImages = new loadImages();
		loadImages.init();
		desktopPane = new JDesktopPane();
		menuBar = new JMenuBar();
		Menu = new JMenu("Menu");
		Menu.setMnemonic(KeyEvent.VK_A);
		menuAbout = new MenuItem("About");
		menuOpen = new MenuItem("Open");
		menuClose = new MenuItem("Close");
		popup = new PopupMenu("Popup");
		trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage("images/app.png"));
		trayIcon.setPopupMenu(popup); 
		
		background = new JLabel();
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setIcon(new ImageIcon(loadImages.mainBackground)); 
		background.setBounds(0, 0,800, 700);
	}
	
	public void addMenuItemsToPopup() {
		popup.add(menuAbout);
		popup.add(menuOpen);
		popup.add(menuClose);
	}
	
	public void addMenuItemsToMenu() {
		
	}
	
	public void addMenusToMenuBar() {
		
	}
	
	public void addComponentsToWindow() {
		LoginWindow = new LoginWindow();
		desktopPane.add(LoginWindow); 
		desktopPane.add(background); 
		this.add(desktopPane); 
		
	}
	
	public void registerListeners() {
		
		menuClose.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SystemTray.getSystemTray().remove(trayIcon);
				Packet9Info Packet = new Packet9Info("killThread");// set the command/info
				Packet.setThreadIndex(threadIndex);//set the index of the thread to be killed
				Packet.writeData(MainWindow.getClientSocket()); 
				System.exit(0);// exit program
			} 
		});
		
		menuAbout.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Micro Star Cable Company App");
			}
		});
		
		menuOpen.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SystemTray.getSystemTray().remove(trayIcon);
				setVisible(true); 
			}
		});
		
		this.addWindowListener( new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					SystemTray.getSystemTray().add(trayIcon);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}); 
		
	}
	
	public void setWindowProperties() {
		this.setJMenuBar(menuBar);
		this.setSize(750, 700);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	public static JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	public static Client getClientSocket() {
		return ClientSocket;
	}

	public static String hashPasword(String password){
		try {
			MessageDigest m;
			m = MessageDigest.getInstance("SHA256");
			m.reset();
			m.update(password.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 62 chars.
			while(hashtext.length() < 62 ){
				hashtext = "0"+hashtext;
			}
			return hashtext; 
		
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return password; 
	}

	

	public static List<String[][]> getOnlineClient() {
		return onlineClient;
	}

	public static void setOnlineClient(List<String[][]> Client) {
		onlineClient = Client;
		for(String[][] clientInfo : onlineClient) { 
			if(clientInfo[0][0].equals(loginID))
			onlineClient.remove(clientInfo);//remove this user info from the list to prevent yourself from showing up in the chat
		}
	}

	public static List<Complain> getComplain() {
		return complain;
	}

	public static void setComplain(List<Complain> list) {
		complain = list;
	}

	public static int getThreadID() {
		return threadIndex;
	}

	public static void setThreadID(int threadID) {
		MainWindow.threadIndex = threadID;
	}

	public static String getLoginID() {
		return loginID;
	}

	public static void setLoginID(String loginID) {
		MainWindow.loginID = loginID;
	}

	
}
