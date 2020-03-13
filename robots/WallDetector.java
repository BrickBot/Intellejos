import lejos.platform.rcx.*;
import lejos.robotics.*;

public class WallDetector implements Behavior
{
	public void action()
	{
		Motor.A.backward();
		Motor.B.backward();
	}

	public void suppress()
	{
	}

	public boolean takeControl()
	{
		if (Sensor.S2.readValue() == 1)
			return true;
		else
			return false;
	}
}
