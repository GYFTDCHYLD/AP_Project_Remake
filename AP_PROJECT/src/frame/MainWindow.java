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
	private JMenu editJMenu;
	private JMenuItem menuItemChat; 
	private JMenuItem menuItemSave;
	private MenuItem menuAbout;
	private MenuItem menuOpen;
	private MenuItem menuClose;
	private TrayIcon trayIcon;
	private PopupMenu popup;
	private JLabel background;
	private loadImages loadImages; 
	
	public static Client ClientSocket; 
	
	
	
	
	public MainWindow(){
		initializeComponent();
		addMenuItemsToPopup();
		addMenuItemsToMenu();
		addMenusToMenuBar();
		addComponentsToWindow();
		registerListeners();
		setWindowProperties();
		ClientSocket = new Client();
		ClientSocket.start();
	}

	public void initializeComponent() {
		loadImages = new loadImages();
		loadImages.init();
		desktopPane = new JDesktopPane();
		menuBar = new JMenuBar();
		Menu = new JMenu("Menu");
		Menu.setMnemonic(KeyEvent.VK_A);
		editJMenu = new JMenu("Edit");
		editJMenu.setMnemonic(KeyEvent.VK_S);
		menuItemChat = new JMenuItem("Chat");
		menuItemSave = new JMenuItem("Save Item");
		menuItemSave.setToolTipText("Saves the active document");
		menuAbout = new MenuItem("About");
		menuOpen = new MenuItem("Open");
		menuClose = new MenuItem("Close");
		popup = new PopupMenu("Popup");
		trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage("images/app.png"));
		trayIcon.setPopupMenu(popup); 
		
		background = new JLabel();
		background.setHorizontalAlignment(SwingConstants.CENTER);
		//background.setIcon(new ImageIcon(loadImages.mainBackground)); 
		background.setBounds(0, 0,1020, 700);
	}
	
	public void addMenuItemsToPopup() {
		popup.add(menuAbout);
		popup.add(menuOpen);
		popup.add(menuClose);
	}
	
	public void addMenuItemsToMenu() {
		Menu.add(menuItemChat);
		editJMenu.add(menuItemSave);
	}
	
	public void addMenusToMenuBar() {
		menuBar.add(Menu);
		menuBar.add(editJMenu);
	}
	
	public void addComponentsToWindow() {
		desktopPane.add(new LoginWindow()); 
		this.add(background);
		this.add(desktopPane);
		
	}
	
	public void registerListeners() {
		
		
		menuItemChat.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				desktopPane.add(ClientSocket.getChat()); 
			}
		});
		
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
				JOptionPane.showMessageDialog(null, "info about this awsome app");
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
		this.setSize(1020, 700);
		this.setResizable(true);
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
	
}
