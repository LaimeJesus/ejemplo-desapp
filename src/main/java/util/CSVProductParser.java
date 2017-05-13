package util;

import java.util.List;

import org.joda.time.Duration;

import exceptions.InvalidArgumentsException;
import exceptions.InvalidCategoryException;
import exceptions.InvalidDurationException;
import exceptions.InvalidMoneyException;
import model.products.Product;

public class CSVProductParser extends CSVParser<Product>{

	
	//processing time is a Long integer
	@Override
	public Product toObject(List<String> attributes) throws InvalidMoneyException, InvalidCategoryException, InvalidArgumentsException, InvalidDurationException {
		Product aProduct = new Product();
		
		validateAttributes(attributes);
		
		String name = attributes.get(0);
		String brand = attributes.get(1);
		String stock = attributes.get(2);
		String money = attributes.get(3);
		String category = attributes.get(4);
		String processingTime = attributes.get(5);
		String imageUrl = attributes.get(6);
		
		aProduct.setName(name);
		aProduct.setBrand(brand);
		aProduct.setStock(Integer.parseInt(stock));
		checkIsValidMoney(money);
		aProduct.setPrice(Money.toMoney(money));
		checkIsValidCategory(category);
		aProduct.setCategory(Category.toCategory(category));
		aProduct.setImageUrl(imageUrl);
		checkIsValidDuration(processingTime);
		int duration = Integer.parseInt(processingTime);
		Long realDuration = new Long(duration);
		aProduct.setProcessingTime(new Duration(realDuration));
		return aProduct;
	}

	private void checkIsValidDuration(String processingTime) throws InvalidDurationException {
		if(!processingTime.matches("[0-9]+")){
			throw new InvalidDurationException();
		}
	}

	private void validateAttributes(List<String> attributes) throws InvalidArgumentsException {
		if(attributes.size() != 7){
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
