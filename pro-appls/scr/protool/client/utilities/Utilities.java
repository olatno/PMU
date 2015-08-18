
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
import java.math.BigDecimal;
import java.net.URL;
import java.net.URISyntaxException;
import scr.protool.client.miscelleneous.BeanStud;


public class Utilities{
	private BeanStud bean;
	private SpinnerHelper sh1;
	private SpinnerHelper sh2; 
	private ClassLoader cl;
	private final int initialRow = 27;
	Locale locale = Locale.UK;
 
	//private DefaultTableModel tableModel; 
    
    public Utilities(){
		bean = new BeanStud();
		sh1 = new SpinnerHelper();
		sh2 = new SpinnerHelper();
		cl = this.getClass().getClassLoader();
		//pu = new PaymentInUtilities();
		
		//tableModel = new DefaultTableModel();
	
	}
		public java.sql.Date usDate(String datestr){
			String[] datestrs = datestr.split("-");
			String date = datestrs[2]+"-"+datestrs[1]+"-"+datestrs[0];
			return java.sql.Date.valueOf(date);
		}
		
		public String usDateString(String datestr){
			String[] datestrs = datestr.split("-");
			String date = datestrs[2]+"-"+datestrs[1]+"-"+datestrs[0];
			return date;
		}
		
		public String ukFormat(java.sql.Date usDate){
			String datestr = usDate.toString();
			String[] datestrs = datestr.split("-");
			String date = datestrs[2]+"-"+datestrs[1]+"-"+datestrs[0];
			return date;
		}
		
