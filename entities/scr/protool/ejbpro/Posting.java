
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;
import java.math.*;



 
       @Entity
	   @Table(name="POSTING")
       public class Posting implements Serializable{

	  
	   private int postingId;
	   private Journal journal;
	   private BigDecimal amount;
	   private AccountCode codeId;
	   private Long version;
	   
	   public Posting(){}

	  @TableGenerator(name="POSTING_GENERATOR", table="Posting_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="Posting_ID",initialValue=0, allocationSize=1)
	  @Id
	  @GeneratedValue(strategy=GenerationType.TABLE, generator="POSTING_GENERATOR")
	  @Column(name="POSTINGID")
	  public int getPostingId(){
	       return postingId;
	   }
 
	   public void setPostingId(int postingId){
       this.postingId=postingId;
	   }
	   
	   @ManyToOne
	   @JoinColumn(name="JOURNAL", referencedColumnName="JOURNALID", nullable=false) 
 	   public Journal getJournal(){
 	       return journal;
	   }

 	   public void setJournal(Journal journal){
	       this.journal = journal;
 	   }
	   
	   @OneToOne 
	   @JoinColumn(name="CODEID", referencedColumnName="CODEID", nullable=false)
 	   public AccountCode getCodeId(){
 	       return codeId;
	   }

 	   public void setCodeId(AccountCode codeId){
	       this.codeId = codeId;
 	   }
	   
	   @Column(name="AMOUNT", precision=12, scale=2)
 	   public BigDecimal getAmount(){

	       return amount;
 	   }

	   public void setAmount(BigDecimal amount){
 	       this.amount = amount;

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