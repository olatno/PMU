
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
import scr.protool.client.utilities.*;
import scr.protool.client.miscelleneous.BeanStud;

/**

*
*

*
* @author <a href="mailto:"></a>
* @version 1.0
*/
public class GeneralExpenses extends JFrame implements ActionListener{//implements ActionListener{
    private Choice group;
    private JTextField searchField;
    private JTable generalView;
    private JTable entryView; 	
    private DefaultTableModel generalDataModel; 
	private DefaultTableModel entryDataModel;
    private JScrollPane generalViewScroll;
    private JScrollPane entryViewScroll; 	
    private final String[]columnNames = {"ACCOUNTS CODE","ACCOUNTS NAME","DATE CREATED"};
	private JComponent enrtyViews;
	private JComponent generalViews;
	private  JTabbedPane tabbedPane;
    private JButton refresh;
    private JButton go;
    private JButton exit;
	private JButton find;
    private JPanel addpanel;
    private JPanel buttonpanel;
	private JPanel buttonpanel2;
    private JPanel generalPanel;
	private JPanel tablepanel;
    private Context ctx;
	private   SpinnerHelper sh;
	private final int initialRow = 23;
    private String client = "";
	private BeanStud bean;
	private Utilities ul = new Utilities();
	private PaymentInUtilities pu =  new PaymentInUtilities();
	private final double weightye = 0.5;
	private boolean generalsView = true;
    private Container con = null;
    
    public GeneralExpenses(String title){//, int proId
   	super(title); 

	con = getContentPane();
	addpanel = new JPanel();
	buttonpanel = new JPanel();
	buttonpanel2 = new JPanel();
	generalPanel = new JPanel();
	tablepanel = new JPanel();
	tabbedPane = new JTabbedPane();
	tablepanel.setLayout(new BorderLayout());
	sh = new SpinnerHelper();
	bean = new BeanStud();
	//Panels
	addpanel.setLayout(new GridBagLayout());
	buttonpanel.setLayout(new GridBagLayout());
	generalPanel.setLayout(new BorderLayout());

	
	GridBagConstraints idTextField = new GridBagConstraints();
	searchField = new JTextField();
	idTextField.gridx = 0;
	idTextField.gridy = 0;
	idTextField.ipady = 2;
	idTextField.gridwidth=4;
	idTextField.ipadx = 350;
	idTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(searchField, idTextField);
	//searchField.setText("%");
	
	GridBagConstraints findButton = new GridBagConstraints();
	find = new JButton("Find");
	findButton.gridwidth = GridBagConstraints.REMAINDER;
	addpanel.add(find, findButton);
	
	GridBagConstraints separator = new GridBagConstraints();
	JSeparator separate = new JSeparator(SwingConstants.HORIZONTAL);
	separator.gridx = 0;
	separator.gridy = 1;
	separator.weightx = 1.0;
	separator.fill = GridBagConstraints.HORIZONTAL;
	separator.gridwidth=5;
	separator.insets = new Insets(20, 0, 10, 0);
	separate.setPreferredSize(new Dimension(200, 5));
	addpanel.add(separate, separator);
	

	Vector<String> columns = new Vector<String>(); 
	for(int tableHeader = 0; tableHeader < columnNames.length; tableHeader++) { 
		columns.addElement(columnNames[tableHeader]); 
	} 
	generalDataModel = new DefaultTableModel(columns, initialRow); 
	generalView = new JTable(generalDataModel);  
		
	int[]widthValue = {60,100,60};
	for(int i = 0; i<widthValue.length; i++){
		TableColumn col  = generalView.getColumnModel().getColumn(i);
		col.setPreferredWidth(widthValue[i]);
	}
		//projectView.removeColumn(projectView.getColumnModel().getColumn(3));
	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	generalViewScroll = new JScrollPane(generalView, v,h); 
	tablepanel.add(generalViewScroll); 

	//BUTTON BOTTOM
	GridBagConstraints groupSelection = new GridBagConstraints();
	group = new Choice();
	group.add("Select ");
	group.add("Prepaid");
	group.add("Paid");
	group.add("Payable");
	group.add("Accrued");
	groupSelection.gridx = 0;
	groupSelection.gridy = 0;
	groupSelection.ipady = 2;
	groupSelection.ipadx = 90;
	buttonpanel.add(group, groupSelection);
	
	GridBagConstraints goButton = new GridBagConstraints();
	go = new JButton("Go");
	goButton.gridx = 1;
	goButton.gridy = 0;
	goButton.insets = new Insets(0, 2, 0, 2);
	buttonpanel.add(go, goButton);
	
	GridBagConstraints refreshButton = new GridBagConstraints();
	refresh= new JButton("Refresh");
	refreshButton.gridx = 2;
	refreshButton.gridy = 0;
	buttonpanel.add(refresh, refreshButton);
	
	GridBagConstraints exitButton = new GridBagConstraints();
	exit = new JButton("Exit");
	exitButton.gridx = 3;
	exitButton.gridy = 0;
	exitButton.insets = new Insets(0, 20, 0, 0);
	buttonpanel.add(exit, exitButton);
	
	generalPanel.add("North", addpanel);
	generalPanel.add("Center", tablepanel);
	generalPanel.add("South", buttonpanel);
	generalPanel.setBorder(new Partition(20,"AddPaymentout"));
	con.add(generalPanel);

	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(500, 600);
	setResizable(false);
	setVisible(true); 
	exit.addActionListener(this);  
	go.addActionListener(this); 
	refresh.addActionListener(this); 
	find.addActionListener(this); 

	pu.loadData(generalDataModel, bean.connect().getOperationalExpensesCodes());
	//	}
    }
	
   
	public int getTableId(int column, DefaultTableModel dataModel, JTable view){
		int  maintablerow = view.getSelectedRow();
		return ((Integer)dataModel.getValueAt(maintablerow, column)).intValue();
	}
	
	
	public String getStringValue(int column, DefaultTableModel dataModel, JTable view){
		int  maintablerow = view.getSelectedRow();
		return (String)dataModel.getValueAt(maintablerow, column);
	}
	
	
	public DefaultTableModel getModelGeneral(){
		return generalDataModel;
	
	}
	
	public JTable getTableGeneral(){
	   return generalView;
	}
	


   public void actionPerformed( ActionEvent e) {  
	
			if(group.getSelectedItem().equals("Prepaid") && e.getSource().equals(go)){
				new AddExpensesPrepaid("Prepaid Expenses", this);
				dispose();	
			}
			if(group.getSelectedItem().equals("Paid") && e.getSource().equals(go)){
				new AddExpensesPaid("Paid Expenses", this);
				dispose();	
			}
			if(group.getSelectedItem().equals("Payable") && e.getSource().equals(go)){
				new AddExpensesPayable("Payable Expenses", this);
				dispose();	
			}
			if(group.getSelectedItem().equals("Accrued") && e.getSource().equals(go)){
				new AddExpensesAccrued("Accrued Expenses", this);
				dispose();	
			}

		if(e.getSource().equals(find)){
			//searchField.setText(searchField.getText().substring(1, searchField.getText().length()));
			if(generalsView){
				ul.findRecord(generalDataModel, searchField, 1);
			}
			else{
				ul.findRecord(entryDataModel, searchField, 1);
			}
		}
		if (e.getSource().equals(refresh)){
			dispose();	
			new GeneralExpenses("new General Expenses");
		}
		if (e.getSource().equals(exit)){
			dispose();	
		}
	}

}