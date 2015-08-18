
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;
import java.sql.Date;


 
       @Entity
	   @Table(name="MATERIALSPAYABLECREDITNOTE")
       public class MaterialsPayableCreditNote extends CreditNote implements Serializable{
	   
 
	   private SupplierInvoice supplierInvoice;
	   public MaterialsPayableCreditNote(){}

	   @ManyToOne
	   @JoinColumn(name="SUPPLIERINVOICE", referencedColumnName="SUPPLIERINVOICEID", nullable=false)
 	   public SupplierInvoice getSupplierInvoice(){

	       return supplierInvoice;
 	   }

	   public void setSupplierInvoice(SupplierInvoice supplierInvoice){
 	       this.supplierInvoice = supplierInvoice;

	   }

   }