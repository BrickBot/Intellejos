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

import javax.swing.*;
import java.awt.*;

/**
* Interface class for all SimDisplays
*
* @author Graham Ritchie
*/
public abstract class SimDisplay extends JPanel
{
	/**
	* Main repaint method. This mathod is called whenever SimUI calls this
	* SimDisplay's repaint methods It must repaint this SimDisplay's JPanel.
	*/
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

}
