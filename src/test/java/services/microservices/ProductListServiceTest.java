package services.microservices;

import static org.junit.Assert.fail;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import builders.ProductBuilder;
import exceptions.MoneyCannotSubstractException;
import model.offers.CategoryOffer;
import model.offers.CrossingOffer;
import model.products.Product;
import model.products.ProductList;
import services.general.GeneralOfferService;
import services.microservices.ProductListService;
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
	

    @Test
    public void testProductListCanBeSaved(){
    	Integer expected = productListService.retriveAll().size();
    	productListService.save(new ProductList());
        Assert.assertEquals(expected+1, productListService.retriveAll().size());
    }
    
    @Test
    public void testProductListWithOffersCanBeSaved() {
    	
    	ProductList aPl = new ProductList();
    	aPl.setTotalAmount(new Money(250,0));
    	Product someProduct = new ProductBuilder()
    		.withBrand("Marolio")
    		.withName("Arroz")
    		.withCategory(Category.Fruit)
    		.withPrice(new Money(15,15))
    		.withStock(54)
    		.build();
    	
    	try {
			aPl.applyOffer(
				new CategoryOffer(
					10,
					new Interval(DateTime.now() , DateTime.now().plusDays(1)) , 
					Category.Baked
				)
			);
			aPl.applyOffer(
				new CrossingOffer(
					10,
					someProduct,
					3,
					2,
					new Interval(DateTime.now() , DateTime.now().plusDays(1))
				)
			);
			Integer expected = generalOfferService.retriveAll().size();
			productListService.save(aPl);			
			Assert.assertEquals(expected+2 , generalOfferService.retriveAll().size() );
		} catch (MoneyCannotSubstractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
    	 
    	
    }
	
}
