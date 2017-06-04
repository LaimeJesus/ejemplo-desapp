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
import exceptions.ProductListDoesNotExist;
import exceptions.UserAlreadyExistsException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import exceptions.UsernameOrPasswordInvalidException;
import model.products.Product;
import model.products.ProductList;
import model.products.SelectedProduct;
import model.registers.CashRegister;
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
		
		shopService.getCashRegisterManager().stop();
		
	}
	
	
	@Test
	public void testCanGetWaitingTime() throws UserIsNotLoggedException, UsernameDoesNotExistException, ProductListDoesNotExist, UserAlreadyExistsException, UsernameOrPasswordInvalidException{
		shopService.initialize(10);
		ProductList pl = new ProductList("prod1");
		User user = new User();
		user.setUsername("pepe");
		user.setPassword(new Password("pass"));
		user.setEmail("pepe@gmail");
		shopService.getUserService().createNewUser(user);
		shopService.getUserService().loginUser(user);
		shopService.getUserService().createProductList(user, pl);
		Duration expected = shopService.waitingTime(user, pl);
		Assert.assertEquals(new Duration(0), expected);
		
		shopService.getUserService().deleteAll();
		
	}
	
	@Test
	public void testUserCanBeReadyToShop() throws InvalidSelectedProduct, UserAlreadyExistsException, UserIsNotLoggedException, UsernameDoesNotExistException, ProductIsAlreadySelectedException, ProductDoesNotExistException, UsernameOrPasswordInvalidException{
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
		
		user = shopService.getUserService().findByUsername("username");
		productList = user.getProfile().getList("productList");
		
		shopService.ready(user, productList);
				
		List<PurchaseRecord> purchases = shopService.getUserService().getPurchaseRecords(user);
		
		Assert.assertFalse(purchases.isEmpty());
		Assert.assertEquals(1, purchases.size());
		
		Assert.assertEquals(new Integer(20), shopService.getProductService().getByExample(product).getStock());
		
		shopService.getUserService().deleteAll();
		shopService.getProductService().deleteAll();
		shopService.getCashRegisterManager().stop();
		
	}
	
	@Test(expected=InvalidSelectedProduct.class)
	public void testReadyToPayAProductListWithASelectedProductWithStockGreaterThanItsProductThrowsAnException() throws UserAlreadyExistsException, InvalidSelectedProduct, UserIsNotLoggedException, UsernameDoesNotExistException, UsernameOrPasswordInvalidException{
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
		
		shopService.getCashRegisterManager().stop();
	}
	
	@Test
	public void testUsersAreQueuingAfterCallingReady() throws InvalidSelectedProduct, UserIsNotLoggedException, UsernameDoesNotExistException, ProductIsAlreadySelectedException, ProductDoesNotExistException, UsernameOrPasswordInvalidException, UserAlreadyExistsException{
		shopService.initialize(1);		
		
		Product product = new Product();
		product.setName("cafe");
		product.setBrand("dolca");
		product.setStock(30);
		product.setPrice(new Money(10,0));
		product.setCategory(Category.Drink);
		product.setProcessingTime(new Duration(1000L));
		
		shopService.getProductService().save(product);
		
		User userOne = new User();
		userOne.setUsername("usernameOne");
		userOne.setPassword(new Password("usernameOne"));
		userOne.setEmail("usernameOne");
		ProductList productListOne = new ProductList();
		productListOne.setName("productListOne");
		
		userOne.getProfile().addNewProductList(productListOne);
		shopService.getUserService().createNewUser(userOne);
		shopService.getUserService().loginUser(userOne);

		User userTwo = new User();
		userTwo.setUsername("usernameTwo");
		userTwo.setPassword(new Password("usernameTwo"));
		userTwo.setEmail("username"); 
		ProductList productListTwo = new ProductList();
		productListTwo.setName("productListTwo");

		userTwo.getProfile().addNewProductList(productListTwo);
		shopService.getUserService().createNewUser(userTwo);
		shopService.getUserService().loginUser(userTwo);

		productListService.selectProduct(userOne, productListOne, product, 5);
		productListService.selectProduct(userTwo, productListTwo, product, 5);
		
		CashRegister cr = shopService.getCashRegisterManager().getRegisters().get(0);
		
		userOne = shopService.getUserService().findByUsername("usernameOne");
		productListOne = userOne.getProfile().getList("productListOne");
		userTwo = shopService.getUserService().findByUsername("usernameTwo");
		productListTwo = userTwo.getProfile().getList("productListTwo");
		shopService.ready(userOne, productListOne);
		Assert.assertEquals(new Duration(5000L), cr.getWaitingTime());
		Assert.assertEquals(1, cr.size());
		shopService.ready(userTwo, productListTwo);
		Assert.assertEquals(2, cr.size());
		Assert.assertEquals(new Duration(10000L), cr.getWaitingTime());		
		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(0, cr.size());
		Assert.assertEquals(new Integer(20), shopService.getProductService().getByExample(product).getStock());
		cr.stop();
	}
}
