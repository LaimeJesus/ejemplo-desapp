package util;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import exceptions.InvalidArgumentsException;
import exceptions.InvalidCategoryException;
import exceptions.InvalidDurationException;
import exceptions.InvalidMoneyException;
import model.Product;
import util.CSVProductParser;

public class CSVParserTest {
	
	@Test
	public void testParsingAnEmptyCSVFileReturnsAnEmptyList() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String emptyCSV = "";
		List<Product> products = csvparser.toListObject(csvparser.parse(emptyCSV));
		Assert.assertNotNull(products);
		Assert.assertEquals(0, products.size());
	}

	@Test
	public void testParsingACSVFileWith1ProductsReturnsAListWith1Products() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "1,nameOne,brandOne,stockOne,1.0,Meat,1,urlOne";
		List<Product> products = csvparser.toListObject(csvparser.parse(productCSV));
		Assert.assertNotNull(products);
		Assert.assertEquals(1, products.size());
	}

	
	@Test
	public void testParsingACSVFileWith5ProductsReturnsAListWith5Products() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "1,nameOne,brandOne,stockOne,1.0,Meat,1,urlOne\n"
				+ "2,nameTwo,brandTwo,stockTwo,2.0,Meat,2,urlTwo\n"
				+ "3,nameThree,brandThree,stockThree,3.0,Fruit,3,urlThree\n"
				+ "4,nameFour,brandFour,stockFour,4.0,Vegetable,4,urlFour\n"
				+ "5,nameFive,brandFive,stockFive,5.0,Drink,5,urlFive\n";
		List<Product> products = csvparser.toListObject(csvparser.parse(productCSV));
		Assert.assertNotNull(products);
		Assert.assertEquals(5, products.size());
	}
	
	
	@Test(expected=InvalidArgumentsException.class)
	public void testParsingACSVFileWithoutSomeValueThrowsAnException() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "nameOne,brandOne,stockOne,1.0";
		
		csvparser.toListObject(csvparser.parse(productCSV));
	}	

	@Test(expected=InvalidCategoryException.class)
	public void testParsingACSVFileContainingACategoryThatDoesnotExistsThrowsAnException() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "1,nameOne,brandOne,stockOne,0.0,Meal,1,urlOne";
		
		csvparser.toListObject(csvparser.parse(productCSV));
	}
	
	@Test(expected=InvalidMoneyException.class)
	public void testMoneyInvalidCaseOnlyIntegerPart() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "1,nameOne,brandOne,stockOne,1,Meat,1,urlOne";
		
		csvparser.toListObject(csvparser.parse(productCSV));
	}
	
	@Test(expected=InvalidMoneyException.class)
	public void testMoneyInvalidCaseOnlyDecimalPart() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "1,nameOne,brandOne,stockOne,.1,Meat,1,urlOne";
		
		csvparser.toListObject(csvparser.parse(productCSV));
	}
	
	@Test(expected=InvalidDurationException.class)
	public void testDurationInvalid() throws Exception{
		CSVProductParser csvparser = new CSVProductParser();
		String productCSV = "1,nameOne,brandOne,stockOne,1.1,Meat,e,urlOne";
		csvparser.toListObject(csvparser.parse(productCSV));
				
	}
	
}
