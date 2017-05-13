package rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import exceptions.UserAlreadyExistsException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import rest.dtos.UserDTO;
import services.general.GeneralService;

@Path("/user")
public class UserController {
	
	
	private GeneralService generalService;
	//	@Consumes("application/x-www-form-urlencoded")
//	public Response signUp(@FormParam("username")String username, @FormParam("password") String pass){
	@POST
	@Path("/signup")
	@Consumes("application/json")
	@Produces("application/json")
	public Response signup(UserDTO user){
		try{
			generalService.createUser(user.fullUser());
			return Response.ok().build();			
		}catch(UserAlreadyExistsException uaee){
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
//	@Consumes("application/x-www-form-urlencoded")
//	public Response login(@FormParam("username")String username, @FormParam("password") String pass){
	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response login(UserDTO user){		
		try {
			generalService.loginUser(user.toUser());
			return Response.ok().build();
		} catch (UsernameDoesNotExistException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
//	@Consumes("application/x-www-form-urlencoded")
//	public Response logout(@FormParam("username")String username){
	@POST
	@Path("/logout")
	@Consumes("application/json")
	@Produces("application/json")
	public Response logout(UserDTO user){
		try {
			generalService.logoutUser(user.toUser());
			return Response.ok().build();
		} catch (UsernameDoesNotExistException | UserIsNotLoggedException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
	
}
