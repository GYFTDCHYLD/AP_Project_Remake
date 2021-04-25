package frame;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import image.loadImages;
import packet.Packet02Logout;
import packet.Packet04Complain;
import domain.Complain;
import domain.Customer;
import domain.Employee;
import domain.User;


public class Dashboard extends JInternalFrame implements ActionListener, ListSelectionListener{
	private JScrollPane scrollPane;
	private JTable table; 
	private ListSelectionModel cellSelect;
	private User user;
	private JDesktopPane dashboard;
	
	private JLabel profileImage;
	private JLabel name;
	private JSeparator separator;
	private JLabel background;
	
	private JButton makeComplain;
	private JButton viewComplain;
	private JButton viewAccount;
	private JButton payBill;
	
	private JButton Chat;
	private JButton logOut;
	
	private JButton assignComplain;
	
	private JButton setVisitDate; 
	
	private String[] category; 
	private JComboBox<String> complainCategory;  
	private JLabel complainTypeLabel;
	
	
	private String[] type; 
	private JComboBox<String> complainType; 
	
	private JTextArea complainText;
	private JLabel complainLabel;
	
	private JButton submit;
	
	public void intializeComponent() {
		
		dashboard = new JDesktopPane();
		
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
		makeComplain.addActionListener(this);
		
		viewComplain = new JButton("View Complains");
		viewComplain.setBounds(270, 20,140, 30);
		viewComplain.addActionListener(this);
		
		viewAccount = new JButton("View Account");
		viewAccount.setBounds(410, 20,130, 30);
		viewAccount.addActionListener(this);
		
		Chat = new JButton("Chat");
		Chat.setBounds(410, 60,130, 30);
		Chat.addActionListener(this);
		
		payBill = new JButton("Pay Bill");
		payBill.setBounds(540, 20,120, 30);
		payBill.addActionListener(this);
		
		
		logOut = new JButton("logout");
		logOut.setBounds(540, 60,120, 30);
		logOut.addActionListener(this);
		
		assignComplain = new JButton("Assign a complain");
		assignComplain.setBounds(120, 20,150, 30);
		assignComplain.addActionListener(this);
		
		setVisitDate = new JButton("Set visit date");
		setVisitDate.setBounds(120, 20,150, 30);
		setVisitDate.addActionListener(this);
		
		separator = new JSeparator();
		separator.setBounds(0, 90,720, 50);
		separator.setBackground(Color.WHITE);
		
		category = new String[]{"", "Network", "Billing", "Other"};
		complainCategory = new JComboBox<String>(category);
		complainCategory.setBounds(10, 110, 180, 25);
		complainCategory.addActionListener(this);
		complainCategory.setVisible(false);
		
		type = new String[]{""};
		complainType = new JComboBox<String>(type);
		complainType.setBounds(10, 150, 180, 25);
		complainType.setVisible(false);
		
		complainTypeLabel = new JLabel("");
		complainTypeLabel.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		complainTypeLabel.setForeground(Color.WHITE);
		complainTypeLabel.setBounds(190, 110,300, 30);
		complainTypeLabel.setVisible(false);
		
		complainText = new JTextArea(); 
		complainText.setBounds(10, 200, 180, 250); 
		complainText.setBackground(Color.WHITE);
		complainText.setForeground(Color.BLACK);
		complainText.setBorder(new LineBorder(java.awt.Color.RED, 1));
		complainText.setFont(new Font("arial", Font.PLAIN, 12)); 
		complainText.setLineWrap(true);
		complainText.setWrapStyleWord(true);
		complainText.setVisible(false);
		
		complainLabel = new JLabel(""); 
		complainLabel.setBounds(200, 200, 150, 25);
		complainLabel.setForeground(Color.WHITE);
		complainLabel.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		complainLabel.setVisible(false);
		
		submit = new JButton("");
		submit.setBounds(10, 460,180, 30);
		submit.addActionListener(this);
		submit.setVisible(false);
		
		background = new JLabel();
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setIcon(new ImageIcon(loadImages.dashboardBackground)); 
		background.setBounds(0, 0,750, 600);
			
		
	}
	
	public void addComponentsToWindow(String user){
		add(dashboard);
		if(user.equals("Customer")) {
			dashboard.add(makeComplain);
			dashboard.add(viewAccount);
			dashboard.add(Chat);
			dashboard.add(payBill);
		}else if(user.equals("Representative")) { 
			dashboard.add(assignComplain);
		}else {
			dashboard.add(setVisitDate);
		}
		dashboard.add(viewComplain);
		dashboard.add(profileImage);
		dashboard.add(name);
		dashboard.add(separator);
		
		
		dashboard.add(complainCategory);
		dashboard.add(complainTypeLabel);
		dashboard.add(complainType);
		dashboard.add(complainText);
		dashboard.add(complainLabel);
		dashboard.add(submit);
		dashboard.add(logOut);
		
		
		dashboard.add(background);
	}
	
