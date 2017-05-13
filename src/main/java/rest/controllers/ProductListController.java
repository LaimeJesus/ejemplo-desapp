package rest.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import exceptions.ProductDoesNotExistException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import model.products.ProductList;
import rest.dtos.CreateListDTO;
import rest.dtos.ProductListDTO;
import rest.dtos.SelectedListDTO;
import rest.dtos.UserDTO;
import services.general.GeneralService;

@Path("/productlist")
public class ProductListController {

	private GeneralService generalService;
	
	@POST
	@Path("/selectproduct")
	@Consumes("application/json")
	@Produces("application/json")
	public Response selectproduct(SelectedListDTO data) {
		try {
			generalService.selectProduct(data.user.toUser(), data.productlist.toProductList(), data.product.toUniqueProduct(), data.quantity);
			return Response.status(Response.Status.OK).build();
		} catch (ProductIsAlreadySelectedException | ProductDoesNotExistException | UsernameDoesNotExistException | UserIsNotLoggedException e) {
			System.out.println(e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Path("/createproductlist")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createproductlist(CreateListDTO cl){
		try{
			generalService.createProductList(cl.user.toUser(), cl.productlist.toProductList());
			return Response.status(Response.Status.OK).build();
		} catch (UsernameDoesNotExistException | UserIsNotLoggedException e) {
			System.out.println(e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/getproductlists")
	@Consumes("application/json")
	@Produces("application/json")
	public List<ProductListDTO> getproductlists(UserDTO user){
		List<ProductListDTO> res = new ArrayList<ProductListDTO>();
		try{
			List<ProductList> productlists = generalService.getProductLists(user.toUser());
			for(ProductList pl : productlists){
				res.add(new ProductListDTO(pl));
			}
			return res;
		}catch (UserIsNotLoggedException | UsernameDoesNotExistException e) {
			System.out.println(e.getMessage());
			return res;
		}
	}
	
	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
}
