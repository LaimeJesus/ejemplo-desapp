package rest.dtos;

import org.joda.time.Duration;

import model.products.Product;
import util.Category;
import util.Money;

public class ProductDetailDTO {
	
	public Integer id;
	public String name;
	public String brand;
	public String imageUrl;
	public String category;
	public Integer stock;
	public String price;
	public String processingTime;
	
	public ProductDetailDTO( Product p) {
		this.id = p.getId();
		this.name = p.getName();
		this.brand = p.getBrand();
		this.imageUrl = p.getImageUrl();
		this.category = p.getCategory().toString();
		this.stock = p.getStock();
		this.price = p.getPrice().toString();
		this.processingTime = p.getProcessingTime().toString();
	}
	
	public static ProductDetailDTO toProductSimpleDTO( Product p ) {
		return new ProductDetailDTO(p);
	}
	
	public Product toProduct () {
		Product p = new Product();
		p.setId(this.id);
		p.setName(this.name);
		p.setBrand(this.brand);
		p.setImageUrl(this.imageUrl);
		p.setCategory(Category.toCategory(this.category));
		p.setPrice(Money.toMoney(this.price));
		p.setStock(this.stock);
		p.setProcessingTime(Duration.parse(this.processingTime));
		return p;
	}

}
