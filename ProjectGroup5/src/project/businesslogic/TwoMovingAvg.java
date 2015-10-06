package project.businesslogic;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import data.access.TradesBeanLocal;
import objects.dataobjects.CompanyObject;
import objects.dataobjects.StockObject;
import objects.dataobjects.TradeHistoryObject;
import objects.dataobjects.UserObject;
import yahooFeed.Feed;

@EJB(name="ejb/TradesBean",beanInterface=TradesBeanLocal.class)
public class TwoMovingAvg implements Runnable {
	static Logger log = Logger.getLogger(TwoMovingAvg.class);
	
	private static final int QUANTITY = 15000;
	private static final int VALUESHORTAVERAGE = 4;
	private static final int VALUELONGAVERAGE = 20;
	private double shortMovingAverage = 0;
	private double longMovingAverage = 0;
	private double pricePaid = 0;
	private double priceGot = 0;
	private double runningTotal = 0;
	private double profitMarginOfInvestment = 3000;
	private double lossMarginOfInvestment = -3000;
	private boolean bought = false;
	private boolean sold = false;
	
	
	
	private LinkedList<Double> shortlist = new LinkedList<>();
	private LinkedList<Double> longlist = new LinkedList<>();
	private LinkedList<Double> differenceLongShort = new LinkedList<>();
	
	private StockObject stock = new StockObject();
		
	public void TwoMovingAverage(String compSymbol) throws InterruptedException{
		
		TradesBeanLocal bean = null;
		TradeHistoryObject trade = null;
		UserObject user = null;
		try {
			InitialContext context = new InitialContext();
			
			bean = (TradesBeanLocal)context.lookup("java:comp/env/ejb/TradesBean");
			user = bean.getUser();
		
		}catch(NamingException e) {
			log.error("NamingException: " + e.getMessage());
			e.printStackTrace();
		}
	while(true){
		
			Thread.sleep(1000);
			
			stock = Feed.feedConnection(compSymbol);
			log.info("Stock Object Created Using Feed Data");
			CompanyObject company = new CompanyObject();
			
			if(bean.getCompany(compSymbol)!=null) {
	        	company = bean.getCompany(compSymbol);
	        }
	        else {
	        	company.setCompanySymbol(compSymbol);
	        	company.setStrategy(1);
	        	bean.addCompany(company);
	        	company = bean.getCompany(compSymbol);
	        }
			
			stock.setCompanyObject(company);

			if (shortlist.size() == VALUESHORTAVERAGE) {
				shortlist.removeFirst();
				shortlist.add((stock.getAskPrice() + stock.getBidPrice())/2);
				shortMovingAverage = calcShortMovingAverage(shortlist);
			}
			else{
				shortlist.add((stock.getAskPrice() + stock.getBidPrice())/2);
			}
			if (longlist.size() == VALUELONGAVERAGE) {
				longlist.removeFirst();
				longlist.add((stock.getAskPrice() + stock.getBidPrice())/2);
				longMovingAverage = calcLongMovingAverage(longlist);
			}
			else{
				longlist.add((stock.getAskPrice() + stock.getBidPrice())/2);
			}
			
			//start of the profit loss
			
			differenceLongShort.add((longMovingAverage - shortMovingAverage));
			
			Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
	        String strDate = now.toString();
			
			if(differenceLongShort.size() == 2){
				// difference was pos now neg.. e.g was above now below SELL
				if(differenceLongShort.get(0) > 0 && differenceLongShort.get(1) < 0){
					if(sold == false){
						System.out.println("SELLLINGGGGGG");
						sold = true;
						priceGot = stock.getBidPrice() * QUANTITY;
						stock.setCompanyObject(company);
						bean.addStock(stock);
						
						trade = new TradeHistoryObject();
						trade.setBought(false);
						trade.setStockObject(stock);
						trade.setTradeTime(strDate);
						trade.setUserObject(user);
						
						bean.addTrade(trade);
						log.info("Trade added: "+trade.getTradeTime());
					}
					
				}
				// difference was neg now pos.. e.g was below now above BUY
				else if(differenceLongShort.get(0) < 0 && differenceLongShort.get(1) > 0){
					if(bought == false){
						System.out.println("BUYYYYYINGGGGGG");
						bought = true;
						pricePaid = stock.getAskPrice() * QUANTITY;
						stock.setCompanyObject(company);
						bean.addStock(stock);
						
						trade = new TradeHistoryObject();
						trade.setBought(true);
						trade.setStockObject(stock);
						trade.setTradeTime(strDate);
						trade.setUserObject(user);
						
						bean.addTrade(trade);
						log.info("Trade added: "+trade.getTradeTime());
					}				
				}
				differenceLongShort.remove(0);
			}
			
			if(bought == true && sold == true){
				runningTotal += (pricePaid - priceGot);
				bought = false;
				sold = false;
				System.out.println("Running Total = " + runningTotal);
				
				if(runningTotal <= lossMarginOfInvestment || runningTotal >= profitMarginOfInvestment)
					log.info("1% Profit Or Loss Margin Met.. Two Moving Average Strategy Exited");
					break;
			}
			
			}
		}
	
	public static double calcLongMovingAverage(LinkedList<Double> lList){
		double av = 0, total = 0;
		for(int i = 0;i<lList.size();i++)
		{
			total += lList.get(i);
		}
		av = total/VALUELONGAVERAGE;
        return av;
	}
	
	public static double calcShortMovingAverage(LinkedList<Double> sList){
		double av = 0, total = 0;
		for(int i = 0;i<sList.size();i++)
		{
			total += sList.get(i);
		}
		av = total/VALUESHORTAVERAGE;
        return av;
	}
	private String symbol;

	public void setSymbol(String compSymbol) {
		symbol = compSymbol;
	}
	
	@Override
	public void run() {
		
		try {
			TwoMovingAverage(symbol);
		} catch (InterruptedException e) {
			log.error("InterruptedException: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

}