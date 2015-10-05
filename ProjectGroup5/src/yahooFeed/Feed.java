package yahooFeed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import objects.dataobjects.StockObject;

import org.jboss.logging.Logger;

public class Feed {
	
	static Logger log = Logger.getLogger(Feed.class);
	
	public static StockObject feedConnection(String stock) {
		/*
		 * method which establishes connection with yahoo
		 * takes String parameter of stock symbol - string supplied by user 
		 * through front end jsp.
		 *  will create an array of string of each stock element these will be passed 
		 *  added to an array list and returned
		 */
		//ArrayList<StockObject> stocks = new ArrayList<>();
		
		StockObject stockObj = null;
		
		StringBuilder url = 
	            new StringBuilder("http://finance.yahoo.com/d/quotes.csv?s=");
            url.append(stock + "+");
        url.append("&f=sab&e=.csv");
        log.info("Company Added to the Yahoo Feed = " + stock);
        
        try {
        String theUrl = url.toString();
        URL obj;
		
			obj = new URL(theUrl);
		
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        
        String inputLine;
        
        String[] fields = null;
        
        stockObj = new StockObject();
        
        while ((inputLine = in.readLine()) != null)
        	{
        		fields = inputLine.split(","); 
        	}
        for (int i =0; i<fields.length;i++) {
        	System.out.print(fields[i]+",");
        	if (fields[i].equals("N/A")) {
        		fields[i]="0.0";
        	}
        }
        
        System.out.println();
        //stockObj.setstockSymbol(fields[0]);
        
        stockObj.setAskPrice(Double.parseDouble(fields[1]));
        stockObj.setBidPrice(Double.parseDouble(fields[2]));
        //Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = now.toString();
        stockObj.setStockTime(strDate);
        
		}
        catch (MalformedURLException e) {
			// TODO Auto-generated catch block
        	log.error("MalformedURLException:" + e.getMessage());
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			log.error("ProtocolException:" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("IOException:" + e.getMessage());
			e.printStackTrace();
		}
		return stockObj;
	}
}