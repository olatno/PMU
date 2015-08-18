
package scr.protool.ejbpro;
import javax.persistence.*;

 
	   @MappedSuperclass
       public class Versioning {

	   private Long version;
	  
	   public Versioning(){}
	   

	   
	   @Version 
	   @Column(name="VERSION")
	   public Long getVersion(){

 	       return version;
 	   }

	   public void setVersion(Long version){
	       this.version = version;

	   }

    }