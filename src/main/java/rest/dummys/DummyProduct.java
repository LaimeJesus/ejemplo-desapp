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
				.withName("Manzana Verde")
				.withBrand("A lo loco")
				.withPrice(new Money(35,0))
				.withCategory(Category.Fruit)
				.withStock(2000)
				.withProcessingTime(new Duration(300L))
				.build();
		one.setImageUrl("http://image.ibb.co/hJyyav/manzanas_verde_sinmarca.jpg");
		Product two = new ProductBuilder()
				.withName("Manzana Roja")
				.withBrand("A lo loco")
				.withPrice(new Money(30,0))
				.withCategory(Category.Fruit)
				.withStock(2000)
				.withProcessingTime(new Duration(300L))
				.build();
		two.setImageUrl("http://image.ibb.co/btY9ha/manzanas_sinmarca.jpg");
		Product three = new ProductBuilder()
				.withName("Naranja")
				.withBrand("A lo loco")
				.withPrice(new Money(20,0))
				.withCategory(Category.Fruit)
				.withStock(2000)
				.withProcessingTime(new Duration(300L))
				.build();
		three.setImageUrl("http://image.ibb.co/mEm6TF/nja.jpg");
		Product four = new ProductBuilder()
				.withName("Queso Mozzarella")
				.withBrand("Los Altos")
				.withPrice(new Money(40,0))
				.withCategory(Category.Dairy)
				.withStock(2000)
				.withProcessingTime(new Duration(100L))
				.build();
		four.setImageUrl("http://image.ibb.co/c0XfoF/queso_mozarella_los_altos.png");
		Product five = new ProductBuilder()
				.withName("Leche")
				.withBrand("La Serenisima")
				.withPrice(new Money(50,0))
				.withCategory(Category.Dairy)
				.withStock(2000)
				.withProcessingTime(new Duration(200L))
				.build();
		five.setImageUrl("http://image.ibb.co/c70VNa/3145_G_1410572595547.jpg");
		Product six = new ProductBuilder()
				.withName("Yogur")
				.withBrand("Activia")
				.withPrice(new Money(50,0))
				.withCategory(Category.Dairy)
				.withStock(2000)
				.withProcessingTime(new Duration(100L))
				.build();
		six.setImageUrl("http://image.ibb.co/jLZwvv/g02126a.png");
		Product seven = new ProductBuilder()
				.withName("Yogur")
				.withBrand("Actimel")
				.withPrice(new Money(30,0))
				.withCategory(Category.Dairy)
				.withStock(2000)
				.withProcessingTime(new Duration(100L))
				.build();
		seven.setImageUrl("http://image.ibb.co/c9UeFv/767c58f237a9289ae3e4a6c00b52d7bd_340_340_0_min_wmark_4ece3026.jpg");
		Product eight = new ProductBuilder()
				.withName("Hamburguesa")
				.withBrand("Paty")
				.withPrice(new Money(60,0))
				.withCategory(Category.Meat)
				.withStock(2000)
				.withProcessingTime(new Duration(300L))
				.build();
		eight.setImageUrl("http://image.ibb.co/eByH2a/hamburguesa_paty1.jpg");
		Product nine = new ProductBuilder()
				.withName("Yogur")
				.withBrand("Yogurisimo")
				.withPrice(new Money(35,50))
				.withCategory(Category.Dairy)
				.withStock(2000)
				.withProcessingTime(new Duration(200L))
				.build();
		nine.setImageUrl("http://image.ibb.co/hTqVNa/2957_G_1410282653523.jpg");
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
