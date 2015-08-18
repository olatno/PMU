
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
public class EditIncoming extends JFrame implements ActionListener, FocusListener {//implements ActionListener{
   
    private JTextField incomingId;
    private JTextField paymentId;
	private JTextField cis;
    private JTextField projectId;
    private JTextField note;
    private JTextField incomingAmount;
    private JSpinner date;
    private JTextField description;
	private JTextField type;
	private JRadioButton exclude; 
    private JButton save;
    private JButton ok;
    private JButton exit;
    private JPanel addpanel;
    private JPanel buttonpanel;
    private JPanel generalPanel;
    private Context ctx;
	private SpinnerHelper sh;
	private BeanStud bean;
	private Utilities ul = new Utilities();
	private PaymentInUtilities pu = new PaymentInUtilities();
	private final double weightye = 0.5;
    Container con = getContentPane();
	private BigDecimal balance ;
	private int paymentNumber = 0;
	private int incomingNumber = 0;
	private BigDecimal totalIncome;
	private BigDecimal totalTax ;
	private BigDecimal totalIncomeExc;
	private BigDecimal totalTaxExc ;
	private IncomingDetails details;
	private final int textFieldColunm = 1;
    
    public EditIncoming(String title, IncomingDetails details){//, int proId
   	super(title); 
	bean = new BeanStud();
	
	this.details = details;
	balance = pu.getTotalQuote(bean.connect().getPaymentBalance((new Integer(String.valueOf(details.getTableModelName().getValueAt(0, 6)))).intValue()));
	
	paymentNumber = ((Integer)details.getTableModelName().getValueAt(0, 6)).intValue();
	incomingNumber = ((Integer)details.getTableModelName().getValueAt(details.getRow(), 6)).intValue();
	
	totalIncome = pu.getTotalQuote(bean.connect().getGenericIncoming(paymentNumber, 10010));//total when balance is nill
	totalTax = pu.getTotalQuote(bean.connect().getGenericIncoming(paymentNumber, 70010));//total tax when balance is nill
    totalIncomeExc =  pu.getTotalQuote(bean.connect().getIncomingExclude(paymentNumber, 10010, incomingNumber));
	totalTaxExc =  pu.getTotalQuote(bean.connect().getIncomingExclude(paymentNumber, 70010, incomingNumber));
	
	addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();
	sh = new SpinnerHelper();
	//Panels
	addpanel.setLayout(new GridBagLayout());
	buttonpanel.setLayout(new GridLayout(1,6));
	generalPanel.setLayout(new BorderLayout());

	//Textfields 
	GridBagConstraints idLabel = new GridBagConstraints();
	idLabel.gridx = 0;
	idLabel.gridy = 0;
	idLabel.anchor =  GridBagConstraints.EAST;
   	idLabel.weighty = weightye;
	addpanel.add(new JLabel("Incoming Id:"), idLabel);

	GridBagConstraints idTextField = new GridBagConstraints();
	incomingId = new JTextField(textFieldColunm);
	idTextField.gridx = 1;
	idTextField.gridy = 0;
	idTextField.ipady = 2;
	idTextField.ipadx = 110;
	idTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(incomingId, idTextField);
	incomingId.setEditable(false);
	incomingId.setText(String.valueOf(incomingNumber));

	GridBagConstraints paymentinLabel = new GridBagConstraints();
	paymentinLabel.gridx = 0;
	paymentinLabel.gridy = 1;
	paymentinLabel.weighty =  weightye;
	paymentinLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Payment Id:"), paymentinLabel);

	GridBagConstraints paymentidField = new GridBagConstraints();
	paymentId = new JTextField(textFieldColunm);
	paymentidField.gridx = 1;
	paymentidField.gridy = 1;
	paymentidField.ipady = 2;
	paymentidField.ipadx = 110;
	paymentidField.anchor =  GridBagConstraints.WEST;
	addpanel.add(paymentId, paymentidField);
	paymentId.setEditable(false);
	paymentId.setText(String.valueOf(paymentNumber));

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
	date1TextField.ipadx = -13;
	date1TextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(sh.getGenericDateEdit(date, String.valueOf(details.getTableModelName().getValueAt(details.getRow(), 0))), date1TextField);
	
	GridBagConstraints amountLabel = new GridBagConstraints();
	amountLabel.gridx = 0;
	amountLabel.gridy = 3;
	amountLabel.weighty = weightye;
	amountLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Incoming Amount:"), amountLabel);

	GridBagConstraints amountTextField = new GridBagConstraints();
	incomingAmount = new JTextField(textFieldColunm);
	amountTextField.gridx = 1;
	amountTextField.gridy = 3;
	amountTextField.ipady = 2;
	amountTextField.ipadx = 115;
	amountTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(incomingAmount, amountTextField);
	incomingAmount.setText(ul.noPoundString(String.valueOf(details.getTableModelName().getValueAt(details.getRow(), 1))));

	
	GridBagConstraints cisLabel = new GridBagConstraints();
	cisLabel.gridx = 0;
	cisLabel.gridy = 4;
	cisLabel.ipadx = 60;
	cisLabel.weighty = weightye;
	cisLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("CIS PAID:", JLabel.RIGHT), cisLabel);

