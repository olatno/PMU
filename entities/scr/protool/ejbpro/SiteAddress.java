
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;



 
       @Entity
       @Table(name="SITEADDRESS")
       public class SiteAddress implements Serializable{ 

	  
	   private int siteAddressId;
	   private Address addresses;
	   private SiteContact siteContact;
	   private Long version;
	   
	   public SiteAddress(){}

	   @TableGenerator(name="SiteAddress_GENERATOR", table="GENERICPK", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="SiteAddress_ID",initialValue=1, allocationSize=1)
	   @Id
	  @Column(name="SITEADDRESSID")
 	  @GeneratedValue(strategy=GenerationType.TABLE, generator="SiteAddress_GENERATOR")
	  public int getSiteAddressId(){
	       return siteAddressId;
	   }

	   public void setSiteAddressId(int siteAddressId){
       this.siteAddressId = siteAddressId;
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
	   @JoinColumn(name="SITECONTACT", referencedColumnName="CONTACTID", nullable=true)
 	   public SiteContact getSiteContact(){
 	       return siteContact;
 	   }

	   public void setSiteContact(SiteContact siteContact){
 	       this.siteContact = siteContact;
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