
package scr.protool.client.project;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.text.*;
import javax.naming.*;
import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import scr.protool.client.utilities.*;
import scr.protool.client.miscelleneous.BeanStud;
import scr.protool.client.miscelleneous.QuotePdf;
/**

*
*

*
* @author <a href="mailto:"></a>
* @version 1.0
*/
public class AddOnProject extends JFrame implements ActionListener{
 
    private JTextField projectId;
    private JTextField projectTitle;
    private JTextField companyName;
    private JTextField quote;
    private JTextField mobile;
    private SpinnerHelper sh;
	private SpinnerHelper shInner;
	private JSpinner duration;
	private JSpinner date;
	private JSpinner validDate;
    private JTextField addresses;
    private JTextField telephone;
    private JTextField email; 
	private JTextField contact;
    private JButton save;
    private JTextField customerId;
    private JButton exit;
	private JButton quotebutton;
	private JButton address;
    private JPanel addpanel;
    private JPanel buttonpanel;
    private JPanel generalPanel;
    private Context ctx;
    private String client = "";
    private BeanStud bean;
	private String strValid;
	private final int textFieldColunm = 1;
	private Utilities ul = new Utilities();
	private Locale locale = Locale.UK;
	private ArrayList<ArrayList<String>> outerlist = new ArrayList<ArrayList<String>>();
    Container con = getContentPane();
	private ArrayList<String> projectInfo;

    
    public AddOnProject(String title, ArrayList<String> projectInfo){
   	super(title); 
	this.projectInfo = projectInfo;
	bean = new BeanStud();
	addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();

	sh = new SpinnerHelper();
	shInner = new SpinnerHelper();
	//Panels
	addpanel.setLayout(new GridBagLayout());
	buttonpanel.setLayout(new GridBagLayout());
	generalPanel.setLayout(new BorderLayout());

	//Textfields use for search
	GridBagConstraints idLabel = new GridBagConstraints();
	//   	idLabel.insets = new Insets(20, 0, 0, 0);
	idLabel.gridx = 0;
	idLabel.gridy = 0;
	idLabel.anchor =  GridBagConstraints.EAST;
       	idLabel.weighty = 0.1;
	//addpanel.add(new JLabel("Project Id:"), idLabel);

	GridBagConstraints idTextField = new GridBagConstraints();
	projectId = new JTextField();
	idTextField.gridx = 1;
	idTextField.gridy = 0;
	idTextField.ipady = 2;
	idTextField.ipadx = 40;
	idTextField.anchor =  GridBagConstraints.WEST;
	//addpanel.add(projectId, idTextField);
	projectId.setEditable(false);

	GridBagConstraints titleLabel = new GridBagConstraints();
	titleLabel.gridx = 0;
	titleLabel.gridy = 0;
      	titleLabel.weighty = 0.1;
	//	titleLabel.insets = new Insets(0, 0, 15, 0);
	titleLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Project Title:"), titleLabel);

	GridBagConstraints titleTextField = new GridBagConstraints();
	projectTitle = new JTextField(textFieldColunm);
	titleTextField.gridx = 1;
	titleTextField.gridy = 0;
	titleTextField.ipady = 2;
	titleTextField.ipadx = 200;
	titleTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(projectTitle, titleTextField);
	

	GridBagConstraints companyLabel = new GridBagConstraints();
	//	clientLabel.insets = new Insets(0, 0, 15, 0);
	companyLabel.gridx = 0;
	companyLabel.gridy = 1;
	companyLabel.weighty = 0.1;
	companyLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Company Name:"), companyLabel);

	GridBagConstraints companyField = new GridBagConstraints();
	companyName = new JTextField(textFieldColunm);
	companyField.gridx = 1;
	companyField.gridy = 1;
	companyField.ipady = 2;
	companyField.ipadx = 200;
	companyField.anchor =  GridBagConstraints.WEST;
	addpanel.add(companyName, companyField);
	companyName.setText(projectInfo.get(0));
	companyName.setEditable(false);

	GridBagConstraints addressLabel = new GridBagConstraints();
	address = new JButton("Addresses");
	addressLabel.gridx = 0;
	addressLabel.gridy = 2;
	addressLabel.weighty = 0.1;
	//	addressLabel.insets = new Insets(0, 0, 15, 0);
	addressLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(address, addressLabel);
	address.setEnabled(false);

	GridBagConstraints addrTextField = new GridBagConstraints();
	addresses = new JTextField(textFieldColunm);
	addrTextField.ipady = 2;
	addrTextField.ipadx = 250;
	addrTextField.gridx = 1;
	addrTextField.gridy = 2;
	addrTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(addresses, addrTextField);
	addresses.setEditable(false);
	addresses.setText(projectInfo.get(1));

	GridBagConstraints teleLabel = new GridBagConstraints();
	teleLabel.gridx = 0;
	teleLabel.gridy = 3;
	teleLabel.weighty = 0.1;
	//	teleLabel.insets = new Insets(0, 0, 15, 0);
	teleLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Telephone:"), teleLabel);

	GridBagConstraints teleTextField = new GridBagConstraints();
	telephone = new JTextField(textFieldColunm);
	teleTextField.gridx = 1;
	teleTextField.gridy = 3;
	teleTextField.ipady = 2;
	teleTextField.ipadx = 200;
	teleTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(telephone, teleTextField);
	telephone.setText(projectInfo.get(2));
	telephone.setEditable(false);

	GridBagConstraints mobileLabel = new GridBagConstraints();
	mobileLabel.gridx = 0;
	mobileLabel.gridy = 4;
	mobileLabel.weighty = 0.1;
	//	costLabel.insets = new Insets(0, 0, 15, 0);
	mobileLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Mobile:"), mobileLabel);

	GridBagConstraints mobileTextField = new GridBagConstraints();
	mobile = new JTextField(textFieldColunm);
	mobileTextField.gridx = 1;
	mobileTextField.gridy = 4;
	mobileTextField.ipady = 2;
	mobileTextField.ipadx = 200;
	mobileTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(mobile, mobileTextField);
	mobile.setText(projectInfo.get(3));
	mobile.setEditable(false);

	GridBagConstraints emailLabel = new GridBagConstraints();
	emailLabel.gridx = 0;
	emailLabel.gridy = 5;
	emailLabel.weighty = 0.1;
	//	emailLabel.insets = new Insets(0, 0, 15, 0);
	emailLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Email:"), emailLabel);

	GridBagConstraints emailTextField = new GridBagConstraints();
	email = new JTextField(textFieldColunm);
	emailTextField.gridx = 1;
	emailTextField.gridy = 5;
	emailTextField.ipady = 2;
	emailTextField.ipadx = 200;
	emailTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(email, emailTextField);
	email.setText(projectInfo.get(4));
	email.setEditable(false);
	
	customerId = new JTextField(textFieldColunm);
	customerId.setText(projectInfo.get(5));

	GridBagConstraints quote1Label = new GridBagConstraints();
	quotebutton = new JButton("QUOTE..");
	quote1Label.gridx = 0;
	quote1Label.gridy = 6;
	quote1Label.weighty = 0.1;
	quote1Label.anchor =  GridBagConstraints.EAST;
	//	quote1Label.insets = new Insets(0, 0, 15, 0);
	addpanel.add(quotebutton, quote1Label);

	GridBagConstraints quote1TextField = new GridBagConstraints();
	quote = new JTextField(textFieldColunm);
	quote1TextField.gridx = 1;
	quote1TextField.gridy = 6;
	quote1TextField.ipady = 2;
	quote1TextField.ipadx = 110;
	quote1TextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(quote, quote1TextField);
	quote.setEditable(false);

	GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 7;
	date1Label.weighty = 0.1;
	//	date1Label.insets = new Insets(0, 0, 15, 0);
    date1Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Date:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	//date = new JTextField();
	date = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 7;
	date1TextField.ipady = 2;
	date1TextField.ipadx = -20;
	date1TextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(sh.getGenericDate(date), date1TextField);

	GridBagConstraints durationLabel = new GridBagConstraints();
	durationLabel.gridx = 0;
	durationLabel.gridy = 8;
	durationLabel.ipadx = 60;
	durationLabel.weighty = 0.1;
	//	durationLabel.insets = new Insets(0, 0, 15, 0);
	durationLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Term:", JLabel.RIGHT), durationLabel);

	GridBagConstraints durationTextField = new GridBagConstraints();
	duration = new JSpinner();//payment terms
	durationTextField.gridx = 1;
	durationTextField.gridy = 8;
	durationTextField.ipady = 2;
	durationTextField.ipadx = 40;
	durationTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(sh.getDaysSpinner(duration), durationTextField);


	//BUTTON BOTTOM
	/*buttonpanel.add(new JLabel(""));

	save = new JButton("SAVE");
	buttonpanel.add(save);

	//	buttonpanel.add(new JLabel(""));

	ok = new JButton("OK");
	buttonpanel.add(ok);

	//	buttonpanel.add(new JLabel(""));
	
	exit = new JButton("EXIT");
	buttonpanel.add(exit);*/
	GridBagConstraints saveButton = new GridBagConstraints();
	save = new JButton("SAVE");
	saveButton.gridx = 1;
	saveButton.gridy = 0;
	buttonpanel.add(save, saveButton);
	
	/*GridBagConstraints okButton = new GridBagConstraints();
	ok = new JButton("OK");
	okButton.gridx = 2;
	okButton.gridy = 0;
	buttonpanel.add(ok, okButton);*/
	
	GridBagConstraints exitButton = new GridBagConstraints();
	exit = new JButton("Exit");
	exitButton.gridx = 3;
	exitButton.gridy = 0;
	//exitButton.insets = new Insets(0, 20, 0, 0);
	buttonpanel.add(exit, exitButton);

	buttonpanel.add(new JLabel(""));
	generalPanel.add("Center", addpanel);
	generalPanel.add("South", buttonpanel);
	generalPanel.setBorder(new Partition(20,"AddProject"));
	con.add(generalPanel);

	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     	setSize(500, 600);
	setVisible(true); 
	exit.addActionListener(this);  
	save.addActionListener(this); 
	//ok.addActionListener(this); 
	quotebutton.addActionListener(this);
	address.addActionListener(this);

	//projectId.setText(String.valueOf(proId));  

    }
	
