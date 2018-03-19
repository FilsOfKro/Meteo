package cursor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import facade.MeteoFacade;

public class DateCursor {

	private JSlider dateSlider;
	
	private Date[] dates;
	
	/**
	 * Initialize with empty structures to prevent null pointers
	 * Attach a listener to the slider which refreshes the windbarbs on change
	 * Even if no data is entered in this constructor, the cursor is fully functional and only needs new data as input by calling setNewDates()
	 */
	public DateCursor() {
		dates = new Date[0];
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
	 * Sorts the array before cloning it and initializes the slider
	 * Attach a ChangeListener to the slider which refreshes the windbarbs on change
	 * @param dates
	 * @param current
	 */
	public DateCursor(Date[] dates, Date current) {
		Arrays.sort(dates);
		
		this.dates = dates.clone();
		dateSlider = new JSlider();
		
		initSlider(this.dates.length, current);

		dateSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				refresh();
			}
		});
	}
	
	/**
	 * Initialize a cursor
	 * Sorts the list before converting it to an array and initializes the slider
	 * Attach a ChangeListener to the slider which refreshes the windbarbs on change
	 * @param dates
	 * @param current
	 */
	public DateCursor(List<Date> dates, Date current) {
		Collections.sort(dates);
		
		this.dates = dates.toArray(new Date[0]);
		
		dateSlider = new JSlider();
		
		initSlider(this.dates.length, current);
		
		dateSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				refresh();
			}
		});
	}
	
	/**
	 * Set a new array of data to the cursor and re-initializes the slider according to the new data
	 * @param dates The new array of data, will be sorted and cloned
	 * @param current The current date of the application, to determine the default value of the slider
	 */
	public void setNewDates(Date[] dates, Date current) {
		Arrays.sort(dates);
		this.dates = dates.clone();

		initSlider(this.dates.length, current);
	}
	
	/**
	 * Set a new array of data to the cursor and re-initializes the slider according to the new data
	 * @param dates The new list of data, will be sorted and converted to an array
	 * @param current The current date of the application, to determine the default value of the slider
	 */
	public void setNewDates(List<Date> dates, Date current) {
		Collections.sort(dates);
		this.dates = dates.toArray(new Date[0]);
		
		initSlider(this.dates.length, current);
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
			dateSlider.setMaximum(this.dates.length);
			int index = getDateIndex(current);
			dateSlider.setValue(index >= 0 ? index+1 : 1);	
		}
	}
	
	/**
	 * Called when the slider's cursor moves. Refreshes the data on screen.
	 */
	private void refresh() {
		Date newDate;
		try {
			newDate = getDateAtIndex(dateSlider.getValue()-1);
			MeteoFacade.getInstance().setCurrentDate(newDate);
			MeteoFacade.getInstance().refreshWindbarbs();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Return the date's index
	 * Return -1 if not found
	 * @param toSearch
	 * @return
	 */
	private int getDateIndex(Date toSearch) {
		for (int j = 0; j < dates.length; j++) {			
			if (dates[j].equals(toSearch)) {
				return j;
			}
		}
		
		return -1;
	}
	
	/**
	 * return the Date at the index passed as parameter
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public Date getDateAtIndex(int index) throws Exception {
		if (index >= 0 && index < dates.length) {
			return dates[index];
		} else {
			throw new Exception("Erreur de curseur : index introuvable");
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
