
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;
import java.sql.Date;


 
       @Entity
	   @Table(name="BILLINGCONTACT")
       public class BillingContact extends Contact implements Serializable{
	   
	   private String clientTele;
 	   private String clientEmail;
	   
	   public BillingContact(){}
	   
	   @Column(name="CLIENTTELE")
 	   public String getClientTele(){

	       return clientTele;
 	   }

	   public void setClientTele(String clientTele){
 	       this.clientTele = clientTele;

 	   }

 	   @Column(name="CLIENTEMAIL")
 	   public String getClientEmail(){

 	       return clientEmail;
 	   }

 	   public void setClientEmail(String clientEmail){
	       this.clientEmail = clientEmail;

 	   }
   }