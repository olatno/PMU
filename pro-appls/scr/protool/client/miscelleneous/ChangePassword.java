package scr.protool.client.miscelleneous;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import scr.protool.client.utilities.*;
import javax.naming.*;

public class ChangePassword extends JFrame implements ActionListener{
	
	private JLabel companyName;
	private JLabel propt;
	private JLabel error;
	private JTextField nameText;
	private JPasswordField passwordText;
	private JPasswordField passwordText1;
	private JButton ok;
	private JPanel text;
	private JPanel button;
	private JPanel banner;
	private JPanel mainPanel;
	private JButton cancel;
	private final int textFieldColunm = 1;
	private final double weightix = 1.0;
	private final double weightiy = 0.1;
	private final int ipadiy = 3;
	private final int ipadix = 280;
	private BeanStud bean;
	private ArrayList<ArrayList<Object>> users = null;
	private String pronter1 = "Enter your new password";
	private String pronter2 = "You have entered wrong password" ;
	private Utilities ul = new Utilities();
	private PaymentInUtilities pu =  new PaymentInUtilities();
	private boolean pass;
	private int getUserId;
	Container con = getContentPane();
	
	// display user interface to be displayed
	public ChangePassword(boolean pass, String title, int getUserId){
		super(title);
		
		bean = new BeanStud();
		this.getUserId = getUserId;
		this.pass = pass;		
		text = new JPanel();
		button = new JPanel();
		mainPanel = new JPanel();
		text.setLayout(new GridBagLayout());
		button.setLayout(new GridBagLayout());
		mainPanel.setLayout(new GridBagLayout());
		banner = new JPanel();
		banner.setLayout(new BorderLayout());
		users = pu.getArrayListOfArrayList(bean.connect().getSingleUserInfos(getUserId));
			
		GridBagConstraints companyLabel = new GridBagConstraints();
		companyName = new JLabel();
		banner.add(companyName);
		companyLabel.gridx = 0;
		companyLabel.weighty = weightiy;
		companyLabel.gridwidth = GridBagConstraints.REMAINDER;
		companyLabel.anchor = GridBagConstraints.NORTH;
		mainPanel.add(banner, companyLabel);
		companyName.setBackground(Color.decode("#A9B9B9"));
		companyName.setOpaque( true );
		companyName.setFont(new Font("Trebuchet Ms", Font.BOLD, 34));
		companyName.setForeground(Color.white);
		companyName.setPreferredSize(new Dimension (420, 60));
		companyName.setText("[your company name]");
		
		GridBagConstraints proptLabel = new GridBagConstraints();
		propt = new JLabel();
		proptLabel.gridx = 0;
		proptLabel.gridwidth = 2;
		proptLabel.anchor = GridBagConstraints.LINE_START;
		proptLabel.weighty = weightix;
		proptLabel.insets = new Insets(0, 0, 5, 0);
		text.add(propt, proptLabel);

		if(pass == true){
			propt.setText(pronter1);
		}
		else {
			propt.setForeground(Color.red);
			propt.setText(pronter2);
		}
		
		GridBagConstraints usernameLabel = new GridBagConstraints();
		usernameLabel.gridx = 0;
		usernameLabel.gridy = 2;
		usernameLabel.insets = new Insets(5, 0, 0, 0);
		usernameLabel.anchor =  GridBagConstraints.EAST;
		usernameLabel.weightx = weightix;
		text.add(new JLabel("User Name:"), usernameLabel);
		
		GridBagConstraints nameTextField = new GridBagConstraints();
		nameText = new JTextField(textFieldColunm);
		nameTextField.gridx = 1;
		nameTextField.gridy = 2;
		nameTextField.insets = new Insets(5, 0, 0, 20);
		nameTextField.ipady = ipadiy;
		nameTextField.ipadx = ipadix;
		nameTextField.anchor =  GridBagConstraints.WEST;
		text.add(nameText, nameTextField);
		nameText.setEditable(false);
		nameText.setText(String.valueOf(users.get(0).get(1)));
		
		GridBagConstraints passwordLabel = new GridBagConstraints();
		passwordLabel.gridx = 0;
		passwordLabel.gridy = 3;
		passwordLabel.anchor =  GridBagConstraints.EAST;
		passwordLabel.insets = new Insets(5, 0, 0, 0);
		passwordLabel.weightx = weightix;
		text.add(new JLabel("Old Password:"), passwordLabel);
		
		GridBagConstraints passwordTextField = new GridBagConstraints();
		passwordText = new JPasswordField(textFieldColunm);
		passwordTextField.gridx = 1;
		passwordTextField.gridy = 3;
		passwordTextField.insets = new Insets(5, 0, 0, 20);
		passwordTextField.ipady = ipadiy;
		passwordTextField.ipadx = ipadix;
		passwordTextField.anchor =  GridBagConstraints.WEST;
		text.add(passwordText, passwordTextField);
		
		GridBagConstraints passwordLabel1 = new GridBagConstraints();
		passwordLabel1.gridx = 0;
		passwordLabel1.gridy = 4;
		passwordLabel1.insets = new Insets(5, 0, 0, 0);
		passwordLabel1.weightx = weightix;
		text.add(new JLabel("New Password:"), passwordLabel1);
		
		GridBagConstraints passwordTextField1 = new GridBagConstraints();
		passwordText1 = new JPasswordField(textFieldColunm);
		passwordTextField1.gridx = 1;
		passwordTextField1.gridy = 4;
		passwordTextField1.insets = new Insets(5, 0, 0, 0);
		passwordTextField1.ipady = ipadiy;
		passwordTextField1.ipadx = ipadix;
		passwordTextField1.anchor =  GridBagConstraints.WEST;
		text.add(passwordText1, passwordTextField1);
		
		GridBagConstraints textLabel = new GridBagConstraints();
		textLabel.gridx = 0;
		textLabel.gridy = 1;
		textLabel.insets = new Insets(0, 0, 20, 0);
		textLabel.anchor = GridBagConstraints.NORTH;
		mainPanel.add(text, textLabel);
	
		GridBagConstraints okButton = new GridBagConstraints();
		ok = new JButton("<html><b><u>O</u>K</b></html>");
		okButton.gridx = 0;
		okButton.gridy = 4;
		okButton.ipadx = 25;
		okButton.insets = new Insets(30, 0, 10, 0);
		button.add(ok, okButton);
		
		GridBagConstraints cancelButton = new GridBagConstraints();
		cancel = new JButton("<html><b>Not Now</b></html>");
		cancelButton.gridx = 1;
		cancelButton.gridy = 4;
		cancelButton.weightx = weightix;
		cancelButton.insets = new Insets(30, 5, 10, 0);
		button.add(cancel, cancelButton);
		
		GridBagConstraints buttonLabel = new GridBagConstraints();
		buttonLabel.gridx = 0;
		buttonLabel.gridy = 2;
		buttonLabel.insets = new Insets(0, 0, 0, 35);
		buttonLabel.anchor = GridBagConstraints.LAST_LINE_END;
		mainPanel.add(button, buttonLabel);
		
		con.add(mainPanel);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		this.setIconImage(ul.mainImage("images/src/LogIn.png"));
		setSize(400, 250);
		pack(); 
		setVisible(true); 
		setResizable(false);
		
		ok.addActionListener(this);
		cancel.addActionListener(this);
	
	}
	public boolean getWrong(){
		return pass;
	}
	
	public void setWrong(boolean pass){
		this.pass = pass;
	}
    public void actionPerformed( ActionEvent e) {  
		if (e.getSource().equals(cancel)){
			dispose();	
		}
	
		if (e.getSource().equals(ok)){
			char [] passwordChar  = passwordText.getPassword();
			String userPassword = new String(passwordChar);
			if(((String)users.get(0).get(2)).equals(userPassword)){
				ArrayList<Object> temp = new ArrayList<Object>();
				temp.add(Integer.valueOf(getUserId));
				char [] newPasswordChar = passwordText1.getPassword();
				temp.add(new String(newPasswordChar));
				bean.connect().updatePassword(ul.writeArrayList(temp));
				dispose();
			}
			else{
				dispose();
				new ChangePassword(false, "Change Password", getUserId);
			}
		}
	}

}