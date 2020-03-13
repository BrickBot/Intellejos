import lejos.platform.rcx.*;
import lejos.util.*;

public class Wallfinder implements SensorListener, SensorConstants
{

	public Wallfinder ()
	{
		Sensor.S2.setTypeAndMode(SENSOR_TYPE_TOUCH,SENSOR_MODE_BOOL);
		Sensor.S2.addSensorListener(this);

		Motor.A.forward();
		Motor.B.forward();
	}

	public void stateChanged(Sensor aSensor, int newValue, int oldValue)
	{
		if (newValue == 1)
		{
			Motor.A.backward();
			Motor.B.backward();
		}
	}

	public static void main(String[] args)
	{
		 Wallfinder jj = new Wallfinder ();
	}
}
