
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;



 
       @Entity
	   @Table(name="ACCOUNTCODE")
       public class AccountCode implements Serializable{

	   private AccountsGroup group;
	   private int codeId;
	   private Long version;
	   private boolean manualPosting;
 	   private String codeName;
	   private java.sql.Date addDate;
	   private String active;

	   public AccountCode(){}


	 @Id
	 @Column(name="CODEID")
	  public int getCodeId(){
	       return codeId;
	   }
 
	   public void setCodeId(int codeId){
       this.codeId=codeId;
	   }
	   
	  @Column(name="MANUALPOSTING")
	  public boolean getManualPosting(){
	       return manualPosting;
	   }
 
	   public void setManualPosting(boolean manualPosting){
       this.manualPosting = manualPosting;
	  }

	   @Column(name="CODENAME")
 	   public String getCodeName(){

	       return codeName;
 	   }

	   public void setCodeName(String codeName){
 	       this.codeName = codeName;

	   } 
		
	   @Column(name="ACTIVE")
 	   public String getActive(){

	       return active;
 	   }

	   public void setActive(String active){
 	       this.active = active;

	   } 
	   
	   @ManyToOne	   
	 //  @JoinColumn(name="GROUP", referencedColumnName="GROUPID", nullable=false)
	   @JoinColumn(name="GROUPS", referencedColumnName="GROUPID", nullable=false) 
 	   public AccountsGroup getGroup(){
 	       return group;
	   }

 	   public void setGroup(AccountsGroup group){
	       this.group = group;
 	   }
	   
	   @Column(name="ADDDATE")
	   public java.sql.Date getAddDate(){
 	       return addDate;
	   }

 	   public void setAddDate(java.sql.Date addDate){
	       this.addDate = addDate;
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