	public class Quote extends  JFrame implements MouseListener , ActionListener{

    Container con = getContentPane();
    private JPanel headerpanel;
    private JPanel contentpanel;
	private JPanel footerpanel;
    private JPanel mainpanel;
	private JPanel ledgerButtonPanel;//might be needed
    private JLabel total;
	private JLabel add;
	private JLabel remove;
	private JTextField totalLabels;
	private  int click = 1;	
    private JTextField description;
	private JButton submit;
	private JButton dispose_exit;
	private JTextField quantity;
	private JTextField unitprice;
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
	private ClassLoader cl = this.getClass().getClassLoader();
	private ArrayList<JTextField> descript = new ArrayList<JTextField>();
	private ArrayList<JTextField> quant = new ArrayList<JTextField>();
	private ArrayList<JTextField> 	uniprice = new ArrayList<JTextField>();
	private ArrayList<JLabel> label = new ArrayList<JLabel>();
	private int index = 0;

    public Quote(String title) {
	super(title);

	headerpanel = new JPanel();
	contentpanel = new JPanel();
	footerpanel = new JPanel();
	mainpanel = new JPanel();
	ledgerButtonPanel = new JPanel();

	contentpanel.setLayout(new GridBagLayout());
	headerpanel.setLayout(new GridBagLayout());
	footerpanel.setLayout(new GridBagLayout());
	mainpanel.setLayout(new BorderLayout());

	
	GridBagConstraints descriLabel = new GridBagConstraints();
	descriLabel.gridx = 0;
	descriLabel.gridy = 0;
	descriLabel.weightx = weightix;
	descriLabel.insets = new Insets(15, 110, 10, 0);
    headerpanel.add(new JLabel("Decription:"), descriLabel);
	
	GridBagConstraints qtyLabel = new GridBagConstraints();
	qtyLabel.gridx = 1;
	qtyLabel.gridy = 0;
	qtyLabel.weightx = weightix;
	qtyLabel.insets = new Insets(15, 90, 10, 0);
    headerpanel.add(new JLabel("Quantity:"), qtyLabel);
	
	GridBagConstraints priLabel = new GridBagConstraints();
	priLabel.gridx = 2;
	priLabel.gridy = 0;
	priLabel.weightx = weightix;
	priLabel.insets = new Insets(15, 0, 10, 30);
    headerpanel.add(new JLabel("Unit Price:"), priLabel);
	
	
	makeTextField(1);
	GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 0;
	date1Label.weighty = 0.1;
	//	date1Label.insets = new Insets(0, 0, 15, 0);
    date1Label.anchor =  GridBagConstraints.EAST;
	footerpanel.add(new JLabel("Valid Date:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	//date = new JTextField();
	validDate = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 0;
	date1TextField.ipady = 2;
	date1TextField.insets = new Insets(0, 0, 0, 30);
	date1TextField.ipadx = -20;
	date1TextField.anchor =  GridBagConstraints.WEST;
	footerpanel.add(shInner.getGenericDate(validDate), date1TextField);
	
	submit = new JButton("SUBMIT");
	GridBagConstraints submitquote = new GridBagConstraints();
	submitquote.gridx = 2;
	submitquote.gridy = 0;
	submitquote.insets = new Insets(0, 0, 0, 30);
	footerpanel.add(submit, submitquote);
	
	GridBagConstraints totalLabel = new GridBagConstraints();
	totalLabel.gridx = 3;
	totalLabel.gridy = 0;
    footerpanel.add(new JLabel("Total:"), totalLabel);
	
	GridBagConstraints priceLabel = new GridBagConstraints();
	totalLabels = new JTextField(textFieldColunm);
	priceLabel.gridx = 4;
	priceLabel.gridy = 0;
	priceLabel.ipadx = 60;
	priceLabel.insets = new Insets(0, 0, 0, 30);
    footerpanel.add(totalLabels , priceLabel);
	totalLabels.setText(new String("£0.00"));
	totalLabels.setEditable(false);
	
	dispose_exit = new JButton("EXIT");
	GridBagConstraints exitquote = new GridBagConstraints();
	exitquote.gridx = 5;
	exitquote.gridy = 0;
	//submitquote.insets = new Insets(0, 0, 0, 60);
	footerpanel.add(dispose_exit, exitquote);
	
	mainpanel.add(contentpanel, BorderLayout.NORTH);
	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	ledgerViewScroll = new JScrollPane(mainpanel, v,h);
	ledgerViewScroll.setColumnHeaderView(headerpanel);
	ledgerViewScroll.setBorder(new Partition(20,"Ledger"));
	con.add(ledgerViewScroll);
	con.add(footerpanel, "South");
	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(650, 600);
	setVisible(true); 
	setResizable(false);
 		
	add.addMouseListener(this); 
	remove.addMouseListener(this);
	dispose_exit.addActionListener(this);
	submit.addActionListener(this);
    }
	
	public void makeTextField(int row){
		Vector<String> innerlist = new Vector<String>();
		GridBagConstraints typeTextField1 = new GridBagConstraints();
		description = new JTextField(textFieldColunm);
		typeTextField1.gridx = 0;
		typeTextField1.gridy = row;
		typeTextField1.ipady = ipadiy;
		typeTextField1.ipadx = 250;
		//typeTextField1.weightx = 1.0;
		typeTextField1.insets = new Insets(0, 0, 10, 40);
		typeTextField1.anchor =  GridBagConstraints.WEST;
		contentpanel.add(description, typeTextField1);
		descript.add(description);
		//innerlist.add(descript.get(index).getText());
		
		GridBagConstraints typeTextField2 = new GridBagConstraints();
		quantity = new JTextField(textFieldColunm);
		typeTextField2.gridx = 1;
		typeTextField2.gridy = row;
		typeTextField2.ipady = ipadiy;
		typeTextField2.ipadx = 60;
		//typeTextField2.weightx = 1.0;
		typeTextField2.insets = new Insets(0, 0, 10, 40);
		typeTextField2.anchor =  GridBagConstraints.WEST;
		contentpanel.add(quantity, typeTextField2);
		quant.add(quantity);
		//innerlist.add(quant.get(index).getText());
			
		GridBagConstraints typeTextField3 = new GridBagConstraints();
		unitprice = new JTextField(textFieldColunm);
		typeTextField3.gridx = 2;
		typeTextField3.gridy = row;
		typeTextField3.ipady = ipadiy;
		//typeTextField3.weightx = 1.0;
		typeTextField3.ipadx = 60;
		typeTextField3.insets = new Insets(0, 0, 10, 0);
		typeTextField3.anchor =  GridBagConstraints.WEST;
		contentpanel.add(unitprice, typeTextField3);
		uniprice.add(unitprice);
		//innerlist.add(uniprice.get(index).getText());
		
		
		GridBagConstraints addLabel = new GridBagConstraints();
		//ImageIcon icon = new ImageIcon("C:/Users/Ola/Desktop/JAVA/JAVA/add.png");
		URL imageAdd = cl.getResource("add.png"); 
		ImageIcon addIcon = null;
		if(imageAdd  != null){
			addIcon = new ImageIcon(imageAdd);
		}
		
		add = new JLabel(addIcon);
		addLabel.gridx = 3;
		addLabel.gridy = row;
		//addLabel.weightx = 1.0;
		addLabel.anchor =  GridBagConstraints.NORTHEAST;
		addLabel.insets = new Insets(3, 0, 0, 0);
		contentpanel.add(add, addLabel);
		
		GridBagConstraints removeLabel = new GridBagConstraints();
		URL imageRemove = cl.getResource("remove.png"); 
		ImageIcon removeIcon = null;
		if(imageRemove  != null){
			removeIcon = new ImageIcon(imageRemove);
		}
		
		//ImageIcon iconr = new ImageIcon("C:/Users/Ola/Desktop/JAVA/JAVA/remove.png");
		remove = new JLabel(removeIcon);
		removeLabel.gridx = 3;
		removeLabel.gridy = row-1;
		removeLabel.anchor =  GridBagConstraints.NORTHEAST;
		removeLabel.insets = new Insets(3, 0, 0, 0);
		if(row > 1){
			contentpanel.add(remove, removeLabel);
			label.add(remove); // list of remove image label use to get index of contentpanel to be removed
			//contentPaneList.add(contentpanel);// add individual rows of content to a list
		}
		
	}
	
	public BigDecimal Results(){
		BigDecimal total = BigDecimal.ZERO;
		if(!outerlist.isEmpty()){
			outerlist.clear(); 
		}
	
		for(int i = 0; i < uniprice.size(); i++){
			ArrayList<String> innerlist = new ArrayList<String>();
			BigDecimal subtotal = BigDecimal.ZERO;
			BigDecimal price =  BigDecimal.ZERO;
			BigDecimal qty =  BigDecimal.ZERO;
			if(!uniprice.get(i).getText().equals("") || !quant.get(i).getText().equals("")){
				innerlist.add(descript.get(i).getText());
				qty = new BigDecimal(uniprice.get(i).getText());
				innerlist.add(uniprice.get(i).getText());
				price = new BigDecimal(quant.get(i).getText());
				innerlist.add(quant.get(i).getText());
				outerlist.add(innerlist);
			}
			subtotal = qty.multiply(price);
			total = total.add(subtotal);
			
		}

		return total;
		
	}
	
	
	public void actionPerformed( ActionEvent e) { 
	
		if (e.getSource().equals(submit)){
			//Integer proId = new Integer(projectId.getText());
		
			String string = NumberFormat.getCurrencyInstance(locale).format(Results());
			quote.setText(string);
			//generate pdf file for quote here
			dispose();	
		}
		if (e.getSource().equals(dispose_exit)){
			quote.setText("");
			dispose();	
		}
	
	
	}
	
	public void getRideInner(ActionEvent evt){
	 
		WindowEvent wev  = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);                
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(add)){
			contentpanel.remove(add);
			click+=e.getClickCount();
			makeTextField(click);
			contentpanel.revalidate();  
			repaint();
			validate();
			if(click >= 2){
				add.addMouseListener(this);
				remove.addMouseListener(this);
			}
			totalLabels.setText(ul.getStringInCurrency(Results()));
			quote.setText(ul.getStringInCurrency(Results()));
		}
		else{
			for(int i = 0; i < label.size(); i++){
				if(e.getSource().equals(label.get(i))){
					contentpanel.remove(descript.get(i));
					descript.remove(i);
					contentpanel.remove(quant.get(i));
					quant.remove(i);
					contentpanel.remove(uniprice.get(i));
					uniprice.remove(i);
					contentpanel.remove(label.get(i));
					label.remove(i);
					contentpanel.revalidate();  
					repaint();
					validate();
				}
			}
			totalLabels.setText(ul.getStringInCurrency(Results()));
			quote.setText(ul.getStringInCurrency(Results()));
		}

	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e){} 
	public void mouseReleased(MouseEvent e) {}
	
}

public class AddAddress extends JFrame implements ActionListener{//implements ActionListener{
   
