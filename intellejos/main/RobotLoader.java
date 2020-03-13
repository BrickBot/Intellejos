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

import java.lang.reflect.*;
import java.io.*;
import java.util.*;

/**
* Robot Loader class.
*
* Loads the main method of a class file that uses Lejos to control the
* simulated robot.
*
* @author Tijn Kooijmans
*/

public class RobotLoader extends Thread
{
	private String robotName;

	public RobotLoader(String aRobotName)
	{
		robotName = aRobotName;
	}

	public void run()
	{
		try
    	{
			BufferedReader br = new BufferedReader(new FileReader("robots/args.txt"));

       		Vector list = new Vector();
       		String in = "empty";
       		while (in != null)
       		{
				in = br.readLine();
		    	if (in != null)
					list.add(in);
			}

			String args[] = new String[list.size()];
			for (int i = 0; i < args.length; i++)
				args[i] = (String)list.get(i);

       		// create reference to robot Class
       		Class rClass = Class.forName(robotName);

	        // start robot by calling the main method of the robot Class
			String s[] = new String[2];
			s[0] = "dummy";
			s[1] = "object";
			Class parTypes[] = new Class[1];
			parTypes[0] = s.getClass();

			Object pars[] = new Object[1];
			pars[0] = args;

			rClass.getMethod("main", parTypes).invoke(null, pars);

			//stop robot when finished
			//SimUI.getController().halt();

       	}
       	catch (Exception e)
       	{
			System.out.println("Exception in RobotLoader: " + e.getMessage());
			MainInterface.displayMessage("Error while loading robot");
			SimUI.getController().halt();
		}
	}
}