
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

/**

*
*

*
* @author <a href="mailto:"></a>
* @version 1.0
*/
public class AddPaymentin extends JFrame implements ActionListener{//implements ActionListener{
    private JTextField paymentId;
	private JTextField accounts;
	private JComboBox vat;
    private Choice posted;
    private JTextField invoiceOut;
    private JTextField amount;
	private JTextField projectName;
    private JSpinner date;
	private JSpinner paymentTerm;
    private JTextField description;
	private JTextField include;
	private JRadioButton quoteradion;
	private JRadioButton newListing;
	private JButton listingButton; 
    private JButton save;
    private JButton ok;
    private JButton exit;
    private JPanel addpanel;//G layout
    private JPanel buttonpanel;// F layout
    private JPanel generalPanel;// GB layout
	private JPanel titlePanel;// GB layout
    private JPanel projectNamePanel;// GB layout
    private JPanel invoicePanel;// GB layout
	private JPanel datePanel;// GB layout
    private JPanel lqRadioPanel1;// GB layout
	private JPanel lqRadioPanel2; //G layout
    private JPanel lvGridBagPanel;// GB layout
	private JPanel lvFlowPanel;// F layout
	private JPanel termPanal;// GB layout
    private JPanel upperPanel;// GB layout
    private JPanel borderPanel;// B layout
	private JPanel vatPanel;
	private JPanel amtlisPanel;
    private Context ctx;
	private   SpinnerHelper sh;
	private Utilities ul = new Utilities();
    private  final int textfieldrow = 1;
    private String client = "";
	private String[] strArray;
    private BeanStud bean;
	private PaymentInUtilities pu = new PaymentInUtilities();
	private final double weightye = 0.5;
	private final int h_gap = -20;
	private final int v_gap = 4;
	private final double weightix = 1.0;
    private final double weightiy = 1.0;
	private final int ipadxx = 130;
	private int projectIds;
	private Hashtable<String, Integer> accountname = null;
	//private Vector<Vector<String>> outerlist = new Vector<Vector<String>>();
	private ArrayList<ArrayList<String>> outerlist = new ArrayList<ArrayList<String>>();
    Container con = getContentPane();
    
