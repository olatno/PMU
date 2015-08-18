package scr.protool.client.fixedasset;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import javax.naming.*;
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
public class AssetRegisterView extends JPanel implements  ActionListener{// implements Serializable, ActionListener{

    private JSpinner date_from;
    private JSpinner date_to;
    private JTextField accountNumber;
    private JTextField description;
    private JButton addproject;
    private JButton searchposting;
    private JComboBox info;
    private JButton details;
    private JPanel buttonpanel;
    private JPanel searchpanel;
    private JPanel tablepanel;
    private SpinnerHelper sh1;
	private SpinnerHelper sh2;
	private BeanStud bean;
	private Utilities ul = new Utilities();
	private PaymentInUtilities pu = new PaymentInUtilities();
    private JTable assetView; 
    private DefaultTableModel assetDataModel; 
    private JScrollPane projectViewScroll; 
    private final String[]columnNames = {"Asset Type","Description","Cost","Accumaleted Dep","Acqusition Date","Status", "", ""};
	//private final String[]columnNames = {"Asset Type","Description","Supplier Name","disposal Date","Amount", "Disposal Type", ""};
    private final int initialRow = 18;
    private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int textFieldColunm = 1;
	private final int gab = 22;
    private final double weightix = 1.0;
	Dimension buttond = new Dimension(75, 18);

    
    public AssetRegisterView(String title){
   	super(new BorderLayout()); 
   
	buttonpanel = new JPanel();
	searchpanel = new JPanel();
	tablepanel = new JPanel();
	sh1 = new SpinnerHelper();
	sh2 = new SpinnerHelper();
	bean = new BeanStud();

	//Panels
	buttonpanel.setLayout(new GridBagLayout());
	searchpanel.setLayout(new GridBagLayout());
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
	searchpanel.add(new JLabel("Accounts Number:"), classLabel);

	GridBagConstraints classTextField = new GridBagConstraints();
	accountNumber = new JTextField(textFieldColunm);
	classTextField.gridx = 1;
	classTextField.gridy = 1;
	classTextField.ipady = ipadiy;
	classTextField.ipadx = ipadix;
	classTextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(accountNumber, classTextField);


	GridBagConstraints quote2Label = new GridBagConstraints();
	quote2Label.gridx = 2;
	quote2Label.gridy = 1;
	quote2Label.anchor =  GridBagConstraints.EAST;
	quote2Label.insets = new Insets(0, 40, 0, 0);
	searchpanel.add(new JLabel("Description:"), quote2Label);

	GridBagConstraints descriptionTextField = new GridBagConstraints();
	description = new JTextField(textFieldColunm);
	descriptionTextField.gridx = 3;
	descriptionTextField.gridy = 1;
	descriptionTextField.ipady = ipadiy;
	descriptionTextField.ipadx = ipadix;
	descriptionTextField.anchor =  GridBagConstraints.WEST;
	searchpanel.add(description, descriptionTextField);
	
	
	GridBagConstraints searchbutton = new GridBagConstraints();
	searchposting = new JButton("SEARCH");
	searchbutton.gridx = 4;
	searchbutton.gridy = 1;
	searchbutton.ipady = 0;
    searchbutton.weightx = weightix;
	searchpanel.add(searchposting, searchbutton);
	
	
    searchpanel.setBorder(new Partition(20,"Tool"));
	//BUTTON WEST


	GridBagConstraints addButton = new GridBagConstraints();
	addproject = new JButton("<html><b><u>N</u>ew</b></html>");
	addButton.gridx = 0;
	addButton.gridy = 0;
	addButton.insets = new Insets(5, 0, 0, 30);
	buttonpanel.add(addproject, addButton);
	
	GridBagConstraints addInfo = new GridBagConstraints();
	String[] taskStings = {"Finacial Summary","Additional Info"};
	info = new JComboBox(taskStings);
	//info.add("Finacial Summary");
	//info.add("Additional Info");
	addInfo.gridx = 1;
	addInfo.gridy = 0;
	addInfo.insets = new Insets(5, 0, 0, 0);
	buttonpanel.add(info, addInfo);
		
	GridBagConstraints detailsButton = new GridBagConstraints();
	details = new JButton("<html><b>Details</b></html>");
	detailsButton.gridx = 2;
	detailsButton.gridy = 0;
	detailsButton.insets = new Insets(5, 0, 0, 0);
	buttonpanel.add(details, detailsButton);
	
/*	GridBagConstraints editButton = new GridBagConstraints();
	edit = new JButton("<html><b>Edit</b></html>");
	editButton.gridx = 2;
	editButton.gridy = 0;
	editButton.insets = new Insets(5, 0, 0, 0);
	buttonpanel.add(edit, editButton );*/

	
	//Table formation
	Vector<String> columns = new Vector<String>(); 
	for(int tableHeader = 0; tableHeader < columnNames.length; tableHeader++) { 
	    columns.addElement(columnNames[tableHeader]); 
	} 
	assetDataModel = new DefaultTableModel(columns, initialRow); 
	assetView = new JTable(assetDataModel);  
 
	int[]widthValue = {80,80,60,60,60,40};

	for(int i = 0; i<widthValue.length; i++){
	    TableColumn col  = assetView.getColumnModel().getColumn(i);
	    col.setPreferredWidth(widthValue[i]);
	}
	assetView.removeColumn(assetView.getColumnModel().getColumn(7));
	assetView.removeColumn(assetView.getColumnModel().getColumn(6));
	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	projectViewScroll = new JScrollPane(assetView, v,h); 
	tablepanel.add(projectViewScroll); 
	projectViewScroll.setMinimumSize(new Dimension (720, 323));

	add("South", buttonpanel);
	add("North", searchpanel); 
	add("Center", tablepanel);

	addproject.addActionListener(this); 
	searchposting.addActionListener(this);
	info.addActionListener(this);
	details.addActionListener(this);
     pu.loadData(assetDataModel, bean.connect().getAssetDetails(), initialRow);
    }
	
