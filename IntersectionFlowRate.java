/**
 * File: 	IntersectionFlowRate.java
 * Author: 	Diane Stratton
 * Class: 	Hood CS 504, Fall 2015
 * Project: Project 2 - Traffic Simulator
 * Date: 	10 October 2015
 * Version:	1
 * Instructions:  See README file
 */

public class IntersectionFlowRate
{
	//declare int variable for each flow rate
	private int northFlowRateCars;
	private int southFlowRateCars;
	private int eastFlowRateCars;
	private int westFlowRateCars;
	private int northFlowRateTrucks;
	private int southFlowRateTrucks;
	private int eastFlowRateTrucks;
	private int westFlowRateTrucks;
	
	public IntersectionFlowRate()
	{ //default constructor, all rates set to 0
		this.northFlowRateCars = 0;
		this.southFlowRateCars = 0;
		this.eastFlowRateCars = 0;
		this.westFlowRateCars = 0;
		this.northFlowRateTrucks = 0;
		this.southFlowRateTrucks = 0;
		this.eastFlowRateTrucks = 0;
		this.westFlowRateTrucks = 0;
	}

	public int getNorthFlowRateCars()
	{  //return north car flow rate
		return northFlowRateCars;
	}

	public int getSouthFlowRateCars()
	{  //return south car flow rate
		return southFlowRateCars;
	}
	
	public int getEastFlowRateCars()
	{  //return east car flow rate 
		return eastFlowRateCars;
	}

	public int getWestFlowRateCars()
	{  //return west car flow rate
		return westFlowRateCars;
	}

	public int getNorthFlowRateTrucks()
	{  //return north truck flow rate
		return northFlowRateTrucks;
	}

	public int getSouthFlowRateTrucks()
	{  //return south truck flow rate
		return southFlowRateTrucks;
	}
	
	public int getEastFlowRateTrucks()
	{  //return east truck flow rate
		return eastFlowRateTrucks;
	}

	public int getWestFlowRateTrucks()
	{  //return west truck flow rate
		return westFlowRateTrucks;
	}

	public void setNorthFlowRateCars(int northFlowRateCars)
	{ this.northFlowRateCars = northFlowRateCars; }

	public void setSouthFlowRateCars(int southFlowRateCars)
	{ this.southFlowRateCars = southFlowRateCars; }
	
	public void setEastFlowRateCars(int eastFlowRateCars)
	{ this.eastFlowRateCars = eastFlowRateCars; }

	public void setWestFlowRateCars(int westFlowRateCars)
	{ this.westFlowRateCars = westFlowRateCars; }

	public void setNorthFlowRateTrucks(int northFlowRateTrucks)
	{ this.northFlowRateTrucks = northFlowRateTrucks; }

	public void setSouthFlowRateTrucks(int southFlowRateTrucks)
	{ this.southFlowRateTrucks = southFlowRateTrucks; }
	
	public void setEastFlowRateTrucks(int eastFlowRateTrucks)
	{ this.eastFlowRateTrucks = eastFlowRateTrucks; }

	public void setWestFlowRateTrucks(int westFlowRateTrucks)
	{ this.westFlowRateTrucks = westFlowRateTrucks; }
	
	//for debugging
	public String toString()
	{
		return "Car/truck rates for N: " + northFlowRateCars + " and " + northFlowRateTrucks +
				"; for S: " + southFlowRateCars + " and " + southFlowRateTrucks +
				"; for E: " + eastFlowRateCars + " and " + eastFlowRateTrucks +
				"; for W: " + westFlowRateCars + " and " + westFlowRateTrucks;
	}

}
