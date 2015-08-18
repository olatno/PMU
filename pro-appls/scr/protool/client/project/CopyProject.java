
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
import javax.swing.border.TitledBorder;
/**

*
*

*
* @author <a href="mailto:"></a>
* @version 1.0
*/
public class CopyProject extends JFrame implements ActionListener{
 
    private JTextField projectId;
    private JTextField projectTitle;
    private JTextField companyName;
    private JTextField mobile;
	private JTextField mobile1;
    private SpinnerHelper sh;
	private SpinnerHelper shInner;
	private JSpinner duration;
	private JSpinner date;
	private JComboBox projectStatus;
	private JSpinner validDate;
    private JTextField addresses;
	private JTextField addresses1;
    private JTextField telephone;
    private JTextField email; 
	private JTextField contact;
	private JTextField contact1;
    private JButton save;
    private JButton ok;
    private JButton exit;
	private JButton quotebutton;
	private JButton address;
    private JPanel addpanel;
    private JPanel buttonpanel;
    private JPanel generalPanel;
	private JPanel upperPanel;
    private JPanel busPanel;
    private JPanel titlePanel;
	private JPanel conPane;
    private JPanel borderPanel;
    private JPanel quoPanel;
	private JPanel conPanel;
    private JPanel valPanel;
    private JPanel quaPanel;
	private JPanel statusPanel;
    private Context ctx;
    private String client = "";
    private BeanStud bean;
	private String strValid;
	private final int textFieldColunm = 1;
	private final double weightix = 1.0;
    private final double weightiy = 1.0;
	private final int h_gap = -100;
	private final int v_gap = 4;
	private Utilities ul = new Utilities();
	private Locale locale = Locale.UK;
	private int proId;
	private int quoteId;
	private ArrayList<ArrayList<String>> outerlist = new ArrayList<ArrayList<String>>();
    Container con = getContentPane();
	private ArrayList<Object> editableDetails;
	//private ArrayList<Object> quoteValues;
    
