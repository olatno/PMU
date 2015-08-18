package scr.protool.client.customer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import javax.naming.*;
import java.io.*;
import scr.protool.client.utilities.*;
import java.math.BigDecimal;
import scr.protool.client.miscelleneous.BeanStud;

/**

*
*

*
* @author <a href="mailto:"></a>
* @version 1.0
*/
public class Incomingview extends JPanel implements  ActionListener{// implements Serializable, ActionListener{

	private BeanStud bean;
    private JTextField invoiceNo;
    private JTextField projectId;
    private JSpinner date_from;
    private JSpinner date_to;
    private JTextField cost_from;
    private JTextField cost_to;
    private JTextField quote_from;
    private JTextField quote_to;
    private JButton addpayment;
    private JButton closeproject;
    private JButton searchproject;
    private JButton editproject;
    private JButton removeproject;
   	private PaymentInUtilities pu;
    private JButton details;
    private JButton find;
    private JPanel buttonpanel;
    private JPanel searchpanel;
    private JPanel tablepanel;
    private SpinnerHelper sh1;
	private SpinnerHelper sh2;
	private Utilities ul = new Utilities();
    private JTable projectView; 
    private DefaultTableModel projectDataModel; 
    private JScrollPane projectViewScroll; 
    private final String[]columnNames = {"Project Title","Invoice NO","Invoice Date","Invoice Amount","Payment","Payment Term","Over Due", " "};
    private final int initialRow = 18;
    private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int textFieldColunm = 1;
    private final double weightix = 1.0;
    
