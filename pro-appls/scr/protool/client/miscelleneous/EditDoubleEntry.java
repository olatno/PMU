package scr.protool.client.miscelleneous;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.text.*;
import javax.naming.*;
import java.util.*;
import java.io.*;
import java.math.*;
import scr.protool.client.utilities.*;
import scr.protool.client.miscelleneous.BeanStud;


/**

*
*

*
* @author <a href="mailto:"></a>
* @version 1.0
*/
public class EditDoubleEntry extends JFrame implements ActionListener, ItemListener{//implements ActionListener{
  
    private JTextField name;
	private JTextField info;
	private JButton changeName;
    private JTextField amount1;
    private JTextField amount2;
    private Choice group1;
	private Choice group2;
    private JSpinner date; 
    private JButton save;
    private JButton ok;
    private JButton exit;
    private JPanel addpanel;
    private JPanel buttonpanel;
    private JPanel generalPanel;
    private Context ctx;
	private SpinnerHelper sh;
    private String client = "";
    private BeanStud bean;
	private ManualPostingView postView;
	private final int textFieldColunm = 1;
	private Utilities ul = new Utilities();
	private final double weightye = 0.5;
	private PaymentInUtilities pu = new PaymentInUtilities();
	private LinkedHashMap<Integer,String> codeName;//= new LinkedHashMap<Integer,String>()
	private LinkedHashMap<String,BigDecimal> codePosted;// = new LinkedHashMap<Integer,String>()
	private Object[] keyObj;
	private Object[] keyObjPosted;
	private String item;
	private int principleCode;
	private int manualPostingId;
	private boolean principle = false;
    Container con = getContentPane();
    
