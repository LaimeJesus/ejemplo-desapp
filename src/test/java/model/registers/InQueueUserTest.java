package model.registers;

import org.junit.Assert;
import org.junit.Test;

import model.products.ProductList;
import model.users.User;

public class InQueueUserTest {

	@Test
	public void testWhenAnInQueueUserTestCallsNewPurchaseThenAddsItsProductListToItsProfile(){
		User anUser = new User();
		ProductList aProductList = new ProductList();
		InQueueUser anInQueueUser = new InQueueUser(aProductList, anUser);
		
		anInQueueUser.newPurchase();
		
		Assert.assertEquals(1,anUser.getProfile().getPurchaseRecords().size());
	}
	
	@Test
	public void testAnInQueueUserWithAnEmptyProductListCallsGetProcessingTimeThenReturnsZeroSeconds(){
		User anUser = new User();
		ProductList aProductList = new ProductList();
		InQueueUser anInQueueUser = new InQueueUser(aProductList, anUser);
		
		Assert.assertEquals(0, anInQueueUser.getProcessingTime().getMillis());
	}
	
	
	
}
