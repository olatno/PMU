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
import java.math.BigDecimal;
import scr.protool.client.utilities.*;
import scr.protool.client.miscelleneous.BeanStud;
import javax.naming.*;


public class BatchPayment extends  JFrame implements ActionListener, ItemListener, FocusListener{

    Container con = getContentPane();
    private JPanel headerpanel;
    private JPanel contentpanel;
	private JPanel footerpanel;
    private JPanel mainpanel;
	private JPanel ledgerButtonPanel;//might be needed
    private JButton submit;
	private JButton dispose_exit;
	private JButton refresh;
	private JLabel hidden;
	private JLabel invoiceId;
	private JSpinner date;
	private JTextField totalLabels;	
    private JTextField allocation;
	private JRadioButton cash;
	private JRadioButton creditcard;
	private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int ipadiyb = 2;
    private final int ipadixb = 4;
    private final double weightix = 1.0;
	private final double weightiy = 1.0;
	private final int textFieldColunm = 1;
	private JTable ledgerView; 
    private DefaultTableModel ledgerDataModel; 
    private JScrollPane ledgerViewScroll; 
	private JScrollPane buttonPanel;
	private SpinnerHelper sh;
	private BeanStud bean;
	private ArrayList<JTextField> invoice = new  ArrayList<JTextField>();
	private ArrayList<JTextField> amount = new  ArrayList<JTextField>();
	private ArrayList<JTextField> allotment = new  ArrayList<JTextField>();
	private ArrayList<JTextField> info = new  ArrayList<JTextField>();
	private ArrayList<JCheckBox> select = new  ArrayList<JCheckBox>();
	private PaymentInUtilities pu = new PaymentInUtilities();
	private ArrayList<ArrayList<Object>> supplierInvoice = null;
	private ArrayList<JLabel> hiddenInvoiceId = new ArrayList<JLabel>();
	private Utilities ul = new Utilities();
	private int supplierId;
	private String [] radioButton = new String[2];
	private Hashtable<String, Integer> accname = null;

