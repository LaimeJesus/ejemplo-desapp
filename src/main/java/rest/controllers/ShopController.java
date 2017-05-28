package rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import exceptions.InvalidSelectedProduct;
import exceptions.ProductListDoesNotExist;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import rest.dtos.ShopUserDTO;
import services.general.GeneralService;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Path("/shop")
public class ShopController {


	private GeneralService generalService;

//  ejemplo de java duration en forma de json
//	{
//		  "standardSeconds": 4,
//		  "standardDays": 0,
//		  "standardHours": 0,
//		  "standardMinutes": 0,
//		  "millis": 4000
//		}
	
	@GET
	@Path("/waitingtime")
	@Consumes("application/json")
	@Produces("application/json")
	public Response waitingTime(@QueryParam("username") String username, @QueryParam("listname") String listname){
		try {
			return Response.ok(generalService.waitingTime(username, listname)).build();
		} catch (UserIsNotLoggedException | UsernameDoesNotExistException | ProductListDoesNotExist e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch (Exception e){
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/ready")
	@Consumes("application/json")
	@Produces("application/json")
	public Response ready(ShopUserDTO su){
			try {
				return Response.ok(generalService.ready(su.user.toUser(), su.productlist.toProductList())).build();
			} catch (InvalidSelectedProduct | UserIsNotLoggedException | UsernameDoesNotExistException e) {
				e.printStackTrace();
				return Response.status(Response.Status.BAD_REQUEST).build();
			} catch (Exception e){
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
	}


	@GET
	@Path("/initialize")
	public Response initialize(@QueryParam("howmany")int howmany){
		try {
			generalService.getShopService().initialize(howmany);
			return Response.ok("Cash registers loaded").build();
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
