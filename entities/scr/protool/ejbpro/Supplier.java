
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


 
       @Entity
	   @Table(name="SUPPLIER")
       public class Supplier implements Serializable{

	  
	   private int supplierId;
 	   private String companyName;
 	   private String contactName;
 	   private String companyTele;
 	   private String companyEmail;
 	   private java.sql.Date startDate;
	   private String paymentTerm;
	   private String account;
	   private String fax;
	   private BigDecimal credit;
 	   private Address addresses;
	   private Long version;
	   
	   public Supplier(){}

	   @TableGenerator(name="SUPPLIER_GENERATOR", table="Supplier_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="Supplier_ID",initialValue=30000, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="SUPPLIER_GENERATOR")
	   @Column(name="SUPPLIERID")
	   public int getSupplierId(){
	       return supplierId;
	    }

	   public void setSupplierId(int supplierId){
       this.supplierId=supplierId;
	   }
	   
	   @Column(name="COMPANYNAME")
 	   public String getCompanyName(){

	       return companyName;
 	   }

	   public void setCompanyName(String companyName){
 	       this.companyName = companyName;

	   }

	   @Column(name="CONTACTNAME")
	   public String getContactName(){

	       return contactName;
 	   }

 	   public void setContactName(String contactName){
 	       this.contactName = contactName;

 	   }
	   
	   @Column(name="COMPANYTELE")
	   public String getCompanyTele(){

	       return companyTele;
 	   }

 	   public void setCompanyTele(String companyTele){
 	       this.companyTele = companyTele;
 	   }

	   @Column(name="COMPANYEMAIL")
 	   public String getCompanyEmail(){

	       return companyEmail;
 	   }

	   public void setCompanyEmail(String companyEmail){
 	       this.companyEmail = companyEmail;

 	   }

	  @Column(name="STARTDATE")
 	  public java.sql.Date getStartDate(){

 	       return startDate;
 	   }

	   public void setStartDate(java.sql.Date startDate){
 	       this.startDate = startDate;

	   }

	   @Column(name="CREDIT", precision=12, scale=2)
	   public BigDecimal getCredit(){

 	       return  credit;
	   }

	   public void setCredit(BigDecimal credit){
	       this.credit = credit;

 	   }

	   @Column(name="PAYMENTTERM")
 	   public String getPaymentTerm(){

 	       return paymentTerm;
 	   }

	   public void setPaymentTerm(String paymentTerm){
	       this.paymentTerm = paymentTerm;

	   }
	   
	   @Column(name="ACCOUNT")
	   public String getAccount(){

 	       return account;
 	   }

	   public void setAccount(String account){
	       this.account = account;

	   }
	   
	   @Column(name="FAX")
	   public String getFax(){

 	       return fax;
 	   }

	   public void setFax(String fax){
	       this.fax = fax;

	   }

	   @OneToOne
 	   @Embedded 
 	   public Address getAddresses(){
 	       return addresses;
 	   }

	   public void setAddresses(Address addresses){
 	       this.addresses = addresses;
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