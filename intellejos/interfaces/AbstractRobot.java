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

package interfaces;

/**
* Interface class for both real and simulated robots.
* Defines all the input and output methods a controller
* can call.
*
* @original author Graham Ritchie, modified by Tijn Kooijmans
*/
public interface AbstractRobot
{
	/******************************************
	   Methods for moving the robot - output
	******************************************/

	/**
	* Sets the robot moving forwards, this will continue until some other
	* method is called to stop it.
	*/
	public abstract void forward();

	/**
	* Sets the robot moving backwards, this will continue until some other
	* method is called to stop it.
	*/
	public abstract void backward();

	/**
	* Sets the robot spinning right, this will continue until some other
	* method is called to stop it.
	*/
	public abstract void right();

	/**
	* Sets the robot spinning left, this will continue until some other
	* method is called to stop it.
	*/
	public abstract void left();

	/**
	* Stops all motors immediately
	*/
	public abstract void stopMoving();


	/**
	* Makes the robot beep
	*/
	public abstract void beep();


	/*************************************************
	  Methods for reading the robot's sensors - input
	**************************************************/

	/**
	* Get the current reading of this sensor
	*
	* @return the current value
	*/
	public abstract int getSensor1();

	/**
	* Get the current reading of this sensor
	*
	* @return the current value
	*/
	public abstract int getSensor2();

	/**
	* Get the current reading of this sensor
	*
	* @return the current value
	*/
	public abstract int getSensor3();

	/**
	* Sets the type of sensor for the robot. 1 = touch, 3 = light.
	*/
	public abstract void setType(int sensorNr, int type);
}
