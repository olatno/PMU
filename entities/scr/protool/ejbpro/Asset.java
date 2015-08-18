
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;
import java.math.BigDecimal;


 
       @Entity
	   	   
	   @Table(name="ASSET")
       public class Asset implements Serializable{

	  
	   private int assetId;
	   private AccountCode assetCode;
 	   private String description;
 	   private String serialNumber;
 	   private String location;
	   private Journal journal;
 	   private java.sql.Date aquisationDate;
	   private Supplier supplierInsurance;
	   private Supplier supplierAsset;
	   private String invoiceNumber;
	   private String assetStatus;
	   private Long version;
	   
	   
	   public Asset(){}

	   @TableGenerator(name="ASSET_GENERATOR", table="Asset_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="Asset_ID",initialValue=0, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="ASSET_GENERATOR")
	   	   
	   @Column(name="ASSETID")
	   public int getAssetId(){
	       return assetId;
	    }

	   public void setAssetId(int assetId){
       this.assetId = assetId;
	   }
	   
	   @Column(name="LOCATION")
 	   public String getLocation(){

	       return location;
 	   }

	   public void setLocation(String location){
 	       this.location = location;

	   }

	   @Column(name="SERIALNUMBER")
	   public String getSerialNumber(){

	       return serialNumber;
 	   }

 	   public void setSerialNumber(String serialNumber){
 	       this.serialNumber = serialNumber;

 	   }
	   
	   @Column(name="DESCRIPTION")
	   public String getDescription(){

	       return description;
 	   }

 	   public void setDescription(String description){
 	       this.description = description;
 	   }	   
	   		
	   @Column(name="ASSETSTATUS")
 	   public String getAssetStatus(){

	       return assetStatus;
 	   }

	   public void setAssetStatus(String assetStatus){
 	       this.assetStatus = assetStatus;

 	   }

	  @Column(name="AQUISATIONDATE")
 	  public java.sql.Date getAquisationDate(){

 	       return aquisationDate;
 	   }

	   public void setAquisationDate(java.sql.Date aquisationDate){
 	       this.aquisationDate = aquisationDate;

	   }

	   @Column(name="INVOICENUMBER")
 	   public String getInvoiceNumber(){

 	       return invoiceNumber;
 	   }

	   public void setInvoiceNumber(String invoiceNumber){
	       this.invoiceNumber = invoiceNumber;

	   }
	   
	   @OneToOne 
	   @JoinColumn(name="ASSETCODE", referencedColumnName="CODEID", nullable=false)
 	   public AccountCode getAssetCode(){
 	       return assetCode;
	   }

 	   public void setAssetCode(AccountCode assetCode){
	       this.assetCode = assetCode;
 	   }
	   
	   @OneToOne 
	   @JoinColumn(name="SUPPLIERASSET", referencedColumnName="SUPPLIERID", nullable=false)
 	   public Supplier getSupplierAsset(){

	       return supplierAsset;
 	   }

	   public void setSupplierAsset(Supplier supplierAsset){
 	       this.supplierAsset = supplierAsset;

	   }
	   
	   @OneToOne 
	   @JoinColumn(name="SUPPLIERINSURANCE", referencedColumnName="SUPPLIERID", nullable=false)
 	   public Supplier getSupplierInsurance(){

	       return supplierInsurance;
 	   }

	   public void setSupplierInsurance(Supplier supplierInsurance){
 	       this.supplierInsurance = supplierInsurance;

	   }

	   @OneToOne 
	   @JoinColumn(name="JOURNAL", referencedColumnName="JOURNALID", nullable=false)
 	   public Journal getJournal(){
 	       return journal;
	   }

 	   public void setJournal(Journal journal){
	       this.journal = journal;
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