    public EditDoubleEntry(String title, ManualPostingView postView){//, int proId LinkedHashMap<Integer,String> codeName, String item
   	super(title); 
	
	this.postView = postView;
	addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();
	sh = new SpinnerHelper();
	bean = new BeanStud();
	manualPostingId = ul.getTableInt(postView.getTable(),
							postView.getTableModel(), 5);
	ArrayList<Object> editInfo = pu.getArrayList(bean.connect().editableManualPosting(manualPostingId));
								
	codePosted = (LinkedHashMap<String,BigDecimal>)editInfo.get(0);
	codeName = (LinkedHashMap<Integer,String>)editInfo.get(1);
	keyObj = codeName.keySet().toArray();
	keyObjPosted = codePosted.keySet().toArray();
	//Panels 
	addpanel.setLayout(new GridBagLayout());
	buttonpanel.setLayout(new GridBagLayout());
	generalPanel.setLayout(new BorderLayout());

	//Textfields u
	GridBagConstraints nameLabel = new GridBagConstraints();
	changeName = new JButton("Change");
	nameLabel.gridx = 0;
	nameLabel.gridy = 0;
	nameLabel.anchor =  GridBagConstraints.EAST;
   	nameLabel.weighty = weightye;
	addpanel.add(changeName, nameLabel);

	GridBagConstraints nameTextField = new GridBagConstraints();
	name = new JTextField(textFieldColunm);
	nameTextField.gridx = 1;
	nameTextField.gridy = 0;
	nameTextField.ipady = 2;
	nameTextField.ipadx = 110;
	nameTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(name, nameTextField);
	name.setEditable(false);
	String strCode = (String)keyObjPosted[0];
	String strCodeName[] = strCode.split("\\.");
	principleCode =  (Integer.valueOf(strCodeName[0])).intValue();
	item = ul.getStringValue(postView.getTable(), postView.getTableModel(), 3);
	name.setText(String.format("%s %s", item, strCodeName[1]));
	
	GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 1;
	date1Label.weighty = weightye;
    date1Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Post Date:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	date = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 1;
	date1TextField.ipady = 2;
	date1TextField.ipadx = -13;
	date1TextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(sh.getGenericDateEdit(date, ul.getStringValue(postView.getTable(), postView.getTableModel(), 4)),
										date1TextField);
	
	GridBagConstraints group1Label = new GridBagConstraints();
	group1Label.gridx = 0;
	group1Label.gridy = 2;
	group1Label.ipadx = 60;
	group1Label.weighty = weightye;
	group1Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Posting1:", JLabel.RIGHT), group1Label);

	String strCode1 = (String)keyObjPosted[1]; 
	String strCodeName1[] = strCode1.split("\\.");
	GridBagConstraints group1TextField = new GridBagConstraints();
	group1 = new Choice();
	group1.add("Select");
	for(int i = 0; i < keyObj.length; i++){
			group1.add(codeName.get((Integer)keyObj[i]));
	}
	group1.select(strCodeName1[1]);
	group1TextField.gridx = 1;
	group1TextField.gridy = 2;
	group1TextField.ipady = 2;
	group1TextField.ipadx = 23;
	group1TextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(group1, group1TextField);
	
	GridBagConstraints amouth1Label = new GridBagConstraints();
	amouth1Label.gridx = 0;
	amouth1Label.gridy = 3;
	amouth1Label.weighty = weightye;
	amouth1Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Amount:"), amouth1Label);

	GridBagConstraints amouth1Field = new GridBagConstraints();
	amount1 = new JTextField(textFieldColunm);
	amouth1Field.gridx = 1;
	amouth1Field.gridy = 3;
	amouth1Field.ipady = 2;
	amouth1Field.ipadx = 115;
	amouth1Field.anchor =  GridBagConstraints.WEST;
	addpanel.add(amount1, amouth1Field);
	amount1.setText(String.valueOf(codePosted.get((String)keyObjPosted[1])));
	
	GridBagConstraints group2Label = new GridBagConstraints();
	group2Label.gridx = 0;
	group2Label.gridy = 4;
	group2Label.ipadx = 60;
	group2Label.weighty = weightye;
	group2Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Posting2:", JLabel.RIGHT), group2Label);

	GridBagConstraints group2TextField = new GridBagConstraints();
	group2 = new Choice();
	group2.add("Select");
	for(int i = 0; i < keyObj.length; i++){
		group2.add(codeName.get((Integer)keyObj[i]));
	}
	if(keyObjPosted.length > 2){
		String strCode2 = (String)keyObjPosted[2]; 
		String strCodeName2[] = strCode2.split("\\.");
		group2.select(strCodeName2[1]);
	}

	group2TextField.gridx = 1;
	group2TextField.gridy = 4;
	group2TextField.ipady = 2;
	group2TextField.ipadx = 23;
	group2TextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(group2, group2TextField);
	
	GridBagConstraints amouth2Label = new GridBagConstraints();
	amouth2Label.gridx = 0;
	amouth2Label.gridy = 5;
	amouth2Label.weighty = weightye;
	amouth2Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Amount:"), amouth2Label);

	GridBagConstraints amouth2Field = new GridBagConstraints();
	amount2 = new JTextField(textFieldColunm);
	amouth2Field.gridx = 1;
	amouth2Field.gridy = 5;
	amouth2Field.ipady = 2;
	amouth2Field.ipadx = 115;
	amouth2Field.anchor =  GridBagConstraints.WEST;
	addpanel.add(amount2, amouth2Field);
	if(keyObjPosted.length > 2){
		amount2.setText(String.valueOf(codePosted.get((String)keyObjPosted[2])));
	}
	else{
		amount2.setEditable(false);
	}
	
	GridBagConstraints numberLabel = new GridBagConstraints();
	numberLabel.gridx = 0;
	numberLabel.gridy = 6;
	numberLabel.weighty = weightye;
	numberLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Additional Info:"), numberLabel);

	GridBagConstraints infoTextField = new GridBagConstraints();
    info = new JTextField(textFieldColunm);
	infoTextField.gridx = 1;
	infoTextField.gridy = 6;
	infoTextField.ipady = 2;
	infoTextField.ipadx = 115;
	infoTextField.insets = new Insets(0, 0, 0, 60);
	infoTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(info, infoTextField);
	info.setText(String.valueOf(editInfo.get(2)));

	//BUTTON BOTTOM
	GridBagConstraints okButton = new GridBagConstraints();
	ok = new JButton("OK");
	okButton.gridx = 2;
	okButton.gridy = 0;
	buttonpanel.add(ok, okButton);
	
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
	group1.addItemListener(this);
	group2.addItemListener(this); 
	ok.addActionListener(this); 
	changeName.addActionListener(this);
    }
  

