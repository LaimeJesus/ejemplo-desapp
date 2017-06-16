package rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.cxf.rs.security.cors.CorsHeaderConstants;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import exceptions.UserAlreadyExistsException;
import rest.dtos.users.UserDTO;
import services.general.GeneralService;


@CrossOriginResourceSharing(
        allowOrigins = {
        		"*"
        }, 
        allowCredentials = true, 
        maxAge = 1,
        allowHeaders = {
                "X-custom-1", "X-custom-2"
             },
	  exposeHeaders = {
	           "X-custom-3", "X-custom-4"
	        }
)
@Path("/cors")
public class CorsController {

	private GeneralService generalService;


	@POST
	@Path("/signuptest")
	@Consumes("application/json")
	@Produces("application/json")
	public Response signup(UserDTO user){
		try{
			System.out.println(user);
			getGeneralService().createUser(user.fullUser());
			return Response.ok()
					.header(CorsHeaderConstants.HEADER_AC_ALLOW_METHODS, "POST")
                    .header(CorsHeaderConstants.HEADER_AC_ALLOW_CREDENTIALS, "false")
                    .header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*")
                    .build();
		}catch(UserAlreadyExistsException e){
			return Response.status(Response.Status.BAD_REQUEST).entity("User already exists").build();
		}catch(Exception e){
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
