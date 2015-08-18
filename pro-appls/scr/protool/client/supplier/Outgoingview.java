package scr.protool.client.supplier;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import javax.naming.*;
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
public class Outgoingview extends JPanel implements  ActionListener, ChangeListener{// implements Serializable, ActionListener{

	private BeanStud bean;
    private JTextField testfieldFour;
    private JTextField testfieldThree;
    private JSpinner date_from;
    private JSpinner date_to;
    private JTextField cost_from;
    private JTextField cost_to;
    private JTextField testfieldOne;
    private JTextField testfieldTwo;
    private JButton addpayment;
    private JButton edit;
    private JButton searchpayment;
    private JButton editproject;
    private JButton removeproject;
    private JButton exit;
    private JButton details;
    private JButton find;
    private JPanel buttonpanel;
    private JPanel searchpanel;
    private JPanel tablepanel;
    private SpinnerHelper sh1;
	private SpinnerHelper sh2;
	private Utilities ul = new Utilities();
    private JTable outgoingView; 
    private DefaultTableModel outgoingDataModel;
    private JTable outgoingSubView; 
    private DefaultTableModel outgoingSubDataModel; 	
	private PaymentInUtilities pu;
    private JScrollPane outgoingViewScroll; 
	private JScrollPane outgoingSubViewScroll; 
	private JComponent supplierView;
	private JComponent directLabour;
	private  JTabbedPane tabbedPane;
	private boolean suppliersInvoice = true;
    private final String[]columnNames = {"SUPPLIER NAME","PROJECT TITLE","INVOICE NO","INVOICE DATE","INVOICE AMOUNT","PAYMENT","OVER DUE",""};
	private final String[]columnSubNames = {"SUB-CONTRACTOR","PROJECT TITLE","INVOICE NO","INVOICE DATE","INVOICE AMOUNT","PAYMENT","OVER DUE",""};
    private final int initialRow = 17;
    private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int textFieldColunm = 1;
    private final double weightix = 1.0;
	private   EditSupplierInvoice invoice;
	private JLabel generic;
   // Container con = getContentPane();
    
    public Outgoingview(String title){
   	super(new BorderLayout()); 
   
    bean = new BeanStud();
	buttonpanel = new JPanel();
	searchpanel = new JPanel();
	tablepanel = new JPanel();
	tabbedPane = new JTabbedPane();
	sh1 = new SpinnerHelper();
	sh2 = new SpinnerHelper();
	pu = new PaymentInUtilities();

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
	testfieldOne = new JTextField(textFieldColunm);
	quote1TextField.gridx = 1;
	quote1TextField.gridy = 1;
	quote1TextField.ipady = ipadiy;
	quote1TextField.ipadx = ipadix;
	quote1TextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(testfieldOne, quote1TextField);


	GridBagConstraints quote2Label = new GridBagConstraints();
	quote2Label.gridx = 2;
	quote2Label.gridy = 1;
	quote2Label.anchor =  GridBagConstraints.EAST;
	searchpanel.add(new JLabel("AMOUNT TO:"), quote2Label);

	GridBagConstraints quote2TextField = new GridBagConstraints();
	testfieldTwo = new JTextField(textFieldColunm);
	quote2TextField.gridx = 3;
	quote2TextField.gridy = 1;
	quote2TextField.ipady = ipadiy;
	quote2TextField.ipadx = ipadix;
	quote2TextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(testfieldTwo, quote2TextField);
	
	GridBagConstraints clientLabel = new GridBagConstraints();
	generic = new JLabel();
	clientLabel.gridx = 0;
	clientLabel.gridy = 2;
	clientLabel.anchor =  GridBagConstraints.EAST;
    searchpanel.add(generic, clientLabel);
	//generic.setText("ID:");
	generic.setText(String.format("%25s","SUPPLIER ID:"));

	GridBagConstraints clientTextField = new GridBagConstraints();
	testfieldThree = new JTextField(textFieldColunm);
	clientTextField.gridx = 1;
	clientTextField.gridy = 2;
	clientTextField.ipady = ipadiy;
	clientTextField.ipadx = ipadix;
	clientTextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(testfieldThree, clientTextField);

	GridBagConstraints titleLabel = new GridBagConstraints();
	titleLabel.gridx = 2;
	titleLabel.gridy = 2;
	titleLabel.weightx = weightix;
	titleLabel.anchor =  GridBagConstraints.EAST;
	searchpanel.add(new JLabel("INVOICE NO:"), titleLabel);

	GridBagConstraints titleTextField = new GridBagConstraints();
	testfieldFour = new JTextField(textFieldColunm);
	titleTextField.gridx = 3;
	titleTextField.gridy = 2;
	titleTextField.ipady = ipadiy;
	titleTextField.ipadx = ipadix;
	titleTextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(testfieldFour, titleTextField);
	
	GridBagConstraints searchbutton = new GridBagConstraints();
	searchpayment = new JButton("SEARCH");
	searchbutton.gridx = 4;
	searchbutton.gridy = 1;
	searchbutton.ipady = 0;
    searchbutton.weightx = weightix;
	searchpanel.add(searchpayment, searchbutton);
	
	
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

	GridBagConstraints editButton = new GridBagConstraints();
	edit = new JButton("<html><b>Edit</b></html>");
	editButton.gridx = 2;
	editButton .gridy = 0;
	editButton.insets = new Insets(5, 0, 0, 0);
	buttonpanel.add(edit, editButton );


	supplierView = makeTableViewMaterial(columnNames);         
	tabbedPane.addTab("Material Invoice", supplierView);
	
	directLabour = makeTableViewLabour(columnSubNames);         
	tabbedPane.addTab("Sub-contractor Invoice", directLabour);

	add("South", buttonpanel);
	add("North", searchpanel); 
	add("Center", tabbedPane);
 
	edit.addActionListener(this);
	addpayment.addActionListener(this); 
	details.addActionListener(this); 
	searchpayment.addActionListener(this);
    tabbedPane.addChangeListener(this);
	pu.loadData(outgoingDataModel, bean.connect().getMaterialOutgoingData());
	pu.loadData(outgoingSubDataModel, bean.connect().getLabourOutgoingData());
   }
	
	
	
