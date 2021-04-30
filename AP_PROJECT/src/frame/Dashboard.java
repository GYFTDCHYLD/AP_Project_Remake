package frame;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import packet.Packet9Info;
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
	
	private JTextArea complainCounter; 
	
	private JButton submit;
	
	private ChatWindow ChatWindow;
	private String ConnectedTo; // this will store the user u are currently chatting/connected to in order to filter incomming messages from other users in the same chat window, this will allow us to make only the mssage on the connected user show in the chat
	private List<Packet03Chat> messages;// a list of chats for the messages being recieved from the server
	
	private JComboBox<String> onlineClientsDropdown;  
	private int recieverIndex; //used to map the index of the the reciever from the Mainwindow to the selection from the dropdown index
	
	private JComboBox<String> technitionList;  
	
	private boolean displayComplainTable;
	
	public void intializeComponent() {
		
		dashboard = new JDesktopPane();
		ChatWindow = new ChatWindow();
		ConnectedTo = "";
		ChatWindow.setVisible(false);
		
		displayComplainTable = false;
		
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
		
		complainCounter = new JTextArea();
		complainCounter.setBounds(420, 3, 230, 55); 
		complainCounter.setBackground(Color.BLACK);
		complainCounter.setForeground(Color.WHITE);
		complainCounter.setBorder(new LineBorder(java.awt.Color.WHITE, 1));
		complainCounter.setFont(new Font("arial", Font.ROMAN_BASELINE, 14)); 
		complainCounter.setLineWrap(true);
		complainCounter.setWrapStyleWord(true);
		complainCounter.setVisible(false);
		
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
		
		technitionList = new JComboBox<String>(); 
		technitionList.setBounds(250, 60,160, 30);
		technitionList.addActionListener(this);
		technitionList.setVisible(false);
		
		payBill = new JButton("Pay Bill");
		payBill.setBounds(540, 20,120, 30);
		payBill.addActionListener(this);
		
		
		logOut = new JButton("logout");
		logOut.setBounds(540, 60,120, 30);
		logOut.addActionListener(this);
		
		assignComplain = new JButton("Assign a complain");
		assignComplain.setBounds(120, 20,150, 30);
		assignComplain.addActionListener(this);
		assignComplain.setVisible(false);
		
		setVisitDate = new JButton("Set visit date");
		setVisitDate.setBounds(120, 20,150, 30);
		setVisitDate.addActionListener(this);
		setVisitDate.setVisible(false);
		
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
		
		table = new JTable();
		table.setCellSelectionEnabled(true);
		table.setForeground(Color.BLACK);
		table.setGridColor(Color.BLUE);
		
		scrollPane = new JScrollPane(table); 
		scrollPane.setBounds(7, 110, 680, 400); 
		scrollPane.setVisible(false);
		
		background = new JLabel();
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setBounds(0, 0,750, 600);
			
		
	}
	
	public void addComponentsToWindow(String user){
		this.add(dashboard);
		dashboard.add(ChatWindow);
		dashboard.add(scrollPane);
		if(user.equals("Customer")) {
			dashboard.add(makeComplain);
			dashboard.add(viewAccount); 
			dashboard.add(payBill);
			name.setForeground(Color.BLACK);
			background.setIcon(new ImageIcon(loadImages.CustomerDashboardBackground)); 
		}else if(user.equals("Representative")) { 
			dashboard.add(technitionList);
			dashboard.add(complainCounter);
			complainCounter.setVisible(true);
			dashboard.add(assignComplain);
			name.setForeground(Color.WHITE);
			background.setIcon(new ImageIcon(loadImages.RepresentativeDashboardBackground)); 
		}else {
			dashboard.add(complainCounter);
			complainCounter.setVisible(true);
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
		if(e.getActionCommand().equals("comboBoxChanged")) { 
			if(onlineClientsDropdown.isVisible()) {
				if(!onlineClientsDropdown.getSelectedItem().equals("Select User")){
					recieverIndex = onlineClientsDropdown.getSelectedIndex()-1; // set the recieverIndex to the selected index od the gropdown menu to locate the reciever ID
					ConnectedTo = MainWindow.getOnlineClient().get(recieverIndex)[0][0].toString();// locate the id for the selected person from the online list and // add the id of the person u are currently having a convo winth, this is used to filter message from diferent conversation
	
					Packet03Chat Packet03Chat = new Packet03Chat(ConnectedTo, MainWindow.getOnlineClient().get(recieverIndex)[0][1].toString(), user.getUserId(), "");
					initiateChat(Packet03Chat);// call the initiate chat function
				}
					
			}else if(technitionList.isVisible()) {
				if(!technitionList.getSelectedItem().equals("Select Technition")){
					JOptionPane.showInternalMessageDialog(dashboard, "You Have Selected: " + technitionList.getSelectedItem(), user.getFirstName(), JOptionPane.INFORMATION_MESSAGE);
					assignComplain.setVisible(true);
					technitionList.setVisible(false);
					Packet9Info assign = new Packet9Info("");// prepare message 
					assign.setAssignment("Assign a complain");// command/instruction for the server 
					assign.setLoginId(user.getUserId());// rep id to be attacched to the complain
					assign.setInfo(ComplainID);// complain id
					assign.setInfo2(MainWindow.getTechnitions().get(technitionList.getSelectedIndex()-1)[0][0].toString());// technition id
					assign.writeData(MainWindow.getClientSocket());  // send info to client
				}else {
					JOptionPane.showInternalMessageDialog(dashboard, "Selected a Technition", user.getFirstName(), JOptionPane.ERROR_MESSAGE);
				}
			}else {
				switch (String.valueOf(complainCategory.getSelectedItem())) {
					case "Network":
									complainType.removeAllItems();
									complainType.addItem("");
									complainType.addItem("Cable");
									complainType.addItem("Tellephone");
									complainType.addItem("Internet");
									complainType.setVisible(true);
						break;
					case "Billing":
									complainType.removeAllItems();
									complainType.addItem("");
									complainType.addItem("Paid");
									complainType.addItem("Unpaid");
									complainType.setVisible(true);
						break;
					default:
									complainType.removeAllItems();
									complainType.setVisible(false);

				}
			}
		}else {
			if (e.getActionCommand().equals("Send")) {

				if (complainCategory.getSelectedItem().equals(""))
					JOptionPane.showInternalMessageDialog(dashboard, "Choose a complain Category", "", JOptionPane.ERROR_MESSAGE);
				else {
					if (!complainCategory.getSelectedItem().equals("Other")) {
						if (complainType.getSelectedItem().equals("")) {
							JOptionPane.showInternalMessageDialog(dashboard, "Choose a complain type", "", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					if (complainText.getText().equals("")) {
						JOptionPane.showInternalMessageDialog(dashboard, "Type your Complain", "", JOptionPane.ERROR_MESSAGE);
						return;
					}

					sendComplain();
				}

			} else {
				try {
					DisplayComponent(e.getActionCommand()); // display component for the button that was pressed
				}catch (ClassCastException ex) {
					
				}
			}
		}
		} catch (Exception e2) {
			
		}
	}
	
	public void DisplayComponent(String clicked){
		if(!displayComplainTable) {// dnt show these items if the table is not visible
			assignComplain.setVisible(false); 
			setVisitDate.setVisible(false);
		}
		onlineClientsDropdown.setVisible(false);
		startChatButton.setVisible(true); 
		technitionList.setVisible(false);
		complainCategory.setVisible(false);
		complainTypeLabel.setVisible(false);
		complainText.setVisible(false);
		complainLabel.setVisible(false);
		complainType.setVisible(false);
		submit.setVisible(false);
		displayComplainTable = false;
		scrollPane.setVisible(false);
		
		
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
										if(user instanceof Employee) { 
											assignComplain.setVisible(true);// for rep 
											setVisitDate.setVisible(true);// for tech
										}
										
										displayComplainTable = true;
										createTable(); 
										break;
			case "View Account":
										JOptionPane.showMessageDialog(dashboard, "Ammount due: "+ ((Customer)user).getBillingAccount().getAmountDue()
												+" Due date: " + ((Customer)user).getBillingAccount().getDueDate()
												+" Status: " + ((Customer)user).getBillingAccount().getStatus(), "",JOptionPane.INFORMATION_MESSAGE);
										break;
			case "Pay Bill":
										break;
			case "Assign a complain":
										technitionList.removeAllItems();
										technitionList.addItem("Select Technition");
										for(String[][] tecInfo : MainWindow.getTechnitions()) { 
											technitionList.addItem(tecInfo[0][1]);// add the technitions name to the dropdown  
										}
										displayComplainTable = true;
										createTable();
										JOptionPane.showInternalMessageDialog(dashboard, "Select the row/complain to assign", "", JOptionPane.INFORMATION_MESSAGE);
										break;
			case "Set visit date":		
										displayComplainTable = true;
										createTable();
										JOptionPane.showInternalMessageDialog(dashboard, "Select the row/complain to set date", "", JOptionPane.INFORMATION_MESSAGE);
										break;
			case "Start Chat":
										onlineClientsDropdown.removeAllItems();
										if(MainWindow.getOnlineClient().size() == 0) {
											JOptionPane.showInternalMessageDialog(dashboard, "Nobody available to chat", "Micro Star",JOptionPane.INFORMATION_MESSAGE);
										}else {
											onlineClientsDropdown.addItem("Select User");
											for(String[][] clientInfo : MainWindow.getOnlineClient()) { 
												onlineClientsDropdown.addItem(clientInfo[0][1]);// add the online user's name to the dropdown
											}
											onlineClientsDropdown.revalidate();
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
										Packet02Logout logout = new Packet02Logout("logout");
										logout.setHandlerID(MainWindow.getClientHandlerId());// prepare the logout packet with the client handler id
										logout.writeData(MainWindow.getClientSocket()); // send the logout packet to the server
										break;
			
			default:
			
		}
	}

	



	
	private void sendComplain() { 
		String categoryAndType;
		if(complainType.isVisible())
			categoryAndType = complainCategory.getSelectedItem() + " ("+complainType.getSelectedItem()+")";
		else 
			categoryAndType = complainCategory.getSelectedItem().toString();
			
		Packet04Complain Packet = new Packet04Complain(new Complain(1, user.getUserId(), categoryAndType, complainText.getText(), "", "", ""));
		Packet.writeData(MainWindow.getClientSocket()); 
	}
	
			
	
	String ComplainID = "";
	@Override
	public void valueChanged(ListSelectionEvent e) {// use for table selection for assignng complains and setting date for technition
		 
		String Data = null;
		int[] row = table.getSelectedRows();
		int[] columns = table.getSelectedColumns();
		for (int i = 0; i < row.length; i++) {
			for (int j = 0; j < columns.length; j++) {
				ComplainID = (String) table.getValueAt(row[i], 0);
				Data = (String) table.getValueAt(row[i], columns[j]);

			}
		}
		if(user instanceof Employee) {
			JOptionPane.showInternalMessageDialog(dashboard,"Complain Id Selected: "+ ComplainID, user.getFirstName(),JOptionPane.INFORMATION_MESSAGE);
			if(((Employee) user).getJobTitle().matches("Representative")) {
				assignComplain.setVisible(false);
				technitionList.setVisible(true);
			}else {
				setVisitDate.setVisible(false);
			}
		}
	}
	
	public void createTable() {

		int total = 0, resolved = 0, unResolved = 0;
		String column[]={"ID","TYPE","MESSAGE","REPRESENTATIVE","ASSIGNED TECHNICIAN","VISIT DATE"};
		String data[][];
		data = new String[MainWindow.getComplain().size()][7];
		for(int row = 0; row < MainWindow.getComplain().size(); row ++) {
			data[row][0] = MainWindow.getComplain().get(row).getId()+"";
			data[row][1] = MainWindow.getComplain().get(row).getType();
			data[row][2] = MainWindow.getComplain().get(row).getMessage();
			data[row][3] = MainWindow.getComplain().get(row).getRepId();
			data[row][4] = MainWindow.getComplain().get(row).getTecId();
			data[row][5] = MainWindow.getComplain().get(row).getVisitDate();
			
			total ++;
			if(!data[row][5].equals(""))
				resolved++;
			unResolved = (total - resolved);
			complainCounter.setText("  Total Complain(s) : " + total 
							    + "\n  Resolved               : " + resolved
							    + "\n  Un-Resolved:        : " + unResolved); 
		}
			
		if(displayComplainTable) {
			table = new JTable(data, column);
			table.setCellSelectionEnabled(true);
			table.setGridColor(Color.BLACK);
			table.getTableHeader().setFont(new Font("arial", Font.PLAIN, 14)); 
			table.setUpdateSelectionOnSort(true);
			if (user instanceof Employee)
				table.setEnabled(true);
			else
				table.setEnabled(false);
			
			cellSelect = table.getSelectionModel();  
			cellSelect.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			cellSelect.addListSelectionListener(this);
			scrollPane = new JScrollPane(table); 
			scrollPane.setBounds(7, 110, 680, 400); 
			
			dashboard.add(scrollPane);
			dashboard.moveToFront(scrollPane); 
		}
		
	}
	
	public void append(Packet03Chat chat) {// this function is used to add all incomming chat to an array list and append message to textarea
		messages.add(chat);// store all chat reciever while online
		
		if(!ChatWindow.isVisible()) {
			JOptionPane.showInternalMessageDialog(dashboard,"Message From: "+ chat.getSenderName(), user.getFirstName(),JOptionPane.INFORMATION_MESSAGE);// display a popup with the message from the sender
			initiateChat(chat);// display the chat window with the message if u are not currently talking to someone else
		}else if(!(chat.getSenderId().equals(ChatWindow.getME().getUserId()) || chat.getSenderId().equals(ConnectedTo)))// if the message is not from the current user or from the user that the cutrrent user is connect to
			JOptionPane.showInternalMessageDialog(dashboard,"Message From: "+ chat.getSenderName(), user.getFirstName(),JOptionPane.INFORMATION_MESSAGE);// display a popup with the message from the sender
		DisplayComponent("");
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
	
	
	
	
	public void initiateChat(Packet03Chat chat) {// function used to bring up the chat window and initiant the connection with selected client/user
		startChatButton.setText("End Chat");//change the name on the button after it has been clicked
		startChatButton.setVisible(true);// show chat button
		onlineClientsDropdown.setVisible(false);// hide dropdown in chat button's place
		
		ConnectedTo = chat.getSenderId();// add the id of the person u are currently having a convo winth, this is used to filter message from diferent conversation
		ChatWindow.setME(user);// set the user info for the chat, this user object includes the sender name and id
		ChatWindow.setReceiverId(chat.getSenderId());// add the reciever's id to the chat
		ChatWindow.setChatWindowTitle("Connect with: " + chat.getSenderName());// update the title of the chat with the recievere's name, showing with whom u are connected with 
		ChatWindow.setVisible(true); //make the chat window visible
		filterMessage();// the conversation between the current user and the connected user
	}


	public void setComplainCategoryIndex(int index) {
		this.complainCategory.setSelectedIndex(index);
	}
	
	public JDesktopPane getDashboard() {
		return dashboard; 
	}
	
	
	
	public void setComplainText(String complainText) {
		this.complainText.setText(complainText);
	}

}
