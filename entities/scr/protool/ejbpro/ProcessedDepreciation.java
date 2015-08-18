
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;



 
	@Entity
	@Table(name="PROCESSEDDEPRECIATION")
	public class ProcessedDepreciation implements Serializable{
		private Depreciation depreciation;
		private Journal journal;
		private int processedDepreciationId;
		private Long version;
			
		public ProcessedDepreciation(){}
		@TableGenerator(name="PROCESSEDDEPRECIATION_GENERATOR", table="ProcessedDepreciation_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="ProcessedDepreciation_ID",initialValue=0, allocationSize=1)
		@Id
		@GeneratedValue(strategy=GenerationType.TABLE, generator="PROCESSEDDEPRECIATION_GENERATOR")
		@Column(name="PROCESSEDDEPRECIATIONID")
		public int getProcessedDepreciationId(){
			return processedDepreciationId;
		}
 
		public void setProcessedDepreciationId(int processedDepreciationId){
			this.processedDepreciationId = processedDepreciationId;
		}
	
	   @OneToOne 
	   @JoinColumn(name="JOURNAL", referencedColumnName="JOURNALID", nullable=false)
 	   public Journal getJournal(){
 	       return journal;
	   }

 	   public void setJournal(Journal journal){
	       this.journal = journal;
 	   }
	   
	   @ManyToOne 
	   @JoinColumn(name="DEPRECIATION", referencedColumnName="DEPRECIATIONID", nullable=false) 
	   public Depreciation getDepreciation(){
			return depreciation;
	   }
	   
	   public void setDepreciation(Depreciation depreciation){
			this.depreciation = depreciation;
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
	   
	   
	   
	   