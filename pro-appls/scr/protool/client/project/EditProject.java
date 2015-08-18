
package scr.protool.client.project;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.text.*;
import javax.naming.*;
import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import scr.protool.client.utilities.*;
import scr.protool.client.miscelleneous.BeanStud;
/**

*
*

*
* @author <a href="mailto:"></a>
* @version 1.0
*/
public class EditProject extends JFrame implements ActionListener{
 
    private JTextField projectId;
    private JTextField projectTitle;
    private JTextField companyName;
    private JTextField quote;
    private JTextField mobile;
    private SpinnerHelper sh;
	private SpinnerHelper shInner;
	private JSpinner duration;
	private JSpinner date;
	private JSpinner validDate;
	private JComboBox projectStatus;
    private JTextField addresses;
    private JTextField telephone;
    private JTextField email; 
	private JTextField contact;
    private JButton update;
    private JButton exit;
	private JButton quotebutton;
	private JButton address;
    private JPanel addpanel;
    private JPanel buttonpanel;
    private JPanel generalPanel;
    private String client = "";
    private BeanStud bean;
	private String strValid;
	private final int textFieldColunm = 1;
	private Utilities ul = new Utilities();
	private Locale locale = Locale.UK;
	private ArrayList<Object> details;
	private String addressrt;
	private int proId;
    Container con = getContentPane();
    
