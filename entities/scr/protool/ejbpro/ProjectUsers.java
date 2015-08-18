
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;
 
       @Entity
	   @Table(name="PROJECTUSERS")
       public class ProjectUsers implements Serializable{
	   private int projectUsersId;
	   private String userName;
	   private String userPassword;
	   private boolean supGroup;
	   private boolean userActive;
	   private java.sql.Date issueDate;
	   private Long version;

	   public ProjectUsers(){}
	   
	   @TableGenerator(name="ProjectUsers_GENERATOR", table="ProjectUsers_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="ProjectUsers_ID",initialValue=0, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="ProjectUsers_GENERATOR")
	   @Column(name="PROJECTUSERSID")
	   public int getProjectUsersId(){
	       return projectUsersId;
	    }

	   public void setProjectUsersId(int projectUsersId){
       this.projectUsersId=projectUsersId;
	   }
	   
	   @Column(name="USERNAME")
	   public String getUserName(){
 	       return userName;
	   }

 	   public void setUserName(String userName){
	       this.userName = userName;
 	   }
	   
	   @Column(name="USERPASSWORD")
	   public String getUserPassword(){
 	       return userPassword;
	   }

 	   public void setUserPassword(String userPassword){
	       this.userPassword = userPassword;
 	   }
	   
	   @Column(name="SUPGROUP")
	   public boolean getSupGroup(){
 	       return supGroup;
	   }

 	   public void setSupGroup(boolean supGroup){
	       this.supGroup = supGroup;
 	   }
	   
	  @Column(name="USERACTIVE")
	  public boolean getUserActive(){
 	       return userActive;
	   }

 	   public void setUserActive(boolean userActive){
	       this.userActive = userActive;
 	   }
	   
	   @Column(name="ISSUEDATE")
	   public java.sql.Date getIssueDate(){

 	       return issueDate;
 	   }

	   public void setIssueDate(java.sql.Date issueDate){
 	       this.issueDate = issueDate;

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