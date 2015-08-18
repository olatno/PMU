
package scr.protool.client.supplier;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.text.*;
import javax.naming.*;
import java.util.*;
import java.io.*;
import scr.protool.client.utilities.*;
import scr.protool.client.miscelleneous.BeanStud;

/**

*
*

*
* @author olatunji nofiu <a href="o.nofiu@flexiblebooks.com"></a>
* @version 1.0
*/
public class EditSupplier extends JFrame implements ActionListener{
  
    private JTextField companyName;
    private JTextField contactName;
	private JTextField account;
    private JTextField quote;
    private JTextField cost;
	private JSpinner term;
    private SpinnerHelper sh;
	private SpinnerHelper daysh;
	private JSpinner date;
    private JTextField addresses;
    private JTextField telephone;
	private JTextField fax;
    private JTextField email; 
    private JButton ok;
    private JButton exit;
	private JButton quotebutton;
    private JPanel addpanel;
    private JPanel buttonpanel;
    private JPanel generalPanel;
    private String client = "";
    private BeanStud bean;
	private String address;
	private final int textFieldColunm = 1;
	private Utilities ul = new Utilities();
	private Locale locale = Locale.UK;
    Container con = getContentPane();
	private int supplierId;
	private PaymentInUtilities pu;
    
    public EditSupplier(String title, int supplierId){
   	super(title); 
	this.supplierId=supplierId;
	address = new String();
	bean = new BeanStud();
	pu = new PaymentInUtilities();
	ArrayList<Object> details = pu.getArrayList(bean.connect().getSupplierSingleDetails(supplierId));
	addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();

	sh = new SpinnerHelper();
	daysh = new SpinnerHelper();
	//Panels
	addpanel.setLayout(new GridBagLayout());
	buttonpanel.setLayout(new GridLayout(1,6));
	generalPanel.setLayout(new BorderLayout());

	//Textfields use for search
	GridBagConstraints idLabel = new GridBagConstraints();
	idLabel.gridx = 0;
	idLabel.gridy = 0;
	idLabel.anchor =  GridBagConstraints.EAST;
    idLabel.weighty = 0.1;
	addpanel.add(new JLabel("Company Name:"), idLabel);

	GridBagConstraints idTextField = new GridBagConstraints();
	companyName = new JTextField(textFieldColunm);
	idTextField.gridx = 1;
	idTextField.gridy = 0;
	idTextField.ipady = 2;
	idTextField.gridwidth = 2;
	idTextField.ipadx = 200;
	idTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(companyName, idTextField);
	companyName.setText(String.valueOf(details.get(0)));

	GridBagConstraints titleLabel = new GridBagConstraints();
	titleLabel.gridx = 0;
	titleLabel.gridy = 1;
    titleLabel.weighty = 0.1;
	titleLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Contact Name:"), titleLabel);

	GridBagConstraints titleTextField = new GridBagConstraints();
	contactName = new JTextField(textFieldColunm);
	titleTextField.gridx = 1;
	titleTextField.gridy = 1;
	titleTextField.ipady = 2;
	titleTextField.gridwidth = 2;
	titleTextField.ipadx = 200;
	titleTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(contactName, titleTextField);
	contactName.setText(String.valueOf(details.get(1)));	
	
	GridBagConstraints accLabel = new GridBagConstraints();
	accLabel.gridx = 0;
    accLabel.gridy = 2;
    accLabel.weighty = 0.1;
	accLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Account Number:"), accLabel);

	GridBagConstraints accTextField = new GridBagConstraints();
	account = new JTextField(textFieldColunm);
	accTextField.gridx = 1;
	accTextField.gridy = 2;
	accTextField.ipady = 2;
	accTextField.gridwidth = 2;
	accTextField.ipadx = 200;
	accTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(account, accTextField);
	account.setText(String.valueOf(details.get(2)));	

	GridBagConstraints quote1Label = new GridBagConstraints();
	quotebutton = new JButton("ADDRESSES..");
	quote1Label.gridx = 0;
	quote1Label.gridy = 3;
	quote1Label.insets = new Insets(0, 50, 0, 0);
	quote1Label.weighty = 0.1;
	quote1Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(quotebutton, quote1Label);

	GridBagConstraints addrTextField = new GridBagConstraints();
	addresses = new JTextField(textFieldColunm);
	addrTextField.ipady = 2;
	addrTextField.ipadx = 250;
	addrTextField.gridx = 1;
	addrTextField.gridy = 3;
	addrTextField.gridwidth = 2;
	addrTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(addresses, addrTextField);
	//addresses.setEditable(false);
	ArrayList<String> addstr = (ArrayList<String>)details.get(3);

	for(int i = 0; i < addstr.size(); i++){
		String empty = new String( "\" \"");
		if(addstr.get(i)==null){
			address = address+","+empty;
		}
		else{
			address = address+","+addstr.get(i);
		}
	}
	addresses.setText(address.trim().substring(1, address.length()));
	
	GridBagConstraints teleLabel = new GridBagConstraints();
	teleLabel.gridx = 0;
	teleLabel.gridy = 4;
	teleLabel.weighty = 0.1;
	teleLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Phone / FAX:"), teleLabel);

	GridBagConstraints teleTextField = new GridBagConstraints();
	telephone = new JTextField(textFieldColunm);
	teleTextField.gridx = 1;
	teleTextField.gridy = 4;
	teleTextField.ipady = 2;
	teleTextField.ipadx = 124;
	teleTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(telephone, teleTextField);
	telephone.setText(String.valueOf(details.get(4)));	
	
	GridBagConstraints faxTextField = new GridBagConstraints();
	fax = new JTextField(textFieldColunm);
	faxTextField.gridx = 2;
	faxTextField.gridy = 4;
	faxTextField.ipady = 2;
	faxTextField.weightx=1.0;
	faxTextField.ipadx = 150;
	faxTextField.insets = new Insets(0, 10, 0, 0);
	faxTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(fax, faxTextField);
	fax.setText(String.valueOf(details.get(5)));	


	GridBagConstraints emailLabel = new GridBagConstraints();
	emailLabel.gridx = 0;
	emailLabel.gridy = 5;
	emailLabel.weighty = 0.1;
	emailLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Email:"), emailLabel);

	GridBagConstraints emailTextField = new GridBagConstraints();
	email = new JTextField(textFieldColunm);
	emailTextField.gridx = 1;
	emailTextField.gridy = 5;
	emailTextField.ipady = 2;
	emailTextField.gridwidth = 2;
	emailTextField.ipadx = 200;
	emailTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(email, emailTextField);
	email.setText(String.valueOf(details.get(6)));
	
	GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 6;
	date1Label.weighty = 0.1;
    date1Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Date:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	date = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 6;
	date1TextField.ipady = 2;
	date1TextField.ipadx = 10;
	date1TextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(sh.getGenericDateEdit(date, ul.ukFormat((java.sql.Date)details.get(7))), date1TextField);	

	GridBagConstraints costLabel = new GridBagConstraints();
	costLabel.gridx = 0;
	costLabel.gridy = 7;
	costLabel.weighty = 0.1;
	costLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Credit Limit:"), costLabel);

	GridBagConstraints costTextField = new GridBagConstraints();
	cost = new JTextField(textFieldColunm);
	costTextField.gridx = 1;
	costTextField.gridy = 7;
	costTextField.ipady = 2;
	costTextField.ipadx = 110;
	costTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(cost, costTextField);
	cost.setText(String.valueOf(details.get(8)));	
	
	GridBagConstraints termLabel = new GridBagConstraints();
	termLabel.gridx = 0;
	termLabel.gridy = 8;
	termLabel.weighty = 0.1;
	termLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Term:"), termLabel);

	GridBagConstraints termTextField = new GridBagConstraints();
	term = new JSpinner();
	termTextField.gridx = 1;
	termTextField.gridy = 8;
	termTextField.ipady = 2;
	termTextField.ipadx = 30;
	termTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(sh.getDaySpinnerEdit(term, (String)details.get(9)), termTextField);


	//BUTTON BOTTOM
	buttonpanel.add(new JLabel(""));
	buttonpanel.add(new JLabel(""));
	ok = new JButton("OK");
	buttonpanel.add(ok);
	exit = new JButton("EXIT");
	buttonpanel.add(exit);
	buttonpanel.add(new JLabel(""));
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
	ok.addActionListener(this); 
	quotebutton.addActionListener(this);  

    }

