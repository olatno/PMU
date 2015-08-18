
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;



 
       @Entity
	   @Inheritance(strategy=InheritanceType.JOINED)
	   @Table(name="CONTACT")
       public abstract class Contact extends Versioning implements Serializable{
	   private int contactId;
	   private String mobile;
	   private String contactName;

	   public Contact(){}
	   
	   @TableGenerator(name="CONTACT_GENERATOR", table="Contact_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="Contact_ID",initialValue=0, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="CONTACT_GENERATOR")
	   @Column(name="CONTACTID")
	   public int getContactId(){
	       return contactId;
	    }

	   public void setContactId(int contactId){
       this.contactId = contactId;
	   }
	   
	   @Column(name="MOBILE")
	   public String getMobile(){

 	       return mobile;
 	   }

	   public void setMobile(String mobile){
	       this.mobile = mobile;

	   }
	   
	   @Column(name="CONTACTNAME")
	   public String getContactName(){

	       return contactName;
 	   }

 	   public void setContactName(String contactName){
 	       this.contactName = contactName;

 	   }

    }