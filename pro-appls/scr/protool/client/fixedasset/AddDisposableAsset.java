
package scr.protool.client.fixedasset;
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
public class AddDisposableAsset extends JFrame implements ActionListener, ItemListener{//implements ActionListener{
    
    private JTextField assetId;
    private Choice group;
    private JTextField description;
    private JSpinner date;
	private JTextField amount;
	private Choice vat;
    private JButton ok;
    private JButton exit;
    private JPanel addpanel;
    private JPanel buttonpanel;
    private JPanel generalPanel;
    private Context ctx;
	private SpinnerHelper sh;
	private final int textfieldrow = 1;
	boolean activateAccountcode;
   	private PaymentInUtilities pu ;
	private BeanStud bean;
	private AssetProcessedDetails details;
	private Utilities ul;
	private Hashtable<String, Integer> accountname;
    Container con = getContentPane();
    
    public AddDisposableAsset(String title, AssetProcessedDetails details){//, int proId
   	super(title); 
	
	this.details = details;
	bean = new BeanStud();
	pu = new PaymentInUtilities();
	ul = new Utilities();
	addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();
	sh = new SpinnerHelper();

	addpanel.setLayout(new GridBagLayout());
	buttonpanel.setLayout(new GridBagLayout());
	generalPanel.setLayout(new BorderLayout());

	//Textfields used
	GridBagConstraints idLabel = new GridBagConstraints();
	idLabel.gridx = 0;
	idLabel.gridy = 0;
	idLabel.anchor =  GridBagConstraints.EAST;
    idLabel.weighty = 0.1;
	addpanel.add(new JLabel("Asset Id:"), idLabel);

	GridBagConstraints idTextField = new GridBagConstraints();
	assetId = new JTextField(textfieldrow);
	idTextField.gridx = 1;
	idTextField.gridy = 0;
	idTextField.ipady = 2;
	idTextField.ipadx = 110;
	idTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(assetId, idTextField);
	String strId_strDesc = (String)details.getTableModelName().getValueAt(0, 4);
	String strassetId[] = strId_strDesc.split("\\.");
	assetId.setText(strassetId[0]);
	assetId.setEditable(false);
	
	GridBagConstraints descLabel = new GridBagConstraints();
	descLabel.gridx = 0;
	descLabel.gridy = 1;
	descLabel.anchor =  GridBagConstraints.EAST;
    descLabel.weighty = 0.1;
	addpanel.add(new JLabel("Description:"), descLabel);

	GridBagConstraints descTextField = new GridBagConstraints();
	description = new JTextField(textfieldrow);
	descTextField.gridx = 1;
	descTextField.gridy = 1;
	descTextField.ipady = 2;
	descTextField.ipadx = 110;
	descTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(description, descTextField);
	description.setText(strassetId[1]);
	description.setEditable(false);
	
	GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 2;
	date1Label.weighty = 0.1;
    date1Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Disposal Date:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	date = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 2;
	date1TextField.ipady = 2;
	date1TextField.ipadx = -13;
	date1TextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(sh.getGenericDate(date), date1TextField);


	GridBagConstraints titleLabel = new GridBagConstraints();
	titleLabel.gridx = 0;
	titleLabel.gridy = 3;
    titleLabel.weighty = 0.1;
	titleLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Dis Type:"), titleLabel);

	GridBagConstraints choiceField = new GridBagConstraints();
    group = new Choice();
	group.add("Cash");
	group.add("Scrap");
	choiceField.gridx = 1;
	choiceField.gridy = 3;
	choiceField.ipadx = 55;
	choiceField.anchor =  GridBagConstraints.WEST;
	addpanel.add(group, choiceField);
	
	GridBagConstraints amountLabel = new GridBagConstraints();
    amountLabel.gridx = 0;
	amountLabel.gridy = 4;
	amountLabel.weighty = 0.1;
	amountLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Amount(Inc VAT):"), amountLabel);

	GridBagConstraints amountTextField = new GridBagConstraints();
	amount = new JTextField(textfieldrow);
	amountTextField.ipady = 2;
	amountTextField.ipadx = 110;
	amountTextField.gridx = 1;
	amountTextField.gridy = 4;
	amountTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(amount, amountTextField);

	GridBagConstraints vatLabel = new GridBagConstraints();
	vatLabel.gridx = 0;
	vatLabel.gridy = 5;
	//vatLabel.ipadx = 60;
	vatLabel.weighty = 0.1;
	vatLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("VAT:", JLabel.RIGHT), vatLabel);

	GridBagConstraints vatTextField = new GridBagConstraints();
	vat = new Choice();
	vat.add("None");
	vat.add("0%");
	vat.add("17.5%");
	vat.add("20%");
	vatTextField.gridx = 1;
	vatTextField.gridy = 5;
	vatTextField.ipady = 2;
	vatTextField.ipadx = 55;
	vatTextField.insets = new Insets(0, 0, 0, 60);
	vatTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(vat, vatTextField);
		

	//BUTTON BOTTOM
	GridBagConstraints okButton = new GridBagConstraints();
	ok = new JButton("OK");
	okButton.gridx = 2;
	okButton.gridy = 0;
	okButton.insets = new Insets(10, 0, 0, 0);
	buttonpanel.add(ok, okButton);
	
	GridBagConstraints exitButton = new GridBagConstraints();
	exit = new JButton("Exit");
	exitButton.gridx = 3;
	exitButton.gridy = 0;
	exitButton.insets = new Insets(10, 20, 0, 0);
	buttonpanel.add(exit, exitButton);

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
	ok.addActionListener(this); 
    }
	
	
	public void itemStateChanged(ItemEvent e) {
	
		if(group.getSelectedItem().equals("Scrap")){
			amount.setText("0");
		}
		else{
			amount.setText("");
		}
	}
	
	
   public void actionPerformed( ActionEvent e) {  

	if (e.getSource().equals(exit)){
	    dispose();	
	}
 
	if (e.getSource().equals(ok)){
		ArrayList<String> accdetails =  new ArrayList<String>();
		accdetails.add(assetId.getText());
		accdetails.add(ul.usDateString(sh.dateString(date)));
		accdetails.add(group.getSelectedItem());
		accdetails.add(amount.getText());
		accdetails.add(vat.getSelectedItem());
	    bean.connect().addAssetDisposal(ul.writeArrayListStr(accdetails)); 
		dispose();		
	   }
    }

}