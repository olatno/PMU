package scr.protool.client.project;
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
import javax.swing.border.TitledBorder;
import scr.protool.client.utilities.*;
import scr.protool.client.miscelleneous.QuotePdf;
import scr.protool.client.miscelleneous.BeanStud;


public class ProjectDetails extends  JFrame implements Serializable, ActionListener, ItemListener{//ItemListener implements Serializable, ActionListener

    Container con = getContentPane();
    private JPanel mainPanel;
	//private JPanel detail1Panel;
	//private JPanel detail2Panel;
    private JPanel 	detailPanel;
	private JPanel buttonPanel;
	private JPanel upperGrid1;
	private JPanel upperGrid2;
	private JPanel lowerGrid1;
	private JPanel lowerGrid2;
	private JPanel upperGridBag;
	private JPanel lowerGridBag;
	private JPanel buttomGridLayout;
    private JButton refresh;//bottom for mainPanel
    private JButton add_on;//bottom for ledgerPanel
    private JButton edit;//bottom for ledgerPanel
    private JButton exit;//bottom for mainPanel
	private JButton generate;
	private JComboBox projectDetail;
	private JTextField projectId;
	private JTextField contact;
	private JTextField firstAddresses;
	private JTextField secoundAddresses;
	private JTextField townAddresses;
	private JTextField countyAddresses;
	private JTextField postAddresses;
	private JTextField clientTele;
	private JTextField clientMobile;
	private JTextField clientEmail;
	private JTextField quoteId;
	private JTextField date;
	private JTextField netAmount;
	private JTextField vatAmount;
	private JTextField country;
	private JTextField customerId;
	private JTextField customerName;
	private JTextField totalAmount;
	private JTextField totalItem;
	private JRadioButton siteButton;
	private JRadioButton billButton;
	private JPanel radioPanel;
	/*private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int ipadiyb = 2;
    private final int ipadixb = 4;*/
	
	private final int ipadiy = 1;
    private final int ipadixl = 200;
	private final int ipadixs = 60;
    private final int ipadixq = 120;
	private final int pleft = 0;
	private final int pright = 0;
	
    private final double weightix = 1.0;
	private final double weightiy = 1.0;
	private final int initialRow = 13;
	private final int textFieldColunm = 1;
	private final int padding = 20;
	private final int paddingLeft = 20;
	private final int paddingRight = 20;
	private final int paddingLeftLower = 26;
	private final int paddingRightLower = 5;
	private final int v_gap1 = 2;
	private final int h_gap1 = -110;
	private final int v_gap2 = -2;
	private final int h_gap2 = -40;
	private final int v_gap4 = 1;
	private final int h_gap4 = -35;
	private final int v_gap3 = 0;
	private final int h_gap3 = -50;
	private BeanStud bean;
	private Utilities ul = new Utilities();
	private ArrayList<Object> details = null;
	private ArrayList<String> projectDetails;
	private ArrayList<String> siteDetails;

