
package scr.protool.client.general;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.text.*;
import javax.naming.*;
import java.util.*;
import java.io.*;
import scr.protool.client.supplier.AddSupplier;
import scr.protool.client.utilities.*;
import scr.protool.client.miscelleneous.BeanStud;


/**

*
*

*
* @author <a href="mailto:"></a>
* @version 1.0
*/
public class AccruedExpensesPayment extends JFrame implements ActionListener{//implements ActionListener{

    private JTextField name;
	private JTextField number;
    private JTextField invoiceOut;
    private JTextField amount;
    private Choice group;
	private Choice vat;
    private JSpinner date;
    private JTextField description;
	private JTextField include;
	private JTextField supplierName;
	private JButton supplierButton;
	private JRadioButton exclude; 
    private JButton save;
    private JButton ok;
    private JButton exit;
    private JPanel addpanel;
    private JPanel buttonpanel;
    private JPanel generalPanel;
    private Context ctx;
	private SpinnerHelper sh;
    private String client = "";
    private BeanStud bean;
	private Integer supplierIds ;
	private Utilities ul = new Utilities();
	private PaymentInUtilities pu =  new PaymentInUtilities();
	private final double weightye = 0.5;
	private GeneralExpensesView expenses;
	private  final int textfieldrow = 1;
	private int accruedId;
	private Hashtable<String, Integer> accname = null;
    Container con = getContentPane();
    
    public AccruedExpensesPayment(String title, GeneralExpensesView expenses){//, int proId
   	super(title); 

	this.expenses = expenses;
	accruedId = expenses.getTableId(5, expenses.getModelAccrued(),
				expenses.getTableAccrued());
	addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();
	sh = new SpinnerHelper();
	bean = new BeanStud();
	
	//defaultstr = ul.getArrayListStr(bean.connect().defaultSupplier());
	//Panels
	addpanel.setLayout(new GridBagLayout());
	buttonpanel.setLayout(new GridBagLayout());
	generalPanel.setLayout(new BorderLayout());

	//Textfields u
	GridBagConstraints supplierLabel = new GridBagConstraints();
	supplierButton = new JButton("Supplier");
	supplierLabel.gridx = 0;
	supplierLabel.gridy = 0;
	supplierLabel.anchor =  GridBagConstraints.EAST;
   	supplierLabel.weighty = weightye;
	addpanel.add(supplierButton, supplierLabel);

	GridBagConstraints supplierTextField = new GridBagConstraints();
	supplierName = new JTextField(textfieldrow);
	supplierTextField.gridx = 1;
	supplierTextField.gridy = 0;
	supplierTextField.ipady = 2;
	supplierTextField.ipadx = 110;
	supplierTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(supplierName, supplierTextField);
	supplierName.setEditable(false);

	supplierName.setText(expenses.getStringValue(0, expenses.getModelAccrued(),
				expenses.getTableAccrued()));
	
	GridBagConstraints nameLabel = new GridBagConstraints();
	nameLabel.gridx = 0;
	nameLabel.gridy = 1;
	nameLabel.anchor =  GridBagConstraints.EAST;
   	nameLabel.weighty = weightye;
	addpanel.add(new JLabel("Accounts Name:"), nameLabel);

	GridBagConstraints nameTextField = new GridBagConstraints();
	name = new JTextField(textfieldrow);
	nameTextField.gridx = 1;
	nameTextField.gridy = 1;
	nameTextField.ipady = 2;
	nameTextField.ipadx = 110;
	nameTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(name, nameTextField);
	name.setText(expenses.getStringValue(1, expenses.getModelAccrued(),
				expenses.getTableAccrued()));
	name.setEditable(false);

	GridBagConstraints numberLabel = new GridBagConstraints();
	numberLabel.gridx = 0;
	numberLabel.gridy = 2;
	numberLabel.weighty = weightye;
	numberLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Invoice Number:"), numberLabel);

	GridBagConstraints numberTextField = new GridBagConstraints();
    number = new JTextField(textfieldrow);
	numberTextField.gridx = 1;
	numberTextField.gridy = 2;
	numberTextField.ipady = 2;
	numberTextField.ipadx = 115;
	numberTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(number, numberTextField);
	
	GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 3;
	date1Label.weighty = weightye;
    date1Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Invoice Date:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	date = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 3;
	date1TextField.ipady = 2;
	date1TextField.ipadx = -13;
	date1TextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(sh.getGenericDate(date), date1TextField);
	
	GridBagConstraints amouthLabel = new GridBagConstraints();
	amouthLabel.gridx = 0;
	amouthLabel.gridy = 4;
	amouthLabel.weighty = weightye;
	amouthLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Amount(Inc VAT):"), amouthLabel);

	GridBagConstraints amouthField = new GridBagConstraints();
	amount = new JTextField(textfieldrow);
	amouthField.gridx = 1;
	amouthField.gridy = 4;
	amouthField.ipady = 2;
	amouthField.ipadx = 115;
	amouthField.anchor =  GridBagConstraints.WEST;
	addpanel.add(amount, amouthField);
	amount.setText(String.valueOf(ul.getBigDecimals(bean.connect().getAccruedBalance(accruedId))));
	
	GridBagConstraints groupLabel = new GridBagConstraints();
	groupLabel.gridx = 0;
	groupLabel.gridy = 5;
	groupLabel.ipadx = 60;
	groupLabel.weighty = weightye;
	groupLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Paid With:", JLabel.RIGHT), groupLabel);

	GridBagConstraints groupTextField = new GridBagConstraints();
	group = new Choice();
	//group.add("Bank/Cash");
	//group.add("Credit Card");
	accname = pu.getAccountCodeName(bean.connect().HashTableAccruedPosting());
	for(Enumeration<String> enu = accname.keys(); enu.hasMoreElements();){
		group.add(enu.nextElement());
	}
	groupTextField.gridx = 1;
	groupTextField.gridy = 5;
	groupTextField.ipady = 2;
	groupTextField.ipadx = 25;
	groupTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(group, groupTextField);

	GridBagConstraints vatLabel = new GridBagConstraints();
	vatLabel.gridx = 0;
	vatLabel.gridy = 6;
	vatLabel.ipadx = 60;
	vatLabel.weighty = weightye;
	vatLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("VAT:", JLabel.RIGHT), vatLabel);

