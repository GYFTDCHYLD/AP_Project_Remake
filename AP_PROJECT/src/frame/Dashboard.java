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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

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
	
	private User user;
	private JDesktopPane dashboard;
	
	private JLabel profileImage;
	private JLabel name;
	private JSeparator separator;
	private JLabel background;
	
	private JScrollPane scrollPane;
	private JTable table; 
	
	
	private JButton makeComplain;
	private JButton viewComplain;
	private JButton viewAccount;
	private JButton payBill;
	
	private JButton startChatButton;
	private JButton logOut;
	
	private JButton submitButton;
	
	private JButton assignComplainButton;
	private JButton setVisitDateButton; 
	
	private String[] category; 
	private JComboBox<String> complainDropdownCategory;
	
	private String[] type; 
	private JComboBox<String> complainDropdownType; 
	
	private JComboBox<String> onlineClientsDropdown;  
	private int recieverIndex; //used to map the index of the the reciever from the Mainwindow to the selection from the dropdown index
	
	private JComboBox<String> technitionDropdownList;  
	
	private JTextArea complainTextField;
	private JTextArea complainCounter; 
	
	private JTextField setdateTextField; 
	
	private ChatWindow ChatWindow;
	private String ConnectedTo; // this will store the user u are currently chatting/connected to in order to filter incomming messages from other users in the same chat window, this will allow us to make only the mssage on the connected user show in the chat
	private List<Packet03Chat> messages;// a list of chats for the messages being recieved from the server
	private List<Complain> complains; 
	private List<String[][]>  onlineClient;
	private List<String[][]>  technicions; 
	private boolean displayComplainTable;
	
	
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
		
		this.setBounds(15, 0, 720, 560);
		this.setVisible(true); 
		
		this.user = user;
		if(this.user.getNameTitle().equals("Mr"))
			profileImage.setIcon(new ImageIcon(loadImages.maleAvtar));
		else 
			profileImage.setIcon(new ImageIcon(loadImages.femaleAvtar));
		
		name.setText(this.user.getNameTitle() + ". " +   this.user.getFirstName() + " " + this.user.getLastName()); 
	}
	
	
	public void intializeComponent() {
		
		dashboard = new JDesktopPane();
		ChatWindow = new ChatWindow();
		ConnectedTo = "";
		ChatWindow.setVisible(false);
		
		displayComplainTable = false;
		
		messages = new ArrayList<>();// the will contain all chat that was recieve since being online
		complains = new ArrayList<>(); 
		onlineClient = new ArrayList<>();
		technicions = new ArrayList<>();
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
		complainCounter.setEditable(false);
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
		
		technitionDropdownList = new JComboBox<String>(); 
		technitionDropdownList.setBounds(250, 60,160, 30);
		technitionDropdownList.addActionListener(this);
		technitionDropdownList.setVisible(false);
		
		setdateTextField = new JTextField(); 
		setdateTextField.setBounds(250, 60,160, 30);
		setdateTextField.setVisible(false);
		setdateTextField.setToolTipText("Type date here"); 
		
		payBill = new JButton("Pay Bill");
		payBill.setBounds(540, 20,120, 30);
		payBill.addActionListener(this);
		
		
		logOut = new JButton("logout");
		logOut.setBounds(540, 60,120, 30);
		logOut.addActionListener(this);
		
		assignComplainButton = new JButton("Assign a complain");
		assignComplainButton.setBounds(120, 20,150, 30);
		assignComplainButton.addActionListener(this);
		assignComplainButton.setVisible(false);
		
		setVisitDateButton = new JButton("Set visit date");
		setVisitDateButton.setBounds(120, 20,150, 30);
		setVisitDateButton.addActionListener(this);
		setVisitDateButton.setVisible(false);
		
		separator = new JSeparator();
		separator.setBounds(0, 90,720, 50);
		separator.setBackground(Color.WHITE);
		
		category = new String[]{"", "Network", "Billing", "Other"};
		complainDropdownCategory = new JComboBox<String>(category);
		complainDropdownCategory.setBounds(10, 110, 180, 25);
		complainDropdownCategory.addActionListener(this);
		complainDropdownCategory.setVisible(false); 
		
		type = new String[]{""};
		complainDropdownType = new JComboBox<String>(type);
		complainDropdownType.setBounds(10, 150, 180, 25);
		complainDropdownType.setVisible(false);
		
		complainTextField = new JTextArea(); 
		complainTextField.setBounds(10, 200, 180, 250); 
		complainTextField.setBackground(Color.WHITE);
		complainTextField.setForeground(Color.BLACK);
		complainTextField.setBorder(new LineBorder(java.awt.Color.RED, 1));
		complainTextField.setFont(new Font("arial", Font.PLAIN, 12)); 
		complainTextField.setLineWrap(true);
		complainTextField.setWrapStyleWord(true);
		complainTextField.setVisible(false);
		
		submitButton = new JButton("");
		submitButton.setBounds(10, 460,180, 30);
		submitButton.addActionListener(this);
		submitButton.setVisible(false);
		
		table = new JTable();
		table.setCellSelectionEnabled(true);
		table.setGridColor(Color.BLACK);
		table.getTableHeader().setFont(new Font("arial", Font.PLAIN, 14));
		table.getTableHeader().setBackground(Color.BLACK); 
		table.getTableHeader().setForeground(Color.WHITE); 
		ListSelectionModel cellSelect = table.getSelectionModel();  
		cellSelect.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelect.addListSelectionListener(this);
	
		
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
			dashboard.add(technitionDropdownList);
			dashboard.add(complainCounter);
			complainCounter.setVisible(true);
			dashboard.add(assignComplainButton);
			name.setForeground(Color.WHITE);
			background.setIcon(new ImageIcon(loadImages.RepresentativeDashboardBackground)); 
		}else {
			dashboard.add(setdateTextField);
			dashboard.add(complainCounter);
			complainCounter.setVisible(true);
			dashboard.add(setVisitDateButton);
			name.setForeground(Color.WHITE);
			background.setIcon(new ImageIcon(loadImages.TechnitianDashboardBackground)); 
		}
		
		dashboard.add(onlineClientsDropdown);
		dashboard.add(startChatButton);
		dashboard.add(viewComplain);
		dashboard.add(profileImage);
		dashboard.add(separator);
		dashboard.add(name);
		
		
		
		dashboard.add(complainDropdownCategory);
		dashboard.add(complainDropdownType);
		dashboard.add(complainTextField);
		dashboard.add(submitButton);
		dashboard.add(logOut);
		
		
		dashboard.add(background);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
		if(e.getActionCommand().equals("comboBoxChanged")) { 
			if(onlineClientsDropdown.isVisible()) {
				if(!onlineClientsDropdown.getSelectedItem().equals("Select User")){
					recieverIndex = onlineClientsDropdown.getSelectedIndex()-1; // set the recieverIndex to the selected index od the gropdown menu to locate the reciever ID
					ConnectedTo = onlineClient.get(recieverIndex)[0][0].toString();// locate the id for the selected person from the online list and // add the id of the person u are currently having a convo winth, this is used to filter message from diferent conversation
	
					Packet03Chat Packet03Chat = new Packet03Chat(ConnectedTo, onlineClient.get(recieverIndex)[0][1].toString(), user.getUserId(), "");
					initiateChat(Packet03Chat);// call the initiate chat function
				}
					
			}else if(technitionDropdownList.isVisible()) {
				if(!technitionDropdownList.getSelectedItem().equals("Select Technition")){
					JOptionPane.showInternalMessageDialog(dashboard, "You Have Selected: " + technitionDropdownList.getSelectedItem(), user.getFirstName(), JOptionPane.INFORMATION_MESSAGE);
					assignComplainButton.setVisible(true);
					technitionDropdownList.setVisible(false);
					Packet9Info assign = new Packet9Info("");// prepare message 
					assign.setAssignment("Assign a complain");// command/instruction for the server 
					assign.setLoginId(user.getUserId());// rep id to be attacched to the complain
					assign.setInfo(ComplainID);// complain id
					assign.setInfo2(technicions.get(technitionDropdownList.getSelectedIndex()-1)[0][0].toString());// technition id
					assign.writeData(MainWindow.getClientSocket());  // send info to client
				}else {
					JOptionPane.showInternalMessageDialog(dashboard, "Selected a Technition", user.getFirstName(), JOptionPane.ERROR_MESSAGE);
				}
			}else {
				switch (String.valueOf(complainDropdownCategory.getSelectedItem())) {
					case "Network":
									complainDropdownType.removeAllItems();
									complainDropdownType.addItem("");
									complainDropdownType.addItem("Cable");
									complainDropdownType.addItem("Tellephone");
									complainDropdownType.addItem("Internet");
									complainDropdownType.setVisible(true);
						break;
					case "Billing":
									complainDropdownType.removeAllItems();
									complainDropdownType.addItem("");
									complainDropdownType.addItem("Paid");
									complainDropdownType.addItem("Unpaid");
									complainDropdownType.setVisible(true);
						break;
					default:
									complainDropdownType.removeAllItems();
									complainDropdownType.setVisible(false);

				}
			}
		}else {
			if (e.getActionCommand().equals("Send")) {

				if (complainDropdownCategory.getSelectedItem().equals(""))
					JOptionPane.showInternalMessageDialog(dashboard, "Choose a complain Category", "", JOptionPane.ERROR_MESSAGE);
				else {
					if (!complainDropdownCategory.getSelectedItem().equals("Other")) {
						if (complainDropdownType.getSelectedItem().equals("")) {
							JOptionPane.showInternalMessageDialog(dashboard, "Choose a complain type", "", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					if (complainTextField.getText().equals("")) {
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
			assignComplainButton.setVisible(false);  
			setVisitDateButton.setVisible(false);
		}
		onlineClientsDropdown.setVisible(false);
		startChatButton.setVisible(true); 
		technitionDropdownList.setVisible(false);
		setdateTextField.setVisible(false);
		complainDropdownCategory.setVisible(false);
		complainTextField.setVisible(false);
		complainDropdownType.setVisible(false); 
		submitButton.setVisible(false);
		displayComplainTable = false;
		scrollPane.setVisible(false);
		editTable = false;
		
		
		switch (clicked) {
		
			case "Make a Complain":
										complainDropdownCategory.setVisible(true); 
										complainTextField.setVisible(true); 
										submitButton.setVisible(true); 
										submitButton.setText("Send"); 
										break;
			case "View Complains":
										if(user instanceof Employee) {
											setVisitDateButton.setText("Set visit date"); 
											assignComplainButton.setVisible(true);// for rep 
											setVisitDateButton.setVisible(true);// for tech
										}
										
										displayComplainTable = true;
										populateTable(); 
										break;
			case "Assign a complain":
										technitionDropdownList.removeAllItems(); 
										technitionDropdownList.addItem("Select Technition");
										for(String[][] tecInfo : technicions) { 
											technitionDropdownList.addItem(tecInfo[0][1]);// add the technitions name to the dropdown  
										}
										editTable = true;
										displayComplainTable = true;
										populateTable();
										JOptionPane.showInternalMessageDialog(dashboard, "Select the row/complain to assign", "", JOptionPane.INFORMATION_MESSAGE);
										break;
			case "Set visit date":		
										setVisitDateButton.setText("Finalize Date");
										displayComplainTable = true;
										editTable = true;
										populateTable();
										JOptionPane.showInternalMessageDialog(dashboard, "Select the row/complain to set date", "", JOptionPane.INFORMATION_MESSAGE);
										break;
			case "Finalize Date":
										if(setdateTextField.getText().equals("")) {
											setVisitDateButton.setText("Set visit date"); 
											JOptionPane.showInternalMessageDialog(dashboard, "Type date in the date field", "", JOptionPane.ERROR_MESSAGE);
										}else {
											Packet9Info assign = new Packet9Info("");// prepare message 
											assign.setAssignment("Set Date");// command/instruction for the server 
											assign.setInfo(ComplainID);// complain id
											assign.setInfo2(setdateTextField.getText());// Date
											assign.writeData(MainWindow.getClientSocket());  // send info to client
											setVisitDateButton.setText("Set visit date"); 
											setdateTextField.setText("");
											setdateTextField.setVisible(false);
										}
										break;
			case "View Account":
										JOptionPane.showMessageDialog(dashboard, "Ammount due: "+ ((Customer)user).getBillingAccount().getAmountDue()
												+" Due date: " + ((Customer)user).getBillingAccount().getDueDate()
												+" Status: " + ((Customer)user).getBillingAccount().getStatus(), "",JOptionPane.INFORMATION_MESSAGE);
										break;
			case "Pay Bill":
										break;
			case "Start Chat":
										onlineClientsDropdown.removeAllItems();
										if(onlineClient.size() == 0) {
											JOptionPane.showInternalMessageDialog(dashboard, "Nobody available to chat", "Micro Star",JOptionPane.INFORMATION_MESSAGE);
										}else {
											onlineClientsDropdown.addItem("Select User");
											for(String[][] clientInfo : onlineClient) { 
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
		if(complainDropdownType.isVisible())
			categoryAndType = complainDropdownCategory.getSelectedItem() + " ("+complainDropdownType.getSelectedItem()+")";
		else 
			categoryAndType = complainDropdownCategory.getSelectedItem().toString();
			
		Packet04Complain Packet = new Packet04Complain(new Complain(1, user.getUserId(), categoryAndType, complainTextField.getText(), "", "", ""));
		Packet.writeData(MainWindow.getClientSocket()); 
	}
	
			
	boolean editTable = false;
	public void populateTable() {
		int total = 0, resolved = 0, unResolved = 0;
		
		String columnNames[]={"ID","TYPE","MESSAGE","REPRESENTATIVE","ASSIGNED TECHNICIAN","VISIT DATE"};
		Object[][] rowData = new Object[complains.size()][columnNames.length];
		
		for(int row = 0; row < complains.size(); row ++) { 
			rowData[row][0] = complains.get(row).getId();
			rowData[row][1] = complains.get(row).getType();
			rowData[row][2] = complains.get(row).getMessage();
			if(complains.get(row).getRepId().matches(user.getUserId()))// if representative is loggedin
				rowData[row][3] = "ME";
			else
				rowData[row][3] = complains.get(row).getRepId();
			if(complains.get(row).getTechId().matches(user.getUserId()))// if Technician is loggedin
				rowData[row][4] = "ME";
			else
				rowData[row][4] = complains.get(row).getTechId();
			rowData[row][5] = complains.get(row).getVisitDate(); 
			
			total ++;
			if(!complains.get(row).getVisitDate().equals(""))
				resolved++;
			unResolved = (total - resolved);
			complainCounter.setText("  Total Complain(s) : " + total 
							    + "\n  Resolved               : " + resolved
							    + "\n  Un-Resolved:        : " + unResolved); 
		}
		
		DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames);
		table.setModel(tableModel);
		 
		if(displayComplainTable) {
			if (editTable)
				table.setEnabled(true);
			else
				table.setEnabled(false);
			
			scrollPane.setVisible(true); 
		}else {
			scrollPane.setVisible(false); 
		}
	}
	
	String ComplainID = "";
	String TechIdFieldInTable;
	String VisitDateFieldInTable;
	@Override
	public void valueChanged(ListSelectionEvent e) {// use for table selection for assigning complains and setting date for Technician
		 
		String Data = null;
		int[] row = table.getSelectedRows();
		int[] columns = table.getSelectedColumns();
		for (int i = 0; i < row.length; i++) {
			for (int j = 0; j < columns.length; j++) {
				ComplainID = String.valueOf(table.getValueAt(row[i], 0));
				TechIdFieldInTable = String.valueOf(table.getValueAt(row[i], 4));// use to check if the field is empty/ complain is already assigned
				VisitDateFieldInTable = String.valueOf(table.getValueAt(row[i], 5));// use to check if the field is empty/ date is already set
				Data = (String) table.getValueAt(row[i], columns[j]);
			}
		}
		if(user instanceof Employee) {
			if(((Employee) user).getJobTitle().matches("Representative")) {
				if(TechIdFieldInTable.equals("")) {
					assignComplainButton.setVisible(false);
					technitionDropdownList.setVisible(true);
				}else
					JOptionPane.showInternalMessageDialog(dashboard,"Complain Already Assigned", user.getFirstName(),JOptionPane.ERROR_MESSAGE);
			}else {
				if(VisitDateFieldInTable.equals("")) {
					setVisitDateButton.setVisible(true);
					if(setVisitDateButton.getText().matches("Finalize Date"))
						setdateTextField.setVisible(true); 
				}else
					JOptionPane.showInternalMessageDialog(dashboard,"Date Already Set", user.getFirstName(),JOptionPane.ERROR_MESSAGE);
				
			}
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
		this.complainDropdownCategory.setSelectedIndex(index);
	}
	
	public JDesktopPane getDashboard() {
		return dashboard; 
	}
	
	public void setComplainText(String complainText) {
		this.complainTextField.setText(complainText);
	}


	public List<Complain> getComplains() {
		return complains;
	}


	public void setComplains(List<Complain> complains) { 
		this.complains = complains;
		populateTable();
	}


	public List<String[][]> getOnlineClient() { 
		return onlineClient;
	}


	public void setOnlineClient(List<String[][]> onlineClient) {
		this.onlineClient = onlineClient;
		for(String[][] clientInfo : onlineClient) { 
			if(clientInfo[0][0].equals(user.getUserId()))
			onlineClient.remove(clientInfo);//remove this user info from the list to prevent yourself from showing up in the chat
		}
	}


	public List<String[][]> getTechnicions() {
		return technicions; 
	}


	public void setTechnicions(List<String[][]> technicions) {
		this.technicions = technicions;
	}
}