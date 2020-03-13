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

import simworldobjects.*;
import intellejos.Intellejos;
import util.*;
import interfaces.*;

import java.awt.*;
import java.lang.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

/**
* Provides a user interface for a simulation
*
* @orignal author Graham Ritchie, modified by Tijn Kooijmans
*/
public class SimUI extends JInternalFrame implements Runnable
{
    private Container mainContainer;
    private SimDisplay display=null;
    private JLayeredPane mainPane;
    private SimWorld world=null;
    private boolean running=false;
    private boolean paused=false;
    private int UPDATE_TIME=30;
    private static int screenWidth=1000;
    private static int screenHeight=800;
    private static Controller c;

    private static final String NO_CLASS="__NOCLASS__";

    /**
    * Sets up the main window for an empty simulation
    */
    public SimUI()
    {

        // window setup
		super("Simulator",true,true,true,true);
		setSize(screenWidth,screenHeight);

		 // stop the simulation when the window closes.
		addInternalFrameListener(new InternalFrameAdapter()
		{
  	        public void internalFrameClosing(InternalFrameEvent e)
			{
  	            Intellejos.addToLog("SimUI.init(): simulator quitting");

				// stop running (if we are)
				running=false;

		    	// stop the controller
				c.halt();

				System.exit(0);
		    }
        });

        setupWindow();

	}

    /**
    * Sets up the main window
    */
    public void setupWindow()
    {
        // set up main pane
        mainContainer=getContentPane();
        mainContainer.setLayout(new BoxLayout(mainContainer,BoxLayout.Y_AXIS));

		// create and set the menu bar
		JMenuBar mb=createMenuBar();
		setJMenuBar(mb);

    }

