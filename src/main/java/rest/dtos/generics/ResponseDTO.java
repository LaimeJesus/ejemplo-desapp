package rest.dtos.generics;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

public class ResponseDTO {

	
	public Gson gson;

	public ResponseDTO(){
		gson = new Gson();
	}
	
	public Response ok(Object entity) {
		return Response.ok().entity(gson.toJson(entity))
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600")
				.build();
	}

	public Response error(Status status, String message) {
		return Response.status(status).entity(new ErrorDTO(status, message)).build();	
	}

}
