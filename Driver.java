/**
 * File: 	Driver.java
 * Author: 	Diane Stratton
 * Class: 	Hood CS 504, Fall 2015
 * Project: Project 2 - Traffic Simulator
 * Date: 	14 October 2015
 * Version:	1
 * Instructions:  See README file
 */

public class Driver
{ //start of class Driver

	public static void main(String[] args)
	{ //start of method main
		
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
		
	} //end of method main

} //end of class Driver
