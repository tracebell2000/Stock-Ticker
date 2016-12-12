import net.sf.json.JSONObject;
import org.json.simple.*;
/**
 * Stock.java - Represents a stock and its data
 * @author Salvatore Bellassai
 * @version 4-17-15
 */
public class Stock {
	
	private String lastTradeDate = "";
	private String symbol = "";
	private float askPrice = 0;
	private float lastPrice = 0;
	private float change = 0;
	private String marketCap =  "";
	private float avgVolume = 0;
	private float volume = 0;
	private float changeYTDHigh = 0;
	private float changeYTDLow = 0;
	private float changePercYTD = 0;
	private float dayHigh = 0;
	private float dayLow = 0;
	private float yearHigh = 0;
	private float yearLow = 0;
	private float open = 0;
	private float fiftyDayAvg = 0;
	private float twoHundredDayAvg = 0;
	private float realTimeAsk = 0;
	private float close = 0;
	private String stockName = "";
	private JSONObject stock;
	
	public Stock(JSONObject json) throws Exception{
		JSONObject query = (JSONObject)(json.get("query"));
		JSONObject results = (JSONObject)(query.get("results"));
		stock = (JSONObject)(results.get("quote"));
		symbol += stock.get("symbol");
		stockName += stock.get("Name");
		if(stockName.equals("null"))
			throw new StockNotFoundException("The stock " + symbol + " could not be found in the database");
		lastTradeDate += stock.get("LastTradeDate");
		askPrice = Float.parseFloat("" +stock.get("Ask"));
		if(stock.get("AskRealtime").equals(null))
			;
		else
			realTimeAsk = Float.parseFloat("" +stock.get("AskRealtime"));
		lastPrice = Float.parseFloat("" +stock.get("LastTradePriceOnly"));
		change = Float.parseFloat("" +stock.get("Change"));
		marketCap += stock.get("MarketCapitalization");
		avgVolume = Float.parseFloat("" +stock.get("AverageDailyVolume"));
		volume = Float.parseFloat("" +stock.get("Volume"));
		changeYTDHigh = Float.parseFloat("" +stock.get("ChangeFromYearHigh"));
		changeYTDLow = Float.parseFloat("" +stock.get("ChangeFromYearLow"));
		dayHigh = Float.parseFloat("" +stock.get("DaysHigh"));
		dayLow= Float.parseFloat("" +stock.get("DaysLow"));
		yearHigh = Float.parseFloat("" +stock.get("YearHigh"));
		yearLow = Float.parseFloat("" +stock.get("YearLow"));
		fiftyDayAvg = Float.parseFloat("" +stock.get("FiftydayMovingAverage"));
		twoHundredDayAvg = Float.parseFloat("" +stock.get("TwoHundreddayMovingAverage"));
		close = Float.parseFloat("" +stock.get("PreviousClose"));
		open = Float.parseFloat("" +stock.get("Open"));
	}//end constructor
	
	/**
	 * Returns a string representation of a stock
	 */
	public String toString(){
		String result;
		if(stock != null){
			result = ("Stock information for : " + stockName + "\n" +
					"\tLast date stock was traded: " + lastTradeDate + "\n" +
					"\tTicker Symbol: " + symbol + "\n" +
					"\tAsking Price: $" + askPrice + "\n" +
					"\tReal Time Asking Price: $" + realTimeAsk + "\n" +
					"\tLast Traded Price: $" + lastPrice + "\n" +
					"\tChange: $" + change + "\n" +
					"\tMarket Capitalization: $" + marketCap + "\n" +
					"\tTotal Volume: " + volume + " stocks\n" +
					"\tAverage Daily Volume: " + avgVolume + " stocks\n" +
					"\tChange from year High $" + changeYTDHigh + "\n" +
					"\tChange from year Low $" + changeYTDLow + "\n" +
					"\tDays High: $" + dayHigh + "\n" +
					"\tDays Low: $" + dayLow + "\n" +
					"\tYear High: $" + yearHigh + "\n" +
					"\tYear Low: $" + yearLow + "\n" +
					"\t50 Day Average: $" + fiftyDayAvg + "\n" +
					"\t200 Day Average: $" + twoHundredDayAvg + "\n" +
					"\tClose: $" + close + "\n" +
					"\tOpen: $" + open + "\n\n");
			}//end if
		else{
			result = ("No Data for given stock");
			}//end else
		return result;
		}//end toString
	/**
	 * Returns the stocks ticker symbol		
	 * @return stock ticker symbol
	 */
	public String getSymbol(){
		return symbol;
	}//end getSymbol method
	/**
	 * Returns the real time asking price
	 * @return real time asking price
	 */
	public float getAsk(){
		return realTimeAsk;
	}//end getAsk method
	/**
	 * Exception if stock is not found on the yahoo database
	 */
	public class StockNotFoundException extends Exception{
		public StockNotFoundException(String message){
			super(message);
		}//end StockNotFoundException constructor
	}//end StockNotFoundException class

}
