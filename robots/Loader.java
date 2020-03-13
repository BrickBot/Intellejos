import lejos.platform.rcx.*;
import lejos.robotics.*;

public class Loader implements SensorConstants
{
	public Loader()
	{
		Sensor.S2.setTypeAndMode(SENSOR_TYPE_TOUCH, SENSOR_MODE_BOOL);

		Motor.A.forward();
		Motor.B.forward();

		Behavior b[] = new Behavior[1];
		b[0] = new WallDetector();
		Arbitrator a = new Arbitrator(b);
		a.start();
	}

	public static void main(String[] args)
	{
		Loader l = new Loader();
	}

}
