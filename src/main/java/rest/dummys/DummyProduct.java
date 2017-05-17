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
				.withStock(350)
				.withProcessingTime(new Duration(2L))
				.build();
		Product two = new ProductBuilder()
				.withName("Leche")
				.withBrand("La serenisima")
				.withPrice(new Money(11,70))
				.withCategory(Category.Dairy)
				.withStock(500)
				.withProcessingTime(new Duration(2L))
				.build();

		Product three = new ProductBuilder()
				.withName("Leche")
				.withBrand("La serenisima")
				.withPrice(new Money(11,70))
				.withCategory(Category.Dairy)
				.withStock(500)
				.withProcessingTime(new Duration(2L))
				.build();

		Product four = new ProductBuilder()
				.withName("Pan")
				.withBrand("Bimbo")
				.withPrice(new Money(28,50))
				.withCategory(Category.Baked)
				.withStock(400)
				.withProcessingTime(new Duration(2L))
				.build();

		Product five = new ProductBuilder()
				.withName("Bondiola")
				.withBrand("Paladini")
				.withPrice(new Money(65,39))
				.withCategory(Category.Meat)
				.withStock(20)
				.withProcessingTime(new Duration(3L))
				.build();

		Product six = new ProductBuilder()
				.withName("Lechuga")
				.withBrand("La buena nueva")
				.withPrice(new Money(64,50))
				.withCategory(Category.Vegetable)
				.withStock(500)
				.withProcessingTime(new Duration(2L))
				.build();

		Product seven = new ProductBuilder()
				.withName("Manzana")
				.withBrand("La mendocina")
				.withPrice(new Money(24,00))
				.withCategory(Category.Fruit)
				.withStock(600)
				.withProcessingTime(new Duration(2L))
				.build();

		Product eight = new ProductBuilder()
				.withName("Arroz")
				.withBrand("Molto")
				.withPrice(new Money(15,30))
				.withCategory(Category.Baked)
				.withStock(370)
				.withProcessingTime(new Duration(2L))
				.build();

		Product nine = new ProductBuilder()
				.withName("Leche")
				.withBrand("Sancor")
				.withPrice(new Money(21,56))
				.withCategory(Category.Dairy)
				.withStock(500)
				.withProcessingTime(new Duration(2L))
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

		return res;

	}

}
