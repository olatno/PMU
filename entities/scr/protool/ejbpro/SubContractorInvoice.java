
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;

 
       @Entity
	   @Table(name="SUBCONTRACTORINVOICE")
       public class SubContractorInvoice implements Serializable{

	  
	   private int subContractorInvoiceId;
 	   private SubContractor subcontractor;
 	   private Journal journal;
 	   private Project project;
 	   private java.sql.Date invoiceDate;
	   private String invoiceNumber;
	   private Collection<SubContractorPayment> subContractorPayment;
	   private Long version;
	   
	   public SubContractorInvoice(){}

	   @TableGenerator(name="SUBCONTRACTORINVOICE_GENERATOR", table="SubContractorInvoice_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="SubContractorInvoice_ID",initialValue=0, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="SUBCONTRACTORINVOICE_GENERATOR")
	   @Column(name="SUBCONTRACTORINVOICEID")
	   public int getSubContractorInvoiceId(){
	       return subContractorInvoiceId;
	    }

	   public void setSubContractorInvoiceId(int subContractorInvoiceId){
       this.subContractorInvoiceId=subContractorInvoiceId;
	   }
	   
	   @ManyToOne
	   @JoinColumn(name="SUBCONTRACTOR", referencedColumnName="SUBCONTRACTORID", nullable=false)
 	   public SubContractor getSubcontractor(){

	       return subcontractor;
 	   }

	   public void setSubcontractor(SubContractor subcontractor){
 	       this.subcontractor = subcontractor;

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
	   
	  @Column(name="INVOICEDATE")
 	  public java.sql.Date getInvoiceDate(){

 	       return invoiceDate;
 	   }

	   public void setInvoiceDate(java.sql.Date invoiceDate){
 	       this.invoiceDate = invoiceDate;

	   }

	   @Column(name="INVOICENUMBER")
 	   public String getInvoiceNumber(){

 	       return invoiceNumber;
 	   }

	   public void setInvoiceNumber(String invoiceNumber){
	       this.invoiceNumber = invoiceNumber;

	   }
	   
	   @OneToMany(cascade=CascadeType.ALL, mappedBy="subContractorInvoice")
 	   public Collection<SubContractorPayment> getSubContractorPayment(){
	       return subContractorPayment;
 	   }

	   public void setSubContractorPayment(Collection<SubContractorPayment> subContractorPayment){
 	       this.subContractorPayment = subContractorPayment;
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