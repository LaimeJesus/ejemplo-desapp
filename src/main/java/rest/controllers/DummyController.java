package rest.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import exceptions.UserAlreadyExistsException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import exceptions.UsernameOrPasswordInvalidException;
import model.products.Product;
import model.products.ProductList;
import model.users.User;
import rest.dummys.DummyLists;
import rest.dummys.DummyProduct;
import rest.dummys.DummyUser;
import services.general.GeneralService;

@Path("/dummy")
public class DummyController {

	private GeneralService generalService;

	@GET
	@Path("/example")
	@Produces("application/json")
	public Response initialize() throws UsernameDoesNotExistException, UserIsNotLoggedException, UsernameOrPasswordInvalidException{
		try {
			List<Product> products = new DummyProduct().example();
			List<User> users = new DummyUser().example();
			List<ProductList> lists = new DummyLists().example();
			List<ProductList> lists2 = new DummyLists().example();
			generalService.addProducts(products);
			
			User uno = users.get(0);
			User dos = users.get(1);
			
			generalService.createUser(uno);
			generalService.loginUser(uno);
			generalService.createUser(dos);
			generalService.loginUser(dos);
			for(ProductList list : lists){
				generalService.createProductList(uno, list);
				
			}
			for(ProductList list : lists2){
				generalService.createProductList(dos, list);
				
			}
			generalService.logoutUser(uno);
			generalService.logoutUser(dos);

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
