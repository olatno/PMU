
package scr.protool.ejbpro;
import javax.persistence.*;
import java.util.*;
import java.io.Serializable;
import java.math.BigDecimal;



 
	@Entity
	@Table(name="DEPRECIATION")
	public class Depreciation implements Serializable{
		private Asset asset;
		private int depreciationId;
		private BigDecimal residual;
		private String lifeSpan;
		private String rate;
		private Collection<ProcessedDepreciation> processedDepreciation;
		private String depType;
		private Long version;
			
		public Depreciation(){}
		@TableGenerator(name="DEPRECIATION_GENERATOR", table="Depreciation_Table", pkColumnName="TABLE_SEQUENCE", valueColumnName="VALUE_COLUMN", pkColumnValue="Depreciation_ID",initialValue=0, allocationSize=1)
		@Id
		@GeneratedValue(strategy=GenerationType.TABLE, generator="DEPRECIATION_GENERATOR")
		@Column(name="DEPRECIATIONID")
		public int getDepreciationId(){
			return depreciationId;
		}
 
		public void setDepreciationId(int depreciationId){
			this.depreciationId = depreciationId;
		}
	
		@OneToOne
	   @JoinColumn(name="ASSET", referencedColumnName="ASSETID", nullable=false)
	   public Asset getAsset(){
			return asset;
	   }
	   
	   public void setAsset(Asset asset){
			this.asset = asset;
	   }
	   
	   @Column(name="RESIDUAL", precision=12, scale=2)
	   public BigDecimal getResidual(){
 	       return residual;
	   }

 	   public void setResidual(BigDecimal residual){
	       this.residual = residual;
 	   }
	   
	   @Column(name="LIFESPAN")
 	   public String getLifeSpan(){

	       return lifeSpan;
 	   }

	   public void setLifeSpan(String lifeSpan){
 	       this.lifeSpan = lifeSpan;

 	   }

	   @Column(name="DEPTYPE")
 	   public String getDepType(){

 	       return depType;
 	   }

	   public void setDepType(String depType){
	       this.depType = depType;

	   }
	   
	   @Column(name="RATE")
 	   public String getRate(){

	       return rate;
 	   }

	   public void setRate(String  rate){
 	       this. rate =  rate;

 	   }
	   
	   @OneToMany(cascade=CascadeType.ALL, mappedBy="depreciation")
 	   public Collection<ProcessedDepreciation> getProcessedDepreciation(){
	       return processedDepreciation;
 	   }

	   public void setProcessedDepreciation(Collection<ProcessedDepreciation> processedDepreciation){
 	       this.processedDepreciation = processedDepreciation;
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
	   
	   
	   
	   