    public EditProject(String title, int proId){
   	super(title); 
	this.proId = proId;
	bean = new BeanStud();
	//try{
	details = ul.getArrayList(bean.connect().projectDetail(proId));
	addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();
	addressrt = new String();
	sh = new SpinnerHelper();
	shInner = new SpinnerHelper();
	//Panels
	addpanel.setLayout(new GridBagLayout());
	buttonpanel.setLayout(new GridBagLayout());
	generalPanel.setLayout(new BorderLayout());
	
	//Textfields use for search
	GridBagConstraints idLabel = new GridBagConstraints();
	//   	idLabel.insets = new Insets(20, 0, 0, 0);
	idLabel.gridx = 0;
	idLabel.gridy = 0;
	idLabel.anchor =  GridBagConstraints.EAST;
       	idLabel.weighty = 0.1;
	//addpanel.add(new JLabel("Project Id:"), idLabel);

	GridBagConstraints idTextField = new GridBagConstraints();
	projectId = new JTextField();
	idTextField.gridx = 1;
	idTextField.gridy = 0;
	idTextField.ipady = 2;
	idTextField.ipadx = 40;
	idTextField.anchor =  GridBagConstraints.WEST;
	//addpanel.add(projectId, idTextField);
	projectId.setEditable(false);

	GridBagConstraints titleLabel = new GridBagConstraints();
	titleLabel.gridx = 0;
	titleLabel.gridy = 0;
      	titleLabel.weighty = 0.1;
	//	titleLabel.insets = new Insets(0, 0, 15, 0);
	titleLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Project Title:"), titleLabel);
	
	GridBagConstraints titleTextField = new GridBagConstraints();
	projectTitle = new JTextField(textFieldColunm);
	titleTextField.gridx = 1;
	titleTextField.gridy = 0;
	titleTextField.ipady = 2;
	titleTextField.ipadx = 200;
	titleTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(projectTitle, titleTextField);
	//try{
	projectTitle.setText((String)details.get(0));

	GridBagConstraints companyLabel = new GridBagConstraints();
	//	clientLabel.insets = new Insets(0, 0, 15, 0);
	companyLabel.gridx = 0;
	companyLabel.gridy = 1;
	companyLabel.weighty = 0.1;
	companyLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Company Name:"), companyLabel);

	GridBagConstraints companyField = new GridBagConstraints();
	companyName = new JTextField(textFieldColunm);
	companyField.gridx = 1;
	companyField.gridy = 1;
	companyField.ipady = 2;
	companyField.ipadx = 200;
	companyField.anchor =  GridBagConstraints.WEST;
	addpanel.add(companyName, companyField);
	
	companyName.setText((String)details.get(1));

	GridBagConstraints addressLabel = new GridBagConstraints();
	address = new JButton("Addresses");
	addressLabel.gridx = 0;
	addressLabel.gridy = 2;
	addressLabel.weighty = 0.1;
	//	addressLabel.insets = new Insets(0, 0, 15, 0);
	addressLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(address, addressLabel);

	GridBagConstraints addrTextField = new GridBagConstraints();
	addresses = new JTextField(textFieldColunm);
	addrTextField.ipady = 2;
	addrTextField.ipadx = 250;
	addrTextField.gridx = 1;
	addrTextField.gridy = 2;
	addrTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(addresses, addrTextField);
	addresses.setEditable(false);
	
	ArrayList<String> addstr = (ArrayList<String>)details.get(2);

	String empty = "\" \"";
	for(int i = 0; i < addstr.size(); i++){
		if(addstr.get(i)==null){
			addressrt = addressrt+","+empty;
		}
		else{
			addressrt = addressrt+","+addstr.get(i);
		}
	}
	addresses.setText(addressrt.trim().substring(1, addressrt.length()));

	GridBagConstraints teleLabel = new GridBagConstraints();
	teleLabel.gridx = 0;
	teleLabel.gridy = 3;
	teleLabel.weighty = 0.1;
	//	teleLabel.insets = new Insets(0, 0, 15, 0);
	teleLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Telephone:"), teleLabel);
	
	GridBagConstraints teleTextField = new GridBagConstraints();
	telephone = new JTextField(textFieldColunm);
	teleTextField.gridx = 1;
	teleTextField.gridy = 3;
	teleTextField.ipady = 2;
	teleTextField.ipadx = 200;
	teleTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(telephone, teleTextField);
	telephone.setText((String)details.get(3));

	GridBagConstraints mobileLabel = new GridBagConstraints();
	mobileLabel.gridx = 0;
	mobileLabel.gridy = 4;
	mobileLabel.weighty = 0.1;
	//	costLabel.insets = new Insets(0, 0, 15, 0);
	mobileLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Mobile:"), mobileLabel);

	GridBagConstraints mobileTextField = new GridBagConstraints();
	mobile = new JTextField(textFieldColunm);
	mobileTextField.gridx = 1;
	mobileTextField.gridy = 4;
	mobileTextField.ipady = 2;
	mobileTextField.ipadx = 200;
	mobileTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(mobile, mobileTextField);
	mobile.setText((String)details.get(4));

	GridBagConstraints emailLabel = new GridBagConstraints();
	emailLabel.gridx = 0;
	emailLabel.gridy = 5;
	emailLabel.weighty = 0.1;
	//	emailLabel.insets = new Insets(0, 0, 15, 0);
	emailLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Email:"), emailLabel);

	GridBagConstraints emailTextField = new GridBagConstraints();
	email = new JTextField(textFieldColunm);
	emailTextField.gridx = 1;
	emailTextField.gridy = 5;
	emailTextField.ipady = 2;
	emailTextField.ipadx = 200;
	emailTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(email, emailTextField);
	email.setText((String)details.get(5));

	GridBagConstraints statusLabel = new GridBagConstraints();
	statusLabel.gridx = 0;
	statusLabel.gridy = 6;
	statusLabel.weighty = 0.1;
    statusLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Status:"), statusLabel);
	
	GridBagConstraints combbox = new GridBagConstraints();
	String[] taskStrings = { "Open", "Continue", "Closed", "Cancel"};
	projectStatus = new JComboBox(taskStrings);
	combbox.anchor =  GridBagConstraints.EAST;
	combbox.gridx = 1;
	combbox.gridy = 6;
	combbox.insets = new Insets(0, 0, 0, 160);
	combbox.ipadx = 35;
	addpanel.add(projectStatus, combbox);
	String cobstr = (String)details.get(6);
	projectStatus.setSelectedItem(cobstr.charAt(0)+cobstr.substring(1).toLowerCase());

	GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 7;
	date1Label.weighty = 0.1;
	//	date1Label.insets = new Insets(0, 0, 15, 0);
    date1Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Date:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	//date = new JTextField();
	date = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 7;
	date1TextField.ipady = 2;
	date1TextField.ipadx = -20;
	date1TextField.anchor =  GridBagConstraints.WEST;
	//addpanel.add(sh.getGenericDate(date), date1TextField);
	addpanel.add(sh.getGenericDateEdit(date, ul.usDateString((String)details.get(7))), date1TextField);

	GridBagConstraints durationLabel = new GridBagConstraints();
	durationLabel.gridx = 0;
	durationLabel.gridy = 8;
	durationLabel.ipadx = 60;
	durationLabel.weighty = 0.1;
	//	durationLabel.insets = new Insets(0, 0, 15, 0);
	durationLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Term:", JLabel.RIGHT), durationLabel);

	GridBagConstraints durationTextField = new GridBagConstraints();
	duration = new JSpinner();
	durationTextField.gridx = 1;
	durationTextField.gridy = 8;
	durationTextField.ipady = 2;
	durationTextField.ipadx = 40;
	durationTextField.anchor =  GridBagConstraints.WEST;
	//addpanel.add(sh.getDaysSpinner(duration), durationTextField);
	try{
	addpanel.add(sh.getDaySpinnerEdit(duration, (String)details.get(8)), durationTextField);
	}			
    catch (Exception e) { 
		JOptionPane.showMessageDialog(this, "Quote PDF not generated!" +e.toString(),   
                       "Error!", JOptionPane.INFORMATION_MESSAGE);
		
	}
	//BUTTON BOTTOM
	contact = new JTextField(textFieldColunm);
	contact.setText((String)details.get(9));
	GridBagConstraints saveButton = new GridBagConstraints();
	update = new JButton("Save");
	saveButton.gridx = 1;
	saveButton.gridy = 0;
	buttonpanel.add(update, saveButton);

	
	GridBagConstraints exitButton = new GridBagConstraints();
	exit = new JButton("Exit");
	exitButton.gridx = 3;
	exitButton.gridy = 0;
	//exitButton.insets = new Insets(0, 20, 0, 0);
	buttonpanel.add(exit, exitButton);

	buttonpanel.add(new JLabel(""));
	generalPanel.add("Center", addpanel);
	generalPanel.add("South", buttonpanel);
	generalPanel.setBorder(new Partition(20,"AddProject"));
	con.add(generalPanel);

	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(500, 600);
	setVisible(true);
	setResizable(false);
	
	exit.addActionListener(this);  
	update.addActionListener(this); 
	address.addActionListener(this);
	projectStatus.addActionListener(this);

	projectId.setText(String.valueOf(proId));  

    }
	
	

public class AddAddress extends JFrame implements ActionListener{//implements ActionListener{
   
