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
import javax.naming.*;
import scr.protool.client.utilities.*;

public class UserLogIn extends JPanel implements ActionListener{
	
	private JLabel companyName;
	private JLabel propt;
	private JLabel error;
	private JTextField nameText;
	private JPasswordField passwordText;
	private JButton ok;
	private JPanel text;
	private JPanel button;
	private JPanel banner;
	private JButton cancel;
	private final int textFieldColunm = 1;
	private final double weightix = 1.0;
	private final double weightiy = 0.1;
	private final int ipadiy = 3;
	private final int ipadix = 280;
	private PaymentInUtilities pu =  new PaymentInUtilities();
	private static Utilities ulex =  new Utilities();
	private String pronter1 = "Enter your user name and password";
	private String pronter2 = "You have entered wrong password or username" ;
	private String pronter3 = "Your account has been deactivated" ;
	private static boolean wrong, activate = false;
	private static boolean pass = true;
	private static JFrame frame = null;
	private int passwordid;
	private ArrayList<ArrayList<Object>> users ;
	
	// display user interface to be displayed
	public UserLogIn(){
		super(new GridBagLayout()); 
		text = new JPanel();
		button = new JPanel();
		text.setLayout(new GridBagLayout());
		button.setLayout(new GridBagLayout());
		banner = new JPanel();
		banner.setLayout(new BorderLayout());
		users = pu.getArrayListOfArrayList(new BeanStud().connect().projectUserInfos());

			
		GridBagConstraints companyLabel = new GridBagConstraints();
		companyName = new JLabel();
		banner.add(companyName);
		companyLabel.gridx = 0;
		companyLabel.weighty = weightiy;
		companyLabel.gridwidth = GridBagConstraints.REMAINDER;
		companyLabel.anchor = GridBagConstraints.NORTH;
		add(banner, companyLabel);
		companyName.setBackground(Color.decode("#A9B9B9"));
		companyName.setOpaque( true );
		companyName.setFont(new Font("Trebuchet Ms", Font.BOLD, 34));
		companyName.setForeground(Color.white);
		companyName.setPreferredSize(new Dimension (410, 60));
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
		else if(wrong == true ){
			propt.setForeground(Color.red);
			propt.setText(pronter2);
		}
		else if(activate == true){
			propt.setForeground(Color.red);
			propt.setText(pronter3);
		}
		
		GridBagConstraints usernameLabel = new GridBagConstraints();
		usernameLabel.gridx = 0;
		usernameLabel.gridy = 2;
		usernameLabel.insets = new Insets(5, 0, 0, 0);
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
		
		GridBagConstraints passwordLabel = new GridBagConstraints();
		passwordLabel.gridx = 0;
		passwordLabel.gridy = 3;
		passwordLabel.insets = new Insets(5, 0, 0, 0);
		passwordLabel.weightx = weightix;
		text.add(new JLabel("Password:"), passwordLabel);
		
		GridBagConstraints passwordTextField = new GridBagConstraints();
		passwordText = new JPasswordField(textFieldColunm);
		passwordTextField.gridx = 1;
		passwordTextField.gridy = 3;
		passwordTextField.insets = new Insets(5, 0, 0, 20);
		passwordTextField.ipady = ipadiy;
		passwordTextField.ipadx = ipadix;
		passwordTextField.anchor =  GridBagConstraints.WEST;
		text.add(passwordText, passwordTextField);
		
		GridBagConstraints textLabel = new GridBagConstraints();
		textLabel.gridx = 0;
		textLabel.gridy = 1;
		textLabel.insets = new Insets(0, 0, 30, 0);
		textLabel.anchor = GridBagConstraints.NORTH;
		add(text, textLabel);
	
		GridBagConstraints okButton = new GridBagConstraints();
		ok = new JButton("OK");
		okButton.gridx = 0;
		okButton.gridy = 4;
		okButton.ipadx = 25;
		okButton.insets = new Insets(30, 0, 10, 0);
		button.add(ok, okButton);
		
		GridBagConstraints cancelButton = new GridBagConstraints();
		cancel = new JButton("Cancel");
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
		add(button, buttonLabel);
		
		setPreferredSize(new Dimension (400, 250));
		ok.addActionListener(this);
		cancel.addActionListener(this);
	}
	
    public void actionPerformed( ActionEvent e) {  
		if (e.getSource().equals(cancel)){
			frame.dispose();	
		}
		if (e.getSource().equals(ok)){
			for(int i = 0; i < users.size(); i++){
				ArrayList<Object> temp = users.get(i);
				char [] passwordChar  = passwordText.getPassword();
				String userPassword = new String(passwordChar);
				if(((String)temp.get(1)).equals(nameText.getText()) && ((String)temp.get(2)).equals(userPassword)){
					if(((Boolean)temp.get(3)).booleanValue()){
						frame.dispose();
						new ProtoolMainGUI("Flexiblebooks", (ArrayList<String>)temp.get(5)).setPasswordId(((Integer)temp.get(0)).intValue());
						i = users.size();
					}
					else{

						frame.dispose();
						createAndShowGUI(false, false, true);
						i = users.size();
					}
				}
				if(i == users.size()-1){
					frame.dispose();
					createAndShowGUI(false, true, false);

				}	
			}

		}
	}
	
	//Create and set up the window. 
	private static JFrame createAndShowGUI(boolean passes, boolean wrongs, boolean activates ) { 
		pass = passes; wrong = wrongs; activate  = activates;
		frame = new JFrame("Login"); 
		frame.setFont(new Font("Trebuchet Ms", Font.BOLD, 20));
		frame.setForeground(Color.white);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		//Create and set up the content pane. 
		UserLogIn newContentPane = new UserLogIn(); 
		newContentPane.setOpaque(true); //content panes must be opaque 
		frame.setContentPane(newContentPane);
		frame.setIconImage(ulex.mainImage("images/src/LogIn.png"));		
		//Display the window. 
		frame.setSize(510, 370);
		frame.pack(); 
		frame.setVisible(true); 
		frame.setResizable(false);
		return frame;
	} 
	public static void main(String[] args) { 
	//Schedule a job for the event-dispatching thread: 
	//creating and showing this application's GUI.
	  try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception unused) {
         // Ignore exception because we can't do anything.  Will use default.
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() { 
			public void run() { 
			createAndShowGUI(true, false, false); 
			} 
		}); 
	} 
}