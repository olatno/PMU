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
public class SubContractorView extends JPanel implements  ActionListener{// implements Serializable, ActionListener{

	private BeanStud bean;
    private JTextField balance_from;
    private JTextField balance_to;
    private JTextField supplier;
    private JTextField suppliername;
    private JButton addproject;
    private JButton searchproject;
	private  JTabbedPane tabbedPane;
	private JButton batch;
    private JButton invoice;
	private JButton detail;
    private JButton exitproject;
	private JLabel number;
	private JLabel name;
    private JPanel buttonpanel;
    private JPanel searchpanel;
    private JPanel tablepanel;
    private SpinnerHelper sh1;
	private SpinnerHelper sh2;
	private PaymentInUtilities pu;
	private Utilities ul;
    private JTable labourView; 	
	private DefaultTableModel labourDataModel;
    private JScrollPane labourViewScroll; 	
	private final String[]columnSubNames = {"BUSINESS NAME","SUB-CONTRACTOR NAME","REFERENCE"," OUTSTANDING", "PAYMENT TERM","DATE CREATED", ""};
    private final int initialRow = 18;
    private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int gab = 22;
	private final int textFieldColunm = 1;
    private final double weightix = 1.0;
	private JComponent supplierView;
	private JComponent directLabour;
	private boolean suppliersView = true;
	Dimension buttond = new Dimension(75, 18);
    
