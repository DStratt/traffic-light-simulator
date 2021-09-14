/**
 * File: 	Driver.java
 * Project: 	Project 2 - Traffic Simulator
 * Instructions:  See README file
 */

public class Driver
{

	public static void main(String[] args)
	{
		
		String filename = null; //flow rate input filename
		try
		{
			if(args[0] != null) //if user specifies filename at command line
			{ 
				//set local variable equal to filename input by user
				String inputFileName = args[0];
				//set method variable equal to local variable
				filename = inputFileName;
			}
		}
		catch(ArrayIndexOutOfBoundsException e) //handle exception
		{
			//set local variable to default output filename
			filename = "input.txt";
		}
		
		//declare and instantiate an instance of TrafficSim,
		//providing the input filename to the constructor
		TrafficSim sim = new TrafficSim(filename);
		
	}

}