	GridBagConstraints vatTextField = new GridBagConstraints();
	vat = new Choice();
	vat.add("None");
	vat.add("0%");
	vat.add("17.5%");
	vat.add("20%");
	vatTextField.gridx = 1;
	vatTextField.gridy = 6;
	vatTextField.ipady = 2;
	vatTextField.ipadx = 55;
	vatTextField.insets = new Insets(0, 0, 0, 60);
	//vatTextField.weightx = 0.1;
	vatTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(vat, vatTextField);
	

	//BUTTON BOTTOM
	GridBagConstraints okButton = new GridBagConstraints();
	ok = new JButton("OK");
	okButton.gridx = 2;
	okButton.gridy = 0;
	buttonpanel.add(ok, okButton);
	
	GridBagConstraints exitButton = new GridBagConstraints();
	exit = new JButton("Exit");
	exitButton.gridx = 3;
	exitButton.gridy = 0;
	exitButton.insets = new Insets(0, 20, 0, 0);
	buttonpanel.add(exit, exitButton);
	
	generalPanel.add("Center", addpanel);
	generalPanel.add("South", buttonpanel);
	generalPanel.setBorder(new Partition(20,"AddPaymentout"));
	con.add(generalPanel);

	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(500, 600);
	setResizable(false);
	setVisible(true); 
	exit.addActionListener(this);  
	supplierButton.addActionListener(this); 
	ok.addActionListener(this); 

    }
	
	/*public class SupplierInfo extends JFrame implements ActionListener, ItemListener{//implements ActionListener{

		private JButton refresh;
		private JButton oksup;
		private JButton newsup;
		private JButton exitsup;
		private JPanel headerpanel;
		private JPanel contentpanel;
		private JPanel footerpanel;
		private JPanel mainpanel;
		private JScrollPane projectPane;
		private Context ctx;
		private ArrayList<JLabel> titles = new ArrayList<JLabel>();
		private ArrayList<JLabel> supplierId = new ArrayList<JLabel>();
		private ArrayList<JCheckBox> checked = new ArrayList<JCheckBox>();
		//private   SpinnerHelper sh;
		private String client = "";
		private final int ipadiy = 2;
		private final int ipadix = 140;
		private final int ipadiyb = 2;
		private final int ipadixb = 4;
		private int selectIndex = 0;
		private final double weightix = 2.0;
		private final double weightiy = 1.0;
		Container con = getContentPane();
    
		public SupplierInfo(String title){//, int proId
		super(title); 

	//Panels	
		headerpanel = new JPanel();
		contentpanel = new JPanel();
		footerpanel = new JPanel();
		mainpanel = new JPanel();
	
		contentpanel.setLayout(new GridBagLayout());
		headerpanel.setLayout(new GridBagLayout());
		footerpanel.setLayout(new GridBagLayout());
		mainpanel.setLayout(new BorderLayout());
		//sh = new SpinnerHelper();
	

		GridBagConstraints descriLabel = new GridBagConstraints();
		descriLabel.gridx = 0;
		descriLabel.gridy = 0;
		descriLabel.ipadx = 5;
		descriLabel.insets = new Insets(15, 0, 10, 0);
		headerpanel.add(new JLabel("Select:"), descriLabel);
	
		GridBagConstraints qtyLabel = new GridBagConstraints();
		qtyLabel.gridx = 1;
		qtyLabel.gridy = 0;
		qtyLabel.weightx = weightix;
		qtyLabel.ipadx = 25;
		qtyLabel.insets = new Insets(15, 0, 10, 0);
		headerpanel.add(new JLabel("Supplie Name:"), qtyLabel);
		
		GridBagConstraints dateLabel = new GridBagConstraints();
		dateLabel.gridx = 2;
		dateLabel.gridy = 0;
		dateLabel.ipadx = 10;
		dateLabel.weightx = weightix;
		dateLabel.insets = new Insets(15, 90, 10, 0);
		headerpanel.add(new JLabel("Supplier Id:"), dateLabel);
		int row = 0;
		ArrayList<ArrayList<String>> supplierDetails = pu.getArrayListOfArrayListStr(bean.connect().supplierInfoListing());//supplierInfos()
 
		for(Iterator<ArrayList<String>> it = supplierDetails.iterator(); it.hasNext();){
			ArrayList<String> obj = it.next();
			GridBagConstraints checkSelect = new GridBagConstraints();
			JCheckBox check = new JCheckBox();
			checkSelect.gridx = 0;
			checkSelect.gridy = row;
			checkSelect.ipadx = 5;
			checkSelect.insets = new Insets(15, 0, 10, 0);
			contentpanel.add(check, checkSelect);
			checked.add(check);
		
			GridBagConstraints typeTextField1 = new GridBagConstraints();
			JLabel protitle = new JLabel();
			typeTextField1.gridx = 1;
			typeTextField1.gridy = row;
			typeTextField1.ipady = ipadiy;
			typeTextField1.weightx = weightix;
			typeTextField1.ipadx = 25;
			typeTextField1.anchor =  GridBagConstraints.WEST;
			typeTextField1.insets = new Insets(15, 50, 10, 60);
			contentpanel.add(protitle , typeTextField1);
			titles.add(protitle);
			String str = obj.get(0);
			if(str.length() > 27){
				String str1 = str.substring(0, 28);
				protitle.setText(str1+"...");
			}
			else{
				protitle.setText(str);
			}
		
			GridBagConstraints typeTextField2 = new GridBagConstraints();
			JLabel supplierids = new JLabel();
			typeTextField2.gridx = 2;
			typeTextField2.gridy = row;
			typeTextField2.ipady = ipadiy;
			typeTextField2.ipadx = 10;
			typeTextField2.anchor =  GridBagConstraints.EAST;
			typeTextField2.weightx = weightix;
			typeTextField2.insets = new Insets(15, 0, 10, 50);
			contentpanel.add(supplierids, typeTextField2);
			supplierids.setText(obj.get(1));
			supplierId.add(supplierids);
			row++;
		}
	//BUTTON BOTTOM
		GridBagConstraints submitquote = new GridBagConstraints();
		oksup = new JButton("OK");
		submitquote.gridx = 0;
		submitquote.gridy = 0;
		submitquote.insets = new Insets(0, 0, 0, 10);
		footerpanel.add(oksup, submitquote);
		
		GridBagConstraints newsupB = new GridBagConstraints();
		newsup = new JButton("New");
		newsupB.gridx = 1;
		newsupB.gridy = 0;
		newsupB.insets = new Insets(0, 0, 0, 10);
		footerpanel.add(newsup, newsupB);
		
		GridBagConstraints refreshB = new GridBagConstraints();
		refresh = new JButton("Refresh");
		refreshB.gridx = 2;
		refreshB.gridy = 0;
		refreshB.insets = new Insets(0, 0, 0, 20);
		footerpanel.add(refresh, refreshB);
		
	
		GridBagConstraints exitquote = new GridBagConstraints();
		exitsup = new JButton("Exit");
		exitquote.gridx = 3;
		exitquote.gridy = 0;
		footerpanel.add(exitsup, exitquote);
	
	
	
	
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
		mainpanel.add(contentpanel, BorderLayout.NORTH);
		projectPane = new JScrollPane(mainpanel, v,h);
		projectPane.setColumnHeaderView(headerpanel);
		projectPane.setBorder(new Partition(20,"Ledger"));
		con.add(projectPane);
		con.add(footerpanel, "South");

		pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 600);
		setResizable(false);
		setVisible(true); 
		exitsup.addActionListener(this);
		newsup.addActionListener(this);		
		refresh.addActionListener(this); 
		oksup.addActionListener(this); 

		for(int i = 0; i < checked.size(); i++){
			checked.get(i).addItemListener(this); 
		}
	}
	
		public void itemStateChanged(ItemEvent e) {
 
			for(int i = 0; i < checked.size(); i++){
				if(checked.get(i).isSelected()){
					selectIndex = i;
					for(int j = 0; j < checked.size(); j++){
						checked.get(j).setEnabled(false);
					}
				}
		
			}
		}	


		public void actionPerformed( ActionEvent e) {  

			if (e.getSource().equals(exitsup)){
				dispose();	
			}
	 
			if (e.getSource().equals(refresh)){
				dispose();	
				SupplierInfo ds = new SupplierInfo("List Of Suppliers");
			}
	 
			if (e.getSource().equals(oksup)){
				supplierName.setText(titles.get(selectIndex).getText());
				supplierIds = new Integer(supplierId.get(selectIndex).getText());
				dispose();	
			}
			
			if (e.getSource().equals(newsup)){
				new AddSupplier("New Supplier");
			}
		}
	}*/

   public void actionPerformed( ActionEvent e) {  

	if (e.getSource().equals(exit)){
	    dispose();	
	}
	/*if (e.getSource().equals(supplierButton)){
		SupplierInfo ds = new SupplierInfo("List Of Suppliers");
	 }*/
	if (e.getSource().equals(ok)){
	    ArrayList<String> paid = new ArrayList<String>();
		paid.add(String.valueOf(accruedId));
		paid.add(String.valueOf(accname.get(group.getSelectedItem())));
		paid.add(number.getText());
		paid.add(ul.usDateString(sh.dateString(date)));
		paid.add(amount.getText());
		paid.add(vat.getSelectedItem());
	    bean.connect().accruedExpensesPayment(ul.writeArrayListStr(paid)); 
		dispose();
	}
  }


}