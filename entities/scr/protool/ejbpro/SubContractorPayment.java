
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;

 
       @Entity
	   @Table(name="SUBCONTRACTORPAYMENT")
       public class SubContractorPayment implements Serializable{

	  
	   private int subContractorPaymentId;
 	   private SubContractorInvoice subContractorInvoice;
	   private Journal journal;
 	   private java.sql.Date paymentDate;
	   private String note;
	   private String type;
	   private Long version;
	   
	   public SubContractorPayment(){}

	   @TableGenerator(name="SubContractorPayment_GENERATOR", table="SubContractorPayment_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="SubContractorPayment_ID",initialValue=0, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="SubContractorPayment_GENERATOR")
	   @Column(name="SUBCONTRACTORPAYMENTID")
	   public int getSubContractorPaymentId(){
	       return subContractorPaymentId;
	    }

	   public void setSubContractorPaymentId(int subContractorPaymentId){
       this.subContractorPaymentId=subContractorPaymentId;
	   }
	   
	   @ManyToOne
	   @JoinColumn(name="SUBCONTRACTORINVOICE", referencedColumnName="SUBCONTRACTORINVOICEID", nullable=false)
 	   public SubContractorInvoice getSubContractorInvoice(){

	       return subContractorInvoice;
 	   }

	   public void setSubContractorInvoice(SubContractorInvoice subContractorInvoice){
 	       this.subContractorInvoice = subContractorInvoice;

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