package frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import domain.User;
import image.loadImages;
import packet.Packet03Chat;

public class ChatWindow extends JInternalFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private static JTextArea ChatArea ; // where the chat is displayed
	private static JScrollPane ScrollPlane; 
	private static JTextField ChatBox;// where you type your message
	private static JLabel background;
	private User ME;// user info for the chat
	private String receiverId;
	

	public void intializeComponent() {
		ChatArea  = new JTextArea(); // where the chat is displayed
		ScrollPlane = new JScrollPane(ChatArea); 
		ChatBox = new JTextField();// where you type your message

		
		ScrollPlane.setBounds(5, 2, 365, 310);// x, y, width, height
		ChatArea.setBackground(Color.BLACK);
		ChatArea.setForeground(Color.WHITE);
		ChatArea.setEditable(false);
		ChatArea.setLineWrap(true);
		ChatArea.setWrapStyleWord(true);
		ScrollPlane.setAutoscrolls(true);
		
		ChatBox.setSize(370, 40); // width, height
		ChatBox.setLocation(2, 320); // x, y
		ChatBox.addActionListener(this);
		
		
		background = new JLabel();
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setIcon(new ImageIcon(loadImages.formBackground)); 
		background.setBounds(0, 0,400, 410);
		
	}
	
	
	public void addComponentsToWindow(){
		getContentPane().add(ScrollPlane);
		getContentPane().add(ChatBox);
		getContentPane().add(background);
	}
	
	public void setWindowsProperties() {
		setLayout(null);
		this.setBounds(125, 150, 400, 410); 
		this.setVisible(true); 
	}
	
	
	public ChatWindow(User user, String receiverID, String Name) { 
		super("Connect with: " + Name,false,false,false,true); 
		ME = user;
		receiverId = receiverID; 
		intializeComponent() ;
		addComponentsToWindow();
		setWindowsProperties();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String getText = ChatBox.getText();
		if(!getText.equals("")) {
			ChatBox.setText("");
			Packet03Chat Chat = new Packet03Chat(ME.getUserId(), ME.getFirstName(), receiverId, getText);
			Chat.writeData(MainWindow.getClientSocket());
		}
		
	}


	public JTextArea getChatArea() {
		return ChatArea;
	}
	
	public void append(Packet03Chat chat) {
		String message = "";
		if(chat.getSenderId().equals(ME.getUserId()))
			message = "Me: " + chat.getMessage();
		else
			message = chat.getSenderName() + ": " + chat.getMessage();
		ChatArea.append(message + "\n"); 
	}
	
}
