
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;




 
    @Entity
	@Table(name="ACCOUNTSGROUP")
    public class AccountsGroup implements Serializable{

	  
	   private int groupId;
	   private String category;
	   private java.sql.Date dateAdded;
	   private Long version;
	   private Collection<AccountCode> acccodes;

	   public AccountsGroup(){}
	   
		@Id
		@Column(name="GROUPID")
		public int getGroupId(){
			return groupId;
		}
 
		public void setGroupId(int groupId){
		this.groupId=groupId;
		}
	   
	   
	    @Column(name="CATEGORY")
		public String getCategory(){
			return category;
		}

		public void setCategory(String category){
			this.category = category;
		}
	   
	   @Column(name="DATEADDED")
		public java.sql.Date getAddDate(){
 	       return dateAdded;
		}

		public void setAddDate(java.sql.Date dateAdded){
	       this.dateAdded = dateAdded;
		}
	   
		@OneToMany(cascade=CascadeType.ALL, mappedBy = "group")
		public Collection<AccountCode> getAccCodes() {
			return acccodes;
		}
    
		public void setAccCodes(Collection<AccountCode> acccodes) {
			this.acccodes = acccodes;
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