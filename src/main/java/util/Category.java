package util;

import org.apache.commons.lang3.EnumUtils;

public enum Category {

	Dairy, Meat, Cleaning, Baked, Vegetable, Fruit, Perfumery, Drink;
	
	public static Category toCategory(String aCategory) {
		return Category.valueOf(aCategory);
	}

	public static boolean exists(String aCategory) {
		return EnumUtils.isValidEnum(Category.class, aCategory);
	}
	
}
