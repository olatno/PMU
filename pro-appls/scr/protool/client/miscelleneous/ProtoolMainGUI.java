package scr.protool.client.miscelleneous;
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
import javax.swing.border.EtchedBorder; 
import javax.swing.BorderFactory;  
import javax.swing.border.Border; 
import scr.protool.client.supplier.SupplierView;
import scr.protool.client.supplier.SubContractorView;
import scr.protool.client.supplier.Outgoingview;
import scr.protool.client.fixedasset.AssetRegisterView;
import scr.protool.client.project.ProjectViews;
import scr.protool.client.customer.Incomingview;
import scr.protool.client.general.GeneralExpenses;
import scr.protool.client.general.GeneralExpensesView;
import javax.naming.*;
import scr.protool.client.utilities.*;


public class ProtoolMainGUI extends  JFrame implements Serializable, ActionListener{//ItemListener

    Container con = getContentPane();
    private JPanel buttonPanel;
    private JPanel mainPanel;
	 private JPanel uperbuttonPanel;
	private JPanel bannerPanel;
	private JPanel upperPanel;
	private JPanel upperGridPanel;
	private JPanel buttonPanel2;
	private JPanel incorporatePanel;
	private JPanel viewPanel;
	private JPanel defaultPanel;
	private JLabel companyName;
	private JLabel defaultLabel;
	private JLabel incoporaLabel;
	private JButton contact;
	private JButton helpLabel;
    private JButton project;
    private JButton paymentIn;
    private JButton paymentOut;
    private JButton costcenter;
	private JButton supplier;
	private JButton sub_contact;
	private JButton manual;
	private JButton password;
	private JButton asset;
    private JButton help;
    private JButton exit;
	private JButton expense;
	private JButton expenseView;
	private GridBagConstraints middleContentPanel;
	private GridBagConstraints upperLabel;
	private Outgoingview outview;
	private ProjectViews proview;
	private Incomingview inview;
	private ViewCodeDetails costview;
	private ChangePassword changepass;
	private GeneralExpensesView expensesview;
	private ManualPostingView manualview;
	private AssetRegisterView assetview;
	private ArrayList<JComponent> listPane = new ArrayList<JComponent>();
   // private ProtoolProject projectpro;
	private SupplierView suppliers;
	private SubContractorView subcontact;
	private ArrayList<String> userModel;
    private final int width = 120;
	private final int height = 49;
	private Font allF = null;
	private int passwordid;
	private Utilities ul;
	final String image ="C:/Users/Ola/ProtooMD/picture #0D83DD";
	private final String bDecoder = "#A9B9B9";
	private final String tDecoder = "#373F40";

