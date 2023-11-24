package net.daveray.rewardsapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import net.daveray.rewardsapi.func.CalculatePointsFunction;


/**
 * CalculatePointsFunction unit test
 * 
 * @author Dave Ray Reizes <daveray@daveray.net>
 * @since November 2023
 */
@SpringBootTest
public class CalculatePointsFunctionTest {
	static Logger logger = LoggerFactory.getLogger(CalculatePointsFunctionTest.class);
   
    static CalculatePointsFunction calculatePoints = new CalculatePointsFunction();
    
    @Test
	void TestCalculatePoints() {
		List<Integer> points = new ArrayList<>();
		List<Map<String, Object>> response = new ArrayList<>();
		Map<String, Object> results = new HashMap<>();
		
		results.put("AMOUNT", "177.00");
		response.add(results);
		
		results = new HashMap<>();
		results.put("AMOUNT", "23.00");
		response.add(results);
		
		results = new HashMap<>();
		results.put("AMOUNT", "192.00");
		response.add(results);		
		
		results = new HashMap<>();
		results.put("AMOUNT", "583.00");
		response.add(results);		
		
		results = new HashMap<>();
		results.put("AMOUNT", "10.00");
		response.add(results);		
		
		response.stream().forEachOrdered(e -> {
			points.add( calculatePoints.apply(e.get("AMOUNT").toString()) );
			});
		logger.info(points.toString());

		int amount = points.stream().mapToInt(p -> p).sum();		
		logger.info(Integer.valueOf(amount).toString());
		
		assertNotNull(amount, "amount is null");
		assertEquals(amount, 1454);
	}

}