    public Incomingview(String title){
   	super(new BorderLayout()); 
   
	buttonpanel = new JPanel();
	searchpanel = new JPanel();
	tablepanel = new JPanel();
	sh1 = new SpinnerHelper();
	sh2 = new SpinnerHelper();
	pu = new PaymentInUtilities();
	bean = new BeanStud();
	
	//Panels
	buttonpanel.setLayout(new GridBagLayout());
	searchpanel.setLayout(new GridBagLayout());
	tablepanel.setLayout(new BorderLayout());
		
	GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 0;
	date1Label.weightx=0.4;
	date1Label.anchor =  GridBagConstraints.EAST;
	searchpanel.add(new JLabel("DATE FROM:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	date_from = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 0;
	date1TextField.ipady = ipadiy;
	date1TextField.ipadx = 50;
	date1TextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(sh1.getGenericDate(date_from), date1TextField);

	GridBagConstraints date2Label = new GridBagConstraints();
	date2Label.gridx = 2;
	date2Label.gridy = 0;
	date2Label.anchor =  GridBagConstraints.EAST;
	searchpanel.add(new JLabel("DATE TO:"), date2Label);

	GridBagConstraints date2TextField = new GridBagConstraints();
	date_to = new JSpinner();
	date2TextField.gridx = 3;
	date2TextField.gridy = 0;
	date2TextField.ipady = ipadiy;
	date2TextField.ipadx = 50;
	date2TextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(sh2.getGenericDate(date_to), date2TextField);
	
		GridBagConstraints quote1Label = new GridBagConstraints();
	quote1Label.gridx = 0;
	quote1Label.gridy = 1;
	quote1Label.anchor =  GridBagConstraints.EAST;
	searchpanel.add(new JLabel("AMOUNT FROM:"), quote1Label);

	GridBagConstraints quote1TextField = new GridBagConstraints();
	quote_from = new JTextField(textFieldColunm);
	quote1TextField.gridx = 1;
	quote1TextField.gridy = 1;
	quote1TextField.ipady = ipadiy;
	quote1TextField.ipadx = ipadix;
	quote1TextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(quote_from, quote1TextField);


	GridBagConstraints quote2Label = new GridBagConstraints();
	quote2Label.gridx = 2;
	quote2Label.gridy = 1;
	quote2Label.anchor =  GridBagConstraints.EAST;
	searchpanel.add(new JLabel("AMOUNT TO:"), quote2Label);

	GridBagConstraints quote2TextField = new GridBagConstraints();
	quote_to = new JTextField(textFieldColunm);
	quote2TextField.gridx = 3;
	quote2TextField.gridy = 1;
	quote2TextField.ipady = ipadiy;
	quote2TextField.ipadx = ipadix;
	quote2TextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(quote_to, quote2TextField);
	
	GridBagConstraints clientLabel = new GridBagConstraints();
	clientLabel.gridx = 0;
	clientLabel.gridy = 2;
	clientLabel.anchor =  GridBagConstraints.EAST;
	// 	clientLabel.weighty = weightiy;
       	searchpanel.add(new JLabel("PROJECT ID:"), clientLabel);

	GridBagConstraints clientTextField = new GridBagConstraints();
	projectId = new JTextField(textFieldColunm);
	clientTextField.gridx = 1;
	clientTextField.gridy = 2;
	clientTextField.ipady = ipadiy;
	clientTextField.ipadx = ipadix;
	clientTextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(projectId, clientTextField);

	GridBagConstraints titleLabel = new GridBagConstraints();
	titleLabel.gridx = 2;
	titleLabel.gridy = 2;
	titleLabel.weightx = weightix;
	titleLabel.anchor =  GridBagConstraints.EAST;
	searchpanel.add(new JLabel("INVOICE NO:"), titleLabel);

	GridBagConstraints titleTextField = new GridBagConstraints();
	invoiceNo = new JTextField(textFieldColunm);
	titleTextField.gridx = 3;
	titleTextField.gridy = 2;
	titleTextField.ipady = ipadiy;
	titleTextField.ipadx = ipadix;
    //titleTextField.weightx = weightix;
	titleTextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(invoiceNo, titleTextField);
	
	GridBagConstraints searchbutton = new GridBagConstraints();
	searchproject = new JButton("SEARCH");
	searchbutton.gridx = 4;
	searchbutton.gridy = 1;
	searchbutton.ipady = 0;
    searchbutton.weightx = weightix;
	searchpanel.add(searchproject, searchbutton);
	
	
    searchpanel.setBorder(new Partition(20,"Tool"));
	//BUTTON WEST
	
	GridBagConstraints addButton = new GridBagConstraints();
	addpayment = new JButton("<html><b>Payment</b></html>");
	addButton.gridx = 0;
	addButton.gridy = 0;
	addButton.insets = new Insets(5, 0, 0, 0);
	buttonpanel.add(addpayment, addButton);

	GridBagConstraints detailsButton = new GridBagConstraints();
	details = new JButton("<html><b>Details</b></html>");
	detailsButton.gridx = 1;
	detailsButton.gridy = 0;
	detailsButton.insets = new Insets(5, 0, 0, 0);
	buttonpanel.add(details, detailsButton);

	GridBagConstraints invoiceButton = new GridBagConstraints();
	closeproject = new JButton("<html><b>Edit</b></html>");
	invoiceButton.gridx = 2;
	invoiceButton .gridy = 0;
	invoiceButton.insets = new Insets(5, 0, 0, 0);
	buttonpanel.add(closeproject, invoiceButton );

	//Table formation
	Vector<String> columns = new Vector<String>(); 
	for(int tableHeader = 0; tableHeader < columnNames.length; tableHeader++) { 
	    columns.addElement(columnNames[tableHeader]); 
	} 
	projectDataModel = new DefaultTableModel(columns, initialRow); 
	projectView = new JTable(projectDataModel);  
 
	int[]widthValue = {300,175,150,150,150,150,100};

	for(int i = 0; i<widthValue.length; i++){
	    TableColumn col  = projectView.getColumnModel().getColumn(i);
	    col.setPreferredWidth(widthValue[i]);
	}
	projectView.removeColumn(projectView.getColumnModel().getColumn(7));
	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	projectViewScroll = new JScrollPane(projectView, v,h); 
	tablepanel.add(projectViewScroll);
	projectViewScroll.setMinimumSize(new Dimension (720, 323));	

	add("South", buttonpanel);
	add("North", searchpanel); 
	add("Center", tablepanel); 
	
	addpayment.addActionListener(this); 
	details.addActionListener(this);
	searchproject.addActionListener(this);
	
	pu.loadData(projectDataModel, bean.connect().getPaymentData());

    }
	
	public int getTableId(){
		int  maintablerow = projectView.getSelectedRow();
		return ((Integer)projectDataModel.getValueAt(maintablerow, 7)).intValue();
	}
	
	public String getInvoioceId(){
		int  maintablerow = projectView.getSelectedRow();
		return (String)projectDataModel.getValueAt(maintablerow, 2);
	}
	
	public String getAmount(){
		int  maintablerow = projectView.getSelectedRow();
		return (String)projectDataModel.getValueAt(maintablerow, 4);
	}
	
	public String getDate(){
		int  maintablerow = projectView.getSelectedRow();
		return (String)projectDataModel.getValueAt(maintablerow, 3);
	}
	
	public void actionPerformed( ActionEvent e) {  
		if (e.getSource().equals(searchproject)){

			if((quote_from.getText().equals("") && quote_to.getText().equals("")) && (projectId.getText().equals("")&&invoiceNo.getText().equals(""))){
				//date search
				pu.loadData(projectDataModel, bean.connect().getPaymentData(pu.sqlDateString(sh1.getGenericDate(date_from)), pu.sqlDateString(sh2.getGenericDate(date_to))), initialRow);

			}
			else if((projectId.getText().equals("")&&invoiceNo.getText().equals(""))&&(!quote_from.getText().equals("") && !quote_to.getText().equals(""))){
					//amount search and reset 
					pu.loadData(projectDataModel, bean.connect().getPaymentData(pu.sqlDateString(sh1.getGenericDate(date_from)), pu.sqlDateString(sh2.getGenericDate(date_to)), new BigDecimal(quote_from.getText()), new BigDecimal(quote_to.getText())), initialRow);

			}
			else if ((quote_from.getText().equals("") && quote_to.getText().equals(""))&&(invoiceNo.getText().equals("")&&!projectId.getText().equals(""))){
					//project id search
					pu.loadData(projectDataModel, bean.connect().getPaymentData(pu.sqlDateString(sh1.getGenericDate(date_from)), pu.sqlDateString(sh2.getGenericDate(date_to)), (Integer.valueOf(projectId.getText())).intValue()), initialRow);

			}
			else if((quote_from.getText().equals("") && quote_to.getText().equals("")) && (!invoiceNo.getText().equals("") && projectId.getText().equals(""))){
				//invoice no search	
				pu.loadData(projectDataModel, bean.connect().getPaymentData(pu.sqlDateString(sh1.getGenericDate(date_from)), pu.sqlDateString(sh2.getGenericDate(date_to)), invoiceNo.getText()), initialRow);

			}
	}
	  
	if (e.getSource().equals(addpayment)){
	
	    AddIncoming ds = new AddIncoming("INCOMING", getTableId());

	  }
	  
	if (e.getSource().equals(details)){
	     IncomingDetails incoming = new IncomingDetails("Customer Ledger", this);
	  }
    }

}