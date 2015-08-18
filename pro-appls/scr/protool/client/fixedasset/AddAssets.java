
package scr.protool.client.fixedasset;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import java.net.*;
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
public class AddAssets extends JFrame implements ActionListener, MouseListener{//implements ActionListener{

    private JTextField name;
	private JTextField number;
	private JTextField supplierName;
    private JTextField invoiceOut;
    private JTextField amount;
	private JTextField residual;
	private JTextField serial;
	private JTextField insurance;
	private JTextField location;
	private JTextField rateValue;
    private Choice group;
	private Choice vat;
	private Choice depreciation;
    private JSpinner date;
	private JLabel secoundLabel;
	private JLabel firstLabel;
	private JLabel nameLab;
	private JLabel invoice;
	private JLabel lifeSpan;
	private JLabel residualVal;
	private JLabel depMethod;
	private JLabel discript;
	private JLabel serialNum;
	private JLabel physical;
	private JLabel vatLab;
	private JLabel rateVal;
	private JLabel paid;
	private JLabel aquisat;
	private JLabel amt;
	private JSpinner period;
    private JTextField description;
	private JButton supplierButton; 
	private JButton insuranceButton;
    private JButton save;
    private JButton ok;
    private JButton exit;
    private JPanel addpanel;
	private JPanel addRight;
    private JPanel buttonpanel;
    private JPanel generalPanel;
	private BorderLayout mainLayout;
    private Context ctx;
	private SpinnerHelper sh;
	private SpinnerHelper periodsh;
    private String client = "";
    private BeanStud bean;
	private Utilities ul = new Utilities();
	private PaymentInUtilities pu =  new PaymentInUtilities();
	private AssetsList list;
	private Integer assetSupplierId;
	private Integer insuranceSupplierId;
	private ClassLoader cl = this.getClass().getClassLoader();
	private Hashtable<String, Integer> accname = null;
	private  final int textfieldrow = 1;
	private final double weightye = 0.5;
	private boolean leftSide = false;
	private boolean insuranceSupplier = false;
	private boolean assetSupplier = false;
	private ArrayList<String> rightList = new ArrayList<String>();
	private ArrayList<String> leftList = new ArrayList<String>();
    Container con = getContentPane();
    
    public AddAssets(String title, AssetsList list){//, int proId
   	super(title); 

	this.list = list;
	//addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();
	sh = new SpinnerHelper();
	periodsh = new SpinnerHelper();
	bean = new BeanStud();
	//Panels
	//addpanel.setLayout(new GridBagLayout());
	buttonpanel.setLayout(new GridBagLayout());
	mainLayout = new BorderLayout();
	generalPanel.setLayout(mainLayout);
	
	//BUTTON BOTTOM
	GridBagConstraints flabel = new GridBagConstraints();
	URL imageLeft = cl.getResource("left.png"); 
	ImageIcon leftIcon = null;
	if(imageLeft  != null){
		leftIcon = new ImageIcon(imageLeft);
	}
	firstLabel = new JLabel(leftIcon);
	flabel.gridx = 0;
	flabel.gridy = 0;
	buttonpanel.add(firstLabel, flabel);
	//firstLabel.setBackground(Colord.ecode("#F0F0F0"));
	
	GridBagConstraints slabel = new GridBagConstraints();
	URL imageRight = cl.getResource("right.png"); 
	ImageIcon rightIcon = null;
	if(imageRight  != null){
		rightIcon = new ImageIcon(imageRight);
	}
	secoundLabel = new JLabel(rightIcon);
	slabel.gridx = 1;
	slabel.gridy = 0;
	buttonpanel.add(secoundLabel, slabel);
	
	GridBagConstraints okButton = new GridBagConstraints();
	ok = new JButton("OK");
	okButton.gridx = 2;
	okButton.gridy = 0;
	okButton.insets = new Insets(0, 10, 0, 0);
	buttonpanel.add(ok, okButton);
	ok.setEnabled(false);
	
	GridBagConstraints exitButton = new GridBagConstraints();
	exit = new JButton("Exit");
	exitButton.gridx = 3;
	exitButton.gridy = 0;
	exitButton.insets = new Insets(0, 20, 0, 0);
	buttonpanel.add(exit, exitButton);
	
	generalPanel.add("Center", leftPanel());
	generalPanel.add("South", buttonpanel);
	generalPanel.setBorder(new Partition(20,"AddPaymentout"));
	con.add(generalPanel);

	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(500, 600);
	setResizable(false);
	setVisible(true); 
	ok.addActionListener(this); 
	firstLabel.addMouseListener(this); 
	secoundLabel.addMouseListener(this); 
	exit.addActionListener(this);  
    }
	public class SupplierInfo extends JFrame implements ActionListener, ItemListener{//implements ActionListener{

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
		private int selectIndex;
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
	 
