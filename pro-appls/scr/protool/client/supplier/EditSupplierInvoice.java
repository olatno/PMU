
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
public class EditSupplierInvoice extends JFrame implements ActionListener{//implements ActionListener{
    private JTextField invoice;
    private JTextField account;
    private JTextField projectId;
	private JTextField supplierId;
    private JTextField note;
	private Choice vat;
    private JTextField invoiceAmount;
    private JSpinner date;
	private JButton addproject; 
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
	private Utilities ul = new Utilities();
	private PaymentInUtilities pu;
	private final int textFieldColunm = 1;
	private final double weightye = 0.5;
	private Integer projectIds = null;
	private int invoiceid;
	private ArrayList<Object> invoiceDetails;
    Container con = getContentPane();
    
    public EditSupplierInvoice(String title, int invoiceid){
	 super(title); 
	this.invoiceid = invoiceid;

	bean = new BeanStud();
	pu =  new PaymentInUtilities();
	invoiceDetails = pu.getArrayList(bean.connect().editSupplierInvoice(invoiceid));
	addpanel = new JPanel();
	buttonpanel = new JPanel();
	generalPanel = new JPanel();
	sh = new SpinnerHelper();
	//Panels
	addpanel.setLayout(new GridBagLayout());
	buttonpanel.setLayout(new GridBagLayout());
	generalPanel.setLayout(new BorderLayout());

	//Textfields 
	GridBagConstraints projectButton = new GridBagConstraints();
	addproject = new JButton("PROJECT");
	projectButton.gridx = 0;
	projectButton.gridy = 0;
	projectButton.anchor =  GridBagConstraints.EAST;
   	projectButton.weighty = weightye;
	addpanel.add(addproject, projectButton);

	GridBagConstraints idTextField = new GridBagConstraints();
	projectId = new JTextField(textFieldColunm);
	idTextField.gridx = 1;
	idTextField.gridy = 0;
	idTextField.ipady = 2;
	idTextField.ipadx = 115;
	idTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(projectId, idTextField);
	String str[] = ((String)invoiceDetails.get(0)).split("\\.");
	projectId.setText(str[1]);
	projectIds = Integer.valueOf(str[0]);
	projectId.setEditable(false);
	
	GridBagConstraints supplierIdLabel = new GridBagConstraints();
	supplierIdLabel.gridx = 0;
	supplierIdLabel.gridy = 1;
	supplierIdLabel.weighty =  weightye;
    supplierIdLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Supplier Id:"), supplierIdLabel);

	GridBagConstraints supplierIdField = new GridBagConstraints();
	supplierId = new JTextField(textFieldColunm);
	supplierIdField.gridx = 1;
	supplierIdField.gridy = 1;
	supplierIdField.ipady = 2;
	supplierIdField.ipadx = 115;
	supplierIdField.anchor =  GridBagConstraints.WEST;
	addpanel.add(supplierId, supplierIdField);
	supplierId.setText(String.valueOf(invoiceDetails.get(1)));
	//supplierId.setEditable(false);

	GridBagConstraints materialLabel = new GridBagConstraints();
	materialLabel.gridx = 0;
	materialLabel.gridy = 2;
	materialLabel.weighty =  weightye;
    materialLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Accounts Name:"), materialLabel);

	GridBagConstraints materialField = new GridBagConstraints();
	account = new JTextField(textFieldColunm);
	materialField.gridx = 1;
	materialField.gridy = 2;
	materialField.ipady = 2;
	materialField.ipadx = 115;
	materialField.anchor =  GridBagConstraints.WEST;
	addpanel.add(account, materialField);
	account.setEditable(false);
	Hashtable<Integer, String> accname = pu.getAccountCodeId(bean.connect().getHashTableProjectCost());
	account.setText(accname.get(new Integer(50010)));
	
	GridBagConstraints invoiceLabel = new GridBagConstraints();
	invoiceLabel.gridx = 0;
	invoiceLabel.gridy = 3;
	invoiceLabel.weighty = weightye;
	invoiceLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Invoice Number:"), invoiceLabel);

	GridBagConstraints invoiceTextField = new GridBagConstraints();
	invoice = new JTextField(textFieldColunm);
	invoiceTextField.gridx = 1;
	invoiceTextField.gridy = 3;
	invoiceTextField.ipady = 2;
	invoiceTextField.ipadx = 115;
	invoiceTextField.anchor =  GridBagConstraints.WEST;
	invoice.setText(String.valueOf(invoiceDetails.get(2)));
	addpanel.add(invoice, invoiceTextField);

	GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 4;
	date1Label.weighty = weightye;
    date1Label.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Invoice Date:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	date = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 4;
	date1TextField.ipady = 2;
	date1TextField.ipadx = -11;
	date1TextField.anchor =  GridBagConstraints.WEST;
	//addpanel.add(sh.getGenericDate(date), date1TextField);
	addpanel.add(sh.getGenericDateEdit(date, ul.ukFormat((java.sql.Date)invoiceDetails.get(3))), date1TextField);
	
	GridBagConstraints amountLabel = new GridBagConstraints();
	amountLabel.gridx = 0;
	amountLabel.gridy = 5;
	amountLabel.weighty = weightye;
	amountLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("Invoice Amount:"), amountLabel);

