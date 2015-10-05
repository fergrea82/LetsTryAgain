package objects.dataobjects;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="marketcompany")
public class MarketCompanyObject {
	
	@Id
	private int indexID;
	@ManyToOne(optional = false)
	@JoinColumn(name="MarketID", referencedColumnName="MarketID")
	MarketObject marketObj;
	@ManyToOne(optional = false)
	@JoinColumn(name="CompanyID", referencedColumnName="CompanyID")
	CompanyObject companyObj;
	
	public int getIndexID() {
		return indexID;
	}
	public void setIndexID(int indexID) {
		this.indexID = indexID;
	}
	public MarketObject getMarketObj() {
		return marketObj;
	}
	public void setMarketObj(MarketObject marketObj) {
		this.marketObj = marketObj;
	}
	public CompanyObject getCompanyObj() {
		return companyObj;
	}
	public void setCompanyObj(CompanyObject companyObj) {
		this.companyObj = companyObj;
	}
}
