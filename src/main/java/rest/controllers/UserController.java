package rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import com.google.gson.Gson;

import exceptions.UserAlreadyExistsException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import exceptions.UsernameOrPasswordInvalidException;
import rest.dtos.ProfileDTO;
import rest.dtos.UserDTO;
import rest.dtos.UsernameDTO;
import services.general.GeneralService;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Path("/user")
public class UserController {
	
	
	private GeneralService generalService;

	@POST
	@Path("/signup")
	@Consumes("application/json")
	@Produces("application/json")
	public Response signup(UserDTO user){
		try{
			System.out.println(user);
			generalService.createUser(user.fullUser());
			return Response.ok(MediaType.APPLICATION_JSON)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
            .header("Access-Control-Allow-Credentials", "true")
            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
            .header("Access-Control-Max-Age", "1209600")
            .entity(user)
            .build();
		}catch(UserAlreadyExistsException e){
			return Response.status(Response.Status.BAD_REQUEST).entity("User already exists").build();
		}catch(Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response login(UserDTO user){		
		try {
			Gson gson = new Gson();
			generalService.loginUser(user.fullUser());
			return Response.ok(MediaType.APPLICATION_JSON)
		            .header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600")
		            .entity(gson.toJson(user.username))
		            .build();
		} catch (UsernameDoesNotExistException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch (UsernameOrPasswordInvalidException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}catch(Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Path("/logout")
	@Consumes("application/json")
	@Produces("application/json")
	public Response logout(UsernameDTO user){
		try {
			Gson gson = new Gson();
			generalService.logoutUser(user.toUser());
			return Response.ok(MediaType.APPLICATION_JSON)
		            .header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600")
		            .entity(gson.toJson(user))
		            .build();
		} catch (UsernameDoesNotExistException | UserIsNotLoggedException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("/myprofile")
	@Produces("application/json")
	public Response profile(@QueryParam("username") String username){
		try{
			return Response.ok(new ProfileDTO(generalService.getProfile(username)))
		            .header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600")
		            .build();
		}catch(Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Path("/changepassword")
	@Produces("application/json")
	public Response changepassword(UserDTO user){
		try{
			generalService.updatePassword(user.toUser());
			return Response.ok().build();			
		} catch(Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
		
}
