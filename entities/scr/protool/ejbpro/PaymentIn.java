
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;



 
       @Entity
	   @Table(name="PAYMENTIN")
       public class PaymentIn implements Serializable{

	  
	   private Journal journal;
	   private Project project;
 	   private String invoice;
	   private String paymentTerm;
	   private Collection<Incoming> incoming;
	   private int paymentInId;
	   private java.sql.Date invoiceDate;
	   private Long version;
	   
	   public PaymentIn(){}

	  @TableGenerator(name="PAYMENTIN_GENERATOR", table="PaymentIn_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="PaymentIn_ID",initialValue=20000, allocationSize=1)
	 @Column(name="PAYMENTINID")
	 @Id
	  @GeneratedValue(strategy=GenerationType.TABLE, generator="PAYMENTIN_GENERATOR")
	  public int getpaymentInId(){
	       return paymentInId;
	   }
 
	   public void setpaymentInId(int paymentInId){
       this.paymentInId=paymentInId;
	   }
	   
	   @OneToOne 
	   @JoinColumn(name="JOURNAL", referencedColumnName="JOURNALID", nullable=false)
 	   public Journal getJournal(){
 	       return journal;
	   }

 	   public void setJournal(Journal journal){
	       this.journal = journal;
 	   }
	   
	   @OneToOne 
	   @JoinColumn(name="PROJECT", referencedColumnName="PROJECTID", nullable=false)
 	   public Project getProject(){
 	       return project;
	   }

 	   public void setProject(Project project){
	       this.project = project;
 	   }
	   
	  @Column(name="INVOICE")
 	   public String getInvoice(){

	       return invoice;
 	   }

	   public void setInvoice(String invoice){
 	       this.invoice = invoice;

	   } 
	   
	   @Column(name="INVOICEDATE")
 	   public java.sql.Date getInvoiceDate(){

	       return invoiceDate;
 	   }

	   public void setInvoiceDate(java.sql.Date invoiceDate){
 	       this.invoiceDate = invoiceDate;

	   } 
	   
	   @Column(name="PAYMENTTERM")
	   public String getPaymentTerm(){

 	       return paymentTerm;
 	   }

	   public void setPaymentTerm(String paymentTerm){
	       this.paymentTerm = paymentTerm;

	   }

 	   @OneToMany(cascade=CascadeType.ALL, mappedBy="payment")
 	   public Collection<Incoming> getIncoming(){
	       return incoming;
 	   }

	   public void setIncoming(Collection<Incoming> incoming){
 	       this.incoming = incoming;
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