	public void DisplayComponent(String clicked){
		complainCategory.setVisible(false);
		complainTypeLabel.setVisible(false);
		complainText.setVisible(false);
		complainLabel.setVisible(false);
		complainType.setVisible(false);
		submit.setVisible(false);
		
		try {
			scrollPane.setVisible(false);
			dashboard.remove(scrollPane);
		} catch (Exception e) {
				
		}
		
		switch (clicked) {
		
			case "Make a Complain":
										complainCategory.setVisible(true);
										complainTypeLabel.setVisible(true);
										complainText.setVisible(true);
										complainLabel.setVisible(true);
										submit.setVisible(true);
										submit.setText("Send"); 
										break;
			case "View Complains":
										
										createTable();
										break;
			case "View Account":
										JOptionPane.showMessageDialog(null, "Ammount due: "+ ((Customer)user).getBillingAccount().getAmountDue()
												+" Due date: " + ((Customer)user).getBillingAccount().getDueDate()
												+" Status: " + ((Customer)user).getBillingAccount().getStatus(), "",JOptionPane.INFORMATION_MESSAGE);
										break;
			case "Pay Bill":
										break;
			case "Assign a complain":
										break;
			case "Set visit date":
										break;
			case "Chat":
										
										ChatWindow ChatWindow = new ChatWindow();
										MainWindow.getDesktopPane().add(ChatWindow);
										MainWindow.getDesktopPane().moveToFront(ChatWindow);
										break;
			case "logout":
										Packet02Logout Packet = new Packet02Logout(MainWindow.getLoginID());
										Packet.writeData(MainWindow.getClientSocket());
										break;
			
			default:
			
		}
	}

	
	public void setWindowsProperties() {
		this.setBounds(15, 0, 720, 560);
		this.setVisible(true); 
	}
	
	public Dashboard(User user) {  
		super("",false,false,false,true);
		intializeComponent();
		if(user instanceof Employee) { 
			this.title = ((Employee) user).getJobTitle() + " Dashboard";
			addComponentsToWindow(((Employee)user).getJobTitle());
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
		
		if(!(e.getActionCommand().equals("comboBoxChanged") || e.getActionCommand().equals("Send"))) 
			DisplayComponent(e.getActionCommand());	
		else {
			
			if(e.getActionCommand().equals("Send")){
				
				if(complainCategory.getSelectedItem().equals("")) 
					JOptionPane.showMessageDialog(null, "Choose a complain Category", "",JOptionPane.ERROR_MESSAGE);
				else {
					if(!complainCategory.getSelectedItem().equals("Other")) {
						if(complainType.getSelectedItem().equals("")) {
							JOptionPane.showMessageDialog(null, "Choose a complain type", "",JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					if(complainText.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Type your Complain", "",JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					sendComplain();
				}
					
			}else {
				complainType.setVisible(true); 
				switch (String.valueOf(complainCategory.getSelectedItem())) {
					case "Network":
										complainType.removeAllItems();
										complainType.addItem("");
										complainType.addItem("Cable");
										complainType.addItem("Tellephone");
										complainType.addItem("Internet");
										break;
					case "Billing":
										complainType.removeAllItems();
										complainType.addItem("");
										complainType.addItem("Paid");
										complainType.addItem("Unpaid");
										break;
					case "Other":
										complainType.removeAllItems();
										complainType.setVisible(false);  
										break;
					default:
										complainType.removeAllItems();
										complainType.setVisible(false); 
					
				}
			}
		}
	}
	
	private void sendComplain() { 
		Packet04Complain Packet = new Packet04Complain(new Complain(1, user.getUserId(), String.valueOf(complainType.getSelectedItem()), complainText.getText(), "", "", new Date(0,0,0)));
		Packet.writeData(MainWindow.getClientSocket()); 
		this.complainText.setText("");
	}
	
			
	
	
	public JDesktopPane getDashboard() {
		return dashboard; 
	}
	
	
	public JTextArea getComplainText() {
		return complainText;
	}
	
	public void setComplainText(String complainText) {
		this.complainText.setText(complainText);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		String Data = null;
		int[] row = table.getSelectedRows();
		int[] columns = table.getSelectedColumns();
		for (int i = 0; i < row.length; i++) {
			for (int j = 0; j < columns.length; j++) {
				Data = (String) table.getValueAt(row[i], columns[j]);

			}
		}
		System.out.println("Table element selected is: " + Data);

	}
	
	public void createTable() {

		String column[]={"ID","TYPE","MESSAGE","REPRESENTATIVE","ASSIGNED TECHNICIAN","VISIT DATE"};
		String data[][];
		data = new String[MainWindow.getComplain().size()][7];
		for(int row = 0; row < MainWindow.getComplain().size(); row ++) {
			data[row][0] = MainWindow.getComplain().get(row).getId()+"";
			data[row][1] = MainWindow.getComplain().get(row).getType();
			data[row][2] = MainWindow.getComplain().get(row).getMessage();
			data[row][3] = MainWindow.getComplain().get(row).getRepId();
			data[row][4] = MainWindow.getComplain().get(row).getTecId();
			data[row][5] = MainWindow.getComplain().get(row).getVisitDate()+"";
			
		}
			
			
		table = new JTable(data, column);
		table.setCellSelectionEnabled(true);
		table.setForeground(Color.BLACK);
		table.setGridColor(Color.BLUE);
		table.setOpaque(true); 
		table.getTableHeader().setFont(new Font("arial", Font.PLAIN, 14));
		table.setAutoscrolls(true); 
		
		cellSelect = table.getSelectionModel();  
		cellSelect.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		cellSelect.addListSelectionListener(this);
		scrollPane = new JScrollPane(table); 
		scrollPane.setBounds(10, 110, 680, 400); 
		scrollPane.setOpaque(true);;
		
		dashboard.add(scrollPane);
		dashboard.moveToFront(scrollPane); 
		scrollPane.setVisible(true);
		
	}

}
