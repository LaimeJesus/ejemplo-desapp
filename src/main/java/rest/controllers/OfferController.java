package rest.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import rest.dtos.CategoryDTO;
import rest.dtos.users.UserDTO;
import services.general.GeneralService;
import util.Category;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Path("/offer")
public class OfferController {
	
	
	private GeneralService generalService;
	
	
	@GET
	@Path("/categories")
	@Produces("application/json")
	public Response getAllCategories(){
		
		try {
			List<CategoryDTO> values = new ArrayList<CategoryDTO>(); 
			for (Category category : Category.values()){
				values.add(new CategoryDTO(category));
			}
			return Response.ok(MediaType.APPLICATION_JSON)
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600")
	            .entity(values)
	            .build();
		} catch (Exception e) {
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