    public ProtoolMainGUI(String title, ArrayList<String> userModel) {//
	super(title);
	this.userModel = userModel;
	ul = new Utilities();
	allF = new Font( "DejaVu Sans", Font.BOLD, 12 );
	uperbuttonPanel = new JPanel();
	mainPanel = new JPanel();
	viewPanel = new JPanel();
	incorporatePanel = new JPanel();
	buttonPanel2 = new JPanel();
	bannerPanel = new JPanel();
	upperPanel = new JPanel();
	upperGridPanel = new JPanel();
	defaultPanel = new JPanel();
	GridBagLayout gridbutton = new GridBagLayout();
	buttonPanel  = new JPanel();
	buttonPanel.setLayout(gridbutton);

	uperbuttonPanel.setLayout(new GridBagLayout());
	mainPanel.setLayout(new GridBagLayout());
	upperGridPanel.setLayout(new GridBagLayout());
	viewPanel.setLayout(new BorderLayout());
	upperPanel.setLayout(new BorderLayout());
	//defaultPanel.setLayout(new BorderLayout());
	defaultPanel.setLayout(new BoxLayout(defaultPanel, BoxLayout.LINE_AXIS)) ;
	incorporatePanel.setLayout(new BorderLayout());
	bannerPanel.setLayout(new BorderLayout());
	buttonPanel2.setLayout(new FlowLayout());
	
	
	//client banner
	GridBagConstraints companyLabel = new GridBagConstraints();
	companyName = new JLabel();//"<html>JW Electrical Services</html>" 
	bannerPanel.add(companyName);
	companyLabel.gridx = 0;
	companyLabel.gridy = 0;
	companyLabel.anchor = GridBagConstraints.FIRST_LINE_START;
	companyLabel.insets = new Insets(25, 0, 0, 80);
	upperGridPanel.add(bannerPanel, companyLabel);
	companyName.setBackground(Color.decode(bDecoder));
	companyName.setOpaque( true );
	InputStream fontFile = ul.fileResources("font/src/Walkway_UltraBold.ttf");
	 
	Font created = null;
	try{
		created = Font.createFont(Font.TRUETYPE_FONT , fontFile);
		
	}
	catch(FontFormatException f){}
	catch(IOException ie){}
	if(created != null){
		Font derived = created.deriveFont(Font.BOLD, 48f);
		companyName.setFont(derived);
		
	}	
	companyName.setForeground(Color.white);
	companyName.setText("[your company name]");//("<html>JW Electrical Services</html>"); Font.BOLD,
	
	GridBagConstraints contactLabel = new GridBagConstraints();
	contact = new JButton("Contact");
	contactLabel.gridx = 1;
	contactLabel.gridy = 1;
	contact.setBackground(Color.decode(bDecoder));
	uperbuttonPanel.add(contact, contactLabel);
	
	GridBagConstraints helpLabel = new GridBagConstraints();
	help = new JButton("Help");
	helpLabel.gridx = 2;
	helpLabel.gridy = 1;
	help.setBackground(Color.decode(bDecoder));
	uperbuttonPanel.add(help, helpLabel);
	upperGridPanel.setBackground(Color.decode(bDecoder));
	
	GridBagConstraints passButton = new GridBagConstraints();
	password = new JButton("Password");
	passButton.gridx = 3;
	passButton.gridy = 1;
	password.setBackground(Color.decode(bDecoder));
	uperbuttonPanel.add(password, passButton);
	
	GridBagConstraints upButton = new GridBagConstraints();
	upButton.gridx = 1;
	upButton.gridy = 3;
	upButton.anchor = GridBagConstraints.NORTHWEST;
	upButton.insets = new Insets(0, 0, 10, 50);
	upperGridPanel.add(uperbuttonPanel,upButton);
	//panel to hold upper components
	upperLabel = new GridBagConstraints();
	upperPanel.add(upperGridPanel);
	upperLabel.gridx = 0;
	upperLabel.gridwidth = 5;
	upperLabel.weightx = 0.5;
	upperLabel.fill = GridBagConstraints.BOTH;
	upperPanel.setOpaque( true );
	upperPanel.setPreferredSize(new Dimension (870, 70));
	upperPanel.setBackground(Color.decode(bDecoder));
	mainPanel.add(upperPanel, upperLabel);
	listPane.add(upperPanel);
	//upperPanel.setResizable(false);
	
	//first button

	GridBagConstraints pr = new GridBagConstraints();
	project = new JButton("Project");
	project.setFont(allF);
	project.setForeground(Color.decode(tDecoder)); 
    project.setPreferredSize(new Dimension (width, height));
	pr.gridx = 0;
	pr.gridy = 0;
	buttonPanel.add(project, pr);
	if(!userAccess("project")){
		project.setEnabled(false);
	}
	

	//secound button
	GridBagConstraints po = new GridBagConstraints();
	//ImageIcon icon1 = new ImageIcon(image+"/payout.gif");
	paymentIn = new JButton("<html>Accounts<br> Receivable</html>");
	paymentIn.setFont(allF);
	paymentIn.setForeground(Color.decode(tDecoder)); 
    paymentIn.setPreferredSize(new Dimension (width, height));
	po.gridx = 0;
	po.gridy = 1;
	buttonPanel.add(paymentIn, po);
	if(!userAccess("paymentin")){
		paymentIn.setEnabled(false);
	}
	
	GridBagConstraints supplierBag = new GridBagConstraints();
	//ImageIcon icon2 = new ImageIcon(image+"/payIN.gif");
	supplier = new JButton("Supplier");
	supplier.setFont(allF);
	supplier.setForeground(Color.decode(tDecoder)); 
   	supplier.setPreferredSize(new Dimension (width, height));
	supplierBag.gridx = 0;
	supplierBag.gridy = 2;
	buttonPanel.add(supplier, supplierBag);
	if(!userAccess("supplier")){
		supplier.setEnabled(false);
	}
	
	GridBagConstraints subcontactBag = new GridBagConstraints();
	//ImageIcon icon2 = new ImageIcon(image+"/payIN.gif");
	sub_contact = new JButton("Sub-Contactor");
	sub_contact.setFont(allF);
	sub_contact.setForeground(Color.decode(tDecoder)); 
   	sub_contact.setPreferredSize(new Dimension (width, height));
	subcontactBag.gridx = 0;
	subcontactBag.gridy = 3;
	buttonPanel.add(sub_contact, subcontactBag);
	if(!userAccess("subcontractor")){
		sub_contact.setEnabled(false);
	}

	GridBagConstraints pi = new GridBagConstraints();
	//ImageIcon icon2 = new ImageIcon(image+"/payIN.gif");
	paymentOut = new JButton("<html>Accounts<br> Payable</html>");
	paymentOut.setFont(allF);
	paymentOut.setForeground(Color.decode(tDecoder)); 
   	paymentOut.setPreferredSize(new Dimension (width, height));
	pi.gridx = 0;
	pi.gridy = 4;
	buttonPanel.add(paymentOut, pi);
	if(!userAccess("paymentout")){
		paymentOut.setEnabled(false);
	}
	
	GridBagConstraints expens = new GridBagConstraints();
	//ImageIcon icon5 = new ImageIcon(image+"/exit.gif");
	expense = new JButton("Expenses");
	expense.setFont(allF);
	expense.setForeground(Color.decode(tDecoder)); 
    expense.setPreferredSize(new Dimension (width, height));
	expens.gridx = 0;
	expens.gridy = 5;
	buttonPanel.add(expense, expens);
	if(!userAccess("expenses")){
		expense.setEnabled(false);
	}

	GridBagConstraints co = new GridBagConstraints();
	//ImageIcon icon3 = new ImageIcon(image+"/cost.gif");
	costcenter = new JButton("Accounts Code");
    costcenter.setPreferredSize(new Dimension (width, height));
	costcenter.setFont(allF);
	costcenter.setForeground(Color.decode(tDecoder)); 
	co.gridx = 0;
	co.gridy = 8;
	buttonPanel.add(costcenter, co);
	if(!userAccess("accountscode")){
		costcenter.setEnabled(false);
	}
	
	
	GridBagConstraints manualBag = new GridBagConstraints();
	//ImageIcon icon4 = new ImageIcon(image+"/help.gif");
	manual = new JButton("Manual Posting");
	manual.setFont(allF);
	manual.setForeground(Color.decode(tDecoder)); 
    manual.setPreferredSize(new Dimension (width, height));
	manualBag.gridx = 0;
	manualBag.gridy = 6;
	buttonPanel.add(manual, manualBag);
	if(!userAccess("manualposting")){
		manual.setEnabled(false);
	}

	GridBagConstraints hl = new GridBagConstraints();
	//ImageIcon icon4 = new ImageIcon(image+"/help.gif");
	asset = new JButton("Asset Register");
	asset.setFont(allF);
	asset.setForeground(Color.decode(tDecoder)); 
    asset.setPreferredSize(new Dimension (width, height));
	hl.gridx = 0;
	hl.gridy = 7;
	buttonPanel.add(asset, hl);
	if(!userAccess("asset")){
		asset.setEnabled(false);
	}

	GridBagConstraints middleButtonPanel = new GridBagConstraints();
	middleButtonPanel.gridx = 0;
	middleButtonPanel.gridy = 1;
	middleButtonPanel.gridheight  = 5;
	middleButtonPanel.anchor =  GridBagConstraints.WEST;
	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
	buttonPanel2.setMinimumSize(new Dimension(150, 450));
	Border outer = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
	Border inner = BorderFactory.createEmptyBorder(-2, 0, 0, 0);
	Border combined = BorderFactory.createCompoundBorder(outer, inner);
	buttonPanel2.setBorder(combined);
	middleButtonPanel.insets = new Insets(-12, 0, 0, 0);
	buttonPanel2.add(buttonPanel);
	mainPanel.add(buttonPanel2, middleButtonPanel);
	listPane.add(buttonPanel2);

	middleContentPanel = new GridBagConstraints();
	defaultLabel = new JLabel(){
		public Dimension getPreferredSize() { 
			return new Dimension(800, 440); 
		} 
		public Dimension getMinimumSize() { 
			return new Dimension(720, 393); 
		} 
		public Dimension getMaximumSize() { 
			return new Dimension(800, 441); 
		} 
	}; 
	
	defaultPanel.add("West",defaultLabel);
	middleContentPanel.gridx = 1;
	middleContentPanel.gridy = 1;
	middleContentPanel.insets = new Insets(10, 0, 75, 0);
	middleContentPanel.gridheight  = 5;
	defaultLabel.setText("Flexiblebooks");
	mainPanel.add(defaultPanel, middleContentPanel);
	defaultLabel.setBackground(Color.decode("#FFFFFF"));
	defaultLabel.setOpaque( true );
	defaultLabel.setFont(new Font("Trebuchet Ms", Font.BOLD, 60));
	defaultLabel.setForeground(Color.black);
	listPane.add(defaultPanel);
	
			
	GridBagConstraints incorpLabel = new GridBagConstraints();
	incoporaLabel = new JLabel();
	incorporatePanel.add(incoporaLabel);
	incorpLabel.gridx = 1;
	incorpLabel.gridy = 8;
	incorpLabel.gridwidth = GridBagConstraints.REMAINDER ;
	Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
	String current = dateFormat.format(calendar.getTime());
	incorpLabel.anchor = GridBagConstraints.EAST;
	//mainPanel.add(incorporatePanel, incorpLabel);
	incoporaLabel.setOpaque( true );
	incoporaLabel.setFont(new Font("Trebuchet Ms", Font.ITALIC, 12));
	incoporaLabel.setForeground(Color.red);
	incoporaLabel.setText("Copyright © 2010 - "+String.format("%s", current)+" Flexiblebooks.com");
	listPane.add(incorporatePanel);
	con.add(mainPanel);
	pack();
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(900, 600);
	this.setResizable(false);
	this.setVisible(true);   
	this.setIconImage(ul.mainImage("images/src/gui_icon.png"));
		
	project.addActionListener(this);
    paymentIn.addActionListener(this);
	paymentOut.addActionListener(this);
	supplier.addActionListener(this); 
	costcenter.addActionListener(this); 
	//help.addActionListener(this);
	asset.addActionListener(this);
	manual.addActionListener(this);
	password.addActionListener(this);
	sub_contact.addActionListener(this);	
	expense.addActionListener(this); 

    }
	
