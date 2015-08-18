
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
import scr.protool.client.utilities.*;
//import javax.ejb.EJB;

/**

*
*

*
* @author <a href="mailto:"></a>
* @version 1.0
*/
public class AddCodeDetails extends JFrame implements ActionListener, ItemListener{//implements ActionListener{
    // private ProtoolClientBean clientbean;
    private JTextField costId;
    private Choice group;
    private JTextField name;
    private JSpinner date;
	private JTextField code;
	private JCheckBox active;
    private JCheckBox manual;
    private JButton save;
    private JButton ok;
    private JButton exit;
    private JPanel addpanel;
    private JPanel buttonpanel;
    private JPanel generalPanel;
    private Context ctx;
	private   SpinnerHelper sh;
	private final int textfieldrow = 1;
	boolean activateAccountcode;
   	private PaymentInUtilities pu = new PaymentInUtilities();
	private Object [] groupbyname = null;
	private Object [] groupbyid = null;
	private BeanStud bean;
	private Utilities ul = new Utilities();
	private AccountsUtilities ula = new AccountsUtilities();
	private Hashtable<String, Integer> accountname;
    Container con = getContentPane();
    
    public AddCodeDetails(String title){//, int proId
   	super(title); 
	
	bean = new BeanStud();
	groupbyname = ula.getGroupByName(bean.connect().getGroupName());
	groupbyid = ula.getGroupById(bean.connect().getGroupId());

	addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();
	sh = new SpinnerHelper();
	//ula = new AccountsUtilities();
	//Panels
	addpanel.setLayout(new GridBagLayout());
	buttonpanel.setLayout(new GridLayout(1,7));
	generalPanel.setLayout(new BorderLayout());

	//Textfields use for search
	GridBagConstraints idLabel = new GridBagConstraints();
	//   	idLabel.insets = new Insets(20, 0, 0, 0);
	idLabel.gridx = 0;
	idLabel.gridy = 0;
	idLabel.anchor =  GridBagConstraints.EAST;
       	idLabel.weighty = 0.1;
	//addpanel.add(new JLabel("Accounts Id:"), idLabel);

	GridBagConstraints idTextField = new GridBagConstraints();
	costId = new JTextField(textfieldrow);
	idTextField.gridx = 1;
	idTextField.gridy = 0;
	idTextField.ipady = 2;
	idTextField.ipadx = 110;
	idTextField.anchor =  GridBagConstraints.WEST;
	//addpanel.add(costId, idTextField);
	costId.setEditable(false);
	//costId.setText(String.valueOf(defaultId));

	GridBagConstraints titleLabel = new GridBagConstraints();
	titleLabel.gridx = 0;
	titleLabel.gridy = 1;
    titleLabel.weighty = 0.1;
	titleLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Group:"), titleLabel);

	GridBagConstraints choiceField = new GridBagConstraints();
    group = new Choice();
	group.add("SELECT GROUP");
//	for(int i = 0; i < groupbyname.length; i++){
//		group.add((String)groupbyname[i]);
	//}
	accountname = pu.getAccountCodeName(bean.connect().getHashTableAccountsGroup());
	Enumeration<String> groupname = accountname.keys();
   // group = new Choice();
	while(groupname.hasMoreElements()){
		group.add(groupname.nextElement());
	}
	choiceField.gridx = 1;
	choiceField.gridy = 1;
	choiceField.ipady = 2;
	//choiceField.ipadx = 90;
	choiceField.anchor =  GridBagConstraints.WEST;
	addpanel.add(group, choiceField);
	
	GridBagConstraints codeLabel = new GridBagConstraints();
    codeLabel.gridx = 0;
	codeLabel.gridy = 2;
	codeLabel.weighty = 0.1;
	//	addressLabel.insets = new Insets(0, 0, 15, 0);
	codeLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Accounts Code:"), codeLabel);

	GridBagConstraints codeTextField = new GridBagConstraints();
	code = new JTextField(textfieldrow);
	codeTextField.ipady = 2;
	codeTextField.ipadx = 110;
	codeTextField.gridx = 1;
	codeTextField.gridy = 2;
	codeTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(code, codeTextField);
	code.setEditable(false);

	GridBagConstraints nameLabel = new GridBagConstraints();
    nameLabel.gridx = 0;
	nameLabel.gridy = 3;
	nameLabel.weighty = 0.1;
	//	addressLabel.insets = new Insets(0, 0, 15, 0);
	nameLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Accounts Name:"), nameLabel);

	GridBagConstraints receiptTextField = new GridBagConstraints();
	name = new JTextField(textfieldrow);
	receiptTextField.ipady = 2;
	receiptTextField.ipadx = 190;
	receiptTextField.gridx = 1;
	receiptTextField.gridy = 3;
	receiptTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(name, receiptTextField);
	//name.setText(String.valueOf(groupbyname.length));
	GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 4;
	date1Label.weighty = 0.1;
	//	date1Label.insets = new Insets(0, 0, 15, 0);
    date1Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Date:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	date = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 4;
	date1TextField.ipady = 2;
	date1TextField.ipadx = -13;
	date1TextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(sh.getGenericDate(date), date1TextField);
	
	GridBagConstraints diffLabel = new GridBagConstraints();
	diffLabel.gridx = 0;
	diffLabel.gridy = 5;
	diffLabel.weighty = 0.1;
	//diffLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Active:"), diffLabel);

	GridBagConstraints incField = new GridBagConstraints();
	active = new JCheckBox("",true);
	incField.gridx = 1;
	incField.gridy = 5;
	incField.ipady = 2;
	incField.anchor =  GridBagConstraints.WEST;
	addpanel.add(active, incField); 
	
	GridBagConstraints manualLabel = new GridBagConstraints();
	manualLabel.gridx = 0;
	manualLabel.gridy = 6;
	manualLabel.weighty = 0.1;
	//diffLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Manual Posting:"), manualLabel);

	GridBagConstraints manualField = new GridBagConstraints();
	manual = new JCheckBox("",false);
	manualField.gridx = 1;
	manualField.gridy = 6;
	manualField.ipady = 2;
	manualField.anchor =  GridBagConstraints.WEST;
	addpanel.add(manual, manualField); 

	//BUTTON BOTTOM
	buttonpanel.add(new JLabel(""));

	save = new JButton("SAVE");
	buttonpanel.add(save);

	//	buttonpanel.add(new JLabel(""));

	ok = new JButton("OK");
	buttonpanel.add(ok);

	//	buttonpanel.add(new JLabel(""));
	
	exit = new JButton("EXIT");
	buttonpanel.add(exit);

	buttonpanel.add(new JLabel(""));
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
	group.addItemListener (this);
	
	save.addActionListener(this); 
	ok.addActionListener(this); 
	active.addActionListener(this);


    }
	
