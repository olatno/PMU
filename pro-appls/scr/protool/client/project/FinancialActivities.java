
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

public class FinancialActivities extends  JFrame implements ActionListener, ListSelectionListener{//ItemListener implements Serializable, ActionListener

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
	private JLabel projectTitle;
	private PaymentInUtilities pu = new PaymentInUtilities();
    private int projectId;
	private String name;
   

    public FinancialActivities(String title, int projectId, String name) {
	super(title);
	this.projectId = projectId;
	this.name = name;
	mainPanel = new JPanel();
	detailPanel = new JPanel();
	ledgerPanel = new JPanel();
	ledgerTablePanel = new JPanel();
	ledgerPanel.setLayout(new GridBagLayout());
	detailPanel.setLayout(new GridBagLayout());
	ledgerTablePanel.setLayout(new BorderLayout());
	
	//JTextArea set up
	GridBagConstraints topLable = new GridBagConstraints();
	projectTitle = new JLabel(String.format("%s %s", "Project Name:", name));
	topLable.gridx = 0;
	topLable.gridy = 0;
	topLable.insets = new Insets(10, 0, 0, 0);
	detailPanel.add(projectTitle, topLable);
	projectTitle.setFont(new Font (null, Font.BOLD, 14));
	/*Vector<String> columns = new Vector<String>(); 
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
	ledgerTablePanel.add(new FinancialActivitiesLabel(), BorderLayout.CENTER);*/
	
	GridBagConstraints editPay = new GridBagConstraints();
	editPayment = new JButton("EDIT");
	editPay.gridx = 1;
	editPay.gridy = 0;
	//ledgerPanel.add(editPayment, editPay);
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
	ledgerTablePanel.add(new FinancialActivitiesLabel(projectId), BorderLayout.CENTER);
	mainPanel.add("North", detailPanel);
	mainPanel.add("South", ledgerTablePanel);
	mainPanel.setBorder(new Partition(20,"Ledger"));
	con.add(mainPanel);
	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(650, 600);
	setResizable(false);
	setVisible(true);   
	//pu.projectFinancialDetails(ledgerDataModel, projectId);

	//rowSM.addListSelectionListener(this);	
	exit.addActionListener(this);
	refresh.addActionListener(this);
	editPayment.addActionListener(this);	
    }
	
	//public DefaultTableModel getTableModelName(){
	//	return ledgerDataModel;
	//}
	
	public JTextField getNote(){
		return note;
	}
	
	public JTextField getType(){
		return type;
	}
	
	//public int getRow(){
	//	return ledgerView.getSelectedRow();
	//}

    public void actionPerformed( ActionEvent e) {  
	if (e.getSource().equals(exit)){
	    dispose();	
	  }
	 if (e.getSource().equals(refresh)){
		 dispose();
		 new FinancialActivities("Project Financial Activities", projectId, name);
	  }
	  if (e.getSource().equals(editPayment)){
		
		// new EditIncoming("EDIT INCOMING", this);
		 

	  }
	  
    }
	
    public void valueChanged(ListSelectionEvent e) {
	/*	int  maintablerow = ledgerView.getSelectedRow();
		int paymentId = ((Integer)ledgerDataModel.getValueAt(0, 6)).intValue();
		if(maintablerow > 0 && maintablerow <= pu.arrayListOfObject(paymentId).size()){
			type.setText(String.valueOf(pu.arrayListOfObject(paymentId).get(maintablerow-1).get(1)));
			note.setText(String.valueOf(pu.arrayListOfObject(paymentId).get(maintablerow-1).get(4)));
		}
		else{
			type.setText("");
			note.setText("");
		}*/
   }  
   
}