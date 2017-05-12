package rest.dtos;

import model.products.Product;
import util.Category;

public class ProductDTO {
	
	public int id;
	public String name;
	public String brand;
//	public Integer stock;
//	public String price;
	public Category category;
	//public Duration processingTime;
	//public String imageUrl;
	
	public ProductDTO(){
		
	}
	
	public ProductDTO(Product p){
		this.id = p.getId();
		this.name = p.getName();
		this.brand = p.getBrand();
//		this.stock = p.getStock();
//		this.price = p.getMoneyValue();
		this.category = p.getCategory();
		//this.processingTime = p.getProcessingTime();
		//this.imageUrl = p.getImageUrl();

	}
}
