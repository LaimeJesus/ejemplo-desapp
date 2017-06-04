package rest.dtos.offers;

import org.joda.time.DateTime;

public class DateDTO {
	public DateDTO(DateTime date) {
		this.year = date.getYear();
		this.month = date.getMonthOfYear();
		this.day = date.getDayOfMonth();
	}
	public int year;
	public int month;
	public int day;
	public DateTime toDateTime() {
		return new DateTime(this.year, this.month, this.day, 0, 0);
	}
}
