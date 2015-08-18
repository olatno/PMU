
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;

       @Entity
	   @Inheritance(strategy=InheritanceType.JOINED)
	   @Table(name="EXPENSESINVOICE")
       public abstract class ExpensesInvoice extends Versioning implements Serializable{

	  
	   private int expensesInvoiceId;
	   private Journal journal;
	   private Supplier supplier;
	   private String dtype;
 	   private java.sql.Date invoiceDate;
	   private AccountCode operationExpenses;

	   
	   public ExpensesInvoice(){}

	   @TableGenerator(name="ExpensesInvoice_GENERATOR", table="ExpensesInvoice_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="ExpensesInvoice_ID",initialValue=0, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="ExpensesInvoice_GENERATOR")
	   @Column(name="EXPENSESINVOICEID")
	   public int getExpensesInvoiceId(){
	       return expensesInvoiceId;
	    }

	   public void setExpensesInvoiceId(int expensesInvoiceId){
       this.expensesInvoiceId=expensesInvoiceId;
	   }
	  
	   @OneToOne 
	   @JoinColumn(name="JOURNALID", referencedColumnName="JOURNALID", nullable=false)
 	   public Journal getJournal(){
 	       return journal;
	   }

 	   public void setJournal(Journal journal){
	       this.journal = journal;
 	   }

	  @Column(name="INVOICEDATE")
 	  public java.sql.Date getInvoiceDate(){

 	       return invoiceDate;
 	   }

	   public void setInvoiceDate(java.sql.Date invoiceDate){
 	       this.invoiceDate = invoiceDate;

	   }

	   @Column(name="DTYPE", insertable=false, updatable=false)
 	   public String getDtype(){

 	       return dtype;
 	   }

	   public void setDtype(String dtype){
	       this.dtype = dtype;

	   }
	   
	   @ManyToOne
	   @JoinColumn(name="SUPPLIER", referencedColumnName="SUPPLIERID", nullable=false)
 	   public Supplier getSupplier(){

	       return supplier;
 	   }

	   public void setSupplier(Supplier supplier){
 	       this.supplier = supplier;

	   }
	   
	   @OneToOne
	   @JoinColumn(name="OPERATIONEXPENSES", referencedColumnName="CODEID", nullable=false)
 	   public AccountCode getOperationExpenses(){
 	       return operationExpenses;
	   }

 	   public void setOperationExpenses(AccountCode operationExpenses){
	       this.operationExpenses = operationExpenses;
 	   }

   }