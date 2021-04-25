package frame;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import image.loadImages;
import packet.Packet01Login;


public class LoginWindow extends JInternalFrame implements ActionListener{
	private JTextField loginIdField;
	private JLabel loginIdLabel;
	
	private JPasswordField passwordField; 
	private JLabel passwordLabel;
	
	private JButton signUp;
	private JButton Login;
	
	private JLabel background;
	
	
	public void intializeComponent() {
		
		loginIdField = new JTextField(); 
		loginIdField.setBounds(10, 50, 180, 25); 
		loginIdField.setBorder(new LineBorder(java.awt.Color.RED, 1));
		loginIdField.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		loginIdLabel = new JLabel("User ID");
		loginIdLabel.setBounds(200, 50, 150, 25);
		loginIdLabel.setForeground(Color.WHITE);
		loginIdLabel.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		passwordField = new JPasswordField(); 
		passwordField.setBounds(10, 90, 180, 25); 
		passwordField.setBorder(new LineBorder(java.awt.Color.RED, 1));
		passwordField.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(200, 90, 150, 25);
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		signUp = new JButton("Sign-Up");
		signUp.addActionListener(this);
		signUp.setBounds(80, 150, 100, 30);
		
		Login = new JButton("Submit");
		Login.addActionListener(this);
		Login.setBounds(200, 150, 100, 30);
		
		background = new JLabel();
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setIcon(new ImageIcon(loadImages.formBackground)); 
		background.setBounds(0, 0,400, 250);
			
		
	}
	public void addComponentsToWindow(){
		getContentPane().add(loginIdField);
		getContentPane().add(loginIdLabel);
		
		getContentPane().add(passwordField);
		getContentPane().add(passwordLabel);
		
		getContentPane().add(signUp);
		getContentPane().add(Login);
		
		getContentPane().add(background);
	}
	
	public void setWindowsProperties() {
		setLayout(null);
		this.setBounds(125, 150, 400, 250);
		this.setVisible(true); 
	}
	public LoginWindow() {
		super("Login",false,false,false,true); 
		intializeComponent() ;
		addComponentsToWindow();
		setWindowsProperties();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Sign-Up")) {
			MainWindow.getDesktopPane().add(new SignUpWindow());
			this.dispose();
			MainWindow.getDesktopPane().moveToBack(background); 
		}
		
		if(e.getActionCommand().equals("Submit")) {
			if(loginIdField.getText().equals("")) 
				JOptionPane.showMessageDialog(null, "Enter your User Id", "",JOptionPane.ERROR_MESSAGE);
			else if(passwordField.getText().equals("")) 
				JOptionPane.showMessageDialog(null, "Enter your Password", "",JOptionPane.ERROR_MESSAGE);
			else {
				Packet01Login Packet = new Packet01Login(loginIdField.getText(), MainWindow.hashPasword(passwordField.getText()));
				Packet.writeData(MainWindow.getClientSocket());  
			}
			
		} 
	}
	public JTextField getLoginIdField() {
		return loginIdField;
	}
	
	
}
