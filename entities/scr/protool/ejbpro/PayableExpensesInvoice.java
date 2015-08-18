
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.Collection;
import java.io.Serializable;


 
       @Entity
	   @Table(name="PAYABLEEXPENSESINVOICE")
       public class PayableExpensesInvoice extends ExpensesInvoice implements Serializable{

	   private String invoiceNumber;
	   private Collection<PayableExpensesPayment> payableExpensesPayment;
	   private Collection<ExpensesPayableCreditNote> expensesPayableCreditNote;
	   
	   public PayableExpensesInvoice(){}

	   @Column(name="INVOICENUMBER")	
	   public String getInvoiceNumber(){

 	        return invoiceNumber;
 	   }

	   public void setInvoiceNumber(String invoiceNumber){
			 this.invoiceNumber = invoiceNumber;

	   }
	   
	   @OneToMany(cascade=CascadeType.ALL, mappedBy="expensesPayableInvoice")
 	   public Collection<PayableExpensesPayment> getPayableExpensesPayment(){
	       return payableExpensesPayment;
 	   }

	   public void setPayableExpensesPayment(Collection<PayableExpensesPayment> payableExpensesPayment){
 	       this.payableExpensesPayment = payableExpensesPayment;
	   }
	   
	   @OneToMany(cascade=CascadeType.ALL, mappedBy="expensesPayableInvoice")
 	   public Collection<ExpensesPayableCreditNote> getExpensesPayableCreditNote(){
	       return expensesPayableCreditNote;
 	   }

	   public void setExpensesPayableCreditNote(Collection<ExpensesPayableCreditNote> ExpensesPayableCreditNote){
 	       this.expensesPayableCreditNote = expensesPayableCreditNote;
	   }

   }