    public ProjectDetails(int projectid, String title) {
	super(title);

	bean = new BeanStud();
	mainPanel = new JPanel();
	//detail1Panel = new JPanel();
	upperGrid1 = new JPanel();
	upperGrid2 = new JPanel();
	lowerGrid1 = new JPanel();
	lowerGrid2 = new JPanel();
	//detail2Panel = new JPanel();
	detailPanel = new JPanel();
	buttonPanel = new JPanel();
	radioPanel = new JPanel();
	buttomGridLayout = new JPanel();
	upperGridBag = new JPanel();
	lowerGridBag = new JPanel();
    //TitledBorder pane1 = BorderFactory.createTitledBorder("Project Details");
	TitledBorder pane1 = BorderFactory.createTitledBorder(null,
                              "Project Details",
                              TitledBorder.DEFAULT_JUSTIFICATION,
                              TitledBorder.DEFAULT_POSITION,
                              new Font("", Font.BOLD, 12));
	TitledBorder pane2 = BorderFactory.createTitledBorder(null,
                              "Quote Details",
                              TitledBorder.DEFAULT_JUSTIFICATION,
                              TitledBorder.DEFAULT_POSITION,
                              new Font("", Font.BOLD, 12));
	//TitledBorder pane2 = BorderFactory.createTitledBorder("Quote Details");
	//detail1Panel.setLayout(new BorderLayout());
	
	upperGridBag.setBorder(pane1);
	upperGridBag.setPreferredSize(new Dimension(515, 310));
	lowerGridBag.setBorder(pane2);
	lowerGridBag.setPreferredSize(new Dimension(515, 140));
	
	//detail2Panel.setBorder(pane2);
	radioPanel.setLayout(new GridLayout(1, 2));
	
	//project details container
	GridLayout gLayout1 = new GridLayout(12,2);
	upperGrid1.setLayout(gLayout1);
	upperGrid1.setPreferredSize(new Dimension(415, 310));
	gLayout1.setVgap(v_gap1);
	gLayout1.setHgap(h_gap1);
	
	GridLayout gLayout2 = new GridLayout(12,2);
	upperGrid2.setLayout(gLayout2);
	upperGrid2.setPreferredSize(new Dimension(115, 305));
	gLayout2.setVgap(v_gap3);
	gLayout2.setHgap(h_gap3);
	
	//quote details container
	
	GridLayout lLayout1 = new GridLayout(5,2);
	lowerGrid1.setLayout(lLayout1);
	lowerGrid1.setPreferredSize(new Dimension(265, 140));
	lLayout1.setVgap(v_gap4);
	lLayout1.setHgap(h_gap4);
	
	GridLayout lLayout2 = new GridLayout(5,2);
	lowerGrid2.setLayout(lLayout2);
	lowerGrid2.setPreferredSize(new Dimension(250, 110));
	lLayout2.setVgap(v_gap2);
	lLayout2.setHgap(h_gap2);
	
	upperGridBag.setLayout(new GridBagLayout());
	lowerGridBag.setLayout(new GridBagLayout());
	
	buttonPanel.setLayout(new GridBagLayout());
	detailPanel.setLayout(new FlowLayout());
	mainPanel.setLayout(new BorderLayout());
	buttomGridLayout.setLayout(new GridBagLayout());
	details = ul.getArrayList(bean.connect().projectDetails(projectid));

	//project details
	projectDetails = (ArrayList<String>)details.get(0);
	siteDetails = (ArrayList<String>)details.get(2);

	/* row one*/
    upperGrid1.add(new JLabel("Project Id:"));
	
	GridBagConstraints idTextField = new GridBagConstraints();
	JPanel idPanel = new JPanel();
	projectId = new JTextField(textFieldColunm);
	idTextField.gridx = 0;
	idTextField.gridy = 0;
	idTextField.ipady = ipadiy;
	idTextField.ipadx = ipadixs;
	idTextField.insets = new Insets(0, 0, 0, 140);
	idTextField.anchor =  GridBagConstraints.PAGE_START;
	idPanel.setLayout(new GridBagLayout());
	idPanel.add(projectId, idTextField);
	upperGrid1.add(idPanel);
	projectId.setText(projectDetails.get(0));
	projectId.setEditable(false);
	
	/*row 2*/
    upperGrid1.add(new JLabel("Poject Title:"));

	GridBagConstraints titleField = new GridBagConstraints();
	JPanel companyPanel = new JPanel();
	JTextField titles = new JTextField(textFieldColunm);
	titleField.gridx = 0;
	titleField.gridy = 0;
	titleField.ipady = ipadiy;
	titleField.ipadx = ipadixl;
	companyPanel.setLayout(new GridBagLayout());
	companyPanel.add(titles, titleField);
	upperGrid1.add(titles);
	titles.setText(projectDetails.get(1));
	titles.setEditable(false);

	/*row 3*/
	upperGrid1.add(new JLabel("Contact Name:"));

	/*companyPanels.setLayout(new GridBagLayout());*/
	contact = new JTextField(textFieldColunm);
	upperGrid1.add(contact);
	contact.setText(projectDetails.get(2));
	contact.setEditable(false);
	
	/*row 4*/

    upperGrid1.add(new JLabel("Address Line 1:"));

	firstAddresses = new JTextField(textFieldColunm);
	upperGrid1.add(firstAddresses);
	firstAddresses.setText(projectDetails.get(3));
	firstAddresses.setEditable(false);

	 
	 /*row 5*/

    upperGrid1.add(new JLabel("Address Line 2:"));

	secoundAddresses = new JTextField(textFieldColunm);
	upperGrid1.add(secoundAddresses);
	secoundAddresses.setText(projectDetails.get(4));
	secoundAddresses.setEditable(false);
	
		 /*row 6*/
    upperGrid1.add(new JLabel("Town:"));

	townAddresses = new JTextField(textFieldColunm);
	upperGrid1.add(townAddresses);
	townAddresses.setText(projectDetails.get(5));
	townAddresses.setEditable(false);
	 
	 /*row 7*/
    upperGrid1.add(new JLabel("County:"));
	
	countyAddresses = new JTextField(textFieldColunm);
	upperGrid1.add(countyAddresses);
	countyAddresses.setText(projectDetails.get(6));
	countyAddresses.setEditable(false);
	
		 /*row 8*/
    upperGrid1.add(new JLabel("Post Code:"));
	
	postAddresses = new JTextField(textFieldColunm);
	upperGrid1.add(postAddresses);
	postAddresses.setText(projectDetails.get(7));
	postAddresses.setEditable(false);
	
	 /*row 9*/ 
	upperGrid1.add(new JLabel("Country:"));
	
	country = new JTextField(textFieldColunm);
	upperGrid1.add(country);
	country.setText(projectDetails.get(8));
	country.setEditable(false);
	
	 /*row 10*/
    upperGrid1.add(new JLabel("Telephone:"));
	
	clientTele = new JTextField(textFieldColunm);
	upperGrid1.add(clientTele);
	clientTele.setText(projectDetails.get(9));
	clientTele.setEditable(false);

		 /*row 11*/
    upperGrid1.add(new JLabel("Mobile:"));

	clientMobile = new JTextField(textFieldColunm);
	upperGrid1.add(clientMobile);
	clientMobile.setText(projectDetails.get(10));
	clientMobile.setEditable(false);
	
	/*row 12*/
    upperGrid1.add(new JLabel("Email:"));

	JPanel clientEmailPanel = new JPanel();
	clientEmailPanel.setLayout(new GridBagLayout());
	GridBagConstraints emailTextField = new GridBagConstraints();
	clientEmail = new JTextField(textFieldColunm);
	emailTextField.gridx = 0;
	emailTextField.gridy = 0;
	emailTextField.ipady = ipadiy;
	emailTextField.ipadx = ipadixl;
	clientEmail = new JTextField(textFieldColunm);
	clientEmailPanel.add(clientEmail ,emailTextField);
	upperGrid1.add(clientEmailPanel);
	clientEmail.setText(projectDetails.get(11));
	clientEmail.setEditable(false);

	upperGrid2.add(new JLabel(""));
	upperGrid2.add(new JLabel(""));
	
	upperGrid2.add(new JLabel("Status:"));
	JPanel statusPanel = new JPanel();
	statusPanel.setLayout(new GridBagLayout());
	GridBagConstraints statusField = new GridBagConstraints();
	JTextField status = new JTextField(textFieldColunm);
	statusField.gridx = 0;
	statusField.gridy = 0;
	statusField.ipady = ipadiy;
	statusField.ipadx = ipadixs;
	statusPanel.add(status, statusField);
	upperGrid2.add(statusPanel);
	status.setText(projectDetails.get(12));
	status.setHorizontalAlignment(JTextField.CENTER);
	status.setEditable(false);

	upperGrid2.add(new JLabel("Invoiced:"));
	JPanel invoicedPanel = new JPanel();
	invoicedPanel.setLayout(new GridBagLayout());
	GridBagConstraints invoicedField = new GridBagConstraints();
	JTextField invoiced = new JTextField(textFieldColunm);
	invoicedField.gridx = 0;
	invoicedField.gridy = 0;
	invoicedField.ipady = ipadiy;
	invoicedField.ipadx = ipadixs;
	invoicedPanel.add(invoiced, invoicedField);
	upperGrid2.add(invoicedPanel);
	invoiced.setText(projectDetails.get(13));
	invoiced.setHorizontalAlignment(JTextField.CENTER);
	invoiced.setEditable(false);
	
	upperGrid2.add(new JLabel(""));
	upperGrid2.add(new JLabel(""));
	
	upperGrid2.add(new JLabel(""));
	upperGrid2.add(new JLabel(""));
	
	upperGrid2.add(new JLabel(""));
	upperGrid2.add(new JLabel(""));
	
	upperGrid2.add(new JLabel(""));
	upperGrid2.add(new JLabel(""));
	
	upperGrid2.add(new JLabel(""));
	upperGrid2.add(new JLabel(""));
	
	upperGrid2.add(new JLabel(""));
	upperGrid2.add(new JLabel(""));
	
	upperGrid2.add(new JLabel(""));
	upperGrid2.add(new JLabel(""));
	
	billButton = new JRadioButton("Billing Address", true);
	upperGrid2.add(billButton);
	upperGrid2.add(new JLabel(""));
	
	siteButton = new JRadioButton("Site Address");
	upperGrid2.add(siteButton);
	upperGrid2.add(new JLabel(""));

	
	GridBagConstraints upperGridone = new GridBagConstraints();
	upperGridone.gridx = 0;
	upperGridone.gridy = 0;
	upperGridone.insets = new Insets(0, 0, 0, 0);
	upperGridBag.add(upperGrid1, upperGridone);
	
	GridBagConstraints upperGridtwo = new GridBagConstraints();
	upperGridtwo.gridx = 1;
	upperGridtwo.gridy = 0;
	upperGridtwo.insets = new Insets(0, 40, 0, 0);
	upperGridBag.add(upperGrid2, upperGridtwo);

	ButtonGroup group = new ButtonGroup();
    group.add(billButton);
    group.add(siteButton);
	
	detailPanel.add(upperGridBag);
	
	customerId = new JTextField(textFieldColunm);
	customerId.setText(projectDetails.get(14));
	customerName = new JTextField(textFieldColunm);
	customerName.setText(projectDetails.get(2));
	
	//quote details
	ArrayList<String> quoteDetails = (ArrayList<String>)details.get(1);

	/* right column*/
    lowerGrid1.add(new JLabel("Quote Id:"));

	JPanel quoteIdPanel = new JPanel();
	quoteIdPanel.setLayout(new GridBagLayout());
	GridBagConstraints qIdTextField = new GridBagConstraints();
	quoteId = new JTextField(textFieldColunm);
	qIdTextField.gridx = 0;
	qIdTextField.gridy = 0;
	qIdTextField.ipady = ipadiy;
	qIdTextField.ipadx = ipadixq;
	qIdTextField.weightx = weightix;
	qIdTextField.insets = new Insets(0, 0, 0, paddingRightLower);
	quoteIdPanel.add(quoteId, qIdTextField);
	quoteId.setText(quoteDetails.get(0));
	quoteId.setEditable(false);
	lowerGrid1.add(quoteIdPanel);
	
	/* row 2*/
    lowerGrid1.add(new JLabel("Net Amount:"));

	JPanel netAmountPanel = new JPanel();
	netAmountPanel.setLayout(new GridBagLayout());
	GridBagConstraints netField = new GridBagConstraints();
	netAmount = new JTextField(textFieldColunm);
	netField.gridx = 0;
	netField.gridy = 0;
	netField.ipady = ipadiy;
	netField.ipadx = ipadixq;
	netField.insets = new Insets(0, 0, 0, paddingRightLower);
	netAmountPanel.add(netAmount, netField);
	netAmount.setText(quoteDetails.get(2));
	netAmount.setEditable(false);
	lowerGrid1.add(netAmountPanel);
	
	/*row 3*/
    lowerGrid1.add(new JLabel("VAT:"));

	JPanel vatAmountPanel = new JPanel();
	vatAmountPanel.setLayout(new GridBagLayout());
	GridBagConstraints vatField = new GridBagConstraints();
	vatAmount = new JTextField(textFieldColunm);
	vatField.gridx = 0;
	vatField.gridy = 0;
	vatField.ipady = ipadiy;
	vatField.ipadx = ipadixq;
	vatField.insets = new Insets(0, 0, 0, paddingRightLower);
	vatAmountPanel.add(vatAmount, vatField);
	vatAmount.setText(quoteDetails.get(3));
	vatAmount.setEditable(false);
	lowerGrid1.add(vatAmountPanel);
	
	/*row 4*/
    lowerGrid1.add(new JLabel("Total Amount:"));

	JPanel totalAmountPanel = new JPanel();
	totalAmountPanel.setLayout(new GridBagLayout());
	GridBagConstraints totalField = new GridBagConstraints();
	totalAmount = new JTextField(textFieldColunm);
	totalField.gridx = 0;
	totalField.gridy = 0;
	totalField.ipady = ipadiy;
	totalField.ipadx = ipadixq;
	totalField.insets = new Insets(0, 0, 0, paddingRightLower);
	totalAmountPanel.add(totalAmount, totalField);
	totalAmount.setText(quoteDetails.get(4));
	totalAmount.setEditable(false);
	lowerGrid1.add(totalAmountPanel);
	

	
	/*row 5*/
    lowerGrid1.add(new JLabel("Total Item:"));

	JPanel totalItemPanel = new JPanel();
	totalItemPanel.setLayout(new GridBagLayout());
	GridBagConstraints itemsField = new GridBagConstraints();
	totalItem = new JTextField(textFieldColunm);
	itemsField.gridx = 0;
	itemsField.gridy = 0;
	itemsField.ipady = ipadiy;
	itemsField.ipadx = ipadixq;
	itemsField.insets = new Insets(0, 0, 0, paddingRightLower);
	totalItemPanel.add(totalItem, itemsField);
	totalItem.setText(quoteDetails.get(5));
	totalItem.setEditable(false);
	lowerGrid1.add(totalItemPanel);
	
	/* left column*/
	lowerGrid2.add(new JLabel("Valid Date:"));

	JPanel datePanel = new JPanel();
	datePanel.setLayout(new GridBagLayout());
	GridBagConstraints dateField = new GridBagConstraints();
	date = new JTextField(textFieldColunm);
	dateField.gridx = 0;
	dateField.gridy = 0;
	dateField.ipady = ipadiy;
	dateField.ipadx = ipadixq;
	dateField.insets = new Insets(0, 0, 0, 0);
	datePanel.add(date, dateField);
	date.setText(ul.usDateString(quoteDetails.get(1)));
	date.setEditable(false);
	lowerGrid2.add(datePanel);
	
	lowerGrid2.add(new JLabel(""));
	lowerGrid2.add(new JLabel(""));
	
	lowerGrid2.add(new JLabel(""));
	lowerGrid2.add(new JLabel(""));
	
	lowerGrid2.add(new JLabel(""));
	lowerGrid2.add(new JLabel(""));
	
	lowerGrid2.add(new JLabel(""));
	JPanel generatePanel = new JPanel();
	generatePanel.setLayout(new GridBagLayout());
	GridBagConstraints gener = new GridBagConstraints();
	generate = new JButton("Generate");
	gener.gridx = 0;
	gener.gridy = 0;
	gener.insets = new Insets(0, 0, 0, 0);
	generatePanel.add(generate, gener);
	lowerGrid2.add(generatePanel);
	

	
	GridBagConstraints lowerGridone = new GridBagConstraints();
	lowerGridone.gridx = 0;
	lowerGridone.gridy = 0;
	lowerGridone.insets = new Insets(0, -10, 0, 0);
	lowerGridBag.add(lowerGrid1, lowerGridone);
	
	GridBagConstraints lowerGridtwo = new GridBagConstraints();
	lowerGridtwo.gridx = 1;
	lowerGridtwo.gridy = 0;
	lowerGridtwo.insets = new Insets(0, 30, 0, 0);
	lowerGridBag.add(lowerGrid2, lowerGridtwo);
	
	
	detailPanel.add(lowerGridBag);

	GridBagConstraints addOn = new GridBagConstraints();
	add_on = new JButton("Copy");
	addOn.gridx = 1;
	addOn.gridy = 0;
	buttonPanel.add(add_on, addOn);
	
	GridBagConstraints editDetails = new GridBagConstraints();
	edit = new JButton("Edit");
	editDetails.gridx = 2;
	editDetails.gridy = 0;
	editDetails.insets = new Insets(0, 0, 0, 10);
	buttonPanel.add(edit, editDetails);

	GridBagConstraints refreshPay = new GridBagConstraints();
	refresh = new JButton("Refresh");
	refreshPay.gridx = 3;
	refreshPay.gridy = 0;
	refreshPay.insets = new Insets(0, 0, 0, 0);
	buttonPanel.add(refresh, refreshPay);
	
	GridBagConstraints exitPay = new GridBagConstraints();
	exit = new JButton("Exit");
	exitPay.gridx = 4;
	exitPay.gridy = 0;
	buttonPanel.add(exit, exitPay);
	
	GridBagConstraints buttomGrid = new GridBagConstraints();
	buttomGrid.gridx = 0;
	buttomGrid.gridy = 0;
	buttomGrid.insets = new Insets(0, 0, 40, -350);
	buttomGridLayout.add(buttonPanel, buttomGrid);
	
	mainPanel.add("Center", detailPanel);
	mainPanel.setBorder(new Partition(20,"ProjectDetail"));
	con.add(mainPanel);
	con.add(buttomGridLayout, "South");
	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(650, 600);
	setVisible(true); 
	setResizable(false);	
  	setIconImage(ul.mainImage("images/src/gui_icon.png"));
	
	//projectDetail.addActionListener(this);   
	edit.addActionListener(this);
	refresh.addActionListener(this);   
	add_on.addActionListener(this);   
	exit.addActionListener(this);
	generate.addActionListener(this);
    billButton.addItemListener(this);        
    siteButton.addItemListener(this); 	
  }
	public String getLine1(){
		if(firstAddresses.getText().equals(""))
			return new String( "\" \"");
		return firstAddresses.getText();
	}
	
