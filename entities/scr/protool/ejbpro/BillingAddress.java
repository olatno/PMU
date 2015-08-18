
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;



 
       @Entity
       @Table(name="BILLINGADDRESS")
       public class BillingAddress implements Serializable{

	  
	   private int billingAddressId;
	   private Address addresses;
	   private BillingContact billingContact;
	   private Long version;
	   
	   public BillingAddress(){}

	   @TableGenerator(name="BillingAddress_GENERATOR", table="GENERICPK", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="BillingAddress_ID",initialValue=1, allocationSize=1)
	   @Id
	  @Column(name="BILLINGADDRESSID")
 	  @GeneratedValue(strategy=GenerationType.TABLE, generator="BillingAddress_GENERATOR")
	  public int getBillingAddressId(){
	       return billingAddressId;
	   }

	   public void setBillingAddressId(int billingAddressId){
       this.billingAddressId = billingAddressId;
	   }
	    
	   @OneToOne
 	   @Embedded 
 	   public Address getAddresses(){
 	       return addresses;
 	   }

	   public void setAddresses(Address addresses){
 	       this.addresses = addresses;
 	   }
	   
	   @OneToOne
	   @JoinColumn(name="BILLINGCONTACT", referencedColumnName="CONTACTID", nullable=false)
 	   public BillingContact getBillingContact(){
 	       return billingContact;
 	   }

	   public void setBillingContact(BillingContact billingContact){
 	       this.billingContact = billingContact;
 	   }
	   
	   @Version 
	   @Column(name="VERSION")
	   public Long getVersion(){

 	       return version;
 	   }

	   public void setVersion(Long version){
	       this.version = version;

	   }

   }