import java.io.*;
import java.net.*;
import net.sf.json.*;
import org.apache.commons.lang.exception.*;
import org.json.*;
import java.util.*;
/**
 * Controller.java - Contains methods to manipulate the list of stocks
 * @author Salvatore Bellassai
 * @version 4-15-15
 */

public class Controller {
	private List<Stock> stockList = new LinkedList<Stock>();
	private List<Stock> newStockList;
	private List<String> symbolList = new LinkedList<String>();
	private Stock[] stock;
	private JSONObject[] stockData;
	private String result;
	private int count;
	
	/**
	 * Starts the program with an initial array containing strings of stock symbols
	 * @param input String of stock symbols
	 * @return String representation of all the stocks
	 * @throws Exception
	 */
	public String start(String[] input) throws Exception {
		stockData = new JSONObject[input.length];
		stock = new Stock[input.length];
		result = "";
		count = 0;
		try{
			for (int i = 0; i < input.length; ++i){
				if(input[i] != null){
					String JSonString = readURL("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22" + input[i] + "%22)%0A%09%09&env=http%3A%2F%2Fdatatables.org%2Falltables.env&format=json");
					stockData[i] = JSONObject.fromObject(JSonString);
					stock[i] = new Stock(stockData[i]);
					stockList.add(stock[i]);
					symbolList.add(input[i]);
					result += stock[i].toString();
					++count;
				}//end if
			}//end for loop	;
		}//end try
		catch(IOException error){
			;
		}//end catch

		return result;
	}//end start method
	public void add(String newStockStr){
		Stock newStock = null;
		try{
			String JSonString = readURL("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22" + newStockStr + "%22)%0A%09%09&env=http%3A%2F%2Fdatatables.org%2Falltables.env&format=json");
			newStock = new Stock(JSONObject.fromObject(JSonString));
		}//end try
		catch(Exception e){
			View.printException(e);
		}//end catch
		if(newStock != null)
			stockList.add(newStock);
		symbolList.add(newStockStr);
	}//end add method
	public void remove(String stockStr)throws Exception{
		Iterator<Stock> it = stockList.iterator();
		boolean found = false;
		while(it.hasNext()){
			Stock tempStock = it.next();
			String tempStr = tempStock.getSymbol();
			if(tempStr.equals(stockStr)){
				it.remove();
				found = true;
			}//end if
		}//end while
		Iterator<String> symbolIt = symbolList.iterator();
		if(found){
			while(symbolIt.hasNext()){
				String temp = symbolIt.next();
				if(temp.equals(stockStr))
					symbolIt.remove();
			}//end while
		}//end outer if
		else
			throw new Exception("Desired stock " + stockStr + " could not be found and was therefore not removed");
	}//end remove method
	/**
	 * Updates all of the stocks with the newest information available
	 */
	public void update(){
		newStockList = new LinkedList<Stock>();
		try{
			for(String s: symbolList){
				String JSonString = readURL("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22" + s + "%22)%0A%09%09&env=http%3A%2F%2Fdatatables.org%2Falltables.env&format=json");
				Stock tempStock = new Stock(JSONObject.fromObject(JSonString));
				newStockList.add(tempStock);
			}//end for
		}//end try
		catch(Exception e){
			View.printException(e);
		}//end catch
		stockList = newStockList;
	}//end update method
	/**
	 * return a String with the asking price and stock symbol of each stock
	 * @return string of asking price and stock symbol
	 */
	public String askPrice(){
		String result = "";
		for(Stock s: stockList){
			result += s.getSymbol() + "\n    Asking Price: $" + s.getAsk() + "\n\n";
		}//end for
		return result;
	}//end askPrice method
	/**
	 * Returns a string representation of all of the stocks
	 */
	public String toString(){
		result = "";
		for (Stock s: stockList){
			result += s.toString();
		}//end for
		return result;
	}//end toString method
	
	/**
	 * Reads the URL given and returns a String representation of what is on the site
	 * @param webservice
	 * @return String of site
	 * @throws Exception
	 */
	public static String readURL(String webservice) throws Exception 
	{	
		URL oracle = new URL(webservice);
		BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

		String inputLine;
		String result = "";

		while ((inputLine = in.readLine()) != null)
			result = result + inputLine;

		in.close();
		return result;
    }//end readUrl method
	
}//end class
