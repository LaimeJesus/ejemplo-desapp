package rest.dtos.generics;

import org.joda.time.Duration;

public class DurationDTO {
	int milliseconds;
	
	public DurationDTO(Duration d){
		milliseconds = Math.toIntExact(d.getMillis());
	}

	public Duration toDuration() {
		return new Duration(milliseconds);
	}

}
