package data.access;

import java.util.List;

import javax.ejb.Local;

import objects.dataobjects.CompanyObject;
import objects.dataobjects.StockObject;
import objects.dataobjects.TradeHistoryObject;
import objects.dataobjects.UserObject;

@Local
public interface TradesBeanLocal {

	void addStock(StockObject stock);

	CompanyObject getCompany(String symbol);

	void addCompany(CompanyObject company);

	Boolean companyCheck(CompanyObject comp);
	
	public void addTrade(TradeHistoryObject trade);

	public List<TradeHistoryObject> getAllTrades();

	UserObject getUser();

	

}
