package cursor;

import java.util.Date;

import facade.MeteoFacade;

/**
 * This class is used to delay a call to refresh the windbarbs when the slider has changed
 * Use this to prevent loading too much windbarbs at once into the RAM
 * The display will only be refreshed when the slider hasn't change value for a determined period of time
 * @author sebastien
 *
 */
public class DelayedRefresh {

	private static DelayedRefresh instance;
	
	private long previousDelay;
	
	private DateCursor theCursor;
	
	private Thread thread;
	
	private boolean hasRefreshed;
	
	public static final long MS_BEFORE_REFRESH = 500; 
	
	private DelayedRefresh(DateCursor cursor) {
		hasRefreshed = false;
		previousDelay = System.currentTimeMillis();
		theCursor = cursor;
		
		thread = new Thread() {
			@Override
			public void run() {
				while (!hasRefreshed) {
					long currentTime = System.currentTimeMillis();
					
					if (hasWaitedEnough(currentTime, previousDelay, MS_BEFORE_REFRESH)) {
						refresh();
						hasRefreshed = true;
					}
				}
			}
		};
		
		thread.start();
	}

	/**
	 * Use this method to delay a call to refresh the screen with the date selected by the DateCursor object
	 * The method handles the instantiation of the class.
	 * Each time you call this method, the timer is reseted and thus further delays the call to refresh methods.
	 * @param cursor
	 */
	public static void delayRefresh(DateCursor cursor) {
		if (instance == null) {
			instance = new DelayedRefresh(cursor);
		} else {
			if (instance.hasRefreshed) {
				instance = new DelayedRefresh(cursor);
			}
		}
		
		instance.previousDelay = System.currentTimeMillis();
	}
	
	/**
	 * Called when the delayRefresh method hasn't been called for a determined period of time
	 * Set the new date and refreshes the windbarbs
	 */
	private void refresh() {
		Date newDate;
		try {
			newDate = theCursor.getDateAtIndex(theCursor.getSlider().getValue()-1);
			MeteoFacade.getInstance().setCurrentDate(newDate);
			MeteoFacade.getInstance().refreshWindbarbs();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Returns whether the time "msToWait" has passed or not, according to the 2 other parameters
	 * @param currentTime The current time (should be > previousTime)
	 * @param previousTime The time to compare to (should be < currentTime)
	 * @param msToWait The time to have passed between the two times
	 * @return
	 */
	private boolean hasWaitedEnough(long currentTime, long previousTime, long msToWait) {
		return currentTime - previousTime > msToWait;
	}
}
