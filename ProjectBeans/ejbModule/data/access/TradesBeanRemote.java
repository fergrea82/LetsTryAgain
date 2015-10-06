package data.access;

import java.util.List;

import javax.ejb.Remote;

import objects.dataobjects.CompanyObject;
import objects.dataobjects.StockObject;
import objects.dataobjects.TradeHistoryObject;
import objects.dataobjects.UserObject;
@Remote
public interface TradesBeanRemote {
	void addStock(StockObject stock);
	CompanyObject getCompany(String symbol);
	void addCompany(CompanyObject company);
	Boolean companyCheck(CompanyObject comp);
	public List<TradeHistoryObject> getAllTrades();
	void addTrade(TradeHistoryObject trade);
	UserObject getUser();
}
