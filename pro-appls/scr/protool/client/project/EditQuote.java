
package scr.protool.client.project;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
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
import scr.protool.client.miscelleneous.QuotePdf;
/**

*
*

*
* @author <a href="mailto:"></a>
* @version 1.0
*/

public class EditQuote extends  JFrame implements MouseListener , ActionListener, AdjustmentListener{

    Container con = getContentPane();
    private JPanel headerpanel;
    private JPanel contentpanel;
	private JPanel footerpanel;
    private JPanel mainpanel;
	private JPanel scrollAppearPanel;
	private JPanel scrollDisAppearPanel;
	private JPanel headerPanels;//might be needed
    private JLabel total;
	private JLabel add;
	private JPanel buttomGridLayout;
    private JPanel buttomFlowLayout;
	private JTextField totalLabels;	
	private JButton update;
	private JButton dispose_exit;
	private final int ipadiy = 2;
    private final int ipadix = 140;
	private final int ipadiyb = 2;
    private final int ipadixb = 4;
    private final double weightix = 2.0;
	private final double weightiy = 1.0;
	private final int initialRow = 22;
	private final int textFieldColunm = 1;
    private JScrollPane ledgerViewScroll; 
	private Utilities ul = new Utilities();
	private JSpinner validDate;
	private SpinnerHelper sh;
	private PaymentInUtilities pu = new PaymentInUtilities();
	private ClassLoader cl = this.getClass().getClassLoader();
	private ArrayList<JTextField> descript = new ArrayList<JTextField>();
	private ArrayList<JTextField> quant = new ArrayList<JTextField>();
	private ArrayList<JTextField> uniprice = new ArrayList<JTextField>();
	private ArrayList<JLabel> label = new ArrayList<JLabel>();
	private ArrayList<ArrayList<String>> outerlist = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> arrayListOfQuote ;
	private ArrayList<Object> quoteValues;
	private  int click;	
	private int index = 0;
	private int quoteId;
	private BeanStud bean;

