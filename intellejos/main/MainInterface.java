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

import intellejos.Intellejos;
import util.*;
import interfaces.*;

import java.awt.*;
import java.lang.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/**
* The main GUI.
*
* @original author Graham Ritchie, modified by Tijn Kooijmans
*/
public class MainInterface extends JFrame
{
	private static JDesktopPane mainPane; // the main desktop pane

	/**
	* Sets up the JDesktopPane
	*/
	public MainInterface()
	{
		super("Intellejos - Lejos Simulator");

        // Make the main window indented 50 pixels from each edge of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset*2, screenSize.height-inset*2);

        // Quit the whole program when the main window closes.
        addWindowListener(new WindowAdapter()
		{
            public void windowClosing(WindowEvent e)
			{
                Intellejos.addToLog("MainInterface.init(): System quitting");
				System.exit(0);
            }
        });

		// set up the main pane
		mainPane=new JDesktopPane();

        setContentPane(mainPane);

        setVisible(true);

        createSimulatorFrame();
	}

	/**
	* Creates a new simulator window
	*/
	public static void createSimulatorFrame()
	{
        SimUI frame = new SimUI();
		frame.setVisible(true);
        mainPane.add(frame);
        try
		{
            frame.setSelected(true);
        }
		catch (Exception e)
		{
			Intellejos.addToLog("MainInterface.createFrame(): Failed to create internal simulator frame properly: "+e);
		}
    }

	/**
	* Creates a new window to display messages from external processes
	*
	* @return the ExternalMessager frame
	*/
	public static ExternalMessager createExternalMessagerFrame()
	{
        ExternalMessager frame = new ExternalMessager();
		frame.setVisible(true);
        mainPane.add(frame);
        try
		{
            frame.setSelected(true);
        }
		catch (Exception e)
		{
			Intellejos.addToLog("MainInterface.createFrame(): Failed to create internal frame properly: "+e);
		}
		return frame;
    }

	/**
	* Displays messages to the user in a dialog box
	*
	* @param message the message to be displayed to the user
	*/
	public static void displayMessage(String message)
	{
		// pop up a dialog box with the message
		IntellegoDialog dialog=new IntellegoDialog(message);
	}
}