	 public byte [] writeVector(Vector<Object> d){

		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(d);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	public byte [] writeStringArray(String[] str){

		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(str);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	public String readString(byte [] str){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(str);
            ObjectInputStream ois = new ObjectInputStream(input);	
			return (String)ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	}

	
	public InputStream fileResources(String filePath){
		 return  cl.getResourceAsStream(filePath);
	}
	
	public Image mainImage(String imageName){
		URL imagePro = cl.getResource(imageName); 
		return (imagePro != null ? new ImageIcon(imagePro).getImage() : null);
	}
	
	public ImageIcon iconImage(String imageName){
		URL imagePro = cl.getResource(imageName); 
		return (imagePro != null ? new ImageIcon(imagePro) : null);
	}
	
	public BigDecimal getBigDecimals(byte[] decimal){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(decimal);
            ObjectInputStream ois = new ObjectInputStream(input);
			return ((BigDecimal)ois.readObject());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	/*
		**method discode arraylist of arraylist byte
		**takes arraylist of arraylist which of object as argument
	*/
	public byte [] writeVectorOfVector(Vector<Vector<String>> d){//getProjectData

		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(d);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	

	public byte [] writeArrayList(java.util.List<Object> d){

		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(d);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	public byte [] writeArrayListStr(java.util.List<String> d){

		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(d);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	public byte [] writeListOfArrayListStr(ArrayList<ArrayList<String>> d){

		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(d);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	public byte [] writeInteger(Integer projectid){

		try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(buffer);
            oos.writeObject(projectid);
            oos.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	
	public int getGenericId(byte[] entityId, int initial){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(entityId);
            ObjectInputStream ois = new ObjectInputStream(input);
			java.util.List<Integer> ls = (java.util.List<Integer>)ois.readObject();
			if(ls.size() > 0){
			return ls.get(ls.size()-1).intValue()+1;
			}
			return initial;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public Vector<Vector<Object>> readVectorProject(byte[] vectorDate){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(vectorDate);
            ObjectInputStream ois = new ObjectInputStream(input);	
			return (Vector<Vector<Object>>)ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public Vector<Vector<Object>> getProjectInMoney(byte[] listAmount){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(listAmount);
            ObjectInputStream ois = new ObjectInputStream(input);	
			return (Vector<Vector<Object>>)ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
		public ArrayList<Object> getProjectForEditing(byte[] listProjectDate){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(listProjectDate);
            ObjectInputStream ois = new ObjectInputStream(input);	
			return (ArrayList<Object>)ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public ArrayList<Object> getArrayList(byte[] byteList){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(byteList);
            ObjectInputStream ois = new ObjectInputStream(input);	
			return (ArrayList<Object>)ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public ArrayList<String> getArrayListStr(byte[] byteList){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(byteList);
            ObjectInputStream ois = new ObjectInputStream(input);	
			return (ArrayList<String>)ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public String getStringInCurrency(BigDecimal localcurrency){
	
		return NumberFormat.getCurrencyInstance(locale).format(localcurrency);
	}
	
	public void loadDate(DefaultTableModel tableModel, JTextField amount_from,  JTextField amount_to){
		//List<ArrayList> list = new ArrayList<ArrayList>();
		 BigDecimal from = new  BigDecimal(amount_from.getText());
		 BigDecimal to = new  BigDecimal(amount_to.getText());
		Object toIncrease[] = new Object[1];
		int row = 0;
		Vector<Vector<Object>> temp = getProjectInMoney(bean.connect().geneProjectAmount(from, to));
		Iterator<Vector<Object>> list = temp.iterator();
		while(list.hasNext()){
			Vector<Object> data = list.next();
			for(int c = 0; c < data.size(); c++){
				  
				if(data.elementAt(c) instanceof String){
					tableModel.setValueAt((String)data.get(c), row, c);
					}
				else if(data.elementAt(c) instanceof Integer){

					tableModel.setValueAt((Integer)data.get(c), row, c);

				   }
				else if(data.elementAt(c) instanceof java.sql.Date){
					tableModel.setValueAt(ukFormat((java.sql.Date)data.get(c)), row, c);
					}
				else if(data.elementAt(c) instanceof  BigDecimal){
					String string = NumberFormat.getCurrencyInstance(locale).format(( BigDecimal)data.get(c));
					tableModel.setValueAt(string, row, c);
					//tableModel.setValueAt((Double)data.get(c), row, c);
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
	public void loadDate(DefaultTableModel tableModel, byte[] vectorDate ){
		Object toIncrease[] = new Object[1];
		int row = 0;
		//Vector<Vector<Object>> temp = readVectorProject(bean.connect().getProjectData());
		Vector<Vector<Object>> temp = readVectorProject(vectorDate);
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
					tableModel.setValueAt(ukFormat((java.sql.Date)datas.get(c)), row, c);
				}
				else if(datas.elementAt(c) instanceof  BigDecimal){
					//tableModel.setValueAt((Double)datas.get(c), row, c);
					String string = NumberFormat.getCurrencyInstance(locale).format(( BigDecimal)datas.get(c));
					tableModel.setValueAt(string, row, c);
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
	
	public void loadDate(DefaultTableModel tableModel, ArrayList<Object> datas , int initialRows){
		//Object toIncrease[] = new Object[1];
		//int row = 0;
		//Vector<Vector<Object>> temp = readVectorProject(bean.connect().getProjectData());
		//Vector<Vector<Object>> temp = readVectorProject(vectorDate);
		//Enumeration<Vector<Object>> enumerate = temp.elements();
	//	while(enumerate.hasMoreElements()){
		//	Vector<Object> datas = enumerate.nextElement();
			for(int c = 0; c < datas.size(); c++){
				if(datas.get(c) instanceof String){
					tableModel.setValueAt((String)datas.get(c), 0, c);
					}
				else if(datas.get(c) instanceof Integer){
					tableModel.setValueAt((Integer)datas.get(c), 0, c);
				}
				else if(datas.get(c) instanceof java.sql.Date){
					tableModel.setValueAt(ukFormat((java.sql.Date)datas.get(c)), 0, c);
				}
				else if(datas.get(c) instanceof  BigDecimal){
					//tableModel.setValueAt((Double)datas.get(c), row, c);
					String string = NumberFormat.getCurrencyInstance(locale).format(( BigDecimal)datas.get(c));
					tableModel.setValueAt(string, 0, c);
				}
				
			}
			//row++;
				//if(row >= initialRow)
				//	tableModel.addRow(toIncrease);
				while(tableModel.getRowCount() > initialRows){
					tableModel.removeRow(tableModel.getRowCount()-1);
				}
				
			//}
	
	}
	
	
	public void loadData(DefaultTableModel tableModel){
		Object toIncrease[] = new Object[1];
		int row = 0;
		Vector<Vector<Object>> temp = readVectorProject(bean.connect().getProjectData());
		//Vector<Vector<Object>> temp = readVectorProject(vectorDate);
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
					tableModel.setValueAt(ukFormat((java.sql.Date)datas.get(c)), row, c);
				}
				else if(datas.elementAt(c) instanceof  BigDecimal){
					String string = NumberFormat.getCurrencyInstance(locale).format(( BigDecimal)datas.get(c));
					tableModel.setValueAt(string, row, c);
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
	public void loadDate(DefaultTableModel tableModel, JSpinner datefrom, JSpinner dateto){
		Object toIncrease[] = new Object[1];
		int row = 0;
		JFormattedTextField textfrom = ((JSpinner.DateEditor)datefrom.getEditor()).getTextField();
		JFormattedTextField textto = ((JSpinner.DateEditor)dateto.getEditor()).getTextField();
		java.sql.Date from = usDate(textfrom.getText());
		java.sql.Date to = usDate(textto.getText());
		Vector<Vector<Object>> temp = readVectorProject(bean.connect().geneProjectDate(from, to));
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
					tableModel.setValueAt(ukFormat((java.sql.Date)datas.get(c)), row, c);
				}
				else if(datas.elementAt(c) instanceof  BigDecimal){
					String string = NumberFormat.getCurrencyInstance(locale).format((BigDecimal)datas.get(c));
					tableModel.setValueAt(string, row, c);
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

		public void findRecord(DefaultTableModel tableModel, JTextField field, int column){
			for(int i = 0; i < tableModel.getRowCount(); i++){
				String str = field.getText();
				if(str.regionMatches(true, 0,((String)tableModel.getValueAt(i,column)),0,3)){
					tableModel.moveRow(i,i,0);
				}
			}
		
		}
		
		public String noPoundString(String pound){			
			return pound.substring(1, pound.length()); 				
		}
		
		public int getTableInt(JTable table, DefaultTableModel tabModel, int column){
			return ((Integer)tabModel.getValueAt(table.getSelectedRow(), column)).intValue();
		}
		
		public String getStringValue(JTable view, DefaultTableModel dataModel,  int index){
			return (String)dataModel.getValueAt(view.getSelectedRow(), index);
		}
		

}