/**
 * File: 	Vehicle.java
 * Project: 	Project 2 - Traffic Simulator
 * Instructions:  See README file
 */

public class Vehicle
{
	//declare variables
	private char type; //c for car, t for truck, or u for unknown
	private int timeEntered; //time vehicle enters queue
	private int timeAtLight; //time vehicle arrives at light
	//timeAtLight used to time truck waiting period
	
	public Vehicle() //default constructor
	{
		type = 'u'; //for type unknown
		timeEntered = 0;
		timeAtLight = 0;
	}
	
	/**
	 * Constructor with char and int parameters.
	 * @param type
	 * 		Vehicle type, as a char (c for car, t for truck)
	 * @param timeEntered
	 * 		time (in seconds) that vehicle entered queue, as an int
	 */
	public Vehicle(char type, int timeEntered)
	{ 
		this.type = type;
		this.timeEntered = timeEntered;
		this.timeAtLight = 0; //set to default
	}

	protected char getType()
	{ return type; }

	protected int getTimeEntered()
	{ return timeEntered; }

	protected int getTimeAtLight()
	{ return timeAtLight; }
	
	protected void setType(char type)
	{ this.type = type; }

	protected void setTimeEntered(int timeEntered)
	{ this.timeEntered = timeEntered; }
	
	protected void setTimeAtLight(int timeAtLight)
	{ this.timeAtLight = timeAtLight; }

}