	public Integer initialAccountsCode(String classification){//String classification
		int initialValue = 0;
		for(int i = 0; i < groupbyname.length; i++){
			if(classification.equals((String)groupbyname[i])){
				return new Integer(((Integer)groupbyid[i]).intValue());
			}
		}
		return null;		
	}


	
	
	public void itemStateChanged(ItemEvent e) {
	
		String initialstr = group.getSelectedItem();
		if(initialstr.equals("SELECT GROUP")){
			code.setText(String.valueOf(""));
		}
		//int groupid = initialAccountsCode(initialstr).intValue();accountname
		int groupid = accountname.get(initialstr).intValue();
			if(ula.getAccountsId(bean.connect().geneAccountsCode(groupid)) == null){
				//code.setText(String.valueOf(initialAccountsCode(initialstr).intValue()+10));
				code.setText(String.valueOf(groupid+10));
			}
			else{
				Integer nextValue = ula.getAccountsId(bean.connect().geneAccountsCode(groupid));
				code.setText(String.valueOf(nextValue.intValue()+10));
			}
		
		
	}
	
	
   public void actionPerformed( ActionEvent e) {  

	if (e.getSource().equals(exit)){
	    dispose();	
		}
		
	if (e.getSource().equals(active)){
	    activateAccountcode = active.isSelected();	
		}
	if (e.getSource().equals(save)){
		ArrayList<String> accdetails =  new ArrayList<String>();
		accdetails.add(String.valueOf(initialAccountsCode(group.getSelectedItem())));
		accdetails.add(code.getText());
		accdetails.add(name.getText());
		accdetails.add(ul.usDateString(sh.dateString(date)));
		if(active.isSelected()){
			accdetails.add("YES");
		}
		else{
			accdetails.add("NO");
		}
		if(manual.isSelected()){
			accdetails.add("true");
		}
		else{
			accdetails.add("false"); 
		}
	    bean.connect().addAccountsCode(ul.writeArrayListStr(accdetails)); 
		dispose();
		new AddCodeDetails("NEW ACCOUNTS CODE");	
		
	}
 
	if (e.getSource().equals(ok)){
		ArrayList<String> accdetails =  new ArrayList<String>();
		accdetails.add(String.valueOf(initialAccountsCode(group.getSelectedItem())));
		accdetails.add(code.getText());
		accdetails.add(name.getText());
		accdetails.add(ul.usDateString(sh.dateString(date)));
		if(active.isSelected()){
			accdetails.add("YES");
		}
		else{
			accdetails.add("NO");
		}
		if(manual.isSelected()){
			accdetails.add("true");
		}
		else{
			accdetails.add("false"); 
		}
	    bean.connect().addAccountsCode(ul.writeArrayListStr(accdetails)); 
		dispose();		
	   }
    }


}