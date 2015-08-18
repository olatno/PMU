package scr.protool.client.general;
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
public class GeneralExpensesView extends JPanel implements  ActionListener, ChangeListener{// implements Serializable, ActionListener{

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
    private JButton newExpenses;
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
    private JTable prepaidView; 
	private JTable payableView;
    private JTable accruedView; 
	private JTable paidView; 	
    private DefaultTableModel prepaidDataModel;
    private DefaultTableModel payableDataModel; 
    private DefaultTableModel accruedDataModel;
    private DefaultTableModel paidDataModel; 	
	private PaymentInUtilities pu;
    private JScrollPane prepaidViewScroll; 
	private JScrollPane payableViewScroll;
    private JScrollPane accruedViewScroll; 
	private JScrollPane paidViewScroll; 	
	private JComponent prepaidComponentView;
	private JComponent payableComponentView;
	private JComponent accruedComponentView;
	private JComponent paidComponentView;
	private  JTabbedPane tabbedPane;
	private boolean suppliersInvoice = true;
    private final String[]columnPrepaid = {"Supplier's Name","Invoice NO","Expenses Name","Invoice Date","Invoice Amount","Period","Allotment",""};
	private final String[]columnPayable = {"Supplier's Name","Invoice NO","Expenses Name","Invoice Date","Invoice Amount","Payment","Over Due",""};
	private final String[]columnAccrued = {"Supplier's Name","Expenses Name","Accrued Date","Accrued Amount","Invoice",""};
	private final String[]columnPaid = {"Expenses Type","Supplier's Name","Invoice NO","Expenses Name","Date","Amount",""};
    private final int initialRow = 16;
    private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int textFieldColunm = 1;
    private final double weightix = 1.0;
	private boolean prepaid, payable, accrued = false;
	private boolean paid = true;
	//private   EditSupplierInvoice invoice;
	private JLabel generic;
    
    public GeneralExpensesView(String title){
   	super(new BorderLayout()); 
   
	buttonpanel = new JPanel();
	searchpanel = new JPanel();
	tablepanel = new JPanel();
	tabbedPane = new JTabbedPane();
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

	
	GridBagConstraints newButton = new GridBagConstraints();
	newExpenses = new JButton("<html><b><u>N</u>ew</b></html>");
	newButton.gridx = 0;
	newButton .gridy = 0;
	newButton.insets = new Insets(5, 0, 0, 30);
	buttonpanel.add(newExpenses, newButton );
	
	GridBagConstraints addButton = new GridBagConstraints();
	addpayment = new JButton("<html><b>Payment</b></html>");
	addButton.gridx = 2;
	addButton.gridy = 0;
	addButton.insets = new Insets(5, 0, 0, 0);
	buttonpanel.add(addpayment, addButton);
	addpayment.setEnabled(false);
	
	GridBagConstraints detailsButton = new GridBagConstraints();
	details = new JButton("<html><b>Details</b></html>");
	detailsButton.gridx = 3;
	detailsButton.gridy = 0;
	detailsButton.insets = new Insets(5, 0, 0, 0);
	buttonpanel.add(details, detailsButton);

	
	paidComponentView = makeTableViewPaid(columnPaid);         
	tabbedPane.addTab("Processed Expenses", paidComponentView);
	
	prepaidComponentView = makeTableViewPrepaid(columnPrepaid);         
	tabbedPane.addTab("Prepaid Expenses", prepaidComponentView);
	
	payableComponentView = makeTableViewPayable(columnPayable);         
	tabbedPane.addTab("Payable Expenses", payableComponentView);
	
	accruedComponentView = makeTableViewAccrued(columnAccrued);         
	tabbedPane.addTab("Accrued Expenses", accruedComponentView);

	add("South", buttonpanel);
	add("North", searchpanel); 
	add("Center", tabbedPane);
	
	newExpenses.addActionListener(this);
	addpayment.addActionListener(this); 
	details.addActionListener(this); 
	searchpayment.addActionListener(this);
    tabbedPane.addChangeListener(this);
	//byte[] testarray = bean.connect().prepaidInfomations();
	//	pu.loadData(prepaidDataModel, bean.connect().prepaidInfomations());
    }
	
	
	public int getTableId(int index, DefaultTableModel dataModel, JTable view){
		int  maintablerow = view.getSelectedRow();
		return ((Integer)dataModel.getValueAt(maintablerow, index)).intValue();
	}
	
	
	public String getStringValue(int index, DefaultTableModel dataModel, JTable view){
		int  maintablerow = view.getSelectedRow();
		return (String)dataModel.getValueAt(maintablerow, index);
	}
	
	
	public DefaultTableModel getModelPrepaid(){
		return prepaidDataModel;
	
	}
	
