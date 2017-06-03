package rest.controllers;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import com.google.gson.JsonSyntaxException;

import exceptions.ProductAlreadyCreatedException;
import exceptions.ProductNotExistException;
import model.products.Product;
import rest.dtos.ResponseDTO;
import services.general.GeneralService;

@Path("/products")
public class ProductsController {

	private GeneralService generalService;
	private ResponseDTO response;

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}

	@PostConstruct
	public void init(){
		response = new ResponseDTO();
	}

	/////////////////////////////////////////////////////////////
	//METHODS FOR ALL PRODUCTS
	@GET
	@Path("/")
	@Produces("application/json")
	public Response getProducts(){
		return response.ok(generalService.getProductService().retriveAll());
	}

	@DELETE
	@Path("/")
	public Response deleteProducts(){
		generalService.getProductService().deleteAll();
		return response.ok("deleted products");
	}

	@POST
	@Path("/")
	public Response createProduct(String productJson){
		try {
			generalService.createProduct(response.gson.fromJson(productJson, Product.class));
			return response.ok("created product");
		} catch (JsonSyntaxException | ProductAlreadyCreatedException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		} catch (Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/////////////////////////////////////////////////////////////
	//METHODS FOR A SPECIFIC PRODUCT
	@GET
	@Path("/{productId}")
	@Produces("application/json")
	public Response getProductById(@PathParam("productId") Integer productId){
		try {
			return response.ok(generalService.getProductById(productId));
		} catch (ProductNotExistException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		} catch (Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	@DELETE
	@Path("/{productId}")
	public Response deleteProductById(@PathParam("productId") Integer productId){
		generalService.deleteProductById(productId);
		return response.ok("deleted product");
	}

	@PUT
	@Path("/{productId}")
	@Consumes("application/json")
	public Response createOrUpdateProduct(@PathParam("productId") Integer productId, String productJson){
		try {
			Product product = response.gson.fromJson(productJson, Product.class);
			generalService.createOrUpdateProduct(productId, product);
			return response.ok("product created or updated");
		} catch (JsonSyntaxException | ProductAlreadyCreatedException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		} catch (Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	/////////////////////////////////////////////////////////////
	
	@PUT
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response uploadProductFile(MultipartBody body){
		String file = null;
		for(Attachment att : body.getAllAttachments()){
			file = att.getObject(String.class);
		}
		try {
			getGeneralService().upload(file);
			return response.ok(generalService.getProductService().retriveAll());
		} catch (Exception e) {
			return response.error(Status.CONFLICT, e.getMessage());
		}
	}

}
