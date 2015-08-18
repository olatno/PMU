
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;


       @Entity
	   @Table(name="INCOMING")
       public class Incoming implements Serializable{

	   private PaymentIn payment;
	   private Journal journal;
	   private java.sql.Date paymentInDate;
	   private int incomingId;
	   private String note;
	   private Long version;

	   public Incoming(){}

	  @TableGenerator(name="INCOMING_GENERATOR", table="Incoming_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="Incoming_ID",initialValue=2000, allocationSize=1)
	  @Id
	  @GeneratedValue(strategy=GenerationType.TABLE, generator="INCOMING_GENERATOR")
	  @Column(name="INCOMINGID")
	  public int getIncomingId(){
	       return incomingId;
	   }
 
	   public void setIncomingId(int incomingId){
       this.incomingId=incomingId;
	   }

	   @OneToOne 
	   @JoinColumn(name="JOURNAL", referencedColumnName="JOURNALID", nullable=false)
 	   public Journal getJournal(){
 	       return journal;
	   }

 	   public void setJournal(Journal journal){
	       this.journal = journal;
 	   }
	   
	   @Column(name="PAYMENTINDATE")
 	   public java.sql.Date getPaymentInDate(){

	       return paymentInDate;
 	   }

	   public void setPaymentInDate(java.sql.Date paymentInDate){
 	       this.paymentInDate = paymentInDate;

	   } 	   
	    
	   @Column(name="NOTE")
	   public String getNote(){
 	       return note;
	   }

 	   public void setNote(String note){
	       this.note = note;
 	   }
	   
	   @ManyToOne 
	   @JoinColumn(name="PAYMENT", referencedColumnName="PAYMENTINID", nullable=false) 
 	   public PaymentIn getPayment(){
 	       return payment;
	   }

 	   public void setPayment(PaymentIn payment){
	       this.payment = payment;
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