		private JTextField addressline1;
		private JTextField addressline2;
		private JTextField town_city;
		private JTextField county_state;
		private JTextField postCode;
		private JTextField country;
		private JTextField proTitle;
		private JButton save;
		private JButton exit;
		private JPanel addpanel;
		private JPanel buttonpanel;
		private JPanel generalPanel;
		private String client = "";
		private final double weightye = 0.5;
		private String empty = "\" \"";
		Container con = getContentPane();
    
		public AddAddress(String title){//, int proId
		super(title); 
		
		String addstring[] = addresses.getText().split(",");
		addpanel = new JPanel();
		buttonpanel = new JPanel();
		generalPanel = new JPanel();
		//sh = new SpinnerHelper();
		//Panels
		addpanel.setLayout(new GridBagLayout());
		buttonpanel.setLayout(new GridBagLayout());
		generalPanel.setLayout(new BorderLayout());

		//Textfields 
		GridBagConstraints idLabel = new GridBagConstraints();
		idLabel.gridx = 0;
		idLabel.gridy = 0;
		idLabel.anchor =  GridBagConstraints.EAST;
		idLabel.weighty = weightye;
		addpanel.add(new JLabel("Project Title:"), idLabel);

		GridBagConstraints idTextField = new GridBagConstraints();
		proTitle = new JTextField(textFieldColunm);
		idTextField.gridx = 1;
		idTextField.gridy = 0;
		idTextField.ipady = 2;
		idTextField.weightx = 0.05;
		idTextField.ipadx = 190;
		idTextField.anchor =  GridBagConstraints.WEST;
		addpanel.add(proTitle, idTextField);
		proTitle.setText(projectTitle.getText());
		proTitle.setEditable(false);
		
		GridBagConstraints contactLabel = new GridBagConstraints();
		contactLabel.gridx = 0;
		contactLabel.gridy = 1;
		contactLabel.weighty =  weightye;
		contactLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Contact Name:"), contactLabel);

		GridBagConstraints contactField = new GridBagConstraints();
		//contact = new JTextField(textFieldColunm);
		contactField.gridx = 1;
		contactField.gridy = 1;
		contactField.ipady = 2;
		contactField.ipadx = 190;
		contactField.anchor =  GridBagConstraints.WEST;
		addpanel.add(contact, contactField);
		contact.setText((String)details.get(9));

		GridBagConstraints address1Label = new GridBagConstraints();
		address1Label.gridx = 0;
		address1Label.gridy = 2;
		address1Label.weighty =  weightye;
		address1Label.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Address Line 1:"), address1Label);

		GridBagConstraints address1Field = new GridBagConstraints();
		addressline1 = new JTextField(textFieldColunm);
		address1Field.gridx = 1;
		address1Field.gridy = 2;
		address1Field.ipady = 2;
		address1Field.ipadx = 190;
		address1Field.anchor =  GridBagConstraints.WEST;
		addpanel.add(addressline1, address1Field);
		addressline1.setText(addstring[0]);
	
		GridBagConstraints amountLabel = new GridBagConstraints();
		amountLabel.gridx = 0;
		amountLabel.gridy = 3;
		amountLabel.weighty = weightye;
		amountLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Address Line 2:"), amountLabel);

		GridBagConstraints address2Field = new GridBagConstraints();
		addressline2 = new JTextField(textFieldColunm);
		address2Field.gridx = 1;
		address2Field.gridy = 3;
		address2Field.ipady = 2;
		address2Field.ipadx = 190;
		address2Field.anchor =  GridBagConstraints.WEST;
		addpanel.add(addressline2, address2Field);
		addressline2.setText(addstring[1]);

		GridBagConstraints cityLabel = new GridBagConstraints();
		cityLabel.gridx = 0;
		cityLabel.gridy = 4;
		cityLabel.ipadx = 60;
		cityLabel.weighty = weightye;
		cityLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Town / City:", JLabel.RIGHT), cityLabel);

		GridBagConstraints cityField = new GridBagConstraints();
		town_city = new JTextField(textFieldColunm);
		cityField.gridx = 1;
		cityField.gridy = 4;
		cityField.ipady = 2;
		cityField.ipadx = 190;
		cityField.anchor =  GridBagConstraints.WEST;
		addpanel.add(town_city, cityField);
		town_city.setText(addstring[2]);
	
		GridBagConstraints countyLabel = new GridBagConstraints();
		countyLabel.gridx = 0;
		countyLabel.gridy = 5;
		countyLabel.weighty = weightye;
		countyLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("County / State:"), countyLabel);

