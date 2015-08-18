
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;


 
       @Entity
	   @Table(name="SUPPLIERINVOICE")
       public class SupplierInvoice implements Serializable{

	  
	   private int supplierInvoiceId;
 	   private Supplier supplier;
 	   private Journal journal;
 	   private Project project;
 	   private java.sql.Date invoiceDate;
	   private String invoiceNumber;
	   private Collection<SupplierPayment> supplierPayment;
	   private Collection<MaterialsPayableCreditNote> creditNotePayment;
	   private Long version;
	   
	   public SupplierInvoice(){}

	   @TableGenerator(name="SUPPLIERINVOICE_GENERATOR", table="SupplierInvoice_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="SupplierInvoice_ID",initialValue=0, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="SUPPLIERINVOICE_GENERATOR")
	   @Column(name="SUPPLIERINVOICEID")
	   public int getSupplierInvoiceId(){
	       return supplierInvoiceId;
	    }

	   public void setSupplierInvoiceId(int supplierInvoiceId){
       this.supplierInvoiceId=supplierInvoiceId;
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
	   @JoinColumn(name="JOURNAL", referencedColumnName="JOURNALID", nullable=false)
 	   public Journal getJournal(){
 	       return journal;
	   }

 	   public void setJournal(Journal journal){
	       this.journal = journal;
 	   }
	   
	   @ManyToOne
	   @JoinColumn(name="PROJECT", referencedColumnName="PROJECTID", nullable=false)
	   public Project getProject(){

	       return project;
 	   }

 	   public void setProject(Project project){
 	       this.project = project;
 	   }

 	  public java.sql.Date getInvoiceDate(){

 	       return invoiceDate;
 	   }

	   public void setInvoiceDate(java.sql.Date invoiceDate){
 	       this.invoiceDate = invoiceDate;

	   }


 	   public String getInvoiceNumber(){

 	       return invoiceNumber;
 	   }

	   public void setInvoiceNumber(String invoiceNumber){
	       this.invoiceNumber = invoiceNumber;

	   }
	   	   
	   @OneToMany(cascade=CascadeType.ALL, mappedBy="supplierInvoice")
 	   public Collection<SupplierPayment> getSupplierPayment(){
	       return supplierPayment;
 	   }

	   public void setSupplierPayment(Collection<SupplierPayment> supplierPayment){
 	       this.supplierPayment = supplierPayment;
	   }
	   
	   @OneToMany(cascade=CascadeType.ALL, mappedBy="supplierInvoice")
 	   public Collection<MaterialsPayableCreditNote> getMaterialsPayableCreditNote(){
	       return creditNotePayment;
 	   }

	   public void setMaterialsPayableCreditNote(Collection<MaterialsPayableCreditNote> creditNotePayment){
 	       this.creditNotePayment = creditNotePayment;
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