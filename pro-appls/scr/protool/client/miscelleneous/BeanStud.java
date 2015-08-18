package scr.protool.client.miscelleneous;
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
import scr.protool.ejbprointer.GenericClient;

public class BeanStud {
	 private GenericClient  clientBean;
	 
	public BeanStud(){}
	
	public GenericClient connect(){
		//use global jndi naming to get handle to ejb stub
		try{
			Properties props = new Properties(); 
			props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
			props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
			props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
			props.setProperty("org.omg.CORBA.ORBInitialHost", "94.76.200.215"); 
			//props.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1"); 
			props.setProperty("org.omg.CORBA.ORBInitialPort", "3700"); 
			InitialContext ctx = new InitialContext(props); 
			clientBean = (GenericClient)ctx.lookup("java:global/genericClientApp/pro-ejbs/GenericClientBean");

		}catch (Exception ex){}
		return clientBean;
	}
	
}