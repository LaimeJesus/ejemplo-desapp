package rest.dtos.generics;

import org.joda.time.DateTime;

public class DateDTO {
	public DateDTO(DateTime date) {
		this.year = date.getYear();
		this.month = date.getMonthOfYear();
		this.day = date.getDayOfMonth();
		this.hours = date.getHourOfDay();
		this.minutes = date.getMinuteOfHour();
//		this.seconds = date.getSecondOfMinute();
	}
	
	public int year;
	public int month;
	public int day;
	public int hours;
	public int minutes;
//	private int seconds;
	public DateTime toDateTime() {
		return new DateTime(this.year, this.month, this.day, 0, 0);
	}
}
