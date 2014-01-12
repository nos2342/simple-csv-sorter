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
 * Run The main class.
 * 
 * @author Matthew Hausman
 */

public class Run {
	private final static int ATTRIBUTES = 27;
	private final static int NUM_HEADERS = ATTRIBUTES + 1;
	private final static int EXIT = -3, REVERSE = -2, HELP = -1;
	private final static String FILENAME = "example-data.csv";
	private final static String PATH = System.getProperty("user.dir")
	    + File.separator + FILENAME;
	private final static String[] COMMANDS =
	/*
	 * These are the miscellaneous commands. If you're adding new ones, remember
	 * to handle them in the switch statement in main
	 */
	{ "exit", "reverse", "help",
	/*
	 * These commands each sort the data by the corresponding attribute. Feel free
	 * to modify the commands here. If you're adding new attributes to sort,
	 * though, remember to increase ATTRIBUTES
	 */
	"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
	    "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
	    "27" };
	private static String[] HEADERS = new String[NUM_HEADERS];
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File csv = new File(FILENAME);
		Scanner fileReader = null;
		System.out.println(csv.getPath());
		try{
			fileReader = new Scanner(csv);
			fileReader.useDelimiter(",|\r|\n"); // separate by commas and new lines
		}catch (FileNotFoundException e){
			System.out.println("Failed to access " + PATH);
			System.exit(1);
		}
		
		// first we get our headers
		for (int i = 0; i < NUM_HEADERS && fileReader.hasNext(); i++)
			HEADERS[i] = fileReader.next();
		
		// now we assemble our data. It will always be stored in this list.
		LinkedList<Business> data = new LinkedList<Business>();
		while (fileReader.hasNext()){
			String name = fileReader.next();
			BigDecimal[] attributes = new BigDecimal[ATTRIBUTES];
			for (int i = 0; i < attributes.length; i++){
				try{
					attributes[i] = fileReader.nextBigDecimal();
				}catch (InputMismatchException e){
					attributes[i] = null;
				}
			}
			data.add(new Business(name, attributes));
		}
		
		/*
		 * Here we set up a map from commands to integers, by which we can identify
		 * them
		 */
		HashMap<String, Integer> commandMap = new HashMap<String, Integer>();
		int commandInt = ATTRIBUTES - COMMANDS.length;
		for (String command : COMMANDS)
			commandMap.put(command, commandInt++);
		
		// Finally, we can get some input
		Scanner input = new Scanner(System.in);
		
		// this loop condition only fails when an End Of File (EOF) is entered
		while (input.hasNext()){
			String command = input.next();
			Integer index = commandMap.get(command);
			if (index == null){
				System.out.println();
			}else if (index < 0){
				switch (index) {
					case EXIT:// exit
						System.exit(0);
					case REVERSE:// reverse
						data = reverse(data);
						printData(data);
						break;
					case HELP:// help TODO
						help();
						break;
					default:
						System.out.println("Something has gone very wrong, possibly in "
								+ "the command structure.");
						break;
				}
			}else{
				data = heapSort(index, data);
				printData(data);
			}
		}
		
	}
	
	/*
	 * A helper method for sorting a data set via heapsort. This is an O(NlogN)
	 * (linearithmic) algorithm.
	 */
	private static LinkedList<Business>
	    heapSort(int n, Collection<Business> data) {
		PriorityQueue<Business> aHeap = new PriorityQueue<Business>(data.size()
		/* ,new BusinessComparator<Business>(n) */);
		for (Business b : data){
			b.setActive(n);
			aHeap.offer(b);
		}
		
		LinkedList<Business> newData = new LinkedList<Business>();
		while (!aHeap.isEmpty())
			newData.add(aHeap.poll());
		return newData;
	}
	
	/*
	 * A helper method for reversing the order of a sorted dataset. This is a
	 * linear algorithm.
	 */
	private static LinkedList<Business> reverse(Collection<Business> data) {
		Stack<Business> aStack = new Stack<Business>();
		
		// push all the data to the stack...
		for (Business b : data)
			aStack.push(b);
		
		// ... and pop it all off again.
		LinkedList<Business> newData = new LinkedList<Business>();
		while (!aStack.isEmpty())
			newData.add(aStack.pop());
		return newData;
	}
	
	/*
	 * A poorly conceived but well-meaning idea for a command. It wasn't a
	 * priority, and was cut last minute.
	 */
	private static void help() {// TODO
	
	}
	
	/*
	 * A helper method for printing data to output. Can be easily modified to
	 * print to a file instead.
	 * 
	 * Currently, the method skips Businesses with null active values.
	 */
	private static void printData(LinkedList<Business> data) {
		System.out.println("Ticker\t" + HEADERS[data.peek().active + 1]);
		for (Business b : data)
			if (b.getActive() != null)
				System.out.println(b);
	}
}
