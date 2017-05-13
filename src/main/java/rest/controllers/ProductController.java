package rest.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import model.products.Product;
import rest.dtos.ProductDTO;
import services.microservices.ProductService;
import util.Category;

@Path("/product")
public class ProductController {


	private ProductService productService;
	
	@GET
	@Path("/all")
	@Produces("application/json")
	public List<ProductDTO> products(){
		try{
			for(int i=0;i<10;i++){
				Product p = new Product();
				p.setName("name:" + String.valueOf(i));
				p.setBrand("brand:" + String.valueOf(i));
				p.setCategory(Category.Baked);
				p.setStock(i*10);
				productService.save(p);			
			}
			//creo la lista de respuesta
			List<ProductDTO> productsDTOs = new ArrayList<ProductDTO>();
			//lleno la lista con los productos pasados a dtos
			for(Product p : productService.retriveAll()){
				productsDTOs.add(new ProductDTO(p));
			}
			//devuelvo la lista de dtos
			return productsDTOs;			
		}catch(Exception e){
			return new ArrayList<ProductDTO>();
		}	
	}
	
	@POST
	@Path("addproduct")
	@Produces("application/json")
	public Response addproduct(ProductDTO product){
		try{
			productService.save(product.toProduct());
			return Response.status(Response.Status.OK).build();
		}catch(Exception e){
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	
//	@POST
//	@Path("/upload")
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces("application/json")
//	public String upload(@Multipart(value = "file") Attachment att){
//        try{
//        	System.out.println("LLEGUE 1");
//        	System.out.println(att.getContentType());
//        	//file..close();
//            System.out.println("LLEGUE 2");
//            //System.out.println(fileString);
//            //this.productService.upload(fileString);
//            System.out.println("LLEGUE 3");
//            return "test loaded";
//        }
//        catch(Exception e){
//        	return "You failed to upload test => " + e.getMessage(); 
//        }
//    }

    public ProductService getProductService(){
    	return this.productService;
    }

    public void setProductService(ProductService aProductService){
    	this.productService = aProductService;
    }    
}
