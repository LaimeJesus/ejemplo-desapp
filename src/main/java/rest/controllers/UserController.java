package rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import com.google.gson.Gson;

import exceptions.PurchaseRecordNotExistException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserDoesNotExistException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import exceptions.UsernameOrPasswordInvalidException;
import model.users.User;
import rest.dtos.ErrorDTO;
import rest.dtos.ProfileDTO;
import rest.dtos.UserDTO;
import rest.dtos.UsernameDTO;
import services.general.GeneralService;
import util.Address;

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
	
	
//	REFACTORIZANDO LOS ENDPOINTS PARA HACERLOS GENERICOS
	
	@GET
	@Path("/users/{userId}")
	@Produces("application/json")
	public Response getById(@PathParam("userId") Integer userId){
		try {
			return ok(generalService.getUserService().getUserById(userId));
		} catch (UserDoesNotExistException e) {
			return error(Status.CONFLICT, e.getMessage());
		}
	}
	
	@GET
	@Path("/users/{userId}/profile")
	@Produces("application/json")
	public Response getProfileById(@PathParam("userId") Integer userId){
		try {
			return ok(generalService.getUserService().getUserById(userId).getProfile());
		} catch (UserDoesNotExistException e) {
			return error(Status.CONFLICT, e.getMessage());
		}
	}
	
	@PUT
	@Path("/users")
	@Consumes("application/json")
	public Response createOrUpdateUser(UserDTO user){		
		User u = generalService.getUserService().findByUsername(user.username);
		if(u == null){
			try {
				generalService.createUser(user.fullUser());
				return Response.ok().build();
			} catch (UserAlreadyExistsException e) {
				return Response.status(Status.CONFLICT).build();
			}
		}else{
			generalService.getUserService().update(user.toUser());
			return Response.ok().build();			
		}
	}
	
	@GET
	@Path("/users/{userId}/purchases")
	@Produces("application/json")
	public Response getPurchases(@PathParam("userId") Integer userId){
		try {
			return ok(generalService.getUserService().getUserById(userId).getProfile().getPurchaseRecords());
		} catch (UserDoesNotExistException e) {
			return error(Status.CONFLICT, e.getMessage());
		}
	}
	
	@GET
	@Path("users/{userId}/purchases/{purchaseId}")
	@Produces("application/json")
	public Response getPurchaseById(@PathParam("userId") Integer userId, @PathParam("purchaseId") Integer purchaseId){
		try {
			return ok(generalService.getPurchaseRecord(userId, purchaseId));
		} catch (PurchaseRecordNotExistException | UserDoesNotExistException e) {
			return error(Status.CONFLICT, e.getMessage());
		} catch(Exception e){
			return error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GET
	@Path("/users/{userId}/address")
	public Response getAddress(@PathParam("userId") Integer userId){
		
		try {
			return ok(generalService.getUserService().getUserById(userId));
		} catch (UserDoesNotExistException e) {
			return error(Status.CONFLICT, e.getMessage());
		}
	}
	
	@PUT
	@Path("/users/{id}/profile")
	public Response createOrUpdateProfile(@PathParam("id") Integer id, ProfileDTO profile){
		User u = generalService.getUserService().findById(id);
		u.getProfile().setAddress(new Address(profile.address));
		generalService.getUserService().update(u);
		return Response.ok().build();
	}
	
	@GET
	@Path("/users")
	@Produces("application/json")
	public Response users(){
		return Response.ok(generalService.getUserService().getRepository().findAll()).build();
	}
	
	@GET
	@Path("/users/{userId}/lists")
	@Produces("application/json")
	public Response lists(@PathParam("userId") Integer userId){
		try {
			return ok(generalService.getUserService().getProductLists(userId));
		} catch (UserDoesNotExistException e) {
			return error(Status.CONFLICT, e.getMessage());
		}
	}
	
	@GET
	@Path("/users/{userId}/lists/")
	@Produces("application/json")
	public Response lists(@PathParam("userId") Integer userId, @QueryParam("sortBy") String field){
		char order = field.charAt(0);
		String sanitize = field.substring(1);
		try {
			return ok(generalService.getUserService().getProductLists(userId));
		} catch (UserDoesNotExistException e) {
			return error(Status.CONFLICT, e.getMessage());
		}
	}
	
//	AUTH VALIDATIONS 
	@POST
	@Path("/users/signup")
	public Response create(UserDTO user){
		try {
			generalService.createUser(user.fullUser());
			return Response.ok().build();
		} catch (UserAlreadyExistsException e) {
			return error(Status.FORBIDDEN, e.getMessage());
		}
	}
	
	
	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
	
	public Response ok(Object entity){
		return Response.ok().entity(entity).build();
	}
	public Response error(Status s, String message){
		return Response.status(s).entity(new ErrorDTO(s, message)).build();
	}
	
}
