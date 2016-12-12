import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
//import java.util.*;
//import java.util.Timer;
/**
 * View.java - GUI class for Stock Ticker program, also contains main method to start program
 * @author Salvatore Bellassai
 * @version 4-18-15
 */

public class View{
	private static JFrame start, exception, mainFrame, tempFrame;
	private static JTextArea input, output, askOutput;
	private static JTextField timeInput;
	private static String stockResult;
	private static int counter, countdown, timeRemaining;
	private static Controller controller = new Controller();
	private static JLabel timeLeft;
	private static Timer countdownTimer;

	public static void main(String[] args) {
		start = new JFrame("Stocks");
		start.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);	
		start.setLayout(new BorderLayout());
		Panel panel = new Panel();
		Panel west = new Panel();
		Panel north = new Panel();
		input = new JTextArea(10,5);
		timeInput = new JTextField(5);
		JScrollPane scroll = new JScrollPane(input);
		
		JButton enter = new JButton("ENTER");
		MainListener listener = new MainListener();
		enter.addActionListener(listener);
		
		JLabel label = new JLabel("Enter the time in seconds to wait between data refresh");
		JLabel label2 = new JLabel("Enter the stock symbols you");
		JLabel label3 = new JLabel("would like to look up.");
		JLabel label4 = new JLabel("One per line");
		panel.setLayout(new BorderLayout());
		
		north.add(label);
		north.add(timeInput);
		north.setPreferredSize(new Dimension(350,50));
		
		west.add(label2);
		west.add(label3);
		west.add(label4);
		west.setPreferredSize(new Dimension(250,100));
		
		panel.add(north,BorderLayout.NORTH);
		panel.add(west,BorderLayout.WEST);
		panel.add(scroll,BorderLayout.EAST);
		panel.add(enter, BorderLayout.SOUTH);
		
