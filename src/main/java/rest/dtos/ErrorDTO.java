package rest.dtos;

import javax.ws.rs.core.Response.Status;

public class ErrorDTO {

	public ErrorDTO(Status status, String message) {
		errorCode = status.getStatusCode();
		errorMessage = message;
	}
	public ErrorDTO() {
	}
	public int errorCode;
	public String errorMessage;
	
}
