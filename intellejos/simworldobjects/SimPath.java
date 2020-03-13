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

/**
* Models a simple path object
*
* @author Tijn Kooijmans
*/
public class SimPath extends BasicSimObject
{
	/**
	* Sets up this path
	*
	* @param x the path's x coordinate
	* @param y the path's y coordinate
	* @param z the path's z coordinate
	* @param b the path's bearing
	* @param length the path's length
	* @param width the path's width
	*/
	public SimPath(double x, double y, double z, double b, double length, double width)
	{
		// initialise the SimObject
		super(0.0,length,width,"path",x,y,z,0.0,b);
	}
}
