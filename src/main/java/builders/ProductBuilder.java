package builders;

import model.Product;
import util.Money;

public class ProductBuilder {
	
	private Product product;
	
	public ProductBuilder() {
		this.product = new Product();
	}
	
	private Product getProduct() {
		return this.product;
	}
	
	public Product build() {
		return this.getProduct();
	}
	
	public ProductBuilder withName(String newName) {
		this.getProduct().setName(newName);
		return this;
	}
	
	public ProductBuilder withBrand(String newBrand) {
		this.getProduct().setBrand(newBrand);
		return this;
	}
	
	public ProductBuilder withStock(String newStock) {
		this.getProduct().setStock(newStock);
		return this;
	}
	
	public ProductBuilder withPrice(Money newPrice) {
		this.getProduct().setPrice(newPrice);
		return this;
	}

}
