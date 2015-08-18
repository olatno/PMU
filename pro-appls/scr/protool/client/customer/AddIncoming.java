
package scr.protool.client.customer;
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
public class AddIncoming extends JFrame implements ActionListener, FocusListener {//implements ActionListener{
   
    //private JTextField incomingId;
    private JTextField paymentId;
	private JTextField cis;
    private JTextField projectId;
    private JTextField note;
    private JTextField incomingAmount;
    private JSpinner date;
    private JTextField description;
	private JComboBox type;
	//private JRadioButton exclude; 
    private JButton save;
    private JButton ok;
    private JButton exit;
    private JPanel addpanel;
    private JPanel buttonpanel;
    private JPanel generalPanel;
	private SpinnerHelper sh;
    private BeanStud bean;
	private Utilities ul = new Utilities();
	private PaymentInUtilities pu = new PaymentInUtilities();
	private final double weightye = 0.5;
	private final int textFieldColunm = 1;
    Container con = getContentPane();
	private BigDecimal balance ;
	private LinkedHashMap<String, Integer> paymentType;
    
    public AddIncoming(String title, int paymentNum){//, int proId
   	super(title); 
	
	bean = new BeanStud();
	balance = pu.getTotalQuote(bean.connect().getPaymentBalance(paymentNum));
	paymentType = pu.genericHashMap(bean.connect().getHashMapPaymentType());
	addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();
	sh = new SpinnerHelper();
	//Panels
	addpanel.setLayout(new GridBagLayout());
	buttonpanel.setLayout(new GridBagLayout());
	generalPanel.setLayout(new BorderLayout());

	//Textfields 
	GridBagConstraints paymentinLabel = new GridBagConstraints();
	paymentinLabel.gridx = 0;
	paymentinLabel.gridy = 0;
	paymentinLabel.weighty =  weightye;
	paymentinLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Payment Id:"), paymentinLabel);

	GridBagConstraints paymentidField = new GridBagConstraints();
	paymentId = new JTextField(textFieldColunm);
	paymentidField.gridx = 1;
	paymentidField.gridy = 0;
	paymentidField.ipady = 2;
	paymentidField.ipadx = 110;
	paymentidField.anchor =  GridBagConstraints.WEST;
	addpanel.add(paymentId, paymentidField);
	paymentId.setEditable(false);
	paymentId.setText(String.valueOf(paymentNum));

	GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 1;
	date1Label.weighty = weightye;
    date1Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Payment Date:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	date = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 1;
	date1TextField.ipady = 2;
	date1TextField.ipadx = 5;
	date1TextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(sh.getGenericDate(date), date1TextField);
	
	GridBagConstraints amountLabel = new GridBagConstraints();
	amountLabel.gridx = 0;
	amountLabel.gridy = 2;
	amountLabel.weighty = weightye;
	amountLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Incoming Amount:"), amountLabel);

	GridBagConstraints amountTextField = new GridBagConstraints();
	incomingAmount = new JTextField(textFieldColunm);
	amountTextField.gridx = 1;
	amountTextField.gridy = 2;
	amountTextField.ipady = 2;
	amountTextField.ipadx = 115;
	amountTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(incomingAmount, amountTextField);
	incomingAmount.setText(String.valueOf(balance));

	
	GridBagConstraints cisLabel = new GridBagConstraints();
	cisLabel.gridx = 0;
	cisLabel.gridy = 3;
	cisLabel.ipadx = 60;
	cisLabel.weighty = weightye;
	cisLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("CIS PAID:", JLabel.RIGHT), cisLabel);

	GridBagConstraints cisTextField = new GridBagConstraints();
	cis = new JTextField(textFieldColunm);
	cisTextField.gridx = 1;
	cisTextField.gridy = 3;
	cisTextField.ipady = 2;
	cisTextField.ipadx = 115;
	cisTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(cis, cisTextField);
	cis.setText("0");
	
	GridBagConstraints typeLabel = new GridBagConstraints();
	typeLabel.gridx = 0;
	typeLabel.gridy = 4;
	typeLabel.weighty = weightye;
	typeLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Payment Type:"), typeLabel);

	GridBagConstraints typeField = new GridBagConstraints();
	Set<String> keys = paymentType.keySet();
	type = new JComboBox(keys.toArray());
	typeField.gridx = 1;
	typeField.gridy = 4;
	typeField.ipady = 2;
    typeField.ipadx = 50;
	typeField.anchor =  GridBagConstraints.WEST;
	addpanel.add(type, typeField);
	
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
	GridBagConstraints saveButton = new GridBagConstraints();
	save = new JButton("SAVE");
	saveButton.gridx = 0;
	saveButton.gridy = 0;
	buttonpanel.add(save, saveButton);
	
	GridBagConstraints exitButton = new GridBagConstraints();
	exit = new JButton("EXIT");
	exitButton.gridx = 1;
	exitButton.gridy = 0;
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
	save.addActionListener(this); 
	cis.addFocusListener(this);
	incomingAmount.addFocusListener(this);
    }

   public void actionPerformed( ActionEvent e) {  

	if (e.getSource().equals(exit)){
	    dispose();	
	 }

	 if (e.getSource().equals(save)){
		ArrayList<String> prodetails =  new ArrayList<String>();
		prodetails.add(paymentId.getText());
		prodetails.add(ul.usDateString(sh.dateString(date)));
		prodetails.add(incomingAmount.getText());
		String ciaValue  = cis.getText();
		if(ciaValue.charAt(0) == '£'){
			prodetails.add(ul.noPoundString(ciaValue));
		}
		else{
			prodetails.add(ciaValue);
		}
		prodetails.add(String.valueOf(paymentType.get((String)type.getSelectedItem())));
		prodetails.add(note.getText());
	    bean.connect().addIncomingIn(ul.writeArrayListStr(prodetails)); 
		dispose();
	  }
	}
	public void focusLost(FocusEvent e){
		if(e.getSource().equals(cis)){
			BigDecimal tax = new BigDecimal(cis.getText());
			BigDecimal incoming = new BigDecimal(incomingAmount.getText());
			if((tax.add(incoming)).compareTo(balance) > 0){
				cis.setText("£0.00");
				incomingAmount.setText(String.valueOf(balance));
			}
		}
		else{
			BigDecimal incoming = new BigDecimal(incomingAmount.getText());
			if(incoming.compareTo(balance) > 0){
				incomingAmount.setText(String.valueOf(balance));
			}
		}
	
	}
	public void focusGained(FocusEvent e){}

}