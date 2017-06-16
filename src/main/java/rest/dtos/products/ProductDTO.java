package rest.dtos.products;

import java.util.List;
import java.util.stream.Collectors;

import model.products.Product;
import rest.dtos.generics.DurationDTO;
import util.Category;
import util.Money;

public class ProductDTO {
	public int id;
	public String name;
	public String brand;
	public Integer stock;
	public Money price;
	public Category category;
	//milliseconds
	public DurationDTO processingTime;
	public String imageUrl;

	
	public ProductDTO(Product product) {
		id = product.getId();
		name = product.getName();
		brand = product.getBrand();
		stock = product.getStock();
		price = product.getPrice();
		category = product.getCategory();
		processingTime = new DurationDTO(product.getProcessingTime());
		imageUrl = product.getImageUrl();
	}


	public Product toProduct(){
		Product p = new Product();
		
		p.setName(name);
		p.setBrand(brand);
		p.setStock(stock);
		p.setPrice(price);
		p.setCategory(category);
		p.setProcessingTime(processingTime.toDuration());
		p.setImageUrl(imageUrl);
		
		return p;
	}


	public static List<ProductDTO> createProducts(List<Product> products) {
		return products.stream().map((Product p) -> new ProductDTO(p)).collect(Collectors.toList());
	}
}
