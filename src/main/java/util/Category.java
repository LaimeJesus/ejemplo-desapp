package util;

public enum Category {

	Dairy, Meat, Cleaning, Baked, Vegetable, Fruit, Perfumery, Drink;
	
	public static Category toCategory(String aCategory) {
		return Category.valueOf(aCategory);
	}

	public static boolean exists(String aCategory) {
		Category[] categories = Category.values();
		for(Category c : categories){
			if(c.name().equals(aCategory)){
				return true;
			}
		}
		return false;
	}
	
}