    public AddPaymentin(String title, int projectIds, String projectTitle){//, int proId , String quoteamount
   	super(title); 
	this.projectIds=projectIds;
	bean = new BeanStud();
	addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();
	borderPanel = new JPanel();
	upperPanel = new JPanel();
	titlePanel = new JPanel();
	projectNamePanel = new JPanel();
	invoicePanel = new JPanel();
	datePanel = new JPanel();
	lqRadioPanel1 = new JPanel();
	lqRadioPanel2 = new JPanel();
	lvGridBagPanel = new JPanel();
	lvFlowPanel = new JPanel();
	termPanal = new JPanel();
	vatPanel = new JPanel();
	amtlisPanel = new JPanel();
	sh = new SpinnerHelper();
	//Panels
	GridLayout gLayout = new GridLayout(8,2);
	addpanel.setLayout(gLayout);
	FlowLayout lLayout = new FlowLayout();
	lLayout.setHgap(0);
	buttonpanel.setLayout(lLayout);
	borderPanel.setLayout(new BorderLayout());
	generalPanel.setLayout(new GridBagLayout());
	upperPanel.setLayout(new GridBagLayout());
	titlePanel.setLayout(new GridBagLayout());
	projectNamePanel.setLayout(new GridBagLayout());
	invoicePanel.setLayout(new GridBagLayout());
	datePanel.setLayout(new GridBagLayout());
	lqRadioPanel1.setLayout(new GridBagLayout());
	GridLayout rLayout = new GridLayout(1,2);
	lqRadioPanel2.setLayout(rLayout);
	lvGridBagPanel.setLayout(new GridBagLayout());
	termPanal.setLayout(new GridBagLayout());
	vatPanel.setLayout(new GridBagLayout());
	amtlisPanel.setLayout(new GridBagLayout());


	addpanel.add(new JLabel("Project Title:"));
	
	GridBagConstraints projectTextField = new GridBagConstraints();
	projectName = new JTextField(textfieldrow);
	projectTextField.gridx = 0;
	projectTextField.gridy = 0;
	projectTextField.ipady = 2;
	projectTextField.ipadx = ipadxx;
	titlePanel.add(projectName, projectTextField);
	projectName.setEditable(false);
	projectName.setText(projectTitle);
	addpanel.add(titlePanel);
	addpanel.add(new JLabel("Account Name:"));

	GridBagConstraints choiceField = new GridBagConstraints();
	accounts = new JTextField(textfieldrow);
	String strCon = ul.readString(bean.connect().genericAccount(40010));
	strArray = strCon.split("\\.");
	choiceField.gridx = 0;
	choiceField.gridy = 0;
	choiceField.ipady = 2;
	choiceField.ipadx = ipadxx;
	projectNamePanel.add(accounts, choiceField);
	accounts.setText(strArray[1]);
	accounts.setEditable(false);
	addpanel.add(projectNamePanel);

	addpanel.add(new JLabel("Invoice No:"));

	GridBagConstraints invoiceOutTextField = new GridBagConstraints();
	invoiceOut = new JTextField(textfieldrow);
	invoiceOutTextField.ipady = 2;
	invoiceOutTextField.ipadx = ipadxx;
	invoiceOutTextField.gridx = 0;
	invoiceOutTextField.gridy = 0;
	//invoiceOutTextField.anchor =  GridBagConstraints.WEST;
	invoicePanel.add(invoiceOut, invoiceOutTextField);
	addpanel.add(invoicePanel);
	
	if(pu.paymentInvoice(bean.connect().getPaymentInfo(projectIds))!= null){
		invoiceOut.setText(pu.paymentInvoice(bean.connect().getPaymentInfo(projectIds)));
	}
	
	addpanel.add(new JLabel("Invoice Date:"));

	GridBagConstraints date1TextField = new GridBagConstraints();
	date = new JSpinner();
	date1TextField.gridx = 0;
	date1TextField.gridy = 0;
	date1TextField.ipady = 2;
	date1TextField.ipadx = 40;
	datePanel.add(sh.getGenericDate(date), date1TextField);
	addpanel.add(datePanel);
	
	addpanel.add(new JLabel("Payment Term:"));
	
	GridBagConstraints paymentField = new GridBagConstraints();
	paymentTerm = new JSpinner();
	paymentField.gridx = 0;
	paymentField.gridy = 0;
	paymentField.ipady = 2;
	paymentField.ipadx = 90;
	termPanal.add(sh.getDaysSpinner(paymentTerm), paymentField);// need to be set to project.getPaymentTerm value
	addpanel.add(termPanal);
	

	addpanel.add(new JLabel("Create From:"));
	
	quoteradion = new JRadioButton("Quote");
	quoteradion.setSelected(true);
	lqRadioPanel2.add(quoteradion);
	
	newListing = new JRadioButton("Listing");
	lqRadioPanel2.add(newListing);
	rLayout.setHgap(40);
	ButtonGroup groups = new ButtonGroup();
    groups.add(quoteradion);
    groups.add(newListing);
	
	GridBagConstraints howButton = new GridBagConstraints();
	howButton.gridx = 0;
	howButton.gridy = 0;
	lqRadioPanel1.add(lqRadioPanel2, howButton);
	addpanel.add(lqRadioPanel1);
	
	addpanel.add(new JLabel(""));
	
	GridBagConstraints amouthLabel = new GridBagConstraints();
	listingButton = new JButton("Listing..");
	amouthLabel.gridx = 0;
	amouthLabel.gridy = 0;
	amouthLabel.ipady = 2;
	amouthLabel.ipadx = 5;
	amtlisPanel.add(listingButton, amouthLabel);
	listingButton.setEnabled(false);
	
	GridBagConstraints amouthField = new GridBagConstraints();
	amount = new JTextField(textfieldrow);
	amouthField.gridx = 1;
	amouthField.gridy = 0;
	amouthField.ipady = 2;
	amouthField.ipadx = 55;
	amtlisPanel.add(amount, amouthField);
	amount.setEditable(false);
	amount.setText(ul.getStringInCurrency(pu.getTotalQuote(bean.connect().getQuoteTotals(projectIds))));
	
	GridBagConstraints amtlist = new GridBagConstraints();
	amtlist.gridx = 0;
	amtlist.gridy = 0;
	lvGridBagPanel.add(amtlisPanel, amtlist);
	addpanel.add(lvGridBagPanel);
	

	addpanel.add(new JLabel("VAT:"));

	GridBagConstraints vatTextField = new GridBagConstraints();
	String[] taskStrings = { "None", "20%"};
	vat = new JComboBox(taskStrings); //temporate component, must be taking from global variable and uneditable textfield
	vatTextField.gridx = 0;
	vatTextField.gridy = 0;
	vatTextField.ipady = 2;
	vatTextField.ipadx = 95;
	//vatTextField.anchor =  GridBagConstraints.WEST;
	vatPanel.add(vat, vatTextField);
	addpanel.add(vatPanel);
	

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
	
	//BUTTON BOTTOM
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
	setResizable(false);
	setVisible(true); 
	setIconImage(ul.mainImage("images/src/gui_icon.png"));
	
	exit.addActionListener(this); 
	save.addActionListener(this); 
	listingButton.addActionListener(this); 
	quoteradion.addActionListener(this); 
	newListing.addActionListener(this); 

    }
	
