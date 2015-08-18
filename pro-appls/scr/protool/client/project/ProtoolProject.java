
package scr.protool.client.project;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import javax.naming.*;
import java.text.*;
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
public class ProtoolProject extends JFrame implements Serializable, ActionListener{

	private BeanStud bean;
    private JTextField projectTitle;
    private JTextField clientName;
    private JSpinner date_from;
    private JSpinner date_to;
    private JTextField cost_from;
    private JTextField cost_to;
    private JTextField quote_from;
    private JTextField quote_to;
    private JButton addproject;
    private JButton invoice;
    private JButton searchproject;
    private JButton editproject;
    private JButton removeproject;
    private JButton exitproject;
    private JButton details;
    private JButton find;
    private JPanel buttonpanel;
    private JPanel searchpanel;
    private JPanel tablepanel;
    private AddProject addProject;
	//private AddPaymentin addPayment;
	private EditProject editProject;
	private Utilities ul = new Utilities();
	private AccountsUtilities ula = new AccountsUtilities();
    private JTable projectView; 
    private DefaultTableModel projectDataModel; 
    private JScrollPane projectViewScroll; 
    private final String[]columnNames = {"PROJECT ID ","PROJECT TITLE","CLIENT NAME","QUOTE","ESTIMATED COST","START DATE","DURATION","STATUS"};
    private final int initialRow = 24;
    private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int textFieldColunm = 1;
    private final double weightix = 1.0;
	private   SpinnerHelper sh1;
	private   SpinnerHelper sh2; 
	Locale locale = Locale.UK;
    Container con = getContentPane();
    
    public ProtoolProject(String title){
   	super(title); 
    bean = new BeanStud();
	buttonpanel = new JPanel();
	searchpanel = new JPanel();
	tablepanel = new JPanel();

	//Panels
	buttonpanel.setLayout(new GridLayout(10,1));
	searchpanel.setLayout(new GridBagLayout());
	tablepanel.setLayout(new BorderLayout());
	sh1 = new SpinnerHelper();
	sh2 = new SpinnerHelper();


	//Textfields use for search
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
	searchpanel.add(new JLabel("QUOTE FROM:"), quote1Label);

	GridBagConstraints quote1TextField = new GridBagConstraints();
	quote_from = new JTextField(textFieldColunm);
	quote1TextField.gridx = 1;
	quote1TextField.gridy = 1;
	quote1TextField.ipady = ipadiy;
	quote1TextField.ipadx = ipadix;
	quote1TextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(quote_from, quote1TextField);


	GridBagConstraints quote2Label = new GridBagConstraints();
	quote2Label.gridx = 2;
	quote2Label.gridy = 1;
	quote2Label.anchor =  GridBagConstraints.EAST;
	searchpanel.add(new JLabel("QUOTE TO:"), quote2Label);

	GridBagConstraints quote2TextField = new GridBagConstraints();
	quote_to = new JTextField(textFieldColunm);
	quote2TextField.gridx = 3;
	quote2TextField.gridy = 1;
	quote2TextField.ipady = ipadiy;
	quote2TextField.ipadx = ipadix;
	quote2TextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(quote_to, quote2TextField);
	
	GridBagConstraints clientLabel = new GridBagConstraints();
	clientLabel.gridx = 0;
	clientLabel.gridy = 2;
	clientLabel.anchor =  GridBagConstraints.EAST;
	// 	clientLabel.weighty = weightiy;
       	searchpanel.add(new JLabel("CLIENT NAME:"), clientLabel);

	GridBagConstraints clientTextField = new GridBagConstraints();
	clientName = new JTextField(textFieldColunm);
	clientTextField.gridx = 1;
	clientTextField.gridy = 2;
	clientTextField.ipady = ipadiy;
	clientTextField.ipadx = ipadix;
	clientTextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(clientName, clientTextField);

	GridBagConstraints titleLabel = new GridBagConstraints();
	titleLabel.gridx = 2;
	titleLabel.gridy = 2;
	titleLabel.weightx = weightix;
	titleLabel.anchor =  GridBagConstraints.EAST;
	searchpanel.add(new JLabel("PROJECT TITLE:"), titleLabel);

	GridBagConstraints titleTextField = new GridBagConstraints();
	projectTitle = new JTextField(textFieldColunm);
	titleTextField.gridx = 3;
	titleTextField.gridy = 2;
	titleTextField.ipady = ipadiy;
	titleTextField.ipadx = ipadix;
   // titleTextField.weightx = weightix;
	titleTextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(projectTitle, titleTextField);
	
	GridBagConstraints searchbutton = new GridBagConstraints();
	//projectTitle = new JTextField(textFieldColunm);
	searchproject = new JButton("SEARCH");
	searchbutton.gridx = 4;
	searchbutton.gridy = 1;
	searchbutton.ipady = 7;
	//searchbutton.ipadx = ipadix;
	//searchbutton.gridheight = 4;
    searchbutton.weightx = weightix;
	//titleTextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(searchproject, searchbutton);
	
       	searchpanel.setBorder(new Partition(20,"Tool"));
	//BUTTON WEST
	addproject = new JButton("ADD");
	buttonpanel.add(addproject);
	buttonpanel.add(new JLabel());
	details = new JButton("DETAILS");
	buttonpanel.add(details);
	buttonpanel.add(new JLabel());
	invoice = new JButton("INVOICE");
	buttonpanel.add(invoice);
	buttonpanel.add(new JLabel());
	//searchproject = new JButton("SEARCH");
	//buttonpanel.add(searchproject);
	//find = new JButton("FIND");
	//buttonpanel.add(find);
	//details = new JButton("DETAILS");
	//buttonpanel.add(details);
	//editproject = new JButton("EDIT");
	//buttonpanel.add(editproject);
	//removeproject = new JButton("DELETE");
	//buttonpanel.add(removeproject);
	buttonpanel.add(new JLabel());
	buttonpanel.add(new JLabel());
	buttonpanel.add(new JLabel());
	exitproject = new JButton("EXIT");
	buttonpanel.add(exitproject);

	//Table formation
	Vector<String> columns = new Vector<String>(); 
	for(int tableHeader = 0; tableHeader < columnNames.length; tableHeader++) { 
	    columns.addElement(columnNames[tableHeader]); 
	} 
	projectDataModel = new DefaultTableModel(columns, initialRow); 
	projectView = new JTable(projectDataModel);  
 
	int[]widthValue = {150,200,200,150,175,150,150,150};

	for(int i = 0; i<widthValue.length; i++){
	    TableColumn col  = projectView.getColumnModel().getColumn(i);
	    col.setPreferredWidth(widthValue[i]);
	}

	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	projectViewScroll = new JScrollPane(projectView, v,h); 
	tablepanel.add(projectViewScroll); 

	con.add("West", buttonpanel);
	con.add("North", searchpanel); 
	con.add("Center", tablepanel);

	pack();
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(900, 580);
	setResizable(false);
	setVisible(true); 
	
	invoice.addActionListener(this); 
	exitproject.addActionListener(this); 
	addproject.addActionListener(this); 
	searchproject.addActionListener(this); 
	details.addActionListener(this);
	find.addActionListener(this); 
	editproject.addActionListener(this); 
	removeproject.addActionListener(this);
	ul.loadDate(projectDataModel, bean.connect().getProjectData());
	
    }


