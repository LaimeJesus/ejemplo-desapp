package rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import rest.dtos.CreateListDTO;
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
			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
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
		}catch (Exception e) {
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
