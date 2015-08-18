
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;
import java.sql.Date;


 
       @Entity
	   @Table(name="EXPENSESPAYABLECREDITNOTE")
       public class ExpensesPayableCreditNote extends CreditNote implements Serializable{
	   
 	   
	   private PayableExpensesInvoice expensesPayableInvoice;
	   public ExpensesPayableCreditNote(){}

	   @ManyToOne
	   @JoinColumn(name="EXPENSESPAYABLEINVOICE", referencedColumnName="EXPENSESINVOICEID", nullable=false)	   
	   public PayableExpensesInvoice getExpensesPayableInvoice(){

 	        return expensesPayableInvoice;
 	   }

	   public void setExpensesPayableInvoice(PayableExpensesInvoice expensesPayableInvoice){
			 this.expensesPayableInvoice = expensesPayableInvoice;

	   }

   }