
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
public class SubContractorPayment extends JFrame implements ActionListener{//implements ActionListener{
    private JTextField subContractor;
    private JTextField invoiceId;
	private JComboBox bank;
	private Choice cis;
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
	private   SpinnerHelper sh;
	private final int textFieldColunm = 1;
    private BeanStud bean;
	private Utilities ul = new Utilities();
	private PaymentInUtilities pu =  new PaymentInUtilities();
	private final double weightye = 0.5;
    Container con = getContentPane();
	private int invoiceIds;
	private LinkedHashMap<String, Integer>  accname = null;
	private Outgoingview view;
    
    public SubContractorPayment(String title, Outgoingview view){
   	super(title); 
	
	bean = new BeanStud();
	this.view = view;
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
	addpanel.add(new JLabel("Sub-contractor Name:"), idLabel);

	GridBagConstraints idTextField = new GridBagConstraints();
	subContractor = new JTextField(textFieldColunm);
	idTextField.gridx = 1;
	idTextField.gridy = 0;
	idTextField.ipady = 2;
	idTextField.ipadx = 110;
	idTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(subContractor, idTextField);
	subContractor.setEditable(false);
	subContractor.setText(view.getStringValue(0, view.getModel(), view.getTable()));

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
	invoiceId.setText(view.getStringValue(2, view.getModel(), view.getTable()));

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
	addpanel.add(new JLabel("Amount:"), amountLabel);

	String amounts = view.getStringValue(4, view.getModel(), view.getTable());
	String payments = view.getStringValue(5, view.getModel(), view.getTable());
	BigDecimal remaining = new BigDecimal(ul.noPoundString(amounts)).subtract(new BigDecimal(ul.noPoundString(payments)));
	GridBagConstraints amountTextField = new GridBagConstraints();
	invoiceAmount = new JTextField(textFieldColunm);
	amountTextField.gridx = 1;
	amountTextField.gridy = 3;
	amountTextField.ipady = 2;
	amountTextField.ipadx = 115;
	amountTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(invoiceAmount, amountTextField);
	invoiceAmount.setText(String.valueOf(remaining));
	
	GridBagConstraints bankLabel = new GridBagConstraints();
	bankLabel.gridx = 0;
	bankLabel.gridy = 4;
	bankLabel.ipadx = 60;
	bankLabel.weighty = weightye;
	bankLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Posted To:", JLabel.RIGHT), bankLabel);

	GridBagConstraints bankTextField = new GridBagConstraints();

	accname = pu.genericHashMap(bean.connect().getHashMapPaymentOutType());
	Set<String> keys = accname.keySet();
	bank = new JComboBox(keys.toArray());
	//bank.addItem("Credit Note");
	bank.removeItem("Credit Card");
	bankTextField.gridx = 1;
	bankTextField.gridy = 4;
	bankTextField.ipady = 2;
	bankTextField.ipadx = 50;
	bankTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(bank, bankTextField);
	
	GridBagConstraints cisLabel = new GridBagConstraints();
	cisLabel.gridx = 0;
	cisLabel.gridy = 5;
	cisLabel.ipadx = 60;
	cisLabel.weighty = weightye;
	cisLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("CIS:", JLabel.RIGHT), cisLabel);

	GridBagConstraints cisTextField = new GridBagConstraints();
	cis = new Choice();
	cis.add("0%");
	cis.add("18%");
	cis.add("20%");
	cisTextField.gridx = 1;
	cisTextField.gridy = 5;
	cisTextField.ipady = 2;
	cisTextField.ipadx = 35;
	cisTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(cis, cisTextField);
	
	GridBagConstraints noteLabel = new GridBagConstraints();
    noteLabel.gridx = 0;
	noteLabel.gridy = 6;
	noteLabel.weighty = weightye;
	noteLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Additional Info:"), noteLabel);

	GridBagConstraints noteTextField = new GridBagConstraints();
	note = new JTextField(textFieldColunm);
	noteTextField.ipady = 2;
	noteTextField.ipadx = 190;
	noteTextField.gridx = 1;
	noteTextField.gridy = 6;
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
		prodetails.add(String.valueOf(view.getTableId(7, view.getModel(), view.getTable())));
		prodetails.add(ul.usDateString(sh.dateString(date)));
		prodetails.add(invoiceAmount.getText());
		prodetails.add(cis.getSelectedItem());
		prodetails.add(note.getText());
		prodetails.add(String.valueOf(accname.get((String)bank.getSelectedItem())));
	    bean.connect().addSubContractorPayment(ul.writeArrayListStr(prodetails)); 
		dispose();

	  }

    }

}