	public DefaultTableModel getTableModel(){
		return assetDataModel;
	}
	
	public JTable getTable(){
	   return assetView;
	}
	
	public void actionPerformed( ActionEvent e) {  
		if(e.getSource().equals(addproject)){
			new AssetsList("Assets List");
		
		}
		if (e.getSource().equals(searchposting)){

			if(accountNumber.getText().equals("") && description.getText().equals("")){
				pu.loadData(assetDataModel, bean.connect().getAssetDetails(pu.sqlDateString(sh1.getGenericDate(date_from)),
							pu.sqlDateString(sh2.getGenericDate(date_to))), initialRow);
			}
			else if(!accountNumber.getText().equals("") && description.getText().equals("")){
					pu.loadData(assetDataModel, bean.connect().getAssetDetails(pu.sqlDateString(sh1.getGenericDate(date_from)),
							pu.sqlDateString(sh2.getGenericDate(date_to)), (Integer.valueOf(accountNumber.getText())).intValue()), initialRow);
			}
			else if(accountNumber.getText().equals("") && !description.getText().equals("")){
					pu.loadData(assetDataModel, bean.connect().getAssetDetails(pu.sqlDateString(sh1.getGenericDate(date_from)),
							pu.sqlDateString(sh2.getGenericDate(date_to)), description.getText()), initialRow);
			}
		}
		/*if(e.getSource().equals(edit)){
			new EditDoubleEntry("Edit Double Entry", this);
		}*/
		//JComboBox cb = (JComboBox)e.getSource();
	
		if(e.getSource().equals(details) && ((String)info.getSelectedItem()).equals("Additional Info")){
			new FixedAssetDetails("Fixed Asset Details", ul.getTableInt(assetView, assetDataModel, 6));
		}
		if(e.getSource().equals(details) && ((String)info.getSelectedItem()).equals("Finacial Summary")){
			new AssetProcessedDetails("Processed Depreciation Details", this);
		}
    }

}