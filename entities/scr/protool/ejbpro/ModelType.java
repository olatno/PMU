
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;
//import javax.persistence.FetchType;



 
       @Entity
	   @Table(name="MODELTYPE")
       public class ModelType implements Serializable{

	  
	   private int modelTypeId;
	   private UserModel userModel;
	   private String modelStringType;
	   private Long version;

	   public ModelType(){}

	
	  @TableGenerator(name="MODELTYPE_GENERATOR", table="ModelType_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="ModelType_ID",initialValue=1, allocationSize=1)
	  @Id
	  @GeneratedValue(strategy=GenerationType.TABLE, generator="MODELTYPE_GENERATOR")
	  @Column(name="MODELTYPEID")
	  public int getModelTypeId(){
	       return modelTypeId;
	   }
 
	   public void setModelTypeId(int modelTypeId){
       this.modelTypeId = modelTypeId;
	   }
 
 	   @ManyToOne
	   @JoinColumn(name="USERMODEL", referencedColumnName="USERMODELID", nullable=false)
 	   public UserModel getUserModel(){
 	       return userModel;
	   }

 	   public void setUserModel(UserModel userModel){
	       this.userModel = userModel;
 	   }
	   
	   @Column(name="MODELSTRINGTYPE")
	   public String getModelStringType(){
			return modelStringType;
	   }
	   
	   public void setModelStringType(String modelStringType){
	       this.modelStringType = modelStringType;
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