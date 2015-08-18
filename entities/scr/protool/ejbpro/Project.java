
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;


 
       @Entity
       @Table(name="PROJECT")
       public class Project implements Serializable{

	  
	   private int projectId;
 	   private String projectName;
 	   private String paymentTerm;
 	   private java.sql.Date startDate;
 	   private ProjectStatus status;
	   private Collection<SupplierInvoice> supplierInvoice;
	   private Collection<SubContractorInvoice> subContractorInvoice;
 	   private Customer customer;
	   private SiteAddress siteAddress;
	   private Long version;

	   
	   public Project(){}

	   @TableGenerator(name="PROJECT_GENERATOR", table="Project_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="PROTOOL_ID",initialValue=10000, allocationSize=1)
	   @Id
 	  @GeneratedValue(strategy=GenerationType.TABLE, generator="PROJECT_GENERATOR")
	  @Column(name="PROJECTID")
	  public int getProjectId(){
	       return projectId;
	   }

	   public void setProjectId(int projectId){
       this.projectId = projectId;
	   }

 	   @Column(name="PROJECTNAME")
 	   public String getProjectName(){

	       return projectName;
 	   }

	   public void setProjectName(String projectName){
 	       this.projectName = projectName;

	   }
	   @Column(name="PAYMENTTERM")
	   public String getPaymentTerm(){

 	       return paymentTerm;
 	   }

	   public void setPaymentTerm(String paymentTerm){
	       this.paymentTerm = paymentTerm;

	   }
	   
	   @ManyToOne
	   @JoinColumn(name="CUSTOMER",referencedColumnName="CUSTOMERID", nullable=false) 
 	   public Customer getCustomer(){
 	       return customer;
	   }

 	   public void setCustomer(Customer customer){
	       this.customer = customer;
 	   }

 
 	   @Column(name="STARTDATE")
 	   public java.sql.Date getStartDate(){

 	       return startDate;
 	   }

	   public void setStartDate(java.sql.Date startDate){
 	       this.startDate = startDate;

	   }

 	   @Column(name="STATUS")
 	   @Enumerated(EnumType.STRING)  
 	   public ProjectStatus getStatus(){
 	       return status;
 	   }

	   public void setStatus(ProjectStatus status){
 	       this.status = status;
 	   }
	   
	   @OneToMany(cascade=CascadeType.ALL, mappedBy="project")
 	   public Collection<SupplierInvoice> getSupplierInvoice(){
	       return supplierInvoice;
 	   }

	   public void setSupplierInvoice(Collection<SupplierInvoice> supplierInvoice){
 	       this.supplierInvoice = supplierInvoice;
	   }
	   
	   @OneToMany(cascade=CascadeType.ALL, mappedBy="project")
 	   public Collection<SubContractorInvoice> getSubContractorInvoice(){
	       return subContractorInvoice;
 	   }

	   public void setSubContractorInvoice(Collection<SubContractorInvoice> subContractorInvoice){
 	       this.subContractorInvoice = subContractorInvoice;
	   }
	   
	   @OneToOne 
	   @JoinColumn(name="SITEADDRESS", referencedColumnName="SITEADDRESSID", nullable=false)
	   public SiteAddress getSiteAddress(){
			return siteAddress;
	   }
	   
	   public void setSiteAddress(SiteAddress siteAddress){
			this.siteAddress = siteAddress;
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