
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;


 
       @Entity
	   @Table(name="PAIDEXPENSESINVOICE")
       public class PaidExpensesInvoice extends ExpensesInvoice implements Serializable{
			private String invoiceNumber;

			private AccountCode paymentMethod;
			
			public PaidExpensesInvoice(){}
			
	  @Column(name="INVOICENUMBER")
      public String getInvoiceNumber(){

 	        return invoiceNumber;
 	   }

	   public void setInvoiceNumber(String invoiceNumber){
		 this.invoiceNumber = invoiceNumber;

	   }
		  
	   @OneToOne 
	   @JoinColumn(name="PAYMENTMETHOD", referencedColumnName="CODEID", nullable=false)
 	   public AccountCode getPaymentMethod(){
 	       return paymentMethod;
	   }

 	   public void setPaymentMethod(AccountCode paymentMethod){
	       this.paymentMethod = paymentMethod;
 	   }	

	}