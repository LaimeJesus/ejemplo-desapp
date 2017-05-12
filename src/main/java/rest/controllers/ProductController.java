package rest.controllers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMultipart;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import model.products.Product;
import rest.dtos.ProductDTO;
import services.microservices.ProductService;
import util.Category;
import util.Money;

//@RestController
//@Controller
//@RequestMapping("/product")
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
