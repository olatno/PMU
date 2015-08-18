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
public class ProjectViews extends JPanel implements Serializable, ActionListener{

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
    private JComboBox projectActivities;
    private JButton details;
    private PaymentInUtilities pu;
    private JPanel buttonpanel;
    private JPanel searchpanel;
	private JPanel gridButtonPanel;
    private JPanel flowButtonPanel;
	private JPanel bottomCombine;
    private JPanel tablepanel;
    private AddProject addProject;
	private AddPaymentin addPayment;
	private EditProject editProject;
	private Utilities ul = new Utilities();
	private AccountsUtilities ula = new AccountsUtilities();
    private JTable projectView; 
    private DefaultTableModel projectDataModel; 
    private JScrollPane projectViewScroll; 
    private final String[]columnNames = {"Project Title","Company Name","Quote","Date","Status","Invoiced",""};
    private final int initialRow = 18;
    private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int textFieldColunm = 1;
    private final double weightix = 1.0;
	private   SpinnerHelper sh1;
	private   SpinnerHelper sh2; 


    public ProjectViews(String title){
   	super(new BorderLayout()); 
    bean = new BeanStud();
	flowButtonPanel = new JPanel();
	gridButtonPanel = new JPanel() ;
	buttonpanel = new JPanel();
	bottomCombine = new JPanel();
	searchpanel = new JPanel();
	tablepanel = new JPanel();

	//Panels
	GridLayout buttomCom = new GridLayout(1, 2);
	buttomCom.setHgap(-70);
	bottomCombine.setLayout(buttomCom);
	buttonpanel.setLayout(new GridBagLayout());
	searchpanel.setLayout(new GridBagLayout());
	gridButtonPanel.setLayout(new GridBagLayout());
	flowButtonPanel.setLayout(new GridBagLayout());
	tablepanel.setLayout(new BorderLayout());
	sh1 = new SpinnerHelper();
	sh2 = new SpinnerHelper();
	pu = new PaymentInUtilities();


	//Textfields use for search
	GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 0;
	date1Label.weightx=0.4;
	date1Label.insets = new Insets(0, 0, 0, 5);
	searchpanel.add(new JLabel("Date From:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	date_from = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 0;
	date1TextField.ipady = ipadiy;
	date1TextField.ipadx = 51;
	date1TextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(sh1.getGenericDate(date_from), date1TextField);

	GridBagConstraints date2Label = new GridBagConstraints();
	date2Label.gridx = 2;
	date2Label.gridy = 0;
	date2Label.insets = new Insets(0, 2, 0, 0);;
	searchpanel.add(new JLabel("Date To:"), date2Label);

	GridBagConstraints date2TextField = new GridBagConstraints();
	date_to = new JSpinner();
	date2TextField.gridx = 3;
	date2TextField.gridy = 0;
	date2TextField.ipady = ipadiy;
	date2TextField.ipadx = 51;
	date2TextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(sh2.getGenericDate(date_to), date2TextField);
	
	GridBagConstraints quote1Label = new GridBagConstraints();
	quote1Label.gridx = 0;
	quote1Label.gridy = 1;
	//quote1Label.anchor =  GridBagConstraints.EAST;
	searchpanel.add(new JLabel("Quote From:"), quote1Label);

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
	quote2Label.insets = new Insets(0, 8, 0, 0);
	searchpanel.add(new JLabel("Quote To:"), quote2Label);

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
	//clientLabel.anchor =  GridBagConstraints.EAST;
    searchpanel.add(new JLabel("Client Name:"), clientLabel);

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
	titleLabel.insets = new Insets(0, 0, 0, -24);
	searchpanel.add(new JLabel("Project Title:"), titleLabel);

	GridBagConstraints titleTextField = new GridBagConstraints();
	projectTitle = new JTextField(textFieldColunm);
	titleTextField.gridx = 3;
	titleTextField.gridy = 2;
	titleTextField.ipady = ipadiy;
	titleTextField.ipadx = ipadix;

	titleTextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(projectTitle, titleTextField);
	
	GridBagConstraints searchbutton = new GridBagConstraints();
	
	searchproject = new JButton("Search");
	searchbutton.gridx = 4;
	searchbutton.gridy = 2;
	searchbutton.ipady = 0;
    searchbutton.weightx = weightix;
	searchpanel.add(searchproject, searchbutton);
	
   searchpanel.setBorder(new Partition(20,"Tool"));
	
	//BUTTON SOUTH
	GridBagConstraints combbox = new GridBagConstraints();
	String[] taskStrings = { "Project Details", "Financial Activities"};
	projectActivities = new JComboBox(taskStrings);
	combbox.gridx = 0;
	combbox.gridy = 0;
	combbox.ipady = 1;
	combbox.insets = new Insets(5, 0, 0, 0);
	gridButtonPanel.add(projectActivities, combbox);
	//gridButtonPanel.add(projectActivities);
	
	GridBagConstraints detailsButton = new GridBagConstraints();
	details = new JButton("<html><b>Details</b></html>");
	detailsButton.gridx = 1;
	detailsButton.gridy = 0;
	detailsButton.insets = new Insets(5, 0, 0, 0);
	gridButtonPanel.add(details, detailsButton);
	//gridButtonPanel.add(details);
	
	GridBagConstraints invoiceButton = new GridBagConstraints();
	invoice = new JButton("<html><b>Invoice</b></html>");
	invoiceButton.gridx = 2;
	invoiceButton.gridy = 0;
	invoiceButton.insets = new Insets(5, 0, 0, 0);
	gridButtonPanel.add(invoice, invoiceButton );
	//gridButtonPanel.add(invoice);
	
	bottomCombine.add(gridButtonPanel);
	
	GridBagConstraints addButton = new GridBagConstraints();
	addproject = new JButton("<html><b><u>N</u>ew Quote</b></html>");
	addButton.gridx = 0;
	addButton.gridy = 0;
	addButton.insets = new Insets(5, 0, 0, 0);
	flowButtonPanel.add(addproject, addButton);
	//flowButtonPanel.add(addproject);
	
	bottomCombine.add(flowButtonPanel);
	
	GridBagConstraints bottomButton = new GridBagConstraints();
	bottomButton.gridx = 0;
	bottomButton.gridy = 0;
	bottomButton.insets = new Insets(0, 260, 0, 0);
	buttonpanel.add(bottomCombine, bottomButton);
	

	//Table formation
	Vector<String> columns = new Vector<String>(); 
	for(int tableHeader = 0; tableHeader < columnNames.length; tableHeader++) { 
	    columns.addElement(columnNames[tableHeader]); 
	} 
	projectDataModel = new DefaultTableModel(columns, initialRow); 
	projectView = new JTable(projectDataModel);
	
 	//remove last column that contain project id from view
 	projectView.removeColumn( projectView.getColumnModel().getColumn(6)); 
	
	int[]widthValue = {315,260,110,110,110,110};
	for(int i = 0; i<widthValue.length; i++){
	    TableColumn col  = projectView.getColumnModel().getColumn(i);
	    col.setPreferredWidth(widthValue[i]);
	}

	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	projectViewScroll = new JScrollPane(projectView, v,h); 
	tablepanel.add(projectViewScroll); 
	projectViewScroll.setMinimumSize(new Dimension (720, 323));
	
	add("South", buttonpanel);
	add("North", searchpanel); 
	add("Center", tablepanel);

	invoice.addActionListener(this); 
	projectActivities.addActionListener(this); 
	addproject.addActionListener(this); 
	searchproject.addActionListener(this); 
	details.addActionListener(this);

	//	ul.loadDate(projectDataModel, bean.connect().getProjectData());

    }


	
	public JTable getTablename(){
		return projectView;
	}
	
	public DefaultTableModel getTableModelName(){
		return projectDataModel;
	}
	public int getTableId(){
		int  maintablerow = projectView.getSelectedRow();
		return ((Integer)projectDataModel.getValueAt(maintablerow, 6)).intValue();
	}
	
	public String	getProjectTitle(){
		int  maintablerow = projectView.getSelectedRow();
		return (String)projectDataModel.getValueAt(maintablerow, 0);
	}
	
	public  BigDecimal getTableQuote(){
		int  maintablerow = projectView.getSelectedRow();
		return new BigDecimal(String.valueOf(projectDataModel.getValueAt(maintablerow, 2)));
	}

    public void actionPerformed( ActionEvent e) {  


	if (e.getSource().equals(addproject)){	
			new AddProject("New Project", ul.getGenericId(bean.connect().geneProtoolId(), 10000));
		}
		

		if (e.getSource().equals(searchproject)){

			if((quote_from.getText().equals("") && quote_to.getText().equals("")) && (clientName.getText().equals("")&&projectTitle.getText().equals(""))){
				//date search
				pu.loadData(projectDataModel, bean.connect().getProjectDetails(pu.sqlDateString(sh1.getGenericDate(date_from)), pu.sqlDateString(sh2.getGenericDate(date_to))), initialRow);

			}
			else if((clientName.getText().equals("")&&projectTitle.getText().equals(""))&&(!quote_from.getText().equals("") && !quote_to.getText().equals(""))){
					//quote search and reset 
					pu.loadData(projectDataModel, bean.connect().getProjectDetails(pu.sqlDateString(sh1.getGenericDate(date_from)), pu.sqlDateString(sh2.getGenericDate(date_to)), new BigDecimal(quote_from.getText()), new BigDecimal(quote_to.getText())), initialRow);

			}
			else if ((quote_from.getText().equals("") && quote_to.getText().equals(""))&&(projectTitle.getText().equals("")&&!clientName.getText().equals(""))){
					//client name search
					pu.loadData(projectDataModel, bean.connect().getProjectDetailClient(pu.sqlDateString(sh1.getGenericDate(date_from)), pu.sqlDateString(sh2.getGenericDate(date_to)), clientName.getText()), initialRow);

			}
			else if((quote_from.getText().equals("") && quote_to.getText().equals("")) && (!projectTitle.getText().equals("") && clientName.getText().equals(""))){
				//project title search	
				pu.loadData(projectDataModel, bean.connect().getProjectDetailTitle(pu.sqlDateString(sh1.getGenericDate(date_from)), pu.sqlDateString(sh2.getGenericDate(date_to)), projectTitle.getText()), initialRow);

			}
	}
		if(e.getSource().equals(details)){
		
			String taskName = (String)projectActivities.getSelectedItem();
			if(taskName.equals("Project Details")){
				new ProjectDetails(getTableId(), "Client Profile & Quote");
			}
			else{
				new FinancialActivities("Project Financial Activities", getTableId(), getProjectTitle());
			}

		}

		

		if(e.getSource().equals(invoice)){
	
			 new AddPaymentin("New Invoice", getTableId(), getProjectTitle());//, ul.getStringInCurrency(getTableQuote()

			
		}

    }



}