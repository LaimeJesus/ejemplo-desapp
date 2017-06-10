package rest.dummys;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;

import builders.ProductBuilder;
import model.products.Product;
import util.Category;
import util.Money;

public class DummyProduct {

	public List<Product> example(){
		ArrayList<Product> res = new ArrayList<Product>();
		Product one = new ProductBuilder()
				.withName("Arroz")
				.withBrand("Marolio")
				.withPrice(new Money(13,50))
				.withCategory(Category.Baked)
				.withStock(2000)
				.withProcessingTime(new Duration(200L))
				.build();
		one.setImageUrl("https://image.ibb.co/mB7ELv/arroz_marolio.jpg");
		Product two = new ProductBuilder()
				.withName("Lechuga")
				.withBrand("La criollita")
				.withPrice(new Money(11,70))
				.withCategory(Category.Dairy)
				.withStock(2000)
				.withProcessingTime(new Duration(200L))
				.build();
		two.setImageUrl("https://image.ibb.co/eYyM0v/lechuga.jpg");
		Product three = new ProductBuilder()
				.withName("Leche")
				.withBrand("La serenisima")
				.withPrice(new Money(11,70))
				.withCategory(Category.Dairy)
				.withStock(2000)
				.withProcessingTime(new Duration(200L))
				.build();
		three.setImageUrl("https://image.ibb.co/cKU5Sa/leche_laserenisima.jpg");
		Product four = new ProductBuilder()
				.withName("Pan")
				.withBrand("Bimbo")
				.withPrice(new Money(28,50))
				.withCategory(Category.Baked)
				.withStock(2000)
				.withProcessingTime(new Duration(200L))
				.build();
		four.setImageUrl("https://image.ibb.co/eSozna/pan_bimbo.jpg");
		Product five = new ProductBuilder()
				.withName("Bondiola")
				.withBrand("Paladini")
				.withPrice(new Money(65,39))
				.withCategory(Category.Meat)
				.withStock(2000)
				.withProcessingTime(new Duration(300L))
				.build();
		five.setImageUrl("https://image.ibb.co/hnP5Sa/bondiola_paladini.jpg");
		Product six = new ProductBuilder()
				.withName("Lechuga")
				.withBrand("La buena nueva")
				.withPrice(new Money(64,50))
				.withCategory(Category.Vegetable)
				.withStock(2000)
				.withProcessingTime(new Duration(200L))
				.build();
		six.setImageUrl("https://image.ibb.co/eYyM0v/lechuga.jpg");
		Product seven = new ProductBuilder()
				.withName("Manzana")
				.withBrand("La mendocina")
				.withPrice(new Money(24,00))
				.withCategory(Category.Fruit)
				.withStock(2000)
				.withProcessingTime(new Duration(200L))
				.build();
		seven.setImageUrl("https://image.ibb.co/cshQSa/manzana.jpg");
		Product eight = new ProductBuilder()
				.withName("Arroz")
				.withBrand("Molto")
				.withPrice(new Money(15,30))
				.withCategory(Category.Baked)
				.withStock(2000)
				.withProcessingTime(new Duration(200L))
				.build();
		eight.setImageUrl("https://image.ibb.co/eyF10v/arroz_molto.jpg");
		Product nine = new ProductBuilder()
				.withName("Leche")
				.withBrand("Sancor")
				.withPrice(new Money(21,56))
				.withCategory(Category.Dairy)
				.withStock(2000)
				.withProcessingTime(new Duration(200L))
				.build();
		nine.setImageUrl("https://image.ibb.co/juYofv/leche_sancor.jpg");
		Product ten = new ProductBuilder()
				.withName("Jabon")
				.withBrand("Ariel")
				.withPrice(new Money(35,60))
				.withCategory(Category.Perfumery)
				.withStock(2000)
				.withProcessingTime(new Duration(300L))
				.build();
		res.add(one);
		res.add(two);
		res.add(three);
		res.add(four);
		res.add(five);
		res.add(six);
		res.add(seven);
		res.add(eight);
		res.add(nine);
		res.add(ten);
		
		return res;

	}

}
