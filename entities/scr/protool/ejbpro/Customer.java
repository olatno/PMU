
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;


 
       @Entity
       @Table(name="CUSTOMER")
       public class Customer implements Serializable{

	  
	   private int customerId;
 	   //private String clientName;
	   private String companyName;
 	   //private String clientTele;
 	   //private String clientEmail;
	   private BillingAddress billingAddress;
	   //private SiteAddress siteAddress;
 	   private java.sql.Date registerDate;
	  // private String mobile;
	   private Long version; 
	  // private Collection<SiteAddress> siteAddress;
	   private Collection<Project> project;
	   
	   public Customer(){}

	   @TableGenerator(name="Customer_GENERATOR", table="GENERICPK", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="Customer_ID",initialValue=1, allocationSize=1)
	   @Id
	   @Column(name="CUSTOMERID")
 	  @GeneratedValue(strategy=GenerationType.TABLE, generator="Customer_GENERATOR")
	  public int getCustomerId(){
	       return customerId;
	   }

	   public void setCustomerId(int customerId){
       this.customerId = customerId;
	   }
	   
 	  /* @Column(name="CLIENTNAME")
	   public String getClientName(){

	       return clientName;
 	   }

 	   public void setClientName(String clientName){
 	       this.clientName = clientName;

 	   }*/

 	   @Column(name="COMPANYNAME")
	  public String getCompanyName(){

	      return companyName;
    }

 	  public void setCompanyName(String companyName){
 	      this.companyName = companyName;
 	   }

 	  /* @Column(name="CLIENTTELE")
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

 	   }*/
	   
	   @OneToOne 
	   @JoinColumn(name="BILLINGADDRESS", referencedColumnName="BILLINGADDRESSID", nullable=false)
	   public BillingAddress getBillingAddress(){
			return billingAddress;
	   }
	   
	   public void setBillingAddress(BillingAddress billingAddress){
			this.billingAddress = billingAddress;
	   }
	   
	   /*@OneToOne 
	   @JoinColumn(name="SITEADDRESS", referencedColumnName="SITEADDRESSID", nullable=false)
	   public SiteAddress getSiteAddress(){
			return siteAddress;
	   }
	   
	   public void setSiteAddress(SiteAddress siteAddress){
			this.siteAddress = siteAddress;
	   }
	 
 	   @OneToMany(cascade=CascadeType.ALL, mappedBy="customer")
 	   public Collection<SiteAddress> getSiteAddress(){
	       return siteAddress;
 	   }

	   public void setSiteAddress(Collection<SiteAddress> siteAddress){
 	       this.siteAddress = siteAddress;
	   }*/


 	   @Column(name="REGISTERDATE")
 	   public java.sql.Date getRegisterDate(){

 	       return registerDate;
 	   }

	   public void setRegisterDate(java.sql.Date registerDate){
 	       this.registerDate = registerDate;

	   }




 	   @OneToMany(cascade=CascadeType.ALL, mappedBy="customer")
 	   public Collection<Project> getProject(){
	       return project;
 	   }

	   public void setProject(Collection<Project> project){
 	       this.project = project;
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