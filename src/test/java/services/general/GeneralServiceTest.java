package services.general;

import static org.junit.Assert.fail;

import org.joda.time.DateTime;
import org.joda.time.Interval;
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
import exceptions.MoneyCannotSubstractException;
import exceptions.ProductDoesNotExistException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.UserAlreadyExistsException;
import exceptions.UsernameOrPasswordInvalidException;
import exceptions.WrongUserPermissionException;
import model.offers.CategoryOffer;
import model.offers.CombinationOffer;
import model.products.Product;
import model.products.ProductList;
import model.users.Profile;
import model.users.User;
import services.microservices.ProductListService;
import services.microservices.ProductService;
import services.microservices.SelectedProductService;
import services.microservices.UserService;
import util.Category;
import util.Money;
import util.Password;
import util.Permission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class GeneralServiceTest {

	
	
	@Autowired
    @Qualifier("services.general.generalservice")
    private GeneralService generalService;
	
	@Autowired
    @Qualifier("services.microservices.userservice")
    private UserService userService;

	@Autowired
    @Qualifier("services.general.generalofferservice")
    private GeneralOfferService generalOfferService;

	@Autowired
    @Qualifier("services.microservices.productservice")
    private ProductService productService;
	
	@Autowired
    @Qualifier("services.microservices.productlistservice")
    private ProductListService productListService;
	
	@Autowired
    @Qualifier("services.microservices.selectedproductservice")
    private SelectedProductService selectedProductService;
	
	@Before
	public void setUp() {
		generalOfferService.deleteAll();
		userService.deleteAll();
		productService.deleteAll();
		productListService.deleteAll();
		selectedProductService.deleteAll();
	}
	
	@Test
    public void testWhenCreatingNewUserThenUserServiceCanRetriveIt(){
		User userToSignUp = new UserBuilder()
				.withUsername("sandoval.lucasj")
				.withEmail("sandoval.lucasj@gmail.com")
				.withPassword(new Password("estaesmipass"))
				.withUserPermission(Permission.NORMAL)
				.build();
		
		try {
			Integer expected = userService.retriveAll().size();
			generalService.createUser(userToSignUp);
			
			Assert.assertEquals(expected+1 , userService.retriveAll().size());
			userService.delete(userToSignUp);
		} catch (UserAlreadyExistsException e) {
			fail();
		}
    }
	
	@Test (expected = UserAlreadyExistsException.class)
    public void testWhenCreatingNewUserThatExistThenUserServiceThrowsException() throws UserAlreadyExistsException{
		User userToSignUp = new UserBuilder()
				.withUsername("sandoval.lucasj")
				.withEmail("sandoval.lucasj@gmail.com")
				.withPassword(new Password("estaesmipass"))
				.withUserPermission(Permission.NORMAL)
				.build();
		User anotherUserToSignUp = new UserBuilder()
				.withUsername("sandoval.lucasj")
				.withEmail("otrosandoval@gmail.com")
				.withPassword(new Password("miotrapass"))
				.withUserPermission(Permission.NORMAL)
				.build();
		
		generalService.createUser(userToSignUp);
		generalService.createUser(anotherUserToSignUp);
		
		userService.delete(userToSignUp);
    }
	
	@Test
    public void testWhenAAdminUserCreatesAnOfferThenEverythinIsOkay(){
		User userToSignUp = new UserBuilder()
				.withUsername("sandoval.lucasj2")
				.withEmail("sandoval.lucasj@gmail.com")
				.withPassword(new Password("estaesmipass"))
				.withUserPermission(Permission.ADMIN)
				.build();
		Integer expected = generalOfferService.retriveAll().size();
		try {
			generalService.createUser(userToSignUp);
			generalService.createOffer(new CombinationOffer(), userToSignUp);
			Assert.assertEquals(expected+1, generalOfferService.retriveAll().size());
		} catch (UsernameOrPasswordInvalidException | WrongUserPermissionException | UserAlreadyExistsException e) {
			e.printStackTrace();
			fail();
		}
    }
	
	@Test
	public void testWhenISaveANewProductThenEverythingIsOkay() {
		
		Integer expected = productService.count();
		
		Product productToSave = new ProductBuilder()
				.build();
		
		generalService.addProduct(productToSave);
		Integer current = productService.count();
		
		Assert.assertEquals(new Integer(expected+1), current);
	}
	
	@Test
	public void testWhenIUpdateANewProductThenTheNumberOfProductsIsNotModified() {
		
		
		Product productToSave = new ProductBuilder()
				.build();
		
		generalService.addProduct(productToSave);
		Integer expected = productService.count();
		productToSave.setCategory(Category.Dairy);
		generalService.updateProduct(productToSave);
		
		Integer current = productService.count();
		
		Assert.assertEquals(expected, current);
	}
	
	@Test
	public void testWhenICreateAProductListThenEverythingIsOkay() throws UserAlreadyExistsException, UsernameOrPasswordInvalidException {
		
		ProductList someProductList = new ProductList("First");
		
		Profile someProfile = new Profile();
		someProfile.addNewProductList(someProductList);
		
		User somaValidUser = new UserBuilder()
			.withUsername("someUser")
			.withEmail("Sandoval.lucasj@gamail.com")
			.withPassword(new Password("asd"))
			.build();
		
		somaValidUser.setProfile(someProfile);
		
		Integer expected = productListService.count();
		
		generalService.createUser(somaValidUser);
		
		ProductList second = new ProductList("Second");
		
		generalService.createNewProductList(somaValidUser,second);
		
		
		Assert.assertEquals(new Integer(expected+2), productListService.count());
		
		ProductList validFirst = productListService.getByUser(someProductList, somaValidUser);
		ProductList validSecond = productListService.getByUser(second, somaValidUser);
		
		Assert.assertNotNull(validFirst.getId());
		Assert.assertNotNull(validSecond.getId());
		
	}
	
	@Test
	public void test1() throws UserAlreadyExistsException, UsernameOrPasswordInvalidException, MoneyCannotSubstractException {
		
		ProductList someProductList = new ProductList("First");
		
		Profile someProfile = new Profile();
		someProfile.addNewProductList(someProductList);
		
		User somaValidUser = new UserBuilder()
			.withUsername("someUser")
			.withEmail("Sandoval.lucasj@gamail.com")
			.withPassword(new Password("asd"))
			.build();
		
		somaValidUser.setProfile(someProfile);
		
		Integer expected = productListService.count();
		
		DateTime today = DateTime.now();
		Interval anInterval = new Interval(today , today.plusDays(1));
		
		generalService.createUser(somaValidUser);
		ProductList valid = productListService.getByUser(someProductList, somaValidUser);
		Money currentAmount = valid.getTotalAmount();
		
		CategoryOffer aValidCategoryOffer = new CategoryOffer(10, anInterval, Category.Baked);
		
		generalService.applyOffer(somaValidUser, aValidCategoryOffer, someProductList);
		valid = productListService.getByUser(someProductList, somaValidUser);
		
		Assert.assertEquals(currentAmount , valid.getTotalAmount());
		
	}
	
	@Test
	public void test2() throws UserAlreadyExistsException, UsernameOrPasswordInvalidException, MoneyCannotSubstractException, ProductIsAlreadySelectedException, ProductDoesNotExistException {
		
		User someValidUser = new UserBuilder()
			.withUsername("someUser")
			.withEmail("sandoval.lucasj@gmail.com")
			.withPassword(new Password("asd"))
			.build();
		
		Product validProduct = new ProductBuilder()
			.withName("Arroz")
			.withBrand("Marolio")
			.withPrice(new Money(3,50))
			.withCategory(Category.Baked)
			.withStock(35)
			.build();
		
		ProductList someProductList = new ProductList("First");		

		
		//Product e = productService.getByExample(validProduct);
		
		generalService.createUser(someValidUser);
		productService.save(validProduct);
		
		generalService.createNewProductList(someValidUser, someProductList);
		User us = generalService.getUserService().findByUsername("someUser");
		
		ProductList valid = productListService.getByUser(someProductList, someValidUser);
		System.out.println("Antes "+ valid.getTotalAmount());
		Money result = generalService.selectProduct(someValidUser, someProductList, validProduct, 10);
		
		ProductList validAfterPersist = productListService.getByUser(someProductList, someValidUser);
		
		Assert.assertEquals("sandoval.lucasj@gmail.com", us.getEmail());
		Assert.assertEquals(1, us.getProfile().getAllProductList().size());
		Assert.assertEquals(valid.getId(), validAfterPersist.getId());
		Assert.assertEquals(new Money(35,0), result);
		//Assert.assertEquals(new Money(10,0),e.getPrice());
		//Assert.assertEquals(new Money(0,0) , valid.getTotalAmount());
	}
	
	@Test
	public void test5() throws UserAlreadyExistsException, UsernameOrPasswordInvalidException{
		
		userService.deleteAll();
		generalOfferService.deleteAll();
		productListService.deleteAll();
		productService.deleteAll();
		selectedProductService.deleteAll();
		
		ProductList pl1 = new ProductList("1");

		User user = new UserBuilder()
				.withUsername("user")
				.withEmail("user@gmail.com")
				.withPassword(new Password("user"))
				.build();
		
		Product p1 = new Product();
		p1.setName("p1");
		p1.setBrand("p1");
		p1.setPrice(new Money(10,0));
		p1.setCategory(Category.Baked);
		p1.setStock(35);

		generalService.createUser(user);
		generalService.createNewProductList(user, pl1);
		productService.save(p1);
		
		Product p2 = new Product();
		p2.setName("p2");
		p2.setBrand("Marolio");
		p2.setPrice(new Money(20,0));
		p2.setCategory(Category.Baked);
		p2.setStock(35);

		ProductList pl2 = new ProductList("2");
		generalService.createNewProductList(user, pl2);
		Assert.assertEquals(new Money(0,0), productListService.getByUser(pl2, user).getTotalAmount());
	}
	
	
	
	
	
}