	public JTable getTablePrepaid(){
	   return prepaidView;
	}
	
	public DefaultTableModel getModelPayable(){
		return payableDataModel;
	
	}
	
	public JTable getTablePayable(){
	   return payableView;
	}
	
	public DefaultTableModel getModelAccrued(){
		return accruedDataModel;
	
	}
	
	public JTable getTableAccrued(){
	   return accruedView;
	}
	
	public DefaultTableModel getModelPaid(){
		return paidDataModel;
	
	}
	
	public JTable getTablePaid(){
	   return paidView;
	}
	
	public JComponent makeTableViewPrepaid(String[] column){
	
		Vector<String> columns = new Vector<String>(); 
		for(int tableHeader = 0; tableHeader < column.length; tableHeader++) { 
			columns.addElement(column[tableHeader]); 
		} 
		prepaidDataModel = new DefaultTableModel(columns, initialRow); 
		prepaidView = new JTable(prepaidDataModel);  
 
		int[]widthValue = {150,200,200,150,175,150, 100};

		for(int i = 0; i<widthValue.length; i++){
			TableColumn col  = prepaidView.getColumnModel().getColumn(i);
			col.setPreferredWidth(widthValue[i]);
		}
		prepaidView.removeColumn(prepaidView.getColumnModel().getColumn(7));
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
		prepaidViewScroll = new JScrollPane(prepaidView, v,h); 
		tablepanel.add(prepaidViewScroll);
		prepaidViewScroll.setMinimumSize(new Dimension (715, 295));
		pu.loadData(prepaidDataModel, bean.connect().prepaidInfomations(), initialRow);
		return prepaidViewScroll;
	}

	public JComponent makeTableViewPayable(String[] column){
		
		Vector<String> columns = new Vector<String>(); 
		for(int tableHeader = 0; tableHeader < column.length; tableHeader++) { 
			columns.addElement(column[tableHeader]); 
		} 
		payableDataModel = new DefaultTableModel(columns, initialRow); 
		payableView = new JTable(payableDataModel);  
 
		int[]widthValue = {150,200,200,150,175,150, 100};

		for(int i = 0; i<widthValue.length; i++){
			TableColumn col  = payableView.getColumnModel().getColumn(i);
			col.setPreferredWidth(widthValue[i]);
		}
		payableView.removeColumn(payableView.getColumnModel().getColumn(7));
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
		payableViewScroll = new JScrollPane(payableView, v,h); 
		tablepanel.add(payableViewScroll);
		payableViewScroll.setMinimumSize(new Dimension (715, 295));
		pu.loadData(payableDataModel, bean.connect().payableInvoicesSearch(), initialRow);
		return payableViewScroll;
	}

