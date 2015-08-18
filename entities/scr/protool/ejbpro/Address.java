
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;
import java.sql.Date;



 
       @Embeddable
       public class Address implements Serializable{

	   private String addressLine1;
	   private String addressLine2;
	   private String town_city;
	   private String county_state;
	   private String postCode;
	   private String country;

	   public Address(){}  
	   
	   @Column(name="ADDRESSLINE1")
	   public String getAddressLine1(){
 	       return addressLine1;
	   }

 	   public void setAddressLine1(String addressLine1){
	       this.addressLine1 = addressLine1;
 	   }
	   
	   @Column(name="ADDRESSLINE2")
	   public String getAddressLine2(){
 	       return addressLine2;
	   }

 	   public void setAddressLine2(String addressLine2){
	       this.addressLine2 = addressLine2;
 	   }
	   
	   @Column(name="TOWN_CITY")
	   public String getTownCity(){
 	       return town_city;
	   }

 	   public void setTownCity(String town_city){
	       this.town_city = town_city;
 	   }
	   
	   @Column(name="COUNTY_STATE")
	   public String getCountyState(){
 	       return county_state;
	   }

 	   public void setCountyState(String county_state){
	       this.county_state = county_state;
 	   }
	   
	   @Column(name="POSTCODE")
	   public String getPostCode(){
 	       return postCode;
	   }

 	   public void setPostCode(String postCode){
	       this.postCode = postCode;
 	  }
	  
	  @Column(name="COUNTRY")
	  public String getCountry(){
 	       return country;
	   }

 	   public void setCountry(String country){
	       this.country = country;
 	   }

    }