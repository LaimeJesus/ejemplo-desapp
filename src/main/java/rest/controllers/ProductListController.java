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

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import exceptions.ProductDoesNotExistException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import model.products.ProductList;
import model.users.User;
import rest.dtos.CreateListDTO;
import rest.dtos.ProductListDTO;
import rest.dtos.SelectedListDTO;
import services.general.GeneralService;

@CrossOriginResourceSharing(allowAllOrigins = true)
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
	@Path("/create")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createproductlist(CreateListDTO cl){
		try{
			User user = generalService.getUserService().findByUsername(cl.username);
			ProductList list = new ProductList(cl.name);
			generalService.createProductList(user,list);
			return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600").build();
		} catch (UsernameDoesNotExistException | UserIsNotLoggedException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch(Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/mylists")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getproductlists(@QueryParam("username") String username ){
		List<ProductListDTO> res = new ArrayList<ProductListDTO>();
		try{
			List<ProductList> productlists = generalService.getProductLists(generalService.getUserService().findByUsername(username));
			for(ProductList pl : productlists){
				res.add(new ProductListDTO(pl));
			}
			return Response
					.status(Response.Status.OK)
					.header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600")
		            .entity(res)
		            .build();
		}catch (UserIsNotLoggedException | UsernameDoesNotExistException e) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(e.getMessage())
					.build();
		}
	}
	
	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
}