    public CopyProject(String title, int proId){
   	super(title); 
	
	this.proId = proId;
	this.quoteId = quoteId;
	bean = new BeanStud();
	editableDetails = ul.getArrayList(bean.connect().editableProjectDetails(proId));
	//quoteValues = ul.getArrayList(bean.connect().quoteValues(quoteId));
	addpanel = new JPanel();
	buttonpanel = new JPanel();
	upperPanel = new JPanel();
	busPanel = new JPanel();
	titlePanel = new JPanel();
	conPanel = new JPanel();
	generalPanel = new JPanel();
	borderPanel = new JPanel();
	quoPanel = new JPanel();
	valPanel = new JPanel();
	statusPanel = new JPanel();
	quaPanel = new JPanel();
	sh = new SpinnerHelper();
	shInner = new SpinnerHelper();
	
	GridLayout gLayout = new GridLayout(8,2);
	addpanel.setLayout(gLayout);
	FlowLayout lLayout = new FlowLayout();
	lLayout.setHgap(0);
	buttonpanel.setLayout(lLayout);
	borderPanel.setLayout(new BorderLayout());
	generalPanel.setLayout(new GridBagLayout());
	upperPanel.setLayout(new GridBagLayout());
	busPanel.setLayout(new GridBagLayout());
	titlePanel.setLayout(new GridBagLayout());
	conPanel.setLayout(new GridBagLayout());
	quoPanel.setLayout(new GridBagLayout());
	valPanel.setLayout(new GridBagLayout());
	statusPanel.setLayout(new GridBagLayout());
	quaPanel.setLayout(new GridBagLayout());

	ArrayList<String> general = (ArrayList<String>)editableDetails.get(0);
	addpanel.add(new JLabel("Project Title:"));
	projectTitle = new JTextField(textFieldColunm);
	GridBagConstraints titlName = new GridBagConstraints();
	titlName.gridx = 0;
	titlName.gridy = 0;
	titlName.ipady = 2;
	titlName.ipadx = 210;
	titlName.weightx = weightix;
	titlName.weighty = weightiy;
	titlePanel.add(projectTitle, titlName);
	//projectTitle.setText(general.get(0));
	addpanel.add(titlePanel);

	
	addpanel.add(new JLabel("Business Name:"));
	companyName = new JTextField(textFieldColunm);
	GridBagConstraints busName = new GridBagConstraints();
	busName.gridx = 0;
	busName.gridy = 0;
	busName.ipady = 2;
	busName.ipadx = 210;
	busName.weightx = weightix;
	busName.weighty = weightiy;
	busPanel.add(companyName, busName);
	companyName.setText(general.get(1));
	addpanel.add(busPanel);
		
	addpanel.add(new JLabel(""));
	address = new JButton("Add Contact Details");
	address.setFont(new Font("", Font.BOLD, 10));
	GridBagConstraints adContact = new GridBagConstraints();
	adContact.gridx = 0;
	adContact.gridy = 0;
	adContact.ipady = 2;
	adContact.ipadx = 10;
	adContact.insets = new Insets(0, 0, 0, 88);
	conPanel.add(address, adContact);
	addpanel.add(conPanel);
		
	addpanel.add(new JLabel("Quote Date:"));
	date = new JSpinner();
	GridBagConstraints quoteD = new GridBagConstraints();
	quoteD.gridx = 0;
	quoteD.gridy = 0;
	quoteD.ipady = 2;
	quoteD.ipadx = 10;
	quoteD.insets = new Insets(0, 0, 0, 109);
	quoPanel.add(sh.getGenericDate(date), quoteD);
	//quoPanel.add(sh.getGenericDateEdit(date, ul.usDateString((String)general.get(2))), quoteD);
	addpanel.add(quoPanel);
	
	addpanel.add(new JLabel("Valid Until:"));
	validDate = new JSpinner();
	GridBagConstraints validD = new GridBagConstraints();
	validD.gridx = 0;
	validD.gridy = 0;
	validD.ipady = 2;
	validD.ipadx = 10;
	validD.insets = new Insets(0, 0, 0, 109);
	//valPanel.add(shInner.getGenericDateEdit(validDate, ul.usDateString((String)quoteValues.get(0))), validD);
	valPanel.add(shInner.getGenericDate(validDate), validD);
	addpanel.add(valPanel);	
	
	addpanel.add(new JLabel("Project Status:"));
	GridBagConstraints combbox = new GridBagConstraints();
	String[] taskStrings = { "Open", "Continue"};
	projectStatus = new JComboBox(taskStrings);
	combbox.anchor =  GridBagConstraints.EAST;
	combbox.gridx = 0;
	combbox.gridy = 0;
	combbox.ipady = 2;
	combbox.ipadx = 45;
	combbox.insets = new Insets(0, 0, 0, 109);
	statusPanel.add(projectStatus, combbox);
	String cobstr = general.get(3);
	projectStatus.setSelectedItem(cobstr.charAt(0)+cobstr.substring(1).toLowerCase());
	addpanel.add(statusPanel);	
		
	addpanel.add(new JLabel(""));
	quotebutton = new JButton("Add Quote Details");
	quotebutton.setFont(new Font("", Font.BOLD, 10));
	GridBagConstraints adQuote = new GridBagConstraints();
	adQuote.gridx = 0;
	adQuote.gridy = 0;
	adQuote.ipady = 3;
	adQuote.ipadx = 12;
	adQuote.insets = new Insets(0, 0, 0, 93);
	quaPanel.add(quotebutton, adQuote);
	addpanel.add(quaPanel);
	gLayout.setHgap(h_gap);
	gLayout.setVgap(v_gap);
	
	GridBagConstraints upper = new GridBagConstraints();
	upper.gridx = 0;
	upper.gridy = 0;
	upper.weightx = weightix;
	upper.weighty = weightiy;
	upper.insets = new Insets(50, 0, 0, 0);
	upperPanel.add(addpanel, upper);
	borderPanel.add(upperPanel, BorderLayout.PAGE_START );
	borderPanel.setBorder(new Partition(20,"SpecialBilling"));
			 
	save = new JButton("Save");
	buttonpanel.add(save);
	
	exit = new JButton("Exit");
	buttonpanel.add(exit);

	GridBagConstraints lower = new GridBagConstraints();
	lower.gridx = 0;
	lower.gridy = 0;
	lower.weighty = weightix;
	lower.insets = new Insets(0, 310, 50, 0);
	generalPanel.add(buttonpanel, lower);
	
	con.add(borderPanel, BorderLayout.CENTER);
	con.add(generalPanel, BorderLayout.SOUTH);

	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(500, 600);
	setVisible(true); 
	setResizable(false);
	setIconImage(ul.mainImage("images/src/gui_icon.png"));
	
	exit.addActionListener(this);  
	save.addActionListener(this); 
	quotebutton.addActionListener(this);
	address.addActionListener(this);

	projectId.setText(String.valueOf(proId));  

    }
	
	
	public class Quote extends  JFrame implements MouseListener , ActionListener, AdjustmentListener{

