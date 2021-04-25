package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import domain.Register;
import image.loadImages;
import packet.Packet00Register;

public class SignUpWindow extends JInternalFrame implements ActionListener{
	
	private String[] choices;
	private JComboBox<String> dropdown;
	private JLabel nameTitle;
	
	private JTextField firstName;
	private JLabel firstNameLabel;
	
	private JTextField lastName;
	private JLabel lastNameLabel;
	
	private JTextField email;
	private JLabel emailLabel;
	
	private JTextField phoneNumber;
	private JLabel phoneNumberLabel;
	
	private JPasswordField passwordField; 
	private JLabel passwordLabel;
	
	private JPasswordField passwordConfirmField; 
	private JLabel passwordConfirmLabel;
	
	private JButton signUp;
	private JButton Login;
	
	private JLabel background;
	
	
	public void intializeComponent() {
		
		choices = new String[]{"", "Mr", "Ms", "Mrs"};
		dropdown = new JComboBox<String>(choices);
		dropdown.setBounds(10, 20, 180, 25);
		
		nameTitle = new JLabel("Name Title");
		nameTitle.setBounds(200, 20, 150, 25);
		nameTitle.setForeground(Color.WHITE);
		nameTitle.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		firstName = new JTextField(); 
		firstName.setBounds(10, 60, 180, 25); 
		firstName.setBorder(new LineBorder(java.awt.Color.RED, 1));
		firstName.setFont(new Font("arial", Font.TYPE1_FONT, 16)); 
		
		firstNameLabel = new JLabel("first Name"); 
		firstNameLabel.setBounds(200, 60, 150, 25);
		firstNameLabel.setForeground(Color.WHITE);
		firstNameLabel.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		lastName = new JTextField(); 
		lastName.setBounds(10, 100, 180, 25); 
		lastName.setBorder(new LineBorder(java.awt.Color.RED, 1));
		lastName.setFont(new Font("arial", Font.TYPE1_FONT, 16)); 
		
		lastNameLabel = new JLabel("Last Name"); 
		lastNameLabel.setBounds(200, 100, 150, 25);
		lastNameLabel.setForeground(Color.WHITE);
		lastNameLabel.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		email = new JTextField(); 
		email.setBounds(10, 140, 180, 25); 
		email.setBorder(new LineBorder(java.awt.Color.RED, 1));
		email.setFont(new Font("arial", Font.TYPE1_FONT, 16)); 
		
		emailLabel = new JLabel("Email"); 
		emailLabel.setBounds(200, 140, 150, 25);
		emailLabel.setForeground(Color.WHITE);
		emailLabel.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		phoneNumber = new JTextField(); 
		phoneNumber.setBounds(10, 180, 180, 25); 
		phoneNumber.setBorder(new LineBorder(java.awt.Color.RED, 1));
		phoneNumber.setFont(new Font("arial", Font.TYPE1_FONT, 16)); 
		
		phoneNumberLabel = new JLabel("Phone Number"); 
		phoneNumberLabel.setBounds(200, 180, 150, 25);
		phoneNumberLabel.setForeground(Color.WHITE);
		phoneNumberLabel.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		passwordField = new JPasswordField(); 
		passwordField.setBounds(10, 220, 180, 25); 
		passwordField.setBorder(new LineBorder(java.awt.Color.RED, 1));
		passwordField.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(200, 220, 150, 25);
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		passwordConfirmField = new JPasswordField(); 
		passwordConfirmField.setBounds(10, 260, 180, 25); 
		passwordConfirmField.setBorder(new LineBorder(java.awt.Color.RED, 1));
		passwordConfirmField.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		passwordConfirmLabel = new JLabel("Confirm Password");
		passwordConfirmLabel.setBounds(200, 260, 150, 25);
		passwordConfirmLabel.setForeground(Color.WHITE);
		passwordConfirmLabel.setFont(new Font("arial", Font.TYPE1_FONT, 16));
		
		signUp = new JButton("Login");
		signUp.addActionListener(this);
		signUp.setBounds(120, 320, 100, 30);
		
		Login = new JButton("Submit");
		Login.addActionListener(this);
		Login.setBounds(240, 320, 100, 30);
		
		background = new JLabel();
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setIcon(new ImageIcon(loadImages.formBackground)); 
		background.setBounds(0, 0,400, 410);
		
	}
	public void addComponentsToWindow(){
		getContentPane().add(dropdown);
		getContentPane().add(nameTitle);
		
		getContentPane().add(firstName);
		getContentPane().add(firstNameLabel);
		
		getContentPane().add(lastName);
		getContentPane().add(lastNameLabel);
		
		getContentPane().add(email);
		getContentPane().add(emailLabel);
		
		getContentPane().add(phoneNumber);
		getContentPane().add(phoneNumberLabel);
		
		getContentPane().add(passwordField);
		getContentPane().add(passwordLabel);
		
		getContentPane().add(passwordConfirmField);
		getContentPane().add(passwordConfirmLabel);
		
		getContentPane().add(signUp);
		getContentPane().add(Login);
		
		getContentPane().add(background);
	}
	
	public void setWindowsProperties() {
		setLayout(null);
		this.setBounds(125,150, 400, 410); 
		this.setVisible(true); 
	}
	
	
	public SignUpWindow() {
		super("Sign-Up",false,false,false,true);  
		intializeComponent() ;
		addComponentsToWindow();
		setWindowsProperties();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Submit")) {
			if(dropdown.getSelectedItem().equals("")) 
				JOptionPane.showMessageDialog(null, "Select Name Title", "",JOptionPane.ERROR_MESSAGE);
			else if(firstName.getText().equals("")) 
				JOptionPane.showMessageDialog(null, "Enter your First Name", "",JOptionPane.ERROR_MESSAGE);
			else if(lastName.getText().equals("")) 
				JOptionPane.showMessageDialog(null, "Enter your Last Name", "",JOptionPane.ERROR_MESSAGE);
			else if(email.getText().equals("")) 
				JOptionPane.showMessageDialog(null, "Enter your Email", "",JOptionPane.ERROR_MESSAGE);
			else if(phoneNumber.getText().equals("")) 
				JOptionPane.showMessageDialog(null, "Enter your Phone Number", "",JOptionPane.ERROR_MESSAGE);
			else if(passwordField.getText().equals("")) 
				JOptionPane.showMessageDialog(null, "Enter your Password", "",JOptionPane.ERROR_MESSAGE); 
			else if(passwordConfirmField.getText().equals("")) 
				JOptionPane.showMessageDialog(null, "ConfirmPassword", "",JOptionPane.ERROR_MESSAGE);
			else if(!passwordField.getText().equals(passwordConfirmField.getText())) 
				JOptionPane.showMessageDialog(null, "Passwords Did Not Match!", "",JOptionPane.ERROR_MESSAGE);
			else {
				Register Register = new Register(dropdown.getSelectedItem().toString(),firstName.getText(),lastName.getText(),Long.valueOf(phoneNumber.getText()), email.getText(), MainWindow.hashPasword(passwordField.getText())); 
				Packet00Register Packet = new Packet00Register(Register);
				Packet.writeData(MainWindow.getClientSocket());
				this.dispose();
			}
		}else if(e.getActionCommand().equals("Login")) {
			MainWindow.getDesktopPane().add(new LoginWindow());
			MainWindow.getDesktopPane().moveToBack(background); 
			this.dispose();
		}
	}
	

}