	public String getLine2(){
		if(secoundAddresses.getText().equals(""))
			return new String( "\" \"");
		return secoundAddresses.getText();
	}
	
	public String getTown(){
		if(townAddresses.getText().equals(""))
			return new String( "\" \"");
		return townAddresses.getText();
	}
	
	public String getCounty(){
		if(countyAddresses.getText().equals(""))
			return new String( "\" \"");
		return countyAddresses.getText();
	}
	
	/*public String getCountry(){
		if(country.getText().equals(""))
			return new String( "\" \"");
		return country.getText();
	}*/
	
	public String getPostCode(){
		if(postAddresses.getText().equals(""))
			return new String( "\" \"");
		return postAddresses.getText();
	}

	public String addresses(){
		return getLine1()+","+getLine2()+","+getTown()+","+getCounty()+","+getPostCode()+","+"UK";
	}
	
	public void itemStateChanged(ItemEvent e){
		
		if(e.getSource().equals(siteButton)){
			contact.setText(siteDetails.get(0));
			firstAddresses.setText(siteDetails.get(1));
			secoundAddresses.setText(siteDetails.get(2));
			townAddresses.setText(siteDetails.get(3));
			countyAddresses.setText(siteDetails.get(4));
			postAddresses.setText(siteDetails.get(5));
			country.setText(siteDetails.get(6));
			clientMobile.setText(siteDetails.get(7));
		}
		
		else{
			contact.setText(projectDetails.get(2));
			firstAddresses.setText(projectDetails.get(3));
			secoundAddresses.setText(projectDetails.get(4));
			townAddresses.setText(projectDetails.get(5));
			countyAddresses.setText(projectDetails.get(6));
			postAddresses.setText(projectDetails.get(7));
			country.setText(projectDetails.get(8));
			clientMobile.setText(projectDetails.get(10));
		
		}
	}
    public void actionPerformed( ActionEvent e) {  

		if (e.getSource().equals(exit)){
			dispose();	
		}
		if (e.getSource().equals(generate)){
			ArrayList<Object> projectQuote = ul.getArrayList(bean.connect().quotePDFSummary((Integer.valueOf(quoteId.getText())).intValue()));
			new QuotePdf(projectQuote);
		}
		
		if(e.getSource().equals(edit)){
			/*if(((String)projectDetail.getSelectedItem()).equals("Quote")){
				new EditQuote("Update Quote", (Integer.valueOf(quoteId.getText())).intValue());
			}
			
			else{*/
			//	try{
				new EditProjects("Edit Project", (Integer.valueOf(projectId.getText())).intValue(), (Integer.valueOf(quoteId.getText())).intValue());
					/*}
				catch (Exception ex) { 
					JOptionPane.showMessageDialog(this, "Quote PDF not generated!" +ex.toString(),   
                        "Error!", JOptionPane.INFORMATION_MESSAGE);
		
				}
			}*/
		}
		
		if(e.getSource().equals(add_on)){
			/*ArrayList<String> addOnDetails = new ArrayList<String>();
			
			addOnDetails.add(company.getText());
			addOnDetails.add(addresses());
			addOnDetails.add(clientTele.getText());
			addOnDetails.add(clientMobile.getText());
			addOnDetails.add(clientEmail.getText());
			addOnDetails.add(customerId.getText());
			addOnDetails.add(customerName.getText());
			new AddOnProject("Add On Project",  addOnDetails);
			try{*/
			new CopyProject("Copy Project", (Integer.valueOf(projectId.getText())).intValue());
			
				/*			}
				catch (Exception ex) { 
					JOptionPane.showMessageDialog(this, "Quote PDF not generated!" +ex.toString(),   
                        "Error!", JOptionPane.INFORMATION_MESSAGE);
		
				}*/
		}
		if(e.getSource().equals(refresh)){
			dispose();
			new ProjectDetails((Integer.valueOf(projectId.getText())).intValue(), "Client Profile & Quote");
		}
    }
}