			if (e.getSource().equals(oksup) && assetSupplier){
				supplierName.setText(titles.get(selectIndex).getText());
				assetSupplierId = Integer.valueOf(supplierId.get(selectIndex).getText());
				selectIndex = 0;
				dispose();	
			}
			if (e.getSource().equals(oksup) && insuranceSupplier){
				insurance.setText(titles.get(selectIndex).getText());
				insuranceSupplierId = Integer.valueOf(supplierId.get(selectIndex).getText());
				selectIndex = 0;
				dispose();	
			}
			
			if (e.getSource().equals(newsup)){
				new AddSupplier("New Supplier");
			}
		}
	}
	
	public JPanel rightPanel(){
		addRight =  new JPanel();
		addRight.setLayout(new GridBagLayout());
		GridBagConstraints supplierLabel = new GridBagConstraints();
		supplierButton = new JButton("Supplier");
		supplierLabel.gridx = 0;
		supplierLabel.gridy = 0;
		supplierLabel.anchor =  GridBagConstraints.EAST;
		supplierLabel.weighty = weightye;
		addRight.add(supplierButton, supplierLabel);
		
		GridBagConstraints supplierTextField = new GridBagConstraints();
		supplierName = new JTextField(textfieldrow);
		supplierTextField.gridx = 1;
		supplierTextField.gridy = 0;
		supplierTextField.ipady = 2;
		supplierTextField.ipadx = 110;
		supplierTextField.anchor =  GridBagConstraints.WEST;
		addRight.add(supplierName, supplierTextField);
		supplierName.setEditable(false);
		if(!rightList.isEmpty()){
			supplierName.setText(rightList.get(8));
		}

		GridBagConstraints numberLabel = new GridBagConstraints();
		invoice = new JLabel("Invoice Number:");
		numberLabel.gridx = 0;
		numberLabel.gridy = 1;
		numberLabel.weighty = weightye;
		numberLabel.anchor =  GridBagConstraints.EAST;
		addRight.add(invoice, numberLabel);

		GridBagConstraints numberTextField = new GridBagConstraints();
		number = new JTextField(textfieldrow);
		numberTextField.gridx = 1;
		numberTextField.gridy = 1;
		numberTextField.ipady = 2;
		numberTextField.ipadx = 115;
		numberTextField.anchor =  GridBagConstraints.WEST;
		addRight.add(number, numberTextField);
		if(!rightList.isEmpty()){
			number.setText(rightList.get(1));
		}
		
		GridBagConstraints insuranceLabel = new GridBagConstraints();
		insuranceButton = new JButton("Insured By");
		insuranceLabel.gridx = 0;
		insuranceLabel.gridy = 2;
		insuranceLabel.weighty = weightye;
		insuranceLabel.anchor =  GridBagConstraints.EAST;
		addRight.add(insuranceButton, insuranceLabel);

		GridBagConstraints insuranceTextField = new GridBagConstraints();
		insurance = new JTextField(textfieldrow);
		insuranceTextField.gridx = 1;
		insuranceTextField.gridy = 2;
		insuranceTextField.ipady = 2;
		insuranceTextField.ipadx = 115;
		insuranceTextField.anchor =  GridBagConstraints.WEST;
		addRight.add(insurance, insuranceTextField);
		insurance.setEditable(false);
		if(!rightList.isEmpty()){
			insurance.setText(rightList.get(9));
		}
		
		GridBagConstraints date1Label = new GridBagConstraints();
		aquisat = new JLabel("Aquisition Date:");
		date1Label.gridx = 0;
		date1Label.gridy = 3;
		date1Label.weighty = weightye;
		date1Label.anchor =  GridBagConstraints.EAST;
		addRight.add(aquisat, date1Label);

		GridBagConstraints date1TextField = new GridBagConstraints();
		date = new JSpinner();
		date1TextField.gridx = 1;
		date1TextField.gridy = 3;
		date1TextField.ipady = 2;
		date1TextField.ipadx = 12;
		date1TextField.anchor =  GridBagConstraints.WEST;
		addRight.add(sh.getGenericDate(date), date1TextField);
		
		
		GridBagConstraints periodLabel = new GridBagConstraints();
		lifeSpan = new JLabel("Life Span:");
		periodLabel.gridx = 0;
		periodLabel.gridy = 4;
		periodLabel.weighty = weightye;
		periodLabel.anchor =  GridBagConstraints.EAST;
		addRight.add(lifeSpan, periodLabel);

		GridBagConstraints periodTextField = new GridBagConstraints();
		period = new JSpinner();
		periodTextField.gridx = 1;
		periodTextField.gridy = 4;
		periodTextField.ipady = 2;
		periodTextField.ipadx = 60;
		periodTextField.anchor =  GridBagConstraints.WEST;
		addRight.add(periodsh.getYearSpinner(period), periodTextField);
		
		GridBagConstraints amouthLabel = new GridBagConstraints();
		residualVal = new JLabel("Residual Value:");
		amouthLabel.gridx = 0;
		amouthLabel.gridy = 5;
		amouthLabel.weighty = weightye;
		amouthLabel.anchor =  GridBagConstraints.EAST;
		addRight.add(residualVal, amouthLabel);

		GridBagConstraints amouthField = new GridBagConstraints();
		residual = new JTextField(textfieldrow);
		amouthField.gridx = 1;
		amouthField.gridy = 5;
		amouthField.ipady = 2;
		amouthField.ipadx = 115;
		amouthField.anchor =  GridBagConstraints.WEST;
		if(!rightList.isEmpty()){
			residual.setText(rightList.get(5));
		}
		addRight.add(residual, amouthField);
		
		GridBagConstraints depLabel = new GridBagConstraints();
		depMethod = new JLabel("Dep Method:", JLabel.RIGHT);//Depreciation 
		depLabel.gridx = 0;
		depLabel.gridy = 6;
		depLabel.ipadx = 60;
		depLabel.weighty = weightye;
		depLabel.anchor =  GridBagConstraints.EAST;
		addRight.add(depMethod, depLabel);

		GridBagConstraints depTextField = new GridBagConstraints();
		depreciation = new Choice();
		depreciation.add("Straigh Line");
		depreciation.add("Reducing Balance");
		depTextField.gridx = 1;
		depTextField.gridy = 6;
		depTextField.ipady = 2;
		depTextField.ipadx = 15;
		depTextField.insets = new Insets(0, 0, 0, 60);
		depTextField.anchor =  GridBagConstraints.WEST;
		if(!rightList.isEmpty()){
			depreciation.select(rightList.get(6));
		}
		addRight.add(depreciation, depTextField);
		
	   GridBagConstraints rateValLabel = new GridBagConstraints();
		rateVal = new JLabel("Rate / Value:");
		rateValLabel.gridx = 0;
		rateValLabel.gridy = 7;
		rateValLabel.weighty = weightye;
		rateValLabel.anchor =  GridBagConstraints.EAST;
		addRight.add(rateVal, rateValLabel);

		GridBagConstraints rateValueField = new GridBagConstraints();
		rateValue = new JTextField(textfieldrow);
		rateValueField.gridx = 1;
		rateValueField.gridy = 7;
		rateValueField.ipady = 2;
		rateValueField.ipadx = 115;
		rateValueField.anchor =  GridBagConstraints.WEST;
		if(!rightList.isEmpty()){
			rateValue.setText(rightList.get(7));
		}
		addRight.add(rateValue, rateValueField);
		
		supplierButton.addActionListener(this); 
		insuranceButton.addActionListener(this);
		return addRight;
	}
	
	public JPanel leftPanel(){
		addpanel = new JPanel();
		addpanel.setLayout(new GridBagLayout());
		GridBagConstraints nameLabel = new GridBagConstraints();
		nameLab = new JLabel("Accounts Name:");
		nameLabel.gridx = 0;
		nameLabel.gridy = 0;
		nameLabel.anchor =  GridBagConstraints.EAST;
		nameLabel.weighty = weightye;
		addpanel.add(nameLab, nameLabel);

		GridBagConstraints nameTextField = new GridBagConstraints();
		name = new JTextField(textfieldrow);
		nameTextField.gridx = 1;
		nameTextField.gridy = 0;
		nameTextField.ipady = 2;
		nameTextField.ipadx = 110;
		nameTextField.anchor =  GridBagConstraints.WEST;
		addpanel.add(name, nameTextField);
		name.setText(ul.getStringValue(list.getTableGeneral(), list.getModelGeneral(), 1));
		name.setEditable(false);
	
		GridBagConstraints descriptionLabel = new GridBagConstraints();
		discript = new JLabel("Description:");
		descriptionLabel.gridx = 0;
		descriptionLabel.gridy = 1;
		descriptionLabel.anchor =  GridBagConstraints.EAST;
		descriptionLabel.weighty = weightye;
		addpanel.add(discript, descriptionLabel);

		GridBagConstraints descriptionTextField = new GridBagConstraints();
		description = new JTextField(textfieldrow);
		descriptionTextField.gridx = 1;
		descriptionTextField.gridy = 1;
		descriptionTextField.ipady = 2;
		descriptionTextField.ipadx = 110;
		descriptionTextField.anchor =  GridBagConstraints.WEST;
		if(!leftList.isEmpty()){
			description.setText(leftList.get(1));
		}
		addpanel.add(description, descriptionTextField);
	
	
		GridBagConstraints serialLabel = new GridBagConstraints();
		serialNum = new JLabel("Serial Number:");
		serialLabel.gridx = 0;
		serialLabel.gridy = 2;
		serialLabel.anchor =  GridBagConstraints.EAST;
		serialLabel.weighty = weightye;
		addpanel.add(serialNum, serialLabel);

		GridBagConstraints serialTextField = new GridBagConstraints();
		serial = new JTextField(textfieldrow);
		serialTextField.gridx = 1;
		serialTextField.gridy = 2;
		serialTextField.ipady = 2;
		serialTextField.ipadx = 110;
		serialTextField.anchor =  GridBagConstraints.WEST;
		if(!leftList.isEmpty()){
			serial.setText(leftList.get(2));
		}
		addpanel.add(serial, serialTextField);
	
		GridBagConstraints locationLabel = new GridBagConstraints();
		physical = new JLabel("Physical location:");
		locationLabel.gridx = 0;
		locationLabel.gridy = 3;
		locationLabel.anchor =  GridBagConstraints.EAST;
		locationLabel.weighty = weightye;
		addpanel.add(physical, locationLabel);

		GridBagConstraints locationTextField = new GridBagConstraints();
		location = new JTextField(textfieldrow);
		locationTextField.gridx = 1;
		locationTextField.gridy = 3;
		locationTextField.ipady = 2;
		locationTextField.ipadx = 110;
		locationTextField.anchor =  GridBagConstraints.WEST;
		if(!leftList.isEmpty()){
			location.setText(leftList.get(3));
		}
		addpanel.add(location, locationTextField);
	
		//this should be implmeneted as free text for inputting account code
		//as asset could be financed by lease / hire purchase
		GridBagConstraints groupLabel = new GridBagConstraints();
		paid = new JLabel("Paid With:", JLabel.RIGHT);
		groupLabel.gridx = 0;
		groupLabel.gridy = 4;
		groupLabel.ipadx = 60;
		groupLabel.weighty = weightye;
		groupLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(paid, groupLabel);
	
		GridBagConstraints groupTextField = new GridBagConstraints();
		group = new Choice();

		accname = pu.getAccountCodeName(bean.connect().HashTableMaterialPosting());
		for(Enumeration<String> enu = accname.keys(); enu.hasMoreElements();){
			group.add(enu.nextElement());
		}
		groupTextField.gridx = 1;
		groupTextField.gridy = 4;
		groupTextField.weighty = weightye;
		groupTextField.ipady = 2;
		groupTextField.ipadx = 25;
		groupTextField.anchor =  GridBagConstraints.WEST;
		if(!leftList.isEmpty()){
			group.select(leftList.get(7));
		}
		addpanel.add(group, groupTextField);
	
		GridBagConstraints amouthLabel = new GridBagConstraints();
		amt = new JLabel("Amount(Inc VAT):");
		amouthLabel.gridx = 0;
		amouthLabel.gridy = 5;
		amouthLabel.weighty = weightye;
		amouthLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(amt, amouthLabel);

		GridBagConstraints amouthField = new GridBagConstraints();
		amount = new JTextField(textfieldrow);
		amouthField.gridx = 1;
		amouthField.gridy = 5;
		amouthField.ipady = 2;
		amouthField.ipadx = 115;
		amouthField.anchor =  GridBagConstraints.WEST;
		if(!leftList.isEmpty()){
			amount.setText(leftList.get(5));
		}
		addpanel.add(amount, amouthField);
	
		GridBagConstraints vatLabel = new GridBagConstraints();
		vatLab = new JLabel("VAT:", JLabel.RIGHT);
		vatLabel.gridx = 0;
		vatLabel.gridy = 6;
		vatLabel.ipadx = 60;
		vatLabel.weighty = weightye;
		vatLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(vatLab, vatLabel);

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
		vatTextField.anchor =  GridBagConstraints.WEST;
		if(!leftList.isEmpty()){
			vat.select(leftList.get(6));
		}
		addpanel.add(vat, vatTextField);
		
		return addpanel;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(secoundLabel) && leftSide == false){
			leftList.clear();
			
			leftList.add(String.valueOf(ul.getTableInt(list.getTableGeneral(), 
							list.getModelGeneral(), 0)));
			if(description.getText() != null)
				leftList.add(description.getText());
			else{
				leftList.add("");
			}
			if(serial.getText() != null)
				leftList.add(serial.getText());
			else{
				leftList.add("");
			}
			if(location.getText() != null)
				leftList.add(location.getText());
			else{
				leftList.add("");
			}
			leftList.add(String.valueOf(accname.get(group.getSelectedItem())));
			if(amount.getText() != null)
				leftList.add(amount.getText());
			else{
				leftList.add("");
			}
			leftList.add(vat.getSelectedItem());
			leftList.add(group.getSelectedItem());
			generalPanel.remove(mainLayout.getLayoutComponent(BorderLayout.CENTER));
			generalPanel.add("Center", rightPanel());
			leftSide = true;
			//secoundLabel.setBackground(Colord.ecode("#F0F0F0"));
			ok.setEnabled(true);
			repaint();
			validate();
		}
		if (e.getSource().equals(firstLabel) && leftSide){
			
			rightList.clear();

			if(supplierName.getText() != null)
				rightList.add(String.valueOf(assetSupplierId));
			else{
				rightList.add("");
			}
			if(number.getText() != null)
				rightList.add(number.getText());
			else{
				rightList.add("");
			}
			if(insurance.getText() != null)
				rightList.add(String.valueOf(insuranceSupplierId));
			else{
				rightList.add("");
			}
			rightList.add(ul.usDateString(sh.dateString(date)));
			rightList.add(periodsh.yearString(period));
			if(residual.getText() != null)
				rightList.add(residual.getText());
			else{
				rightList.add("");
			}
			rightList.add(depreciation.getSelectedItem());
			
			if(rateValue.getText() != null)
				rightList.add(rateValue.getText());
			else{
				rightList.add("");
			}
			
			if(supplierName.getText() != null)
				rightList.add(supplierName.getText());
			else{
				rightList.add("");
			}
			if(insurance.getText() != null)
				rightList.add(insurance.getText());
			else{
				rightList.add("");
			}
			
			generalPanel.remove(mainLayout.getLayoutComponent(BorderLayout.CENTER));
			generalPanel.add("Center", leftPanel());
			leftSide = false;
			//firstLabel.setBackground(Colord.ecode("#F0F0F0"));
			repaint();
			validate();
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e){} 
	public void mouseReleased(MouseEvent e) {}
	
    public void actionPerformed( ActionEvent e) {  

	  if (e.getSource().equals(exit)){
	    dispose();	
	 }
	 if (e.getSource().equals(supplierButton)){
		assetSupplier = true;
		insuranceSupplier = false;
		SupplierInfo ds = new SupplierInfo("List Of Suppliers");
	 }
	 if (e.getSource().equals(insuranceButton)){
		insuranceSupplier = true;
		assetSupplier = false;
		SupplierInfo ds = new SupplierInfo("List Of Suppliers");
	 }
	 
	 if (e.getSource().equals(ok)){
	
	 java.util.List<String> details = null;
	 if(!leftSide){

		details = new ArrayList<String>(rightList);
		details.add(0, String.valueOf(ul.getTableInt(list.getTableGeneral(), 
							list.getModelGeneral(), 0)));
		details.add(1, description.getText());
		details.add(2, serial.getText());
		details.add(3, location.getText());
		details.add(4, String.valueOf(accname.get(group.getSelectedItem())));
		details.add(5, amount.getText());
		details.add(6, vat.getSelectedItem());
		
	 }
	 
	 else{
		leftList.remove(leftList.get(7));
		details = new ArrayList<String>(leftList);
		details.add(String.valueOf(assetSupplierId));
		details.add(number.getText());
		details.add(String.valueOf(insuranceSupplierId));
		details.add(ul.usDateString(sh.dateString(date)));
		details.add(periodsh.yearString(period));
		details.add(residual.getText());
		details.add(depreciation.getSelectedItem());
		details.add(rateValue.getText());
		
	 }
	 	bean.connect().addAssets(ul.writeArrayListStr(details)); 
	    dispose();	
	 }
	 			
  }

}