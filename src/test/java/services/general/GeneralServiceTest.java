package services.general;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import exceptions.OfferIsAlreadyCreatedException;
import exceptions.ProductDoesNotExistException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import exceptions.UsernameOrPasswordInvalidException;
import exceptions.WrongUserPermissionException;
import model.offers.CategoryOffer;
import model.offers.CombinationOffer;
import model.offers.Offer;
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

	@Test (expected = UsernameOrPasswordInvalidException.class)
	public void testWhenAUserTriesToLogInWithIncorrectPasswordThenThrowsAnException() throws UserAlreadyExistsException, UsernameDoesNotExistException, UsernameOrPasswordInvalidException {
		User userToSignUp = new UserBuilder()
			.withUsername("sandoval.lucasj")
			.withEmail("sandoval.lucasj@gmail.com")
			.withPassword(new Password("estaesmipass"))
			.withUserPermission(Permission.NORMAL)
			.build();

		generalService.createUser(userToSignUp);

		userToSignUp.setPassword(new Password("otrapass"));
		generalService.loginUser(userToSignUp);
	}

	@Test (expected = UsernameDoesNotExistException.class)
	public void testWhenAUserThatIsNotRegisteredTriesToLoginThenThrowsAnException() throws UsernameDoesNotExistException, UsernameOrPasswordInvalidException {
		User userToSignUp = new UserBuilder()
			.withUsername("sandoval.lucasj")
			.withEmail("sandoval.lucasj@gmail.com")
			.withPassword(new Password("estaesmipass"))
			.withUserPermission(Permission.NORMAL)
			.build();

		generalService.loginUser(userToSignUp);
	}

	@Test
    public void testWhenAAdminUserCreatesAnOfferThenEverythinIsOkay() throws UsernameDoesNotExistException, WrongUserPermissionException, UserIsNotLoggedException, UserAlreadyExistsException, UsernameOrPasswordInvalidException, OfferIsAlreadyCreatedException{
		User user = new UserBuilder()
				.withUsername("sandoval.lucasj")
				.withEmail("sandoval.lucasj@gmail.com")
				.withPassword(new Password("estaesmipass"))
				.withUserPermission(Permission.ADMIN)
				.build();
		Interval valid = new Interval(DateTime.now(), DateTime.now().plusDays(1));

		Product validProduct1 = new ProductBuilder()
			.withName("Arroz")
			.withBrand("Molto")
			.build();

		Product validProduct2 = new ProductBuilder()
			.withName("Leche")
			.withBrand("La Serenisima")
			.build();

		generalService.addProduct(validProduct1);
		generalService.addProduct(validProduct2);

		CombinationOffer validOffer = new CombinationOffer(validProduct1, validProduct2, 15, valid);

		generalService.createUser(user);
		generalService.loginUser(user);
		generalService.createOffer(validOffer, user);
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
	public void testWhenICreateAProductListThenEverythingIsOkay() throws UsernameDoesNotExistException, UserIsNotLoggedException, UserAlreadyExistsException, UsernameOrPasswordInvalidException {

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


	@Test (expected = OfferIsAlreadyCreatedException.class)
	public void testWhenCreatingACategoryOfferThenItCantBeCreatedAgain() throws UserAlreadyExistsException, UsernameDoesNotExistException, WrongUserPermissionException, UserIsNotLoggedException, UsernameOrPasswordInvalidException, OfferIsAlreadyCreatedException {
		Interval valid = new Interval(DateTime.now(), DateTime.now().plusDays(1));
		CategoryOffer validOffer = new CategoryOffer(15, valid, Category.Cleaning);

		User someValidUser = new UserBuilder()
			.withUsername("someUser")
			.withEmail("sandoval.lucasj@gmail.com")
			.withPassword(new Password("mypassword"))
			.withUserPermission(Permission.ADMIN)
			.build();

		generalService.createUser(someValidUser);
		generalService.loginUser(someValidUser);
		generalService.createOffer(validOffer, someValidUser);
		
		List<Offer> all = generalOfferService.retriveAll();
		for (Offer a : all) {
			System.out.println(a.getValidPeriod());
		}
		
		generalService.createOffer(validOffer, someValidUser);
	}

	
	@Test
	public void testTransactional() {
		
		generalService.getUserService().deleteAll();
		
		User someUser = new UserBuilder()
				.withEmail("transactionaltest@gmail.com")
				.withPassword(new Password("altapassword"))
				.withUsername("transactional")
				.withUserPermission(Permission.ADMIN)
				.build();
		
		try {
			generalService.createUserForTest(someUser);
		} catch (Exception e) {
			System.out.println("ASDS");
			System.out.println( generalService.getUserService().retriveAll().size());
			System.out.println("ASDS");
			
		} finally {
			Assert.assertTrue(generalService.getUserService().retriveAll().isEmpty());			
		}
	}
	
	@Test
	public void testArchiteture1() {
		boolean result = true;
		
		try {
			
			Method[] methods = Class.forName("services.general.GeneralService").getMethods();
			Method[] superMethods = Class.forName("services.general.GeneralService").getSuperclass().getMethods();
			
			List<Method> myMethods = Arrays.asList(methods);
			List<Method> myFatherMethods = Arrays.asList(superMethods);
			
			for (Method m : methods) {
				if ( !(m.getName().contains("Service") || myFatherMethods.contains(m)) ) {
					Annotation[] annotations = m.getAnnotations();
					if (annotations.length == 0) {
						result = false;
					}
					for (Annotation a : annotations) {
						result &= a.toString().startsWith("@org.springframework.transaction.annotation.Transactional");
					}
				}
			}
			
			Assert.assertTrue(result);
		
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
	public void testArchiteture2() {
		
		boolean result = false;
		
		try {
			List<Method> mymethods = Arrays.asList(Class.forName("rest.controllers.UsersController").getMethods());
			List<Method> myFatherMethods = Arrays.asList(Class.forName("rest.controllers.UsersController").getSuperclass().getMethods());
			
			for (Method m : mymethods) {
				
				if ( !(m.getName().contains("Service") || myFatherMethods.contains(m)) ) 
				
				{					
					
					System.out.println(m.getName());
					List<Annotation> annotations = Arrays.asList(m.getAnnotations());
					
					if (annotations.isEmpty()){
						result = false;
					}
					
					for (Annotation a : annotations) {
						if (	a.toString().contains("GET") 	||
								a.toString().contains("POST") 	||
								a.toString().contains("PUT") 	|| 
								a.toString().contains("DELETE") 
								) {
							result &= m.getReturnType().getName().contains("javax.ws.rs.core.Response");
						}
					}
				}
				
			}
			
			Assert.assertTrue(result);
			
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
		
	}
	
	

	@After
	public void after(){
		generalService.getUserService().deleteAll();
	}

}
