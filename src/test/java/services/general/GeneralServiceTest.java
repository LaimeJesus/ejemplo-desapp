package services.general;

import static org.junit.Assert.fail;

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
import exceptions.UserAlreadyExistsException;
import exceptions.UsernameOrPasswordInvalidException;
import exceptions.WrongUserPermissionException;
import model.offers.CombinationOffer;
import model.products.Product;
import model.products.ProductList;
import model.users.Profile;
import model.users.User;
import services.microservices.ProductListService;
import services.microservices.ProductService;
import services.microservices.UserService;
import util.Category;
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
	
	@Before
	public void setUp() {
		generalOfferService.deleteAll();
		userService.deleteAll();
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
	public void testWhenICreateAProductListThenEverythingIsOkay() {
		
		ProductList someProductList = new ProductList();
		
		Profile someProfile = new Profile();
		someProfile.addNewProductList(someProductList);
		
		User someUser = new UserBuilder()
			.withUsername("someUser")
			.build();
		
		someUser.setProfile(someProfile);
		
		userService.save(someUser);
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