    Container con = getContentPane();
    private JPanel headerpanel;
    private JPanel contentpanel;
	private JPanel footerpanel;
    private JPanel mainpanel;
	private JPanel buttomGridLayout;
    private JPanel buttomFlowLayout;
	private JPanel scrollAppearPanel;
	private JPanel scrollDisAppearPanel;
	private JPanel headerPanels;//might be needed
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
    private final double weightix = 1.0;
	private final double weightiy = 1.0;
	private final int initialRow = 22;
	private final int textFieldColunm = 1;
	private int quantityScroll = -283;
	private int desciptScroll = 48;
	private int priceScroll = 89;
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
	private JButton save = null;

    public Quote(String title) {
	super(title);

	headerpanel = new JPanel();
	contentpanel = new JPanel();
	footerpanel = new JPanel();
	mainpanel = new JPanel();
	scrollDisAppearPanel = new JPanel();
	scrollAppearPanel = new JPanel();
	headerPanels = new JPanel();
	buttomFlowLayout = new JPanel();
	buttomGridLayout = new JPanel();

	contentpanel.setLayout(new GridBagLayout());
	scrollDisAppearPanel.setLayout(new GridBagLayout());
	scrollAppearPanel.setLayout(new GridBagLayout());
	GridLayout headerGrid = new GridLayout(1,2);
	headerGrid.setHgap(65);
	headerpanel.setLayout(headerGrid);
	buttomFlowLayout.setLayout(new FlowLayout());
	buttomGridLayout.setLayout(new GridBagLayout());
	headerPanels.setLayout(new GridBagLayout());
	headerPanels.setPreferredSize(new Dimension(480, 10));
	footerpanel.setLayout(new GridBagLayout());
	mainpanel.setLayout(new BorderLayout());

	
	GridBagConstraints descriLabel = new GridBagConstraints();
	descriLabel.gridx = 0;
	descriLabel.gridy = 0;
	descriLabel.weightx = weightix;
	descriLabel.anchor =  GridBagConstraints.LINE_START;
	descriLabel.insets = new Insets(0, 48, 7, 0);
    headerPanels.add(new JLabel("Decription:"), descriLabel);
	
    headerpanel.add(new JLabel("Quantity:"));
    headerpanel.add(new JLabel("Unit Price:"));
	
	GridBagConstraints qtypriLabel = new GridBagConstraints();
	qtypriLabel.gridx = 1;
	qtypriLabel.gridy = 0;
	qtypriLabel.insets = new Insets(0, 0, 0, 90);
	headerPanels.add(headerpanel, qtypriLabel);
	
	makeTextField(1);

	GridBagConstraints totalLabel = new GridBagConstraints();
	totalLabel.gridx = 0;
	totalLabel.gridy = 0;
	totalLabel.insets = new Insets(0, 0, 0, 10);
    footerpanel.add(new JLabel("Total:"), totalLabel);
	
	GridBagConstraints priceLabel = new GridBagConstraints();
	totalLabels = new JTextField(textFieldColunm);
	priceLabel.gridx = 1;
	priceLabel.gridy = 0;
	priceLabel.ipadx = 60;
	priceLabel.insets = new Insets(0, 0, 0, 11);
    footerpanel.add(totalLabels , priceLabel);
	totalLabels.setText(new String("£0.00"));
	totalLabels.setEditable(false);
	
	submit = new JButton("Save");
	GridBagConstraints submitquote = new GridBagConstraints();
	submitquote.gridx = 2;
	submitquote.gridy = 0;
	submitquote.insets = new Insets(0, 0, 0, 0);
	footerpanel.add(submit, submitquote);
	
	dispose_exit = new JButton("Exit");
	GridBagConstraints exitquote = new GridBagConstraints();
	exitquote.gridx = 3;
	exitquote.gridy = 0;
	//submitquote.insets = new Insets(0, 0, 0, 60);
	footerpanel.add(dispose_exit, exitquote);
	buttomFlowLayout.add(footerpanel);
	
	GridBagConstraints buttomGrid = new GridBagConstraints();
	buttomGrid.gridx = 0;
	buttomGrid.gridy = 0;
	buttomGrid.insets = new Insets(0, 0, 0, -290);
	buttomGridLayout.add(buttomFlowLayout, buttomGrid);
	
	mainpanel.add(contentpanel, BorderLayout.NORTH);
	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	ledgerViewScroll = new JScrollPane(mainpanel, v,h);
	ledgerViewScroll.setColumnHeaderView(headerPanels);
	ledgerViewScroll.setBorder(new Partition(20,"Ledger"));
	con.add(ledgerViewScroll);
	con.add(buttomGridLayout, "South");
	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(650, 600);
	setVisible(true); 
	setResizable(false);
	setIconImage(ul.mainImage("images/src/gui_icon.png"));
 		
	dispose_exit.addActionListener(this);
	submit.addActionListener(this);
	add.addMouseListener(this); 
	remove.addMouseListener(this);
	ledgerViewScroll.getVerticalScrollBar().addAdjustmentListener(this); 

    }
	
