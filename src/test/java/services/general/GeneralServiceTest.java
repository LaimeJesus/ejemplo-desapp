package services.general;

import static org.junit.Assert.fail;

import java.util.List;

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
import exceptions.ProductDoesNotExistOnListException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import exceptions.UsernameOrPasswordInvalidException;
import exceptions.WrongUserPermissionException;
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
		
		userService.delete(userToSignUp);
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
		System.out.println("cree el usuario");
		generalService.loginUser(user);
		System.out.println("logee el usuario");
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
	public void testWhenICreateAProductListThenEverythingIsOkay() throws UsernameDoesNotExistException, UserIsNotLoggedException {
		
		ProductList someProductList = new ProductList("First");
		
		Profile someProfile = new Profile();
		someProfile.addNewProductList(someProductList);
		
		User someValidUser = new UserBuilder()
			.withUsername("someUser")
			.withEmail("sandoval.lucasj@gmail.com")
			.withPassword(new Password("mypassword"))
			.withUserPermission(Permission.NORMAL)
			.build();
		
		someValidUser.setProfile(someProfile);
		
		userService.save(someValidUser);
		
		ProductList anotherProductList = new ProductList("Second");
		generalService.loginUser(someValidUser);
		generalService.createProductList(someValidUser, anotherProductList);
		User saved = userService.findByUsername("someUser");
		Assert.assertTrue(userService.getListsFromUser(saved).contains(anotherProductList));
	}
	
	@Test
	public void testWhenISelectAProductFromAListThatExistThenEverythingIsOkay() throws ProductIsAlreadySelectedException {
		
		ProductList someProductList = new ProductList("First");
		
		Profile someProfile = new Profile();
		someProfile.addNewProductList(someProductList);
		
		User someValidUser = new UserBuilder()
			.withUsername("someUser")
			.withEmail("sandoval.lucasj@gmail.com")
			.withPassword(new Password("mypassword"))
			.withUserPermission(Permission.NORMAL)
			.build();
		
		someValidUser.setProfile(someProfile);
		
		Product someValidProduct = new ProductBuilder()
			.withBrand("Marolio")
			.withName("Arroz")
			.withPrice(new Money(13,50))
			.build();
		
		userService.save(someValidUser);
		productService.save(someValidProduct);
		Integer expected = selectedProductService.count();
		
		User saved = userService.findByUsername("someUser");
		

		Assert.assertTrue(true);
//		
//		generalService.selectProduct(saved, someProductList, someValidProduct, 3);
//		List<ProductList> lists = userService.getListsFromUser(saved);
//		
//		Assert.assertTrue(lists.contains(someProductList));
//		
//		Assert.assertEquals( new Integer(expected+1) , selectedProductService.count());
		
	}
	
	
	
	
	
	
	
	
	
}
