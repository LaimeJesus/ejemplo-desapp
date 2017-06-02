package rest.dtos;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

public class ResponseDTO {

	
	public Gson gson;

	public ResponseDTO(){
		gson = new Gson();
	}
	
	public Response ok(Object entity) {
		return Response.ok().entity(gson.toJson(entity)).build();
	}

	public Response error(Status status, String message) {
		return Response.status(status).entity(new ErrorDTO(status, message)).build();	
	}

}