		GridBagConstraints countyField = new GridBagConstraints();
		county_state = new JTextField(textFieldColunm);
		countyField.ipady = 2;
		countyField.ipadx = 190;
		countyField.gridx = 1;
		countyField.gridy = 5;
		countyField.anchor =  GridBagConstraints.WEST;
		addpanel.add(county_state, countyField);
		county_state.setText(addstring[3]);
	
		GridBagConstraints postLabel = new GridBagConstraints();
		postLabel.gridx = 0;
		postLabel.gridy = 6;
		postLabel.weighty = weightye;
		postLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Post Code:"), postLabel);

		GridBagConstraints postField = new GridBagConstraints();
		postCode = new JTextField(textFieldColunm);
		postField.gridx = 1;
		postField.gridy = 6;
		postField.ipady = 2;
		postField.ipadx = 190;
		postField.anchor =  GridBagConstraints.WEST;
		addpanel.add(postCode, postField);
		postCode.setText(addstring[4]);
	
		GridBagConstraints countryLabel = new GridBagConstraints();
		countryLabel.gridx = 0;
		countryLabel.gridy = 7;
		countryLabel.weighty = weightye;
		countryLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Country:"), countryLabel);

		GridBagConstraints countryField = new GridBagConstraints();
		country = new JTextField(textFieldColunm);
		countryField.gridx = 1;
		countryField.gridy = 7;
		countryField.ipady = 2;
		countryField.ipadx = 190;
		countryField.anchor =  GridBagConstraints.WEST;
		addpanel.add(country, countryField);
		country.setText(addstring[5]);
		
