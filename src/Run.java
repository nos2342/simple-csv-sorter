import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

/**
 * Run 
 *
 * The main class.
 * 
 * @author Maura Hausman
 */

public class Run {
	private final static int EXIT = -3, REVERSE = -2, SAVE = -1;
	private final static String FILENAME = "example-data.csv";
	private final static String PATH = System.getProperty("user.dir")
	    + File.separator + FILENAME;
	private final static String MENU = 
	"To sort the table, enter the column number you'd like to sort by.\n\n" +
	"To reverse the order, enter -2.\n\n" +
	"To save the new CSV, enter -1.\n\n" +
	"To exit, enter -3.\n";
	private final static String PROMPT = ">";
	
	private static int NUM_VALUES;
	private static int NUM_HEADERS;
	private static Object[] HEADERS;
	private static LinkedList<Row> data;
	
  /**
	 * @param args
	 */
	public static void main(String[] args) {
		File csv = new File(FILENAME);
		System.out.println(csv.getPath());
		try{
			Scanner fileReader = new Scanner(csv);
			fileReader.useDelimiter(",|\r|\n"); // separate by commas and new lines
			// first we get our headers
		  getHeaders(fileReader.nextLine());
		
		  // now we assemble our data.
		  assembleData(fileReader);
		}catch (FileNotFoundException e){
			System.out.println("Failed to find " + PATH);
			System.exit(1);
		}
		
		//finally, we are ready to accept commands
		displayMenu();
		
	}
	
	/*
	 * 
	 */
	private static void getHeaders(String s) {//TODO
	  Scanner headHunter = new Scanner(s);
	  headHunter.useDelimiter(",");
	  LinkedList<String> headerlist = new LinkedList<String>();
	  while (headHunter.hasNext()){
	    headerlist.add(headHunter.next());
	  }
	  HEADERS = headerlist.toArray(new String[0]);
	  NUM_HEADERS = HEADERS.length;
	  NUM_VALUES = NUM_HEADERS - 1;
	}
	
	/*
	 * 
	 */
	private static LinkedList<Row> assembleData(Scanner fileReader){//TODO
	  data = new LinkedList<Row>();
		while (fileReader.hasNext()){
			String key = fileReader.next();
			BigDecimal[] values = new BigDecimal[NUM_VALUES];
			for (int i = 0; i < values.length; i++){
				try{
					values[i] = fileReader.nextBigDecimal();
				}catch (InputMismatchException e){
					values[i] = null;
				}
			}
			data.add(new Row(key, values));
		}
	  return null;
	}
	
	/*
	 * 
	 */
	private static void displayMenu(){
	  //TODO implement real commands e.g. "exit" "sort 1"
		System.out.println(MENU);
		while (true){
		  System.out.print(PROMPT);
		  Scanner input = new Scanner(System.in);
			try {
			  Integer index = input.nextInt();
			  switch (index) {
				  case EXIT:// exit
					  System.exit(0);
				  case REVERSE:// reverse
					  data = reverse();
					  printData();
					  break;
			    case SAVE://save
			      save();
			      break;
				  default:
					  if (index > NUM_HEADERS || index < EXIT){
		          System.out.println("Invalid input: " + index);
		        } else{
					    data = heapSort(index);
					    printData();
					  }
			  }
		  } catch (InputMismatchException e) {
		    System.out.println("Invalid input.");
		  }
		}
	}
	
	/*
	 * A helper method for sorting a data set via heapsort. This is a linearithmic
	 * algorithm.
	 */
	private static LinkedList<Row> heapSort(int n) {
		PriorityQueue<Row> aHeap = new PriorityQueue<Row>(data.size());
		for (Row b : data){
			b.selectColumn(n);
			aHeap.offer(b);
		}
		
		LinkedList<Row> newData = new LinkedList<Row>();
		while (!aHeap.isEmpty())
			newData.add(aHeap.poll());
		return newData;
	}
	
	/*
	 * A helper method for reversing the order of a sorted dataset. This is a
	 * linear algorithm.
	 */
	private static LinkedList<Row> reverse() {
		Stack<Row> aStack = new Stack<Row>();
		
		// push all the data to the stack...
		for (Row b : data)
			aStack.push(b);
		
		// ... and pop it all off again.
		LinkedList<Row> newData = new LinkedList<Row>();
		while (!aStack.isEmpty())
			newData.add(aStack.pop());
		return newData;
	}
	
	/*
	 * 
	 */
	private static void help() {// TODO make this a command-line option, -h
	
	}
	
	/*
	 * A helper method for printing data to output. Can be easily modified to
	 * print to a file instead.
	 * 
	 * Currently, the method skips Rows with null active values.
	 */
	private static void printData() {
	  int activeColumn = data.peek().getActiveColumn();
	  if(activeColumn == 0){
	    System.out.println(HEADERS[activeColumn]);
	  }
		System.out.print("Ticker\t" + HEADERS[activeColumn + 1]);
		for (Row b : data)
			if (b.activeValue() != null)
				System.out.println(b);
	}
	
	/*
	 * 
	 */
	private static boolean save(){//TODO save data as a CSV file
	  return false;
	}
}