	/**
	* Creates the menu bar for the main window, and adds action listeners
	* to the menus.
	*
	* @return the menu bar
	*/
	private JMenuBar createMenuBar()
	{
		// menu bar
		JMenuBar mb=new JMenuBar();

		// menus
		JMenu controlMenu=new JMenu("Controls");

		// control menu items
		JMenuItem pickWorld=new JMenuItem("Pick SimWorld");
		JMenuItem addNewRobot=new JMenuItem("Add Robot");
		JMenuItem startSim=new JMenuItem("Start Simulation");
		JMenuItem pauseSim=new JMenuItem("Pause Simulation"); //gbb
		JMenuItem unpauseSim=new JMenuItem("Unpause Simulation"); //gbb
		JMenuItem stopSim=new JMenuItem("Stop Simulation");

		// add action listeners
		pickWorld.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
			{
				if (world == null)
					selectWorld();
				else
					MainInterface.displayMessage("Please restart to load a new world");
            }
        });

		addNewRobot.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
			{
				if (c == null)
				{
					addRobot();
					startSimulation();
				}
				else if (c.getRobot() == null)
				{
					addRobot();
					startSimulation();
				}
				else
					MainInterface.displayMessage("Please restart to load a new robot");
	        }
        });

		startSim.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
			{
                startSimulation();
            }
        });

		stopSim.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
			{
                stopSimulation();
            }
        });

		// add items to control menu
		controlMenu.add(pickWorld);
		controlMenu.add(addNewRobot);
		//controlMenu.add(startSim);

	    // add menus to menu bar
	    mb.add(controlMenu);

		return mb;
	}

    /**
    * Main loop: updates the world and repaints the screen
    */
    public void run()
    {

		if(world==null || display==null)
        {
            MainInterface.displayMessage("You must select a SimWorld and a SimDisplay first");
        }
        else
        {
            while (running)
            {
                // update SimWorld
                world.tick();

                // repaint the display screen
                display.repaint();

                // sleep for a bit
                try{Thread.sleep(UPDATE_TIME);}catch(Exception e){}
            }
        }
    }

    /**
    * Allows the user to pick a simworld for their simulation, and loads it
    */
    public void selectWorld()
    {
        // create simworld by getting the class name from the user
        String className=getClassName("Pick a SimWorld class file:","simworlds");

        // check user actually selected a file
        if (className.equals(NO_CLASS))
        {
            // do nothing
        }
        else
        {
            // if there is already a world open, stop the current sim and get rid of it.
            if(world!=null)
            {
                stopSimulation();
                world=null;
                mainContainer.remove(display);
                display=null;
            }

            // try to create a simworld from this class
            SimWorld s=createSimWorld(className);

            // check simworld creation was successful
            if(s!=null)
            {
                // set s as the world for this simulation
                world=s;

                // and create a new display with this simworld
                display=new SimpleDisplay(world);

                display.setVisible(true);

                // add the simdisplay to the main pane
                mainContainer.add(display, BorderLayout.CENTER);

                // set size to force display to repaint
                setSize(screenWidth,screenHeight);
            }
            else // simworld creation failed, so just bail out
            {
                MainInterface.displayMessage("Failed to create SimWorld");
                Intellejos.addToLog("SimUI.selectWorld(): Failed to create SimWorld with name "+className);
            }
        }
    }

    /**
    * Dynamically loads a simworld class
    *
    * @param name the name of the simworld as a string
    *
    * @return the simworld, or null if unsuccessful
    */
    public SimWorld createSimWorld(String name)
    {
        SimWorld s=null;

        try
        {
            // try to create the class
            Class simworldClass=Class.forName(name);

            try
            {
                // try to cast the class to a SimWorld, this will fail if the class
                // is not a valid Intellego SimWorld
                s=(SimWorld)simworldClass.newInstance();
            }
            catch (Exception e)
            {
                MainInterface.displayMessage("Error: this class is not a valid Intellego SimWorld");
            }
        }
        catch (Exception e)
        {
            // failed to create the class, so return null
            MainInterface.displayMessage("Error creating class");
            Intellejos.addToLog("SimUI.createSimWorld(): Error creating class: "+name+": "+e);
            return null;
        }

        // return the simworld
        return s;
    }

    /**
    * Allows the user to pick a robot class, and loads it into this simulation
    */
    public void addRobot()
    {
        if(world==null || display==null)
        {
            MainInterface.displayMessage("You must select a SimWorld and a SimDisplay first");
        }
        else
        {
            // get the name of the class from the user
            String className=getClassName("Pick a Robot class file:","robots");

            // check user actually selected a class name
            if (className.equals(NO_CLASS))
            {
                 // do nothing
            }
            else
            {
                // create an instance of this class
                c = new Controller(className);

                if(c!=null)
                {
                    // and add it to the system if it is not null
                    addController(c);
                }
                else
                {
                    MainInterface.displayMessage("Failed to create Controller");
                    Intellejos.addToLog("SimUI.addRobot(): Failed to create Controller with name "+className);
                }
            }
        }
    }

    /**
    * Gets the class name of the required class from the user
    * using a JFileChooser
    *
    * @param title the title to display in the FileChooser window
    * @param base_dir the directory to start looking in
    *
    * @return the classname as string
    */
    public String getClassName(String title,String base_dir)
    {
        String className=" ";

        // pop up a file chooser dialog
        JFileChooser chooser=new JFileChooser(new File(System.getProperties().getProperty("user.dir"),base_dir));

        // add a filename filter for class files
        String[] extensions={".class"};
        chooser.addChoosableFileFilter(new FileChooserFilter(extensions,"Compiled Class File"));

        chooser.setDialogTitle(title);
        int returnVal=chooser.showOpenDialog(this);

        try
        {
            if (returnVal==JFileChooser.APPROVE_OPTION) // user has selected a file
            {
                // get the user's selected file
                File file=chooser.getSelectedFile();

                // get the classname
                className=file.getName();

                Intellejos.addToLog("SimUI.getClassName(): opening file: "+className);

                // chop .class extension off
                className=className.substring(0,file.getName().length() - 6);

                Intellejos.addToLog("SimUI.getClassName(): attempting to open class: "+className);

                // return the classname
                return (className);
            }
            else // user selected cancel or some other button we are going to ignore
            {
                return NO_CLASS;
            }
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
    * Dynamically loads a controller class
    *Test.class
    * @param name the name of the controller as a string
    *
    * @return the controller, or null if unsuccessful
    */
    public Controller createController(String name)
    {
        Controller con=null;

        try
        {
            // try to create the class
            Class controllerClass=Class.forName(name);

            try
            {
                // try to cast the class to a Controller, this will fail if the class
                // is not a valid Intellego Controller
                con=(Controller)controllerClass.newInstance();
            }
            catch (Exception e)
            {
                MainInterface.displayMessage("Error: class is not a valid Intellego Controller class");
                Intellejos.addToLog("SimUI.createController(): Attempt to create Intellego Controller "+name+" failed. Not a valid Controller.");
            }
        }
        catch (Exception e)
        {
            // failed to create the class, so return null
            MainInterface.displayMessage("Error creating class");
            Intellejos.addToLog("SimUI.createController(): Error creating class "+name+": "+e);
            return null;
        }

        // return the controller
        return con;
    }

    /**
    * Adds a controller to this simulation
    *
    * @param c the controller to be added
    */
    public void addController(Controller c)
    {
        // check if controller creation was successsful
        if(c!=null)
        {
            // and get robot details
            InitRobotDialog d=new InitRobotDialog();

            // create the robot
            d.createRobot(world,c,display);

            // and repaint the display
            repaint();
        }
        else // failed to create controller
        {
            MainInterface.displayMessage("Failed to add controller");
            Intellejos.addToLog("SimUI.addController: Failed to add controller");
        }
    }

    /**
    * Starts this simulation (by starting a new thread for this instance)
    */
    public void startSimulation()
    {
        // check there is a simworld and a simdisplay open
        if (world==null || display==null)
        {
            // if not display an error message
            MainInterface.displayMessage("You must pick a SimWorld and a SimDisplay first");
        }
        else
        {
            // check we're not already running
            if(!running)
            {
                running=true;

                Thread t=new Thread(this);
                t.start();
            }

            // start up the controller
            Thread u=new Thread(c);
            u.setPriority(Thread.MIN_PRIORITY);
            u.start();
            Intellejos.addToLog("SimUI.run(): Started controller");
        }
    }

    /**
    * Stops this simulation
    */
    public void stopSimulation()
    {
        // check there is a simworld and a simdisplay open
        if (world==null || display==null)
        {
            // if not display an error message
            MainInterface.displayMessage("No simulation to stop");
        }
        else
        {
            running=false;

            // stop the controller
            c.halt();

        }
    }

    /**
    * Returns object of robot controller
    */
    public static Controller getController()
    {
    	return c;
    }



}


