
package scr.protool.client.supplier;
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
import scr.protool.client.miscelleneous.BeanStud;



public class SubContractorDetails extends  JFrame implements ActionListener{//ItemListener implements Serializable, ActionListener

    Container con = getContentPane();
	private BeanStud bean;
    private JPanel mainPanel;
	private JPanel detailPanel;
    private JPanel ledgerPanel;
    private JButton refresh;//bottom for mainPanel
    private JButton editPayment;//bottom for ledgerPanel
    private JButton exit;//bottom for mainPanel 
	private JTextField contact;
	private JTextField contractor;
	private JTextField business;
	private JTextField utr;
	private JTextField addressOne;
	private JTextField addressTwo;
	private JTextField town;
	private JTextField county;
	private JTextField mobile;
	private JTextField phone;
	private JTextField email;
	private JTextField postcode;
	private JTextField country;
	private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int ipadiyb = 2;
    private final int ipadixb = 4;
    private final double weightix = 2.0;
	private final double weightiy = 1.0;
	private final int initialRow = 22;
	private final int textFieldColunm = 1;
	 private JTable ledgerView; 
    private DefaultTableModel ledgerDataModel; 
    private JScrollPane ledgerViewScroll; 
	private JScrollPane buttonPanel;
	private PaymentInUtilities pu;
	private Utilities ul;
	private String empty = null;	