	public int getTableId(int index){
		int  maintablerow = outgoingView.getSelectedRow();
		return ((Integer)outgoingDataModel.getValueAt(maintablerow, index)).intValue();
	}
	
	public String getStringValue(int index){
		int  maintablerow = outgoingView.getSelectedRow();
		return (String)outgoingDataModel.getValueAt(maintablerow, index);
	}
	
	public int getTableId(int index, DefaultTableModel dataModel, JTable view){
		int  maintablerow = view.getSelectedRow();
		return ((Integer)dataModel.getValueAt(maintablerow, index)).intValue();
	}
	
	
	public String getStringValue(int index, DefaultTableModel dataModel, JTable view){
		int  maintablerow = view.getSelectedRow();
		return (String)dataModel.getValueAt(maintablerow, index);
	}
	
	
	public DefaultTableModel getModel(){
		return outgoingSubDataModel;
	
	}
	
	public JTable getTable(){
	   return outgoingSubView;
	}
	
	public JComponent makeTableViewMaterial(String[] column){
		Vector<String> columns = new Vector<String>(); 
		for(int tableHeader = 0; tableHeader < column.length; tableHeader++) { 
			columns.addElement(column[tableHeader]); 
		} 
		outgoingDataModel = new DefaultTableModel(columns, initialRow); 
		outgoingView = new JTable(outgoingDataModel);  
 
		int[]widthValue = {150,200,200,150,175,150, 100};

		for(int i = 0; i<widthValue.length; i++){
			TableColumn col  = outgoingView.getColumnModel().getColumn(i);
			col.setPreferredWidth(widthValue[i]);
		}
		outgoingView.removeColumn(outgoingView.getColumnModel().getColumn(7));
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
		outgoingViewScroll = new JScrollPane(outgoingView, v,h); 
		tablepanel.add(outgoingViewScroll);
		outgoingViewScroll.setMinimumSize(new Dimension (715, 295));
		return outgoingViewScroll;
	}

	public JComponent makeTableViewLabour(String[] column){
		Vector<String> columns = new Vector<String>(); 
		for(int tableHeader = 0; tableHeader < column.length; tableHeader++) { 
			columns.addElement(column[tableHeader]); 
		} 
		outgoingSubDataModel = new DefaultTableModel(columns, initialRow); 
		outgoingSubView = new JTable(outgoingSubDataModel);  
 
		int[]widthValue = {150,200,200,150,175,150, 100};

		for(int i = 0; i<widthValue.length; i++){
			TableColumn col  = outgoingSubView.getColumnModel().getColumn(i);
			col.setPreferredWidth(widthValue[i]);
		}
		outgoingSubView.removeColumn(outgoingSubView.getColumnModel().getColumn(7));
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
		outgoingSubViewScroll = new JScrollPane(outgoingSubView, v,h); 
		tablepanel.add(outgoingSubViewScroll);
		outgoingSubViewScroll.setMinimumSize(new Dimension (715, 252));
		return outgoingSubViewScroll;
	}		
	
	public String getAmount(){
		int  maintablerow = outgoingView.getSelectedRow();
		return (String)outgoingDataModel.getValueAt(maintablerow, 4);
	}
	
	public String getDate(){
		int  maintablerow = outgoingView.getSelectedRow();
		return (String)outgoingDataModel.getValueAt(maintablerow, 3);
	}
	public void stateChanged(ChangeEvent e){
		JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
		if(sourceTabbedPane.getSelectedComponent().equals(directLabour)){
			suppliersInvoice = false;
			generic.setText("SUB-CONTACTOR ID:");		
		}
		else{
			suppliersInvoice = true;
			generic.setText(String.format("%25s","SUPPLIER ID:"));
		}
	}
	
