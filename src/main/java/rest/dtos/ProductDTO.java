package rest.dtos;

import org.joda.time.Duration;

import model.products.Product;
import util.Category;
import util.Money;

public class ProductDTO {
	
	public String name;
	public String brand;
	public Integer stock;
	public String price;
	public String category;
	public Integer processingTime;
	//public String imageUrl;
	
	public ProductDTO(){
		
	}
	
	public ProductDTO(Product p){
		this.name = p.getName();
		this.brand = p.getBrand();
		this.stock = p.getStock();
		this.price = p.getPrice().toString();
		this.category = p.getCategory().toString();
		this.processingTime = (int) p.getProcessingTime().getStandardSeconds();
		//this.imageUrl = p.getImageUrl();
	}
	
	public Product toProduct(){
		Product prod = new Product();
		prod.setName(name);
		prod.setBrand(brand);
		prod.setStock(stock);
		prod.setPrice(Money.toMoney(price));
		prod.setCategory(Category.valueOf(category));
		prod.setProcessingTime(new Duration(processingTime));
		return prod;
	}

	public Product toUniqueProduct() {
		Product prod = new Product();
		prod.setName(name);
		prod.setBrand(brand);
		return prod;
	}
}
