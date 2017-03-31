package model;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

public class LocationManagerService {

	
	
	
	// Replace the API key below with a valid API key.
	GeoApiContext context = new GeoApiContext().setApiKey("YOUR_API_KEY");
	GeocodingResult[] results =  GeocodingApi.geocode(context,"1600 Amphitheatre Parkway Mountain View, CA 94043").await();
	
	System.out.println(results[0].formattedAddress);
	
}
