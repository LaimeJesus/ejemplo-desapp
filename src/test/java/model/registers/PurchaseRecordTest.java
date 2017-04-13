package model.registers;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import model.ProductList;
import model.PurchaseRecord;

public class PurchaseRecordTest {

	@Test
	public void testANewPurchaseRecordHasTheCurrentDaytime(){
		
		ProductList aProductListMock = Mockito.mock(ProductList.class);
		
		Assert.assertEquals(DateTime.now().getMinuteOfHour(), new PurchaseRecord(aProductListMock).getPurchasingDate().getMinuteOfHour());
	}

}
