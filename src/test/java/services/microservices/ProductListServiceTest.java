package services.microservices;

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

import builders.UserBuilder;
import exceptions.MoneyCannotSubstractException;
import exceptions.ProductDoesNotExistException;
import exceptions.ProductIsAlreadySelectedException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserIsNotLoggedException;
import exceptions.UsernameDoesNotExistException;
import builders.ProductBuilder;
import model.offers.CategoryOffer;
import model.offers.CrossingOffer;
import model.products.Product;
import model.products.ProductList;
import model.users.User;
import services.general.GeneralOfferService;
import services.general.GeneralService;
import services.microservices.ProductListService;
import util.Password;
import util.Permission;
import util.Category;
import util.Money;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring-persistence-context.xml", "classpath*:/META-INF/spring-services-context.xml" })
public class ProductListServiceTest {

	
	@Autowired
    @Qualifier("services.microservices.productlistservice")
    private ProductListService productListService;
	
	@Autowired
    @Qualifier("services.general.generalofferservice")
    private GeneralOfferService generalOfferService;
	
	@Autowired
    @Qualifier("services.microservices.userservice")
    private UserService userService;
	
	@Autowired
	private GeneralService generalService;
	
	@Before
	public void setUp() {
		productListService.deleteAll();
		generalOfferService.deleteAll();
		userService.deleteAll();
	}

    @Test
    public void testProductListCanBeSaved(){
    	Integer expected = productListService.retriveAll().size();
    	productListService.save(new ProductList("test"));
        Assert.assertEquals(expected+1, productListService.retriveAll().size());
    }
    
    @Test
    public void testWhenAProductListIsSavedThenHasAnId() throws UsernameDoesNotExistException, UserAlreadyExistsException{
    	
    	ProductList someProductList = new ProductList("First");
    	User someUser = new UserBuilder()
    		.withUsername("lucas")
    		.withEmail("sandoval.lucasj@gmail.com")
    		.withPassword(new Password("asdasd"))
    		.withUserPermission(Permission.NORMAL)
    		.build();
    	
    	someUser.getProfile().addNewProductList(someProductList);
		generalService.createUser(someUser);
		
		ProductList expected = productListService.getByUser(new ProductList("First"), someUser);
		
		Assert.assertNotEquals(expected.getId() , null);
		
		generalService.getUserService().deleteAll();
		generalService.getProductListService().deleteAll();
    }
    
    @Test
    public void testProductListWithOffersCanBeSaved() 
     throws MoneyCannotSubstractException, 
    		UsernameDoesNotExistException, 
    		UserAlreadyExistsException, 
    		UserIsNotLoggedException, 
    		ProductIsAlreadySelectedException, 
    		ProductDoesNotExistException {
    	
    	
    	
    	ProductList aPl = new ProductList("MyProductList");
    	
    	CategoryOffer offer1 = new CategoryOffer();
    	CrossingOffer offer2 = new CrossingOffer();
    	
    	aPl.getAppliedOffers().add(offer1);
    	aPl.getAppliedOffers().add(offer2);
    	
    	generalOfferService.save(offer1);
    	generalOfferService.save(offer2);
    	
    	User someUser = new UserBuilder()
    		.withUsername("lucas")
    		.withEmail("sandoval.lucasj@gmail.com")
    		.withPassword(new Password("asdasd"))
    		.withUserPermission(Permission.NORMAL)
    		.build();
    	
    	generalService.createUser(someUser);
    	generalService.loginUser(someUser);
    	generalService.createProductList(someUser, aPl);
    	
    	Integer expected = generalOfferService.retriveAll().size();
    	 
    	Product someProduct = new ProductBuilder()
    		.withBrand("Marolio")
    		.withName("Arroz")
    		.withCategory(Category.Fruit)
    		.withPrice(new Money(15,15))
    		.withStock(54)
    		.build();
    	
    	generalService.getProductService().save(someProduct);	
    	generalService.selectProduct(someUser, aPl, someProduct, 3);
    	
    	CategoryOffer offer3 = new CategoryOffer(
			10,
			new Interval(DateTime.now() , DateTime.now().plusDays(1)) , 
			Category.Baked
			);
    	
    	generalService.applyOffer(someUser, offer3, aPl);
    	
    	Assert.assertEquals(expected , new Integer(generalOfferService.retriveAll().size()) );
    	
    	generalService.getGeneralOfferService().deleteAll();
    	generalService.getUserService().deleteAll();
    	generalService.getProductService().deleteAll();
    }
	
}
