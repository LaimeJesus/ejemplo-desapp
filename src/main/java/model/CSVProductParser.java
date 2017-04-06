package model;

import java.util.List;

import exceptions.InvalidArgumentsException;
import exceptions.InvalidCategoryException;
import exceptions.InvalidMoneyException;
import util.Category;
import util.Money;

public class CSVProductParser extends CSVParser<Product>{

	@Override
	public Product toObject(List<String> attributes) throws InvalidMoneyException, InvalidCategoryException, InvalidArgumentsException {
		Product aProduct = new Product();
		
		checkValidArguments(attributes);
		
		String name = attributes.get(0);
		String brand = attributes.get(1);
		String stock = attributes.get(2);
		String money = attributes.get(3);
		String category = attributes.get(4);
		
		aProduct.setName(name);
		aProduct.setBrand(brand);
		aProduct.setStock(stock);
		checkIsValidMoney(money);
		aProduct.setPrice(Money.toMoney(money));
		checkIsValidCategory(category);
		aProduct.setCategory(Category.toCategory(category));
		return aProduct;
	}

	private void checkValidArguments(List<String> attributes) throws InvalidArgumentsException {
		if(attributes.size() != 5){
			throw new InvalidArgumentsException();
		}
	}

	private void checkIsValidCategory(String aCategory) throws InvalidCategoryException {
		if(!Category.exists(aCategory)){
			throw new InvalidCategoryException();
		}
	}

	private void checkIsValidMoney(String aMoney) throws InvalidMoneyException {
		if(!Money.isValid(aMoney)){
			throw new InvalidMoneyException();
		}
	}

}