	GridBagConstraints cisTextField = new GridBagConstraints();
	cis = new JTextField(textFieldColunm);
	cisTextField.gridx = 1;
	cisTextField.gridy = 4;
	cisTextField.ipady = 2;
	cisTextField.ipadx = 115;
	cisTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(cis, cisTextField);
	cis.setText(ul.noPoundString(String.valueOf(details.getTableModelName().getValueAt(details.getRow(), 2))));
	
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
	note.setText(details.getNote().getText());
	
	GridBagConstraints typeLabel = new GridBagConstraints();
	typeLabel.gridx = 0;
	typeLabel.gridy = 6;
	typeLabel.weighty = weightye;
	typeLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Payment Type:"), typeLabel);

	GridBagConstraints typeField = new GridBagConstraints();
	type = new JTextField(textFieldColunm);
	typeField.gridx = 1;
	typeField.gridy = 6;
	typeField.ipady = 2;
    typeField.ipadx = 115;
	typeField.anchor =  GridBagConstraints.WEST;
	addpanel.add(type, typeField);
	type.setText(details.getType().getText());
	

	//BUTTON BOTTOM
	buttonpanel.add(new JLabel(""));
	buttonpanel.add(new JLabel(""));
	save = new JButton("SAVE");
	buttonpanel.add(save);
	exit = new JButton("EXIT");
	buttonpanel.add(exit);
	buttonpanel.add(new JLabel(""));
	buttonpanel.add(new JLabel(""));
	
	generalPanel.add("Center", addpanel);
	generalPanel.add("South", buttonpanel);
	generalPanel.setBorder(new Partition(20,"AddPaymentout"));
	con.add(generalPanel);

	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(500, 580);
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
		Vector<Object> prodetails =  new Vector<Object>();
		prodetails.add(ul.usDate(sh.dateString(date)));
		prodetails.add(cis.getText());
		prodetails.add(note.getText());
		prodetails.add(type.getText());
		prodetails.add(paymentId.getText());
		prodetails.add(incomingAmount.getText());
		prodetails.add(incomingId.getText());
	    bean.connect().updateIncomingIn(ul.writeVector(prodetails)); 
		dispose();
	  }
	}
	
	public void focusLost(FocusEvent e){
		BigDecimal tax = new BigDecimal(cis.getText());
		BigDecimal incoming = new BigDecimal(incomingAmount.getText());

		BigDecimal income_plus_tax = totalIncome.add(totalTax);
		BigDecimal incomeexc_plus_taxexc = totalIncomeExc.add(totalTaxExc);	
		if(e.getSource().equals(incomingAmount)){
			if(((incoming.add(tax)).add(incomeexc_plus_taxexc)).compareTo(income_plus_tax) > 0){
				incomingAmount.setText(ul.noPoundString(String.valueOf(details.getTableModelName().getValueAt(details.getRow(), 1))));
				cis.setText(ul.noPoundString(String.valueOf(details.getTableModelName().getValueAt(details.getRow(), 2))));
			}
		}
		else{
			if(((tax.add(incoming)).add(incomeexc_plus_taxexc)).compareTo(income_plus_tax) > 0){
				cis.setText(ul.noPoundString(String.valueOf(details.getTableModelName().getValueAt(details.getRow(), 2))));
				incomingAmount.setText(ul.noPoundString(String.valueOf(details.getTableModelName().getValueAt(details.getRow(), 1))));
			}
		}
	
	}
	public void focusGained(FocusEvent e){}

}