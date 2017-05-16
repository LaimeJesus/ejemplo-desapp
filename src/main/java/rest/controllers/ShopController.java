package rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.joda.time.Duration;

import exceptions.InvalidSelectedProduct;
import exceptions.ProductListDoesNotExist;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import rest.dtos.ShopUserDTO;
import services.general.GeneralService;

@Path("/shop")
public class ShopController {
	
	
	private GeneralService generalService;
	
	@GET
	@Path("/waitingtime")
	@Consumes("application/json")
	@Produces("application/json")
	public Response waitingTime(@QueryParam("username") String username, @QueryParam("listname") String listname){
		try {
			Duration d = generalService.waitingTime(username, listname);
			return Response.ok(d.getStandardSeconds()).build();
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
				generalService.ready(su.user.toUser(), su.productlist.toProductList());
				return Response.status(Response.Status.OK).build(); 
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
