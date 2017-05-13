package rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.joda.time.Duration;

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
	public long waitingTime(ShopUserDTO su){
		try {
			Duration d = generalService.waitingTime(su.user.toUser(), su.productlist.toProductList());
			return d.getStandardSeconds();
		} catch (UserIsNotLoggedException | UsernameDoesNotExistException | ProductListDoesNotExist e) {
			return -1L;
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
		}catch(Exception e){
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
