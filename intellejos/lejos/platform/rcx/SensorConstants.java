package lejos.platform.rcx;

/**
 * Constants for Sensor methods.
 * @see josx.platform.rcx.Sensor#setTypeAndMode
 */
public interface SensorConstants
{
	public static final int SENSOR_TYPE_TOUCH  = 1;
	public static final int SENSOR_TYPE_LIGHT  = 3;

	public static final int SENSOR_MODE_RAW    = 0x00;
	public static final int SENSOR_MODE_BOOL   = 0x20;
	public static final int SENSOR_MODE_PCT    = 0x80;

	public static final int RAW_VALUE          = 0;
	public static final int CANONICAL_VALUE    = 1;
	public static final int BOOLEAN_VALUE      = 2;
}