	public class EditQuotes extends  JFrame implements MouseListener , ActionListener, AdjustmentListener{
    Container con = getContentPane();
    private JPanel headerpanel;
    private JPanel contentpanel;
	private JPanel footerpanel;
    private JPanel mainpanel;
	private JPanel scrollAppearPanel;
	private JPanel scrollDisAppearPanel;
	private JPanel headerPanels;//might be needed
    private JLabel total;
	private JLabel add;
	private JPanel buttomGridLayout;
    private JPanel buttomFlowLayout;
	private JTextField totalLabels;	
	private JButton update;
	private JButton dispose_exit;
	private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int ipadiyb = 2;
    private final int ipadixb = 4;
    private final double weightix = 2.0;
	private final double weightiy = 1.0;
	private final int initialRow = 22;
	private final int textFieldColunm = 1;
    private JScrollPane ledgerViewScroll; 
	private Utilities ul = new Utilities();
	//private JSpinner validDate;
	private SpinnerHelper sh;
	private PaymentInUtilities pu = new PaymentInUtilities();
	private ClassLoader cl = this.getClass().getClassLoader();
	private ArrayList<JTextField> descript = new ArrayList<JTextField>();
	private ArrayList<JTextField> quant = new ArrayList<JTextField>();
	private ArrayList<JTextField> uniprice = new ArrayList<JTextField>();
	private ArrayList<JLabel> label = new ArrayList<JLabel>();
	//private ArrayList<ArrayList<String>> outerlist = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<Object>> arrayListOfQuote ;
	//private ArrayList<Object> quoteValues;
	private  int click;	
	private int index = 0;
	private int quoteId;
	private BeanStud bean;

