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
import rest.dtos.ProductDTO;
import rest.dtos.ProductUserDTO;
import services.general.GeneralService;
import util.Category;

@Path("/product")
public class ProductController {

	private GeneralService generalService;
	
	
	@GET
	@Path("/all")
	@Produces("application/json")
	public Response all(){
		try{
			List<ProductDTO> products = ProductDTO.toDTOs(getGeneralService().getProductService().retriveAll());
		    return Response.ok(products, MediaType.APPLICATION_JSON).build();
		}catch(Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Exception raised").build();
		}
	}
	// ejemplo de suo: /product/createexamples?howmany=10
	@GET
	@Path("/createexamples")
	@Produces("application/json")
	public Response createexamples(@QueryParam("howmany") int howmany){
		try{
			for(int i=0;i<howmany;i++){
				Product p = new Product();
				p.setName("name:" + String.valueOf(i));
				p.setBrand("brand:" + String.valueOf(i));
				p.setCategory(Category.Baked);
				p.setStock(i*10);
				getGeneralService().addProduct(p);
			}
			List<ProductDTO> products = ProductDTO.toDTOs(getGeneralService().getProductService().retriveAll());
		    return Response.ok(products, MediaType.APPLICATION_JSON).build();
		}catch(Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Exception raised").build();
		}	
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
	
	public GeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(GeneralService generalService) {
		this.generalService = generalService;
	}
	
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response upload(MultipartBody body){
        try{
        	System.out.println("how many files are atached: " + body.getAllAttachments().size());
        	for(Attachment att : body.getAllAttachments()){
        		System.out.println(att.getObject(String.class));
        		String file = att.getObject(String.class);
        		getGeneralService().upload(file);        		
        	}
            return Response.ok("file uploaded").build();
        }
        catch(Exception e){
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); 
        }
    }
    
}
