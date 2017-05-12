package services.general;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import java.util.List;

import org.junit.After;
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
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
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
    public void testWhenCreatingNewUserThenUserServiceCanRetriveIt() throws UserAlreadyExistsException{
		User userToSignUp = new UserBuilder()
				.withUsername("sandoval.lucasj")
				.withEmail("sandoval.lucasj@gmail.com")
				.withPassword(new Password("estaesmipass"))
				.withUserPermission(Permission.NORMAL)
				.build();
		Integer expected = userService.retriveAll().size();
		generalService.createUser(userToSignUp);
		
		Assert.assertEquals(expected+1 , userService.retriveAll().size());
		userService.delete(userToSignUp);
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
    }
	
	@Test
    public void testWhenAAdminUserCreatesAnOfferThenEverythinIsOkay() throws UsernameDoesNotExistException, WrongUserPermissionException, UserIsNotLoggedException, UserAlreadyExistsException{
		User user = new UserBuilder()
				.withUsername("sandoval.lucasj")
				.withEmail("sandoval.lucasj@gmail.com")
				.withPassword(new Password("estaesmipass"))
				.withUserPermission(Permission.ADMIN)
				.build();
		generalService.createUser(user);
		generalService.loginUser(user);
		generalService.createOffer(new CombinationOffer(), user);
		Assert.assertEquals(1, generalOfferService.retriveAll().size());
		
		generalService.getUserService().delete(user);
		generalOfferService.deleteAll();
    }
	
	@Test
	public void testWhenISaveANewProductThenEverythingIsOkay() {
		
		Integer expected = productService.count();
		
		Product productToSave = new ProductBuilder()
				.build();
		
		generalService.addProduct(productToSave);
		Integer current = productService.count();
		
		Assert.assertEquals(new Integer(expected+1), current);
		
		generalService.getProductService().deleteAll();
		
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
		
		generalService.getProductService().deleteAll();
	}
	
	@Test
	public void testWhenICreateAProductListThenEverythingIsOkay() throws UsernameDoesNotExistException, UserIsNotLoggedException, UserAlreadyExistsException {
		
		ProductList someProductList = new ProductList("First");
		
		Profile someProfile = new Profile();
		someProfile.addNewProductList(someProductList);
		User someValidUser = new UserBuilder()
			.withUsername("someUser")
			.withEmail("Sandoval.lucasj@gamail.com")
			.withPassword(new Password("asd"))
			.build();
		
		someValidUser.setProfile(someProfile);
		
		Integer expected = productListService.count();
		
		generalService.createUser(someValidUser);
		ProductList second = new ProductList("Second");
		generalService.loginUser(someValidUser);
		generalService.createProductList(someValidUser,second);
		
		
		Assert.assertEquals(new Integer(expected+2), productListService.count());
		
		ProductList validFirst = productListService.getByUser(someProductList, someValidUser);
		ProductList validSecond = productListService.getByUser(second, someValidUser);
		
		Assert.assertNotNull(validFirst.getId());
		Assert.assertNotNull(validSecond.getId());
		
		ProductList anotherProductList = new ProductList("Second");
		generalService.loginUser(someValidUser);
		generalService.createProductList(someValidUser, anotherProductList);
		User saved = userService.findByUsername("someUser");
		Assert.assertTrue(userService.getListsFromUser(saved).contains(anotherProductList));
	}
	
	public void testWhenISelectAProductFromAListThatExistThenEverythingIsOkay() throws UserAlreadyExistsException, UsernameOrPasswordInvalidException, MoneyCannotSubstractException, UsernameDoesNotExistException {
		
		ProductList someProductList = new ProductList("First");
		
		Profile someProfile = new Profile();
		someProfile.addNewProductList(someProductList);
		
		User somaValidUser = new UserBuilder()
			.withUsername("someUser")
			.withEmail("Sandoval.lucasj@gamail.com")
			.withPassword(new Password("asd"))
			.build();
		
		somaValidUser.setProfile(someProfile);
		
		DateTime today = DateTime.now();
		Interval anInterval = new Interval(today , today.plusDays(1));
		
		generalService.createUser(somaValidUser);
		generalService.loginUser(somaValidUser);
		ProductList valid = productListService.getByUser(someProductList, somaValidUser);
		Money currentAmount = valid.getTotalAmount();
		
		CategoryOffer aValidCategoryOffer = new CategoryOffer(10, anInterval, Category.Baked);
		
		generalService.applyOffer(somaValidUser, aValidCategoryOffer, someProductList);
		valid = productListService.getByUser(someProductList, somaValidUser);
		
		Assert.assertEquals(currentAmount , valid.getTotalAmount());	
	}
	
	@Test
	public void testWhenSelectingAProductThenMyTotalAmountIsUpdated() throws UserAlreadyExistsException, UsernameOrPasswordInvalidException, MoneyCannotSubstractException, ProductIsAlreadySelectedException, ProductDoesNotExistException, UsernameDoesNotExistException, UserIsNotLoggedException {
		
		User someValidUser = new UserBuilder()
			.withUsername("someUser")
			.withEmail("sandoval.lucasj@gmail.com")
			.withPassword(new Password("asd"))
			.build();
		
		Product validProduct = new ProductBuilder()
			.withName("asdfgh")
			.withBrand("asdfgh")
			.withStock(35)
			.withPrice(new Money(3,50))
			.withCategory(Category.Baked)
			.withProcessingTime(new Duration(1L))
			.build();
		
		ProductList someProductList = new ProductList("First");		

		productService.save(validProduct);
		
		generalService.createUser(someValidUser);
		generalService.loginUser(someValidUser);
		generalService.createProductList(someValidUser, someProductList);		
		generalService.selectProduct(someValidUser, someProductList, validProduct, 10);
		someValidUser = generalService.getUserService().findByUsername("someUser");
		
		ProductList validAfterPersist = productListService.getByUser(someProductList, someValidUser);
		
		Assert.assertEquals("sandoval.lucasj@gmail.com", someValidUser.getEmail());
		Assert.assertEquals(1, someValidUser.getProfile().getAllProductList().size());
		Assert.assertEquals(new Money(35,0), validAfterPersist.getTotalAmount());
		
		generalService.getUserService().delete(someValidUser);
		generalService.getProductService().deleteAll();
		
	}

	@Test
	public void testWhenApplyAnApplicableOfferThenMyTotalAmountIsUpdated() throws UserAlreadyExistsException, UsernameOrPasswordInvalidException, MoneyCannotSubstractException, ProductIsAlreadySelectedException, ProductDoesNotExistException, UsernameDoesNotExistException, UserIsNotLoggedException {
		
		ProductList someProductList = new ProductList("First");
		
		Profile someProfile = new Profile();
		someProfile.addNewProductList(someProductList);
		
		User someValidUser = new UserBuilder()
			.withUsername("someUser")
			.withEmail("sandoval.lucasj@gmail.com")
			.withPassword(new Password("mypassword"))
			.build();
		
		someValidUser.setProfile(someProfile);
		
		Product someValidProduct = new ProductBuilder()
			.withBrand("Marolio")
			.withName("Arroz")
			.withPrice(new Money(13,50))
			.build();
		
		userService.save(someValidUser);
		productService.save(someValidProduct);
		userService.loginUser(someValidUser);
		Integer expected = selectedProductService.count();
				
		User saved = userService.findByUsername("someUser");
		generalService.selectProduct(someValidUser, someProductList, someValidProduct, 3);
		List<ProductList> lists = userService.getListsFromUser(saved);
		
		Assert.assertTrue(lists.contains(someProductList));
		
		Assert.assertEquals( new Integer(expected+1) , selectedProductService.count());

		Assert.assertTrue(true);
		
		generalService.getUserService().deleteAll();
		generalService.getProductService().deleteAll();
		generalService.getGeneralOfferService().deleteAll();
		
	}
	
	
	@After
	public void after(){
		generalService.getUserService().deleteAll();
	}
	
}
