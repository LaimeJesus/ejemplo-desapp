package rest.controllers;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import exceptions.OfferIsAlreadyCreatedException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import exceptions.UsernameOrPasswordInvalidException;
import exceptions.WrongUserPermissionException;
import model.offers.Offer;
import model.products.Product;
import model.products.ProductList;
import model.users.User;
import rest.dummys.DummyLists;
import rest.dummys.DummyOffers;
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
			List<Offer> alloffers = new DummyOffers().example();
			generalService.addProducts(products);
			
			User lucas = users.get(0);
			User jesus = users.get(1);
			generalService.createUser(lucas);
			generalService.loginUser(lucas);
			for (ProductList list : lists){				
				generalService.createProductList(lucas, list);
			}
			
			generalService.logoutUser(lucas);
			generalService.createUser(jesus);
			generalService.loginUser(jesus);
			for (ProductList list : lists2){				
				generalService.createProductList(jesus, list);
			}
			for (Offer o : alloffers) {
				generalService.createOffer(o, jesus);
			}
			generalService.logoutUser(jesus);
			
			generalService.initRegisters(1);
			return Response.ok(Response.Status.ACCEPTED).entity("All data loaded correctly").build();

		} catch (UserAlreadyExistsException | WrongUserPermissionException | OfferIsAlreadyCreatedException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@DELETE
	@Path("/all")
	public void deleteall(){
		generalService.getUserService().deleteAll();
		generalService.getProductService().deleteAll();
		generalService.getGeneralOfferService().deleteAll();
//		generalService.getShopService().getCashRegisterManager().stop();
	}

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

}
