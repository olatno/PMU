package scr.protool.client.miscelleneous;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import javax.naming.*;
import java.io.*;
import scr.protool.client.utilities.*;


public class TableDataModel extends AbstractTableModel{
	private BeanStud bean;
	private Utilities ul = new Utilities();
	private PaymentInUtilities pu = new PaymentInUtilities();
	private Object[][] data = null; 
	//private Vector<Vector<Object>> temp = null;
	private ArrayList<ArrayList<Object>> temp = null;
	private String[] columnNames = {"Accounts Code","Accounts Description","Classification","Date Created","Active","Manual Posting"};

	public TableDataModel(){
	
		bean = new BeanStud();
		//temp = ul.readVectorProject(bean.connect().getAccountsCodeDetails());
		//data = new Object[temp.size()][4];
		//Enumeration<Vector<Object>> enumerate = temp.elements();	
		temp = pu.getArrayListOfArrayList(bean.connect().getAccountsCodeDetails());
		data = new Object[temp.size()][5];
		for(int i = 0; i < data.length; i++){
			data[i] = ((ArrayList<Object>) temp.get(i)).toArray();    
		}

	}
	//public TableDataModel(){}
	    public int getColumnCount() {
           // return col.size();
		   return columnNames.length;
        }

        public String getColumnName(int c) {
            //return col.get(c);
			return columnNames[c];

        }


        public int getRowCount() {
            return data.length;
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }


   /*
    * JTable uses this method to determine the default renderer/
    * editor for each cell.  If we didn't implement this method,
    * then the last column would contain text ("true"/"false"),
    * rather than a check box.
    */
   public Class<? extends Object> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
     }
}