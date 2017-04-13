package model;

import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


public class SelectedProductTest {

	@Test
	public void testGetTheProcessingTimeReturnsTheProcessingTimeOfMyProductMultipliedForItsQuantity(){
		
		Product aProductMock = Mockito.mock(Product.class);
		
		SelectedProduct sp = new SelectedProduct(aProductMock, 10);
		
		Mockito.when(aProductMock.getProcessingTime()).thenReturn(new Duration(1L));
		
		Assert.assertEquals(new Duration(10L), sp.getProcessingTime());
	}
}