	GridBagConstraints amountTextField = new GridBagConstraints();
	invoiceAmount = new JTextField(textFieldColunm);
	amountTextField.gridx = 1;
	amountTextField.gridy = 5;
	amountTextField.ipady = 2;
	amountTextField.ipadx = 115;
	amountTextField.anchor =  GridBagConstraints.WEST;
	invoiceAmount.setText(String.valueOf((BigDecimal)invoiceDetails.get(4)));
	addpanel.add(invoiceAmount, amountTextField);

	
	GridBagConstraints cisLabel = new GridBagConstraints();
	cisLabel.gridx = 0;
	cisLabel.gridy = 6;
	cisLabel.ipadx = 60;
	cisLabel.weighty = weightye;
	cisLabel.anchor =  GridBagConstraints.EAST;
	addpanel.add(new JLabel("VAT:", JLabel.RIGHT), cisLabel);

	GridBagConstraints vatTextField = new GridBagConstraints();
    vat = new Choice();
	//String vatstr = String.valueOf(((Double)invoiceDetails.get(5)).doubleValue())+"%";
	String vatstr = String.valueOf(invoiceDetails.get(5))+"%";
	vat.add("none");
	vat.add("0%");
	vat.add("17.5%");
	vat.add("20%");
	vat.select(vatstr);
	vatTextField.gridx = 1;
	vatTextField.gridy = 6;
	vatTextField.ipady = 2;
	vatTextField.ipadx = 50;
	vatTextField.anchor =  GridBagConstraints.WEST;
	addpanel.add(vat, vatTextField);
	//vatchoice = vat.getSelectedItem();