	public class AddAddress extends JFrame implements ActionListener{//implements ActionListener{
   
		private JTextField addressline1;
		private JTextField addressline2;
		private JTextField town_city;
		private JTextField county_state;
		private JTextField postCode;
		private JTextField country;
		private JTextField supplier;
		private JButton save;
		private JButton exit;
		private JPanel addpanel;
		private JPanel buttonpanel;
		private JPanel generalPanel;
		private SpinnerHelper sh;
		private String client = "";
		private final double weightye = 0.5;
		Container con = getContentPane();
		private String empty = new String( "\" \"");
    
		public AddAddress(String title){//, int proId
		super(title);
		String addstring[] = addresses.getText().split(",");
		addpanel = new JPanel();
		buttonpanel = new JPanel();
		generalPanel = new JPanel();
		sh = new SpinnerHelper();
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
		addpanel.add(new JLabel("Supplier Name:"), idLabel);

		GridBagConstraints idTextField = new GridBagConstraints();
		supplier = new JTextField(textFieldColunm);
		idTextField.gridx = 1;
		idTextField.gridy = 0;
		idTextField.ipady = 2;
		idTextField.weightx = 0.05;
		idTextField.ipadx = 190;
		idTextField.anchor =  GridBagConstraints.WEST;
		addpanel.add(supplier, idTextField);
		supplier.setText(companyName.getText());
		supplier.setEditable(false);

		GridBagConstraints paymentinLabel = new GridBagConstraints();
		paymentinLabel.gridx = 0;
		paymentinLabel.gridy = 1;
		paymentinLabel.weighty =  weightye;
		paymentinLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Address Line 1:"), paymentinLabel);

		GridBagConstraints paymentidField = new GridBagConstraints();
		addressline1 = new JTextField(textFieldColunm);
		paymentidField.gridx = 1;
		paymentidField.gridy = 1;
		paymentidField.ipady = 2;
		paymentidField.ipadx = 190;
		paymentidField.anchor =  GridBagConstraints.WEST;
		addpanel.add(addressline1, paymentidField);

		if(empty.equals(addstring[0])){
			addressline1.setText("");
		}
		else{
		addressline1.setText(addstring[0]);
		}
	
		GridBagConstraints amountLabel = new GridBagConstraints();
		amountLabel.gridx = 0;
		amountLabel.gridy = 2;
		amountLabel.weighty = weightye;
		amountLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Address Line 2:"), amountLabel);

		GridBagConstraints amountTextField = new GridBagConstraints();
		addressline2 = new JTextField(textFieldColunm);
		amountTextField.gridx = 1;
		amountTextField.gridy = 2;
		amountTextField.ipady = 2;
		amountTextField.ipadx = 190;
		amountTextField.anchor =  GridBagConstraints.WEST;
		addpanel.add(addressline2, amountTextField);

		if(empty.equals(addstring[1])){
			addressline2.setText("");
		}
		else{
		addressline2.setText(addstring[1]);
		}
	
		GridBagConstraints cisLabel = new GridBagConstraints();
		cisLabel.gridx = 0;
		cisLabel.gridy = 3;
		cisLabel.ipadx = 60;
		cisLabel.weighty = weightye;
		cisLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Town / City:", JLabel.RIGHT), cisLabel);

		GridBagConstraints cisTextField = new GridBagConstraints();
		town_city = new JTextField(textFieldColunm);
		cisTextField.gridx = 1;
		cisTextField.gridy = 3;
		cisTextField.ipady = 2;
		cisTextField.ipadx = 190;
		cisTextField.anchor =  GridBagConstraints.WEST;
		addpanel.add(town_city, cisTextField);

		if(empty.equals(addstring[2])){
			town_city.setText("");
		}
		else{
			town_city.setText(addstring[2]);
		}
	
		GridBagConstraints noteLabel = new GridBagConstraints();
		noteLabel.gridx = 0;
		noteLabel.gridy = 4;
		noteLabel.weighty = weightye;
		noteLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("County / State:"), noteLabel);

