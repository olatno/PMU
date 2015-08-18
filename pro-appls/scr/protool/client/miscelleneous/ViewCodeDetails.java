package scr.protool.client.miscelleneous;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import javax.naming.*;
import java.io.*;
import scr.protool.client.utilities.*;

/**

*
*

*
* @author <a href="mailto:"></a>
* @version 1.0
*/
public class ViewCodeDetails extends JPanel implements  ActionListener{// implements Serializable, ActionListener{

    private BeanStud bean;
    private JSpinner date_from;
    private JSpinner date_to;
    private JTextField classification;
    private JTextField accountname;
    private JButton addproject;
    private JButton searchproject;
    private JButton edit;
    private JButton refresh;
    private JPanel buttonpanel;
    private JPanel searchpanel;
    private JPanel tablepanel;
    private SpinnerHelper sh1;
	private SpinnerHelper sh2;
	private JPanel search;
   // private AddProject addProject;
	private Utilities ul = new Utilities();
    private JTable projectView; 
    private DefaultTableModel projectDataModel; 
    private JScrollPane projectViewScroll; 
    private final String[]columnNames = {"ACCOUNTS ID ","ACCOUNTS NAME","CLASSIFICATION","DATE CREATED","VISIBILITY"};
    private final int initialRow = 18;
    private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int textFieldColunm = 1;
	private final int gab = 22;
    private final double weightix = 1.0;
	private ProtoolMainGUI progui;
	Dimension buttond = new Dimension(75, 18);
    
    public ViewCodeDetails(String title, ProtoolMainGUI progui){
   	super(new BorderLayout()); 
   
	this.progui = progui;
   	bean = new BeanStud();
	buttonpanel = new JPanel();
	searchpanel = new JPanel();
	tablepanel = new JPanel();
	search = new JPanel();
	sh1 = new SpinnerHelper();
	sh2 = new SpinnerHelper();

	//Panels
	buttonpanel.setLayout(new GridBagLayout());
	searchpanel.setLayout(new GridBagLayout());
	search.setLayout(new GridBagLayout());
	tablepanel.setLayout(new BorderLayout());
		
	GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 0;
	date1Label.weightx=0.4;
	date1Label.insets = new Insets(0, 0, gab, 0);
	date1Label.anchor =  GridBagConstraints.EAST;
	searchpanel.add(new JLabel("DATE FROM:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	date_from = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 0;
	date1TextField.ipady = ipadiy;
	date1TextField.insets = new Insets(0, 0, gab, 0);
	date1TextField.ipadx = 50;
	date1TextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(sh1.getGenericDate(date_from), date1TextField);

	GridBagConstraints date2Label = new GridBagConstraints();
	date2Label.gridx = 2;
	date2Label.gridy = 0;
	date2Label.insets = new Insets(0, 40, gab, 0);
	date2Label.anchor =  GridBagConstraints.EAST;
	searchpanel.add(new JLabel("DATE TO:"), date2Label);

	GridBagConstraints date2TextField = new GridBagConstraints();
	date_to = new JSpinner();
	date2TextField.gridx = 3;
	date2TextField.gridy = 0;
	date2TextField.ipady = ipadiy;
	date2TextField.insets = new Insets(0, 0, gab, 0);
	date2TextField.ipadx = 50;
	date2TextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(sh2.getGenericDate(date_to), date2TextField);
	
	GridBagConstraints classLabel = new GridBagConstraints();
	classLabel.gridx = 0;
	classLabel.gridy = 1;
	classLabel.anchor =  GridBagConstraints.EAST;
	searchpanel.add(new JLabel("CLASSIFICATION:"), classLabel);

	GridBagConstraints classTextField = new GridBagConstraints();
	classification = new JTextField(textFieldColunm);
	classTextField.gridx = 1;
	classTextField.gridy = 1;
	classTextField.ipady = ipadiy;
	classTextField.ipadx = ipadix;
	classTextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(classification, classTextField);


	GridBagConstraints quote2Label = new GridBagConstraints();
	quote2Label.gridx = 2;
	quote2Label.gridy = 1;
	quote2Label.anchor =  GridBagConstraints.EAST;
	quote2Label.insets = new Insets(0, 40, 0, 0);
	searchpanel.add(new JLabel("ACCOUNTS NAME:"), quote2Label);

	GridBagConstraints accountnameTextField = new GridBagConstraints();
	accountname = new JTextField(textFieldColunm);
	accountnameTextField.gridx = 3;
	accountnameTextField.gridy = 1;
	accountnameTextField.ipady = ipadiy;
	accountnameTextField.ipadx = ipadix;
	accountnameTextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(accountname, accountnameTextField);
	
	
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
	
	GridBagConstraints editButton = new GridBagConstraints();
	edit = new JButton("<html><b>Edit</b></html>");
	editButton.gridx = 2;
	editButton.gridy = 0;
	editButton.insets = new Insets(5, 0, 0, 0);
	buttonpanel.add(edit, editButton );

	GridBagConstraints refreshButton = new GridBagConstraints();
	refresh = new JButton("<html><b>Refresh</b></html>");
	refreshButton.gridx = 1;
	refreshButton.gridy = 0;
	refreshButton.insets = new Insets(5, 0, 0, 0);
	buttonpanel.add(refresh, refreshButton);
	
	//Table formation
	Vector<String> columns = new Vector<String>(); 
	for(int tableHeader = 0; tableHeader < columnNames.length; tableHeader++) { 
	    columns.addElement(columnNames[tableHeader]); 
	} 
	projectDataModel = new DefaultTableModel(columns, initialRow); 
	projectView = new JTable(new TableDataModel());  

	int[]widthValue = {50,150,160,50,30};
	for(int i = 0; i<widthValue.length; i++){
	    TableColumn col  = projectView.getColumnModel().getColumn(i);
	    col.setPreferredWidth(widthValue[i]);
	}
	
	//accounts code format to right of cell
    TableColumn colnumber  = projectView.getColumnModel().getColumn(0);
	DefaultTableCellRenderer render = new DefaultTableCellRenderer();
	render.setHorizontalAlignment(render.LEFT);
	colnumber.setCellRenderer(render);
	

	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	projectViewScroll = new JScrollPane(projectView, v,h); 
	tablepanel.add(projectViewScroll); 
	projectViewScroll.setMinimumSize(new Dimension (720, 323));

	add("South", buttonpanel);
	add("North", searchpanel); 
	add("Center", tablepanel);


	addproject.addActionListener(this); 
	refresh.addActionListener(this); 
	searchproject.addActionListener(this);
	edit.addActionListener(this);	
	//loadData();*/
    }
	public int getCodeId(){
		return new Integer(String.valueOf(new TableDataModel().getValueAt(projectView.getSelectedRow(), 0))).intValue();
	}
	
	public void actionPerformed( ActionEvent e) {  
		
		if (e.getSource().equals(addproject)){
			
	    	new AddCodeDetails("NEW ACCOUNTS CODE");	
			//  	new AddCodeDetails("NEW ACCOUNTS CODE", 4000000);	
		}
	  
	  	if (e.getSource().equals(edit)){
				new EditCodeDetails(getCodeId(), "EDIT ACCOUNTS CODE");
		}
 	  if (e.getSource().equals(refresh)){
	         progui.refeshButton(new ViewCodeDetails("ACCOUNTS CODE", progui));	
		} 
    }

}