		private JTextField addressline1;
		private JTextField addressline2;
		private JTextField town_city;
		private JTextField county_state;
		private JTextField postCode;
		private JTextField country;
		private JTextField proTitle;
		private JButton save;
		private JButton exit;
		private JPanel addpanel;
		private JPanel buttonpanel;
		private JPanel generalPanel;
		private String client = "";
		private final double weightye = 0.5;
		Container con = getContentPane();
    
		public AddAddress(String title){//, int proId
		super(title); 
		addpanel = new JPanel();
		buttonpanel = new JPanel();
		generalPanel = new JPanel();
		//sh = new SpinnerHelper();
		//Panels
		addpanel.setLayout(new GridBagLayout());
		buttonpanel.setLayout(new GridBagLayout());
		generalPanel.setLayout(new BorderLayout());

		//Textfields 
		GridBagConstraints idLabel = new GridBagConstraints();
		idLabel.gridx = 0;
		idLabel.gridy = 0;
		idLabel.anchor =  GridBagConstraints.EAST;
		idLabel.weighty = weightye;
		addpanel.add(new JLabel("Project Title:"), idLabel);

		GridBagConstraints idTextField = new GridBagConstraints();
		proTitle = new JTextField(textFieldColunm);
		idTextField.gridx = 1;
		idTextField.gridy = 0;
		idTextField.ipady = 2;
		idTextField.weightx = 0.05;
		idTextField.ipadx = 190;
		idTextField.anchor =  GridBagConstraints.WEST;
		addpanel.add(proTitle, idTextField);
		proTitle.setText(projectTitle.getText());
		proTitle.setEditable(false);
		
		GridBagConstraints contactLabel = new GridBagConstraints();
		contactLabel.gridx = 0;
		contactLabel.gridy = 1;
		contactLabel.weighty =  weightye;
		contactLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Contact Name:"), contactLabel);

		GridBagConstraints contactField = new GridBagConstraints();
		contact = new JTextField(textFieldColunm);
		contactField.gridx = 1;
		contactField.gridy = 1;
		contactField.ipady = 2;
		contactField.ipadx = 190;
		contactField.anchor =  GridBagConstraints.WEST;
		addpanel.add(contact, contactField);
		contact.setText(projectInfo.get(6));

		GridBagConstraints address1Label = new GridBagConstraints();
		address1Label.gridx = 0;
		address1Label.gridy = 2;
		address1Label.weighty =  weightye;
		address1Label.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Address Line 1:"), address1Label);

		GridBagConstraints address1Field = new GridBagConstraints();
		addressline1 = new JTextField(textFieldColunm);
		address1Field.gridx = 1;
		address1Field.gridy = 2;
		address1Field.ipady = 2;
		address1Field.ipadx = 190;
		address1Field.anchor =  GridBagConstraints.WEST;
		addpanel.add(addressline1, address1Field);
	
		GridBagConstraints amountLabel = new GridBagConstraints();
		amountLabel.gridx = 0;
		amountLabel.gridy = 3;
		amountLabel.weighty = weightye;
		amountLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Address Line 2:"), amountLabel);

		GridBagConstraints address2Field = new GridBagConstraints();
		addressline2 = new JTextField(textFieldColunm);
		address2Field.gridx = 1;
		address2Field.gridy = 3;
		address2Field.ipady = 2;
		address2Field.ipadx = 190;
		address2Field.anchor =  GridBagConstraints.WEST;
		addpanel.add(addressline2, address2Field);

	
		GridBagConstraints cityLabel = new GridBagConstraints();
		cityLabel.gridx = 0;
		cityLabel.gridy = 4;
		cityLabel.ipadx = 60;
		cityLabel.weighty = weightye;
		cityLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Town / City:", JLabel.RIGHT), cityLabel);

		GridBagConstraints cityField = new GridBagConstraints();
		town_city = new JTextField(textFieldColunm);
		cityField.gridx = 1;
		cityField.gridy = 4;
		cityField.ipady = 2;
		cityField.ipadx = 190;
		cityField.anchor =  GridBagConstraints.WEST;
		addpanel.add(town_city, cityField);
	
		GridBagConstraints countyLabel = new GridBagConstraints();
		countyLabel.gridx = 0;
		countyLabel.gridy = 5;
		countyLabel.weighty = weightye;
		countyLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("County / State:"), countyLabel);

