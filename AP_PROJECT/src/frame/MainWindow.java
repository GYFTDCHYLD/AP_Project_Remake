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

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import image.loadImages;
import network.Client;

public class MainWindow extends JFrame{
	private static JDesktopPane desktopPane;

	private JMenuBar menuBar;
	private JMenu Menu; 
	private MenuItem menuAbout;
	private MenuItem menuOpen;
	private MenuItem menuClose;
	private TrayIcon trayIcon;
	private PopupMenu popup;
	private JLabel background;
	private loadImages loadImages; 
	private static LoginWindow LoginWindow; 
	public static Client ClientSocket; 
	
	
	
	
	public MainWindow(){
		initializeComponent();
		addMenuItemsToPopup();
		addMenuItemsToMenu();
		addMenusToMenuBar();
		addComponentsToWindow();
		registerListeners();
		setWindowProperties();
		ClientSocket = new Client(this); 
		ClientSocket.run(); 
	}

	public void initializeComponent() {
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
		background.setBounds(0, 0,700, 700);
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
				System.exit(0); 
			}
		});
		
		menuAbout.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Michrstar Cable Company App");
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
		this.setSize(700, 700);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	public JDesktopPane getDesktopPane() {
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
}
