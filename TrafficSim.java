/**
 * File: 	TrafficSim.java
 * Project: 	Project 2 - Traffic Simulator
 * Instructions:  See README file
 */

//imports required for class
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TrafficSim
{
	//declare variables
	private Queue <Vehicle> northbound;
	private Queue <Vehicle> southbound;
	private Queue <Vehicle> eastbound;
	private Queue <Vehicle> westbound;
	private Linked_List results;
	private IntersectionFlowRate flowRate;
	private int count; //counts time of simulation in seconds
	private int greenCountNS, greenCountEW; //counters for lights for each direction
	private int currentDirection; //0 for NS, 1 for EW
	private boolean debug = false;
	
	/**
	 * Constructor. Takes input filename as parameter.
	 * Instantiates each queue, the Linked_List, and the 
	 * IntersectionFlowRate. Sets count to 0. If readFromFile is
	 * true, then runs traffic simulation and displays final
	 * results.
	 * @param filename
	 * 		name of input file containing vehicle flow rates, as a String
	 */
	public TrafficSim(String filename)
	{	
		northbound = new LinkedList<Vehicle>();
		southbound = new LinkedList<Vehicle>();
		eastbound = new LinkedList<Vehicle>();
		westbound = new LinkedList<Vehicle>();
		results = new Linked_List();
		flowRate = new IntersectionFlowRate();
		count = 0; //set time to 0 seconds
		if(readFromFile(filename)) //if flow rates have been set
		{
			runSimulation(); //run the traffic simulation method
			results.displayFinalStatistics(results); //display final results
		}		
	}
	
	/**
	 * Read flow rate information from a text file and assign the 
	 * appropriate rates to the appropriate flowRate variables.
	 * @param filename
	 * 		name of input file, as a String
	 * @return
	 * 		return true if flowRate variables have been set
	 */
	private boolean readFromFile(String filename)
	{
		boolean debugFileRead = false;
		boolean ratesAssigned = false;
		char direction; int carFlowRate, truckFlowRate;
		
		Scanner infile = null; //declare Scanner object
		try //detect FileNotFound exception
		{ infile = new Scanner(new FileReader(filename)); }
				
		catch(FileNotFoundException e) //handle exception
		{
			System.out.println("File not found");
			e.printStackTrace();
			System.exit(0);
		}
		
		while(infile.hasNextLine()) //while input file has more data
		{
			String line = infile.nextLine(); //set line to next line
			//create tokenizer to break line down into tokens
			StringTokenizer tokenizer = new StringTokenizer(line);
			//assign direction, car rate, and truck rate tokens
			direction = tokenizer.nextToken().charAt(0);
			carFlowRate = Integer.parseInt(tokenizer.nextToken());
			truckFlowRate = Integer.parseInt(tokenizer.nextToken());
			
			switch(direction) //use direction switch to assign rates
			{
			case 'N': //northbound
			{
				flowRate.setNorthFlowRateCars(carFlowRate);
				flowRate.setNorthFlowRateTrucks(truckFlowRate);
				break;
			}
			case 'S': //southbound
			{
				flowRate.setSouthFlowRateCars(carFlowRate);
				flowRate.setSouthFlowRateTrucks(truckFlowRate);
				break;
			}
			case 'E': //eastbound
			{
				flowRate.setEastFlowRateCars(carFlowRate);
				flowRate.setEastFlowRateTrucks(truckFlowRate);
				break;
			}
			case 'W': //westbound
			{
				flowRate.setWestFlowRateCars(carFlowRate);
				flowRate.setWestFlowRateTrucks(truckFlowRate);
				break;
			}
			default:
			{
				System.out.println("Error.");
				ratesAssigned = false;
			}		
			}
		}
		infile.close(); //close input stream
		if(debugFileRead)
		{ System.out.println(flowRate.toString()); }
		ratesAssigned = true; //no errors encountered
		return ratesAssigned; //return ratesAssigned

	}
	
	/**
	 * Adds a new vehicle to the appropriate queue.
	 * @param direction
	 * 		Queue direction, as a char
	 * @param x
	 * 		Vehicle instance to be added to queue
	 */
	private void addVehicle(char direction, Vehicle x)
	{
		switch(direction) //use switch to assign to correct queue
		{
		case 'N': //add to northbound
		{
			northbound.add(x);
			break;
		}
		case 'S': //add to southbound
		{
			southbound.add(x);
			break;
		}
		case 'E': //add to eastbound
		{
			eastbound.add(x);
			break;
		}
		case 'W': //add to westbound
		{
			westbound.add(x);
			break;
		}
		default:
		{ System.out.println("Error."); }
		}
	}
	
	/**
	 * Determines if new car should be added at time count using a mod
	 * calculation. For each flow rate, if count (in seconds) is evenly
	 * divisible by flow rate (converted to cars/second), then a new car
	 * should be added. Passes direction and new Vehicle instance to the 
	 * addVehicle method.
	 */
	private void checkForNewCars()
	{
		if(count % (1 / ((double)flowRate.getNorthFlowRateCars() / 60)) == 0)
		{
			Vehicle northCar = new Vehicle('c', count); //new Vehicle instance
			addVehicle('N', northCar); //adds Vehicle to northbound queue
		}
		if(count % (1 / ((double)flowRate.getSouthFlowRateCars() / 60)) == 0)
		{
			Vehicle southCar = new Vehicle('c', count); //new Vehicle instance
			addVehicle('S', southCar); //adds Vehicle to southbound queue
		}
		if(count % (1 / ((double)flowRate.getEastFlowRateCars() / 60)) == 0)
		{
			Vehicle eastCar = new Vehicle('c', count); //new Vehicle instance
			addVehicle('E', eastCar); //adds Vehicle to eastbound queue
		}
		if(count % (1 / ((double)flowRate.getWestFlowRateCars() / 60)) == 0)
		{
			Vehicle westCar = new Vehicle('c', count); //new Vehicle instance
			addVehicle('W', westCar); //adds Vehicle to westbound queue
		}
	}
	
	/**
	 * Determines if new truck should be added at time count using a mod
	 * calculation. For each flow rate, if count (in seconds) is evenly
	 * divisible by flow rate (converted to trucks/second), then a new truck
	 * should be added. Passes direction and new Vehicle instance to the 
	 * addVehicle method. If the added truck is at the head of the queue,
	 * sets timeAtLight for the truck to the current count (in seconds).
	 */
	private void checkForNewTrucks()
	{
		if(count % (1 / ((double)flowRate.getNorthFlowRateTrucks() / 60)) == 0)
		{
			Vehicle northTruck = new Vehicle('t', count); //new Vehicle instance
			addVehicle('N', northTruck); //adds Vehicle to northbound queue
			if((northbound.peek().getType() == 't') && (northbound.peek().getTimeEntered() == count))
			{ //if head of queue is the truck we just added
				northbound.peek().setTimeAtLight(count); //need to set timeAtLight to current count
			}
		}
		if(count % (1 / ((double)flowRate.getSouthFlowRateTrucks() / 60)) == 0)
		{
			Vehicle southTruck = new Vehicle('t', count); //new Vehicle instance
			addVehicle('S', southTruck); //adds Vehicle to southbound queue
			if((southbound.peek().getType() == 't') && (southbound.peek().getTimeEntered() == count))
			{ //if head of queue is the truck we just added
				southbound.peek().setTimeAtLight(count); //need to set timeAtLight to current count
			}
		}
		if(count % (1 / ((double)flowRate.getEastFlowRateTrucks() / 60)) == 0)
		{
			Vehicle eastTruck = new Vehicle('t', count); //new Vehicle instance
			addVehicle('E', eastTruck); //adds Vehicle to eastbound queue
			if((eastbound.peek().getType() == 't') && (eastbound.peek().getTimeEntered() == count))
			{ //if head of queue is the truck we just added
				eastbound.peek().setTimeAtLight(count); //need to set timeAtLight to current count
			}
		}
		if(count % (1 / ((double)flowRate.getWestFlowRateTrucks() / 60)) == 0)
		{
			Vehicle westTruck = new Vehicle('t', count); //new Vehicle instance
			addVehicle('W', westTruck); //adds Vehicle to westbound queue
			if((westbound.peek().getType() == 't') && (westbound.peek().getTimeEntered() == count))
			{ //if head of queue is the truck we just added
				westbound.peek().setTimeAtLight(count); //need to set timeAtLight to current count
			}
		}
	}
	
	/**
	 * If Vehicles in northbound and southbound queues are ready to move, adds
	 * corresponding ResultVehicles to the Linked_List instance and removes the
	 * Vehicles from the queues. If truck moves up to the head of either queue,
	 * then timeAtLight for the truck is set to current time count (in seconds).
	 */
	private void moveNSVehicles()
	{
		if(!northbound.isEmpty()) //if vehicles in N queue
		{
			if(northbound.peek().getType() == 'c') //a car is at the light
			{ //add car to Linked_List and delete car from queue
				results.addResultVehicle('c', northbound.poll().getTimeEntered(), count);
			}
			//else if a truck is at the light and has been waiting at least 2 seconds
			else if((northbound.peek().getType() == 't') && 
					(count >= northbound.peek().getTimeAtLight() + 2))
			{ //add truck to Linked_List and delete truck from queue
				results.addResultVehicle('t', northbound.poll().getTimeEntered(), count);
			}
		}
		
		if(!southbound.isEmpty()) //if vehicles in S queue
		{
			if(southbound.peek().getType() == 'c') //a car is at the light
			{ //add car to Linked_List and delete car from queue
				results.addResultVehicle('c', southbound.poll().getTimeEntered(), count);
			}
			//else if a truck is at the light and has been waiting at least 2 seconds
			else if((southbound.peek().getType() == 't') && 
					(count >= southbound.peek().getTimeAtLight() + 2))
			{ //add truck to Linked_List and delete truck from queue
				results.addResultVehicle('t', southbound.poll().getTimeEntered(), count);
			}
		}
		if(trucksAtNorth()) //if truck is now at N light
		{ 
			if(northbound.peek().getTimeAtLight() == 0) //if timeAtLight set to default
			{
				northbound.peek().setTimeAtLight(count); //set timeAtLight to current count
			}
		}
		if(trucksAtSouth()) //if truck is now at S light
		{
			if(southbound.peek().getTimeAtLight() == 0) //if timeAtLight set to default
			{
				southbound.peek().setTimeAtLight(count); //set timeAtLight to current count
			}
		}
	}
	
	/**
	 * Checks to see if truck is at intersection in northbound queue.
	 * @return
	 * 		true if truck is at intersection
	 */
	private boolean trucksAtNorth()
	{
		boolean trucksAtNorth = false; 
		if(!northbound.isEmpty()) //if Vehicles in queue
		{
			if(northbound.peek().getType() == 't')
				trucksAtNorth = true; //true if head vehicle is truck
		}
		return trucksAtNorth;
	}
	
	/**
	 * Checks to see if truck is at intersection in southbound queue.
	 * @return
	 * 		true if truck is at intersection
	 */
	private boolean trucksAtSouth()
	{
		boolean trucksAtSouth = false;
		if(!southbound.isEmpty()) //if Vehicles in queue
		{
			if(southbound.peek().getType() == 't')
				trucksAtSouth = true; //true if head vehicle is truck
		}
		return trucksAtSouth;
	}
	
	/**
	 * If Vehicles in eastbound and westbound queues are ready to move, adds
	 * corresponding ResultVehicles to the Linked_List instance and removes the
	 * Vehicles from the queues. If truck moves up to the head of either queue,
	 * then timeAtLight for the truck is set to current time count (in seconds).
	 */
	private void moveEWVehicles()
	{
		if(!eastbound.isEmpty()) //if vehicles in E queue
		{
			if(eastbound.peek().getType() == 'c') //a car is at the light
			{ //add car to Linked_List and delete car from queue
				results.addResultVehicle('c', eastbound.poll().getTimeEntered(), count);
			}
			//else if a truck is at the light and has been waiting at least 2 seconds
			else if((eastbound.peek().getType() == 't') && 
					(count >= eastbound.peek().getTimeAtLight() + 2))
			{ //add truck to Linked_List and delete truck from queue
				results.addResultVehicle('t', eastbound.poll().getTimeEntered(), count);
			}
		}
		if(!westbound.isEmpty()) //if vehicles in W queue
		{
			if(westbound.peek().getType() == 'c') //a car is at the light
			{ //add car to Linked_List and delete car from queue
				results.addResultVehicle('c', westbound.poll().getTimeEntered(), count);
			}
			//else if a truck is at the light and has been waiting at least 2 seconds
			else if((westbound.peek().getType() == 't') && 
					(count >= westbound.peek().getTimeAtLight() + 2))
			{ //add truck to Linked_List and delete truck from queue
				results.addResultVehicle('t', westbound.poll().getTimeEntered(), count);
			}
		}
		if(trucksAtEast()) //if truck now at E light
		{
			if(eastbound.peek().getTimeAtLight() == 0) //if timeAtLight set to default
			{
				eastbound.peek().setTimeAtLight(count); //set timeAtLight to current count
			}
		}
		if(trucksAtWest()) //if truck now at W light
		{
			if(westbound.peek().getTimeAtLight() == 0) //if timeAtLight set to default
			{
				westbound.peek().setTimeAtLight(count); //set timeAtLight to current count
			}
		}
	}
	
	/**
	 * Checks to see if truck is at intersection in eastbound queue.
	 * @return
	 * 		true if truck is at intersection
	 */
	private boolean trucksAtEast()
	{
		boolean trucksAtEast = false;
		if(!eastbound.isEmpty()) //if Vehicles in queue
		{
			if(eastbound.peek().getType() == 't')
				trucksAtEast = true; //true if head vehicle is truck
		}
		return trucksAtEast;
	}
	
	/**
	 * Checks to see if truck is at intersection in westbound queue.
	 * @return
	 * 		true if truck is at intersection
	 */
	private boolean trucksAtWest()
	{
		boolean trucksAtWest = false;
		if(!westbound.isEmpty()) //if Vehicles in queue
		{
			if(westbound.peek().getType() == 't')
				trucksAtWest = true; //true if head vehicle is truck
		}
		return trucksAtWest;
	}
		
	/**
	 * Sets initial conditions: 2 cars in each queue. Prints board.
	 */
	private void initializeBoard()
	{
		Vehicle car = new Vehicle('c', 0); //new Vehicle instance
		//add two cars to each queue
		addVehicle('N', car);
		addVehicle('N', car);
		addVehicle('S', car);
		addVehicle('S', car);
		addVehicle('E', car);
		addVehicle('E', car);
		addVehicle('W', car);
		addVehicle('W', car);
		printBoard();
	}
	
	/**
	 * Displays board with Vehicles waiting at intersection and number
	 * of Vehicles in each queue. Also displays time count in seconds.
	 */
	private void printBoard()
	{
		boolean debug = false;
		System.out.println();
		printSouth();
		printEastWest();
		printNorth();
		System.out.println();
		System.out.println("At clock: " + count);
		if(debug)
		{
			System.out.println("Direction of travel: " + currentDirection);
			System.out.println("NS light counter: " + greenCountNS);
			System.out.println("EW light counter: " + greenCountEW);
		}
		System.out.println("-------------------------------");
	}
	
	/**
	 * Displays portion of board with southbound queue, southbound queue
	 * statistics, and eastbound queue statistics.
	 */
	private void printSouth()
	{
		System.out.printf("%12s%6d\n", "SB", southbound.size());
		for(int k = 6; k >= 3; k--)
		{
			if(southbound.size() >= k)
			{ System.out.printf("%15s\n", "x"); }
			else
			{ System.out.println(); }
		}
		if(southbound.size() >= 2)
		{ System.out.printf("%10s%5s\n", "EB", "x"); }
		else
		{ System.out.printf("%10s\n", "EB"); }
		if(southbound.size() >= 1)
		{ System.out.printf("%9d%6s\n", eastbound.size(), 
				southbound.peek().getType()); }
		else
		{ System.out.printf("%9d\n", eastbound.size()); }
	}
	
	/**
	 * Displays portion of board with eastbound and westbound queues.
	 */
	private void printEastWest()
	{
		if(eastbound.size() != 0) //if eastbound queue is not empty
		{
			System.out.printf("%8s", " "); //tab to E starting point
			if(eastbound.size() >= 6) //if six Vehicles will be displayed for E
			{
				for(int k = 5; k >= 1; k--)
					System.out.print("x"); //prints 5 x's
				System.out.print(eastbound.peek().getType()); //prints Vehicle type
			}
			else if((eastbound.size() < 6) && (eastbound.size() > 0))
			{
				int q = 6 - eastbound.size();
				switch(q) //adjusts spacing when eastbound queue size < 6
				{
				case 1:
				{
					System.out.printf("%1s", " ");
					break;
				}
				case 2:
				{
					System.out.printf("%2s", " ");
					break;
				}
				case 3:
				{
					System.out.printf("%3s", " ");
					break;
				}
				case 4:
				{
					System.out.printf("%4s", " ");
					break;
				}
				case 5:
				{
					System.out.printf("%5s", " ");
					break;
				}
				default:
				{ System.out.println("error."); }
				} //end switch
				
				for(int j = eastbound.size() - 1; j > 0; j--)
				{ System.out.print("x"); }
				System.out.print(eastbound.peek().getType());
			}
			System.out.printf("%1s", " "); //tab to W starting point
			if(westbound.size() >= 6) //if six Vehicles will be displayed
			{
				//print Vehicle type
				System.out.print(westbound.peek().getType());
				for(int k = 5; k >= 1; k--)
				{ System.out.print("x"); } //prints 5 x's
			}
			else if((westbound.size() < 6) && (westbound.size() > 0))
			{
				//print Vehicle type
				System.out.print(westbound.peek().getType());
				for(int k = westbound.size(); k > 1; k--)
				{ System.out.print("x"); } //adjust number of x's
			}
		}
		else // eastbound queue is empty, need to adjust westbound start point
		{
			System.out.printf("%15s", " "); //tab to West starting point
			if(westbound.size() >= 6) //if six Vehicles will be displayed
			{
				//print Vehicle type
				System.out.print(westbound.peek().getType());
				for(int k = 5; k >= 1; k--)
				{ System.out.print("x"); } //prints 5 x's
			}
			else if((westbound.size() < 6) && (westbound.size() > 0))
			{
				//print Vehicle type
				System.out.print(westbound.peek().getType());
				for(int k = westbound.size(); k > 1; k--)
				{ System.out.print("x"); } //adjust number of x's 
			}	
		}
		System.out.println(); //extra line for spacing
	}
	
	/**
	 * Displays portion of board with northbound queue, northbound queue
	 * statistics, and westbound queue statistics.
	 */
	private void printNorth()
	{
		if(northbound.size() >= 1)
		{ System.out.printf("%15s\n", northbound.peek().getType()); }
		else
		{ System.out.println(); }
		if(northbound.size() >= 2)
		{ System.out.printf("%15s%6s\n", "x", "WB"); }
		else
		{ System.out.printf("%20s\n", "WB"); }
		if(northbound.size() >= 3)
		{ System.out.printf("%15s%6s\n", "x", westbound.size()); }
		else
		{ System.out.printf("%20s\n", westbound.size()); }
		for(int k = 6; k >= 4; k--)
		{
			if(northbound.size() >= k)
			{ System.out.printf("%15s\n", "x"); }
			else
			{ System.out.println(); }
		}
		System.out.printf("%12s%6d\n", "NB", northbound.size());
	}
	
	/**
	 * Changes direction. If 0 (for NS), changes to 1 (for EW), and 
	 * vice versa.
	 * @return
	 * 		value of currentDirection (either 0 or 1), as an int
	 */
	private int changeDirection()
	{
		if(currentDirection == 0)
			currentDirection = 1;
		else if(currentDirection == 1)
			currentDirection = 0;
		return currentDirection;
	}
	
	/**
	 * Calls initializeBoard method. Increments count to 1 second, to 
	 * start simulation. Sets NS and EW green light counter to 0. Sets
	 * currentDirection to 0, indicating NS traffic is moving. Runs
	 * simulation for 120 seconds. adjusting flow of traffic based on
	 * project requirements. Displays board at each second of the 
	 * simulation.
	 */
	public void runSimulation()
	{
		initializeBoard();
		count++; //increment count to 1 second
		greenCountNS = 0; //set NS green light to 0
		greenCountEW = 0; //set EW green light to 0
		currentDirection = 0; //start with NS traffic
		
		for (count = 1; count < 121; count++) //runs from 1-120 seconds
		{	
			if(currentDirection == 0) //if NS light is green
			{
				greenCountNS++; //increment NS light counter
				if(greenCountNS <= 30) //NS light green for 30 seconds minimum
				{
					moveNSVehicles();
					checkForNewCars();
					checkForNewTrucks();
					printBoard();
					if(debug)
					{ printQueueHeads(); }
				}
				else if((greenCountNS > 30) && (greenCountNS <= 40) && 
						eastbound.isEmpty() && westbound.isEmpty())
				{ //if no EW traffic, extend NS green by 10 seconds
					moveNSVehicles();
					checkForNewCars();
					checkForNewTrucks();
					printBoard();
					if(debug)
					{ printQueueHeads(); }
				}
				else //light change
				{
					greenCountEW = 0; //reset EW light counter to 0
					System.out.println("NS green met 30second minimum. EW traffic waiting. Change light.");
					count--; //adjust count due to traffic change
					changeDirection();
				}
			}
			else //currentDirection == 1; EW light is green
			{
				greenCountEW++; //increment EW light counter
				if(greenCountEW <= 10) //EW light green for 10 seconds minimum
				{
					moveEWVehicles();
					checkForNewCars();
					checkForNewTrucks();
					printBoard();
					if(debug)
					{ printQueueHeads(); }
				}
				else if((greenCountEW <= 30) && ((!eastbound.isEmpty()) || (!westbound.isEmpty())))
				{ //EW light green for 30 seconds max. as long as E or W has traffic
					
					moveEWVehicles();
					checkForNewCars();
					checkForNewTrucks();
					printBoard();
					if(debug)
					{ printQueueHeads(); }
				}
				else //light change
				{
					greenCountNS = 0; //reset NS light counter to 0
					System.out.println("EW is empty. NS is primary route. Change light.");
					count--; //adjust count due to traffic change
					changeDirection();
				}
			}
		}
	}
	
	/**
	 * For debugging. If queues have contents, displays type, timeEntered,
	 * and timeAtLight for heads of each queue.
	 */
	private void printQueueHeads()
	{
		if(!northbound.isEmpty()) //if N queue has Vehicles
		{
			System.out.println("Northbound type, time entered, time at light:");
			System.out.printf("%c%s%d%s%d\n", northbound.peek().getType(), ", ", 
					northbound.peek().getTimeEntered(), ", ",
					northbound.peek().getTimeAtLight());
		}
		if(!southbound.isEmpty()) //if S queue has Vehicles
		{
			System.out.println("Southbound type, time entered, time at light:");
			System.out.printf("%c%s%d%s%d\n", southbound.peek().getType(), ", ",
					southbound.peek().getTimeEntered(), ", ",
					southbound.peek().getTimeAtLight());
		}
		if(!eastbound.isEmpty()) //if E queue has Vehicles
		{
			System.out.println("Eastbound type, time entered, time at light:");
			System.out.printf("%c%s%d%s%d\n", eastbound.peek().getType(), ", ", 
					eastbound.peek().getTimeEntered(), ", ",
					eastbound.peek().getTimeAtLight());
		}
		if(!westbound.isEmpty()) //if W queue has Vehicles
		{
			System.out.println("Westbound type, time entered, time at light:");
			System.out.printf("%c%s%d%s%d\n", westbound.peek().getType(), ", ",
					westbound.peek().getTimeEntered(), ", ",
					westbound.peek().getTimeAtLight());
		}
	}
	
}