	public void actionPerformed( ActionEvent e) {  
		if(suppliersInvoice == true){
			if (e.getSource().equals(searchpayment)){
				for(int i = 0 ; i < outgoingDataModel.getRowCount(); i++){
					for(int j = 0; j < outgoingDataModel.getColumnCount(); j++){
						outgoingDataModel.setValueAt(new String(""), i, j);
					}
				}
				if((testfieldOne.getText().equals("") && testfieldTwo.getText().equals("")) && (testfieldThree.getText().equals("")&&testfieldFour.getText().equals(""))){
				//date search
					pu.loadData(outgoingDataModel, bean.connect().supplierInvoiceDate(pu.sqlDate(sh1.getGenericDate(date_from)), pu.sqlDate(sh2.getGenericDate(date_to))));
				}
				else if((testfieldThree.getText().equals("")&&testfieldFour.getText().equals(""))&&(!testfieldOne.getText().equals("") && !testfieldTwo.getText().equals(""))){
					//amount search and reset 
					pu.loadData(outgoingDataModel, bean.connect().supplierInvoiceAmount(new BigDecimal(testfieldOne.getText()), new BigDecimal(testfieldTwo.getText())));
				}
				else if ((testfieldOne.getText().equals("") && testfieldTwo.getText().equals(""))&&(testfieldFour.getText().equals("")&&!testfieldThree.getText().equals(""))){
					//supplier id search
					pu.loadData(outgoingDataModel, bean.connect().supplierInvoiceID((Integer.valueOf(testfieldThree.getText())).intValue()));
				}
				else if((testfieldOne.getText().equals("") && testfieldTwo.getText().equals("")) && (!testfieldFour.getText().equals("") && testfieldThree.getText().equals(""))){
						pu.loadData(outgoingDataModel, bean.connect().supplierInvoice(testfieldFour.getText()));
				}
			}
			if (e.getSource().equals(addpayment)){
				MaterialPayment ds = new MaterialPayment("MATERIAL PAYMENT", 
				getStringValue(0, outgoingDataModel,outgoingView), 
				getStringValue(2,outgoingDataModel,outgoingView), 
				getTableId(7,outgoingDataModel,outgoingView));
			}
	  
			if (e.getSource().equals(details)){
				MaterialPaymentDetails outgoing = new MaterialPaymentDetails("Supplier Ledger", this);
			}
			if (e.getSource().equals(edit)){
				invoice = new EditSupplierInvoice("EDIT SUPPLIER INVOICE", 
				getTableId(7,outgoingDataModel,outgoingView));
			}
		}
		else{
			if(e.getSource().equals(addpayment)){
				new SubContractorPayment("SUB-CONTRACTOR INVOICE PAYMENT", this);
			}
			if (e.getSource().equals(searchpayment)){
				for(int i = 0 ; i < outgoingSubDataModel.getRowCount(); i++){
					for(int j = 0; j < outgoingSubDataModel.getColumnCount(); j++){
						outgoingSubDataModel.setValueAt(new String(""), i, j);
					}
				}
				if((testfieldOne.getText().equals("") && testfieldTwo.getText().equals("")) && (testfieldThree.getText().equals("")&&testfieldFour.getText().equals(""))){
				//date search
					pu.loadData(outgoingSubDataModel, bean.connect().getLabourOutgoingData(pu.sqlDate(sh1.getGenericDate(date_from)), pu.sqlDate(sh2.getGenericDate(date_to))));
				}
				else if((testfieldThree.getText().equals("")&&testfieldFour.getText().equals(""))&&(!testfieldOne.getText().equals("") && !testfieldTwo.getText().equals(""))){
					//amount search and reset 
					pu.loadData(outgoingSubDataModel, bean.connect().getLabourOutgoingData(new BigDecimal(testfieldOne.getText()), new BigDecimal(testfieldTwo.getText())));
				}
				else if ((testfieldOne.getText().equals("") && testfieldTwo.getText().equals(""))&&(testfieldFour.getText().equals("")&&!testfieldThree.getText().equals(""))){
					//supplier id search
					pu.loadData(outgoingSubDataModel, bean.connect().getLabourOutgoingData((Integer.valueOf(testfieldThree.getText())).intValue()));
				}
				else if((testfieldOne.getText().equals("") && testfieldTwo.getText().equals("")) && (!testfieldFour.getText().equals("") && testfieldThree.getText().equals(""))){
						pu.loadData(outgoingSubDataModel, bean.connect().getLabourOutgoingData(testfieldFour.getText()));
				}
			}
			if (e.getSource().equals(details)){
				 new SubPaymentDetails("Sub-Contractor Ledger", this);
			}
		}

	  
    }

}