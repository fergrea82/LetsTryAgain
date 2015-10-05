package objects.dataobjects;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="stocks")
public class StockObject {
	
	@Id
	private int stockID;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="CompanyID", referencedColumnName="CompanyID")
	CompanyObject companyObj;
	
	private double bidPrice;
	private double askPrice;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="MarketID", referencedColumnName="MarketID")
	MarketObject marketObj;
	
	private String StockTime;
	
	@OneToMany(mappedBy="stockObj", fetch=FetchType.EAGER)
	private List<TradeHistoryObject> trades;
	
	public StockObject() {
		//default constructor
	}
	
	public StockObject(int stockID, MarketObject marketObj, CompanyObject companyObj, double bidPrice, double askPrice, String stockTime){
		this.stockID=stockID;
		this.marketObj=marketObj;
		this.companyObj=companyObj;
		this.bidPrice=bidPrice;
		this.askPrice=askPrice;
		this.StockTime=stockTime;
	}
	
	public int getStockID() {
		return stockID;
	}
	public void setStockID(int stockID) {
		this.stockID = stockID;
	}
	public MarketObject getMarket() {
		return marketObj;
	}
	public void setMarketID(MarketObject marketObj) {
		this.marketObj = marketObj;
	}
	public CompanyObject getCompanyObject() {
		return companyObj;
	}
	public void setCompanyObject(CompanyObject companyObj) {
		this.companyObj = companyObj;
	}
	public double getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}
	public double getAskPrice() {
		return askPrice;
	}
	public void setAskPrice(double askPrice) {
		this.askPrice = askPrice;
	}

	public String getStockTime() {
		return StockTime;
	}

	public void setStockTime(String stockTime) {
		StockTime = stockTime;
	}
	

	
	
	

}
