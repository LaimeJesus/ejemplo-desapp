package rest.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import rest.dtos.ProductDTO;
import rest.dtos.ProductUserDTO;
import services.general.GeneralService;

@Path("/product")
public class ProductController {

	private GeneralService generalService;
	
	
	@GET
	@Path("/all")
	@Produces("application/json")
	public Response all(){
		try{
			List<ProductDTO> products = ProductDTO.toDTOs(getGeneralService().allProducts());
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
			return Response.ok().build();
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
