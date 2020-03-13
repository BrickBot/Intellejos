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

import lejos.platform.rcx.*;
import lejos.util.*;

/**
* Example of a Intellejos robot that uses Lejos classes
*
* @author Tijn Kooijmans
*/

public class Jojo implements TimerListener
{

	public Jojo()
	{
		Motor.A.forward();
		Motor.B.backward();

		Timer t = new Timer(2000, this);
		t.start();
	}

	public void timedOut()
	{
		Motor.A.reverseDirection();
		Motor.B.reverseDirection();
	}

	public static void main(String[] args)
	{
		Jojo jj = new Jojo();
	}
}
