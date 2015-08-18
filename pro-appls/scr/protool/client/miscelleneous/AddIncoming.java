
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

/**

*
*

*
* @author <a href="mailto:"></a>
* @version 1.0
*/
public class AddIncoming extends JFrame implements ActionListener{//implements ActionListener{
    // private ProtoolClientBean clientbean;
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
	private   SpinnerHelper sh;
   // private  ProtoolClient  clientBean;
    private String client = "";
   // private BeanStud bean;
	//private Utilities ul = new Utilities();
	private final double weightye = 0.5;
    Container con = getContentPane();
    
    public AddIncoming(String title){//, int proId
   	super(title); 


	addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();
	sh = new SpinnerHelper();
	//bean = new BeanStud();
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
	incomingId = new JTextField();
	idTextField.gridx = 1;
	idTextField.gridy = 0;
	idTextField.ipady = 2;
	idTextField.ipadx = 110;
	idTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(incomingId, idTextField);
	incomingId.setEditable(false);

	GridBagConstraints paymentinLabel = new GridBagConstraints();
	paymentinLabel.gridx = 0;
	paymentinLabel.gridy = 1;
	paymentinLabel.weighty =  weightye;
	paymentinLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Payment Id:"), paymentinLabel);

	GridBagConstraints paymentidField = new GridBagConstraints();
	paymentId = new JTextField();
	paymentidField.gridx = 1;
	paymentidField.gridy = 1;
	paymentidField.ipady = 2;
	paymentidField.ipadx = 110;
	paymentidField.anchor =  GridBagConstraints.WEST;
	addpanel.add(paymentId, paymentidField);
	paymentId.setEditable(false);

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
	addpanel.add(sh.getGenericDate(date), date1TextField);
	
	GridBagConstraints amountLabel = new GridBagConstraints();
	amountLabel.gridx = 0;
	amountLabel.gridy = 3;
	amountLabel.weighty = weightye;
	amountLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Incoming Amount:"), amountLabel);

	GridBagConstraints amountTextField = new GridBagConstraints();
	incomingAmount = new JTextField();
	amountTextField.gridx = 1;
	amountTextField.gridy = 3;
	amountTextField.ipady = 2;
	amountTextField.ipadx = 115;
	amountTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(incomingAmount, amountTextField);

	
	GridBagConstraints cisLabel = new GridBagConstraints();
	cisLabel.gridx = 0;
	cisLabel.gridy = 4;
	cisLabel.ipadx = 60;
	cisLabel.weighty = weightye;
	cisLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("CIS PAID:", JLabel.RIGHT), cisLabel);

	GridBagConstraints cisTextField = new GridBagConstraints();
	cis = new JTextField();
	cisTextField.gridx = 1;
	cisTextField.gridy = 4;
	cisTextField.ipady = 2;
	cisTextField.ipadx = 115;
	cisTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(cis, cisTextField);
	
	GridBagConstraints noteLabel = new GridBagConstraints();
    noteLabel.gridx = 0;
	noteLabel.gridy = 5;
	noteLabel.weighty = weightye;
	noteLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Additional Info:"), noteLabel);

	GridBagConstraints noteTextField = new GridBagConstraints();
	note = new JTextField();
	noteTextField.ipady = 2;
	noteTextField.ipadx = 190;
	noteTextField.gridx = 1;
	noteTextField.gridy = 5;
	noteTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(note, noteTextField);
	
	GridBagConstraints typeLabel = new GridBagConstraints();
	typeLabel.gridx = 0;
	typeLabel.gridy = 6;
	typeLabel.weighty = weightye;
	typeLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Payment Type:"), typeLabel);

	GridBagConstraints typeField = new GridBagConstraints();
	type = new JTextField();
	typeField.gridx = 1;
	typeField.gridy = 6;
	typeField.ipady = 2;
    typeField.ipadx = 115;
	typeField.anchor =  GridBagConstraints.WEST;
	addpanel.add(type, typeField);
	

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
	//save.addActionListener(this); 
	//ok.addActionListener(this); 
	//	setRemoteClient(clientBean);
	//projectId.setText(String.valueOf(proId));  
	//int get =  ul.getGenericId(bean.connect().geneProtoolId(), 2000);
	//	}
    }

   public void actionPerformed( ActionEvent e) {  

	if (e.getSource().equals(exit)){
	    dispose();	
	 }
	}

	 /*if (e.getSource().equals(save)){
		Vector<Object> prodetails =  new Vector<Object>();
		prodetails.add(projectTitle.getText());
		prodetails.add(clientName.getText());
		prodetails.add(addresses.getText());
		prodetails.add(telephone.getText());
		prodetails.add(email.getText());
		prodetails.add(Double.valueOf(quote.getText()));
		prodetails.add(Double.valueOf(cost.getText()));
		prodetails.add(ul.usDate(date.getText()));
		prodetails.add(Integer.valueOf(duration.getText()));
	    bean.connect().AddPaymentin(ul.writeVector(prodetails)); 
		dispose();
		new AddPaymentin("NEW PROJECT", ul.getGenericId(bean.connect().geneProtoolId()));
		//new AddProject("NEW PROJECT", bean.connect().geneProtoolId());
	}
 
	if (e.getSource().equals(ok)){
		Vector<Object> prodetails =  new Vector<Object>();
		prodetails.add(projectTitle.getText());
		prodetails.add(clientName.getText());
		prodetails.add(addresses.getText());
		prodetails.add(telephone.getText());
		prodetails.add(email.getText());
		prodetails.add(Double.valueOf(quote.getText()));
		prodetails.add(Double.valueOf(cost.getText()));
		prodetails.add(ul.usDate(date.getText()));
		prodetails.add(Integer.valueOf(duration.getText()));
	    bean.connect().AddPaymentin(ul.writeVector(prodetails));    
	    dispose();
	}
    }*/
	  /* public static void main(String[] args)throws Exception{
		use global jndi naming to get handle to ejb stub
		try{
			clientBean = (ProtoolClient) new InitialContext().lookup("java:global/ProtoolMD/pro-ejbs/ProtoolClientBean");
		}catch (Exception ex){}
	
		 AddIncoming ds = new AddIncoming("INCOMING");
	}*/

}