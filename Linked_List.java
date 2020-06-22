/**
 * File: 	Linked_List.java
 * Author: 	Diane Stratton
 * Class: 	Hood CS 504, Fall 2015
 * Project: Project 2 - Traffic Simulator
 * Date: 	10 October 2015
 * Version:	1
 * Instructions:  See README file
 */

public class Linked_List
{
	//declare ResultVehicle objects to use for navigating Linked_List
	private ResultVehicle FRONT, REAR, CURSOR, INSERT;
	
	/**
	 * Default constructor for Linked_List. Sets pointer ResultVehicle objects FRONT, REAR,
	 *  and CURSOR to null.
	 */
	public Linked_List()
	{ //begin constructor
		
		FRONT = null;
		REAR = null;
		CURSOR = null;
		
	} //end constructor
	
	/**
	 * Determines if Linked_List instance is empty
	 * @return
	 *    true if Linked_List instance is empty, otherwise false
	 */
	public boolean isEmpty()
	{
		if((FRONT == null) && (REAR == null))
			return true;
		else
			return false;
	}
	
	/**
	 * Creates new instance of ResultVehicle object and adds to Linked_List instance.
	 * @param type
	 *    type of ResultVehicle, as a char
	 * @param timeEntered
	 *    count (seconds) when the Vehicle originally entered queue, as an int
	 * @param timeRemoved
	 *    count (seconds) when the Vehicle was removed from the queue, as an int   
	 */
	public void addResultVehicle(char type, int timeEntered, int timeRemoved)
	{ //start of method addResultVehicle
		
		INSERT = new ResultVehicle(); //creates new ResultVehicle in Linked_List
		INSERT.setType(type); //sets ResultVehicle type
		INSERT.setTimeEntered(timeEntered); //sets ResultVehicle timeEntered
		INSERT.setTimeRemoved(timeRemoved); //sets ResultVehicle timeRemoved
		INSERT.calculateWaitTime(); //sets ResultVehicle waitTime
		
		
		if(isEmpty() == true) //if the Linked_List is empty
		{
			//set the new ResultVehicle as the first object in the Linked_List
			INSERT.setLink(null); //set link of new ResultVehicle to null
			FRONT = INSERT; //sets FRONT pointer to new ResultVehicle
		}
		else //if the Linked_List already contains ResultVehicle(s)
		{
			//add the new ResultVehicle to the end of the Linked_List
			REAR.setLink(INSERT); //set link of REAR pointer to new ResultVehicle
			INSERT.setLink(null); //set link of new ResultVehicle to null
		}
		REAR = INSERT; //reset REAR pointer to point to new ResultVehicle
		
	} //end of method addResultVehicle
	
	/**
	 * Counts total number of ResultVehicles in Linked_List instance.
	 * @param vehicleList
	 * 		Linked_List instance that represents vehicles that have passed
	 * through the intersection 
	 * @return
	 * 		number of ResultVehicles in Linked_List, as an int
	 */
	public int countResultVehicles(Linked_List vehicleList)
	{
		int listSize = 0; //vehicle counter
		for(CURSOR = FRONT; CURSOR != null; CURSOR = CURSOR.getLink())
		{ listSize++; } //add 1 for every ResultVehicle
		return listSize; //return number of vehicles
	}
	
	/**
	 * Determine average wait time for all ResultVehicles in Linked_List
	 * instance.
	 * @param vehicleList
	 * 		Linked_List instance that represents vehicles that have passed
	 * through the intersection
	 * @return
	 * 		average wait time for all ResultVehicles, as a double
	 */
	public double findAverageWaitTime(Linked_List vehicleList)
	{
		int waitTimeSum = 0; //variable to hold sum of all wait times
		double aveWaitTime; //declare double variable for average wait time
		
		for(CURSOR = FRONT; CURSOR != null; CURSOR = CURSOR.getLink())
		{
			waitTimeSum = waitTimeSum + CURSOR.getWaitTime(); //add wait times
		}
		//calculate average wait time by dividing sum by total vehicle count
		//need to cast waitTimeSum to double
		aveWaitTime = (double)waitTimeSum / countResultVehicles(vehicleList);
		return aveWaitTime; //return average wait time
	}
	
	/**
	 * Count number of cars in Linked_List instance.
	 * @param vehicleList
	 * 		Linked_List instance that represents vehicles that have passed
	 * through the intersection
	 * @return
	 * 		number of cars in Linked_List, as an int
	 */
	public int countResultCars(Linked_List vehicleList)
	{
		int carCount = 0; //car counter
		for(CURSOR = FRONT; CURSOR != null; CURSOR = CURSOR.getLink())
		{
			if(CURSOR.getType() == 'c')
			{ carCount++; } //add 1 if vehicle is a car
		}
		return carCount; //return number of cars
	}
	
	/**
	 * Count number of trucks in Linked_List instance. Subtract
	 * number of cars from total number of vehicles.
	 * @param vehicleList
	 * 		Linked_List instance that represents vehicles that have passed
	 * through the intersection
	 * @return
	 * 		number of trucks in Linked_List, as an int
	 */
	public int countResultTrucks(Linked_List vehicleList)
	{
		int truckCount; //truck counter
		//determine number of trucks by subtracting number of cars from total number of vehicles
		truckCount = countResultVehicles(vehicleList) - countResultCars(vehicleList);
		return truckCount; //return number of trucks
	}
	
	/**
	 * Display required output: total number of vehicles through intersection, total
	 * number of cars through intersection, total number of trucks through intersection,
	 * and average wait time for intersection.
	 * @param vehicleList
	 * 		Linked_List instance that represents vehicles that have passed
	 * through the intersection
	 */
	public void displayFinalStatistics(Linked_List vehicleList)
	{
		System.out.println("The final results are:");
		System.out.printf("%s%d\n", 
				"The number of vehicles that passed through the intersection is: ",
				countResultVehicles(vehicleList));
		System.out.printf("%s%d\n", 
				"The number of cars that passed through the intersection is: ", 
				countResultCars(vehicleList));
		System.out.printf("%s%d\n", 
				"The number of trucks that passed through the intersection is: ",
				countResultTrucks(vehicleList));
		System.out.printf("%s%.6f\n", 
				"The average wait time for this intersection is: ", 
				findAverageWaitTime(vehicleList));
	}
	
	/**
	 * Debugging method. Use to display data for each ResultVehicle in
	 * Linked_List instance.
	 */
	public void displayResultVehicleList()
	{		
		if(isEmpty() == true) //provides feedback if Linked_List is empty
		{
			System.out.println("No vehicles to display.");
			return;
		}
		else
		{
			System.out.println("Vehicles that have moved through intersection are:");
			for(CURSOR = FRONT; CURSOR != null; CURSOR = CURSOR.getLink())
			{
				//display info for each ResultVehicle in Linked_List instance 
				System.out.printf("%c%s%d%s%d%s%d%s%d%n", CURSOR.getType(), ", ", 
						CURSOR.getTimeEntered(), ", ", CURSOR.getTimeRemoved(), ", ", 
						CURSOR.getTimeAtLight(), ", ", CURSOR.getWaitTime());
				System.out.println();
			}
		}
	}
	
}