   public class GeneralPostings extends JFrame implements ActionListener{
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
	private int countRow = 0;
    
    public GeneralPostings(String title){
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
	entryDataModel = new DefaultTableModel(columns, initialRow); 
	entryView = new JTable(entryDataModel);  
		
	int[]widthValue = {60, 100, 60};
	for(int i = 0; i<widthValue.length; i++){
		TableColumn col  = entryView.getColumnModel().getColumn(i);
		col.setPreferredWidth(widthValue[i]);
	}

	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	entryViewScroll = new JScrollPane(entryView, v,h); 
	tablepanel.add(entryViewScroll); 

	//BUTTON BOTTOM
	GridBagConstraints groupSelection = new GridBagConstraints();
	group = new Choice();
	group.add("Debit");
	group.add("Credit");
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
	refresh.addActionListener(this); 	
	go.addActionListener(this); 
	find.addActionListener(this); 
	 
	pu.loadData(entryDataModel, bean.connect().getManualPostingCodes());
    }

   public void actionPerformed( ActionEvent e) {  
		
		if( e.getSource().equals(go)){
				item = group.getSelectedItem();
				principleCode =  ul.getTableInt(entryView, entryDataModel, 0);
				name.setText(String.format("%s %s", item, ul.getStringValue(entryView, entryDataModel, 1)));
				dispose();	
			}
		
		if(e.getSource().equals(find)){

			if(generalsView){
				ul.findRecord(entryDataModel, searchField, 1);
			}
			else{
				ul.findRecord(entryDataModel, searchField, 1);
			}
		}
		
		if (e.getSource().equals(refresh)){
			dispose();
			new GeneralPostings("Manual Posting Codes");
		}

	}

 }

  public void itemStateChanged(ItemEvent e){
	if(e.getSource().equals(group1)){
		if(!group1.getSelectedItem().equals(group2.getSelectedItem())){
			amount1.setEditable(true);
		}
	
		else{
			amount1.setEditable(false);
		}
	}
	if(e.getSource().equals(group2)){
		if(!group2.getSelectedItem().equals(group1.getSelectedItem()) && !amount1.getText().equals("")){
			amount2.setEditable(true);
		}
		else if(group2.getSelectedItem().equals("Select")){
				amount2.setEditable(false);
		}
		else{
			amount2.setEditable(false);
		}
	}
  
  }
   public void actionPerformed( ActionEvent e) {  
   
   	if (e.getSource().equals(ok)){
		ArrayList<Object> newPosting = new ArrayList<Object>();
		ArrayList<String> amount = new ArrayList<String>();
		ArrayList<String> code = new ArrayList<String>();

		if(!amount1.getText().equals("")){
			amount.add(amount1.getText());
		}
		else{
			amount.add("0");
		}
		if(!amount2.getText().equals("")){
			amount.add(amount2.getText());
		}
		else{
			amount.add("0");
		}
		newPosting.add(amount);
		
		code.add(String.valueOf(principleCode));
		if(group1.getSelectedIndex() != -1 && !group1.getSelectedItem().equals("Select")){
			code.add(String.valueOf((Integer)keyObj[group1.getSelectedIndex()-1]));
		}

		if(group2.getSelectedIndex() != -1 && !group2.getSelectedItem().equals("Select")){
			code.add(String.valueOf((Integer)keyObj[group2.getSelectedIndex()-1]));
		}

		newPosting.add(code);
		newPosting.add(ul.usDateString(sh.dateString(date)));
		newPosting.add(info.getText());
		newPosting.add(item);
		newPosting.add(Integer.valueOf(manualPostingId));
		bean.connect().editManualPosting(ul.writeArrayList(newPosting)); 
	    dispose();	
	}
	if(e.getSource().equals(changeName)){
		new GeneralPostings("Manual Posting Codes");
	}
	if (e.getSource().equals(exit)){
	    dispose();	
	}
	}

}