    public EditQuote(String title, int quoteId) {
	super(title);

	this.quoteId = quoteId;
	sh = new SpinnerHelper();
	bean = new BeanStud();
	quoteValues = ul.getArrayList(bean.connect().quoteValues(quoteId));
	arrayListOfQuote = (ArrayList<ArrayList<String>>)quoteValues.get(1);

	headerpanel = new JPanel();
	contentpanel = new JPanel();
	footerpanel = new JPanel();
	mainpanel = new JPanel();
	scrollDisAppearPanel = new JPanel();
	scrollAppearPanel = new JPanel();
	headerPanels = new JPanel();
	//ledgerButtonPanel = new JPanel();
	buttomFlowLayout = new JPanel();
	buttomGridLayout = new JPanel();

	/*contentpanel.setLayout(new GridBagLayout());
	headerpanel.setLayout(new GridBagLayout());
	buttomFlowLayout.setLayout(new FlowLayout());
	buttomGridLayout.setLayout(new GridBagLayout());
	footerpanel.setLayout(new GridBagLayout());
	mainpanel.setLayout(new BorderLayout());*/
	
	contentpanel.setLayout(new GridBagLayout());
	scrollDisAppearPanel.setLayout(new GridBagLayout());
	scrollAppearPanel.setLayout(new GridBagLayout());
	GridLayout headerGrid = new GridLayout(1,2);
	headerGrid.setHgap(65);
	headerpanel.setLayout(headerGrid);
	buttomFlowLayout.setLayout(new FlowLayout());
	buttomGridLayout.setLayout(new GridBagLayout());
	headerPanels.setLayout(new GridBagLayout());
	headerPanels.setPreferredSize(new Dimension(480, 10));
	footerpanel.setLayout(new GridBagLayout());
	mainpanel.setLayout(new BorderLayout());

	GridBagConstraints descriLabel = new GridBagConstraints();
	descriLabel.gridx = 0;
	descriLabel.gridy = 0;
	descriLabel.weightx = weightix;
	descriLabel.anchor =  GridBagConstraints.LINE_START;
	descriLabel.insets = new Insets(0, 48, 7, 0);
    headerPanels.add(new JLabel("Decription:"), descriLabel);
	
    headerpanel.add(new JLabel("Quantity:"));
    headerpanel.add(new JLabel("Unit Price:"));
	
	GridBagConstraints qtypriLabel = new GridBagConstraints();
	qtypriLabel.gridx = 1;
	qtypriLabel.gridy = 0;
	qtypriLabel.insets = new Insets(0, 0, 0, 90);
	headerPanels.add(headerpanel, qtypriLabel);
	
	/*GridBagConstraints descriLabel = new GridBagConstraints();
	descriLabel.gridx = 0;
	descriLabel.gridy = 0;
	descriLabel.gridwidth = 2;
	descriLabel.weightx = weightix;
	descriLabel.anchor =  GridBagConstraints.LINE_START;
	descriLabel.insets = new Insets(0, 48, 0, 0);
    headerpanel.add(new JLabel("Decription:"), descriLabel);
	
	GridBagConstraints qtyLabel = new GridBagConstraints();
	qtyLabel.gridx = 1;
	qtyLabel.gridy = 0;
	qtyLabel.insets = new Insets(0, 0, 0, -283);
    headerpanel.add(new JLabel("Quantity:"), qtyLabel);
	
	GridBagConstraints priLabel = new GridBagConstraints();
	priLabel.gridx = 2;
	priLabel.gridy = 0;
	priLabel.anchor =  GridBagConstraints.LINE_START;
	priLabel.weighty = weightiy;
	priLabel.insets = new Insets(0, 0, 0, 89);
    headerpanel.add(new JLabel("Unit Price:"), priLabel);*/
	
	if(arrayListOfQuote.isEmpty()){
		click  = 1;
		makeTextField(1);
		
	}
	else{
		click = arrayListOfQuote.size()-1;
		for(int i = 0; i < arrayListOfQuote.size(); i++){

			if(i > 0 && i < arrayListOfQuote.size()){
				contentpanel.remove(add);
			}
		
			makeTextField(i);
		}
	}
	/*GridBagConstraints date1Label = new GridBagConstraints();
	date1Label.gridx = 0;
	date1Label.gridy = 0;
	date1Label.weighty = 0.1;
	//	date1Label.insets = new Insets(0, 0, 15, 0);
    date1Label.anchor =  GridBagConstraints.EAST;
	footerpanel.add(new JLabel("Valid Date:"), date1Label);

	GridBagConstraints date1TextField = new GridBagConstraints();
	//date = new JTextField();
	validDate = new JSpinner();
	date1TextField.gridx = 1;
	date1TextField.gridy = 0;
	date1TextField.ipady = 2;
	date1TextField.insets = new Insets(0, 0, 0, 30);
	date1TextField.ipadx = -20;
	date1TextField.anchor =  GridBagConstraints.WEST;
	//footerpanel.add(sh.getGenericDate(validDate), date1TextField);
	footerpanel.add(sh.getGenericDateEdit(validDate, ul.usDateString((String)quoteValues.get(0))), date1TextField);
	
	update = new JButton("Update");
	GridBagConstraints submitquote = new GridBagConstraints();
	submitquote.gridx = 2;
	submitquote.gridy = 0;
	submitquote.insets = new Insets(0, 0, 0, 30);
	footerpanel.add(update, submitquote);
	
	GridBagConstraints totalLabel = new GridBagConstraints();
	totalLabel.gridx = 3;
	totalLabel.gridy = 0;
    footerpanel.add(new JLabel("Total:"), totalLabel);
	
	GridBagConstraints priceLabel = new GridBagConstraints();
	totalLabels = new JTextField(textFieldColunm);
	priceLabel.gridx = 4;
	priceLabel.gridy = 0;
	priceLabel.ipadx = 60;
	priceLabel.insets = new Insets(0, 0, 0, 30);
    footerpanel.add(totalLabels , priceLabel);
	totalLabels.setText(new String("£0.00"));
	totalLabels.setEditable(false);
	
	dispose_exit = new JButton("Exit");
	GridBagConstraints exitquote = new GridBagConstraints();
	exitquote.gridx = 5;
	exitquote.gridy = 0;
	//submitquote.insets = new Insets(0, 0, 0, 60);
	footerpanel.add(dispose_exit, exitquote);*/
	GridBagConstraints totalLabel = new GridBagConstraints();
	totalLabel.gridx = 0;
	totalLabel.gridy = 0;
	totalLabel.insets = new Insets(0, 0, 0, 10);
    footerpanel.add(new JLabel("Total:"), totalLabel);
	
	GridBagConstraints priceLabel = new GridBagConstraints();
	totalLabels = new JTextField(textFieldColunm);
	priceLabel.gridx = 1;
	priceLabel.gridy = 0;
	priceLabel.ipadx = 60;
	priceLabel.insets = new Insets(0, 0, 0, 11);
    footerpanel.add(totalLabels , priceLabel);
	totalLabels.setText(new String("£0.00"));
	totalLabels.setEditable(false);
	
	update = new JButton("Save");
	GridBagConstraints submitquote = new GridBagConstraints();
	submitquote.gridx = 2;
	submitquote.gridy = 0;
	submitquote.insets = new Insets(0, 0, 0, 0);
	footerpanel.add(update, submitquote);
	
	dispose_exit = new JButton("Exit");
	GridBagConstraints exitquote = new GridBagConstraints();
	exitquote.gridx = 3;
	exitquote.gridy = 0;
	//submitquote.insets = new Insets(0, 0, 0, 60);
	footerpanel.add(dispose_exit, exitquote);
	buttomFlowLayout.add(footerpanel);
	
	GridBagConstraints buttomGrid = new GridBagConstraints();
	buttomGrid.gridx = 0;
	buttomGrid.gridy = 0;
	buttomGrid.insets = new Insets(0, 0, 0, -290);
	buttomGridLayout.add(buttomFlowLayout, buttomGrid);
	
	
	mainpanel.add(contentpanel, BorderLayout.NORTH);
	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED; 
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	ledgerViewScroll = new JScrollPane(mainpanel, v,h);
	ledgerViewScroll.setColumnHeaderView(headerPanels);
	ledgerViewScroll.setBorder(new Partition(20,"Ledger"));
	con.add(ledgerViewScroll);
	con.add(buttomGridLayout, "South");
	pack();
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(650, 600);
	setVisible(true); 
	setResizable(false);
  	setIconImage(ul.mainImage("images/src/gui_icon.png"));
	dispose_exit.addActionListener(this);
	update.addActionListener(this);
	ledgerViewScroll.getVerticalScrollBar().addAdjustmentListener(this); 
    }
	
