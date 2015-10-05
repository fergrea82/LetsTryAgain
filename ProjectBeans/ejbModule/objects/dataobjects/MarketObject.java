package objects.dataobjects;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="market")
public class MarketObject {
	
	public MarketObject() {
		//super();
	}
	
	@Id
	private int marketID;
	private String marketName;
	
	@OneToMany(mappedBy="marketObj", fetch=FetchType.EAGER)
	private List<StockObject> stocks;
	
	@OneToMany(mappedBy="marketObj", fetch=FetchType.EAGER)
	private List<MarketCompanyObject> marketcompany;
	
	public MarketObject(int marketID, String marketName){
		this.marketID=marketID;
		this.marketName=marketName;
	}
	
	public int getMarketID() {
		return marketID;
	}
	public void setMarketID(int marketID) {
		this.marketID = marketID;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

}
