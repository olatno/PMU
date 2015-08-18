
package scr.protool.client.fixedasset;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import javax.naming.*;
import scr.protool.client.utilities.*;
import java.math.BigDecimal;
import scr.protool.client.miscelleneous.BeanStud;



public class FixedAssetDetails extends  JFrame implements ActionListener{//ItemListener implements Serializable, ActionListener

    Container con = getContentPane();
	private BeanStud bean;
    private JPanel mainPanel;
	private JPanel detailPanel;
    private JPanel ledgerPanel;
    private JButton refresh;//bottom for mainPanel
    private JButton editPayment;//bottom for ledgerPanel
    private JButton exit;//bottom for mainPanel 
	private JTextField description; 
	private JTextField assetId; 
	private JTextField insurance;
	private JTextField insuranceId;
	private JTextField serial;
	private JTextField location;
	private JTextField rate;
	private JTextField method;
	private JTextField life;
	private JTextField aquisition;
	private JTextField supplier;
	private JTextField supplierId;
	private JTextField residual;
	private JTextField vat;
	private JTextField cost;
	private JTextField financed;
	private JTextField invoice;
	private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int ipadiyb = 2;
    private final int ipadixb = 4;
    private final double weightix = 2.0;
	private final double weightiy = 0.5;
	private final int initialRow = 22;
	private final int textFieldColunm = 1;
	 private JTable ledgerView; 
    private DefaultTableModel ledgerDataModel; 
    private JScrollPane ledgerViewScroll; 
	private JScrollPane buttonPanel;
	private PaymentInUtilities pu;
	private Utilities ul;
	private String empty = null;
	private int assetsId;

