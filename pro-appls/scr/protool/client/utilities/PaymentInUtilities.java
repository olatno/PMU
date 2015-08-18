
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
import scr.protool.client.miscelleneous.BeanStud;
import scr.protool.client.customer.Incomingview;
import scr.protool.client.supplier.Outgoingview;
import scr.protool.client.general.GeneralExpensesView;
import scr.protool.client.fixedasset.AssetRegisterView;


public class PaymentInUtilities{
    private BeanStud bean;
	private Utilities ul = new Utilities();
	private final int initialRow = 27;
	
    public PaymentInUtilities(){
		bean = new BeanStud();
	}
	
	public Hashtable<String, Integer> getAccountCodeName(byte[] entityId){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(entityId);
            ObjectInputStream ois = new ObjectInputStream(input);
			Hashtable<String, Integer> ht = (Hashtable<String, Integer>)ois.readObject();
			if(ht.size() > 0){
				return ht;
			}
			return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
		
	public Hashtable<Integer, String> getAccountCodeId(byte[] entityId){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(entityId);
            ObjectInputStream ois = new ObjectInputStream(input);
			Hashtable<Integer, String> ht = (Hashtable<Integer, String>)ois.readObject();
			if(ht.size() > 0){
				return ht;
			}
			return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public Hashtable<Integer, Integer> getPaymentId(byte[] entityId){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(entityId);
            ObjectInputStream ois = new ObjectInputStream(input);
			Hashtable<Integer, Integer> ht = (Hashtable<Integer, Integer>)ois.readObject();
			if(ht.size() > 0){
				return ht;
			}
			return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public int getPaymentInId(byte[] entityId, int projectid){
	 Hashtable<Integer, Integer> payment = getPaymentId(bean.connect().getHashTablePaymentInId());
	 Enumeration<Integer> enu = null;
	 if(payment != null){
		enu = payment.keys();
	  }
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(entityId);
            ObjectInputStream ois = new ObjectInputStream(input);
			java.util.List<Integer> ls = (java.util.List<Integer>)ois.readObject();
			if(ls.size() > 0){
				while(enu.hasMoreElements()){
					Integer projectIdDB = enu.nextElement();
					if(projectIdDB.intValue() ==  projectid){
						return (payment.get(projectIdDB)).intValue();
					}
				}
				return ls.get(ls.size()-1).intValue()+1;
				
			}
				return 20000;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public byte [] writeArrayList(ArrayList<ArrayList<Object>> d){

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
	public String paymentInvoice(byte[] entityId){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(entityId);
            ObjectInputStream ois = new ObjectInputStream(input);
			String ls = (String)ois.readObject();
			if(ls!=null){
			return ls;
			}
			return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public Integer integerValue(byte[] entityId){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(entityId);
            ObjectInputStream ois = new ObjectInputStream(input);
			Integer ls = (Integer)ois.readObject();
			if(ls!=null){
			return ls;
			}
			return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public BigDecimal getTotalQuote(byte[] total){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(total);
            ObjectInputStream ois = new ObjectInputStream(input);
			return ((BigDecimal)ois.readObject());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public BigDecimal getBigDecimal(byte[] total){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(total);
            ObjectInputStream ois = new ObjectInputStream(input);
			return ((BigDecimal)ois.readObject());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public LinkedHashMap<String,Integer> genericHashMap(byte[] generic){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(generic);
            ObjectInputStream ois = new ObjectInputStream(input);
			return ((LinkedHashMap<String,Integer>)ois.readObject());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public ArrayList<Object> getArrayList(byte[] listProjectDate){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(listProjectDate);
            ObjectInputStream ois = new ObjectInputStream(input);	
			return (ArrayList<Object>)ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public ArrayList<BigDecimal> getArrayListBigDecimal(byte[] listBigDecimal){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(listBigDecimal);
            ObjectInputStream ois = new ObjectInputStream(input);	
			return (ArrayList<BigDecimal>)ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public ArrayList<ArrayList<Object>> getArrayListOfArrayList(byte[] values){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(values);
            ObjectInputStream ois = new ObjectInputStream(input);
			return (ArrayList<ArrayList<Object>>)ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public ArrayList<ArrayList<String>> getArrayListOfArrayListStr(byte[] values){
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(values);
            ObjectInputStream ois = new ObjectInputStream(input);
			return (ArrayList<ArrayList<String>>)ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	
	}
	
	public ArrayList<ArrayList<Object>> arrayListOfObject(Incomingview view){
	
		return getArrayListOfArrayList(bean.connect().getPaymentInDetails(view.getTableId()));
	}
	
	public ArrayList<ArrayList<Object>> arrayListOfObjects(Outgoingview view){
	
		return getArrayListOfArrayList(bean.connect().getSupplierPaymentDetails(view.getTableId(7)));
	//	return getArrayListOfArrayList(value);
	}
	
	public ArrayList<ArrayList<Object>> arrayListOfObjects(byte[] searchResult){
	
		return getArrayListOfArrayList(searchResult);
		//return null;
	}
	
	public ArrayList<Object> listOfObject(byte[] searchResult){
	
		return getArrayList(searchResult);
		//return null;
	}
	
	public ArrayList<ArrayList<Object>> arrayListOfObject(int paymentID){
	
		return getArrayListOfArrayList(bean.connect().getPaymentInDetails(paymentID));
	}
	
	public ArrayList<ArrayList<Object>> arrayListOfObjects(int paymentID){
	
		return getArrayListOfArrayList(bean.connect().getSupplierPaymentDetails(paymentID));
		//return getArrayListOfArrayList(value);
	}
	
	public ArrayList<ArrayList<Object>> subArrayListOfObjects(int paymentID){
	
		return getArrayListOfArrayList(bean.connect().getSubContractorPaymentDetails(paymentID));
		//return getArrayListOfArrayList(value);
	}
	
	public void paymentInDetails(DefaultTableModel tableModel, Incomingview view){	
		Object toIncrease[] = new Object[1];
		int row = 1;
		ArrayList<ArrayList<Object>> temp = arrayListOfObject(view);
		Iterator<ArrayList<Object>> list = temp.iterator();
		tableModel.setValueAt(view.getDate(), 0, 4);
		tableModel.setValueAt(view.getAmount(), 0, 5);
		tableModel.setValueAt(view.getTableId(), 0, 6);//tablemodel value not visible in table, use for refersh button in incoming details
		while(list.hasNext()){
			ArrayList<Object> data = list.next();

					tableModel.setValueAt(ul.ukFormat((java.sql.Date)data.get(5)), row, 0);

					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(0)), row, 1);
				
					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(2)), row, 2);
					
					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(3)), row, 3);
					
					tableModel.setValueAt((Integer)data.get(6), row, 6);

				row++;
				if(row >= tableModel.getRowCount())
					tableModel.addRow(toIncrease);
				while(row >= tableModel.getRowCount() && tableModel.getRowCount() > temp.size()){
						tableModel.removeRow(tableModel.getRowCount()-1);
				}
				tableModel.setValueAt(new String(""), row, 4);
				tableModel.setValueAt(new String(""), row, 5);
			}
			tableModel.setValueAt(new String("Balance"), row+1, 4);
			String string2 = ul.getStringInCurrency(getTotalQuote(bean.connect().getPaymentBalance(view.getTableId())));

			tableModel.setValueAt(string2, row+1, 5);
	
	}
	
	public void paymentInDetails(DefaultTableModel tableModel, int paymentID){	
		Object toIncrease[] = new Object[1];
		int row = 1;
		ArrayList<ArrayList<Object>> temp = arrayListOfObject(paymentID);
		Iterator<ArrayList<Object>> list = temp.iterator();
		tableModel.setValueAt(tableModel.getValueAt(0, 3), 0, 3);
		tableModel.setValueAt(tableModel.getValueAt(0, 4), 0, 4);
		tableModel.setValueAt(new Integer(paymentID), 0, 5);//tablemodel value not visible in table, use for refersh button in incoming details
		while(list.hasNext()){
			ArrayList<Object> data = list.next();

					tableModel.setValueAt(ul.ukFormat((java.sql.Date)data.get(2)), row, 0);

					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(4)), row, 1);
				
					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(0)), row, 2);
					
					tableModel.setValueAt((Integer)data.get(5), row, 5);

				row++;
				if(row >= tableModel.getRowCount())
					tableModel.addRow(toIncrease);
				while(row >= tableModel.getRowCount() && tableModel.getRowCount() > temp.size()){
						tableModel.removeRow(tableModel.getRowCount()-1);
				}
				tableModel.setValueAt(new String(""), row, 3);
				tableModel.setValueAt(new String(""), row, 4);
				
			}
			tableModel.setValueAt(new String("Balance"), row+1, 3);
			String string2 = ul.getStringInCurrency(getTotalQuote(bean.connect().getPaymentBalance(paymentID)));
			tableModel.setValueAt(string2, row+1, 4);
	
	}
	
		public void paymentOutDetails(DefaultTableModel tableModel, Outgoingview view){	
		Object toIncrease[] = new Object[1];
		int row = 1;
		//ArrayList<ArrayList<Object>> temp = arrayListOfObjects(view, bean.connect().getSupplierPaymentDetails(view.getTableId(7)));
		ArrayList<ArrayList<Object>> temp = arrayListOfObjects(view);
		Iterator<ArrayList<Object>> list = temp.iterator();
		tableModel.setValueAt(view.getStringValue(3), 0, 3);
		tableModel.setValueAt(view.getStringValue(4), 0, 4);
		tableModel.setValueAt(view.getTableId(7), 0, 5);//tablemodel value not visible in table, use for refersh button in incoming details
		while(list.hasNext()){
			ArrayList<Object> data = list.next();

					tableModel.setValueAt(ul.ukFormat((java.sql.Date)data.get(0)), row, 0);

					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(1)), row, 1);
				
					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(2)), row, 2);
					
					tableModel.setValueAt((Integer)data.get(5), row, 5);

				row++;
				if(row >= tableModel.getRowCount())
					tableModel.addRow(toIncrease);
				while(row >= tableModel.getRowCount() && tableModel.getRowCount() > temp.size()){
						tableModel.removeRow(tableModel.getRowCount()-1);
				}
				
			}
			tableModel.setValueAt(new String("Balance"), row+1, 3);
			String string2 = ul.getStringInCurrency(getTotalQuote(bean.connect().materialPaymentBalance(view.getTableId(7))));

