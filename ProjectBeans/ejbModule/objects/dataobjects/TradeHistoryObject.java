package objects.dataobjects;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="tradehistory")
public class TradeHistoryObject {
	
	@Id
	public int tradeID;
	@ManyToOne(optional = false)
	@JoinColumn(name="UserID", referencedColumnName="UserID")
	UserObject userObj;
	@ManyToOne(optional = false)
	@JoinColumn(name="StockID", referencedColumnName="StockID")
	StockObject stockObj;
	public String tradeTime;
	public boolean isBought;

	public TradeHistoryObject(int tradeID, int userID, StockObject stockObj, String tradeTime, boolean isBought){
		this.tradeID=tradeID;
		this.userObj=userObj;
		this.stockObj=stockObj;
		this.tradeTime=tradeTime;
		this.isBought=isBought;
	}
	
	public int getTradeID() {
		return tradeID;
	}
	public void setTradeID(int tradeID) {
		this.tradeID = tradeID;
	}
	public UserObject getUserObject() {
		return userObj;
	}
	public void setUserObject(UserObject userObj) {
		this.userObj = userObj;
	}
	public StockObject getStockObj() {
		return stockObj;
	}
	public void setStockObject(StockObject stockObj) {
		this.stockObj = stockObj;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public boolean isBought() {
		return isBought;
	}

	public void setBought(boolean isBought) {
		this.isBought = isBought;
	}
	

}