    public SubContractorDetails(String title, int subContractorId) {
	super(title);
	
	bean = new BeanStud();
	ul = new Utilities();
	pu = new PaymentInUtilities();
	empty = new String( "\" \"");
	ArrayList<Object> details = pu.getArrayList(bean.connect().getSubContractorSingleDetails(subContractorId));
	mainPanel = new JPanel();
	detailPanel = new JPanel();
	ledgerPanel = new JPanel();
	detailPanel.setLayout(new GridBagLayout());
	ledgerPanel.setLayout(new GridBagLayout());

	GridBagConstraints typeLabel = new GridBagConstraints();
	typeLabel.gridx = 0;
	typeLabel.gridy = 0;
	typeLabel.anchor =  GridBagConstraints.EAST;
	typeLabel.weightx = weightix;
	typeLabel.insets = new Insets(15, 15, 20, 0);
    detailPanel.add(new JLabel("Name:"), typeLabel);

	GridBagConstraints typeTextField = new GridBagConstraints();
	contact = new JTextField(textFieldColunm);
	typeTextField.gridx = 1;
	typeTextField.gridy = 0;
	typeTextField.ipady = ipadiy;
	typeTextField.ipadx = ipadix;
	typeTextField.insets = new Insets(15, 0, 20, 0);
	typeTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(contact, typeTextField);
	contact.setEditable(false);
	
	if(empty.equals(String.valueOf(details.get(1)))){
		 contact.setText("");
		}
	else{
		contact.setText(String.valueOf(details.get(1)));
	}
	GridBagConstraints noteLabel = new GridBagConstraints();
	noteLabel.gridx = 2;
	noteLabel.gridy = 0;
	noteLabel.anchor =  GridBagConstraints.EAST;
	noteLabel.weightx = weightix;
	noteLabel.insets = new Insets(15, 10, 20, 0);
    detailPanel.add(new JLabel("Sub-contractor ID:"), noteLabel);

	GridBagConstraints noteTextField = new GridBagConstraints();
	contractor = new JTextField(textFieldColunm);
	noteTextField.gridx = 3;
	noteTextField.gridy = 0;
	noteTextField.ipady = ipadiy;
	noteTextField.ipadx = ipadix;
	noteTextField.weightx = weightix;
	noteTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(contractor, noteTextField);
	contractor.setEditable(false);
	contractor.setText(String.valueOf(subContractorId));
	
	GridBagConstraints urtLabel = new GridBagConstraints();
	urtLabel.gridx = 0;
	urtLabel.gridy = 1;
	urtLabel.anchor =  GridBagConstraints.EAST;
	urtLabel.weightx = weightix;
	urtLabel.insets = new Insets(15, 15, 20, 0);
    detailPanel.add(new JLabel("UTR:"), urtLabel);

	GridBagConstraints urtTextField = new GridBagConstraints();
	utr = new JTextField(textFieldColunm);
	urtTextField.gridx = 1;
	urtTextField.gridy = 1;
	urtTextField.ipady = ipadiy;
	urtTextField.ipadx = ipadix;
	urtTextField.insets = new Insets(15, 0, 20, 0);
	urtTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(utr, urtTextField);
	utr.setEditable(false);
	
	if(empty.equals(String.valueOf(details.get(2)))){
		 utr.setText("");
		}
	else{
		utr.setText(String.valueOf(details.get(2)));
	}
	
	GridBagConstraints bussLabel = new GridBagConstraints();
	bussLabel.gridx = 2;
	bussLabel.gridy = 1;
	bussLabel.anchor =  GridBagConstraints.EAST;
	bussLabel.weightx = weightix;
	bussLabel.insets = new Insets(15, 10, 20, 0);
    detailPanel.add(new JLabel("Business Type:"), bussLabel);

	GridBagConstraints  bussTextField = new GridBagConstraints();
	business = new JTextField(textFieldColunm);
	bussTextField.gridx = 3;
	bussTextField.gridy = 1;
	bussTextField.ipady = ipadiy;
	bussTextField.ipadx = ipadix;
	bussTextField.weightx = weightix;
	bussTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(business,  bussTextField);
	business.setEditable(false);
	
	if(empty.equals(String.valueOf(details.get(4)))){
		business.setText("");
		}
	else{
		business.setText(String.valueOf(details.get(4)));
	}

	GridBagConstraints oneLabel = new GridBagConstraints();
	oneLabel.gridx = 0;
	oneLabel.gridy = 2;
	oneLabel.anchor =  GridBagConstraints.EAST;
	oneLabel.weightx = weightix;
	oneLabel.insets = new Insets(15, 0, 20, 0);
    detailPanel.add(new JLabel("Address Line 1:"), oneLabel);
	
	GridBagConstraints oneTextField = new GridBagConstraints();
	addressOne = new JTextField(textFieldColunm);
	oneTextField.gridx = 1;
	oneTextField.gridy = 2;
	oneTextField.ipady = ipadiy;
	oneTextField.ipadx = ipadix+ipadix;
	oneTextField.gridwidth = 3;
	oneTextField.weightx = weightix;
	oneTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(addressOne, oneTextField);
	addressOne.setEditable(false);
	ArrayList<String> addstring = (ArrayList<String>)details.get(6);
	if(empty.equals(addstring.get(0))){
			addressOne.setText("");
		}
	else{
		addressOne.setText(addstring.get(0));
	}
	
	GridBagConstraints twoLabel = new GridBagConstraints();
	twoLabel.gridx = 0;
	twoLabel.gridy = 3;
	twoLabel.anchor =  GridBagConstraints.EAST;
	twoLabel.weightx = weightix;
	twoLabel.insets = new Insets(15, 0, 20, 0);
    detailPanel.add(new JLabel("Address Line 2:"), twoLabel);
	
	GridBagConstraints twoTextField = new GridBagConstraints();
	addressTwo = new JTextField(textFieldColunm);
	twoTextField.gridx = 1;
	twoTextField.gridy = 3;
	twoTextField.ipady = ipadiy;
	twoTextField.ipadx = ipadix+ipadix;
	twoTextField.gridwidth = 3;
	twoTextField.weightx = weightix;
	twoTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(addressTwo, twoTextField);
	addressTwo.setEditable(false);

	if(empty.equals(addstring.get(1))){
			addressTwo.setText("");
		}
	else{
		addressTwo.setText(addstring.get(1));
	}
	
	GridBagConstraints townLabel = new GridBagConstraints();
	townLabel.gridx = 0;
	townLabel.gridy = 4;
	townLabel.anchor =  GridBagConstraints.EAST;
	townLabel.weightx = weightix;
	townLabel.insets = new Insets(15, 0, 20, 0);
    detailPanel.add(new JLabel("Town:"), townLabel);

	GridBagConstraints townTextField = new GridBagConstraints();
	town = new JTextField(textFieldColunm);
	townTextField.gridx = 1;
	townTextField.gridy = 4;
	townTextField.ipady = ipadiy;
	townTextField.ipadx = ipadix;
	townTextField.insets = new Insets(15, 0, 20, 0);
	townTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(town, townTextField);
	town.setEditable(false);

	if(empty.equals(addstring.get(2))){
			town.setText("");
		}
	else{
		town.setText(addstring.get(2));
	}
	
	GridBagConstraints countyLabel = new GridBagConstraints();
	countyLabel.gridx = 2;
	countyLabel.gridy = 4;
	countyLabel.anchor =  GridBagConstraints.EAST;
	countyLabel.weightx = weightix;
	countyLabel.insets = new Insets(15, 0, 20, 0);
    detailPanel.add(new JLabel("County:"), countyLabel);

	GridBagConstraints countyTextField = new GridBagConstraints();
	county = new JTextField(textFieldColunm);
	countyTextField.gridx = 3;
	countyTextField.gridy = 4;
	countyTextField.ipady = ipadiy;
	countyTextField.ipadx = ipadix;
	countyTextField.weightx = weightix;
	countyTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(county, countyTextField);
	county.setEditable(false);

	if(empty.equals(addstring.get(3))){
			county.setText("");
		}
	else{
		county.setText(addstring.get(3));	
	}		
	
	GridBagConstraints codeLabel = new GridBagConstraints();
	codeLabel.gridx = 0;
	codeLabel.gridy = 5;
	codeLabel.anchor =  GridBagConstraints.EAST;
	codeLabel.weightx = weightix;
	codeLabel.insets = new Insets(15, 0, 20, 0);
    detailPanel.add(new JLabel("Post Code:"), codeLabel);
	
	GridBagConstraints codeTextField = new GridBagConstraints();
	postcode = new JTextField(textFieldColunm);
	codeTextField.gridx = 1;
	codeTextField.gridy = 5;
	codeTextField.ipady = ipadiy;
	codeTextField.ipadx = ipadix;
	codeTextField.weightx = weightix;
	codeTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(postcode, codeTextField);
	postcode.setEditable(false);
	
	if(empty.equals(addstring.get(4))){
		 postcode.setText("");
		}
	else{
		postcode.setText(addstring.get(4));
	}	
	
	GridBagConstraints countryLabel = new GridBagConstraints();
	countryLabel.gridx = 0;
	countryLabel.gridy = 6;
	countryLabel.anchor =  GridBagConstraints.EAST;
	countryLabel.weightx = weightix;
	countryLabel.insets = new Insets(15, 0, 20, 0);
    detailPanel.add(new JLabel("Country:"), countryLabel);
	
	GridBagConstraints countryTextField = new GridBagConstraints();
	country = new JTextField(textFieldColunm);
	countryTextField.gridx = 1;
	countryTextField.gridy = 6;
	countryTextField.ipady = ipadiy;
	countryTextField.ipadx = ipadix;
	countryTextField.weightx = weightix;
	countryTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(country, countryTextField);
	country.setEditable(false);
	
	if(empty.equals(addstring.get(5))){
		 country.setText("");
		}
	else{
		country.setText(addstring.get(5));
	}		
	
	GridBagConstraints phoneLabel = new GridBagConstraints();
	phoneLabel.gridx = 0;
	phoneLabel.gridy = 7;
	phoneLabel.anchor =  GridBagConstraints.EAST;
	phoneLabel.weightx = weightix;
	phoneLabel.insets = new Insets(15, 0, 20, 0);
    detailPanel.add(new JLabel("Telephone:"), phoneLabel);

	GridBagConstraints phoneTextField = new GridBagConstraints();
	phone = new JTextField(textFieldColunm);
	phoneTextField.gridx = 1;
	phoneTextField.gridy = 7;
	phoneTextField.ipady = ipadiy;
	phoneTextField.ipadx = ipadix;
	phoneTextField.insets = new Insets(15, 0, 20, 0);
	phoneTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(phone, phoneTextField);
	phone.setEditable(false);

	if(empty.equals(String.valueOf(details.get(7)))){
		 phone.setText("");
		}
	else{
		phone.setText(String.valueOf(details.get(7)));
	}	
	
	GridBagConstraints mobileLabel = new GridBagConstraints();
	mobileLabel.gridx = 2;
	mobileLabel.gridy = 7;
	mobileLabel.anchor =  GridBagConstraints.EAST;
	mobileLabel.weightx = weightix;
	mobileLabel.insets = new Insets(15, 0, 20, 0);
    detailPanel.add(new JLabel("Mobile:"), mobileLabel);

	GridBagConstraints mobileTextField = new GridBagConstraints();
	mobile = new JTextField(textFieldColunm);
	mobileTextField.gridx = 3;
	mobileTextField.gridy = 7;
	mobileTextField.ipady = ipadiy;
	mobileTextField.ipadx = ipadix;
	mobileTextField.weightx = weightix;
	mobileTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(mobile, mobileTextField);
	mobile.setEditable(false);

	if(empty.equals(String.valueOf(details.get(8)))){
		 mobile.setText("");
		}
	else{
		mobile.setText(String.valueOf(details.get(8)));
	}	
	
	GridBagConstraints emailLabel = new GridBagConstraints();
	emailLabel.gridx = 0;
	emailLabel.gridy = 8;
	emailLabel.anchor =  GridBagConstraints.EAST;
	emailLabel.weightx = weightix;
	emailLabel.insets = new Insets(15, 0, 20, 0);
    detailPanel.add(new JLabel("E-mail:"), emailLabel);	
	
	GridBagConstraints emailTextField = new GridBagConstraints();
	email = new JTextField(textFieldColunm);
	emailTextField.gridx = 1;
	emailTextField.gridy = 8;
	emailTextField.ipady = ipadiy;
	emailTextField.ipadx = ipadix+ipadix;
	emailTextField.gridwidth = 3;
	emailTextField.weightx = weightix;
	emailTextField.insets = new Insets(15, 0, 20, 0);
	emailTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(email, emailTextField);
	email.setEditable(false);

	if(empty.equals(String.valueOf(details.get(9)))){
		 email.setText("");
		}
	else{
		email.setText(String.valueOf(details.get(9)));
	}

	GridBagConstraints editPay = new GridBagConstraints();
	editPayment = new JButton("EDIT");
	editPay.gridx = 1;
	editPay.gridy = 0;
	ledgerPanel.add(editPayment, editPay);
	GridBagConstraints refreshPay = new GridBagConstraints();
	refresh = new JButton("REFRESH");
	refreshPay.gridx = 2;
	refreshPay.gridy = 0;
	ledgerPanel.add(refresh, refreshPay);
	GridBagConstraints exitPay = new GridBagConstraints();
	exit = new JButton("EXIT");
	exitPay.gridx = 3;
	exitPay.gridy = 0;
	ledgerPanel.add(exit, exitPay);

	mainPanel.add("North", detailPanel);
	mainPanel.add("South", ledgerPanel);
	mainPanel.setBorder(new Partition(20,"Ledger"));
	con.add(mainPanel);
	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(650, 600);
	setVisible(true);   
	setResizable(false);
	editPayment.addActionListener(this);
	refresh.addActionListener(this);   
	exit.addActionListener(this);       
    }

    public void actionPerformed( ActionEvent e) {  


	if (e.getSource().equals(editPayment)){
			new EditSubContractor("Update Sub-Contractor", (Integer.valueOf(contractor.getText())).intValue());
	  }
	 if (e.getSource().equals(refresh)){
		dispose();
	    new SubContractorDetails("Sub-contractor Details", (Integer.valueOf(contractor.getText())).intValue());
	  }
	 if (e.getSource().equals(exit)){
		dispose();
	  }
    }
    
}