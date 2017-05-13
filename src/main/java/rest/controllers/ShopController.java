package rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.joda.time.Duration;

import exceptions.ProductListDoesNotExist;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import rest.dtos.ShopUserDTO;
import services.microservices.ShopService;

@Path("/shop")
public class ShopController {
	
	public ShopService shopService;
	
	@GET
	@Consumes("application/json")
	public Response waitingTime(ShopUserDTO su){
		try {
			Duration d = shopService.waitingTime(su.user.toUser(), su.productlist.toProductList());
			return null;
		} catch (UserIsNotLoggedException | UsernameDoesNotExistException | ProductListDoesNotExist e) {
			return null;
		}
		
	}

	
	@POST
	@Consumes("application/json")
	public Response ready(ShopUserDTO su){
		return null;
	}
	
}
