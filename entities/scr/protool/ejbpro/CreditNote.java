
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;



 
       @Entity
	   @Inheritance(strategy=InheritanceType.JOINED)
	   @Table(name="CREDITNOTE")
       public abstract class CreditNote extends Versioning implements Serializable{
	   private int creditNoteId;
	   private BigDecimal amount;
	   private String cnnumber;
	   private java.sql.Date issueDate;
	   private String dtype;
	   private BigDecimal vat;

	   public CreditNote(){}
	   
	   @TableGenerator(name="CREDITNOTE_GENERATOR", table="CreditNote_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="CreditNote_ID",initialValue=0, allocationSize=1)
	   @Id
 	   @GeneratedValue(strategy=GenerationType.TABLE, generator="CREDITNOTE_GENERATOR")
	   @Column(name="CREDITNOTEID")
	   public int getCreditNoteId(){
	       return creditNoteId;
	    }

	   public void setCreditNoteId(int creditNoteId){
       this.creditNoteId=creditNoteId;
	   }
	   
	   @Column(name="AMOUNT", precision=5, scale=2)
	   public BigDecimal getAmount(){
 	       return amount;
	   }

 	   public void setAmount(BigDecimal amount){
	       this.amount = amount;
 	   }
	   
	   @Column(name="VAT", precision=5, scale=2)
	   public BigDecimal getVat(){
 	       return vat;
	   }

 	   public void  setVat(BigDecimal vat){
	       this.vat = vat;
 	   }
	   
	   @Column(name="CNNUMBER")
	   public String getCNnumber(){
 	       return cnnumber;
	   }

 	   public void setCNnumber(String cnnumber){
	       this.cnnumber = cnnumber;
 	   }
	   
	   @Column(name="ISSUEDATE")
	   public java.sql.Date getIssueDate(){

 	       return issueDate;
 	   }

	   public void setIssueDate(java.sql.Date issueDate){
 	       this.issueDate = issueDate;

	   }
	  
	   @Column(name="DTYPE", insertable=false, updatable=false)
 	   public String getDtype(){

 	       return dtype;
 	   }

	   public void setDtype(String dtype){
	       this.dtype = dtype;

	   }

    }