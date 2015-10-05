package project.businesslogic;
import java.util.Calendar;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import data.access.TradesBeanLocal;
import objects.dataobjects.CompanyObject;
import objects.dataobjects.StockObject;
import objects.dataobjects.TradeHistoryObject;
import yahooFeed.Feed;

public class TwoMovingAvg implements Runnable {

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
		try {
			InitialContext context = new InitialContext();
			
			bean = (TradesBeanLocal)context.lookup("java:comp/env/ejb/TradesBean");
		
		}catch(NamingException e) {
			//log.error("NamingException: " + e.getMessage());
			e.printStackTrace();
		}
	while(true){
		
			Thread.sleep(1000);
			
			stock = Feed.feedConnection(compSymbol);

			CompanyObject company = new CompanyObject();
			
			if(bean.getCompany(compSymbol)!=null) {
	        	company = bean.getCompany(compSymbol);
	        }
	        else {
	        	company.setCompanySymbol(compSymbol);
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
			
			if(differenceLongShort.size() == 2){
				// difference was pos now neg.. e.g was above now below SELL
				if(differenceLongShort.get(0) > 0 && differenceLongShort.get(1) < 0){
					if(sold == false){
						System.out.println("SELLLINGGGGGG");
						sold = true;
						priceGot = stock.getBidPrice() * QUANTITY;
						trade.setBought(false);
						trade.setStockObject(stock);
						trade.setTradeTime(Calendar.getInstance().getTime().toString());
						
					}
					
				}
				// difference was neg now pos.. e.g was below now above BUY
				else if(differenceLongShort.get(0) < 0 && differenceLongShort.get(1) > 0){
					if(bought == false){
						System.out.println("BUYYYYYINGGGGGG");
						bought = true;
						pricePaid = stock.getAskPrice() * QUANTITY;
						trade.setBought(false);
						trade.setStockObject(stock);
						trade.setTradeTime(Calendar.getInstance().getTime().toString());
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
			e.printStackTrace();
		}
		
	}

}