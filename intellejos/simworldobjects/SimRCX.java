/*

Copyright 2004 Tijn Kooijmans <intellejos@kooijmans.nu>

This file is part of Intellejos. Intellejos is a modification of Intellego,
developed by Graham Ritchie.

Intellejos is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

Intellejos is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Intellego; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/

package simworldobjects;

import interfaces.*;
import main.*;

import java.awt.*;
import java.lang.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.geom.*;

/**
* Simulates an RCX for use in SimWorld
*
* @original author Graham Ritchie, modified by Tijn Kooijmans
*/
public class SimRCX extends BasicSimObject implements AbstractRobot
{
	public static final int STATE_STOPPED=1;
	public static final int STATE_FORWARD=2;
	public static final int STATE_BACKWARD=3;
	public static final int STATE_RIGHT=4;
	public static final int STATE_LEFT=5;

	private int state;
	private SimWorld world;

	private SimSensor S1;
	private SimSensor S2;
	private SimSensor S3;

	private Controller controller;

	/**
	* Constructor: sets up the robot with some predefined parameters
	*
	* @param w the SimWorld where this SimRCX resides
	* @param c the Controller of this SimRCX
	*/
	public SimRCX(SimWorld w, Controller c)
	{
		// initialise the SimObject
		super(30.0,40.0,60.0,"robot",600.0,0.0,200.0,0.0,160.0);

		// set this robot's world
		this.world=w;

		// set this robot's controler
		this.controller=c;

		setupRobot();
	}

	/**
	* Constructor: sets up the robot according to the parameters
	*/
	public SimRCX(SimWorld w, Controller c, double height, double width, double length, String type, double x, double y, double z, double bearingXY, double bearingXZ)
	{
		// initialise the SimObject
		super(height,width,length,type,x,y,z,bearingXY,bearingXZ);

		// set this robot's world
		world=w;

		// set this robot's controler
		this.controller=c;

		setupRobot();
	}

	/**
	* Sets up robot: adds sensors etc.
	*/
	private void setupRobot()
	{
		// set the robot's initial state to stopped
		this.state=SimRCX.STATE_STOPPED;

		setType(1, Controller.SENSOR_TYPE_LIGHT);
		setType(2, Controller.SENSOR_TYPE_LIGHT);
		setType(3, Controller.SENSOR_TYPE_LIGHT);

		// add sensors to the world
		world.addObject(S1);
		world.addObject(S2);
		world.addObject(S3);
	}

	public void setType(int sensorNr, int type)
	{
		if (sensorNr == 1)
		{
			if(type == Controller.SENSOR_TYPE_LIGHT)
				S1=new SimLightSensor(this,-this.getWidth(),-this.getLength()/4);
			else
				S1=new SimTouchSensor(this,-this.getWidth(),-this.getLength()/4);

			world.addObject(S1);
		}
		else if (sensorNr == 2)
		{
			if(type == Controller.SENSOR_TYPE_LIGHT)
				S2=new SimLightSensor(this,0.0,-(this.getLength()/2)-25);
			else
				S2=new SimTouchSensor(this,0.0,-(this.getLength()/2)-25);

			world.addObject(S2);
		}
		else if (sensorNr == 3)
		{
			if(type == Controller.SENSOR_TYPE_LIGHT)
				S3=new SimLightSensor(this,this.getWidth(),-this.getLength()/4);
			else
				S3=new SimTouchSensor(this,this.getWidth(),-this.getLength()/4);

			world.addObject(S3);
		}

	}

	public SimWorld getWorld()
	{
		return this.world;
	}

	/*******************************************************************
	                 Methods required by AbstractRobot
	*******************************************************************/

	/**
	* Sets the robot moving forwards, this will continue until some other
	* method is called to stop it.
	*/
	public void forward()
	{
		stopMoving();
		this.state=SimRCX.STATE_FORWARD;
		this.setDesiredVelocity(1);
	}

	/**
	* Sets the robot moving backwards, this will continue until some other
	* method is called to stop it.
	*/
	public void backward()
	{
		stopMoving();
		this.state=SimRCX.STATE_BACKWARD;
		this.setDesiredVelocity(-1);
	}

	/**
	* Sets the robot spinning right, this will continue until some other
	* method is called to stop it.
	*/
	public void right()
	{
		stopMoving();
		this.state=SimRCX.STATE_RIGHT;
		this.setDesiredBearingVelocityXZ(1);
	}

	/**
	* Sets the robot spinning left, this will continue until some other
	* method is called to stop it.
	*/
	public void left()
	{
		stopMoving();
		this.state=SimRCX.STATE_LEFT;
		this.setDesiredBearingVelocityXZ(-1);
	}

	/**
	* Stops all motors immediately
	*/
	public void stopMoving()
	{
		this.state=SimRCX.STATE_STOPPED;
		this.setDesiredVelocity(0);
		this.setDesiredBearingVelocityXZ(0);
	}

	/**
	* Makes the robot beep
	*/
	public void beep()
	{
		Toolkit.getDefaultToolkit().beep();
	}

	/**
	* Get the current reading of this sensor
	*
	* @return the current value
	*/
	public int getSensor1()
	{
		return S1.getValue();
	}

	/**
	* Get the current reading of this sensor
	*
	* @return the current value
	*/
	public int getSensor2()
	{
		return S2.getValue();
	}

	/**
	* Get the current reading of this sensor
	*
	* @return the current value
	*/
	public int getSensor3()
	{
		return S3.getValue();
	}
}