		GridBagConstraints countyField = new GridBagConstraints();
		county_state = new JTextField(textFieldColunm);
		countyField.ipady = 2;
		countyField.ipadx = 190;
		countyField.gridx = 1;
		countyField.gridy = 5;
		countyField.anchor =  GridBagConstraints.WEST;
		addpanel.add(county_state, countyField);
	
		GridBagConstraints postLabel = new GridBagConstraints();
		postLabel.gridx = 0;
		postLabel.gridy = 6;
		postLabel.weighty = weightye;
		postLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Post Code:"), postLabel);

		GridBagConstraints postField = new GridBagConstraints();
		postCode = new JTextField(textFieldColunm);
		postField.gridx = 1;
		postField.gridy = 6;
		postField.ipady = 2;
		postField.ipadx = 190;
		postField.anchor =  GridBagConstraints.WEST;
		addpanel.add(postCode, postField);
	
		GridBagConstraints countryLabel = new GridBagConstraints();
		countryLabel.gridx = 0;
		countryLabel.gridy = 7;
		countryLabel.weighty = weightye;
		countryLabel.anchor =  GridBagConstraints.EAST;
		addpanel.add(new JLabel("Country:"), countryLabel);

		GridBagConstraints countryField = new GridBagConstraints();
		country = new JTextField(textFieldColunm);
		countryField.gridx = 1;
		countryField.gridy = 7;
		countryField.ipady = 2;
		countryField.ipadx = 190;
		countryField.anchor =  GridBagConstraints.WEST;
		addpanel.add(country, countryField);
	
