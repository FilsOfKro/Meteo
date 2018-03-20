package cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DateCursor {

	private JSlider dateSlider;
	
	private List<Date> dates;
	
	
	
	/**
	 * Initialize with empty structures to prevent null pointers
	 * Attach a listener to the slider which refreshes the windbarbs on change
	 * Even if no data is entered in this constructor, the cursor is fully functional and only needs new data as input by calling setNewDates()
	 */
	public DateCursor() {
		dates = new ArrayList<Date>();
		dateSlider = new JSlider();
		initSlider(0, null);
		
		dateSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				refresh();
			}
		});
	}
	
	/**
	 * Initialize a cursor
	 * Sorts the list and initializes the slider
	 * Attach a ChangeListener to the slider which refreshes the windbarbs on change
	 * @param dates
	 * @param current
	 */
	public DateCursor(List<Date> dates, Date current) {
		Collections.sort(dates);
		
		this.dates = dates;
		
		dateSlider = new JSlider();
		
		initSlider(this.dates.size(), current);
		
		dateSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				refresh();
			}
		});
	}
	
	/**
	 * Set a new array of data to the cursor and re-initializes the slider according to the new data
	 * @param dates The new list of data, will be sorted
	 * @param current The current date of the application, to determine the default value of the slider
	 */
	public void setNewDates(List<Date> dates, Date current) {
		Collections.sort(dates);
		this.dates = dates;
		
		initSlider(this.dates.size(), current);
	}
	
	/**
	 * Initialize the slider with the correct parameters
	 * @param size The maximum value of the slide (usually the number of dates)
	 * @param current The current Date used by the application. If present in the Date array, its index in the array will be used. Otherwise the slider's value will default to 1.
	 */
	private void initSlider(int size, Date current) {
		if (size == 0) {
			dateSlider.setMinimum(0);
			dateSlider.setMaximum(0);
			dateSlider.setValue(0);
		} else {
			dateSlider.setMinimum(1);
			dateSlider.setMaximum(this.dates.size());
			int index = getDateIndex(current);
			dateSlider.setValue(index >= 0 ? index+1 : 1);	
		}
	}
	
	/**
	 * Called when the slider's cursor moves.
	 * Call a delayer method which will refresh the data on screen when a determined amount of time has passed since the last change
	 * The delayed call is used for optimization purpose, to prevent useless refreshing
	 */
	private void refresh() {
		DelayedRefresh.delayRefresh(this);
	}
	
	/**
	 * Return the date's index
	 * Return -1 if not found
	 * @param toSearch
	 * @return
	 */
	private int getDateIndex(Date toSearch) {
		return dates.indexOf(toSearch);
	}
	
	/**
	 * return the Date at the index passed as parameter
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public Date getDateAtIndex(int index) throws Exception {
		if (index >= 0 && index < dates.size()) {
			return dates.get(index);
		} else {
			throw new IndexOutOfBoundsException("Erreur de curseur : index introuvable");
		}
	}
	
	/**
	 * Returns the JSlider associated to this DateCursor object
	 * The JSlider already has a ChangeListener attached which refreshes the windbarbs when a change happens
	 * @return
	 */
	public JSlider getSlider() {
		return dateSlider;
	}
	
}
