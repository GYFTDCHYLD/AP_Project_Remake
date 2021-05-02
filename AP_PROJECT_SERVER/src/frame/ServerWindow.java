package frame;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


import image.loadImages;
import network.*;

public class ServerWindow extends JFrame implements ActionListener{
	private static int port;
	private static JLabel background;
	private static JLabel StartDate;
	private loadImages loadImages;
	private static JDesktopPane serverDash;
	private static JLabel Status;
	private static JLabel IpAddress;
	private static JLabel connectedClient;
	
	private static JLabel movingLabel;
	
	private JButton buton;
	
	public ServerWindow(int port){
		super("Microstar Server");
		this.setSize(500, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		this.port = port;
		
		loadImages = new loadImages();
		loadImages.init();
		
		serverDash = new JDesktopPane();
		getServerDash().setBounds(0, 0, 500, 400);
		getServerDash().setBackground(Color.WHITE);
		getServerDash().setVisible(true);
		add(getServerDash());
		
		StartDate = new JLabel("");
		getStartDate().setBounds(50, 10, 500, 25);
		getStartDate().setForeground(Color.BLUE);
		getStartDate().setFont(new Font("arial", Font.BOLD, 16));
		getStartDate().setVisible(true);
		getServerDash().add(getStartDate());
		
		Status = new JLabel("Status: OFFLINE");
		getStatus().setBounds(180, 50, 150, 25);
		getStatus().setForeground(Color.BLACK);
		getStatus().setFont(new Font("arial", Font.BOLD, 16));
		getStatus().setVisible(true);
		getServerDash().add(getStatus());
		
		IpAddress = new JLabel("");
		getIpAddress().setBounds(140, 80, 300, 50);
		getIpAddress().setForeground(Color.BLACK);
		getIpAddress().setFont(new Font("arial", Font.BOLD, 20));
		getIpAddress().setVisible(true);
		getServerDash().add(getIpAddress()); 
		
		connectedClient = new JLabel();
		getConnectedClient().setBounds(140, 120, 300, 50);
		getConnectedClient().setForeground(Color.BLACK);
		getConnectedClient().setFont(new Font("arial", Font.BOLD, 20));
		getConnectedClient().setVisible(true);
		getServerDash().add(getConnectedClient());
		
		movingLabel = new JLabel("....");
		getMovingLabel().setBounds(40, 130, 100, 150);
		getMovingLabel().setForeground(Color.BLACK);
		getMovingLabel().setFont(new Font("arial", Font.TRUETYPE_FONT, 50));
		getMovingLabel().setVisible(false);
		getServerDash().add(getMovingLabel());
		
		buton = new JButton("START"); 
		buton.setBounds(200, 300, 100, 30);
		buton.setForeground(Color.BLACK);
		buton.setFont(new Font("arial", Font.BOLD, 20));
		buton.addActionListener(this);
		buton.setVisible(true);
		getServerDash().add(buton);
		
		background = new JLabel(new ImageIcon(loadImages.ServerWindowBackground)); 
		background.setHorizontalAlignment(SwingConstants.TRAILING);
		background.setBounds(0, 0,500, 400);
		getServerDash().add(background);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "START":
						buton.setText("STOP");
						ServerThread  = new Thread(ServerThread);
						ServerThread.start();
						animate();
						
			break;
		case "STOP":	
						Server.serverExit();
			break;

		default:
						break;
		}
		
	}
	
	public void animate(){ 
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			String direction = "right";
			int Timing = 0;
			int xAxis = 40;
			public void run() {
					Timing ++;
				if(Timing%2 == 0) {
					if(direction.equals("right"))
						xAxis++;
					else {
						xAxis--;
					}
					getMovingLabel().setBounds(xAxis, 100, 100, 150);	
				}
				if(xAxis == 400)
					direction = "left";
				if(xAxis == 40)
					direction = "right";
				if(Timing == 10)// control the timing variable to prevent it from reaching a very large number 
					Timing = 0;
				
			}	
		}, 0, 5);
	}
	
	
	public static String Ip(){ 
		String  address = ""; 
		try {
			Socket Socket = new Socket();
			Socket.connect(new InetSocketAddress("google.com", 80)); 
			InetAddress ip = Socket.getLocalAddress();
			address = ip.toString().replaceAll("/", "");
		}catch(UnknownHostException e) {
			address = "127.0.0.1";
			return address;
		} catch (IOException e) {
			
		}
		return address;
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


	public static JLabel getMovingLabel() {
		return movingLabel;
	}


	public static JLabel getIpAddress() {
		return IpAddress;
	}


	public static JLabel getStartDate() {
		return StartDate;
	}

	public static Thread ServerThread = new Thread() { 
		public void run() {
			new Server(port);// create the server with the port number
		}		
	};
	
}