		GridBagConstraints noteTextField = new GridBagConstraints();
		county_state = new JTextField(textFieldColunm);
		noteTextField.ipady = 2;
		noteTextField.ipadx = 190;
		noteTextField.gridx = 1;
		noteTextField.gridy = 4;
		noteTextField.anchor =  GridBagConstraints.WEST;
		addpanel.add(county_state, noteTextField);

		if(empty.equals(addstring[3])){
			county_state.setText("");
		}
		else{
			county_state.setText(addstring[3]);
		}
	
		GridBagConstraints typeLabel = new GridBagConstraints();
		typeLabel.gridx = 0;
		typeLabel.gridy = 5;
		typeLabel.weighty = weightye;
		typeLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Post Code:"), typeLabel);

		GridBagConstraints typeField = new GridBagConstraints();
		postCode = new JTextField(textFieldColunm);
		typeField.gridx = 1;
		typeField.gridy = 5;
		typeField.ipady = 2;
		typeField.ipadx = 190;
		typeField.anchor =  GridBagConstraints.WEST;
		addpanel.add(postCode, typeField);

		if(empty.equals(addstring[4])){
			postCode.setText("");
		}
		else{
			postCode.setText(addstring[4]);
		}
	
		GridBagConstraints countryLabel = new GridBagConstraints();
		countryLabel.gridx = 0;
		countryLabel.gridy = 6;
		countryLabel.weighty = weightye;
		countryLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Country:"), countryLabel);

