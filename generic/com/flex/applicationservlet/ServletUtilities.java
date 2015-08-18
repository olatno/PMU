
package com.flex.servletmain; 
import java.util.*;
//import java.text.*;
import java.io.*;

    public class ServletUtilities{
	
	public ServletUtilities(){}
	
	protected byte[] getStrToByte(String requestUsername, String requestPassword){
		String[] strArray = {requestUsername, requestPassword};
	
		try {
           ByteArrayOutputStream buffer = new ByteArrayOutputStream();
           ObjectOutputStream oos = new ObjectOutputStream(buffer);
           oos.writeObject(strArray);
           oos.close();
           return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error writing to byte-array!");
        }
	}
	
	protected String getClientPage(byte[] clientPage){ 
		try {
            ByteArrayInputStream input = new ByteArrayInputStream(clientPage);
            ObjectInputStream ois = new ObjectInputStream(input);	
			return (String)ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error reading from byte-array!");
        }
	}
 }

