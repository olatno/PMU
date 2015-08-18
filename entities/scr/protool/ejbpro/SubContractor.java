
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;
 
       @Entity
	   @Table(name="SUBCONTRACTOR")
       public class SubContractor implements Serializable{

	  
	   private int subContractorId;
 	   private String companyName;
 	   private String contactName;
 	   private String companyTele;
 	   private String companyEmail;
 	   private java.sql.Date createdDate;
	   private String paymentTerm;
	   private String reference;
	   private String mobile;
	   private String utr;
	   private String business;
 	   private Address addresses;
	   private Long version;
	   
	   public SubContractor(){}

	   @TableGenerator(name="SUBCONTRACTOR_GENERATOR", table="SubContractor_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="SubContractor_ID",initialValue=40000, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="SUBCONTRACTOR_GENERATOR")
	   @Column(name="SUBCONTRACTORID")
	   public int getSubContractorId(){
	       return subContractorId;
	    }

	   public void setSubContractorId(int subContractorId){
       this.subContractorId=subContractorId;
	   }
	   
	   @Column(name="COMPANYNAME")
	   public String getCompanyName(){

	       return companyName;
 	   }

	   public void setCompanyName(String companyName){
 	       this.companyName = companyName;

	   }
	   
	   @Column(name="BUSINESS")
	   public String getBusiness(){

 	       return business;
 	   }

	   public void setBusiness(String business){
	       this.business = business;

	   }

	   @Column(name="CONTACTNAME")
	   public String getContactName(){

	       return contactName;
 	   }

 	   public void setContactName(String contactName){
 	       this.contactName = contactName;

 	   }

	   @Column(name="REFERENCE")
	   public String getReference(){

 	       return reference;
 	   }

	   public void setReference(String reference){
	       this.reference = reference;

	   }
	   
	  @OneToOne
 	  @Embedded 
 	  public Address getAddresses(){
 	       return addresses;
 	   }

	  public void setAddresses(Address addresses){
 	       this.addresses = addresses;
 	   }
	   
	  @Column(name="COMPANYTELE")
	  public String getCompanyTele(){

	       return companyTele;
 	   }

 	  public void setCompanyTele(String companyTele){
 	       this.companyTele = companyTele;
 	   }

	  @Column(name="MOBILE")
	  public String getMobile(){

 	       return mobile;
 	   }

	  public void setMobile(String mobile){
	       this.mobile = mobile;

	   }
	   
	  @Column(name="COMPANYEMAIL")
 	  public String getCompanyEmail(){

	       return companyEmail;
 	   }

	   public void setCompanyEmail(String companyEmail){
 	       this.companyEmail = companyEmail;

 	   }

	  @Column(name="CREATEDDATE")
 	  public java.sql.Date getStartDate(){

 	       return createdDate;
 	   }

	   public void setStartDate(java.sql.Date createdDate){
 	       this.createdDate = createdDate;

	   }

	  @Column(name="UTR")
	   public String getUtr(){

 	       return  utr;
	   }

	   public void setUtr(String utr){
	       this.utr = utr;

 	   }

	   @Column(name="PAYMENTTERM")
 	   public String getPaymentTerm(){

 	       return paymentTerm;
 	   }

	   public void setPaymentTerm(String paymentTerm){
	       this.paymentTerm = paymentTerm;

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