	public boolean userAccess(String access){
	
		for(int i = 0;  i < userModel.size(); i++){
			if(userModel.get(i).equals(access)){
				return true;
			}
		}
		return false;
	}
	
	public int getPasswordId(){
		return  passwordid;
	}
	public void setPasswordId(int passwordid){
		this.passwordid = passwordid;
	}
	public void refeshButton(JPanel panel){

		mainPanel.remove(listPane.get(2));
		mainPanel.revalidate();
		middleContentPanel.gridx = 1;
		middleContentPanel.gridy = 1;
		middleContentPanel.gridheight = 5;
		mainPanel.add(panel, middleContentPanel);
		listPane.set(2, panel);
		validate();
		repaint();
		panel.setBackground(Color.red);
	}
	public void bannerAdj( int inserter){
	upperLabel = new GridBagConstraints();
	upperPanel.add(upperGridPanel);
	upperLabel.gridx = 0;
	upperLabel.gridwidth = 5;
	upperLabel.weightx = 0.5;
	upperLabel.insets = new Insets(0, 0, inserter, 0);
	upperLabel.fill = GridBagConstraints.BOTH;
	upperPanel.setOpaque( true );
	upperPanel.setPreferredSize(new Dimension (870, 70));
	upperPanel.setBackground(Color.decode("#0D83DD"));
	mainPanel.add(upperPanel, upperLabel);
	listPane.add(upperPanel);
	}
	
