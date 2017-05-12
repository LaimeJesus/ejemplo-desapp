package rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import exceptions.UserAlreadyExistsException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import model.users.User;
import services.microservices.UserService;
import util.Password;

@Path("/user")
public class UserController {
	
	private UserService userService;
	
	@POST
	@Path("/signup")
	@Consumes("application/x-www-form-urlencoded")
	public Response signUp(@FormParam("username")String username, @FormParam("password") String pass){
		User user = new User();
		user.setUsername(username);
		user.setPassword(new Password(pass));
		try{
			userService.createNewUser(user);
			return Response.ok().build();			
		}catch(UserAlreadyExistsException uaee){
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@POST
	@Path("/login")
	@Consumes("application/x-www-form-urlencoded")
	public Response login(@FormParam("username")String username, @FormParam("password") String pass){
		try {
			User user = userService.findByUsername(username);
			userService.loginUser(user);
			return Response.ok().build();
		} catch (UsernameDoesNotExistException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@POST
	@Path("/logout")
	@Consumes("application/x-www-form-urlencoded")
	public Response logout(@FormParam("username")String username){
		try {
			User user = userService.findByUsername(username);
			userService.logout(user);
			return Response.ok().build();
		} catch (UsernameDoesNotExistException | UserIsNotLoggedException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
