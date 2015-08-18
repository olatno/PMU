
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;
import java.math.*;


 
       @Entity
       public class AccruedExpensesInvoice extends ExpensesInvoice implements Serializable{
	   
 	   private String note;
	   private boolean invoiceReceived;
	   private BigDecimal amount;

	   public AccruedExpensesInvoice(){}
		
	   @Column(name="NOTE")
	   public String getNote(){
		return note;
		}
		
		public void setNote(String note){
			this.note = note;
		}
		
		@Column(name="INVOICERECEIVED")
		public boolean getInvoiceReceived(){
 	       return invoiceReceived;
	   }

 	   public void setInvoiceReceived(boolean invoiceReceived){
	       this.invoiceReceived = invoiceReceived;
 	   }
	   
	   @Column(name="AMOUNT", precision=12, scale=2)
 	   public BigDecimal getAmount(){

	       return amount;
 	   }

	   public void setAmount(BigDecimal amount){
 	       this.amount = amount;

	   }
	   

	}