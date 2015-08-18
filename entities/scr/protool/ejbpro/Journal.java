
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;



 
       @Entity
	   @Table(name="JOURNAL")
       public class Journal implements Serializable{

	  
	   private int journalId;
	   private String codeDR;
	   private String codeCR;
	   private boolean postManual;
	   private java.sql.Date postingDate;
	   private Collection<Posting> postingdrcr;
	   private Long version;
	   
	   public Journal(){}

	  @TableGenerator(name="JOURNAL_GENERATOR", table="Journal_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="Journal_ID",initialValue=0, allocationSize=1)
	  @Id
	  @GeneratedValue(strategy=GenerationType.TABLE, generator="JOURNAL_GENERATOR")
	  @Column(name="JOURNALID")
	  public int getJournalId(){
	       return journalId;
	   }
 
	   public void setJournalId(int journalId){
       this.journalId=journalId;
	   }
	   
	   @Column(name="CODECR")
 	   public String getCodeCR(){
 	       return codeCR;
	   }

 	   public void setCodeCR(String codeCR){
	       this.codeCR = codeCR;
 	   }
	   
	   @Column(name="CODEDR")
 	   public String getCodeDR(){
 	       return codeDR;
	   }

 	   public void setCodeDR(String codeDR){
	       this.codeDR = codeDR;
 	   }
	   
	   @OneToMany(cascade=CascadeType.ALL, mappedBy = "journal")
       public Collection<Posting> getPostingdrcr() {
			return postingdrcr;
		}
    
       public void setPostingdrcr(Collection<Posting> postingdrcr) {
			this.postingdrcr = postingdrcr;
		}
		
	  @Column(name="POSTMANUAL")
	   public boolean getPostManual(){
 	       return postManual;
	   }

 	   public void setPostManual(boolean postManual){
	       this.postManual = postManual;
 	   }
	   
	  @Column(name="POSTINGDATE")
 	  public java.sql.Date getPostingDate(){

 	       return postingDate;
 	   }

	   public void setPostingDate(java.sql.Date postingDate){
 	       this.postingDate = postingDate;

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