    public void actionPerformed( ActionEvent e) { 
		
	if (e.getSource().equals(project)) {

		mainPanel.remove(listPane.get(2));
		mainPanel.revalidate();
		middleContentPanel.insets = new Insets(0, 12, 20, 0);
		proview = new ProjectViews("Test");
		middleContentPanel.gridx = 1;
		middleContentPanel.gridy = 1;
		middleContentPanel.gridheight = 5;
		mainPanel.add(proview, middleContentPanel);
		listPane.set(2, proview);
		validate();
		repaint();
		project.setBackground(Color.red);
	}
		
	else{
		if(!e.getSource().equals(password)){
			project.setOpaque( true );
			project.setBackground(Color.decode("#F0F0F0"));
	
		}
	}

	if (e.getSource().equals(paymentIn)) { 
		mainPanel.remove(listPane.get(2));
		mainPanel.revalidate();
		inview = new Incomingview("Test");
		middleContentPanel.insets = new Insets(0, 12, 20, 0);
		middleContentPanel.gridx = 1;
		middleContentPanel.gridy = 1;
		middleContentPanel.gridheight = 5;
		mainPanel.add(inview, middleContentPanel);
		listPane.set(2, inview);
		validate();
		repaint();
		paymentIn.setBackground(Color.red);
	} 
	
	else{
		if(!e.getSource().equals(password)){
		paymentIn.setBackground(Color.decode("#F0F0F0"));
		}
	}
	
	if (e.getSource().equals(supplier)) { 
		mainPanel.remove(listPane.get(2));
		mainPanel.revalidate();
		suppliers = new SupplierView("SUPPLIERS");
		middleContentPanel.insets = new Insets(0, 12, 20, 0);
		middleContentPanel.gridx = 1;
		middleContentPanel.gridy = 1;
		middleContentPanel.gridheight = 5;
		mainPanel.add(suppliers, middleContentPanel);
		listPane.set(2, suppliers);
		validate();
		repaint();
		supplier.setBackground(Color.red);  
	} 
	
	else{
		if(!e.getSource().equals(password)){
		supplier.setBackground(Color.decode("#F0F0F0"));
		}
	}
	
	if (e.getSource().equals(sub_contact)) { 
		mainPanel.remove(listPane.get(2));
		mainPanel.revalidate();
		subcontact = new SubContractorView("Test");
		middleContentPanel.insets = new Insets(0, 12, 20, 0);
		middleContentPanel.gridx = 1;
		middleContentPanel.gridy = 1;
		middleContentPanel.gridheight = 5;
		mainPanel.add(subcontact, middleContentPanel);
		listPane.set(2, subcontact);
		validate();
		repaint();
		sub_contact.setBackground(Color.red); 
	}
	
	else{
		if(!e.getSource().equals(password)){
		sub_contact.setBackground(Color.decode("#F0F0F0"));
		}
	}
	
	if (e.getSource().equals(paymentOut)) { 
		mainPanel.remove(listPane.get(2));
		mainPanel.revalidate();
		outview = new Outgoingview("Test");
		middleContentPanel.insets = new Insets(0, 12, 20, 0);
		middleContentPanel.gridx = 1;
		middleContentPanel.gridy = 1;
		middleContentPanel.gridheight = 5;
		mainPanel.add(outview, middleContentPanel);
		listPane.set(2, outview);
		validate();
		repaint();
		paymentOut.setBackground(Color.red);
	}
	
	else{
		if(!e.getSource().equals(password)){
		paymentOut.setBackground(Color.decode("#F0F0F0"));
		}
	}
	
	if (e.getSource().equals(expense)) { 
		mainPanel.remove(listPane.get(2));
		mainPanel.revalidate();
		expensesview = new GeneralExpensesView("Test");
		middleContentPanel.insets = new Insets(0, 12, 20, 0);
		middleContentPanel.gridx = 1;
		middleContentPanel.gridy = 1;
		middleContentPanel.gridheight = 5;
		mainPanel.add(expensesview, middleContentPanel);
		listPane.set(2, expensesview);
		validate();
		repaint();
		expense.setBackground(Color.red);
	}
	
	else{
		if(!e.getSource().equals(password)){
		expense.setBackground(Color.decode("#F0F0F0"));
		}
	}
	
	
	if (e.getSource().equals(costcenter)) { 
		mainPanel.remove(listPane.get(2));
		mainPanel.revalidate();
		costview = new ViewCodeDetails("Test", this);
		middleContentPanel.insets = new Insets(0, 12, 20, 0);
		middleContentPanel.gridx = 1;
		middleContentPanel.gridy = 1;
		middleContentPanel.gridheight = 5;
		mainPanel.add(costview, middleContentPanel);
		listPane.set(2, costview);
		validate();
		repaint();
		costcenter.setBackground(Color.red);
	}
	
	else{
		if(!e.getSource().equals(password)){
		costcenter.setBackground(Color.decode("#F0F0F0"));
		}
	}
	
	if (e.getSource().equals(manual)) { 
		mainPanel.remove(listPane.get(2));
		mainPanel.revalidate();
		manualview = new ManualPostingView("Test");
		middleContentPanel.insets = new Insets(0, 12, 20, 0);
		middleContentPanel.gridx = 1;
		middleContentPanel.gridy = 1;
		middleContentPanel.gridheight = 5;
		mainPanel.add(manualview, middleContentPanel);
		listPane.set(2, manualview);
		validate();
		repaint();
		manual.setBackground(Color.red);
	}
	
	else{
		if(!e.getSource().equals(password)){
		manual.setBackground(Color.decode("#F0F0F0"));
		}
	}
	
	if (e.getSource().equals(asset)) { 
		mainPanel.remove(listPane.get(2));
		mainPanel.revalidate();
		assetview = new AssetRegisterView("Test");
		middleContentPanel.insets = new Insets(0, 12, 20, 0);
		middleContentPanel.gridx = 1;
		middleContentPanel.gridy = 1;
		middleContentPanel.gridheight = 5;
		mainPanel.add(assetview, middleContentPanel);
		listPane.set(2, assetview);
		validate();
		repaint();
		asset.setBackground(Color.red);
	}
	else{
		if(!e.getSource().equals(password)){
			asset.setBackground(Color.decode("#F0F0F0"));
		}
	}

	if (e.getSource().equals(password)){
		changepass = new ChangePassword(true, "Change Password" , getPasswordId());

	}

    }

}