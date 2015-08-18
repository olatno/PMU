
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;
 
       @Entity
	   @Table(name="SUPPLIERPAYMENT")
       public class SupplierPayment implements Serializable{

	  
	   private int supplierPaymentId;
 	   private SupplierInvoice supplierInvoice;
	   private Journal journal;
 	   private java.sql.Date paymentDate;
	   private String note;
	   private String type;
	   private Long version;
	   
	   public SupplierPayment(){}

	   @TableGenerator(name="SUPPLIERPAYMENT_GENERATOR", table="SupplierPayment_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="SupplierPayment_ID",initialValue=0, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="SUPPLIERPAYMENT_GENERATOR")
	   @Column(name="SUPPLIERPAYMENTID")
	   public int getSupplierPaymentId(){
	       return supplierPaymentId;
	    }

	   public void setSupplierPaymentId(int supplierPaymentId){
       this.supplierPaymentId=supplierPaymentId;
	   }
	   
	   @ManyToOne
	   @JoinColumn(name="SUPPLIERINVOICE", referencedColumnName="SUPPLIERINVOICEID", nullable=false)
 	   public SupplierInvoice getSupplierInvoice(){

	       return supplierInvoice;
 	   }

	   public void setSupplierInvoice(SupplierInvoice supplierInvoice){
 	       this.supplierInvoice = supplierInvoice;

	   }
	   
	   @OneToOne 
	   @JoinColumn(name="JOURNAL", referencedColumnName="JOURNALID", nullable=false)
 	   public Journal getJournal(){
 	       return journal;
	   }

 	   public void setJournal(Journal journal){
	       this.journal = journal;
 	   }
	   
	   @Column(name="TYPE")
	   public String getType(){
		return type;
	   }
	 public void setType(String type){
		
		this.type=type;
	 }
		
	  @Column(name="NOTE")
	  public String getNote(){
		return note;
	  }
	  
	  public void setNote(String note){
		
			this.note=note;
	 }
	 
	  @Column(name="PAYMENTDATE")
 	  public java.sql.Date getPaymentDate(){

 	       return paymentDate;
 	   }

	   public void setPaymentDate(java.sql.Date paymentDate){
 	       this.paymentDate = paymentDate;

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