	public void makeTextField(int row){
		
		GridBagConstraints typeTextField1 = new GridBagConstraints();
		JTextField description = new JTextField(textFieldColunm);
		typeTextField1.gridx = 0;
		typeTextField1.gridy = row;
		typeTextField1.ipady = ipadiy;
		typeTextField1.ipadx = 250;
		typeTextField1.insets = new Insets(0, 0, 5, 40);
		typeTextField1.anchor =  GridBagConstraints.WEST;
		contentpanel.add(description, typeTextField1);
		if(row < arrayListOfQuote.size()){
			description.setText(arrayListOfQuote.get(row).get(0));
		}
		descript.add(description);
	
		GridBagConstraints typeTextField2 = new GridBagConstraints();
		JTextField quantity = new JTextField(textFieldColunm);
		typeTextField2.gridx = 1;
		typeTextField2.gridy = row;
		typeTextField2.ipady = ipadiy;
		typeTextField2.ipadx = 60;
		typeTextField2.insets = new Insets(0, 0, 5, 40);
		typeTextField2.anchor =  GridBagConstraints.WEST;
		contentpanel.add(quantity, typeTextField2);
		if(row < arrayListOfQuote.size()){
			quantity.setText(arrayListOfQuote.get(row).get(1));
		}
		quant.add(quantity);
			
		GridBagConstraints typeTextField3 = new GridBagConstraints();
		JTextField unitprice = new JTextField(textFieldColunm);
		typeTextField3.gridx = 2;
		typeTextField3.gridy = row;
		typeTextField3.ipady = ipadiy;
		typeTextField3.ipadx = 60;
		typeTextField3.insets = new Insets(0, 0, 5, 0);
		typeTextField3.anchor =  GridBagConstraints.WEST;
		contentpanel.add(unitprice, typeTextField3);
		if(row < arrayListOfQuote.size()){
			unitprice.setText(arrayListOfQuote.get(row).get(2));
		}
		uniprice.add(unitprice);
		
		GridBagConstraints addLabel = new GridBagConstraints();
		add = new JLabel(ul.iconImage("images/src/add.png"));
		addLabel.gridx = 3;
		addLabel.gridy = row;
		//addLabel.weightx = 1.0;
		addLabel.anchor =  GridBagConstraints.NORTHEAST;
		addLabel.insets = new Insets(3, 0, 0, 0);
		contentpanel.add(add, addLabel);
		add.addMouseListener(this); 
		
		GridBagConstraints removeLabel = new GridBagConstraints();
	    JLabel remove = new JLabel(ul.iconImage("images/src/remove.png"));
		removeLabel.gridx = 3;
		removeLabel.gridy = row-1;
		removeLabel.anchor =  GridBagConstraints.NORTHEAST;
		removeLabel.insets = new Insets(3, 0, 0, 0);
		remove.addMouseListener(this);
		if(row > 1 && arrayListOfQuote.isEmpty()){
			contentpanel.add(remove, removeLabel);
			label.add(remove);
		
		}
		else if(row >= 1 && !arrayListOfQuote.isEmpty()){
			contentpanel.add(remove, removeLabel);
			label.add(remove);
		}
		
	}
	
