package services.microservices;

import org.junit.Test;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.recommendations.Recommendation;
import services.microservices.RecommendationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/META-INF/spring-persistence-context.xml", "/META-INF/spring-services-context.xml" })
public class RecommendationServiceTest {

	@Autowired
	@Qualifier("services.microservices.recommendationservice")
	private RecommendationService recommendationService;
	
	
	@Test
	public void testARecommendationCanBeSaved() {
		recommendationService.save(new Recommendation());
		
		Assert.assertEquals(1, recommendationService.retriveAll().size());
	}
	
}
