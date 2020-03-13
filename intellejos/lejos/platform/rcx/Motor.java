package lejos.platform.rcx;

/**
 * Abstraction for a motor. Three instances of <code>Motor</code>
 * are available: <code>Motor.A</code>, <code>Motor.B</code>
 * and <code>Motor.C</code>. To control each motor use
 * methods <code>forward, backward, reverseDirection, stop</code>
 * and <code>flt</code>. To set each motor's power, use
 * <code>setPower</code>.
 * <p>
 * Example:<p>
 * <code><pre>
 *   Motor.A.forward();
 *   Motor.B.forward();
 *   Thread.sleep (1000);
 *   Motor.A.stop();
 *   Motor.B.stop();
 * </pre></code>
 */

import main.*;

public class Motor
{
  private char  iId;
  private short iMode = 3;

  /**
   * Motor A.
   */
  public static final Motor A = new Motor ('A');
  /**
   * Motor B.
   */
  public static final Motor B = new Motor ('B');

  private Motor (char aId)
  {
    iId = aId;
  }

  /**
   * Get the ID of the motor. One of 'A' or 'B'
   */
  public final char getId()
  {
    return iId;
  }

  /**
   * Causes motor to rotate forward.
   */
  public final void forward()
  {
    iMode = 1;
    SimUI.getController().forward(iId);
  }

  /**
   * Return true if motor is forward.
   */
  public final boolean isForward()
  {
    return (iMode == 1);
  }

  /**
   * Causes motor to rotate backwards.
   */
  public final void backward()
  {
    iMode = 2;
    SimUI.getController().backward(iId);
  }

  /**
   * Return true if motor is backward.
   */
  public final boolean isBackward()
  {
    return (iMode == 2);
  }

  /**
   * Reverses direction of the motor. It only has
   * effect if the motor is moving.
   */
  public final void reverseDirection()
  {
    if (iMode == 1 || iMode == 2)
    {
      iMode = (short) (3 - iMode);
      if (iMode == 1)
      {
      	SimUI.getController().forward(iId);
      }
      else if (iMode == 2)
      {
      	SimUI.getController().backward(iId);
      }
    }
  }

  /**
   * @return true iff the motor is currently in motion.
   */
  public final boolean isMoving()
  {
    return (iMode == 1 || iMode == 2);
  }

  /**
   * Causes motor to stop, pretty much
   * instantaneously. In other words, the
   * motor doesn't just stop; it will resist
   * any further motion.
   */
  public final void stop()
  {
    iMode = 3;
	SimUI.getController().stop(iId);
  }

  /**
   * Return true if motor is stopped.
   */
  public final boolean isStopped()
  {
    return (iMode == 3);
  }

}