    public BatchPayment(String title, int supplierId) {
	super(title);
	this.supplierId = supplierId;
	bean = new BeanStud();
	supplierInvoice = pu.getArrayListOfArrayList(bean.connect().supplierBatchInvoice(supplierId));
	headerpanel = new JPanel();
	contentpanel = new JPanel();
	footerpanel = new JPanel();
	mainpanel = new JPanel();
	ledgerButtonPanel = new JPanel();
	sh = new SpinnerHelper();

	contentpanel.setLayout(new GridBagLayout());
	headerpanel.setLayout(new GridBagLayout());
	footerpanel.setLayout(new GridBagLayout());
	mainpanel.setLayout(new BorderLayout());

	
	GridBagConstraints descriLabel = new GridBagConstraints();
	descriLabel.gridx = 0;
	descriLabel.gridy = 0;
	descriLabel.insets = new Insets(20, 0, 20, 0);
	descriLabel.anchor =  GridBagConstraints.WEST;
	contentpanel.add(new JLabel("Amount:"), descriLabel);
	
	GridBagConstraints amountLabel = new GridBagConstraints();
	allocation = new JTextField(textFieldColunm);
	amountLabel.gridx = 1;
	amountLabel.gridy = 0;
	amountLabel.ipadx = 90;
	amountLabel.ipady = 2;
	amountLabel.insets = new Insets(20, 0, 20, 0);
    contentpanel.add(allocation, amountLabel);

	
	GridBagConstraints hiddenLabel = new GridBagConstraints();
	hidden = new JLabel();
	hiddenLabel.gridx = 2;
	hiddenLabel.gridy = 0;
	hiddenLabel.ipady = 2;
	hiddenLabel.insets = new Insets(20, 0, 20, 0);
    contentpanel.add(hidden, hiddenLabel);
	hidden.setVisible(false);
	
	GridBagConstraints dateLabel = new GridBagConstraints();
	dateLabel.gridx = 0;
	dateLabel.gridy = 1;
	dateLabel.insets = new Insets(0, 0, 20, 0);
	dateLabel.gridx = GridBagConstraints.RELATIVE;
    contentpanel.add(new JLabel("Payment Date:"), dateLabel);
	
	GridBagConstraints date1TextField = new GridBagConstraints();
	date = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 1;
	date1TextField.ipady = 2;
	date1TextField.insets = new Insets(0, 0, 20, 0);

	contentpanel.add(sh.getGenericDate(date), date1TextField);
	
	GridBagConstraints howLabel = new GridBagConstraints();
	howLabel.gridx = 0;
	howLabel.gridy = 2;
	howLabel.insets = new Insets(0, 0, 20, 0);
	contentpanel.add(new JLabel("Payment Type:"), howLabel);
	accname = pu.getAccountCodeName(bean.connect().HashTableMaterialPosting());

	int index = 0;
	for(Enumeration<String> enu = accname.keys(); enu.hasMoreElements();){
		radioButton[index] = enu.nextElement();
		index++;
	}
	
	GridBagConstraints howButton = new GridBagConstraints();
	cash = new JRadioButton(radioButton[1]);
	howButton.gridx = 1;
	howButton.gridy = 2;
	howButton.insets = new Insets(0, 0, 20, 0);
	contentpanel.add(cash, howButton);
	cash.setSelected(true);
	
	GridBagConstraints howButton1 = new GridBagConstraints();
	creditcard = new JRadioButton(radioButton[0]);
	howButton1.gridy = 2;
	howButton1.gridx = GridBagConstraints.RELATIVE;
	howButton1.insets = new Insets(0, 0, 20, 0);
	howButton1.anchor =  GridBagConstraints.WEST;
	contentpanel.add(creditcard, howButton1);
	creditcard.setSelected(true);
	ButtonGroup groups = new ButtonGroup();
    groups.add(cash);
    groups.add(creditcard);
	
	GridBagConstraints separator = new GridBagConstraints();
	JSeparator separate = new JSeparator(SwingConstants.HORIZONTAL);
	separator.gridx = 0;
	separator.gridy = 3;
	separator.weightx = 1.0;
	separator.fill = GridBagConstraints.HORIZONTAL;
	separator.gridwidth=5;
	separator.insets = new Insets(0, 0, 5, 0);
	separate.setPreferredSize(new Dimension(600, 5));
	contentpanel.add(separate, separator);

	
	GridBagConstraints selectLabel = new GridBagConstraints();
	selectLabel.gridx = 0;
	selectLabel.gridy = 4;
	selectLabel.anchor =  GridBagConstraints.WEST;
	selectLabel.insets = new Insets(0, 0, 5, 0);
    contentpanel.add(new JLabel("Select:"), selectLabel);
	
	GridBagConstraints invoiceLabel = new GridBagConstraints();
	invoiceLabel.gridx = 1;
	invoiceLabel.gridy = 4;
	invoiceLabel.insets = new Insets(0, 0, 5, 0);
    contentpanel.add(new JLabel("Invoice Number:"), invoiceLabel);
	
	GridBagConstraints priLabel = new GridBagConstraints();
	priLabel.gridx = 2;
	priLabel.gridy = 4;
	priLabel.anchor =  GridBagConstraints.EAST;
	priLabel.insets = new Insets(0, 120, 5, 0);
    contentpanel.add(new JLabel("Amount:"), priLabel);
	
		
	GridBagConstraints allocationLabel = new GridBagConstraints();
	allocationLabel.gridx = 3;
	allocationLabel.gridy = 4;
	allocationLabel.anchor =  GridBagConstraints.LINE_START;
	//allocationLabel.anchor =  GridBagConstraints.WEST;
	allocationLabel.insets = new Insets(0, 50, 5, 0);
    contentpanel.add(new JLabel("Allocation:"), allocationLabel);
	
	GridBagConstraints infoLabel = new GridBagConstraints();
	infoLabel.gridx = 4;
	infoLabel.gridy = 4;
	infoLabel.weightx = 1.0;
	infoLabel.anchor =  GridBagConstraints.LINE_START;
	//infoLabel.anchor =  GridBagConstraints.WEST;
	infoLabel.insets = new Insets(0, 60, 5, 0);
    contentpanel.add(new JLabel("Note:"), infoLabel);
	
	for(int j = 0; j < supplierInvoice.size(); j++){
		makeTextField(j);
	}
	
	GridBagConstraints refreshBatchPayment = new GridBagConstraints();
	refresh = new JButton("REFRESH");
	refreshBatchPayment.gridx = 0;
	refreshBatchPayment.gridy = 0;
	refreshBatchPayment.insets = new Insets(0, 20, 0, 20);
	footerpanel.add(refresh, refreshBatchPayment);
	
	GridBagConstraints submitBatchPayment = new GridBagConstraints();
	submit = new JButton("SUBMIT");
	submitBatchPayment.gridx = 1;
	submitBatchPayment.gridy = 0;
	submitBatchPayment.insets = new Insets(0, 20, 0, 20);
	footerpanel.add(submit, submitBatchPayment);
	
	
	dispose_exit = new JButton("EXIT");
	GridBagConstraints exitBatchPayment = new GridBagConstraints();
	exitBatchPayment.gridx = 2;
	exitBatchPayment.gridy = 0;
	footerpanel.add(dispose_exit, exitBatchPayment);
	
	mainpanel.add(headerpanel, BorderLayout.NORTH);
	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	ledgerViewScroll = new JScrollPane(mainpanel, v,h);
	ledgerViewScroll.setColumnHeaderView( contentpanel);
	ledgerViewScroll.setBorder(new Partition(20,"AddPaymentout"));

	con.add(ledgerViewScroll);
	con.add(footerpanel, "South");
	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(650, 600);
	setVisible(true);
	setResizable(false);
	

		for(int i = 0; i < select.size(); i++){
		
			select.get(i).addItemListener(this);
			allotment.get(i).addFocusListener(this);
		}
		dispose_exit.addActionListener(this);
		submit.addActionListener(this);
		refresh.addActionListener(this);		
		allocation.addFocusListener(this);
    }
	
