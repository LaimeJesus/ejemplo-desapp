package rest.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import exceptions.UserAlreadyExistsException;
import model.products.Product;
import model.users.User;
import rest.dummys.DummyProduct;
import rest.dummys.DummyUser;
import services.general.GeneralService;

@Path("/dummy")
public class DummyController {

	private GeneralService generalService;

	@GET
	@Path("/example")
	@Produces("application/json")
	public Response initialize(){
		try {
			List<Product> products = new DummyProduct().example();
			List<User> users = new DummyUser().example();
			generalService.addProducts(products);
			for(User u : users){
				generalService.createUser(u);
			}
			generalService.initRegisters(10);
			return Response.status(Response.Status.ACCEPTED).entity("All data loaded correctly").build();

		} catch (UserAlreadyExistsException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

}