		//BUTTON BOTTOM
	GridBagConstraints savebutton = new GridBagConstraints();
	save = new JButton("SAVE");
	savebutton.gridx = 0;
	savebutton.gridy = 0;
	savebutton.insets = new Insets(0, 0, 0, 20);
	//buttonpanel.add(save, savebutton);
	
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
	save.addActionListener(this); 
	ok.addActionListener(this); 
	addproject.addActionListener(this);

    }

	public class ProjectInfo extends JFrame implements ActionListener, ItemListener{//implements ActionListener{

		private JButton refresh;
		private JButton okpro;
		private JButton exitpro;
		private JPanel headerpanel;
		private JPanel contentpanel;
		private JPanel footerpanel;
		private JPanel mainpanel;
		private JScrollPane projectPane;
		private Context ctx;
		private ArrayList<JLabel> titles = new ArrayList<JLabel>();
		private ArrayList<JLabel> project = new ArrayList<JLabel>();
		private ArrayList<JCheckBox> checked = new ArrayList<JCheckBox>();
		private   SpinnerHelper sh;
		private String client = "";
		private final int ipadiy = 2;
		private final int ipadix = 140;
		private final int ipadiyb = 2;
		private final int ipadixb = 4;
		private int selectIndex = 0;
		private final double weightix = 2.0;
		private final double weightiy = 1.0;
		Container con = getContentPane();
    
		public ProjectInfo(String title){//, int proId
		super(title); 

	//Panels	
		headerpanel = new JPanel();
		contentpanel = new JPanel();
		footerpanel = new JPanel();
		mainpanel = new JPanel();
	
		contentpanel.setLayout(new GridBagLayout());
		headerpanel.setLayout(new GridBagLayout());
		footerpanel.setLayout(new GridBagLayout());
		mainpanel.setLayout(new BorderLayout());
		sh = new SpinnerHelper();
	

		GridBagConstraints descriLabel = new GridBagConstraints();
		descriLabel.gridx = 0;
		descriLabel.gridy = 0;
		descriLabel.ipadx = 5;
		descriLabel.insets = new Insets(15, 0, 10, 0);
		headerpanel.add(new JLabel("Select:"), descriLabel);
	
		GridBagConstraints qtyLabel = new GridBagConstraints();
		qtyLabel.gridx = 1;
		qtyLabel.gridy = 0;
		qtyLabel.weightx = weightix;
		qtyLabel.ipadx = 25;
		qtyLabel.insets = new Insets(15, 0, 10, 0);
		headerpanel.add(new JLabel("Project Title:"), qtyLabel);
		
		GridBagConstraints dateLabel = new GridBagConstraints();
		dateLabel.gridx = 2;
		dateLabel.gridy = 0;
		dateLabel.ipadx = 10;
		dateLabel.weightx = weightix;
		dateLabel.insets = new Insets(15, 90, 10, 25);
		headerpanel.add(new JLabel("Project Id:"), dateLabel);
		int row = 0;
		ArrayList<ArrayList<Object>> projectDetails = pu.getArrayListOfArrayList(bean.connect().projectInfos());
		//for(int row = 0; row < 6; row++){//   
		for(Iterator<ArrayList<Object>> it = projectDetails.iterator(); it.hasNext();){
			ArrayList<Object> obj = it.next();
			GridBagConstraints checkSelect = new GridBagConstraints();
			JCheckBox check = new JCheckBox();
			checkSelect.gridx = 0;
			checkSelect.gridy = row;
			checkSelect.ipadx = 5;
			checkSelect.insets = new Insets(15, 0, 10, 0);
			contentpanel.add(check, checkSelect);
			checked.add(check);
		
			GridBagConstraints typeTextField1 = new GridBagConstraints();
			JLabel protitle = new JLabel();
			typeTextField1.gridx = 1;
			typeTextField1.gridy = row;
			typeTextField1.ipady = ipadiy;
			typeTextField1.weightx = weightix;
			typeTextField1.ipadx = 25;
			typeTextField1.anchor =  GridBagConstraints.WEST;
			typeTextField1.insets = new Insets(15, 50, 10, 60);
			contentpanel.add(protitle , typeTextField1);
			titles.add(protitle);
			String str = (String)obj.get(1);
			if(str.length() > 27){
				String str1 = str.substring(0, 28);
				protitle.setText(str1+"...");
			}
			else{
				protitle.setText(str);
			}
		
			GridBagConstraints typeTextField2 = new GridBagConstraints();
			JLabel projectids = new JLabel();
			typeTextField2.gridx = 2;
			typeTextField2.gridy = row;
			typeTextField2.ipady = ipadiy;
			typeTextField2.ipadx = 10;
			typeTextField2.anchor =  GridBagConstraints.EAST;
			typeTextField2.weightx = weightix;
			typeTextField2.insets = new Insets(15, 0, 10, 35);
			contentpanel.add(projectids, typeTextField2);
			projectids.setText(String.valueOf((Integer)obj.get(0)));
			project.add(projectids);
			row++;
		}
	//BUTTON BOTTOM
		GridBagConstraints submitquote = new GridBagConstraints();
		okpro = new JButton("OK");
		submitquote.gridx = 0;
		submitquote.gridy = 0;
		submitquote.insets = new Insets(0, 0, 0, 20);
		footerpanel.add(okpro, submitquote);
		
		GridBagConstraints refreshB = new GridBagConstraints();
		refresh = new JButton("REFRESH");
		refreshB.gridx = 1;
		refreshB.gridy = 0;
		refreshB.insets = new Insets(0, 0, 0, 20);
		footerpanel.add(refresh, refreshB);
		
	
		GridBagConstraints exitquote = new GridBagConstraints();
		exitpro = new JButton("EXIT");
		exitquote.gridx = 2;
		exitquote.gridy = 0;
		footerpanel.add(exitpro, exitquote);
	
	
	
	
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
		mainpanel.add(contentpanel, BorderLayout.NORTH);
		projectPane = new JScrollPane(mainpanel, v,h);
		projectPane.setColumnHeaderView(headerpanel);
		projectPane.setBorder(new Partition(20,"Ledger"));
		con.add(projectPane);
		con.add(footerpanel, "South");

		pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 600);
		setResizable(false);
		setVisible(true); 
		exitpro.addActionListener(this);  
		refresh.addActionListener(this); 
		okpro.addActionListener(this); 

		for(int i = 0; i < checked.size(); i++){
			checked.get(i).addItemListener(this); 
		}
	}
	
		public void itemStateChanged(ItemEvent e) {
 
			for(int i = 0; i < checked.size(); i++){
				if(checked.get(i).isSelected()){
					selectIndex = i;
					for(int j = 0; j < checked.size(); j++){
						checked.get(j).setEnabled(false);
					}
				}
		
			}
		}	


		public void actionPerformed( ActionEvent e) {  

		if (e.getSource().equals(exitpro)){
			dispose();	
		}
	 
		if (e.getSource().equals(refresh)){
			dispose();	
			ProjectInfo ds = new ProjectInfo("List Of Project");
		}
	 
	 	if (e.getSource().equals(okpro)){
			projectId.setText(titles.get(selectIndex).getText());
			projectIds = new Integer(project.get(selectIndex).getText());
			dispose();	
		}
		}
	}

   public void actionPerformed( ActionEvent e) {  

	if (e.getSource().equals(exit)){
	    dispose();	
	 }
	 
	if (e.getSource().equals( addproject)){
	     ProjectInfo ds = new ProjectInfo("List Of Project");	
	 }
	 
	 if (e.getSource().equals(ok)){
		java.util.List<Object> supplierinvoice =  new ArrayList<Object>();
		supplierinvoice.add(Integer.valueOf(invoiceid));
		supplierinvoice.add(projectIds);
		supplierinvoice.add(new Integer(supplierId.getText()));
		supplierinvoice.add(new Integer(50010));
		supplierinvoice.add(invoice.getText());
		supplierinvoice.add(ul.usDate(sh.dateString(date)));
		supplierinvoice.add(invoiceAmount.getText());
		supplierinvoice.add(vat.getSelectedItem());
	    bean.connect().editSupplierInvoices(ul.writeArrayList(supplierinvoice));    
	    dispose();

	 }
	 
	/* if (e.getSource().equals(save)){
		java.util.List<Object> supplierinvoice =  new ArrayList<Object>();
		supplierinvoice.add(projectIds);
		supplierinvoice.add(new Integer(supplierId.getText()));
		supplierinvoice.add(new Integer(50010));
		supplierinvoice.add(invoice.getText());
		supplierinvoice.add(ul.usDate(sh.dateString(date)));
		supplierinvoice.add(Double.valueOf(invoiceAmount.getText()));
		supplierinvoice.add(vat.getSelectedItem());
	    bean.connect().EditSupplierInvoice(ul.writeArrayList(supplierinvoice)); 
		dispose();
		EditSupplierInvoice ds = new EditSupplierInvoice("SUPLIER INVOICE", (new Integer(supplierId.getText())).intValue());
	    	
	 }*/
	
	}

}