package rest.dtos;

import util.Category;

public class CategoryDTO {

	public String name;
	
	public CategoryDTO(Category category) {
		this.name = category.toString();
	}
}
