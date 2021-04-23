package frame;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;

import org.w3c.dom.UserDataHandler;

import image.loadImages;
import packet.Packet00Register;
import packet.Packet01Login;
import packet.Packet07User;
import domain.Employee;
import domain.Login;
import domain.User;


public class Dashboard extends JInternalFrame implements ActionListener{
	private JTabbedPane tablePane;
	private JTable table; 
	private JTableHeader tableHeader; 
	private User user;
	
	private JLabel background;
	
	
	public void intializeComponent() {
		
		tablePane = new JTabbedPane(); 
		tablePane.setBounds(10, 50, 680, 450); 
		tablePane.setBorder(new LineBorder(java.awt.Color.WHITE, 1));
		tablePane.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		table = new JTable();
		table.setForeground(Color.WHITE);
		table.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		

		tableHeader = new JTableHeader();
		tableHeader.setForeground(Color.WHITE);
		tableHeader.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		background = new JLabel();
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setIcon(new ImageIcon(loadImages.dashboardBackground)); 
		background.setBounds(0, 0,700, 500);
			
		
	}
	public void addComponentsToWindow(){
		table.add(tableHeader);
		tablePane.add(table);
		getContentPane().add(tablePane);
		getContentPane().add(background);
	}
	
	public void setWindowsProperties() {
		setLayout(null);
		this.setBounds(-10, 0, 720, 560);
		this.setVisible(true); 
	}
	
	public Dashboard(User user) {  
		super("",false,false,false,true); 
		if(user instanceof Employee)
			this.title = ((Employee) user).getJobTitle() + " Dashboard";
		else 
			this.title = "Customer Dashboard";
		this.user = user;
		intializeComponent() ;
		addComponentsToWindow();
		setWindowsProperties();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
}
