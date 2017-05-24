package rest.dtos;

import java.util.ArrayList;
import java.util.List;

import model.products.Product;
import util.Category;
import util.Money;

public class ProductSimpleDTO {
	
	public Integer id;
	public String name;
	public String brand;
	public String imageUrl;
	
	public ProductSimpleDTO(){
		
	}
	
	public ProductSimpleDTO(Product p){
		this.id = p.getId();
		this.name = p.getName();
		this.brand = p.getBrand();
		if (p.getImageUrl() != null){
			this.imageUrl = p.getImageUrl();
		} else {
			this.imageUrl = "no-image";
		}
		
	}

	public static List<ProductSimpleDTO> toDTOs(List<Product> retriveAll) {
		ArrayList<ProductSimpleDTO> res = new ArrayList<ProductSimpleDTO>();
		for(Product p : retriveAll){
			res.add(new ProductSimpleDTO(p));
		}
		return res;
	}

	public Product toUniqueProduct() {
		Product prod = new Product();
		prod.setName(name);
		prod.setBrand(brand);
		return prod;
	}

}