    public SubContractorView(String title){
   	super(new BorderLayout()); 
   
    bean = new BeanStud();
   //Panels
	buttonpanel = new JPanel();
	searchpanel = new JPanel();
	tablepanel = new JPanel();
	tabbedPane = new JTabbedPane();
	
	//utilities
	sh1 = new SpinnerHelper();
	sh2 = new SpinnerHelper();
	pu = new PaymentInUtilities();
	ul = new Utilities();
	
	//Panels layout
	buttonpanel.setLayout(new GridBagLayout());
	searchpanel.setLayout(new GridBagLayout());
	tablepanel.setLayout(new BorderLayout());
	
	GridBagConstraints idLabel = new GridBagConstraints();
	number = new JLabel();
	idLabel.gridx = 0;
	idLabel.gridy = 0;
	idLabel.weightx=0.4;
	idLabel.insets = new Insets(0, 0, gab, 0);
	idLabel.anchor =  GridBagConstraints.EAST;
	//searchpanel.add(new JLabel("ACCOUNT NUMBER:"), idLabel);
	searchpanel.add(number, idLabel);
	number.setText("REFERENCE:");

	GridBagConstraints idTextField = new GridBagConstraints();
	supplier = new JTextField(textFieldColunm);
	idTextField.gridx = 1;
	idTextField.gridy = 0;
	idTextField.ipadx = ipadix;
	idTextField.ipady = ipadiy;
	idTextField.insets = new Insets(0, 0, gab, 0);
	idTextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(supplier, idTextField);


	GridBagConstraints quote2Label = new GridBagConstraints();
	name = new JLabel();
	quote2Label.gridx = 2;
	quote2Label.gridy = 0;
	quote2Label.insets = new Insets(0, 0, gab, 0);
	quote2Label.anchor =  GridBagConstraints.EAST;
	//quote2Label.gridwidth = 2;
	searchpanel.add(name, quote2Label);
	//searchpanel.add(new JLabel("SUPPLIER NAME:"), quote2Label);
	name.setText("SUB-CONTRACTOR NAME:");

	GridBagConstraints accountnameTextField = new GridBagConstraints();
	suppliername = new JTextField(textFieldColunm);
	accountnameTextField.gridx = 3;
	accountnameTextField.gridy = 0;
	accountnameTextField.ipady = ipadiy;
	accountnameTextField.ipadx = ipadix;
	accountnameTextField.insets = new Insets(0, 0, gab, 0);
	accountnameTextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(suppliername, accountnameTextField);
	
	GridBagConstraints ac1Label = new GridBagConstraints();
	ac1Label.gridx = 0;
	ac1Label.gridy = 1;
	ac1Label.anchor =  GridBagConstraints.EAST;
	searchpanel.add(new JLabel("ACCOUNTS BAL FROM:"), ac1Label);

	GridBagConstraints ac1TextField = new GridBagConstraints();
	balance_from = new JTextField(textFieldColunm);
	ac1TextField.gridx = 1;
	ac1TextField.gridy = 1;
	ac1TextField.ipady = ipadiy;
	ac1TextField.ipadx = ipadix;
	ac1TextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(balance_from, ac1TextField);


	GridBagConstraints ac2Label = new GridBagConstraints();
	ac2Label.gridx = 2;
	ac2Label.gridy = 1;
	ac2Label.anchor =  GridBagConstraints.EAST;
	ac2Label.insets = new Insets(0, 40, 0, 0);
	searchpanel.add(new JLabel("ACCOUNTS BAL TO:"), ac2Label);

	GridBagConstraints ac2TextField = new GridBagConstraints();
	balance_to = new JTextField(textFieldColunm);
	ac2TextField.gridx = 3;
	ac2TextField.gridy = 1;
	ac2TextField.ipady = ipadiy;
	ac2TextField.ipadx = ipadix;
	ac2TextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(balance_to, ac2TextField);
	
	
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
	addproject = new JButton("<html><b><u>N</u>ew</b></html>");
	addButton.gridx = 0;
	addButton.gridy = 0;
	addButton.insets = new Insets(5, 0, 0, 30);
	buttonpanel.add(addproject, addButton);
	
	GridBagConstraints invoiceButton = new GridBagConstraints();
	invoice = new JButton("<html><b>Invoice</b></html>");
	invoiceButton.gridx = 1;
	invoiceButton.gridy = 0;
	invoiceButton.insets = new Insets(5, 0, 0, 0);
	buttonpanel.add(invoice, invoiceButton);

	GridBagConstraints detailsButton = new GridBagConstraints();
	detail = new JButton("<html><b>Details</b></html>");
	detailsButton.gridx = 3;
	detailsButton.gridy = 0;
	detailsButton.insets = new Insets(5, 0, 0, 0);
	buttonpanel.add(detail, detailsButton);

	GridBagConstraints batchButton = new GridBagConstraints();
	batch = new JButton("<html><b>Batch</b></html>");
	batchButton.gridx = 2;
	batchButton.gridy = 0;
	batchButton.insets = new Insets(5, 0, 0, 0);
	buttonpanel.add(batch, batchButton );
	
	Vector<String> columns = new Vector<String>(); 
	for(int tableHeader = 0; tableHeader < columnSubNames.length; tableHeader++) { 
		columns.addElement(columnSubNames[tableHeader]); 
	} 
	labourDataModel = new DefaultTableModel(columns, initialRow); 
	labourView = new JTable(labourDataModel);  
		
	int[]widthValue = {150,100,60,60,60,60};
	for(int i = 0; i<widthValue.length; i++){
		TableColumn col  = labourView.getColumnModel().getColumn(i);
		col.setPreferredWidth(widthValue[i]);
	}
	labourView.removeColumn(labourView.getColumnModel().getColumn(6));
	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	labourViewScroll = new JScrollPane(labourView, v,h); 
	tablepanel.add(labourViewScroll);
	labourViewScroll.setMinimumSize(new Dimension (720, 323));

	add("South", buttonpanel);
	add("North", searchpanel); 
	add("Center", tablepanel);

	addproject.addActionListener(this); 
	searchproject.addActionListener(this);
	detail.addActionListener(this);	
	invoice.addActionListener(this);
	batch.addActionListener(this);
	pu.loadData(labourDataModel,  bean.connect().getSubContractorData());

   }

	
	public void actionPerformed( ActionEvent e) {  
			if (e.getSource().equals(addproject)){
				new AddSubContractor("NEW SUB-CONTRACTOR");
			}
			if(e.getSource().equals(batch)){
				new BatchPayment("Invoice Batch Payment", ul.getTableInt(labourView,labourDataModel,6));
			}
			if(e.getSource().equals(invoice)){
	
				new AddSubContractorInvoice("SUB-CONTRACTOR INVOICE", ul.getTableInt(labourView,labourDataModel,6));
			}

			if(e.getSource().equals(detail)){

				new SubContractorDetails("Sub-contractor Details", ul.getTableInt(labourView,labourDataModel,6));
			}
			if (e.getSource().equals(searchproject)){ 
				for(int i = 0 ; i < labourDataModel.getRowCount(); i++){
					for(int j = 0; j < labourDataModel.getColumnCount(); j++){
						labourDataModel.setValueAt(new String(""), i, j);
					}
				}
				if(balance_from.getText().equals("")&&balance_to.getText().equals("")&&suppliername.getText().equals("")){
					pu.loadData(labourDataModel,  bean.connect().getSubContractorRef(supplier.getText()));
	 
				}
				else if(balance_from.getText().equals("")&&balance_to.getText().equals("")){
					supplier.setText("");
					pu.loadData(labourDataModel,  bean.connect().getSubContractorData(suppliername.getText()));
				}
	 
				else{
					supplier.setText("");
					suppliername.setText("");
					pu.loadData(labourDataModel,  bean.connect().getSubContractorData(new BigDecimal(balance_from.getText()) , new BigDecimal(balance_to.getText())));
				}
	 
			}
    }

}