    public FixedAssetDetails(String title, int assetsId) {
	super(title);
	
	this.assetsId = assetsId;
	bean = new BeanStud();
	ul = new Utilities();
	pu = new PaymentInUtilities();
	empty = new String( "\" \"");
	ArrayList<Object> details = pu.getArrayList(bean.connect().getAssetSingleDetails(assetsId));
	mainPanel = new JPanel();
	detailPanel = new JPanel();
	ledgerPanel = new JPanel();
	detailPanel.setLayout(new GridBagLayout());
	ledgerPanel.setLayout(new GridBagLayout());

	GridBagConstraints typeLabel = new GridBagConstraints();
	typeLabel.gridx = 0;
	typeLabel.gridy = 0;
	typeLabel.anchor =  GridBagConstraints.EAST;
	typeLabel.weightx = weightix;
	typeLabel.insets = new Insets(8, 15, 20, 0);
    detailPanel.add(new JLabel("Description:"), typeLabel);

	GridBagConstraints typeTextField = new GridBagConstraints();
	description = new JTextField(textFieldColunm);
	typeTextField.gridx = 1;
	typeTextField.gridy = 0;
	typeTextField.ipady = ipadiy;
	typeTextField.ipadx = ipadix;
	typeTextField.insets = new Insets(8, 0, 20, 0);
	typeTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(description, typeTextField);
	description.setEditable(false);
	
	if(empty.equals(String.valueOf(details.get(0)))){
		 description.setText("");
		}
	else{
		description.setText(String.valueOf(details.get(0)));
	}
	GridBagConstraints noteLabel = new GridBagConstraints();
	noteLabel.gridx = 2;
	noteLabel.gridy = 0;
	noteLabel.anchor =  GridBagConstraints.EAST;
	noteLabel.weightx = weightix;
	noteLabel.insets = new Insets(8, 10, 20, 0);
    detailPanel.add(new JLabel("ID:"), noteLabel);

	GridBagConstraints noteTextField = new GridBagConstraints();
	assetId = new JTextField(textFieldColunm);
	noteTextField.gridx = 3;
	noteTextField.gridy = 0;
	noteTextField.ipady = ipadiy;
	noteTextField.ipadx = ipadix;
	noteTextField.weightx = weightix;
	noteTextField.insets = new Insets(8, 0, 20, 0);
	noteTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(assetId, noteTextField);
	assetId.setEditable(false);
	assetId.setText(String.valueOf(assetsId));
	
	if(empty.equals(String.valueOf(details.get(1)))){
		 assetId.setText("");
		}
	else{
		assetId.setText(String.valueOf(details.get(1)));
	}

	GridBagConstraints serialLabel = new GridBagConstraints();
	serialLabel.gridx = 0;
	serialLabel.gridy = 1;
	serialLabel.anchor =  GridBagConstraints.EAST;
	serialLabel.weightx = weightix;
	serialLabel.insets = new Insets(8, 0, 20, 0);
    detailPanel.add(new JLabel("Serial Number:"), serialLabel);
	
	GridBagConstraints serialTextField = new GridBagConstraints();
	serial = new JTextField(textFieldColunm);
	serialTextField.gridx = 1;
	serialTextField.gridy = 1;
	serialTextField.ipady = ipadiy;
	serialTextField.ipadx = ipadix+ipadix;
	serialTextField.gridwidth = 3;
	serialTextField.insets = new Insets(8, 0, 20, 0);
	serialTextField.weightx = weightix;
	serialTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(serial, serialTextField);
	serial.setEditable(false);
	
	if(empty.equals(String.valueOf(details.get(2)))){
			serial.setText("");
		}
	else{
		serial.setText(String.valueOf(details.get(2)));
	}
	
	GridBagConstraints locationLabel = new GridBagConstraints();
	locationLabel.gridx = 0;
	locationLabel.gridy = 2;
	locationLabel.anchor =  GridBagConstraints.EAST;
	locationLabel.weightx = weightix;
	locationLabel.insets = new Insets(8, 0, 20, 0);
    detailPanel.add(new JLabel("Asset Location:"), locationLabel);
	
	GridBagConstraints locationTextField = new GridBagConstraints();
	location = new JTextField(textFieldColunm);
	locationTextField.gridx = 1;
	locationTextField.gridy = 2;
	locationTextField.ipady = ipadiy;
	locationTextField.ipadx = ipadix+ipadix;
	locationTextField.gridwidth = 3;
	locationTextField.insets = new Insets(8, 0, 20, 0);
	locationTextField.weightx = weightix;
	locationTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(location, locationTextField);
	location.setEditable(false);

	if(empty.equals(String.valueOf(details.get(3)))){
			location.setText("");
		}
	else{
		location.setText(String.valueOf(details.get(3)));
	}
	
	GridBagConstraints financedLabel = new GridBagConstraints();
	financedLabel.gridx = 0;
	financedLabel.gridy = 3;
	financedLabel.anchor =  GridBagConstraints.EAST;
	financedLabel.weightx = weightix;
	financedLabel.insets = new Insets(8, 0, 20, 0);
    detailPanel.add(new JLabel("Financed By:"), financedLabel);

	GridBagConstraints financedTextField = new GridBagConstraints();
	financed = new JTextField(textFieldColunm);
	financedTextField.gridx = 1;
	financedTextField.gridy = 3;
	financedTextField.ipady = ipadiy;
	financedTextField.ipadx = ipadix;
	financedTextField.insets = new Insets(8, 0, 20, 0);
	financedTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(financed, financedTextField);
	financed.setEditable(false);

	if(empty.equals(String.valueOf(details.get(4)))){
			financed.setText("");
		}
	else{
		financed.setText(String.valueOf(details.get(4)));
	}
	
	GridBagConstraints invoiceLabel = new GridBagConstraints();
	invoiceLabel.gridx = 2;
	invoiceLabel.gridy = 3;
	invoiceLabel.anchor =  GridBagConstraints.EAST;
	invoiceLabel.weightx = weightix;
	invoiceLabel.insets = new Insets(8, 5, 20, 0);
    detailPanel.add(new JLabel("Invoice Number:"), invoiceLabel);

	GridBagConstraints invoiceTextField = new GridBagConstraints();
	invoice = new JTextField(textFieldColunm);
	invoiceTextField.gridx = 3;
	invoiceTextField.gridy = 3;
	invoiceTextField.ipady = ipadiy;
	invoiceTextField.ipadx = ipadix;
	invoiceTextField.insets = new Insets(8, 0, 20, 0);
	invoiceTextField.weightx = weightix;
	invoiceTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(invoice, invoiceTextField);
	invoice.setEditable(false);

	if(empty.equals(String.valueOf(details.get(5)))){
			invoice.setText("");
		}
	else{
		invoice.setText(String.valueOf(details.get(5)));
	}
	
	GridBagConstraints costLabel = new GridBagConstraints();
	costLabel.gridx = 0;
	costLabel.gridy = 4;
	costLabel.anchor =  GridBagConstraints.EAST;
	costLabel.weightx = weightix;
	costLabel.insets = new Insets(8, 0, 20, 0);
    detailPanel.add(new JLabel("Cost:"), costLabel);

	GridBagConstraints costTextField = new GridBagConstraints();
	cost = new JTextField(textFieldColunm);
	costTextField.gridx = 1;
	costTextField.gridy = 4;
	costTextField.ipady = ipadiy;
	costTextField.ipadx = ipadix;
	costTextField.insets = new Insets(8, 0, 20, 0);
	costTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(cost, costTextField);
	cost.setEditable(false);

	if(empty.equals(String.valueOf(details.get(6)))){
			cost.setText("");
		}
	else{
		cost.setText(ul.getStringInCurrency((BigDecimal)details.get(6)));
	}
	
	GridBagConstraints vatLabel = new GridBagConstraints();
	vatLabel.gridx = 2;
	vatLabel.gridy = 4;
	vatLabel.anchor =  GridBagConstraints.EAST;
	vatLabel.weightx = weightix;
	vatLabel.insets = new Insets(8, 0, 20, 0);
    detailPanel.add(new JLabel("VAT:"), vatLabel);

	GridBagConstraints vatTextField = new GridBagConstraints();
	vat = new JTextField(textFieldColunm);
	vatTextField.gridx = 3;
	vatTextField.gridy = 4;
	vatTextField.ipady = ipadiy;
	vatTextField.ipadx = ipadix;
	vatTextField.insets = new Insets(8, 0, 20, 0);
	vatTextField.weightx = weightix;
	vatTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(vat, vatTextField);
	vat.setEditable(false);

	if(empty.equals(String.valueOf(details.get(7)))){
			vat.setText("");
		}
	else{
		vat.setText(ul.getStringInCurrency((BigDecimal)details.get(7)));
	}
	
	GridBagConstraints supplierLabel = new GridBagConstraints();
	supplierLabel.gridx = 0;
	supplierLabel.gridy = 5;
	supplierLabel.anchor =  GridBagConstraints.EAST;
	supplierLabel.weightx = weightix;
	supplierLabel.insets = new Insets(8, 0, 20, 0);
    detailPanel.add(new JLabel("Supplier:"), supplierLabel);

	GridBagConstraints supplierTextField = new GridBagConstraints();
	supplier = new JTextField(textFieldColunm);
	supplierTextField.gridx = 1;
	supplierTextField.gridy = 5;
	supplierTextField.ipady = ipadiy;
	supplierTextField.ipadx = ipadix;
	supplierTextField.insets = new Insets(8, 0, 20, 0);
	supplierTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(supplier, supplierTextField);
	supplier.setEditable(false);
	String[] suppliers = String.valueOf(details.get(8)).split("\\.");
	if(suppliers == null){
			supplier.setText("");
		}
	else{
		supplier.setText(suppliers[0]);
	}
	
	GridBagConstraints supplierIdLabel = new GridBagConstraints();
	supplierIdLabel.gridx = 2;
	supplierIdLabel.gridy = 5;
	supplierIdLabel.anchor =  GridBagConstraints.EAST;
	supplierIdLabel.weightx = weightix;
	supplierIdLabel.insets = new Insets(8, 0, 20, 0);
    detailPanel.add(new JLabel("ID:"), supplierIdLabel);

	GridBagConstraints supplierIdTextField = new GridBagConstraints();
	supplierId = new JTextField(textFieldColunm);
	supplierIdTextField.gridx = 3;
	supplierIdTextField.gridy = 5;
	supplierIdTextField.ipady = ipadiy;
	supplierIdTextField.ipadx = ipadix;
	supplierIdTextField.insets = new Insets(8, 0, 20, 0);
	supplierIdTextField.weightx = weightix;
	supplierIdTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(supplierId, supplierIdTextField);
	supplierId.setEditable(false);

	if(suppliers == null){
			supplierId.setText("");
		}
	else{
		supplierId.setText(suppliers[1]);
	}

	GridBagConstraints insuranceLabel = new GridBagConstraints();
	insuranceLabel.gridx = 0;
	insuranceLabel.gridy = 6;
	insuranceLabel.anchor =  GridBagConstraints.EAST;
	insuranceLabel.weightx = weightix;
	insuranceLabel.insets = new Insets(8, 15, 20, 0);
    detailPanel.add(new JLabel("Insured By:"), insuranceLabel);

	GridBagConstraints insuranceTextField = new GridBagConstraints();
	insurance = new JTextField(textFieldColunm);
	insuranceTextField.gridx = 1;
	insuranceTextField.gridy = 6;
	insuranceTextField.ipady = ipadiy;
	insuranceTextField.ipadx = ipadix;
	insuranceTextField.insets = new Insets(8, 0, 20, 0);
	insuranceTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(insurance, insuranceTextField);
	insurance.setEditable(false);
	String[] insurances = String.valueOf(details.get(9)).split("\\.");
	if(insurances == null){
			insurance.setText("");
		}
	else{
		insurance.setText(insurances[0]);
	}
	
	GridBagConstraints insuranceIdLabel = new GridBagConstraints();
	insuranceIdLabel.gridx = 2;
	insuranceIdLabel.gridy = 6;
	insuranceIdLabel.anchor =  GridBagConstraints.EAST;
	insuranceIdLabel.weightx = weightix;
	insuranceIdLabel.insets = new Insets(8, 0, 20, 0);
    detailPanel.add(new JLabel("ID:"), insuranceIdLabel);

	GridBagConstraints  insuranceIdTextField = new GridBagConstraints();
	insuranceId = new JTextField(textFieldColunm);
	insuranceIdTextField.gridx = 3;
	insuranceIdTextField.gridy = 6;
	insuranceIdTextField.ipady = ipadiy;
	insuranceIdTextField.ipadx = ipadix;
	insuranceIdTextField.insets = new Insets(8, 0, 20, 0);
	insuranceIdTextField.weightx = weightix;
	insuranceIdTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(insuranceId,  insuranceIdTextField);
	insuranceId.setEditable(false);
	
	if(insurances == null){
			insuranceId.setText("");
		}
	else{
		insuranceId.setText(insurances[1]);
	}
			
	GridBagConstraints aquisitionLabel = new GridBagConstraints();
	aquisitionLabel.gridx = 0;
	aquisitionLabel.gridy = 7;
	aquisitionLabel.anchor =  GridBagConstraints.EAST;
	aquisitionLabel.weightx = weightix;
	aquisitionLabel.insets = new Insets(8, 0, 20, 0);
    detailPanel.add(new JLabel("Aquisition Date:"), aquisitionLabel);

	GridBagConstraints aquisitionTextField = new GridBagConstraints();
	aquisition = new JTextField(textFieldColunm);
	aquisitionTextField.gridx = 1;
	aquisitionTextField.gridy = 7;
	aquisitionTextField.ipady = ipadiy;
	aquisitionTextField.ipadx = ipadix;
	aquisitionTextField.insets = new Insets(8, 0, 20, 0);
	aquisitionTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(aquisition, aquisitionTextField);
	aquisition.setEditable(false);


	if(empty.equals(String.valueOf(details.get(10)))){
			aquisition.setText("");
		}
	else{
		aquisition.setText(ul.ukFormat((java.sql.Date)details.get(10)));
	}
	
	
	GridBagConstraints lifeLabel = new GridBagConstraints();
	lifeLabel.gridx = 2;
	lifeLabel.gridy = 7;
	lifeLabel.anchor =  GridBagConstraints.EAST;
	lifeLabel.weightx = weightix;
	lifeLabel.insets = new Insets(8, 0, 20, 0);
    detailPanel.add(new JLabel("Life Span:"), lifeLabel);

	GridBagConstraints lifeTextField = new GridBagConstraints();
	life = new JTextField(textFieldColunm);
	lifeTextField.gridx = 3;
	lifeTextField.gridy = 7;
	lifeTextField.ipady = ipadiy;
	lifeTextField.ipadx = ipadix;
	lifeTextField.insets = new Insets(8, 0, 20, 0);
	lifeTextField.weightx = weightix;
	lifeTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(life, lifeTextField);
	life.setEditable(false);

	if(empty.equals(String.valueOf(details.get(11)))){
			life.setText("");
		}
	else{
		life.setText(String.valueOf(details.get(11)));
	}
	
	GridBagConstraints residualLabel = new GridBagConstraints();
	residualLabel.gridx = 0;
	residualLabel.gridy = 8;
	residualLabel.anchor =  GridBagConstraints.EAST;
	residualLabel.weightx = weightix;
	residualLabel.insets = new Insets(8, 0, 20, 0);
    detailPanel.add(new JLabel("Residual:"), residualLabel);
	
	GridBagConstraints residualTextField = new GridBagConstraints();
	residual = new JTextField(textFieldColunm);
	residualTextField.gridx = 1;
	residualTextField.gridy = 8;
	residualTextField.ipady = ipadiy;
	residualTextField.ipadx = ipadix;
	residualTextField.insets = new Insets(8, 0, 20, 0);
	residualTextField.weightx = weightix;
	residualTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(residual, residualTextField);
	residual.setEditable(false);
	
	if(empty.equals(String.valueOf(details.get(12)))){
			residual.setText("");
		}
	else{
		residual.setText(String.valueOf(details.get(12)));
	}
	
	GridBagConstraints methodLabel = new GridBagConstraints();
	methodLabel.gridx = 0;
	methodLabel.gridy = 9;
	methodLabel.anchor =  GridBagConstraints.EAST;
	methodLabel.weightx = weightix;
	methodLabel.insets = new Insets(8, 0, 20, 0);
    detailPanel.add(new JLabel("Dep Method:"), methodLabel);

	GridBagConstraints methodTextField = new GridBagConstraints();
	method = new JTextField(textFieldColunm);
	methodTextField.gridx = 1;
	methodTextField.gridy = 9;
	methodTextField.ipady = ipadiy;
	methodTextField.ipadx = ipadix;
	methodTextField.insets = new Insets(8, 0, 20, 0);
	methodTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(method, methodTextField);
	method.setEditable(false);

	if(empty.equals(String.valueOf(details.get(13)))){
			method.setText("");
		}
	else{
		method.setText(String.valueOf(details.get(13)));
	}
	
	GridBagConstraints rateLabel = new GridBagConstraints();
	rateLabel.gridx = 2;
	rateLabel.gridy = 9;
	rateLabel.anchor =  GridBagConstraints.EAST;
	rateLabel.weightx = weightix;
	rateLabel.insets = new Insets(8, 0, 20, 0);
    detailPanel.add(new JLabel("Dep Rate:"), rateLabel);

	GridBagConstraints rateTextField = new GridBagConstraints();
	rate = new JTextField(textFieldColunm);
	rateTextField.gridx = 3;
	rateTextField.gridy = 9;
	rateTextField.ipady = ipadiy;
	rateTextField.ipadx = ipadix;
	rateTextField.insets = new Insets(8, 0, 20, 0);
	rateTextField.weightx = weightix;
	rateTextField.anchor =  GridBagConstraints.WEST;
	detailPanel.add(rate, rateTextField);
	rate.setEditable(false);

	if(empty.equals(String.valueOf(details.get(14)))){
			rate.setText("");
		}
	else{
		rate.setText(String.valueOf(details.get(14)));
	}

	GridBagConstraints editPay = new GridBagConstraints();
	editPayment = new JButton("EDIT");
	editPay.gridx = 1;
	editPay.gridy = 0;
	ledgerPanel.add(editPayment, editPay);
	GridBagConstraints refreshPay = new GridBagConstraints();
	refresh = new JButton("REFRESH");
	refreshPay.gridx = 2;
	refreshPay.gridy = 0;
	ledgerPanel.add(refresh, refreshPay);
	GridBagConstraints exitPay = new GridBagConstraints();
	exit = new JButton("EXIT");
	exitPay.gridx = 3;
	exitPay.gridy = 0;
	ledgerPanel.add(exit, exitPay);

	mainPanel.add("North", detailPanel);
	mainPanel.add("South", ledgerPanel);
	mainPanel.setBorder(new Partition(20,"Ledger"));
	con.add(mainPanel);
	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(650, 600);
	setVisible(true);   
	setResizable(false);
	editPayment.addActionListener(this);
	refresh.addActionListener(this);   
	exit.addActionListener(this);       
    }

    public void actionPerformed( ActionEvent e) {  


	if (e.getSource().equals(editPayment)){
			new EditAsset("Update Asset", assetsId);
	  }
	 if (e.getSource().equals(refresh)){
		dispose();
	    new FixedAssetDetails("Fixed Asset Details", assetsId);
	  }
	 if (e.getSource().equals(exit)){
		dispose();
	  }
    }
    
}