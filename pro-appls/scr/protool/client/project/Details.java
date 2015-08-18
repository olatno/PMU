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
import scr.protool.client.utilities.*;
import scr.protool.client.miscelleneous.BeanStud;


public class Details extends  JFrame implements Serializable, ActionListener{//ItemListener implements Serializable, ActionListener

    Container con = getContentPane();
    private JPanel mainPanel;
	private JPanel detailPanel;
    private JPanel ledgerPanel;
	private JPanel ledgerTablePanel;
    private JButton project;//upper for detailPanel
	private JButton editDetails;//upper for detailPanel
    private JButton refresh;//bottom for mainPanel
    private JButton update;//bottom for ledgerPanel
    private JButton editPayment;//bottom for ledgerPanel
    private JButton exit;//bottom for mainPanel
	private JTextField clientName;
	private JTextField firstAddresses;
	private JTextField townAddresses;
	private JTextField countyAddresses;
	private JTextField postAddresses;
	private JTextField clientTele;
	private JTextField clientEmail;
	private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int ipadiyb = 2;
    private final int ipadixb = 4;
    private final double weightix = 2.0;
	private final double weightiy = 1.0;
	private final int initialRow = 13;
	private final int textFieldColunm = 1;
	 private JTable ledgerView; 
    private DefaultTableModel ledgerDataModel; 
    private JScrollPane ledgerViewScroll; 
	private JScrollPane buttonPanel;
	private BeanStud bean;
	private ProtoolProject tableModel;
    private final String[]columnNames = {"DATE","RECEIPT(£)","DATE","Amount(£)"};//"DATE","RECEIPT(£)","QUOTE(£)"
   

