package rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import rest.dtos.UserDTO;

@Path("/user")
public class UserController {
	
	
	@POST
	@Path("/signup")
	@Consumes("application/json")
	public Response signUp(UserDTO u){
		return Response.ok().build();
	}

	@POST
	@Path("/login")
	@Consumes("application/json")
	public Response login(UserDTO u){
		return Response.ok().build();
	}
	
	@POST
	@Path("/logout")
	@Consumes("application/json")
	public Response logout(UserDTO u){
		return Response.ok().build();
	}
	
}