		start.setPreferredSize(new Dimension(350,300));
		start.add(panel);
		start.pack();
		start.setVisible(true);
		
	}//end main method
	/**
	 * Main GUI for stock ticker program, contains buttons and a jtextarea
	 */
	public static void mainView(int timerCount){
		timeRemaining = countdown;
		mainFrame = new JFrame("STOCKS");
		mainFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		Panel panel = new Panel();
		panel.setLayout(new BorderLayout());
		Panel west = new Panel();
		
		JButton add = new JButton("ADD STOCK");
		AddListener alistener = new AddListener();
		add.addActionListener(alistener);
		
		JButton update = new JButton("UPDATE");
		UpdateListener ulistener = new UpdateListener();
		update.addActionListener(ulistener);
		
		JButton remove = new JButton("REMOVE");
		RemoveListener rlistener = new RemoveListener();
		remove.addActionListener(rlistener);
		
		JButton key = new JButton("TERMS KEY");
		KeyListener klistener = new KeyListener();
		key.addActionListener(klistener);
		
		JLabel timeLabel = new JLabel("Time Until Refresh:");
		timeLeft = new JLabel("" + timeRemaining);
		
		countdownTimer = new Timer(1000, new CountdownTimerListener());
		countdownTimer.start();
		
		output = new JTextArea();
		output.setText(controller.toString());
		JScrollPane scroll = new JScrollPane(output);
		
		west.add(add);
		west.add(update);
		west.add(remove);
		west.add(key);
		west.add(timeLabel);
		west.add(timeLeft);
		west.setPreferredSize(new Dimension(110,200));
		
		askOutput = new JTextArea(10,11);
		JScrollPane askScroll = new JScrollPane(askOutput);
		askOutput.setText(controller.askPrice());	
		
		panel.add(askScroll, BorderLayout.EAST);
		panel.add(west, BorderLayout.WEST);
		panel.add(scroll, BorderLayout.CENTER);
		
		mainFrame.add(panel);
		mainFrame.setPreferredSize(new Dimension(600,600));
		mainFrame.pack();
		mainFrame.setVisible(true);	
		
	}//end mainView method
	/**
	 * GUI to get the symbol from the user of the stock to be added
	 */
	public static void addView(){
		tempFrame = new JFrame("Add Stock");
		Panel panel = new Panel();
		JLabel label = new JLabel("Enter the new stock to monitor");
		input = new JTextArea(1,5);
		JButton enter = new JButton("ENTER");
		AddViewListener listener = new AddViewListener();
		enter.addActionListener(listener);
		panel.add(label);
		panel.add(input);
		panel.add(enter);
		tempFrame.add(panel);
		tempFrame.pack();
		tempFrame.setVisible(true);
	}//end addView method
	/**
	 * GUI for removing a stock from list
	 */
	public static void removeView(){
		tempFrame = new JFrame("Remove Stock");
		Panel panel = new Panel();
		JLabel label = new JLabel("Enter the stock to be removed");
		input = new JTextArea(1,5);
		JButton enter = new JButton("ENTER");
		RemoveViewListener listener = new RemoveViewListener();
		enter.addActionListener(listener);
		panel.add(label);
		panel.add(input);
		panel.add(enter);
		tempFrame.add(panel);
		tempFrame.pack();
		tempFrame.setVisible(true);
	}//end removeView method
	/**
	 * GUI for a list of stock terms and what they mean
	 */
	public static void keyView(){
		JFrame keyFrame = new JFrame("KEY");
		Panel panel = new Panel();
		JTextArea keyOutput = new JTextArea();
		JScrollPane scroll = new JScrollPane(keyOutput);
		String key = ("Asking Price: The lowest price the seller of a stock is willing to accept\n"+
						"Change: The change in price of the company's stock since the previous trading day's close\n"+
						"Market Capitalization: The current stock price multipled by the total number of outstanding shares\n"+
						"Total Volume: The trade volume of the companys stock\n"+
						"Change from Year High: The difference in the current stock price and the highest price the stock has been this year\n"+
						"Change from Year Low: The difference in the current stock price and the lowest price the stock has been this year\n"+
						"Days High: The highest price the stock has traded for today\n"+
						"Days Low: The lowest price the stock has traded for today\n"+
						"Year High: The highest price the stock has traded for this year\n"+
						"Year Low: THe lowest price the stock has traded for this year\n"+
						"50 Day Average: Average of the Stocks closing price over the last 50 days\n"+
						"200 Day Average: Average of the Stocks closing price over the last 200 days\n"+
						"Open: The stocked opening price for the day\n"+
						"Close: The stocks closing price for the previous day");
		panel.add(scroll);
		keyOutput.setText(key);
		keyFrame.add(panel);
		keyFrame.pack();
		keyFrame.setVisible(true);
		
	}//end keyView method
	/**
	 * Displays a windows with the given error and gives use the option to dismiss
	 * @param e Exception to be displayed
	 */
	public static void printException(Exception e){
		exception = new JFrame("ERROR!");
		JLabel text = new JLabel(e.toString());
		JButton dismiss = new JButton("DISMISS");
		DismissListener listener = new DismissListener();
		dismiss.addActionListener(listener);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(text,BorderLayout.CENTER);
		panel.add(dismiss, BorderLayout.SOUTH);
		exception.add(panel);
		exception.setPreferredSize(new Dimension(700,100));
		exception.pack();
		exception.setVisible(true);
	}//end printException
	
	/**
	 * Listens for Enter button press on main method window, stores user input as an array of strings and calls controller start method with the array
	 * @author Trace
	 *
	 */
	static class MainListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			counter = 0;
			for(String line: input.getText().split("\\n")){
				++counter;
			}//end for
			String[] stocks = new String[counter];
			counter = 0;
			for(String line: input.getText().split("\\n")){
				stocks[counter] = line;
				++counter;
			}//end for
			try{
				stockResult = controller.start(stocks);
			}//end try
			catch (Exception exception){
				printException(exception);
			}//end catch
			countdown = (Integer.parseInt(timeInput.getText()) );
			start.setVisible(false);
			mainView(countdown);
		}//end actionPerformed method
	}//end MainListener class
	/**
	 * Listens for enter button press in addView method, calls controller add method with user inputed stock symbol, updates main jtextarea and hides frame when done
	 */
	public static class AddViewListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String stockStr = input.getText();
			controller.add(stockStr);
			output.setText(controller.toString());
			askOutput.setText(controller.askPrice());
			tempFrame.setVisible(false);
		}//end actionPerformed
	}//end AddViewListener
	/**
	 * Listens for remove button to be pressed in mainView, calls removeView()
	 */
	public static class RemoveListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			removeView();
		}//end actionPerformed
	}//end RemoveListener
	/**
	 * Listens for enter button to be presed in removeView(), calls controller remove method and refreshes textarea in mainView()
	 */
	public static class RemoveViewListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String stockStr = input.getText();
			try{
				controller.remove(stockStr);
			}//end try
			catch(Exception e){
				View.printException(e);
			}//end catch
			output.setText(controller.toString());
			askOutput.setText(controller.askPrice());
			tempFrame.setVisible(false);
		}//end actionPerformed
	}//end RemoveViewListener
	/**
	 * Listens for key button to be pressed in mainView(), calls keyView()
	 */
	public static class KeyListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			keyView();
		}//end actionPerformed
	}//end KeyListener
	/**
	 * Listens for add button to be pressed in mainView, calls addView method
	 */
	public static class AddListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			addView();
		}//end actionPerformed
	}//end AddListener
	/**
	 * Listens for update button to be pressed, calls controller update method and updates output jtextarea
	 */
	public static class UpdateListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			controller.update();
			output.setText(controller.toString());
			askOutput.setText(controller.askPrice());
		}//end actionPerformed
	}//end UpdateListener
	/**
	 * Listens for the Dismiss button to be pressed on the printException method, hides windows on action
	 */
	/**
	 * Listener for timer
	 */
	public static class CountdownTimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(--timeRemaining > 0)
				timeLeft.setText("" + timeRemaining);
			else{
				timeLeft.setText("0");
				controller.update();
				output.setText(controller.toString());
				askOutput.setText(controller.askPrice());
				timeRemaining = countdown + 1;
				countdownTimer.start();
			}//end else
		}//end actionPerformed
	}//end CountdownTimerListener
	public static class DismissListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			exception.setVisible(false);
		}//end actionPerformed
	}//end DismissListener

}//end View class
