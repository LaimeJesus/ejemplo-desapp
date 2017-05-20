package rest.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import model.products.Product;
import rest.dtos.ProductSimpleDTO;
import rest.dtos.ProductUserDTO;
import services.general.GeneralService;
import util.Category;
import util.Money;

@Path("/product")
public class ProductController {

	private GeneralService generalService;
	
	
	@GET
	@Path("/all")
	@Produces("application/json")
	public Response all(){
		try{
			List<ProductSimpleDTO> products = ProductSimpleDTO.toDTOs(getGeneralService().allProducts());
		    return Response.ok(products, MediaType.APPLICATION_JSON)
		            .header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600")
		            .build();
		}catch(Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Exception raised").build();
		}
	}
	// ejemplo de suo: /product/createexamples?howmany=10
	@GET
	@Path("/createexamples")
	@Produces("application/json")
	public Response createexamples(@QueryParam("howmany") int howmany){
		
		for(int i=0;i<howmany;i++){
			Product p = new Product();
			p.setName("name:" + String.valueOf(i));
			p.setBrand("brand:" + String.valueOf(i));
			p.setCategory(Category.Baked);
			p.setPrice(Money.toMoney(String.valueOf(i*10) + ".0"));
			p.setStock(i*10);
			getGeneralService().addProduct(p);
		}
		List<Product> prods = getGeneralService().allProducts();
		List<ProductSimpleDTO> products = ProductSimpleDTO.toDTOs(prods);
	    return Response.ok(products, MediaType.APPLICATION_JSON).build();
		
	}
	
	@POST
	@Path("/addproduct")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addproduct(ProductUserDTO pu){
		try{
			getGeneralService().addProduct(pu.user.toUser(), pu.product.toProduct());
			return Response.status(Response.Status.OK).build();
		}catch(Exception e){
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response upload(MultipartBody body){
		try{
			System.out.println("how many files are atached: " + body.getAllAttachments().size());
			for(Attachment att : body.getAllAttachments()){
				String file = att.getObject(String.class);
				getGeneralService().upload(file);
			}
			//return Response.ok("file uploaded").build();
			return Response.ok(ProductSimpleDTO.toDTOs(getGeneralService().allProducts())).build();
		}
		catch(Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); 
		}
	}

	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
	
    
}
