import lejos.platform.rcx.*;
import lejos.util.*;

public class Square implements TimerListener
{

	public Square()
	{
		Motor.A.forward();
		Motor.B.forward();

		Timer t = new Timer(2700, this);
		t.start();
	}

	public void timedOut()
	{
		Motor.B.reverseDirection();
	}

	public static void main(String[] args)
	{
		Square jj = new Square();
	}
}
