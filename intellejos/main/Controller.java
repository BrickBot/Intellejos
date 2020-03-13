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

package main;

/**
* Controller class.
*
* This class interfaces between the lejos classes and the simworld
*
* @original author Graham Ritchie, modified by Tijn Kooijmans
*/

import interfaces.*;
import java.beans.*;
import java.lang.reflect.*;

public class Controller extends Thread
{
	public AbstractRobot robot = null;
	private boolean running;
	private int motorA = 0; //0=stop, 1=forward, 2=backward
	private int motorB = 0; //0=stop, 1=forward, 2=backward
	private int[] sensors;
	private String robotClass;

	/**
	* Sensor type constants (see getSensors() for explanation)
	*/
	public static int SENSOR_TYPE_LIGHT=3;
	public static int SENSOR_TYPE_TOUCH=1;

	public Controller(String name)
	{
		robotClass = name;
	}

	public void initController(AbstractRobot r)
	{
		robot=r;
		if (robotClass == null)
		{
			// do nothing
		}
		else
		{
			RobotLoader rl = new RobotLoader(robotClass);
			rl.start();
		}
	}

	public AbstractRobot getRobot()
	{
		return robot;
	}

	public int[] getSensors()
	{
		return sensors;
	}

	public void run()
	{
		running=true;
	}

	public void halt()
	{
		// stops the robot
		stop('A');
		stop('B');
		running=false;
	}

	public void forward(char motorNr)
	{
		// define right direction for robot

		if (motorNr == 'A')
		{
			motorA = 1;

			if (motorB == 0)
			{
				robot.right();
			}
			else if (motorB == 1)
			{
				robot.forward();
			}
			else if (motorB == 2)
			{
				robot.right();
			}

		}
		else if (motorNr == 'B')
		{
			motorB = 1;

			if (motorA == 0)
			{
				robot.left();
			}
			else if (motorA == 1)
			{
				robot.forward();
			}
			else if (motorA == 2)
			{
				robot.left();
			}

		}
	}

	public void backward(char motorNr)
	{
		// define right direction for robot

		if (motorNr == 'A')
		{
			motorA = 2;

			if (motorB == 0)
			{
				robot.left();
			}
			else if (motorB == 1)
			{
				robot.left();
			}
			else if (motorB == 2)
			{
				robot.backward();
			}

		}
		else if (motorNr == 'B')
		{
			motorB = 2;

			if (motorA == 0)
			{
				robot.right();
			}
			else if (motorA == 1)
			{
				robot.right();
			}
			else if (motorA == 2)
			{
				robot.backward();
			}

		}
	}

	public void stop(char motorNr)
	{
		// define right direction for robot

		if (motorNr == 'A')
		{
			motorA = 0;

			if (motorB == 0)
			{
				robot.stopMoving();
			}
			else if (motorB == 1)
			{
				robot.left();
			}
			else if (motorB == 2)
			{
				robot.right();
			}

		}
		else if (motorNr == 'B')
		{
			motorB = 0;

			if (motorA == 0)
			{
				robot.stopMoving();
			}
			else if (motorA == 1)
			{
				robot.right();
			}
			else if (motorA == 2)
			{
				robot.left();
			}

		}
	}

	public int readSensor(int sensorNr)
	{
		int aValue = 0;
		if (sensorNr == 1)
		{
			aValue = robot.getSensor1();
		}
		else if (sensorNr == 2)
		{
			aValue = robot.getSensor2();
		}
		else if (sensorNr == 3)
		{
			aValue = robot.getSensor3();
		}
		return aValue;
	}
}
