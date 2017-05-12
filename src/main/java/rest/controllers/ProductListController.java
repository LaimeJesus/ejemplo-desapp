package rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import model.products.ProductList;
import model.users.User;
import rest.dtos.SelectedListDTO;
import services.general.GeneralService;
import services.microservices.ProductListService;
import services.microservices.ProductService;
import services.microservices.UserService;

@Path("/productlist")
public class ProductListController {

	private ProductListService productListService;
	private UserService userService;
	private GeneralService generalService;
	private ProductService productService;
	
	@POST
	@Path("/selectproduct")
	@Consumes("application/json")
	@Produces("application/json")
	public Response selectProduct(SelectedListDTO data) {
		try {
			User exist = getUserService().findByUsername(data.getUsername());			
			getGeneralService().selectProduct(
					exist,
					new ProductList(data.getProdListName()), 
					getProductService().findById(data.getIdProd()),
					data.getQuantity());

			return Response.status(Response.Status.OK).build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@POST
	@Path("/createproductlist")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
	public Response createProductList(@FormParam("username")String username, @FormParam("listname")String listname){
		User user = generalService.getUserService().findByUsername(username);
		try{
			user = generalService.getUserService().validateLogged(user);
			generalService.createProductList(user, new ProductList(listname));
			return Response.status(Response.Status.OK).build();
		}catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	public ProductListService getProductListService() {
		return productListService;
	}

	public void setProductListService(ProductListService productListService) {
		this.productListService = productListService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
}
