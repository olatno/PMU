
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;

 
       @Entity
	   @Table(name="QUOTE")
       public class Quote implements Serializable{

	  
	   private int quoteId;
	   private Project project;
	   private java.sql.Date dateValid;
	   private Collection<Quoteline> quotelines;
	   private Long version;

	   public Quote(){}

	
	  @TableGenerator(name="QUOTE_GENERATOR", table="Quote_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="Quote_ID",initialValue=1, allocationSize=1)
	  @Id
	  @GeneratedValue(strategy=GenerationType.TABLE, generator="QUOTE_GENERATOR")
	  @Column(name="QUOTEID")
	  public int getQuoteId(){
	       return quoteId;
	   }
 
	   public void setQuoteId(int quoteId){
       this.quoteId=quoteId;
	   }

	   @OneToOne 
	   @JoinColumn(name="PROJECT", referencedColumnName="PROJECTID", nullable=false)
 	   public Project getProject(){
 	       return project;
	   }

 	   public void setProject(Project project){
	       this.project = project;
 	   }
	   
	  @Column(name="DATEVALID") 
	  public java.sql.Date getDateValid(){

 	       return dateValid;
 	   }

	   public void setDateValid(java.sql.Date dateValid){
 	       this.dateValid = dateValid;

	   }

	   @OneToMany(cascade=CascadeType.ALL, mappedBy = "quotes")
       public Collection<Quoteline> getQuotelines() {
			return quotelines;
		}
    
       public void setQuotelines(Collection<Quoteline> quotelines) {
			this.quotelines = quotelines;
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