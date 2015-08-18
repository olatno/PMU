
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;



 
       @Entity
	   @Table(name="ACCRUEDEXPENSESPAYMENT")
       public class AccruedExpensesPayment implements Serializable{

	  
	   private int accruedPaymentId;
 	   private AccruedExpensesInvoice expensesAccruedInvoice;
	   private AccountCode paymentType;
 	   private Journal journal;
 	   private java.sql.Date paymentDate;
	   private String invoiceNumber;
	   private Long version;
	   
	   public AccruedExpensesPayment(){}

	   @TableGenerator(name="ACCRUEDPAYMENT_GENERATOR", table="AccruedPayment_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="AccruedPayment_ID",initialValue=0, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="ACCRUEDPAYMENT_GENERATOR")
	   @Column(name="ACCRUEDPAYMENTID")
	   public int getAccruedPaymentId(){
	       return accruedPaymentId;
	    }

	   public void setAccruedPaymentId(int accruedPaymentId){
       this.accruedPaymentId = accruedPaymentId;
	   }
	   
	   @ManyToOne
	   @JoinColumn(name="EXPENSESINVOICEID", referencedColumnName="EXPENSESINVOICEID", nullable=false)
 	   public AccruedExpensesInvoice getExpensesAccruedInvoice(){

	       return expensesAccruedInvoice;
 	   }

	   public void setExpensesAccruedInvoice(AccruedExpensesInvoice expensesAccruedInvoice){
 	       this.expensesAccruedInvoice = expensesAccruedInvoice;

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

	   @Column(name="INVOICENUMBER")
	   public String getInvoiceNumber(){
		return invoiceNumber;
	   }
		public void setInvoiceNumber(String invoiceNumber){
		
			this.invoiceNumber=invoiceNumber;
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