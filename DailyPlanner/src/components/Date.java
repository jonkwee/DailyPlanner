package components;

import org.joda.time.LocalDate;

public class Date {
	
	private int year;
	private int month;
	private int day;
	private LocalDate date;
	
	// constructor for current date
	public Date() {
		date = LocalDate.now();
		this.year = date.getYear();
		this.month = date.getMonthOfYear();
		this.day = date.getDayOfMonth();
	}
	
	// constructor for custom date
	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
		date = new LocalDate(year,month,day);
	}
	/**
	 * Returns int representation of year from Date object
	 * @param  		None
	 * @return      String
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * Returns int representation of month from Date object
	 * @param  		None
	 * @return      String
	 */
	public int getMonth() {
		return month;
	}
	
	/**
	 * Returns int representation of day from Date object
	 * @param  		None
	 * @return      String
	 */
	public int getDay() {
		return day;
	}
	
	/**
	 * Returns string representation of year from Date object
	 * @param  		None
	 * @return      String
	 */
	public String yearToString() {
		return "" + year;
	}
	
	/**
	 * Returns string representation of month from Date object
	 * @param  		None
	 * @return      String
	 */
	public String monthToString() {
		String[] monthArray = {"January", "February", "March", "April", "May", "June",
				"July","August","September","October","November","December"};
		return monthArray[month - 1];
	}
	
	/**
	 * Returns string representation of day from Date object
	 * @param  		None
	 * @return      String
	 */
	public String dayToString() {
		return "" + day;
	}
	
	public void setDay(int newDay) {
		day = newDay;
	}
	
	/**
	 * Set the current year to the next year on the calendar.
	 * @param  		None
	 * @return      None
	 */
	private void setNextYear() {
		year++;
	}
	
	/**
	 * Set the current yea to the previous year on the calendar.
	 * @param  		None
	 * @return      None
	 */
	private void setPreviousYear() {
		year--;
	}
	
	public void setNextDay() {
		day++;
		if (day > getLastDayOfMonth()) {
			setNextMonth();
			day = 1;
		}
	}
	
	public void setPreviousDay() {
		day--;
		if (day < 1) {
			setPreviousMonth();
			day = getLastDayOfMonth();
		}
	}
	
	/**
	 * Set the current month to the next month on the calendar.
	 * @param  		None
	 * @return      None
	 */
	public void setNextMonth() {
		int newMonth = (month + 1 == 13)? 1 : month + 1;
		month = newMonth;
		if (month == 1) {
			setNextYear();
		}
	}
	
	/**
	 * Set the current month to the previous month on the calendar.
	 * @param  		None
	 * @return      None
	 */
	public void setPreviousMonth() {
		int newMonth = (month - 1 == 0)? 12 : month - 1;
		month = newMonth;
		if (month == 12) {
			setPreviousYear();
		}
	}
	
	/**
	 * Returns the first day of the current month
	 * @param  		None
	 * @return      1 = Monday, 2 = Tuesday, 3 = Wednesday,
	 *              4 = Thursday, 5 = Friday, 6 = Saturday,
	 *              7 = Sunday
	 */
	public int getFirstDayOfMonth() {
		LocalDate temp = new LocalDate(year,month,1);
		int firstDay = temp.getDayOfWeek();
		return firstDay;
	}
	
	/**
	 * Returns the last day of the current month
	 * @param  		None
	 * @return      int (31,30 or 28/29)
	 */
	public int getLastDayOfMonth() {
		LocalDate temp = new LocalDate(year, month, 1);
		return temp.dayOfMonth().getMaximumValue();
	}
	
	
}
