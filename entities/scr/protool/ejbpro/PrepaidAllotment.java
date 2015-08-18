
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;

		
		@SqlResultSetMapping(name="resolution", 
		columns={@ColumnResult(name="PDATE"), @ColumnResult(name="ID"), @ColumnResult(name="TYPES")})
		@NamedNativeQueries({@NamedNativeQuery(name="ExpensesResolution", query="SELECT c.invoiceDate AS PDATE, b.expensesInvoiceId AS ID, c.dtype AS TYPES FROM PaidExpensesInvoice b, ExpensesInvoice c " +
		"WHERE c.expensesInvoiceId = b.expensesInvoiceId AND c.invoiceDate BETWEEN ?1 AND ?2 UNION SELECT f.paymentDate AS PDATE, f.accruedPaymentId AS ID, g.dtype AS TYPES FROM AccruedExpensesPayment f, ExpensesInvoice g " + 
		"WHERE f.expensesInvoiceId = g.expensesInvoiceId AND f.paymentDate BETWEEN ?1 AND ?2 UNION SELECT h.paymentDate AS PDATE, h.expensesPaymentId AS ID, i.dtype AS TYPES FROM PayableExpensesPayment h, ExpensesInvoice i " +
		"WHERE h.expensesInvoiceId = i.expensesInvoiceId AND h.paymentDate BETWEEN ?1 AND ?2 UNION SELECT a.paymentDate AS PDATE, a.prepaidId AS ID, d.dtype AS TYPES FROM PrepaidAllotment a, ExpensesInvoice d " +
		"WHERE a.expensesInvoiceId = d.expensesInvoiceId AND a.paymentDate BETWEEN ?1 AND ?2 ORDER BY PDATE DESC", resultSetMapping="resolution"),
		@NamedNativeQuery(name="ExpensesResolutionSupplier", query="SELECT c.invoiceDate AS PDATE, b.expensesInvoiceId AS ID, c.dtype AS TYPES FROM PaidExpensesInvoice b, ExpensesInvoice c " +
		"WHERE c.expensesInvoiceId = b.expensesInvoiceId AND c.invoiceDate BETWEEN ?1 AND ?2 AND c.supplier_supplierId = ?3 UNION SELECT f.paymentDate AS PDATE, f.accruedPaymentId AS ID, g.dtype AS TYPES FROM AccruedExpensesPayment f, ExpensesInvoice g " + 
		"WHERE f.expensesInvoiceId = g.expensesInvoiceId AND f.paymentDate BETWEEN ?1 AND ?2 AND g.supplier_supplierId = ?3 UNION SELECT h.paymentDate AS PDATE, h.expensesPaymentId AS ID, i.dtype AS TYPES FROM PayableExpensesPayment h, ExpensesInvoice i " +
		"WHERE h.expensesInvoiceId = i.expensesInvoiceId AND h.paymentDate BETWEEN ?1 AND ?2 AND i.supplier_supplierId = ?3 UNION SELECT a.paymentDate AS PDATE, a.prepaidId AS ID, d.dtype AS TYPES FROM PrepaidAllotment a, ExpensesInvoice d " +
		"WHERE a.expensesInvoiceId = d.expensesInvoiceId AND a.paymentDate BETWEEN ?1 AND ?2 AND d.supplier_supplierId = ?3 ORDER BY PDATE DESC", resultSetMapping="resolution"),
		@NamedNativeQuery(name="ExpensesResolutionInvoice", query="SELECT c.invoiceDate AS PDATE, b.expensesInvoiceId AS ID, c.dtype AS TYPES FROM PaidExpensesInvoice b, ExpensesInvoice c " +
		"WHERE c.expensesInvoiceId = b.expensesInvoiceId AND c.invoiceDate BETWEEN ?1 AND ?2 AND b.invoiceNumber = ?3 UNION SELECT f.paymentDate AS PDATE, f.accruedPaymentId AS ID, g.dtype AS TYPES FROM AccruedExpensesPayment f, ExpensesInvoice g " + 
		"WHERE f.expensesInvoiceId = g.expensesInvoiceId AND f.paymentDate BETWEEN ?1 AND ?2 AND f.invoiceNumber = ?3 UNION SELECT h.paymentDate AS PDATE, h.expensesPaymentId AS ID, i.dtype AS TYPES FROM PayableExpensesPayment h, ExpensesInvoice i, PayableExpensesInvoice p " +
		"WHERE h.expensesInvoiceId = i.expensesInvoiceId AND p.expensesInvoiceId = i.expensesInvoiceId AND h.paymentDate BETWEEN ?1 AND ?2 AND p.invoiceNumber = ?3 UNION SELECT a.paymentDate AS PDATE, a.prepaidId AS ID, d.dtype AS TYPES FROM PrepaidAllotment a, ExpensesInvoice d, PrepaidExpensesInvoice p " +
		"WHERE a.expensesInvoiceId = d.expensesInvoiceId AND p.expensesInvoiceId = d.expensesInvoiceId AND a.paymentDate BETWEEN ?1 AND ?2 AND p.invoiceNumber = ?3 ORDER BY PDATE DESC", resultSetMapping="resolution")
		})
		
       @Entity
	   @Table(name="PREPAIDALLOTMENT")
       public class PrepaidAllotment implements Serializable{
	   
 	   private PrepaidExpensesInvoice prepaidExpenses;
	   private int prepaidId;
	   private Journal journal;
	   private String period;
 	   private java.sql.Date paymentDate;
	   private Long version;

	   public PrepaidAllotment(){}

	   @TableGenerator(name="PREPAID_GENERATOR", table="Prepaid_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="Prepaid_ID",initialValue=0, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="PREPAID_GENERATOR")
	   @Column(name="PREPAIDID")
	   public int getPrepaidId(){
	       return prepaidId;
	    }

	   public void setPrepaidId(int prepaidId){
       this.prepaidId = prepaidId;
	   }
	   
	   @ManyToOne
	   @JoinColumn(name="EXPENSESINVOICEID", referencedColumnName="EXPENSESINVOICEID", nullable=false)
 	   public PrepaidExpensesInvoice getPrepaidExpenses(){

	       return prepaidExpenses;
 	   }

	   public void setPrepaidExpenses(PrepaidExpensesInvoice prepaidExpenses){
 	       this.prepaidExpenses = prepaidExpenses;

	   }
	   
	   @OneToOne 
	   @JoinColumn(name="JOURNAL", referencedColumnName="JOURNALID", nullable=false)
 	   public Journal getJournal(){
 	       return journal;
	   }

 	   public void setJournal(Journal journal){
	       this.journal = journal;
 	   }
	   
	   @Column(name="PAYMENTDATE")
	   public java.sql.Date getPaymentDate(){

 	       return paymentDate;
 	   }

	   public void setPaymentDate(java.sql.Date paymentDate){
 	       this.paymentDate = paymentDate;

	   }
	   
	   @Column(name="PERIOD")
	   public String getPeriod(){
		   return period;  
	   }
	   
	   public void setPeriod(String period){
			this.period = period;
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