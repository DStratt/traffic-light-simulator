/**
 * File: 	ResultVehicle.java
 * Author: 	Diane Stratton
 * Class: 	Hood CS 504, Fall 2015
 * Project: Project 2 - Traffic Simulator
 * Date: 	10 October 2015
 * Version:	1
 * Instructions:  See README file
 */

//is a subclass of Vehicle
public class ResultVehicle extends Vehicle
{
	//declare variables
	private int timeRemoved; //time vehicle removed from queue
	private int waitTime; //total time vehicle spent waiting in queue
	private ResultVehicle link; //ResultVehicle is node for Linked_List
	
	public ResultVehicle() //default constructor
	{
		super(); //calls base class constructor for type, timeEntered, timeAtLight
		timeRemoved = 0; //set to default
		waitTime = timeRemoved - super.getTimeEntered(); //calculates wait time
		//uses call to base class getter to retrieve timeEntered
	}
	
	/**
	 * Constructor with char and two int parameters. Calls base class
	 * constructor for type and timeEntered parameters. Sets timeRemoved to
	 * timeRemoved parameter. Value for waitTime calculated by subtracting
	 * timeEntered from timeRemoved.
	 * @param type
	 * 		vehicle type, as a char (c for car, t for truck)
	 * @param timeEntered
	 * 		time (in seconds) that vehicle entered queue, as an int
	 * @param timeRemoved
	 * 		time (in seconds) that vehicle was removed from queue, as an int
	 */
	public ResultVehicle(char type, int timeEntered, int timeRemoved)
	{
		super(type, timeEntered);
		this.timeRemoved = timeRemoved;
		waitTime = timeRemoved - timeEntered;
	}
	
	public int getTimeRemoved()
	{ return timeRemoved; }
	
	public int getWaitTime()
	{ return waitTime; 	}
	
	public ResultVehicle getLink()
	{ return link; }
	
	public void setTimeRemoved(int timeRemoved)
	{ this.timeRemoved = timeRemoved; }
	
	public void setWaitTime(int waitTime)
	{ this.waitTime = waitTime; }
	
	public void setLink(ResultVehicle link)
	{ this.link = link; }
	
	/**
	 * Calculates value for waitTime. Uses a call to base class getter to
	 * retrieve timeEntered, which is then subtracted from timeRemoved.
	 * Method is used by Linked_List class when new ResultVehicle is 
	 * added to Linked_List instance.
	 */
	public void calculateWaitTime()
	{ this.waitTime = this.timeRemoved - super.getTimeEntered(); }
	
	//use toString for debugging
	public String toString()
	{
		return "Vehicle type: " + super.getType() + 
				" and wait time is: " + waitTime;
	}

}
