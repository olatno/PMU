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
import scr.protool.client.miscelleneous.BeanStud;


/**

*
*

*
* @author <a href="mailto:"></a>
* @version 1.0
*/
public class AddDoubleEntry extends JFrame implements ActionListener, ItemListener{//implements ActionListener{
  
    private JTextField name;
	private JTextField info;
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
	private   SpinnerHelper sh;
    private String client = "";
    private BeanStud bean;
	private final int textFieldColunm = 1;
	private Utilities ul = new Utilities();
	private final double weightye = 0.5;
	private LinkedHashMap<Integer,String> codeName;
	private Object[] keyObj;
	private String item;
    Container con = getContentPane();
    
    public AddDoubleEntry(String title, LinkedHashMap<Integer,String> codeName, String item){//, int proId
   	super(title); 

	this.codeName = codeName;
	this.item = item;
	addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();
	sh = new SpinnerHelper();
	keyObj = codeName.keySet().toArray();
	bean = new BeanStud();
	//Panels
	addpanel.setLayout(new GridBagLayout());
	buttonpanel.setLayout(new GridBagLayout());
	generalPanel.setLayout(new BorderLayout());

	//Textfields u
	GridBagConstraints nameLabel = new GridBagConstraints();
	nameLabel.gridx = 0;
	nameLabel.gridy = 0;
	nameLabel.anchor =  GridBagConstraints.EAST;
   	nameLabel.weighty = weightye;
	addpanel.add(new JLabel("Accounts Name:"), nameLabel);

	GridBagConstraints nameTextField = new GridBagConstraints();
	name = new JTextField(textFieldColunm);
	nameTextField.gridx = 1;
	nameTextField.gridy = 0;
	nameTextField.ipady = 2;
	nameTextField.ipadx = 110;
	nameTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(name, nameTextField);
	name.setEditable(false);
	name.setText(String.format("%s %s", item, codeName.get((Integer)keyObj[0])));
	
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
	addpanel.add(sh.getGenericDate(date), date1TextField);
	
	GridBagConstraints group1Label = new GridBagConstraints();
	group1Label.gridx = 0;
	group1Label.gridy = 2;
	group1Label.ipadx = 60;
	group1Label.weighty = weightye;
	group1Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Posting1:", JLabel.RIGHT), group1Label);

	GridBagConstraints group1TextField = new GridBagConstraints();
	group1 = new Choice();
	group1.add("Select");
	for(int i = 1; i < keyObj.length; i++){
		group1.add(codeName.get((Integer)keyObj[i]));
	}
	group1TextField.gridx = 1;
	group1TextField.gridy = 2;
	//groupTextField.weightx = weightye;
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
	amount1.setEditable(false);
	
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
	for(int i = 1; i < keyObj.length; i++){
		group2.add(codeName.get((Integer)keyObj[i]));
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
	amount2.setEditable(false);

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
	
		else{
			amount2.setEditable(false);
		}
	}
  
  }
   public void actionPerformed( ActionEvent e) {  
   
   	if (e.getSource().equals(ok)){
		ArrayList<Object> posting = new ArrayList<Object>();
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
		posting.add(amount);
		
		code.add(String.valueOf((Integer)keyObj[0]));
		if(group1.getSelectedIndex() != -1 && !group1.getSelectedItem().equals("Select")){
			code.add(String.valueOf((Integer)keyObj[group1.getSelectedIndex()]));
		}

		if(group2.getSelectedIndex() != -1 ){
			code.add(String.valueOf((Integer)keyObj[group2.getSelectedIndex()]));
		}

		posting.add(code);
		posting.add(ul.usDateString(sh.dateString(date)));
		posting.add(info.getText());
		posting.add(item);
		bean.connect().addManualPosting(ul.writeArrayList(posting)); 
	    dispose();	
	}

	if (e.getSource().equals(exit)){
	    dispose();	
	}
	}

}