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
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import com.google.gson.JsonSyntaxException;

import exceptions.InvalidUploadCSV;
import exceptions.ProductAlreadyCreatedException;
import exceptions.ProductNotExistException;
import model.products.Product;
import rest.dtos.generics.ResponseDTO;
import rest.dtos.products.ProductDTO;
import services.general.GeneralService;

@CrossOriginResourceSharing(allowAllOrigins = true)
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
		try{
			return response.ok(ProductDTO.createProducts(generalService.getProductService().retriveAll()));
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "server is not working correctly");
		}
	}

	@DELETE
	@Path("/")
	public Response deleteProducts(){
		try{
		generalService.getProductService().deleteAll();
		return response.ok("deleted products");
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "server is not working correctly");
		}
	}

	@POST
	@Path("/")
	@Consumes("application/json")
	public Response createProduct(String productJson){
		try {
			generalService.createProduct(response.gson.fromJson(productJson, ProductDTO.class).toProduct());
			return response.ok("created product");
		} catch (ProductAlreadyCreatedException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		} catch(JsonSyntaxException e){
		  return response.error(Status.CONFLICT, e.getMessage());
		}
		catch (Exception e){
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
			return response.ok(new ProductDTO(generalService.getProductById(productId)));
		} catch (ProductNotExistException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		} catch (Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	@DELETE
	@Path("/{productId}")
	public Response deleteProductById(@PathParam("productId") Integer productId){
		try {
			generalService.deleteProductById(productId);
			return response.ok("deleted product");
		} catch (ProductNotExistException e) {
			return response.error(Status.CONFLICT,e.getMessage());
		} catch(Exception e){
			return response.error(Status.INTERNAL_SERVER_ERROR, "server is not working correctly");
		}
	}

	@PUT
	@Path("/{productId}")
	@Consumes("application/json")
	public Response createOrUpdateProduct(@PathParam("productId") Integer productId, String productJson){
		try {
			Product product = response.gson.fromJson(productJson, ProductDTO.class).toProduct();
			generalService.createOrUpdateProduct(productId, product);
			return response.ok("product created or updated");
		} catch (ProductAlreadyCreatedException e) {
			return response.error(Status.CONFLICT, e.getMessage());
		} catch(JsonSyntaxException e){
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
      } catch (InvalidUploadCSV e) {
        return response.error(Status.CONFLICT, e.getMessage());
      } catch (ProductAlreadyCreatedException e) {
        return response.error(Status.CONFLICT, e.getMessage());
      } catch (Exception e) {
        return response.error(Status.CONFLICT, "server not working correctly");
      }
	}

}
