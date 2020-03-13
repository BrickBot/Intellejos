import lejos.platform.rcx.*;
import lejos.util.*;

/**
* Example of a Intellejos robot that uses Lejos classes
*
* @author Tijn Kooijmans
*/

public class TimedJojo implements SensorListener, TimerListener, SensorConstants
{
	Timer t;

	public TimedJojo()
	{
		Motor.A.forward();
		Motor.B.forward();

		Sensor.S2.addSensorListener(this);
		Sensor.S2.setTypeAndMode(SENSOR_TYPE_TOUCH, SENSOR_MODE_BOOL);
		Sensor.S2.activate();
		System.out.println("Initial sensor value = " + Sensor.S2.readValue());
		t = new Timer(6000, this);
	}

	public void stateChanged(Sensor sensor, int oldValue, int newValue)
	{
		System.out.println(sensor.getId() + " - " + newValue);
		if (newValue == 1)
		{
			Motor.A.backward();
			t.start();
		}
	}

	public void timedOut()
	{
		Motor.A.forward();
		t.stop();
	}

	public static void main(String[] args)
	{
		TimedJojo jj = new TimedJojo();
	}
}