			tableModel.setValueAt(string2, row+1, 4);
	
	}
	
		public void paymentOutDetails(DefaultTableModel tableModel, byte[] searchResult,  byte[] balResult,DefaultTableModel model, GeneralExpensesView view, int lastValue, JTable table, int date, int amount){	
		Object toIncrease[] = new Object[1];
		int row = 1;
		int counter = 0;

		ArrayList<ArrayList<Object>> temp = arrayListOfObjects(searchResult);
		Iterator<ArrayList<Object>> list = temp.iterator();
		tableModel.setValueAt(view.getStringValue(date,  model, table), 0, 3);
		tableModel.setValueAt(view.getStringValue(amount, model, table), 0, 4);
		tableModel.setValueAt(view.getTableId(lastValue,  model, table), 0, 5);//tablemodel value not visible in table, use for refersh button in incoming details
		while(list.hasNext()){
			ArrayList<Object> data = list.next();

					tableModel.setValueAt(ul.ukFormat((java.sql.Date)data.get(0)), row, 0);

					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(1)), row, 1);
				
					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(2)), row, 2);
					
					tableModel.setValueAt((Integer)data.get(5), row, 5);

				row++;
				if(row >= tableModel.getRowCount())
					tableModel.addRow(toIncrease);
				while(row >= tableModel.getRowCount() && tableModel.getRowCount() > temp.size()){
						tableModel.removeRow(tableModel.getRowCount()-1);
				}
				
			}
			tableModel.setValueAt(new String("Balance"), row+1, 3);
			String string2 = ul.getStringInCurrency(getTotalQuote(balResult));//bean.connect().prepaidPaymentBalance(view.getTableId(lastValue,  model, table))
			
			tableModel.setValueAt(string2, row+1, 4);
	
	}
	public void paymentOutAccruedDetails(DefaultTableModel tableModel, byte[] searchResult, GeneralExpensesView view){
	
		int row = 1;
		
		ArrayList<Object> data = getArrayList(searchResult);

		tableModel.setValueAt(view.getStringValue(2,  view.getModelAccrued(), view.getTableAccrued()), 0, 3);
		tableModel.setValueAt(view.getStringValue(3,  view.getModelAccrued(), view.getTableAccrued()), 0, 4);
		tableModel.setValueAt(view.getTableId(5,  view.getModelAccrued(), view.getTableAccrued()), 0, 5);//tablemodel value not visible in table, use for refersh button in incoming details
		if(data.size() >= 2){
			for(int i = 0; i < row; i++){
				tableModel.setValueAt(ul.ukFormat((java.sql.Date)data.get(0)), row, 0);

				tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(1)), row, 1);
				
				
				tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(2)), row, 2);
					
				tableModel.setValueAt((Integer)data.get(7), row, 5);
				
				tableModel.setValueAt(new String("Adj"), row, 3);
				String string1 = ul.getStringInCurrency((BigDecimal)data.get(5));
			
				tableModel.setValueAt(string1, row, 4);
			}
			row++;
			tableModel.setValueAt(new String("Balance"), row+1, 3);
			String string2 = ul.getStringInCurrency((BigDecimal)data.get(6));
			
			tableModel.setValueAt(string2, row+1, 4);
			
		}
		else{
			tableModel.setValueAt(new String("Balance"), row+1, 3);
			String string2 = ul.getStringInCurrency((BigDecimal)data.get(0));
			
			tableModel.setValueAt(string2, row+1, 4);
		}
	}
	
	public void manualPostingDetailsView(DefaultTableModel tableModel, byte[] searchResult){
		ArrayList<Object> data = getArrayList(searchResult);
		LinkedHashMap<String,BigDecimal> postedCodes = (LinkedHashMap<String,BigDecimal>)data.get(4);
		Object[] keyObj =  postedCodes.keySet().toArray();
		for(int i = 0; i < keyObj.length; i++){
			if(i == 0){
				String strPrinciple = (String)keyObj[0]; 
				String strCodeName[] = strPrinciple.split("\\.");
				tableModel.setValueAt(ul.ukFormat((java.sql.Date)data.get(3)), i, 3);
				tableModel.setValueAt(strCodeName[1], i, 4);
				tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)postedCodes.get((String)keyObj[0])), i, 5);
				tableModel.setValueAt((Integer)data.get(5), i, 6);
			}
			
			else{
				String strOther = (String)keyObj[i]; 
				String strCodeName1[] = strOther.split("\\.");
				tableModel.setValueAt(ul.ukFormat((java.sql.Date)data.get(3)), i, 0);
				tableModel.setValueAt(strCodeName1[1], i, 1);
				tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)postedCodes.get((String)keyObj[i])), i, 2);
			}
		
		}
	}
	
	public void paymentOutAccruedDetails(DefaultTableModel tableModel, byte[] searchResult){
	
		int row = 1;
		
		ArrayList<Object> data = listOfObject(searchResult);

		tableModel.setValueAt(tableModel.getValueAt(0, 3), 0, 3);
		tableModel.setValueAt(tableModel.getValueAt(0, 4), 0, 4);
		tableModel.setValueAt(tableModel.getValueAt(0, 5), 0, 5);//tablemodel value not visible in table, use for refersh button in incoming details
		if(data.size() >= 2){
			for(int i = 0; i < row; i++){
				tableModel.setValueAt(ul.ukFormat((java.sql.Date)data.get(0)), row, 0);

				tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(1)), row, 1);
				
				tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(2)), row, 2);
					
				tableModel.setValueAt((Integer)data.get(7), row, 5);
				
				tableModel.setValueAt(new String("Adj"), row, 3);
				String string1 = ul.getStringInCurrency((BigDecimal)data.get(5));
			
				tableModel.setValueAt(string1, row, 4);
			}
			row++;
			
			tableModel.setValueAt(new String(""), row, 3);
			tableModel.setValueAt(new String(""), row, 4);
			
			tableModel.setValueAt(new String("Balance"), row+1, 3);
			String string2 = ul.getStringInCurrency((BigDecimal)data.get(6));
			
			tableModel.setValueAt(string2, row+1, 4);
			
		}
		else{
			tableModel.setValueAt(new String("Balance"), row+1, 3);
			String string2 = ul.getStringInCurrency((BigDecimal)data.get(6));
			
			tableModel.setValueAt(string2, row+1, 4);
		}

	
	}
	
	public void paymentOutPaidDetails(DefaultTableModel tableModel, byte[] searchResult, GeneralExpensesView view ){
	
		int row = 1;
		
		ArrayList<Object> data = getArrayList(searchResult);

		tableModel.setValueAt(view.getStringValue(4,  view.getModelPaid(), view.getTablePaid()), 0, 3);
		tableModel.setValueAt(view.getStringValue(5,  view.getModelPaid(), view.getTablePaid()), 0, 4);
		tableModel.setValueAt(view.getTableId(6,  view.getModelPaid(), view.getTablePaid()), 0, 5);//tablemodel value not visible in table, use for refersh button in incoming details
	
			for(int i = 0; i < row; i++){
				tableModel.setValueAt(ul.ukFormat((java.sql.Date)data.get(0)), row, 0);

				tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(1)), row, 1);
				
				
				tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(2)), row, 2);
				
			}
			row++;
			//tableModel.setValueAt(new String(""), row+1, 3);
			//tableModel.setValueAt(new String(""), row+1, 4);
						//balance calculation using accounts payable table data
			String amount = ul.noPoundString((String)tableModel.getValueAt(1, 1));
			String vat = ul.noPoundString((String)tableModel.getValueAt(1, 2));
			String gross = ul.noPoundString((String)tableModel.getValueAt(0, 4));
			
			BigDecimal balance = new BigDecimal(amount).add(new BigDecimal(vat)).subtract(new  BigDecimal(gross));
			
			tableModel.setValueAt(new String("Balance"), row+1, 3);
			String string2 = ul.getStringInCurrency(balance);
			
			tableModel.setValueAt(string2, row+1, 4);

	
	}
	
	public void paymentOutPaidDetails(DefaultTableModel tableModel, byte[] searchResult){
	
		int row = 1;
		
		ArrayList<Object> data = listOfObject(searchResult);

		tableModel.setValueAt(tableModel.getValueAt(0, 3), 0, 3);
		tableModel.setValueAt(tableModel.getValueAt(0, 4), 0, 4);
		tableModel.setValueAt(tableModel.getValueAt(0, 5), 0, 5);//tablemodel value not visible in table, use for refersh button in incoming details
		
			for(int i = 0; i < row; i++){
				tableModel.setValueAt(ul.ukFormat((java.sql.Date)data.get(0)), row, 0);

				tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(1)), row, 1);
				
				tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(2)), row, 2);
	
			}
			row++;
			
			tableModel.setValueAt(new String(""), row+1, 3);
			tableModel.setValueAt(new String(""), row+1, 4);
						//balance calculation using accounts payable table data
			String amount = ul.noPoundString((String)tableModel.getValueAt(1, 1));
			String vat = ul.noPoundString((String)tableModel.getValueAt(1, 2));
			String gross = ul.noPoundString((String)tableModel.getValueAt(0, 4));
			
			BigDecimal balance = new BigDecimal(amount).add(new BigDecimal(vat)).subtract(new  BigDecimal(gross));
			
			tableModel.setValueAt(new String("Balance"), row+1, 3);
			String string2 = ul.getStringInCurrency(balance);
			
			tableModel.setValueAt(string2, row+1, 4);
	}
	
	public void paymentOutDetails(DefaultTableModel tableModel, int paymentID){	
		Object toIncrease[] = new Object[1];
		int row = 1;
		ArrayList<ArrayList<Object>> temp = arrayListOfObjects(paymentID);
		//ArrayList<ArrayList<Object>> temp = getArrayListOfArrayList(bean.connect().getSupplierPaymentDetails(paymentID));
		Iterator<ArrayList<Object>> list = temp.iterator();
		tableModel.setValueAt(tableModel.getValueAt(0, 3), 0, 3);
		tableModel.setValueAt(tableModel.getValueAt(0, 4), 0, 4);
		tableModel.setValueAt(Integer.valueOf(paymentID), 0, 5);//tablemodel value not visible in table, use for refersh button in incoming details
		while(list.hasNext()){
			ArrayList<Object> data = list.next();

					tableModel.setValueAt(ul.ukFormat((java.sql.Date)data.get(0)), row, 0);

					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(1)), row, 1);
				
					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(2)), row, 2);
					
					tableModel.setValueAt((Integer)data.get(5), row, 5);

				row++;
				if(row >= tableModel.getRowCount())
					tableModel.addRow(toIncrease);
				while(row >= tableModel.getRowCount() && tableModel.getRowCount() > temp.size()){
						tableModel.removeRow(tableModel.getRowCount()-1);
				}
				tableModel.setValueAt(new String(""), row, 3);
				tableModel.setValueAt(new String(""), row, 4);
				
			}
			tableModel.setValueAt(new String("Balance"), row+1, 3);
			String string2 = ul.getStringInCurrency(getTotalQuote(bean.connect().materialPaymentBalance(paymentID)));
			tableModel.setValueAt(string2, row+1, 4);
	
	}
	
	public void assetDepreciationDetails(DefaultTableModel tableModel, AssetRegisterView view){	
		Object toIncrease[] = new Object[1];
		int row = 1;
		String strId_strDesc = String.valueOf(ul.getTableInt(view.getTable(), view.getTableModel(), 6))+"."+ 
						ul.getStringValue(view.getTable(), view.getTableModel(), 1);
		String strassetId[] = strId_strDesc.split("\\.");
		int assetId = (Integer.valueOf(strassetId[0])).intValue();
		ArrayList<ArrayList<Object>> temp = getArrayListOfArrayList(bean.connect().getProcessedDepreciationDetails(assetId));
		Iterator<ArrayList<Object>> list = temp.iterator();
		tableModel.setValueAt(ul.getStringValue(view.getTable(), view.getTableModel(), 4), 0, 2);
		tableModel.setValueAt(ul.getStringValue(view.getTable(), view.getTableModel(), 2), 0, 3);
		tableModel.setValueAt(strId_strDesc, 0, 4);//tablemodel value not visible in table, use for refersh button in incoming details
		while(list.hasNext()){
			ArrayList<Object> data = list.next();

					tableModel.setValueAt(ul.ukFormat((java.sql.Date)data.get(0)), row, 0);

					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(1)), row, 1);
				
					
					tableModel.setValueAt((Integer)data.get(4), row, 4);

				row++;
				if(row >= tableModel.getRowCount())
					tableModel.addRow(toIncrease);
				while(row >= tableModel.getRowCount() && tableModel.getRowCount() > temp.size()){
						tableModel.removeRow(tableModel.getRowCount()-1);
				}
				
			}
			tableModel.setValueAt(new String("Net Book Value"), row+1, 2);
			String string2 = ul.getStringInCurrency(getTotalQuote(bean.connect().getNetBookValue(assetId)));
			tableModel.setValueAt(string2, row+1, 3);
	
	}
	
	
	
	public void subPaymentDetails(DefaultTableModel tableModel, Outgoingview view){	
		Object toIncrease[] = new Object[1];
		int row = 1;
		//ArrayList<ArrayList<Object>> temp = arrayListOfObjects(view, bean.connect().getSupplierPaymentDetails(view.getTableId(7)));
		ArrayList<ArrayList<Object>> temp = getArrayListOfArrayList(bean.connect().getSubContractorPaymentDetails(view.getTableId(7, view.getModel(), view.getTable())));
		Iterator<ArrayList<Object>> list = temp.iterator();
		tableModel.setValueAt(view.getStringValue(3,  view.getModel(), view.getTable()), 0, 4);
		tableModel.setValueAt(view.getStringValue(4, view.getModel(), view.getTable()), 0, 5);
		tableModel.setValueAt(view.getTableId(7,  view.getModel(), view.getTable()), 0, 6);//tablemodel value not visible in table, use for refersh button in incoming details
		while(list.hasNext()){
			ArrayList<Object> data = list.next();

					tableModel.setValueAt(ul.ukFormat((java.sql.Date)data.get(0)), row, 0);

					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(1)), row, 1);
				
					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(2)), row, 2);
					
					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(3)), row, 3);
					
					tableModel.setValueAt((Integer)data.get(6), row, 6);

				row++;
				if(row >= tableModel.getRowCount())
					tableModel.addRow(toIncrease);
				while(row >= tableModel.getRowCount() && tableModel.getRowCount() > temp.size()){
						tableModel.removeRow(tableModel.getRowCount()-1);
				}
				tableModel.setValueAt(new String(""), row, 4);
				tableModel.setValueAt(new String(""), row, 5);
			}
			tableModel.setValueAt(new String("Balance"), row+1, 4);
			
			//balance calculation using accounts payable table data
			String str1 = ul.noPoundString(view.getStringValue(4,  view.getModel(), view.getTable()));
			String str2 = ul.noPoundString(view.getStringValue(5,  view.getModel(), view.getTable()));
			BigDecimal diff = new BigDecimal(str1).subtract(new  BigDecimal(str2));
			String string2 = ul.getStringInCurrency(diff);

			tableModel.setValueAt(string2, row+1, 5);
	
	}
	
	public void subPaymentDetails(DefaultTableModel tableModel, int paymentId, Outgoingview view){	
		Object toIncrease[] = new Object[1];
		int row = 1;

		ArrayList<ArrayList<Object>> temp = subArrayListOfObjects(paymentId);
		Iterator<ArrayList<Object>> list = temp.iterator();
		tableModel.setValueAt(view.getStringValue(3,  view.getModel(), view.getTable()), 0, 4);
		tableModel.setValueAt(view.getStringValue(4, view.getModel(), view.getTable()), 0, 5);
		tableModel.setValueAt(view.getTableId(7,  view.getModel(), view.getTable()), 0, 6);//tablemodel value not visible in table, use for refersh button in incoming details
		while(list.hasNext()){
			ArrayList<Object> data = list.next();

					tableModel.setValueAt(ul.ukFormat((java.sql.Date)data.get(0)), row, 0);

					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(1)), row, 1);
				
					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(2)), row, 2);
					
					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)data.get(3)), row, 3);
					
					tableModel.setValueAt((Integer)data.get(6), row, 6);

				row++;
				if(row >= tableModel.getRowCount())
					tableModel.addRow(toIncrease);
				while(row >= tableModel.getRowCount() && tableModel.getRowCount() > temp.size()){
						tableModel.removeRow(tableModel.getRowCount()-1);
				}
				tableModel.setValueAt(new String(""), row, 4);
				tableModel.setValueAt(new String(""), row, 5);
			}
			tableModel.setValueAt(new String("Balance"), row+1, 4);
			
			//balance calculation using accounts payable table data
			String str1 = ul.noPoundString(view.getStringValue(4,  view.getModel(), view.getTable()));
			String str2 = ul.noPoundString(view.getStringValue(5,  view.getModel(), view.getTable()));
			BigDecimal diff = new BigDecimal(str1).subtract(new BigDecimal(str2));
			String string2 = ul.getStringInCurrency(diff);

			tableModel.setValueAt(string2, row+1, 5);
	
	}
	
	public void loadData(DefaultTableModel tableModel, byte[] arrayListdata){
		Object toIncrease[] = new Object[1];
		int row = 0;
		for(int i = 0 ; i < tableModel.getRowCount(); i++){
			for(int j = 0; j < tableModel.getColumnCount(); j++){
				tableModel.setValueAt(new String(""), i, j);
			}
		}
		ArrayList<ArrayList<Object>> temp = getArrayListOfArrayList(arrayListdata);
		Iterator<ArrayList<Object>> iterate = temp.iterator();
		while(iterate.hasNext()){
			ArrayList<Object> datas = iterate.next();
			for(int c = 0; c < datas.size(); c++){
				if(datas.get(c) instanceof String){
					tableModel.setValueAt((String)datas.get(c), row, c);
					}
				else if(datas.get(c) instanceof Integer){
					tableModel.setValueAt((Integer)datas.get(c), row, c);
				}
				else if(datas.get(c) instanceof java.sql.Date){
					tableModel.setValueAt(ul.ukFormat((java.sql.Date)datas.get(c)), row, c);
				}
				else if(datas.get(c) instanceof BigDecimal && datas.get(c) != null){
				
					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)datas.get(c)), row, c);
				}
				
			}
			row++;
			if(row >= 16){
				tableModel.addRow(toIncrease);
			}

		}
	
	}
	
	public void tableRowTrimer(DefaultTableModel tableModel, int initialRows, Collection<?> temp){
		int rowCounter = 0;
		while(tableModel.getRowCount() > initialRows && tableModel.getRowCount() > temp.size()){
			rowCounter++;
			tableModel.removeRow(tableModel.getRowCount()-1);
			//if(rowCounter == initialRows){
			//	break;
			//}
		}
		temp.clear();
	}
	
	public void loadData(DefaultTableModel tableModel, byte[] arrayListdata, int initialRows){
		Object toIncrease[] = new Object[1];
		int row = 0;
		for(int i = 0 ; i < tableModel.getRowCount(); i++){
			for(int j = 0; j < tableModel.getColumnCount(); j++){
				tableModel.setValueAt(new String(""), i, j);
			}
		}
		ArrayList<ArrayList<Object>> temp = getArrayListOfArrayList(arrayListdata);
		Iterator<ArrayList<Object>> iterate = temp.iterator();
		while(iterate.hasNext()){
			ArrayList<Object> datas = iterate.next();
			for(int c = 0; c < datas.size(); c++){
				if(datas.get(c) instanceof String){
					tableModel.setValueAt((String)datas.get(c), row, c);
					}
				else if(datas.get(c) instanceof Integer){
					tableModel.setValueAt((Integer)datas.get(c), row, c);
				}
				else if(datas.get(c) instanceof java.sql.Date){
					tableModel.setValueAt(ul.ukFormat((java.sql.Date)datas.get(c)), row, c);
				}
				else if(datas.get(c) instanceof BigDecimal ){
				
					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)datas.get(c)), row, c);
				}
				
			}
			row++;
			if(row >= initialRows){
				tableModel.addRow(toIncrease);
			}
		}

		tableRowTrimer(tableModel, initialRows, temp);
		//temp.clear();
	}

	public void loadData(DefaultTableModel tableModel, ArrayList<Object> datas , int initialRows){

		for(int c = 0; c < datas.size(); c++){
			if(datas.get(c) instanceof String){
				tableModel.setValueAt((String)datas.get(c), 0, c);
			}
			else if(datas.get(c) instanceof Integer){
				tableModel.setValueAt((Integer)datas.get(c), 0, c);
			}
			else if(datas.get(c) instanceof java.sql.Date){
				tableModel.setValueAt(ul.ukFormat((java.sql.Date)datas.get(c)), 0, c);
			}
			else if(datas.get(c) instanceof BigDecimal){
					//tableModel.setValueAt((BigDecimal)datas.get(c), row, c);
				//String string = NumberFormat.getCurrencyInstance(locale).format(((BigDecimal)datas.get(c)).BigDecimalValue());
				tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)datas.get(c)), 0, c);
			}
			
		}
		while(tableModel.getRowCount() > initialRows){
			tableModel.removeRow(tableModel.getRowCount()-1);
		}	
	}
	
	public String sqlDateString(JSpinner spinner){
		JFormattedTextField textdate = ((JSpinner.DateEditor)spinner.getEditor()).getTextField();
		return ul.usDateString(textdate.getText());
	
	}
  public java.sql.Date sqlDate(JSpinner spinner){
		//java.sql.Date date = new java.sql.Date();
		JFormattedTextField textdate = ((JSpinner.DateEditor)spinner.getEditor()).getTextField();
		java.sql.Date date = ul.usDate(textdate.getText());
		return date;
	}
	
	public void projectFinancialDetails(DefaultTableModel tableModel, int projectId){
		ArrayList<BigDecimal> datas = getArrayListBigDecimal(bean.connect().projectFinancialActivities(projectId));
		tableModel.setValueAt("INCOMING", 1, 1);
		tableModel.setValueAt("Total Incoming", 3, 3);
		tableModel.setValueAt(ul.getStringInCurrency(datas.get(0)), 3, 4);
		tableModel.setValueAt("OUTGOING", 5, 1);
		tableModel.setValueAt("Material Total", 7, 3);
		tableModel.setValueAt(ul.getStringInCurrency(datas.get(1)), 7, 4);
		tableModel.setValueAt("Labour Total", 9, 3);
		tableModel.setValueAt(ul.getStringInCurrency(datas.get(2)), 9, 4);
		tableModel.setValueAt("LCurrent position as at [date] ", 11, 3);
		tableModel.setValueAt(ul.getStringInCurrency(datas.get(3)), 11, 4);
	}
	
	public void loadSingleData(DefaultTableModel tableModel, byte[] arrayListdata){

		ArrayList<Object> datas = getArrayList(arrayListdata);
		int row = 0;
			for(int c = 0; c < datas.size(); c++){
				if(datas.get(c) instanceof String){
					tableModel.setValueAt((String)datas.get(c), row, c);
					}
				else if(datas.get(c) instanceof Integer){
					tableModel.setValueAt((Integer)datas.get(c), row, c);
				}
				else if(datas.get(c) instanceof java.sql.Date){
					tableModel.setValueAt(ul.ukFormat((java.sql.Date)datas.get(c)), row, c);
				}
				else if(datas.get(c) instanceof BigDecimal){
					tableModel.setValueAt(ul.getStringInCurrency((BigDecimal)datas.get(c)), row, c);
				}
				
			}
				
		}
	

}