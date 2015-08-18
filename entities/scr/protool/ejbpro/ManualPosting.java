
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;



 
       @Entity
	   @Table(name="MANUALPOSTING")
       public class ManualPosting implements Serializable{

	  
	   private int manualPostingId;
	   private AccountCode principleCode;
	   private Journal journal;
 	   private java.sql.Date postDate;
	   private String postNote;
	   private Long version;
	   
	   public ManualPosting(){}

	   @TableGenerator(name="MANUALPOSTING_GENERATOR", table="ManualPosting_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="ManualPosting_ID",initialValue=0, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="MANUALPOSTING_GENERATOR")
	   
	   @Column(name="MANUALPOSTINGID")
	   public int getManualPostingId(){
	       return manualPostingId;
	    }

	   public void setManualPostingId(int manualPostingId){
       this.manualPostingId = manualPostingId;
	   }
	  
	  @OneToOne
	  @JoinColumn(name="PRINCIPLECODE", referencedColumnName="CODEID")
	  public AccountCode getPrincipleCode(){

	       return principleCode;
 	   }

	   public void setPrincipleCode(AccountCode principleCode){
 	       this.principleCode = principleCode;

	   }
	   
	   
	   @OneToOne 
	   @JoinColumn(name="JOURNAL", referencedColumnName="JOURNALID", nullable=false)
 	   public Journal getJournal(){
 	       return journal;
	   }

 	   public void setJournal(Journal journal){
	       this.journal = journal;
 	   }

	   @Column(name="POSTNOTE")
	   public String getPostNote(){
		return postNote;
	   }
		public void setPostNote(String postNote){
		
			this.postNote = postNote;
	   }
	   
	   @Column(name="POSTDATE")
 	   public java.sql.Date getPostDate(){

 	       return postDate;
 	    }

	    public void setPostDate(java.sql.Date postDate){
 	       this.postDate = postDate;

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