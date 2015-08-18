
package scr.protool.client.customer;
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

public class IncomingDetails extends  JFrame implements ActionListener, ListSelectionListener{//ItemListener implements Serializable, ActionListener

    Container con = getContentPane();
    private JPanel mainPanel;
    private JPanel demacationPanel;//might be needed
	private JPanel detailPanel;
    private JPanel ledgerPanel;
	private JPanel ledgerButtonPanel;
	private JPanel ledgerTablePanel;
    private JButton refresh;//bottom for mainPanel
    private JButton editPayment;//bottom for ledgerPanel
    private JButton exit;//bottom for mainPanel
	private JTextField type;
	private JTextField note;
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
	private Incomingview view;
	private PaymentInUtilities pu = new PaymentInUtilities();
    private final String[]columnNames = {"DATE","Received/BDebt(£)","VAT(£)","CIS(£)","DATE","Amount(£)", " "};//"DATE","RECEIPT(£)","QUOTE(£)"
   

    public IncomingDetails(String title, Incomingview view) {
	super(title);
	this.view = view;
	mainPanel = new JPanel();
	detailPanel = new JPanel();
	ledgerPanel = new JPanel();
	ledgerTablePanel = new JPanel();
	ledgerButtonPanel = new JPanel();
	demacationPanel = new JPanel();
	detailPanel.setLayout(new GridBagLayout());
	ledgerPanel.setLayout(new GridBagLayout());
	ledgerTablePanel.setLayout(new BorderLayout());

	
	ledgerButtonPanel.setLayout(new GridLayout(1,4));
	GridBagConstraints typeLabel = new GridBagConstraints();
	typeLabel.gridx = 0;
	typeLabel.gridy = 0;
	typeLabel.anchor =  GridBagConstraints.EAST;
	typeLabel.weightx = weightix;
	typeLabel.insets = new Insets(15, 0, 20, 0);
    detailPanel.add(new JLabel("PAYMENT TYPE:"), typeLabel);

	GridBagConstraints typeTextField = new GridBagConstraints();
	type = new JTextField(textFieldColunm);
	typeTextField.gridx = 1;
	typeTextField.gridy = 0;
	typeTextField.ipady = ipadiy;
	typeTextField.ipadx = ipadix;
	typeTextField.insets = new Insets(15, 0, 20, 0);
	typeTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(type, typeTextField);
	type.setEditable(false);
	
	GridBagConstraints noteLabel = new GridBagConstraints();
	noteLabel.gridx = 2;
	noteLabel.gridy = 0;
	noteLabel.anchor =  GridBagConstraints.EAST;
	noteLabel.weightx = weightix;
	noteLabel.insets = new Insets(0, 25, 0, 0);
    detailPanel.add(new JLabel("Additional Info:"), noteLabel);

	GridBagConstraints noteTextField = new GridBagConstraints();
	note = new JTextField(textFieldColunm);
	noteTextField.gridx = 3;
	noteTextField.gridy = 0;
	noteTextField.ipady = ipadiy;
	noteTextField.ipadx = ipadix;
	noteTextField.weightx = weightix;
	noteTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(note, noteTextField);
	note.setEditable(false);

	
	//JTextArea set up
	ledgerTablePanel.add(new JLabel(String.format("%s %s", "INVOICE NO", view.getInvoioceId())),BorderLayout.NORTH);
	Vector<String> columns = new Vector<String>(); 
	for(int tableHeader = 0; tableHeader < columnNames.length; tableHeader++) { 
	    columns.addElement(columnNames[tableHeader]); 
	} 
	ledgerDataModel = new DefaultTableModel(columns, initialRow); 
	ledgerView = new JTable(ledgerDataModel);
	ledgerView.setPreferredScrollableViewportSize(new Dimension(500, 380));
	ledgerView.setShowGrid(false);
	
	ledgerView.removeColumn(ledgerView.getColumnModel().getColumn(6));//remove column number 5, which contain paymentId
	
	ListSelectionModel rowSM = ledgerView.getSelectionModel();
	
	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	ledgerViewScroll = new JScrollPane(ledgerView, v,h);
	ledgerTablePanel.add(ledgerViewScroll, BorderLayout.CENTER);
	
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

	ledgerTablePanel.add(ledgerPanel, BorderLayout.SOUTH);
	mainPanel.add("North", detailPanel);
	mainPanel.add("Center", ledgerTablePanel);
	mainPanel.setBorder(new Partition(20,"Ledger"));
	con.add(mainPanel);
	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(650, 600);
	setResizable(false);
	setVisible(true);   
	pu.paymentInDetails(ledgerDataModel, view);

	rowSM.addListSelectionListener(this);	
	exit.addActionListener(this);
	refresh.addActionListener(this);
	editPayment.addActionListener(this);	
    }
	
	public DefaultTableModel getTableModelName(){
		return ledgerDataModel;
	}
	
	public JTextField getNote(){
		return note;
	}
	
	public JTextField getType(){
		return type;
	}
	
	public int getRow(){
		return ledgerView.getSelectedRow();
	}

    public void actionPerformed( ActionEvent e) {  
	if (e.getSource().equals(exit)){
	    dispose();	
	  }
	 if (e.getSource().equals(refresh)){
		 pu.paymentInDetails(ledgerDataModel, view);
	  }
	  if (e.getSource().equals(editPayment)){
		
		 new EditIncoming("EDIT INCOMING", this);
		 

	  }
	  
    }
	
    public void valueChanged(ListSelectionEvent e) {
		int  maintablerow = ledgerView.getSelectedRow();
		int paymentId = ((Integer)ledgerDataModel.getValueAt(0, 6)).intValue();
		if(maintablerow > 0 && maintablerow <= pu.arrayListOfObject(paymentId).size()){
			type.setText(String.valueOf(pu.arrayListOfObject(paymentId).get(maintablerow-1).get(1)));
			note.setText(String.valueOf(pu.arrayListOfObject(paymentId).get(maintablerow-1).get(4)));
		}
		else{
			type.setText("");
			note.setText("");
		}
   }  
   
}