package frame;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import image.loadImages;
import network.*;

public class ServerWindow extends JFrame implements ActionListener{
	public static JLabel background;
	public static JLabel StartDate;
	private loadImages loadImages;
	private static JDesktopPane serverDash;
	private static JLabel Status;
	private static JLabel connectedClient;
	
	private JButton buton;

	public ServerWindow(){
		super("Microstar Server");
		this.setSize(500, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		loadImages = new loadImages();
		loadImages.init();
		
		serverDash = new JDesktopPane();
		getServerDash().setBounds(0, 0, 500, 400);
		getServerDash().setBackground(Color.WHITE);
		getServerDash().setVisible(true);
		add(getServerDash());
		
		StartDate = new JLabel("");
		StartDate.setBounds(50, 10, 500, 25);
		StartDate.setForeground(Color.BLUE);
		StartDate.setFont(new Font("arial", Font.BOLD, 16));
		StartDate.setVisible(true);
		getServerDash().add(StartDate);
		
		Status = new JLabel("Status: OFFLINE");
		getStatus().setBounds(170, 60, 150, 25);
		getStatus().setForeground(Color.BLACK);
		getStatus().setFont(new Font("arial", Font.BOLD, 16));
		getStatus().setVisible(true);
		getServerDash().add(getStatus());
		
		connectedClient = new JLabel();
		getConnectedClient().setBounds(120, 100, 300, 50);
		getConnectedClient().setForeground(Color.BLACK);
		getConnectedClient().setFont(new Font("arial", Font.BOLD, 20));
		getConnectedClient().setVisible(true);
		getServerDash().add(getConnectedClient());
		
		buton = new JButton("START"); 
		buton.setBounds(200, 300, 100, 30);
		buton.setForeground(Color.BLACK);
		buton.setFont(new Font("arial", Font.BOLD, 20));
		buton.addActionListener(this);
		buton.setVisible(true);
		getServerDash().add(buton);
		
		background = new JLabel(new ImageIcon(loadImages.CustomerDashboardBackground));
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setBounds(0, 0,800, 700);
		getServerDash().add(background);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "START":
						buton.setText("STOP");
						ServerThread  = new Thread(ServerThread);
						ServerThread.start();
						
			break;
		case "STOP":	
						Server.serverExit();
			break;

		default:
						break;
		}
		
	}

	
	public static JDesktopPane getServerDash() {
		return serverDash;
	}


	public static JLabel getConnectedClient() {
		return connectedClient;
	}


	public static JLabel getStatus() {
		return Status;
	}


	public static Thread ServerThread = new Thread() { 
		public void run() {
			new Server(8000);// create the server with the port number
		}		
	};
	
}
