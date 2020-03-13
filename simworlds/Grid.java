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

import simworldobjects.*;

/**
* Models a 5 x 5 grid layout
*
* @author Tijn Kooijmans
*/

public class Grid extends BasicSimWorld
{

	public Grid()
	{
		// initialise the world size
		super(1000,1000,1100);

		SimPath path1=new SimPath(500.0,0.0,100.0,0.0,1000.0,20.0);
		addObject(path1);

		SimPath path2=new SimPath(500.0,0.0,300.0,0.0,1000.0,20.0);
		addObject(path2);

		SimPath path9=new SimPath(500.0,0.0,500.0,0.0,1000.0,20.0);
		addObject(path9);

		SimPath path10=new SimPath(500.0,0.0,700.0,0.0,1000.0,20.0);
		addObject(path10);

		SimPath path11=new SimPath(500.0,0.0,900.0,0.0,1000.0,20.0);
		addObject(path11);


		SimPath path4=new SimPath(100.0,0.0,400.0,90.0,1000.0,20.0);
		addObject(path4);

		SimPath path5=new SimPath(300.0,0.0,400.0,90.0,1000.0,20.0);
		addObject(path5);

		SimPath path6=new SimPath(500.0,0.0,400.0,90.0,1000.0,20.0);
		addObject(path6);

		SimPath path7=new SimPath(700.0,0.0,400.0,90.0,1000.0,20.0);
		addObject(path7);

		SimPath path8=new SimPath(900.0,0.0,400.0,90.0,1000.0,20.0);
		addObject(path8);
	}
}