	public void makeTextField(int row){

		GridBagConstraints typeTextField1 = new GridBagConstraints();
		description = new JTextField(textFieldColunm);
		typeTextField1.gridx = 0;
		typeTextField1.gridy = row;
		typeTextField1.ipady = ipadiy;
		typeTextField1.ipadx = 250;
		typeTextField1.insets = new Insets(0, 0, 5, 40);
		typeTextField1.anchor =  GridBagConstraints.WEST;
		contentpanel.add(description, typeTextField1);
		descript.add(description);
		
		GridBagConstraints typeTextField2 = new GridBagConstraints();
		quantity = new JTextField(textFieldColunm);
		typeTextField2.gridx = 1;
		typeTextField2.gridy = row;
		typeTextField2.ipady = ipadiy;
		typeTextField2.ipadx = 60;
		typeTextField2.insets = new Insets(0, 0, 5, 40);
		typeTextField2.anchor =  GridBagConstraints.WEST;
		contentpanel.add(quantity, typeTextField2);
		quant.add(quantity);
			
		GridBagConstraints typeTextField3 = new GridBagConstraints();
		unitprice = new JTextField(textFieldColunm);
		typeTextField3.gridx = 2;
		typeTextField3.gridy = row;
		typeTextField3.ipady = ipadiy;
		typeTextField3.ipadx = 60;
		typeTextField3.insets = new Insets(0, 0, 5, 0);
		typeTextField3.anchor =  GridBagConstraints.WEST;
		contentpanel.add(unitprice, typeTextField3);
		uniprice.add(unitprice);
		
		GridBagConstraints addLabel = new GridBagConstraints();
		add = new JLabel(ul.iconImage("images/src/add.png"));
		addLabel.gridx = 3;
		addLabel.gridy = row;
		addLabel.anchor =  GridBagConstraints.NORTHEAST;
		addLabel.insets = new Insets(3, 0, 0, 0);
		contentpanel.add(add, addLabel);
		
		GridBagConstraints removeLabel = new GridBagConstraints();
		remove = new JLabel(ul.iconImage("images/src/remove.png"));
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
			//quote.setText(string);
			//generate pdf file for quote here
			dispose();	
		}
		if (e.getSource().equals(dispose_exit)){
			//quote.setText("");
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
			//quote.setText(ul.getStringInCurrency(Results()));
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
			//quote.setText(ul.getStringInCurrency(Results()));
		}

	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e){} 
	public void mouseReleased(MouseEvent e) {}
	public void adjustmentValueChanged(AdjustmentEvent e) {

	  if(ledgerViewScroll.getVerticalScrollBar().isVisible()){
		GridBagConstraints descriLabel = new GridBagConstraints();
		descriLabel.gridx = 0;
		descriLabel.gridy = 0;
		descriLabel.weightx = weightix;
		descriLabel.anchor =  GridBagConstraints.LINE_START;
		descriLabel.insets = new Insets(0, 40, 7, 0);
		scrollAppearPanel.add(new JLabel("Decription:"), descriLabel);
		
		GridBagConstraints qtypriLabel = new GridBagConstraints();
		qtypriLabel.gridx = 1;
		qtypriLabel.gridy = 0;
		qtypriLabel.insets = new Insets(0, 0, 0, 80);
		scrollAppearPanel.add(headerpanel, qtypriLabel);
		ledgerViewScroll.setColumnHeaderView(scrollAppearPanel);
	  }
	  
	  else{
		GridBagConstraints descriLabel = new GridBagConstraints();
		descriLabel.gridx = 0;
		descriLabel.gridy = 0;
		descriLabel.weightx = weightix;
		descriLabel.anchor =  GridBagConstraints.LINE_START;
		descriLabel.insets = new Insets(0, 48, 7, 0);
		scrollDisAppearPanel.add(new JLabel("Decription:"), descriLabel);
		
		GridBagConstraints qtypriLabel = new GridBagConstraints();
		qtypriLabel.gridx = 1;
		qtypriLabel.gridy = 0;
		qtypriLabel.insets = new Insets(0, 0, 0, 90);
		scrollDisAppearPanel.add(headerpanel, qtypriLabel);
		ledgerViewScroll.setColumnHeaderView(scrollDisAppearPanel);
	  }
	  
  }
	
}


	public class AddAddress extends JFrame implements ActionListener, ItemListener{//implements ActionListener{
   
		private JTextField addressline1;
		private JTextField addressline2;
		private JTextField town_city;
		private JTextField county_state;
		private JTextField postCode;
		private JTextField country;
		private JTextField addressline3;
		private JTextField addressline4;
		private JTextField town_city1;
		private JTextField county_state1;
		private JTextField postCode1;
		private JTextField country1;
		private JTextField proTitle;
		//private JTextField telephone;
		//private JTextField mobile;
		//private JTextField mobile1;
		//private JTextField email;
		//private JTextField contact;
		private JButton save;
		private JButton exit;
		private JCheckBox siteCheck;
		private JPanel addpanel;
		private JPanel buttonpanel;
		private JPanel generalPanel;
		private String client = "";
		private final double weightye = 0.5;
		private final int textFieldColunm = 1;
		private final int h_gap = -150;
		private final int v_gap = 2;
		Container con = getContentPane();
    
		public AddAddress(String title){//, int proId
		super(title); 
		addpanel = new JPanel();
		buttonpanel = new JPanel();
		generalPanel = new JPanel();
		//sh = new SpinnerHelper();
		addresses = new JTextField(textFieldColunm);//hidden textfield for contacts details
		addresses1 = new JTextField(textFieldColunm);//hidden textfield for contacts details
		addpanel.setLayout(new GridBagLayout());
		buttonpanel.setLayout(new GridBagLayout());
		generalPanel.setLayout(new BorderLayout());

		GridBagConstraints billPane = new GridBagConstraints();
		 billPane.gridx = 0;
		 billPane.gridy = 0;
		 billPane.insets = new Insets(0, 0, 0, 0);
		 billPane.gridwidth = 3;
		 addpanel.add(billingAddress(), billPane);

		GridBagConstraints sitePane = new GridBagConstraints();
		 sitePane.gridx = 0;
		 sitePane.gridy = 1;
		 sitePane.insets = new Insets(0, 0, 0, 0);
		 sitePane.gridwidth = 3;
		 addpanel.add(siteAddress(), sitePane);
		 
		GridBagConstraints saveButton = new GridBagConstraints();
		save = new JButton("Save");
		saveButton.gridx = 2;
		saveButton.gridy = 0;
		buttonpanel.add(save, saveButton);
	
		GridBagConstraints exitButton = new GridBagConstraints();
		exit = new JButton("Exit");
		exitButton.gridx = 3;
		exitButton.gridy = 0;
		exitButton.insets = new Insets(0, 0, 0, 0);
		buttonpanel.add(exit, exitButton);
	
		generalPanel.add(addpanel, BorderLayout.CENTER);
		generalPanel.setBorder(new Partition(20,"SpecialBilling"));
		con.setLayout(new BorderLayout());
		con.add("Center",generalPanel);
		con.add(buttonpanel, BorderLayout.AFTER_LAST_LINE);
		pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 600);
		setResizable(false);
		setVisible(true); 
		setIconImage(ul.mainImage("images/src/gui_icon.png"));
		exit.addActionListener(this);  
		save.addActionListener(this); 
	}
	
	public JPanel siteAddress(){
		ArrayList<String> siteDetails = (ArrayList<String>)editableDetails.get(2);
		JPanel pane = new JPanel();
		TitledBorder pane1 = BorderFactory.createTitledBorder(null,
                              "Site Address",
                              TitledBorder.DEFAULT_JUSTIFICATION,
                              TitledBorder.DEFAULT_POSITION,
                              new Font("", Font.BOLD, 12));
		GridLayout gLayout = new GridLayout(8,2);
		pane.setBorder(pane1);
		gLayout.setVgap(v_gap);
		gLayout.setHgap(h_gap);
		pane.setLayout(gLayout);
		pane.setPreferredSize(new Dimension(325, 200));
		
		pane.add(new JLabel("Contact Name:"));
		contact1 = new JTextField(textFieldColunm);
		pane.add(contact1);
		
		pane.add(new JLabel("Address Line 1:"));
		addressline3 = new JTextField(textFieldColunm);
		//addressline3.setText(siteDetails.get(0));
		pane.add(addressline3);

		pane.add(new JLabel("Address Line 2:"));
		addressline4 = new JTextField(textFieldColunm);
		/*if(!siteDetails.get(1).equals("")){
			addressline4.setText(siteDetails.get(1));
		}*/
		pane.add(addressline4);

		pane.add(new JLabel("Town:"));
		town_city1 = new JTextField(textFieldColunm);
		//town_city1.setText(siteDetails.get(2));
		pane.add(town_city1);
	
		pane.add(new JLabel("County:"));
		county_state1 = new JTextField(textFieldColunm);
		//county_state1.setText(siteDetails.get(3));
		pane.add(county_state1);
	
		pane.add(new JLabel("Post Code:"));
		postCode1 = new JTextField(textFieldColunm);
		//postCode1.setText(siteDetails.get(4));
		pane.add(postCode1);

		pane.add(new JLabel("Country:"));
		country1 = new JTextField(textFieldColunm);
		//country1.setText(siteDetails.get(5));
		pane.add(country1);
		
		pane.add(new JLabel("Mobile:"));
		mobile1 = new JTextField(textFieldColunm);
		//mobile1.setText(billDetails.get(8));
		pane.add(mobile1);
		
		return pane;
	
	}
	
	public JPanel siteCheckedBox(){
		JPanel addressType = new JPanel();
		addressType.setLayout(new FlowLayout());
		addressType.add(new JLabel("Same Site Address? "));
		siteCheck = new JCheckBox();
		addressType.add(siteCheck);
		siteCheck.addItemListener(this);
		return addressType;
	}
	
	
	public JPanel billingAddress(){
		ArrayList<String> billDetails = (ArrayList<String>)editableDetails.get(1);
		JPanel pane = new JPanel();
		JPanel paneb = new JPanel();
		
		TitledBorder pane2 = BorderFactory.createTitledBorder(null,
                              "Billing Address",
                              TitledBorder.DEFAULT_JUSTIFICATION,
                              TitledBorder.DEFAULT_POSITION,
                              new Font("", Font.BOLD, 12));
		GridLayout gLayout = new GridLayout(11,2);
		paneb.setLayout(new BorderLayout());
		paneb.setBorder(pane2);
		gLayout.setVgap(v_gap);
		gLayout.setHgap(h_gap);
		pane.setLayout(gLayout);
		pane.setPreferredSize(new Dimension(315, 250));
		
		pane.add(new JLabel("Project Title:"));
		proTitle = new JTextField(textFieldColunm);
		pane.add(proTitle);
		proTitle.setText(projectTitle.getText());
		proTitle.setEditable(false);
		
		pane.add(new JLabel("Contact Name:"));
		contact = new JTextField(textFieldColunm);
		contact.setText(billDetails.get(0));
		contact.setEditable(false);
		pane.add(contact);
		
		pane.add(new JLabel("Address Line 1:"));
		addressline1 = new JTextField(textFieldColunm);
		addressline1.setText(billDetails.get(1));
		addressline1.setEditable(false);
		pane.add(addressline1);

		pane.add(new JLabel("Address Line 2:"));
		addressline2 = new JTextField(textFieldColunm);
		if(!billDetails.get(2).equals("")){
			addressline2.setText(billDetails.get(2));
		}
		addressline2.setEditable(false);
		pane.add(addressline2);

		pane.add(new JLabel("Town:"));
		town_city = new JTextField(textFieldColunm);
		town_city.setText(billDetails.get(3));
		town_city.setEditable(false);
		pane.add(town_city);
	
		pane.add(new JLabel("County:"));
		county_state = new JTextField(textFieldColunm);
		county_state.setText(billDetails.get(4));
		county_state.setEditable(false);
		pane.add(county_state);
	
		pane.add(new JLabel("Post Code:"));
		postCode = new JTextField(textFieldColunm);
		postCode.setText(billDetails.get(5));
		postCode.setEditable(false);
		pane.add(postCode);

		pane.add(new JLabel("Country:"));
		country = new JTextField(textFieldColunm);
		country.setText(billDetails.get(6));
		country.setEditable(false);
		pane.add(country);
		
		pane.add(new JLabel("Telephone:"));
		telephone = new JTextField(textFieldColunm);
		telephone.setText(billDetails.get(7));
		telephone.setEditable(false);
		pane.add(telephone);
		
		pane.add(new JLabel("Mobile:"));
		mobile = new JTextField(textFieldColunm);
		mobile.setText(billDetails.get(8));
		mobile.setEditable(false);
		pane.add(mobile);
		
		pane.add(new JLabel("Email:"));
		email = new JTextField(textFieldColunm);
		email.setText(billDetails.get(9));
		email.setEditable(false);
		pane.add(email);
		
		paneb.add("Center", pane);
		paneb.add("South", siteCheckedBox());
		
		return paneb;
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
		
		public String getContact(){
			if(contact.getText().equals(""))
				return new String( "\" \"");
			return contact.getText();
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
		
				public String getLine3(){
			if(addressline3.getText().equals(""))
				return new String( "\" \"");
			return addressline1.getText();
		}
	
		public String getLine4(){
			if(addressline4.getText().equals(""))
				return new String( "\" \"");
			return addressline2.getText();
		}
	
		public String getTown1(){
			if(town_city1.getText().equals(""))
				return new String( "\" \"");
			return town_city1.getText();
		}
	
		public String getCounty1(){
			if(county_state1.getText().equals(""))
				return new String( "\" \"");
			return county_state1.getText();
		}
	
		public String getCountry1(){
			if(country1.getText().equals(""))
				return new String( "\" \"");
			return country1.getText();
		}
	
		public String getPostCode1(){
			if(postCode1.getText().equals(""))
				return new String( "\" \"");
			return postCode1.getText();
		}
		
		public void itemStateChanged(ItemEvent e){
			if(e.getSource().equals(siteCheck)){
				if(e.getStateChange() == ItemEvent.SELECTED){
					contact1.setText(contact.getText());
					addressline3.setText(addressline1.getText());
					addressline4.setText(addressline2.getText());
					town_city1.setText(town_city.getText());
					county_state1.setText(county_state.getText());
					postCode1.setText(postCode.getText());
					country1.setText(country.getText());
					mobile1.setText(mobile.getText());
				}
				else{
					if(!contact1.getText().equals("")){
						contact1.setText("");
					}
					
					if(!addressline3.getText().equals("")){
						addressline3.setText("");
					}
					if(!addressline4.getText().equals("")){
						addressline4.setText("");
					}
					if(!town_city1.getText().equals("")){
						town_city1.setText("");
					}
					if(!county_state1.getText().equals("")){
						county_state1.setText("");
					}
					
					if(!postCode1.getText().equals("")){
						postCode1.setText("");
					}
					
					if(!country1.getText().equals("")){
						country1.setText("");
					}
					
					if(!mobile1.getText().equals("")){
						mobile1.setText("");
					}
				
				}
			}
		}
		
		public void actionPerformed( ActionEvent e) {  

			if (e.getSource().equals(exit)){
				dispose();	
			}
			if (e.getSource().equals(save)){
				//addresses.setEditable(true);
				addresses.setText(getLine1()+","+getLine2()+","+getTown()+","+getCounty()+","+getPostCode()+","+getCountry());
				addresses1.setText(getLine3()+","+getLine4()+","+getTown1()+","+getCounty1()+","+getPostCode1()+","+getCountry1());//need to include mobile in database
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
		try{
	      new AddAddress("Edit Contact Details");
		  }
		   catch (Exception ex) { 
		JOptionPane.showMessageDialog(this, "Quote PDF not generated!" +ex.toString(),   
                        "Error!", JOptionPane.INFORMATION_MESSAGE);
		
	}
	}

	if (e.getSource().equals(save)){
		//ArrayList<String> billDetails = (ArrayList<String>)editableDetails.get(1);
		ArrayList<String> siteDetails = (ArrayList<String>)editableDetails.get(2);
		ArrayList<String> siteContact = new ArrayList<String>();
		//ArrayList<String> billContact = new ArrayList<String>();
		ArrayList<Object> prodetails =  new ArrayList<Object>();
		prodetails.add(String.valueOf(proId));
		prodetails.add(projectTitle.getText());
		//prodetails.add(companyName.getText());
		
		/*if(addresses != null)
			prodetails.add(addressinfo(addresses.getText()));
		else{
			String unchangeBillAddress = billDetails.get(1)+","+billDetails.get(2)+","+billDetails.get(3)
										+","+billDetails.get(4)+","+billDetails.get(5)+","+billDetails.get(6);
			prodetails.add(addressinfo(unchangeBillAddress));
		
		} 
		
		if(telephone != null)
			billContact.add(telephone.getText());
		else{billContact.add(billDetails.get(7));}
		
		if(mobile != null)
			billContact.add(mobile.getText());
		else{billContact.add(billDetails.get(8));}
		
		if(email != null)
			billContact.add(email.getText());
		else{billContact.add(billDetails.get(9));}
		if(contact != null)
			billContact.add(contact.getText());
		else{billContact.add(billDetails.get(0));}
		prodetails.add(billContact);*/
		
		prodetails.add(ul.usDateString(sh.dateString(date)));
		prodetails.add(ul.usDateString(shInner.dateString(validDate)));
		
		if(addresses1 != null)
			prodetails.add(addressinfo(addresses1.getText()));
		else{
			String unchangeSiteAddress = siteDetails.get(0)+","+siteDetails.get(1)+","+siteDetails.get(2)
										+","+siteDetails.get(3)+","+siteDetails.get(4)+","+siteDetails.get(5);
			prodetails.add(addressinfo(unchangeSiteAddress));
			}

		if(mobile1 != null)
			siteContact.add(mobile1.getText());
		else{siteContact.add(siteDetails.get(7));}	
		if(contact1 != null)
			siteContact.add(contact1.getText());
		else{siteContact.add(siteDetails.get(6));}	
		prodetails.add(siteContact);
		
		prodetails.add((String)projectStatus.getSelectedItem());
		prodetails.add("30 days");//Payment Term moved to PaymentIn view - sh.dayString(duration)
		
		//String[] genericInfo = {String.valueOf(quoteId), ul.usDateString(shInner.dateString(validDate))};quoteValues
		 ArrayList<Object> copyPreviuorDetails = ul.getArrayList(bean.connect().addOnProject(ul.writeArrayList(prodetails), ul.writeListOfArrayListStr(outerlist)));
		
		//bean.connect().copyProjectQuote(ul.writeArrayList(prodetails), ul.writeStringArray(genericInfo), ul.writeListOfArrayListStr(outerlist)); 

		dispose();
		new QuotePdf(copyPreviuorDetails);

	
	}
 

    }

}