    public Details(int projectid, String title) {
	super(title);

	bean = new BeanStud();
	mainPanel = new JPanel();
	detailPanel = new JPanel();
	ledgerPanel = new JPanel();
	ledgerTablePanel = new JPanel();
	detailPanel.setLayout(new GridBagLayout());
	ledgerPanel.setLayout(new GridBagLayout());
	ledgerTablePanel.setLayout(new BorderLayout());

	GridBagConstraints clientLabel = new GridBagConstraints();
	clientLabel.gridx = 0;
	clientLabel.gridy = 0;
	clientLabel.anchor =  GridBagConstraints.EAST;
	clientLabel.weightx = weightix;
 	//clientLabel.weighty = weightiy;
	clientLabel.insets = new Insets(15, 0, 20, 0);
    detailPanel.add(new JLabel("CLIENT NAME:"), clientLabel);

	GridBagConstraints clientTextField = new GridBagConstraints();
	clientName = new JTextField(textFieldColunm);
	clientTextField.gridx = 1;
	clientTextField.gridy = 0;
	clientTextField.ipady = ipadiy;
	clientTextField.ipadx = ipadix;
	clientTextField.insets = new Insets(15, 0, 20, 0);
	clientTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(clientName, clientTextField);
	
	GridBagConstraints addressLabel = new GridBagConstraints();
	addressLabel.gridx = 0;
	addressLabel.gridy = 1;
	addressLabel.anchor =  GridBagConstraints.EAST;
	//addressLabel.weighty = weightiy;
    detailPanel.add(new JLabel("ADDRESSES:"), addressLabel);

	GridBagConstraints addressTextField = new GridBagConstraints();
	firstAddresses = new JTextField(textFieldColunm);
	addressTextField.gridx = 1;
	addressTextField.gridy = 1;
	addressTextField.ipady = ipadiy;
	addressTextField.ipadx = ipadix;
	//addressTextField.weighty = weightiy;
	addressTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(firstAddresses, addressTextField);
	
	GridBagConstraints townLabel = new GridBagConstraints();
	townLabel.gridx = 0;
	townLabel.gridy = 2;
	townLabel.anchor =  GridBagConstraints.EAST;
	// 	clientLabel.weighty = weightiy;
	townLabel.insets = new Insets(20, 0, 20, 0);
    detailPanel.add(new JLabel("TOWN:"), townLabel);

	GridBagConstraints townTextField = new GridBagConstraints();
	townAddresses = new JTextField(textFieldColunm);
	townTextField.gridx = 1;
	townTextField.gridy = 2;
	townTextField.ipady = ipadiy;
	townTextField.ipadx = ipadix;
	townTextField.insets = new Insets(20, 0, 20, 0);
	townTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(townAddresses, townTextField);
	
	GridBagConstraints countyLabel = new GridBagConstraints();
	countyLabel.gridx = 0;
	countyLabel.gridy = 3;
	countyLabel.anchor =  GridBagConstraints.EAST;
	// 	clientLabel.weighty = weightiy;
	countyLabel.insets = new Insets(0, 0, 20, 0);
    detailPanel.add(new JLabel("COUNTY:"), countyLabel);

	GridBagConstraints countyTextField = new GridBagConstraints();
	countyAddresses = new JTextField(textFieldColunm);
	countyTextField.gridx = 1;
	countyTextField.gridy = 3;
	countyTextField.ipady = ipadiy;
	countyTextField.ipadx = ipadix;
	countyTextField.insets = new Insets(0, 0, 20, 0);
	countyTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(countyAddresses, countyTextField);
	
	GridBagConstraints postLabel = new GridBagConstraints();
	postLabel.gridx = 0;
	postLabel.gridy = 4;
	postLabel.anchor =  GridBagConstraints.EAST;
	// 	clientLabel.weighty = weightiy;
    detailPanel.add(new JLabel("POST CODE:"), postLabel);

	GridBagConstraints postTextField = new GridBagConstraints();
	postAddresses = new JTextField(textFieldColunm);
	postTextField.gridx = 1;
	postTextField.gridy = 4;
	postTextField.ipady = ipadiy;
	postTextField.ipadx = ipadix;
	postTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(postAddresses, postTextField);
	
	GridBagConstraints teleLabel = new GridBagConstraints();
	teleLabel.gridx = 2;
	teleLabel.gridy = 0;
	teleLabel.anchor =  GridBagConstraints.EAST;
	teleLabel.weightx = weightix;
	teleLabel.insets = new Insets(0, 25, 0, 0);
    detailPanel.add(new JLabel("TELEPHONE:"), teleLabel);

	GridBagConstraints teleTextField = new GridBagConstraints();
	clientTele = new JTextField(textFieldColunm);
	teleTextField.gridx = 3;
	teleTextField.gridy = 0;
	teleTextField.ipady = ipadiy;
	teleTextField.ipadx = ipadix;
	teleTextField.weightx = weightix;
	teleTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(clientTele, teleTextField);
	
	GridBagConstraints emailLabel = new GridBagConstraints();
	emailLabel.gridx = 2;
	emailLabel.gridy = 1;
	emailLabel.anchor =  GridBagConstraints.EAST;
	//emailLabel.weighty = weightiy;
    detailPanel.add(new JLabel("EMAIL:"), emailLabel);

	GridBagConstraints emailTextField = new GridBagConstraints();
	clientEmail = new JTextField(textFieldColunm);
	emailTextField.gridx = 3;
	emailTextField.gridy = 1;
	emailTextField.ipady = ipadiy;
	emailTextField.ipadx = ipadix;
	//emailTextField.weighty = weightiy;
	emailTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(clientEmail, emailTextField);
	
	GridBagConstraints updateButton = new GridBagConstraints();
	editDetails = new JButton("UPDATE");
	updateButton.gridx = 1;
	updateButton.gridy = 6;
	updateButton.ipady = ipadiyb;
	updateButton.ipadx = ipadixb;
	//teleTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(editDetails, updateButton);
	
	GridBagConstraints newButton = new GridBagConstraints();
	project = new JButton("NEW");
	newButton.gridx = 2;
	newButton.gridy = 6;
	newButton.ipady = ipadiyb;
	newButton.ipadx = ipadixb;
	//teleTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(project, newButton);
	
	//JTextArea set up
	//int  maintablerow = tableModel.getTablename().getSelectedRow();
	//String projectid = (String)tableModel.getTableModelName().getValueAt(maintablerow, 0);
	String projectstring = String.valueOf(projectid);
	String strs = String.format("%1s %s\n %s\n", "PROJECT", projectstring, "LEDGER");
	ledgerTablePanel.add(new JLabel(strs),BorderLayout.NORTH);
	Vector<String> columns = new Vector<String>(); 
	for(int tableHeader = 0; tableHeader < columnNames.length; tableHeader++) { 
	    columns.addElement(columnNames[tableHeader]); 
	} 
	ledgerDataModel = new DefaultTableModel(columns, initialRow); 
	ledgerView = new JTable(ledgerDataModel);
	ledgerView.setPreferredScrollableViewportSize(new Dimension(500, 200));
	ledgerView.setShowGrid(false);
	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	ledgerViewScroll = new JScrollPane(ledgerView, v,h);
	ledgerTablePanel.add(ledgerViewScroll, BorderLayout.CENTER);

	GridBagConstraints updatePay = new GridBagConstraints();
	update = new JButton("ADD");
	updatePay.gridx = 1;
	updatePay.gridy = 0;
	ledgerPanel.add(update, updatePay);
	GridBagConstraints editPay = new GridBagConstraints();
	editPayment = new JButton("EDIT");
	editPay.gridx = 2;
	editPay.gridy = 0;
	ledgerPanel.add(editPayment, editPay);

	GridBagConstraints refreshPay = new GridBagConstraints();
	refresh = new JButton("REFRESH");
	refreshPay.gridx = 3;
	refreshPay.gridy = 0;
	ledgerPanel.add(refresh, refreshPay);

	GridBagConstraints exitPay = new GridBagConstraints();
	exit = new JButton("EXIT");
	exitPay.gridx = 4;
	exitPay.gridy = 0;
	ledgerPanel.add(exit, exitPay);
	
	ledgerTablePanel.add(ledgerPanel, BorderLayout.SOUTH);
	mainPanel.add("North", detailPanel);
	mainPanel.add("Center", ledgerTablePanel);
	mainPanel.setBorder(new Partition(20,"Ledger"));
	con.add(mainPanel);
	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(650, 580);
	setVisible(true);   

	//project.addActionListener(this);   
		exit.addActionListener(this);       
    }


    public void actionPerformed( ActionEvent e) {  

	if (e.getSource().equals(exit)){
	    dispose();	
		}
    }
}