    public EditQuotes(String title) {
	super(title);

	
	sh = new SpinnerHelper();
	bean = new BeanStud();
	//quoteValues = ul.getArrayList(bean.connect().quoteValues(quoteId));
	//arrayListOfQuote = (ArrayList<ArrayList<String>>)quoteValues.get(1);
	arrayListOfQuote = pu.getArrayListOfArrayList(bean.connect().getQuoteValues(projectIds));

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

	
	if(arrayListOfQuote.isEmpty()){
		click  = 1;
		makeTextField(1);
		
	}
	else{
		click = arrayListOfQuote.size()-1;
		for(int i = 0; i < arrayListOfQuote.size(); i++){

			if(i > 0 && i < arrayListOfQuote.size()){
				contentpanel.remove(add);
			}
		
			makeTextField(i);
		}
	}

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
	
	update = new JButton("Save");
	GridBagConstraints submitquote = new GridBagConstraints();
	submitquote.gridx = 2;
	submitquote.gridy = 0;
	submitquote.insets = new Insets(0, 0, 0, 0);
	footerpanel.add(update, submitquote);
	
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
	update.addActionListener(this);
	ledgerViewScroll.getVerticalScrollBar().addAdjustmentListener(this); 
    }
	
	public void makeTextField(int row){
		
		GridBagConstraints typeTextField1 = new GridBagConstraints();
		JTextField description = new JTextField(textFieldColunm);
		typeTextField1.gridx = 0;
		typeTextField1.gridy = row;
		typeTextField1.ipady = ipadiy;
		typeTextField1.ipadx = 250;
		typeTextField1.insets = new Insets(0, 0, 5, 40);
		typeTextField1.anchor =  GridBagConstraints.WEST;
		contentpanel.add(description, typeTextField1);
		if(row < arrayListOfQuote.size()){
			description.setText(String.valueOf(arrayListOfQuote.get(row).get(0)));
		}
		descript.add(description);
	
		GridBagConstraints typeTextField2 = new GridBagConstraints();
		JTextField quantity = new JTextField(textFieldColunm);
		typeTextField2.gridx = 1;
		typeTextField2.gridy = row;
		typeTextField2.ipady = ipadiy;
		typeTextField2.ipadx = 60;
		typeTextField2.insets = new Insets(0, 0, 5, 40);
		typeTextField2.anchor =  GridBagConstraints.WEST;
		contentpanel.add(quantity, typeTextField2);
		if(row < arrayListOfQuote.size()){
			quantity.setText(String.valueOf(arrayListOfQuote.get(row).get(1)));
		}
		quant.add(quantity);
			
		GridBagConstraints typeTextField3 = new GridBagConstraints();
		JTextField unitprice = new JTextField(textFieldColunm);
		typeTextField3.gridx = 2;
		typeTextField3.gridy = row;
		typeTextField3.ipady = ipadiy;
		typeTextField3.ipadx = 60;
		typeTextField3.insets = new Insets(0, 0, 5, 0);
		typeTextField3.anchor =  GridBagConstraints.WEST;
		contentpanel.add(unitprice, typeTextField3);
		if(row < arrayListOfQuote.size()){
			unitprice.setText(String.valueOf(arrayListOfQuote.get(row).get(2)));
		}
		uniprice.add(unitprice);
		
		GridBagConstraints addLabel = new GridBagConstraints();
		add = new JLabel(ul.iconImage("images/src/add.png"));
		addLabel.gridx = 3;
		addLabel.gridy = row;
		//addLabel.weightx = 1.0;
		addLabel.anchor =  GridBagConstraints.NORTHEAST;
		addLabel.insets = new Insets(3, 0, 0, 0);
		contentpanel.add(add, addLabel);
		add.addMouseListener(this); 
		
		GridBagConstraints removeLabel = new GridBagConstraints();
	    JLabel remove = new JLabel(ul.iconImage("images/src/remove.png"));
		removeLabel.gridx = 3;
		removeLabel.gridy = row-1;
		removeLabel.anchor =  GridBagConstraints.NORTHEAST;
		removeLabel.insets = new Insets(3, 0, 0, 0);
		remove.addMouseListener(this);
		if(row > 1 && arrayListOfQuote.isEmpty()){
			contentpanel.add(remove, removeLabel);
			label.add(remove);
		
		}
		else if(row >= 1 && !arrayListOfQuote.isEmpty()){
			contentpanel.add(remove, removeLabel);
			label.add(remove);
		}
		
	}
	