	public JComponent makeTableViewAccrued(String[] column){

		Vector<String> columns = new Vector<String>(); 
		for(int tableHeader = 0; tableHeader < column.length; tableHeader++) { 
			columns.addElement(column[tableHeader]); 
		} 
		accruedDataModel = new DefaultTableModel(columns, initialRow); 
		accruedView = new JTable(accruedDataModel);  
 
		int[]widthValue = {150,200,200,150,175};

		for(int i = 0; i<widthValue.length; i++){
			TableColumn col  = accruedView.getColumnModel().getColumn(i);
			col.setPreferredWidth(widthValue[i]);
		}
		accruedView.removeColumn(accruedView.getColumnModel().getColumn(5));
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
		accruedViewScroll = new JScrollPane(accruedView, v,h); 
		tablepanel.add(accruedViewScroll);
		accruedViewScroll.setMinimumSize(new Dimension (715, 295));
		pu.loadData(accruedDataModel, bean.connect().accruedInvoicesSearch(), initialRow);
		return accruedViewScroll;
	}

	public JComponent makeTableViewPaid(String[] column){
		
		Vector<String> columns = new Vector<String>(); 
		for(int tableHeader = 0; tableHeader < column.length; tableHeader++) { 
			columns.addElement(column[tableHeader]); 
		} 
		paidDataModel = new DefaultTableModel(columns, initialRow); 
		paidView = new JTable(paidDataModel);  
 
		int[]widthValue = {135,200,150,150,140,100};

		for(int i = 0; i<widthValue.length; i++){
			TableColumn col  = paidView.getColumnModel().getColumn(i);
			col.setPreferredWidth(widthValue[i]);
		}
		paidView.removeColumn(paidView.getColumnModel().getColumn(6));
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
		paidViewScroll = new JScrollPane(paidView, v,h); 
		tablepanel.add(paidViewScroll);
		paidViewScroll.setMinimumSize(new Dimension (715, 295));
		pu.loadData(paidDataModel, bean.connect().expensesResolutions(), initialRow);
		return paidViewScroll;
	}	
	
	/*public String getAmount(){
		int  maintablerow = outgoingView.getSelectedRow();
		return (String)outgoingDataModel.getValueAt(maintablerow, 4);
	}
	
	public String getDate(){
		int  maintablerow = outgoingView.getSelectedRow();
		return (String)outgoingDataModel.getValueAt(maintablerow, 3);
	}*/
	public void stateChanged(ChangeEvent e){
		JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
		if(sourceTabbedPane.getSelectedComponent().equals(prepaidComponentView)){
			addpayment.setEnabled(false);
			prepaid = true;
			paid = false; payable = false; accrued = false;
		}
		if(sourceTabbedPane.getSelectedComponent().equals(payableComponentView)){
			addpayment.setEnabled(true);
			payable = true;	
			paid = false; prepaid = false; accrued = false;
		}
		
		if(sourceTabbedPane.getSelectedComponent().equals(accruedComponentView)){
			addpayment.setEnabled(true);
			accrued = true;
			paid = false; prepaid = false; payable = false;
		}
	
		if(sourceTabbedPane.getSelectedComponent().equals(paidComponentView)){
			addpayment.setEnabled(false);
			paid = true;
			accrued = false; prepaid = false; payable = false;
		}
	}
	