		//BUTTON BOTTOM
		GridBagConstraints saveButton = new GridBagConstraints();
		save = new JButton("Save");
		saveButton.gridx = 2;
		saveButton.gridy = 0;
		buttonpanel.add(save, saveButton);
	
		GridBagConstraints exitButton = new GridBagConstraints();
		exit = new JButton("Exit");
		exitButton.gridx = 3;
		exitButton.gridy = 0;
		exitButton.insets = new Insets(0, 20, 0, 0);
		buttonpanel.add(exit, exitButton);
	
		generalPanel.add("Center", addpanel);
		generalPanel.add("South", buttonpanel);
		generalPanel.setBorder(new Partition(20,"AddPaymentout"));
		con.add(generalPanel);
		pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 600);
		setResizable(false);
		setVisible(true); 
		exit.addActionListener(this);  
		save.addActionListener(this); 
		}
		public String getLine1(){
			if(addressline1.getText().equals(""))
				return new String( "\" \"");
			return addressline1.getText();
		}
	
		public String getLine2(){
			if(addressline2.getText().equals(""))
				return new String( "\" \"");
			return addressline2.getText();
		}
	
		public String getTown(){
			if(town_city.getText().equals(""))
				return new String( "\" \"");
			return town_city.getText();
		}
	
		public String getCounty(){
			if(county_state.getText().equals(""))
				return new String( "\" \"");
			return county_state.getText();
		}
	
		public String getCountry(){
			if(country.getText().equals(""))
				return new String( "\" \"");
			return country.getText();
		}
	
		public String getPostCode(){
			if(postCode.getText().equals(""))
				return new String( "\" \"");
			return postCode.getText();
		}

		public void actionPerformed( ActionEvent e) {  

			if (e.getSource().equals(exit)){
				contact.setText((String)details.get(9));
				dispose();	
			}
			if (e.getSource().equals(save)){
				addresses.setEditable(true);
				addresses.setText(getLine1()+","+getLine2()+","+getTown()+","+getCounty()+","+getPostCode()+","+getCountry());
				dispose();
			}
		}

	}
	
	public String[] addressinfo(String address){
		return address.split(",");
	}

    public void actionPerformed( ActionEvent e) {  
	if (e.getSource().equals(exit)){
	
	    dispose();	
	}
	
	if (e.getSource().equals(address)){
	      new AddAddress("Addresses");	
	}

	if (e.getSource().equals(update)){
	
		ArrayList<Object> prodetails =  new ArrayList<Object>();
		prodetails.add(String.valueOf(proId));
		prodetails.add(projectTitle.getText());
		prodetails.add(companyName.getText());
		prodetails.add(addressinfo(addresses.getText()));
		prodetails.add(telephone.getText());
		prodetails.add(mobile.getText());
		prodetails.add(email.getText());
		prodetails.add(ul.usDateString(sh.dateString(date)));
		prodetails.add(sh.dayString(duration));
		prodetails.add((String)projectStatus.getSelectedItem());
		prodetails.add(contact.getText());
		bean.connect().updateProject(ul.writeArrayList(prodetails)); 
		dispose();

	}
 

    }

}