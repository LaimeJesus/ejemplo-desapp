package rest.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import exceptions.ProductDoesNotExistException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import model.products.ProductList;
import rest.dtos.CreateListDTO;
import rest.dtos.ProductListDTO;
import rest.dtos.SelectedListDTO;
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
			return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600").build();
		} catch (ProductIsAlreadySelectedException | ProductDoesNotExistException | UsernameDoesNotExistException | UserIsNotLoggedException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch(Exception e){
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
			return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600").build();
		} catch (UsernameDoesNotExistException | UserIsNotLoggedException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/getproductlists")
	@Consumes("application/json")
	@Produces("application/json")
	public List<ProductListDTO> getproductlists(@QueryParam("username") String username){
		List<ProductListDTO> res = new ArrayList<ProductListDTO>();
		try{
			List<ProductList> productlists = generalService.getProductLists(generalService.getUserService().findByUsername(username));
			for(ProductList pl : productlists){
				res.add(new ProductListDTO(pl));
			}
			return res;
		}catch (UserIsNotLoggedException | UsernameDoesNotExistException e) {
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
