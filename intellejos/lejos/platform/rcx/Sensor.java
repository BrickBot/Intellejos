package lejos.platform.rcx;

/**
 * Abstraction for a sensor (<i>considerably changed since alpha5</i>).
 * There are three Sensor instances available: Sensor.S1, Sensor.S2 and
 * Sensor.S3. They correspond to sensor inputs labeled 1, 2 and 3 in the
 * RCX, respectively. Before using a sensor, you should set its mode
 * and type with <code>setTypeAndMode</code> using constants defined in <code>SensorConstants</code>.
 * You should also activate the sensor.
 * <p>
 * You can poll for sensor values in a loop using the readValue method
 * or one of the other read methods. There is also a low level method which
 * can be used when maximum performance is required. Another way to
 * monitor sensor values is to add a <code>SensorListener</code>. All sensor events
 * are dispatched to listeners by a single thread created by this class. The
 * thread is a daemon thread and so will not prevent termination of an
 * application if all other threads have exited.
 * <p>
 * Example:<p>
 * <code><pre>
 *   Sensor.S1.setTypeAndMode (3, 0x80);
 *   Sensor.S1.activate();
 *   Sensor.S1.addSensorListener (new SensorListener() {
 *     public void stateChanged (Sensor src, int oldValue, int newValue) {
 *       // Will be called whenever sensor value changes
 *       LCD.showNumber (newValue);
 *       try {
 *         Thread.sleep (100);
 *       } catch (InterruptedException e) {
 *         // ignore
 *       }
 *     }
 *   });
 *
 * </pre></code>
 *
 * @see josx.platform.rcx.SensorConstants
 * @see josx.platform.rcx.SensorListener
 */

import main.*;

public class Sensor implements ListenerCaller
{
  private int iSensorId;
  private int iSensorType;
  private int iSensorMode;
  private short iNumListeners = 0;
  private SensorListener[] iListeners;
  private int iPreviousValue;
  private boolean active = false;

  /**
   * Sensor labeled 1 on RCX.
   */
  public static final Sensor S1 = new Sensor (1);

  /**
   * Sensor labeled 2 on RCX.
   */
  public static final Sensor S2 = new Sensor (2);

  /**
   * Sensor labeled 3 on RCX.
   */
  public static final Sensor S3 = new Sensor (3);

  /**
   * Array containing all three sensors [0..2].
   */
  public static final Sensor[] SENSORS = { Sensor.S1, Sensor.S2, Sensor.S3 };

  /**
   * Reads the canonical value of the sensor.
   */
  public final int readValue()
  {
    return readSensorValue (iSensorMode);
  }

  /**
   * Reads the raw value of the sensor.
   */
  public final int readRawValue()
  {
    return readSensorValue (0);
  }

  /**
   * Reads the boolean value of the sensor.
   */
  public final boolean readBooleanValue()
  {
    return readSensorValue (2) != 0;
  }

  private Sensor (int aId)
  {
    iSensorId = aId;
    setTypeAndMode (3, 0x80);
  }

  /**
   * Return the ID of the sensor. One of 0, 1 or 2.
   */
  public final int getId()
  {
    return iSensorId;
  }

  /**
   * Adds a sensor listener.
   * <p>
   * <b>
   * NOTE 1: You can add at most 8 listeners.<br>
   * NOTE 2: Synchronizing inside listener methods could result
   * in a deadlock.
   * </b>
   * @see josx.platform.rcx.SensorListener
   */
  public synchronized void addSensorListener (SensorListener aListener)
  {
    if (iListeners == null)
    {
        iListeners = new SensorListener[8];
    }
    iListeners[iNumListeners++] = aListener;
    ListenerThread.get().addSensorToMask(iSensorId, this);
  }

  /**
   * Activates the sensor. This method should be called
   * if you want to get accurate values from the
   * sensor. In the case of light sensors, you should see
   * the led go on when you call this method.
   */
  public final void activate()
  {
    active = true;
  }

  /**
   * Passivates the sensor.
   */
  public final void passivate()
  {
    active = false;
  }

  /**
   * Sets the sensor's mode and type. If this method isn't called,
   * the default type is 3 (LIGHT) and the default mode is 0x80 (PERCENT).
   * @param aType 1 = TOUCH, 3 = LIGHT
   * @param aMode 0x00 = RAW, 0x20 = BOOL, 0x80 = PERCENT,
   * @see josx.platform.rcx.SensorConstants
   */
  public final void setTypeAndMode (int aType, int aMode)
  {
        if (aMode == 0x00)
		iSensorMode = 0;
		else if (aMode == 0x20)
		 	iSensorMode = 2;
		else
		 	iSensorMode = 1;

		iSensorType = aType;
		SimUI.getController().robot.setType(iSensorId, aType);
  }

  /**
   * Method for reading sensor values.
   * @param aSensorId Sensor ID (0..2).
   * @param aRequestType 0 = raw value, 1 = percentual value, 2 = boolean value.
   */
  public int readSensorValue (int aRequestType)
  {
  	int aReturnValue = 0;

	if (active || iSensorType == SensorConstants.SENSOR_TYPE_TOUCH)
	{
	 	if (aRequestType == 0) // request raw value
		{
			if (iSensorType == SensorConstants.SENSOR_TYPE_TOUCH)
			{
				if (SimUI.getController().readSensor(iSensorId) == 1)
					aReturnValue = 1024;
				else
					aReturnValue = 0;
			}
			else if (iSensorType == SensorConstants.SENSOR_TYPE_LIGHT)
			{
				int value = SimUI.getController().readSensor(iSensorId);
				aReturnValue = value * 1024 / 100;
			}
		}
		else if (aRequestType == 1) // request percentual value
		{
			if (iSensorType == SensorConstants.SENSOR_TYPE_TOUCH)
			{
				if (SimUI.getController().readSensor(iSensorId) == 1)
					aReturnValue = 100;
				else
					aReturnValue = 0;
			}
			else if (iSensorType == SensorConstants.SENSOR_TYPE_LIGHT)
			{
				aReturnValue = SimUI.getController().readSensor(iSensorId);

			}
		}
		else if (aRequestType == 2) // request boolean value
		{
			if (iSensorType == SensorConstants.SENSOR_TYPE_TOUCH)
			{
				aReturnValue = SimUI.getController().readSensor(iSensorId);
			}
			else if (iSensorType == SensorConstants.SENSOR_TYPE_LIGHT)
			{
				int value = SimUI.getController().readSensor(iSensorId);
				if (value > 50)
					aReturnValue = 1;
				else
					aReturnValue = 0;

			}
		}
	}

	return aReturnValue;

  }

  public synchronized void callListeners() {
    int newValue = readSensorValue(iSensorMode);
    for (int i = 0; i < iNumListeners; i++) {
      iListeners[i].stateChanged(this, iPreviousValue, newValue);
    }
    iPreviousValue = newValue;
  }
}
