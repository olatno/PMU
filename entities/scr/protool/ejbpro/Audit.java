
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;



 
       @Entity
	   @Table(name = "AUDIT")
       public class Audit implements Serializable{

	  
	   private int auditId;
	   private String reference;
	   private ProjectUsers projectUsers;
	   private java.util.Calendar datetimeField;
	   private String amountSPan;
	   private Long version;
    



	   public Audit(){}

	
	  @TableGenerator(name="AUDIT_GENERATOR", table="Audit_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="Audit_ID",initialValue=1, allocationSize=1)
	  @Id
	  @GeneratedValue(strategy=GenerationType.TABLE, generator="AUDIT_GENERATOR")
	  @Column(name="AUDITID")
	  public int getAuditId(){
	       return auditId;
	   }
 
	   public void setAuditId(int auditId){
       this.auditId = auditId;
	   }
	   
	   @Column(name = "DATETIMEFIELD")
	   @Temporal(TemporalType.TIMESTAMP)
	   public java.util.Calendar getDatetimeField(){
		 return datetimeField;
	   }
	   
	   public void setDatetimeField(java.util.Calendar datetimeField){
		  this.datetimeField = datetimeField;
	   }
	   
	   @Column(name="REFERENCE")
 	   public String getReference(){
 	       return reference;
	   }

 	   public void setReference(String reference){
	       this.reference = reference;
 	   }
	   
	   @Column(name="AMOUNTSPAN")
 	   public String getAmountSPan(){
 	       return amountSPan;
	   }

 	   public void setAmountSPan(String amountSPan){
	       this.amountSPan = amountSPan;
 	   }

	   @OneToOne 
	   @JoinColumn(name="PROJECTUSERS", referencedColumnName="PROJECTUSERSID", nullable=false)
 	   public ProjectUsers getProjectUsers(){
 	       return projectUsers;
	   }

 	   public void setProjectUsers(ProjectUsers projectUsers){
	       this.projectUsers = projectUsers;
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