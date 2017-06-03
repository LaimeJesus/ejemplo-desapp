package rest.dtos.offers;

import org.joda.time.DateTime;

public class DateDTO {
	public DateDTO(DateTime date) {
		year = date.getYear();
		month = date.getMonthOfYear();
		day = date.getDayOfMonth();
	}
	public int year;
	public int month;
	public int day;
	public DateTime toDateTime() {
		return new DateTime(year, month, day, 0, 0);
	}
}