	public JTable getTablename(){
		return projectView;
	}
	
	public DefaultTableModel getTableModelName(){
		return projectDataModel;
	}
	public int getTableId(){
		int  maintablerow = projectView.getSelectedRow();
		return ((Integer)projectDataModel.getValueAt(maintablerow, 0)).intValue();
	}
	
	public String	getProjectTitle(){
		int  maintablerow = projectView.getSelectedRow();
		return (String)projectDataModel.getValueAt(maintablerow, 1);
	}
	
	public  BigDecimal getTableQuote(){
		int  maintablerow = projectView.getSelectedRow();
		return new BigDecimal(String.valueOf(projectDataModel.getValueAt(maintablerow, 3)));
	}

    public void actionPerformed( ActionEvent e) {  
	if (e.getSource().equals(exitproject)){
	    dispose();	
	}

	if (e.getSource().equals(addproject)){
	//	int initialValue = 49999;
		//if(ula.getGenericId(bean.connect().geneProtoolId())==null){
		//	new AddProject("NEW PROJECT", initialValue);
			
		//	}
		//	else{
				
				new AddProject("NEW PROJECT", ul.getGenericId(bean.connect().geneProtoolId(), 10000));
		//	}

		}
		
		if(e.getSource().equals(searchproject)){
			for(int i = 0 ; i < projectDataModel.getRowCount(); i++){
				for(int j = 0; j < projectDataModel.getColumnCount(); j++){
					projectDataModel.setValueAt(new String(""), i, j);
				}
			}
			if(!quote_from.getText().equals("") && !quote_to.getText().equals("")){
				ul.loadDate(projectDataModel, quote_from, quote_to);
				if(projectTitle.getText().equals("")){
					ul.findRecord(projectDataModel, clientName, 2);
				}
				else if(clientName.getText().equals("")){
				ul.findRecord(projectDataModel, projectTitle, 1);
				clientName.setText("");
				}
				
			}
			else {
				ul.loadDate(projectDataModel, sh1.getGenericDate(date_from), sh2.getGenericDate(date_to));
				if(projectTitle.getText().equals("")){
					ul.findRecord(projectDataModel, clientName, 2);
				}
				else if(clientName.getText().equals("")){
				ul.findRecord(projectDataModel, projectTitle, 1);
				clientName.setText("");
				}
				//loadDateFrom();
			}
		
		}
		if(e.getSource().equals(details)){

			Details ds = new Details(getTableId(), "Client Profile & Ledger");
		}
		if(e.getSource().equals(find)){
			if(projectTitle.getText().equals("")){
			ul.findRecord(projectDataModel, clientName, 2);
			}
			else{
				ul.findRecord(projectDataModel, projectTitle, 1);
				clientName.setText("");
			}
		}
		
		if(e.getSource().equals(editproject)){
			  new EditProject("EDIT PROJECT", getTableId());
			
		}
		if(e.getSource().equals(invoice)){
			 new AddPaymentin("INVOICE", getTableId(), getProjectTitle());//, ul.getStringInCurrency(getTableQuote()
			
		}
		if(e.getSource().equals(removeproject)){
			bean.connect().deleteProject(ul.writeInteger(new Integer(getTableId())));
			 dispose();	
			new ProtoolProject("P M T");
		}
    }



}