		GridBagConstraints countryField = new GridBagConstraints();
		country = new JTextField(textFieldColunm);
		countryField.gridx = 1;
		countryField.gridy = 6;
		countryField.ipady = 2;
		countryField.ipadx = 190;
		countryField.anchor =  GridBagConstraints.WEST;
		addpanel.add(country, countryField);

		if(empty.equals(addstring[5])){
			country.setText("");
		}
		else{
			country.setText(addstring[5]);
		}
	
	//BUTTON BOTTOM
		GridBagConstraints saveButton = new GridBagConstraints();
		saveButton.gridx = 0;
		saveButton.gridy = 0;
		save = new JButton("Update");
		buttonpanel.add(save, saveButton);
		
		GridBagConstraints exitButton = new GridBagConstraints();
		exitButton.gridx = 1;
		exitButton.gridy = 0;
		exit = new JButton("Exit");
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
				dispose();	
			}
			if (e.getSource().equals(save)){
				addresses.setEditable(true);
				addresses.setText(getLine1()+","+getLine2()+","+getTown()+","+getCounty()+","+getPostCode()+","+getCountry());
				dispose();
			}
		}

	}
	
	public String[] addressinfo(String addresses){
		return addresses.split(",");
	}


    public void actionPerformed( ActionEvent e) {  
	if (e.getSource().equals(exit)){
	
	    dispose();	
	}
	
	if (e.getSource().equals(quotebutton)){
	      new AddAddress("Addresses");	
	}
 
	if (e.getSource().equals(ok)){
		java.util.List<Object> supplierdetails =  new ArrayList<Object>();
		supplierdetails.add(companyName.getText());
		supplierdetails.add(contactName.getText());
		supplierdetails.add(telephone.getText());
		supplierdetails.add(email.getText());
		supplierdetails.add(ul.usDate(sh.dateString(date)));
		supplierdetails.add(cost.getText());
		supplierdetails.add(sh.dayString(term));
		supplierdetails.add(account.getText());
		supplierdetails.add(fax.getText());
		supplierdetails.add(addressinfo(addresses.getText()));
	    bean.connect().updateSupplier(ul.writeArrayList(supplierdetails), ul.writeInteger(Integer.valueOf(supplierId)));    
	    dispose();
		}
    }

}