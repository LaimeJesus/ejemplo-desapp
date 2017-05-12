package services.microservices;

import java.util.List;

import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import exceptions.InvalidSelectedProduct;
import exceptions.ProductDoesNotExistException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import model.products.Product;
import model.products.ProductList;
import model.products.SelectedProduct;
import model.registers.PurchaseRecord;
import model.users.User;
import util.Category;
import util.Money;
import util.Password;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/META-INF/spring-persistence-context.xml", "/META-INF/spring-services-context.xml" })
public class ShopServiceTest {

	@Autowired
	@Qualifier("services.microservices.shopservice")
	public ShopService shopService;
	@Autowired
	@Qualifier("services.microservices.productlistservice")
	public ProductListService productListService;
	
	
	@Before
	public void setUp(){
		shopService.getUserService().deleteAll();
		shopService.getProductService().deleteAll();
	}
	
	@Test
	public void testCreateRegisters(){
		shopService.initialize(10);
		
		Assert.assertEquals(10, shopService.getCashRegisterManager().getRegisters().size());	
	}
	
	
	@Test
	public void testCanGetWaitingTime(){
		shopService.initialize(10);
		ProductList pl = new ProductList();
		
		Assert.assertEquals(new Duration(0), shopService.waitingTime(pl));
	}
	
	@Test
	public void testUserCanBeReadyToShop() throws InvalidSelectedProduct, UserAlreadyExistsException, UserIsNotLoggedException, UsernameDoesNotExistException, ProductIsAlreadySelectedException, ProductDoesNotExistException{
		shopService.initialize(5);		
				
		Product product = new Product();
		product.setName("cafe");
		product.setBrand("dolca");
		product.setStock(30);
		product.setPrice(new Money(10,0));
		product.setCategory(Category.Drink);
		product.setProcessingTime(new Duration(500L));
		
		shopService.getProductService().save(product);
		User user = new User();
		user.setUsername("username");
		user.setPassword(new Password("username"));
		user.setEmail("username");
		ProductList productList = new ProductList();
		productList.setName("productList");
		
		user.getProfile().addNewProductList(productList);

		shopService.getUserService().createNewUser(user);
		shopService.getUserService().loginUser(user);
		productListService.selectProduct(user, productList, product, 10);
		
		Product pl = shopService.getProductService().getByExample(product);		

		shopService.ready(user, productList);
				
		List<PurchaseRecord> purchases = shopService.getUserService().getPurchaseRecords(user);
		
		Assert.assertFalse(purchases.isEmpty());
		Assert.assertEquals(1, purchases.size());
		
		Assert.assertEquals(new Integer(20), shopService.getProductService().getByExample(product).getStock());
		
		
		shopService.getUserService().deleteAll();
		shopService.getProductService().deleteAll();
		
	}
	
	@Test(expected=InvalidSelectedProduct.class)
	public void testReadyToPayAProductListWithASelectedProductWithStockGreaterThanItsProductThrowsAnException() throws UserAlreadyExistsException, InvalidSelectedProduct, UserIsNotLoggedException, UsernameDoesNotExistException{
		shopService.getUserService().deleteAll();
		shopService.getProductService().deleteAll();
		
		shopService.initialize(5);		
		
		SelectedProduct sp = new SelectedProduct();		
		Product p = new Product();
		p.setName("cafe");
		p.setBrand("dolca");
		p.setStock(5);
		p.setPrice(new Money(10,0));
		p.setCategory(Category.Drink);
		p.setProcessingTime(new Duration(1000L));
		
		shopService.getProductService().save(p);
		
		sp.setProduct(p);
		sp.setQuantity(10);
		
		User u = new User();
		u.setUsername("username");
		u.setPassword(new Password("username"));
		u.setEmail("username");
		ProductList pl = new ProductList();
		pl.setName("productList");
		pl.allProducts.add(sp);
		u.getProfile().getAllProductList().add(pl);

		shopService.getUserService().createNewUser(u);
		shopService.getUserService().loginUser(u);
		
		shopService.ready(u, pl);
		shopService.getUserService().deleteAll();
		shopService.getProductService().deleteAll();
	}
	
	
}
