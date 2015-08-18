
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;



 
       @Entity
	   @Table(name="QUOTELINE")
       public class Quoteline implements Serializable{

	  
	   private int quotelineId;
	   private Quote quotes;
 	   private String description;
	   private BigDecimal qty;
	   private BigDecimal unitPrice;
	   private Long version;

	   public Quoteline(){}

	
	  @TableGenerator(name="QUOTELINE_GENERATOR", table="Quoteline_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="Quoteline_ID",initialValue=1, allocationSize=1)
	  @Id
	  @GeneratedValue(strategy=GenerationType.TABLE, generator="QUOTELINE_GENERATOR")
	  @Column(name="QUOTELINEID")
	  public int getQuotelineId(){
	       return quotelineId;
	   }
 
	   public void setQuotelineId(int quotelineId){
			this.quotelineId=quotelineId;
	   }
	   
	   @Column(name="DESCRIPTION")
 	   public String getDescription(){

 	       return description;
 	   }

	   public void setDescription(String description){
 	       this.description = description;

	   }
	   
	   @Column(name="QTY", precision=12, scale=3)
	   public BigDecimal getQty(){

 	       return qty;
 	   }

	   public void setQty(BigDecimal qty){
 	       this.qty = qty;

	   }
	   
	   @Column(name="UNITPRICE", precision=12, scale=2)
	   public BigDecimal getUnitPrice(){

 	       return unitPrice;
 	   }

	   public void setUnitPrice(BigDecimal unitPrice){
 	       this.unitPrice = unitPrice;

	   }

	   @ManyToOne
	   @JoinColumn(name="QUOTES", referencedColumnName="QUOTEID", nullable=false)
 	   public Quote getQuotes(){
 	       return quotes;
	   }

 	   public void setQuotes(Quote quotes){
	       this.quotes = quotes;
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