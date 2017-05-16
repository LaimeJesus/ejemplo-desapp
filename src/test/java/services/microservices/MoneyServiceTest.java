package services.microservices;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import builders.ProductBuilder;
import builders.UserBuilder;
import exceptions.ProductDoesNotExistException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import model.products.Product;
import model.products.ProductList;
import model.users.User;
import services.general.GeneralOfferService;
import services.general.GeneralService;
import services.microservices.MoneyService;
import services.microservices.ProductListService;
import services.microservices.ProductService;
import services.microservices.UserService;
import util.Category;
import util.Money;
import util.Password;
import util.Permission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class MoneyServiceTest {

	
	@Autowired
    @Qualifier("services.moneyservice")
    private MoneyService moneyService;
	
	@Autowired
    @Qualifier("services.microservices.productservice")
    private ProductService productService;
	
	@Autowired
    @Qualifier("services.microservices.productlistservice")
    private ProductListService productListService;
	
	@Autowired
    @Qualifier("services.microservices.userservice")
    private UserService userService;
	
	@Autowired
    @Qualifier("services.general.generalofferservice")
    private GeneralOfferService generalOfferService;
	
	@Autowired
	private GeneralService generalService;
	
	@Before
	public void setUp() throws Exception {
		moneyService.deleteAll();
		productService.deleteAll();
		generalOfferService.deleteAll();
		userService.deleteAll();
	}
	
	@Test
	public void testWhenWorkingWithListsMoneysArenPersisted() throws UserAlreadyExistsException, ProductIsAlreadySelectedException, ProductDoesNotExistException, UsernameDoesNotExistException, UserIsNotLoggedException {
		
		Integer expected = moneyService.retriveAll().size();
		
		Product p1 = new ProductBuilder()
			.withName("Arroz")
			.withBrand("Marolio")
			.withStock(10)
			.withCategory(Category.Baked)
			.withPrice(new Money(3,0))
			.build();
		
		Product p2 = new ProductBuilder()
				.withName("Arroz")
				.withBrand("Arroz")
				.withStock(10)
				.withCategory(Category.Baked)
				.withPrice(new Money(3,0))
				.build();
		
		Product p3 = new ProductBuilder()
				.withName("Arroz")
				.withBrand("Dia")
				.withStock(10)
				.withCategory(Category.Baked)
				.withPrice(new Money(3,0))
				.build();
		
		productService.save(p1);
		productService.save(p2);
		productService.save(p3);
		
		ProductList pl = new ProductList("pl");
		User valid = new UserBuilder()
			.withUsername("sandi")
			.withEmail("arroba")
			.withPassword(new Password("qweqwe"))
			.withUserPermission(Permission.NORMAL)
			.build();
		generalService.createUser(valid);
		generalService.loginUser(valid);
		generalService.createProductList(valid, pl);
		generalService.selectProduct(valid, pl, p2, 7);
		ProductList validPostPL = productListService.getByUser(pl, valid);
		
		Integer current = moneyService.retriveAll().size();		
		Assert.assertEquals(new Integer(expected+3) , current);
		Assert.assertEquals(1, validPostPL.getAllProducts().size());
		Assert.assertEquals(0, validPostPL.getAppliedOffers().size());
		
		Money currentMoney = validPostPL.getTotalAmount();		
		Assert.assertEquals(new Money(21,0), currentMoney);
		
		generalService.getUserService().deleteAll();
		generalService.getProductService().deleteAll();
	}

}
