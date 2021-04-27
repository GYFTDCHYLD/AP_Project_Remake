package frame;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
import packet.Packet03Chat;
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
	
	private JButton startChatButton;
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
	
	private ChatWindow ChatWindow;
	private String ConnectedTo; // this will store the user u are currently chatting/connected to in order to filter incomming messages from other users in the same chat window, this will allow us to make only the mssage on the connected user show in the chat
	private List<Packet03Chat> messages;// a list of chats for the messages being recieved from the server
	
	private JComboBox<String> onlineClientsDropdown;  
	private int recieverIndex; //used to map the index of the the reciever from the Mainwindow to the selection from the dropdown index
	
	public void intializeComponent() {
		
		dashboard = new JDesktopPane();
		ChatWindow = new ChatWindow();
		ConnectedTo = "";
		ChatWindow.setVisible(false);
		
		messages = new ArrayList<Packet03Chat>();// the will contain all chat that was recieve since being online
		
		profileImage = new JLabel();
		profileImage.setHorizontalAlignment(SwingConstants.CENTER);
		profileImage.setBounds(40, 10,50, 50);
		
		name = new JLabel();
		name.setFont(new Font("arial", Font.TYPE1_FONT, 16));
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
		
		startChatButton = new JButton("Start Chat");
		startChatButton.setBounds(410, 60,130, 30);
		startChatButton.addActionListener(this);
		
		onlineClientsDropdown = new JComboBox<String>(); 
		onlineClientsDropdown.setBounds(410, 60,130, 30);
		onlineClientsDropdown.addActionListener(this);
		onlineClientsDropdown.setVisible(false);
		
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
		background.setBounds(0, 0,750, 600);
			
		
	}
	
	public void addComponentsToWindow(String user){
		this.add(dashboard);
		dashboard.add(ChatWindow);
		if(user.equals("Customer")) {
			dashboard.add(makeComplain);
			dashboard.add(viewAccount); 
			dashboard.add(payBill);
			name.setForeground(Color.BLACK);
			background.setIcon(new ImageIcon(loadImages.CustomerDashboardBackground)); 
		}else if(user.equals("Representative")) { 
			dashboard.add(assignComplain);
			name.setForeground(Color.WHITE);
			background.setIcon(new ImageIcon(loadImages.RepresentativeDashboardBackground)); 
		}else {
			dashboard.add(setVisitDate);
			name.setForeground(Color.WHITE);
			background.setIcon(new ImageIcon(loadImages.TechnitianDashboardBackground)); 
		}
		
		dashboard.add(onlineClientsDropdown);
		dashboard.add(startChatButton);
		dashboard.add(viewComplain);
		dashboard.add(profileImage);
		dashboard.add(separator);
		dashboard.add(name);
		
		
		
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
			case "Start Chat":
										
										if(MainWindow.getOnlineClient().size() == 0) {
											JOptionPane.showMessageDialog(null, "Nobody available to chat", "Micro Star",JOptionPane.INFORMATION_MESSAGE);
										}else {

											
											onlineClientsDropdown.addItem("Select User");
											for(String[][] clientInfo : MainWindow.getOnlineClient()) { 
												onlineClientsDropdown.addItem(clientInfo[0][1]);// add the online user's name to the dropdown
											}
											onlineClientsDropdown.revalidate();
											startChatButton.setText("End Chat");//change the name on the button after it has been clicked
											startChatButton.setVisible(false);// hide chat button
											onlineClientsDropdown.setVisible(true);// show dropdown in chat button's place
										}
										break;
			case "End Chat":
										startChatButton.setText("Start Chat");//change the name on the button after it has been clicked
										ChatWindow.setVisible(false); 
										onlineClientsDropdown.removeAllItems();// remove all items from the dropdown list
										break;
			case "logout":
										Packet02Logout logout = new Packet02Logout(MainWindow.getLoginID());// prepare the logout packet with the user id
										logout.writeData(MainWindow.getClientSocket()); // send the logout packet to the server
										MainWindow.setOnlineClient(new ArrayList<String[][]>());//remove the list of online clients from the main window after logging out
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
		try {
			if(!(e.getActionCommand().equals("comboBoxChanged") || e.getActionCommand().equals("Send"))) 
				DisplayComponent(e.getActionCommand());	// display component for the button that was pressed
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
					if(!onlineClientsDropdown.getSelectedItem().equals("Select User")) {
						onlineClientsDropdown.setVisible(false); // hide online client dropdown menu
						startChatButton.setVisible(true);// show start chat button in its place	
						
						recieverIndex = onlineClientsDropdown.getSelectedIndex()-1; // set the recieverIndex to the selected index od the gropdown menu to locate the reciever ID
						ConnectedTo = MainWindow.getOnlineClient().get(recieverIndex)[0][0].toString();
						ChatWindow.setME(user);// set the user info for the chat, this user object includes the sender name and id
						ChatWindow.setReceiverId(ConnectedTo);// add the reciever's id to the chat
						ChatWindow.setChatWindowTitle("Connect with: " + MainWindow.getOnlineClient().get(recieverIndex)[0][1].toString());// update the title of the chat with the recievere's name, showing with whom u are connected with 
						ChatWindow.setVisible(true); //make the chat window visible
						filterMessage();// the conversation between the current user and the connected user
						
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
		}catch (Exception ex) {
			
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
		scrollPane.setBounds(7, 110, 680, 400); 
		scrollPane.setOpaque(true);;
		
		dashboard.add(scrollPane);
		dashboard.moveToFront(scrollPane); 
		scrollPane.setVisible(true);
		
	}
	
	public void append(Packet03Chat chat) {// this function is used to add all incomming chat to an array list and append message to textarea
		messages.add(chat);// store all chat reciever while online
		
		
		if(!ChatWindow.isVisible())
			JOptionPane.showMessageDialog(null,"Message From: "+ chat.getSenderName(), user.getFirstName(),JOptionPane.INFORMATION_MESSAGE);// display a popup with the message from the sender
		else if(!(chat.getSenderId().equals(ChatWindow.getME().getUserId()) || chat.getSenderId().equals(ConnectedTo)))// if the message is not from the current user or from the user that the cutrrent user is connect to
			JOptionPane.showMessageDialog(null,"Message From: "+ chat.getSenderName(), user.getFirstName(),JOptionPane.INFORMATION_MESSAGE);// display a popup with the message from the sender
		
		filterMessage();// display only the from the user and the connect person in the current chat
	}
	
	private void filterMessage() {
		String message = "";
		ChatWindow.getChatArea().setText("");
		for(Packet03Chat mesChat : messages) {// filter message for the chat
			if( (mesChat.getSenderId().equals(ChatWindow.getME().getUserId()) && mesChat.getRecieverId().equals(ConnectedTo)) || (mesChat.getSenderId().equals(ConnectedTo) && mesChat.getRecieverId().equals(ChatWindow.getME().getUserId())) ) {
				if(mesChat.getSenderId().equals(ChatWindow.getME().getUserId()))
					message = "Me: " + mesChat.getMessage();
				else {
					message = mesChat.getSenderName() + ": " + mesChat.getMessage();
				}
				ChatWindow.getChatArea().append(message + "\n");
			}
		}
	}

}