	public void makeTextField(int row){
	
		GridBagConstraints selectCheck = new GridBagConstraints();
		JCheckBox check = new JCheckBox();
		selectCheck.gridx = 0;
		selectCheck.gridy = row;
		selectCheck.anchor =  GridBagConstraints.WEST;
		selectCheck.weightx = weightix;
		selectCheck.insets = new Insets(0, 0, 10, 0);
		headerpanel.add(check, selectCheck);
		select.add(check);
		
		GridBagConstraints typeTextField1 = new GridBagConstraints();
		JTextField invoicenumber = new JTextField(textFieldColunm);
		typeTextField1.gridx = 1;
		typeTextField1.gridy = row;
		typeTextField1.ipady = ipadiy;
		typeTextField1.ipadx = 150;
		typeTextField1.weightx = weightix;
		typeTextField1.insets = new Insets(0, 0, 10, 40);
		typeTextField1.anchor =  GridBagConstraints.LINE_END;
		headerpanel.add(invoicenumber, typeTextField1);
		invoicenumber.setEditable(false);
		if(row < supplierInvoice.size()){
			invoicenumber.setText(String.valueOf(supplierInvoice.get(row).get(0)));
		}
		invoice.add(invoicenumber);
		
		GridBagConstraints typeTextField2 = new GridBagConstraints();
		JTextField invoiceamount = new JTextField(textFieldColunm);
		typeTextField2.gridx = 2;
		typeTextField2.gridy = row;
		typeTextField2.ipady = ipadiy;
		typeTextField2.ipadx = 60;
		typeTextField2.weightx = weightix;
		typeTextField2.anchor =  GridBagConstraints.WEST;
		typeTextField2.insets = new Insets(0, 0, 10, 0);
		typeTextField2.anchor =  GridBagConstraints.WEST;
		headerpanel.add(invoiceamount, typeTextField2);
		invoiceamount.setEditable(false);
		if(row < supplierInvoice.size()){
			invoiceamount.setText(String.valueOf(supplierInvoice.get(row).get(1)));
		}
		amount.add(invoiceamount);
		
		GridBagConstraints typeTextField3 = new GridBagConstraints();
		JTextField allocatedamount = new JTextField(textFieldColunm);
		typeTextField3.gridx = 3;
		typeTextField3.gridy = row;
		typeTextField3.ipady = ipadiy;
		typeTextField3.weightx = weightix;
		typeTextField3.ipadx = 60;
		typeTextField3.insets = new Insets(0, 0, 10, 0);
		typeTextField3.anchor =  GridBagConstraints.WEST;
		headerpanel.add(allocatedamount, typeTextField3);
		allocatedamount.setEditable(false);
		allotment.add(allocatedamount);
		
		GridBagConstraints typeTextField4 = new GridBagConstraints();
		JTextField note = new JTextField(textFieldColunm);
		typeTextField4.gridx = 4;
		typeTextField4.gridy = row;
		typeTextField4.ipady = ipadiy;
		typeTextField4.weightx = weightix;
		typeTextField4.ipadx = 80;
		typeTextField4.insets = new Insets(0, 0, 10, 0);
		typeTextField4.anchor =  GridBagConstraints.WEST;
		headerpanel.add(note, typeTextField4);
		note.setEditable(false);
		info.add(note);
	
		GridBagConstraints invoiceLabels = new GridBagConstraints();
		invoiceId = new JLabel();
		invoiceLabels.gridx = 5;
		invoiceLabels.gridy = row;
		invoiceLabels.insets = new Insets(0, 0, 10, 0);
		invoiceLabels.weightx = weightix;
		headerpanel.add(invoiceId, invoiceLabels);
		invoiceId.setVisible(false);
		if(row < supplierInvoice.size()){
			invoiceId.setText(String.valueOf(supplierInvoice.get(row).get(2)));
		}
		hiddenInvoiceId.add(invoiceId);
	}
	