	public BigDecimal Results(){
		BigDecimal total = BigDecimal.ZERO;
		if(!outerlist.isEmpty()){//test polliforation of outerlist before doing using Results() method
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
	
	
	/*public void actionPerformed( ActionEvent e) { 
	
		if (e.getSource().equals(submit)){
			Integer proId = new Integer(projectId.getText());
		
			String string = NumberFormat.getCurrencyInstance(locale).format(Results());
			quote.setText(string);
			//generate pdf file for quote here
			dispose();	
		}
		if (e.getSource().equals(dispose_exit)){
			quote.setText("");
			dispose();	
		}
	
	
	}*/
	
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

			totalLabels.setText(ul.getStringInCurrency(Results()));
	
			
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



    public void actionPerformed( ActionEvent e) {  

	if (e.getSource().equals(dispose_exit)){
	
	    dispose();	
	}

	if (e.getSource().equals(update)){
		Results();
		//genericInfo[0] = String.valueOf(quoteId); 
		//genericInfo[1] = ul.usDateString(shInner.dateString(validDate)); 
		//{String.valueOf(quoteId), ul.usDateString(sh.dateString(validDate))};
		//bean.connect().updateQuotes(ul.writeStringArray(genericInfo), ul.writeListOfArrayListStr(outerlist)); 
		dispose();
		//new QuotePdf(projectQuote);
		
	}

    }

}
/*	public class Quote extends  JFrame implements MouseListener , ActionListener{
    Container con = getContentPane();
    private JPanel headerpanel;
    private JPanel contentpanel;
	private JPanel footerpanel;
    private JPanel mainpanel;
	private JPanel ledgerButtonPanel;//might be needed
    private JLabel total;
	private JLabel add;
	private JTextField totalLabels;
	private JButton submit;
	private JButton dispose_exit;
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
	private ClassLoader cl = this.getClass().getClassLoader();
	private Vector<JTextField> descript = new Vector<JTextField>();
	private Vector<JTextField> quant = new Vector<JTextField>();
	private Vector<JTextField> 	uniprice = new Vector<JTextField>();
	private Vector<JLabel> label = new Vector<JLabel>();
	private ArrayList<ArrayList<Object>> arrayListOfQuote = pu.getArrayListOfArrayList(bean.connect().getQuoteValues(projectIds));
	private  int click = arrayListOfQuote.size()-1;	
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
	//click = arrayListOfQuote.size()-1;
	for(int i = 0; i < arrayListOfQuote.size(); i++){
		if(i > 0 && i < arrayListOfQuote.size()){
			contentpanel.remove(add);
		}
		makeTextField(i);
	}
	submit = new JButton("UPDATE");
	GridBagConstraints submitquote = new GridBagConstraints();
	submitquote.gridx = 0;
	submitquote.gridy = 0;
	submitquote.insets = new Insets(0, 0, 0, 60);
	footerpanel.add(submit, submitquote);
	
	GridBagConstraints totalLabel = new GridBagConstraints();
	totalLabel.gridx = 1;
	totalLabel.gridy = 0;
    footerpanel.add(new JLabel("Grand Total:"), totalLabel);
	
	GridBagConstraints priceLabel = new GridBagConstraints();
	totalLabels = new JTextField(textFieldColunm);
	priceLabel.gridx = 2;
	priceLabel.gridy = 0;
	priceLabel.ipadx = 60;
	priceLabel.insets = new Insets(0, 0, 0, 60);
    footerpanel.add(totalLabels , priceLabel);
	totalLabels.setText(ul.getStringInCurrency(pu.getTotalQuote(bean.connect().getQuoteTotals(projectIds))));
	//totalLabels.setText(String.valueOf(label.size()));
	totalLabels.setEditable(false);
	
	dispose_exit = new JButton("EXIT");
	GridBagConstraints exitquote = new GridBagConstraints();
	exitquote.gridx = 3;
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
	setIconImage(ul.mainImage("images/src/gui_icon.png"));
	
	dispose_exit.addActionListener(this);
	submit.addActionListener(this);
    }
	
	public void makeTextField(int row){
		
		Vector<String> innerlist = new Vector<String>();
		GridBagConstraints typeTextField1 = new GridBagConstraints();
		JTextField description = new JTextField(textFieldColunm);
		typeTextField1.gridx = 0;
		typeTextField1.gridy = row;
		typeTextField1.ipady = ipadiy;
		typeTextField1.ipadx = 250;
		typeTextField1.insets = new Insets(0, 0, 10, 40);
		typeTextField1.anchor =  GridBagConstraints.WEST;
		contentpanel.add(description, typeTextField1);
		if(row < arrayListOfQuote.size()){
			description.setText(String.valueOf(arrayListOfQuote.get(row).get(0)));
		}
		descript.add(description);
		
		GridBagConstraints typeTextField2 = new GridBagConstraints();
		JTextField quantity = new JTextField(textFieldColunm);
		typeTextField2.gridx = 1;
		typeTextField2.gridy = row;
		typeTextField2.ipady = ipadiy;
		typeTextField2.ipadx = 60;
		typeTextField2.insets = new Insets(0, 0, 10, 40);
		typeTextField2.anchor =  GridBagConstraints.WEST;
		contentpanel.add(quantity, typeTextField2);
		if(row < arrayListOfQuote.size()){
			quantity.setText(String.valueOf(arrayListOfQuote.get(row).get(1)));
		}
		quant.add(quantity);
			
		GridBagConstraints typeTextField3 = new GridBagConstraints();
		JTextField unitprice = new JTextField(textFieldColunm);
		typeTextField3.gridx = 2;
		typeTextField3.gridy = row;
		typeTextField3.ipady = ipadiy;
		typeTextField3.ipadx = 60;
		typeTextField3.insets = new Insets(0, 0, 10, 0);
		typeTextField3.anchor =  GridBagConstraints.WEST;
		contentpanel.add(unitprice, typeTextField3);
		if(row < arrayListOfQuote.size()){
			unitprice.setText(String.valueOf(arrayListOfQuote.get(row).get(2)));
		}
		uniprice.add(unitprice);
		
		GridBagConstraints addLabel = new GridBagConstraints();
		URL imageAdd = cl.getResource("add.png"); 
		ImageIcon addIcon = null;
		if(imageAdd  != null){
			addIcon = new ImageIcon(imageAdd);
		}
		
		add = new JLabel(addIcon);
		addLabel.gridx = 3;
		addLabel.gridy = row;
		addLabel.anchor =  GridBagConstraints.NORTHEAST;
		addLabel.insets = new Insets(3, 0, 0, 0);
		contentpanel.add(add, addLabel);
		add.addMouseListener(this); 
		
		GridBagConstraints removeLabel = new GridBagConstraints();
		URL imageRemove = cl.getResource("remove.png"); 
		ImageIcon removeIcon = null;
		if(imageRemove  != null){
			removeIcon = new ImageIcon(imageRemove);
		}
		JLabel remove = new JLabel(removeIcon);
		removeLabel.gridx = 3;
		removeLabel.gridy = row-1;
		removeLabel.anchor =  GridBagConstraints.NORTHEAST;
		removeLabel.insets = new Insets(3, 0, 0, 0);
		remove.addMouseListener(this);
		if(row > 0){
			contentpanel.add(remove, removeLabel);
			label.add(remove);

		}
		
	}
	
	public BigDecimal Results(){
		BigDecimal total = BigDecimal.ZERO;
		
		if(!outerlist.isEmpty()){
			outerlist.clear(); 
		}
		for(int i = 0; i < uniprice.size(); i++){
			Vector<String> innerlist = new Vector<String>();
			BigDecimal subtotal = BigDecimal.ZERO;
			BigDecimal price =  BigDecimal.ZERO;
			BigDecimal qty =  BigDecimal.ZERO;
			if(!uniprice.get(i).getText().equals("") || !quant.get(i).getText().equals("")){
				innerlist.addElement(descript.get(i).getText());
				qty = new BigDecimal(uniprice.get(i).getText());
				innerlist.addElement(uniprice.get(i).getText());
				price = new BigDecimal(quant.get(i).getText());
				innerlist.addElement(quant.get(i).getText());
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
		
			//String string = NumberFormat.getCurrencyInstance(locale).format(Results());
			amount.setText(ul.getStringInCurrency(Results()));
			bean.connect().updateQuote(ul.writeVectorOfVector(outerlist), ul.writeInteger(new Integer(projectIds))); 
			//UPDATE QUOTE WITH NEW DATA   here
			dispose();	
		}
		if (e.getSource().equals(dispose_exit)){
			amount.setText("");
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
				//remove.addMouseListener(this);
			
			}
			totalLabels.setText(ul.getStringInCurrency(Results()));
			amount.setText(ul.getStringInCurrency(Results()));
		}
		else{
			//remove.addMouseListener(this);
			for(int i = 0; i < label.size(); i++){
				if(e.getSource().equals(label.get(i))){
					contentpanel.remove(descript.get(i));
					descript.removeElementAt(i);
					contentpanel.remove(quant.get(i));
					quant.removeElementAt(i);
					contentpanel.remove(uniprice.get(i));
					uniprice.removeElementAt(i);
					contentpanel.remove(label.get(i));
					label.removeElementAt(i);
					contentpanel.revalidate();  
					repaint();
					validate();
				}
			}
			totalLabels.setText(ul.getStringInCurrency(Results()));
			amount.setText(ul.getStringInCurrency(Results()));
		}
	}
	public void mouseEntered(MouseEvent e) {} 
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e){} 
	public void mouseReleased(MouseEvent e) {}
	
}*/

   public void actionPerformed( ActionEvent e) {  

	if (e.getSource().equals(exit)){
	    dispose();	
	 }
	
	if(e.getSource().equals(quoteradion)){
		listingButton.setEnabled(false);
		amount.setText(ul.getStringInCurrency(pu.getTotalQuote(bean.connect().getQuoteTotals(projectIds))));
		
		
	 }
	
	if(e.getSource().equals(newListing)){
		listingButton.setEnabled(true);
		amount.setText("");
	  }
	  
	 if(e.getSource().equals(listingButton)){
		 EditQuotes ds = new EditQuotes("Edit Customer Quote");
	  }
	

	 if (e.getSource().equals(save)){
	   //generate pdf file for INVOICE here 
		java.util.List<String> paydetails =  new ArrayList<String>();
		paydetails.add(String.valueOf(strArray[0]));
		//paydetails.add(accountname.get(group.getSelectedItem()));
		paydetails.add(String.valueOf(projectIds));
		paydetails.add(invoiceOut.getText());
		paydetails.add(pu.sqlDateString(sh.getGenericDate(date)));
		paydetails.add(String.valueOf(pu.getTotalQuote(bean.connect().getQuoteTotals(projectIds))));
		paydetails.add(sh.dayString(paymentTerm));
		paydetails.add((String)vat.getSelectedItem());
		if(pu.paymentInvoice(bean.connect().getPaymentInfo(projectIds)) == null){
			bean.connect().addPaymentIn(ul.writeArrayListStr(paydetails)); 
		 }
		else{
			//bean.connect().updatePaymentIn(ul.writeVector(paydetails));
		}
		dispose();
	  }
	}

}