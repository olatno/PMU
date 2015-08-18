
package scr.protool.client.supplier;
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
public class MaterialPayment extends JFrame implements ActionListener{//implements ActionListener{
    private JTextField supplierId;
    private JTextField invoiceId;
	private JComboBox bank;
    private JTextField note;
    private JTextField invoiceAmount;
    private JSpinner date;
    private JTextField description;
	private JTextField type;
    private JButton ok;
    private JButton exit;
	private JButton save;
    private JPanel addpanel;
    private JPanel buttonpanel;
    private JPanel generalPanel;
    private Context ctx;
	private SpinnerHelper sh;
	private final int textFieldColunm = 1;
    private BeanStud bean;
	private Utilities ul = new Utilities();
	private PaymentInUtilities pu =  new PaymentInUtilities();
	private final double weightye = 0.5;
    Container con = getContentPane();
	private int invoiceIds;
    private LinkedHashMap<String, Integer>  accname = null;
    
    public MaterialPayment(String title, String supplier, String invoice, int invoiceIds){
   	super(title); 

	bean = new BeanStud();
	this.invoiceIds = invoiceIds;
	addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();
	sh = new SpinnerHelper();
	//Panels
	addpanel.setLayout(new GridBagLayout());
	buttonpanel.setLayout(new GridBagLayout());
	generalPanel.setLayout(new BorderLayout());

	//Textfields 
	GridBagConstraints idLabel = new GridBagConstraints();
	idLabel.gridx = 0;
	idLabel.gridy = 0;
	idLabel.anchor =  GridBagConstraints.EAST;
   	idLabel.weighty = weightye;
	addpanel.add(new JLabel("Supplier Name:"), idLabel);

	GridBagConstraints idTextField = new GridBagConstraints();
	supplierId = new JTextField(textFieldColunm);
	idTextField.gridx = 1;
	idTextField.gridy = 0;
	idTextField.ipady = 2;
	idTextField.ipadx = 110;
	idTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(supplierId, idTextField);
	supplierId.setEditable(false);
	supplierId.setText(supplier);

	GridBagConstraints invoiceLabel = new GridBagConstraints();
	invoiceLabel.gridx = 0;
	invoiceLabel.gridy = 1;
	invoiceLabel.weighty =  weightye;
	invoiceLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Invoice No:"), invoiceLabel);

	GridBagConstraints invoiceField = new GridBagConstraints();
	invoiceId = new JTextField(textFieldColunm);
	invoiceField.gridx = 1;
	invoiceField.gridy = 1;
	invoiceField.ipady = 2;
	invoiceField.ipadx = 110;
	invoiceField.anchor =  GridBagConstraints.WEST;
	addpanel.add(invoiceId, invoiceField);
	invoiceId.setEditable(false);
	invoiceId.setText(invoice);

	GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 2;
	date1Label.weighty = weightye;
    date1Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Payment Date:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	date = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 2;
	date1TextField.ipady = 2;
	date1TextField.ipadx = -10;
	date1TextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(sh.getGenericDate(date), date1TextField);
	
	GridBagConstraints amountLabel = new GridBagConstraints();
	amountLabel.gridx = 0;
	amountLabel.gridy = 3;
	amountLabel.weighty = weightye;
	amountLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Invoice Amount:"), amountLabel);

	GridBagConstraints amountTextField = new GridBagConstraints();
	invoiceAmount = new JTextField(textFieldColunm);
	amountTextField.gridx = 1;
	amountTextField.gridy = 3;
	amountTextField.ipady = 2;
	amountTextField.ipadx = 115;
	amountTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(invoiceAmount, amountTextField);

	
	GridBagConstraints bankLabel = new GridBagConstraints();
	bankLabel.gridx = 0;
	bankLabel.gridy = 4;
	bankLabel.ipadx = 60;
	bankLabel.weighty = weightye;
	bankLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Posted To:", JLabel.RIGHT), bankLabel);

	GridBagConstraints bankTextField = new GridBagConstraints();
	/*bank = new Choice();
	bank.add("Credit Note");
    accname = pu.getAccountCodeName(bean.connect().HashTableMaterialPosting());
	for(Enumeration<String> enu = accname.keys(); enu.hasMoreElements();){
		bank.add(enu.nextElement());
	}*/
	accname = pu.genericHashMap(bean.connect().getHashMapPaymentOutType());
	Set<String> keys = accname.keySet();
	bank = new JComboBox(keys.toArray());
	bank.addItem("Credit Note");
	bankTextField.gridx = 1;
	bankTextField.gridy = 4;
	bankTextField.ipady = 2;
	bankTextField.ipadx = 50;
	bankTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(bank, bankTextField);
	
	GridBagConstraints noteLabel = new GridBagConstraints();
    noteLabel.gridx = 0;
	noteLabel.gridy = 5;
	noteLabel.weighty = weightye;
	noteLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Additional Info:"), noteLabel);

	GridBagConstraints noteTextField = new GridBagConstraints();
	note = new JTextField(textFieldColunm);
	noteTextField.ipady = 2;
	noteTextField.ipadx = 190;
	noteTextField.gridx = 1;
	noteTextField.gridy = 5;
	noteTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(note, noteTextField);

	//BUTTON BOTTOM	
	GridBagConstraints okbutton = new GridBagConstraints();
	ok = new JButton("OK");
	okbutton.gridx = 1;
	okbutton.gridy = 0;
	okbutton.insets = new Insets(0, 0, 0, 20);
    buttonpanel.add(ok, okbutton);
	
	GridBagConstraints exitquote = new GridBagConstraints();
	exit = new JButton("EXIT");
	exitquote.gridx = 2;
	exitquote.gridy = 0;
	buttonpanel.add(exit, exitquote);
	
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
	ok.addActionListener(this); 
    }

   public void actionPerformed( ActionEvent e) {  

	if (e.getSource().equals(exit)){
	    dispose();	
	 }
	

	 if (e.getSource().equals(ok)){
		ArrayList<String> prodetails =  new ArrayList<String>();
		prodetails.add(String.valueOf(invoiceIds));
		prodetails.add(invoiceAmount.getText());
		if(((String)bank.getSelectedItem()).equals("Credit Note")){
			prodetails.add(String.valueOf(20000));
		}
		else{
			prodetails.add(String.valueOf(accname.get((String)bank.getSelectedItem())));
		}
		prodetails.add((String)bank.getSelectedItem());
		prodetails.add(note.getText());
		prodetails.add(ul.usDateString(sh.dateString(date)));
	    bean.connect().addMaterialPayment(ul.writeArrayListStr(prodetails)); 
		dispose();

	  }

    }

}