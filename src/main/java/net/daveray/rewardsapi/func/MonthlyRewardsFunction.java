package net.daveray.rewardsapi.func;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MonthlyRewardsFunction provides the business logic</br>
 * to transform a transaction query into an ApiResult. </br>
 * Calls the CalculatePointsFunction to get the </br>
 * total points value for a transaction.</br>
 * And then sums the results for total </br>
 * spend amount and points</br>
 * 
 * @author Dave Ray Reizes <daveray@daveray.net>
 * @since November 2023
 */@Component
public class MonthlyRewardsFunction implements Function<List<Map<String, Object>>, Map<String, String>>{

	@Autowired CalculatePointsFunction calculatePoints;
	
	public Map<String, String> apply(List<Map<String, Object>> transactions){
		List<Integer> points = new ArrayList<>();
		List<Integer> amounts = new ArrayList<>();
		Map<String, String> rewards = new HashMap<>();

		rewards.put( "customerId", transactions.get(0).get("CUSTOMER_ID").toString() );
		rewards.put( "name", transactions.get(0).get("NAME").toString() );
		rewards.put( "month", transactions.get(0).get("LEMOIS").toString() );
		
		transactions.stream().forEachOrdered(t -> {
			points.add( calculatePoints.apply( t.get("AMOUNT").toString() ));
			
			String amount = t.get("AMOUNT").toString();
			amount = amount.substring( 0, amount.indexOf(".") );
			amounts.add( Integer.valueOf( amount ) );//no cents .00
			});

		int totalPoints = points.stream().mapToInt(p -> p).sum();
		int totalAmount = amounts.stream().mapToInt(a -> a).sum();
		rewards.put( "points", Integer.valueOf(totalPoints).toString() );
		rewards.put( "amount", Integer.valueOf(totalAmount).toString()+".00" ); //makes cents	
		
		return rewards;
	}
}
