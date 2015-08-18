
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;

       @Entity
	   @Table(name="PAYABLEEXPENSESPAYMENT")
       public class PayableExpensesPayment implements Serializable{

	  
	   private int expensesPaymentId;
 	   private PayableExpensesInvoice expensesPayableInvoice;
	   private AccountCode paymentType;
	   private Journal journal;
 	   private java.sql.Date paymentDate;
	   private String note;
	   private String type;
	   private Long version;
	   
	   public PayableExpensesPayment(){}

	   @TableGenerator(name="GENERALPAYMENT_GENERATOR", table="GeneralPayment_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="GeneralPayment_ID",initialValue=0, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="GENERALPAYMENT_GENERATOR")
	   @Column(name="EXPENSESPAYMENTID")
	   public int getExpensesPaymentId(){
	       return expensesPaymentId;
	    }

	   public void setExpensesPaymentId(int expensesPaymentId){
       this.expensesPaymentId = expensesPaymentId;
	   }
	   
	   @ManyToOne
	   @JoinColumn(name="EXPENSESINVOICEID ", referencedColumnName="EXPENSESINVOICEID", nullable=false)
 	   public PayableExpensesInvoice getExpensesPayableInvoice(){

	       return expensesPayableInvoice;
 	   }

	   public void setExpensesPayableInvoice(PayableExpensesInvoice expensesPayableInvoice){
 	       this.expensesPayableInvoice = expensesPayableInvoice;

	   }
	  
	  	   
	   @OneToOne 
	   @JoinColumn(name="PAYMENTTYPE", referencedColumnName="CODEID", nullable=false)
 	   public AccountCode getPaymentType(){

	       return paymentType;
 	   }

	   public void setPaymentType(AccountCode paymentType){
 	       this.paymentType = paymentType;

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