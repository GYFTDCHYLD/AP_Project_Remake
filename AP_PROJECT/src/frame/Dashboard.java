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
import javax.swing.JSeparator;
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
	
	private JLabel profileImage;
	private JLabel name;
	private JSeparator separator;
	private JLabel background;
	
	private JButton makeComplain;
	private JButton viewComplain;
	private JButton viewAccount;
	private JButton payBill;
	
	private JButton assignComplain;
	
	private JButton setVisitDate; 
	
	
	public void intializeComponent() {
		
		tablePane = new JTabbedPane(); 
		tablePane.setBounds(10, 50, 680, 450); 
		tablePane.setBorder(new LineBorder(java.awt.Color.WHITE, 1));
		tablePane.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		profileImage = new JLabel();
		profileImage.setHorizontalAlignment(SwingConstants.CENTER);
		profileImage.setIcon(new ImageIcon(loadImages.dashboardBackground)); 
		profileImage.setBounds(40, 10,50, 50);
		
		name = new JLabel();
		name.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		name.setForeground(Color.WHITE);
		name.setBounds(10, 60,300, 30);
		
		makeComplain = new JButton("Make a Complain");
		makeComplain.setBounds(120, 20,150, 30);
		
		viewComplain = new JButton("View Complains");
		viewComplain.setBounds(270, 20,140, 30);
		
		viewAccount = new JButton("View Account");
		viewAccount.setBounds(410, 20,130, 30);
		
		payBill = new JButton("Pay Bill");
		payBill.setBounds(540, 20,120, 30);
		
		assignComplain = new JButton("Assign a complain");
		assignComplain.setBounds(120, 20,150, 30);
		
		setVisitDate = new JButton("Set visit date");
		setVisitDate.setBounds(120, 20,150, 30);
		
		separator = new JSeparator();
		separator.setBounds(0, 90,720, 50);
		separator.setBackground(Color.WHITE);
		
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
	public void addComponentsToWindow(String user){
		//table.add(tableHeader);
		//tablePane.add(table);
		//getContentPane().add(tablePane);
		if(user.equals("Customer")) {
			getContentPane().add(makeComplain);
			getContentPane().add(viewAccount);
			getContentPane().add(payBill);
		}else if(user.equals("Representative")) {
			getContentPane().add(assignComplain);
		}else {
			getContentPane().add(setVisitDate);
		}
		getContentPane().add(viewComplain);
		getContentPane().add(profileImage);
		getContentPane().add(name);
		getContentPane().add(separator);
		getContentPane().add(background);
	}

	
	public void setWindowsProperties() {
		setLayout(null);
		this.setBounds(-10, 0, 720, 560);
		this.setVisible(true); 
	}
	
	public Dashboard(User user) {  
		super("",false,false,false,true);
		intializeComponent();
		if(user instanceof Employee) { 
			this.title = ((Employee) user).getJobTitle() + " Dashboard";
			addComponentsToWindow(((Employee) user).getJobTitle());
		}else {
			this.title = "Customer Dashboard";
			addComponentsToWindow("Customer");
		}
		
		setWindowsProperties();
		this.user = user;
		if(this.user.getNameTitle().equals("Mr"))
			profileImage.setIcon(new ImageIcon(loadImages.maleAvtar));
		else 
			profileImage.setIcon(new ImageIcon(loadImages.femaleAvtar));
		
		name.setText(this.user.getNameTitle() + ". " +   this.user.getFirstName() + " " + this.user.getLastName()); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
}
