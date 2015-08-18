
package scr.protool.client.utilities;
import java.util.*;
import java.text.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.naming.*;
import java.awt.*;
import java.awt.event.*;
import scr.protool.client.miscelleneous.BeanStud;


public class AccountsUtilities{
	private BeanStud bean;
	private Utilities ul = new Utilities();
	String arrayclass [] = {"REVENUE", "PROJECT COSTS", "OPERATING EXPENSES"};
	String arraycode [] = {"40010","50010", "60010"};
	private final int initialRow = 27;
    
    public AccountsUtilities(){
		bean = new BeanStud();
	}
	
	public Integer initialAccountsCode(String classification){//String classification
		for(int i = 0; i < arrayclass.length; i++){
			if(classification.equals(arrayclass[i])){
				return new Integer(arraycode[i]);
			}
		}
		return null;		
	}
	
	public int getGenericId(byte[] entityId){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(entityId);
            ObjectInputStream ois = new ObjectInputStream(input);
			java.util.List<Integer> ls = (java.util.List<Integer>)ois.readObject();
			if(ls.size() > 0){
				return ls.get(ls.size()-1).intValue()+1;
			}
			return 50000;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public int readAccountsDetails(byte[] object){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(object);
            ObjectInputStream ois = new ObjectInputStream(input);
				return ((Integer)ois.readObject()).intValue();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
		
	public Integer getAccountsId(byte[] entityId){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(entityId);
            ObjectInputStream ois = new ObjectInputStream(input);
			java.util.List<Integer> ls = (java.util.List<Integer>)ois.readObject();
			//return ls.get(ls.size()-1).intValue()+10;
			if(ls.size() > 0){
				return ls.get(ls.size()-1);
			}
			return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}

	public Object [] getGroupById(byte[] entityId){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(entityId);
            ObjectInputStream ois = new ObjectInputStream(input);
			java.util.List<Integer> ls = (java.util.List<Integer>)ois.readObject();
			//return ls.get(ls.size()-1).intValue()+10;
			if(ls.size() > 0){
				return  ls.toArray();
			}
			return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}

	public Object [] getGroupByName(byte[] entityId){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(entityId);
            ObjectInputStream ois = new ObjectInputStream(input);
			java.util.List<String> ls = (java.util.List<String>)ois.readObject();
			//return ls.get(ls.size()-1).intValue()+10;
			if(ls.size() > 0){
				return ls.toArray();
			}
			return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}


	public void loadData(DefaultTableModel tableModel){
		Object toIncrease[] = new Object[1];
		int row = 0;
		Vector<Vector<Object>> temp = ul.readVectorProject(bean.connect().getAccountsCodeDetails());
		Enumeration<Vector<Object>> enumerate = temp.elements();
		while(enumerate.hasMoreElements()){
			Vector<Object> datas = enumerate.nextElement();
			for(int c = 0; c < datas.size(); c++){
				if(datas.elementAt(c) instanceof String){
					tableModel.setValueAt((String)datas.get(c), row, c);
					}
				else if(datas.elementAt(c) instanceof Integer){
					tableModel.setValueAt((Integer)datas.get(c), row, c);
				}
				else if(datas.elementAt(c) instanceof java.sql.Date){
					tableModel.setValueAt(ul.ukFormat((java.sql.Date)datas.get(c)), row, c);
				}
				if(c > 1){
					 if(datas.elementAt(c) instanceof String && ((String)datas.get(c)).equals("YES")){

						tableModel.setValueAt(new Boolean(true), row, c);
					//	getColumnClass(c);
					//	tableModel.getColumnClass(c);
					}
					else if(datas.elementAt(c) instanceof String && ((String)datas.get(c)).equals("")){

						tableModel.setValueAt(new Boolean(true), row, c);
					//	getColumnClass(c);
						//tableModel.getColumnClass(c);
					}
					else if(datas.elementAt(c) instanceof String && ((String)datas.get(c)).equals("NO")){
						//getColumnClass(c);
						tableModel.setValueAt(new Boolean(false), row, c);
						
					//	tableModel.getColumnClass(c);
					}
				}
				
			}
			row++;
				if(row >= initialRow)
					tableModel.addRow(toIncrease);
				while(row >= initialRow && tableModel.getRowCount() > temp.size()){
					tableModel.removeRow(tableModel.getRowCount()-1);
				}
				
			}
	
	}
	

}