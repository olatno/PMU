
package scr.protool.ejbpro;
import javax.persistence.*;
import java.io.Serializable;

 
	@Entity
	@Table(name="ASSETDISPOSAL")
	public class AssetDisposal implements Serializable{
		private Asset asset;
	    private Journal journal;
		private int assetDisposalId;
		private String disposalType;
		private java.sql.Date disposalDate;
		private Long version;
			
		public AssetDisposal(){}
		@TableGenerator(name="ASSETDISPOSAL_GENERATOR", table="AssetDisposal_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="AssetDisposal_ID",initialValue=0, allocationSize=1)
		@Id
		@GeneratedValue(strategy=GenerationType.TABLE, generator="ASSETDISPOSAL_GENERATOR")
		@Column(name="ASSETDISPOSALID")
		public int getAssetDisposalId(){
			return assetDisposalId;
		}
 
		public void setAssetDisposalId(int assetDisposalId){
			this.assetDisposalId = assetDisposalId;
		}
	
	   @OneToOne 
	   @JoinColumn(name="JOURNAL", referencedColumnName="JOURNALID", nullable=false)
 	   public Journal getJournal(){
 	       return journal;
	   }

 	   public void setJournal(Journal journal){
	       this.journal = journal;
 	   }
	   
	  @OneToOne
	  @JoinColumn(name="ASSET", referencedColumnName="ASSETID", nullable=false)
	   public Asset getAsset(){
			return asset;
	   }
	   
	   public void setAsset(Asset asset){
			this.asset = asset;
	   }
	   
	   @Column(name="DISPOSALDATE")
	   public java.sql.Date getDisposalDate(){

 	       return disposalDate;
 	   }

	   public void setDisposalDate(java.sql.Date disposalDate){
 	       this.disposalDate = disposalDate;

	   }
	   
	   	@Column(name="DISPOSALTYPE")
	   public String getDisposalType(){
			return disposalType;
	   }
	   
	   public void setDisposalType(String disposalType){
			this.disposalType = disposalType;
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
	   
	   
	   
	   