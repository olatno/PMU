
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;

 
       @Entity
	   @Table(name="PREPAIDEXPENSESINVOICE")
       public class PrepaidExpensesInvoice extends ExpensesInvoice implements Serializable{
	   
	   private int period;
	   private AccountCode paymentMethod;
	   private String invoiceNumber;
	   private Collection<PrepaidAllotment> prepaidAllotment;
	   
	   public PrepaidExpensesInvoice(){}

	   @OneToOne 
	   @JoinColumn(name="PAYMENTMETHOD", referencedColumnName="CODEID", nullable=false)
 	   public AccountCode getPaymentMethod(){
 	       return paymentMethod;
	   }

 	   public void setPaymentMethod(AccountCode paymentMethod){
	       this.paymentMethod = paymentMethod;
 	   }
	   
	   @Column(name="INVOICENUMBER")
	   public String getInvoiceNumber(){
 	       return invoiceNumber;
 	    }

	   public void setInvoiceNumber(String invoiceNumber){
		   this.invoiceNumber = invoiceNumber;

	   }
	   
	   @Column(name="PERIOD")
	   public int getPeriod(){
			return period;
		}
		
		public void setPeriod(int period){
			this.period = period;
		}
		
	  @OneToMany(cascade=CascadeType.ALL, mappedBy="prepaidExpenses")
 	   public Collection<PrepaidAllotment> getPrepaidAllotment(){
	       return prepaidAllotment;
 	   }

	   public void setPrepaidAllotment(Collection<PrepaidAllotment> prepaidAllotment){
 	       this.prepaidAllotment = prepaidAllotment;
	   }
		
   }