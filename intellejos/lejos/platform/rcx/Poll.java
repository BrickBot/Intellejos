/**
 * RCX access classes.
 */
package lejos.platform.rcx;

/**
 * Provides blocking access to events from the RCX. Poll is a bit
 * of a misnomer (since you don't 'poll' at all) but it takes its
 * name from the Unix call of the same name.
 */

 import main.*;

public class Poll
{
    private int newValueS1 = -1;
    private int newValueS2 = -1;
    private int newValueS3 = -1;
    private int previousValueS1 = -1;
    private int previousValueS2 = -1;
    private int previousValueS3 = -1;

    private final int UPDATE_TIME = 10;

    public static final short SENSOR1_MASK = 1;
    public static final short SENSOR2_MASK = 2;
    public static final short SENSOR3_MASK = 3;
    public static final short ALL_SENSORS  = 4;

    public static final short RUN_MASK     = 5;
    public static final short VIEW_MASK    = 6;
    public static final short PRGM_MASK    = 7;
    public static final short ALL_BUTTONS  = 8;

    public static final short SERIAL_MASK = 9;

    private static Poll monitor = new Poll(true);

    // This is reflected in the kernel structure
    private short changed = 0;  // The 'changed' mask.

    /**
     * Private constructor. Sets up the poller in the kernel.
     */
    private Poll(boolean dummy)
    {
        setPoller();
    }

    /**
     * Constructor.
     */
    public Poll() {
    }

    /**
     * Wait for the sensor/button values to change then return.
     *
     * @param mask bit mask of values to monitor.
     * @param millis wait for at most millis milliseconds. 0 = forever.
     * @return a bit mask of the values that have changed
     * @throws InterruptedException
     */
    public final int poll(int millis) throws InterruptedException
    {
        synchronized (monitor)
        {

            int ret = 0;

            // The inputs we're interested in may have already changed
            // since we last looked so check before we wait.
            while (ret == 0)
            {
                monitor.wait(millis);

                // Work out what's changed that we're interested in.
                ret = monitor.changed;
            }
            return ret;
        }
    }


    /**
     * Sets up and starts the the poller
     */
    private final void setPoller()
    {
        new Thread()
		{
 	        public void run()
		    {
                this.setPriority(Thread.MIN_PRIORITY);
                do
				{

     	            synchronized (monitor)
	 			    {
			    		newValueS1 = SimUI.getController().readSensor(1);
						newValueS2 = SimUI.getController().readSensor(2);
						newValueS3 = SimUI.getController().readSensor(3);
        	            if (newValueS1 != previousValueS1)
						{
                            monitor.changed = SENSOR1_MASK;
                            monitor.notifyAll();
						    previousValueS1 = newValueS1;

                        }
						else if (newValueS2 != previousValueS2)
						{
                            monitor.changed = SENSOR2_MASK;
                            monitor.notifyAll();
						    previousValueS2 = newValueS2;

                        }
						else if (newValueS3 != previousValueS3)
						{
                            monitor.changed = SENSOR3_MASK;
                            monitor.notifyAll();
						    previousValueS3 = newValueS3;

                        }
						else
						    monitor.changed = 0;
                    }

                    // sleep for a bit
                	try{Thread.sleep(UPDATE_TIME);}catch(Exception e){}
                } while (true);
            }
        }.start();
    }
}