	public BigDecimal Results(){
		BigDecimal total = BigDecimal.ZERO;
		if(!outerlist.isEmpty()){//test polliforation of outerlist before doing using Results() method
			outerlist.clear(); 
		}
	
		for(int i = 0; i < uniprice.size(); i++){
			ArrayList<String> innerlist = new ArrayList<String>();
			BigDecimal subtotal = BigDecimal.ZERO;
			BigDecimal price =  BigDecimal.ZERO;
			BigDecimal qty =  BigDecimal.ZERO;
			if(!uniprice.get(i).getText().equals("") || !quant.get(i).getText().equals("")){
				innerlist.add(descript.get(i).getText());
				qty = new BigDecimal(uniprice.get(i).getText());
				innerlist.add(uniprice.get(i).getText());
				price = new BigDecimal(quant.get(i).getText());
				innerlist.add(quant.get(i).getText());
				outerlist.add(innerlist);
			}
			subtotal = qty.multiply(price);
			total = total.add(subtotal);
			
		}

		return total;
		
	}
	
	
	/*public void actionPerformed( ActionEvent e) { 
	
		if (e.getSource().equals(submit)){
			Integer proId = new Integer(projectId.getText());
		
			String string = NumberFormat.getCurrencyInstance(locale).format(Results());
			quote.setText(string);
			//generate pdf file for quote here
			dispose();	
		}
		if (e.getSource().equals(dispose_exit)){
			quote.setText("");
			dispose();	
		}
	
	
	}*/
	
	public void getRideInner(ActionEvent evt){
	 
		WindowEvent wev  = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);                
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
	
	public void mouseClicked(MouseEvent e) {

		if(e.getSource().equals(add)){
			contentpanel.remove(add);
			click+=e.getClickCount();
			makeTextField(click);
			contentpanel.revalidate();  
			repaint();
			validate();

			totalLabels.setText(ul.getStringInCurrency(Results()));
	
			
		}
		else{
			for(int i = 0; i < label.size(); i++){
				if(e.getSource().equals(label.get(i))){
					contentpanel.remove(descript.get(i));
					descript.remove(i);
					contentpanel.remove(quant.get(i));
					quant.remove(i);
					contentpanel.remove(uniprice.get(i));
					uniprice.remove(i);
					contentpanel.remove(label.get(i));
					label.remove(i);
					contentpanel.revalidate();  
					repaint();
					validate();
				}
			}
			totalLabels.setText(ul.getStringInCurrency(Results()));
			//quote.setText(ul.getStringInCurrency(Results()));
		}

	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e){} 
	public void mouseReleased(MouseEvent e) {}
	public void adjustmentValueChanged(AdjustmentEvent e) {

	  if(ledgerViewScroll.getVerticalScrollBar().isVisible()){
		GridBagConstraints descriLabel = new GridBagConstraints();
		descriLabel.gridx = 0;
		descriLabel.gridy = 0;
		descriLabel.weightx = weightix;
		descriLabel.anchor =  GridBagConstraints.LINE_START;
		descriLabel.insets = new Insets(0, 40, 7, 0);
		scrollAppearPanel.add(new JLabel("Decription:"), descriLabel);
		
		GridBagConstraints qtypriLabel = new GridBagConstraints();
		qtypriLabel.gridx = 1;
		qtypriLabel.gridy = 0;
		qtypriLabel.insets = new Insets(0, 0, 0, 80);
		scrollAppearPanel.add(headerpanel, qtypriLabel);
		ledgerViewScroll.setColumnHeaderView(scrollAppearPanel);
	  }
	  
	  else{
		GridBagConstraints descriLabel = new GridBagConstraints();
		descriLabel.gridx = 0;
		descriLabel.gridy = 0;
		descriLabel.weightx = weightix;
		descriLabel.anchor =  GridBagConstraints.LINE_START;
		descriLabel.insets = new Insets(0, 48, 7, 0);
		scrollDisAppearPanel.add(new JLabel("Decription:"), descriLabel);
		
		GridBagConstraints qtypriLabel = new GridBagConstraints();
		qtypriLabel.gridx = 1;
		qtypriLabel.gridy = 0;
		qtypriLabel.insets = new Insets(0, 0, 0, 90);
		scrollDisAppearPanel.add(headerpanel, qtypriLabel);
		ledgerViewScroll.setColumnHeaderView(scrollDisAppearPanel);
	  }
	  
  }



    public void actionPerformed( ActionEvent e) {  

	if (e.getSource().equals(dispose_exit)){
	
	    dispose();	
	}

	if (e.getSource().equals(update)){
		Results();
		String[] genericInfo = {String.valueOf(quoteId), ul.usDateString(sh.dateString(validDate))};
		bean.connect().updateQuotes(ul.writeStringArray(genericInfo), ul.writeListOfArrayListStr(outerlist)); 
		dispose();
		//new QuotePdf(projectQuote);
		
	}

    }

}