
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;

 
       @Entity
	   @Table(name="USERMODEL")
       public class UserModel implements Serializable{

	  
	   private int userModelId;
	   private ProjectUsers projectUser;
	   private Collection<ModelType> modelType;
	   private Long version;

	   public UserModel(){}

	
	  @TableGenerator(name="USERMODEL_GENERATOR", table="UserModel_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="UserModel_ID",initialValue=1, allocationSize=1)
	  @Id
	  @GeneratedValue(strategy=GenerationType.TABLE, generator="USERMODEL_GENERATOR")
	  @Column(name="USERMODELID")
	  public int getUserModelId(){
	       return userModelId;
	   }
 
	   public void setUserModelId(int userModelId){
       this.userModelId = userModelId;
	   }

	   @OneToOne 
	   @JoinColumn(name="PROJECTUSER", referencedColumnName="PROJECTUSERSID", nullable=false)
 	   public ProjectUsers getProjectUser(){
 	       return projectUser;
	   }

 	   public void setProjectUser(ProjectUsers projectUser){
	       this.projectUser = projectUser;
 	   }

	   @OneToMany(cascade=CascadeType.ALL, mappedBy = "userModel")
       public Collection<ModelType> getModelType() {
			return modelType;
		}
    
       public void setModelType(Collection<ModelType> modelType) {
			this.modelType = modelType;
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