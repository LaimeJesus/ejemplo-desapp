package rest.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.joda.time.Duration;

import builders.ProductBuilder;
import builders.UserBuilder;
import exceptions.UserAlreadyExistsException;
import model.products.Product;
import model.users.User;
import services.general.GeneralService;
import util.Category;
import util.Money;
import util.Password;
import util.Permission;

@Path("/")
public class InitializerController {

	private GeneralService generalService;
	
	@GET
	@Path("/init")
	@Produces("application/json")
	public Response initialize(){
		
		Product toAdd = new ProductBuilder()
			.withName("Arroz")
			.withBrand("Marolio")
			.withPrice(new Money(13,50))
			.withCategory(Category.Baked)
			.withStock(350)
			.withProcessingTime(new Duration(2L))
			.build();
			
		generalService.addProduct(toAdd);
		
		toAdd.setName("Leche");
		toAdd.setBrand("La serenisima");
		toAdd.setPrice(new Money(11,70));
		toAdd.setCategory(Category.Dairy);
		toAdd.setStock(500);
		toAdd.setProcessingTime(new Duration(2L));
		generalService.addProduct(toAdd);
		
		toAdd.setName("Leche");
		toAdd.setBrand("La serenisima");
		toAdd.setPrice(new Money(11,70));
		toAdd.setCategory(Category.Dairy);
		toAdd.setStock(500);
		toAdd.setProcessingTime(new Duration(2L));
		generalService.addProduct(toAdd);
		
		toAdd.setName("Pan");
		toAdd.setBrand("Bimbo");
		toAdd.setPrice(new Money(28,50));
		toAdd.setCategory(Category.Baked);
		toAdd.setStock(400);
		toAdd.setProcessingTime(new Duration(2L));
		generalService.addProduct(toAdd);
		
		toAdd.setName("Bondiola");
		toAdd.setBrand("Paladini");
		toAdd.setPrice(new Money(65,39));
		toAdd.setCategory(Category.Meat);
		toAdd.setStock(20);
		toAdd.setProcessingTime(new Duration(3L));
		generalService.addProduct(toAdd);
		
		toAdd.setName("Lechuga");
		toAdd.setBrand("La buena nueva");
		toAdd.setPrice(new Money(64,50));
		toAdd.setCategory(Category.Vegetable);
		toAdd.setStock(500);
		toAdd.setProcessingTime(new Duration(2L));
		generalService.addProduct(toAdd);
		
		toAdd.setName("Manzana");
		toAdd.setBrand("La mendocina");
		toAdd.setPrice(new Money(24,00));
		toAdd.setCategory(Category.Fruit);
		toAdd.setStock(600);
		toAdd.setProcessingTime(new Duration(2L));
		generalService.addProduct(toAdd);
		
		toAdd.setName("Arroz");
		toAdd.setBrand("Molto");
		toAdd.setPrice(new Money(15,30));
		toAdd.setCategory(Category.Baked);
		toAdd.setStock(370);
		toAdd.setProcessingTime(new Duration(2L));
		generalService.addProduct(toAdd);
		
		toAdd.setName("Leche");
		toAdd.setBrand("Sancor");
		toAdd.setPrice(new Money(21,56));
		toAdd.setCategory(Category.Dairy);
		toAdd.setStock(500);
		toAdd.setProcessingTime(new Duration(2L));
		generalService.addProduct(toAdd);
		
		
		User jesus = new UserBuilder()
			.withUsername("JesusLaime")
			.withEmail("laimejesu@gmail.com")
			.withPassword(new Password("desapp123"))
			.withUserPermission(Permission.ADMIN)
			.build();
		User lucas = new UserBuilder()
			.withUsername("LucasSandoval")
			.withEmail("sandoval.lucasj@gmail.com")
			.withPassword(new Password("desapp123"))
			.withUserPermission(Permission.ADMIN)
			.build();
		
		try {

			generalService.createUser(jesus);
			generalService.createUser(lucas);
			
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