	//BUTTON BOTTOM
		/*buttonpanel.add(new JLabel(""));
		buttonpanel.add(new JLabel(""));
		save = new JButton("SAVE");
		buttonpanel.add(save);
		exit = new JButton("EXIT");
		buttonpanel.add(exit);
		buttonpanel.add(new JLabel(""));
		buttonpanel.add(new JLabel(""));*/
		
		GridBagConstraints saveButton = new GridBagConstraints();
		save = new JButton("SAVE");
		saveButton.gridx = 2;
		saveButton.gridy = 0;
		buttonpanel.add(save, saveButton);
	
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
		save.addActionListener(this); 
		}
		public String getLine1(){
			if(addressline1.getText().equals(""))
				return new String( "\" \"");
			return addressline1.getText();
		}
	
		public String getLine2(){
			if(addressline2.getText().equals(""))
				return new String( "\" \"");
			return addressline2.getText();
		}
	
		public String getTown(){
			if(town_city.getText().equals(""))
				return new String( "\" \"");
			return town_city.getText();
		}
	
		public String getCounty(){
			if(county_state.getText().equals(""))
				return new String( "\" \"");
			return county_state.getText();
		}
	
		public String getCountry(){
			if(country.getText().equals(""))
				return new String( "\" \"");
			return country.getText();
		}
	