	public void actionPerformed( ActionEvent e) { 
		if(e.getSource().equals(newExpenses)){
			new GeneralExpenses("New General Expenses");
		
		}
		if (e.getSource().equals(searchpayment)){

			if((testfieldOne.getText().equals("") && testfieldTwo.getText().equals("")) && (testfieldThree.getText().equals("")&&testfieldFour.getText().equals(""))){
				//date search
					if(prepaid){
						pu.loadData(prepaidDataModel, bean.connect().prepaidInfomations(pu.sqlDate(sh1.getGenericDate(date_from)), pu.sqlDate(sh2.getGenericDate(date_to))), initialRow);
					}
					else if(payable){
						pu.loadData(payableDataModel, bean.connect().payableInvoicesSearch(pu.sqlDate(sh1.getGenericDate(date_from)), pu.sqlDate(sh2.getGenericDate(date_to))), initialRow);
					}
					
					else if(accrued){
						pu.loadData(accruedDataModel, bean.connect().accruedInvoicesSearch(pu.sqlDate(sh1.getGenericDate(date_from)), pu.sqlDate(sh2.getGenericDate(date_to))), initialRow);
					}
					
					else{
		
						pu.loadData(paidDataModel, bean.connect().expensesResolutions(pu.sqlDateString(sh1.getGenericDate(date_from)), pu.sqlDateString(sh2.getGenericDate(date_to))), initialRow);
					}
				}
			else if((testfieldThree.getText().equals("")&&testfieldFour.getText().equals(""))&&(!testfieldOne.getText().equals("") && !testfieldTwo.getText().equals(""))){
					//amount search and reset 
					
					if(prepaid){
						pu.loadData(prepaidDataModel, bean.connect().prepaidInfomations(pu.sqlDate(sh1.getGenericDate(date_from)), pu.sqlDate(sh2.getGenericDate(date_to)), new BigDecimal(testfieldOne.getText()), new BigDecimal(testfieldTwo.getText())), initialRow);
					}
					else if(payable){
						pu.loadData(payableDataModel, bean.connect().payableInvoicesSearch(pu.sqlDate(sh1.getGenericDate(date_from)), pu.sqlDate(sh2.getGenericDate(date_to)), new BigDecimal(testfieldOne.getText()), new BigDecimal(testfieldTwo.getText())), initialRow);
					}
					
					else if(accrued){
						pu.loadData(accruedDataModel, bean.connect().accruedInvoicesSearch(pu.sqlDate(sh1.getGenericDate(date_from)), pu.sqlDate(sh2.getGenericDate(date_to)), new BigDecimal(testfieldOne.getText()), new BigDecimal(testfieldTwo.getText())), initialRow);
					}
					
					else{
					
						pu.loadData(paidDataModel, bean.connect().expensesResolutions(pu.sqlDateString(sh1.getGenericDate(date_from)), pu.sqlDateString(sh2.getGenericDate(date_to)), new BigDecimal(testfieldOne.getText()), new BigDecimal(testfieldTwo.getText())), initialRow);
					}
				}
			else if ((testfieldOne.getText().equals("") && testfieldTwo.getText().equals(""))&&(testfieldFour.getText().equals("")&&!testfieldThree.getText().equals(""))){
					//supplier id search

					if(prepaid){
						pu.loadData(prepaidDataModel, bean.connect().prepaidInfomations(pu.sqlDate(sh1.getGenericDate(date_from)), pu.sqlDate(sh2.getGenericDate(date_to)), (Integer.valueOf(testfieldThree.getText())).intValue()), initialRow);
					}
					else if(payable){
						pu.loadData(payableDataModel, bean.connect().payableInvoicesSearch(pu.sqlDate(sh1.getGenericDate(date_from)), pu.sqlDate(sh2.getGenericDate(date_to)), (Integer.valueOf(testfieldThree.getText())).intValue()), initialRow);
					}
					
					else if(accrued){
						pu.loadData(accruedDataModel, bean.connect().accruedInvoicesSearch(pu.sqlDate(sh1.getGenericDate(date_from)), pu.sqlDate(sh2.getGenericDate(date_to)), (Integer.valueOf(testfieldThree.getText())).intValue()), initialRow);
					}
					
					else{
					
						pu.loadData(paidDataModel, bean.connect().expensesResolutions(pu.sqlDateString(sh1.getGenericDate(date_from)), pu.sqlDateString(sh2.getGenericDate(date_to)), (Integer.valueOf(testfieldThree.getText())).intValue()), initialRow);
					}
				}
			else if((testfieldOne.getText().equals("") && testfieldTwo.getText().equals("")) && (!testfieldFour.getText().equals("") && testfieldThree.getText().equals(""))){
					if(prepaid){
						pu.loadData(prepaidDataModel, bean.connect().prepaidInfomations(pu.sqlDate(sh1.getGenericDate(date_from)), pu.sqlDate(sh2.getGenericDate(date_to)), testfieldFour.getText()), initialRow);
					}
					else if(payable){
						pu.loadData(payableDataModel, bean.connect().payableInvoicesSearch(pu.sqlDate(sh1.getGenericDate(date_from)), pu.sqlDate(sh2.getGenericDate(date_to)), testfieldFour.getText()), initialRow);
					}
					
					else if(accrued){
						pu.loadData(accruedDataModel, bean.connect().accruedInvoicesSearch(pu.sqlDate(sh1.getGenericDate(date_from)), pu.sqlDate(sh2.getGenericDate(date_to)), testfieldFour.getText()), initialRow);
					}
					
					else{
					
						pu.loadData(paidDataModel, bean.connect().expensesResolutions(pu.sqlDateString(sh1.getGenericDate(date_from)), pu.sqlDateString(sh2.getGenericDate(date_to)), testfieldFour.getText()), initialRow);
					}
				}
		}

	  
		if (e.getSource().equals(details) && prepaid == true){
			byte[] prepaidResult = bean.connect().allotmentInfoDetails(getTableId(7,prepaidDataModel, prepaidView));
			byte[] prepaidBalance =  bean.connect().prepaidPaymentBalance(getTableId(7,prepaidDataModel, prepaidView));
			new ExpensesPaymentDetails("Prepaid Expenses Ledger",prepaidResult, prepaidBalance, this, prepaidDataModel, prepaidView, 7, 3, 4, 1);


		}
		if (e.getSource().equals(details) && payable == true){
			byte[] payableResult = bean.connect().getExpensesPayableDetails(getTableId(7,payableDataModel, payableView));
			byte[] payableBalance = bean.connect().getPayableBalance(Integer.valueOf(getTableId(7,payableDataModel, payableView)));
			 new ExpensesPaymentDetails("Payable Expenses Ledger", payableResult, payableBalance, this, payableDataModel, payableView, 7, 3, 4, 1);
		}
		if (e.getSource().equals(details) && accrued == true){
			byte[] accruedResult =  bean.connect().getAccruedDetails(getTableId(5, accruedDataModel, accruedView));
			new ExpensesAccruedDetails("Accrued Expenses Ledger",accruedResult, this);
		}
		if (e.getSource().equals(details) && paid == true){
			int genericid = getTableId(6, paidDataModel, paidView);
			if(((String)getStringValue(0, paidDataModel, paidView)).equals("Payable")){

				byte[] payableResolute = bean.connect().payableExpensesResolutionDetails(genericid);
				new ExpensesPaidDetails("Processed Payable Details", payableResolute, this);
			}
			else if(((String)getStringValue(0, paidDataModel, paidView)).equals("Paid")){

				byte[] paidResolute = bean.connect().paidExpensesResolutionDetails(genericid);
				new ExpensesPaidDetails("Processed Paid Details", paidResolute, this);
			}
			else if(((String)getStringValue(0, paidDataModel, paidView)).equals("Accrued")){

				byte[] accruedResolute = bean.connect().accruedExpensesResolutionDetails(genericid);
				new ExpensesPaidDetails("Processed Accrued Details", accruedResolute, this);
			}
			else if(((String)getStringValue(0, paidDataModel, paidView)).equals("Prepaid")){

				byte[] prepaidResolute = bean.connect().prepaidExpensesResolutionDetails(genericid);
				new ExpensesPaidDetails("Processed Prepaid Details", prepaidResolute, this);
			}

		}
		
		if(e.getSource().equals(addpayment) && payable == true){
			String supplier = getStringValue(0, payableDataModel, payableView);
			String invoice = getStringValue(1, payableDataModel, payableView);
			int id = getTableId(7, payableDataModel, payableView);
			new PayableExpenseslPayment("Payable Expenses Payment", supplier, invoice, id);
		}
		
		if(e.getSource().equals(addpayment) && accrued == true){
			new AccruedExpensesPayment("Accrued Expenses Process", this);
		}

    }

}