	public int getSupplierId(){
		return supplierId;
	}

	public void focusLost(FocusEvent e){
		BigDecimal allocation_amount =  BigDecimal.ZERO;
		BigDecimal total =  BigDecimal.ZERO;

		if(e.getSource().equals(allocation)){
			if(!allocation.getText().isEmpty()){
				allocation.setEditable(false);
				hidden.setText(allocation.getText());
				 allocation_amount = new BigDecimal(hidden.getText());
				for(int i = 0; i < allotment.size(); i++){
					if(!allotment.get(i).getText().isEmpty() && select.get(i).isSelected()){
						total = total.add(new BigDecimal(allotment.get(i).getText()));					
					}
				}
				allocation.setText(String.valueOf(allocation_amount.subtract(total)));
			}
		}
		
		else{
		
			for(int i = 0; i < allotment.size(); i++){
				if(!allotment.get(i).getText().isEmpty() && select.get(i).isSelected()){
					total = total.add(new BigDecimal(allotment.get(i).getText()));
				}

			}
			if(!hidden.getText().isEmpty()){

				allocation.setText(String.valueOf(new BigDecimal(hidden.getText()).subtract(total)));
			}
		}
	
	}	public void focusGained(FocusEvent e){}
	

	
	public void itemStateChanged(ItemEvent e){
		int state = e.getStateChange();
		for(int i = 0; i < select.size(); i++){
			if (e.getItem().equals(select.get(i))){
				if(state == e.SELECTED){
					allotment.get(i).setEditable(true);
					info.get(i).setEditable(true);
				}
				else{
					 allotment.get(i).setEditable(false);
					 allotment.get(i).setText("");
					 allotment.get(i).setText(null);
					 info.get(i).setEditable(false);
					 info.get(i).setText("");
					 info.get(i).setText(null);
												 
				}
			
			}
		}
		BigDecimal total = BigDecimal.ZERO;
		for(int i = 0; i < allotment.size(); i++){

			if(!allotment.get(i).getText().isEmpty() && select.get(i).isSelected()){
				total = total.add(new BigDecimal(allotment.get(i).getText()));
			}

		}
		if(!hidden.getText().isEmpty()){

			allocation.setText(String.valueOf(new BigDecimal(hidden.getText()).subtract(total)));
		}
	}
	
	public void actionPerformed( ActionEvent e) { 
	
		if (e.getSource().equals(submit)){
			ArrayList<ArrayList<Object>> batchPayment =  new ArrayList<ArrayList<Object>>();
			ArrayList<Object> commonData = new ArrayList<Object>();
			for(int i = 0; i < select.size(); i++){
				ArrayList<Object> payment = new ArrayList<Object>();
				if(select.get(i).isSelected()){
					payment.add(Integer.valueOf(hiddenInvoiceId.get(i).getText()));
					payment.add(allotment.get(i).getText());
					payment.add(info.get(i).getText());
					
				}
				batchPayment.add(payment);			
			}
			if(cash.isSelected()){
				commonData.add(accname.get(radioButton[1]));
				commonData.add(radioButton[1]);
			}
			else{
				commonData.add(accname.get(radioButton[0]));
				commonData.add(radioButton[0]);
			}
				commonData.add(ul.usDate(sh.dateString(date)));
			bean.connect().addMaterialPayments(pu.writeArrayList(batchPayment), ul.writeArrayList(commonData)); 
			dispose();
		}
		if (e.getSource().equals(dispose_exit)){

			dispose();	
		}
		if (e.getSource().equals(refresh)){
			dispose();	
			new BatchPayment("Customer BatchPayment", getSupplierId());
		}
	}
    
}