		public String getPostCode(){
			if(postCode.getText().equals(""))
				return new String( "\" \"");
			return postCode.getText();
		}

		public void actionPerformed( ActionEvent e) {  

			if (e.getSource().equals(exit)){
				
				dispose();	
			}
			if (e.getSource().equals(save)){
				addresses.setEditable(true);
				addresses.setText(getLine1()+","+getLine2()+","+getTown()+","+getCounty()+","+getPostCode()+","+getCountry());
				dispose();
			}
		}

	}
	
	public String[] addressinfo(String address){
		return address.split(",");
	}

    public void actionPerformed( ActionEvent e) {  
		//Quote q = null;	q.getRideInner(e);
	if (e.getSource().equals(exit)){
	
	    dispose();	
	}
	
	if (e.getSource().equals(quotebutton)){
	     Quote ds = new Quote("Customer Quote");	
	}
	
	if (e.getSource().equals(address)){
	      new AddAddress("Addresses");	
	}

	if (e.getSource().equals(save)){
	
		
		ArrayList<Object> prodetails =  new ArrayList<Object>();
		prodetails.add(projectTitle.getText());

		prodetails.add(ul.usDateString(sh.dateString(date)));
		prodetails.add(sh.dayString(duration));
		prodetails.add(ul.usDateString(shInner.dateString(validDate)));
		prodetails.add(customerId.getText());

		ArrayList<Object> projectQuote = ul.getArrayList(bean.connect().addOnProject(ul.writeArrayList(prodetails), ul.writeListOfArrayListStr(outerlist)));
		dispose();
		new QuotePdf(projectQuote);
		}

    }

}